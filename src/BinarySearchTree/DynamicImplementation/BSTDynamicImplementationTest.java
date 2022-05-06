package src.BinarySearchTree.DynamicImplementation;

import org.junit.jupiter.api.DisplayName;
import        org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import        org.junit.jupiter.api.BeforeEach;
import src.Files.FileOps;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;


public class BSTDynamicImplementationTest {

    BST_DynamicImpementation UUT;
    private final static int[] testArray = {10, 50, 60, 70, 100, 120, 55, 30, 40, 20};

    @BeforeEach
    void setUp() {
        UUT = new BST_DynamicImpementation();
    }
    
    @Test
    @DisplayName("Test whether a predefined array is constructed correctly")
    void AppendTest() {
        
        for(int T : testArray)                                                              // Inserts all the elements of the testArray into the table
            UUT.Push(T);
    
        Node current = UUT.Root;
        
        assertEquals(10, current.getInfo(), "Root should be 10");           // Root should be 10
        assertNull(current.getLeft(), "Root's left should be null");
        current = current.getRight();                                                       // Move to the right child of the root (50).
        assertEquals(50, current.getInfo(), "Root's right should be 50");
        assertEquals(2,current.getChildrenNumber(), "Root's right should have 2 children");
        current = current.getLeft();                                                       // Move to the left child of the root's right (30).
        assertEquals(30,current.getInfo(), "Left node should be 30");
        assertEquals(2,current.getChildrenNumber(), "Left node should have 2 children");
        assertEquals(20,current.getLeft().getInfo(), "Left node's left should be 20");
        assertEquals(40,current.getRight().getInfo(), "Left node's right should be 40");
        current = UUT.Search(60);                                                            // Find 60
        assertEquals(60,current.getInfo(), "60 should be found");
        assertEquals(2,current.getChildrenNumber(), "60 should have 2 children");
        assertEquals(55,current.getLeft().getInfo(), "60's left should be 55");
        assertEquals(70,current.getRight().getInfo(), "60's right should be 70");
        current = UUT.Search(55);                                                            // Find 55
        assertEquals(55,current.getInfo(), "55 should be found");
        assertEquals(0,current.getChildrenNumber(), "55 should have no children");
        current = UUT.Search(70);                                                            // Find 70
        assertEquals(70,current.getInfo(), "70 should be found");
        assertEquals(1,current.getChildrenNumber(), "70 should have 1 child");
        assertNull(current.getLeft(), "70's left should be null");
        assertEquals(100,current.getRight().getInfo(), "70's right should be 100");
        current = UUT.Search(100);                                                           // Find 100
        assertEquals(100,current.getInfo(), "100 should be found");
        assertEquals(1,current.getChildrenNumber(), "100 should have 1 child");
        assertNull(current.getLeft(), "100's left should be null");
        assertEquals(120,current.getRight().getInfo(), "100's right should be 120");;
        current = UUT.Search(120);                                                           // Find 120
        assertEquals(120,current.getInfo(), "120 should be found");
        assertEquals(0,current.getChildrenNumber(), "120 should have no children");
    }
    
    @DisplayName("Tests whether the delete method works correctly")
    @ParameterizedTest(name = "{index} => Deleting {0} from the tree" )
    @ValueSource(ints = {120, 70, 60, 10 })
    void delete(int number) {
        for(int T : testArray)                              // Inserts all the elements of the testArray into the table
            UUT.Push(T);
    
        assertTrue(UUT.isBST(UUT.Root), "Tree is constructed incorrectly");
        
        UUT.Pull(number);                                 // Deletes the element with the given number
        
        switch (number) {
            case 10:
                assertEquals(50,UUT.Root.getInfo(), "Root should be 50");
                assertEquals(2,UUT.Root.getChildrenNumber(), "Root should have 2 children");
                assertEquals(30,UUT.Root.getLeft().getInfo(), "Root's left should be 30");
                assertEquals(60,UUT.Root.getRight().getInfo(), "Root's right should be 60");
                assertNull(UUT.Search(10), "10 should not be found");
                break;
            case 60:
                assertEquals(10,UUT.Root.getInfo(), "Root should be 10");
                assertEquals(2, UUT.Search(50).getChildrenNumber(), "50 should have 2 children");
                assertEquals(30,UUT.Search(50).getLeft().getInfo(), "50's left should be 30");
                assertEquals(70,UUT.Search(50).getRight().getInfo(), "50's right should be 70");
                assertNull(UUT.Search(60), "60 should not be found");
                break;
            case 70:
                assertEquals(10,UUT.Root.getInfo(), "Root should be 10");
                assertEquals(2, UUT.Search(60).getChildrenNumber(), "60 should have 2 children");
                assertEquals(55,UUT.Search(60).getLeft().getInfo(), "60's left should be 55");
                assertEquals(100,UUT.Search(60).getRight().getInfo(), "60's right should be 100");
                assertNull(UUT.Search(70), "70 should not be found");
                break;
            case 120:
                assertEquals(10,UUT.Root.getInfo(), "Root should be 10");
                assertEquals(0, UUT.Search(100).getChildrenNumber(), "100 should have no children");
                assertTrue(UUT.Search(100).isLeaf(), "100 should be a leaf");
                assertNull(UUT.Search(120), "120 should not exist");
                break;
            default:
                fail("The number that was deleted is not in the table");
        }
    }

    @ParameterizedTest(name = "{index} => Test adding to the array {0} Elements" )
    @DisplayName("Tests whether the append method works correctly for different sizes of arrays")
    @ValueSource(ints = {10, 100, 1000, 10000, 100000, 1000000})
    void LargeScaleTest(int size) {
        int[] arr = new int[size];
        final int[] counter = {0};
        ThreadLocalRandom.current().ints(0, Integer.MAX_VALUE).distinct().limit(size).forEach(i -> {
            arr[counter[0]] = i;
            counter[0]++;
        });
        
        Duration start = Duration.ofNanos(System.nanoTime());
        
        assertTimeout(Duration.ofSeconds(5), () -> {
            for(int i = 0 ; i < size ; i++ ){
                UUT.Push(arr[i]);
            }
        }, "The table should be filled with " + size + " elements within 2 second");
        System.out.print("Duration of Append for " + size + " Elements : " + Duration.ofNanos(System.nanoTime()).minus(start).toMillis() + "ms |");
        System.out.println("Average comparisons : " + UUT.getCounter()/size + " comparisons");
    }

    
    @Test
    @DisplayName("Test the functionality of each method against real data")
    void RealWorldScenario() throws IOException {
        FileOps DataFile = new FileOps("src/Files/keys_1000000_BE.bin");
        FileOps DelFile  = new FileOps("src/Files/keys_del_100_BE.bin");
        
        int[] InsertionKeys = DataFile.Read();
        int[] DeletionKeys  = DelFile.Read();
        
        for(int key : InsertionKeys) {
            UUT.Push(key);
        }
        
        int counter = 0;
        
        for(int key : DeletionKeys) {
            UUT.Pull(key);
            counter++;
            assertTrue(UUT.isBST(UUT.Root), "The tree is incorrect after the " + counter + "th deletion");
        }
    }
}

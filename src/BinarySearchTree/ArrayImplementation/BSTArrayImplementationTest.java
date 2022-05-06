package src.BinarySearchTree.ArrayImplementation;

import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.Timeout;
import src.Files.FileOps;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.*;

class BSTArrayImplementationTest {

    private final static int SIZE = 10;
    private final static int[] testArray = {10, 50, 60, 70, 100, 120, 55, 30, 40, 20};

    BST_ArrayImplementation UUT;
    
    @Rule
    Timeout globalTimeout = Timeout.seconds(2);
    
    @BeforeEach
    void setUp() {
        UUT = new BST_ArrayImplementation(10);
    }

    @AfterEach
    void tearDown() {
        UUT = null;
    }

    @DisplayName("Tests whether the delete method works correctly")
    @ParameterizedTest
    @ValueSource(ints = {120, 70, 60, 10 })
    void delete(int number) {
        for(int T : testArray)                              // Inserts all the elements of the testArray into the table
            UUT.Push(T);

        UUT.Pull(number);                                 // Deletes the element with the given number


        switch (number) {
            case 10:
                assertEquals(2,UUT.getChildrenNum(UUT.Search(50)), " The number of children of the new root should be 2");
                assertNotNull(UUT.table[UUT.Search(50)][1], " The left child of the new root should not be null");
                assertNotNull(UUT.table[UUT.Search(50)][1], " The right child of the new root should not be null");
                assertEquals(30,UUT.Value(UUT.Left(UUT.Search(50))), " The left child of the new root should be 30");
                assertEquals(60,UUT.Value(UUT.Right(UUT.Search(50))), " The right child of the new root should be 60");
                break;
            case 60:
                assertEquals(1,UUT.getChildrenNum(UUT.Search(55)), " The number of children of the parent should be 1");
                assertEquals(70,UUT.Value(UUT.Right(UUT.Search(55))), " The child should now be 70");
                break;
            case 70:
                assertEquals(2,UUT.getChildrenNum(UUT.Search(60)), " The number of children of the parent should be 2");
                assertEquals(100,UUT.table[UUT.Right(UUT.Search(60))][0], " The child of the parent should be 100");
                break;
            case 120:
                assertEquals(0,UUT.getChildrenNum(UUT.Search(100)), " The number of children of the parent should be 0");
                break;
            default:
                fail("The number that was deleted is not in the table");
        }
    }

    @Test
    @DisplayName("Tests whether the table is constructed correctly")
    void append() {
        UUT.Reset();

        for(int T : testArray)
            UUT.Push(T);

        System.out.println(UUT.getDuration().toNanos() + " milliseconds | Accesses : " + UUT.getCounter());

        UUT.Reset();

        for(int i = 0; i < SIZE; i++) {
            assertNotEquals(testArray[i], -1, "Element " + i + " not found");
            assertTrue(UUT.isBST(UUT.Root), "The tree constructed incorrectly");
        }
        
        assertEquals(1, UUT.getChildrenNum(UUT.Search(10)), "Wrong children number for 10");
        assertEquals(2, UUT.getChildrenNum(UUT.Search(50)), "Wrong children number for 50");
        assertEquals(2, UUT.getChildrenNum(UUT.Search(30)), "Wrong children number for 30");
        assertEquals(0, UUT.getChildrenNum(UUT.Search(20)), "Wrong children number for 20");
        assertEquals(0, UUT.getChildrenNum(UUT.Search(40)), "Wrong children number for 40");
        assertEquals(0, UUT.getChildrenNum(UUT.Search(55)), "Wrong children number for 55");
        assertEquals(1, UUT.getChildrenNum(UUT.Search(70)), "Wrong children number for 70");
        assertEquals(1, UUT.getChildrenNum(UUT.Search(100)), "Wrong children number for 100");
        assertEquals(0, UUT.getChildrenNum(UUT.Search(120)), "Wrong children number for 120");

        for(int T : testArray){
            Integer left  = UUT.table[UUT.Search(T)][1];
            Integer right = UUT.table[UUT.Search(T)][2];
            Integer LeftInfo = null;
            Integer RightInfo = null;
            if(left != null)
                LeftInfo = UUT.table[left][0];
            if(right != null)
                RightInfo = UUT.table[right][0];

            switch (T){
                case 10:
                    assertNull(left, "Left child of 10 is not null");
                    assertNotNull(right, "Right child of 10 is null");
                    assertEquals(50, RightInfo, "Wrong right child of 10");
                    break;
                case 50:
                    assertNotNull(left, "Left child of 50 is null");
                    assertNotNull(right, "Right child of 50 is null");
                    assertEquals(30, LeftInfo, "Wrong left child of 50");
                    assertEquals(60, RightInfo, "Wrong right child of 50");
                    break;
                case 60:
                    assertNotNull(left, "Left child of 60 is null");
                    assertNotNull(right, "Right child of 60 is null");
                    assertEquals(70, RightInfo, "Wrong right child of 60");
                    assertEquals(55, LeftInfo, "Wrong left child of 60");
                    break;
                case 70:
                    assertNull(left, "Left child of 70 is not null");
                    assertNotNull(right, "Right child of 70 is null");
                    assertEquals(100, RightInfo, "Wrong right child of 70");
                    break;
                case 100:
                    assertNull(left, "Left child of 100 is not null");
                    assertNotNull(right, "Right child of 100 is null");
                    assertEquals(120, RightInfo, "Wrong right child of 100");
                    break;
                case 55:
                    assertNull(left, "Left child of 55 is not null");
                    assertNull(right, "Right child of 55 is not null");
                    break;
                case 30:
                    assertNotNull(left, "Left child of 30 is null");
                    assertNotNull(right, "Right child of 30 is null");
                    assertEquals(20, LeftInfo, "Wrong left child of 30");
                    assertEquals(40, RightInfo, "Wrong right child of 30");
                    break;
                case 40:
                    assertNull(left, "Left child of 40 is not null");
                    assertNull(right, "Right child of 40 is not null");
                    break;
                case 20:
                    assertNull(left, "Left child of 20 is not null");
                    assertNull(right, "Right child of 20 is not null");
                    break;
                case 120:
                    assertNull(left, "Left child of 120 is not null");
                    assertNull(right, "Right child of 120 is not null");
                    break;
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100,1000,10000, 100000, 1000000})
    @DisplayName("Tst Appending large amounts of data into the tree")
    void LargeScaleAppend(int size) {
        
        UUT = new BST_ArrayImplementation(size);
        
        int[] arr = ThreadLocalRandom.current().ints(0, Integer.MAX_VALUE).distinct().limit(size).toArray();
        
        System.out.println("Done generating random numbers");
        
        assertTimeout(Duration.ofSeconds(10), () -> {
            for(int i = 0; i < size; i++){
                UUT.Push(arr[i]);
            }
        }, "Append took too long");
    
        assertTrue(UUT.isBST(UUT.Root), "Tree is not constructed correctly");
        
        System.out.print("Done appending " + size + " elements | ");
        System.out.println("Accesses: " + UUT.getCounter()/size + "\n");
        
    }
    
    @RepeatedTest(10)
    void TestThatBreaks(){
        UUT = new BST_ArrayImplementation(10);
    
        int[] arr = ThreadLocalRandom.current().ints(0, 300).distinct().limit(10).toArray();
        
        for(int val : arr)
            UUT.Push(val);
        
        assertTrue(UUT.isBST(UUT.Root), "Tree is not constructed correctly");
        
        int counter = 0;
        for(int val : arr) {
            UUT.Pull(val);
            counter++;
            assertTrue(UUT.isBST(UUT.Root), "The tree is incorrect after the " + counter + "th deletion");
        }
    }
    
    @Test
    @DisplayName("Test the functionality of each method against real data")
    void RealWorldScenario() throws IOException {
        FileOps DataFile = new FileOps("src/Files/keys_1000000_BE.bin");
        FileOps DelFile = new FileOps("src/Files/keys_del_100_BE.bin");
    
        int[] InsertionKeys = DataFile.Read();
        int[] DeletionKeys = DelFile.Read();
    
        UUT = new BST_ArrayImplementation(InsertionKeys.length);
        
        for (int key : InsertionKeys)
            UUT.Push(key);
        
        assertTrue(UUT.isBST(UUT.Root), " Tree is not constructed correctly");
    
        System.out.println("Done inserting");
        int counter = 0;
    
        for (int key : DeletionKeys) {
            UUT.Pull(key);
            counter++;
            assertTrue(UUT.isBST(UUT.Root), "The tree is incorrect after the " + counter + "th deletion");
        }
    }
}
package src.PriorityQueue.DynamicImplementation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import src.Files.FileOps;
import src.PriorityQueue.ArrayImplementation.PQ_ArrayImplementation;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.max;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class PQDynamicImplementationTest {
    
    /** Data for the heap to be tested on. */
    private final static int[] testArray = {10, 50, 60, 70, 100, 120, 55, 30, 40, 20};
    private final static int[] Result = {120, 100,70, 60, 55, 50, 40, 30, 20, 10};
    
    PQ_DynamicImplementation UUT;

    @BeforeEach
    void setUp() {
        UUT = new PQ_DynamicImplementation();
    }
    
    @AfterEach
    void tearDown() {
        UUT = null;
    }
    
    @Test
    @DisplayName("Test adding a predefined set of numbers into the tree and then checking if the tree is in the correct order")
    void Append() {
        for (int j : testArray) {
            UUT.Push(j);
            System.out.print(j + ", ");
        }
        
        System.out.println("\nDone Adding\n");
        
        for (int i = 0; i < testArray.length; i++) {
            int num = UUT.Pull();
            System.out.print(num + ", ");
            assertEquals( Result[i],num, "The heap is not in the correct order");
        }
    }
    
    @Test
    void AppendArray(){
        UUT.Push(testArray);
    
        System.out.println("Done Adding");

         for (int i = 0 ; i < testArray.length; i++) {
             int num = UUT.Pull();
             assertEquals(Result[i],num, "The heap is not in the correct order");
         }
    }
    
    @RepeatedTest(300)
    void RandomAppendTest() {
        Random r = new Random();
        Vector<Integer> v = new Vector<>();

        for (int i = 0; i < 20; i++) {
            int num = r.nextInt(100);
            while (v.contains(num)) {
                num = r.nextInt(100);
            }
            System.out.print(num + ", ");
            v.add(num);
            UUT.Push(num);
        }

        System.out.println("\nDone Adding");

        for (int i = 0; i < 20; i++) {
            int num = UUT.Pull();
            System.out.print(num + ", ");
            assertEquals(max(v), num, "The heap is not in the correct order");
            v.remove((Integer) num);
        }

    }
    
    @Test
    @DisplayName("Test that breaks")
    void TestThatBreaks() {
        int[] val = { 19, 21, 26, 68, 78, 3, 46, 82, 69, 77, 7, 51, 81, 83, 54, 65, 63, 9, 15, 99 };
        Vector<Integer> v = new Vector<>();
        
        for (int i = 0; i < 20; i++) {
            v.add(val[i]);
            System.out.print(val[i] + ", ");
            UUT.Push(val[i]);
        }

        System.out.println("\nDone Adding");

        for (int i = 0; i < 20; i++) {
            int num = UUT.Pull();
            System.out.print(num + ", ");
            assertEquals(max(v), num, "The heap is not in the correct order");
            v.remove((Integer) num);
        }

    }
    
    @ParameterizedTest
    @ValueSource(ints = { 10, 100, 1000, 10000, 100000, 1000000 })
    @Timeout(value = 10)
    @DisplayName("Large Scale Test")
    void LargeScaleTest(int size) {
        int[] arr = new int[size];
        Vector<Integer> v = new Vector<>();
        
        arr = ThreadLocalRandom.current().ints(0, Integer.MAX_VALUE).distinct().limit(size).toArray();
        for(int val : arr)
            v.add(val);
        
        System.out.println("Done generating random numbers");
    
        int[] finalArr = arr;
        assertTimeout(Duration.ofSeconds(1), () -> {
            for (int i = 0; i < size; i++) {
                UUT.Push(finalArr[i]);
            }
        }, "The heap must be built in less than 1 second");
    
        System.out.println("Added " + size + " numbers, took " + UUT.getDuration().toMillis() + " milliseconds, and performed " + UUT.getCounter()/size+ " comparisons");
        UUT.Reset();
        
        for(int i = 0; i < size; i++) {
            AtomicInteger num = new AtomicInteger();
            assertTimeout(Duration.ofSeconds(1), () -> {
                        num.set(UUT.Pull());
                    }, "The value must be pulled in less than 1 second");
            assertEquals(max(v), num.get(), "The heap is not in the correct order");
            v.remove((Integer) num.get());
        }
    
        System.out.println("Pulled " + size + " numbers, took " + UUT.getDuration().toMillis() + " milliseconds, and performed " + UUT.getCounter()/size+ " comparisons");
        UUT.Reset();
    
        System.out.println();
        
    }
    
    @Test
    @DisplayName("Test adding the file data into the Queue")
    void RealWorldTest() throws IOException {
        FileOps DataFile = new FileOps("src/Files/keys_1000000_BE.bin");
        FileOps DelFile  = new FileOps("src/Files/keys_del_100_BE.bin");
        
        int[] InsertionKeys = DataFile.Read();
        int[] DeletionKeys  = DelFile.Read();
        
        for(int key : InsertionKeys) {
            UUT.Push(key);
        }
        
        Assertions.assertTrue(UUT.isPQ(UUT.Root), "The Priority Queue is not constructed correctly");
        
        for(int key : DeletionKeys) {
            UUT.Pull();
            Assertions.assertTrue(UUT.isPQ(UUT.Root), "The Priority Queue is not pulling correctly");
        }
    }
    
}
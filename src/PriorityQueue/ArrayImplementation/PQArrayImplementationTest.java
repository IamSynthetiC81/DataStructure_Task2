package src.PriorityQueue.ArrayImplementation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import src.Files.FileOps;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Collections.max;
import static org.junit.jupiter.api.Assertions.assertEquals;


class PQArrayImplementationTest {
    
    PQ_ArrayImplementation UUT;
    
    @BeforeEach
    void setUp() {
        UUT = new PQ_ArrayImplementation(20);
    }
    
    @AfterEach
    void tearDown() {
        UUT = null;
    }
    
    @Test
    @DisplayName("Small scale test for basic functionality")
    void OrderedPushTest() {
        for(int i = 1 ; i <= 10 ; i++){
            UUT.Push(i);
        }
        System.out.println("Done adding");
        
        for(int i = 0 ; i < 10 ; i++){
            int num = UUT.Pull();
            System.out.print(num + ", ");
            assertEquals(10 - i, num, "Expected: " + (10 - i) + " Actual: " + num);
        }
        System.out.println("\nsuccess\n\n");
    }
    @Test
    @DisplayName("A Test that used to break used for finding bugs" )
    void PushTestThatBreaks(){
        int[] val = {79,75,87,61,58,81,43,77,35,64};
        Collection<Integer> c = new Vector<>();
        
        
        for(int v : val) {
            UUT.Push(v);
            c.add(v);
        }
            
        System.out.println("Done adding");
        
        for(int i = 0 ; i < 10 ; i++){
            int num = UUT.Pull();
            System.out.print(num + ", ");
            assertEquals(max(c), num, "Expected: " + max(c) + " Actual: " + num);
            c.remove(num);
        }
    }
    
    
    @ParameterizedTest(name = "{index} => Adding {0} Elements to the PQ")
    @ValueSource(ints = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 })
    @DisplayName("Test adding many values to the PQ, then pulling them, and checking whether the order is correct")
    void RandomPushTest(int size) {
        UUT = new PQ_ArrayImplementation(size);
        
        int[] arr = new int[size];
        Vector<Integer> v = new Vector<>();
        final int[] counter = {0};
        ThreadLocalRandom.current().ints(0, Integer.MAX_VALUE).distinct().limit(size).forEach(i -> {
            arr[counter[0]] = i;
            v.add(i);
            counter[0]++;
        });

        for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + ", ");
            UUT.Push(arr[i]);
        }

        System.out.println("\nDone adding");

        for (int i = 0; i < size; i++) {
            int num = UUT.Pull();
            Integer max =max(v);
            v.remove(max);
            System.out.print(num + ", ");
            assertEquals(max, num, "Expected: " + max + " Actual: " + num);
        }
        System.out.println("\nsuccess\n\n");

    }
    
    @ParameterizedTest(name = "{index} => Appending {0} Elements to the PQ")
    @ValueSource(ints = { 100, 1000, 10000, 100000, 1000000 })
    @DisplayName("Test adding many values to the PQ")
    void LargeScalePush(int size) {
        UUT = new PQ_ArrayImplementation(size);

        int[] Accesses = new int[size];
        
        for (int i = 0; i < size; i++) {
            UUT.Push(i);
            Accesses[i] = UUT.getCounter();
            UUT.Reset();
        }
    
        System.out.println("Average Accesses: " + Arrays.stream(Accesses).average().getAsDouble());
    }
    
    @Test
    @DisplayName("Test adding the file data into the Queue")
    void RealWorldTest() throws IOException {
        FileOps DataFile = new FileOps("src/Files/keys_1000000_BE.bin");
        FileOps DelFile  = new FileOps("src/Files/keys_del_100_BE.bin");
    
        int[] InsertionKeys = DataFile.Read();
        int[] DeletionKeys  = DelFile.Read();
    
        UUT = new PQ_ArrayImplementation(InsertionKeys.length);
        
        for(int key : InsertionKeys) {
            UUT.Push(key);
        }
    
        Assertions.assertTrue(UUT.isPQ(0), "The Priority Queue is not constructed correctly");
    
        for(int key : DeletionKeys) {
            UUT.Pull();
            Assertions.assertTrue(UUT.isPQ(0), "The Priority Queue is not pulling correctly");
        }
    }
    
}
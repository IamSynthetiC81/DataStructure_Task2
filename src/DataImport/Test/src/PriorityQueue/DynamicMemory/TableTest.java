package src.PriorityQueue.DynamicMemory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.DataImport.FileOps;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    
    Table UUT;
    FileOps fileOps;
    
    @BeforeEach
    void setUp() {
        UUT = new Table();
        fileOps = new FileOps("src/Files/keys_1000000_BE.bin");
    }
    
    @AfterEach
    void tearDown() {
        UUT = null;
    }
    
    @Test
    @DisplayName("Test for Data Insertion")
    void append() {
        // Insert 100 elements into the table
        for(int key = 0; key < 100 ; key++) {
            UUT.Append(key);
        }
    
        // Check if the table is sorted after insertion of 100 elements in ascending order
        for(int i = 1 ; i < 100 ; i++) {
            assertTrue(UUT.Table[i] > UUT.Table[i-1], "Key " + i + " is not greater than " + (i-1));
        }
        
        // Check of the keys are in the correct place and order in the table after insertion.
        for(int key = 0; key < 100 ; key++) {
            assertEquals(key, UUT.Table[key]);
        }
    }
    
    @Test
    @DisplayName("Test for Data Removal")
    void remove() {
        for(int key = 0; key < 100 ; key++) {
            UUT.Append(key);
        }
        
        Vector<Integer> keys = new Vector<>();
        Random rand = new Random();
        
        // Generate random and unique keys to remove from the table and store them in a vector for comparison later.
        for(int key = 0; key < 30 ; key++) {
            int val = rand.nextInt(100);                         // Generate random key.
            while(keys.contains(val)){                                  // Check if the key is already in the vector.
                val = rand.nextInt(100);                          // If it is, generate a new key and check again.
            }                                                           // This is to ensure that the keys are unique.
           keys.add(val);                                               // Add the key to the vector.
        }
    
        for (Integer integer : keys) {                                  // For each key in the vector of keys to remove.
            UUT.Remove(integer);                                        // - Remove the key from the table.
        }
    
        for (Integer key : keys) {                                      // Check if the keys are still in the table.
            assertEquals(-1, UUT.Query(key), " Key " + key + " not removed");
        }
        
        // Check if the table is sorted after removal of 30 elements in ascending order
        for(int i = 1 ; i < UUT.Table.length ; i++ ){
            assertTrue(UUT.Table[i] > UUT.Table[i-1], "Key " + i + " is not greater than " + (i+1));
        }
    }
    
    @Test
    @DisplayName("Test for Data Search")
    void query() {
        for(int key = 0; key < 100 ; key++) {
            UUT.Append(key);
        }
    
        for(int key = 0; key < 100 ; key++) {
            assertNotEquals(-1, UUT.Query(key), " Key " + key + " not found");
        }
    }
}
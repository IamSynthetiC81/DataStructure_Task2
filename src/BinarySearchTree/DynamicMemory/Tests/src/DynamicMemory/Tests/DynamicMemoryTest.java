package src.BinarySearchTree.DynamicMemory.Tests.src.DynamicMemory.Tests;

import        org.junit.jupiter.api.Test;
import src.BinarySearchTree.DynamicMemory.BST;
import static org.junit.jupiter.api.Assertions.*;

import        org.junit.jupiter.api.BeforeEach;


public class DynamicMemoryTest {

    BST UUT;

    @BeforeEach
    void setUp() {
        UUT = new BST();
    }
    
    @Test
    void AppendTest() {

        // Test for exception
        for (int i = 0; i < 50; i++) {
            assertNull( UUT.Query(i), "Should not find a node with info " + i + " when it does not exist.");
            
        }

        // Test for no exception
        for (int i = 0; i < 50; i++) {
            final int I = i;
            assertDoesNotThrow(() -> {
                UUT.Append(I);
                UUT.Query(I);

            });
        }

        // Test for exception
        for (int i = 0; i < 50; i++) {
            final int I = i; 

            assertThrows(IllegalArgumentException.class, () -> {
                UUT.Append(I);
            });

        }
    }

}

package src.ArrayImplementation;

import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import src.ArrayImplementation.Table;

import static org.junit.jupiter.api.Assertions.*;


class TableTest {

    private final static int SIZE = 10;
    private final static int[] testArray = {10, 50, 60, 70, 100, 120, 55, 30, 40, 20};


    Table UUT;

    @BeforeEach
    void setUp() {
        UUT = new Table(10);
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
            UUT.Append(T);

        UUT.Delete(number);                                 // Deletes the element with the given number


        switch (number) {
            case 10:
                assertEquals(-1,UUT.findParent(UUT.Search(50)), " The parent of the new root should be -1");
                assertEquals(2,UUT.getChildrenNum(UUT.Search(50)), " The number of children of the new root should be 2");
                assertNotNull(UUT.table[UUT.Search(50)][1], " The left child of the new root should not be null");
                assertNotNull(UUT.table[UUT.Search(50)][1], " The right child of the new root should not be null");
                assertEquals(30,UUT.getInfo(UUT.getLeftChild(UUT.Search(50))), " The left child of the new root should be 30");
                assertEquals(60,UUT.getInfo(UUT.getRightChild(UUT.Search(50))), " The right child of the new root should be 60");
                break;
            case 60:
                assertEquals(55,UUT.table[UUT.findParent(UUT.Search(70))][0], " The parent child should now be 55");
                assertEquals(1,UUT.getChildrenNum(UUT.Search(55)), " The number of children of the parent should be 1");
                assertEquals(70,UUT.getInfo(UUT.getRightChild(UUT.Search(55))), " The child should now be 70");
                break;
            case 70:
                assertEquals(60,UUT.table[UUT.findParent(UUT.Search(100))][0], " The parent of the child should now be 60");
                assertEquals(2,UUT.getChildrenNum(UUT.Search(60)), " The number of children of the parent should be 2");
                assertEquals(100,UUT.table[UUT.getRightChild(UUT.Search(60))][0], " The child of the parent should be 100");
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
        for(int T : testArray)
            UUT.Append(T);

        for(int i = 0; i < SIZE; i++)
            assertNotEquals(testArray[i], -1, "Element " + i + " not found");

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

    @Test
    void search() {
    }
}
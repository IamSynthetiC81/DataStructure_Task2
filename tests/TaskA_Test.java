package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import src.TaskA;

public class TaskA_Test {

    private final static int Size = 10;

    TaskA UUT;



    @BeforeEach
    void setUpClass(){
        UUT = new TaskA(Size);
    }

    @Test
    public void AddNewNodeTest() {
        for (int i = 0; i < 10; i++)
            UUT.Append(i);
    }

    
}

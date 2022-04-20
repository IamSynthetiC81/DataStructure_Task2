import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.DataImport.FileOps;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileOpsTest {
    
    FileOps UUT;
    String Path = "src/Files/keys_1000000_BE.bin";
    
    @BeforeEach
    void setUp() {
        UUT = new FileOps(new File(Path));  // Create the object
    }
    
    @AfterEach
    void tearDown() throws IOException {
        UUT.Close();                        // Close the file
        UUT= null;                          // Delete the object
    }
    
    @Test
    void read() throws IOException {
        int[] array = UUT.Read();
        
        // Check whether the first element is correct
        assertEquals(1, array[0], " The first byte should be 1");
    }
}
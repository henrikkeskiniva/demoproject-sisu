
package fi.tuni.prog3.sisu;

import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    
    @Test
    public void testWriteToFile() throws Exception {
        User user = new User();
        user.setId("Henrik");
        boolean expected = true;
        boolean test =  new FileHandler().writeToFile("test.json", user);
        assertEquals(expected, test);
    }

    @Test
    public void testReadFromFile() throws Exception {
        File testfile = new File("test.json");
        User user = new FileHandler().readFromFile(testfile);
        String expected = "Henrik";
        String test = user.getId();
        assertEquals(expected, test);
        
    }


    
}

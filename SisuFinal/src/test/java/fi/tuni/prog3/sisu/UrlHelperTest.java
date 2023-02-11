
package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UrlHelperTest {
    

    @Test
    public void testUrlFormat1() {
        String test = new UrlHelper().UrlFormat("uta-ok-ykoodi-41176", -1023);
        String expected = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=uta-ok-ykoodi-41176&universityId=tuni-university-root-id";
        assertEquals(expected, test);
        
    }
    
    @Test
    public void testUrlFormat2() {

        String test = new UrlHelper().UrlFormat("otm-2966b2c5-7c94-491f-8c9d-ed918ce13869", 1);
        String expected = "https://sis-tuni.funidata.fi/kori/api/course-units/otm-2966b2c5-7c94-491f-8c9d-ed918ce13869";
        assertEquals(expected, test);
        
    }
    
}

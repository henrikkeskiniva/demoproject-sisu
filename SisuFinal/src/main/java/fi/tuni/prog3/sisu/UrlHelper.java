
package fi.tuni.prog3.sisu;


public class UrlHelper {
    
    public String moduleUrlbase = "https://sis-tuni.funidata.fi/kori/api/modules/";
    public String moduleUrlstart = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=";
    public String moduleUrlend = "&universityId=tuni-university-root-id";

    public String courseUrlbase = "https://sis-tuni.funidata.fi/kori/api/course-units/";
    public String courseUrlstart = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=";
    public String courseUrlend = "&universityId=tuni-university-root-id";
    
    //type = 1 -> course, type /= 1 -> module
    public String UrlFormat(String groupId, int type) {
        String ret = "";
        if (groupId.charAt(0) == 'o' && groupId.charAt(1) == 't' && groupId.charAt(2) == 'm') {
            if (type == 1) {
                ret = courseUrlbase + groupId;
            }
            else {
                ret = moduleUrlbase + groupId;
            }
            
        }
        else {
            
            if (type == 1) {
                ret = courseUrlstart + groupId + courseUrlend;
            }
            else {
                ret = moduleUrlstart + groupId + moduleUrlend;
            }  
        }
        return ret;
    }
}

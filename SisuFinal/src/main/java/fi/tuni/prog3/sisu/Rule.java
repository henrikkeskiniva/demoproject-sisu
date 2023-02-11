
package fi.tuni.prog3.sisu;

import com.google.gson.JsonObject;


public class Rule {
    public String type;
    public String groupId;
    public String allMandatory;
    public JsonObject credits;
    public String localId;
    
    //Mahdollisen ylemmän säännön localId.
    public String upperId;
    
    public Rule() {
        
    }

    public String getType() {
        return type;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getLocalId() {
       return localId;
    }
    
    public String getAllMandatory() {
        return allMandatory;
    }
    
    public JsonObject getCredits() {
        return credits;
    }
    
    public String getUpperId() {
        return upperId;
    }
    
    public void setUpperId(String id) {
        this.upperId = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAllMandatory(String allMandatory) {
        this.allMandatory = allMandatory;
    }

    public void setCredits(JsonObject credits) {
        this.credits = credits;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
    
    public void setGroupId(String id) {
      this.groupId = id;
    }
    
    





    
    
    
}

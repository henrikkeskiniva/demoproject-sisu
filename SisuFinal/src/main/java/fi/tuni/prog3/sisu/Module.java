
package fi.tuni.prog3.sisu;

import com.google.gson.JsonObject;

public class Module {
   public JsonObject name;
   public String id;
   public String code; 
   public String groupId;
   public JsonObject credits;
   public JsonObject targetCredits;
   public JsonObject rule;
   public int moduleIndex = 0;
   
    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getGroupId() {
        return groupId;
    }
    public JsonObject getName() {
        return name;
    }
    public String getNameString() {
        String namest = "";
        if(name.has("fi")) {
            namest = name.get("fi").getAsString();
        }
        else if (name.has("en")) {
            namest = name.get("en").getAsString();
        }
        else {
            namest = name.get("name").getAsString();
        }
        return namest;
    }
    public JsonObject getCredits() {
        return credits;
    }
    public JsonObject getTargetCredits() {
        return targetCredits;
    }
    public JsonObject getRule() {
        return rule;
    }  
    

    public void setName(JsonObject name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setCredits(JsonObject credits) {
        this.credits = credits;
    }

    public void setTargetCredits(JsonObject targetCredits) {
        this.targetCredits = targetCredits;
    }

    public void setRule(JsonObject rule) {
        this.rule = rule;
    }

    public int getIndex() {
        return moduleIndex;
    }

    public void setIndex(int index) {
        this.moduleIndex = index;
    }
    
}

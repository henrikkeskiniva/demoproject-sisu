
package fi.tuni.prog3.sisu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;



public class RuleChecker {
    private ArrayList<Rule> rules = new ArrayList<>();
    //sisältää korkeamman tason sääntöjä, kuten CreditsRule ja CompositeRule
    private ArrayList<Rule> rules2 = new ArrayList<>();
    

    
    public RuleChecker (JsonElement rule) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Rule>rulesarr = new ArrayList<>(); 
        var obj = rule.getAsJsonObject();
        var obj2 = obj;
        
        if (obj.get("type").getAsString().equals("CreditsRule") || obj.get("type").getAsString().equals("CompositeRule")) {
            String localId = null;
            Rule robject = UpperRuleCreator(localId, obj.deepCopy());
            rules2.add(robject);
        }
        int depthcount = 1;
        boolean cont = true;
        boolean deeper = false;
        
        while(cont) {
            if (!obj.get("type").getAsString().equals("CreditsRule") && !obj.get("type").getAsString().equals("CompositeRule")) {
                cont = false;
                break;
            }
            else if (obj.has("rule")) {
                
                String localId = obj.get("localId").getAsString();
                obj = obj.get("rule").getAsJsonObject();
                
                if (!obj.get("type").getAsString().equals("CreditsRule") && !obj.get("type").getAsString().equals("CompositeRule")) {
                    cont = false;
                    break;
                }
                Rule robject = UpperRuleCreator(localId, obj.deepCopy());
                rules2.add(robject);
                
                if (!obj.get("type").getAsString().equals("CreditsRule") && !obj.get("type").getAsString().equals("CompositeRule")) {
                    cont = false;
                    break;
                }
                
            }
            else {

                
                String localId = obj.get("localId").getAsString();
                obj2 = obj.get("rules").getAsJsonArray().get(0).getAsJsonObject();
                if (!obj2.get("type").getAsString().equals("CreditsRule") && !obj2.get("type").getAsString().equals("CompositeRule")) {
                    cont = false;
                    break;
                }

                Rule robj2 = UpperRuleCreator(localId, obj2.deepCopy());
                rules2.add(robj2);
                
                if (!obj2.get("type").getAsString().equals("CreditsRule") && !obj2.get("type").getAsString().equals("CompositeRule")) {
                    cont = false;
                    
                    break;
                }
                while (cont) {
                    
                    String locId = obj2.get("localId").getAsString();
                    obj2 = obj2.get("rules").getAsJsonArray().get(0).getAsJsonObject();
                    
                    if (!obj2.get("type").getAsString().equals("CreditsRule") && !obj2.get("type").getAsString().equals("CompositeRule")) {
                        cont = false;
                        deeper = true;
                        
                        break;
                        
                    }
                    
                    Rule robj3 = UpperRuleCreator(locId, obj2.deepCopy());
                    rules2.add(robj3);

                    if (!obj2.get("type").getAsString().equals("CreditsRule") && !obj2.get("type").getAsString().equals("CompositeRule")) {
                        cont = false;
                        deeper = true;
                        
                        break;
                    }
                    depthcount += 1;
                }
 
            }
            

        }
        JsonArray rulesjson = null;
        String localId = obj.get("localId").getAsString();
        
        if (!deeper) {
            
            rulesjson = obj.get("rules").getAsJsonArray();
            
        }        
        else {
            for(int i = 0; i < depthcount; i++) {
                String localId2 = rules2.get(rules2.size()-1).getLocalId();
            
                Rule lastRuleObj = UpperRuleCreator(localId2, obj.deepCopy().get("rules").getAsJsonArray().get(0).getAsJsonObject());
                rules2.add(lastRuleObj);
            
                rulesjson = obj.get("rules").getAsJsonArray().get(0).getAsJsonObject().get("rules").getAsJsonArray();
            }  
        }


        for (int i = 0; i < rulesjson.size(); i++) {
            var ob = rulesjson.get(i).getAsJsonObject();

            if (ob.get("type").getAsString().equals("AnyCourseUnitRule")){
                
                Rule ruleobj = gson.fromJson(ob, Rule.class);
                ruleobj.setUpperId(localId);
                rulesarr.add(ruleobj);
                
            }               
            else if (ob.get("type").getAsString().equals("AnyModuleRule")){
                Rule ruleobj = gson.fromJson(ob, Rule.class);
                ruleobj.setUpperId(localId);
                rulesarr.add(ruleobj);
                
            }
            else if (ob.get("type").getAsString().equals("CourseUnitRule")){
                Rule ruleobj = gson.fromJson(ob, Rule.class);
                ruleobj.setGroupId(ob.get("courseUnitGroupId").getAsString());
                ruleobj.setUpperId(localId);
                rulesarr.add(ruleobj);
              
            }
            
            else if (ob.get("type").getAsString().equals("ModuleRule")){
                
                Rule ruleobj = gson.fromJson(ob, Rule.class);
                ruleobj.setGroupId(ob.get("moduleGroupId").getAsString());
                ruleobj.setUpperId(localId);
                rulesarr.add(ruleobj);
   
            }
            else {
                RuleChecker rs = new RuleChecker(ob);
                ArrayList<Rule> rulesinner = rs.getRules();
                ArrayList<Rule> rules2inner = rs.getRules2();
                for (int j = 0; i < rulesinner.size(); i++) {
                    rules.add(rulesinner.get(j));
                }
                for (int j = 0; i < rules2inner.size(); i++) {
                    rules2.add(rules2inner.get(j));
                }
            }
            
        }

        rules = rulesarr;
    }
    
    public Rule UpperRuleCreator(String UpperId, JsonObject obj2) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        var obj = obj2.deepCopy();
        
        Rule rule = new Rule();
        
        if (obj.has("rule")) {

            obj.remove("rule");
            rule = gson.fromJson(obj.getAsJsonObject(), Rule.class);
            rule.setUpperId(UpperId);
            
        }
        else {

            obj.remove("rules");
            rule = gson.fromJson(obj.getAsJsonObject(), Rule.class);
            rule.setUpperId(UpperId);
            
        }
        return rule;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }
    public ArrayList<Rule> getRules2() {
        return rules2;
    }
    
}

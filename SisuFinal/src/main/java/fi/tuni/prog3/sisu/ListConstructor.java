/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author henkv
 */
public class ListConstructor {

    /**
     *
     * @param module
     * @return
     * @throws IOException
     */
    public ArrayList<ModuleExtended> ListConstructor(Module module) throws IOException {

        API api = new API();
        ArrayList<ModuleExtended> modules = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RuleChecker rulesD = new RuleChecker(module.getRule());

        ArrayList<Rule> rules = rulesD.getRules();

        for (int i = 0; i < rules.size(); i++) {

            if (rules.get(i).getType().equals("CourseUnitRule")) {
                JsonObject jsonobject1 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(rules.get(i).getGroupId(), 1));
                ModuleExtended course = gson.fromJson(jsonobject1, ModuleExtended.class);
                course.setDepth(1);

                modules.add(course);
            } else if (rules.get(i).getGroupId() == null) {
                //ei tehdä mitään
            } else {

                JsonObject jsonobject1 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(rules.get(i).getGroupId(), 0));
                if (jsonobject1.get("type").getAsString().equals("StudyModule")) {
                    ModuleExtended stud = gson.fromJson(jsonobject1, ModuleExtended.class);
                    stud.setDepth(1);

                    stud.setOwns(OwnsList(new RuleChecker(jsonobject1.get("rule")).getRules()));
                    modules.add(stud);
                } else if (jsonobject1.get("type").getAsString().equals("GroupingModule")) {
                    ModuleExtended group = gson.fromJson(jsonobject1, ModuleExtended.class);
                    group.setDepth(1);

                    group.setOwns(OwnsList(new RuleChecker(jsonobject1.get("rule")).getRules()));
                    modules.add(group);
                }
                RuleChecker check = new RuleChecker(jsonobject1.get("rule"));
                ArrayList<Rule> rs = check.getRules();

                for (int j = 0; j < rs.size(); j++) {

                    if (rs.get(j).getType().equals("CourseUnitRule")) {
                        JsonObject jsonobject2 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(rs.get(j).getGroupId(), 1));
                        ModuleExtended course = gson.fromJson(jsonobject2, ModuleExtended.class);
                        course.setDepth(2);
                        course.setBelongsTo(jsonobject1.get("groupId").getAsString());
                        modules.add(course);
                    } else if (rs.get(j).getGroupId() == null) {

                    } else {

                        JsonObject jsonobject2 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(rs.get(j).getGroupId(), 0));
                        if (jsonobject2.get("type").getAsString().equals("StudyModule")) {
                            ModuleExtended stud = gson.fromJson(jsonobject2, ModuleExtended.class);
                            stud.setDepth(2);

                            stud.setBelongsTo(jsonobject1.get("groupId").getAsString());
                            stud.setOwns(OwnsList(new RuleChecker(jsonobject2.get("rule")).getRules()));
                            modules.add(stud);
                        } else if (jsonobject2.get("type").getAsString().equals("GroupingModule")) {
                            ModuleExtended group = gson.fromJson(jsonobject2, ModuleExtended.class);
                            group.setDepth(2);
                            group.setBelongsTo(jsonobject1.get("groupId").getAsString());
                            group.setOwns(OwnsList(new RuleChecker(jsonobject2.get("rule")).getRules()));
                            modules.add(group);
                        }
                        RuleChecker check2 = new RuleChecker(jsonobject2.get("rule"));
                        ArrayList<Rule> r = check2.getRules();

                        for (int k = 0; k < r.size(); k++) {
                            if (r.get(k).getType().equals("CourseUnitRule")) {
                                JsonObject jsonobject3 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(r.get(k).getGroupId(), 1));
                                ModuleExtended course = gson.fromJson(jsonobject3, ModuleExtended.class);
                                course.setDepth(3);
                                course.setBelongsTo(jsonobject2.get("groupId").getAsString());
                                modules.add(course);
                            } else if (r.get(k).getGroupId() == null) {

                            } else {

                                JsonObject jsonobject3 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(r.get(k).getGroupId(), 0));
                                if (jsonobject3.get("type").getAsString().equals("StudyModule")) {
                                    ModuleExtended stud = gson.fromJson(jsonobject3, ModuleExtended.class);
                                    stud.setDepth(3);
                                    stud.setBelongsTo(jsonobject2.get("groupId").getAsString());
                                    stud.setOwns(OwnsList(new RuleChecker(jsonobject3.get("rule")).getRules()));
                                    modules.add(stud);
                                } else if (jsonobject3.get("type").getAsString().equals("GroupingModule")) {
                                    ModuleExtended group = gson.fromJson(jsonobject3, ModuleExtended.class);
                                    group.setDepth(3);
                                    group.setBelongsTo(jsonobject2.get("groupId").getAsString());
                                    group.setOwns(OwnsList(new RuleChecker(jsonobject3.get("rule")).getRules()));
                                    modules.add(group);
                                }

                                RuleChecker check3 = new RuleChecker(jsonobject3.get("rule"));
                                ArrayList<Rule> rr = check3.getRules();

                                for (int l = 0; l < rr.size(); l++) {
                                    if (rr.get(l).getType().equals("CourseUnitRule")) {
                                        JsonObject jsonobject4 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(rr.get(l).getGroupId(), 1));
                                        ModuleExtended course = gson.fromJson(jsonobject4, ModuleExtended.class);
                                        course.setDepth(4);
                                        course.setBelongsTo(jsonobject3.get("groupId").getAsString());
                                        modules.add(course);
                                    } else if (rr.get(l).getGroupId() == null) {

                                    } else {

                                        JsonObject jsonobject4 = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(rr.get(l).getGroupId(), 0));
                                        if (jsonobject4.get("type").getAsString().equals("StudyModule")) {
                                            ModuleExtended stud = gson.fromJson(jsonobject4, ModuleExtended.class);
                                            stud.setDepth(4);
                                            stud.setBelongsTo(jsonobject3.get("groupId").getAsString());
                                            stud.setOwns(OwnsList(new RuleChecker(jsonobject4.get("rule")).getRules()));
                                            modules.add(stud);
                                        } else if (jsonobject4.get("type").getAsString().equals("GroupingModule")) {
                                            ModuleExtended group = gson.fromJson(jsonobject3, ModuleExtended.class);
                                            group.setDepth(4);
                                            group.setBelongsTo(jsonobject2.get("groupId").getAsString());
                                            group.setOwns(OwnsList(new RuleChecker(jsonobject4.get("rule")).getRules()));
                                            modules.add(group);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        return modules;
    }

    /**
     * Asettaa moduulien välisiä kuuluvuussuhteita
     *
     * @param rules Lista käsiteltäviä ylätason Rule-objekteja
     * @return Palauttaa tarkistetun listan
     */
    public ArrayList<String> OwnsList(ArrayList<Rule> rules) {
        ArrayList<String> ownslist = new ArrayList<>();
        for (Rule r : rules) {
            if (r.getType().equals("CourseUnitRule") || r.getType().equals("ModuleRule")) {
                ownslist.add(r.getGroupId());
            }
        }
        return ownslist;
    }

}

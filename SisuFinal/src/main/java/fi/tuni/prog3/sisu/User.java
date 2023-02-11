package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * Käyttäjäluokka ohjelman tutkintorakenteiden tallentamiseen ja lataamiseen
 */
public class User {

//    public String name;
    public String id;
    public String email;
    public Module degree;
    public int credits = 0;
    public ArrayList<Module> modules = new ArrayList<>();

    public User() {

    }

    public User(String name, String id, String email) {
//        this.name = name;
        this.id = id;
        this.email = email;

    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Module getDegree() {
        return degree;
    }

    public void setDegree(Module degree) {
        this.degree = degree;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;

    }

    public void addToModules(Module module) {
        modules.add(module);
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " email: " + this.email
                + " degree: " + this.degree;
    }

}

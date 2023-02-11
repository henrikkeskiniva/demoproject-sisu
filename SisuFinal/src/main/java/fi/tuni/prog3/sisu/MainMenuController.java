package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * Yhdistää ohjelman päänäkymän ja taustaohjelman
 */
public class MainMenuController {

    @FXML
    Label currentID;

    @FXML
    ListView degreeContent;

    @FXML
    ListView chosenContent;

    @FXML
    VBox creditsContent;

    @FXML
    Label addLabel;

    @FXML
    Label removeLabel;

    @FXML
    Label currentCreditsLabel;

    @FXML
    Label degreeCreditsLabel;

    @FXML
    Label ownerInfoLabel;

    @FXML
    Button addButton;

    @FXML
    Button removeButton;

    /**
     * Lisää uuden moduulin aktiiviseen tutkintoon
     *
     * @param module Lisättävä moduuli
     */
    @FXML
    public void addModuleToDegree(ModuleExtended module) {

        int creds = 0;
        if (module.getCredits() != null) {
            creds = module.getCredits().get("min").getAsInt();
        }
        addCredits(creds);
        Sisu.chosen.add(module);
        Sisu.chosenModules.clear();
        for (var m : Sisu.chosen) {
            Sisu.chosenModules.add(m);
        }
    }

    /**
     * Poistaa annetun tutkinnon aktiivisesta tutkinnosta
     *
     * @param module Poistettava moduuli
     */
    @FXML
    public void removeModuleFromDegree(ModuleExtended module) {
        int creds = 0;
        if (module.getCredits() != null) {
            creds = module.getCredits().get("min").getAsInt() - 2 * module.getCredits().get("min").getAsInt();
        }

        for (int i = 0; i < Sisu.chosen.size(); i++) {
            if (module.getIndex() == Sisu.chosen.get(i).getIndex()) {
                addCredits(creds);
                Sisu.chosen.remove(i);
                Sisu.chosenModules.clear();
                for (var m : Sisu.chosen) {
                    Sisu.chosenModules.add(m);
                }
            }
        }
    }

    /**
     * Lisää opintopistekertymää
     *
     * @param credits Lisättävä opintopistemäärä
     */
    @FXML
    public void addCredits(int credits) {
        Sisu.currentDegreeCredits = Sisu.currentDegreeCredits + credits;
    }

    /**
     * Palauttaa käyttäjän asetusvalikkoon
     *
     * @throws IOException
     */
    @FXML
    private void backToUserMenu() throws IOException {
        Sisu.setRoot("userMenu");
    }

    /**
     * Tallentaa käyttäjän tutkinnon .json tiedostoon
     *
     * @throws IOException
     */
    @FXML
    private void saveUserToFile() throws IOException {
        Sisu.user.setModules(Sisu.chosenModules);
        Sisu.user.setCredits(Sisu.currentDegreeCredits);
        FileHandler fh = new FileHandler();
        fh.writeToFile(Sisu.user.getId() + ".json", Sisu.user);
    }

    /**
     * Rakentaa ohjelman pääikkunanäkymän
     *
     * @param id Aktiivisen tutkinnon groupID
     * @param name Aktiivisen tutkinnon suomenkielinen nimi
     * @throws IOException
     */
    @FXML
    public void buildMainView(String id, String name) throws IOException {

        // Päivitä käyttäjän tutkinto ruudulle
        currentID.setText(id + ": " + name);


        System.out.println("chosen:");
        for (var m : Sisu.chosen) {
            System.out.println(m.getNameString() + " : " + m.getGroupId());
        }
        updateList(degreeContent, Sisu.modules);
        updateList(chosenContent, Sisu.chosen);
        

        
        

        enableModuleSelection();
    }

    /**
     * Mahdollistaa kurssien lisäämisen ja poistamisen list-view olioista.
     */
    public void enableModuleSelection() {

        // Kurssien Lisääminen
        degreeContent.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> ov,
                    String old_val, String new_val) {
                addLabel.setGraphic(addButton);
                addLabel.setContentDisplay(ContentDisplay.RIGHT);
                addLabel.setText("Paina \"Lisää\" lisätäksesi moduulin.");
                String[] parts = new_val.split(" ");

                ModuleExtended module = new ModuleExtended();
                String s = parts[parts.length - 1].substring(1, parts[parts.length - 1].length() - 2);
                int index = Integer.parseInt(s);
                module = Sisu.modules.get(index - 1);

                if (module.getCredits() != null) {
                    currentCreditsLabel.setText("Kurssista saatavat vähimmäisopintopisteet: " + module.getCredits().get("min").getAsInt());
                } else if (module.getTargetCredits() != null) {
                    currentCreditsLabel.setText("Opintojaksoon vaadittavat opintopisteet: " + module.getTargetCredits().get("min").getAsInt());
                }

                if (module.getBelongsTo() != null) {
                    var mod = new ModuleExtended();
                    for (int i = 0; i < Sisu.modules.size(); i++) {
                        if (Sisu.modules.get(i).getGroupId().equals(module.getBelongsTo())) {
                            mod = Sisu.modules.get(i);
                        }
                    }
                    ownerInfoLabel.setText("Ylemmän moduulin nimi: " + mod.getNameString());
                }
//                var list = module.getOwns();

                final ModuleExtended mod = module;
                addButton.setOnAction((ActionEvent event) -> {
                    boolean contains = false;
                    boolean belongs = true;

                    if (mod.getBelongsTo() == null) {
                        belongs = false;
                    }
                    for (int i = 0; i < Sisu.chosen.size(); i++) {
                        if (Sisu.chosen.get(i).getGroupId().equals(mod.getGroupId())) {
                            contains = true;
                        }
                        if (Sisu.chosen.get(i).getGroupId().equals(mod.getBelongsTo())) {
                            belongs = false;
                        }
                    }
                    if (!contains && !belongs) {
                        addModuleToDegree(mod);
                        Platform.runLater(() -> updateList(chosenContent, Sisu.chosen));
                        degreeCreditsLabel.setText(Sisu.currentDegreeCredits + "/" + Sisu.degreeCredits);
//                        addLabel.setGraphic(null);
//                        addLabel.setText(null);
                        addButton.requestFocus();
                    } else if (belongs) {
                        addLabel.setText("Tämä moduuli kuuluu toiseen moduuliin. Lisää ylemmän tason moduuli ennen kuin lisäät tämän moduulin.");
//                        addLabel.setGraphic(null);
                    } else {
                        addLabel.setText("Moduuli on jo lisätty.");
//                        addLabel.setGraphic(null);
                    }
                });
            }
        });

        // Kurssien poistaminen
        chosenContent.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> ov,
                    String old_val, String new_val) {
                removeLabel.setText("Paina \"Poista\" poistaaksesi valitsemasi moduulin.");
                removeLabel.setGraphic(removeButton);
                removeLabel.setContentDisplay(ContentDisplay.RIGHT);
                ModuleExtended module = new ModuleExtended();

                String[] parts = new String[0];
                if (new_val != null) {
                    parts = new_val.split(" ");
                    String s = parts[parts.length - 1].substring(1, parts[parts.length - 1].length() - 2);
                    int index = Integer.parseInt(s);
                    module = Sisu.modules.get(index - 1);
                }
                if (module != null && !module.getOwns().isEmpty()) {
                    removeLabel.setText("Paina \"Poista\" poistaaksesi tämän moduulin sekä kaikki siitä periytyvät moduulit.");
                }

                final ModuleExtended mod = module;
                removeButton.setOnAction((ActionEvent event) -> {

                    if (!Sisu.chosen.isEmpty() && mod != null && mod.getOwns().isEmpty()) {

                        removeModuleFromDegree(mod);
                        Platform.runLater(() -> updateList(chosenContent, Sisu.chosen));
                        removeButton.requestFocus();
                        degreeCreditsLabel.setText(Sisu.currentDegreeCredits + "/" + Sisu.degreeCredits);
//                        removeLabel.setText(null);
//                        removeLabel.setGraphic(null);
                    } else if (!Sisu.chosen.isEmpty() && mod != null && !mod.getOwns().isEmpty()) {
                        removeModuleFromDegree(mod);
                        Platform.runLater(() -> updateList(chosenContent, Sisu.chosen));
//                        removeLabel.setText(null);
//                        removeLabel.setGraphic(null);
                        for (int i = 0; i < mod.getOwns().size(); i++) {
                            var mod2 = new ModuleExtended();
                            for (var m : Sisu.modules) {
                                if (m.getGroupId().equals(mod.getOwns().get(i)) && m.getBelongsTo().equals(mod.getGroupId())) {
                                    mod2 = m;
                                }
                            }

                            if (Sisu.chosen.contains(mod2)) {
                                removeModuleFromDegree(mod2);
                                Platform.runLater(() -> updateList(chosenContent, Sisu.chosen));
                                degreeCreditsLabel.setText(Sisu.currentDegreeCredits + "/" + Sisu.degreeCredits);
                            }

                            for (int j = 0; j < mod2.getOwns().size(); j++) {
                                var mod3 = new ModuleExtended();

                                for (var m : Sisu.modules) {
                                    if (m.getGroupId().equals(mod2.getOwns().get(j)) && m.getBelongsTo().equals(mod2.getGroupId())) {
                                        mod3 = m;
                                    }
                                }

                                if (Sisu.chosen.contains(mod3)) {
                                    removeModuleFromDegree(mod3);
                                    Platform.runLater(() -> updateList(chosenContent, Sisu.chosen));
                                    degreeCreditsLabel.setText(Sisu.currentDegreeCredits + "/" + Sisu.degreeCredits);
                                }

                                for (int k = 0; k < mod3.getOwns().size(); k++) {
                                    var mod4 = new ModuleExtended();

                                    for (var m : Sisu.modules) {
                                        if (m.getGroupId().equals(mod3.getOwns().get(k)) && m.getBelongsTo().equals(mod3.getGroupId())) {
                                            mod4 = m;
                                        }
                                    }

                                    if (Sisu.chosen.contains(mod4)) {
                                        removeModuleFromDegree(mod4);
                                        Platform.runLater(() -> updateList(chosenContent, Sisu.chosen));
                                        degreeCreditsLabel.setText(Sisu.currentDegreeCredits + "/" + Sisu.degreeCredits);
                                    }
                                    for (int l = 0; l < mod4.getOwns().size(); l++) {
                                        var mod5 = new ModuleExtended();

                                        for (var m : Sisu.modules) {
                                            if (m.getGroupId().equals(mod4.getOwns().get(k)) && m.getBelongsTo().equals(mod4.getGroupId())) {
                                                mod5 = m;
                                            }
                                        }

                                        if (Sisu.chosen.contains(mod5)) {
                                            removeModuleFromDegree(mod5);
                                            Platform.runLater(() -> updateList(chosenContent, Sisu.chosen));
                                            degreeCreditsLabel.setText(Sisu.currentDegreeCredits + "/" + Sisu.degreeCredits);
                                        }
                                    }
                                }
                            }
                        }
                    }

                });
            }
        });
    }

    /**
     *
     * @param list Näkymä johon parametrina annettu moduulilista sijoitetaan
     * @param array Lista näytettäviä moduuleja
     */
    private void updateList(ListView list, ArrayList<ModuleExtended> array) {
        list.getItems().clear();

        for (int i = 0; i < array.size(); i++) {
            String name = listStringConstructor(array.get(i));
            list.getItems().add(name);
        }
    }

    /**
     *
     * @param module Käsiteltävä moduuli joka halutaan muuttaa luettavaksi
     * tekstiksi
     * @return Palauttaa formatoidun moduuli- tai kurssistring-olion
     */
    private String listStringConstructor(ModuleExtended module) {
        String string = "";
        for (int i = 1; i < module.getDepth(); i++) {
            string = string + "   ";
        }

        if (!module.getNameString().equals("AnyModule") && !module.getNameString().equals("AnyCourse")) {
            //joka courseunitilla on credits-attribuutti
            if (!(module.getCredits() == null)) {
                string = string + "Kurssi: " + module.getCode() + " " + module.getNameString() + " " + module.getCredits().get("min").getAsString() + " op" + " (" + module.getIndex() + "). ";
            } //monella moduulilla on targetCredits-ominaisuus
            else if (!(module.getTargetCredits() == null)) {
                if (module.getCode() == null) {
                    string = string + "Opintokokonaisuus: " + " " + module.getNameString() + " " + module.getTargetCredits().get("min").getAsString() + " op" + " (" + module.getIndex() + "). ";
                } else {
                    string = string + "Opintokokonaisuus: " + module.getCode() + " " + module.getNameString() + " " + module.getTargetCredits().get("min").getAsString() + " op" + " (" + module.getIndex() + "). ";
                }
            } //jos kummatkin on null, kyseessä on esim. grouping module
            else {
                if (module.getCode() == null) {
                    string = string + "Opintokokonaisuus: " + module.getNameString() + " (" + module.getIndex() + "). ";
                } else {
                    string = string + "Opintokokonaisuus: " + module.getCode() + " " + module.getNameString() + " (" + module.getIndex() + "). ";
                }
            }
        }
        return string;
    }

}

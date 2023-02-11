package fi.tuni.prog3.sisu;

import com.google.gson.JsonObject;
import static fi.tuni.prog3.sisu.Sisu.api;
import static fi.tuni.prog3.sisu.Sisu.gson;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Yhdistää käyttäjän asetusvalikon näkymän ja taustaohjelman
 */
public class UserMenuController {

    private Stage stage;
    private Parent root;

    @FXML
    TextField studentField;

    @FXML
    TextField emailField;

    @FXML
    TextField degreeField;

    @FXML
    Button searchButton;

    /**
     * Palauttaa käyttäjän takaisin aloitusvalikkoon
     *
     * @throws IOException
     */
    @FXML
    private void backToStartMenu() throws IOException {
        Sisu.setRoot("startMenu");
    }

    /**
     * Ohjaa käyttäjän ohjelman päänäkymään
     *
     * @throws IOException
     */
    @FXML
    private void continueToMainMenu() throws IOException {
        Sisu.setRoot("mainMenu");
    }

    /**
     * Hakee Sisun Kori API:sta annetulla tutkinto-id:llä tutkintodataa
     *
     * @param event Käytetään asettamaan aktiivinen näkymä
     * @throws IOException
     */
    @FXML
    private void searchDegreeById(ActionEvent event) throws IOException {

        String id = degreeField.getText();
        JsonObject degreeResponse = api.getJsonObjectFromApi(new UrlHelper().UrlFormat(id, 0));
        ModuleExtended degreeModule = gson.fromJson(degreeResponse, ModuleExtended.class);
        JsonObject nameObject = degreeResponse.getAsJsonObject("name");
        String finnishName = "";
        if (nameObject.get("fi") != null) {
            finnishName = nameObject.get("fi").getAsString();
        }
        else {
            finnishName = nameObject.get("en").getAsString();
        }
        

        ModuleExtended responseDegreeModule = gson.fromJson(degreeResponse, ModuleExtended.class);

        Sisu.modules = new ListConstructor().ListConstructor(responseDegreeModule);
        Sisu.degreeCredits = responseDegreeModule.getTargetCredits().get("min").getAsInt();

        //annetaan kaikille moduuleille index-arvo
        for (int i = 0; i < Sisu.modules.size(); i++) {
            Sisu.modules.get(i).setIndex(i + 1);
        }


        // Luodaan käyttäjäolio
        User newUser = new User();
        newUser.setId(studentField.getText());
        newUser.setEmail(emailField.getText());
        newUser.setDegree(degreeModule);

        // Asetetaan uusi olio nykyiseksi käyttäjäksi
        Sisu.user = newUser;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = (Parent) loader.load();

        MainMenuController mainController = loader.getController();

        mainController.buildMainView(id, finnishName);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Sisu.scene.setRoot(root);
        stage.setScene(Sisu.scene);
        stage.show();
    }
}

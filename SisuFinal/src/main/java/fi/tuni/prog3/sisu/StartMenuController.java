package fi.tuni.prog3.sisu;

import java.io.File;
import javafx.stage.FileChooser;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Yhdistää aloitusvalikon näkymän ja taustaohjelman
 */
public class StartMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label helpLabel;

    @FXML
    VBox start;

    /**
     * Ohjaa käyttäjän ohjelman asetusvalikkoon
     *
     * @throws IOException
     */
    @FXML
    private void continueToUserMenu() throws IOException {
        Sisu.setRoot("userMenu");
    }

    /**
     * Lataa valmiin käyttäjän .json-tiedostosta ja päivittää ohjelman näkymän
     *
     * @param event Käytetään aktiviisen näkymän vaihtamiseen
     * @throws IOException
     */
    @FXML
    private void loadUserAndContinueToMenu(ActionEvent event) throws IOException {

        // Tiedostonvalinnan esiasetukset
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new ExtensionFilter(".JSON", "*.json"));
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Open a .json User file");
        File userFile = fileChooser.showOpenDialog(null);

        if (userFile != null) {
            // Asetetaan avattu käyttäjä nykyiseksi käyttäjäksi
            FileHandler fh = new FileHandler();
            User inputUser = fh.readFromFile(userFile);
            Sisu.user = inputUser;
            
            Module responseDegreeModule = Sisu.user.getDegree();
            Sisu.modules = new ListConstructor().ListConstructor(responseDegreeModule);
            Sisu.degreeCredits = responseDegreeModule.getTargetCredits().get("min").getAsInt();
            Sisu.currentDegreeCredits = Sisu.user.getCredits();

            for (int i = 0; i < Sisu.modules.size(); i++) {
                Sisu.modules.get(i).setIndex(i + 1);
            }

            // Add user choices to listview                
            for (int i = 0; i < Sisu.user.getModules().size();i++) {
                
                for (var m : Sisu.modules) {
                    
                    
                    if  (m.getIndex() == Sisu.user.getModules().get(i).getIndex()) {
                        Sisu.chosen.add(m);
                    }
                }
            }


            
            // Ladataan pääikkuna
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Parent root = (Parent) loader.load();

            MainMenuController mainController = loader.getController();

            mainController.buildMainView(inputUser.degree.getGroupId(), inputUser.degree.getNameString());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Sisu.scene.setRoot(root);
            stage.setScene(Sisu.scene);
            stage.show();

        } else {
            helpLabel.setText("Please use a valid .json file.");
        }
    }
}

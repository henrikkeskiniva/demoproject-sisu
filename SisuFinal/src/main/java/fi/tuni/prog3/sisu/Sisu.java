package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Sisu ohjelman pääluokka. Alustaa ohjelmassa käytettävät staattiset
 * jäsenmuuttujat
 */
public class Sisu extends Application {

    //lista moduuleista, joka generoidaan esim. rulecheckerin ja silmukoinnin avulla, vastaa ruudun vasenta puolta (list)
    public static ArrayList<ModuleExtended> modules = new ArrayList<>();
    //lista valituista moduuleista, vastaa ruudun oikeaa puolta (list2)
    public static ArrayList<ModuleExtended> chosen = new ArrayList<>();
    //lista, joka tallenetaan user-olioon. Alustetaan chosen-listalla aina, kun siihen tehdään muutoksia
    public static ArrayList<Module> chosenModules = new ArrayList<>();
    public static int degreeCredits = 0;
    public static int currentDegreeCredits = 0;

    public static Scene scene;
    public static API api;
    public static Gson gson;
    public static User user;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Sisu GUI");
        stage.setMaximized(true);
        scene = new Scene(loadFXML("startMenu"));
        String css = this.getClass().getClassLoader().getResource("css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Martian+Mono:wght@400;800&display=swap");
        stage.setScene(scene);

        stage.show();

        api = new API();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Sisu.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}

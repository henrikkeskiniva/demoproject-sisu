package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Tiedostojen käsittelyyn määritellyn rajapinnan toteuttava luokka.
 */
public class FileHandler implements iReadAndWriteToFile {

    /**
     * Lukee annetusta tiedostosta ohjelmaan dataa
     *
     * @param fileName Luettava tiedosto (.json)
     * @return palauttaa ohjelmassa käytettävän käyttäjä-olion
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public User readFromFile(File fileName) throws FileNotFoundException, IOException {
        User user = new User();
//        File file = new File(fileName);
        if (fileName.isFile()) {

            try {

                Gson gson = new Gson();
//                Reader reader = Files.newBufferedReader(fileName);
                Reader reader = new FileReader(fileName);
                user = gson.fromJson(reader, User.class);

                reader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
        return user;

    }

    /**
     * Tallentaa .json-tiedostoon ohjelman käyttäjän tutkinnon tilan.
     *
     * @param fileName Tallennettavan tiedoston nimi
     * @param user Tallennettavan käyttäjän tiedot sisältävä olio
     * @return Palauttaa totuusarvon tallennuksen onnistumisesta
     * @throws IOException
     */
    @Override
    public boolean writeToFile(String fileName, User user) throws IOException {
        boolean success = false;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(fileName));
            gson.toJson(user, writer);

            writer.close();
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}

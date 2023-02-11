package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;

public class API implements iAPI {

    /**
     * Kori-API:n käyttämiseen tarkoitettu käsittelymetodi
     *
     * @param urlString API:sta haettava päätepiste
     * @return Palauttaa tutkinto- tai kurssirakennetta vastaavan JsonObjektin
     * @throws MalformedURLException
     * @throws IOException
     */
    @Override
    public JsonObject getJsonObjectFromApi(String urlString) throws MalformedURLException, IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        InputStream inputStream = connection.getInputStream();
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        String jsonNull = "";
        var obj = new Gson().fromJson(jsonNull, JsonObject.class);
        if (text.charAt(0) == '[') {
            JsonArray arr = new Gson().fromJson(text, JsonArray.class);
            JsonElement elem = arr.get(0);
            obj = elem.getAsJsonObject();
        } else {
            obj = new Gson().fromJson(text, JsonObject.class);
        }

        return obj;
    }
}

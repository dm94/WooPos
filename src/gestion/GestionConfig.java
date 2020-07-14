package gestion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaz.Config;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GestionConfig {

    public static void guardarConfig(Config config) {
        File f = new File("config.json");
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .setLenient()
                .create();
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config cargarConfig() {
        Config config = new Config();
        File f = new File("config.json");
        if (!f.exists()) {
            config = new Config();
            guardarConfig(config);
            return config;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .setLenient()
                .create();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)) {
            config = gson.fromJson(reader, Config.class);
            if (config == null) config = new Config();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return config;
    }

    public static String devolverJson (String direcion) {
        String json = "";
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(direcion);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            json = buffer.toString();
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try { reader.close();
                } catch (IOException e) {}
            }
        }

        return json;
    }
}

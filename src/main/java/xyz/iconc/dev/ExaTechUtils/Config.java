package xyz.iconc.dev.ExaTechUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config implements Serializable {
    private static final String CONFIG_FILE_PATH = "./config.json";

    private static Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private Gson gson;


    private ConfigObject configObject;

    public Config() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public ConfigObject getConfigObject() {
        return configObject;
    }

    public void saveConfig() {
        File configFile = new File(CONFIG_FILE_PATH);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(configFile);

            fileOutputStream.write(gson.toJson(configObject).getBytes(StandardCharsets.UTF_8));

            fileOutputStream.close();

        } catch (IOException e) {
            LOGGER.error(e.toString());
            Controller.ExitApplication(0);
        }
    }

    public void loadConfig() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            generateConfig(configFile);
        }

        StringBuilder sb = new StringBuilder();
        try {

            FileInputStream fileInputStream = new FileInputStream(configFile);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            fileInputStream.close();

        } catch (IOException e) {
            LOGGER.error(e.toString());
            Controller.ExitApplication(0);
        }

        configObject = gson.fromJson(sb.toString(), ConfigObject.class);
    }

    private void generateConfig(File configFile) {
        try {
            if (!configFile.exists()) {
                if (!configFile.createNewFile()) LOGGER.error("Config file was not able to be created.");
            }


            FileOutputStream fileOutputStream = new FileOutputStream(configFile);

            fileOutputStream.write(gson.toJson(new ConfigObject()).getBytes(StandardCharsets.UTF_8));

            fileOutputStream.close();


        } catch (IOException e) {
            LOGGER.error(e.toString());
            Controller.ExitApplication(0);
        }
    }

    static class ConfigObject {
        private String API_TOKEN = "";

        protected ConfigObject() {

        }

        public String getAPI_TOKEN() {
            return API_TOKEN;
        }
    }

    public static void main(String[] args) {
        Config config = new Config();
        config.loadConfig();

        System.out.println(config.getConfigObject().getAPI_TOKEN());

    }
}

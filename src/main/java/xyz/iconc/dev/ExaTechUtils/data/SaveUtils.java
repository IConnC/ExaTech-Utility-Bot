package xyz.iconc.dev.ExaTechUtils.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SaveUtils {
    private static Logger logger = LoggerFactory.getLogger(SaveUtils.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SaveUtils() {

    }

    public static boolean SaveData(SaveData saveData) {
        String json = gson.toJson(saveData);

        File file = new File(saveData.getSaveFileName());


        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdir();
            if (!file.exists()) file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(json);

            bufferedWriter.close();

            fileWriter.close();

        } catch (IOException e) {
            logger.error(e.toString());
            return false;
        }

        return true;
    }

    public static SaveData LoadData(SaveData saveData) {
        SaveData loadedSaveData;
        File file = new File(saveData.getSaveFileName());
        if (!file.exists()) return null;


        StringBuilder sb = new StringBuilder();
        try {

            FileInputStream fileInputStream = new FileInputStream(saveData.getSaveFileName());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = bufferedReader.readLine();
            while (line != null) {
                sb.append(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            logger.error(e.toString());
            return null;
        }
        loadedSaveData = gson.fromJson(sb.toString(), saveData.getSaveClass());

        return loadedSaveData;
    }
}

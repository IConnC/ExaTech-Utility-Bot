package xyz.iconc.dev.ExaTechUtils.data;

public interface SaveData {
    String DATA_VERSION = null;


    void gatherData();

    void getSaveData();

    String getSaveFileName();

    String getSaveVersion();
}

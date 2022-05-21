package xyz.iconc.dev.ExaTechUtils.data;

public interface SaveData {
    String DATA_VERSION = null;


    Object loadData();

    boolean saveData(Object obj);

    String getSaveFileName();

    String getSaveVersion();

    Class<? extends SaveData> getSaveClass();

}

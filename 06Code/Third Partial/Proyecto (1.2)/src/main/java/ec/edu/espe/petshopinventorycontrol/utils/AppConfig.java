package ec.edu.espe.petshopinventorycontrol.utils;

import java.util.Properties;


public class AppConfig {

   
    private static class Holder {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    private Properties properties;

    private AppConfig() {
       
        properties = new Properties();
        properties.setProperty("MONGO_URI", "mongodb+srv://Steven:Steven2001@cluster0.mp8muds.mongodb.net/?appName=Cluster0t");
        properties.setProperty("DB_NAME", "PetshopInventoryControlBD");
        properties.setProperty("APP_VERSION", "1.5.0");
    }

    public static AppConfig getInstance() {
        return Holder.INSTANCE;
    }

    public String getProperty(String key) {
        
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("La clave de configuración no puede estar vacía.");
        }
        return properties.getProperty(key, "N/A");
    }
}
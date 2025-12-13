package ec.edu.espe.petshopinventorycontrol.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class FileUtils {

    // Gson personalizado con soporte para LocalDate
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static String readText(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists())
                return null;

            return Files.readString(f.toPath(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            return null;
        }
    }

    public static boolean writeText(String filePath, String content) {
        try {
            File f = new File(filePath);

            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }

            Files.writeString(Path.of(filePath), content, StandardCharsets.UTF_8);
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public static boolean saveJson(String filePath, Object data) {
        return writeText(filePath, GSON.toJson(data));
    }

    public static <T> T loadJson(String filePath, Class<T> clazz) {
        String txt = readText(filePath);
        return (txt == null) ? null : GSON.fromJson(txt, clazz);
    }

    public static void ensureFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();
    }
}

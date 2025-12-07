package ec.edu.espe.petshopinventorycontrol.utils;

import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    private static final Gson GSON = new Gson();

    // CREA CARPETA SI NO EXISTE
    public static void ensureFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static String readText(String resourcePath) {
        try {
            File f = new File(resourcePath);
            if (!f.exists()) return null;

            return Files.readString(f.toPath(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            return null;
        }
    }

    public static boolean writeText(String filePath, String content) {
        try {
            File f = new File(filePath);

            // CREA DIRECTORIOS
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

    public static <T> T loadJson(String resourcePath, Class<T> clazz) {
        String txt = readText(resourcePath);
        return (txt == null) ? null : GSON.fromJson(txt, clazz);
    }
}

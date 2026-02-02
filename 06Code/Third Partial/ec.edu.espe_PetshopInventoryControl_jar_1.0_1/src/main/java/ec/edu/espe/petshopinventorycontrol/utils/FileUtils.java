package ec.edu.espe.petshopinventorycontrol.utils;

import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    private static final Gson GSON = new Gson();

    public static String readText(String resourcePath) {
        try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                return null;
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean writeText(String filePath, String content) {
        try {
            File f = new File(filePath);
            f.getParentFile().mkdirs();
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

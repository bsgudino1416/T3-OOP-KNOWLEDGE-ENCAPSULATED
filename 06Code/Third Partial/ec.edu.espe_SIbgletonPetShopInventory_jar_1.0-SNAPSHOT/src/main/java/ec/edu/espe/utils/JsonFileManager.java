package ec.edu.espe.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ec.edu.espe.model.Producto;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonFileManager {
    
    // 1. CAMBIO: Ahora guardamos dentro de una carpeta llamada "data"
    private static final String RUTA_CARPETA = "data";
    private static final String RUTA_ARCHIVO = RUTA_CARPETA + File.separator + "productos.json";

    public static void guardar(List<Producto> productos) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(productos);
        
        // 2. CAMBIO: Verificar si la carpeta existe, si no, crearla
        File carpeta = new File(RUTA_CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); // Crea el directorio 'data'
        }

        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            writer.write(json);
            System.out.println("Datos guardados en: " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public static List<Producto> leer() {
        Gson gson = new Gson();
        List<Producto> lista = new ArrayList<>();

        try (Reader reader = new FileReader(RUTA_ARCHIVO)) {
            Type listType = new TypeToken<ArrayList<Producto>>(){}.getType();
            lista = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró base de datos previa. Se creará una nueva al guardar.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (lista == null) {
            return new ArrayList<>();
        }
        return lista;
    }
}
package ec.edu.espe.petshopinventorycontrol.utils;

public class ProductIdGenerator {

    private ProductIdGenerator() {}

    public static String generate(
            String productType,
            String animal,
            String sizeStage
    ) {

        String sizeCode = "";

        if (sizeStage != null && !sizeStage.isEmpty()) {
            sizeCode = "-" + getSizeStageCode(sizeStage);
        }

        long timestamp = System.currentTimeMillis() % 100000;

        return getProductTypeCode(productType)
                + "-" + getAnimalCode(animal)
                + sizeCode
                + "-" + String.format("%05d", timestamp);
    }

    private static String getProductTypeCode(String type) {
        switch (type) {
            case "Comida": return "COM";
            case "Accesorio": return "ACC";
            case "Juguete": return "JUG";
            case "Medicina": return "MED";
            case "Snack": return "SNK";
            default: return "OTR";
        }
    }

    private static String getAnimalCode(String animal) {
        switch (animal) {
            case "Perro": return "PER";
            case "Gato": return "GAT";
            case "Conejo": return "CON";
            case "Hámster": return "HAM";
            case "Cuy": return "CUY";
            case "Cerdo": return "CER";
            case "Caballo": return "CAB";
            case "Pollo": return "POL";
            case "Vaca": return "VAC";
            default: return "OTR";
        }
    }

    private static String getSizeStageCode(String size) {
        switch (size) {
            case "Pequeño": return "P";
            case "Mediano": return "M";
            case "Grande": return "G";
            case "Gatito": return "K";
            case "Adulto": return "A";
            case "Senior": return "S";
            case "Juvenil": return "J";
            default: return "";
        }
    }
}


package ec.edu.espe.petshopinventorycontrol.controller;

public final class IdCodeMapper {

    private IdCodeMapper() {
    }

    public static String typeCode(String typeProduct) {
        String value = normalize(typeProduct);
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "COMIDA" -> "COM";
            case "ACCESORIO" -> "ACC";
            case "JUGUETE" -> "JUG";
            case "MEDICINA" -> "MED";
            default -> null;
        };
    }

    public static String animalCode(String animalType) {
        String value = normalize(animalType);
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "PERRO", "DOG" -> "DOG";
            case "GATO", "CAT" -> "CAT";
            case "HAMSTER", "HAM" -> "HAM";
            case "CONEJO", "RAB" -> "RAB";
            case "GALLINA", "CHI" -> "CHI";
            case "VACA", "COW" -> "COW";
            case "CABALLO", "HOR" -> "HOR";
            case "CERDO", "PIG" -> "PIG";
            default -> null;
        };
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed.toUpperCase();
    }
}

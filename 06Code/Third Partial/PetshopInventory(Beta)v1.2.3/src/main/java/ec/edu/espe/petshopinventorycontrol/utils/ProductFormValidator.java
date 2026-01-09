package ec.edu.espe.petshopinventorycontrol.utils;


/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class ProductFormValidator {
    
        private ProductFormValidator() {
        // 
    }

    public static boolean isValidMakeName(String make) {
        if (make == null || make.trim().isEmpty()) {
            return false;
        }

        if (make.length() < 2 || make.length() > 30) {
            return false;
        }

        if (!TextValidationUtil.isOnlyLetters(make)) {
            return false;
        }

        return !TextValidationUtil.containsBadWords(make);
    }
    
    public static boolean isValidAnimal(String animal, String otherAnimal) {

    if (animal == null || animal.trim().isEmpty()) {
        return false;
    }

    if (!animal.equals("Otro")) {
        return true;
    }

    // "Other", aditional
    if (otherAnimal == null || otherAnimal.trim().isEmpty()) {
        return false;
    }

    if (!TextValidationUtil.isOnlyLetters(otherAnimal)) {
        return false;
    }

    return !TextValidationUtil.containsBadWords(otherAnimal);
}
    
    public static boolean isValidFlavor(String flavor) {

    if (flavor == null || flavor.trim().isEmpty()) {
        return false;
    }

    if (!TextValidationUtil.isOnlyLetters(flavor)) {
        return false;
    }

    if (TextValidationUtil.containsBadWords(flavor)) {
        return false;
    }

    return true;
}
    
    public static boolean hasFlavor(String productType) {
    if (productType == null) {
        return false;
    }

    return productType.equals("Comida") || productType.equals("Snack");
}

    
}

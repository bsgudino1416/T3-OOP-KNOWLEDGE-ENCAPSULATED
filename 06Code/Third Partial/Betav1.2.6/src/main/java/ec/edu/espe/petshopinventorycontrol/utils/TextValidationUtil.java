package ec.edu.espe.petshopinventorycontrol.utils;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class TextValidationUtil {
        private static final String[] BAD_WORDS = {
        "puta", "mierda", "carajo", "verga", "pendejo", "idiota"
    };

    private TextValidationUtil() {
        // Evita instancias
    }

    public static boolean containsBadWords(String text) {
        if (text == null) return false;

        String lowerText = text.toLowerCase();

        for (String word : BAD_WORDS) {
            if (lowerText.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnlyLetters(String text) {
        return text != null && text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
}

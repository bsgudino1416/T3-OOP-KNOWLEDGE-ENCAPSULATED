package ec.edu.espe.petshopinventorycontrol.model.services;

import java.util.Arrays;

public final class Base32Codec {

    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
    private static final int[] LOOKUP = new int[128];

    static {
        Arrays.fill(LOOKUP, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            LOOKUP[ALPHABET[i]] = i;
        }
    }

    private Base32Codec() {
    }

    public static String encode(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder((data.length * 8 + 4) / 5);
        int buffer = data[0] & 0xff;
        int next = 1;
        int bitsLeft = 8;
        while (bitsLeft > 0 || next < data.length) {
            if (bitsLeft < 5) {
                if (next < data.length) {
                    buffer <<= 8;
                    buffer |= (data[next++] & 0xff);
                    bitsLeft += 8;
                } else {
                    int pad = 5 - bitsLeft;
                    buffer <<= pad;
                    bitsLeft += pad;
                }
            }
            int index = (buffer >> (bitsLeft - 5)) & 0x1f;
            bitsLeft -= 5;
            out.append(ALPHABET[index]);
        }
        return out.toString();
    }

    public static byte[] decode(String base32) {
        if (base32 == null || base32.trim().isEmpty()) {
            return new byte[0];
        }
        String cleaned = base32.trim().replace("=", "").replaceAll("\\s+", "").toUpperCase();
        byte[] bytes = new byte[cleaned.length() * 5 / 8];
        int buffer = 0;
        int bitsLeft = 0;
        int count = 0;
        for (int i = 0; i < cleaned.length(); i++) {
            char ch = cleaned.charAt(i);
            if (ch >= LOOKUP.length || LOOKUP[ch] == -1) {
                continue;
            }
            buffer <<= 5;
            buffer |= LOOKUP[ch] & 0x1f;
            bitsLeft += 5;
            if (bitsLeft >= 8) {
                bytes[count++] = (byte) ((buffer >> (bitsLeft - 8)) & 0xff);
                bitsLeft -= 8;
            }
        }
        return count == bytes.length ? bytes : Arrays.copyOf(bytes, count);
    }
}

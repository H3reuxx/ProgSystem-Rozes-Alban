package org.example.progsystem.tp2;

import java.nio.charset.StandardCharsets;

public class Utils {

    /* curseur += writeInt(memory, curseur, value) */
    public static int writeInt(byte[] memory, int offset, int value) {
        // TODO: Écrire les 4 octets de 'value' dans 'memory'
        // à partir de 'offset', en big-endian.
        for (int i = 0; i < 4; i++) {
            byte b = (byte)((value >> (8*(3-i)))& 0xFF);
            memory[offset+i] = b;
        }

        return 4;
    }

    public static int readInt(byte[] memory, int offset) {
        // TODO: Reconstituer le int sur 4 octets.
        int i = 0;
        for (int index = 0; index < 4; index++) {
            i = ((i << 8) | (memory[offset + index] & 0xFF));
        }
        return i;
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        // TODO: Écrire les 2 octets de 'value'.
        for (int i = 0; i < 2; i++) {
            byte b = (byte)((value >> (8*(1-i)))& 0xFF);
            memory[offset+i] = b;
        }

        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        // TODO: Lire le short sur 2 octets.
        /* on reconstruit le short puis print */
        short s = 0;
        for (int i = 0; i < 2; i++) {
            s = (short) ((s << 8) | (memory[offset + i] & 0xFF));
        }
        return s;
    }

    public static int writeLong(byte[] memory, int offset, long value) {
        for (int i = 0; i < 8; i++) {
            byte b = (byte)((value >> (8*(7-i)))& 0xFF);
            memory[offset+i] = b;
        }
        return 8;
    }

    public static long readLong(byte[] memory, int offset) {
        long i = 0;
        for (int index = 0; index < 8; index++) {
            i = ((i << 8) | (memory[offset + index] & 0xFF));
        }
        return i;
    }

    public static int writeString(
            byte[] memory,
            int offset,
            String str,
            int maxLength) {

        // TODO:
        // 1. Convertir la chaîne en octets.
        // 2. Copier les octets sans dépasser maxLength.
        // 3. Nettoyer le reste de la zone avec des zéros.

        byte[] temp = str.getBytes();

        int i;
        for (i = 0; i < temp.length && i < maxLength; i++) {
            memory[offset + i] = temp[i];
        }

        while (i < maxLength) {
            memory[offset + i] = 0;
            i++;
        }

        return maxLength;
    }

    public static String readString(
            byte[] memory,
            int offset,
            int maxLength) {

        // TODO:
        // Lire jusqu'au premier octet nul
        // ou jusqu'à maxLength.
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maxLength && memory[offset+i] != 0; i++) {
            sb.append(memory[offset+i]);
        }

        return sb.toString();
    }
}

package org.example.progsystem;

public class Utils {

    /* curseur += writeInt(memory, curseur, value) */
    public static int writeInt(byte[] memory, int offset, int value) {
        // TODO: Écrire les 4 octets de 'value' dans 'memory'
        // à partir de 'offset', en big-endian.
        for (int i = 0; i < 3; i++) {
            value = value<<i;
            byte b = (byte)(value&0xFF);
            memory[offset+i] = b;
        }

        return 4;
    }

    public static int readInt(byte[] memory, int offset) {
        // TODO: Reconstituer le int sur 4 octets.
        return 0;
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        // TODO: Écrire les 2 octets de 'value'.
        for (int i = 0; i < 1; i++) {
            value = (short)(value<<i);
            byte b = (byte)(value&0xFF);
            memory[offset+i] = b;
        }
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        // TODO: Lire le short sur 2 octets.
        return 0;
    }
}

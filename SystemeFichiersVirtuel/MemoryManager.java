package org.example.progsystem;

public class MemoryManager {

    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024;
    public static final int NUM_BLOCKS =
            TOTAL_MEMORY / BLOCK_SIZE;

    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET =
            2 * BLOCK_SIZE;
    public static final int DATA_OFFSET =
            129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128;

    public static final int INODE_TABLE_SIZE =
            DATA_OFFSET - INODE_TABLE_OFFSET;

    public static final int MAX_INODES =
            INODE_TABLE_SIZE / INODE_SIZE;

    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        writeSuperblock();

        // TODO:
        // Réserver les blocs système 0 à 128.

        for (int i=0; i<=128; i++) {
            int indexByte = BITMAP_OFFSET + (i / 8);
            memory[indexByte] = (byte) (memory[indexByte] | (1 << (i % 8)));
        }
    }

    private void writeSuperblock() {
        // TODO:
        // Utiliser Utils pour écrire les métadonnées.

        Utils.writeString(
                memory,
                SUPERBLOCK_OFFSET,
                "MYFS1.0",
                16);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 16,
                BLOCK_SIZE);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 20,
                TOTAL_MEMORY);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 24,
                NUM_BLOCKS);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 28,
                MAX_INODES);
    }

    public byte[] getFilesystemMemory() {
        return memory;
    }

    public boolean setBlockUsed(int blockNumber, boolean used) {
        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {
            return false;
        }

        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;

        if (used) {
            // TODO:
            // Positionner le bit à 1.
            memory[offset] |= (byte) (1 << bitPosition);
        } else {
            // TODO:
            // Positionner le bit à 0.
            memory[offset] &= (byte) (1 << bitPosition);
        }

        return true;
    }

    public int isBlockUsed(int blockNumber) {

        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {
            return -1;
        }

        // TODO:
        // Calculer byteIndex.
        // Calculer bitPosition.
        // Lire le bit.

        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;

        byte b = (byte) (memory[offset] & (1 << bitPosition));

        if (b != 0)
            return 1;
        else {
            return 0;
        }
    }

    public int allocateBlock() {

        // TODO:
        // Parcourir les blocs de données :
        // 129 .. NUM_BLOCKS - 1.
        //
        // Retourner le premier bloc libre.
        // Le marquer immédiatement comme utilisé.

        for (int indice = 129; indice < (NUM_BLOCKS-1); indice++) {
            if (isBlockUsed(indice) == 0) {
                setBlockUsed(indice, true);

                return indice;
            }
        }

        return -1;
    }
}
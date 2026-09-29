package org.example.progsystem;

public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;
    private byte[] memory;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;
    public static final int INODE_TABLE_OFFSET = 1024;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
        this.memory = memoryManager.getFilesystemMemory();
    }

    public int getInodeOffset() {
        // TODO:
        // Calculer l'offset exact de l'inode.

        return INODE_TABLE_OFFSET + (inodeNumber * INODE_SIZE);
    }

    public int getFileType() {
        // TODO:
        // Lire le type à offset + 4.

        return Utils.readInt(memory, getInodeOffset() + 4);
    }

    public int getFileSize() {
        // TODO:
        // Lire la taille à offset + 8.
        return Utils.readInt(memory, getInodeOffset() + 8);
    }

    public int[] getDirectPointers() {

        int[] pointers =
                new int[DIRECT_POINTERS];

        // TODO:
        // Lire les 10 pointeurs directs.

        for (int index = 0; index < 10; index++) {
            pointers[index] = Utils.readInt(memory, getInodeOffset() + 40 + (4*index));
        }

        return pointers;
    }
}

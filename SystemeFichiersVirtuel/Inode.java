package org.example.progsystem.tp2;

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
            pointers[index] = Utils.readInt(memory, getInodeOffset() + 28 + (4 * index));
        }

        return pointers;
    }

    public void writeToMemory(
            int fileType,
            int fileSize,
            long creationTime,
            long modificationTime,
            int[] directPointers,
            int indirectPointer,
            short permissions,
            int linkCount) {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int offset = getInodeOffset();

        // TODO:
        // 1. Numéro d'inode
        // 2. Type
        // 3. Taille
        // 4. Création
        // 5. Modification
        // 6. 10 pointeurs directs
        // 7. Pointeur indirect
        // 8. Permissions
        // 9. Nombre de liens

        Utils.writeInt(memory, offset, inodeNumber);
        offset += 4;

        Utils.writeInt(memory, offset, fileType);
        offset += 4;

        Utils.writeInt(memory, offset, fileSize);
        offset += 4;

        Utils.writeLong(memory, offset, creationTime);
        offset += 8;

        Utils.writeLong(memory, offset, modificationTime);
        offset += 8;

        for (int i = 0; i < 10; i++) {
            Utils.writeInt(memory, offset, directPointers[i]);
            offset += 4;
        }

        Utils.writeInt(memory, offset, indirectPointer);
        offset += 4;

        Utils.writeShort(memory, offset, permissions);
        offset += 2;

        Utils.writeInt(memory, offset, linkCount);
        offset += 4;
    }


}

package org.example.progsystem.tp2;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        // TODO:
        // Parcourir les inodes de 0 à MAX_INODES - 1.
        // Identifier le premier inode libre.
        // Retourner son numéro.

        for (int index = 0; index <= MemoryManager.MAX_INODES - 1; index++) {
            int offset = MemoryManager.INODE_TABLE_OFFSET + (index * MemoryManager.INODE_SIZE);

            if (Utils.readInt(memory, offset + 4) == 0) {
                return index;
            }
        }

        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        Inode inode = new Inode(memoryManager, inodeNum);
        long tempsActuel = System.currentTimeMillis();

        inode.writeToMemory(1, 0, tempsActuel, tempsActuel, new int[Inode.DIRECT_POINTERS], 0, (short) 0644, 1);

        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}
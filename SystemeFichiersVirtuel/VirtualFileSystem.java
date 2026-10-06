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

    public boolean writeFile(
            int inodeNum,
            byte[] data) {

        int blocksNeeded =
                (data.length
                        + MemoryManager.BLOCK_SIZE - 1)
                        / MemoryManager.BLOCK_SIZE;

        Inode inode =
                new Inode(memoryManager, inodeNum);

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers =
                new int[Inode.DIRECT_POINTERS];

        // TODO:
        // Allouer blocksNeeded blocs.

        int alloue = 0;
        while (alloue < blocksNeeded) {
            int block = memoryManager.allocateBlock();

            if (block == -1) {
                for (int i = 0; i < alloue; i++) {
                    memoryManager.setBlockUsed(blockPointers[i], false);
                }
                return false;
            }

            blockPointers[alloue] = block;
            alloue++;
        }

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        for (int i = 0; i < blocksNeeded; i++) {
            int toCopy = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int physicalOffset = blockPointers[i] * MemoryManager.BLOCK_SIZE;

            System.arraycopy(data, dataSrcOffset, memory, physicalOffset, toCopy);

            dataSrcOffset += toCopy;
            bytesRemaining -= toCopy;
        }

        int[] anciensP = inode.getDirectPointers();
        for (int i = 0; i < Inode.DIRECT_POINTERS; i++) {
            if (anciensP[i] != 0) {
                memoryManager.setBlockUsed(anciensP[i], false);
            }
        }

        // Mise à jour de l'inode en conservant les champs existants.
        int offset = inode.getInodeOffset();
        long creationTime = Utils.readLong(memory, offset + 12);
        int indirectPointer = Utils.readInt(memory, offset + 68);
        short permissions = Utils.readShort(memory, offset + 72);
        int linkCount = Utils.readInt(memory, offset + 74);

        inode.writeToMemory(inode.getFileType(), data.length, creationTime, System.currentTimeMillis(), blockPointers, indirectPointer, permissions, linkCount);

        return true;
    }

    public byte[] readFile(int inodeNum) {

        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize =
                inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData =
                new byte[fileSize];

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] blockPointers =
                inode.getDirectPointers();

        int bytesRestants = fileSize;
        int destination = 0;
        int i = 0;

        while (bytesRestants > 0 && i < Inode.DIRECT_POINTERS) {
            int toCopy = Math.min(bytesRestants, MemoryManager.BLOCK_SIZE);
            int physicalOffset = blockPointers[i] * MemoryManager.BLOCK_SIZE;

            System.arraycopy(memory, physicalOffset, fileData, destination, toCopy);

            destination += toCopy;
            bytesRestants -= toCopy;
            i++;
        }

        return fileData;
    }
}
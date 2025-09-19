package Samples;

import model.Folder;
import model.MultiFolder;

import java.util.List;

public class MultipleFolders implements MultiFolder {
    private final String name;
    private final String size;
    List<Folder> folders;

    public MultipleFolders(String name, String size, List<Folder> folders) {
        this.name = name;
        this.size = size;
        this.folders = folders;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSize() {
        return this.size;
    }

    @Override
    public List<Folder> getFolders() {
        return this.folders;
    }
}

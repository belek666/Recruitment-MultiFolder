package Samples;

import model.Folder;

public class SingleFolder implements Folder {

    private final String name;
    private final String size;

    public SingleFolder(String name, String size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size;
    }
}

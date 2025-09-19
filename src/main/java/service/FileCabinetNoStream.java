package service;

import model.Cabinet;
import model.Folder;
import model.FolderSize;
import model.MultiFolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinetNoStream implements Cabinet {
    private List<Folder> folders;

    public FileCabinetNoStream(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        for (Folder folder : scanFolder(folders))
        {
            if (folder.getName().equals(name))
            {
                return Optional.of(folder);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        if (!checkValidSize(size))
        {
            return null;
        }

        List<Folder> list = new ArrayList<>();
        for (Folder folder : scanFolder(folders))
        {
            if (folder.getSize().equals(size))
            {
                list.add(folder);
            }
        }
        return list;
    }

    @Override
    public int count() {
        return scanFolder(folders).size();
    }

    private List<Folder> scanFolder(List<Folder> folders)
    {
        List<Folder> list = new ArrayList<>();
        for (Folder folder : folders)
        {
            list.add(folder);
            if (folder instanceof MultiFolder) {
                list.addAll(scanFolder(((MultiFolder) folder).getFolders()));
            }
        }
        return list;
    }

    private boolean checkValidSize(String size)
    {
        for (FolderSize folderSize : FolderSize.values())
        {
            if (folderSize.name().equals(size))
            {
                return true;
            }
        }
        return false;
    }
}

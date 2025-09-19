package service;

import model.Cabinet;
import model.Folder;
import model.FolderSize;
import model.MultiFolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return scanFolders(folders).filter(folder -> folder.getName().equals(name)).findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        if (!checkValidSize(size))
        {
            return null;
        }
        return scanFolders(folders).filter(folder -> folder.getSize().equals(size)).collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) scanFolders(folders).count();
    }

    private Stream<Folder> scanFolders(List<Folder> folders) {
        return folders.stream().flatMap(folder -> {
            if (folder instanceof MultiFolder)
            {
                return Stream.concat(Stream.of(folder), scanFolders(((MultiFolder) folder).getFolders()));
            }
            else
            {
                return Stream.of(folder);
            }
        });
    }

    private boolean checkValidSize(String size)
    {
        return Arrays.stream(FolderSize.values()).anyMatch(folderSize -> folderSize.name().equals(size));
    }
}

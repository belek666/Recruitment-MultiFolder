import Samples.MultipleFolders;
import Samples.SingleFolder;
import model.Folder;
import model.FolderSize;
import org.junit.Test;
import service.FileCabinet;
import service.FileCabinetNoStream;

import java.util.List;

import static org.junit.Assert.*;

public class TestFileCabinetNoStream {

    @Test
    public void testOneLevelFolderValid() {
        List<Folder> folders = List.of(new SingleFolder("LargeFolder", FolderSize.LARGE.name()),
                new SingleFolder("MediumFolder", FolderSize.MEDIUM.name()),
                new SingleFolder("SmallFolder", FolderSize.SMALL.name()));

        FileCabinetNoStream cabinet = new FileCabinetNoStream(folders);

        assertTrue(cabinet.findFolderByName("LargeFolder").isPresent());
        assertTrue(cabinet.findFolderByName("MediumFolder").isPresent());
        assertTrue(cabinet.findFolderByName("SmallFolder").isPresent());

        assertEquals(1, cabinet.findFoldersBySize("LARGE").size());
        assertEquals(1, cabinet.findFoldersBySize("MEDIUM").size());
        assertEquals(1, cabinet.findFoldersBySize("SMALL").size());
        assertEquals(3, cabinet.count());
    }

    @Test
    public void testOneLevelFolderInvalid() {
        List<Folder> folders = List.of(new SingleFolder("MediumFolder", FolderSize.MEDIUM.name()),
                new SingleFolder("MediumFolder", FolderSize.MEDIUM.name()),
                new SingleFolder("SmallFolder", FolderSize.SMALL.name()));

        FileCabinetNoStream cabinet = new FileCabinetNoStream(folders);

        assertFalse(cabinet.findFolderByName("LargeFolder").isPresent());
        assertEquals(0, cabinet.findFoldersBySize("LARGE").size());
        assertEquals(3, cabinet.count());
    }

    @Test
    public void testMultiLevelFolderValid() {
        List<Folder> folders = List.of(
                new MultipleFolders("Root", FolderSize.LARGE.name(),
                        List.of(new SingleFolder("First", FolderSize.SMALL.name()),
                                new SingleFolder("Second", FolderSize.MEDIUM.name()),
                                new MultipleFolders("Sub", FolderSize.LARGE.name(),
                                        List.of(new SingleFolder("Third", FolderSize.MEDIUM.name()),
                                                new MultipleFolders("Empty", FolderSize.SMALL.name(), List.of())))))
        );

        FileCabinetNoStream cabinet = new FileCabinetNoStream(folders);

        assertTrue(cabinet.findFolderByName("Root").isPresent());
        assertTrue(cabinet.findFolderByName("First").isPresent());
        assertTrue(cabinet.findFolderByName("Second").isPresent());
        assertTrue(cabinet.findFolderByName("Sub").isPresent());
        assertTrue(cabinet.findFolderByName("Third").isPresent());
        assertTrue(cabinet.findFolderByName("Empty").isPresent());

        assertEquals(2, cabinet.findFoldersBySize("LARGE").size());
        assertEquals(2, cabinet.findFoldersBySize("MEDIUM").size());
        assertEquals(2, cabinet.findFoldersBySize("SMALL").size());
        assertEquals(6, cabinet.count());
    }

    @Test
    public void testMultiLevelFolderInvalid() {
        List<Folder> folders = List.of(new MultipleFolders("MediumFolder", FolderSize.MEDIUM.name(),
                List.of(new SingleFolder("MediumFolder", FolderSize.MEDIUM.name()),
                        new SingleFolder("SmallFolder", FolderSize.SMALL.name()))));

        FileCabinetNoStream cabinet = new FileCabinetNoStream(folders);

        assertFalse(cabinet.findFolderByName("LargeFolder").isPresent());
        assertEquals(0, cabinet.findFoldersBySize("LARGE").size());
        assertEquals(3, cabinet.count());
    }

    @Test
    public void testInvalidFolderSize() {
        List<Folder> folders = List.of(new SingleFolder("LargeFolder", FolderSize.LARGE.name()),
                new SingleFolder("MediumFolder", FolderSize.MEDIUM.name()),
                new SingleFolder("SmallFolder", FolderSize.SMALL.name()));

        FileCabinetNoStream cabinet = new FileCabinetNoStream(folders);

        assertNull(cabinet.findFoldersBySize("BIG"));
    }
}

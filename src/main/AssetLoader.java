package main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class AssetLoader {
    private AssetLoader() {
    }

    public static File getFile(String relativePath) {
        Path workingDirectory = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();

        for (Path base = workingDirectory; base != null; base = base.getParent()) {
            Path directMatch = base.resolve(relativePath).normalize();
            if (directMatch.toFile().isFile()) {
                return directMatch.toFile();
            }

            Path sourceFolderMatch = base.resolve("Source Folder").resolve(relativePath).normalize();
            if (sourceFolderMatch.toFile().isFile()) {
                return sourceFolderMatch.toFile();
            }
        }

        return workingDirectory.resolve("Source Folder").resolve(relativePath).normalize().toFile();
    }
}

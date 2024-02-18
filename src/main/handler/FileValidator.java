package main.handler;

import java.nio.file.Path;
import java.util.List;

public class FileValidator {

    private static final List<String> SYSTEM_FILE_EXTENSIONS = List.of("bak", "cab", "cfg", "cpl", "dll",
            "dmp", "icns", "ini", "msi", "sys", "exe", "bin");

    public boolean isPathValid(Path path) {
        return SYSTEM_FILE_EXTENSIONS.stream()
                .noneMatch(ext -> path.getFileName().toString().endsWith(ext));
    }
}

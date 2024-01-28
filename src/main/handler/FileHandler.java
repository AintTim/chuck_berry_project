package main.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String VALID_FILE_FORMAT = ".txt";

    private FileHandler() {

    }

    public static List<String> readFile(Path filePath) {
        if (Files.exists(filePath) && validate(filePath)) {
            try {
                return Files.readAllLines(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public static void writeFile(Path destination, String text) {
        try {
            if (!Files.exists(destination)) {
                Files.createFile(destination);
            }
            Files.write(destination, text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validate(Path path) {
        return path.endsWith(VALID_FILE_FORMAT);
    }
}

package main.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileHandler {

    FileValidator fileValidator;
    public FileHandler(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    public List<String> readFile(Path filePath) {
        try {
            if (fileValidator.isPathValid(filePath)) {
                return Files.readAllLines(filePath);
            } else {
                throw new IllegalArgumentException(String.format("Указанный путь ведет к файлу недопустимого формата: %s", filePath.getFileName()));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Невозможно прочитать указанный файл");
        }
    }

    public void writeFile(Path destination, String text) {
        if (!fileValidator.isPathValid(destination)) {
            throw new IllegalArgumentException("Указан недопустимый тип файла");
        }
        try {
            if (!Files.exists(destination)) {
                Files.createFile(destination);
            }
            Files.write(destination, text.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("Невозможно сделать запись в указанный файл");
        }
    }
}

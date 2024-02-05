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

    public List<String> readFile(Path source) {
        try {
            if (fileValidator.isPathValid(source)) {
                return Files.readAllLines(source);
            } else {
                throw new IllegalArgumentException(String.format("Указанный путь ведет к файлу недопустимого формата: %s", source.getFileName()));
            }
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Невозможно прочитать указанный файл - %s", source));
        }
    }

    public void writeFile(Path destination, String text) {
        if (!fileValidator.isPathValid(destination)) {
            throw new IllegalArgumentException(String.format("Указан недопустимый тип файла %s", destination.getFileName()));
        }
        try {
            if (!Files.exists(destination)) {
                Files.createFile(destination);
            }
            Files.write(destination, text.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Проверьте корректность указанного пути -  %s", destination));
        }
    }
}

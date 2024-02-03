package main;

import static main.model.Alphabet.*;

import main.handler.ConsoleHandler;
import main.handler.FileHandler;
import main.handler.FileValidator;
import main.model.EncryptionModel;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static final Path SOURCE_FILE_PATH = Path.of("src/resources/source.txt");
    private static final Path DEST_FILE_PATH = Path.of("src/resources/encrypted.txt");
    private static final Path DEST_FILE_PATH_1 = Path.of("src/resources/decrypted.txt");

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(new FileValidator());
        ConsoleHandler consoleHandler = new ConsoleHandler(new Scanner(System.in));

        CryptoAnalyzer cryptoAnalyzer = new CryptoAnalyzer(fileHandler, RUSSIAN, PUNCTUATION);
        cryptoAnalyzer.encrypt(new EncryptionModel(SOURCE_FILE_PATH, DEST_FILE_PATH, 3));
        cryptoAnalyzer.decrypt(new EncryptionModel(DEST_FILE_PATH, DEST_FILE_PATH_1, 3));

        //bruteForce - 3 файла: откуда, куда и референс
        //статистика - 3 файла екст. Текст / екст, текст
        // список системных файлов ОС
    }
}

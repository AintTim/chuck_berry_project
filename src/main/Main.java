package main;

import static main.model.Alphabet.*;

import main.handler.ConsoleHandler;
import main.handler.FileHandler;
import main.handler.FileValidator;
import main.model.Option;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static final Path SOURCE_FILE_PATH = Path.of("src/resources/source.txt");
    private static final Path DEST_FILE_PATH = Path.of("src/resources/encrypted.txt");
    private static final Path DEST_FILE_PATH_1 = Path.of("src/resources/decrypted.txt");

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(new FileValidator());
        ConsoleHandler console = new ConsoleHandler(new Scanner(System.in));
        CryptoAnalyzer analyzer = new CryptoAnalyzer(fileHandler, RUSSIAN, PUNCTUATION);

        console.presentAvailableOptions();
        Option option = console.selectOption();

        switch (option) {
            case ENCRYPT -> analyzer.encrypt(console.createEncryptedObject(false));
            case DECRYPT -> analyzer.decrypt(console.createEncryptedObject(false));
            case BRUTE_FORCE -> analyzer.bruteForce(console.createEncryptedObject(true));
            case STATISTICAL_ANALYSIS -> analyzer.bruteForce(null);
        }

        //bruteForce - 3 файла: откуда, куда и референс
        //статистика - 3 файла екст. Текст / екст, текст
        // список системных файлов ОС


    }
}

package main;

import main.handler.ConsoleHandler;
import main.handler.FileContentHandler;
import main.handler.FileHandler;
import main.handler.FileValidator;
import main.model.Option;

import java.util.Scanner;

import static main.constant.Instruction.ENCRYPTION_KEY;

public class Main {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler(new FileValidator());
        FileContentHandler fileContentHandler = new FileContentHandler(fileHandler);
        ConsoleHandler console = new ConsoleHandler(new Scanner(System.in));

        console.presentAlphabets();
        CryptoAnalyzer analyzer = new CryptoAnalyzer(fileContentHandler, console.selectAlphabet());
        boolean keepWorking = true;

        while (keepWorking) {
            console.presentOptions();
            Option option = console.selectOption();

            switch (option) {
                case ENCRYPT -> analyzer.encrypt(console.createEncryptedObject(false), true);
                case DECRYPT -> analyzer.decrypt(console.createEncryptedObject(false), true);
                case BRUTE_FORCE ->
                        console.announce(ENCRYPTION_KEY + analyzer.bruteForce(console.createEncryptedObject(true)));
                case STATISTICAL_ANALYSIS ->
                        console.announce(ENCRYPTION_KEY + analyzer.statisticalAnalysis(console.createEncryptedObject(true)));
                case EXIT -> keepWorking = false;
            }
        }
    }
}

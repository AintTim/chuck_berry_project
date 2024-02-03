package main.handler;

import main.model.EncryptionModel;

import java.nio.file.Path;
import java.util.Scanner;

public class ConsoleHandler {
        Scanner scanner;

        public ConsoleHandler(Scanner scanner) {
            this.scanner = scanner;
        }

        public EncryptionModel createEncryptedObject() {
            System.out.println("Введите адрес файла оригинального текста: ");
            Path source = Path.of(scanner.nextLine());

            System.out.println("Введите адрес файла с результатом: ");
            Path destination = Path.of(scanner.nextLine());

            System.out.println("Введите ключ шифрования: ");
            int key = scanner.nextInt();
            return new EncryptionModel(source, destination, key);
        }
}

package main.handler;

import static main.constant.Instruction.*;
import main.model.EncryptionModel;
import main.model.Option;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleHandler {
        Scanner scanner;

        public ConsoleHandler(Scanner scanner) {
            this.scanner = scanner;
        }

        public EncryptionModel createEncryptedObject(boolean hasReference) {
            Path source = Path.of(provideData(ENTER_SOURCE_PATH));
            Path destination = Path.of(provideData(ENTER_DESTINATION_PATH));
            Path reference = null;
            int key;
            if (hasReference) {
                reference = Path.of(provideData(ENTER_REFERENCE_PATH));
                key = 1;
            } else {
                key = Integer.parseInt(provideData(ENTER_KEY));
                while (!validateKey(key)) {
                    key = Integer.parseInt(provideData(INVALID_KEY));
                }
            }

            return new EncryptionModel(source, destination, reference, key);
        }

        public void presentAvailableOptions() {
            System.out.println(AVAILABLE_OPTIONS);
            Arrays.stream(Option.values()).forEach(o -> {
                System.out.printf("Для выбора опции '%s' нажмите %d\t(%s)%n", o.name(), o.getValue(), o.getDescription());
            });
        }

        public Option selectOption() {
            System.out.println(SELECT_OPTION);
            int input = Integer.parseInt(scanner.nextLine());
            Optional<Option> option = Option.getFromValue(input);
            while (option.isEmpty()) {
                System.out.println(INVALID_OPTION);
                input = Integer.parseInt(scanner.nextLine());
                option = Option.getFromValue(input);
            }
            return option.get();
        }

        private String provideData(String instruction) {
            System.out.println(instruction);
            return scanner.nextLine();
        }

        private static boolean validateKey(int key) {
            return key > 0;
        }
}

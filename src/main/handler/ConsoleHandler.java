package main.handler;

import main.model.Alphabet;
import main.model.EncryptionModel;
import main.model.Option;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import static main.constant.Instruction.AVAILABLE_ALPHABETS;
import static main.constant.Instruction.AVAILABLE_OPTIONS;
import static main.constant.Instruction.ENTER_DESTINATION_PATH;
import static main.constant.Instruction.ENTER_KEY;
import static main.constant.Instruction.ENTER_REFERENCE_PATH;
import static main.constant.Instruction.ENTER_SOURCE_PATH;
import static main.constant.Instruction.INVALID_ALPHABET;
import static main.constant.Instruction.INVALID_KEY;
import static main.constant.Instruction.INVALID_OPTION;
import static main.constant.Instruction.SELECT_ALPHABET;
import static main.constant.Instruction.SELECT_OPTION;

public class ConsoleHandler {
    private final Scanner scanner;

    public ConsoleHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    private static boolean validateKey(int key) {
        return key > 0;
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

    public void announce(String announcement) {
        System.out.println(announcement);
    }

    public void presentOptions() {
        announce(AVAILABLE_OPTIONS);
        Arrays.stream(Option.values())
                .forEach(o -> announce(String.format("Для выбора опции '%s' нажмите %d\t(%s)", o.name(), o.getValue(), o.getDescription())));
    }

    public void presentAlphabets() {
        announce(AVAILABLE_ALPHABETS);
        Arrays.stream(Alphabet.values())
                .forEach(a -> announce(a.name()));
    }

    public List<Alphabet> selectAlphabet() {
        String spaceRegex = " ";
        try {
            announce(SELECT_ALPHABET);
            return Stream.of(scanner.nextLine().split(spaceRegex))
                    .map(Alphabet::valueOf)
                    .toList();
        } catch (IllegalArgumentException e) {
            announce(INVALID_ALPHABET);
            return selectAlphabet();
        }
    }

    public Option selectOption() {
        announce(SELECT_OPTION);
        int input = Integer.parseInt(scanner.nextLine());
        Optional<Option> option = Option.getFromValue(input);
        while (option.isEmpty()) {
            announce(INVALID_OPTION);
            input = Integer.parseInt(scanner.nextLine());
            option = Option.getFromValue(input);
        }
        return option.get();
    }

    private String provideData(String instruction) {
        announce(instruction);
        return scanner.nextLine();
    }
}

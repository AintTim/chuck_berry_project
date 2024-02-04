package main;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import main.constant.Instruction;
import main.handler.FileHandler;
import main.model.Alphabet;
import main.model.EncryptionModel;

import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;
import static main.handler.MapHandler.getKey;
import static main.handler.MapHandler.getSortedValues;

public class CryptoAnalyzer {

    private static final String UNIVERSAL_NON_WORD_REGEX = "[^\\wa-zA-Zа-яА-Я]";
    private final Map<Integer, Character> alphabetMap;
    private final FileHandler fileHandler;

    public CryptoAnalyzer(FileHandler fileHandler, Alphabet... alphabets) {
        alphabetMap = mapAlphabet(Alphabet.getDictionaries(alphabets));
        this.fileHandler = fileHandler;
    }

    public void encrypt(EncryptionModel model) {
        List<String> originalText = fileHandler.readFile(model.getSource());
        StringBuilder encryptedText = new StringBuilder(originalText.size());

        BiConsumer<Character, Integer> encryptSymbol = (symbol,k) -> {
            if (alphabetMap.containsValue(Character.toLowerCase(symbol))) {
                k = (getKey(alphabetMap, Character.toLowerCase(symbol)) + k) % alphabetMap.size();
                Character encryptedSymbol = (Character.isUpperCase(symbol))
                        ? Character.toUpperCase(alphabetMap.get(k))
                        : alphabetMap.get(k);
                encryptedText.append(encryptedSymbol);
            } else {
                encryptedText.append(symbol);
            }
        };

        for (String line : originalText) {
            for (char c : line.toCharArray()) {
                encryptSymbol.accept(c, model.getKey());
            }
            encryptedText.append(System.lineSeparator());
        }
        fileHandler.writeFile(model.getDestination(), encryptedText.toString());
    }
    
    public void decrypt(EncryptionModel model) {
        int decryptKey = alphabetMap.size() - (model.getKey() % alphabetMap.size());
        model.setKey(decryptKey);
        encrypt(model);
    }

    public EncryptionModel bruteForce(EncryptionModel model) {
        Map<String, MutableInt> dictionary = collectUniqueWords(model.getReference(), UNIVERSAL_NON_WORD_REGEX);
        List<String> fiveMostPopularWords = getSortedValues(dictionary, Comparator.comparingInt(e -> e.getValue().value)).subList(0, 5);
        int key = model.getKey();
        while (key < alphabetMap.size()) {
            decrypt(model);
            if (allWordsMatch(model.getDestination(), fiveMostPopularWords)) {
                System.out.println(Instruction.BRUTE_FORCE_VALID_KEY + key);
                return model;
            }
            model.setKey(++key);
        }
        //Добавить исключение или рекомендацию уменьшить количество проверямых ключей
        throw new IllegalArgumentException("Не удалось расшифровать файл. Попробуйте увеличить объём текста с примером или сократить количество проверямых ключей");
    }

    private boolean allWordsMatch(Path source, List<String> wordCheckList) {
        return collectUniqueWords(source, UNIVERSAL_NON_WORD_REGEX).keySet().containsAll(wordCheckList);
    }

    private Map<String, MutableInt> collectUniqueWords(Path source, String splitter) {
        Map<String, MutableInt> dictionary = new HashMap<>();
        for (String line : fileHandler.readFile(source)) {
            Arrays.stream(line.split(splitter))
                    .filter(w -> !w.isBlank())
                    .forEach(word -> {
                        MutableInt value = dictionary.get(word);
                        if (value == null) {
                            dictionary.put(word, new MutableInt());
                        } else {
                            value.increment();
                        }
                    });
        }
        return dictionary;
    }

    private static Map<Integer, Character> mapAlphabet(List<Character> alphabet) {
        return IntStream.range(0, alphabet.size())
                .boxed()
                .collect(toMap(i -> i, alphabet::get));
    }

    @Getter
    @EqualsAndHashCode(of = {"value"})
    private static class MutableInt {
        private int value = 1;

        public void increment() {
            ++value;
        }
    }
}

package main;

import main.handler.FileHandler;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;
import static main.handler.MapHandler.getKey;

public class CryptoAnalyzer {

    private final Map<Integer, Character> alphabetMap;
    private final List<String> originalText;
    private final StringBuilder encryptedText;

    public CryptoAnalyzer(Path sourceFilePath, List<Character> alphabet) {
        originalText = FileHandler.readFile(sourceFilePath);
        alphabetMap = mapAlphabet(alphabet);
        encryptedText = new StringBuilder(originalText.size());
    }

    public void encrypt(int key, Path destination) {
        if (!validateKey(key)) {
            throw new IllegalArgumentException(String.format("Ключ шифра '%d' не соответствует требованиям!", key));
        }

        BiConsumer<Character, Integer> encryptSymbol = (symbol,k) -> {
            if (alphabetMap.containsValue(symbol)) {
                k = (getKey(alphabetMap, symbol) + k) % alphabetMap.size();
                encryptedText.append(alphabetMap.get(k));
            } else {
                encryptedText.append(symbol);
            }
        };

        for (String line : originalText) {
            for (char c : line.toLowerCase().toCharArray()) {
                encryptSymbol.accept(c, key);
            }
        }
        FileHandler.writeFile(destination, encryptedText.toString());
    }
    
    public void decrypt(int key, Path destination) {
        int decryptKey = alphabetMap.size() - (key % alphabetMap.size());
        encrypt(decryptKey, destination);
    }
    
    private boolean validateKey(int key) {
        return key > 0 && key < (alphabetMap.size() - 1);
    }

    private static Map<Integer, Character> mapAlphabet(List<Character> alphabet) {
        return IntStream.range(0, alphabet.size())
                .boxed()
                .collect(toMap(i -> i, alphabet::get));
    }
}

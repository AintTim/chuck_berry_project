package main;

import main.handler.FileHandler;
import main.model.Alphabet;
import main.model.EncryptionModel;

import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;
import static main.handler.MapHandler.getKey;

public class CryptoAnalyzer {

    private final Map<Integer, Character> alphabetMap;
    private final FileHandler fileHandler;

    public CryptoAnalyzer(FileHandler fileHandler, Alphabet... alphabets) {
        alphabetMap = mapAlphabet(Alphabet.getDictionaries(alphabets));
        this.fileHandler = fileHandler;
    }

    public void encrypt(EncryptionModel model) {
        if (!validateKey(model.getKey())) {
            throw new IllegalArgumentException(String.format("Ключ шифра '%d' не соответствует требованиям!", model.getKey()));
        }
        List<String> originalText = fileHandler.readFile(model.getSource());
        StringBuilder encryptedText = new StringBuilder(originalText.size());

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
                encryptSymbol.accept(c, model.getKey());
            }
        }
        fileHandler.writeFile(model.getDestination(), encryptedText.toString());
    }
    
    public void decrypt(EncryptionModel model) {
        int decryptKey = alphabetMap.size() - (model.getKey() % alphabetMap.size());
        model.setKey(decryptKey);
        encrypt(model);
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

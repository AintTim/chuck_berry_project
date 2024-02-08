package main;

import static main.handler.MapHandler.*;
import main.handler.FileContentHandler;
import main.model.Alphabet;
import main.model.EncryptionModel;

import java.nio.file.Path;
import java.util.*;

public class CryptoAnalyzer {

    private static final String UNIVERSAL_NON_WORD_REGEX = "[^\\wa-zA-Zа-яА-Я]";
    private final Map<Integer, Character> alphabetMap;
    private final FileContentHandler fileHandler;

    public CryptoAnalyzer(FileContentHandler fileContentHandler, Alphabet... alphabets) {
        alphabetMap = getIndexedMap(Alphabet.getDictionaries(alphabets));
        this.fileHandler = fileContentHandler;
    }

    public void encrypt(EncryptionModel model) {
        List<String> originalText = fileHandler.readContent(model.getSource());
        StringBuilder encryptedText = new StringBuilder(originalText.size());

        for (String line : originalText) {
            for (char symbol : line.toCharArray()) {
                Character encryptedSymbol = (alphabetMap.containsValue(Character.toLowerCase(symbol)))
                        ? encryptSymbol(symbol, model.getKey())
                        : symbol;
                encryptedText.append(encryptedSymbol);
            }
            encryptedText.append(System.lineSeparator());
        }
        fileHandler.writeContent(model.getDestination(), encryptedText.toString());
    }

    public void decrypt(EncryptionModel model) {
        int decryptKey = alphabetMap.size() - (model.getKey() % alphabetMap.size());
        model.setKey(decryptKey);
        encrypt(model);
    }

    public int bruteForce(EncryptionModel model) {
        List<String> threeCommonWords = fileHandler.collectCommonWords(model.getReference(), UNIVERSAL_NON_WORD_REGEX, 3);
        int key = model.getKey();
        while (key < alphabetMap.size()) {
            decrypt(model);
            if (allWordsMatch(model.getDestination(), threeCommonWords)) {
                return key;
            }
            model.setKey(++key);
        }
        throw new IllegalArgumentException("Не удалось расшифровать файл.\nПопробуйте увеличить объём текста с примером");
    }

    public int statisticalAnalysis(EncryptionModel model) {
        Character referenceMostCommonChar = getCommonChar(model.getReference());
        Character sourceMostCommonChar = getCommonChar(model.getSource());
        int encryptionKey = getEncryptionKey(referenceMostCommonChar, sourceMostCommonChar);
        model.setKey(encryptionKey);
        decrypt(model);
        return encryptionKey;
    }

    private Integer getEncryptionKey(Character origin, Character encrypted) {
        int key1 = getKey(alphabetMap, origin);
        int key2 = getKey(alphabetMap, encrypted);
        return (key2 < key1)
                ? alphabetMap.size() + key2 - key1
                : key2 - key1;
    }

    private Character getCommonChar(Path source) {
        return getSortedValues(fileHandler.collectSymbolFrequency(source, alphabetMap), Map.Entry.comparingByValue()).get(0);
    }

    private Character encryptSymbol(Character symbol, int key) {
        int encryptionKey = (getKey(alphabetMap, Character.toLowerCase(symbol)) + key) % alphabetMap.size();
        return  (Character.isUpperCase(symbol))
                ? Character.toUpperCase(alphabetMap.get(encryptionKey))
                : alphabetMap.get(encryptionKey);
    }

    private boolean allWordsMatch(Path source, List<String> wordCheckList) {
        return fileHandler.collectUniqueWords(source, UNIVERSAL_NON_WORD_REGEX).keySet().containsAll(wordCheckList);
    }
}

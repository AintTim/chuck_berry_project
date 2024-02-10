package main.handler;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.file.Path;
import java.util.*;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.toMap;

public class FileContentHandler {
    private final FileHandler fileHandler;

    public FileContentHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public List<String> readContent(Path source) {
        return fileHandler.readFile(source);
    }

    public void writeContent(Path destination, String content) {
        fileHandler.writeFile(destination, content);
    }

    /**
     * Подсчитываем частоту для всех уникальных слов в файле (ключи) и возвращаем некоторое их количество
     * @see FileContentHandler#collectUniqueWords(Path, String)
     * @param source путь файла-источника
     * @param splitter символ, разделяющий слова
     * @param limit количество возвращаемых ключей
     * @return Список наиболее распространённых слов в файле
     */
    public List<String> collectCommonWords(Path source, String splitter, int limit) {
        Map<String, Integer> dictionary = collectUniqueWords(source, splitter);
        return MapHandler.getSortedValues(dictionary, Comparator.comparingInt(Map.Entry::getValue))
                .subList(0, limit);
    }

    /**
     * @see main.model.Alphabet енам с символами
     * @param source путь файла-источника
     * @param alphabet список символов для анализа
     * @return карту с символами и их частотой в рамках файла-источника
     */
    public Map<Character, Double> collectSymbolFrequency(Path source, Map<Integer, Character> alphabet) {
        Map<Character, MutableInt> dictionary = new HashMap<>();
        for (String line : readContent(source)) {
            for (char symbol : line.toCharArray()) {
                if (alphabet.containsValue(Character.toLowerCase(symbol))) {
                    MutableInt value = dictionary.get(symbol);
                    if (value == null) {
                        dictionary.put(symbol, new MutableInt());
                    } else {
                        value.increment();
                    }
                }
            }
        }
        int totalNumOfSymbols = dictionary.values()
                .stream()
                .map(MutableInt::getValue)
                .reduce(0, Integer::sum);

        ToDoubleFunction<MutableInt> countFrequency = number -> (double) number.value / totalNumOfSymbols * 100;

        return  dictionary.entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, e -> countFrequency.applyAsDouble(e.getValue())));
    }

    public Map<String, Integer> collectUniqueWords(Path source, String splitter) {
        Map<String, Integer> dictionary = new HashMap<>();
        for (String line : readContent(source)) {
            collectUniqueWords(line, splitter).forEach((k,v) -> dictionary.merge(k, 1, Integer::sum));
        }
        return dictionary;
    }

    public Map<String, Integer> collectUniqueWords(String source, String splitter) {
        Map<String, MutableInt> dictionary = new HashMap<>();
        Arrays.stream(source.split(splitter))
                .filter(w -> !w.isBlank())
                .forEach(word -> {
                    MutableInt value = dictionary.get(word);
                    if (value == null) {
                        dictionary.put(word, new MutableInt());
                    } else {
                        value.increment();
                    }
                });
        return dictionary.entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().value));
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

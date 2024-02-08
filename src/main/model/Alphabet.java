package main.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Alphabet {
    RU(List.of('а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'й','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я')),
    ENG(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')),
    PUNCTUATION(List.of(' ', '.', ',', '«', '»', '"', '\'', ':', '!', '?'));

    private final List<Character> dictionary;

    Alphabet(List<Character> dictionary) {
        this.dictionary = dictionary;
    }

    public static List<Character> getDictionaries(Alphabet... alphabets) {
        return Arrays.stream(alphabets)
                .collect(ArrayList::new,
                        (list, dictionary) -> list.addAll(dictionary.dictionary),
                        ArrayList::addAll);
    }
}

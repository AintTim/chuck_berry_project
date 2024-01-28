package main;

import main.handler.FileHandler;

import java.nio.file.Path;
import java.util.List;

public class CryptoAnalyzer {
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    final List<String> origin;

    public CryptoAnalyzer(Path sourceFilePath) {
        origin = FileHandler.readFile(sourceFilePath);
    }
}

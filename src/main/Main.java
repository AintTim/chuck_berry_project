package main;

import java.nio.file.Path;

public class Main {
    private static final Path SOURCE_FILE_PATH = Path.of("source.txt");
    CryptoAnalyzer cryptoAnalyzer = new CryptoAnalyzer(SOURCE_FILE_PATH);

}

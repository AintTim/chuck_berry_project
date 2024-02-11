package main.model;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.List;

@Getter
@Setter
public class EncryptionModel {
    private Path source;
    private Path destination;
    private Path reference;
    private int key;
    private List<String> cachedSourceText;

    public EncryptionModel(Path source, Path destination, Path reference, int key) {
        this.source = source;
        this.destination = destination;
        this.reference = reference;
        this.key = key;
    }
}

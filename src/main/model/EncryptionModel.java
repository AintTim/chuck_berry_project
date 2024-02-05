package main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
@Getter
@Setter
@AllArgsConstructor
public class EncryptionModel {
    private Path source;
    private Path destination;
    private Path reference;
    private int key;
}

package main.model;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

import static main.constant.Description.*;
@Getter
public enum Option {
    ENCRYPT(1, ENCRYPT_DESC),
    DECRYPT(2, DECRYPT_DESC),
    BRUTE_FORCE(3, BRUTE_FORCE_DESC),
    STATISTICAL_ANALYSIS(4, STATISTICS_ANALYSIS_DESC);

    private final int value;
    private final String description;

    Option(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Optional<Option> getFromValue(int value) {
        return Arrays.stream(Option.values())
                .filter(o -> o.getValue() == value)
                .findFirst();
    }
}

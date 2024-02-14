package main.model;

import lombok.Getter;
import main.constant.Description;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Option {
    ENCRYPT(1, Description.ENCRYPT_DESC),
    DECRYPT(2, Description.DECRYPT_DESC),
    BRUTE_FORCE(3, Description.BRUTE_FORCE_DESC),
    STATISTICAL_ANALYSIS(4, Description.STATISTICS_ANALYSIS_DESC),
    EXIT(5, Description.EXIT_DESC);

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

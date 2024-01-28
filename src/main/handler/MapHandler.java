package main.handler;

import java.util.Map;

public class MapHandler {

    private MapHandler() {

    }

    public static <K, V> K getKey(Map< K, V> map, V value) {
        return map.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(value))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}

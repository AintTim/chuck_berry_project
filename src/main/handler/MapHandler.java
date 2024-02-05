package main.handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MapHandler {

    private MapHandler() {

    }

    public static <K, V> List<K> getSortedValues(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        return map.entrySet().stream()
                .sorted(comparator.reversed())
                .collect(ArrayList::new,
                        (list, entry) -> list.add(entry.getKey()),
                        ArrayList::addAll);
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        return map.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(value))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}

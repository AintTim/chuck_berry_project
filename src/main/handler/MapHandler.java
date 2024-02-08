package main.handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

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

    public static <V> Map<Integer, V> getIndexedMap(List<V> values) {
        return IntStream.range(0, values.size())
                .boxed()
                .collect(toMap(i -> i, values::get));
    }
}

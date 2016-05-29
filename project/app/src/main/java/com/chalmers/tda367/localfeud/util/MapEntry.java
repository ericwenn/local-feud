package com.chalmers.tda367.localfeud.util;

import java.util.AbstractMap;
import java.util.Map;

public class MapEntry<K, V> extends AbstractMap.SimpleEntry {
    private final K key;
    private V value;

    public MapEntry(K key, V value) {
        super(key, value);
        this.key = key;
        this.value = value;
    }

    public MapEntry(Map.Entry<K, V> entry) {
        this(entry.getKey(), entry.getValue());
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return value;
    }
}

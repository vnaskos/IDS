package com.vnaskos.ids.adapter.influxdb;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

class MapWithNameAsKey<V extends Named> implements Iterable<V> {

    private final HashMap<String, V> internalMap = new HashMap<>();

    public void add(V value) {
        this.internalMap.put(value.getName(), value);
    }

    public void addAll(MapWithNameAsKey<V> map) {
        for (V value : map) {
            add(value);
        }
    }

    public Stream<V> stream() {
        return internalMap.values().stream();
    }

    public int size() {
        return internalMap.size();
    }

    public V get(String key) {
        return internalMap.get(key);
    }

    public V remove(String key) {
        return internalMap.remove(key);
    }

    public Collection<V> toArray() {
        return internalMap.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapWithNameAsKey<?> that = (MapWithNameAsKey<?>) o;
        return internalMap.equals(that.internalMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalMap);
    }

    @Override
    public Iterator<V> iterator() {
        return internalMap.values().iterator();
    }
}

package org.example.cache;

public interface Cache<T> {
    T get(Long key);

    T put(Long key, T value);

    void delete(Long key);
}

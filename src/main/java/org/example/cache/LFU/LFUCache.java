package org.example.cache.LFU;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class LFUCache<T> {

    /**
     * Коллекция для хранения значений (ключ - будет id, значение лист связанных объектов)
     */
    public LinkedHashMap<Long, Node<T>> storage;

    /**
     * Емкость кэша (размер задается в applicationCache.yml)
     */
    public int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.storage = new LinkedHashMap<>(capacity, 1);
    }

}

package org.example.cache.LRU;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LRUCache <T>{
    /**
     * Коллекция для хранения значений (ключ - будет id, значение лист связанных объектов)
     */
    public Map<Long, LinkedNode<T>> content;
    /**
     * Ссылка на первый элемент
     */
    public LinkedNode<T> head;
    /**
     * Ссылка на последний элемент
     */
    public LinkedNode<T> tail;
    /**
     * Емкость кэша (размер задается в applicationCache.yml)
     */
    public int capacity;
    /**
     * Количество элементов в списке (размер задается в applicationCache.yml)
     */
    public int size;


    public LRUCache (int capacity) {
        this.capacity = capacity;
        content = new HashMap<>(capacity);
        head = new LinkedNode<T>();
        tail = new LinkedNode<T>();
        head.next = tail;
        tail.prev = head;
    }
}

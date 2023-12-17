package org.example.cache.LRU;

import lombok.Data;
import lombok.ToString;

@Data
public class LinkedNode<T> {
    /**
     * Значение ссылки
     */
    T value;
    /**
     * Значение ключа
     */
    Long key;

    /**
     * Ссылка на соседний элемент(предыдущий)
     */
    @ToString.Exclude
    LinkedNode<T> prev;
    /**
     * Ссылка на соседний элемент(следующий)
     */
    @ToString.Exclude
    LinkedNode<T> next;


}

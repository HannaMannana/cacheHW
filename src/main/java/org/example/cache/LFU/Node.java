package org.example.cache.LFU;

import lombok.Data;

@Data
public class Node<T> {
    /**
     * Значение ссылки
     */
    private final T value;
    /**
     * Частота обращений
     */
    private long frequency;

    public Node(T value) {
        this.value = value;
        this.frequency = 1;
    }

    public Node<T> incrementFrequency() {
        ++frequency;
        return this;
    }

}

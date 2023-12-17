package org.example.cache.LFU;

import lombok.RequiredArgsConstructor;
import org.example.cache.Cache;

import java.util.Map;
import java.util.Objects;


@RequiredArgsConstructor
public class LFUImpl<T> implements Cache<T> {

    private final LFUCache<T> lfuCache;

    /**
     * Добавляет в кэш по ключу(id)
     *
     * @param key идентификатор пользователя
     * @return объект добавленный в кэш
     */
    @Override
    public T get(Long key) {
        Node<T> node = lfuCache.storage.get(key);
        if (node == null) {
            return null;
        }
        return node.incrementFrequency().getValue();
    }

    /**
     * Берет из кэша по ключу(id) объект и значению (объект)
     *
     * @param key идентификатор пользователя
     * @return объект добавленный в кэш
     */
    @Override
    public T put(Long key, T value) {
        doEvictionIfNeeded(key);
        Node<T> oldNode = lfuCache.storage.put(key, new Node<T>(value));
        if (oldNode == null) {
            return null;
        }
        return oldNode.getValue();
    }

    /**
     * Перемещает элемент при повторном обращении
     *
     * @param putKey идентификатор пользователя
     */
    private void doEvictionIfNeeded(Long putKey) {
        if (lfuCache.storage.size() < lfuCache.capacity) {
            return;
        }
        long minFrequency = Long.MAX_VALUE;
        Long keyToRemove = null;
        for (Map.Entry<Long, Node<T>> entry : lfuCache.storage.entrySet()) {
            if (Objects.equals(entry.getKey(), putKey)) {
                return;
            }
            if (minFrequency >= entry.getValue().getFrequency()) {
                minFrequency = entry.getValue().getFrequency();
                keyToRemove = entry.getKey();
            }
        }
        lfuCache.storage.remove(keyToRemove);
    }

    /**
     * Удаляет из кэша по ключу(id) объект
     *
     * @param key идентификатор пользователя
     */
    @Override
    public void delete(Long key) {
        if (lfuCache.storage.containsKey(key)) {
            lfuCache.storage.remove(key);
        }
    }


}


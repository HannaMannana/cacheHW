package org.example.cache.LRU;

import lombok.RequiredArgsConstructor;
import org.example.cache.Cache;

@RequiredArgsConstructor
public class LRUImpl<T> implements Cache<T> {

    private final LRUCache<T> lruCache;

    /**
     * Добавляет в кэш по ключу(id) и значению (объект)
     *
     * @param key   идентификатор пользователя
     * @param value значение пользователя
     * @return объект добавленный в кэш
     */
    @Override
    public T put(Long key, T value) {
        LinkedNode<T> node = lruCache.content.get(key);
        if (node != null) {
            node.value = value;
            removeNode(node);
            moveToHead(node);
        } else {
            node = new LinkedNode<T>();
            node.value = value;
            node.key = key;
            lruCache.content.put(key, node);
            moveToHead(node);
            lruCache.size++;

            if (lruCache.size > lruCache.capacity) {
                lruCache.content.remove(lruCache.tail.prev.key);
                removeNode(lruCache.tail.prev);
                lruCache.size--;
            }
        }
        return node.value;

    }

    /**
     * Берет из кэша по ключу(id) объект
     *
     * @param key идентификатор пользователя
     * @return объект добавленный в кэш
     */
    @Override
    public T get(Long key) {
        LinkedNode<T> elm = lruCache.content.get(key);
        if (elm == null) {
            return null;
        }
        removeNode(elm);
        moveToHead(elm);
        return (T) elm.value;
    }

    /**
     * Удаляет из кэша по ключу(id) объект
     *
     * @param key идентификатор пользователя
     */
    @Override
    public void delete(Long key) {
        LinkedNode<T> node = lruCache.content.get(key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = lruCache.content.remove(key);
    }

    /**
     * Перемещает ссылки при добавлении элемента
     *
     * @param node ссылка на элемент
     */
    public void removeNode(LinkedNode<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * Перемещает элемент в начало списка при повторном обращении
     *
     * @param node ссылка на элемент
     */
    public void moveToHead(LinkedNode<T> node) {
        LinkedNode<T> temp = lruCache.head.next;
        lruCache.head = node;
        node.prev = lruCache.head;
        node.next = temp;
        temp.prev = node;
    }
}

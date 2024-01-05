package org.example.proxy;

import lombok.RequiredArgsConstructor;
import org.example.cache.Cache;
import org.example.cache.LFU.LFUCache;
import org.example.cache.LFU.LFUImpl;
import org.example.cache.LRU.LRUCache;
import org.example.cache.LRU.LRUImpl;
import org.example.config.CacheProperties;
import org.example.config.Constants;

@RequiredArgsConstructor
public class Factory<T> {


    private Cache<T> cache;
    private final CacheProperties cacheProperties = new CacheProperties();
    private final LRUCache<T> lruCache = new LRUCache<>(Integer.parseInt(cacheProperties.getProperty(Constants.CAPACITY)));
    private final LFUCache<T> lfuCache = new LFUCache<>(Integer.parseInt(cacheProperties.getProperty(Constants.CAPACITY)));
    private final String VALUE = cacheProperties.getProperty(Constants.TYPE);


    public Cache<T> createCache() {

        if ("LRU".equalsIgnoreCase(VALUE)) {
            this.cache = new LRUImpl<T>(lruCache);
        } else if ("LFU".equalsIgnoreCase(VALUE)) {
            this.cache = new LFUImpl<T>(lfuCache);
        }
        return cache;
    }

}
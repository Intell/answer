package com.intell.lesson.auth.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by yr on 13-11-24.
 * 封装 spring cache 到 Shiro 的 cache<K, V>中，实现cache的对接
 *
 * @param <K>
 * @param <V>
 */
public class ShiroCache<K, V> implements Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(ShiroCache.class);

    private org.springframework.cache.Cache cache;

    public String name;


    public ShiroCache(String cacheName, org.springframework.cache.Cache cache) {
        this.name = cacheName;
        this.cache = cache;
    }


    @Override
    public V get(K key) throws CacheException {

        if (key == null) {
            return null;
        } else {
            org.springframework.cache.Cache.ValueWrapper value = cache.get(key.toString());
            V v = (V) ((value == null) ? null : value.get());
            logger.debug("从缓存中获取对象 key [{}] {}", new Object[] { key, v });
            return v;
        }

    }

    @Override
    public V put(K key, V value) throws CacheException {

        logger.debug("写入对象到缓存 key [{}] {}", new Object[] { key, value });

        if (key == null) {
            return null;
        } else {
            V previous = get(key);
            cache.put(key.toString(), value);
            return previous;
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("删除缓存对象 key [" + key + "]");
        V previous = get(key);
        cache.evict(key.toString());
        return previous;
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("清除全部缓存！");
        cache.clear();
    }

    @Override
    public int size() {
        // unsupported in spring AbstractCache
        return 0;
    }

    @Override
    public Set<K> keys() {
        // unsupported in spring AbstractCache
        return Collections.emptySet();
    }

    @Override
    public Collection<V> values() {
        // unsupported in spring AbstractCache
        return Collections.emptyList();
    }


}

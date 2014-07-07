package com.intell.lesson.auth.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yr on 13-11-24.
 * 应用springCacheMange实现shiro的CacheManager
 */
public class ShiroCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(ShiroCacheManager.class);

    private final ConcurrentMap<String, Cache> springCaches = new ConcurrentHashMap();
    private org.springframework.cache.CacheManager springCacheManager;


    public ShiroCacheManager(org.springframework.cache.CacheManager springCacheManager) {

        Assert.notNull(springCacheManager, "ShiroSpringCacheManager初始化失败，springCacheManager=null");

        this.springCacheManager = springCacheManager;

        //将 springCacheManager 中的cache 初始化到 shiro 的cacheManager 中
        Collection<String> cacheNames = springCacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            ShiroCache shiroCache = new ShiroCache<>(cacheName, springCacheManager.getCache(cacheName));
            if (shiroCache != null) {
                springCaches.putIfAbsent(cacheName, shiroCache);
            }
        }
    }



    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        Cache<K, V> cache = springCaches.get(cacheName);

        if (cache==null) {
            logger.error("shiroManager中获取 {} 失败", cacheName);
        }

        return cache;
    }

    public org.springframework.cache.CacheManager getSpringCacheManager() {
        return springCacheManager;
    }
}

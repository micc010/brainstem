/**
 * @文件名称： RedisMybatisCache.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/22
 * @作者 ： Roger
 */
package com.github.rogerli.common.cache;

import org.apache.ibatis.cache.Cache;
import org.mybatis.caches.redis.ConfigWithHost;
import org.mybatis.caches.redis.RedisCallback;
import org.mybatis.caches.redis.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Roger
 * @create 2016/12/22 11:03
 */
public class RedisMybatisCache implements Cache {

    private final static String PACKAGE_NAME = "com:rogerli:mybatis:";

    private final ReadWriteLock readWriteLock = new RedisMybatisDummyReadWriteLock();

    private String id;

    private JedisPool pool;

    public RedisMybatisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
        ConfigWithHost configWithHost = RedisMybatisConfigurationBuilder.getInstance().parseConfiguration();
        pool = new JedisPool(configWithHost, configWithHost.getHost());
    }

    private Object execute(RedisCallback callback) {
        Jedis jedis = pool.getResource();
        try {
            return callback.doWithRedis(jedis);
        } finally {
            jedis.close();
        }
    }

    public String getId() {
        return this.id;
    }

    public int getSize() {
        return (Integer) execute(jedis -> {
            Map<byte[], byte[]> result = jedis.hgetAll((PACKAGE_NAME + id.toString()).getBytes());
            return result.size();
        });
    }

    public void putObject(final Object key, final Object value) {
        execute(jedis -> {
            jedis.hset((PACKAGE_NAME + id.toString()).getBytes(), key.toString().getBytes(), SerializeUtil.serialize(value));
            return null;
        });
    }

    public Object getObject(final Object key) {
        return execute(jedis -> SerializeUtil.unserialize(jedis.hget((PACKAGE_NAME + id.toString()).getBytes(), key.toString().getBytes())));
    }

    public Object removeObject(final Object key) {
        return execute(jedis -> jedis.hdel((PACKAGE_NAME + id.toString()), key.toString()));
    }

    public void clear() {
        execute(jedis -> {
            jedis.del((PACKAGE_NAME + id.toString()));
            return null;
        });

    }

    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public String toString() {
        return "Redis {" + (PACKAGE_NAME + id) + "}";
    }


}

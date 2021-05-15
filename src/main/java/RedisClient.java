import redis.clients.jedis.Jedis;

public class RedisClient {

    public String get(String key) {
        try (Jedis jedis = getReadJedis()) {
            return jedis.get(key);
        }
    }

    public void set(String key, String value) {
        try (Jedis jedis = getWriteJedis()) {
            jedis.set(key, value);
        }
    }

    private Jedis getWriteJedis() {
        return JedisFactory.getInstance().getJedisPool();
    }

    private Jedis getReadJedis() {
        return JedisFactory.getInstance().getJedisPool();
    }
}

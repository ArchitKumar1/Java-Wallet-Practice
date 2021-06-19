import redis.clients.jedis.Jedis;


public class JedisFactory {
    private static final JedisFactory instance = new JedisFactory();
    private static Jedis jedisPool;


    private JedisFactory() {
        jedisPool = new Jedis();
    }

    public static JedisFactory getInstance() {
        return instance;
    }

    public Jedis getJedisPool() {
        return jedisPool;
    }
}

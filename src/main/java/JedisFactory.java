import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisFactory {
    private static Jedis jedisPool;
    private static final JedisFactory instance = new JedisFactory();


    private JedisFactory() {
        // TODO change jedis to jedis pool
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPool = new JedisPool(jedisPoolConfig, "*.*.*.*", 6379, 10 * 1000);

        jedisPool = new Jedis();
    }

    public static JedisFactory getInstance() {
        return instance;
    }

    public Jedis getJedisPool() {
        return jedisPool;
    }
}

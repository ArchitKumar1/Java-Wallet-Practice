package com.application;

import redis.clients.jedis.Jedis;

import static com.application.Constants.LOCALHOST;
import static com.application.Constants.REDIS_PORT;


public class JedisFactory {
    private static final JedisFactory instance = new JedisFactory();
    private static Jedis jedis;


    private JedisFactory() {

        jedis = new Jedis(LOCALHOST,REDIS_PORT);
    }

    public static JedisFactory getInstance() {
        return instance;
    }
    public Jedis getJedis(){
        return jedis;
    }
}

package com.application;

import com.application.config.Config;
import com.application.config.ConfigFactory;
import com.application.config.RedisConfig;

public class Setup {

    public void run() {
        Config config = new Config();
        RedisConfig redisConfig = getRedisConfig();

        config.setRedisConfig(redisConfig);
        ConfigFactory.setConfig(config);
    }

    private RedisConfig getRedisConfig() {
        return new RedisConfig();
        // TODO specific configs can be added later.
    }
}

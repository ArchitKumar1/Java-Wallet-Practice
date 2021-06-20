package com.application.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RedisConfig {

    private String url;
    private Integer dbIndex;
    private Integer longTimeout;
    private Integer maxTotal;
    private Integer minIdle;
    private Integer maxIdle;
    private String password;
    private Integer port;
    private Integer queryTimeout;
}
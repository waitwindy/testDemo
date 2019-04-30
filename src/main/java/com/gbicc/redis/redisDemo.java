package com.gbicc.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class redisDemo {

    public static void main(String[] args) {
//        JedisPoolConfig
        Jedis jedis = new Jedis("192.168.172.100",6379);
        String s = jedis.get("5e13c918bc1a3befaa9b3d2dabe71207");
        System.out.println(s);

//        jedis.set("5e13c918bc1a3befaa9b3d2dabe71207","aaaaasdfas");

    }
}

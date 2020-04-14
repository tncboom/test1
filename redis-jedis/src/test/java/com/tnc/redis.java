package com.tnc;

import com.sun.xml.internal.ws.spi.db.FieldSetter;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Auther 涂南昌
 * @Date 2020/4/7-21:07
 * @Param
 * @return
 * @Description
 */

public class redis {
    @Test
    public void testJedis() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }

    @Test
    public void testList() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.lpush("listJedis", "a", "b", "c");
        List<String> list = jedis.lrange("listJedis", 0, -1);
        for (String s : list) {
            System.out.println(s);
        }
        jedis.close();
    }

    @Test
    public void testDemo() {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.hset("userABC", "A", "10");
        jedis.hset("userABC", "B", "30");
        jedis.hset("userABC", "C", "99999999");
        for (int i = 1; i < 10; i++) {
            if (Integer.valueOf(jedis.hget("userABC", "A")) >= 0) {
                jedis.hincrBy("userABC", "A", -i);
                System.out.println(jedis.hget("userABC", "A"));
            } else {
                System.out.println("A的使用次数到期了");
            }
            jedis.hincrBy("userABC", "B", -i);

            System.out.println(jedis.hget("userABC", "B"));
            jedis.hincrBy("userABC", "C", -i);
            System.out.println(jedis.hget("userABC", "C"));


            jedis.close();

        }
    }
}
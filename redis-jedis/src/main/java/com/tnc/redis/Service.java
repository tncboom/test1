package com.tnc.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * @Auther 涂南昌
 * @Date 2020/4/7-21:41
 * @Param
 * @return
 * @Description
 */

public class Service {
    private  String id;
    private int num;
    public Service (String id ,int num) {
        this.id=id;
        this.num=num;
    }
    //控制单元
    public void service() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String value = jedis.get("comid:" + id);
        //判断该值是否存在
        try {
            if (value == null) {
                jedis.setex("comid:" + id, 20, Long.MAX_VALUE-num + "");
            } else {
               Long val=jedis.incr("comid:" + id);
                business(id,num-(Long.MAX_VALUE-val));
            }
        } catch (JedisDataException e) {
            System.out.println("到达上限");
            return;
        } finally {
            jedis.close();
        }
    }

    public void business(String id,Long val) {
        System.out.println("用户："+id+"业务操作执行第几次："+val);
    }
}

class MyThread extends Thread {
    Service sc ;
    public MyThread(String id,int num) {
        sc=new Service(id,num);
    }
    public void run() {
        while (true) {
            sc.service();

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        MyThread mt1 = new MyThread("初级用户",10);
        MyThread mt2 = new MyThread("高级用户",30);
        mt1.start();
        mt2.start();
    }
    }








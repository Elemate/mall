package com.wq.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    public void JedisTest() throws Exception{
        //创建连接
        Jedis jedis = new Jedis("10.1.1.179");
        //操作
        jedis.set("a","哈哈");
        String value = jedis.get("a");
        System.out.println(value);
        //关闭连接
        jedis.close();
    }

    public void JedisPoolTest() throws Exception{
        //创建连接池
        JedisPool pool = new JedisPool("10.1.1.179");
        //获得连接
        Jedis jedis = pool.getResource();
        jedis.set("b","为什么");
        String value = jedis.get("b");
        System.out.println(value);
        jedis.close();
        pool.close();
    }

    public void JedisClusterTest() throws Exception{

        Set<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("10.1.1.179",7001));
        hostAndPorts.add(new HostAndPort("10.1.1.179",7002));
        hostAndPorts.add(new HostAndPort("10.1.1.179",7003));
        hostAndPorts.add(new HostAndPort("10.1.1.179",7004));
        hostAndPorts.add(new HostAndPort("10.1.1.179",7005));
        hostAndPorts.add(new HostAndPort("10.1.1.179",7006));

        JedisCluster cluster = new JedisCluster(hostAndPorts);
        cluster.hset("key1","field1","why field1");
        String value = cluster.hget("key1","field1");
        System.out.println(value);

        cluster.close();
    }
}

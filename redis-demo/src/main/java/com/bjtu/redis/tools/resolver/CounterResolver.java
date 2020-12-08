package com.bjtu.redis.tools.resolver;

import com.bjtu.redis.tools.domain.CounterUnit;
import redis.clients.jedis.Jedis;

/**
 * @program: redis-demo
 * @description: counterResolver的父类，是一个抽象类，定义了jedis的连接和要执行的counter定义
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public abstract class CounterResolver {
    protected Jedis jedis;
    protected CounterUnit counter;


    public abstract String resolve();

    /**
     * @Title: updateCounter
     * @Description: 设定将要执行的相关counter的参数
     * @param counter: 将要执行的counter
     * @param jedis: redis连接
     * @return: void
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 15:44
     */
    public void updateCounter(CounterUnit counter,Jedis jedis){
        this.jedis=jedis;
        this.counter=counter;
    }
}

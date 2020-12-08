package com.bjtu.redis.tools.resolver;

import com.bjtu.redis.JedisInstance;
import com.bjtu.redis.tools.domain.CounterUnit;
import com.bjtu.redis.tools.resolver.typeResolver.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

/**
 * @program: redis-demo
 * @description: 工厂模式，返回对应类型的counterResolver
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class ResolverFactory {
    //存放相应resolver的map
    private HashMap<String, CounterResolver> resolverMap = new HashMap<>();
    //思考一个jedis是action级别的，一个action共用一个jedis
    private Jedis jedis = JedisInstance.getInstance().getResource();

    //private static ResolverFactory factory = new ResolverFactory();

        /**
         * @Title: ResolverFactory
         * @Description: ResolverFactory的构造器，将对应类型的counterResolver放入map中
         * @param : void
         * @return: void
         * @throw: void
         * @Author: codingLiOOT
         * @Date: 2020/12/8 15:47
         */
        public ResolverFactory() {
        resolverMap.put("num", new NumCounterResolver());
        resolverMap.put("string", new StringCounterResolver());
        resolverMap.put("zset", new ZsetCounterResolver());
        resolverMap.put("list", new ListCounterResolver());
        resolverMap.put("hash", new HashCounterResolver());
    }

    /**
     * @Title: getResolver
     * @Description: 对外暴露的获取counter更新后的resolver的方法，
     * @param type:  执行的counter类型
     * @param counter: 执行的counter
     * @return: com.bjtu.redis.tools.resolver.CounterResolver
     * @throw:
     * @Author: codingLiOOT
     * @Date: 2020/12/8 15:50
     */
    public CounterResolver getResolver(String type, CounterUnit counter) {
        resolverMap.get(type).updateCounter(counter, jedis);
        return resolverMap.get(type);
    }

    public void freeJedisResource(){
        jedis.close();
    }

}

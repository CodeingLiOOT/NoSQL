package com.bjtu.redis.tools.resolver.typeResolver;

import com.bjtu.redis.tools.resolver.CounterResolver;

/**
 * @program: redis-demo
 * @description: num类型的counter解决
 * 目前实现：1.num类型数据的查找和修改，以及超时时间的设定
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class NumCounterResolver extends CounterResolver {

    //Jedis jedis
    //CounterUnit counter


    @Override
    public String resolve() {

        StringBuilder res = new StringBuilder();
        String key = counter.getKeyFields();
        String condition = counter.getValueCondition();
        int expireTime = counter.getExpireTime();
        if (key != null) {
            if (jedis.exists(key)) {
                if (condition != null) {
                    //更改新值
                    jedis.incrBy(key, Long.parseLong(condition));
                    res.append(key).append(" increased by ").append(condition)
                            .append(", now is : ").append(jedis.get(key)).append("\n");
                } else {
                    //查询现有值
                    res.append(key).append(" now is : ").append(jedis.get(key)).append("\n");
                }
                if (expireTime != 0) {
                    //设置超时时间
                    jedis.expire(key, expireTime);
                    res.append(key).append("set new expire time : ").append(expireTime).append("\n");
                }
                res.append(key).append(" ttl is : ").append(jedis.ttl(key)).append("\n");
            } else {
                if (condition != null) {
                    if (expireTime != 0) {
                        //有超时参数设置新值
                        jedis.setex(key, expireTime, condition);
                        res.append("New key : ").append(key).append(", value : ").append(condition).append("\n");
                        res.append(key).append(" ttl is : ").append(jedis.ttl(key)).append("\n");
                    } else {
                        //无超时时间参数设置新值
                        jedis.set(key, condition);
                        res.append("New key : ").append(key).append(", value : ").append(condition).append("\n");
                    }
                } else {
                    //没有操作也没有redis中存在的key，无效查询操作
                    res.append("None key to operate\n");
                }
            }
        } else {
            //counter配置错误
            res.append("None effective operation\n");
        }
        return res.toString();
    }
}

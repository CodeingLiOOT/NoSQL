package com.bjtu.redis.tools.resolver.typeResolver;

import com.bjtu.redis.tools.resolver.CounterResolver;

/**
 * @program: redis-demo
 * @description: String类型的counter解决
 * 目前实现：1.通过设定的condition来更新当前key的值和查询当前key的值
 * 尚未实现体现String类型二进制安全的存储图片等信息
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class StringCounterResolver extends CounterResolver {


    @Override
    public String resolve() {
        StringBuilder res = new StringBuilder();
        String key = counter.getKeyFields();
        String condition = counter.getValueCondition();
        int expireTime = counter.getExpireTime();
        if (key != null) {
            if (jedis.exists(key)) {
                if (condition != null) {
                    //覆盖新值
                    jedis.set(key,condition);
                }
                res.append(key).append(" now is : ").append(jedis.get(key)).append("\n");
                if (expireTime != 0) {
                    //设置超时时间
                    jedis.expire(key, expireTime);
                    res.append(key).append("set new expire time : ").append(expireTime).append("\n");
                }
                res.append(key).append(" ttl is : ").append(jedis.ttl(key)).append("\n");
            } else {
                if (condition != null) {
                    if (expireTime != 0) {
                        //创建新值并设定超时
                        jedis.setex(key, expireTime, condition);
                        res.append("New key : ").append(key).append(", value : ").append(condition).append("\n");
                        res.append(key).append(" ttl is : ").append(jedis.ttl(key)).append("\n");
                    } else {
                        //创建新值
                        jedis.set(key, condition);
                        res.append("New key : ").append(key).append(", value : ").append(condition).append("\n");
                    }
                } else {
                    //没有有效的可查询的值
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


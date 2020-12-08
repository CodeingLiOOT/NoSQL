package com.bjtu.redis.tools.resolver.typeResolver;

import com.bjtu.redis.tools.domain.LimitUnit;
import com.bjtu.redis.tools.resolver.CounterResolver;

import java.util.UUID;

/**
 * @program: redis-demo
 * @description: List类型的counter解决，目前简单实现了令牌桶算法
 * 目前实现：1.根据condition判断是需要获取令牌的请求还是放入令牌的定时任务及令牌桶的相关实现 2.采用UUID作为token的唯一标识符
 * 尚未实现利用BLPOP命令的阻塞等待令牌操作，只是简单如果没有令牌就拒掉请求
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class ListCounterResolver extends CounterResolver {


    /**
     * @param : void
     * @Title: resolve
     * @Description: 分辨是普通的List操作还是带限流的操作分别执行不同的resolve
     * @return: java.lang.String
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 15:00
     */
    @Override
    public String resolve() {
        LimitUnit limit = counter.getLimit();
        if (limit != null) {
            return limitResolve(limit);
        } else {
            return normalResolve();
        }
    }

    private String limitResolve(LimitUnit limit) {
        String key = counter.getKeyFields();
        String Condition = counter.getValueCondition();
        int expireTime = counter.getExpireTime();
        if (key != null) {
            if (Condition != null) {
                int condition = Integer.parseInt(Condition);
                if (condition > 0) {
                    //condition大于0，认定为放令牌操作，condition认定为需要放几个令牌
                    for (int i = 0; i < condition; i++) {
                        if (jedis.llen(key) < limit.getMaxSizeOfToken()) {
                            //采用UUID作为token的唯一标识符，后续可以通过UUID来检验令牌的合法性
                            jedis.rpush(key, String.valueOf(UUID.randomUUID()));
                            //放令牌时如果有超时时间参数进行设置，认定应用场景为长期没有令牌放入就认定该项任务取消
                            if (expireTime != 0) {
                                jedis.expire(key, expireTime);
                            }
                            System.out.println("put a token to bucket!");
                        }
                    }
                } else if (condition < 0) {
                    //condition小于0，认定为取令牌的操作，condition认定为需要取几个令牌
                    for (int i = 0; i > condition; i--) {
                        Object token = jedis.lpop(key);
                        if (token != null) {
                            System.out.println("request : get token!");
                        } else {
                            System.out.println("request : No token!");
                        }
                    }
                } else {
                    //如果condition为0，认为为查询操作
                    System.out.println("now the num of token is : " + jedis.llen(key));
                }
            } else {
                //如果condition为空，没有操作，默认认为查找令牌桶中令牌剩余个数
                System.out.println("now the num of token is : " + jedis.llen(key));
            }
        } else {
            //如果counter配置的key为空，认定counter配置错误，不执行
            System.out.println("None effective operation");
        }
        return "";
    }

    private String normalResolve() {
        return null;
    }
}

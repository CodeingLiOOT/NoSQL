package com.bjtu.redis.tools.resolver.typeResolver;

import com.bjtu.redis.tools.domain.LimitUnit;
import com.bjtu.redis.tools.resolver.CounterResolver;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.UUID;

/**
 * @program: redis-demo
 * @description: zset类型的counter解决
 * 目前实现：1.zset滑动窗口的简单限流
 * 尚未实现：zset的普通相关操作
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class ZsetCounterResolver extends CounterResolver {

    /**
     * @Title: resolve
     * @Description: 根据是否有limit相关配置来进行对应的限流和普通操作
     * @param : void
     * @return: java.lang.String
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 15:33
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
        long nowTime = System.nanoTime() / 1000000L;//ms级不准确

        /*
         *   以下注释部分应用场景为：当用户打出一个请求，然后需要进行限流操作，检测到已经达到限流的峰值
         *   就将请求加入到等待队列中（本地未实现），所以先将所有打来的请求加入到Zset里再进行判断，所有请求都会被执行
         *   本地目前只能存储窗口内的请求如果该窗口内请求都无法执行将会被移除，未实现加入等待队列功能
         */

          //pipeline对于同一个key的多次操作性能较好
//        Pipeline pipeline = jedis.pipelined();
          //保证下列操作一起批量执行
//        pipeline.multi();
          //首先加入当前请求，然后移除窗口外的所有数据，获取窗口内数据数量并设置超时时间
//        pipeline.zadd(key, nowTime, String.valueOf(UUID.randomUUID()));
//        pipeline.zremrangeByScore(key, 0, nowTime - limit.getIntervalTimeOfWindow() * 1000);
//        Response<Long> cnt = pipeline.zcard(key);
//        pipeline.expire(key, limit.getIntervalTimeOfWindow());
//        pipeline.exec();
//        pipeline.close();
          //返回请求是否被执行
//        System.out.println("Request resolve : " + (cnt.get() <= limit.getMaxSizeOfReq()));
//        return "";


        /*
         *   以下部分应用场景为，用户打来请求后，先判断是否达到限流的阈值，如果已经达到，则请求直接拒绝掉，只有请求打来的时候
         *   滑动窗口内key数量不超过峰值，请求才会被执行
         */

        //zcard（）命令返回的response<Long>必须在pipeline和multi代码段外执行（查看response<Long>.get()源代码可知）
        jedis.zremrangeByScore(key, 0, nowTime - limit.getIntervalTimeOfWindow() * 1000);
        Long cnt = jedis.zcard(key);

        //System.out.println(cnt.get());
        if ( cnt < limit.getMaxSizeOfReq()) {
            jedis.zadd(key, nowTime, String.valueOf(UUID.randomUUID()));
            if(counter.getExpireTime()!=0){
                jedis.expire(key,counter.getExpireTime());
            }
            System.out.println("Request resolve : " + true);
        } else {
            System.out.println("Request resolve : " + false);
        }
        return "";


    }

    private String normalResolve() {
        return null;
    }

}

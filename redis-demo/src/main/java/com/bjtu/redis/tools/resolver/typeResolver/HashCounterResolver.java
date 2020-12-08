package com.bjtu.redis.tools.resolver.typeResolver;

import com.bjtu.redis.tools.resolver.CounterResolver;
import com.bjtu.redis.tools.utils.DateSplitUtils;

import java.util.Date;
import java.util.List;

/**
 * @program: redis-demo
 * @description: Hash类型的Counter，实现了Freq的功能，可以实现分钟级的统计，
 * 目前实现功能：1.在给定时间执行一次操作 2.统计一段时间区间内操作次数 3.未给定操作条件时默认统计全部时间的操作次数
 * 尚未实现根据传入参数来动态维护多个hash，即月级的hash中仅存放每天的数量，每天的hash存放每小时，每小时的hash存放分钟
 * 通过参数来设置过期时间，当过期时间结束后自动存放至上一级hash中的功能
 * 思考：超时时间和设定值的操作不在一起，并不是同时操作，若请求量巨大的情况下，会存在一定误差（但认为统计操作对超时要求不精确所以未修改）
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class HashCounterResolver extends CounterResolver {

    /**
     * @Title: resolve
     * @Description: Freq的counter解决
     * @param : void
     * @return: java.lang.String
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 14:57
     */
    @Override
    public String resolve() {
        String key = counter.getKeyFields();
        String Condition = counter.getValueCondition();
        int expireTime = counter.getExpireTime();
        String incrValue = counter.getValue();
        if (key != null) {
            if (Condition != null) {
                List<Date> dates = DateSplitUtils.dateFormat(Condition);
                if (dates.size() == 1) {
                    //执行请求而不是统计
                    Long filed = DateSplitUtils.dateKey(dates.get(0));
                    if (incrValue != null) {
                        //如果key不存在则会自动生成并执行操作，所以此处不需要执行判断key是否存在的操作
                        jedis.hincrBy(key, String.valueOf(filed), Long.parseLong(incrValue));
                        System.out.print("the " + Condition + " access incr by " + incrValue);
                        //如果有超时时间参数，就设置或更新该超时时间，目前仅实现对整个hash的统计
                        //假定应用场景为当expireTime时间内没有用户操作就清空hash，等下次操作之后开始重新统计
                        if (expireTime != 0) {
                            jedis.expire(key, expireTime);
                            System.out.println(" new expire time : " + expireTime);
                            return "";
                        }
                    } else {
                        //给定单个时间却未给定操作次数，默认该次操作无效
                        System.out.println("no effective operation");
                        return "";
                    }
                } else if (dates.size() == 2) {
                    //统计该时间区间内操作次数
                    //如果key不存在则会返回一个null，默认认为操作次数为0，故不执行判断key是否存在操作
                    List<Long> fileds = DateSplitUtils.dateKeys(dates);
                    long sum = 0;
                    for (Long filed : fileds) {
                        String fieldValue = jedis.hget(key, String.valueOf(filed));
                        if (fieldValue == null) {
                            sum += 0;
                        } else {
                            sum = sum + Long.parseLong(fieldValue);
                        }
                    }
                    System.out.println("between " + dates.get(0) + " and " + dates.get(1) + " access num is : " + sum);
                } else {
                    //传入了错误的时间参数（目前尚未实现对多个区间或者多个时间执行操作的多参数操作）
                    System.out.println("no effective operation");
                }
            } else {
                //counter中未有所需统计或者执行的操作，默认为统计全部操作次数
                List<String> values = jedis.hvals(key);
                long sum = 0;
                for (String value : values) {
                    if (value == null) {
                        sum += 0;
                    } else {
                        sum = sum + Long.parseLong(value);
                    }

                }
                System.out.println("all time access num is : " + sum);
                return "";
            }
        } else {
            //传来的counter不存在key，说明counter配置错误，打印错误信息
            System.out.println("None effective operation");
        }
        return "";
    }
}

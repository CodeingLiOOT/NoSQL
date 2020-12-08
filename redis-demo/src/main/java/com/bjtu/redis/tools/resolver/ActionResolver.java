package com.bjtu.redis.tools.resolver;

import com.bjtu.redis.tools.domain.ActionUnit;
import com.bjtu.redis.tools.domain.CounterUnit;
import com.bjtu.redis.tools.utils.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: redis-demo
 * @description: Action的解决，将action挂钩的所有counter加入到一个列表里顺序执行
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class ActionResolver {
    public static String resolve(ActionUnit action){
        List<CounterUnit> counters=new ArrayList<>();
        ResolverFactory factory=new ResolverFactory();
        StringBuilder res= new StringBuilder();
        action.getShowCounter().forEach(counterConfig -> {
            counters.add(ConfigUtils.getCounter(counterConfig.getCounterName()));
        });
        action.getOperateCounter().forEach(counterConfig -> {
            counters.add(ConfigUtils.getCounter(counterConfig.getCounterName()));
        });
        for (int i = 0; i < counters.size(); i++) {
            System.out.println("the "+(i+1)+" counter is resolving");
            //获取工厂返回的counter的执行器并调用解决方法
            res.append(factory.getResolver(counters.get(i).getType(), counters.get(i)).resolve())
                    .append("\n");
        }
        factory.freeJedisResource();
        return res.toString();
    }
}

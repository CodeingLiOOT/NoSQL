package com.bjtu.redis.tools.domain;

/**
 * @program: redis-demo
 * @description: 连接action和counter的类，存储了counter的名字
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class CounterConfig {
    private String CounterName;

    public String getCounterName() {
        return CounterName;
    }

    public void setCounterName(String counterName) {
        CounterName = counterName;
    }

    @Override
    public String toString() {
        return "CounterConfig{" +
                "CounterName='" + CounterName + '\'' +
                '}';
    }
}

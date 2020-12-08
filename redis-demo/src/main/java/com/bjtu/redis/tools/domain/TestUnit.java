package com.bjtu.redis.tools.domain;

import java.util.List;

/**
 * @program: redis-demo
 * @description: Test的定义类，Test是指我重复执行一或多次的一个或多个Action，方便进行限流和短期大量请求
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class TestUnit {

    private int ID;
    private String name;
    //执行次数
    private int times;
    //两次执行action的相隔时间
    private int intervalTime;
    //存放要执行的action
    private List<ActionConfig> action;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public List<ActionConfig> getAction() {
        return action;
    }

    public void setAction(List<ActionConfig> action) {
        this.action = action;
    }
}

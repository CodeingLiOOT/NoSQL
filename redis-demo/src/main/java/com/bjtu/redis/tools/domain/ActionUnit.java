package com.bjtu.redis.tools.domain;

import java.util.List;

/**
 * @program: redis-demo
 * @description: Action的定义类
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class ActionUnit {


    private int ID;
    private String name;
    private String description;

    //要执行的counter
    private List<CounterConfig> OperateCounter;
    private List<CounterConfig> showCounter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CounterConfig> getOperateCounter() {
        return OperateCounter;
    }

    public void setOperateCounter(List<CounterConfig> operateCounter) {
        OperateCounter = operateCounter;
    }

    public List<CounterConfig> getShowCounter() {
        return showCounter;
    }

    public void setShowCounter(List<CounterConfig> showCounter) {
        this.showCounter = showCounter;
    }

    @Override
    public String toString() {
        return "ActionUnit{" +
                "ID=" + ID +
                ", description='" + description + '\'' +
                ", OperateCounter=" + OperateCounter +
                ", showCounter=" + showCounter +
                '}';
    }

}

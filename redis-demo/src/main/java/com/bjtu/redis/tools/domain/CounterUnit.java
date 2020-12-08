package com.bjtu.redis.tools.domain;

/**
 * @program: redis-demo
 * @description: counter的定义类
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class CounterUnit {
    private int ID;
    private String name;
    private String description;
    private String keyFields;
    private String type;
    private String value;
    //对key得操作
    private String valueCondition;
    //超时时间
    private int expireTime;
    //是否有限流操作
    private LimitUnit limit;

    public LimitUnit getLimit() {
        return limit;
    }

    public void setLimit(LimitUnit limit) {
        this.limit = limit;
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

    public String getKeyFields() {
        return keyFields;
    }

    public void setKeyFields(String keyFields) {
        this.keyFields = keyFields;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueCondition() {
        return valueCondition;
    }

    public void setValueCondition(String valueCondition) {
        this.valueCondition = valueCondition;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "CounterUnit{" +
                "ID=" + ID +
                ", description='" + description + '\'' +
                ", keyFields='" + keyFields + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", valueCondition='" + valueCondition + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

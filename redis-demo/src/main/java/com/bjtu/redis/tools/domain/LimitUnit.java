package com.bjtu.redis.tools.domain;

/**
 * @program: redis-demo
 * @description: 限流单元的相关定义
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class LimitUnit {
    //限流算法类型，包括SlideWindow滑动窗口和TokenBucket令牌桶算法
    private String type;

    //滑动窗口的相关定义，包括滑动窗口的时间长度和一个窗口内最大请求数量
    //滑动窗口适用于极短时间内的限流，如果为60s100w次限流则不适用，会耗费大量资源
    private int intervalTimeOfWindow;
    private int maxSizeOfReq;

    //令牌桶的相关定义，包括令牌桶内令牌的最大数量和向令牌桶放Token的间隔时间
    //令牌桶相比于漏斗算法之类的算法的优势在于令牌桶内可以累计存储桶，短期内大量请求的要求可以满足
    //但漏斗算法则不行，处理请求的速率是固定的
    private int maxSizeOfToken;
    private int intervalTimeOfToken;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIntervalTimeOfWindow() {
        return intervalTimeOfWindow;
    }

    public void setIntervalTimeOfWindow(int intervalTimeOfWindow) {
        this.intervalTimeOfWindow = intervalTimeOfWindow;
    }

    public int getMaxSizeOfReq() {
        return maxSizeOfReq;
    }

    public void setMaxSizeOfReq(int maxSizeOfReq) {
        this.maxSizeOfReq = maxSizeOfReq;
    }

    public int getMaxSizeOfToken() {
        return maxSizeOfToken;
    }

    public void setMaxSizeOfToken(int maxSizeOfToken) {
        this.maxSizeOfToken = maxSizeOfToken;
    }

    public int getIntervalTimeOfToken() {
        return intervalTimeOfToken;
    }

    public void setIntervalTimeOfToken(int intervalTimeOfToken) {
        this.intervalTimeOfToken = intervalTimeOfToken;
    }
}

package com.bjtu.redis.tools.utils;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: redis-demo
 * @description: 读写锁配置
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class LockUtils {
    //声明一个读写锁
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //获得读锁
    public static void getReadLock() {
        readWriteLock.readLock().lock();
    }
    //释放读锁
    public static void freeReadLock() {
        readWriteLock.readLock().unlock();
    }
    //获得写锁
    public static void getWriteLock() {
        readWriteLock.writeLock().lock();
    }
    //释放写锁
    public static void freeWriteLock() {
        readWriteLock.writeLock().unlock();
    }
}

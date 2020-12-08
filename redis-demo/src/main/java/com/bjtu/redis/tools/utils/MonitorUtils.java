package com.bjtu.redis.tools.utils;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;

/**
 * @program: redis-demo
 * @description: 文件监听工具类，当监听到文件改变后执行相应操作
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class MonitorUtils {
    //要监听的文件夹
    private static final File directory = new File(new File("."), "src/main/resources");
    //新建对应文件夹的监听observer
    private static FileAlterationObserver observer = new FileAlterationObserver(directory);
    //设定轮询间隔时间
    private static final long interval = 1000;
    //新建monitor
    private static FileAlterationMonitor monitor = new FileAlterationMonitor(interval);


    /**
     * @Title: fileMonitoring
     * @Description: 文件监听
     * @param : void
     * @return: void
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 16:43
     */
    public static void fileMonitoring() throws Exception {

        //使用fileAlterationListenerAdaptor实现对onFileChange的监听
        observer.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(File file) {
                super.onFileChange(file);
                System.out.println("File changed");
                //获得写锁
                LockUtils.getWriteLock();
                System.out.println("get write lock");
                try {
                    //更新配置
                    ConfigUtils.loadConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //释放写锁
                    LockUtils.freeWriteLock();
                    System.out.println("free write lock");
                }
            }
        });
        monitor.addObserver(observer);
        monitor.start();
    }
}

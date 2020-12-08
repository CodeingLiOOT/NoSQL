package com.bjtu.redis.tools.resolver;

import com.bjtu.redis.tools.domain.ActionUnit;
import com.bjtu.redis.tools.domain.TestUnit;
import com.bjtu.redis.tools.utils.ConfigUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis-demo
 * @description: test的解决，将test挂钩的action按照给定参数设定好的定时任务顺序执行
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class TestResolver {

    /**
     * @Title: resolve
     * @Description: test的解决，首先将action加入列表，送入定时任务，并开启新线程定时结束任务
     * @param test: 要执行的test
     * @return: java.lang.String
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 15:53
     */
    public static String resolve(TestUnit test) {
        StringBuffer buffer = new StringBuffer();
        List<ActionUnit> action = new ArrayList<>();
        test.getAction().forEach(actionConfig -> {
            action.add(ConfigUtils.getAction(actionConfig.getActionName()));
        });

        //设置定时任务
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        //三个参数分别为要定时执行的线程，第一次间隔，和后续两次任务的间隔时间，时间间隔的时间单位
        ScheduledFuture future = service.scheduleAtFixedRate(() -> {
                    //加try catch是防止某次定时任务出现异常后直接取消后续所有任务
                    try {
                        action.forEach(ActionResolver::resolve);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 0, test.getIntervalTime(),
                TimeUnit.SECONDS);

        //启动新线程来定时取消任务
        //作用为可以在终端通过输入来同时进行多个test，主要测试令牌桶算法
        //操作方法为先后执行test的第二三个
        //未对输出进行管理，所以可能提示用户输入的和test执行的输出会混在一起
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep((test.getTimes() + 1) * test.getIntervalTime());
                System.out.println("task complete");
                future.cancel(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return buffer.toString();
    }
}

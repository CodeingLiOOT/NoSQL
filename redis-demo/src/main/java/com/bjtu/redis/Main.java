package com.bjtu.redis;

import com.bjtu.redis.tools.domain.ActionUnit;
import com.bjtu.redis.tools.domain.TestUnit;
import com.bjtu.redis.tools.resolver.ActionResolver;
import com.bjtu.redis.tools.resolver.TestResolver;
import com.bjtu.redis.tools.utils.ConfigUtils;
import com.bjtu.redis.tools.utils.LockUtils;
import com.bjtu.redis.tools.utils.MonitorUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @program: redis-demo
 * @description: 加载json配置，并返回配置的相关属性
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class Main {
    /**终端输出*/
    private static final PrintWriter out = new PrintWriter(System.out);
    /**终端输入*/
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @Title: getFlag
     * @Description: 获取用户输入，并执行对应的操作
     * @param: void
     * @return: int 用户输入
     * @throw:  IOException
     * @Author: codingLiOOT
     * @Date: 2020/12/8 13:05
     */
    private static int getFlag() throws IOException {
        int flag = -1;
        out.println("Redis Counter  \n"
                + "1. 显示Action \n"
                + "2.执行Action \n"
                + "3.显示Test \n"
                + "4.执行Test \n"
                + "0. 退出Counter");
        out.flush();

        //判断用户输入，如果非法输入就返回-1
        try {
            flag = Integer.parseInt(in.readLine());
        } catch (NumberFormatException e) {
            out.println("Error Input");
            out.println();
            out.flush();
        }
        return flag;
    }

    /**
     * @Title: showAllAction
     * @Description: 打印所有可执行的Action
     * @param : void
     * @return: void
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 14:14
     */
    private static void showAllAction() {
        out.println("All Action is : ");
        for (int i = 0; i < ConfigUtils.getActionNum(); i++) {
            out.println(i + 1 + ".  " + ConfigUtils.getActionName(i));
        }
        out.println();
        out.flush();
    }

    /**
     * @Title: resolveAction
     * @Description: 获取用户输入的将要执行的Action并送入ActionResolver中进行执行
     * @param : void
     * @return: void
     * @throw: IOException
     * @Author: codingLiOOT
     * @Date: 2020/12/8 14:35
     */
    private static void resolveAction() throws IOException {
        int flag = -1;
        do {
            try {
                out.println("please input action index(0 to exit):  ");
                out.flush();
                flag = Integer.parseInt(in.readLine());
                if (flag == 0) {
                    return;
                } else if (flag <= ConfigUtils.getActionNum() && flag > 0) {
                    break;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                out.println("Error Input");
                out.println();
                out.flush();
            }
        } while (true);

        ActionUnit action = ConfigUtils.getAction(ConfigUtils.getActionName(flag - 1));
        out.println(action.getName() + "is resolving!\n");
        out.flush();

        out.print(ActionResolver.resolve(action));
        out.flush();

    }

    /**
     * @Title: resolveTest
     * @Description: 获取用户输入的将要执行的Test并送入TestResolver中执行
     * @param : void
     * @return: void
     * @throw:
     * @Author: codingLiOOT
     * @Date: 2020/12/8 14:37
     */
    private static void resolveTest() {
        int flag = -1;
        do {
            try {
                out.println("please input test index(0 to exit):  ");
                out.flush();
                flag = Integer.parseInt(in.readLine());
                if (flag == 0) {
                    return;
                } else if (flag <= ConfigUtils.getTestNum() && flag > 0) {
                    break;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException | IOException e) {
                out.println("Error Input");
                out.println();
                out.flush();
            }
        } while (true);

        TestUnit testUnit = ConfigUtils.getTest(ConfigUtils.getTestName(flag - 1));
        out.println(testUnit.getName() + "is resolving!\n");
        out.flush();

        out.println(TestResolver.resolve(testUnit));
        out.flush();

    }

    /**
     * @Title: showAllTest
     * @Description: 展示所有可执行的Test
     * @param : void
     * @return: void
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 14:37
     */
    private static void showAllTest() {
        out.println("All Test is : ");
        for (int i = 0; i < ConfigUtils.getTestNum(); i++) {
            out.println(i + 1 + ".  " + ConfigUtils.getTestName(i));
        }
        out.println();
        out.flush();
    }


    public static void main(String[] args) throws Exception {
        MonitorUtils.fileMonitoring();
        ConfigUtils.loadConfig();

        //用户输入选择
        int flag;
        do {
            //获取读锁
            LockUtils.getReadLock();
            System.out.println("get read lock");
            flag = getFlag();
            try {
                switch (flag) {
                    case 0:
                        LockUtils.freeReadLock();
                        System.out.println("free read lock\n");
                        System.exit(0);
                    case 1:
                        showAllAction();
                        break;
                    case 2:
                        showAllAction();
                        resolveAction();
                        break;
                    case 3:
                        showAllTest();
                        break;
                    case 4:
                        showAllTest();
                        resolveTest();
                        break;
                    default:
                }
            } finally {
                //释放读锁，放在finally语句保证不论是否正常进行下去都可以成功释放锁
                LockUtils.freeReadLock();
                System.out.println("free read lock\n");
            }
        } while (true);
    }


}

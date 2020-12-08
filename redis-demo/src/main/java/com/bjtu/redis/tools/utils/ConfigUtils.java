package com.bjtu.redis.tools.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjtu.redis.tools.domain.ActionUnit;
import com.bjtu.redis.tools.domain.CounterUnit;
import com.bjtu.redis.tools.domain.TestUnit;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: redis-demo
 * @description: 加载json配置，并返回配置的相关属性
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class ConfigUtils {

    //action map and action name list
    private static HashMap<String, ActionUnit> actionMap = new HashMap<>();
    private static List<String> actionNameList = new ArrayList<>();
    //counter map
    private static HashMap<String, CounterUnit> counterMap = new HashMap<>();

    //test map and test name list
    private static HashMap<String, TestUnit> testMap = new HashMap<>();
    private static List<String> testNameList = new ArrayList<>();

    /**
     * @Title: loadCounterJson
     * @Description: 加载counter json
     * @param : void
     * @return: void
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 16:06
     */
    protected static void loadCounterJson() throws IOException {
        String counterPath = "src/main/resources/counter.json";
        counterMap.clear();
        String content = FileUtils.readFileToString(new File(counterPath), StandardCharsets.UTF_8);
        JSONObject counter = JSON.parseObject(content);
        JSONArray array = counter.getJSONArray("Counter");

        for (int i = 0; i < array.size(); i++) {
            CounterUnit counterUnit = array.getJSONObject(i).toJavaObject(CounterUnit.class);
            counterMap.put(counterUnit.getName(), counterUnit);
        }
    }

    /**
     * @Title: loadCounterJson
     * @Description: 加载action json
     * @param : void
     * @return: void
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 16:06
     */
    protected static void loadActionJson() throws IOException {
        String actionPath = "src/main/resources/action.json";
        actionNameList.clear();
        actionMap.clear();
        String content = FileUtils.readFileToString(new File(actionPath), StandardCharsets.UTF_8);
        JSONObject action = JSON.parseObject(content);
        JSONArray array = action.getJSONArray("Action");
        for (int i = 0; i < array.size(); i++) {
            ActionUnit actionUnit = array.getJSONObject(i).toJavaObject(ActionUnit.class);
            actionMap.put(actionUnit.getName(), actionUnit);
            actionNameList.add(actionUnit.getName());
        }
    }

    /**
     * @Title: loadCounterJson
     * @Description: 加载test json
     * @param : void
     * @return: void
     * @throw: void
     * @Author: codingLiOOT
     * @Date: 2020/12/8 16:06
     */
    protected static void loadTestJson() throws IOException {
        String actionPath = "src/main/resources/testManyTimes.json";
        testNameList.clear();
        testMap.clear();
        String content = FileUtils.readFileToString(new File(actionPath), StandardCharsets.UTF_8);
        JSONObject action = JSON.parseObject(content);
        JSONArray array = action.getJSONArray("Test");
        for (int i = 0; i < array.size(); i++) {
            TestUnit testUnit = array.getJSONObject(i).toJavaObject(TestUnit.class);
            testMap.put(testUnit.getName(), testUnit);
            testNameList.add(testUnit.getName());
        }
    }

    public static void loadConfig() throws IOException {
        loadCounterJson();
        loadActionJson();
        loadTestJson();
    }

    //获取action，counter，test的对应对象，名字和列表大小
    public static ActionUnit getAction(String name) {
        return actionMap.get(name);
    }

    public static CounterUnit getCounter(String name) {
        return counterMap.get(name);
    }

    public static String getActionName(int index) {
        return actionNameList.get(index);
    }

    public static int getActionNum() {
        return actionNameList.size();
    }

    public static TestUnit getTest(String name) {
        return testMap.get(name);
    }

    public static String getTestName(int index) {
        return testNameList.get(index);
    }

    public static int getTestNum() {
        return testNameList.size();
    }
}

package com.bjtu.redis.tools.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: redis-demo
 * @description: 加载json配置，并返回配置的相关属性
 * reference : https://blog.csdn.net/qq_45534015/article/details/106799730
 * 针对需求进行相应封装
 * dateFormat()返回对应解析成所需格式的list
 * dateKeys()返回时间切片生成的key，默认为分钟级
 * dateKey()返回单个时间所对应的key
 * @author: CodingLiOOT
 * @create: 2020-12-08 12:54
 * @version: 1.0
 **/
public class DateSplitUtils {

    public enum IntervalType {
        DAY,
        HOUR,
        MINUTE,
        SECOND,
        ;
    }

    /**
     * 时间切割
     *
     * @param startTime    被切割的开始时间
     * @param endTime      被切割的结束时间
     * @param intervalType
     * @param interval     >0
     * @return
     */
    public static List<DateSplit> splitDate(Date startTime, Date endTime, IntervalType intervalType, int interval) {
        if (interval < 0) {
            return null;
        }
        if (endTime.getTime() <= startTime.getTime()) {
            return null;
        }

        if (intervalType == IntervalType.DAY) {
            return splitByDay(startTime, endTime, interval);
        }
        if (intervalType == IntervalType.HOUR) {
            return splitByHour(startTime, endTime, interval);
        }
        if (intervalType == IntervalType.MINUTE) {
            return splitByMinute(startTime, endTime, interval);
        }
        if (intervalType == IntervalType.SECOND) {
            return splitBySecond(startTime, endTime, interval);
        }
        return null;
    }

    /**
     * 按照小时切割时间区间
     */
    public static List<DateSplit> splitByHour(Date startTime, Date endTime, int intervalHours) {
        if (endTime.getTime() <= startTime.getTime()) {
            return null;
        }

        List<DateSplit> dateSplits = new ArrayList<>(256);

        DateSplit param = new DateSplit();
        param.setStartDateTime(startTime);
        param.setEndDateTime(endTime);
        param.setEndDateTime(addHours(startTime, intervalHours));
        while (true) {
            param.setStartDateTime(startTime);
            Date tempEndTime = addHours(startTime, intervalHours);
            if (tempEndTime.getTime() >= endTime.getTime()) {
                tempEndTime = endTime;
            }
            param.setEndDateTime(tempEndTime);

            dateSplits.add(new DateSplit(param.getStartDateTime(), param.getEndDateTime()));

            startTime = addHours(startTime, intervalHours);
            if (startTime.getTime() >= endTime.getTime()) {
                break;
            }
            if (param.getEndDateTime().getTime() >= endTime.getTime()) {
                break;
            }
        }
        return dateSplits;
    }

    /**
     * 按照秒切割时间区间
     */
    public static List<DateSplit> splitBySecond(Date startTime, Date endTime, int intervalSeconds) {
        if (endTime.getTime() <= startTime.getTime()) {
            return null;
        }
        List<DateSplit> dateSplits = new ArrayList<>(256);

        DateSplit param = new DateSplit();
        param.setStartDateTime(startTime);
        param.setEndDateTime(endTime);
        param.setEndDateTime(addSeconds(startTime, intervalSeconds));
        while (true) {
            param.setStartDateTime(startTime);
            Date tempEndTime = addSeconds(startTime, intervalSeconds);
            if (tempEndTime.getTime() >= endTime.getTime()) {
                tempEndTime = endTime;
            }
            param.setEndDateTime(tempEndTime);

            dateSplits.add(new DateSplit(param.getStartDateTime(), param.getEndDateTime()));

            startTime = addSeconds(startTime, intervalSeconds);
            if (startTime.getTime() >= endTime.getTime()) {
                break;
            }
            if (param.getEndDateTime().getTime() >= endTime.getTime()) {
                break;
            }
        }
        return dateSplits;
    }

    /**
     * 按照天切割时间区间
     */
    public static List<DateSplit> splitByDay(Date startTime, Date endTime, int intervalDays) {
        if (endTime.getTime() <= startTime.getTime()) {
            return null;
        }
        List<DateSplit> dateSplits = new ArrayList<>(256);

        DateSplit param = new DateSplit();
        param.setStartDateTime(startTime);
        param.setEndDateTime(endTime);
        param.setEndDateTime(addDays(startTime, intervalDays));
        while (true) {
            param.setStartDateTime(startTime);
            Date tempEndTime = addDays(startTime, intervalDays);
            if (tempEndTime.getTime() >= endTime.getTime()) {
                tempEndTime = endTime;
            }
            param.setEndDateTime(tempEndTime);

            dateSplits.add(new DateSplit(param.getStartDateTime(), param.getEndDateTime()));

            startTime = addDays(startTime, intervalDays);
            if (startTime.getTime() >= endTime.getTime()) {
                break;
            }
            if (param.getEndDateTime().getTime() >= endTime.getTime()) {
                break;
            }
        }
        return dateSplits;
    }

    /**
     * 按照分钟切割时间区间
     *
     * @param startTime
     * @param endTime
     * @param intervalMinutes
     * @return
     */
    public static List<DateSplit> splitByMinute(Date startTime, Date endTime, int intervalMinutes) {
        if (endTime.getTime() <= startTime.getTime()) {
            return null;
        }
        List<DateSplit> dateSplits = new ArrayList<>(256);

        DateSplit param = new DateSplit();
        param.setStartDateTime(startTime);
        param.setEndDateTime(endTime);
        param.setEndDateTime(addMinute(startTime, intervalMinutes));
        while (true) {
            param.setStartDateTime(startTime);
            Date tempEndTime = addMinute(startTime, intervalMinutes);
            if (tempEndTime.getTime() >= endTime.getTime()) {
                tempEndTime = endTime;
            }
            param.setEndDateTime(tempEndTime);

            dateSplits.add(new DateSplit(param.getStartDateTime(), param.getEndDateTime()));

            startTime = addMinute(startTime, intervalMinutes);
            if (startTime.getTime() >= endTime.getTime()) {
                break;
            }
            if (param.getEndDateTime().getTime() >= endTime.getTime()) {
                break;
            }
        }
        return dateSplits;
    }


    private static Date addDays(Date date, int days) {
        return add(date, Calendar.DAY_OF_MONTH, days);
    }

    private static Date addHours(Date date, int hours) {
        return add(date, Calendar.HOUR_OF_DAY, hours);
    }

    private static Date addMinute(Date date, int minute) {
        return add(date, Calendar.MINUTE, minute);
    }

    private static Date addSeconds(Date date, int second) {
        return add(date, Calendar.SECOND, second);
    }

    private static Date add(final Date date, final int calendarField, final int amount) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    private static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DateSplit {
        private Date startDateTime;
        private Date endDateTime;

        public String getStartDateTimeStr() {
            return formatDateTime(startDateTime);
        }

        public String getEndDateTimeStr() {
            return formatDateTime(endDateTime);
        }
    }


    /**
     * @Title: dateFormat
     * @Description: 通过对传入字符串进行处理，从而返回一个标准格式的Date类型
     * @param time: 需要处理的字符串格式为“yyyyMMddHHmm”即“202012071845”
     * @return java.util.List<java.util.Date> (Date类型列表)
     * @Author: codingLiOOT
     * @Date: 2020/12/8
     */
    public static List<Date> dateFormat(String time) {
        String[] times = time.split("\\s+");
        List<Date> dates = new ArrayList<>();
        SimpleDateFormat strToDate = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat dateToStr = new SimpleDateFormat("yyyyMMddHH00");
        for (int i = 0; i < times.length; i++) {
            try {
                Date date = strToDate.parse(times[i]);
                String dateString = dateToStr.format(date);
                dates.add(strToDate.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    /**
     * @Title: dateKey
     * @Description: 处理传入的Date类型数据，返回其所对应得hash的field
     * @param date: 待处理的Date类型
     * @return java.lang.Long
     * @Author: codingLiOOT
     * @Date: 2020/12/8
     */
    public static Long dateKey(Date date) {
        return date.getTime();
    }

    /**
     * @Title: dateKeys
     * @Description: 处理传入的起止时间，返回按分钟切片的hash的field列表
     * @param dates: 传入的起止时间，类型为Date
     * @return java.util.List<java.lang.Long>
     * @Author: codingLiOOT
     * @Date: 2020/12/8 12:44
     */
    public static List<Long> dateKeys(List<Date> dates) {
        List<DateSplitUtils.DateSplit> dateSplits = DateSplitUtils.splitDate(dates.get(0), dates.get(1), IntervalType.MINUTE, 1);
        List<Long> keys = new ArrayList<>();
        for (DateSplitUtils.DateSplit dateSplit : dateSplits) {
            keys.add(dateSplit.getStartDateTime().getTime());
            //System.out.println("切割后的时间区间: " + dateSplit.getStartDateTimeStr() + " --->  " +  dateSplit.getEndDateTimeStr());
        }
        return keys;
    }


    public static void main(String[] args) {
        String time = "202012071650 202012072300";
        List<Date> dates = dateFormat(time);

        List<DateSplitUtils.DateSplit> dateSplits = DateSplitUtils.splitDate(dates.get(0), dates.get(1), DateSplitUtils.IntervalType.HOUR, 2);
        List<Long> keys = new ArrayList<>();

        for (DateSplitUtils.DateSplit dateSplit : dateSplits) {
            keys.add(dateSplit.getStartDateTime().getTime());
            //System.out.println("切割后的时间区间: " + dateSplit.getStartDateTimeStr() + " --->  " +  dateSplit.getEndDateTimeStr());
        }
        keys.forEach(System.out::println);

    }


}

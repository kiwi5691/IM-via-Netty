package com.IM.netty.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期格式化工具类
 * @author kiwi
 */
public class DateTimeUtil {

    public final static  int BASE_YMDHMS = 1;
    public final static  int WORDSWITHPM_AM = 2;

    public static LocalDateTime DateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return instant.atZone(zone).toLocalDateTime();
    }
    public static String getDateTimeDisplayString(LocalDateTime dateTime,Integer cases) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strDate2 = dtf2.format(dateTime);
        //根据格式转换
        switch (cases){
            case WORDSWITHPM_AM :
                //获取当前时间
                StringBuilder builder = new StringBuilder();
                Integer diff = getTimeDiff(dateTime);
                if(diff<=2) {
                    dtf2 = DateTimeFormatter.ofPattern("HH:mm");
                    strDate2 = dtf2.format(dateTime);
                    builder.append(timeBuilder(diff));
                    builder.append(" ").append(strDate2);
                    strDate2 = builder.toString();
                }else {
                    dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
                    strDate2 = dtf2.format(dateTime);
                }
                break;
                case BASE_YMDHMS :
                 dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
                 strDate2 = dtf2.format(dateTime);
                break;
        }
        return strDate2;
    }

    public static Integer getTimeDiff(LocalDateTime dateTime){
        LocalDateTime nowTime= LocalDateTime.now();
        long now = nowTime.toEpochSecond(ZoneOffset.ofHours(0));
        long nowDay = now /(60*60*24);
        long t2 = dateTime.toEpochSecond(ZoneOffset.ofHours(0));
        long day2 = t2/(60*60*24);
        return Math.abs((int)(nowDay- day2));

    }

    public static  String timeBuilder(Integer days){
        switch (days){
            case 1: return "昨天";
            case 2: return "前天";
            case 0: return "今天";
            default: return " ";
        }

    }
}

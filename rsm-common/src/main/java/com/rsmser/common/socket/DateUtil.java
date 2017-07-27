package com.rsmser.common.socket;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * @author zhenggm
 * @create 2017-07-27 下午 2:26
 **/


public class DateUtil {

    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date StrZtoDate(String str){
        str = str.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = null;
        try {
            d = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String DateStrToStr(String str){
        return DateToStr(StrZtoDate(str));
    }


}

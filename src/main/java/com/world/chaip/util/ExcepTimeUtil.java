package com.world.chaip.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExcepTimeUtil {

    //除去时间最后的一个   .0
    public static String getExcepTime(String time) throws ParseException {
        if(time != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(format.parse(time));
        }else {
            return time;
        }
    }
}

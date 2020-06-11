package com.cloud.log.util;

import java.util.Date;

/**
 * @author: yangj
 * @date: Created in 2020/6/11
 */
public class DateUtil {

    /** 8小时 */
    private static final long _8_HOURS = 1000*60*60*8;
    /**
     *  GMT+0800 to UTC/GMT
     */
    public static Date toUTC(Date date){
        if(date == null) {
            return null;
        }
        long date_ = date.getTime();
        return new Date(date_ - _8_HOURS);
    }


}

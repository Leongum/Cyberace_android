package com.G5432.Utils;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {

    public static Date parseDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null || date.isEmpty())
            return null;

        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public static String parseDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }


    public static String getUrl(String url, String... params) {
        String newUrl = MessageFormat.format(url, params);
        return newUrl.replace(" ", "%20");
    }
}

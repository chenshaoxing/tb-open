package com.hr.system.manage.util;

import java.io.IOException;
import java.util.Calendar;
public class DayUtil {
    public static int getMonthWorkDay(String dateStr) throws IOException {
            Calendar c = Calendar.getInstance(java.util.Locale.CHINA);
            String[] sp = dateStr.split("-");
            c.set(Calendar.YEAR, Integer.parseInt(sp[0]));
            c.set(Calendar.MONTH, Integer.parseInt(sp[1]) - 1);
            c.set(Calendar.DATE, 1);//计算1号是星期几
            int countDay = getDay(sp[0], sp[1]);
            System.out.println("总天数" +getDay(sp[0], sp[1]));
            int workDay = 0;
            int countWeek = countDay/7;
            int wd = c.get(Calendar.DAY_OF_WEEK);
            int yuDay = getYuTian(countDay%7, wd);
            workDay = countWeek * 5 + yuDay;
            return workDay;
    }

    private static int getDay(String year, String month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    private static int getYuTian(int endDay, int toDay) {
        int yuTian = 0;
        if(1 == toDay) {
            toDay += 5;
        } else {
            toDay = toDay -2;
        }
        yuTian = endDay + toDay;
        if(yuTian > 7) {
            return yuTian%7;
        }
        if(yuTian == 6) {
            return endDay -1;
        } else if(yuTian == 7) {
            return endDay -2;
        } else {
            return endDay;
        }
    }
}



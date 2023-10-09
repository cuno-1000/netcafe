package com.netcafe.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 验证年龄
public class AdultVerification {
    public static boolean isValidAge(String id) {
        int birth_year = Integer.parseInt(id.substring(6,10));
        int birth_month = Integer.parseInt(id.substring(10,12));
        int birth_day = Integer.parseInt(id.substring(12,14));

        try {
            return checkAdult(new SimpleDateFormat("dd/MM/yyyy").parse(birth_day+"/"+birth_month+"/"+birth_year));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkAdult(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTime(date);
        int year = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (year > 18) {
            return true;
        } else if (year < 18) {
            return false;
        }
        // 如果年相等，就比较月份
        int month = current.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
        if (month > 0) {
            return true;
        } else if (month < 0) {
            return false;
        }
        // 如果月也相等，就比较天
        int day = current.get(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH);
        return  day >= 0;
    }
}

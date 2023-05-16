package com.example.emp.common.consts;


import com.example.emp.common.utils.PasswordGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Constants {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Status Code Class");
    }


    public static String randomPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true).useLower(true).useUpper(true).build();
        return passwordGenerator.generate(6); // output ex.: lrU12f 75iwI9
    }

    public static class RESPONSE_CODE {
        public static final String STATUS_CODE_OK = "200";
        public static final String STATUS_CODE_NOT_FOUND = "404";
        public static final String STATUS_CODE_SERVER_ERROR = "500";
    }

    public static class RESPONSE_TYPE {
        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";
        public static final String WARNING = "WARNING";
        public static final String CONFIRM = "CONFIRM";
        public static final String ERROR_LOGIN = "ERROR LOGIN";

    }


    public static Integer randomCodeEMP() {
        Random rand = new Random();
        int i = rand.nextInt(10000);
        if (i < 1000) {
            while (true) {
                i = rand.nextInt(10000);
                if (i < 1000) {
                    i = rand.nextInt(10000);
                } else {
                    return i;
                }
            }
        }
        return i;
    }

    public static String getTimeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.now();
        return dtf.format(localTime);
    }

    public static Date stringToDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Date[] getStartWeekAndEndWeekNow() {


        Date startWeek;
        Date endWeek;

        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

// get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
//        cal.add(Calendar.DAY_OF_WEEK, 1);
        startWeek = cal.getTime();


// start of the next week
//        cal.add(Calendar.DAY_OF_WEEK, -1);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        endWeek = cal.getTime();

        return new Date[]{startWeek, endWeek};
    }


}

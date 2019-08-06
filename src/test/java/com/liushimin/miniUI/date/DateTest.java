package com.liushimin.miniUI.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parse = sdf.parse("2012-01-31T10:50:12");

        System.out.println(parse);
        /*Timestamp timestamp = new Timestamp(1, 1, 1, 1, 1, 1, 1);
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        System.out.println(ts);
        System.out.println(timestamp instanceof Date);
        System.out.println(timestamp instanceof Timestamp);*/
        /*byte bt = 0;
//        bt = 127;
        short s = 0;
        int i = 0;
        long l = 0;
        boolean b = true;
        char c = ' ';
        float f = 0.0f;
        double d = 0.0f;
        System.out.println(d);*/



    }
}

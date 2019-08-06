package com.liushimin.miniUI;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstMini {
    public static void main(String[] args) {
        String s = "@aaa@bbb@ccc";
        String regex = "@\\w+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            int end = matcher.end();
            int start = matcher.start(0);
            String key = matcher.group(0);
            String group = matcher.group();
            System.out.println(key);
            System.out.println(group);
            System.out.println(end);
            System.out.println(start);

        }
    }
}

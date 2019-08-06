package com.liushimin.miniUI.util;

import com.liushimin.miniUI.pojo.Educational;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConvertUtilsTest {

    @Test
    public void convertToBean() throws Exception {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("id", "5");
//        param.put("name", "张三");
        Educational o = ConvertUtils.convertToBean(Educational.class, param);
        System.out.println(o);
        Map map = ConvertUtils.convertToMap(o);
        System.out.println(map);
    }

    @Test
    public void convertToMap() {
    }

    @Test
    public void convertToMapList() {
    }

    @Test
    public void convertToBeanList() {
    }
}
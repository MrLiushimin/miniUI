package com.liushimin.miniUI.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * 运行ParameterTest
 */
@RunWith(Parameterized.class)
public class ParameterTest {
//    private static Calculator calculator = new Calculator();
    private int result;
    private int input1;
    private int input2;

    @Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {4, 2, 2},
                {3, 0, 3},
                {5, 2, 3}
        });
    }

    //构造函数，对变量进行初始化
    public ParameterTest(int result, int input1, int input2){
        this.result = result;
        this.input1 = input1;
        this.input2 = input2;
    }

    @Test
    public void add(){
//        calculator.square(param);
        Assert.assertEquals(result, new Calculate().add(input1, input2));
    }
}

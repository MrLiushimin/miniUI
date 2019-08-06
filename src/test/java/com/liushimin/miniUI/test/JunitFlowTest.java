package com.liushimin.miniUI.test;

import org.junit.*;

public class JunitFlowTest {

    /**
     * @BeforeClass:这个注解表示这个方法会在所有测试方法执行之前执行
     * 因为是static修饰的静态方法，所有只会执行一次。通常用来进行一些
     * 资源的加载，如通过JUnit测试Spring相关的东西时，可以在这个方法
     * 中加载Spring的配置文件
     */
    @BeforeClass
    public static void setUpBefore() {
        System.out.println("this is before class");
    }

    /**
     * @AfterClass:这个注解表示这个方法会在所有方法执行完毕之后执行，通常用来释放资源
     */
    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("this is after class");
    }

    @Before
    public void setUp() {
        System.out.println("this is before");
    }

    @After
    public void tearDown() {
        System.out.println("this is Down");
    }

    @Test
    public void test1() {
        System.out.println("this is test1");
    }



}

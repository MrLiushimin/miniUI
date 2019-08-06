package com.liushimin.miniUI.test;

import org.junit.*;

import static org.junit.Assert.*;

public class CalculateTest {
    public CalculateTest() {
        System.out.println("构造函数");
    }

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

    @Ignore
    public void add() {
        System.out.println("加");
        assertEquals(5, new Calculate().add(1, 4));
    }

    @Test
    public void subtract() {
        System.out.println("减");
        assertEquals(4, new Calculate().subtract(9, 5));
    }

    @Test
    public void multiply() {
        System.out.println("乘");
        assertEquals(6, new Calculate().multiply(2, 3));
    }

    @Test(expected = ArithmeticException.class)
    public void divide() {
        System.out.println("除");
        assertEquals(3, new Calculate().divide(9, 0));
    }
}
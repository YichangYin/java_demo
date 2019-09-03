package com.demo;

import java.util.Calendar;

/**
 * @program: java_demo
 * @description:
 * @author: YinYichang
 * @create: 2018/07/17 16:37
 **/
public class HelloWorld {

    private Integer age;

    public static void main(String[] args) {
        String str = "aa这是一条测试信息";
        System.out.println(str.charAt(1) == 97);
        HelloWorld hello = new HelloWorld();
        hello.setAge(1);
        int a = hello.getAge();
        System.out.println(a);

        Calendar calendar = Calendar.getInstance();
        String weeknum = "日一二三四五六";
        System.out.println("星期："+calendar.get(Calendar.DAY_OF_WEEK));
        String weeknumCh = String.valueOf(weeknum.charAt(calendar.get(Calendar.DAY_OF_WEEK) -1));
        System.out.println(weeknumCh);

        /**
         * 这是一个测试的程序
         */
        System.out.println("Hello World;");
        //test();
    }
    /** 
      * @Description:
      * @Param: [] 
      * @return: void 
      * @Author: YinYichang 
      * @Date: 2018/7/17 
      */ 
    public static void test() {
        int num = 10;
        for (int i = 1; i < num; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + i * j + "  ");
            }
            System.out.println();
        }
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return this.age;
    }
}

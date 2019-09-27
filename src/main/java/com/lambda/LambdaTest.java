package com.lambda;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @program: java_demo
 * @description: lambda表达式
 *              语法：(params) -> expression
 *                   (params) -> statement
 *                   (params) -> { statements }
 *              说明：parameters：参数，多个以逗号隔开。
 *                   expression ：语句，即一句话或者一个操作。
 *                   statements ：陈述，一个或多个语句。
 * @author: Mr.Walloce
 * @create: 2019/08/12 10:33
 **/
public class LambdaTest {

    public static void main(String[] args) {

        List<Person> roster = Person.createRoster();

//        oldRunnable();

        newRunnable();
    }

    /**
      * @Description 传统方式筛选
      * @param list
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/9/27 10:18
      */
    public static void oldFilter(List<Person> list) {

        for (Person person : list) {
            if (person.getAge() > 15 && person.getAge() < 25) {
                person.printPerson();
            }
        }
    }

    /**
      * @Description lambda方式筛选
      * @param list
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/9/27 10:19
      */
    public static void newFilter(List<Person> list) {
        list.stream()
                .filter(person -> person.getAge() > 15)
                .filter(person -> person.getAge() < 25)
                .forEach(person -> person.printPerson());
    }

    /**
      * @Description 传统方法排序
      * @param list
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/9/27 10:16
      */
    public static void oldSort(List<Person> list){
        //排序
        Collections.sort(list,new Comparator<Person>() {
            //根据年龄进行排序
            @Override
            public int compare(Person a,Person b){
                if (a.getAge() <= b.getAge()) {
                    return 1;
                }else{
                    return -1;
                }
            }
        });
    }

    /**
      * @Description lambda表达式排序
      * @param list
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/9/27 10:16
      */
    public static void newSort(List<Person> list){
        //lambda会根据list自动推断出 a,b 的类型
        Collections.sort(list, (a, b) -> a.getAge() < b.getAge() ? 1:-1);
    }

    /**
      * @Description 普通的Runnable
      * @param
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/9/27 10:28
      */
    public static void oldRunnable(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("oldRunnable running");
                List<Person> people = Person.createRoster();
                for (Person person : people) {
                    person.printPerson();
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * @Description lambda表达式方式
     * @param
     * @Return void
     * @Author Mr.Walloce
     * @Date 2019/9/27 10:29
     */
    public static void newRunnable(){
        new Thread(() -> {
            System.out.println("newRunnable running");
            List<Person> people = Person.createRoster();
            people.forEach(person -> person.printPerson());
        }).start();
    }
}

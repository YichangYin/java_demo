package com.demo;

import java.util.*;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2018/08/15 19:58
 **/
public class HashMapTest {

    public static void main(String[] args) {
        noOrder();
        hasOrder();
        likedHashMap();
    }

    public static void noOrder() {
        System.out.println("------无序（随机输出------");
        Map map = new HashMap();
        map.put("1", "Level 1");
        map.put("2", "Level 2");
        map.put("3", "Level 3");
        map.put("4", "Level 4");
        map.put("F", "Level F");
        map.put("Q", "Level Q");
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println("Key: " + e.getKey() + ";   Value: "
                    + e.getValue());
        }
    }
    // 有序(默认排序，不能指定)
    public static void hasOrder() {
        System.out.println("------有序（但是按默认顺充，不能指定）------");
        Map map = new TreeMap();
        map.put("F", "Level F");
        map.put("7", "Level 1");
        map.put("8", "Level 2");
        map.put("4", "Level 3");
        map.put("4", "Level 4");
        map.put("Q", "Level Q");
        map.put("E", "Level E");
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println("Key: " + e.getKey() + ";   Value: "
                    + e.getValue());
        }
    }
    public static void likedHashMap() {
        System.out.println("------有序（根据输入的顺序输出）------");
        Map map = new LinkedHashMap();
        map.put("F", "Level F");
        map.put("7", "Level 1");
        map.put("8", "Level 2");
        map.put("4", "Level 3");
        map.put("4", "Level 4");
        map.put("Q", "Level Q");
        map.put("E", "Level E");
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println("Key: " + e.getKey() + ";   Value: "
                    + e.getValue());
        }
    }
}

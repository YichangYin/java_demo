package com.demo;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2019/06/18 10:23
 **/
public class ReferenceTest {

    private static List<Object> list = new ArrayList<Object>();

    public static void main(String[] args) {

        //testSoftReference();
        //testSoftReference1();
        testWeakReference();
    }

    public static void testSoftReference() {
        for (int i = 0; i < 5; i++) {
            //创建弱引用字节数组
            byte[] buff = new byte[1024 * 1024 * 1000];
            SoftReference<byte[]> sr = new SoftReference<byte[]>(buff);
            list.add(sr);
        }

        //主动通知垃圾回收，内存不足时回收弱引用
        System.gc();

        for(int i=0; i < list.size(); i++){
            Object obj = ((SoftReference) list.get(i)).get();
            System.out.println(obj);
        }
    }

    public static void testSoftReference1() {
        //强引用
        byte[] buff = null;

        for (int i = 0; i < 5; i++) {
            buff = new byte[1024 * 1024 * 1000];
            SoftReference<byte[]> sr = new SoftReference<byte[]>(buff);
            list.add(sr);
        }

        //主动通知垃圾回收，内存不足时回收弱引用
        System.gc();

        for(int i=0; i < list.size(); i++){
            Object obj = ((SoftReference) list.get(i)).get();
            System.out.println(obj);
        }
    }

    public static void testWeakReference() {
        for (int i = 0; i < 5; i++) {
            byte[] buff = new byte[1024 * 1024 * 1000];
            WeakReference<byte[]> sr = new WeakReference<byte[]>(buff);
            list.add(sr);
        }

        //主动通知垃圾回收
        System.gc();

        for(int i=0; i < list.size(); i++){
            Object obj = ((WeakReference) list.get(i)).get();
            System.out.println(obj);
        }
    }

    ReferenceQueue queue = new ReferenceQueue ();

}


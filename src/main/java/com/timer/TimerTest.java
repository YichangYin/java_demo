package com.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @program: java_demo
 * @description: Java中Timer定时器测试
 * @author: Mr.Walloce
 * @create: 2019/07/29 22:36
 **/
public class TimerTest {

    public static class MyTimer extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        public void run() {
            System.out.println("任务被调度执行...");
        }
    }

    public static void main(String[] args) {
        Timer t = new Timer();
        MyTimer mt = new MyTimer();
        //立即执行
        //t.schedule(mt, new Date());

        //5s后执行
        //t.schedule(mt, 5000);

        //5s 后执行，每2s执行一次
        //t.schedule(mt, 5000, 2000);

        //每2s执行一次
        t.schedule(mt, new Date(), 2000);
    }
}

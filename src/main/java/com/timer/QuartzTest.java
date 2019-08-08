package com.timer;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2019/07/30 18:10
 **/
public class QuartzTest {

    /**
      * @Description 测试job
      * @Author Mr.Walloce
      * @Date 2019/7/30 18:17
      */
    public static class QuartzTestJob implements Job {

        /**
          * @Description TODO
          * @param jobExecutionContext JobExecutionContext中包含了Quartz运行时的环境以及Job本身的详细数据信息。
          * @Return void
          * @Author Mr.Walloce
          * @Date 2019/7/29 19:17
          */
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Quartz 测试任务被执行中...");
        }
    }

    /**
      * 调度器（Simple Triggers）
      * @param
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/7/30 18:19
      */
    public static void mySimpleSchedule() {
        try {
            //创建调度器实例scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //创建JobDetail实例，创建要执行的job
            JobDetail jobDetail = JobBuilder.newJob(QuartzTestJob.class)
                    .withIdentity("job1", "group1").build();

            //构建Trigger实例,每隔1s执行一次
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    //立即生效
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            //每隔40s执行一次
                            .withIntervalInSeconds(5)
                            //一直执行
                            .repeatForever())
                    .build();

            //调度执行任务
            scheduler.scheduleJob(jobDetail, trigger);
            //启动
            scheduler.start();

            //睡眠
            //Thread.sleep(6000);

            //停止
            //scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
      * @Description 调度器（Cron Triggers）
      * @param
      * @Return void
      * @Author Mr.Walloce
      * @Date 2019/7/29 18:59
      */
    public static void myCronSchedule() {
        try {
            //创建调度器实例scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail job = JobBuilder.newJob(QuartzTestJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    //20秒执行一次
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new QuartzTest().mySimpleSchedule();
    }
}

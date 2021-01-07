package com.lazyboy.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义执行任务
 *
 * @auther: zhouwei
 * @date: 2020/8/20 12:06
 */
public class HelloWorldJob implements Job {

    private static int anInt = 0;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        Trigger trigger = jobExecutionContext.getTrigger();
//        System.out.println("I AM A QUARTZ JOB-" + key + "-" + anInt + "!");
        logger.info("========>executing job:{}->{}", key,jobDataMap);
//        anInt++;
    }

    public static void main(String[] args) {
        try {
            //创建调度器
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            System.out.println("===>scheduler starting...");
            JobKey jobKey = JobKey.jobKey("hello-world", "default group");
            //创建Job&JobDetail
            JobDetail jobDetail = JobBuilder.newJob(HelloWorldJob.class)
                    .withIdentity(jobKey).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("hello-world-trigger", "hello-world")
                    .startNow().withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                                    .withIntervalInSeconds(1)
                                    .repeatForever()
                    ).build();
            defaultScheduler.scheduleJob(jobDetail, trigger);
            defaultScheduler.start();
            System.out.println("===>scheduler starts");


            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("===>scheduler is shutdown...");
            defaultScheduler.shutdown();
            System.out.println("===>scheduler is shutdown finish...");

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
package com.lazyboy.controller;

import com.lazyboy.job.HelloWorldJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * @auther: zhouwei
 * @date: 2021/1/7 13:58
 */
@RequestMapping("/job")
@Controller
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/createJob")
    @ResponseBody
    public String createJob(String name, String group, int supplierId,int hour,int minutes) throws SchedulerException {
        //创建Job&JobDetail
        JobKey jobKey = JobKey.jobKey(name, group);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("supplierId", supplierId);
        JobDetail jobDetail = JobBuilder.newJob(HelloWorldJob.class)
                .setJobData(jobDataMap)
                .withIdentity(jobKey)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(name + "-trigger", group + "trigger-group")
                .startNow()
                .withSchedule(
                        CronScheduleBuilder.dailyAtHourAndMinute(hour,minutes)
                        .inTimeZone(TimeZone.getTimeZone(ZoneId.of("+7")))
                ).build();


        scheduler.scheduleJob(jobDetail, trigger);
//        scheduler.pauseJob(jobKey);
        return "success";
    }

    public static void main(String[] args) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+1:00");
        TimeZone timeZone1 = TimeZone.getTimeZone(ZoneId.of("-1"));
        String[] availableIDs = TimeZone.getAvailableIDs();
        System.out.println(Arrays.toString(availableIDs));
        System.out.println(timeZone.getID());
        System.out.println(timeZone1.getID());

        System.out.println(TimeZone.getTimeZone("+7"));
    }


    @PostMapping("/triggerJob")
    @ResponseBody
    public String triggerJob(String name, String group) throws SchedulerException {
        //创建Job&JobDetail
        JobKey jobKey = JobKey.jobKey(name, group);

        scheduler.triggerJob(jobKey);
        return "success";
    }

    @PostMapping("/resumeJob")
    @ResponseBody
    public String resumeJob(String name, String group) throws SchedulerException {
        //创建Job&JobDetail
        JobKey jobKey = JobKey.jobKey(name, group);

        scheduler.resumeJob(jobKey);
        return "success";
    }

    @PostMapping("/pauseJob")
    @ResponseBody
    public String pauseJob(String name, String group) throws SchedulerException {
        //创建Job&JobDetail
        JobKey jobKey = JobKey.jobKey(name, group);

        scheduler.pauseJob(jobKey);
        return "success";
    }
}
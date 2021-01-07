package com.lazyboy.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @auther: zhouwei
 * @date: 2021/1/7 10:36
 */
@Configuration
public class SchedulerConfig {

//    @Bean
    /*public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource)
    {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);

        // quartz参数
        Properties prop = new Properties();
        //调度器实例名称
        prop.put("org.quartz.scheduler.instanceName", "AATSSScheduler");
        //如果使用集群，instanceId必须唯一，设置成AUTO
        prop.put("org.quartz.scheduler.instanceId", "AUTO");


        // 线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        //线程池中线程数量，这意味着最多可以同时运行20个job。
        prop.put("org.quartz.threadPool.threadCount", "20");
        //线程优先级
        prop.put("org.quartz.threadPool.threadPriority", "5");


        // JobStore配置
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 集群配置
        prop.put("org.quartz.jobStore.isClustered", "false");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
//        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");

        // sqlserver 启用
        // prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");
        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        factory.setQuartzProperties(prop);

        factory.setSchedulerName("RuoyiScheduler");
        // 延时启动
        factory.setStartupDelay(1);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 可选，QuartzScheduler
        // 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        factory.setAutoStartup(true);

        return factory;
    }*/

    @Bean
    public Scheduler schedule() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     * 设置quartz属性
     *
     */
    public static Properties quartzProperties() {
        Properties prop = new Properties();


        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");//实例化threadPool时,使用的线程类为SimpleThreadPool
        //threadCount和threadPriority将setter的形式注入ThreadPoolS实例
        prop.put("org.quartz.threadPool.threadCount", "5");//并发数
        prop.put("org.quartz.threadPool.threadPriority", "5");//优先级
        prop.put("org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer", true);
        //prop.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");//默认存储在内存中


        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");//数据库持久化
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
//        prop.put("org.quartz.jobStore.isClustered", false);//是否开启集群
//        prop.put("org.quartz.jobStore.clusterCheckinInterval", 20000);//集群检查
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");//表前缀:true
        prop.put("org.quartz.jobStore.dataSource", "myDS");//数据库
        prop.put("org.quartz.dataSource.myDS.driver", "com.mysql.jdbc.Driver");
        prop.put("org.quartz.dataSource.myDS.URL", "jdbc:mysql://localhost:3308/local_test?characterEncoding=UTF-8&useSSL=false");
        prop.put("org.quartz.dataSource.myDS.user", "root");
        prop.put("org.quartz.dataSource.myDS.password", "123456");
        prop.put("org.quartz.dataSource.myDS.maxConnections", "10");
        return prop;
    }

    /**
     *
     *
     * #调度器实例名称
     * org.quartz.scheduler.instanceName = quartzScheduler
     *
     * #调度器实例编号自动生成
     * org.quartz.scheduler.instanceId = AUTO
     *
     * #持久化方式配置
     * org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
     *
     * #持久化方式配置数据驱动，MySQL数据库
     * org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
     *
     * #quartz相关数据表前缀名
     * org.quartz.jobStore.tablePrefix = QRTZ_
     *
     * #开启分布式部署
     * org.quartz.jobStore.isClustered = true
     * #配置是否使用
     * org.quartz.jobStore.useProperties = false
     *
     * #分布式节点有效性检查时间间隔，单位：毫秒
     * org.quartz.jobStore.clusterCheckinInterval = 20000
     *
     * #线程池实现类
     * org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
     *
     * #执行最大并发线程数量
     * org.quartz.threadPool.threadCount = 10
     *
     * #线程优先级
     * org.quartz.threadPool.threadPriority = 5
     *
     * #配置为守护线程，设置后任务将不会执行
     * #org.quartz.threadPool.makeThreadsDaemons=true
     *
     * #配置是否启动自动加载数据库内的定时任务，默认true
     * org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread = true
     *
     */
}
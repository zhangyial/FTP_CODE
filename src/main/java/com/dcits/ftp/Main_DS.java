package com.dcits.ftp;

import com.dcits.ftp.ftpUtils.ConfigMessage;
import com.dcits.ftp.quartz.QuartzJob;
import com.dcits.ftp.quartz.QuartzJob_Loop;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import sun.applet.Main;
import java.io.*;


/**
 * Describe: 测试log打印
 * Company: 神州数码信息系统有限公司
 * Version: v1.0
 * User:xiudx
 * Date:2017/9/14 9:31
 */
public class Main_DS {
    private static Log logger = LogFactory.getLog(Main.class);
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    public static String JOB_NAME = "QuartzJob";
    public static String TRIGGER_NAME = "trigger";
    public static String JOB_GROUP_NAME = "group1";
    public static String TRIGGER_GROUP_NAME = "group1";

    public static void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);
            sched.start();
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
    }

    //定点启动
    public static void ds() {
        try {
            logger.info("系统初始化配置开始");
            ConfigMessage.init();
            String hour = ConfigMessage.getScheduleTime();
            String value = "0  0 " + hour + " * * * ?";
            logger.info("系统每天" + hour + "点为你同步银联数据");
            addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, QuartzJob.class, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //周期性启动，每两小时
    public static void loop() {
        try {
            logger.info("配置初始化开始");
            JOB_NAME = "QuartzJob_Loop";
            TRIGGER_NAME = "trigger";
            JOB_GROUP_NAME = "group2";
            TRIGGER_GROUP_NAME = "group2";
            ConfigMessage.init();
            String hour = ConfigMessage.getScheduleLoopTime();
            String value = "0 0 */" + hour + " * * ?";
            logger.info("系统每" + hour + "小时为你检测数据是否存在");
            addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, QuartzJob_Loop.class, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {
               ConfigMessage.init();
               if (ConfigMessage.getLOOP()){
                   loop();
               }else {
                   ds();
               }
    }
}

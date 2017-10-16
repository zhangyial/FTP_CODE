package com.dcits.ftp.ftpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Describe:
 * Company: 神州数码信息系统有限公司
 * Version: v1.0
 * User:xiudx
 * Date:2017/9/14 9:38
 */
public class ConfigMessage {
    private static  String YL_HOST;
    private static  int YL_PORT;
    private static String YL_USERNAME;
    private static String YL_PASSWORD;
    private static String SW_HOST;
    private static int SW_PORT;
    private static String SW_USERNAME;
    private static String SW_PASSWORD;
    private static String PATH_MEMORY;
    private static String skyUnionPayReportSdjgdm;
    private static String SCHEDULE_TIME;
    private static String SCHEDULE_LOOP_TIME;
    private static Boolean LOOP;

    public static Boolean getLOOP() {
        return LOOP;
    }

    public static String getScheduleLoopTime() {
        return SCHEDULE_LOOP_TIME;
    }

    public static String getScheduleTime() {
        return SCHEDULE_TIME;
    }

    public static String getSkyUnionPayReportSdjgdm() {
        return skyUnionPayReportSdjgdm;
    }

    public static String getYL_HOST() {
        return YL_HOST;
    }

    public static int getYL_PORT() {
        return YL_PORT;
    }

    public static String getYL_USERNAME() {
        return YL_USERNAME;
    }

    public static String getYL_PASSWORD() {
        return YL_PASSWORD;
    }

    public static String getSW_HOST() {
        return SW_HOST;
    }

    public static int getSW_PORT() {
        return SW_PORT;
    }

    public static String getSW_USERNAME() {
        return SW_USERNAME;
    }

    public static String getSW_PASSWORD() {
        return SW_PASSWORD;
    }

    public static String getPathMemory() {return PATH_MEMORY;}

    public static void init() throws IOException {
        //通过文件路径读取配置文件
        Properties prop = new Properties();
        InputStream in = new BufferedInputStream (new FileInputStream(System.getProperty("user.dir")+"/ftpConfig.properties"));
       // InputStream in = new BufferedInputStream (new FileInputStream("src/main/resources/ftpConfig.properties"));
        prop.load(in);     ///加载属性列表
        in.close();
        YL_HOST=prop.getProperty("FTP_YL_HOST");
        YL_PORT=Integer.parseInt(prop.getProperty("FTP_YL_PORT"));
        YL_USERNAME=prop.getProperty("FTP_YL_USERNAME");
        YL_PASSWORD=prop.getProperty("FTP_YL_PASSWORD");
        SW_HOST=prop.getProperty("FTP_SW_HOST");
        SW_PORT=Integer.parseInt(prop.getProperty("FTP_SW_PORT"));
        SW_USERNAME=prop.getProperty("FTP_SW_USERNAME");
        SW_PASSWORD=prop.getProperty("FTP_SW_PASSWORD");
        PATH_MEMORY=prop.getProperty("PATH_MEMORY");
        skyUnionPayReportSdjgdm = prop.getProperty("skyUnionPayReportSdjgdm");
        SCHEDULE_TIME= prop.getProperty("SCHEDULE_TIME");
        SCHEDULE_LOOP_TIME=prop.getProperty("SCHEDULE_LOOP_TIME");
        LOOP= Boolean.valueOf(prop.getProperty("LOOP"));
    }

}

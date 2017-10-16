package com.dcits.ftp.ftpUtils;
import java.io.*;
import java.util.*;

/**
 * Describe: ftp工具类
 * Company: 神州数码信息系统有限公司
 * Version: v1.0
 * User:xiudx
 * Date:2017/9/14 9:31
 */
public class FtpUtils {


    // 创建目录
    public static void createDir(String destDirName) {
        File dir = new File(destDirName);
        if (!dir.exists()) {
            //不存在则创建目录
            dir.mkdir();
        }
    }

    /*
        根据系统当前时间得到前一天和前两天的相关年月日信息
     */
    public static Map timeManage() {
        Map timemap = new HashMap();
        Date dNow = new Date();   //当前时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        String[] defaultStartDate=new String[2];
        for(int i=0;i<2;i++){
            calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
            String fileyear=String.valueOf(calendar.get(Calendar.YEAR));//获取年份
            String filemonth=String.valueOf(calendar.get(Calendar.MONTH)+1);//获取年份
            if(filemonth.length()==1){
                filemonth="0"+filemonth;
            }
            String filedate=String.valueOf(calendar.get(Calendar.DATE));
            defaultStartDate[i]=fileyear+filemonth+filedate;
        }
        timemap.put("mdrdate", defaultStartDate[0]);
        timemap.put("filedate", defaultStartDate[1].substring(2));
        return timemap;
    }


    //根据规则得到文件名的生成方法
    public static String getFileName() {
        String filename = "INO" + "" + timeManage().get("filedate") + "00TPCPM";
        filename = filename + ConfigMessage.getSkyUnionPayReportSdjgdm();
        return filename;
    }
}


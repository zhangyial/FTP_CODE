package com.dcits.ftp;

import com.dcits.ftp.ftpUtils.ConfigMessage;
import com.dcits.ftp.ftpUtils.FtpDownload;
import com.dcits.ftp.ftpUtils.FtpUtils;
import org.quartz.SchedulerException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.dcits.ftp.ftpUtils.FtpUpload.upLoadFromProduction;

/**
 * Created by zhangyial on 2017/9/20.
 */
public class Main_BC {
    public static void main(String[] args) throws SchedulerException, ParseException {
        try {
            ConfigMessage.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //得到文件夹名与文件名
        //文件名为当天的年月日日期的前两天 文件夹名为当天的年月日日期的前一天
        //例如当日为2017/09/18 则文件夹名为2017/09/17 文件名为2017/09/16
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(args[0]);
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, +1);
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
        String dname = defaultStartDate[0];
        String filename = defaultStartDate[1].substring(2);
        filename = "INO" + "" + filename + "00TPCPM";
        filename = filename + ConfigMessage.getSkyUnionPayReportSdjgdm();
        //在本地创建文件夹
       FtpUtils.createDir(ConfigMessage.getPathMemory() + "/" + dname);
        //下载文件到本地
       boolean flag=FtpDownload.downloadFile(ConfigMessage.getYL_HOST(), ConfigMessage.getYL_PORT(), ConfigMessage.getYL_USERNAME(), ConfigMessage.getYL_PASSWORD(), dname, filename, ConfigMessage.getPathMemory() + "/" + dname);
        if(flag) {
            //上传文件到ftp服务器
            upLoadFromProduction(ConfigMessage.getSW_HOST(), ConfigMessage.getSW_PORT(), ConfigMessage.getSW_USERNAME(), ConfigMessage.getSW_PASSWORD(), dname, filename, ConfigMessage.getPathMemory() + "/" + dname + "/" + filename);
        }else {
            return;
        }
        }
}

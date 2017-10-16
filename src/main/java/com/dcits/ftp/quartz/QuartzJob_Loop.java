package com.dcits.ftp.quartz;

import com.dcits.ftp.ftpUtils.ConfigMessage;
import com.dcits.ftp.ftpUtils.FtpDownload;
import com.dcits.ftp.ftpUtils.FtpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.*;
import java.util.Date;

import static com.dcits.ftp.ftpUtils.FtpUpload.upLoadFromProduction;

/**
 * Describe:
 * Company: 神州数码信息系统有限公司
 * Version: v1.0
 * User:cuihld
 * Date:2017/9/14 14:42
 */


public class QuartzJob_Loop implements Job {
    private static Log logger = LogFactory.getLog(QuartzJob_Loop.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("定时任务开始执行" + new Date());
        try {
            ConfigMessage.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = FtpUtils.getFileName();
        String dname = (String) FtpUtils.timeManage().get("mdrdate");
        boolean  flag=connect_ftp(ConfigMessage.getSW_HOST(), ConfigMessage.getSW_PORT(), ConfigMessage.getSW_USERNAME(), ConfigMessage.getSW_PASSWORD(), dname, filename);
       if (!flag){
           logger.info("文件不存在，正在为您同步此文件");
           FtpUtils.createDir(ConfigMessage.getPathMemory() + "/" + FtpUtils.timeManage().get("mdrdate"));
        //下载文件到本地
        FtpDownload.downloadFile(ConfigMessage.getYL_HOST(), ConfigMessage.getYL_PORT(), ConfigMessage.getYL_USERNAME(), ConfigMessage.getYL_PASSWORD(), dname, filename, ConfigMessage.getPathMemory() + "/" + dname);
        //上传文件到ftp服务器
        upLoadFromProduction(ConfigMessage.getSW_HOST(), ConfigMessage.getSW_PORT(), ConfigMessage.getSW_USERNAME(), ConfigMessage.getSW_PASSWORD(), dname, filename, ConfigMessage.getPathMemory() + "/" + dname + "/" + filename);
       }
    }

    public static boolean connect_ftp(String url,// FTP服务器hostname
                                     int port,// FTP服务器端口
                                     String username, // FTP登录账号
                                     String password, // FTP登录密码
                                     String path, // FTP服务器保存目录
                                     String filename // 上传到FTP服务器上的文件名
    ) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            logger.info("开始登陆税务FTP服务器进行检测");
            //连接FTP服务器
            ftp.connect(url, port);
            //登录FTP服务器
            ftp.login(username, password);
            //验证FTP服务器是否登录成功
            int replyCode = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                logger.info("登陆税务FTP服务器失败");
                ftp.disconnect();
            }
            logger.info("正在检测文件是否存在");
            ftp.setDataTimeout(60000);       //设置传输超时时间为60秒
            ftp.setConnectTimeout(60000);       //连接超时为60秒
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            FTPFile[] ftpFiles = ftp.listFiles();    //遍历该目录返回List
            for(FTPFile file : ftpFiles){
                if(path.equalsIgnoreCase(file.getName())&&file.getSize()>0){
                    logger.info("文件已存在,无需同步");
                    success=true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    logger.error(ioe);
                }
            }
        }
        return success;
    }



}



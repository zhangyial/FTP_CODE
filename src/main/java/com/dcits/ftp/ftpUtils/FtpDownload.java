package com.dcits.ftp.ftpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public  class FtpDownload {
    private static Log logger = LogFactory.getLog(FtpDownload.class);
    /**
     * 下载文件
     * @param hostname FTP服务器地址
     * @param port  FTP服务器端口号
     * @param username  FTP登录帐号
     * @param password  FTP登录密码
     * @param pathname  FTP服务器文件目录
     * @param filename  文件名称
     * @param localpath 下载后的文件路径
     * @return
     */
    public static boolean downloadFile(String hostname, int port, String username, String password, String pathname, String filename, String localpath){
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            logger.info("开始登陆银联FTP服务器");
            //连接FTP服务器
            ftpClient.connect(hostname, port);
            //登录FTP服务器
            ftpClient.login(username, password);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                logger.info("登陆银联FTP服务器失败");
                ftpClient.disconnect();
                return flag;
            }
            logger.info("登陆银联FTP服务器成功!");
            //切换FTP目录
            logger.info("开始下载文件...");
            ftpClient.changeWorkingDirectory(pathname);    //切换目录到日期匹配的目录
            FTPFile[] ftpFiles = ftpClient.listFiles();    //遍历该目录返回List
            for(FTPFile file : ftpFiles){
                if(filename.equalsIgnoreCase(file.getName())){      //根据日期参数匹配下载文件
                    File localFile = new File(localpath + "/" + file.getName());
                    OutputStream os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);          //将匹配到的下载文件抽成OutputStream并写入到指定的os中
                    os.close();
                    logger.info("下载文件成功！");
                }else{
                    logger.info("银联FTP服务器不存在对账文件，请手动导入!");
                }
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        } finally{
            if(ftpClient.isConnected()){
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                       logger.error(e);
                }
            }
        }
        return flag;
    }

}
	

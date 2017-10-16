package com.dcits.ftp.ftpUtils;

/**
 * Created by zhangyial on 2017/9/15.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUpload {
    private static Log logger = LogFactory.getLog(FtpUpload.class);
    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url      FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false *
     * @Version 1.0
     */
    public static boolean uploadFile(String url,// FTP服务器hostname
                                     int port,// FTP服务器端口
                                     String username, // FTP登录账号
                                     String password, // FTP登录密码
                                     String path, // FTP服务器保存目录
                                     String filename, // 上传到FTP服务器上的文件名
                                     InputStream input // 输入流
    ) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            logger.info("开始登陆税务FTP服务器");
            int reply;
            ftp.connect(url, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("登陆税务FTP服务器失败");
                ftp.disconnect();
                return success;
            }
            logger.info("登陆税务FTP服务器成功！");
            logger.info("开始上传文件...");
            ftp.setDataTimeout(60000);       //设置传输超时时间为60秒
            ftp.setConnectTimeout(60000);       //连接超时为60秒
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(filename, input);
            logger.info("上传文件成功！");
            input.close();
            ftp.logout();
            success = true;
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

    /**
     * 将本地文件上传到FTP服务器上 *
     */
    public static void upLoadFromProduction(String url,// FTP服务器hostname
                                            int port,// FTP服务器端口
                                            String username, // FTP登录账号
                                            String password, // FTP登录密码
                                            String path, // FTP服务器保存目录
                                            String filename, // 上传到FTP服务器上的文件名
                                            String orginfilename // 输入流文件名
    ) {
        try {
            FileInputStream in = new FileInputStream(new File(orginfilename));
            boolean flag = uploadFile(url, port, username, password, path, filename, in);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

}
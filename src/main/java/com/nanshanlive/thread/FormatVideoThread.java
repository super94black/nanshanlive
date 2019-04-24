package com.nanshanlive.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author zhang
 * @Date 2019/4/21 14:40
 * @Content 直播流关闭后视频转码线程
 */
public class FormatVideoThread extends Thread {
    public String byteV;
    public String name;
    public String addr;
    public String videoName;
    public FormatVideoThread(String byteV,String name,String addr,String videoName){
        this.byteV = byteV;
        this.name = name;
        this.addr = addr;
        this.videoName = videoName;
    }


    @Override
    public void run() {
        ProcessBuilder builder = new ProcessBuilder();
        String baseAdd = "D://Program Files//nginx//nginx-rtmp-module//tmp//rec//";

        builder.command("ffmpeg","-y","-i", baseAdd + name + ".flv",
                "-b:v", byteV, "-s", "960x540",baseAdd + addr
                        + videoName+".flv"

        );

        builder.redirectErrorStream(false);
        try {
            Process process = builder.start();
            InputStream in = process.getInputStream();
            System.out.println("正在转换，请稍候=======================");
            convertStreamToString(in);
            System.out.println("--------");
            InputStream errorStream = process.getErrorStream();
            if (errorStream != null && errorStream.read() > 0) {
                System.out.println("错误：");
                convertStreamToString(errorStream);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("错误：");
            e.printStackTrace();
        }
    }

    public String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();

        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line + "/n");
            }
        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
}

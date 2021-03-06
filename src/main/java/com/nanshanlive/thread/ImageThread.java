package com.nanshanlive.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author zhang
 * @Date 2019/4/21 16:48
 * @Content 直播间封面截图线程
 */
public class ImageThread extends Thread {

    public Integer uid;
    public String token;

    public ImageThread(Integer uid,String token){
        this.uid = uid;
        this.token = token;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProcessBuilder builder = new ProcessBuilder();
        String add = "D://Program Files//nginx//nginx-rtmp-module//tmp//rec//" + uid + ".flv";
        builder.command("ffmpeg", "-ss", "4", "-y","-i", add,
                 "-f", "image2","D://Program Files//nginx//nginx-rtmp-module//tmp//rec//img//"

                        + uid + "-pass=" + token + ".jpg");

        builder.redirectErrorStream(false);
        try {
            Process process = builder.start();

            InputStream in = process.getInputStream();
            while (null == in){
                process = builder.start();
                in = process.getInputStream();
            }

            System.out.println("正在进行截图，请稍候=======================");
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

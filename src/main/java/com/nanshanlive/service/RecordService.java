package com.nanshanlive.service;

import com.nanshanlive.util.CurlUtil;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2019/4/16 21:53
 * @Content
 */
@Service
public class RecordService {

    /**
     * 开启直播录制
     * @param name
     */
    public void recordLiveStream(String name){
        String url = "http://localhost/control/record/start?app=push&name="+ name + "&rec=rec";
        String result = CurlUtil.getContent(url,null,"GET");
        while ("".equals(result)){
            result = CurlUtil.getContent(url,null,"GET");
        }

    }

    /**
     * 关闭直播录制
     * @param name
     */
    public void stopRecordLiveStream(String name){
        String url = "http://localhost/control/record/stop?app=push&name="+ name + "&rec=rec";
        String result = CurlUtil.getContent(url,null,"GET");
        while (null == result){
            stopRecordLiveStream(name);
        }
    }



}

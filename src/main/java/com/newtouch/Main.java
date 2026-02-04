package com.newtouch;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.Date;

public class Main {
    /**
     * 一周执行1次
     * @param args
     */
    public static void main(String[] args) {
        //获取登录认证。打开https://portal.newtouch.com/newtouchPts/workbrench，然后【工作台】，【日志填写】按钮鼠标右键，复制链接地址
        String s = HttpUtil.get("https://eds.newtouch.com/api/otherLogin/iam/login?iamKey=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        String token = JSONUtil.parseObj(s).getByPath("token", String.class);
//        System.out.println(token);
        SimpleWorklogJob job = new SimpleWorklogJob();
        Date date = new Date();
        DateTime start = DateUtil.beginOfWeek(date);
        DateTime end = DateUtil.endOfWeek(date);
        //日报
        do {
            job.execDayWork(start, "oms客户画像需求开发", token);
            start = DateUtil.offset(start, DateField.DAY_OF_MONTH, 1);
        } while (!start.isAfter(end));
        //周报
        job.execWeekWork(DateUtil.beginOfWeek(date,true),"oms客户画像需求接口",token);
    }
}
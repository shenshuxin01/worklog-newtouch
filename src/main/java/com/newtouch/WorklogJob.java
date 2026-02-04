package com.newtouch;

import java.util.Date;

public interface WorklogJob {
    /**
     * 填写某天的日报
     * @param workDate 工作日
     * @param workDescription 工作内容描述
     */
    void execDayWork(Date workDate, String workDescription,String authorization);

    /**
     * 填写周的周报
     * @param workDate 工作周
     * @param workDescription 工作内容描述 oms客户画像需求接口
     */
    void execWeekWork(Date workDate, String workDescription,String authorization);
}

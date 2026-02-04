package com.newtouch;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.Date;

public class SimpleWorklogJob implements WorklogJob{


    /**
     * 填写某天的日报
     *
     * @param workDate        工作日
     * @param workDescription 工作内容描述
     */
    @Override
    public void execDayWork(Date workDate, String workDescription,String authorization) {
        Assert.notEmpty(workDescription,"工作描述必填");
        Assert.notNull(workDate);
        Assert.notEmpty(authorization);
        String body= """
                {
                    "id": "",
                    "depId": "J",
                    "departmentId": "68cfbf6d-f4be-11ee-89b1-fa163ea58b38",
                    "thirdDepId": "J",
                    "reportDate": "%s",
                    "workFrom": "08:30",
                    "workTo": "17:30",
                    "action1Id": "daac19c00be76602182f3354a3d8eb05",
                    "action2Id": "7c002ed77fd24a6c0f98b92e4a075a58",
                    "actionFirstId": "0106",
                    "actionSecondId": "010601",
                    "workDesc1": "%s",
                    "workDesc2": "PC",
                    "workHours": 9,
                    "isHaveProject": "有",
                    "proRecordId": 17976,
                    "proId": "17976",
                    "timeType": 1
                }
                """.formatted(DateUtil.formatDate(workDate),workDescription);
        String result = HttpUtil.createPost("https://eds.newtouch.com/api/workReport/add").auth(authorization).body(body).execute().body();
        String code = JSONUtil.parse(result).getByPath("code", String.class);
        if (!"200".equals(code)) {
            System.err.println(workDate+"->"+result);
        }else {
            System.out.println(workDate+"->"+result);
        }
    }

    /**
     * 填写周的周报
     *
     * @param workDate        工作周
     * @param workDescription oms客户画像需求接口
     */
    @Override
    public void execWeekWork(Date workDate, String workDescription,String authorization) {
        Assert.notEmpty(workDescription,"工作描述必填");
        Assert.notNull(workDate);
        Assert.equals(2,DateUtil.dayOfWeek(workDate),"日期必须是周一");
        Assert.notEmpty(authorization);
        String body= """
                {
                     "weekreportdate": "%s",
                     "id": "7c5ec892f1b0dff70d079319d5e0b632",
                     "unfinishwork": "上周工作任务已经完成，并且主要是java开发相关工作内容。",
                     "workproblem": "上周学习完成任务已经完成，学习项目相关业务知识。",
                     "remark": "通过参与项目%s的开发和学习，获取java开发经验和项目管理经验的收获",
                     "arrangement": "项目%s，继续开发",
                     "planwork": "继续学习业务知识，深入了解天弘基金固定收益投资交易系统的主要功能模块，掌握固定收益投资中的常见交易策略及其在系统中的实现",
                     "weekstate": "1"
                 }
                """.formatted(DateUtil.formatDate(workDate),workDescription,workDescription);
        String result = HttpUtil.createPost("https://eds.newtouch.com/api/weekReport/submit").auth(authorization).body(body).execute().body();
        String code = JSONUtil.parse(result).getByPath("code", String.class);
        if (!"200".equals(code)) {
            System.err.println(workDate+"->"+result);
        }else {
            System.out.println(workDate+"->"+result);
        }
    }
}

package com.system.glue.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.glue.domain.process.GlueSysControl;

import java.util.Date;

/**
 * @author Dandan
 * @date 2023/11/29 10:30
 **/
public class GlueSysControlInfo extends GlueSysControl {

    /**
     * 颜色
     */
    private String msgColor;

    /**
     * 胶水开始使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date glueStartUseTime;

    /**
     * 胶水已经使用时间
     */
    private Long glueAlreadyUseTime;

    /**
     * 警示信息
     */
    private String alarmMsg;

    public Long getGlueAlreadyUseTime() {
        return glueAlreadyUseTime;
    }

    public void setGlueAlreadyUseTime(Long glueAlreadyUseTime) {
        this.glueAlreadyUseTime = glueAlreadyUseTime;
    }

    public Date getGlueStartUseTime() {
        return glueStartUseTime;
    }


    public void setGlueStartUseTime(Date glueStartUseTime) {
        this.glueStartUseTime = glueStartUseTime;
    }

    public String getMsgColor() {
        return msgColor;
    }

    public void setMsgColor(String msgColor) {
        this.msgColor = msgColor;
    }

    public String getAlarmMsg() {
        return alarmMsg;
    }

    public void setAlarmMsg(String alarmMsg) {
        this.alarmMsg = alarmMsg;
    }
}

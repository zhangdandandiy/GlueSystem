package com.system.glue.domain;

import java.util.Date;

/**
 * 胶水预警&报警信息日志对象
 *
 * @author Dandan
 * @date 2023/11/20 15:15
 **/
public class GlueSysMonitorLog {

    /**
     * 监控日志表的主键id，自增
     */
    private Long id;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

    /**
     * 该管胶水被用于哪台点胶机
     */
    private String glueMachineName;

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

    /**
     * 该管胶水被用于哪条线
     */
    private String lineName;

    /**
     * 报警消息
     */
    private String alarmMsg;

    /**
     * 消息类型 0回温预警/1回温报警/2解冻报警/3脱泡报警/4线上使用预警/5线上使用报警
     */
    private int type;

    /**
     * 判断这条消息是否发送过企业微信 0 未发送 1 已发送
     */
    private int status;

    /**
     * 记录创建时间
     */
    private Date createTime;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGlueBarCode(String glueBarCode) {
        this.glueBarCode = glueBarCode;
    }

    public String getGlueBarCode() {
        return glueBarCode;
    }

    public void setGlueMachineName(String glueMachineName) {
        this.glueMachineName = glueMachineName;
    }

    public String getGlueMachineName() {
        return glueMachineName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setAlarmMsg(String alarmMsg) {
        this.alarmMsg = alarmMsg;
    }

    public String getAlarmMsg() {
        return alarmMsg;
    }

}

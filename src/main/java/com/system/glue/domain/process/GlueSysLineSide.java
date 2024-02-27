package com.system.glue.domain.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 线边仓胶水实体类
 *
 * @author Dandan
 * @date 2023/11/20 12:43
 **/
public class GlueSysLineSide {

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

    /**
     * 该管胶水被用于哪条线
     */
    private String lineName;

    /**
     * 该管胶水被用于哪台点胶机
     */
    private String glueMachineName;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

    /**
     * 胶水常温解冻的开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unfreezeStartTime;

    /**
     * 胶水剩余寿命
     */
    private Long remainLife;

    /**
     * 胶水出库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outStorageTime;

    public void setOutStorageTime(Date outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public Date getOutStorageTime() {
        return outStorageTime;
    }

    public Date getUnfreezeStartTime() {
        return unfreezeStartTime;
    }

    public void setUnfreezeStartTime(Date unfreezeStartTime) {
        this.unfreezeStartTime = unfreezeStartTime;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setGlueBarCode(String glueBarCode) {
        this.glueBarCode = glueBarCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getGlueBarCode() {
        return glueBarCode;
    }

    public String getGlueMachineName() {
        return glueMachineName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setGlueMachineName(String glueMachineName) {
        this.glueMachineName = glueMachineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Long getRemainLife() {
        return remainLife;
    }

    public void setRemainLife(Long remainLife) {
        this.remainLife = remainLife;
    }

}

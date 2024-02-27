package com.system.glue.domain.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 冰箱回温胶水展示列表
 *
 * @author Dandan
 * @date 2023/11/16 14:16
 **/
public class GlueSysThawing {

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

    /**
     * 胶水开始回温的时间(扫码获取)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date thawingStartTime;

    /**
     * 胶水结束回温的时间(扫码获取)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date thawingEndTime;

    /**
     * 胶水回温需要的时间(min)
     */
    private Long thawingNeedTime;

    /**
     * 胶水已经回温的时间(min)
     */
    private Long alreadyThawingTime;

    /**
     * 胶水剩余回温的时间(min)
     */
    private Long remainThawingTime;

    /**
     * 胶水是否需要在冰箱回温 true/false
     */
    private String isNeedThawing;

    /**
     * 回温超过时间
     */
    private Long exceedThawingTime;

    public Long getExceedThawingTime() {
        return exceedThawingTime;
    }

    public void setExceedThawingTime(Long exceedThawingTime) {
        this.exceedThawingTime = exceedThawingTime;
    }

    public void setIsNeedThawing(String isNeedThawing) {
        this.isNeedThawing = isNeedThawing;
    }

    public String getIsNeedThawing() {
        return isNeedThawing;
    }

    public Date getThawingEndTime() {
        return thawingEndTime;
    }

    public void setThawingEndTime(Date thawingEndTime) {
        this.thawingEndTime = thawingEndTime;
    }

    public Long getAlreadyThawingTime() {
        return alreadyThawingTime;
    }

    public Date getThawingStartTime() {
        return thawingStartTime;
    }

    public Long getRemainThawingTime() {
        return remainThawingTime;
    }

    public Long getThawingNeedTime() {
        return thawingNeedTime;
    }

    public String getGlueBarCode() {
        return glueBarCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setAlreadyThawingTime(Long alreadyThawingTime) {
        this.alreadyThawingTime = alreadyThawingTime;
    }

    public void setGlueBarCode(String glueBarCode) {
        this.glueBarCode = glueBarCode;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setRemainThawingTime(Long remainThawingTime) {
        this.remainThawingTime = remainThawingTime;
    }

    public void setThawingNeedTime(Long thawingNeedTime) {
        this.thawingNeedTime = thawingNeedTime;
    }

    public void setThawingStartTime(Date thawingStartTime) {
        this.thawingStartTime = thawingStartTime;
    }

}

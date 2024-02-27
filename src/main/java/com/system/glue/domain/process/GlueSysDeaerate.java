package com.system.glue.domain.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 脱泡胶水实体类
 *
 * @author Dandan
 * @date 2023/11/16 19:17
 **/
public class GlueSysDeaerate {

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

    /**
     * 胶水是否需要脱泡
     */
    private String isNeedDeaerate;

    /**
     * 胶水入库时间，插入这个表即为入库
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inStorageTime;

    /**
     * 胶水开始脱泡的时间(扫码获取)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deaerateStartTime;

    /**
     * 胶水开始脱泡的时间(扫码获取)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deaerateEndTime;

    /**
     * 胶水脱泡需要的时间(min)
     */
    private Long deaerateNeedTime;

    /**
     * 胶水已经脱泡的时间(min)
     */
    private Long alreadyDeaerateTime;

    /**
     * 胶水剩余脱泡的时间(min)
     */
    private Long remainDeaerateTime;

    /**
     * 超过脱泡时间
     */
    private Long exceedDeaerateTime;

    public Long getExceedDeaerateTime() {
        return exceedDeaerateTime;
    }

    public void setExceedDeaerateTime(Long exceedDeaerateTime) {
        this.exceedDeaerateTime = exceedDeaerateTime;
    }

    public void setIsNeedDeaerate(String isNeedDeaerate) {
        this.isNeedDeaerate = isNeedDeaerate;
    }

    public String getIsNeedDeaerate() {
        return isNeedDeaerate;
    }

    public void setDeaerateEndTime(Date deaerateEndTime) {
        this.deaerateEndTime = deaerateEndTime;
    }

    public Date getDeaerateEndTime() {
        return deaerateEndTime;
    }

    public void setInStorageTime(Date inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public Date getInStorageTime() {
        return inStorageTime;
    }

    public String getGlueBarCode() {
        return glueBarCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setGlueBarCode(String glueBarCode) {
        this.glueBarCode = glueBarCode;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getDeaerateStartTime() {
        return deaerateStartTime;
    }

    public Long getAlreadyDeaerateTime() {
        return alreadyDeaerateTime;
    }

    public Long getDeaerateNeedTime() {
        return deaerateNeedTime;
    }

    public Long getRemainDeaerateTime() {
        return remainDeaerateTime;
    }

    public void setAlreadyDeaerateTime(Long alreadyDeaerateTime) {
        this.alreadyDeaerateTime = alreadyDeaerateTime;
    }

    public void setDeaerateNeedTime(Long deaerateNeedTime) {
        this.deaerateNeedTime = deaerateNeedTime;
    }

    public void setDeaerateStartTime(Date deaerateStartTime) {
        this.deaerateStartTime = deaerateStartTime;
    }

    public void setRemainDeaerateTime(Long remainDeaerateTime) {
        this.remainDeaerateTime = remainDeaerateTime;
    }
}

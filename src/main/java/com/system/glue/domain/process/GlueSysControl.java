package com.system.glue.domain.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 线体寿命管控实体类
 *
 * @author Dandan
 * @date 2023/11/18 14:30
 **/
public class GlueSysControl {

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
     * 胶水是否需要在冰箱回温 true/false
     */
    private String isNeedThawing;

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
     * 胶水回温需要的时间
     */
    private Long thawingNeedTime;

    /**
     * 胶水回温花费的时间(min)
     */
    private Long thawingUsedTime;

    /**
     * 胶水常温解冻的开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unfreezeStartTime;

    /**
     * 胶水是否需要常温解冻 true/false
     */
    private String isNeedUnfreeze;

    /**
     * 胶水是否需要脱泡
     */
    private String isNeedDeaerate;

    /**
     * 胶水常温解冻的结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unfreezeEndTime;

    /**
     * 降水常温解冻需要的时间
     */
    private Long unfreezeNeedTime;

    /**
     * 胶水常温解冻花费的时间
     */
    private Long unfreezeUsedTime;

    /**
     * 胶水脱泡的开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deaerateStartTime;

    /**
     * 胶水脱泡的结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deaerateEndTime;

    /**
     * 胶水脱泡需要的时间
     */
    private Long deaerateNeedTime;

    /**
     * 脱泡花费的时间
     */
    private Long deaerateUsedTime;

    /**
     * 上线开始使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lineStartTime;

    /**
     * 上线结束使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lineEndTime;

    /**
     * 线上使用时长
     */
    private Long lineUsedTime;

    /**
     * 胶水预警寿命 单位 min
     */
    private Long warnLifeTime;

    /**
     * 胶水报警寿命
     */
    private Long alarmLifeTime;

    /**
     * 胶水出库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outStorageTime;

    public Date getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(Date outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public String getIsNeedDeaerate() {
        return isNeedDeaerate;
    }

    public String getIsNeedThawing() {
        return isNeedThawing;
    }

    public String getIsNeedUnfreeze() {
        return isNeedUnfreeze;
    }

    public void setIsNeedDeaerate(String isNeedDeaerate) {
        this.isNeedDeaerate = isNeedDeaerate;
    }

    public void setIsNeedThawing(String isNeedThawing) {
        this.isNeedThawing = isNeedThawing;
    }

    public void setIsNeedUnfreeze(String isNeedUnfreeze) {
        this.isNeedUnfreeze = isNeedUnfreeze;
    }

    public Long getWarnLifeTime() {
        return warnLifeTime;
    }

    public Long getAlarmLifeTime() {
        return alarmLifeTime;
    }

    public void setAlarmLifeTime(Long alarmLifeTime) {
        this.alarmLifeTime = alarmLifeTime;
    }

    public void setWarnLifeTime(Long warnLifeTime) {
        this.warnLifeTime = warnLifeTime;
    }

    public void setThawingEndTime(Date thawingEndTime) {
        this.thawingEndTime = thawingEndTime;
    }

    public Date getThawingEndTime() {
        return thawingEndTime;
    }

    public Date getDeaerateEndTime() {
        return deaerateEndTime;
    }

    public Date getUnfreezeEndTime() {
        return unfreezeEndTime;
    }

    public void setDeaerateEndTime(Date deaerateEndTime) {
        this.deaerateEndTime = deaerateEndTime;
    }

    public void setUnfreezeEndTime(Date unfreezeEndTime) {
        this.unfreezeEndTime = unfreezeEndTime;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setGlueMachineName(String glueMachineName) {
        this.glueMachineName = glueMachineName;
    }

    public String getLineName() {
        return lineName;
    }

    public String getGlueMachineName() {
        return glueMachineName;
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

    public void setDeaerateStartTime(Date deaerateStartTime) {
        this.deaerateStartTime = deaerateStartTime;
    }

    public void setDeaerateNeedTime(Long deaerateNeedTime) {
        this.deaerateNeedTime = deaerateNeedTime;
    }

    public Long getDeaerateNeedTime() {
        return deaerateNeedTime;
    }

    public Date getDeaerateStartTime() {
        return deaerateStartTime;
    }

    public Date getThawingStartTime() {
        return thawingStartTime;
    }

    public void setUnfreezeStartTime(Date unfreezeStartTime) {
        this.unfreezeStartTime = unfreezeStartTime;
    }

    public void setUnfreezeNeedTime(Long unfreezeNeedTime) {
        this.unfreezeNeedTime = unfreezeNeedTime;
    }

    public Long getUnfreezeNeedTime() {
        return unfreezeNeedTime;
    }

    public Date getUnfreezeStartTime() {
        return unfreezeStartTime;
    }

    public void setThawingStartTime(Date thawingStartTime) {
        this.thawingStartTime = thawingStartTime;
    }

    public void setThawingNeedTime(Long thawingNeedTime) {
        this.thawingNeedTime = thawingNeedTime;
    }

    public Long getThawingNeedTime() {
        return thawingNeedTime;
    }

    public void setDeaerateUsedTime(Long deaerateUsedTime) {
        this.deaerateUsedTime = deaerateUsedTime;
    }

    public Long getDeaerateUsedTime() {
        return deaerateUsedTime;
    }

    public Date getLineEndTime() {
        return lineEndTime;
    }

    public Date getLineStartTime() {
        return lineStartTime;
    }

    public Long getLineUsedTime() {
        return lineUsedTime;
    }

    public Long getThawingUsedTime() {
        return thawingUsedTime;
    }

    public Long getUnfreezeUsedTime() {
        return unfreezeUsedTime;
    }

    public void setLineEndTime(Date lineEndTime) {
        this.lineEndTime = lineEndTime;
    }

    public void setLineStartTime(Date lineStartTime) {
        this.lineStartTime = lineStartTime;
    }

    public void setLineUsedTime(Long lineUsedTime) {
        this.lineUsedTime = lineUsedTime;
    }

    public void setThawingUsedTime(Long thawingUsedTime) {
        this.thawingUsedTime = thawingUsedTime;
    }

    public void setUnfreezeUsedTime(Long unfreezeUsedTime) {
        this.unfreezeUsedTime = unfreezeUsedTime;
    }
}

package com.system.glue.domain.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 常温解冻胶水展示列表
 * @author Dandan
 * @date 2023/11/16 18:19
 **/
public class GlueSysUnfreeze {

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

    /**
     * 胶水入库时间，插入这个表即为入库
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inStorageTime;

    /**
     * 胶水开始常温解冻的时间(扫码获取)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unfreezeStartTime;

    /**
     * 胶水开始常温解冻的时间(扫码获取)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unfreezeEndTime;

    /**
     * 胶水常温解冻需要的时间(min)
     */
    private Long unfreezeNeedTime;

    /**
     * 胶水已经常温解冻的时间(min)
     */
    private Long alreadyUnfreezeTime;

    /**
     * 胶水是否需要常温解冻 true/false
     */
    private String isNeedUnfreeze;

    /**
     * 胶水剩余常温解冻的时间(min)
     */
    private Long remainUnfreezeTime;

    /**
     * 胶水超过常温解冻时间
     */
    private Long exceedUnfreezeTime;

    public Long getExceedUnfreezeTime() {
        return exceedUnfreezeTime;
    }

    public void setExceedUnfreezeTime(Long exceedUnfreezeTime) {
        this.exceedUnfreezeTime = exceedUnfreezeTime;
    }

    public void setUnfreezeEndTime(Date unfreezeEndTime) {
        this.unfreezeEndTime = unfreezeEndTime;
    }

    public Date getUnfreezeEndTime() {
        return unfreezeEndTime;
    }

    public void setIsNeedUnfreeze(String isNeedUnfreeze) {
        this.isNeedUnfreeze = isNeedUnfreeze;
    }

    public String getIsNeedUnfreeze() {
        return isNeedUnfreeze;
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

    public Date getInStorageTime() {
        return inStorageTime;
    }

    public Date getUnfreezeStartTime() {
        return unfreezeStartTime;
    }

    public Long getAlreadyUnfreezeTime() {
        return alreadyUnfreezeTime;
    }

    public Long getRemainUnfreezeTime() {
        return remainUnfreezeTime;
    }

    public Long getUnfreezeNeedTime() {
        return unfreezeNeedTime;
    }

    public void setAlreadyUnfreezeTime(Long alreadyUnfreezeTime) {
        this.alreadyUnfreezeTime = alreadyUnfreezeTime;
    }

    public void setInStorageTime(Date inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public void setRemainUnfreezeTime(Long remainUnfreezeTime) {
        this.remainUnfreezeTime = remainUnfreezeTime;
    }

    public void setUnfreezeNeedTime(Long unfreezeNeedTime) {
        this.unfreezeNeedTime = unfreezeNeedTime;
    }

    public void setUnfreezeStartTime(Date unfreezeStartTime) {
        this.unfreezeStartTime = unfreezeStartTime;
    }

}

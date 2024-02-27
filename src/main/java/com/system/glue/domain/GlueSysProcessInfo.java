package com.system.glue.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 胶水流程内信息对象 glue_sys_process_info
 *
 * @author Dandan
 * @date 2023/11/15 16:07
 **/
public class GlueSysProcessInfo {

    /**
     * 胶水流程信息表的主键id，自增
     */
    private Long id;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

    /**
     * 从胶水码中解析的厂商码/供应商码
     */
    private String supplierCode;

    /**
     * 从胶水码中解析出来的料号
     */
    private String materialCode;

    /**
     * 从胶水码中解析出来的胶水的生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date productionDate;

    /**
     * 从胶水码中解析出来的购买日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shoppingDate;

    /**
     * 从胶水码中解析出来的胶水的保质期，单位为月(M)
     */
    private String shelfLife;

    /**
     * 从胶水码中解析出来的胶水的重量
     */
    private String weight;

    /**
     * 从胶水码中解析出来的胶水的内部码
     */
    private String selfCode;

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
     * 胶水是否已经入库 true/false
     */
    private String isInStorage;

    /**
     * 胶水入库时间，插入这个表即为入库
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inStorageTime;

    /**
     * 胶水是否需要在冰箱回温 true/false
     */
    private String isNeedThawing;

    /**
     * 胶水回温需要的时间
     */
    private Long thawingNeedTime;

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
     * 胶水回温花费的时间(min)
     */
    private Long thawingUsedTime;

    /**
     * 胶水是否需要常温解冻 true/false
     */
    private String isNeedUnfreeze;

    /**
     * 降水常温解冻需要的时间
     */
    private Long unfreezeNeedTime;

    /**
     * 胶水常温解冻的开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unfreezeStartTime;

    /**
     * 胶水常温解冻的结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unfreezeEndTime;

    /**
     * 胶水常温解冻花费的时间
     */
    private Long unfreezeUsedTime;

    /**
     * 胶水是否需要脱泡
     */
    private String isNeedDeaerate;

    /**
     * 胶水脱泡需要的时间
     */
    private Long deaerateNeedTime;

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
     * 脱泡花费的时间
     */
    private Long deaerateUsedTime;

    /**
     * 胶水是否已经出库
     */
    private String isOutStorage;

    /**
     * 胶水出库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outStorageTime;

    /**
     * 胶水是否上线使用 true/false
     */
    private String isInLine;

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
     * 胶水是否已注销 true/false
     */
    private String isCancel;

    /**
     * 胶水注销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cancelTime;

    public void setDeaerateUsedTime(Long deaerateUsedTime) {
        this.deaerateUsedTime = deaerateUsedTime;
    }

    public Long getDeaerateUsedTime() {
        return deaerateUsedTime;
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

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setShoppingDate(Date shoppingDate) {
        this.shoppingDate = shoppingDate;
    }

    public Date getShoppingDate() {
        return shoppingDate;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public void setSelfCode(String selfCode) {
        this.selfCode = selfCode;
    }

    public String getSelfCode() {
        return selfCode;
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

    public void setGlueMachineName(String glueMachineName) {
        this.glueMachineName = glueMachineName;
    }

    public String getGlueMachineName() {
        return glueMachineName;
    }

    public void setIsInStorage(String isInStorage) {
        this.isInStorage = isInStorage;
    }

    public String getIsInStorage() {
        return isInStorage;
    }

    public void setInStorageTime(Date inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public Date getInStorageTime() {
        return inStorageTime;
    }

    public void setIsNeedThawing(String isNeedThawing) {
        this.isNeedThawing = isNeedThawing;
    }

    public String getIsNeedThawing() {
        return isNeedThawing;
    }

    public void setThawingNeedTime(Long thawingNeedTime) {
        this.thawingNeedTime = thawingNeedTime;
    }

    public Long getThawingNeedTime() {
        return thawingNeedTime;
    }

    public void setThawingStartTime(Date thawingStartTime) {
        this.thawingStartTime = thawingStartTime;
    }

    public Date getThawingStartTime() {
        return thawingStartTime;
    }

    public void setThawingEndTime(Date thawingEndTime) {
        this.thawingEndTime = thawingEndTime;
    }

    public Date getThawingEndTime() {
        return thawingEndTime;
    }

    public void setThawingUsedTime(Long thawingUsedTime) {
        this.thawingUsedTime = thawingUsedTime;
    }

    public Long getThawingUsedTime() {
        return thawingUsedTime;
    }

    public void setIsNeedUnfreeze(String isNeedUnfreeze) {
        this.isNeedUnfreeze = isNeedUnfreeze;
    }

    public String getIsNeedUnfreeze() {
        return isNeedUnfreeze;
    }

    public void setUnfreezeNeedTime(Long unfreezeNeedTime) {
        this.unfreezeNeedTime = unfreezeNeedTime;
    }

    public Long getUnfreezeNeedTime() {
        return unfreezeNeedTime;
    }

    public void setUnfreezeStartTime(Date unfreezeStartTime) {
        this.unfreezeStartTime = unfreezeStartTime;
    }

    public Date getUnfreezeStartTime() {
        return unfreezeStartTime;
    }

    public void setUnfreezeEndTime(Date unfreezeEndTime) {
        this.unfreezeEndTime = unfreezeEndTime;
    }

    public Date getUnfreezeEndTime() {
        return unfreezeEndTime;
    }

    public void setUnfreezeUsedTime(Long unfreezeUsedTime) {
        this.unfreezeUsedTime = unfreezeUsedTime;
    }

    public Long getUnfreezeUsedTime() {
        return unfreezeUsedTime;
    }

    public void setIsNeedDeaerate(String isNeedDeaerate) {
        this.isNeedDeaerate = isNeedDeaerate;
    }

    public String getIsNeedDeaerate() {
        return isNeedDeaerate;
    }

    public void setDeaerateNeedTime(Long deaerateNeedTime) {
        this.deaerateNeedTime = deaerateNeedTime;
    }

    public Long getDeaerateNeedTime() {
        return deaerateNeedTime;
    }

    public void setDeaerateStartTime(Date deaerateStartTime) {
        this.deaerateStartTime = deaerateStartTime;
    }

    public Date getDeaerateStartTime() {
        return deaerateStartTime;
    }

    public void setDeaerateEndTime(Date deaerateEndTime) {
        this.deaerateEndTime = deaerateEndTime;
    }

    public Date getDeaerateEndTime() {
        return deaerateEndTime;
    }

    public void setIsOutStorage(String isOutStorage) {
        this.isOutStorage = isOutStorage;
    }

    public String getIsOutStorage() {
        return isOutStorage;
    }

    public void setOutStorageTime(Date outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public Date getOutStorageTime() {
        return outStorageTime;
    }

    public void setIsInLine(String isInLine) {
        this.isInLine = isInLine;
    }

    public String getIsInLine() {
        return isInLine;
    }

    public void setLineStartTime(Date lineStartTime) {
        this.lineStartTime = lineStartTime;
    }

    public Date getLineStartTime() {
        return lineStartTime;
    }

    public void setLineEndTime(Date lineEndTime) {
        this.lineEndTime = lineEndTime;
    }

    public Date getLineEndTime() {
        return lineEndTime;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

}

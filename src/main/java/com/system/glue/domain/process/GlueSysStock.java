package com.system.glue.domain.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 库存胶水实体类
 *
 * @author Dandan
 * @date 2023/11/20 13:10
 **/
public class GlueSysStock {

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

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
     * 胶水入库时间，插入这个表即为入库
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inStorageTime;

    /**
     * 胶水剩余寿命
     */
    private Long remainLife;

    public void setRemainLife(Long remainLife) {
        this.remainLife = remainLife;
    }

    public Long getRemainLife() {
        return remainLife;
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

    public Date getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(Date inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public Date getShoppingDate() {
        return shoppingDate;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public void setShoppingDate(Date shoppingDate) {
        this.shoppingDate = shoppingDate;
    }
}

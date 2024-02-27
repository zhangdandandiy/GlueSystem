package com.system.glue.domain.process;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 在库胶水实体类
 *
 * @author Dandan
 * @date 2023/11/16 14:17
 **/
public class GlueSysInStorage {

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

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
     * 胶水入库时间，插入这个表即为入库
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inStorageTime;

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

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setInStorageTime(Date inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public Date getInStorageTime() {
        return inStorageTime;
    }

}

package com.system.glue.domain.dto;

/**
 * @author Dandan
 * @date 2023/11/17 15:40
 **/
public class GlueSysOutStorageParam {

    /**
     * 该管胶水被用于哪个专案
     */
    private String projectName;

    /**
     * 入库扫描的胶水码
     */
    private String glueBarCode;

    /**
     * 该管胶水被用于哪条线
     */
    private String lineName;

    /**
     * 该管胶水被用于哪台点胶机
     */
    private String glueMachineName;

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

}

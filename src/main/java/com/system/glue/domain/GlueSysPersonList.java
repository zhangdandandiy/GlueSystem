package com.system.glue.domain;

import java.util.Date;

/**
 * 胶水报警信息发送人员名单对象 glue_sys_person_list
 *
 * @author Dandan
 * @date 2023/11/15 15:47
 **/
public class GlueSysPersonList {

    /**
     * 人员维护名单表的主键id，自增
     */
    private Long id;

    /**
     * 专案名称，例如 WZ-A87
     */
    private String projectName;

    /**
     * 姓名
     */
    private String personName;

    /**
     * 工号
     */
    private String personNumber;

    /**
     * 创建时间
     */
    private Date createTime;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getPersonNumber() {
        return personNumber;
    }

}

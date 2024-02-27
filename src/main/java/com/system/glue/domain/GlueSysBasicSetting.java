package com.system.glue.domain;

/**
 * 胶水基础设置对象 glue_sys_basic_setting
 *
 * @author Dandan
 * @date 2023/11/15 14:57
 **/
public class GlueSysBasicSetting {

    /**
     * 胶水基础设置表的id，自增
     */
    private Long id;

    /**
     * 专案名称，例如 WZ-A87
     */
    private String projectName;

    /**
     * 线体数量，整数型 1/2/3/4/5……
     */
    private Long lineNumber;

    /**
     * 点胶机数量 1/2/3/4/5……
     */
    private Long glueMachineNumber;

    /**
     * 编码格式 ICT编码/Henkel码
     */
    private String encodingFormat;

    /**
     * 厂商/供应商代码
     */
    private String supplierCode;

    /**
     * 料号
     */
    private String materialCode;

    /**
     * 胶水是否需要在冰箱回温 true/false
     */
    private String isNeedThawing;

    /**
     * 冰箱回温需要的时间上限
     */
    private Long thawingTimeUpper;

    /**
     * 冰箱回温需要的时间下限
     */
    private Long thawingTimeLower;

    /**
     * 胶水是否需要常温解冻 true/false
     */
    private String isNeedUnfreeze;

    /**
     * 解冻需要的时间上限
     */
    private Long unfreezeTimeUpper;

    /**
     * 解冻需要的时间下限
     */
    private Long unfreezeTimeLower;

    /**
     * 胶水是否需要脱泡
     */
    private String isNeedDeaerate;

    /**
     * 脱泡需要的时间上限
     */
    private Long deaerateTimeUpper;

    /**
     * 脱泡需要的时间下限
     */
    private Long deaerateTimeLower;

    /**
     * 胶水预警寿命 单位 min
     */
    private Long warnLifeTime;

    /**
     * 胶水报警寿命
     */
    private Long alarmLifeTime;

    /**
     * 胶水可使用寿命
     */
    private Long useLifeTime;

    /**
     * 是否需要实时监控，实时监控的意义在于推送企业微信信息，true/false
     */
    private String isNeedRealTimeMonitor;

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

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setGlueMachineNumber(Long glueMachineNumber) {
        this.glueMachineNumber = glueMachineNumber;
    }

    public Long getGlueMachineNumber() {
        return glueMachineNumber;
    }

    public void setEncodingFormat(String encodingFormat) {
        this.encodingFormat = encodingFormat;
    }

    public String getEncodingFormat() {
        return encodingFormat;
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

    public void setIsNeedThawing(String isNeedThawing) {
        this.isNeedThawing = isNeedThawing;
    }

    public String getIsNeedThawing() {
        return isNeedThawing;
    }

    public void setThawingTimeUpper(Long thawingTimeUpper) {
        this.thawingTimeUpper = thawingTimeUpper;
    }

    public Long getThawingTimeUpper() {
        return thawingTimeUpper;
    }

    public void setThawingTimeLower(Long thawingTimeLower) {
        this.thawingTimeLower = thawingTimeLower;
    }

    public Long getThawingTimeLower() {
        return thawingTimeLower;
    }

    public void setIsNeedUnfreeze(String isNeedUnfreeze) {
        this.isNeedUnfreeze = isNeedUnfreeze;
    }

    public String getIsNeedUnfreeze() {
        return isNeedUnfreeze;
    }

    public void setUnfreezeTimeUpper(Long unfreezeTimeUpper) {
        this.unfreezeTimeUpper = unfreezeTimeUpper;
    }

    public Long getUnfreezeTimeUpper() {
        return unfreezeTimeUpper;
    }

    public void setUnfreezeTimeLower(Long unfreezeTimeLower) {
        this.unfreezeTimeLower = unfreezeTimeLower;
    }

    public Long getUnfreezeTimeLower() {
        return unfreezeTimeLower;
    }

    public void setIsNeedDeaerate(String isNeedDeaerate) {
        this.isNeedDeaerate = isNeedDeaerate;
    }

    public String getIsNeedDeaerate() {
        return isNeedDeaerate;
    }

    public void setDeaerateTimeUpper(Long deaerateTimeUpper) {
        this.deaerateTimeUpper = deaerateTimeUpper;
    }

    public Long getDeaerateTimeUpper() {
        return deaerateTimeUpper;
    }

    public void setDeaerateTimeLower(Long deaerateTimeLower) {
        this.deaerateTimeLower = deaerateTimeLower;
    }

    public Long getDeaerateTimeLower() {
        return deaerateTimeLower;
    }

    public void setWarnLifeTime(Long warnLifeTime) {
        this.warnLifeTime = warnLifeTime;
    }

    public Long getWarnLifeTime() {
        return warnLifeTime;
    }

    public void setAlarmLifeTime(Long alarmLifeTime) {
        this.alarmLifeTime = alarmLifeTime;
    }

    public Long getAlarmLifeTime() {
        return alarmLifeTime;
    }

    public void setUseLifeTime(Long useLifeTime) {
        this.useLifeTime = useLifeTime;
    }

    public Long getUseLifeTime() {
        return useLifeTime;
    }

    public void setIsNeedRealTimeMonitor(String isNeedRealTimeMonitor) {
        this.isNeedRealTimeMonitor = isNeedRealTimeMonitor;
    }

    public String getIsNeedRealTimeMonitor() {
        return isNeedRealTimeMonitor;
    }

}

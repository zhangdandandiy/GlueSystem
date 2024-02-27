package com.system.glue.domain;

/**
 * 胶水注销信息对象 glue_sys_cancel_info
 *
 * @author Dandan
 * @date 2023/11/16 8:08
 **/
public class GlueSysCancelInfo extends GlueSysProcessInfo {

    /**
     * 线上使用时间 = 注销时间 - 开始上线时间
     */
    private Long onlineUsedTime;

    /**
     * 实际使用寿命
     * 1、如果有回温(有回温一定有解冻)
     * 实际使用寿命 = 注销时间 - 开始回温时间
     * 2、如果只有解冻
     * 实际使用寿命 = 注销时间 - 开始解冻时间
     * 3、如果既没回温也没解冻
     * 实际使用寿命 = 注销时间 - 出库时间
     */
    private Long actualLife;

    public void setOnlineUsedTime(Long onlineUsedTime) {
        this.onlineUsedTime = onlineUsedTime;
    }

    public Long getOnlineUsedTime() {
        return onlineUsedTime;
    }

    public void setActualLife(Long actualLife) {
        this.actualLife = actualLife;
    }

    public Long getActualLife() {
        return actualLife;
    }

}

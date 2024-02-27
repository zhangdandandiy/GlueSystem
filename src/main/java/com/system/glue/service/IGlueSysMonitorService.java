package com.system.glue.service;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/20 14:48
 **/
public interface IGlueSysMonitorService {

    /**
     * 获取所有需要监控条码的信息
     *
     * @return
     */
    public List<String> getGlueMsgLogByGlueBarCode();

}

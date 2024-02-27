package com.system.glue.mapper;

import com.system.glue.domain.GlueSysMonitorLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/20 15:16
 **/

@Mapper
public interface GlueSysMonitorLogMapper {

    /**
     * 修改胶水预警&报警信息日志
     *
     * @param glueSysMonitorLog 胶水预警&报警信息日志
     * @return 结果
     */
    public int updateGlueSysMonitorLog(GlueSysMonitorLog glueSysMonitorLog);

    /**
     * 查询胶水预警&报警信息日志
     *
     * @param glueBarCode 胶水预警&报警信息日志主键
     * @return 胶水预警&报警信息日志
     */
    public GlueSysMonitorLog selectGlueSysMonitorLogByGlueBarCode(String glueBarCode);

    /**
     * 根据胶水码和消息类型查询信息日志
     *
     * @param glueBarCode
     * @param type
     * @return
     */
    public GlueSysMonitorLog selectGlueSysMonitorLogByType(String glueBarCode, String type);

    /**
     * 查询胶水预警&报警信息日志列表
     *
     * @param glueSysMonitorLog 胶水预警&报警信息日志
     * @return 胶水预警&报警信息日志集合
     */
    public List<GlueSysMonitorLog> selectGlueSysMonitorLogList(GlueSysMonitorLog glueSysMonitorLog);

    /**
     * 通过专案查询胶水未被发送的日志列表
     * @param projectName
     * @return
     */
    public List<GlueSysMonitorLog> selectGlueSysMonitorLogByProjectName(String projectName);

    /**
     * 新增胶水预警&报警信息日志
     *
     * @param glueSysMonitorLog 胶水预警&报警信息日志
     * @return 结果
     */
    public int insertGlueSysMonitorLog(GlueSysMonitorLog glueSysMonitorLog);

}

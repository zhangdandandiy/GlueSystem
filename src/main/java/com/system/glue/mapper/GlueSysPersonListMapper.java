package com.system.glue.mapper;

import com.system.glue.domain.GlueSysPersonList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 胶水报警信息发送人员名单Mapper接口
 *
 * @author Dandan
 * @date 2023/11/15 15:49
 **/

@Mapper
public interface GlueSysPersonListMapper {

    /**
     * 查询胶水报警信息发送人员名单
     *
     * @param id 胶水报警信息发送人员名单主键
     * @return 胶水报警信息发送人员名单
     */
    public GlueSysPersonList selectGlueSysPersonListById(Long id);

    /**
     * 查询胶水报警信息发送人员名单列表
     *
     * @param glueSysPersonList 胶水报警信息发送人员名单
     * @return 胶水报警信息发送人员名单集合
     */
    public List<GlueSysPersonList> selectGlueSysPersonListList(GlueSysPersonList glueSysPersonList);

    /**
     * 新增胶水报警信息发送人员名单
     *
     * @param glueSysPersonList 胶水报警信息发送人员名单
     * @return 结果
     */
    public int insertGlueSysPersonList(GlueSysPersonList glueSysPersonList);

    /**
     * 修改胶水报警信息发送人员名单
     *
     * @param glueSysPersonList 胶水报警信息发送人员名单
     * @return 结果
     */
    public int updateGlueSysPersonList(GlueSysPersonList glueSysPersonList);

    /**
     * 删除胶水报警信息发送人员名单
     *
     * @param id 胶水报警信息发送人员名单主键
     * @return 结果
     */
    public int deleteGlueSysPersonListById(Long id);

    /**
     * 通过专案获取需要发企业微信消息的人员名单
     *
     * @param projectName
     * @return
     */
    public List<String> getPersonNumberList(String projectName);

}

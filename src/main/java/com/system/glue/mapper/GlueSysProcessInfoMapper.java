package com.system.glue.mapper;

import com.system.glue.domain.GlueSysProcessInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/15 16:10
 **/

@Mapper
public interface GlueSysProcessInfoMapper {

    /**
     * 查询胶水流程内信息
     *
     * @param id 胶水流程内信息主键
     * @return 胶水流程内信息
     */
    public GlueSysProcessInfo selectGlueSysProcessInfoById(Long id);

    /**
     * 删除胶水流程内信息
     *
     * @param id
     * @return
     */
    public int deleteGlueSysProcessInfoById(Long id);

    /**
     * 查询胶水流程内信息通过胶水码
     *
     * @param id 胶水流程内信息主键
     * @return 胶水流程内信息
     */
    public GlueSysProcessInfo selectGlueSysProcessInfoByGlueBarCode(String glueBarCode);

    /**
     * 查询胶水流程内信息列表
     *
     * @param glueSysProcessInfo 胶水流程内信息
     * @return 胶水流程内信息集合
     */
    public List<GlueSysProcessInfo> selectGlueSysProcessInfoList(GlueSysProcessInfo glueSysProcessInfo);

    /**
     * 通过专案查询胶水流程内信息列表
     *
     * @param projectName
     * @return
     */
    public List<GlueSysProcessInfo> selectGlueSysProcessInfoListByProjectName(String projectName);

    /**
     * 新增胶水流程内信息
     *
     * @param glueSysProcessInfo 胶水流程内信息
     * @return 结果
     */
    public int insertGlueSysProcessInfo(GlueSysProcessInfo glueSysProcessInfo);

    /**
     * 修改胶水流程内信息
     *
     * @param glueSysProcessInfo 胶水流程内信息
     * @return 结果
     */
    public int updateGlueSysProcessInfo(GlueSysProcessInfo glueSysProcessInfo);

}

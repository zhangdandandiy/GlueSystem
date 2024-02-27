package com.system.glue.mapper;

import com.system.glue.domain.GlueSysBasicSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 胶水基础设置Mapper接口
 *
 * @author Dandan
 * @date 2023/11/15 15:01
 **/

@Mapper
public interface GlueSysBasicSettingMapper {

    /**
     * 查询胶水基础设置
     *
     * @param id 胶水基础设置主键
     * @return 胶水基础设置
     */
    public GlueSysBasicSetting selectGlueSysBasicSettingById(Long id);

    /**
     * 查询胶水基础设置
     *
     * @param projectName 专案名称
     * @return 胶水基础设置
     */
    public GlueSysBasicSetting selectGlueSysBasicSettingByProjectName(String projectName);

    /**
     * 查询胶水基础设置列表
     *
     * @param projectName
     * @return
     */
    public List<GlueSysBasicSetting> selectGlueSysBasicSettingListByProjectName(String projectName);

    /**
     * 查询胶水基础设置列表
     *
     * @param glueSysBasicSetting 胶水基础设置
     * @return 胶水基础设置集合
     */
    public List<GlueSysBasicSetting> selectGlueSysBasicSettingList(GlueSysBasicSetting glueSysBasicSetting);

    /**
     * 新增胶水基础设置
     *
     * @param glueSysBasicSetting 胶水基础设置
     * @return 结果
     */
    public int insertGlueSysBasicSetting(GlueSysBasicSetting glueSysBasicSetting);

    /**
     * 修改胶水基础设置
     *
     * @param glueSysBasicSetting 胶水基础设置
     * @return 结果
     */
    public int updateGlueSysBasicSetting(GlueSysBasicSetting glueSysBasicSetting);

    /**
     * 删除胶水基础设置
     *
     * @param id 胶水基础设置主键
     * @return 结果
     */
    public int deleteGlueSysBasicSettingById(Long id);

    /**
     * 获取所有专案列表
     *
     * @return
     */
    public List<String> getAllProjectName();

}

package com.system.glue.service;

import com.system.glue.domain.GlueSysBasicSetting;

import java.util.List;

/**
 * 胶水基础设置Service接口
 *
 * @author Dandan
 * @date 2023/11/15 15:05
 **/
public interface IGlueSysBasicSettingService {

    /**
     * 通过id查询胶水基础设置
     *
     * @param id 胶水基础设置主键
     * @return 胶水基础设置
     */
    public GlueSysBasicSetting selectGlueSysBasicSettingById(Long id);

    /**
     * 通过专案名查询胶水基础设置
     *
     * @param id 胶水基础设置主键
     * @return 胶水基础设置
     */
    public GlueSysBasicSetting selectGlueSysBasicSettingByProjectName(String projectName);

    /**
     * 通过专案查询是否存在基础设置
     *
     * @param projectName 专案名称
     * @return 胶水基础设置
     */
    public boolean isGlueSysBasicSettingByProjectName(String projectName);

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
    public String insertGlueSysBasicSetting(GlueSysBasicSetting glueSysBasicSetting);

    /**
     * 修改胶水基础设置
     *
     * @param glueSysBasicSetting 胶水基础设置
     * @return 结果
     */
    public int updateGlueSysBasicSetting(GlueSysBasicSetting glueSysBasicSetting);

    /**
     * 删除胶水基础设置信息
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

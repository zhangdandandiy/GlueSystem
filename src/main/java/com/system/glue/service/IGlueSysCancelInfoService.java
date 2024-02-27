package com.system.glue.service;

import com.system.glue.domain.GlueSysCancelInfo;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/16 8:24
 **/
public interface IGlueSysCancelInfoService {
    /**
     * 查询胶水注销信息
     *
     * @param id 胶水注销信息主键
     * @return 胶水注销信息
     */
    public GlueSysCancelInfo selectGlueSysCancelInfoById(Long id);

    /**
     * 通过胶水码判断胶水是否已经注销 true 已注销
     *
     * @param glueBarCode
     * @return
     */
    public boolean selectGlueSysCancelInfoByGlueBarCode(String glueBarCode);

    /**
     * 查询胶水注销信息列表
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 胶水注销信息集合
     */
    public List<GlueSysCancelInfo> selectGlueSysCancelInfoList(GlueSysCancelInfo glueSysCancelInfo);

    /**
     * 新增胶水注销信息
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 结果
     */
    public int insertGlueSysCancelInfo(GlueSysCancelInfo glueSysCancelInfo);

    /**
     * 修改胶水注销信息
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 结果
     */
    public int updateGlueSysCancelInfo(GlueSysCancelInfo glueSysCancelInfo);

    /**
     * 删除胶水注销信息信息
     *
     * @param id 胶水注销信息主键
     * @return 结果
     */
    public int deleteGlueSysCancelInfoById(Long id);
}

package com.system.glue.service.impl;

import com.system.glue.domain.GlueSysCancelInfo;
import com.system.glue.mapper.GlueSysCancelInfoMapper;
import com.system.glue.service.IGlueSysCancelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/16 8:26
 **/

@Service
public class GlueSysCancelInfoServiceImpl implements IGlueSysCancelInfoService {

    @Autowired
    private GlueSysCancelInfoMapper glueSysCancelInfoMapper;

    /**
     * 查询胶水注销信息
     *
     * @param id 胶水注销信息主键
     * @return 胶水注销信息
     */
    @Override
    public GlueSysCancelInfo selectGlueSysCancelInfoById(Long id) {
        return glueSysCancelInfoMapper.selectGlueSysCancelInfoById(id);
    }

    /**
     * 通过胶水码判断胶水是否已经注销 true 已注销
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean selectGlueSysCancelInfoByGlueBarCode(String glueBarCode) {
        GlueSysCancelInfo glueSysCancelInfo = glueSysCancelInfoMapper.selectGlueSysCancelInfoByGlueBarCode(glueBarCode);
        return glueSysCancelInfo != null;
    }

    /**
     * 查询胶水注销信息列表
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 胶水注销信息
     */
    @Override
    public List<GlueSysCancelInfo> selectGlueSysCancelInfoList(GlueSysCancelInfo glueSysCancelInfo) {
        return glueSysCancelInfoMapper.selectGlueSysCancelInfoList(glueSysCancelInfo);
    }

    /**
     * 新增胶水注销信息
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 结果
     */
    @Override
    public int insertGlueSysCancelInfo(GlueSysCancelInfo glueSysCancelInfo) {
        return glueSysCancelInfoMapper.insertGlueSysCancelInfo(glueSysCancelInfo);
    }

    /**
     * 修改胶水注销信息
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 结果
     */
    @Override
    public int updateGlueSysCancelInfo(GlueSysCancelInfo glueSysCancelInfo) {
        return glueSysCancelInfoMapper.updateGlueSysCancelInfo(glueSysCancelInfo);
    }

    /**
     * 删除胶水注销信息信息
     *
     * @param id 胶水注销信息主键
     * @return 结果
     */
    @Override
    public int deleteGlueSysCancelInfoById(Long id) {
        return glueSysCancelInfoMapper.deleteGlueSysCancelInfoById(id);
    }

}

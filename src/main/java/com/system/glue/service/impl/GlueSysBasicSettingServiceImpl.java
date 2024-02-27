package com.system.glue.service.impl;

import com.system.glue.domain.GlueSysBasicSetting;
import com.system.glue.domain.GlueSysProcessInfo;
import com.system.glue.mapper.GlueSysBasicSettingMapper;
import com.system.glue.mapper.GlueSysProcessInfoMapper;
import com.system.glue.service.IGlueSysBasicSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/15 15:10
 **/

@Service
public class GlueSysBasicSettingServiceImpl implements IGlueSysBasicSettingService {

    @Autowired
    private GlueSysBasicSettingMapper glueSysBasicSettingMapper;

    @Autowired
    private GlueSysProcessInfoMapper glueSysProcessInfoMapper;

    /**
     * 查询胶水基础设置
     *
     * @param id 胶水基础设置主键
     * @return 胶水基础设置
     */
    @Override
    public GlueSysBasicSetting selectGlueSysBasicSettingById(Long id) {
        return glueSysBasicSettingMapper.selectGlueSysBasicSettingById(id);
    }


    /**
     * 通过专案名查询胶水基础设置
     *
     * @param projectName 胶水基础设置主键
     * @return 胶水基础设置
     */
    @Override
    public GlueSysBasicSetting selectGlueSysBasicSettingByProjectName(String projectName) {
        return glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(projectName);
    }

    /**
     * 通过专案查询是否存在基础设置 true 存在
     *
     * @param projectName 专案名称
     * @return 胶水基础设置
     */
    @Override
    public boolean isGlueSysBasicSettingByProjectName(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(projectName);
        if (glueSysBasicSetting != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询胶水基础设置列表
     *
     * @param glueSysBasicSetting 胶水基础设置
     * @return 胶水基础设置
     */
    @Override
    public List<GlueSysBasicSetting> selectGlueSysBasicSettingList(GlueSysBasicSetting glueSysBasicSetting) {
        return glueSysBasicSettingMapper.selectGlueSysBasicSettingList(glueSysBasicSetting);
    }

    /**
     * 新增胶水基础设置
     *
     * @param glueSysBasicSetting 胶水基础设置
     * @return 结果
     */
    @Override
    public String insertGlueSysBasicSetting(GlueSysBasicSetting glueSysBasicSetting) {
        // 重复校验
        String projectName = glueSysBasicSetting.getProjectName();
        if (glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(projectName)!=null){
            return "已存在该专案的维护信息，请勿重复维护";
        }else if (glueSysBasicSettingMapper.insertGlueSysBasicSetting(glueSysBasicSetting)>0){
            return "维护成功";
        }else {
            return "维护失败";
        }
    }

    /**
     * 修改胶水基础设置
     *
     * @param glueSysBasicSetting 胶水基础设置
     * @return 结果
     */
    @Override
    @Transactional
    public int updateGlueSysBasicSetting(GlueSysBasicSetting glueSysBasicSetting) {
        // 是否需要回温
        String isNeedThawing = glueSysBasicSetting.getIsNeedThawing();
        // 回温下限
        Long thawingTimeLower = glueSysBasicSetting.getThawingTimeLower();
        // 是否需要解冻
        String isNeedUnfreeze = glueSysBasicSetting.getIsNeedUnfreeze();
        // 解冻下限
        Long unfreezeTimeLower = glueSysBasicSetting.getUnfreezeTimeLower();
        // 是否需要脱泡
        String isNeedDeaerate = glueSysBasicSetting.getIsNeedDeaerate();
        // 脱泡下限
        Long deaerateTimeLower = glueSysBasicSetting.getDeaerateTimeLower();
        List<GlueSysProcessInfo> list = glueSysProcessInfoMapper.selectGlueSysProcessInfoListByProjectName(glueSysBasicSetting.getProjectName());
        for (GlueSysProcessInfo glueSysProcessInfo : list) {
            glueSysProcessInfo.setIsNeedThawing(isNeedThawing);
            glueSysProcessInfo.setThawingNeedTime(thawingTimeLower);
            glueSysProcessInfo.setIsNeedUnfreeze(isNeedUnfreeze);
            glueSysProcessInfo.setUnfreezeNeedTime(unfreezeTimeLower);
            glueSysProcessInfo.setIsNeedDeaerate(isNeedDeaerate);
            glueSysProcessInfo.setDeaerateNeedTime(deaerateTimeLower);
            glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo);
        }
        return glueSysBasicSettingMapper.updateGlueSysBasicSetting(glueSysBasicSetting);
    }

    /**
     * 删除胶水基础设置信息
     *
     * @param id 胶水基础设置主键
     * @return 结果
     */
    @Override
    public int deleteGlueSysBasicSettingById(Long id) {
        return glueSysBasicSettingMapper.deleteGlueSysBasicSettingById(id);
    }

    /**
     * 获取所有专案列表
     *
     * @return
     */
    @Override
    public List<String> getAllProjectName() {
        return glueSysBasicSettingMapper.getAllProjectName();
    }

}

package com.system.glue.service.impl;

import com.system.glue.domain.process.*;
import com.system.glue.mapper.GlueSysShowListMapper;
import com.system.glue.service.IGlueSysShowListService;
import com.system.glue.utils.GlueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/16 14:43
 **/

@Service
public class GlueSysShowListServiceImpl implements IGlueSysShowListService {

    @Autowired
    private GlueSysShowListMapper glueSysShowListMapper;

    @Override
    public List<GlueSysInStorage> selectGlueSysInStorageByProjectName(String projectName) {
        return glueSysShowListMapper.selectGlueSysInStorageByProjectName(projectName);
    }

    /**
     * 通过专案查询冰箱回温胶水列表
     *
     * @param projectName
     * @return
     */
    @Override
    public List<GlueSysThawing> selectGlueSysThawingByProjectName(String projectName) {
        List<GlueSysThawing> list = glueSysShowListMapper.selectGlueSysThawingByProjectName(projectName);
        for (GlueSysThawing glueSysThawing : list) {
            // 已经回温时间
            Long alreadyThawingTime;
            Long remainThawingTime;
            // 超过回温时间
            Long exceedThawingTime;
            if (glueSysThawing.getIsNeedThawing().equals("false") || glueSysThawing.getThawingStartTime() == null) {
                alreadyThawingTime = null;
                remainThawingTime = null;
                exceedThawingTime = null;
            } else {
                if (glueSysThawing.getThawingEndTime() != null) {
                    alreadyThawingTime = GlueUtils.DateDifferentMinute(glueSysThawing.getThawingStartTime(), glueSysThawing.getThawingEndTime());
                } else {
                    alreadyThawingTime = GlueUtils.DateDifferentMinute(glueSysThawing.getThawingStartTime(), new Date());
                }
                // 剩余回温时间
                remainThawingTime = glueSysThawing.getThawingNeedTime() - alreadyThawingTime;
                // 将负差值转换为0
                remainThawingTime = Math.max(remainThawingTime, 0);
                // 超过回温时间
                exceedThawingTime = alreadyThawingTime - glueSysThawing.getThawingNeedTime();
            }

            glueSysThawing.setAlreadyThawingTime(alreadyThawingTime);
            glueSysThawing.setRemainThawingTime(remainThawingTime);
            glueSysThawing.setExceedThawingTime(exceedThawingTime);
        }
        return list;
    }

    /**
     * 通过专案查询常温解冻胶水列表
     *
     * @param projectName
     * @return
     */
    @Override
    public List<GlueSysUnfreeze> selectGlueSysUnfreezeByProjectName(String projectName) {
        List<GlueSysUnfreeze> list = glueSysShowListMapper.selectGlueSysUnfreezeByProjectName(projectName);
        for (GlueSysUnfreeze glueSysUnfreeze : list) {
            // 已经解冻时间
            Long alreadyUnfreezeTime;
            Long remainUnfreezeTime;
            Long exceedUnfreezeTime;
            if (glueSysUnfreeze.getIsNeedUnfreeze().equals("false") || glueSysUnfreeze.getUnfreezeStartTime() == null) {
                alreadyUnfreezeTime = null;
                remainUnfreezeTime = null;
                exceedUnfreezeTime = null;
            } else {
                if (glueSysUnfreeze.getUnfreezeEndTime() != null) {
                    alreadyUnfreezeTime = GlueUtils.DateDifferentMinute(glueSysUnfreeze.getUnfreezeStartTime(), glueSysUnfreeze.getUnfreezeEndTime());
                } else {
                    alreadyUnfreezeTime = GlueUtils.DateDifferentMinute(glueSysUnfreeze.getUnfreezeStartTime(), new Date());
                }
                // 剩余解冻时间
                remainUnfreezeTime = glueSysUnfreeze.getUnfreezeNeedTime() - alreadyUnfreezeTime;
                // 将负值差转换为0
                remainUnfreezeTime = Math.max(remainUnfreezeTime, 0);
                // 超过常温解冻时间
                exceedUnfreezeTime = alreadyUnfreezeTime - glueSysUnfreeze.getUnfreezeNeedTime();
            }

            glueSysUnfreeze.setAlreadyUnfreezeTime(alreadyUnfreezeTime);
            glueSysUnfreeze.setRemainUnfreezeTime(remainUnfreezeTime);
            glueSysUnfreeze.setExceedUnfreezeTime(exceedUnfreezeTime);
        }
        return list;
    }

    /**
     * 通过专案查询已出库胶水列表
     *
     * @param projectName
     * @return
     */
    @Override
    public List<GlueSysOutStorage> selectGlueSysOutStorageByProjectName(String projectName) {
        return glueSysShowListMapper.selectGlueSysOutStorageByProjectName(projectName);
    }

    /**
     * 通过专案查询脱泡胶水列表
     *
     * @param projectName
     * @return
     */
    @Override
    public List<GlueSysDeaerate> selectGlueSysDeaerateByProjectName(String projectName) {
        List<GlueSysDeaerate> list = glueSysShowListMapper.selectGlueSysDeaerateByProjectName(projectName);
        for (GlueSysDeaerate glueSysDeaerate : list) {
            // 已经脱泡时间
            Long alreadyDeaerateTime;
            Long remainDeaerateTime;
            Long exceedDeaerateTime;
            if (glueSysDeaerate.getIsNeedDeaerate().equals("false") || glueSysDeaerate.getDeaerateStartTime() == null) {
                alreadyDeaerateTime = null;
                remainDeaerateTime = null;
                exceedDeaerateTime = null;
            } else {
                if (glueSysDeaerate.getDeaerateEndTime() != null) {
                    alreadyDeaerateTime = GlueUtils.DateDifferentMinute(glueSysDeaerate.getDeaerateStartTime(), glueSysDeaerate.getDeaerateEndTime());
                } else {
                    alreadyDeaerateTime = GlueUtils.DateDifferentMinute(glueSysDeaerate.getDeaerateStartTime(), new Date());
                }
                // 剩余脱泡时间
                remainDeaerateTime = glueSysDeaerate.getDeaerateNeedTime() - alreadyDeaerateTime;
                // 将负值差转换为0
                remainDeaerateTime = Math.max(remainDeaerateTime, 0);
                exceedDeaerateTime = alreadyDeaerateTime - glueSysDeaerate.getDeaerateNeedTime();
            }

            glueSysDeaerate.setAlreadyDeaerateTime(alreadyDeaerateTime);
            glueSysDeaerate.setRemainDeaerateTime(remainDeaerateTime);
            glueSysDeaerate.setExceedDeaerateTime(exceedDeaerateTime);
        }
        return list;
    }

}

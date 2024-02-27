package com.system.glue.service.impl;

import com.system.glue.domain.GlueSysBasicSetting;
import com.system.glue.domain.GlueSysCancelInfo;
import com.system.glue.domain.GlueSysProcessInfo;
import com.system.glue.domain.dto.GlueSysCancelInfoParam;
import com.system.glue.domain.dto.GlueSysControlInfo;
import com.system.glue.domain.process.GlueSysControl;
import com.system.glue.domain.process.GlueSysLineSide;
import com.system.glue.domain.process.GlueSysStock;
import com.system.glue.mapper.GlueSysBasicSettingMapper;
import com.system.glue.mapper.GlueSysCancelInfoMapper;
import com.system.glue.mapper.GlueSysLifeControlMapper;
import com.system.glue.service.IGlueSysLifeControlService;
import com.system.glue.service.IGlueSysProcessInfoService;
import com.system.glue.utils.GlueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/18 13:54
 **/

@Service
public class GlueSysLifeControlServiceImpl implements IGlueSysLifeControlService {

    @Autowired
    private GlueSysLifeControlMapper glueSysLifeControlMapper;

    @Autowired
    private GlueSysBasicSettingMapper glueSysBasicSettingMapper;

    @Autowired
    private IGlueSysProcessInfoService iGlueSysProcessInfoService;

    @Autowired
    private GlueSysCancelInfoMapper glueSysCancelInfoMapper;

    /**
     * 线体寿命管控展示-展示胶水整个的寿命状况
     *
     * @param projectName
     * @return
     */
    @Override
    public List<GlueSysControlInfo> getGlueSysControlByProjectName(String projectName) {
        List<GlueSysBasicSetting> glueBasicList = glueSysBasicSettingMapper.selectGlueSysBasicSettingListByProjectName(projectName);
        List<GlueSysControlInfo> list = glueSysLifeControlMapper.getGlueSysControlByProjectName(projectName);
        for (GlueSysBasicSetting basicSetting : glueBasicList) {
            GlueSysBasicSetting glueSysBasicSetting = glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(basicSetting.getProjectName());
            for (GlueSysControlInfo glueSysControlInfo : list) {
                // 设置胶水已回温时长
                glueSysControlInfo.setThawingUsedTime(getAlreadyThawingTime(glueSysControlInfo));
                // 设置胶水已解冻时长
                glueSysControlInfo.setUnfreezeUsedTime(getAlreadyUnfreezeTime(glueSysControlInfo));
                // 设置胶水已脱泡时长
                glueSysControlInfo.setDeaerateUsedTime(getAlreadyDeaerateTime(glueSysControlInfo));
                // 设置胶水预警时间
                Long warnTime = glueSysBasicSetting.getWarnLifeTime();
                glueSysControlInfo.setWarnLifeTime(warnTime);
                // 设置胶水报警时间
                Long alarmTime = glueSysBasicSetting.getAlarmLifeTime();
                glueSysControlInfo.setAlarmLifeTime(alarmTime);
                // 设置胶水线上使用时间
                glueSysControlInfo.setLineUsedTime(getAlreadyLineTime(glueSysControlInfo));
                // 设置胶水开始使用时间
                glueSysControlInfo.setGlueStartUseTime(getGlueStartUseTime(glueSysControlInfo));
                // 设置胶水已使用时长
                Long alreadyUseTime = getAlreadyUseTime(glueSysControlInfo);
                glueSysControlInfo.setGlueAlreadyUseTime(alreadyUseTime);
                // 报警信息
                String alarmMsg;
                // 字体颜色
                String msgColor;
                if (alreadyUseTime == null) {
                    alarmMsg = null;
                    msgColor = null;
                } else {
                    if (alreadyUseTime >= warnTime && alreadyUseTime < alarmTime) {
                        // 预警
                        alarmMsg = "胶水整体已使用：" + alreadyUseTime + "，预警中!";
                        msgColor = "#FFFF00";
                    } else if (alreadyUseTime > alarmTime) {
                        // 报警
                        alarmMsg = "胶水整体已使用：" + alreadyUseTime + "，需要更换!";
                        msgColor = "#8B0000";
                    } else {
                        // 正常
                        alarmMsg = "胶水整体已使用：" + alreadyUseTime;
                        msgColor = "#008000";
                    }
                }
                // 设置胶水警示信息
                glueSysControlInfo.setAlarmMsg(alarmMsg);
                // 设置胶水字体颜色
                glueSysControlInfo.setMsgColor(msgColor);
            }
        }
        return list;
    }

    /**
     * 胶水线边库列表展示
     *
     * @param projectName
     * @return
     */
    @Override
    public List<GlueSysLineSide> getGlueSysLineSideByProjectName(String projectName) {
        List<GlueSysLineSide> list = glueSysLifeControlMapper.getGlueSysLineSideByProjectName(projectName);
        for (GlueSysLineSide glueSysLineSide : list) {
            glueSysLineSide.setRemainLife(iGlueSysProcessInfoService.getGlueResidualLife(glueSysLineSide.getGlueBarCode()));
        }
        return list;
    }

    /**
     * 胶水库存列表展示
     *
     * @param projectName
     * @return
     */
    @Override
    public List<GlueSysStock> getGlueSysStockByProjectName(String projectName) {
        List<GlueSysStock> glueSysStockList = glueSysLifeControlMapper.getGlueSysStockByProjectName(projectName);
        List<GlueSysBasicSetting> basicSettingsList = glueSysBasicSettingMapper.selectGlueSysBasicSettingListByProjectName(projectName);
        for (GlueSysBasicSetting glueSysBasicSetting : basicSettingsList) {
            String project = glueSysBasicSetting.getProjectName();
            String encodingFormat = glueSysBasicSetting.getEncodingFormat();
            for (GlueSysStock glueSysStock : glueSysStockList) {
                if (glueSysStock.getProjectName().equals(project))
                    glueSysStock.setRemainLife(GlueUtils.getGlueLife(encodingFormat, glueSysStock.getGlueBarCode()));
            }
        }
        return glueSysStockList;
    }

    /**
     * 胶水被使用列表展示
     *
     * @param glueSysCancelInfoParam
     * @return
     */
    @Override
    public List<GlueSysCancelInfo> getGlueSysCancelInfoByProjectName(GlueSysCancelInfoParam glueSysCancelInfoParam) {
        List<GlueSysCancelInfo> list = glueSysLifeControlMapper.getGlueSysCancelInfoByProjectName(glueSysCancelInfoParam);
        for (GlueSysCancelInfo glueSysCancelInfo : list) {
            glueSysCancelInfo.setActualLife(getGlueActualLife(glueSysCancelInfo));
            Long onlineUseTime;
            if (glueSysCancelInfo.getLineStartTime() != null && glueSysCancelInfo.getCancelTime() != null) {
                onlineUseTime = GlueUtils.DateDifferentMinute(glueSysCancelInfo.getLineStartTime(), glueSysCancelInfo.getCancelTime());
            } else {
                onlineUseTime = 0L;
            }
            glueSysCancelInfo.setOnlineUsedTime(onlineUseTime);
        }
        return list;
    }

    /**
     * 判断胶水是否有回温流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeGlueIsThawing(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysCancelInfoMapper.selectGlueSysCancelInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getIsNeedThawing().equals("true");
    }

    /**
     * 判断胶水是否有解冻流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeGlueIsUnfreeze(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysCancelInfoMapper.selectGlueSysCancelInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getIsNeedUnfreeze().equals("true");
    }

    /**
     * 获取胶水实际使用寿命
     *
     * @return
     */
    private Long getGlueActualLife(GlueSysCancelInfo glueSysCancelInfo) {
        // 胶水码
        String glueBarCode = glueSysCancelInfo.getGlueBarCode();
        Long actualLife;
        if (judgeGlueIsThawing(glueBarCode) && judgeGlueIsStartThawing(glueBarCode)) {
            // 1、如果有回温(有回温一定有解冻)，并且已经开始过回温流程
            // 实际使用寿命 = 注销时间 - 开始回温时间
            actualLife = GlueUtils.DateDifferentMinute(glueSysCancelInfo.getThawingStartTime(), glueSysCancelInfo.getCancelTime());
        } else if (judgeGlueIsUnfreeze(glueBarCode) && !judgeGlueIsThawing(glueBarCode) && judgeGlueIsStartUnFreeze(glueBarCode)) {
            // 2、如果只有解冻，并且已经开始过解冻流程
            // 实际使用寿命 = 注销时间 - 开始解冻时间
            actualLife = GlueUtils.DateDifferentMinute(glueSysCancelInfo.getUnfreezeStartTime(), glueSysCancelInfo.getCancelTime());
        } else if (!judgeGlueIsUnfreeze(glueBarCode) && !judgeGlueIsThawing(glueBarCode) && judgeGlueIsStartOutStorage(glueBarCode)) {
            // 3、如果既没回温也没解冻，并且已经出库
            // 实际使用寿命 = 注销时间 - 出库时间
            actualLife = GlueUtils.DateDifferentMinute(glueSysCancelInfo.getOutStorageTime(), glueSysCancelInfo.getCancelTime());
        } else {
            actualLife = 0L;
        }
        return actualLife;
    }

    /**
     * 判断胶水是否已经出库 true 已出库
     */
    private boolean judgeGlueIsStartOutStorage(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysCancelInfoMapper.selectGlueSysCancelInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getOutStorageTime() != null;
    }

    /**
     * 判断胶水是否已经开始解冻流程 true 开始
     */
    private boolean judgeGlueIsStartUnFreeze(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysCancelInfoMapper.selectGlueSysCancelInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getUnfreezeStartTime() != null;
    }

    /**
     * 判断胶水是否已经开始回温流程 true 开始
     */
    private boolean judgeGlueIsStartThawing(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysCancelInfoMapper.selectGlueSysCancelInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getThawingStartTime() != null;
    }

    /**
     * 获取胶水已经使用时间
     */
    private Long getAlreadyUseTime(GlueSysControl glueSysControl) {
        // 获取胶水开始使用时间
        Date startTime = getGlueStartUseTime(glueSysControl);
        if (startTime != null) {
            return GlueUtils.DateDifferentMinute(startTime, new Date());
        } else {
            return null;
        }
    }

    /**
     * 获取胶水开始使用时间
     */
    private Date getGlueStartUseTime(GlueSysControl glueSysControl) {
        if (glueSysControl.getIsNeedThawing().equals("true")) {
            // 如果有回温，则以回温开始时间为胶水开始使用时间，并计为正在使用
            return glueSysControl.getThawingStartTime();
        } else if (glueSysControl.getIsNeedThawing().equals("false") && glueSysControl.getIsNeedUnfreeze().equals("true")) {
            // 如果只有解冻，则以解冻开始时间为胶水开始使用时间，并计为正在使用
            return glueSysControl.getUnfreezeStartTime();
        } else {
            // 否则，以出库开始时间为胶水开始使用时间，并计为正在使用
            return glueSysControl.getOutStorageTime();
        }
    }

    /**
     * 获取胶水线上已经使用时间
     *
     * @param glueSysControl
     * @return
     */
    private Long getAlreadyLineTime(GlueSysControl glueSysControl) {
        // 线上已经使用时间
        Long alreadyLineTime;
        if (glueSysControl.getLineStartTime() == null) {
            alreadyLineTime = null;
        } else if (glueSysControl.getLineEndTime() != null) {
            alreadyLineTime = GlueUtils.DateDifferentMinute(glueSysControl.getLineStartTime(), glueSysControl.getLineEndTime());
        } else {
            alreadyLineTime = GlueUtils.DateDifferentMinute(glueSysControl.getLineStartTime(), new Date());
        }
        return alreadyLineTime;
    }

    /**
     * 获取胶水已经回温时间
     *
     * @param glueSysControl
     * @return
     */
    private Long getAlreadyThawingTime(GlueSysControl glueSysControl) {
        // 已经回温时间
        Long alreadyThawingTime;
        if (glueSysControl.getIsNeedThawing().equals("false") || glueSysControl.getThawingStartTime() == null) {
            alreadyThawingTime = null;
        } else if (glueSysControl.getThawingEndTime() != null) {
            alreadyThawingTime = GlueUtils.DateDifferentMinute(glueSysControl.getThawingStartTime(), glueSysControl.getThawingEndTime());
        } else {
            alreadyThawingTime = GlueUtils.DateDifferentMinute(glueSysControl.getThawingStartTime(), new Date());
        }
        return alreadyThawingTime;
    }

    /**
     * 获取胶水已经解冻时间
     *
     * @param glueSysControl
     * @return
     */
    private Long getAlreadyUnfreezeTime(GlueSysControl glueSysControl) {
        // 已经解冻时间
        Long alreadyUnfreezeTime;
        if (glueSysControl.getIsNeedUnfreeze().equals("false") || glueSysControl.getUnfreezeStartTime() == null) {
            alreadyUnfreezeTime = null;
        } else if (glueSysControl.getUnfreezeEndTime() != null) {
            alreadyUnfreezeTime = GlueUtils.DateDifferentMinute(glueSysControl.getUnfreezeStartTime(), glueSysControl.getUnfreezeEndTime());
        } else {
            alreadyUnfreezeTime = GlueUtils.DateDifferentMinute(glueSysControl.getUnfreezeStartTime(), new Date());
        }
        return alreadyUnfreezeTime;
    }

    /**
     * 获取胶水已经脱泡时间
     *
     * @param glueSysControl
     * @return
     */
    private Long getAlreadyDeaerateTime(GlueSysControl glueSysControl) {
        // 已经脱泡时间
        Long alreadyDeaerateTime;
        if (glueSysControl.getIsNeedDeaerate().equals("false") || glueSysControl.getDeaerateStartTime() == null) {
            alreadyDeaerateTime = null;
        } else if (glueSysControl.getDeaerateEndTime() != null) {
            alreadyDeaerateTime = GlueUtils.DateDifferentMinute(glueSysControl.getDeaerateStartTime(), glueSysControl.getDeaerateEndTime());
        } else {
            alreadyDeaerateTime = GlueUtils.DateDifferentMinute(glueSysControl.getDeaerateStartTime(), new Date());
        }
        return alreadyDeaerateTime;
    }

}

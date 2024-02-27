package com.system.glue.service.impl;

import com.system.glue.domain.GlueSysBasicSetting;
import com.system.glue.domain.GlueSysProcessInfo;
import com.system.glue.domain.dto.GlueSysOutStorageParam;
import com.system.glue.mapper.GlueSysBasicSettingMapper;
import com.system.glue.mapper.GlueSysCancelInfoMapper;
import com.system.glue.mapper.GlueSysProcessInfoMapper;
import com.system.glue.service.IGlueSysProcessInfoService;
import com.system.glue.utils.GlueUtils;
import com.system.glue.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/15 16:43
 **/

@Service
public class GlueSysProcessInfoServiceImpl implements IGlueSysProcessInfoService {

    @Autowired
    private GlueSysProcessInfoMapper glueSysProcessInfoMapper;

    @Autowired
    private GlueSysBasicSettingMapper glueSysBasicSettingMapper;

    @Autowired
    private GlueSysCancelInfoMapper glueSysCancelInfoMapper;

    /**
     * 判断某台点胶机是否正在使用 true 正在使用
     *
     * @return
     */
    @Override
    public boolean judgeGlueIsUsedMachine(GlueSysOutStorageParam glueSysOutStorageParam) {
        String glueBarCode = glueSysOutStorageParam.getGlueBarCode();
        String machineName = glueSysOutStorageParam.getGlueMachineName();
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getIsInLine().equals("true") && glueSysOutStorageParam.getGlueMachineName().equals(machineName);
    }

    /**
     * 修改胶水流程内信息
     *
     * @param glueSysProcessInfo 胶水流程内信息
     * @return 结果
     */
    public int updateGlueSysProcessInfo(GlueSysProcessInfo glueSysProcessInfo) {
        return glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo);
    }

    /**
     * 查询胶水流程内信息列表
     *
     * @param glueSysProcessInfo 胶水流程内信息
     * @return 胶水流程内信息集合
     */
    @Override
    public List<GlueSysProcessInfo> selectGlueSysProcessInfoList(GlueSysProcessInfo glueSysProcessInfo) {
        return glueSysProcessInfoMapper.selectGlueSysProcessInfoList(glueSysProcessInfo);
    }

    /**
     * 通过胶水码判断胶水是否已经在库 true 在库
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean selectGlueSysProcessInfoByGlueBarCode(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo != null;
    }

    /**
     * 通过胶水码查询胶水信息
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public GlueSysProcessInfo selectGlueSysProcessInfo(String glueBarCode) {
        return glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
    }

    /**
     * 判断胶水是否已出库 true 已出库
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean selectGlueIsOutStorage(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getOutStorageTime() != null;
    }

    /**
     * 通过胶水码和使用专案判断是否符合编码原则 true 符合
     *
     * @param ProjectName
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean IsMatchCodePrinciple(String ProjectName, String glueBarCode) {
        GlueSysBasicSetting glueSysBasicSetting = glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(ProjectName);
        // 基础设定的厂商代码
        String basicSupplierCode = glueSysBasicSetting.getSupplierCode();
        // 基础设定的料号
        String basicMaterialCode = glueSysBasicSetting.getMaterialCode();
        // 基础设定的编码原则
        String basicEncodingFormat = glueSysBasicSetting.getEncodingFormat();
        if (basicEncodingFormat.contains("ICT")) {
            return IsMatchCodePrincipleByICT(basicSupplierCode, basicMaterialCode, glueBarCode);
        } else {
            return IsMatchCodePrincipleByHenkel(basicSupplierCode, basicMaterialCode, glueBarCode);
        }
    }

    /**
     * 新增胶水流程内信息
     *
     * @param projectName 该胶水要使用的专案名
     * @param glueBarCode 入库扫描的胶水码
     * @return
     */
    @Override
    public String insertGlueSysProcessInfo(String projectName, String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = new GlueSysProcessInfo();
        GlueSysBasicSetting glueSysBasicSetting = glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(projectName);
        // 获取专案下的编码
        String encodingFormat = glueSysBasicSetting.getEncodingFormat();
        // 设置胶水码
        glueSysProcessInfo.setGlueBarCode(glueBarCode);
        // 设置供应商
        glueSysProcessInfo.setSupplierCode(getSupplierCodeByEncode(encodingFormat, glueBarCode));
        // 设置料号
        glueSysProcessInfo.setMaterialCode(getMaterialCodeByEncode(encodingFormat, glueBarCode));
        // 设置生产日期
        glueSysProcessInfo.setProductionDate(getProductionDateByEncode(encodingFormat, glueBarCode));
        // 设置购买日期 Henkel码中无购买日期
        glueSysProcessInfo.setShoppingDate(getShoppingDateByEncode(encodingFormat, glueBarCode));
        // 设置保质期
        glueSysProcessInfo.setShelfLife(getShelfLifeByEncode(encodingFormat, glueBarCode));
        // 设置重量
        glueSysProcessInfo.setWeight(getWeightByEncode(encodingFormat, glueBarCode));
        // 更改在库状态
        glueSysProcessInfo.setIsInStorage("true");
        glueSysProcessInfo.setInStorageTime(new Date());
        // 设置专案
        glueSysProcessInfo.setProjectName(projectName);
        // 设置点胶机数量
        // 设置是否需要在冰箱回温以及需要回温的时间(使用下限)
        glueSysProcessInfo.setIsNeedThawing(glueSysBasicSetting.getIsNeedThawing());
        glueSysProcessInfo.setThawingNeedTime(glueSysBasicSetting.getThawingTimeLower());
        // 设置是否需要解冻以及需要解冻的时间(使用下限)
        glueSysProcessInfo.setIsNeedUnfreeze(glueSysBasicSetting.getIsNeedUnfreeze());
        glueSysProcessInfo.setUnfreezeNeedTime(glueSysBasicSetting.getUnfreezeTimeLower());
        // 设置是否需要脱泡以及需要脱泡的时间(使用下限)
        glueSysProcessInfo.setIsNeedDeaerate(glueSysBasicSetting.getIsNeedDeaerate());
        glueSysProcessInfo.setDeaerateNeedTime(glueSysBasicSetting.getDeaerateTimeLower());
        // 设置注销状态
        glueSysProcessInfo.setIsCancel("false");
        // 设置在线状态
        glueSysProcessInfo.setIsInLine("false");
        if (glueSysProcessInfoMapper.insertGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "入库成功！";
        } else {
            return "入库失败！";
        }
    }

    /**
     * 根据胶水码获取胶水剩余寿命
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public Long getGlueResidualLife(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        // 获取胶水应用的专案
        String projectName = glueSysProcessInfo.getProjectName();
        GlueSysBasicSetting glueSysBasicSetting = glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(projectName);
        Long alreadyUsedLife = alreadyUsedLife(glueBarCode);
        long remainLife = glueSysBasicSetting.getUseLifeTime() - alreadyUsedLife;
        remainLife = Math.max(remainLife, 0);
        return remainLife;
    }

    /**
     * 根据胶水码获取胶水剩余保质期
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public Long getGlueResidualShelf(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        // 获取胶水应用的专案
        String projectName = glueSysProcessInfo.getProjectName();
        GlueSysBasicSetting glueSysBasicSetting = glueSysBasicSettingMapper.selectGlueSysBasicSettingByProjectName(projectName);
        // 获取胶水编码
        String EncodingFormat = glueSysBasicSetting.getEncodingFormat();
        return GlueUtils.getGlueLife(EncodingFormat, glueBarCode);
    }

    /**
     * 根据胶水码获取胶水已经使用寿命
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public Long getAlreadyUsedLife(String glueBarCode) {
        return alreadyUsedLife(glueBarCode);
    }

    /**
     * 根据胶水码获取胶水已经使用寿命
     *
     * @param glueBarCode
     * @return
     */
    private Long alreadyUsedLife(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        long alreadyUsedLife = 0L;
        if (glueSysProcessInfo.getIsNeedThawing().equals("true") && glueSysProcessInfo.getThawingStartTime() != null) {
            // 如果需要回温(有回温就一定有解冻)，并且已经开始回温，剩余寿命应该从开始回温时间计算
            alreadyUsedLife = GlueUtils.DateDifferentMinute(glueSysProcessInfo.getThawingStartTime(), new Date());
        } else if (glueSysProcessInfo.getIsNeedThawing().equals("false") && glueSysProcessInfo.getIsNeedUnfreeze().equals("true") && glueSysProcessInfo.getUnfreezeStartTime() != null) {
            // 如果需要解冻且不需要回温，并且已经开始解冻，剩余寿命应该从开始解冻时间计算
            alreadyUsedLife = GlueUtils.DateDifferentMinute(glueSysProcessInfo.getUnfreezeStartTime(), new Date());
        } else if (glueSysProcessInfo.getIsNeedThawing().equals("false") && glueSysProcessInfo.getIsNeedUnfreeze().equals("false") && glueSysProcessInfo.getOutStorageTime() != null) {
            // 如果既没有回温也没有解冻，并且胶水已出库，剩余寿命应该从出库时间计算
            alreadyUsedLife = GlueUtils.DateDifferentMinute(glueSysProcessInfo.getOutStorageTime(), new Date());
        }
        return alreadyUsedLife;
    }

    /**
     * 判断胶水是否有回温和解冻流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeGlueIsThawingAndUnfreeze(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getIsNeedThawing().equals("true") || glueSysProcessInfo.getIsNeedUnfreeze().equals("true");
    }

    /**
     * 判断胶水是否有回温流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeGlueIsThawing(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getIsNeedThawing().equals("true");
    }

    /**
     * 判断胶水是否有解冻流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeGlueIsUnfreeze(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getIsNeedUnfreeze().equals("true");
    }

    /**
     * 判断胶水是否有脱泡流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeGlueIsDeaerate(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getIsNeedDeaerate().equals("true");
    }

    /**
     * 更新胶水回温开始时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueThawingStartTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setThawingStartTime(new Date());
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水成功开始回温！";
        } else {
            return "胶水开始回温失败！";
        }
    }

    /**
     * 更新胶水回温结束时间以及回温花费时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueThawingEndTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setThawingEndTime(new Date());
        glueSysProcessInfo.setThawingUsedTime(GlueUtils.DateDifferentMinute(glueSysProcessInfo.getThawingStartTime(), new Date()));
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水成功结束回温！";
        } else {
            return "胶水结束回温失败！";
        }
    }

    /**
     * 更新胶水解冻开始时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueUnfreezeStartTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setUnfreezeStartTime(new Date());
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水成功开始解冻！";
        } else {
            return "胶水开始解冻失败！";
        }
    }

    /**
     * 更新胶水解冻结束时间以及解冻花费时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueUnfreezeEndTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setUnfreezeEndTime(new Date());
        glueSysProcessInfo.setUnfreezeUsedTime(GlueUtils.DateDifferentMinute(glueSysProcessInfo.getUnfreezeStartTime(), new Date()));
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水成功结束解冻！";
        } else {
            return "胶水结束解冻失败！";
        }
    }

    /**
     * 更新胶水出库状态以及出库时间
     *
     * @param glueSysOutStorageParam
     * @return
     */
    @Override
    public String updateGlueOutStorageTime(GlueSysOutStorageParam glueSysOutStorageParam) {
        String glueBarCode = glueSysOutStorageParam.getGlueBarCode();
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        // 设置出库标志以及出库时间
        glueSysProcessInfo.setIsOutStorage("true");
        glueSysProcessInfo.setOutStorageTime(new Date());
        // 设置出库专案
        glueSysProcessInfo.setProjectName(glueSysOutStorageParam.getProjectName());
        // 设置出库线体
        glueSysProcessInfo.setLineName(glueSysOutStorageParam.getLineName());
        // 设置出库点胶机
        glueSysProcessInfo.setGlueMachineName(glueSysOutStorageParam.getGlueMachineName());
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "出库成功！";
        } else {
            return "出库失败！";
        }
    }

    /**
     * 更新胶水脱泡开始时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueDeaerateStarTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setDeaerateStartTime(new Date());
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水成功开始脱泡！";
        } else {
            return "胶水开始脱泡失败！";
        }
    }

    /**
     * 更新胶水上线时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueLineStartTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setLineStartTime(new Date());
        glueSysProcessInfo.setIsInLine("true");
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水已经成功上线！";
        } else {
            return "胶水上线失败！";
        }
    }

    /**
     * 更新胶水下线时间及线上状态
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueLineEndTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setLineEndTime(new Date());
        glueSysProcessInfo.setIsInLine("false");
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水已正常结束使用！";
        } else {
            return "胶水结束使用失败！";
        }
    }

    /**
     * 更新胶水脱泡结束时间以及脱泡花费时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String updateGlueDeaerateEndTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        glueSysProcessInfo.setDeaerateEndTime(new Date());
        glueSysProcessInfo.setDeaerateUsedTime(GlueUtils.DateDifferentMinute(glueSysProcessInfo.getUnfreezeStartTime(), new Date()));
        if (glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo) > 0) {
            return "胶水成功结束脱泡！";
        } else {
            return "胶水结束脱泡失败！";
        }
    }

    /**
     * 在只有解冻流程下，获取是第几步(第一步：扫码开始解冻 第二步：扫码结束解冻)
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String getUnfreezeStep(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        Date ThawingStart = glueSysProcessInfo.getThawingStartTime();
        Date ThawingEnd = glueSysProcessInfo.getThawingEndTime();
        Date UnfreezeStart = glueSysProcessInfo.getUnfreezeStartTime();
        Date UnfreezeEnd = glueSysProcessInfo.getUnfreezeEndTime();
        if (ThawingStart == null && ThawingEnd == null && UnfreezeStart == null && UnfreezeEnd == null) {
            return "Step1";
        } else {
            return "Step2";
        }
    }

    /**
     * 调用了这个方法，说明一定存在回温解冻流程，即要么只有解冻，要么回温解冻都有
     * 在回温和解冻流程都存在的情况下，获取是第几步
     * 第一步：扫码开始回温
     * 第二步：扫码结束回温
     * 第三步：扫码开始解冻
     * 第四步：扫码结束解冻
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String getThawingAndUnfreeStep(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        Date ThawingStart = glueSysProcessInfo.getThawingStartTime();
        Date ThawingEnd = glueSysProcessInfo.getThawingEndTime();
        Date UnfreezeStart = glueSysProcessInfo.getUnfreezeStartTime();
        Date UnfreezeEnd = glueSysProcessInfo.getUnfreezeEndTime();
        String isNeedThawing = glueSysProcessInfo.getIsNeedThawing();
        boolean needThawingResult = isNeedThawing.equals("true");

        if (needThawingResult) {
            // 有回温一定有解冻
            if (ThawingStart == null && ThawingEnd == null && UnfreezeStart == null && UnfreezeEnd == null) {
                // 第一步：扫码开始回温--只更新回温开始时间
                return "Step1";
            } else if (ThawingStart != null && ThawingEnd == null && UnfreezeStart == null && UnfreezeEnd == null) {
                // 第二步：扫码结束回温--只更新回温结束时间和回温使用时间
                return "Step2";
            } else if (ThawingStart != null && ThawingEnd != null && UnfreezeStart == null && UnfreezeEnd == null) {
                // 第三步：扫码开始解冻--只更新解冻开始时间
                return "Step3";
            } else if (ThawingStart != null && ThawingEnd != null && UnfreezeStart != null && UnfreezeEnd == null) {
                // 第四步：扫码结束解冻--只更新解冻结束时间和解冻使用时间
                return "Step4";
            } else {
                // 第五步：提醒回温解冻结束，请直接出库
                return "Step5";
            }
        } else {
            // 只有解冻
            if (ThawingStart == null && ThawingEnd == null && UnfreezeStart == null && UnfreezeEnd == null) {
                return "Step3";
            } else if (ThawingStart == null && ThawingEnd == null && UnfreezeStart != null && UnfreezeEnd == null) {
                return "Step4";
            } else {
                return "Step5";
            }
        }

    }

    /**
     * 在拥有脱泡流程的情况下，获取是第几步
     * 第一步：扫码开始脱泡
     * 第二步：扫码结束脱泡
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String getDeaerateStep(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        Date DeaerateStart = glueSysProcessInfo.getDeaerateStartTime();
        Date DeaerateEnd = glueSysProcessInfo.getDeaerateEndTime();
        if (DeaerateStart == null && DeaerateEnd == null) {
            return "Step1";
        } else {
            return "Step2";
        }
    }

    /**
     * 调用了这个方法，说明前面验证已经完成，要开始上线或者下线操作
     * 获取是第几步
     * 第一步：扫码上线
     * 第二步：扫码下线
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public String getLineStep(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        Date LineStart = glueSysProcessInfo.getLineStartTime();
        Date LineEnd = glueSysProcessInfo.getLineEndTime();
        if (LineStart == null && LineEnd == null) {
            return "Step1";
        } else {
            return "Step2";
        }
    }

    /**
     * 判断胶水解冻是否结束 true 结束
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeUnfreezeIsEnd(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getUnfreezeEndTime() != null;
    }

    /**
     * 判断胶水解冻是否开始 true 开始
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeUnfreezeIsStart(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getUnfreezeStartTime() != null;
    }

    /**
     * 获取胶水解冻剩余时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public Long getUnfreezeRemainTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        // 已经解冻时间
        long alreadyUnfreezeTime = GlueUtils.DateDifferentMinute(glueSysProcessInfo.getUnfreezeStartTime(), new Date());
        // 剩余解冻时间
        long remainUnfreezeTime = glueSysProcessInfo.getUnfreezeNeedTime() - alreadyUnfreezeTime;
        // 将负值差转换为0
        remainUnfreezeTime = Math.max(remainUnfreezeTime, 0);

        return remainUnfreezeTime;
    }

    /**
     * 判断胶水回温是否结束 true 结束
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeThawingIsEnd(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getThawingEndTime() != null;
    }

    /**
     * 获取胶水回温剩余时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public Long getThawingRemainTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        // 已经回温时间
        long alreadyThawingTime = GlueUtils.DateDifferentMinute(glueSysProcessInfo.getThawingStartTime(), new Date());
        // 剩余回温时间
        long remainThawingTime = glueSysProcessInfo.getThawingNeedTime() - alreadyThawingTime;
        // 将负差值转换为0
        remainThawingTime = Math.max(remainThawingTime, 0);
        return remainThawingTime;
    }

    /**
     * 判断胶水脱泡是否结束 true 结束
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeDeaerateIsEnd(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getDeaerateEndTime() != null;
    }

    /**
     * 判断胶水脱泡是否开始 true 开始
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeDeaerateIsStart(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getDeaerateStartTime() != null;
    }

    /**
     * 判断胶水是否已经上线使用 true 开始
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public boolean judgeLineIsStart(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        return glueSysProcessInfo.getLineStartTime() != null && glueSysProcessInfo.getLineEndTime() == null;
    }

    /**
     * 获取胶水脱泡剩余时间
     *
     * @param glueBarCode
     * @return
     */
    @Override
    public Long getDeaerateRemainTime(String glueBarCode) {
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        // 已经脱泡时间
        long alreadyDeaerateTime = GlueUtils.DateDifferentMinute(glueSysProcessInfo.getDeaerateStartTime(), new Date());
        // 剩余脱泡时间
        long remainDeaerateTime = glueSysProcessInfo.getDeaerateNeedTime() - alreadyDeaerateTime;
        // 将负差值转换为0
        remainDeaerateTime = Math.max(remainDeaerateTime, 0);
        return remainDeaerateTime;
    }

    /**
     * 注销胶水
     *
     * @param glueBarCode
     * @return
     */
    @Override
    @Transactional
    public String glueCancel(String glueBarCode) {
        // 先查到胶水在库信息
        GlueSysProcessInfo glueSysProcessInfo = glueSysProcessInfoMapper.selectGlueSysProcessInfoByGlueBarCode(glueBarCode);
        // 更新注销状态和注销时间
        if (glueSysProcessInfo == null) {
            return "胶水已注销，请勿重复注销";
        } else {
            glueSysProcessInfo.setIsCancel("true");
            glueSysProcessInfo.setCancelTime(new Date());
            int rowsUpdate = glueSysProcessInfoMapper.updateGlueSysProcessInfo(glueSysProcessInfo);
            // 插入到注销表中
            int rowsInsert = glueSysCancelInfoMapper.insertGlueSysCancelInfo(glueSysProcessInfo);
            // 删除在库表中的记录
            int rowsDelete = glueSysProcessInfoMapper.deleteGlueSysProcessInfoById(glueSysProcessInfo.getId());
            if (rowsUpdate > 0 && rowsInsert > 0 && rowsDelete > 0) {
                return "胶水注销成功";
            } else {
                return "胶水注销失败";
            }
        }
    }

    /**
     * 根据编码方式和胶水码获取厂商代码
     *
     * @param encodingFormat
     * @param glueBarCode
     * @return
     */
    private String getSupplierCodeByEncode(String encodingFormat, String glueBarCode) {
        List<String> List;
        if (encodingFormat.contains("ICT")) {
            List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
        } else {
            List = Arrays.asList(StringUtils.split(glueBarCode, "-"));
        }
        // 第零位是厂商代码
        return List.get(0);
    }

    /**
     * 根据编码方式和胶水码获取料号
     *
     * @param encodingFormat
     * @param glueBarCode
     * @return
     */
    private String getMaterialCodeByEncode(String encodingFormat, String glueBarCode) {
        List<String> List;
        if (encodingFormat.contains("ICT")) {
            List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
        } else {
            List = Arrays.asList(StringUtils.split(glueBarCode, "-"));
        }
        // 第一位是厂商代码
        return List.get(1);
    }

    /**
     * 根据编码方式和胶水码获取生产日期
     *
     * @param encodingFormat
     * @param glueBarCode
     * @return
     */
    private Date getProductionDateByEncode(String encodingFormat, String glueBarCode) {
        List<String> List;
        Date ProductionDate;
        if (encodingFormat.contains("ICT")) {
            List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
            // 第二位是生产日期
            ProductionDate = GlueUtils.GlueProductionDateByICT(List.get(2));
        } else {
            List = Arrays.asList(StringUtils.split(glueBarCode, "-"));
            // 第三位是生产日期
            ProductionDate = GlueUtils.GlueProductionDateByHenkel(List.get(3));
        }
        return ProductionDate;
    }

    /**
     * 根据编码方式和胶水码获取购买日期
     *
     * @param encodingFormat
     * @param glueBarCode
     * @return
     */
    private Date getShoppingDateByEncode(String encodingFormat, String glueBarCode) {
        List<String> List;
        if (encodingFormat.contains("ICT")) {
            List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
            // 第三位是购买日期
            return GlueUtils.GlueProductionDateByICT(List.get(3));
        } else {
            // Henkel码中无购买日期
            return null;
        }
    }

    /**
     * 根据编码方式和胶水码获取保质期
     *
     * @param encodingFormat
     * @param glueBarCode
     * @return
     */
    private String getShelfLifeByEncode(String encodingFormat, String glueBarCode) {
        List<String> List;
        if (encodingFormat.contains("ICT")) {
            List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
            // 第四位是保质期
            return List.get(4);
        } else {
            List = Arrays.asList(StringUtils.split(glueBarCode, "-"));
            // 第三位是生产日期
            Date ProductionDate = GlueUtils.GlueProductionDateByHenkel(List.get(3));
            // 第四位是到期日期
            Date DueDate = GlueUtils.GlueProductionDateByHenkel(List.get(4));
            // 保质期
            String month = GlueUtils.DateDifferenceMonth(ProductionDate, DueDate) + "M";
            return month;
        }
    }

    /**
     * 根据编码方式和胶水码获取重量
     *
     * @param encodingFormat
     * @param glueBarCode
     * @return
     */
    private String getWeightByEncode(String encodingFormat, String glueBarCode) {
        List<String> List;
        if (encodingFormat.contains("ICT")) {
            List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
            // 第五位是重量
            return List.get(5);
        } else {
            // Henkel码中无重量
            return null;
        }
    }

    /**
     * 通过ICT编码判断是否符合编码原则 true 符合
     *
     * @param basicSupplierCode
     * @param basicMaterialCode
     * @param glueBarCode
     * @return
     */
    private boolean IsMatchCodePrincipleByICT(String basicSupplierCode, String basicMaterialCode, String glueBarCode) {
        List<String> List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
        // 获取胶水码中的厂商代码 第零位
        String SupplierCode = List.get(0);
        // 获取胶水码中的料号 第一位
        String MaterialCode = List.get(1);
        return basicSupplierCode.contains(SupplierCode) && basicMaterialCode.contains(MaterialCode);
    }

    /**
     * 通过Henkel编码判断是否符合编码原则 true 符合
     *
     * @param basicSupplierCode
     * @param basicMaterialCode
     * @param glueBarCode
     * @return
     */
    private boolean IsMatchCodePrincipleByHenkel(String basicSupplierCode, String basicMaterialCode, String glueBarCode) {
        List<String> List = Arrays.asList(StringUtils.split(glueBarCode, "-"));
        // 获取胶水码中的厂商代码 第零位
        String SupplierCode = List.get(0);
        // 获取胶水码中的料号 第一位
        String MaterialCode = List.get(1);
        return basicSupplierCode.contains(SupplierCode) && basicMaterialCode.contains(MaterialCode);
    }

}

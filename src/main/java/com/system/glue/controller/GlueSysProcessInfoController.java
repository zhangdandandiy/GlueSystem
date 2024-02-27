package com.system.glue.controller;

import com.system.glue.domain.GlueSysBasicSetting;
import com.system.glue.domain.GlueSysProcessInfo;
import com.system.glue.domain.core.domain.AjaxResult;
import com.system.glue.domain.dto.GlueSysOutStorageParam;
import com.system.glue.service.IGlueSysBasicSettingService;
import com.system.glue.service.IGlueSysCancelInfoService;
import com.system.glue.service.IGlueSysProcessInfoService;
import com.system.glue.utils.GlueUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dandan
 * @date 2023/11/16 10:27
 **/
@Api(tags = "胶水流程内相关接口")
@RestController
@RequestMapping("/glue/process")
public class GlueSysProcessInfoController extends BaseController {

    @Autowired
    private IGlueSysProcessInfoService iGlueSysProcessInfoService;

    @Autowired
    IGlueSysBasicSettingService iGlueSysBasicSettingService;

    @Autowired
    IGlueSysCancelInfoService iGlueSysCancelInfoService;

    @ApiOperation("用于获取process中数据")
    @GetMapping("/list")
    public AjaxResult list(GlueSysProcessInfo glueSysProcessInfo) {
        return success(iGlueSysProcessInfoService.selectGlueSysProcessInfoList(glueSysProcessInfo));
    }

    @ApiOperation("用于修改process中数据")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody GlueSysProcessInfo glueSysProcessInfo) {
        return toAjax(iGlueSysProcessInfoService.updateGlueSysProcessInfo(glueSysProcessInfo));
    }

    @ApiOperation("胶水剩余寿命(min)")
    @GetMapping("/remainLife")
    public AjaxResult remainLife(String glueBarCode) {
        return success(iGlueSysProcessInfoService.getGlueResidualLife(glueBarCode));
    }

    @ApiOperation("胶水剩余保质期(min)")
    @GetMapping("/remainShelf")
    public AjaxResult remainShelf(String glueBarCode){
        return success(iGlueSysProcessInfoService.getGlueResidualShelf(glueBarCode));
    }

    @ApiOperation("胶水入库接口")
    @PostMapping("/inStorage")
    public AjaxResult inStorage(String projectName, String glueBarCode) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        if (iGlueSysCancelInfoService.selectGlueSysCancelInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水已注销，请勿入库");
        } else if (!(iGlueSysBasicSettingService.isGlueSysBasicSettingByProjectName(projectName))) {
            return error("不存在此专案的维护信息，请先维护相关信息");
        } else if (!(iGlueSysProcessInfoService.IsMatchCodePrinciple(projectName, glueBarCode))) {
            return error("此胶水条码不是" + glueSysBasicSetting.getEncodingFormat() + "编码胶水条码，不包含条码信息");
        } else if (GlueUtils.IsGlueExpire(glueSysBasicSetting.getEncodingFormat(), glueBarCode)) {
            return error("此胶水已过期，禁止入库");
        } else if (iGlueSysProcessInfoService.selectGlueSysProcessInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水已经入库，禁止再次入库");
        } else {
            return success(iGlueSysProcessInfoService.insertGlueSysProcessInfo(projectName, glueBarCode));
        }
    }

    @ApiOperation("胶水回温和解冻接口")
    @PutMapping("/thawingAndUnfreeze")
    public AjaxResult thawingAndUnfreeze(String projectName, String glueBarCode) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        boolean isGlueThawingUnfreeze = iGlueSysProcessInfoService.judgeGlueIsThawingAndUnfreeze(glueBarCode);

        if (iGlueSysCancelInfoService.selectGlueSysCancelInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水已注销");
        } else if (!iGlueSysProcessInfoService.selectGlueSysProcessInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水未入库，请先入库");
        } else if (!isGlueThawingUnfreeze) {
            return error("此胶水无回温和解冻流程，请直接出库");
        } else if (!(iGlueSysBasicSettingService.isGlueSysBasicSettingByProjectName(projectName))) {
            return error("不存在此专案的维护信息，请先维护相关信息");
        } else if (!(iGlueSysProcessInfoService.IsMatchCodePrinciple(projectName, glueBarCode))) {
            return error("此胶水条码不是" + glueSysBasicSetting.getEncodingFormat() + "编码胶水条码，不包含条码信息");
        } else if (GlueUtils.IsGlueExpire(glueSysBasicSetting.getEncodingFormat(), glueBarCode)) {
            return error("此胶水已过期，禁止回温和解冻");
        }

        String msg;
        String step = iGlueSysProcessInfoService.getThawingAndUnfreeStep(glueBarCode);
        switch (step) {
            case "Step1":
                // 第一步：扫码开始回温--只更新回温开始时间
                msg = iGlueSysProcessInfoService.updateGlueThawingStartTime(glueBarCode);
                if (msg.contains("成功")) {
                    return success(msg);
                } else {
                    return error(msg);
                }
            case "Step2":
                // 第二步：扫码结束回温--只更新回温结束时间和回温使用时间，并有正在回温中的警告
                // 获取剩余回温时间
                long remainThawingTime = iGlueSysProcessInfoService.getThawingRemainTime(glueBarCode);
                if (remainThawingTime > 0) {
                    return error("此胶水正在冰箱回温中，还需要 " + remainThawingTime + " 分钟");
                } else {
                    // 更新回温结束时间和回温使用时间
                    msg = iGlueSysProcessInfoService.updateGlueThawingEndTime(glueBarCode);
                    if (msg.contains("成功")) {
                        return success(msg);
                    } else {
                        return error(msg);
                    }
                }
            case "Step3":
                // 第三步：扫码开始解冻--只更新解冻开始时间
                msg = iGlueSysProcessInfoService.updateGlueUnfreezeStartTime(glueBarCode);
                if (msg.contains("成功")) {
                    return success(msg);
                } else {
                    return error(msg);
                }
            case "Step4":
                // 第四步：扫码结束解冻--只更新解冻结束时间和解冻使用时间，并有正在解冻中的警告
                // 获取剩余解冻时间
                long remainUnfreezeTime = iGlueSysProcessInfoService.getUnfreezeRemainTime(glueBarCode);
                if (remainUnfreezeTime > 0) {
                    return error("此胶水正在常温解冻中，还需要 " + remainUnfreezeTime + " 分钟");
                } else {
                    // 更新解冻结束时间和解冻使用时间
                    msg = iGlueSysProcessInfoService.updateGlueUnfreezeEndTime(glueBarCode);
                    if (msg.contains("成功")) {
                        return success(msg);
                    } else {
                        return error(msg);
                    }
                }
            default:
                return error("此胶水已完成回温和解冻，请直接出库");
        }
    }

    @ApiOperation("胶水出库接口")
    @PostMapping("/outStorage")
    public AjaxResult outStorage(@RequestBody GlueSysOutStorageParam glueSysOutStorageParam) {
        String projectName = glueSysOutStorageParam.getProjectName();
        String glueBarCode = glueSysOutStorageParam.getGlueBarCode();
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        if (iGlueSysCancelInfoService.selectGlueSysCancelInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水已注销，请勿出库");
        } else if (!iGlueSysProcessInfoService.selectGlueSysProcessInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水未入库，请先入库");
        } else if (!(iGlueSysBasicSettingService.isGlueSysBasicSettingByProjectName(projectName))) {
            return error("不存在此专案的维护信息，请先维护相关信息");
        } else if (!(iGlueSysProcessInfoService.IsMatchCodePrinciple(projectName, glueBarCode))) {
            return error("此胶水条码不是" + glueSysBasicSetting.getEncodingFormat() + "编码胶水条码，不包含条码信息");
        } else if (GlueUtils.IsGlueExpire(glueSysBasicSetting.getEncodingFormat(), glueBarCode)) {
            return error("此胶水已过期，禁止出库");
        } else if (iGlueSysProcessInfoService.judgeGlueIsUnfreeze(glueBarCode) && !iGlueSysProcessInfoService.judgeUnfreezeIsStart(glueBarCode)) {
            // 需要解冻并且解冻未开始
            return error("此胶水还未常温解冻，请先进行常温解冻流程");
        } else if (iGlueSysProcessInfoService.judgeGlueIsUnfreeze(glueBarCode) && !iGlueSysProcessInfoService.judgeUnfreezeIsEnd(glueBarCode)) {
            // 需要解冻并且解冻未结束
            long remainUnfreezeTime = iGlueSysProcessInfoService.getUnfreezeRemainTime(glueBarCode);
            return error("此胶水正在常温解冻中，还需要 " + remainUnfreezeTime + " 分钟，请先结束解冻");
        } else if (iGlueSysProcessInfoService.selectGlueIsOutStorage(glueBarCode)) {
            return error("此胶水已经出库，请勿重复出库");
        } else {
            String msg = iGlueSysProcessInfoService.updateGlueOutStorageTime(glueSysOutStorageParam);
            if (msg.contains("成功")) {
                return success(msg);
            } else {
                return error(msg);
            }
        }
    }

    @ApiOperation("胶水脱泡接口")
    @PutMapping("/deaerate")
    public AjaxResult deaerate(String projectName, String glueBarCode) {
        String msg;
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        if (iGlueSysCancelInfoService.selectGlueSysCancelInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水已注销");
        } else if (!iGlueSysProcessInfoService.selectGlueSysProcessInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水未入库，请先入库");
        } else if (!iGlueSysProcessInfoService.judgeGlueIsDeaerate(glueBarCode)) {
            return error("此胶水没有脱泡流程，请直接上线使用");
        } else if (!(iGlueSysBasicSettingService.isGlueSysBasicSettingByProjectName(projectName))) {
            return error("不存在此专案的维护信息，请先维护相关信息");
        } else if (!(iGlueSysProcessInfoService.IsMatchCodePrinciple(projectName, glueBarCode))) {
            return error("此胶水条码不是" + glueSysBasicSetting.getEncodingFormat() + "编码胶水条码，不包含条码信息");
        } else if (GlueUtils.IsGlueExpire(glueSysBasicSetting.getEncodingFormat(), glueBarCode)) {
            return error("此胶水已过期，禁止脱泡");
        } else if (!iGlueSysProcessInfoService.selectGlueIsOutStorage(glueBarCode)) {
            return error("此胶水未出库，请先出库");
        } else if (iGlueSysProcessInfoService.judgeDeaerateIsEnd(glueBarCode)) {
            return error("此胶水已完成脱泡，请直接上线使用");
        } else if (iGlueSysProcessInfoService.judgeGlueIsUnfreeze(glueBarCode) && !iGlueSysProcessInfoService.judgeUnfreezeIsStart(glueBarCode)) {
            // 需要解冻并且解冻未开始
            return error("此胶水还未常温解冻，请先进行常温解冻流程");
        } else if (iGlueSysProcessInfoService.judgeGlueIsUnfreeze(glueBarCode) && !iGlueSysProcessInfoService.judgeUnfreezeIsEnd(glueBarCode)) {
            // 需要解冻并且解冻未结束
            long remainUnfreezeTime = iGlueSysProcessInfoService.getUnfreezeRemainTime(glueBarCode);
            return error("此胶水正在常温解冻中，还需要 " + remainUnfreezeTime + " 分钟，请先结束解冻");
        } else {
            String step = iGlueSysProcessInfoService.getDeaerateStep(glueBarCode);
            if (step.equals("Step1")) {
                // 第一步：扫码开始脱泡，更新脱泡开始时间
                msg = iGlueSysProcessInfoService.updateGlueDeaerateStarTime(glueBarCode);
            } else {
                // 第二步：扫码结束脱泡，更新脱泡结束时间
                long remainDeaerateTime = iGlueSysProcessInfoService.getDeaerateRemainTime(glueBarCode);
                if (remainDeaerateTime > 0) {
                    return error("此胶水正在脱泡中，还需要 " + remainDeaerateTime + " 分钟");
                } else {
                    msg = iGlueSysProcessInfoService.updateGlueDeaerateEndTime(glueBarCode);
                }
            }
            if (msg.contains("成功")) {
                return success(msg);
            } else {
                return error(msg);
            }
        }
    }

    @ApiOperation("胶水注销接口")
    @PutMapping("/cancel")
    public AjaxResult cancel(String glueBarCode) {
        String msg = iGlueSysProcessInfoService.glueCancel(glueBarCode);
        if (msg.contains("成功")) {
            return success(msg);
        } else {
            return error(msg);
        }
    }

    @ApiOperation("胶水上线接口")
    @PostMapping("/inLine")
    public AjaxResult inLine(@RequestBody GlueSysOutStorageParam glueSysOutStorageParam) {
        String msg;
        String projectName = glueSysOutStorageParam.getProjectName();
        String glueBarCode = glueSysOutStorageParam.getGlueBarCode();
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        GlueSysProcessInfo glueSysProcessInfo = iGlueSysProcessInfoService.selectGlueSysProcessInfo(glueBarCode);
        if (iGlueSysCancelInfoService.selectGlueSysCancelInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水已注销，请勿上线使用");
        } else if (!iGlueSysProcessInfoService.selectGlueSysProcessInfoByGlueBarCode(glueBarCode)) {
            return error("此胶水未入库，请先入库");
        } else if (!(iGlueSysBasicSettingService.isGlueSysBasicSettingByProjectName(projectName))) {
            return error("不存在此专案的维护信息，请先维护相关信息");
        } else if (!(iGlueSysProcessInfoService.IsMatchCodePrinciple(projectName, glueBarCode))) {
            return error("此胶水条码不是" + glueSysBasicSetting.getEncodingFormat() + "编码胶水条码，不包含条码信息");
        } else if (GlueUtils.IsGlueExpire(glueSysBasicSetting.getEncodingFormat(), glueBarCode)) {
            return error("此胶水已过期，禁止上线使用");
        } else if (iGlueSysProcessInfoService.judgeGlueIsUnfreeze(glueBarCode) && !iGlueSysProcessInfoService.judgeUnfreezeIsStart(glueBarCode)) {
            // 需要解冻并且解冻未开始
            return error("此胶水还未常温解冻，请先进行常温解冻流程");
        } else if (iGlueSysProcessInfoService.judgeGlueIsUnfreeze(glueBarCode) && !iGlueSysProcessInfoService.judgeUnfreezeIsEnd(glueBarCode)) {
            // 需要解冻并且解冻未结束
            long remainUnfreezeTime = iGlueSysProcessInfoService.getUnfreezeRemainTime(glueBarCode);
            return error("此胶水正在常温解冻中，还需要 " + remainUnfreezeTime + " 分钟，请先结束解冻");
        } else if (iGlueSysProcessInfoService.judgeGlueIsDeaerate(glueBarCode) && !iGlueSysProcessInfoService.judgeDeaerateIsStart(glueBarCode)) {
            // 需要脱泡并且脱泡未开始
            return error("此胶水还未脱泡，请先进行脱泡流程");
        } else if (iGlueSysProcessInfoService.judgeGlueIsDeaerate(glueBarCode) && !iGlueSysProcessInfoService.judgeDeaerateIsEnd(glueBarCode)) {
            // 需要脱泡并且脱泡未结束
            long remainDeaerateTime = iGlueSysProcessInfoService.getDeaerateRemainTime(glueBarCode);
            return error("此胶水正在脱泡中，还需要 " + remainDeaerateTime + " 分钟，请先结束脱泡");
        } else if (iGlueSysProcessInfoService.judgeLineIsStart(glueBarCode)) {
            String lineName = glueSysProcessInfo.getLineName();
            String machineName = glueSysProcessInfo.getGlueMachineName();
            return error("此胶水正在 " + lineName + " 线 " + machineName + " 使用，请勿重复扫码");
        } else if (iGlueSysProcessInfoService.judgeGlueIsUsedMachine(glueSysOutStorageParam)) {
            return error("此台点胶机正在使用" + glueBarCode + "胶水！");
        } else if (!glueSysOutStorageParam.getGlueMachineName().equals(glueSysProcessInfo.getGlueMachineName())) {
            return error("此胶水用于 " + glueSysProcessInfo.getGlueMachineName() + " ，但是本台点胶机编号为 " + glueSysOutStorageParam.getGlueMachineName());
        } else {
            // 扫码上线，更新上线时间以及线上状态
            msg = iGlueSysProcessInfoService.updateGlueLineStartTime(glueBarCode);
            if (msg.contains("成功")) {
                return success(msg);
            } else {
                return error(msg);
            }
        }
    }

    @ApiOperation("胶水下线接口")
    @PostMapping("/outLine")
    public AjaxResult outLine(@RequestBody GlueSysOutStorageParam glueSysOutStorageParam) {
        String projectName = glueSysOutStorageParam.getProjectName();
        String glueBarCode = glueSysOutStorageParam.getGlueBarCode();
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        GlueSysProcessInfo glueSysProcessInfo = iGlueSysProcessInfoService.selectGlueSysProcessInfo(glueBarCode);
        if (!(iGlueSysProcessInfoService.IsMatchCodePrinciple(projectName, glueBarCode))) {
            return error("此胶水条码不是" + glueSysBasicSetting.getEncodingFormat() + "编码胶水条码，不包含条码信息");
        } else if (!iGlueSysProcessInfoService.judgeLineIsStart(glueBarCode)) {
            // 胶水未上线使用
            return error("此胶水未上线使用，请先上线使用");
        } else if (!glueSysOutStorageParam.getGlueMachineName().equals(glueSysProcessInfo.getGlueMachineName())) {
            return error("此胶水用于 " + glueSysProcessInfo.getGlueMachineName() + " ，但是本台点胶机编号为 " + glueSysOutStorageParam.getGlueMachineName());
        } else {
            // 扫码下线，更新下线时间以及线上状态
            String msg = iGlueSysProcessInfoService.updateGlueLineEndTime(glueBarCode);
            if (msg.contains("成功")) {
                return success(msg);
            } else {
                return error(msg);
            }
        }
    }

}

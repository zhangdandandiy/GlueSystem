package com.system.glue.service;

import com.system.glue.domain.GlueSysProcessInfo;
import com.system.glue.domain.dto.GlueSysOutStorageParam;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/15 16:30
 **/
public interface IGlueSysProcessInfoService {

    /**
     * 判断某台点胶机是否正在使用 true 正在使用
     *
     * @return
     */
    public boolean judgeGlueIsUsedMachine(GlueSysOutStorageParam glueSysOutStorageParam);

    /**
     * 修改胶水流程内信息
     *
     * @param glueSysProcessInfo 胶水流程内信息
     * @return 结果
     */
    public int updateGlueSysProcessInfo(GlueSysProcessInfo glueSysProcessInfo);

    /**
     * 查询胶水流程内信息列表
     *
     * @param glueSysProcessInfo 胶水流程内信息
     * @return 胶水流程内信息集合
     */
    public List<GlueSysProcessInfo> selectGlueSysProcessInfoList(GlueSysProcessInfo glueSysProcessInfo);

    /**
     * 通过胶水码判断胶水是否已经在库 true 在库
     *
     * @param glueBarCode
     * @return
     */
    public boolean selectGlueSysProcessInfoByGlueBarCode(String glueBarCode);

    /**
     * 通过胶水码查询胶水信息
     *
     * @param glueBarCode
     * @return
     */
    public GlueSysProcessInfo selectGlueSysProcessInfo(String glueBarCode);

    /**
     * 判断胶水是否已出库 true 已出库
     *
     * @param glueBarCode
     * @return
     */
    public boolean selectGlueIsOutStorage(String glueBarCode);

    /**
     * 通过胶水码和使用专案判断是否符合编码原则
     *
     * @param ProjectName
     * @param glueBarCode
     * @return
     */
    public boolean IsMatchCodePrinciple(String ProjectName, String glueBarCode);

    /**
     * 新增胶水流程内信息
     *
     * @param projectName 该胶水要使用的专案名
     * @param glueBarCode 入库扫描的胶水码
     * @return
     */
    public String insertGlueSysProcessInfo(String projectName, String glueBarCode);

    /**
     * 根据胶水码获取胶水剩余寿命
     *
     * @param glueBarCode
     * @return
     */
    public Long getGlueResidualLife(String glueBarCode);

    /**
     * 根据胶水码获取胶水剩余保质期
     *
     * @param glueBarCode
     * @return
     */
    public Long getGlueResidualShelf(String glueBarCode);

    /**
     * 根据胶水码获取胶水已经使用寿命
     *
     * @param glueBarCode
     * @return
     */
    public Long getAlreadyUsedLife(String glueBarCode);

    /**
     * 判断胶水是否有回温和解冻流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeGlueIsThawingAndUnfreeze(String glueBarCode);

    /**
     * 判断胶水是否有回温流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeGlueIsThawing(String glueBarCode);

    /**
     * 判断胶水是否有解冻流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeGlueIsUnfreeze(String glueBarCode);

    /**
     * 判断胶水是否有脱泡流程 true 有
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeGlueIsDeaerate(String glueBarCode);

    /**
     * 更新胶水回温开始时间
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueThawingStartTime(String glueBarCode);

    /**
     * 更新胶水回温结束时间以及回温花费时间
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueThawingEndTime(String glueBarCode);

    /**
     * 更新胶水解冻开始时间
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueUnfreezeStartTime(String glueBarCode);

    /**
     * 更新胶水解冻结束时间以及解冻花费时间
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueUnfreezeEndTime(String glueBarCode);

    /**
     * 更新胶水出库状态以及出库时间
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueOutStorageTime(GlueSysOutStorageParam glueSysOutStorageParam);

    /**
     * 更新胶水脱泡开始时间
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueDeaerateStarTime(String glueBarCode);

    /**
     * 更新胶水上线时间及线上状态
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueLineStartTime(String glueBarCode);

    /**
     * 更新胶水下线时间及线上状态
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueLineEndTime(String glueBarCode);

    /**
     * 更新胶水脱泡结束时间以及脱泡花费时间
     *
     * @param glueBarCode
     * @return
     */
    public String updateGlueDeaerateEndTime(String glueBarCode);

    /**
     * 在只有解冻流程下，获取是第几步(第一步：扫码开始解冻 第二步：扫码结束解冻)
     *
     * @param glueBarCode
     * @return
     */
    public String getUnfreezeStep(String glueBarCode);

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
    public String getThawingAndUnfreeStep(String glueBarCode);

    /**
     * 调用了这个方法，说明一定存在脱泡流程
     * 在拥有脱泡流程的情况下，获取是第几步
     * 第一步：扫码开始脱泡
     * 第二步：扫码结束脱泡
     *
     * @param glueBarCode
     * @return
     */
    public String getDeaerateStep(String glueBarCode);

    /**
     * 调用了这个方法，说明前面验证已经完成，要开始上线或者下线操作
     * 获取是第几步
     * 第一步：扫码上线
     * 第二步：扫码下线
     *
     * @param glueBarCode
     * @return
     */
    public String getLineStep(String glueBarCode);

    /**
     * 判断胶水解冻是否结束 true 结束
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeUnfreezeIsEnd(String glueBarCode);

    /**
     * 判断胶水解冻是否开始 true 开始
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeUnfreezeIsStart(String glueBarCode);

    /**
     * 获取胶水解冻剩余时间
     *
     * @param glueBarCode
     * @return
     */
    public Long getUnfreezeRemainTime(String glueBarCode);

    /**
     * 判断胶水回温是否结束 true 结束
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeThawingIsEnd(String glueBarCode);

    /**
     * 获取胶水回温剩余时间
     *
     * @param glueBarCode
     * @return
     */
    public Long getThawingRemainTime(String glueBarCode);

    /**
     * 判断胶水脱泡是否结束 true 结束
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeDeaerateIsEnd(String glueBarCode);

    /**
     * 判断胶水脱泡是否开始 true 开始
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeDeaerateIsStart(String glueBarCode);

    /**
     * 判断胶水是否已经上线使用 true 开始
     *
     * @param glueBarCode
     * @return
     */
    public boolean judgeLineIsStart(String glueBarCode);

    /**
     * 获取胶水脱泡剩余时间
     *
     * @param glueBarCode
     * @return
     */
    public Long getDeaerateRemainTime(String glueBarCode);

    /**
     * 注销胶水
     *
     * @param glueBarCode
     * @return
     */
    public String glueCancel(String glueBarCode);

}

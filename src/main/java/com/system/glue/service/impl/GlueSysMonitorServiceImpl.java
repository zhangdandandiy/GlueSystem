package com.system.glue.service.impl;

import com.system.glue.domain.GlueSysBasicSetting;
import com.system.glue.domain.GlueSysMonitorLog;
import com.system.glue.domain.GlueSysProcessInfo;
import com.system.glue.mapper.GlueSysMonitorLogMapper;
import com.system.glue.mapper.GlueSysPersonListMapper;
import com.system.glue.mapper.GlueSysProcessInfoMapper;
import com.system.glue.service.IGlueSysBasicSettingService;
import com.system.glue.service.IGlueSysMonitorService;
import com.system.glue.service.IGlueSysProcessInfoService;
import com.system.glue.utils.GlueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dandan
 * @date 2023/11/20 14:48
 **/

@Service
public class GlueSysMonitorServiceImpl implements IGlueSysMonitorService {

    @Autowired
    private IGlueSysProcessInfoService iGlueSysProcessInfoService;

    @Autowired
    private GlueSysProcessInfoMapper glueSysProcessInfoMapper;

    @Autowired
    private GlueSysMonitorLogMapper glueSysMonitorLogMapper;

    @Autowired
    private IGlueSysBasicSettingService iGlueSysBasicSettingService;

    @Autowired
    private GlueSysPersonListMapper glueSysPersonListMapper;

    /**
     * 获取所有需要监控条码的信息
     *
     * @return
     */
    @Override
    public List<String> getGlueMsgLogByGlueBarCode() {
        List<String> MsgList = new ArrayList<>();
        List<GlueSysProcessInfo> list = getNeedMonitorGlueBarCode();
        for (GlueSysProcessInfo glueSysProcessInfo : list) {
            List<String> everyMsgList = judgeGlueMsgLog(glueSysProcessInfo);
            String everyMsg = String.join("\n", everyMsgList);
            MsgList.add(everyMsg);
        }
        return MsgList;
    }

    /**
     * 插入所有需要监控条码的报警&预警信息
     * 每2分钟执行一次
     *
     * @return
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public int InsertGlueMsgLog() {
        System.out.println("开始调用插入log " + GlueUtils.ConvertStringFormat(new Date()));
        int rows = 0;
        GlueSysMonitorLog glueSysMonitorLog = new GlueSysMonitorLog();
        List<GlueSysProcessInfo> list = getNeedMonitorGlueBarCode();
        for (GlueSysProcessInfo glueSysProcessInfo : list) {
            List<String> everyMsgList = judgeGlueMsgLog(glueSysProcessInfo);

            if (everyMsgList.size() > 0) {
                glueSysMonitorLog.setGlueBarCode(glueSysProcessInfo.getGlueBarCode());
                glueSysMonitorLog.setType(getGlueMsgType(everyMsgList));
                // 有不重复的再插入
                if (!isInsertGlueMsg(glueSysMonitorLog)) {
                    glueSysMonitorLog.setGlueMachineName(glueSysProcessInfo.getGlueMachineName());
                    glueSysMonitorLog.setProjectName(glueSysProcessInfo.getProjectName());
                    glueSysMonitorLog.setLineName(glueSysProcessInfo.getLineName());
                    glueSysMonitorLog.setAlarmMsg(subGlueMsgList(everyMsgList));
                    glueSysMonitorLog.setCreateTime(new Date());
                    glueSysMonitorLog.setStatus(0);
                    List<GlueSysMonitorLog> glueSysMonitorLogList = glueSysMonitorLogMapper.selectGlueSysMonitorLogList(glueSysMonitorLog);
                    rows = glueSysMonitorLogMapper.insertGlueSysMonitorLog(glueSysMonitorLog);
                }
            }
        }
        return rows;
    }

    /**
     * 每分钟都向企业微信推送消息
     *
     * @return
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public String WeComSendMsg() {
        System.out.println("开始调用发送消息 " + GlueUtils.ConvertStringFormat(new Date()));
        String responseMsg = null;
        List<String> projectList = getNeedMonitorProject();
        for (String projectName : projectList) {
            // 查询要发送的消息
            List<GlueSysMonitorLog> list = glueSysMonitorLogMapper.selectGlueSysMonitorLogByProjectName(projectName);
            for (GlueSysMonitorLog glueSysMonitorLog : list) {

                String url = "https://m.luxshare-ict.com/api/WorkWeChat/SendTextMessage";
                Integer SendApp = 2;
                // 查询要发送的人
                List<String> personList = glueSysPersonListMapper.getPersonNumberList(projectName);
                String EmpCodes = String.join(",", personList);
                // 有消息才发送
                String sendMsg = getCompleteMsg(glueSysMonitorLog);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                // 构建请求体
                String postData = String.format("{\"SendApp\":\"%s\",\"Account\":\"%s\",\"Password\":\"%s\",\"EmpCodes\":\"%s\",\"Content\":\"%s\"}", SendApp, "12213243", "7kcH8S0Bt&^buGtS", EmpCodes, sendMsg);

                HttpEntity<String> entity = new HttpEntity<>(postData, headers);

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
                responseMsg = response.getBody();
                glueSysMonitorLog.setStatus(1);
                glueSysMonitorLogMapper.updateGlueSysMonitorLog(glueSysMonitorLog);
            }
        }
        return responseMsg;
    }

    /**
     * 获取完整的共同的消息列表
     *
     * @param glueSysMonitorLog
     * @return
     */
    private String getCompleteMsg(GlueSysMonitorLog glueSysMonitorLog) {
        // 专案
        String productName = "专案：" + (glueSysMonitorLog.getProjectName() == null ? "空" : glueSysMonitorLog.getProjectName()) + "\n";
        // 线体编号
        String lineName = "线体编号：" + (glueSysMonitorLog.getLineName() == null ? "空" : glueSysMonitorLog.getLineName()) + "\n";
        // 点胶机
        String machineName = "点胶机：" + (glueSysMonitorLog.getGlueMachineName() == null ? "空" : glueSysMonitorLog.getGlueMachineName()) + "\n";
        // 胶水码
        String glueBarCode = "胶水码：" + (glueSysMonitorLog.getGlueBarCode() == null ? "空" : glueSysMonitorLog.getGlueBarCode()) + "\n";
        // 警示信息
        String msg = "报警信息：" + (glueSysMonitorLog.getAlarmMsg() == null ? "空" : glueSysMonitorLog.getAlarmMsg()) + "\n";
        // 当前时间
        String currentTime = "当前时间：" + GlueUtils.ConvertStringFormat(new Date());
        return productName + lineName + machineName + glueBarCode + msg + currentTime;
    }

    /**
     * 是否已插入该胶水码的此类型预警&报警信息 true 已插入
     *
     * @param glueSysMonitorLog
     * @return
     */
    private boolean isInsertGlueMsg(GlueSysMonitorLog glueSysMonitorLog) {
        List<GlueSysMonitorLog> list = glueSysMonitorLogMapper.selectGlueSysMonitorLogList(glueSysMonitorLog);
        return list.size() > 0;
    }

    /**
     * 获取消息类型
     *
     * @param MsgList
     * @return
     */
    private int getGlueMsgType(List<String> MsgList) {
        String typeStr = MsgList.get(5);
        int type = 6;
        if (typeStr.contains("回温预警")) {
            type = 0;
        } else if (typeStr.contains("回温报警")) {
            type = 1;
        } else if (typeStr.contains("解冻报警")) {
            type = 2;
        } else if (typeStr.contains("脱泡报警")) {
            type = 3;
        } else if (typeStr.contains("线上预警")) {
            type = 4;
        } else if (typeStr.contains("线上报警")) {
            type = 5;
        }
        return type;
    }

    /**
     * 获取需要插入数据库的预警&报警信息
     *
     * @param MsgList
     * @return
     */
    public String subGlueMsgList(List<String> MsgList) {
        return MsgList.stream()
                .skip(6) // 跳过前 6 个元素
                .limit(2) // 限制只取 2 个元素
                .collect(Collectors.joining(";"));
    }

    /**
     * 获取预警&报警消息
     * 0回温预警/1回温报警/2解冻报警/3脱泡报警/4线上使用预警/5线上使用报警
     *
     * @param glueSysProcessInfo
     * @return
     */
    public List<String> judgeGlueMsgLog(GlueSysProcessInfo glueSysProcessInfo) {
        // int type = 6;
        // 线上胶水寿命消息
        List<String> Msg = GlueLineLifeWarn(glueSysProcessInfo);
        if (Msg.size() == 0) {
            if (judgeGlueInThawing(glueSysProcessInfo)) {
                // 在回温中，获取回温消息
                Msg = ThawingWarnAndAlarm(glueSysProcessInfo);
            } else if (judgeGlueInUnfreeze(glueSysProcessInfo)) {
                // 在解冻中，获取解冻消息
                Msg = UnfreezeAlarm(glueSysProcessInfo);
            } else if (judgeGlueInDeaerate(glueSysProcessInfo)) {
                // 在脱泡中，获取脱泡消息
                Msg = DeaerateAlarm(glueSysProcessInfo);
            }
        }
        return Msg;
    }

    /**
     * 判断胶水是否在脱泡中  true 在脱泡中
     *
     * @param glueSysProcessInfo
     * @return
     */
    public boolean judgeGlueInDeaerate(GlueSysProcessInfo glueSysProcessInfo) {
        // 胶水码
        String glueBarCode = glueSysProcessInfo.getGlueBarCode();
        // 有脱泡流程并且脱泡已开始但没有结束，则判定胶水在脱泡中
        return iGlueSysProcessInfoService.judgeGlueIsDeaerate(glueBarCode) && glueSysProcessInfo.getDeaerateStartTime() != null && glueSysProcessInfo.getDeaerateEndTime() == null;
    }

    /**
     * 判断胶水是否在解冻中 true 在解冻中
     *
     * @param glueSysProcessInfo
     * @return
     */
    public boolean judgeGlueInUnfreeze(GlueSysProcessInfo glueSysProcessInfo) {
        // 胶水码
        String glueBarCode = glueSysProcessInfo.getGlueBarCode();
        // 有解冻流程并且解冻已开始但没结束，则判定胶水在解冻中
        return iGlueSysProcessInfoService.judgeGlueIsUnfreeze(glueBarCode) && glueSysProcessInfo.getUnfreezeStartTime() != null && glueSysProcessInfo.getUnfreezeEndTime() == null;
    }

    /**
     * 判断胶水是否在回温中 true 在回温中
     *
     * @param glueSysProcessInfo
     * @return
     */
    public boolean judgeGlueInThawing(GlueSysProcessInfo glueSysProcessInfo) {
        // 胶水码
        String glueBarCode = glueSysProcessInfo.getGlueBarCode();
        // 有回温流程并且回温已开始但没结束，则判定为胶水在回温中
        return iGlueSysProcessInfoService.judgeGlueIsThawing(glueBarCode) && glueSysProcessInfo.getThawingStartTime() != null && glueSysProcessInfo.getThawingEndTime() == null;
    }

    /**
     * 回温预警&报警 无返回长度0
     *
     * @param glueSysProcessInfo
     * @return
     */
    public List<String> ThawingWarnAndAlarm(GlueSysProcessInfo glueSysProcessInfo) {
        // 回温上限
        Long ThawingUpper = getThawingUpper(glueSysProcessInfo.getProjectName());
        // 回温下限
        Long ThawingLower = getThawingLower(glueSysProcessInfo.getProjectName());
        // 回温需要时间
        Long ThawingNeed = glueSysProcessInfo.getThawingNeedTime();
        // 回温开始时间
        Date ThawingStart = glueSysProcessInfo.getThawingStartTime();
        // 已经回温时间(min)
        Long alreadyTime = GlueUtils.DateDifferentMinute(ThawingStart, new Date());
        List<String> MsgList = new ArrayList<>();
        if (alreadyTime + 5 >= ThawingNeed && alreadyTime <= ThawingNeed) {
            MsgList = getCommonMsg(glueSysProcessInfo);
            MsgList.add("类型：回温预警");
            MsgList.add("回温将在5分钟后结束，开始回温时间：" + GlueUtils.ConvertStringFormat(ThawingStart));
        } else if (alreadyTime > ThawingNeed) {
            MsgList = getCommonMsg(glueSysProcessInfo);
            MsgList.add("类型：回温报警");
            MsgList.add("回温超时，开始回温时间：" + GlueUtils.ConvertStringFormat(ThawingStart));
        }
        MsgList.add("已经回温：" + alreadyTime + "(min)");
        MsgList.add("回温上限：" + ThawingUpper + "(min)");
        MsgList.add("回温下限：" + ThawingLower + "(min)");
        // ThawingAlarmMsg = String.join(",", MsgList);
        return MsgList;
    }

    /**
     * 解冻报警 无返回长度0
     *
     * @param glueSysProcessInfo
     * @return
     */
    private List<String> UnfreezeAlarm(GlueSysProcessInfo glueSysProcessInfo) {
        // 解冻上限
        Long UnfreezeUpper = getUnfreezeUpper(glueSysProcessInfo.getProjectName());
        // 解冻下限
        Long UnfreezeLower = getUnfreezeLower(glueSysProcessInfo.getProjectName());
        // 解冻需要时间
        Long UnfreezeNeed = glueSysProcessInfo.getUnfreezeNeedTime();
        // 解冻开始时间
        Date UnfreezeStart = glueSysProcessInfo.getUnfreezeStartTime();
        // 已经解冻时间
        Long alreadyTime = GlueUtils.DateDifferentMinute(UnfreezeStart, new Date());
        List<String> MsgList = new ArrayList<>();
        if (alreadyTime > UnfreezeNeed) {
            MsgList = getCommonMsg(glueSysProcessInfo);
            MsgList.add("类型：解冻报警");
            MsgList.add("解冻超时，开始解冻时间：" + GlueUtils.ConvertStringFormat(UnfreezeStart));
            MsgList.add("已经解冻：" + alreadyTime + "(min)");
            MsgList.add("解冻上限：" + UnfreezeUpper + "(min)");
            MsgList.add("解冻下限：" + UnfreezeLower + "(min)");
            // UnfreezeAlarmMsg = String.join(",", MsgList);
        }
        return MsgList;
    }

    /**
     * 脱泡报警 无返回长度0
     *
     * @param glueSysProcessInfo
     * @return
     */
    private List<String> DeaerateAlarm(GlueSysProcessInfo glueSysProcessInfo) {
        // 脱泡上限
        Long DeaerateUpper = getDeaerateUpper(glueSysProcessInfo.getProjectName());
        // 脱泡下限
        Long DeaerateLower = getDeaerateLower(glueSysProcessInfo.getProjectName());
        // 脱泡需要时间
        Long DeaerateNeed = glueSysProcessInfo.getDeaerateNeedTime();
        // 脱泡开始时间
        Date DeaerateStart = glueSysProcessInfo.getDeaerateStartTime();
        // 已经脱泡时间
        long alreadyTime = GlueUtils.DateDifferentMinute(DeaerateStart, new Date());
        List<String> MsgList = new ArrayList<>();
        if (alreadyTime > DeaerateNeed) {
            MsgList = getCommonMsg(glueSysProcessInfo);
            MsgList.add("类型：脱泡报警");
            MsgList.add("脱泡超时，开始脱泡时间：" + GlueUtils.ConvertStringFormat(DeaerateStart));
            MsgList.add("已经脱泡：" + alreadyTime + "(min)");
            MsgList.add("脱泡上限：" + DeaerateUpper + "(min)");
            MsgList.add("脱泡下限：" + DeaerateLower + "(min)");
            // DeaerateAlarmMsg = String.join(",", MsgList);
        }
        return MsgList;
    }

    /**
     * 线上胶水预警&报警 无返回长度0
     *
     * @param glueSysProcessInfo
     * @return
     */
    private List<String> GlueLineLifeWarn(GlueSysProcessInfo glueSysProcessInfo) {
        // 线上预警时间
        Long LineLifeWarn = getGlueLineLifeWarn(glueSysProcessInfo.getProjectName());
        // 线上报警时间
        Long LineLifeAlarm = getGlueLineLifeAlarm(glueSysProcessInfo.getProjectName());
        // 已经使用的寿命
        Long alreadyUsedLife = iGlueSysProcessInfoService.getAlreadyUsedLife(glueSysProcessInfo.getGlueBarCode());
        List<String> MsgList = new ArrayList<>();
        if (alreadyUsedLife >= LineLifeWarn && alreadyUsedLife < LineLifeAlarm) {
            MsgList = getCommonMsg(glueSysProcessInfo);
            // 线上预警
            MsgList.add("类型：线上预警");
            MsgList.add("线上胶水预警中，预警时间：" + LineLifeWarn);
            MsgList.add("已经使用：" + alreadyUsedLife + "(min)");
        } else if (alreadyUsedLife >= LineLifeAlarm) {
            MsgList = getCommonMsg(glueSysProcessInfo);
            // 线上报警
            MsgList.add("类型：线上报警");
            MsgList.add("线上胶水报警中，报警时间：" + getGlueLineLifeAlarm(glueSysProcessInfo.getProjectName()));
            MsgList.add("已经使用：" + alreadyUsedLife + "(min)");
        }
        return MsgList;
    }

    /**
     * 根据胶水码和消息类型判断信息日志是否存在 true存在
     *
     * @param glueBarCode
     * @param type
     * @return
     */
    private boolean judgeIsMonitorLog(String glueBarCode, String type) {
        GlueSysMonitorLog glueSysMonitorLog = glueSysMonitorLogMapper.selectGlueSysMonitorLogByType(glueBarCode, type);
        return glueSysMonitorLog != null;
    }

    /**
     * 获取线上预警时间(min)
     *
     * @param projectName
     * @return
     */
    private Long getGlueLineLifeWarn(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getWarnLifeTime();
    }

    /**
     * 获取线上报警时间(min)
     *
     * @param projectName
     * @return
     */
    private Long getGlueLineLifeAlarm(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getAlarmLifeTime();
    }

    /**
     * 获取脱泡下限
     *
     * @param projectName
     * @return
     */
    private Long getDeaerateLower(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getDeaerateTimeLower();
    }

    /**
     * 获取脱泡上限
     *
     * @param projectName
     * @return
     */
    private Long getDeaerateUpper(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getDeaerateTimeUpper();
    }

    /**
     * 获取解冻下限
     *
     * @param projectName
     * @return
     */
    private Long getUnfreezeLower(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getUnfreezeTimeLower();
    }

    /**
     * 获取解冻上限
     *
     * @param projectName
     * @return
     */
    private Long getUnfreezeUpper(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getUnfreezeTimeUpper();
    }

    /**
     * 获取回温下限
     *
     * @param projectName
     * @return
     */
    private Long getThawingLower(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getThawingTimeLower();
    }

    /**
     * 获取回温上限
     *
     * @param projectName
     * @return
     */
    private Long getThawingUpper(String projectName) {
        GlueSysBasicSetting glueSysBasicSetting = iGlueSysBasicSettingService.selectGlueSysBasicSettingByProjectName(projectName);
        return glueSysBasicSetting.getThawingTimeUpper();
    }

    /**
     * 获取公共报警/预警消息
     *
     * @param glueSysProcessInfo
     * @return
     */
    private List<String> getCommonMsg(GlueSysProcessInfo glueSysProcessInfo) {
        List<String> msg = new ArrayList<>();
        // 专案
        String ProjectName = glueSysProcessInfo.getProjectName();
        // 胶水码
        String GlueBarCode = glueSysProcessInfo.getGlueBarCode();
        // 线别
        String LineName = glueSysProcessInfo.getLineName();
        // 点胶机
        String GlueMachineName = glueSysProcessInfo.getGlueMachineName();
        msg.add("当前时间：" + GlueUtils.ConvertStringFormat(new Date()));
        msg.add("专案：" + (ProjectName == null ? "空" : ProjectName));
        msg.add("线别：" + (LineName == null ? "空" : LineName));
        msg.add("点胶机：" + (GlueMachineName == null ? "空" : GlueMachineName));
        msg.add("胶水码：" + (GlueBarCode == null ? "空" : GlueBarCode));
        return msg;
    }

    /**
     * 查询所有需要监控的胶水码
     *
     * @return
     */
    private List<GlueSysProcessInfo> getNeedMonitorGlueBarCode() {
        List<String> projectList = getNeedMonitorProject();
        List<GlueSysProcessInfo> processInfoList = new ArrayList<>();
        for (String project : projectList) {
            List<GlueSysProcessInfo> list = glueSysProcessInfoMapper.selectGlueSysProcessInfoListByProjectName(project);
            processInfoList.addAll(list);
        }
        return processInfoList;
    }

    /**
     * 查询所有需要监控的专案
     *
     * @return
     */
    private List<String> getNeedMonitorProject() {
        GlueSysBasicSetting glueSysBasicSetting = new GlueSysBasicSetting();
        glueSysBasicSetting.setIsNeedRealTimeMonitor("true");
        List<GlueSysBasicSetting> list = iGlueSysBasicSettingService.selectGlueSysBasicSettingList(glueSysBasicSetting);
        List<String> projectList = new ArrayList<>();
        for (GlueSysBasicSetting basicSetting : list) {
            projectList.add(basicSetting.getProjectName());
        }
        return projectList;
    }

}

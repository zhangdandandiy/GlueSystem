package com.system.glue.controller;

import com.system.glue.domain.core.domain.AjaxResult;
import com.system.glue.service.IGlueSysMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dandan
 * @date 2023/11/20 15:12
 **/

@Api(tags = "胶水实时监控相关接口")
@RestController
@RequestMapping("/glue/monitor")
public class GlueSysMonitorController extends BaseController {

    @Autowired
    private IGlueSysMonitorService iGlueSysMonitorService;

    @ApiOperation("获取所有需要监控条码的报警和预警信息")
    @GetMapping("/glueLog")
    public AjaxResult glueLog() {
        return success(iGlueSysMonitorService.getGlueMsgLogByGlueBarCode());
    }

}

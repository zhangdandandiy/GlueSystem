package com.system.glue.controller;

import com.system.glue.domain.core.domain.AjaxResult;
import com.system.glue.domain.dto.GlueSysCancelInfoParam;
import com.system.glue.service.IGlueSysLifeControlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dandan
 * @date 2023/11/18 12:57
 **/

@Api(tags = "胶水寿命管控相关接口")
@RestController
@RequestMapping("/glue/life")
public class GlueSysLifeControlController extends BaseController {

    @Autowired
    private IGlueSysLifeControlService iGlueSysLifeControlService;

    @ApiOperation("线体寿命管控展示-展示胶水整个的寿命状况，预警显示黄色，报警显示红色，正常显示绿色")
    @GetMapping("/control")
    public AjaxResult controlGlueList(String projectName) {
        return success(iGlueSysLifeControlService.getGlueSysControlByProjectName(projectName));
    }

    @ApiOperation("库存胶水展示-展示在库未出库胶水的保质期状况")
    @GetMapping("/stock")
    public AjaxResult stockGlueList(String projectName) {
        return success(iGlueSysLifeControlService.getGlueSysStockByProjectName(projectName));
    }

    @ApiOperation("线边仓胶水展示-展示已出库胶水的状况-没经过脱泡(计不计脱泡时间无所谓，脱泡只有几分钟)")
    @GetMapping("/lineSide")
    public AjaxResult lineSideGlueList(String projectName) {
        return success(iGlueSysLifeControlService.getGlueSysLineSideByProjectName(projectName));
    }

    @ApiOperation("前几天使用胶水-展示前几天被使用结束的胶水-即已注销胶水-可传入时间参数")
    @PostMapping("/used")
    public AjaxResult usedGlueList(@RequestBody GlueSysCancelInfoParam glueSysCancelInfoParam) {
        return success(iGlueSysLifeControlService.getGlueSysCancelInfoByProjectName(glueSysCancelInfoParam));
    }

}

package com.system.glue.controller;

import com.system.glue.domain.core.domain.AjaxResult;
import com.system.glue.domain.process.*;
import com.system.glue.service.IGlueSysShowListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/16 14:38
 **/

@Api(tags = "胶水数据展示列表相关接口")
@RestController
@RequestMapping("/glue/show")
public class GlueSysShowListController extends BaseController {

    @Autowired
    private IGlueSysShowListService iGlueSysShowListService;

    /**
     * 查询在库胶水列表
     */
    @ApiOperation("查询在库中胶水列表数据")
    @GetMapping("/inStorage")
    public AjaxResult inStorageList(String projectName) {
        List<GlueSysInStorage> list = iGlueSysShowListService.selectGlueSysInStorageByProjectName(projectName);
        return success(list);
    }

    /**
     * 查询回温中胶水列表数据
     */
    @ApiOperation("查询正在回温中胶水列表数据-展示正在回温胶水的回温计时状况")
    @GetMapping("/inThawing")
    public AjaxResult inThawingList(String projectName) {
        List<GlueSysThawing> list = iGlueSysShowListService.selectGlueSysThawingByProjectName(projectName);
        return success(list);
    }

    /**
     * 查询常温解冻胶水列表数据
     */
    @ApiOperation("查询正在常温解冻中胶水列表数据-展示正在解冻胶水的解冻计时状况")
    @GetMapping("/inUnfreeze")
    public AjaxResult inUnfreeze(String projectName) {
        List<GlueSysUnfreeze> list = iGlueSysShowListService.selectGlueSysUnfreezeByProjectName(projectName);
        return success(list);
    }

    /**
     * 查询已出库胶水列表
     */
    @ApiOperation("查询已出库胶水列表数据")
    @GetMapping("/outStorage")
    public AjaxResult outStorageList(String projectName) {
        List<GlueSysOutStorage> list = iGlueSysShowListService.selectGlueSysOutStorageByProjectName(projectName);
        return success(list);
    }

    /**
     * 查询脱泡胶水列表
     */
    @ApiOperation("查询正在脱泡中胶水列表数据")
    @GetMapping("/inDeaerate")
    public AjaxResult inDeaerate(String projectName) {
        List<GlueSysDeaerate> list = iGlueSysShowListService.selectGlueSysDeaerateByProjectName(projectName);
        return success(list);
    }

}

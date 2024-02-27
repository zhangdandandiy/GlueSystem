package com.system.glue.controller;

import com.system.glue.domain.GlueSysBasicSetting;
import com.system.glue.domain.core.domain.AjaxResult;
import com.system.glue.service.IGlueSysBasicSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 胶水基础设置Controller
 *
 * @author Dandan
 * @date 2023/11/15 15:12
 **/

@Api(tags = "胶水基础设置相关接口")
@RestController
@RequestMapping("/glue/basic")
public class GlueSysBasicSettingController extends BaseController {
    @Autowired
    private IGlueSysBasicSettingService glueSysBasicSettingService;

    /**
     * 查询胶水基础设置列表
     */
    @ApiOperation("查询胶水基础设置列表")
    @GetMapping("/list")
    public AjaxResult list(GlueSysBasicSetting glueSysBasicSetting) {
        List<GlueSysBasicSetting> list = glueSysBasicSettingService.selectGlueSysBasicSettingList(glueSysBasicSetting);
        return success(list);
    }

    /**
     * 获取胶水基础设置详细信息
     */
    @ApiOperation("获取胶水基础设置详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(glueSysBasicSettingService.selectGlueSysBasicSettingById(id));
    }

    /**
     * 新增胶水基础设置
     */
    @ApiOperation("新增胶水基础设置")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody GlueSysBasicSetting glueSysBasicSetting) {
        String msg = glueSysBasicSettingService.insertGlueSysBasicSetting(glueSysBasicSetting);
        if (msg.contains("成功")) {
            return success(msg);
        } else {
            return error(msg);
        }
    }

    /**
     * 修改胶水基础设置
     */
    @ApiOperation("修改胶水基础设置")
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody GlueSysBasicSetting glueSysBasicSetting) {
        return toAjax(glueSysBasicSettingService.updateGlueSysBasicSetting(glueSysBasicSetting));
    }

    /**
     * 删除胶水基础设置
     */
    @ApiOperation("删除胶水基础设置")
    @DeleteMapping("/delete")
    public AjaxResult delete(Long id) {
        return toAjax(glueSysBasicSettingService.deleteGlueSysBasicSettingById(id));
    }

    /**
     * 获取所有专案列表
     */
    @ApiOperation("获取所有专案列表")
    @GetMapping("/allList")
    public AjaxResult getAllProjectName() {
        return success(glueSysBasicSettingService.getAllProjectName());
    }

}

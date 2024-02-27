package com.system.glue.controller;

import com.system.glue.domain.GlueSysPersonList;
import com.system.glue.domain.core.domain.AjaxResult;
import com.system.glue.service.IGlueSysPersonListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 胶水报警信息发送人员名单Controller
 *
 * @author Dandan
 * @date 2023/11/15 15:56
 **/
@Api(tags = "胶水人员名单相关接口")
@RestController
@RequestMapping("/glue/person")
public class GlueSysPersonListController extends BaseController {

    @Autowired
    private IGlueSysPersonListService glueSysPersonListService;

    /**
     * 查询胶水报警信息发送人员名单列表
     */
    @ApiOperation("/查询胶水报警信息发送人员名单列表")
    @GetMapping("/list")
    public AjaxResult list(GlueSysPersonList glueSysPersonList) {
        List<GlueSysPersonList> list = glueSysPersonListService.selectGlueSysPersonListList(glueSysPersonList);
        return success(list);
    }

    /**
     * 获取胶水报警信息发送人员名单详细信息
     */
    @ApiOperation("获取胶水报警信息发送人员名单详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(glueSysPersonListService.selectGlueSysPersonListById(id));
    }

    /**
     * 新增胶水报警信息发送人员名单
     */
    @ApiOperation("新增胶水报警信息发送人员名单")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody GlueSysPersonList glueSysPersonList) {
        return toAjax(glueSysPersonListService.insertGlueSysPersonList(glueSysPersonList));
    }

    /**
     * 修改胶水报警信息发送人员名单
     */
    @ApiOperation("修改胶水报警信息发送人员名单")
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody GlueSysPersonList glueSysPersonList) {
        return toAjax(glueSysPersonListService.updateGlueSysPersonList(glueSysPersonList));
    }

    /**
     * 删除胶水报警信息发送人员名单
     */
    @ApiOperation("删除胶水报警信息发送人员名单")
    @DeleteMapping("/delete")
    public AjaxResult remove(Long id) {
        return toAjax(glueSysPersonListService.deleteGlueSysPersonListById(id));
    }

}

package com.system.glue.service.impl;

import com.system.glue.domain.GlueSysPersonList;
import com.system.glue.mapper.GlueSysPersonListMapper;
import com.system.glue.service.IGlueSysPersonListService;
import com.system.glue.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 胶水报警信息发送人员名单Service业务层处理
 *
 * @author Dandan
 * @date 2023/11/15 15:53
 **/

@Service
public class GlueSysPersonListServiceImpl implements IGlueSysPersonListService {
    @Autowired
    private GlueSysPersonListMapper glueSysPersonListMapper;

    /**
     * 查询胶水报警信息发送人员名单
     *
     * @param id 胶水报警信息发送人员名单主键
     * @return 胶水报警信息发送人员名单
     */
    @Override
    public GlueSysPersonList selectGlueSysPersonListById(Long id) {
        return glueSysPersonListMapper.selectGlueSysPersonListById(id);
    }

    /**
     * 查询胶水报警信息发送人员名单列表
     *
     * @param glueSysPersonList 胶水报警信息发送人员名单
     * @return 胶水报警信息发送人员名单
     */
    @Override
    public List<GlueSysPersonList> selectGlueSysPersonListList(GlueSysPersonList glueSysPersonList) {
        return glueSysPersonListMapper.selectGlueSysPersonListList(glueSysPersonList);
    }

    /**
     * 新增胶水报警信息发送人员名单
     *
     * @param glueSysPersonList 胶水报警信息发送人员名单
     * @return 结果
     */
    @Override
    public int insertGlueSysPersonList(GlueSysPersonList glueSysPersonList) {
        glueSysPersonList.setCreateTime(DateUtils.getNowDate());
        return glueSysPersonListMapper.insertGlueSysPersonList(glueSysPersonList);
    }

    /**
     * 修改胶水报警信息发送人员名单
     *
     * @param glueSysPersonList 胶水报警信息发送人员名单
     * @return 结果
     */
    @Override
    public int updateGlueSysPersonList(GlueSysPersonList glueSysPersonList) {
        return glueSysPersonListMapper.updateGlueSysPersonList(glueSysPersonList);
    }

    /**
     * 删除胶水报警信息发送人员名单信息
     *
     * @param id 胶水报警信息发送人员名单主键
     * @return 结果
     */
    @Override
    public int deleteGlueSysPersonListById(Long id) {
        return glueSysPersonListMapper.deleteGlueSysPersonListById(id);
    }

}

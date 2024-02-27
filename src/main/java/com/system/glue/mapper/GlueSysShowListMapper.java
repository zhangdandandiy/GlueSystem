package com.system.glue.mapper;

import com.system.glue.domain.process.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/16 15:30
 **/

@Mapper
public interface GlueSysShowListMapper {

    /**
     * 通过专案查询在库胶水列表
     *
     * @param ProjectName
     * @return
     */
    public List<GlueSysInStorage> selectGlueSysInStorageByProjectName(String projectName);

    /**
     * 通过专案查询冰箱回温胶水列表
     *
     * @param projectName
     * @return
     */
    public List<GlueSysThawing> selectGlueSysThawingByProjectName(String projectName);

    /**
     * 通过专案查询常温解冻胶水列表
     *
     * @param projectName
     * @return
     */
    public List<GlueSysUnfreeze> selectGlueSysUnfreezeByProjectName(String projectName);

    /**
     * 通过专案查询已出库胶水列表
     *
     * @param projectName
     * @return
     */
    public List<GlueSysOutStorage> selectGlueSysOutStorageByProjectName(String projectName);

    /**
     * 通过专案查询脱泡胶水列表
     *
     * @param projectName
     * @return
     */
    public List<GlueSysDeaerate> selectGlueSysDeaerateByProjectName(String projectName);

}

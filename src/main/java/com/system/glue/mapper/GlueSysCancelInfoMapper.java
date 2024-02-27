package com.system.glue.mapper;

import com.system.glue.domain.GlueSysCancelInfo;
import com.system.glue.domain.GlueSysProcessInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/16 8:10
 **/

@Mapper
public interface GlueSysCancelInfoMapper {

    /**
     * 查询胶水注销信息通过id
     *
     * @param id 胶水注销信息主键
     * @return 胶水注销信息
     */
    public GlueSysCancelInfo selectGlueSysCancelInfoById(Long id);

    /**
     * 查询胶水注销信息通过胶水码
     *
     * @param glueBarCode
     * @return
     */
    public GlueSysCancelInfo selectGlueSysCancelInfoByGlueBarCode(String glueBarCode);

    /**
     * 查询胶水注销信息列表
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 胶水注销信息集合
     */
    public List<GlueSysCancelInfo> selectGlueSysCancelInfoList(GlueSysCancelInfo glueSysCancelInfo);

    /**
     * 新增胶水注销信息
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 结果
     */
    public int insertGlueSysCancelInfo(GlueSysProcessInfo glueSysProcessInfo);

    /**
     * 修改胶水注销信息
     *
     * @param glueSysCancelInfo 胶水注销信息
     * @return 结果
     */
    public int updateGlueSysCancelInfo(GlueSysCancelInfo glueSysCancelInfo);

    /**
     * 删除胶水注销信息
     *
     * @param id 胶水注销信息主键
     * @return 结果
     */
    public int deleteGlueSysCancelInfoById(Long id);

}

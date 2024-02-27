package com.system.glue.service;

import com.system.glue.domain.GlueSysCancelInfo;
import com.system.glue.domain.dto.GlueSysCancelInfoParam;
import com.system.glue.domain.dto.GlueSysControlInfo;
import com.system.glue.domain.process.GlueSysControl;
import com.system.glue.domain.process.GlueSysLineSide;
import com.system.glue.domain.process.GlueSysStock;

import java.util.List;

/**
 * @author Dandan
 * @date 2023/11/18 13:53
 **/
public interface IGlueSysLifeControlService {

    /**
     * 线体寿命管控展示-展示胶水整个的寿命状况
     *
     * @param projectName
     * @return
     */
    public List<GlueSysControlInfo> getGlueSysControlByProjectName(String projectName);

    /**
     * 胶水线边库列表展示
     *
     * @param projectName
     * @return
     */
    public List<GlueSysLineSide> getGlueSysLineSideByProjectName(String projectName);

    /**
     * 胶水库存列表展示
     *
     * @param projectName
     * @return
     */
    public List<GlueSysStock> getGlueSysStockByProjectName(String projectName);

    /**
     * 胶水被使用列表展示
     *
     * @param glueSysCancelInfoParam
     * @return
     */
    public List<GlueSysCancelInfo> getGlueSysCancelInfoByProjectName(GlueSysCancelInfoParam glueSysCancelInfoParam);
}

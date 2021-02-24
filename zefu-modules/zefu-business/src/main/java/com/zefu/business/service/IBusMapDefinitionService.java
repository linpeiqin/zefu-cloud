package com.zefu.business.service;

import java.util.List;
import com.zefu.business.domain.BusMapDefinition;

/**
 * 地图定义Service接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface IBusMapDefinitionService 
{
    /**
     * 查询地图定义
     * 
     * @param id 地图定义ID
     * @return 地图定义
     */
    public BusMapDefinition selectBusMapDefinitionById(Long id);

    /**
     * 查询地图定义列表
     * 
     * @param busMapDefinition 地图定义
     * @return 地图定义集合
     */
    public List<BusMapDefinition> selectBusMapDefinitionList(BusMapDefinition busMapDefinition);

    /**
     * 新增地图定义
     * 
     * @param busMapDefinition 地图定义
     * @return 结果
     */
    public int insertBusMapDefinition(BusMapDefinition busMapDefinition);

    /**
     * 修改地图定义
     * 
     * @param busMapDefinition 地图定义
     * @return 结果
     */
    public int updateBusMapDefinition(BusMapDefinition busMapDefinition);

    /**
     * 批量删除地图定义
     * 
     * @param ids 需要删除的地图定义ID
     * @return 结果
     */
    public int deleteBusMapDefinitionByIds(Long[] ids);

    /**
     * 删除地图定义信息
     * 
     * @param id 地图定义ID
     * @return 结果
     */
    public int deleteBusMapDefinitionById(Long id);
}

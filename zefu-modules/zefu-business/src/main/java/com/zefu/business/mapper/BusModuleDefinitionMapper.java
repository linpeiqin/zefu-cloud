package com.zefu.business.mapper;

import java.util.List;
import com.zefu.business.domain.BusModuleDefinition;

/**
 * 模组定义Mapper接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface BusModuleDefinitionMapper 
{
    /**
     * 查询模组定义
     * 
     * @param id 模组定义ID
     * @return 模组定义
     */
    public BusModuleDefinition selectBusModuleDefinitionById(Long id);

    /**
     * 查询模组定义列表
     * 
     * @param busModuleDefinition 模组定义
     * @return 模组定义集合
     */
    public List<BusModuleDefinition> selectBusModuleDefinitionList(BusModuleDefinition busModuleDefinition);

    /**
     * 新增模组定义
     * 
     * @param busModuleDefinition 模组定义
     * @return 结果
     */
    public int insertBusModuleDefinition(BusModuleDefinition busModuleDefinition);

    /**
     * 修改模组定义
     * 
     * @param busModuleDefinition 模组定义
     * @return 结果
     */
    public int updateBusModuleDefinition(BusModuleDefinition busModuleDefinition);

    /**
     * 删除模组定义
     * 
     * @param id 模组定义ID
     * @return 结果
     */
    public int deleteBusModuleDefinitionById(Long id);

    /**
     * 批量删除模组定义
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusModuleDefinitionByIds(Long[] ids);
}

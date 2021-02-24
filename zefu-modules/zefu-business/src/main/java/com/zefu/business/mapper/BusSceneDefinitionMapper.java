package com.zefu.business.mapper;

import java.util.List;
import com.zefu.business.domain.BusSceneDefinition;

/**
 * 场景定义Mapper接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface BusSceneDefinitionMapper 
{
    /**
     * 查询场景定义
     * 
     * @param id 场景定义ID
     * @return 场景定义
     */
    public BusSceneDefinition selectBusSceneDefinitionById(Long id);

    /**
     * 查询场景定义列表
     * 
     * @param busSceneDefinition 场景定义
     * @return 场景定义集合
     */
    public List<BusSceneDefinition> selectBusSceneDefinitionList(BusSceneDefinition busSceneDefinition);

    /**
     * 新增场景定义
     * 
     * @param busSceneDefinition 场景定义
     * @return 结果
     */
    public int insertBusSceneDefinition(BusSceneDefinition busSceneDefinition);

    /**
     * 修改场景定义
     * 
     * @param busSceneDefinition 场景定义
     * @return 结果
     */
    public int updateBusSceneDefinition(BusSceneDefinition busSceneDefinition);

    /**
     * 删除场景定义
     * 
     * @param id 场景定义ID
     * @return 结果
     */
    public int deleteBusSceneDefinitionById(Long id);

    /**
     * 批量删除场景定义
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusSceneDefinitionByIds(Long[] ids);
}

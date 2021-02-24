package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusSceneDefinitionMapper;
import com.zefu.business.domain.BusSceneDefinition;
import com.zefu.business.service.IBusSceneDefinitionService;

/**
 * 场景定义Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusSceneDefinitionServiceImpl implements IBusSceneDefinitionService 
{
    @Autowired
    private BusSceneDefinitionMapper busSceneDefinitionMapper;

    /**
     * 查询场景定义
     * 
     * @param id 场景定义ID
     * @return 场景定义
     */
    @Override
    public BusSceneDefinition selectBusSceneDefinitionById(Long id)
    {
        return busSceneDefinitionMapper.selectBusSceneDefinitionById(id);
    }

    /**
     * 查询场景定义列表
     * 
     * @param busSceneDefinition 场景定义
     * @return 场景定义
     */
    @Override
    public List<BusSceneDefinition> selectBusSceneDefinitionList(BusSceneDefinition busSceneDefinition)
    {
        return busSceneDefinitionMapper.selectBusSceneDefinitionList(busSceneDefinition);
    }

    /**
     * 新增场景定义
     * 
     * @param busSceneDefinition 场景定义
     * @return 结果
     */
    @Override
    public int insertBusSceneDefinition(BusSceneDefinition busSceneDefinition)
    {
    busSceneDefinition.setCreateBy(SecurityUtils.getUsername());
        busSceneDefinition.setCreateTime(DateUtils.getNowDate());
        return busSceneDefinitionMapper.insertBusSceneDefinition(busSceneDefinition);
    }

    /**
     * 修改场景定义
     * 
     * @param busSceneDefinition 场景定义
     * @return 结果
     */
    @Override
    public int updateBusSceneDefinition(BusSceneDefinition busSceneDefinition)
    {
    busSceneDefinition.setUpdateBy(SecurityUtils.getUsername());
        busSceneDefinition.setUpdateTime(DateUtils.getNowDate());
        return busSceneDefinitionMapper.updateBusSceneDefinition(busSceneDefinition);
    }

    /**
     * 批量删除场景定义
     * 
     * @param ids 需要删除的场景定义ID
     * @return 结果
     */
    @Override
    public int deleteBusSceneDefinitionByIds(Long[] ids)
    {
        return busSceneDefinitionMapper.deleteBusSceneDefinitionByIds(ids);
    }

    /**
     * 删除场景定义信息
     * 
     * @param id 场景定义ID
     * @return 结果
     */
    @Override
    public int deleteBusSceneDefinitionById(Long id)
    {
        return busSceneDefinitionMapper.deleteBusSceneDefinitionById(id);
    }
}

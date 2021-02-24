package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusModuleDefinitionMapper;
import com.zefu.business.domain.BusModuleDefinition;
import com.zefu.business.service.IBusModuleDefinitionService;

/**
 * 模组定义Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusModuleDefinitionServiceImpl implements IBusModuleDefinitionService 
{
    @Autowired
    private BusModuleDefinitionMapper busModuleDefinitionMapper;

    /**
     * 查询模组定义
     * 
     * @param id 模组定义ID
     * @return 模组定义
     */
    @Override
    public BusModuleDefinition selectBusModuleDefinitionById(Long id)
    {
        return busModuleDefinitionMapper.selectBusModuleDefinitionById(id);
    }

    /**
     * 查询模组定义列表
     * 
     * @param busModuleDefinition 模组定义
     * @return 模组定义
     */
    @Override
    public List<BusModuleDefinition> selectBusModuleDefinitionList(BusModuleDefinition busModuleDefinition)
    {
        return busModuleDefinitionMapper.selectBusModuleDefinitionList(busModuleDefinition);
    }

    /**
     * 新增模组定义
     * 
     * @param busModuleDefinition 模组定义
     * @return 结果
     */
    @Override
    public int insertBusModuleDefinition(BusModuleDefinition busModuleDefinition)
    {
    busModuleDefinition.setCreateBy(SecurityUtils.getUsername());
        busModuleDefinition.setCreateTime(DateUtils.getNowDate());
        return busModuleDefinitionMapper.insertBusModuleDefinition(busModuleDefinition);
    }

    /**
     * 修改模组定义
     * 
     * @param busModuleDefinition 模组定义
     * @return 结果
     */
    @Override
    public int updateBusModuleDefinition(BusModuleDefinition busModuleDefinition)
    {
    busModuleDefinition.setUpdateBy(SecurityUtils.getUsername());
        busModuleDefinition.setUpdateTime(DateUtils.getNowDate());
        return busModuleDefinitionMapper.updateBusModuleDefinition(busModuleDefinition);
    }

    /**
     * 批量删除模组定义
     * 
     * @param ids 需要删除的模组定义ID
     * @return 结果
     */
    @Override
    public int deleteBusModuleDefinitionByIds(Long[] ids)
    {
        return busModuleDefinitionMapper.deleteBusModuleDefinitionByIds(ids);
    }

    /**
     * 删除模组定义信息
     * 
     * @param id 模组定义ID
     * @return 结果
     */
    @Override
    public int deleteBusModuleDefinitionById(Long id)
    {
        return busModuleDefinitionMapper.deleteBusModuleDefinitionById(id);
    }
}

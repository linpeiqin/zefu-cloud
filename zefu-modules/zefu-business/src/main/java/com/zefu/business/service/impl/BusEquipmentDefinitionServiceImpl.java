package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusEquipmentDefinitionMapper;
import com.zefu.business.domain.BusEquipmentDefinition;
import com.zefu.business.service.IBusEquipmentDefinitionService;

/**
 * 设备定义Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusEquipmentDefinitionServiceImpl implements IBusEquipmentDefinitionService 
{
    @Autowired
    private BusEquipmentDefinitionMapper busEquipmentDefinitionMapper;

    /**
     * 查询设备定义
     * 
     * @param id 设备定义ID
     * @return 设备定义
     */
    @Override
    public BusEquipmentDefinition selectBusEquipmentDefinitionById(Long id)
    {
        return busEquipmentDefinitionMapper.selectBusEquipmentDefinitionById(id);
    }

    /**
     * 查询设备定义列表
     * 
     * @param busEquipmentDefinition 设备定义
     * @return 设备定义
     */
    @Override
    public List<BusEquipmentDefinition> selectBusEquipmentDefinitionList(BusEquipmentDefinition busEquipmentDefinition)
    {
        return busEquipmentDefinitionMapper.selectBusEquipmentDefinitionList(busEquipmentDefinition);
    }

    /**
     * 新增设备定义
     * 
     * @param busEquipmentDefinition 设备定义
     * @return 结果
     */
    @Override
    public int insertBusEquipmentDefinition(BusEquipmentDefinition busEquipmentDefinition)
    {
    busEquipmentDefinition.setCreateBy(SecurityUtils.getUsername());
        busEquipmentDefinition.setCreateTime(DateUtils.getNowDate());
        return busEquipmentDefinitionMapper.insertBusEquipmentDefinition(busEquipmentDefinition);
    }

    /**
     * 修改设备定义
     * 
     * @param busEquipmentDefinition 设备定义
     * @return 结果
     */
    @Override
    public int updateBusEquipmentDefinition(BusEquipmentDefinition busEquipmentDefinition)
    {
    busEquipmentDefinition.setUpdateBy(SecurityUtils.getUsername());
        busEquipmentDefinition.setUpdateTime(DateUtils.getNowDate());
        return busEquipmentDefinitionMapper.updateBusEquipmentDefinition(busEquipmentDefinition);
    }

    /**
     * 批量删除设备定义
     * 
     * @param ids 需要删除的设备定义ID
     * @return 结果
     */
    @Override
    public int deleteBusEquipmentDefinitionByIds(Long[] ids)
    {
        return busEquipmentDefinitionMapper.deleteBusEquipmentDefinitionByIds(ids);
    }

    /**
     * 删除设备定义信息
     * 
     * @param id 设备定义ID
     * @return 结果
     */
    @Override
    public int deleteBusEquipmentDefinitionById(Long id)
    {
        return busEquipmentDefinitionMapper.deleteBusEquipmentDefinitionById(id);
    }
}

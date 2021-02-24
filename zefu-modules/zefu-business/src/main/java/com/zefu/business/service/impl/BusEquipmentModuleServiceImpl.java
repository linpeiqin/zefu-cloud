package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusEquipmentModuleMapper;
import com.zefu.business.domain.BusEquipmentModule;
import com.zefu.business.service.IBusEquipmentModuleService;

/**
 * 设备模组关联Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusEquipmentModuleServiceImpl implements IBusEquipmentModuleService 
{
    @Autowired
    private BusEquipmentModuleMapper busEquipmentModuleMapper;

    /**
     * 查询设备模组关联
     * 
     * @param id 设备模组关联ID
     * @return 设备模组关联
     */
    @Override
    public BusEquipmentModule selectBusEquipmentModuleById(Long id)
    {
        return busEquipmentModuleMapper.selectBusEquipmentModuleById(id);
    }

    /**
     * 查询设备模组关联列表
     * 
     * @param busEquipmentModule 设备模组关联
     * @return 设备模组关联
     */
    @Override
    public List<BusEquipmentModule> selectBusEquipmentModuleList(BusEquipmentModule busEquipmentModule)
    {
        return busEquipmentModuleMapper.selectBusEquipmentModuleList(busEquipmentModule);
    }

    /**
     * 新增设备模组关联
     * 
     * @param busEquipmentModule 设备模组关联
     * @return 结果
     */
    @Override
    public int insertBusEquipmentModule(BusEquipmentModule busEquipmentModule)
    {
    busEquipmentModule.setCreateBy(SecurityUtils.getUsername());
        busEquipmentModule.setCreateTime(DateUtils.getNowDate());
        return busEquipmentModuleMapper.insertBusEquipmentModule(busEquipmentModule);
    }

    /**
     * 修改设备模组关联
     * 
     * @param busEquipmentModule 设备模组关联
     * @return 结果
     */
    @Override
    public int updateBusEquipmentModule(BusEquipmentModule busEquipmentModule)
    {
    busEquipmentModule.setUpdateBy(SecurityUtils.getUsername());
        busEquipmentModule.setUpdateTime(DateUtils.getNowDate());
        return busEquipmentModuleMapper.updateBusEquipmentModule(busEquipmentModule);
    }

    /**
     * 批量删除设备模组关联
     * 
     * @param ids 需要删除的设备模组关联ID
     * @return 结果
     */
    @Override
    public int deleteBusEquipmentModuleByIds(Long[] ids)
    {
        return busEquipmentModuleMapper.deleteBusEquipmentModuleByIds(ids);
    }

    /**
     * 删除设备模组关联信息
     * 
     * @param id 设备模组关联ID
     * @return 结果
     */
    @Override
    public int deleteBusEquipmentModuleById(Long id)
    {
        return busEquipmentModuleMapper.deleteBusEquipmentModuleById(id);
    }
}

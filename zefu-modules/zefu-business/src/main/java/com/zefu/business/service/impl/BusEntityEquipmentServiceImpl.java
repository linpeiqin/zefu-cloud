package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusEntityEquipmentMapper;
import com.zefu.business.domain.BusEntityEquipment;
import com.zefu.business.service.IBusEntityEquipmentService;

/**
 * 实体设备关联Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusEntityEquipmentServiceImpl implements IBusEntityEquipmentService 
{
    @Autowired
    private BusEntityEquipmentMapper busEntityEquipmentMapper;

    /**
     * 查询实体设备关联
     * 
     * @param id 实体设备关联ID
     * @return 实体设备关联
     */
    @Override
    public BusEntityEquipment selectBusEntityEquipmentById(Long id)
    {
        return busEntityEquipmentMapper.selectBusEntityEquipmentById(id);
    }

    /**
     * 查询实体设备关联列表
     * 
     * @param busEntityEquipment 实体设备关联
     * @return 实体设备关联
     */
    @Override
    public List<BusEntityEquipment> selectBusEntityEquipmentList(BusEntityEquipment busEntityEquipment)
    {
        return busEntityEquipmentMapper.selectBusEntityEquipmentList(busEntityEquipment);
    }

    /**
     * 新增实体设备关联
     * 
     * @param busEntityEquipment 实体设备关联
     * @return 结果
     */
    @Override
    public int insertBusEntityEquipment(BusEntityEquipment busEntityEquipment)
    {
    busEntityEquipment.setCreateBy(SecurityUtils.getUsername());
        busEntityEquipment.setCreateTime(DateUtils.getNowDate());
        return busEntityEquipmentMapper.insertBusEntityEquipment(busEntityEquipment);
    }

    /**
     * 修改实体设备关联
     * 
     * @param busEntityEquipment 实体设备关联
     * @return 结果
     */
    @Override
    public int updateBusEntityEquipment(BusEntityEquipment busEntityEquipment)
    {
    busEntityEquipment.setUpdateBy(SecurityUtils.getUsername());
        busEntityEquipment.setUpdateTime(DateUtils.getNowDate());
        return busEntityEquipmentMapper.updateBusEntityEquipment(busEntityEquipment);
    }

    /**
     * 批量删除实体设备关联
     * 
     * @param ids 需要删除的实体设备关联ID
     * @return 结果
     */
    @Override
    public int deleteBusEntityEquipmentByIds(Long[] ids)
    {
        return busEntityEquipmentMapper.deleteBusEntityEquipmentByIds(ids);
    }

    /**
     * 删除实体设备关联信息
     * 
     * @param id 实体设备关联ID
     * @return 结果
     */
    @Override
    public int deleteBusEntityEquipmentById(Long id)
    {
        return busEntityEquipmentMapper.deleteBusEntityEquipmentById(id);
    }
}

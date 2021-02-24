package com.zefu.business.service;

import java.util.List;
import com.zefu.business.domain.BusEntityEquipment;

/**
 * 实体设备关联Service接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface IBusEntityEquipmentService 
{
    /**
     * 查询实体设备关联
     * 
     * @param id 实体设备关联ID
     * @return 实体设备关联
     */
    public BusEntityEquipment selectBusEntityEquipmentById(Long id);

    /**
     * 查询实体设备关联列表
     * 
     * @param busEntityEquipment 实体设备关联
     * @return 实体设备关联集合
     */
    public List<BusEntityEquipment> selectBusEntityEquipmentList(BusEntityEquipment busEntityEquipment);

    /**
     * 新增实体设备关联
     * 
     * @param busEntityEquipment 实体设备关联
     * @return 结果
     */
    public int insertBusEntityEquipment(BusEntityEquipment busEntityEquipment);

    /**
     * 修改实体设备关联
     * 
     * @param busEntityEquipment 实体设备关联
     * @return 结果
     */
    public int updateBusEntityEquipment(BusEntityEquipment busEntityEquipment);

    /**
     * 批量删除实体设备关联
     * 
     * @param ids 需要删除的实体设备关联ID
     * @return 结果
     */
    public int deleteBusEntityEquipmentByIds(Long[] ids);

    /**
     * 删除实体设备关联信息
     * 
     * @param id 实体设备关联ID
     * @return 结果
     */
    public int deleteBusEntityEquipmentById(Long id);
}

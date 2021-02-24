package com.zefu.business.mapper;

import java.util.List;
import com.zefu.business.domain.BusEquipmentModule;

/**
 * 设备模组关联Mapper接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface BusEquipmentModuleMapper 
{
    /**
     * 查询设备模组关联
     * 
     * @param id 设备模组关联ID
     * @return 设备模组关联
     */
    public BusEquipmentModule selectBusEquipmentModuleById(Long id);

    /**
     * 查询设备模组关联列表
     * 
     * @param busEquipmentModule 设备模组关联
     * @return 设备模组关联集合
     */
    public List<BusEquipmentModule> selectBusEquipmentModuleList(BusEquipmentModule busEquipmentModule);

    /**
     * 新增设备模组关联
     * 
     * @param busEquipmentModule 设备模组关联
     * @return 结果
     */
    public int insertBusEquipmentModule(BusEquipmentModule busEquipmentModule);

    /**
     * 修改设备模组关联
     * 
     * @param busEquipmentModule 设备模组关联
     * @return 结果
     */
    public int updateBusEquipmentModule(BusEquipmentModule busEquipmentModule);

    /**
     * 删除设备模组关联
     * 
     * @param id 设备模组关联ID
     * @return 结果
     */
    public int deleteBusEquipmentModuleById(Long id);

    /**
     * 批量删除设备模组关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusEquipmentModuleByIds(Long[] ids);
}

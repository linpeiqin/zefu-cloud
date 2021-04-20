package com.zefu.business.mapper;

import com.zefu.business.domain.BusDeviceManage;
import com.zefu.common.base.domain.dto.request.DevQueryReqDto;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备管理Mapper接口
 * 
 * @author linking
 * @date 2021-03-19
 */
public interface BusDeviceManageMapper 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理ID
     * @return 设备管理
     */
    public BusDeviceManage selectBusDeviceManageById(Long id);

    /**
     * 查询设备管理列表
     * 
     * @param busDeviceManage 设备管理
     * @return 设备管理集合
     */
    public List<BusDeviceManage> selectBusDeviceManageList(BusDeviceManage busDeviceManage);

    /**
     * 新增设备管理
     * 
     * @param busDeviceManage 设备管理
     * @return 结果
     */
    public int insertBusDeviceManage(BusDeviceManage busDeviceManage);

    /**
     * 修改设备管理
     * 
     * @param busDeviceManage 设备管理
     * @return 结果
     */
    public int updateBusDeviceManage(BusDeviceManage busDeviceManage);

    /**
     * 删除设备管理
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    public int deleteBusDeviceManageById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusDeviceManageByIds(Long[] ids);

    DevicePageResDto selectByCode(String deviceCode);

    List<DevicePageResDto> queryByPage(@Param("item") DevQueryReqDto dto, @Param("startId") Integer startId, @Param("size") Integer pageSize);

    Long countByPage(@Param("item") DevQueryReqDto query);

    void mapGateway(@Param("devices") List<String> devices, @Param("gwDevCode") String gwDeviceCode);

    int batchOfflineByCode(List<String> devices);

    int batchOnlineByCode(List<String> devices);
}

package com.zefu.business.service;

import com.zefu.business.domain.BusDeviceManage;

import com.zefu.common.base.domain.dto.PageReqDto;
import com.zefu.common.base.domain.dto.PageResDto;
import com.zefu.common.base.domain.dto.request.DevQueryReqDto;
import com.zefu.common.base.domain.dto.request.device.DeviceRtItemReqDto;
import com.zefu.common.base.domain.dto.request.device.GatewayMapReqDto;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.dto.response.device.DeviceRtHistoryResDto;
import com.zefu.common.base.domain.dto.response.device.DeviceRtResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.base.enums.DeviceStateEnum;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;

import java.util.List;

/**
 * 设备管理Service接口
 * 
 * @author linking
 * @date 2021-03-19
 */
public interface IBusDeviceManageService 
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
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理ID
     * @return 结果
     */
    public int deleteBusDeviceManageByIds(Long[] ids);

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    public int deleteBusDeviceManageById(Long id);

    DevicePageResDto selectBusDeviceManageByDeviceCode(String deviceCode);

    List<DeviceRtResDto> queryRtByDevCode(String deviceCode, String productCode, ProductFuncTypeEnum productFuncTypeEnum);

    PageResDto<DeviceRtHistoryResDto> searchRtItem(PageReqDto<DeviceRtItemReqDto> searchPage);

    void send(String key,Object bo);

    PageResDto<DeviceRtHistoryResDto> searchSetItem(PageReqDto<DeviceRtItemReqDto> searchPage);

    PageResDto<DevicePageResDto> queryByPage(PageReqDto<DevQueryReqDto> searchPage);
    long countByGwDevice(String deviceCode, DeviceStateEnum total);

    DevicePageResDto queryByDevCode(String gwDeviceCode);

    void mapGateway(GatewayMapReqDto dto);

    void activeDevice(DeviceActiveMqBo bo);

    /**
     * 根据设备编码批量改变设备状态
     * @param devices
     * @param batchOpEnum
     * */
    void batchChangeStatusByCode(List<String> devices, BatchOpEnum batchOpEnum);


    int updateDeviceStatus(BusDeviceManage busDeviceManage);
}

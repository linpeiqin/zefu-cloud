package com.zefu.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zefu.business.cache.IDeviceCache;
import com.zefu.business.cache.IDeviceOfflineCache;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.base.biz.RedisKeyUtil;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.bo.BaseAttrItemBo;
import com.zefu.common.base.domain.bo.PropAttrBo;
import com.zefu.common.base.domain.dto.PageReqDto;
import com.zefu.common.base.domain.dto.PageResDto;
import com.zefu.common.base.domain.dto.request.DevQueryReqDto;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.domain.dto.request.device.DeviceRtItemReqDto;
import com.zefu.common.base.domain.dto.request.device.GatewayMapReqDto;
import com.zefu.common.base.domain.dto.response.device.DeviceRtHistoryResDto;
import com.zefu.common.base.domain.dto.response.device.DeviceRtResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.DeviceStateEnum;
import com.zefu.business.service.IBusProductFuncService;
import com.zefu.common.base.domain.storage.EsMessage;
import com.zefu.common.core.utils.SecurityUtils;
import com.zefu.common.base.metadata.MetaType;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.common.es.config.enums.KeyMatchTypeEnum;
import com.zefu.common.es.domain.EsRange;
import com.zefu.common.es.domain.SearchItem;
import com.zefu.common.storage.service.IDataCenterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusDeviceManageMapper;
import com.zefu.business.domain.BusDeviceManage;
import com.zefu.business.service.IBusDeviceManageService;
import org.springframework.util.CollectionUtils;

/**
 * 设备管理Service业务层处理
 *
 * @author linking
 * @date 2021-03-19
 */
@Service
public class BusDeviceManageServiceImpl implements IBusDeviceManageService {
    @Autowired
    private BusDeviceManageMapper busDeviceManageMapper;
    @Autowired
    IDeviceCache deviceCache;
    @Autowired
    IBusProductFuncService productFuncService;
    @Autowired
    IDataCenterService dataCenterService;
    @Autowired
    IDeviceOfflineCache deviceOfflineCache;

    /**
     * 查询设备管理
     *
     * @param id 设备管理ID
     * @return 设备管理
     */
    @Override
    public BusDeviceManage selectBusDeviceManageById(Long id) {
        return busDeviceManageMapper.selectBusDeviceManageById(id);
    }

    /**
     * 查询设备管理列表
     *
     * @param busDeviceManage 设备管理
     * @return 设备管理
     */
    @Override
    public List<BusDeviceManage> selectBusDeviceManageList(BusDeviceManage busDeviceManage) {
        List<BusDeviceManage> busDeviceManages = busDeviceManageMapper.selectBusDeviceManageList(busDeviceManage);

        return busDeviceManages;
    }

    /**
     * 新增设备管理
     *
     * @param busDeviceManage 设备管理
     * @return 结果
     */
    @Override
    public int insertBusDeviceManage(BusDeviceManage busDeviceManage) {
        busDeviceManage.setCreateBy(SecurityUtils.getUsername());
        busDeviceManage.setCreateTime(DateUtils.getNowDate());
        return busDeviceManageMapper.insertBusDeviceManage(busDeviceManage);
    }

    /**
     * 修改设备管理
     *
     * @param busDeviceManage 设备管理
     * @return 结果
     */
    @Override
    public int updateBusDeviceManage(BusDeviceManage busDeviceManage) {
        busDeviceManage.setUpdateBy(SecurityUtils.getUsername());
        busDeviceManage.setUpdateTime(DateUtils.getNowDate());
        String key = RedisKeyUtil.buildDeviceInfoKey(busDeviceManage.getDeviceCode());
        deviceCache.remove(key);
        return busDeviceManageMapper.updateBusDeviceManage(busDeviceManage);
    }

    /**
     * 批量删除设备管理
     *
     * @param ids 需要删除的设备管理ID
     * @return 结果
     */
    @Override
    public int deleteBusDeviceManageByIds(Long[] ids) {
        return busDeviceManageMapper.deleteBusDeviceManageByIds(ids);
    }

    /**
     * 删除设备管理信息
     *
     * @param id 设备管理ID
     * @return 结果
     */
    @Override
    public int deleteBusDeviceManageById(Long id) {
        return busDeviceManageMapper.deleteBusDeviceManageById(id);
    }

    @Override
    public DevicePageResDto selectBusDeviceManageByDeviceCode(String deviceCode) {
        DevicePageResDto resDto = deviceCache.deviceReader(deviceCode);
        if (null == resDto) {
            resDto = busDeviceManageMapper.selectByCode(deviceCode);
            if (null != resDto) {
                deviceCache.deviceWriter(deviceCode, resDto);
            }
        }
        return resDto;
    }

    /**
     * 如果属性在数据库已经移除，则在缓存中也移除
     */
    private void removeUnuse(String redisKey, List<ProductFuncItemResDto> propList, Map<String, String> cacheMap) {
        if (!CollectionUtils.isEmpty(cacheMap) || !CollectionUtils.isEmpty(propList)) {
            return;
        }
        cacheMap.forEach((key, value) -> {
            Boolean isExists = false;
            for (ProductFuncItemResDto item : propList) {
                if (item.getIdentifier().equals(key)) {
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                deviceCache.removeHashMap(redisKey, key);
            }
        });
    }

    @Override
    public List<DeviceRtResDto> queryRtByDevCode(String deviceCode, String productCode, ProductFuncTypeEnum productFuncTypeEnum) {
        String redisKey = RedisKeyUtil.buildRtCacheKey(deviceCode, productFuncTypeEnum);
        List<DeviceRtResDto> resultFinal = new ArrayList<>();
        Map<String, String> map = deviceCache.getHashMapAll(redisKey);
        List<ProductFuncItemResDto> propList = productFuncService.listFuncByProductCode(productCode, 1, productFuncTypeEnum);
        this.removeUnuse(redisKey, propList, map);
        map = deviceCache.getHashMapAll(redisKey);
        // 只会把identifier存在的数据返回给前端
        for (ProductFuncItemResDto prop : propList) {
            DeviceRtResDto item = new DeviceRtResDto();
            item.setIdentifier(prop.getIdentifier());
            item.setPropName(prop.getPropName());
            String jsonStr = map.get(prop.getIdentifier());
            item.setDataType(prop.getDataType());
            if (StringUtils.isEmpty(jsonStr)) {
                item.setValue("/");
                item.setArrivedTime("/");
            } else {
                EsMessage bo = JSONProvider.parseObject(jsonStr, EsMessage.class);
                item.setValue(bo.getRequest());
                item.setArrivedTime(bo.getTimestamp());
                BaseAttrItemBo attrItemBo = JSONProvider.parseObjectDefValue(prop.getAttr(), BaseAttrItemBo.class);
                if (!StringUtils.isEmpty(attrItemBo.getUnit())) {
                    item.setUnit(attrItemBo.getUnit());
                }
                if (MetaType.BOOLEAN.getType().equals(attrItemBo.getDataType())) {
                    if ((Boolean) bo.getRequest()) {
                        item.setValue(attrItemBo.getBool1());
                    } else {
                        item.setValue(attrItemBo.getBool0());
                    }
                }

            }
            resultFinal.add(item);
        }
        return resultFinal;
    }

    @Override
    public PageResDto<DeviceRtHistoryResDto> searchRtItem(PageReqDto<DeviceRtItemReqDto> searchPage) {
        DeviceRtItemReqDto params = searchPage.getParamData();
        List<EsRange> listRange = new ArrayList<>();
        List<SearchItem> searchItemList = new ArrayList<>();
        this.buildParam(params.getDeviceCode(), listRange, searchItemList, params);
        ProductFuncTypeEnum typeEnum = ProductFuncTypeEnum.explain(params.getFuncType());
        int startId = (searchPage.getPageNo() - 1) * searchPage.getLimit();
        List<DeviceRtHistoryResDto> resultPage = dataCenterService.searchDeviceRuntimeList(params.getProductCode(), params.getDeviceCode(),
                params.getIdentifier(), typeEnum, searchItemList, startId, searchPage.getLimit(), listRange);

        long total = 0;
        if (!CollectionUtils.isEmpty(resultPage)) {
            total = dataCenterService.countDeviceRuntime(params.getProductCode(), params.getDeviceCode(), typeEnum,
                    params.getIdentifier(), searchItemList, listRange);
            this.processEsRtData(resultPage, params);
        }
        PageResDto<DeviceRtHistoryResDto> pageResult = PageResDto.genResult(searchPage.getPageNo(), searchPage.getLimit(), total, resultPage, null);
        return pageResult;
    }
    @Override
    public PageResDto<DeviceRtHistoryResDto> searchSetItem(PageReqDto<DeviceRtItemReqDto> searchPage) {
        DeviceRtItemReqDto params = searchPage.getParamData();
        List<EsRange> listRange = new ArrayList<>();
        List<SearchItem> searchItemList = new ArrayList<>();
        this.buildParam(params.getDeviceCode(), listRange, searchItemList, params);
        ProductFuncTypeEnum typeEnum = ProductFuncTypeEnum.explain(params.getFuncType());
        int startId = (searchPage.getPageNo() - 1) * searchPage.getLimit();
        List<DeviceRtHistoryResDto> resultPage = dataCenterService.searchDeviceSetList(params.getProductCode(), params.getDeviceCode(),
                params.getIdentifier(), typeEnum, searchItemList, startId, searchPage.getLimit(), listRange);

        long total = 0;
        if (!CollectionUtils.isEmpty(resultPage)) {
            total = dataCenterService.countDeviceSet(params.getProductCode(), params.getDeviceCode(), typeEnum,
                    params.getIdentifier(), searchItemList, listRange);
            this.processEsRtData(resultPage, params);
        }
        PageResDto<DeviceRtHistoryResDto> pageResult =
                PageResDto.genResult(searchPage.getPageNo(), searchPage.getLimit(), total, resultPage, null);
        return pageResult;
    }
    /**
     * 下行指令返回值处理
     */
    private void processEsRtData(List<DeviceRtHistoryResDto> resultPage, DeviceRtItemReqDto params) {
        for (DeviceRtHistoryResDto esMessage : resultPage) {
            Object value = esMessage.getRequest();
            if (null != value) {
                if (MetaType.BOOLEAN.getCode().equals(params.getDataType())) {
                    value = this.processBoolean(value, params);
                }
                esMessage.setRequest(value);
            }
        }
    }

    /**
     * 对参数是boolean类型的返回值做处理
     * 因为boolean类型对true和false都有定义
     */
    private Object processBoolean(Object value, DeviceRtItemReqDto params) {
        ProductFuncTypeEnum productFuncTypeEnum = ProductFuncTypeEnum.explain(params.getFuncType());
        ProductFuncItemResDto resDto =
                productFuncService.queryFunc(params.getProductCode(), productFuncTypeEnum, params.getIdentifier());
        PropAttrBo attrBo = JSONProvider.map2Object(resDto.getAttrMap(), PropAttrBo.class);
        if ((Boolean) value) {
            if (!org.springframework.util.StringUtils.isEmpty(attrBo.getBool1())) {
                value = attrBo.getBool1();
            }

        } else {
            if (!org.springframework.util.StringUtils.isEmpty(attrBo.getBool0())) {
                value = attrBo.getBool0();
            }
        }

        return value;
    }

    private void buildParam(String deviceCode, List<EsRange> rangeList, List<SearchItem> searchItemList, DeviceRtItemReqDto params) {
        EsRange range = new EsRange();
        range.setField(Constants.ES.HEADER_TIMESTAMP);
        if (params.getEndDate() instanceof Date || params.getStartDate() instanceof Date) {
            Long max = null == params.getEndDate() ? null : params.getEndDate().getTime();
            range.setMin(max);
            Long min = null == params.getStartDate() ? null : params.getStartDate().getTime();
            range.setMin(min);
        } else {
            range.setMax(params.getEndDate());
            range.setMin(params.getStartDate());
        }
        rangeList.add(range);

        SearchItem searchItem = new SearchItem();
        searchItem.setKey(Constants.ES.HEADER_DEVICE);
        searchItem.setValue(deviceCode);
        searchItem.setMatchType(KeyMatchTypeEnum.EXIST);
        searchItemList.add(searchItem);
    }

    @Override
    public void send(String key, Object bo) {
        deviceCache.publish(key, bo);
    }



    @Override
    public PageResDto<DevicePageResDto> queryByPage(PageReqDto<DevQueryReqDto> pageReqDto) {
        int startId = (pageReqDto.getPageNo() - 1) * pageReqDto.getLimit();
        DevQueryReqDto item = pageReqDto.getParamData();
        String deviceName = StringUtils.isEmpty(item.getDeviceName()) ? null : item.getDeviceName();
        String gwDevCode = item.getGwDevCode();
        if (!item.getSubDevQuery()) {
            /**是查询网关子设备的话，那就传什么是什么*/
            gwDevCode = StringUtils.isEmpty(item.getGwDevCode()) ? null : item.getGwDevCode();
        }

        String deviceCode = StringUtils.isEmpty(item.getDeviceCode()) ? null : item.getDeviceCode();
        String productCode = StringUtils.isEmpty(item.getProductCode()) ? null : item.getProductCode();
        String nodeType = StringUtils.isEmpty(item.getNodeType()) ? null : item.getNodeType();
        DevQueryReqDto query = new DevQueryReqDto();
        query.setDeviceCode(deviceCode);
        query.setDeviceName(deviceName);
        query.setGwDevCode(gwDevCode);
        query.setProductCode(productCode);
        query.setNodeType(nodeType);
        query.setEnableStatus(item.getEnableStatus());
        query.setActiveStatus(item.getActiveStatus());
        List<DevicePageResDto> list = busDeviceManageMapper.queryByPage(query, startId, pageReqDto.getLimit());
        Long total = busDeviceManageMapper.countByPage(query);
        this.processActiveStatus(list);
        PageResDto<DevicePageResDto> resultPage =
                PageResDto.genResult(pageReqDto.getPageNo(), pageReqDto.getLimit(), total, list, null);
        return resultPage;
    }

    @Override
    public long countByGwDevice(String gwDevCode, DeviceStateEnum stateEnum) {
        BusDeviceManage query = new BusDeviceManage();
        query.setGwDevCode(gwDevCode);
        if (DeviceStateEnum.ACTIVE.equals(stateEnum)) {
            query.setActiveStatus(1);
        }
        return busDeviceManageMapper.selectBusDeviceManageList(query).size();
    }

    @Override
    public DevicePageResDto queryByDevCode(String devCode) {
        DevicePageResDto resDto = deviceCache.deviceReader(devCode);
        if (null == resDto) {
            resDto = busDeviceManageMapper.selectByCode(devCode);
            if (null != resDto) {
                deviceCache.deviceWriter(devCode, resDto);
            }
        }
        return resDto;
    }

    @Override
    public void mapGateway(GatewayMapReqDto dto) {
        busDeviceManageMapper.mapGateway(dto.getDevices(), dto.getGwDeviceCode());
        for (String deviceCode : dto.getDevices()) {
            deviceCache.remove(deviceCode);
        }
    }

    @Override
    public void activeDevice(DeviceActiveMqBo bo) {
        deviceOfflineCache.cacheWriter(bo);
        BusDeviceManage devicePo = new BusDeviceManage();
        devicePo.setDeviceCode(bo.getDeviceCode());
        devicePo.setDevHost(bo.getHost());
        devicePo.setDevPort(bo.getPort());
        int activeStatus = bo.getActive() ? 1 : 0;
        devicePo.setActiveStatus(activeStatus);
        devicePo.setLastOnlineTime(new Date());
        busDeviceManageMapper.updateByCode(devicePo);
    }

    @Override
    public void batchChangeStatusByCode(List<String> devices, BatchOpEnum batchOpEnum) {
        if (CollectionUtils.isEmpty(devices)) {
            return;
        }
        if (BatchOpEnum.OFFLINE.equals(batchOpEnum)) {
            busDeviceManageMapper.batchOfflineByCode(devices);
        } else if (BatchOpEnum.ONLINE.equals(batchOpEnum)) {
            busDeviceManageMapper.batchOnlineByCode(devices);
        }
        this.deviceOfflineCache.cacheBatchWriter(devices,batchOpEnum);
        /*for (String deviceCode : devices) {
            deviceCache.remove(deviceCode);
        }*/
    }

    @Override
    public int updateDeviceStatus(BusDeviceManage busDeviceManage) {
        deviceCache.remove(busDeviceManage.getDeviceCode());
        return this.busDeviceManageMapper.updateBusDeviceManage(busDeviceManage);
    }

    /**
     * 分页查询在线状态
     */
    private void processActiveStatus(List<DevicePageResDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<String> listDevices = new ArrayList<>();
        for (DevicePageResDto item : list) {
            listDevices.add(item.getDeviceCode());
        }
        Map<String, DeviceActiveMqBo> map = deviceOfflineCache.cacheMapReader(listDevices);
        for (DevicePageResDto item : list) {
            DeviceActiveMqBo deviceActiveMqBo = map.get(item.getDeviceCode());
            if (null == deviceActiveMqBo) {
                item.setActiveStatus(0);
                continue;
            }
            item.setLastOnlineTime(deviceActiveMqBo.getTimestamp());
            item.setActiveStatus(deviceActiveMqBo.getActive() ? 1 : 0);
        }
    }
}

package com.zefu.business.service.impl;


import com.zefu.business.cache.IPropertyGetCache;
import com.zefu.common.base.constants.Constants;
import com.zefu.business.service.IBusDeviceManageService;
import com.zefu.business.service.IBusProductFuncService;
import com.zefu.business.service.IPropGetService;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.direct.response.PropertyGetResponse;
import com.zefu.common.base.domain.gateway.mq.PropertyReaderMessageBo;
import com.zefu.common.base.domain.message.DeviceDownMessage;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.base.metadata.MetaType;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.IdGenerate;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.common.protocol.service.IProtocolUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PropGetServiceImpl implements IPropGetService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IBusDeviceManageService deviceService;
    @Autowired
    IProtocolUtilService protocolUtilService;
    @Autowired
    IPropertyGetCache propGetReader;
    @Autowired
    IBusProductFuncService productFuncService;

    @Override
    public PropertyGetResponse fetchProperty(String deviceCode, String identifier, Long timeout) {
        DevicePageResDto devicePageResDto = deviceService.queryByDevCode(deviceCode);
        if(null == devicePageResDto){
            logger.warn("读取设备时发现设备不存在,设备编码=>{}", deviceCode);
            throw GException.genException(ErrorEnum.RESOURCE_NOT_EXISTS);
        }
        DeviceDownMessage deviceDownMessage = this.buildDownMessage(devicePageResDto);
        deviceDownMessage.setIdentifier(identifier);
        logger.info("属性读取messageId:{}", deviceDownMessage.getMessageId());
        PropertyReaderMessageBo readerMessageBo = new PropertyReaderMessageBo();
        readerMessageBo.setDeviceDownMessage(deviceDownMessage);
        readerMessageBo.setDevicePageResDto(devicePageResDto);
        deviceService.send(Constants.REDIS_CHANNEL.PROP_READER,readerMessageBo);
        return this.loopProperty(deviceDownMessage, timeout, identifier);
    }

    private PropertyGetResponse loopProperty(DeviceDownMessage deviceDownMessage, Long timeout, String identifier) {
        PropertyGetResponse response = new PropertyGetResponse();
        Map<String, Object> cacheValue = null;
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < timeout * 1000) {
            try{
                cacheValue = propGetReader.propGetReader(deviceDownMessage.getMessageId());
                if (null != cacheValue) {
                    break;
                }
                Thread.sleep(1000);
            }catch (Exception e){
                logger.warn("属性回执读取异常",e);
            }

        }
        response.setDeviceCode(deviceDownMessage.getDeviceCode());
        response.setIdentifier(identifier);
        if(null == cacheValue){
            response.setValue(null);
        }else{
            response.setValue(cacheValue.get(identifier));
        }
        return response;
    }



    private DeviceDownMessage buildDownMessage(DevicePageResDto devicePageResDto) {
        DeviceDownMessage deviceDownMessage = new DeviceDownMessage();
        deviceDownMessage.setProductCode(devicePageResDto.getProductCode());
        deviceDownMessage.setDeviceCode(devicePageResDto.getDeviceCode());
        //deviceDownMessage.setIdentifier(devicePageResDto.get);
        deviceDownMessage.setTimestamp(System.currentTimeMillis());
        deviceDownMessage.setMessageId(IdGenerate.genId());
        return deviceDownMessage;
    }

    /**
     * 把从redis取出来的字符串值根据物模型类型都转化为对应的数据类型
     **/
    private Object parseValueFromString(String value, String productCode, String identifier) {
        Object result = value;
        ProductFuncItemResDto resDto = this.productFuncService.queryFunc(productCode, ProductFuncTypeEnum.PROP, identifier);
        if(null == resDto){
            logger.warn("标识符资源不存在:identifier=>{} productCode=>{}", identifier, productCode);
            throw GException.genException(ErrorEnum.INVALID_PARAM, "标识符资源不存在");
        }
        String dataType = resDto.getDataType();
        MetaType bcMetaType = MetaType.explain(dataType);
        if(null == bcMetaType){
            logger.warn("标识符的数据类型异常:identifier=>{} productCode=>{}", identifier, productCode);
            throw GException.genException(ErrorEnum.INVALID_PARAM, "标识符的数据类型异常");
        }
        switch (bcMetaType){
            case DATE:
            case STRING:
                result = value;
                break;
            case FLOAT:
            case DOUBLE:
                result = Double.valueOf(value);
                break;
            case LONG:
                result = Long.valueOf(value);
                break;
            case INTEGER:
                result = Integer.valueOf(value);
                break;
            case BOOLEAN:
                result = Boolean.valueOf(value);
                break;
            case STRUCT:
                result = JSONProvider.fromString(value);
                break;

        }
        return result;
    }

}

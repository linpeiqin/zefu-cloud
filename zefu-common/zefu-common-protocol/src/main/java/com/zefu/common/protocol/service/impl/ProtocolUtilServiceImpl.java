package com.zefu.common.protocol.service.impl;


import com.zefu.common.base.annotations.ProtocolAnnotation;
import com.zefu.common.base.domain.dto.response.ProtocolItemResDto;
import com.zefu.common.core.utils.bus.SpringContextUtil;
import com.zefu.common.protocol.base.IBaseProtocol;
import com.zefu.common.protocol.service.IProtocolUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class ProtocolUtilServiceImpl implements IProtocolUtilService {
    @Autowired
    SpringContextUtil springContextUtil;
    /**key=>协议编码 */
    Map<String, IBaseProtocol> protocolInstanceMap = new HashMap<>();


    @Override
    public List<ProtocolItemResDto> listProtocol() {
        List<ProtocolItemResDto> result = new ArrayList<>();
        Map<String, IBaseProtocol> maps = SpringContextUtil.getBeanWithAnnotation(
                ProtocolAnnotation.class);
        maps.forEach((key, protocol)->{
            ProtocolAnnotation annotation = protocol.getClass().getAnnotation(ProtocolAnnotation.class);
            ProtocolItemResDto itemResDto = new ProtocolItemResDto();
            itemResDto.setCode(annotation.protocolCode());
            itemResDto.setDesc(annotation.desc());
            itemResDto.setName(annotation.name());
            result.add(itemResDto);
        });
        return result;
    }

    @Override public IBaseProtocol queryProtocolInstanceByCode(String protocolCode) {
        if(!CollectionUtils.isEmpty(this.protocolInstanceMap)){
            return protocolInstanceMap.get(protocolCode);
        }
        Map<String, IBaseProtocol> maps = SpringContextUtil.getBeanWithAnnotation(ProtocolAnnotation.class);
        IBaseProtocol instance = null;
        for(IBaseProtocol item : maps.values()){
            ProtocolAnnotation annotation = item.getClass().getAnnotation(ProtocolAnnotation.class);
            this.protocolInstanceMap.put(annotation.protocolCode(), item);
            if(annotation.protocolCode().equals(protocolCode)){
                instance = item;
            }
        }
        return instance;
    }
}

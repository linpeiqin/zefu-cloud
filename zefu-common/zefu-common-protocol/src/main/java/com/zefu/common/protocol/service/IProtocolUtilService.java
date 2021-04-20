package com.zefu.common.protocol.service;



import com.zefu.common.base.domain.dto.response.ProtocolItemResDto;
import com.zefu.common.protocol.base.IBaseProtocol;

import java.util.List;


public interface IProtocolUtilService {
    /**
     * 列出所有协议
     * @return
     * */
    List<ProtocolItemResDto> listProtocol();
    /**
     * 根据协议编码查询协议实例
     * @param protocolCode
     * @return
     * */
    IBaseProtocol queryProtocolInstanceByCode(String protocolCode);
}

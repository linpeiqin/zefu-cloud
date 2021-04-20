package com.zefu.common.protocol.base;


import com.zefu.common.base.domain.message.DeviceDownMessage;
import com.zefu.common.base.domain.message.DeviceReportMessage;

public interface IBaseProtocol<T > {
    /**
     * 消息解码
     * @param topic
     * @param deviceId 从mqtt broker连接中取的clientId，数据协议解析的时候可以根据这个来对那些给其他设备上报的数据reject掉
     * @param payload 消息体
     * @return
     * */
    DeviceReportMessage decode(String topic, String deviceId, byte[] payload);
    /**
     * 下行数据,将数据结构转化为设备私有的数据
     * @param topic String 下发的topic
     * @param deviceCode String 设备编码
     * @param deviceDownMessage DeviceDownMessage 准备好的下发数据
     * @return 下发到设备的数据
     * */
    byte[]  encode(String topic, String deviceCode, DeviceDownMessage deviceDownMessage);
}

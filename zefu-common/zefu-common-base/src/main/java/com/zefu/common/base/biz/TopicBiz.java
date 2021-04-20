package com.zefu.common.base.biz;


import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;

public class TopicBiz {
    /**从topic中解析物模型类型
     * 系统前缀/deviceCode/suffix
     * */
    public static String parseDeviceCode(String topic) {
        try {
            String[] splits = topic.split("/");
            return splits[2];
        } catch (Exception e) {
            throw GException.genException(ErrorEnum.MQTT_TOPIC_INVALID, topic);
        }
    }
    /**从topic中解析物模型类型
     * 系统前缀/funcType/identifier/deviceCode
     * */
    public static String parseFuncType(String topic) {
        try {
            String[] splits = topic.split("/");
            return splits[3].toUpperCase();
        } catch (Exception e) {
            throw GException.genException(ErrorEnum.MQTT_TOPIC_INVALID, topic);
        }
    }
    /**从topic中解析物模型类型
     * 系统前缀/funcType/identifier/deviceCode
     * */
    public static ProductFuncTypeEnum parseFuncTypeEnum(String topic) {
        try {
            String[] splits = topic.split("/");
            return ProductFuncTypeEnum.explain(splits[3].toUpperCase());
        } catch (Exception e) {
            throw GException.genException(ErrorEnum.MQTT_TOPIC_INVALID, topic);
        }
    }
    /**
     * 根据协议编码组装订阅主题，用于内部MQTT客户端订阅
     * */
    public static String buildTopicByProduct(){
        return Constants.MQTT.GLOBAL_UP_PREFIX  + "/#";
    }

    public static String buildServiceInvoke(String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(Constants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/service/invoke");
        return sb.toString();
    }

    public static String buildPropertySet( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(Constants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/set");
        return sb.toString();
    }

    public static String buildPropertyGet( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(Constants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/get");
        return sb.toString();
    }
}

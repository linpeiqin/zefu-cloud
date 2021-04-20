package com.zefu.common.base.biz;


import com.zefu.common.base.constants.Constants;

public class MqttUtil {
    /**从mqtt connect的userName中获取deviceCode*/
    public static  String fetchDeviceCode(String userName){
        String[] strings =  userName.split("\\" + Constants.AUTH.SIGN_SPLIT);
        return strings[0];
    }
}

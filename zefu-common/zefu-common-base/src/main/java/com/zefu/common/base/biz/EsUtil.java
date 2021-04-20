package com.zefu.common.base.biz;



import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.DateUtil;

import java.util.Date;


public class EsUtil {
    private static String env;
    public static void setEnv(String env){
        EsUtil.env = env;
    }
    /**
     * 记录设备属性/事件上报记录
     * 系统统一前缀 + product + funcType + property + 月份
     * */
    public static  String buildDevIndex(ProductFuncTypeEnum funcTypeEnum, String productCode, String identifier){
        return buildDevIndexByDate(new Date(), funcTypeEnum, productCode, identifier);
    }

    public static  String buildDevIndexByDate(Date date, ProductFuncTypeEnum funcTypeEnum, String productCode, String identifier){
        String month = DateUtil.formatMonth(date);
        String aliases = buildIndexAliases( productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer();
        sb.append(aliases)
                .append(Constants.ES.INDEX_SPLIT )
                .append(month)
                .append(Constants.ES.INDEX_SPLIT)
                .append(EsUtil.env);
        return sb.toString().toLowerCase();
    }

    /**
     * 记录设备属性设置下发指令, 路由是deviceCode
     * 系统统一前缀 + product + funcType + property  + set + 月份(yyyyMM)
     * */
    public static  String buildDevPropertyIndex(ProductFuncTypeEnum funcTypeEnum, String productCode, String identifier){
        return buildDevPropertyIndexByDate(new Date(), funcTypeEnum, productCode, identifier);
    }

    /**
     * 记录设备属性设置下发指令, 路由是deviceCode, 指定日期
     * 系统统一前缀 + product + funcType + property  + set + 月份(yyyyMM)
     * */
    public static  String buildDevPropertyIndexByDate(Date date, ProductFuncTypeEnum funcTypeEnum, String productCode, String identifier){
        String month = DateUtil.formatMonth(date);
        String aliases = buildPropSetIndexAliases( productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer();
        sb.append(aliases)
                .append(Constants.ES.INDEX_SPLIT )
                .append(month)
                .append(Constants.ES.INDEX_SPLIT)
                .append(EsUtil.env);
        return sb.toString().toLowerCase();
    }
    /**
     * 生成设备数据存储相关的模版
     * */
    public static String buildDeviceTemplateName(String productCode, ProductFuncTypeEnum funcTypeEnum, String identifier){
        return Constants.ES.INDEX_TEMPLATE_PREFIX + productCode + Constants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + Constants.ES.INDEX_SPLIT  + identifier;
    }
    public static String buildSetPropertyTemplateName(String productCode, ProductFuncTypeEnum funcTypeEnum, String identifier){
        return Constants.ES.INDEX_TEMPLATE_PREFIX + Constants.ES.PROPERTY_SET_NAME + Constants.ES.INDEX_SPLIT + productCode + Constants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + Constants.ES.INDEX_SPLIT  + identifier;
    }
    /**
     * 生成设备数据存储相关的模版对应的索引前缀
     * */
    public static String buildIndexPatterns(String productCode, ProductFuncTypeEnum funcTypeEnum, String identifier){
        String aliases =  buildIndexAliases(productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer(aliases)
                .append(Constants.ES.INDEX_SPLIT )
                .append("*");
        return sb.toString().toLowerCase();
     }
    /**
     * 业务数据存储索引创建别名
     * */
    public static String buildIndexAliases(String productCode, ProductFuncTypeEnum funcTypeEnum, String identifier){
        StringBuffer sb = new StringBuffer(Constants.ES.INDEX_DEVICE_PREFIX );
        sb.append(productCode)
                .append(Constants.ES.INDEX_SPLIT)
                .append(funcTypeEnum.getType())
                .append(Constants.ES.INDEX_SPLIT)
                .append(identifier);
        return sb.toString().toLowerCase();
    }

    /**
     * 属性下发别名
     * */
    public static String buildPropSetIndexAliases(String productCode, ProductFuncTypeEnum funcTypeEnum, String identifier){
        StringBuffer sb = new StringBuffer(Constants.ES.INDEX_DEVICE_PREFIX );
        sb.append(Constants.ES.PROPERTY_SET_NAME)
                .append(Constants.ES.INDEX_SPLIT)
                .append(productCode)
                .append(Constants.ES.INDEX_SPLIT)
                .append(funcTypeEnum.getType())
                .append(Constants.ES.INDEX_SPLIT)
                .append(identifier);
        return sb.toString().toLowerCase();
    }
    /**
     * 属性下发别名模版索引规则
     * */
    public static String buildPropSetIndexPatterns(String productCode, ProductFuncTypeEnum funcTypeEnum, String identifier){
        String aliases =  buildPropSetIndexAliases(productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer(aliases)
                .append(Constants.ES.INDEX_SPLIT )
                .append("*");
        return sb.toString().toLowerCase();
    }

}

package com.zefu.business.po2dto;


import com.zefu.business.domain.BusProductFunc;
import com.zefu.common.base.domain.bo.PropAttrBo;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.common.core.utils.bus.ObjectCopyUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProductFucPoConvert {
    public static ProductFuncItemResDto item(BusProductFunc po){
        if(null == po){
            return null;
        }
        ProductFuncItemResDto productFuncItemResDto = new ProductFuncItemResDto();
        ObjectCopyUtil.copyProperties(po, productFuncItemResDto);
        productFuncItemResDto.setProductCode(po.getProductCode());
        productFuncItemResDto.setAttr(po.getAttr());
        productFuncItemResDto.setDataType(po.getDataType());
        productFuncItemResDto.setIdentifier(po.getIdentifier());
        productFuncItemResDto.setPropDesc(po.getFuncDesc());
        productFuncItemResDto.setUnit(po.getUnit());
        productFuncItemResDto.setWrType(po.getWrType());
        productFuncItemResDto.setPropName(po.getFuncName());
        productFuncItemResDto.setCreateTime(po.getCreateTime());
        if(StringUtils.isEmpty(po.getAttr())){
            return productFuncItemResDto;
        }
        PropAttrBo propAttrBo = JSONProvider.parseObject(po.getAttr(), PropAttrBo.class);
        productFuncItemResDto.setRangeMax("" + propAttrBo.getMax());
        productFuncItemResDto.setRangeMin("" + propAttrBo.getMin());
        productFuncItemResDto.setBool0(propAttrBo.getBool0());
        productFuncItemResDto.setBool1(propAttrBo.getBool1());
        productFuncItemResDto.setLength("" + propAttrBo.getLength());
        productFuncItemResDto.setEventType(po.getEventType());
        productFuncItemResDto.setUnit(propAttrBo.getUnit());
        Map map = (Map) JSONProvider.parseObject(po.getAttr(), Map.class);
        productFuncItemResDto.setAttrMap(map);

        return productFuncItemResDto;
    }

    public static List<ProductFuncItemResDto> list(List<BusProductFunc> list){
        List<ProductFuncItemResDto> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return result;
        }
        for(BusProductFunc item:list){
            ProductFuncItemResDto itemBo = item(item);
            result.add(itemBo);
        }
        return result;
    }
}

package com.zefu.business.service.impl;

import com.zefu.business.cache.IProductFuncCache;
import com.zefu.business.po2dto.ProductFucPoConvert;
import com.zefu.business.domain.BusProductFunc;
import com.zefu.business.mapper.BusProductFuncMapper;
import com.zefu.common.base.biz.EsUtil;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.bo.PropAttrBo;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.domain.dto.request.prop.TemplateReqDto;
import com.zefu.common.base.domain.dto.response.prop.TemplateResDto;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.base.metadata.EventTypeEnum;
import com.zefu.common.base.metadata.MetaDesc;
import com.zefu.business.service.IBusProductFuncService;
import com.zefu.common.base.domain.storage.EsMessage;
import com.zefu.common.core.utils.SecurityUtils;
import com.zefu.common.base.metadata.MetaType;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.common.es.config.enums.EsMetaType;
import com.zefu.common.es.domain.PropertiesItem;
import com.zefu.common.storage.service.IDataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 产品功能Service业务层处理
 *
 * @author linking
 * @date 2021-03-12
 */
@Service
public class BusProductFuncServiceImpl implements IBusProductFuncService {
    @Autowired
    private BusProductFuncMapper busProductFuncMapper;
    @Autowired
    private IProductFuncCache productFuncCache;
    @Autowired
    IDataCenterService dataCenterService;

    /**
     * 查询产品功能
     *
     * @param id 产品功能ID
     * @return 产品功能
     */
    @Override
    public BusProductFunc selectBusProductFuncById(Long id) {
        BusProductFunc busProductFunc = busProductFuncMapper.selectBusProductFuncById(id);
        if (busProductFunc == null){
            return null;
        }
        if(StringUtils.isEmpty(busProductFunc.getAttr())){
            return busProductFunc;
        }
        PropAttrBo propAttrBo = JSONProvider.parseObject(busProductFunc.getAttr(), PropAttrBo.class);
        busProductFunc.setRangeMax("" + propAttrBo.getMax());
        busProductFunc.setRangeMin("" + propAttrBo.getMin());
        busProductFunc.setBool0(propAttrBo.getBool0());
        busProductFunc.setBool1(propAttrBo.getBool1());
        busProductFunc.setLength("" + propAttrBo.getLength());
        busProductFunc.setUnit(propAttrBo.getUnit());
        busProductFunc.setAttrMap(JSONProvider.parseObject(busProductFunc.getAttr(), Map.class));
        return busProductFunc;
    }

    /**
     * 查询产品功能列表
     *
     * @param busProductFunc 产品功能
     * @return 产品功能
     */
    @Override
    public List<BusProductFunc> selectBusProductFuncList(BusProductFunc busProductFunc) {
        return busProductFuncMapper.selectBusProductFuncList(busProductFunc);
    }

    /**
     * 新增产品功能
     *
     * @param busProductFunc 产品功能
     * @return 结果
     */
    @Override
    public int insertBusProductFunc(BusProductFunc busProductFunc) {

        this.handlePo(busProductFunc);
        productFuncCache.remove(busProductFunc.getProductCode(), busProductFunc.getFuncType());
        return busProductFuncMapper.insertBusProductFunc(busProductFunc);
    }

    private void handlePo(BusProductFunc record) {
        ProductFuncTypeEnum productFuncTypeEnum = ProductFuncTypeEnum.explain(record.getFuncType());
        //如果功能类型为空则设置为属性
        if (null == productFuncTypeEnum) {
            productFuncTypeEnum = productFuncTypeEnum.PROP;
        }
        if (productFuncTypeEnum.equals(productFuncTypeEnum.EVENT)){
            EventTypeEnum eventTypeEnum = EventTypeEnum.explain(record.getEventType());
            //如果事件为空则设置为通知
            if (null == eventTypeEnum) {
                eventTypeEnum = EventTypeEnum.INFO;
            }
            record.setEventType(eventTypeEnum.getCode());
        }
        if (productFuncTypeEnum.equals(ProductFuncTypeEnum.SERVICE)) {
            String input = record.getInputParam();
            if (org.apache.commons.lang3.StringUtils.isEmpty(input)) {
                record.setDataType("");
            } else {
                PropAttrBo propAttrBo = JSONProvider.parseObject(input, PropAttrBo.class);
                record.setDataType(propAttrBo.getDataType());
            }
        }
        if (!org.apache.commons.lang3.StringUtils.isEmpty(record.getAttr())) {
            PropAttrBo attrBo = JSONProvider.parseObject(record.getAttr(), PropAttrBo.class);
            record.setDataType(attrBo.getDataType());
        }
        record.setCreateBy(SecurityUtils.getUsername());
        record.setIdentifier(record.getIdentifier().toLowerCase());
    }

    /**
     * 修改产品功能
     *
     * @param busProductFunc 产品功能
     * @return 结果
     */
    @Override
    public int updateBusProductFunc(BusProductFunc busProductFunc) {
        busProductFunc.setUpdateBy(SecurityUtils.getUsername());
        busProductFunc.setIdentifier(busProductFunc.getIdentifier().toLowerCase());
        productFuncCache.removeKey(busProductFunc.getProductCode(), busProductFunc.getFuncType());
        return busProductFuncMapper.updateBusProductFunc(busProductFunc);
    }

    /**
     * 批量删除产品功能
     *
     * @param ids 需要删除的产品功能ID
     * @return 结果
     */
    @Override
    public int deleteBusProductFuncByIds(Long[] ids) {
        return busProductFuncMapper.deleteBusProductFuncByIds(ids);
    }

    /**
     * 删除产品功能信息
     *
     * @param id 产品功能ID
     * @return 结果
     */
    @Override
    public int deleteBusProductFuncById(Long id) {
        return busProductFuncMapper.deleteBusProductFuncById(id);
    }

    @Override
    public TemplateResDto template(TemplateReqDto reqDto) {
        ProductFuncTypeEnum funcTypeEnum = ProductFuncTypeEnum.explain(reqDto.getFuncType());
        List<ProductFuncItemResDto> list = new ArrayList<>();
        if (!StringUtils.isEmpty(reqDto.getIdentifier())) {
            ProductFuncItemResDto item = this.queryFunc(reqDto.getProductCode(), funcTypeEnum, reqDto.getIdentifier());
            list.add(item);
        } else {
            list = this.listFuncByProductCode(reqDto.getProductCode(), 1, funcTypeEnum);
        }
        return this.processTemplate(list, funcTypeEnum);
    }

    @Override
    public void releaseProp(Long propId) {
        if (null == propId) {
            throw GException.genException(ErrorEnum.INVALID_PARAM);
        }
        BusProductFunc po = busProductFuncMapper.selectBusProductFuncById(propId);
        if (null == po) {
            throw GException.genException(ErrorEnum.INVALID_PARAM);
        }
        ProductFuncTypeEnum funcTypeEnum = ProductFuncTypeEnum.explain(po.getFuncType());
        /** 创建上报模版 */
        this.createIndexTemplate(po.getProductCode(), funcTypeEnum, po);
        /** 创建属性设置模版 */
        /** 更新状态从草稿到已发布 */
        this.updateFuncStatus(po, funcTypeEnum);
    }

    @Override
    public ProductFuncItemResDto listByProdIdType(String productCode, String identifier, String funcType) {
        BusProductFunc query = new BusProductFunc();
        query.setProductCode(productCode);
        query.setIdentifier(identifier);
        query.setFuncType(funcType);
        BusProductFunc record = busProductFuncMapper.selectBusProductFunc(query);
        return ProductFucPoConvert.item(record);
    }

    /**
     * 模版的名字和索引的前缀一致，索引前缀: bcup/k0amzovaultxu2q4/prop
     */
    private void createIndexTemplate(String productCode, ProductFuncTypeEnum funcTypeEnum, BusProductFunc po) {

        Map<String, PropertiesItem> propertiesMap = new HashMap<>();
        /** 属性/事件上报 服务调用 */
        String tempName = EsUtil.buildDeviceTemplateName(productCode, funcTypeEnum, po.getIdentifier());
        String uploadPattern = EsUtil.buildIndexPatterns(productCode, funcTypeEnum, po.getIdentifier());
        List<String> uploadPatterns = new ArrayList<>();
        uploadPatterns.add(uploadPattern);
        String aliases = EsUtil.buildIndexAliases(productCode, funcTypeEnum, po.getIdentifier());
        /** 属性设置 */
        String setTempName = EsUtil.buildSetPropertyTemplateName(productCode, funcTypeEnum, po.getIdentifier());
        String setPattern = EsUtil.buildPropSetIndexPatterns(productCode, funcTypeEnum, po.getIdentifier());
        List<String> setPatterns = new ArrayList<>();
        setPatterns.add(setPattern);
        String setAliases = EsUtil.buildPropSetIndexAliases(productCode, funcTypeEnum, po.getIdentifier());

        MetaType metaType = MetaType.explain(po.getDataType());
        /** dataType是空 只能是服务调用且没有参数的情况，这种情况默认是字符串 */
        EsMetaType esMetaType = null == metaType ? EsMetaType.STRING : metaType.getEsMetaType();
        for (Field field : EsMessage.class.getDeclaredFields()) {
            PropertiesItem item = new PropertiesItem();
            MetaDesc metaDesc = field.getAnnotation(MetaDesc.class);
            if (!metaDesc.type().equals(EsMetaType.UNKNOWN)) {
                /** 是消息头 */
                item.setType(metaDesc.type().getType());
                propertiesMap.put(field.getName(), item);
            } else if ("request".equals(field.getName())) {
                item.setType(esMetaType.getType());
                propertiesMap.put(field.getName(), item);
            } else if ("response".equals(field.getName())) {
                item.setType(EsMetaType.OBJECT.getType());
                propertiesMap.put(field.getName(), item);
            }
        }

        dataCenterService.createIndexTemplate(tempName, aliases, Constants.ES.HEADER_DEVICE, uploadPatterns,
                propertiesMap);
        if (ProductFuncTypeEnum.PROP.equals(funcTypeEnum)) {
            /** 是属性的话，因为属性会面临设置，所以单独再给他设置个模版 */
            dataCenterService.createIndexTemplate(setTempName, setAliases, Constants.ES.HEADER_DEVICE, setPatterns,
                    propertiesMap);
        }

    }

    private void updateFuncStatus(BusProductFunc source, ProductFuncTypeEnum funcTypeEnum) {
        BusProductFunc update = new BusProductFunc();
        update.setFuncStatus(1);
        update.setId(source.getId());
        busProductFuncMapper.updateBusProductFunc(update);
        productFuncCache.remove(source.getProductCode(), source.getFuncType());
    }

    /**
     * 处理单独的物模型
     */
    private TemplateResDto processTemplate(List<ProductFuncItemResDto> items, ProductFuncTypeEnum funcTypeEnum) {
        if (CollectionUtils.isEmpty(items)) {
            return null;
        }
        TemplateResDto resultDto = new TemplateResDto();
        Map<String, Object> demoData = this.calcDemoValue(items, funcTypeEnum);
        Map<String, Object> template = new HashMap<>();

        for (ProductFuncItemResDto item : items) {
            if (ProductFuncTypeEnum.SERVICE.equals(funcTypeEnum)) {
                template.put(item.getIdentifier(), JSONProvider.fromString(item.getInputParam()));
            } else {
                template.put(item.getIdentifier(), JSONProvider.fromString(item.getAttr()));
            }
        }
        resultDto.setDemoData(demoData);
        resultDto.setTemplate(template);
        return resultDto;
    }

    /**
     * 根据数据类型分类计算数据模版默认值
     */
    private final Map<String, Object> calcDemoValue(List<ProductFuncItemResDto> items,
                                                    ProductFuncTypeEnum funcTypeEnum) {
        Map<String, Object> result = new HashMap<>();
        for (ProductFuncItemResDto item : items) {
            MetaType metaType = MetaType.explain(item.getDataType());
            if (null == metaType) {
                /** 说明是服务调用的无参数调用 */
                result.put(item.getIdentifier(), null);
                continue;
            }
            switch (metaType.getType()) {
                case "num":
                    result.put(item.getIdentifier(), 0);
                    break;
                case "text":
                    result.put(item.getIdentifier(), "示例数据");
                    break;
                case "date":
                    result.put(item.getIdentifier(), System.currentTimeMillis());
                    break;
                case "boolean":
                    result.put(item.getIdentifier(), false);
                    break;
                case "struct":
                    if (ProductFuncTypeEnum.SERVICE.equals(funcTypeEnum)) {
                        ProductFuncItemResDto itemResDto =
                                JSONProvider.parseObject(item.getInputParam(), ProductFuncItemResDto.class);
                        itemResDto.setIdentifier(item.getIdentifier());
                        if ("struct".equals(itemResDto.getDataType())) {
                            /** 一旦进入input数据里面，数据结构就和prop一致了 */
                            result.put(item.getIdentifier(),
                                    this.calcDemoValue(itemResDto.getData(), ProductFuncTypeEnum.PROP));
                        } else if ("".equals(itemResDto.getDataType())) {
                            result.put(item.getIdentifier(), null);
                        } else {
                            List<ProductFuncItemResDto> list = new ArrayList<>();
                            list.add(itemResDto);
                            Map<String, Object> objectMapper = this.calcDemoValue(list, ProductFuncTypeEnum.PROP);
                            result.putAll(objectMapper);
                        }

                    } else {
                        ProductFuncItemResDto itemResDto =
                                JSONProvider.parseObject(item.getAttr(), ProductFuncItemResDto.class);
                        result.put(item.getIdentifier(), this.calcDemoValue(itemResDto.getData(), funcTypeEnum));
                    }

                    break;
                default:
                    return result;
            }
        }

        return result;
    }

    @Override
    public List<ProductFuncItemResDto> listFuncByProductCode(String productCode, Integer funcStatus,
                                                             ProductFuncTypeEnum typeEnum) {
        List<ProductFuncItemResDto> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(productCode)) {
            return resultList;
        }
        List<BusProductFunc> cacheList = productFuncCache.getListProperties(productCode, typeEnum.getType(), funcStatus);
        if (!CollectionUtils.isEmpty(cacheList)) {
            return ProductFucPoConvert.list(cacheList);
        }
        /** 重新装载缓存 */
        BusProductFunc queryDo = new BusProductFunc();
        queryDo.setFuncType(typeEnum.getType());
        queryDo.setProductCode(productCode);
        List<BusProductFunc> innerList = busProductFuncMapper.selectBusProductFuncList(queryDo);
        productFuncCache.setProperties(productCode, typeEnum.getType(), innerList);
        queryDo.setFuncStatus(funcStatus);
        List<BusProductFunc> list = busProductFuncMapper.selectBusProductFuncList(queryDo);
        resultList = ProductFucPoConvert.list(list);
        return resultList;
    }

    @Override
    public ProductFuncItemResDto queryFunc(String productCode, ProductFuncTypeEnum typeEnum, String identifier) {
        if (StringUtils.isEmpty(productCode)) {
            return null;
        }
        BusProductFunc cache = productFuncCache.getProperty(productCode, typeEnum.getType(), identifier, 1);
        if (null != cache) {
            return ProductFucPoConvert.item(cache);
        }
        BusProductFunc queryDo = new BusProductFunc();
        queryDo.setProductCode(productCode);
        if (null != typeEnum) {
            queryDo.setFuncType(typeEnum.getType());
        }
        queryDo.setFuncStatus(1);
        queryDo.setIdentifier(identifier);
        List<BusProductFunc> list = busProductFuncMapper.selectBusProductFuncList(queryDo);
        if (!CollectionUtils.isEmpty(list)) {
            productFuncCache.setProperty(productCode, typeEnum.getType(), list.get(0));
        }
        return CollectionUtils.isEmpty(list) ? null : ProductFucPoConvert.item(list.get(0));
    }

}

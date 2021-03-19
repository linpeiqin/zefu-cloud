package com.zefu.business.service.impl;


import com.zefu.business.domain.dto.response.DeviceRtHistoryResDto;
import com.zefu.business.domain.dto.response.PageResDto;
import com.zefu.business.entity.MessageReplyEntity;
import com.zefu.business.enums.DeviceReplyEnum;
import com.zefu.business.enums.MetaType;
import com.zefu.business.enums.ProductFuncTypeEnum;
import com.zefu.business.metadata.EsInsertDataBo;
import com.zefu.business.service.IDataCenterService;
import com.zefu.business.service.IMessageReplyService;
import com.zefu.business.storage.EsMessage;
import com.zefu.business.util.EsUtil;
import com.zefu.common.core.constant.ComConstants;
import com.zefu.common.core.enums.ErrorEnum;
import com.zefu.common.core.exception.business.ProductFuncException;
import com.zefu.common.es.SampleEsClient;
import com.zefu.common.es.config.enums.EsMetaType;
import com.zefu.common.es.domain.EsRange;
import com.zefu.common.es.domain.PropertiesItem;
import com.zefu.common.es.domain.SearchItem;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class DataCenterServiceImpl implements IDataCenterService {
    private static final Logger log = LoggerFactory.getLogger(DataCenterServiceImpl.class);
    @Autowired
    SampleEsClient esClient;
    @Autowired
    IMessageReplyService messageReplyService;

    @Override
    @Deprecated
    public Boolean createIndex(String productCode, ProductFuncTypeEnum funcTypeEnum, String identifier,
                               MetaType metaType) {
        String indexName = EsUtil.buildDevIndex(funcTypeEnum, productCode, identifier);
        Map<String, EsMetaType> sourceMap = new HashMap<>();
        sourceMap.put(identifier, metaType.getEsMetaType());
        sourceMap.put("deviceCode", EsMetaType.KEYWORD);
        sourceMap.put("arrivedTime", EsMetaType.DATE);
        sourceMap.put("devTime", EsMetaType.DATE);
        return esClient.createIndex(indexName, sourceMap);
    }

    @Override
    public Boolean createIndexTemplate(String template, String indexAliases, String router, List<String> indexPatterns,
        Map<String, PropertiesItem> properties) {
        if (StringUtils.isEmpty(template) || CollectionUtils.isEmpty(indexPatterns)) {
            return false;
        }
        return esClient.createIndexTemplate(template, indexAliases, router, indexPatterns, properties);
    }

    @Override
    public Boolean saveData(EsInsertDataBo data) {
        EsMessage esMessage = data.getEsMessage();
        esMessage.setSaveTimestamp(new Date());
        Boolean ret = esClient.addDoc(data.getIndexName(), data.getEsMessage(), ComConstants.ES.HEADER_DEVICE);
        return ret;
    }

    @Override
    public Boolean deleteIndex(String sourceIndex, String targetIndex) {
        return esClient.dropIndex(sourceIndex);
    }

    @Override
    public List<Map> testQueryDev(String index, String devCode) throws Exception {

        List<Map> result = esClient.queryByCondition(index, null, null, Map.class);
        return result;
    }

    @Override
    public long countDeviceRuntime(String productCode, String deviceCode, ProductFuncTypeEnum typeEnum,
                                   String identifier, List<SearchItem> condition, List<EsRange> rangeList) {
        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier);
        return this.countDevice(aliases, condition, rangeList);
    }

    @Override
    public long countUpRange(String productCode, ProductFuncTypeEnum typeEnum, String identifier, Date start,
        Date end) {
        if(null == start || null == end){
            throw new ProductFuncException(ErrorEnum.INVALID_PARAM.getMsg());
        }
        Date indexDate = new Date(start.getTime() + 100);
        String index = EsUtil.buildDevIndexByDate(indexDate, typeEnum, productCode , identifier);
        return this.countRange(index, start, end);
    }

    @Override
    public long countSetRange(String productCode, ProductFuncTypeEnum typeEnum, String identifier, Date start, Date end) {
        if(null == start || null == end){
            throw new ProductFuncException(ErrorEnum.INVALID_PARAM.getMsg());
        }
        Date indexDate = new Date(start.getTime() + 100);
        String index = EsUtil.buildDevPropertyIndexByDate(indexDate, typeEnum, productCode , identifier);
        return this.countRange(index, start, end);
    }

    private long countRange(String index, Date start, Date end) {
        List<EsRange> rangeList = new ArrayList<>();
        EsRange esRange = new EsRange();
        esRange.setMax(end);
        esRange.setField(ComConstants.ES.HEADER_TIMESTAMP);
        esRange.setMin(start);
        rangeList.add(esRange);
        return this.countDevice(index, null, rangeList);
    }

    @Override
    public List<DeviceRtHistoryResDto> searchDeviceRuntimeList(String productCode, String deviceCode, String identifier,
                                                               ProductFuncTypeEnum typeEnum, List<SearchItem> conditionList, int startId, int size, List<EsRange> rangeList) {

        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier);
        Boolean isQueryReply = false;
        if (ProductFuncTypeEnum.SERVICE.equals(typeEnum)) {
            /** 服务调用记录需要查回执 */
            isQueryReply = true;
        }
        List<DeviceRtHistoryResDto> list =
            this.processHistoryItem(aliases, isQueryReply, deviceCode, conditionList, startId, size, rangeList);

        return list;
    }

    @Override
    public List<DeviceRtHistoryResDto> searchDeviceSetList(String productCode, String deviceCode, String identifier,
        ProductFuncTypeEnum typeEnum, List<SearchItem> condition, int startId, int size, List<EsRange> rangeList) {
        String aliases = EsUtil.buildPropSetIndexAliases(productCode, typeEnum, identifier);
        List<DeviceRtHistoryResDto> list =
            this.processHistoryItem(aliases, true, deviceCode, condition, startId, size, rangeList);
        return list;
    }

    /** 将设备下行数据根据messageId组装回列表中 */
    private List<DeviceRtHistoryResDto> processHistoryItem(String aliases, Boolean isQueryReply, String deviceCode,
        List<SearchItem> condition, int startId, int size, List<EsRange> rangeList) {
        List<DeviceRtHistoryResDto> list =
            this.searchDeviceHistory(aliases, condition, startId, size, rangeList, DeviceRtHistoryResDto.class);
        if (!isQueryReply) {
            /** 不需要查回执 */
            return list;
        }
        List<String> ids = new ArrayList<>();
        for (DeviceRtHistoryResDto message : list) {
            ids.add(message.getMessageId());
        }
        List<MessageReplyEntity> replies = messageReplyService.searchHistory(deviceCode, ids);
        Map<String, MessageReplyEntity> map = new HashMap<>();
        for (MessageReplyEntity entity : replies) {
            if (!map.containsKey(entity.getMessageId())) {
                map.put(entity.getMessageId(), entity);
            } else {
                MessageReplyEntity oldEntity = map.get(entity.getMessageId());
                if (oldEntity.getTimestamp().before(entity.getTimestamp())) {
                    // 老的数据时间小于当前数据时间，这个时候当前数据就要覆盖老的数据
                    map.put(entity.getMessageId(), entity);
                }
            }
        }

        for (DeviceRtHistoryResDto resDto : list) {
            MessageReplyEntity rtMessage = map.get(resDto.getMessageId());
            if (null == rtMessage) {
                resDto.setReplyMessage("");
                resDto.setStatus(DeviceReplyEnum.NORELY.getCode());
                resDto.setReplyTime(null);
            } else {
                resDto.setReplyMessage(rtMessage.getBody());
                resDto.setStatus(rtMessage.getStatus());
                resDto.setReplyTime(rtMessage.getTimestamp());
            }
        }
        return list;
    }

    @Override
    public long countDeviceSet(String productCode, String deviceCode, ProductFuncTypeEnum typeEnum, String identifier,
        List<SearchItem> condition, List<EsRange> rangeList) {
        String aliases = EsUtil.buildPropSetIndexAliases(productCode, typeEnum, identifier);
        return this.countDevice(aliases, condition, rangeList);
    }

    @Override
    public <T> PageResDto<T> searchByPage(String productCode, String deviceCode, String identifier,
                                          ProductFuncTypeEnum typeEnum, List<SearchItem> conditionList, Class<T> resClass, int startId, int size,
                                          List<EsRange> rangeList) {
        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier);
        String[] indices = new String[1];
        indices[0] = aliases;
        List<T> list = new ArrayList<>();
        try {
            list = esClient.searchByPage(indices, ComConstants.ES.HEADER_DEVICE, conditionList, resClass, startId, size,
                ComConstants.ES.HEADER_TIMESTAMP, SortOrder.DESC, rangeList);
            long total = esClient.count(indices, ComConstants.ES.HEADER_DEVICE, conditionList, rangeList);
            int pageNo = startId / size;
            return PageResDto.genResult(pageNo, size, total, list, null);

        } catch (ElasticsearchStatusException ese) {
            if (RestStatus.NOT_FOUND.equals(ese.status())) {
                /** 索引不存在 */
                return PageResDto.genResult(1, size, 0, list, null);
            }
            throw new RuntimeException(ese);
        } catch (Exception e) {
            log.warn("", e);
            throw new ProductFuncException(ErrorEnum.INNER_EXCEPTION.getMsg());
        }
    }

    /**
     * 根据索引查询历史数据总数
     *
     */
    private long countDevice(String index, List<SearchItem> condition, List<EsRange> rangeList) {
        String[] indices = new String[1];
        indices[0] = index;
        try {
            long total = esClient.count(indices, null, condition, rangeList);
            return total;
        } catch (ElasticsearchStatusException ese) {
            if (RestStatus.NOT_FOUND.equals(ese.status())) {
                /** 索引不存在 */
                return 0;
            }
            throw new RuntimeException(ese);
        } catch (Exception e) {
            log.warn("", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 根据索引查询历史数据
     *
     */
    private <T> List<T> searchDeviceHistory(String index, List<SearchItem> conditionList, int startId, int size,
        List<EsRange> rangeList, Class<T> tClass) {
        String[] indices = new String[1];
        List<T> list = new ArrayList<>();
        indices[0] = index;
        try {
            list = esClient.searchByPage(indices, ComConstants.ES.HEADER_DEVICE, conditionList, tClass, startId, size,
                ComConstants.ES.HEADER_TIMESTAMP, SortOrder.DESC, rangeList);
            return list;

        } catch (ElasticsearchStatusException ese) {
            if (RestStatus.NOT_FOUND.equals(ese.status())) {
                /** 索引不存在 */
                return list;
            }
            throw new RuntimeException(ese);
        } catch (Exception e) {
            log.warn("", e);
            throw new ProductFuncException(ErrorEnum.INNER_EXCEPTION.getMsg());
        }
    }
}

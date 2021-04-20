package com.zefu.business.service.impl;

import com.zefu.business.domain.BusProductManage;
import com.zefu.business.mapper.BusProductManageMapper;
import com.zefu.business.service.IBusProductManageService;
import com.zefu.common.base.biz.RedisKeyUtil;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.dto.response.ProtocolItemResDto;
import com.zefu.common.base.enums.NetworkEnum;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import com.zefu.common.protocol.service.IProtocolUtilService;
import com.zefu.common.redis.service.RedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 产品管理Service业务层处理
 * 
 * @author linking
 * @date 2021-03-11
 */
@Service
public class BusProductManageServiceImpl implements IBusProductManageService 
{
    @Autowired
    private BusProductManageMapper busProductManageMapper;
    @Autowired
    private RedisService cacheTemplate;
    @Autowired
    IProtocolUtilService protocolUtilService;

    /**
     * 查询产品管理
     * 
     * @param id 产品管理ID
     * @return 产品管理
     */
    @Override
    public BusProductManage selectBusProductManageById(Long id)
    {
        return busProductManageMapper.selectBusProductManageById(id);
    }

    /**
     * 查询产品管理列表
     * 
     * @param busProductManage 产品管理
     * @return 产品管理
     */
    @Override
    public List<BusProductManage> selectBusProductManageList(BusProductManage busProductManage)
    {
        return busProductManageMapper.selectBusProductManageList(busProductManage);
    }

    /**
     * 新增产品管理
     * 
     * @param busProductManage 产品管理
     * @return 结果
     */
    @Override
    public int insertBusProductManage(BusProductManage busProductManage)
    {
        busProductManage.setCreateBy(SecurityUtils.getUsername());
        busProductManage.setCreateTime(DateUtils.getNowDate());
        String productCode = RandomStringUtils.randomAlphanumeric(16).toLowerCase();
        if (busProductManage.getProductCode() == null){
            busProductManage.setProductCode(productCode);
        }
        NetworkEnum.explainMust(busProductManage.getTransportList());
        return busProductManageMapper.insertBusProductManage(busProductManage);
    }

    /**
     * 修改产品管理
     * 
     * @param busProductManage 产品管理
     * @return 结果
     */
    @Override
    public int updateBusProductManage(BusProductManage busProductManage)
    {
    busProductManage.setUpdateBy(SecurityUtils.getUsername());
        busProductManage.setUpdateTime(DateUtils.getNowDate());
        return busProductManageMapper.updateBusProductManage(busProductManage);
    }

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的产品管理ID
     * @return 结果
     */
    @Override
    public int deleteBusProductManageByIds(Long[] ids)
    {
        return busProductManageMapper.deleteBusProductManageByIds(ids);
    }

    /**
     * 删除产品管理信息
     * 
     * @param id 产品管理ID
     * @return 结果
     */
    @Override
    public int deleteBusProductManageById(Long id)
    {
        return busProductManageMapper.deleteBusProductManageById(id);
    }

    @Override
    public int updateProductStatus(BusProductManage busProductManage) {
        return busProductManageMapper.updateBusProductManage(busProductManage);
    }

    @Override
    public BusProductManage queryByCode(String code) {
        String key = RedisKeyUtil.buildProductInfoKey(code);
        BusProductManage record = cacheTemplate.get(key, BusProductManage.class);
        if(null != record){
            return record;
        }
        BusProductManage query = new BusProductManage();
        query.setProductStatus(1);
        query.setDelFlag(0);
        query.setProductCode(code);
        List<BusProductManage> result = busProductManageMapper.selectBusProductManageList(query);

        if(!CollectionUtils.isEmpty(result)){
            cacheTemplate.set(key, result.get(0), Constants.REDIS_DEF.PRODUCT_INFO_EXPIRED);
        }
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public List<ProtocolItemResDto> listProtocol() {
        return protocolUtilService.listProtocol();
    }
}

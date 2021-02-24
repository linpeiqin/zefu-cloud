package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusMapDefinitionMapper;
import com.zefu.business.domain.BusMapDefinition;
import com.zefu.business.service.IBusMapDefinitionService;

/**
 * 地图定义Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusMapDefinitionServiceImpl implements IBusMapDefinitionService 
{
    @Autowired
    private BusMapDefinitionMapper busMapDefinitionMapper;

    /**
     * 查询地图定义
     * 
     * @param id 地图定义ID
     * @return 地图定义
     */
    @Override
    public BusMapDefinition selectBusMapDefinitionById(Long id)
    {
        return busMapDefinitionMapper.selectBusMapDefinitionById(id);
    }

    /**
     * 查询地图定义列表
     * 
     * @param busMapDefinition 地图定义
     * @return 地图定义
     */
    @Override
    public List<BusMapDefinition> selectBusMapDefinitionList(BusMapDefinition busMapDefinition)
    {
        return busMapDefinitionMapper.selectBusMapDefinitionList(busMapDefinition);
    }

    /**
     * 新增地图定义
     * 
     * @param busMapDefinition 地图定义
     * @return 结果
     */
    @Override
    public int insertBusMapDefinition(BusMapDefinition busMapDefinition)
    {
    busMapDefinition.setCreateBy(SecurityUtils.getUsername());
        busMapDefinition.setCreateTime(DateUtils.getNowDate());
        return busMapDefinitionMapper.insertBusMapDefinition(busMapDefinition);
    }

    /**
     * 修改地图定义
     * 
     * @param busMapDefinition 地图定义
     * @return 结果
     */
    @Override
    public int updateBusMapDefinition(BusMapDefinition busMapDefinition)
    {
    busMapDefinition.setUpdateBy(SecurityUtils.getUsername());
        busMapDefinition.setUpdateTime(DateUtils.getNowDate());
        return busMapDefinitionMapper.updateBusMapDefinition(busMapDefinition);
    }

    /**
     * 批量删除地图定义
     * 
     * @param ids 需要删除的地图定义ID
     * @return 结果
     */
    @Override
    public int deleteBusMapDefinitionByIds(Long[] ids)
    {
        return busMapDefinitionMapper.deleteBusMapDefinitionByIds(ids);
    }

    /**
     * 删除地图定义信息
     * 
     * @param id 地图定义ID
     * @return 结果
     */
    @Override
    public int deleteBusMapDefinitionById(Long id)
    {
        return busMapDefinitionMapper.deleteBusMapDefinitionById(id);
    }
}

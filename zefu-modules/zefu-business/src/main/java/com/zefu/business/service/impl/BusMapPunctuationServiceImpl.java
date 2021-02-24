package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusMapPunctuationMapper;
import com.zefu.business.domain.BusMapPunctuation;
import com.zefu.business.service.IBusMapPunctuationService;

/**
 * 地图标点Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusMapPunctuationServiceImpl implements IBusMapPunctuationService 
{
    @Autowired
    private BusMapPunctuationMapper busMapPunctuationMapper;

    /**
     * 查询地图标点
     * 
     * @param id 地图标点ID
     * @return 地图标点
     */
    @Override
    public BusMapPunctuation selectBusMapPunctuationById(Long id)
    {
        return busMapPunctuationMapper.selectBusMapPunctuationById(id);
    }

    /**
     * 查询地图标点列表
     * 
     * @param busMapPunctuation 地图标点
     * @return 地图标点
     */
    @Override
    public List<BusMapPunctuation> selectBusMapPunctuationList(BusMapPunctuation busMapPunctuation)
    {
        return busMapPunctuationMapper.selectBusMapPunctuationList(busMapPunctuation);
    }

    /**
     * 新增地图标点
     * 
     * @param busMapPunctuation 地图标点
     * @return 结果
     */
    @Override
    public int insertBusMapPunctuation(BusMapPunctuation busMapPunctuation)
    {
    busMapPunctuation.setCreateBy(SecurityUtils.getUsername());
        busMapPunctuation.setCreateTime(DateUtils.getNowDate());
        return busMapPunctuationMapper.insertBusMapPunctuation(busMapPunctuation);
    }

    /**
     * 修改地图标点
     * 
     * @param busMapPunctuation 地图标点
     * @return 结果
     */
    @Override
    public int updateBusMapPunctuation(BusMapPunctuation busMapPunctuation)
    {
    busMapPunctuation.setUpdateBy(SecurityUtils.getUsername());
        busMapPunctuation.setUpdateTime(DateUtils.getNowDate());
        return busMapPunctuationMapper.updateBusMapPunctuation(busMapPunctuation);
    }

    /**
     * 批量删除地图标点
     * 
     * @param ids 需要删除的地图标点ID
     * @return 结果
     */
    @Override
    public int deleteBusMapPunctuationByIds(Long[] ids)
    {
        return busMapPunctuationMapper.deleteBusMapPunctuationByIds(ids);
    }

    /**
     * 删除地图标点信息
     * 
     * @param id 地图标点ID
     * @return 结果
     */
    @Override
    public int deleteBusMapPunctuationById(Long id)
    {
        return busMapPunctuationMapper.deleteBusMapPunctuationById(id);
    }
}

package com.zefu.business.mapper;

import java.util.List;
import com.zefu.business.domain.BusMapPunctuation;

/**
 * 地图标点Mapper接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface BusMapPunctuationMapper 
{
    /**
     * 查询地图标点
     * 
     * @param id 地图标点ID
     * @return 地图标点
     */
    public BusMapPunctuation selectBusMapPunctuationById(Long id);

    /**
     * 查询地图标点列表
     * 
     * @param busMapPunctuation 地图标点
     * @return 地图标点集合
     */
    public List<BusMapPunctuation> selectBusMapPunctuationList(BusMapPunctuation busMapPunctuation);

    /**
     * 新增地图标点
     * 
     * @param busMapPunctuation 地图标点
     * @return 结果
     */
    public int insertBusMapPunctuation(BusMapPunctuation busMapPunctuation);

    /**
     * 修改地图标点
     * 
     * @param busMapPunctuation 地图标点
     * @return 结果
     */
    public int updateBusMapPunctuation(BusMapPunctuation busMapPunctuation);

    /**
     * 删除地图标点
     * 
     * @param id 地图标点ID
     * @return 结果
     */
    public int deleteBusMapPunctuationById(Long id);

    /**
     * 批量删除地图标点
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusMapPunctuationByIds(Long[] ids);
}

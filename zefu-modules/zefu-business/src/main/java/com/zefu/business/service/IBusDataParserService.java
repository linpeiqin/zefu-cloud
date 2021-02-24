package com.zefu.business.service;

import java.util.List;
import com.zefu.business.domain.BusDataParser;

/**
 * 数据解析Service接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface IBusDataParserService 
{
    /**
     * 查询数据解析
     * 
     * @param id 数据解析ID
     * @return 数据解析
     */
    public BusDataParser selectBusDataParserById(Long id);

    /**
     * 查询数据解析列表
     * 
     * @param busDataParser 数据解析
     * @return 数据解析集合
     */
    public List<BusDataParser> selectBusDataParserList(BusDataParser busDataParser);

    /**
     * 新增数据解析
     * 
     * @param busDataParser 数据解析
     * @return 结果
     */
    public int insertBusDataParser(BusDataParser busDataParser);

    /**
     * 修改数据解析
     * 
     * @param busDataParser 数据解析
     * @return 结果
     */
    public int updateBusDataParser(BusDataParser busDataParser);

    /**
     * 批量删除数据解析
     * 
     * @param ids 需要删除的数据解析ID
     * @return 结果
     */
    public int deleteBusDataParserByIds(Long[] ids);

    /**
     * 删除数据解析信息
     * 
     * @param id 数据解析ID
     * @return 结果
     */
    public int deleteBusDataParserById(Long id);
}

package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusDataParserMapper;
import com.zefu.business.domain.BusDataParser;
import com.zefu.business.service.IBusDataParserService;

/**
 * 数据解析Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusDataParserServiceImpl implements IBusDataParserService 
{
    @Autowired
    private BusDataParserMapper busDataParserMapper;

    /**
     * 查询数据解析
     * 
     * @param id 数据解析ID
     * @return 数据解析
     */
    @Override
    public BusDataParser selectBusDataParserById(Long id)
    {
        return busDataParserMapper.selectBusDataParserById(id);
    }

    /**
     * 查询数据解析列表
     * 
     * @param busDataParser 数据解析
     * @return 数据解析
     */
    @Override
    public List<BusDataParser> selectBusDataParserList(BusDataParser busDataParser)
    {
        return busDataParserMapper.selectBusDataParserList(busDataParser);
    }

    /**
     * 新增数据解析
     * 
     * @param busDataParser 数据解析
     * @return 结果
     */
    @Override
    public int insertBusDataParser(BusDataParser busDataParser)
    {
    busDataParser.setCreateBy(SecurityUtils.getUsername());
        busDataParser.setCreateTime(DateUtils.getNowDate());
        return busDataParserMapper.insertBusDataParser(busDataParser);
    }

    /**
     * 修改数据解析
     * 
     * @param busDataParser 数据解析
     * @return 结果
     */
    @Override
    public int updateBusDataParser(BusDataParser busDataParser)
    {
    busDataParser.setUpdateBy(SecurityUtils.getUsername());
        busDataParser.setUpdateTime(DateUtils.getNowDate());
        return busDataParserMapper.updateBusDataParser(busDataParser);
    }

    /**
     * 批量删除数据解析
     * 
     * @param ids 需要删除的数据解析ID
     * @return 结果
     */
    @Override
    public int deleteBusDataParserByIds(Long[] ids)
    {
        return busDataParserMapper.deleteBusDataParserByIds(ids);
    }

    /**
     * 删除数据解析信息
     * 
     * @param id 数据解析ID
     * @return 结果
     */
    @Override
    public int deleteBusDataParserById(Long id)
    {
        return busDataParserMapper.deleteBusDataParserById(id);
    }
}

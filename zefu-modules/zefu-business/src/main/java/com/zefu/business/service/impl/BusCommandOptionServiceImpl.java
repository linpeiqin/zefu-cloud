package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusCommandOptionMapper;
import com.zefu.business.domain.BusCommandOption;
import com.zefu.business.service.IBusCommandOptionService;

/**
 * 命令设置Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusCommandOptionServiceImpl implements IBusCommandOptionService 
{
    @Autowired
    private BusCommandOptionMapper busCommandOptionMapper;

    /**
     * 查询命令设置
     * 
     * @param id 命令设置ID
     * @return 命令设置
     */
    @Override
    public BusCommandOption selectBusCommandOptionById(Long id)
    {
        return busCommandOptionMapper.selectBusCommandOptionById(id);
    }

    /**
     * 查询命令设置列表
     * 
     * @param busCommandOption 命令设置
     * @return 命令设置
     */
    @Override
    public List<BusCommandOption> selectBusCommandOptionList(BusCommandOption busCommandOption)
    {
        return busCommandOptionMapper.selectBusCommandOptionList(busCommandOption);
    }

    /**
     * 新增命令设置
     * 
     * @param busCommandOption 命令设置
     * @return 结果
     */
    @Override
    public int insertBusCommandOption(BusCommandOption busCommandOption)
    {
    busCommandOption.setCreateBy(SecurityUtils.getUsername());
        busCommandOption.setCreateTime(DateUtils.getNowDate());
        return busCommandOptionMapper.insertBusCommandOption(busCommandOption);
    }

    /**
     * 修改命令设置
     * 
     * @param busCommandOption 命令设置
     * @return 结果
     */
    @Override
    public int updateBusCommandOption(BusCommandOption busCommandOption)
    {
    busCommandOption.setUpdateBy(SecurityUtils.getUsername());
        busCommandOption.setUpdateTime(DateUtils.getNowDate());
        return busCommandOptionMapper.updateBusCommandOption(busCommandOption);
    }

    /**
     * 批量删除命令设置
     * 
     * @param ids 需要删除的命令设置ID
     * @return 结果
     */
    @Override
    public int deleteBusCommandOptionByIds(Long[] ids)
    {
        return busCommandOptionMapper.deleteBusCommandOptionByIds(ids);
    }

    /**
     * 删除命令设置信息
     * 
     * @param id 命令设置ID
     * @return 结果
     */
    @Override
    public int deleteBusCommandOptionById(Long id)
    {
        return busCommandOptionMapper.deleteBusCommandOptionById(id);
    }
}

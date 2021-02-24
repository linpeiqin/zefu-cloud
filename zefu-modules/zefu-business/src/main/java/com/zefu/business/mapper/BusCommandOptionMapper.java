package com.zefu.business.mapper;

import java.util.List;
import com.zefu.business.domain.BusCommandOption;

/**
 * 命令设置Mapper接口
 * 
 * @author linking
 * @date 2021-02-08
 */
public interface BusCommandOptionMapper 
{
    /**
     * 查询命令设置
     * 
     * @param id 命令设置ID
     * @return 命令设置
     */
    public BusCommandOption selectBusCommandOptionById(Long id);

    /**
     * 查询命令设置列表
     * 
     * @param busCommandOption 命令设置
     * @return 命令设置集合
     */
    public List<BusCommandOption> selectBusCommandOptionList(BusCommandOption busCommandOption);

    /**
     * 新增命令设置
     * 
     * @param busCommandOption 命令设置
     * @return 结果
     */
    public int insertBusCommandOption(BusCommandOption busCommandOption);

    /**
     * 修改命令设置
     * 
     * @param busCommandOption 命令设置
     * @return 结果
     */
    public int updateBusCommandOption(BusCommandOption busCommandOption);

    /**
     * 删除命令设置
     * 
     * @param id 命令设置ID
     * @return 结果
     */
    public int deleteBusCommandOptionById(Long id);

    /**
     * 批量删除命令设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusCommandOptionByIds(Long[] ids);
}

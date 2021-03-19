package com.zefu.business.mapper;

import java.util.List;
import com.zefu.business.domain.BusProductFunc;

/**
 * 产品功能Mapper接口
 * 
 * @author linking
 * @date 2021-03-12
 */
public interface BusProductFuncMapper 
{
    /**
     * 查询产品功能
     * 
     * @param id 产品功能ID
     * @return 产品功能
     */
    public BusProductFunc selectBusProductFuncById(Long id);

    /**
     * 查询产品功能列表
     * 
     * @param busProductFunc 产品功能
     * @return 产品功能集合
     */
    public List<BusProductFunc> selectBusProductFuncList(BusProductFunc busProductFunc);

    /**
     * 新增产品功能
     * 
     * @param busProductFunc 产品功能
     * @return 结果
     */
    public int insertBusProductFunc(BusProductFunc busProductFunc);

    /**
     * 修改产品功能
     * 
     * @param busProductFunc 产品功能
     * @return 结果
     */
    public int updateBusProductFunc(BusProductFunc busProductFunc);

    /**
     * 删除产品功能
     * 
     * @param id 产品功能ID
     * @return 结果
     */
    public int deleteBusProductFuncById(Long id);

    /**
     * 批量删除产品功能
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusProductFuncByIds(Long[] ids);

}

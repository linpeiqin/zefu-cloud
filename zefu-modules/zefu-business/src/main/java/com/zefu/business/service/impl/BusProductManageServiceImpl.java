package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusProductManageMapper;
import com.zefu.business.domain.BusProductManage;
import com.zefu.business.service.IBusProductManageService;

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
}

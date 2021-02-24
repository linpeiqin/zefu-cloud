package com.zefu.business.service.impl;

import java.util.List;
import com.zefu.common.core.utils.DateUtils;
import com.zefu.common.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zefu.business.mapper.BusSceneEntityMapper;
import com.zefu.business.domain.BusSceneEntity;
import com.zefu.business.service.IBusSceneEntityService;

/**
 * 场景实体关联Service业务层处理
 * 
 * @author linking
 * @date 2021-02-08
 */
@Service
public class BusSceneEntityServiceImpl implements IBusSceneEntityService 
{
    @Autowired
    private BusSceneEntityMapper busSceneEntityMapper;

    /**
     * 查询场景实体关联
     * 
     * @param id 场景实体关联ID
     * @return 场景实体关联
     */
    @Override
    public BusSceneEntity selectBusSceneEntityById(Long id)
    {
        return busSceneEntityMapper.selectBusSceneEntityById(id);
    }

    /**
     * 查询场景实体关联列表
     * 
     * @param busSceneEntity 场景实体关联
     * @return 场景实体关联
     */
    @Override
    public List<BusSceneEntity> selectBusSceneEntityList(BusSceneEntity busSceneEntity)
    {
        return busSceneEntityMapper.selectBusSceneEntityList(busSceneEntity);
    }

    /**
     * 新增场景实体关联
     * 
     * @param busSceneEntity 场景实体关联
     * @return 结果
     */
    @Override
    public int insertBusSceneEntity(BusSceneEntity busSceneEntity)
    {
    busSceneEntity.setCreateBy(SecurityUtils.getUsername());
        busSceneEntity.setCreateTime(DateUtils.getNowDate());
        return busSceneEntityMapper.insertBusSceneEntity(busSceneEntity);
    }

    /**
     * 修改场景实体关联
     * 
     * @param busSceneEntity 场景实体关联
     * @return 结果
     */
    @Override
    public int updateBusSceneEntity(BusSceneEntity busSceneEntity)
    {
    busSceneEntity.setUpdateBy(SecurityUtils.getUsername());
        busSceneEntity.setUpdateTime(DateUtils.getNowDate());
        return busSceneEntityMapper.updateBusSceneEntity(busSceneEntity);
    }

    /**
     * 批量删除场景实体关联
     * 
     * @param ids 需要删除的场景实体关联ID
     * @return 结果
     */
    @Override
    public int deleteBusSceneEntityByIds(Long[] ids)
    {
        return busSceneEntityMapper.deleteBusSceneEntityByIds(ids);
    }

    /**
     * 删除场景实体关联信息
     * 
     * @param id 场景实体关联ID
     * @return 结果
     */
    @Override
    public int deleteBusSceneEntityById(Long id)
    {
        return busSceneEntityMapper.deleteBusSceneEntityById(id);
    }
}

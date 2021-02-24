package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 实体设备关联对象 bus_entity_equipment
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusEntityEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String equipmentNo;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /** 设备位置 */
    @Excel(name = "设备位置")
    private String equipmentPosition;

    /** 设备类别 */
    @Excel(name = "设备类别")
    private String equipmentType;

    /** 实体ID */
    @Excel(name = "实体ID")
    private Long entityId;

    /** 场景ID */
    @Excel(name = "场景ID")
    private Long sceneId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }
    public void setEquipmentNo(String equipmentNo) 
    {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentNo() 
    {
        return equipmentNo;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setEquipmentPosition(String equipmentPosition) 
    {
        this.equipmentPosition = equipmentPosition;
    }

    public String getEquipmentPosition() 
    {
        return equipmentPosition;
    }
    public void setEquipmentType(String equipmentType) 
    {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentType() 
    {
        return equipmentType;
    }
    public void setEntityId(Long entityId) 
    {
        this.entityId = entityId;
    }

    public Long getEntityId() 
    {
        return entityId;
    }
    public void setSceneId(Long sceneId) 
    {
        this.sceneId = sceneId;
    }

    public Long getSceneId() 
    {
        return sceneId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipmentName", getEquipmentName())
            .append("equipmentNo", getEquipmentNo())
            .append("equipmentId", getEquipmentId())
            .append("equipmentPosition", getEquipmentPosition())
            .append("equipmentType", getEquipmentType())
            .append("entityId", getEntityId())
            .append("sceneId", getSceneId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

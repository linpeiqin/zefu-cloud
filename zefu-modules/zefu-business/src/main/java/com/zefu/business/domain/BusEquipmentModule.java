package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 设备模组关联对象 bus_equipment_module
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusEquipmentModule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /** 模组ID */
    @Excel(name = "模组ID")
    private Long moduleId;

    /** 模组名称 */
    @Excel(name = "模组名称")
    private String moduleName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 序号 */
    @Excel(name = "序号")
    private String serialNumber;

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
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setModuleId(Long moduleId) 
    {
        this.moduleId = moduleId;
    }

    public Long getModuleId() 
    {
        return moduleId;
    }
    public void setModuleName(String moduleName) 
    {
        this.moduleName = moduleName;
    }

    public String getModuleName() 
    {
        return moduleName;
    }
    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }
    public void setSerialNumber(String serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() 
    {
        return serialNumber;
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
            .append("equipmentId", getEquipmentId())
            .append("moduleId", getModuleId())
            .append("moduleName", getModuleName())
            .append("equipmentName", getEquipmentName())
            .append("serialNumber", getSerialNumber())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

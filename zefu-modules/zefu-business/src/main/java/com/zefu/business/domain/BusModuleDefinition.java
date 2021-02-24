package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 模组定义对象 bus_module_definition
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusModuleDefinition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 读/写类型 */
    @Excel(name = "读/写类型")
    private String readWriteType;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /** 模组名称 */
    @Excel(name = "模组名称")
    private String moduleName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 模组描述 */
    @Excel(name = "模组描述")
    private String moduleRemark;

    /** 是否验证 */
    @Excel(name = "是否验证")
    private String isVerification;

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
    public void setReadWriteType(String readWriteType) 
    {
        this.readWriteType = readWriteType;
    }

    public String getReadWriteType() 
    {
        return readWriteType;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
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
    public void setModuleRemark(String moduleRemark) 
    {
        this.moduleRemark = moduleRemark;
    }

    public String getModuleRemark() 
    {
        return moduleRemark;
    }
    public void setIsVerification(String isVerification) 
    {
        this.isVerification = isVerification;
    }

    public String getIsVerification() 
    {
        return isVerification;
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
            .append("readWriteType", getReadWriteType())
            .append("equipmentId", getEquipmentId())
            .append("moduleName", getModuleName())
            .append("equipmentName", getEquipmentName())
            .append("moduleRemark", getModuleRemark())
            .append("isVerification", getIsVerification())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

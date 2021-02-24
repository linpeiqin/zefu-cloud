package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 数据解析对象 bus_data_parser
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusDataParser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备源ID */
    @Excel(name = "设备源ID")
    private String equipmentSelfId;

    /** 模组ID */
    @Excel(name = "模组ID")
    private Long moduleId;

    /** 公式 */
    @Excel(name = "公式")
    private String formula;

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
    public void setEquipmentSelfId(String equipmentSelfId) 
    {
        this.equipmentSelfId = equipmentSelfId;
    }

    public String getEquipmentSelfId() 
    {
        return equipmentSelfId;
    }
    public void setModuleId(Long moduleId) 
    {
        this.moduleId = moduleId;
    }

    public Long getModuleId() 
    {
        return moduleId;
    }
    public void setFormula(String formula) 
    {
        this.formula = formula;
    }

    public String getFormula() 
    {
        return formula;
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
            .append("equipmentSelfId", getEquipmentSelfId())
            .append("moduleId", getModuleId())
            .append("formula", getFormula())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

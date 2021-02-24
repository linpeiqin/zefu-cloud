package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 设备定义对象 bus_equipment_definition
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusEquipmentDefinition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String type;

    /** 设备序列 */
    @Excel(name = "设备序列")
    private String serial;

    /** 通讯协议ID */
    @Excel(name = "通讯协议ID")
    private String communicationAgreementId;

    /** 设备本身ID */
    @Excel(name = "设备本身ID")
    private String selfId;

    /** 协议名称 */
    @Excel(name = "协议名称")
    private String agreementName;

    /** 生产厂商 */
    @Excel(name = "生产厂商")
    private String manufactor;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setSerial(String serial) 
    {
        this.serial = serial;
    }

    public String getSerial() 
    {
        return serial;
    }
    public void setCommunicationAgreementId(String communicationAgreementId) 
    {
        this.communicationAgreementId = communicationAgreementId;
    }

    public String getCommunicationAgreementId() 
    {
        return communicationAgreementId;
    }
    public void setSelfId(String selfId) 
    {
        this.selfId = selfId;
    }

    public String getSelfId() 
    {
        return selfId;
    }
    public void setAgreementName(String agreementName) 
    {
        this.agreementName = agreementName;
    }

    public String getAgreementName() 
    {
        return agreementName;
    }
    public void setManufactor(String manufactor) 
    {
        this.manufactor = manufactor;
    }

    public String getManufactor() 
    {
        return manufactor;
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
            .append("name", getName())
            .append("type", getType())
            .append("serial", getSerial())
            .append("communicationAgreementId", getCommunicationAgreementId())
            .append("selfId", getSelfId())
            .append("agreementName", getAgreementName())
            .append("manufactor", getManufactor())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

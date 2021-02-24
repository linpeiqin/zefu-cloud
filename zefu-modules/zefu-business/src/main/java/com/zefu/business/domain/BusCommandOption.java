package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 命令设置对象 bus_command_option
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusCommandOption extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备源ID */
    @Excel(name = "设备源ID")
    private String equipmentSelfId;

    /** 命令号 */
    @Excel(name = "命令号")
    private String commandNo;

    /** 起始地址 */
    @Excel(name = "起始地址")
    private String startAddress;

    /** 数据长度 */
    @Excel(name = "数据长度")
    private Integer dataLength;

    /** 校验码 */
    @Excel(name = "校验码")
    private String crc;

    /** 模组ID */
    @Excel(name = "模组ID")
    private Long moduleId;

    /** 发送串 */
    @Excel(name = "发送串")
    private String sendBuff;

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
    public void setCommandNo(String commandNo) 
    {
        this.commandNo = commandNo;
    }

    public String getCommandNo() 
    {
        return commandNo;
    }
    public void setStartAddress(String startAddress) 
    {
        this.startAddress = startAddress;
    }

    public String getStartAddress() 
    {
        return startAddress;
    }
    public void setDataLength(Integer dataLength) 
    {
        this.dataLength = dataLength;
    }

    public Integer getDataLength() 
    {
        return dataLength;
    }
    public void setCrc(String crc) 
    {
        this.crc = crc;
    }

    public String getCrc() 
    {
        return crc;
    }
    public void setModuleId(Long moduleId) 
    {
        this.moduleId = moduleId;
    }

    public Long getModuleId() 
    {
        return moduleId;
    }
    public void setSendBuff(String sendBuff) 
    {
        this.sendBuff = sendBuff;
    }

    public String getSendBuff() 
    {
        return sendBuff;
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
            .append("commandNo", getCommandNo())
            .append("startAddress", getStartAddress())
            .append("dataLength", getDataLength())
            .append("crc", getCrc())
            .append("moduleId", getModuleId())
            .append("sendBuff", getSendBuff())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

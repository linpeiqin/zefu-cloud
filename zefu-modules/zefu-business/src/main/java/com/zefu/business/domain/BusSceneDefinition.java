package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 场景定义对象 bus_scene_definition
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusSceneDefinition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 场景名称 */
    @Excel(name = "场景名称")
    private String name;

    /** 场景类型 */
    @Excel(name = "场景类型")
    private String type;

    /** 场景主机地址 */
    @Excel(name = "场景主机地址")
    private String macCode;

    /** 父场景 */
    @Excel(name = "父场景")
    private Long parentId;

    /** 场景主机IP */
    @Excel(name = "场景主机IP")
    private String masterIp;

    /** 关联场景 */
    @Excel(name = "关联场景")
    private Long relationId;

    /** 场景地址 */
    @Excel(name = "场景地址")
    private String address;

    /** 管理员ID */
    @Excel(name = "管理员ID")
    private Long adminId;

    /** 管理员名称 */
    @Excel(name = "管理员名称")
    private String adminName;

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
    public void setMacCode(String macCode) 
    {
        this.macCode = macCode;
    }

    public String getMacCode() 
    {
        return macCode;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setMasterIp(String masterIp) 
    {
        this.masterIp = masterIp;
    }

    public String getMasterIp() 
    {
        return masterIp;
    }
    public void setRelationId(Long relationId) 
    {
        this.relationId = relationId;
    }

    public Long getRelationId() 
    {
        return relationId;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setAdminId(Long adminId) 
    {
        this.adminId = adminId;
    }

    public Long getAdminId() 
    {
        return adminId;
    }
    public void setAdminName(String adminName) 
    {
        this.adminName = adminName;
    }

    public String getAdminName() 
    {
        return adminName;
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
            .append("macCode", getMacCode())
            .append("parentId", getParentId())
            .append("masterIp", getMasterIp())
            .append("relationId", getRelationId())
            .append("address", getAddress())
            .append("adminId", getAdminId())
            .append("adminName", getAdminName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

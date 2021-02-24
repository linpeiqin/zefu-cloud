package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 场景实体关联对象 bus_scene_entity
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusSceneEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 实体位置 */
    @Excel(name = "实体位置")
    private String entityPosition;

    /** 实体名称 */
    @Excel(name = "实体名称")
    private String entityName;

    /** 父实体 */
    @Excel(name = "父实体")
    private Long parentId;

    /** 场景ID */
    @Excel(name = "场景ID")
    private Long sceneId;

    /** 实体编号 */
    @Excel(name = "实体编号")
    private String entityNo;

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
    public void setEntityPosition(String entityPosition) 
    {
        this.entityPosition = entityPosition;
    }

    public String getEntityPosition() 
    {
        return entityPosition;
    }
    public void setEntityName(String entityName) 
    {
        this.entityName = entityName;
    }

    public String getEntityName() 
    {
        return entityName;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setSceneId(Long sceneId) 
    {
        this.sceneId = sceneId;
    }

    public Long getSceneId() 
    {
        return sceneId;
    }
    public void setEntityNo(String entityNo) 
    {
        this.entityNo = entityNo;
    }

    public String getEntityNo() 
    {
        return entityNo;
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
            .append("entityPosition", getEntityPosition())
            .append("entityName", getEntityName())
            .append("parentId", getParentId())
            .append("sceneId", getSceneId())
            .append("entityNo", getEntityNo())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

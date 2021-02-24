package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 地图定义对象 bus_map_definition
 * 
 * @author linking
 * @date 2021-02-08
 */
public class BusMapDefinition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 样式 */
    @Excel(name = "样式")
    private String pattern;

    /** 风格 */
    @Excel(name = "风格")
    private String style;

    /** 级别 */
    @Excel(name = "级别")
    private Integer level;

    /** 控制器 */
    @Excel(name = "控制器")
    private String controller;

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
    public void setPattern(String pattern) 
    {
        this.pattern = pattern;
    }

    public String getPattern() 
    {
        return pattern;
    }
    public void setStyle(String style) 
    {
        this.style = style;
    }

    public String getStyle() 
    {
        return style;
    }
    public void setLevel(Integer level) 
    {
        this.level = level;
    }

    public Integer getLevel() 
    {
        return level;
    }
    public void setController(String controller) 
    {
        this.controller = controller;
    }

    public String getController() 
    {
        return controller;
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
            .append("pattern", getPattern())
            .append("style", getStyle())
            .append("level", getLevel())
            .append("controller", getController())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

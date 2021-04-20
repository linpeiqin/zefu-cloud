package com.zefu.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import lombok.Data;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 产品管理对象 bus_product_manage
 *
 * @author linking
 * @date 2021-03-15
 */
@Data
public class BusProductManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 传输协议 */
    @Excel(name = "传输协议")
    private String transportList;

    /** 消息协议 */
    @Excel(name = "消息协议")
    private String protocolCode;

    /** 节点类型 */
    @Excel(name = "节点类型")
    private String nodeType;

    /** 描述 */
    @Excel(name = "描述")
    private String productDesc;

    /** 图标 */
    @Excel(name = "图标")
    private String logUrl;

    /** 0:草稿 1:发布 2:暂停 */
    private Integer productStatus;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标识 */
    private Integer delFlag;
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("transportList", getTransportList())
                .append("protocolCode", getProtocolCode())
                .append("nodeType", getNodeType())
                .append("productDesc", getProductDesc())
                .append("logUrl", getLogUrl())
                .append("productStatus", getProductStatus())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("delFlag",getDelFlag())
                .toString();
    }
}
package com.zefu.business.domain;

import com.zefu.common.core.annotation.Excel;
import com.zefu.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * 产品功能对象 bus_product_func
 *
 * @author linking
 * @date 2021-03-15
 */
@Data
public class BusProductFunc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 关联的产品编码 */
    @Excel(name = "关联的产品编码")
    private String productCode;

    /** 功能名称 */
    @Excel(name = "功能名称")
    private String funcName;

    /** 标识符 */
    @Excel(name = "标识符")
    private String identifier;

    /** 功能类型 服务 属性 事件 */
    @Excel(name = "功能类型 服务 属性 事件")
    private String funcType;

    /** 数据类型 */
    @Excel(name = "数据类型")
    private String dataType;

    /** 数据定义，JSON格式，数字显示取值范围，文本显示长度 */
    @Excel(name = "数据定义，JSON格式，数字显示取值范围，文本显示长度")
    private String dataDefine;

    /** 0:只读 1:读写 */
    @Excel(name = "0:只读 1:读写")
    private Long wrType;

    /** 0:草稿 1:启用 */
    @Excel(name = "0:草稿 1:启用")
    private Long funcStatus;

    /** 描述 */
    @Excel(name = "描述")
    private String funcDesc;

    /** 计量单位(CODE) */
    @Excel(name = "计量单位(CODE)")
    private String unit;

    /** 计量单位名称 */
    @Excel(name = "计量单位名称")
    private String unitName;

    /** 属性 */
    @Excel(name = "属性")
    private String attr;

    /** 事件类型 */
    @Excel(name = "事件类型")
    private String eventType;

    /** 默认异步(服务调用通知结果方式) */
    @Excel(name = "默认异步(服务调用通知结果方式)")
    private Long async;

    /** 服务输入参数 */
    @Excel(name = "服务输入参数")
    private String inputParam;

    /** 服务输出参数 */
    @Excel(name = "服务输出参数")
    private String outputParam;

    /** 状态 */
    @Excel(name = "状态")
    private String status;
    /** 返回的属性格式 */
    Map<String, Object> attrMap;

    private String length;
    private String bool0;
    private String bool1;
    private String rangeMax;
    private String rangeMin;
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("productId", getProductId())
                .append("productCode", getProductCode())
                .append("funcName", getFuncName())
                .append("identifier", getIdentifier())
                .append("funcType", getFuncType())
                .append("dataType", getDataType())
                .append("dataDefine", getDataDefine())
                .append("wrType", getWrType())
                .append("funcStatus", getFuncStatus())
                .append("funcDesc", getFuncDesc())
                .append("unit", getUnit())
                .append("unitName", getUnitName())
                .append("attr", getAttr())
                .append("eventType", getEventType())
                .append("async", getAsync())
                .append("inputParam", getInputParam())
                .append("outputParam", getOutputParam())
                .append("status", getStatus())
                .append("updateBy", getUpdateBy())
                .append("remark", getRemark())
                .toString();
    }
}
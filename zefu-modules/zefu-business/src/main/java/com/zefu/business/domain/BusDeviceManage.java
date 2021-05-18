package com.zefu.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zefu.common.core.annotation.Excel;
import lombok.Data;
import com.zefu.common.core.web.domain.BaseEntity;

/**
 * 设备管理对象 bus_device_manage
 * 
 * @author linking
 * @date 2021-03-19
 */
@Data
public class BusDeviceManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String deviceCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 如果是子设备，关联的网关设备编码 */
    @Excel(name = "如果是子设备，关联的网关设备编码")
    private String gwDevCode;

    /** 所属的产品编码 */
    @Excel(name = "所属的产品编码")
    private String productCode;

    /** 删除标识 */
    private Integer delFlag;

    /** 0:离线 1:在线 */
    @Excel(name = "0:离线 1:在线")
    private Integer activeStatus;

    /** 最近一次上线时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最近一次上线时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastOnlineTime;

    /** 设备密钥 */
    @Excel(name = "设备密钥")
    private String deviceSecret;

    /** 固件版本 */
    @Excel(name = "固件版本")
    private String firmwareVersion;

    /** 最近一次上线主机地址 */
    @Excel(name = "最近一次上线主机地址")
    private String devHost;

    /** 最近一次上线端口 */
    @Excel(name = "最近一次上线端口")
    private Integer devPort;

    /** 状态 */
    @Excel(name = "状态")
    private String enableStatus;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceCode", getDeviceCode())
            .append("deviceName", getDeviceName())
            .append("gwDevCode", getGwDevCode())
            .append("productCode", getProductCode())
            .append("delFlag", getDelFlag())
            .append("activeStatus", getActiveStatus())
            .append("lastOnlineTime", getLastOnlineTime())
            .append("deviceSecret", getDeviceSecret())
            .append("firmwareVersion", getFirmwareVersion())
            .append("devHost", getDevHost())
            .append("devPort", getDevPort())
            .append("enableStatus", getEnableStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

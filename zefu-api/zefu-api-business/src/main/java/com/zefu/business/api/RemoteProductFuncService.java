package com.zefu.business.api;

import com.zefu.business.api.factory.RemoteDeviceFallbackFactory;
import com.zefu.business.api.factory.RemoteProductFuncFallbackFactory;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.constant.ServiceNameConstants;
import com.zefu.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 功能服务
 * 
 * @author ruoyi|linking
 */
@FeignClient(contextId = "remoteProductFuncService", value = ServiceNameConstants.BUSINESS_SERVICE, fallbackFactory = RemoteProductFuncFallbackFactory.class)
public interface RemoteProductFuncService
{

    @PostMapping(value = "/func/listFuncByProductCode")
    public R<List<ProductFuncItemResDto>> listFuncByProductCode(@RequestParam("productCode") String productCode, @RequestParam("funcStatus") Integer funcStatus,
                                                                @RequestParam("typeEnum") ProductFuncTypeEnum typeEnum);

    @GetMapping(value = "/func/queryFunc")
    public R<ProductFuncItemResDto> queryFunc(@RequestParam("productCode") String productCode, @RequestParam("funcType") ProductFuncTypeEnum funcType, @RequestParam("identifier") String identifier);
}

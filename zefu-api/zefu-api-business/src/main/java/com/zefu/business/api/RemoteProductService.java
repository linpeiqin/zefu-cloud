package com.zefu.business.api;

import com.zefu.business.api.factory.RemoteProductFallbackFactory;
import com.zefu.business.api.factory.RemoteProductFuncFallbackFactory;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
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
@FeignClient(contextId = "remoteProductService", value = ServiceNameConstants.BUSINESS_SERVICE, fallbackFactory = RemoteProductFallbackFactory.class)
public interface RemoteProductService
{
    @GetMapping(value = "/product/queryProtocolCodeByCode")
    public R<String> queryProtocolCodeByCode(@RequestParam("code") String code);


}

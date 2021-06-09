package com.zefu.business.api.factory;

import com.zefu.business.api.RemoteProductFuncService;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能服务降级处理
 * 
 * @author ruoyi|linking
 */
@Component
public class RemoteProductFuncFallbackFactory implements FallbackFactory<RemoteProductFuncService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteProductFuncFallbackFactory.class);

    @Override
    public RemoteProductFuncService create(Throwable throwable)
    {
        log.error("功能服务调用失败:{}", throwable.getMessage());
        return new RemoteProductFuncService()
        {
            @Override
            public R<List<ProductFuncItemResDto>> listFuncByProductCode(String productCode, Integer funcStatus, ProductFuncTypeEnum typeEnum) {
                return R.fail();
            }

            @Override
            public R<ProductFuncItemResDto> queryFunc(String productCode, ProductFuncTypeEnum funcType, String identifier) {
                return null;
            }
        };
    }
}

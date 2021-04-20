package com.zefu.business.api.factory;

import com.zefu.business.api.RemoteProductFuncService;
import com.zefu.business.api.RemoteProductService;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.domain.R;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 产品服务降级处理
 * 
 * @author ruoyi|linking
 */
@Component
public class RemoteProductFallbackFactory implements FallbackFactory<RemoteProductService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteProductFallbackFactory.class);

    @Override
    public RemoteProductService create(Throwable throwable)
    {
        log.error("产品服务调用失败:{}", throwable.getMessage());
        return new RemoteProductService()
        {
            @Override
            public R<String> queryProtocolCodeByCode(String code) {
                return null;
            }
        };
    }
}

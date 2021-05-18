package com.zefu.business.api.factory;

import com.zefu.business.api.RemoteBusComService;
import com.zefu.business.api.RemoteProductService;
import com.zefu.common.core.domain.R;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 业务公共服务降级处理
 * 
 * @author ruoyi|linking
 */
@Component
public class RemoteBusComFallbackFactory implements FallbackFactory<RemoteBusComService>
{

    private static final Logger log = LoggerFactory.getLogger(RemoteBusComFallbackFactory.class);

    @Override
    public RemoteBusComService create(Throwable throwable)
    {
        log.error("产品服务调用失败:{}", throwable.getMessage());
        return new RemoteBusComService()
        {
            @Override
            public void propGetValueWrite(String messageId, String value) {

            }

            @Override
            public void todayTotalIncr() {

            }

            @Override
            public R<Boolean> lockProp(String messageId) {
                return null;
            }

            @Override
            public R<Boolean> lockService(String messageId) {
                return null;
            }
        };
    }
}

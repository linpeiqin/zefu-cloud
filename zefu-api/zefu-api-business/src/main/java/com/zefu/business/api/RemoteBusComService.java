package com.zefu.business.api;

import com.zefu.business.api.factory.RemoteBusComFallbackFactory;
import com.zefu.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import com.zefu.common.core.domain.R;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 业务公共服务
 * 
 * @author ruoyi|linking
 */
@FeignClient(contextId = "remoteBusComService", value = ServiceNameConstants.BUSINESS_SERVICE, fallbackFactory = RemoteBusComFallbackFactory.class)
public interface RemoteBusComService
{
    @PostMapping(value = "/com/propertyGetCache/propGetValueWrite")
    public void propGetValueWrite(@RequestParam("messageId") String messageId, @RequestParam("value") Object value);
    @PostMapping(value = "/com/messageCountCache/todayTotalIncr")
    public void todayTotalIncr();
    @PostMapping(value = "/com/propSetLockCache/lock")
    public R<Boolean> lockProp(@RequestParam("messageId") String messageId);

    @PostMapping(value = "/com/serviceInvokeLockCache/lock")
    public R<Boolean> lockService(@RequestParam("messageId") String messageId);
}

package com.zefu.business.controller;

import com.zefu.business.cache.IMessageCountCache;
import com.zefu.business.cache.IPropSetLockCache;
import com.zefu.business.cache.IPropertyGetCache;
import com.zefu.business.cache.IServiceInvokeLockCache;
import com.zefu.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共Controller
 *
 * @author linking
 * @date 2021-03-19
 */
@RestController
@RequestMapping("/com")
public class BusComController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IPropertyGetCache propertyGetCache;
    @Autowired
    IMessageCountCache messageCountCache;
    @Autowired
    IPropSetLockCache propSetLockCache;
    @Autowired
    IServiceInvokeLockCache serviceInvokeLockCache;

    @PostMapping(value = "/propertyGetCache/propGetValueWrite")
    public void propGetValueWrite(String messageId, Object value) {
        this.propertyGetCache.propGetValueWrite(messageId, value);
    }

    @PostMapping(value = "/messageCountCache/todayTotalIncr")
    public void todayTotalIncr() {
        this.messageCountCache.todayTotalIncr();
    }

    @PostMapping(value = "/propSetLockCache/lock")
    public R<Boolean> lockProp(String messageId) {
        Boolean flag = this.propSetLockCache.lock(messageId);
        if (!flag) return R.fail(flag);
        return R.ok(flag);
    }
    @PostMapping(value = "/serviceInvokeLockCache/lock")
    public R<Boolean> lockService(String messageId) {
        Boolean flag = this.serviceInvokeLockCache.lock(messageId);
        if (!flag) return R.fail(flag);
        return R.ok(flag);
    }
}

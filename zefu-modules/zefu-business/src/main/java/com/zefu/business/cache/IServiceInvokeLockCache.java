package com.zefu.business.cache;


public interface IServiceInvokeLockCache {
    Boolean lock(String messageId);
}

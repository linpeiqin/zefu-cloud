package com.zefu.business.cache;


public interface IPropSetLockCache {
    Boolean lock(String messageId);
}

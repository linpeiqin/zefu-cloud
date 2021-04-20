package com.zefu.business.cache;

import java.util.Date;


public interface IJobLockCache {
    Boolean lock(String jobName, Integer seconds);
    void unlock(String jobName);
    Date getLock(String jobName);
}

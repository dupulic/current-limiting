package com.du.service;

import com.du.entity.RdRequestCount;

public interface RdRequestCountService {
    boolean initializeIp(String ip);
    boolean increaseCount(RdRequestCount count);
    RdRequestCount selectByIp(String ip);

}

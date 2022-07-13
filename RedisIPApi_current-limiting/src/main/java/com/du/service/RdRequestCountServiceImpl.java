package com.du.service;

import com.du.dao.RdRequestCountMapper;
import com.du.entity.RdRequestCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RdRequestCountServiceImpl implements RdRequestCountService{
    @Autowired
    RdRequestCountMapper rdRequestCountMapper;

    @Override
    public boolean initializeIp(String ip) {
        System.out.println(ip);
        return rdRequestCountMapper.initializeIp(ip) > 0;
    }

    @Override
    public boolean increaseCount(RdRequestCount count) {
        return rdRequestCountMapper.increaseCount(count) > 0;
    }

    @Override
    public RdRequestCount selectByIp(String ip) {
       return rdRequestCountMapper.selectByIp(ip);
    }

}

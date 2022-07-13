package com.du.dao;

import com.du.entity.RdRequestCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RdRequestCountMapper {
    Integer initializeIp(@Param("ip") String ip);
    Integer increaseCount(@Param("rdRequestCount") RdRequestCount rdRequestCount);
    RdRequestCount selectByIp(String ip);
}

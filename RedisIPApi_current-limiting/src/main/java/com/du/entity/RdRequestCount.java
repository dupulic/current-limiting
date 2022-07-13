package com.du.entity;


public class RdRequestCount {

  private String ip;
  private long success_count;
  private long failure_count;
  private String is_close = "0";

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public long getSuccess_count() {
    return success_count;
  }

  public void setSuccess_count(long success_count) {
    this.success_count = success_count;
  }

  public long getFailure_count() {
    return failure_count;
  }

  public void setFailure_count(long failure_count) {
    this.failure_count = failure_count;
  }

  public String getIs_close() {
    return is_close;
  }

  public void setIs_close(String is_close) {
    this.is_close = is_close;
  }
}

package com.contract.common.enums;

/**
 * Format error code #ModuleAction #Module: 100 #Action 001
 */
public enum ErrorCodeEnum {
  // Hop dong
  HOP_DONG_EXIST(100001), HOP_DONG_MISS_TRAM(100002), HOP_DONG_DA_GIAO_DAM_PHAN(100003),
  // Tram
  TRAM_EXIST(101001);

  private final int code;

  private ErrorCodeEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}

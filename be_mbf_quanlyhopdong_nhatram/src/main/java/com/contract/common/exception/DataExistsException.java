package com.contract.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.contract.common.enums.ErrorCodeEnum;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataExistsException extends RuntimeException {
  private ErrorCodeEnum errorCode;

  public DataExistsException(ErrorCodeEnum errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public DataExistsException() {
  }

  public DataExistsException(String message) {
    super(message);
  }

  public ErrorCodeEnum getErrorCode() {
    return errorCode;
  }
}

package com.contract.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.contract.common.enums.ErrorCodeEnum;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {

  private ErrorCodeEnum errorCode;

  public InvalidDataException(ErrorCodeEnum errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public InvalidDataException() {
  }

  public InvalidDataException(String message) {
    super(message);
  }

  public ErrorCodeEnum getErrorCode() {
    return errorCode;
  }
}

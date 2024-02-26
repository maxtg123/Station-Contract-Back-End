package com.contract.common.util;

public class ArrayUtil {

  public static boolean checkIfSomeExist(Long[] array1, Long[] array2) {
    for (Long num1 : array1) {
      for (Long num2 : array2) {
        if (num1 == num2) {
          return true;
        }
      }
    }
    return false;
  }
}

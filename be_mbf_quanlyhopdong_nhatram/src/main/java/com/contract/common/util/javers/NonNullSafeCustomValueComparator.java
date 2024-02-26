package com.contract.common.util.javers;

import org.javers.core.diff.custom.CustomValueComparator;

public interface NonNullSafeCustomValueComparator<T> extends CustomValueComparator<T> {
  @Override
  default boolean handlesNulls() {
    return true;
  }
}

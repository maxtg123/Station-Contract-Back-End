package com.contract.common.util.javers;

import org.javers.core.diff.custom.CustomValueComparator;

public class StringComparator implements CustomValueComparator<String> {
  // @Override
  // public Optional<SetChange> compare(String s, String t1,
  // PropertyChangeMetadata propertyChangeMetadata,
  // Property property) {
  // // compare
  // return (s != null && s.equals("") && t1 == null) || (s == null && t1 != null
  // && t1.equals(""));
  // }

  public boolean equals(String s, String t1) {
    return (s != null && s.equals("") && t1 == null) || (s == null && t1 != null && t1.equals(""));
  }

  public String toString(String s) {
    // toString like above
    return s;
  }
}

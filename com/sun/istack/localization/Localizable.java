package com.sun.istack.localization;

public interface Localizable {
  public static final String NOT_LOCALIZABLE = "\000";
  
  String getKey();
  
  Object[] getArguments();
  
  String getResourceBundleName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\localization\Localizable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
package com.sun.xml.rpc.tools.wscompile;

import com.sun.xml.rpc.util.localization.Localizable;

public interface UsageIf {
  Localizable getOptionsUsage();
  
  Localizable getFeaturesUsage();
  
  Localizable getInternalUsage();
  
  Localizable getExamplesUsage();
  
  boolean parseArguments(String[] paramArrayOfString, UsageError paramUsageError);
  
  public static class UsageError {
    public Localizable msg;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wscompile\UsageIf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
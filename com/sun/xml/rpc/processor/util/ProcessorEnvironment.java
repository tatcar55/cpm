package com.sun.xml.rpc.processor.util;

import com.sun.xml.rpc.processor.generator.Names;
import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
import com.sun.xml.rpc.util.localization.Localizable;
import java.net.URLClassLoader;

public interface ProcessorEnvironment extends ProcessorEnvironment {
  public static final int F_VERBOSE = 1;
  
  public static final int F_WARNINGS = 2;
  
  void setFlags(int paramInt);
  
  int getFlags();
  
  String getClassPath();
  
  boolean verbose();
  
  void addGeneratedFile(GeneratedFileInfo paramGeneratedFileInfo);
  
  void deleteGeneratedFiles();
  
  URLClassLoader getClassLoader();
  
  void shutdown();
  
  void error(Localizable paramLocalizable);
  
  void warn(Localizable paramLocalizable);
  
  void info(Localizable paramLocalizable);
  
  void printStackTrace(Throwable paramThrowable);
  
  Names getNames();
  
  int getErrorCount();
  
  int getWarningCount();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\ProcessorEnvironment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
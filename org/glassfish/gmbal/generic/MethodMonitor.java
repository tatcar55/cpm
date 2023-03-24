package org.glassfish.gmbal.generic;

public interface MethodMonitor {
  void enter(boolean paramBoolean, String paramString, Object... paramVarArgs);
  
  void info(boolean paramBoolean, Object... paramVarArgs);
  
  void exit(boolean paramBoolean);
  
  void exit(boolean paramBoolean, Object paramObject);
  
  void clear();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\MethodMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
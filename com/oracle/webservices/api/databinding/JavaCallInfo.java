package com.oracle.webservices.api.databinding;

import java.lang.reflect.Method;

public interface JavaCallInfo {
  Method getMethod();
  
  Object[] getParameters();
  
  Object getReturnValue();
  
  void setReturnValue(Object paramObject);
  
  Throwable getException();
  
  void setException(Throwable paramThrowable);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\databinding\JavaCallInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
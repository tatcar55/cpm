package com.sun.xml.registry.common;

import javax.xml.registry.JAXRException;
import javax.xml.registry.JAXRResponse;

public class JAXRResponseImpl implements JAXRResponse {
  protected String requestId;
  
  protected int status = 0;
  
  public String getRequestId() throws JAXRException {
    return this.requestId;
  }
  
  public void setRequestId(String paramString) {
    this.requestId = paramString;
  }
  
  public int getStatus() throws JAXRException {
    synchronized (this) {
      return this.status;
    } 
  }
  
  public void setStatus(int paramInt) {
    synchronized (this) {
      this.status = paramInt;
    } 
  }
  
  public boolean isAvailable() throws JAXRException {
    return (getStatus() != 3);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\JAXRResponseImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
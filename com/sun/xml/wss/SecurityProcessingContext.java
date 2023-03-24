package com.sun.xml.wss;

import com.sun.xml.wss.impl.policy.SecurityPolicy;
import com.sun.xml.wss.impl.policy.StaticPolicyContext;
import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.soap.SOAPMessage;

public interface SecurityProcessingContext {
  void copy(SecurityProcessingContext paramSecurityProcessingContext1, SecurityProcessingContext paramSecurityProcessingContext2) throws XWSSecurityException;
  
  int getConfigType();
  
  Map getExtraneousProperties();
  
  Object getExtraneousProperty(String paramString);
  
  CallbackHandler getHandler();
  
  String getMessageIdentifier();
  
  StaticPolicyContext getPolicyContext();
  
  SOAPMessage getSOAPMessage();
  
  SecurityEnvironment getSecurityEnvironment();
  
  SecurityPolicy getSecurityPolicy();
  
  boolean isInboundMessage();
  
  void isInboundMessage(boolean paramBoolean);
  
  void removeExtraneousProperty(String paramString);
  
  void reset();
  
  void setConfigType(int paramInt);
  
  void setExtraneousProperty(String paramString, Object paramObject);
  
  void setHandler(CallbackHandler paramCallbackHandler);
  
  void setMessageIdentifier(String paramString);
  
  void setPolicyContext(StaticPolicyContext paramStaticPolicyContext);
  
  void setSOAPMessage(SOAPMessage paramSOAPMessage) throws XWSSecurityException;
  
  void setSecurityEnvironment(SecurityEnvironment paramSecurityEnvironment);
  
  void setSecurityPolicy(SecurityPolicy paramSecurityPolicy) throws XWSSecurityException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\SecurityProcessingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
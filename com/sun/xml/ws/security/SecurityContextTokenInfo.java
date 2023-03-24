package com.sun.xml.ws.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.glassfish.gmbal.Description;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;

@ManagedData(name = "SecurityContextTokenInfo")
@Description("Security parameters")
public interface SecurityContextTokenInfo extends Serializable {
  @ManagedAttribute
  @Description("Identifier")
  String getIdentifier();
  
  void setIdentifier(String paramString);
  
  @ManagedAttribute
  @Description("External identifier")
  String getExternalId();
  
  void setExternalId(String paramString);
  
  String getInstance();
  
  void setInstance(String paramString);
  
  @ManagedAttribute
  @Description("Secret")
  byte[] getSecret();
  
  byte[] getInstanceSecret(String paramString);
  
  void addInstance(String paramString, byte[] paramArrayOfbyte);
  
  @ManagedAttribute
  @Description("Creation time")
  Date getCreationTime();
  
  void setCreationTime(Date paramDate);
  
  @ManagedAttribute
  @Description("Expiration time")
  Date getExpirationTime();
  
  void setExpirationTime(Date paramDate);
  
  Set getInstanceKeys();
  
  @ManagedAttribute
  @Description("Issued token context")
  IssuedTokenContext getIssuedTokenContext();
  
  IssuedTokenContext getIssuedTokenContext(SecurityTokenReference paramSecurityTokenReference);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\SecurityContextTokenInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
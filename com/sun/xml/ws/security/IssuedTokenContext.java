package com.sun.xml.ws.security;

import com.sun.xml.wss.XWSSecurityException;
import java.net.URI;
import java.security.Key;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.security.auth.Subject;
import org.glassfish.gmbal.Description;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;

@ManagedData
@Description("Information used by Trust and Security enforcement")
public interface IssuedTokenContext {
  public static final String CLAIMED_ATTRUBUTES = "cliamedAttributes";
  
  public static final String TARGET_SERVICE_CERTIFICATE = "tagetedServiceCertificate";
  
  public static final String STS_CERTIFICATE = "stsCertificate";
  
  public static final String STS_PRIVATE_KEY = "stsPrivateKey";
  
  public static final String WS_TRUST_VERSION = "wstVersion";
  
  public static final String CONFIRMATION_METHOD = "samlConfirmationMethod";
  
  public static final String CONFIRMATION_KEY_INFO = "samlConfirmationKeyInfo";
  
  public static final String AUTHN_CONTEXT = "authnContext";
  
  public static final String KEY_WRAP_ALGORITHM = "keyWrapAlgorithm";
  
  public static final String STATUS = "status";
  
  void setTokenIssuer(String paramString);
  
  @ManagedAttribute
  @Description("Token issuer")
  String getTokenIssuer();
  
  @ManagedAttribute
  @Description("Requestor certificate")
  X509Certificate getRequestorCertificate();
  
  void setRequestorCertificate(X509Certificate paramX509Certificate);
  
  @ManagedAttribute
  @Description("Requestor username")
  String getRequestorUsername();
  
  void setRequestorUsername(String paramString);
  
  @ManagedAttribute
  @Description("Requestor subject")
  Subject getRequestorSubject();
  
  void setRequestorSubject(Subject paramSubject);
  
  void setTokenType(String paramString);
  
  @ManagedAttribute
  @Description("Token type")
  String getTokenType();
  
  void setKeyType(String paramString);
  
  @ManagedAttribute
  @Description("Key type")
  String getKeyType();
  
  void setAppliesTo(String paramString);
  
  @ManagedAttribute
  @Description("appliesTo value")
  String getAppliesTo();
  
  void setSecurityToken(Token paramToken);
  
  @ManagedAttribute
  @Description("Security token")
  Token getSecurityToken();
  
  void setAssociatedProofToken(Token paramToken);
  
  @ManagedAttribute
  @Description("Proof token")
  Token getAssociatedProofToken();
  
  @ManagedAttribute
  @Description("Attached security token reference")
  Token getAttachedSecurityTokenReference();
  
  @ManagedAttribute
  @Description("Unattached security token reference")
  Token getUnAttachedSecurityTokenReference();
  
  void setAttachedSecurityTokenReference(Token paramToken);
  
  void setUnAttachedSecurityTokenReference(Token paramToken);
  
  ArrayList<Object> getSecurityPolicy();
  
  void setOtherPartyEntropy(Object paramObject);
  
  Key getDecipheredOtherPartyEntropy(Key paramKey) throws XWSSecurityException;
  
  @ManagedAttribute
  @Description("Other party entropy")
  Object getOtherPartyEntropy();
  
  void setSelfEntropy(Object paramObject);
  
  @ManagedAttribute
  @Description("Self entropy")
  Object getSelfEntropy();
  
  URI getComputedKeyAlgorithmFromProofToken();
  
  void setProofKey(byte[] paramArrayOfbyte);
  
  byte[] getProofKey();
  
  void setProofKeyPair(KeyPair paramKeyPair);
  
  KeyPair getProofKeyPair();
  
  void setAuthnContextClass(String paramString);
  
  String getAuthnContextClass();
  
  Date getCreationTime();
  
  Date getExpirationTime();
  
  void setCreationTime(Date paramDate);
  
  void setEndpointAddress(String paramString);
  
  String getEndpointAddress();
  
  void setExpirationTime(Date paramDate);
  
  String getSignatureAlgorithm();
  
  void setSignatureAlgorithm(String paramString);
  
  String getEncryptionAlgorithm();
  
  void setEncryptionAlgorithm(String paramString);
  
  String getCanonicalizationAlgorithm();
  
  void setCanonicalizationAlgorithm(String paramString);
  
  String getSignWith();
  
  void setSignWith(String paramString);
  
  String getEncryptWith();
  
  void setEncryptWith(String paramString);
  
  SecurityContextTokenInfo getSecurityContextTokenInfo();
  
  void setTarget(Token paramToken);
  
  Token getTarget();
  
  void setSecurityContextTokenInfo(SecurityContextTokenInfo paramSecurityContextTokenInfo);
  
  void destroy();
  
  Map<String, Object> getOtherProperties();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\IssuedTokenContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
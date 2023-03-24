package com.sun.xml.wss;

import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
import com.sun.xml.wss.core.Timestamp;
import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
import com.sun.xml.wss.saml.Assertion;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.stream.XMLStreamReader;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface SecurityEnvironment {
  X509Certificate getDefaultCertificate(Map paramMap) throws XWSSecurityException;
  
  X509Certificate getCertificate(Map paramMap, String paramString, boolean paramBoolean) throws XWSSecurityException;
  
  SecretKey getSecretKey(Map paramMap, String paramString, boolean paramBoolean) throws XWSSecurityException;
  
  PrivateKey getPrivateKey(Map paramMap, String paramString) throws XWSSecurityException;
  
  PublicKey getPublicKey(Map paramMap, byte[] paramArrayOfbyte) throws XWSSecurityException;
  
  PublicKey getPublicKey(Map paramMap, byte[] paramArrayOfbyte, String paramString) throws XWSSecurityException;
  
  X509Certificate getCertificate(Map paramMap, byte[] paramArrayOfbyte) throws XWSSecurityException;
  
  X509Certificate getCertificate(Map paramMap, byte[] paramArrayOfbyte, String paramString) throws XWSSecurityException;
  
  PrivateKey getPrivateKey(Map paramMap, X509Certificate paramX509Certificate) throws XWSSecurityException;
  
  PrivateKey getPrivateKey(Map paramMap, BigInteger paramBigInteger, String paramString) throws XWSSecurityException;
  
  X509Certificate getCertificate(Map paramMap, PublicKey paramPublicKey, boolean paramBoolean) throws XWSSecurityException;
  
  PrivateKey getPrivateKey(Map paramMap, byte[] paramArrayOfbyte) throws XWSSecurityException;
  
  PrivateKey getPrivateKey(Map paramMap, byte[] paramArrayOfbyte, String paramString) throws XWSSecurityException;
  
  PrivateKey getPrivateKey(Map paramMap, PublicKey paramPublicKey, boolean paramBoolean) throws XWSSecurityException;
  
  PublicKey getPublicKey(Map paramMap, BigInteger paramBigInteger, String paramString) throws XWSSecurityException;
  
  X509Certificate getCertificate(Map paramMap, BigInteger paramBigInteger, String paramString) throws XWSSecurityException;
  
  boolean authenticateUser(Map paramMap, String paramString1, String paramString2) throws XWSSecurityException;
  
  boolean authenticateUser(Map paramMap, String paramString1, String paramString2, String paramString3, String paramString4) throws XWSSecurityException;
  
  String authenticateUser(Map paramMap, String paramString) throws XWSSecurityException;
  
  Subject getSubject();
  
  void validateCreationTime(Map paramMap, String paramString, long paramLong1, long paramLong2) throws XWSSecurityException;
  
  boolean validateCertificate(X509Certificate paramX509Certificate, Map paramMap) throws XWSSecurityException;
  
  void updateOtherPartySubject(Subject paramSubject, String paramString1, String paramString2);
  
  void updateOtherPartySubject(Subject paramSubject, X509Certificate paramX509Certificate);
  
  void updateOtherPartySubject(Subject paramSubject, Assertion paramAssertion);
  
  void updateOtherPartySubject(Subject paramSubject, XMLStreamReader paramXMLStreamReader);
  
  void updateOtherPartySubject(Subject paramSubject1, Subject paramSubject2);
  
  void validateSAMLAssertion(Map paramMap, Element paramElement) throws XWSSecurityException;
  
  void validateSAMLAssertion(Map paramMap, XMLStreamReader paramXMLStreamReader) throws XWSSecurityException;
  
  Element locateSAMLAssertion(Map paramMap, Element paramElement, String paramString, Document paramDocument) throws XWSSecurityException;
  
  AuthenticationTokenPolicy.SAMLAssertionBinding populateSAMLPolicy(Map paramMap, AuthenticationTokenPolicy.SAMLAssertionBinding paramSAMLAssertionBinding, DynamicApplicationContext paramDynamicApplicationContext) throws XWSSecurityException;
  
  String getUsername(Map paramMap) throws XWSSecurityException;
  
  String getPassword(Map paramMap) throws XWSSecurityException;
  
  void validateTimestamp(Map paramMap, Timestamp paramTimestamp, long paramLong1, long paramLong2) throws XWSSecurityException;
  
  void validateTimestamp(Map paramMap, String paramString1, String paramString2, long paramLong1, long paramLong2) throws XWSSecurityException;
  
  CallbackHandler getCallbackHandler() throws XWSSecurityException;
  
  boolean validateAndCacheNonce(Map paramMap, String paramString1, String paramString2, long paramLong) throws XWSSecurityException;
  
  boolean isSelfCertificate(X509Certificate paramX509Certificate);
  
  KerberosContext doKerberosLogin() throws XWSSecurityException;
  
  KerberosContext doKerberosLogin(byte[] paramArrayOfbyte) throws XWSSecurityException;
  
  void updateOtherPartySubject(Subject paramSubject, GSSName paramGSSName, GSSCredential paramGSSCredential);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\SecurityEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
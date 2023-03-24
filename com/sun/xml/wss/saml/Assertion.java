package com.sun.xml.wss.saml;

import com.sun.xml.ws.security.Token;
import com.sun.xml.wss.XWSSecurityException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.crypto.dsig.DigestMethod;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface Assertion extends Token {
  Element sign(PublicKey paramPublicKey, PrivateKey paramPrivateKey) throws SAMLException;
  
  Element sign(X509Certificate paramX509Certificate, PrivateKey paramPrivateKey) throws SAMLException;
  
  Element sign(X509Certificate paramX509Certificate, PrivateKey paramPrivateKey, boolean paramBoolean) throws SAMLException;
  
  Element sign(X509Certificate paramX509Certificate, PrivateKey paramPrivateKey, boolean paramBoolean, String paramString1, String paramString2) throws SAMLException;
  
  Element sign(DigestMethod paramDigestMethod, String paramString, PublicKey paramPublicKey, PrivateKey paramPrivateKey) throws SAMLException;
  
  Element sign(DigestMethod paramDigestMethod, String paramString, X509Certificate paramX509Certificate, PrivateKey paramPrivateKey) throws SAMLException;
  
  Element sign(DigestMethod paramDigestMethod, String paramString, X509Certificate paramX509Certificate, PrivateKey paramPrivateKey, boolean paramBoolean) throws SAMLException;
  
  void setMajorVersion(BigInteger paramBigInteger);
  
  void setMinorVersion(BigInteger paramBigInteger);
  
  void setVersion(String paramString);
  
  Element toElement(Node paramNode) throws XWSSecurityException;
  
  String getSamlIssuer();
  
  String getAssertionID();
  
  String getID();
  
  String getVersion();
  
  BigInteger getMajorVersion();
  
  BigInteger getMinorVersion();
  
  String getIssueInstance();
  
  List<Object> getStatements();
  
  Conditions getConditions();
  
  Advice getAdvice();
  
  Subject getSubject();
  
  boolean verifySignature(PublicKey paramPublicKey) throws SAMLException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\Assertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
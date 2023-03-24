package org.jcp.xml.dsig.internal.dom;

import java.math.BigInteger;
import java.security.KeyException;
import java.security.PublicKey;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.PGPData;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMKeyInfoFactory extends KeyInfoFactory {
  public KeyInfo newKeyInfo(List paramList) {
    return newKeyInfo(paramList, null);
  }
  
  public KeyInfo newKeyInfo(List paramList, String paramString) {
    return new DOMKeyInfo(paramList, paramString);
  }
  
  public KeyName newKeyName(String paramString) {
    return new DOMKeyName(paramString);
  }
  
  public KeyValue newKeyValue(PublicKey paramPublicKey) throws KeyException {
    return new DOMKeyValue(paramPublicKey);
  }
  
  public PGPData newPGPData(byte[] paramArrayOfbyte) {
    return newPGPData(paramArrayOfbyte, null, null);
  }
  
  public PGPData newPGPData(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, List paramList) {
    return new DOMPGPData(paramArrayOfbyte1, paramArrayOfbyte2, paramList);
  }
  
  public PGPData newPGPData(byte[] paramArrayOfbyte, List paramList) {
    return new DOMPGPData(paramArrayOfbyte, paramList);
  }
  
  public RetrievalMethod newRetrievalMethod(String paramString) {
    return newRetrievalMethod(paramString, null, null);
  }
  
  public RetrievalMethod newRetrievalMethod(String paramString1, String paramString2, List paramList) {
    if (paramString1 == null)
      throw new NullPointerException("uri must not be null"); 
    return new DOMRetrievalMethod(paramString1, paramString2, paramList);
  }
  
  public X509Data newX509Data(List paramList) {
    return new DOMX509Data(paramList);
  }
  
  public X509IssuerSerial newX509IssuerSerial(String paramString, BigInteger paramBigInteger) {
    return new DOMX509IssuerSerial(paramString, paramBigInteger);
  }
  
  public boolean isFeatureSupported(String paramString) {
    if (paramString == null)
      throw new NullPointerException(); 
    return false;
  }
  
  public URIDereferencer getURIDereferencer() {
    return DOMURIDereferencer.INSTANCE;
  }
  
  public KeyInfo unmarshalKeyInfo(XMLStructure paramXMLStructure) throws MarshalException {
    if (paramXMLStructure == null)
      throw new NullPointerException("xmlStructure cannot be null"); 
    Node node = ((DOMStructure)paramXMLStructure).getNode();
    node.normalize();
    Element element = null;
    if (node.getNodeType() == 9) {
      element = ((Document)node).getDocumentElement();
    } else if (node.getNodeType() == 1) {
      element = (Element)node;
    } else {
      throw new MarshalException("xmlStructure does not contain a proper Node");
    } 
    String str = element.getLocalName();
    if (str == null)
      throw new MarshalException("Document implementation must support DOM Level 2 and be namespace aware"); 
    if (str.equals("KeyInfo"))
      return new DOMKeyInfo(element, null, getProvider()); 
    throw new MarshalException("invalid KeyInfo tag: " + str);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMKeyInfoFactory.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
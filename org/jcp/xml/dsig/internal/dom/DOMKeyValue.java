package org.jcp.xml.dsig.internal.dom;

import java.security.KeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMKeyValue extends DOMStructure implements KeyValue {
  private KeyFactory rsakf;
  
  private KeyFactory dsakf;
  
  private PublicKey publicKey;
  
  private DOMStructure externalPublicKey;
  
  private DOMCryptoBinary p;
  
  private DOMCryptoBinary q;
  
  private DOMCryptoBinary g;
  
  private DOMCryptoBinary y;
  
  private DOMCryptoBinary j;
  
  private DOMCryptoBinary seed;
  
  private DOMCryptoBinary pgen;
  
  private DOMCryptoBinary modulus;
  
  private DOMCryptoBinary exponent;
  
  static final boolean $assertionsDisabled;
  
  public DOMKeyValue(PublicKey paramPublicKey) throws KeyException {
    if (paramPublicKey == null)
      throw new NullPointerException("key cannot be null"); 
    this.publicKey = paramPublicKey;
    if (paramPublicKey instanceof DSAPublicKey) {
      DSAPublicKey dSAPublicKey = (DSAPublicKey)paramPublicKey;
      DSAParams dSAParams = dSAPublicKey.getParams();
      this.p = new DOMCryptoBinary(dSAParams.getP());
      this.q = new DOMCryptoBinary(dSAParams.getQ());
      this.g = new DOMCryptoBinary(dSAParams.getG());
      this.y = new DOMCryptoBinary(dSAPublicKey.getY());
    } else if (paramPublicKey instanceof RSAPublicKey) {
      RSAPublicKey rSAPublicKey = (RSAPublicKey)paramPublicKey;
      this.exponent = new DOMCryptoBinary(rSAPublicKey.getPublicExponent());
      this.modulus = new DOMCryptoBinary(rSAPublicKey.getModulus());
    } else {
      throw new KeyException("unsupported key algorithm: " + paramPublicKey.getAlgorithm());
    } 
  }
  
  public DOMKeyValue(Element paramElement) throws MarshalException {
    Element element = DOMUtils.getFirstChildElement(paramElement);
    if (element.getLocalName().equals("DSAKeyValue")) {
      this.publicKey = unmarshalDSAKeyValue(element);
    } else if (element.getLocalName().equals("RSAKeyValue")) {
      this.publicKey = unmarshalRSAKeyValue(element);
    } else {
      this.publicKey = null;
      this.externalPublicKey = new DOMStructure(element);
    } 
  }
  
  public PublicKey getPublicKey() throws KeyException {
    if (this.publicKey == null)
      throw new KeyException("can't convert KeyValue to PublicKey"); 
    return this.publicKey;
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramNode);
    Element element = DOMUtils.createElement(document, "KeyValue", "http://www.w3.org/2000/09/xmldsig#", paramString);
    marshalPublicKey(element, document, paramString, paramDOMCryptoContext);
    paramNode.appendChild(element);
  }
  
  private void marshalPublicKey(Node paramNode, Document paramDocument, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    if (this.publicKey != null) {
      if (this.publicKey instanceof DSAPublicKey) {
        marshalDSAPublicKey(paramNode, paramDocument, paramString, paramDOMCryptoContext);
      } else if (this.publicKey instanceof RSAPublicKey) {
        marshalRSAPublicKey(paramNode, paramDocument, paramString, paramDOMCryptoContext);
      } else {
        throw new MarshalException(this.publicKey.getAlgorithm() + " public key algorithm not supported");
      } 
    } else {
      paramNode.appendChild(this.externalPublicKey.getNode());
    } 
  }
  
  private void marshalDSAPublicKey(Node paramNode, Document paramDocument, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Element element1 = DOMUtils.createElement(paramDocument, "DSAKeyValue", "http://www.w3.org/2000/09/xmldsig#", paramString);
    Element element2 = DOMUtils.createElement(paramDocument, "P", "http://www.w3.org/2000/09/xmldsig#", paramString);
    Element element3 = DOMUtils.createElement(paramDocument, "Q", "http://www.w3.org/2000/09/xmldsig#", paramString);
    Element element4 = DOMUtils.createElement(paramDocument, "G", "http://www.w3.org/2000/09/xmldsig#", paramString);
    Element element5 = DOMUtils.createElement(paramDocument, "Y", "http://www.w3.org/2000/09/xmldsig#", paramString);
    this.p.marshal(element2, paramString, paramDOMCryptoContext);
    this.q.marshal(element3, paramString, paramDOMCryptoContext);
    this.g.marshal(element4, paramString, paramDOMCryptoContext);
    this.y.marshal(element5, paramString, paramDOMCryptoContext);
    element1.appendChild(element2);
    element1.appendChild(element3);
    element1.appendChild(element4);
    element1.appendChild(element5);
    paramNode.appendChild(element1);
  }
  
  private void marshalRSAPublicKey(Node paramNode, Document paramDocument, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Element element1 = DOMUtils.createElement(paramDocument, "RSAKeyValue", "http://www.w3.org/2000/09/xmldsig#", paramString);
    Element element2 = DOMUtils.createElement(paramDocument, "Modulus", "http://www.w3.org/2000/09/xmldsig#", paramString);
    Element element3 = DOMUtils.createElement(paramDocument, "Exponent", "http://www.w3.org/2000/09/xmldsig#", paramString);
    this.modulus.marshal(element2, paramString, paramDOMCryptoContext);
    this.exponent.marshal(element3, paramString, paramDOMCryptoContext);
    element1.appendChild(element2);
    element1.appendChild(element3);
    paramNode.appendChild(element1);
  }
  
  private DSAPublicKey unmarshalDSAKeyValue(Element paramElement) throws MarshalException {
    if (this.dsakf == null)
      try {
        this.dsakf = KeyFactory.getInstance("DSA");
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new RuntimeException("unable to create DSA KeyFactory: " + noSuchAlgorithmException.getMessage());
      }  
    Element element = DOMUtils.getFirstChildElement(paramElement);
    if (element.getLocalName().equals("P")) {
      this.p = new DOMCryptoBinary(element.getFirstChild());
      element = DOMUtils.getNextSiblingElement(element);
      this.q = new DOMCryptoBinary(element.getFirstChild());
      element = DOMUtils.getNextSiblingElement(element);
    } 
    if (element.getLocalName().equals("G")) {
      this.g = new DOMCryptoBinary(element.getFirstChild());
      element = DOMUtils.getNextSiblingElement(element);
    } 
    this.y = new DOMCryptoBinary(element.getFirstChild());
    element = DOMUtils.getNextSiblingElement(element);
    if (element != null && element.getLocalName().equals("J")) {
      this.j = new DOMCryptoBinary(element.getFirstChild());
      element = DOMUtils.getNextSiblingElement(element);
    } 
    if (element != null) {
      this.seed = new DOMCryptoBinary(element.getFirstChild());
      element = DOMUtils.getNextSiblingElement(element);
      this.pgen = new DOMCryptoBinary(element.getFirstChild());
    } 
    DSAPublicKeySpec dSAPublicKeySpec = new DSAPublicKeySpec(this.y.getBigNum(), this.p.getBigNum(), this.q.getBigNum(), this.g.getBigNum());
    return (DSAPublicKey)generatePublicKey(this.dsakf, dSAPublicKeySpec);
  }
  
  private RSAPublicKey unmarshalRSAKeyValue(Element paramElement) throws MarshalException {
    if (this.rsakf == null)
      try {
        this.rsakf = KeyFactory.getInstance("RSA");
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new RuntimeException("unable to create RSA KeyFactory: " + noSuchAlgorithmException.getMessage());
      }  
    Element element1 = DOMUtils.getFirstChildElement(paramElement);
    this.modulus = new DOMCryptoBinary(element1.getFirstChild());
    Element element2 = DOMUtils.getNextSiblingElement(element1);
    this.exponent = new DOMCryptoBinary(element2.getFirstChild());
    RSAPublicKeySpec rSAPublicKeySpec = new RSAPublicKeySpec(this.modulus.getBigNum(), this.exponent.getBigNum());
    return (RSAPublicKey)generatePublicKey(this.rsakf, rSAPublicKeySpec);
  }
  
  private PublicKey generatePublicKey(KeyFactory paramKeyFactory, KeySpec paramKeySpec) {
    try {
      return paramKeyFactory.generatePublic(paramKeySpec);
    } catch (InvalidKeySpecException invalidKeySpecException) {
      return null;
    } 
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof KeyValue))
      return false; 
    try {
      KeyValue keyValue = (KeyValue)paramObject;
      if (this.publicKey == null) {
        if (keyValue.getPublicKey() != null)
          return false; 
      } else if (!this.publicKey.equals(keyValue.getPublicKey())) {
        return false;
      } 
    } catch (KeyException keyException) {
      return false;
    } 
    return true;
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 45;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMKeyValue.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
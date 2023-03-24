/*     */ package com.sun.xml.wss.core.reference;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509IssuerSerial;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import java.math.BigInteger;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509IssuerSerial
/*     */   extends ReferenceElement
/*     */ {
/*  70 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */   private XMLX509IssuerSerial delegate;
/*     */   
/*     */   public X509IssuerSerial(SOAPElement element) throws XWSSecurityException {
/*     */     SOAPElement issuerSerialElement;
/*     */     Iterator<SOAPElement> issuerNames, serialNumbers;
/*  76 */     this.cert = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     boolean throwAnException = false;
/*  89 */     if (!element.getLocalName().equals("X509Data") || !XMLUtil.inSignatureNS(element))
/*     */     {
/*  91 */       throwAnException = true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  96 */       issuerSerialElement = element.getChildElements(soapFactory.createName("X509IssuerSerial", "ds", "http://www.w3.org/2000/09/xmldsig#")).next();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 102 */     catch (Exception e) {
/* 103 */       log.log(Level.SEVERE, "WSS0750.soap.exception", new Object[] { "ds:X509IssuerSerial", e.getMessage() });
/*     */ 
/*     */       
/* 106 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 111 */       issuerNames = issuerSerialElement.getChildElements(soapFactory.createName("X509IssuerName", "ds", "http://www.w3.org/2000/09/xmldsig#"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 117 */     catch (SOAPException e) {
/* 118 */       log.log(Level.SEVERE, "WSS0758.soap.exception", new Object[] { "ds:X509IssuerName", e.getMessage() });
/*     */ 
/*     */       
/* 121 */       throw new XWSSecurityException(e);
/*     */     } 
/* 123 */     if (!issuerNames.hasNext())
/* 124 */       throwAnException = true; 
/* 125 */     SOAPElement issuerNameElement = issuerNames.next();
/* 126 */     String issuerName = XMLUtil.getFullTextFromChildren(issuerNameElement);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 131 */       serialNumbers = issuerSerialElement.getChildElements(soapFactory.createName("X509SerialNumber", "ds", "http://www.w3.org/2000/09/xmldsig#"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 137 */     catch (SOAPException e) {
/* 138 */       log.log(Level.SEVERE, "WSS0758.soap.exception", new Object[] { "ds:X509SerialNumber", e.getMessage() });
/*     */ 
/*     */       
/* 141 */       throw new XWSSecurityException(e);
/*     */     } 
/* 143 */     if (!serialNumbers.hasNext())
/* 144 */       throwAnException = true; 
/* 145 */     SOAPElement serialNumberElement = serialNumbers.next();
/* 146 */     String serialNumberString = XMLUtil.getFullTextFromChildren(serialNumberElement);
/*     */     
/* 148 */     BigInteger serialNumber = new BigInteger(serialNumberString);
/*     */     
/* 150 */     if (throwAnException) {
/* 151 */       log.log(Level.SEVERE, "WSS0759.error.creating.issuerserial");
/*     */       
/* 153 */       throw new XWSSecurityException("Cannot create X509IssuerSerial object out of given element");
/*     */     } 
/*     */     
/* 156 */     this.ownerDoc = element.getOwnerDocument();
/* 157 */     this.delegate = new XMLX509IssuerSerial(this.ownerDoc, issuerName, serialNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate cert;
/*     */ 
/*     */   
/*     */   private Document ownerDoc;
/*     */ 
/*     */ 
/*     */   
/*     */   public X509IssuerSerial(Document doc, String X509IssuerName, BigInteger X509SerialNumber) {
/*     */     this.cert = null;
/* 172 */     this.delegate = new XMLX509IssuerSerial(doc, X509IssuerName, X509SerialNumber);
/*     */     
/* 174 */     this.ownerDoc = doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509IssuerSerial(Document doc, String X509IssuerName, String X509SerialNumber) {
/*     */     this.cert = null;
/* 188 */     this.delegate = new XMLX509IssuerSerial(doc, X509IssuerName, X509SerialNumber);
/*     */     
/* 190 */     this.ownerDoc = doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509IssuerSerial(Document doc, String X509IssuerName, int X509SerialNumber) {
/*     */     this.cert = null;
/* 204 */     this.delegate = new XMLX509IssuerSerial(doc, X509IssuerName, X509SerialNumber);
/*     */     
/* 206 */     this.ownerDoc = doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509IssuerSerial(Document doc, X509Certificate x509certificate) {
/*     */     this.cert = null;
/* 216 */     this.delegate = new XMLX509IssuerSerial(doc, x509certificate);
/* 217 */     this.ownerDoc = doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getSerialNumber() throws XWSSecurityException {
/*     */     try {
/* 227 */       return this.delegate.getSerialNumber();
/* 228 */     } catch (Exception e) {
/* 229 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerialNumberInteger() throws XWSSecurityException {
/*     */     try {
/* 240 */       return this.delegate.getSerialNumberInteger();
/* 241 */     } catch (Exception e) {
/* 242 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssuerName() throws XWSSecurityException {
/*     */     try {
/* 253 */       return this.delegate.getIssuerName();
/* 254 */     } catch (Exception e) {
/* 255 */       log.log(Level.SEVERE, "WSS0763.exception.issuername", new Object[] { e.getMessage() });
/*     */ 
/*     */       
/* 258 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/*     */     try {
/* 264 */       SOAPElement issuerSerialElement = (SOAPElement)this.delegate.getElement();
/* 265 */       SOAPElement x509DataElement = (SOAPElement)this.ownerDoc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:X509Data");
/*     */ 
/*     */ 
/*     */       
/* 269 */       x509DataElement.addNamespaceDeclaration("ds", "http://www.w3.org/2000/09/xmldsig#");
/*     */       
/* 271 */       x509DataElement.addChildElement(issuerSerialElement);
/* 272 */       setSOAPElement(x509DataElement);
/* 273 */       return x509DataElement;
/* 274 */     } catch (Exception e) {
/* 275 */       log.log(Level.SEVERE, "WSS0750.soap.exception", new Object[] { "ds:X509IssuerSerial", e.getMessage() });
/*     */ 
/*     */       
/* 278 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/* 283 */     this.cert = cert;
/*     */   }
/*     */   
/*     */   public X509Certificate getCertificate() {
/* 287 */     return this.cert;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\X509IssuerSerial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
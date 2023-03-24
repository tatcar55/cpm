/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.ObjectContainer;
/*     */ import com.sun.org.apache.xml.internal.security.signature.SignedInfo;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignature;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*     */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.security.Key;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class SignatureHeaderBlock
/*     */   extends SecurityHeaderBlockImpl
/*     */ {
/*     */   public static final String SignatureSpecNS = "http://www.w3.org/2000/09/xmldsig#";
/*     */   public static final String SignatureSpecNSprefix = "ds";
/*     */   public static final String TAG_SIGNATURE = "Signature";
/*  94 */   XMLSignature delegateSignature = null;
/*     */   
/*     */   boolean dirty = false;
/*     */   
/*  98 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   String baseURI = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private Document document = null;
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
/*     */   public SignatureHeaderBlock(SOAPElement elem) throws XWSSecurityException {
/* 124 */     super(elem);
/*     */     try {
/* 126 */       this.document = elem.getOwnerDocument();
/* 127 */       this.delegateSignature = new XMLSignature(elem, null);
/* 128 */     } catch (Exception e) {
/*     */       
/* 130 */       log.log(Level.SEVERE, "WSS0322.exception.creating.signatureblock", e);
/*     */ 
/*     */ 
/*     */       
/* 134 */       throw new XWSSecurityException(e);
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public SignatureHeaderBlock(XMLSignature signature) throws XWSSecurityException {
/* 150 */     this.document = signature.getDocument();
/* 151 */     this.delegateSignature = signature;
/* 152 */     this.dirty = true;
/* 153 */     setSOAPElement(getAsSoapElement());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureHeaderBlock(Document doc, String signatureMethodURI) throws XWSSecurityException {
/*     */     try {
/* 171 */       this.document = doc;
/* 172 */       this.delegateSignature = new XMLSignature(doc, null, signatureMethodURI, "http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */ 
/*     */ 
/*     */       
/* 176 */       this.dirty = true;
/* 177 */       setSOAPElement(getAsSoapElement());
/* 178 */     } catch (XMLSecurityException e) {
/* 179 */       log.log(Level.SEVERE, "WSS0322.exception.creating.signatureblock", e);
/*     */ 
/*     */ 
/*     */       
/* 183 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature getSignature() {
/* 192 */     return this.delegateSignature;
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
/*     */   
/*     */   public void sign(Key signingKey) throws XWSSecurityException {
/*     */     try {
/* 207 */       this.delegateSignature.sign(signingKey);
/* 208 */       this.dirty = true;
/* 209 */     } catch (XMLSignatureException e) {
/* 210 */       log.log(Level.SEVERE, "WSS0323.exception.while.signing", e);
/* 211 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getSignedInfo() throws XWSSecurityException {
/* 221 */     return convertToSoapElement(this.delegateSignature.getSignedInfo());
/*     */   }
/*     */   
/*     */   public SignedInfo getDSSignedInfo() {
/* 225 */     return this.delegateSignature.getSignedInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getKeyInfo() throws XWSSecurityException {
/* 234 */     return convertToSoapElement(this.delegateSignature.getKeyInfo());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyInfoHeaderBlock getKeyInfoHeaderBlock() throws XWSSecurityException {
/* 244 */     return new KeyInfoHeaderBlock(this.delegateSignature.getKeyInfo());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getSignatureValue() throws XWSSecurityException {
/*     */     try {
/* 253 */       return this.delegateSignature.getSignatureValue();
/* 254 */     } catch (XMLSignatureException e) {
/* 255 */       log.log(Level.SEVERE, "WSS0324.exception.in.getting.signaturevalue", e);
/*     */ 
/*     */ 
/*     */       
/* 259 */       throw new XWSSecurityException(e);
/*     */     } 
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
/*     */   
/*     */   public void addSignedInfoReference(String referenceURI, Transforms transforms) throws XWSSecurityException {
/*     */     try {
/* 275 */       this.delegateSignature.addDocument(referenceURI, transforms);
/* 276 */       this.dirty = true;
/* 277 */     } catch (XMLSecurityException e) {
/* 278 */       log.log(Level.SEVERE, "WSS0325.exception.adding.reference.to.signedinfo", e);
/*     */ 
/*     */ 
/*     */       
/* 282 */       throw new XWSSecurityException(e);
/*     */     } 
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
/*     */   
/*     */   public void addSignedInfoReference(String referenceURI, Transforms trans, String digestURI) throws XWSSecurityException {
/*     */     try {
/* 298 */       this.delegateSignature.addDocument(referenceURI, trans, digestURI);
/* 299 */       this.dirty = true;
/* 300 */     } catch (XMLSecurityException e) {
/* 301 */       log.log(Level.SEVERE, "WSS0325.exception.adding.reference.to.signedinfo", e);
/*     */ 
/*     */ 
/*     */       
/* 305 */       throw new XWSSecurityException(e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSignedInfoReference(String referenceURI, Transforms trans, String digestURI, String referenceId, String referenceType) throws XWSSecurityException {
/*     */     try {
/* 329 */       this.delegateSignature.addDocument(referenceURI, trans, digestURI, referenceId, referenceType);
/*     */       
/* 331 */       this.dirty = true;
/* 332 */     } catch (XMLSecurityException e) {
/* 333 */       log.log(Level.SEVERE, "WSS0325.exception.adding.reference.to.signedinfo", e);
/*     */ 
/*     */ 
/*     */       
/* 337 */       throw new XWSSecurityException(e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkSignatureValue(X509Certificate cert) throws XWSSecurityException {
/*     */     try {
/* 355 */       return this.delegateSignature.checkSignatureValue(cert);
/* 356 */     } catch (XMLSignatureException e) {
/* 357 */       log.log(Level.SEVERE, "WSS0326.exception.verifying.signature", e);
/* 358 */       throw new XWSSecurityException(e);
/*     */     } 
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
/*     */   
/*     */   public boolean checkSignatureValue(Key pk) throws XWSSecurityException {
/*     */     try {
/* 374 */       return this.delegateSignature.checkSignatureValue(pk);
/* 375 */     } catch (XMLSignatureException e) {
/* 376 */       log.log(Level.SEVERE, "WSS0326.exception.verifying.signature", e);
/* 377 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendObject(SOAPElement object) throws XWSSecurityException {
/*     */     try {
/* 386 */       ObjectContainer objc = new ObjectContainer(object, null);
/* 387 */       this.delegateSignature.appendObject(objc);
/* 388 */     } catch (XMLSecurityException e) {
/* 389 */       log.log(Level.SEVERE, "WSS0382.error.appending.object", e.getMessage());
/* 390 */       throw new XWSSecurityException(e);
/*     */     } 
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
/*     */   public SOAPElement getObjectItem(int index) throws XWSSecurityException {
/* 404 */     return convertToSoapElement(this.delegateSignature.getObjectItem(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getObjectCount() {
/* 413 */     return this.delegateSignature.getObjectLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 420 */     this.delegateSignature.setId(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 429 */     return this.delegateSignature.getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseURI(String uri) {
/* 437 */     this.baseURI = uri;
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
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 449 */     if (this.document == null) {
/* 450 */       log.log(Level.SEVERE, "WSS0383.document.not.set");
/* 451 */       throw new XWSSecurityException("Document not set");
/*     */     } 
/* 453 */     if (this.dirty) {
/* 454 */       setSOAPElement(convertToSoapElement(this.delegateSignature));
/* 455 */       this.dirty = false;
/*     */     } 
/* 457 */     return this.delegateElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocument(Document doc) {
/* 465 */     this.document = doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChanges() {
/* 476 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setApacheResourceResolver(ResourceResolverSpi resolver) {
/* 483 */     this.delegateSignature.addResourceResolver(resolver);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 488 */     return SecurityHeaderBlockImpl.fromSoapElement(element, SignatureHeaderBlock.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPElement convertToSoapElement(ElementProxy proxy) throws XWSSecurityException {
/*     */     try {
/* 495 */       Element elem = proxy.getElement();
/* 496 */       if (elem instanceof SOAPElement) {
/* 497 */         return (SOAPElement)elem;
/*     */       }
/* 499 */       return (SOAPElement)this.document.importNode(elem, true);
/*     */     }
/* 501 */     catch (Exception e) {
/* 502 */       log.log(Level.SEVERE, "WSS0327.exception.converting.signature.tosoapelement", e);
/*     */ 
/*     */ 
/*     */       
/* 506 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\SignatureHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
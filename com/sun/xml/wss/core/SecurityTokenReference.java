/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.ws.security.SecurityTokenReference;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.reference.DirectReference;
/*     */ import com.sun.xml.wss.core.reference.EncryptedKeySHA1Identifier;
/*     */ import com.sun.xml.wss.core.reference.KeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.SamlKeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.X509IssuerSerial;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.Node;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
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
/*     */ public class SecurityTokenReference
/*     */   extends SecurityHeaderBlockImpl
/*     */   implements SecurityTokenReference
/*     */ {
/*  83 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   private ReferenceElement refElement;
/*     */ 
/*     */ 
/*     */   
/*     */   private Element samlAuthorityBinding;
/*     */ 
/*     */   
/*  94 */   private static final String authorityBinding = "AuthorityBinding".intern();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityTokenReference() throws XWSSecurityException {
/*     */     try {
/* 102 */       setSOAPElement(getSoapFactory().createElement("SecurityTokenReference", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*     */     
/*     */     }
/* 110 */     catch (SOAPException e) {
/* 111 */       log.log(Level.SEVERE, "WSS0377.error.creating.str", e.getMessage());
/* 112 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityTokenReference(Document doc) throws XWSSecurityException {
/*     */     try {
/* 122 */       setSOAPElement((SOAPElement)doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:SecurityTokenReference"));
/*     */ 
/*     */ 
/*     */       
/* 126 */       addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*     */     
/*     */     }
/* 129 */     catch (Exception e) {
/* 130 */       log.log(Level.SEVERE, "WSS0378.error.creating.str", e.getMessage());
/* 131 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityTokenReference(SOAPElement element, boolean isBSP) throws XWSSecurityException {
/* 142 */     super(element);
/*     */     
/* 144 */     if (!element.getLocalName().equals("SecurityTokenReference") || !XMLUtil.inWsseNS(element)) {
/*     */       
/* 146 */       log.log(Level.SEVERE, "WSS0379.error.creating.str", element.getTagName());
/* 147 */       throw new XWSSecurityException("Invalid tokenRef passed");
/*     */     } 
/*     */     
/* 150 */     isBSP(isBSP);
/*     */     
/* 152 */     Iterator<Node> eachChild = getChildElements();
/* 153 */     if (!eachChild.hasNext()) {
/* 154 */       throw new XWSSecurityException("Error: A SECURITY_TOKEN_REFERENCE with No child elements encountered");
/*     */     }
/*     */     
/* 157 */     Node node = null;
/*     */ 
/*     */     
/* 160 */     int refMechanismFound = 0;
/*     */     
/* 162 */     while (eachChild.hasNext()) {
/*     */       
/* 164 */       if (isBSP && refMechanismFound > 1) {
/* 165 */         throw new XWSSecurityException("Violation of BSP R3061:  A SECURITY_TOKEN_REFERENCE MUST have exactly one child element");
/*     */       }
/*     */ 
/*     */       
/* 169 */       node = eachChild.next();
/*     */       
/* 171 */       if (node == null) {
/* 172 */         log.log(Level.SEVERE, "WSS0379.error.creating.str");
/* 173 */         throw new XWSSecurityException("Passed tokenReference does not contain a refElement");
/*     */       } 
/*     */ 
/*     */       
/* 177 */       if (node.getNodeType() != 1) {
/*     */         continue;
/*     */       }
/*     */       
/* 181 */       if (authorityBinding == node.getLocalName() || authorityBinding.equals(node.getLocalName())) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 186 */           this.samlAuthorityBinding = (Element)node;
/* 187 */         } catch (Exception e) {
/* 188 */           throw new XWSSecurityException(e);
/*     */         }  continue;
/*     */       } 
/* 191 */       this.refElement = getReferenceElementfromSoapElement((SOAPElement)node, isBSP);
/* 192 */       refMechanismFound++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityTokenReference(SOAPElement element) throws XWSSecurityException {
/* 199 */     this(element, false);
/*     */   }
/*     */   
/*     */   public ReferenceElement getReference() {
/* 203 */     return this.refElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSamlAuthorityBinding(Element binding, Document doc) throws XWSSecurityException {
/* 208 */     if (this.samlAuthorityBinding != null) {
/* 209 */       throw new XWSSecurityException(" SAML AuthorityBinding element is already present");
/*     */     }
/*     */     
/*     */     try {
/* 213 */       addTextNode("\n");
/* 214 */       Element temp = (Element)doc.getOwnerDocument().importNode(binding, true);
/* 215 */       addChildElement((SOAPElement)temp);
/*     */       
/* 217 */       addTextNode("\n");
/* 218 */     } catch (Exception e) {
/* 219 */       throw new XWSSecurityException(e);
/*     */     } 
/* 221 */     this.samlAuthorityBinding = binding;
/*     */   }
/*     */   
/*     */   public Element getSamlAuthorityBinding() {
/* 225 */     return this.samlAuthorityBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReference(ReferenceElement referenceElement) throws XWSSecurityException {
/* 232 */     if (this.refElement != null) {
/* 233 */       log.log(Level.SEVERE, "WSS0380.error.setting.reference");
/* 234 */       throw new XWSSecurityException("Reference element is already present");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 239 */       addTextNode("\n");
/* 240 */       addChildElement(referenceElement.getAsSoapElement());
/* 241 */       addTextNode("\n");
/* 242 */     } catch (SOAPException e) {
/* 243 */       log.log(Level.SEVERE, "WSS0381.error.setting.reference");
/* 244 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 247 */     this.refElement = referenceElement;
/*     */   }
/*     */   
/*     */   public void setWsuId(String wsuId) {
/* 251 */     setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */     
/* 255 */     setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", wsuId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 265 */     setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/*     */ 
/*     */ 
/*     */     
/* 269 */     setAttributeNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11:TokenType", tokenType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenType() {
/* 279 */     return getAttributeNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 286 */     return SecurityHeaderBlockImpl.fromSoapElement(element, SecurityTokenReference.class);
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
/*     */   private ReferenceElement getReferenceElementfromSoapElement(SOAPElement element, boolean isBSP) throws XWSSecurityException {
/* 298 */     String name = element.getLocalName();
/* 299 */     if (name.equals("KeyIdentifier"))
/* 300 */       return (ReferenceElement)getKeyIdentifier(element, isBSP); 
/* 301 */     if (name.equals("Reference"))
/* 302 */       return (ReferenceElement)new DirectReference(element, isBSP); 
/* 303 */     if (name.equals("X509Data"))
/* 304 */       return (ReferenceElement)new X509IssuerSerial(element); 
/* 305 */     if (isBSP && name.equals("KeyName")) {
/* 306 */       throw new XWSSecurityException("Violation of BSP R3027: A SECURITY_TOKEN_REFERENCE MUST NOT use a Key Name to reference a SECURITY_TOKEN. KeyName is not supported");
/*     */     }
/*     */ 
/*     */     
/* 310 */     log.log(Level.SEVERE, "WSS0335.unsupported.referencetype");
/* 311 */     XWSSecurityException xwsse = new XWSSecurityException(element.getTagName() + " key reference type is not supported");
/*     */ 
/*     */ 
/*     */     
/* 315 */     throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KeyIdentifier getKeyIdentifier(SOAPElement element, boolean isBSP) throws XWSSecurityException {
/* 325 */     String keyIdValueType = element.getAttribute("ValueType");
/* 326 */     if (isBSP && keyIdValueType.length() < 1) {
/* 327 */       throw new XWSSecurityException("Voilation of BSP R3054 : A wsse:KeyIdentifier element in a SECURITY_TOKEN_REFERENCE MUST specify a ValueType attribute");
/*     */     }
/*     */ 
/*     */     
/* 331 */     String keyIdEncodingType = element.getAttribute("EncodingType");
/* 332 */     if (isBSP && keyIdEncodingType.length() < 1) {
/* 333 */       throw new XWSSecurityException("Voilation of BSP R3070 : A wsse:KeyIdentifier element in a SECURITY_TOKEN_REFERENCE MUST specify an EncodingType attribute. ");
/*     */     }
/*     */ 
/*     */     
/* 337 */     if (isBSP && !keyIdEncodingType.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary")) {
/* 338 */       throw new XWSSecurityException("Voilation of BSP R3071 : An EncodingType attribute on a wsse:KeyIdentifier element in a SECURITY_TOKEN_REFERENCE MUST have a value of http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/*     */     }
/*     */ 
/*     */     
/* 342 */     if (keyIdValueType.equals("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID") || keyIdValueType.equals("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID"))
/*     */     {
/* 344 */       return (KeyIdentifier)new SamlKeyIdentifier(element); } 
/* 345 */     if (keyIdValueType.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier") || keyIdValueType.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3SubjectKeyIdentifier"))
/*     */     {
/* 347 */       return (KeyIdentifier)new X509SubjectKeyIdentifier(element); } 
/* 348 */     if (keyIdValueType.equals("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1"))
/*     */     {
/* 350 */       return (KeyIdentifier)new X509ThumbPrintIdentifier(element); } 
/* 351 */     if (keyIdValueType.equals("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1"))
/*     */     {
/* 353 */       return (KeyIdentifier)new EncryptedKeySHA1Identifier(element);
/*     */     }
/* 355 */     log.log(Level.SEVERE, "WSS0334.unsupported.keyidentifier");
/* 356 */     throw new XWSSecurityException("Unsupported KeyIdentifier Reference Type encountered");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getAny() {
/* 362 */     return null;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/* 366 */     setWsuId(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 371 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getTokenValue() {
/*     */     try {
/* 377 */       return getAsSoapElement();
/* 378 */     } catch (Exception ex) {
/* 379 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\SecurityTokenReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.ParserConfigurationException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SamlAssertionHeaderBlock
/*     */   extends SecurityHeaderBlockImpl
/*     */   implements SecurityToken
/*     */ {
/*  88 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
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
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 102 */     return SecurityHeaderBlockImpl.fromSoapElement(element, SamlAssertionHeaderBlock.class);
/*     */   }
/*     */ 
/*     */   
/* 106 */   private Document contextDocument_ = null;
/* 107 */   private Element delegateAssertion_ = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SamlAssertionHeaderBlock(Element assertion, Document doc) throws XWSSecurityException {
/* 118 */     if (null != assertion) {
/* 119 */       this.delegateAssertion_ = assertion;
/* 120 */       this.contextDocument_ = doc;
/*     */     } else {
/* 122 */       throw new XWSSecurityException("Assertion may not be null.");
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
/*     */   public SamlAssertionHeaderBlock(SOAPElement element) throws XWSSecurityException {
/* 136 */     this.contextDocument_ = element.getOwnerDocument();
/*     */     
/* 138 */     this.delegateAssertion_ = element;
/*     */ 
/*     */     
/* 141 */     setSOAPElement(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 151 */     if (this.delegateElement != null) {
/* 152 */       return this.delegateElement;
/*     */     }
/*     */     
/* 155 */     if (null == this.contextDocument_) {
/*     */       try {
/* 157 */         this.contextDocument_ = XMLUtil.newDocument();
/* 158 */       } catch (ParserConfigurationException e) {
/* 159 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 164 */       SOAPElement se = (SOAPElement)this.contextDocument_.importNode(this.delegateAssertion_, true);
/* 165 */       setSOAPElement(se);
/*     */     }
/* 167 */     catch (Exception e) {
/* 168 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 171 */     return super.getAsSoapElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getContextDocument() {
/* 179 */     return this.contextDocument_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getDelegateAssertion() {
/* 187 */     return this.delegateAssertion_;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\SamlAssertionHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
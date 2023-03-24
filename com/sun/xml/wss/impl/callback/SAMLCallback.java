/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import com.sun.xml.wss.saml.AuthorityBinding;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public class SAMLCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   Element assertion;
/*     */   Element authorityBinding;
/*     */   AuthorityBinding authorityInfo;
/*     */   XMLStreamReader assertionStream;
/*  55 */   String confirmation = null;
/*  56 */   String version = null;
/*  57 */   String assertionId = null;
/*     */   
/*     */   public static final String SV_ASSERTION_TYPE = "SV-Assertion";
/*     */   
/*     */   public static final String HOK_ASSERTION_TYPE = "HOK-Assertion";
/*     */   
/*     */   public static final String V10_ASSERTION = "SAML10Assertion";
/*     */   
/*     */   public static final String V11_ASSERTION = "SAML11Assertion";
/*     */   public static final String V20_ASSERTION = "SAML20Assertion";
/*     */   
/*     */   public void setAssertionElement(Element samlAssertion) {
/*  69 */     this.assertion = samlAssertion;
/*     */   }
/*     */   
/*     */   public void setAssertionReader(XMLStreamReader samlAssertion) {
/*  73 */     this.assertionStream = samlAssertion;
/*     */   }
/*     */   
/*     */   public Element getAssertionElement() {
/*  77 */     return this.assertion;
/*     */   }
/*     */   
/*     */   public XMLStreamReader getAssertionReader() {
/*  81 */     return this.assertionStream;
/*     */   }
/*     */   
/*     */   public void setAuthorityBindingElement(Element authority) {
/*  85 */     this.authorityBinding = authority;
/*     */   }
/*     */   
/*     */   public Element getAuthorityBindingElement() {
/*  89 */     return this.authorityBinding;
/*     */   }
/*     */   
/*     */   public AuthorityBinding getAuthorityBinding() {
/*  93 */     return this.authorityInfo;
/*     */   }
/*     */   
/*     */   public void setAuthorityBinding(AuthorityBinding auth) {
/*  97 */     this.authorityInfo = auth;
/*     */   }
/*     */   
/*     */   public void setConfirmationMethod(String meth) {
/* 101 */     this.confirmation = meth;
/*     */   }
/*     */   
/*     */   public String getConfirmationMethod() {
/* 105 */     return this.confirmation;
/*     */   }
/*     */   
/*     */   public String getSAMLVersion() {
/* 109 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setSAMLVersion(String ver) {
/* 113 */     this.version = ver;
/*     */   }
/*     */   
/*     */   public void setAssertionId(String id) {
/* 117 */     this.assertionId = id;
/*     */   }
/*     */   
/*     */   public String getAssertionId() {
/* 121 */     return this.assertionId;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\SAMLCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
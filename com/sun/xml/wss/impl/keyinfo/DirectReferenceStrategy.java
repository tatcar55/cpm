/*     */ package com.sun.xml.wss.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.reference.DirectReference;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class DirectReferenceStrategy
/*     */   extends KeyInfoStrategy
/*     */ {
/*  65 */   X509Certificate cert = null;
/*     */   
/*  67 */   String alias = null;
/*     */   
/*     */   boolean forSigning;
/*  70 */   String samlAssertionId = null;
/*     */   
/*  72 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectReferenceStrategy() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectReferenceStrategy(String samlAssertionId) {
/*  81 */     this.samlAssertionId = samlAssertionId;
/*  82 */     this.cert = null;
/*  83 */     this.alias = null;
/*  84 */     this.forSigning = false;
/*     */   }
/*     */   
/*     */   public DirectReferenceStrategy(String alias, boolean forSigning) {
/*  88 */     this.alias = alias;
/*  89 */     this.forSigning = forSigning;
/*  90 */     this.samlAssertionId = null;
/*  91 */     this.cert = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(SecurityTokenReference tokenRef, SecurableSoapMessage secureMsg) throws XWSSecurityException {
/*  97 */     DirectReference ref = getDirectReference(secureMsg, null, null);
/*  98 */     tokenRef.setReference((ReferenceElement)ref);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(KeyInfoHeaderBlock keyInfo, SecurableSoapMessage secureMsg, String x509TokenId) throws XWSSecurityException {
/* 108 */     Document ownerDoc = keyInfo.getOwnerDocument();
/* 109 */     SecurityTokenReference tokenRef = new SecurityTokenReference(ownerDoc);
/* 110 */     DirectReference ref = getDirectReference(secureMsg, x509TokenId, null);
/* 111 */     tokenRef.setReference((ReferenceElement)ref);
/* 112 */     keyInfo.addSecurityTokenReference(tokenRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(KeyInfoHeaderBlock keyInfo, SecurableSoapMessage secureMsg, String x509TokenId, String valueType) throws XWSSecurityException {
/* 121 */     Document ownerDoc = keyInfo.getOwnerDocument();
/* 122 */     SecurityTokenReference tokenRef = new SecurityTokenReference(ownerDoc);
/* 123 */     DirectReference ref = getDirectReference(secureMsg, x509TokenId, valueType);
/* 124 */     tokenRef.setReference((ReferenceElement)ref);
/* 125 */     keyInfo.addSecurityTokenReference(tokenRef);
/*     */   }
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/* 129 */     this.cert = cert;
/*     */   }
/*     */   
/*     */   public String getAlias() {
/* 133 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DirectReference getDirectReference(SecurableSoapMessage secureMsg, String x509TokenId, String valueType) throws XWSSecurityException {
/* 141 */     DirectReference ref = new DirectReference();
/*     */     
/* 143 */     if (this.samlAssertionId != null) {
/* 144 */       String uri = "#" + this.samlAssertionId;
/* 145 */       ref.setURI(uri);
/* 146 */       ref.setValueType("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-saml-token-profile-1.0#SAMLAssertion-1.1");
/*     */     }
/*     */     else {
/*     */       
/* 150 */       if (this.cert == null) {
/* 151 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0185_FILTERPARAMETER_NOT_SET("subjectkeyidentifier"), new Object[] { "subjectkeyidentifier" });
/*     */ 
/*     */ 
/*     */         
/* 155 */         throw new XWSSecurityException("No certificate specified and no default found.");
/*     */       } 
/*     */       
/* 158 */       if (x509TokenId == null) {
/* 159 */         throw new XWSSecurityException("WSU ID is null");
/*     */       }
/* 161 */       String uri = "#" + x509TokenId;
/* 162 */       ref.setURI(uri);
/* 163 */       if (valueType == null || valueType.equals("")) {
/* 164 */         valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
/*     */       }
/* 166 */       ref.setValueType(valueType);
/*     */     } 
/* 168 */     return ref;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\keyinfo\DirectReferenceStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
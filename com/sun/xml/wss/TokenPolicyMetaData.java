/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.KeyBindingBase;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenPolicyMetaData
/*     */ {
/*     */   public static final String TOKEN_POLICY = "token.policy";
/*  68 */   private AuthenticationTokenPolicy tokenPolicy = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenPolicyMetaData(Map runtimeProperties) {
/*  75 */     this.tokenPolicy = (AuthenticationTokenPolicy)runtimeProperties.get("token.policy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssuer() {
/*  82 */     if (this.tokenPolicy == null) {
/*  83 */       return null;
/*     */     }
/*  85 */     KeyBindingBase kb = (KeyBindingBase)this.tokenPolicy.getFeatureBinding();
/*  86 */     return kb.getIssuer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getClaims() throws XWSSecurityException {
/*  94 */     if (this.tokenPolicy == null) {
/*  95 */       return null;
/*     */     }
/*  97 */     KeyBindingBase kb = (KeyBindingBase)this.tokenPolicy.getFeatureBinding();
/*  98 */     Element claimsElement = null;
/*  99 */     byte[] claimBytes = kb.getClaims();
/* 100 */     if (claimBytes != null) {
/*     */       try {
/* 102 */         DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 103 */         dbf.setNamespaceAware(true);
/* 104 */         DocumentBuilder db = dbf.newDocumentBuilder();
/* 105 */         Document doc = db.parse(new ByteArrayInputStream(claimBytes));
/* 106 */         claimsElement = (Element)doc.getElementsByTagNameNS("*", "Claims").item(0);
/* 107 */       } catch (SAXException ex) {
/* 108 */         Logger.getLogger(TokenPolicyMetaData.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 109 */         throw new XWSSecurityException(ex);
/* 110 */       } catch (IOException ex) {
/* 111 */         Logger.getLogger(TokenPolicyMetaData.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 112 */         throw new XWSSecurityException(ex);
/* 113 */       } catch (ParserConfigurationException ex) {
/* 114 */         Logger.getLogger(TokenPolicyMetaData.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 115 */         throw new XWSSecurityException(ex);
/*     */       } 
/*     */     }
/* 118 */     return claimsElement;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\TokenPolicyMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
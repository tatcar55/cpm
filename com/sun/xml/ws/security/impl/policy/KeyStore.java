/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.KeyStore;
/*     */ import java.util.Collection;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyStore
/*     */   extends PolicyAssertion
/*     */   implements KeyStore
/*     */ {
/*  56 */   private static QName loc = new QName("location");
/*  57 */   private static QName type = new QName("type");
/*  58 */   private static QName passwd = new QName("storepass");
/*  59 */   private static QName alias = new QName("alias");
/*  60 */   private static QName keypass = new QName("keypass");
/*  61 */   private static QName aliasSelector = new QName("aliasSelector");
/*  62 */   private static QName callbackHandler = new QName("callbackHandler");
/*     */   
/*  64 */   private char[] password = null;
/*  65 */   private static QName keyStoreLoginModuleConfigName = new QName("keystoreloginmoduleconfig");
/*     */ 
/*     */   
/*     */   public KeyStore() {}
/*     */   
/*     */   public KeyStore(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  71 */     super(name, nestedAssertions, nestedAlternative);
/*     */   }
/*     */   public String getLocation() {
/*  74 */     return getAttributeValue(loc);
/*     */   }
/*     */   
/*     */   public String getType() {
/*  78 */     return getAttributeValue(type);
/*     */   }
/*     */   
/*     */   public char[] getPassword() {
/*  82 */     if (this.password == null) {
/*  83 */       String val = getAttributeValue(passwd);
/*  84 */       if (val != null) {
/*  85 */         this.password = val.toCharArray();
/*     */       }
/*     */     } 
/*  88 */     return this.password;
/*     */   }
/*     */   
/*     */   public String getAlias() {
/*  92 */     return getAttributeValue(alias);
/*     */   }
/*     */   
/*     */   public String getKeyPassword() {
/*  96 */     return getAttributeValue(keypass);
/*     */   }
/*     */   
/*     */   public String getAliasSelectorClassName() {
/* 100 */     return getAttributeValue(aliasSelector);
/*     */   }
/*     */   
/*     */   public String getKeyStoreCallbackHandler() {
/* 104 */     return getAttributeValue(callbackHandler);
/*     */   }
/*     */   
/*     */   public String getKeyStoreLoginModuleConfigName() {
/* 108 */     return getAttributeValue(keyStoreLoginModuleConfigName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\KeyStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
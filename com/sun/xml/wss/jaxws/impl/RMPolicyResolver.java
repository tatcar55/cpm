/*     */ package com.sun.xml.wss.jaxws.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.policy.ModelTranslator;
/*     */ import com.sun.xml.ws.api.policy.ModelUnmarshaller;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.ws.rx.mc.api.McProtocolVersion;
/*     */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RMPolicyResolver
/*     */ {
/*     */   SecurityPolicyVersion spVersion;
/*     */   RmProtocolVersion rmVersion;
/*     */   McProtocolVersion mcVersion;
/*     */   boolean encrypt = false;
/*     */   
/*     */   public RMPolicyResolver() {
/*  69 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*  70 */     this.rmVersion = RmProtocolVersion.WSRM200502;
/*  71 */     this.mcVersion = McProtocolVersion.WSMC200702;
/*     */   }
/*     */   
/*     */   public RMPolicyResolver(SecurityPolicyVersion spVersion, RmProtocolVersion rmVersion) {
/*  75 */     this.spVersion = spVersion;
/*  76 */     this.rmVersion = rmVersion;
/*  77 */     this.mcVersion = McProtocolVersion.WSMC200702;
/*     */   }
/*     */   
/*     */   public RMPolicyResolver(SecurityPolicyVersion spVersion, RmProtocolVersion rmVersion, McProtocolVersion mcVersion, boolean encrypt) {
/*  81 */     this.spVersion = spVersion;
/*  82 */     this.rmVersion = rmVersion;
/*  83 */     this.mcVersion = mcVersion;
/*  84 */     this.encrypt = encrypt;
/*     */   }
/*     */   
/*     */   public Policy getOperationLevelPolicy() throws PolicyException {
/*     */     PolicySourceModel model;
/*     */     try {
/*  90 */       String rmMessagePolicy = this.encrypt ? "rm-msglevel-policy-encrypt.xml" : "rm-msglevel-policy.xml";
/*  91 */       if (SecurityPolicyVersion.SECURITYPOLICY12NS == this.spVersion && RmProtocolVersion.WSRM200502 == this.rmVersion) {
/*  92 */         rmMessagePolicy = "rm-msglevel-policy-sp12.xml";
/*  93 */       } else if (SecurityPolicyVersion.SECURITYPOLICY12NS == this.spVersion && RmProtocolVersion.WSRM200702 == this.rmVersion) {
/*  94 */         rmMessagePolicy = this.encrypt ? "rm-msglevel-policy-sx-encrypt.xml" : "rm-msglevel-policy-sx.xml";
/*  95 */       } else if (SecurityPolicyVersion.SECURITYPOLICY200507 == this.spVersion && RmProtocolVersion.WSRM200702 == this.rmVersion) {
/*  96 */         rmMessagePolicy = "rm-msglevel-policy-sx-sp10.xml";
/*     */       } 
/*  98 */       model = unmarshalPolicy("com/sun/xml/ws/security/impl/policyconv/" + rmMessagePolicy);
/*  99 */     } catch (IOException ex) {
/* 100 */       throw new PolicyException(ex);
/*     */     } 
/* 102 */     Policy mbp = ModelTranslator.getTranslator().translate(model);
/* 103 */     return mbp;
/*     */   }
/*     */   
/*     */   private PolicySourceModel unmarshalPolicy(String resource) throws PolicyException, IOException {
/* 107 */     Reader reader = getResourceReader(resource);
/* 108 */     PolicySourceModel model = ModelUnmarshaller.getUnmarshaller().unmarshalModel(reader);
/* 109 */     reader.close();
/* 110 */     return model;
/*     */   }
/*     */   
/*     */   private Reader getResourceReader(String resourceName) {
/* 114 */     return new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\RMPolicyResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
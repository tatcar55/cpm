/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.HashMap;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyAlternativeHolder
/*     */ {
/*  59 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*  63 */   private HashMap<WSDLBoundOperation, SecurityPolicyHolder> outMessagePolicyMap = null;
/*  64 */   private HashMap<WSDLBoundOperation, SecurityPolicyHolder> inMessagePolicyMap = null;
/*  65 */   private HashMap<String, SecurityPolicyHolder> outProtocolPM = null;
/*  66 */   private HashMap<String, SecurityPolicyHolder> inProtocolPM = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   protected Policy bpMSP = null;
/*     */   
/*     */   protected SecurityPolicyVersion spVersion;
/*     */   private String uuid;
/*     */   
/*     */   public PolicyAlternativeHolder(AssertionSet assertions, SecurityPolicyVersion sv, Policy bpMSP) {
/*  81 */     this.spVersion = sv;
/*  82 */     this.bpMSP = bpMSP;
/*  83 */     this.uuid = UUID.randomUUID().toString();
/*  84 */     this.inMessagePolicyMap = new HashMap<WSDLBoundOperation, SecurityPolicyHolder>();
/*  85 */     this.outMessagePolicyMap = new HashMap<WSDLBoundOperation, SecurityPolicyHolder>();
/*  86 */     this.inProtocolPM = new HashMap<String, SecurityPolicyHolder>();
/*  87 */     this.outProtocolPM = new HashMap<String, SecurityPolicyHolder>();
/*     */   }
/*     */   
/*     */   public void putToOutMessagePolicyMap(WSDLBoundOperation op, SecurityPolicyHolder sh) {
/*  91 */     this.outMessagePolicyMap.put(op, sh);
/*     */   }
/*     */   
/*     */   public SecurityPolicyHolder getFromOutMessagePolicyMap(WSDLBoundOperation op) {
/*  95 */     return this.outMessagePolicyMap.get(op);
/*     */   }
/*     */   
/*     */   public void putToInMessagePolicyMap(WSDLBoundOperation op, SecurityPolicyHolder sh) {
/*  99 */     this.inMessagePolicyMap.put(op, sh);
/*     */   }
/*     */   
/*     */   public SecurityPolicyHolder getFromInMessagePolicyMap(WSDLBoundOperation op) {
/* 103 */     return this.inMessagePolicyMap.get(op);
/*     */   }
/*     */   
/*     */   public void putToOutProtocolPolicyMap(String protocol, SecurityPolicyHolder sh) {
/* 107 */     this.outProtocolPM.put(protocol, sh);
/*     */   }
/*     */   
/*     */   public SecurityPolicyHolder getFromOutProtocolPolicyMap(String protocol) {
/* 111 */     return this.outProtocolPM.get(protocol);
/*     */   }
/*     */   
/*     */   public void putToInProtocolPolicyMap(String protocol, SecurityPolicyHolder sh) {
/* 115 */     this.inProtocolPM.put(protocol, sh);
/*     */   }
/*     */   
/*     */   public SecurityPolicyHolder getFromInProtocolPolicyMap(String protocol) {
/* 119 */     return this.inProtocolPM.get(protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 127 */     return this.uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<WSDLBoundOperation, SecurityPolicyHolder> getOutMessagePolicyMap() {
/* 134 */     return this.outMessagePolicyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<WSDLBoundOperation, SecurityPolicyHolder> getInMessagePolicyMap() {
/* 141 */     return this.inMessagePolicyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<String, SecurityPolicyHolder> getOutProtocolPM() {
/* 148 */     return this.outProtocolPM;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<String, SecurityPolicyHolder> getInProtocolPM() {
/* 155 */     return this.inProtocolPM;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\PolicyAlternativeHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
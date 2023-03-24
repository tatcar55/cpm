/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityPolicyHolder
/*     */ {
/*  62 */   private MessagePolicy mp = null;
/*  63 */   private List<PolicyAssertion> scList = null;
/*  64 */   private List<PolicyAssertion> issuedTokenList = null;
/*  65 */   private List<PolicyAssertion> kerberosTokenList = null;
/*  66 */   private static final List<PolicyAssertion> EMPTY_LIST = Collections.emptyList();
/*  67 */   private AlgorithmSuite suite = null;
/*  68 */   private HashMap<WSDLFault, SecurityPolicyHolder> faultFPMap = null;
/*     */ 
/*     */   
/*     */   private HashMap<String, Set<PolicyAssertion>> configAssertions;
/*     */ 
/*     */   
/*     */   private boolean isIssuedTokenAsEncryptedSupportingToken = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessagePolicy(MessagePolicy mp) {
/*  79 */     this.mp = mp;
/*     */   }
/*     */   
/*     */   public MessagePolicy getMessagePolicy() {
/*  83 */     return this.mp;
/*     */   }
/*     */   
/*     */   public void addSecureConversationToken(PolicyAssertion pa) {
/*  87 */     if (this.scList == null) {
/*  88 */       this.scList = new ArrayList<PolicyAssertion>();
/*     */     }
/*  90 */     this.scList.add(pa);
/*     */   }
/*     */   
/*     */   public List<PolicyAssertion> getSecureConversationTokens() {
/*  94 */     return (this.scList == null) ? EMPTY_LIST : this.scList;
/*     */   }
/*     */   
/*     */   public void addKerberosToken(PolicyAssertion pa) {
/*  98 */     if (this.kerberosTokenList == null) {
/*  99 */       this.kerberosTokenList = new ArrayList<PolicyAssertion>();
/*     */     }
/* 101 */     this.kerberosTokenList.add(pa);
/*     */   }
/*     */   
/*     */   public List<PolicyAssertion> getKerberosTokens() {
/* 105 */     return (this.kerberosTokenList == null) ? EMPTY_LIST : this.kerberosTokenList;
/*     */   }
/*     */   
/*     */   public void addIssuedToken(PolicyAssertion pa) {
/* 109 */     if (this.issuedTokenList == null) {
/* 110 */       this.issuedTokenList = new ArrayList<PolicyAssertion>();
/*     */     }
/* 112 */     this.issuedTokenList.add(pa);
/*     */   }
/*     */   
/*     */   public void addIssuedTokens(List<PolicyAssertion> list) {
/* 116 */     if (this.issuedTokenList == null) {
/* 117 */       this.issuedTokenList = list;
/*     */     } else {
/* 119 */       this.issuedTokenList.addAll(list);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<PolicyAssertion> getIssuedTokens() {
/* 124 */     return (this.issuedTokenList == null) ? EMPTY_LIST : this.issuedTokenList;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getBindingLevelAlgSuite() {
/* 128 */     return this.suite;
/*     */   }
/*     */   
/*     */   public void setBindingLevelAlgSuite(AlgorithmSuite suite) {
/* 132 */     this.suite = suite;
/*     */   }
/*     */   
/*     */   public boolean isIssuedTokenAsEncryptedSupportingToken() {
/* 136 */     return this.isIssuedTokenAsEncryptedSupportingToken;
/*     */   }
/*     */   
/*     */   public void isIssuedTokenAsEncryptedSupportingToken(boolean isIssuedTokenAsEncryptedSupportingToken) {
/* 140 */     this.isIssuedTokenAsEncryptedSupportingToken = isIssuedTokenAsEncryptedSupportingToken;
/*     */   }
/*     */   
/*     */   public void addFaultPolicy(WSDLFault fault, SecurityPolicyHolder policy) {
/* 144 */     if (this.faultFPMap == null) {
/* 145 */       this.faultFPMap = new HashMap<WSDLFault, SecurityPolicyHolder>();
/*     */     }
/* 147 */     this.faultFPMap.put(fault, policy);
/*     */   }
/*     */   
/*     */   public SecurityPolicyHolder getFaultPolicy(WSDLFault fault) {
/* 151 */     if (this.faultFPMap == null) {
/* 152 */       return null;
/*     */     }
/* 154 */     return this.faultFPMap.get(fault);
/*     */   }
/*     */   
/*     */   public void addConfigAssertions(PolicyAssertion assertion) {
/* 158 */     if (this.configAssertions == null) {
/* 159 */       this.configAssertions = new HashMap<String, Set<PolicyAssertion>>();
/*     */     }
/* 161 */     Set<PolicyAssertion> assertions = this.configAssertions.get(assertion.getName().getNamespaceURI());
/* 162 */     if (assertions == null) {
/* 163 */       assertions = new HashSet<PolicyAssertion>();
/* 164 */       this.configAssertions.put(assertion.getName().getNamespaceURI(), assertions);
/*     */     } 
/* 166 */     assertions.add(assertion);
/*     */   }
/*     */   
/*     */   public Set<PolicyAssertion> getConfigAssertions(String namespaceuri) {
/* 170 */     if (this.configAssertions == null) {
/* 171 */       return null;
/*     */     }
/* 173 */     return this.configAssertions.get(namespaceuri);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SecurityPolicyHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
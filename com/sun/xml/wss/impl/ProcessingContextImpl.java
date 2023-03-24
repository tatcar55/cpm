/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*     */ import com.sun.xml.ws.security.secconv.WSSCVersion;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ public class ProcessingContextImpl
/*     */   extends ProcessingContext
/*     */ {
/*  70 */   protected WSSAssertion wssAssertion = null;
/*     */   
/*  72 */   protected Hashtable issuedTokenContextMap = null;
/*     */   
/*  74 */   protected Hashtable scPolicyIDtoSctIdMap = null;
/*     */   
/*  76 */   protected AlgorithmSuite bootAlgoSuite = null;
/*     */ 
/*     */   
/*     */   private WSSCVersion wsscVer;
/*     */   
/*     */   private WSTrustVersion wsTrustVer;
/*     */   
/*     */   private boolean addressingEnabled;
/*     */   
/*     */   private static final String TRUST_CLIENT_CREDENTIALS = "TrustClientCredentialHolder";
/*     */   
/*     */   private static final String ISSUED_SAML_TOKEN = "IssuedSAMLToken";
/*     */   
/*     */   private static final String SAMLID_VS_KEY_CACHE = "SAMLID_VS_KEY_CACHE";
/*     */   
/*     */   private static final String INCOMING_ASSERTION_ID = "Incoming_Saml_Assertion_Id";
/*     */   
/*  93 */   protected AlgorithmSuite algoSuite = null;
/*     */   
/*     */   protected boolean policyHasIssuedToken = false;
/*     */   
/*  97 */   protected IssuedTokenContext secureConversationContext = null;
/*  98 */   protected IssuedTokenContext trustContext = null;
/*     */   
/* 100 */   protected MessagePolicy inferredSecurityPolicy = new MessagePolicy();
/*     */   
/* 102 */   protected List signConfirmIds = new ArrayList();
/*     */ 
/*     */   
/*     */   private boolean isTrustMsg = false;
/*     */ 
/*     */   
/*     */   private boolean isSamlSignatureKey = false;
/*     */   
/* 110 */   private String securityPolicyVersion = null;
/*     */   
/* 112 */   private String wscInstance = null;
/*     */   
/* 114 */   private long timestampTimeout = 0L;
/*     */ 
/*     */   
/*     */   private int iterationsForPDK;
/*     */ 
/*     */   
/*     */   private String action;
/*     */ 
/*     */   
/*     */   public ProcessingContextImpl() {}
/*     */ 
/*     */   
/*     */   public ProcessingContextImpl(Map invocationProps) {
/* 127 */     this.properties = invocationProps;
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
/*     */   public ProcessingContextImpl(StaticPolicyContext context, SecurityPolicy securityPolicy, SOAPMessage message) throws XWSSecurityException {
/* 141 */     super(context, securityPolicy, message);
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
/*     */   public void copy(ProcessingContext ctxx1, ProcessingContext ctxx2) throws XWSSecurityException {
/* 153 */     if (ctxx2 instanceof ProcessingContextImpl) {
/* 154 */       ProcessingContextImpl ctx1 = (ProcessingContextImpl)ctxx1;
/* 155 */       ProcessingContextImpl ctx2 = (ProcessingContextImpl)ctxx2;
/* 156 */       super.copy(ctx1, ctx2);
/* 157 */       ctx1.setIssuedTokenContextMap(ctx2.getIssuedTokenContextMap());
/*     */       
/* 159 */       ctx1.setAlgorithmSuite(ctx2.getAlgorithmSuite());
/* 160 */       ctx1.setSecureConversationContext(ctx2.getSecureConversationContext());
/* 161 */       ctx1.setWSSAssertion(ctx2.getWSSAssertion());
/* 162 */       ctx1.inferredSecurityPolicy = ctx2.getInferredSecurityPolicy();
/*     */       
/* 164 */       ctx1.isTrustMessage(ctx2.isTrustMessage());
/* 165 */       ctx1.hasIssuedToken(ctx2.hasIssuedToken());
/* 166 */       ctx1.setTimestampTimeout(ctx2.getTimestampTimeout());
/* 167 */       ctx1.setWSCInstance(ctx2.getWSCInstance());
/* 168 */       ctx1.setSCPolicyIDtoSctIdMap(ctx2.getSCPolicyIDtoSctIdMap());
/* 169 */       ctx1.setAction(ctx2.getAction());
/*     */       
/* 171 */       ctx1.setBootstrapAlgoSuite(ctx2.getBootstrapAlgoSuite());
/* 172 */       ctx1.setWsscVer(ctx2.getWsscVer());
/* 173 */       ctx1.setWsTrustVer(ctx2.getWsTrustVer());
/* 174 */       ctx1.setAddressingEnabled(ctx2.isAddressingEnabled());
/*     */     } else {
/*     */       
/* 177 */       super.copy(ctxx1, ctxx2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setIssuedTokenContextMap(Hashtable issuedTokenContextMap) {
/* 182 */     this.issuedTokenContextMap = issuedTokenContextMap;
/*     */   }
/*     */   
/*     */   public Hashtable getIssuedTokenContextMap() {
/* 186 */     return this.issuedTokenContextMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurableSoapMessage getSecurableSoapMessage() {
/* 193 */     return this.secureMessage;
/*     */   }
/*     */   
/*     */   public IssuedTokenContext getIssuedTokenContext(String policyID) {
/* 197 */     if (this.issuedTokenContextMap == null)
/*     */     {
/* 199 */       return null;
/*     */     }
/* 201 */     return (IssuedTokenContext)this.issuedTokenContextMap.get(policyID);
/*     */   }
/*     */   
/*     */   public void setIssuedTokenContext(IssuedTokenContext issuedTokenContext, String policyID) {
/* 205 */     if (this.issuedTokenContextMap == null)
/*     */     {
/*     */       
/* 208 */       this.issuedTokenContextMap = new Hashtable<Object, Object>();
/*     */     }
/* 210 */     this.issuedTokenContextMap.put(policyID, issuedTokenContext);
/*     */   }
/*     */   
/*     */   public KerberosContext getKerberosContext() {
/* 214 */     KerberosContext krbContext = (KerberosContext)getExtraneousProperty("KerberosContext");
/* 215 */     return krbContext;
/*     */   }
/*     */   
/*     */   public void setKerberosContext(KerberosContext kerberosContext) {
/* 219 */     setExtraneousProperty("KerberosContext", kerberosContext);
/*     */   }
/*     */   
/*     */   public void setTrustCredentialHolder(IssuedTokenContext ctx) {
/* 223 */     getExtraneousProperties().put("TrustClientCredentialHolder", ctx);
/*     */   }
/*     */   
/*     */   public IssuedTokenContext getTrustCredentialHolder() {
/* 227 */     return (IssuedTokenContext)getExtraneousProperties().get("TrustClientCredentialHolder");
/*     */   }
/*     */   
/*     */   public Element getIssuedSAMLToken() {
/* 231 */     return (Element)getExtraneousProperties().get("IssuedSAMLToken");
/*     */   }
/*     */   
/*     */   public void setIssuedSAMLToken(Element elem) {
/* 235 */     getExtraneousProperties().put("IssuedSAMLToken", elem);
/*     */   }
/*     */   
/*     */   public void setIncomingAssertionId(String assid) {
/* 239 */     getExtraneousProperties().put("Incoming_Saml_Assertion_Id", assid);
/*     */   }
/*     */   
/*     */   public String getIncomingAssertionId() {
/* 243 */     return (String)getExtraneousProperties().get("Incoming_Saml_Assertion_Id");
/*     */   }
/*     */   public void setSecureConversationContext(IssuedTokenContext ctx) {
/* 246 */     this.secureConversationContext = ctx;
/*     */   }
/*     */   
/*     */   public IssuedTokenContext getSecureConversationContext() {
/* 250 */     return this.secureConversationContext;
/*     */   }
/*     */   
/*     */   public void setTrustContext(IssuedTokenContext ctx) {
/* 254 */     this.trustContext = ctx;
/*     */   }
/*     */   
/*     */   public IssuedTokenContext getTrustContext() {
/* 258 */     return this.trustContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmSuite getAlgorithmSuite() {
/* 264 */     return this.algoSuite;
/*     */   }
/*     */   
/*     */   public void setAlgorithmSuite(AlgorithmSuite suite) {
/* 268 */     this.algoSuite = suite;
/*     */   }
/*     */   
/*     */   public void setWSSAssertion(WSSAssertion wssAssertion) {
/* 272 */     this.wssAssertion = wssAssertion;
/*     */   }
/*     */   
/*     */   public WSSAssertion getWSSAssertion() {
/* 276 */     return this.wssAssertion;
/*     */   }
/*     */   
/*     */   public MessagePolicy getInferredSecurityPolicy() {
/* 280 */     return this.inferredSecurityPolicy;
/*     */   }
/*     */   
/*     */   public HashMap getSamlIdVSKeyCache() {
/* 284 */     if (getExtraneousProperties().get("SAMLID_VS_KEY_CACHE") == null) {
/* 285 */       getExtraneousProperties().put("SAMLID_VS_KEY_CACHE", new HashMap<Object, Object>());
/*     */     }
/* 287 */     return (HashMap)getExtraneousProperties().get("SAMLID_VS_KEY_CACHE");
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
/*     */   public void isTrustMessage(boolean isTrust) {
/* 299 */     this.isTrustMsg = isTrust;
/*     */   }
/*     */   
/*     */   public boolean isTrustMessage() {
/* 303 */     return this.isTrustMsg;
/*     */   }
/*     */   
/*     */   public void isSamlSignatureKey(boolean value) {
/* 307 */     this.isSamlSignatureKey = value;
/*     */   }
/*     */   
/*     */   public boolean isSamlSignatureKey() {
/* 311 */     return this.isSamlSignatureKey;
/*     */   }
/*     */   
/*     */   public List getSignatureConfirmationIds() {
/* 315 */     return this.signConfirmIds;
/*     */   }
/*     */   
/*     */   public boolean hasIssuedToken() {
/* 319 */     return this.policyHasIssuedToken;
/*     */   }
/*     */   
/*     */   public void hasIssuedToken(boolean flag) {
/* 323 */     this.policyHasIssuedToken = flag;
/*     */   }
/*     */   
/*     */   public long getTimestampTimeout() {
/* 327 */     return this.timestampTimeout;
/*     */   }
/*     */   
/*     */   public void setTimestampTimeout(long timeout) {
/* 331 */     this.timestampTimeout = timeout;
/*     */   }
/*     */   
/*     */   public void setiterationsForPDK(int iterations) {
/* 335 */     this.iterationsForPDK = iterations;
/*     */   }
/*     */   
/*     */   public int getiterationsForPDK() {
/* 339 */     return this.iterationsForPDK;
/*     */   }
/*     */   
/*     */   public void setSecurityPolicyVersion(String secPolVersion) {
/* 343 */     this.securityPolicyVersion = secPolVersion;
/*     */   }
/*     */   
/*     */   public String getSecurityPolicyVersion() {
/* 347 */     return this.securityPolicyVersion;
/*     */   }
/*     */   
/*     */   public void setWSCInstance(String value) {
/* 351 */     this.wscInstance = value;
/*     */   }
/*     */   
/*     */   public String getWSCInstance() {
/* 355 */     return this.wscInstance;
/*     */   }
/*     */   
/*     */   public String getWSSCVersion(String nsUri) {
/* 359 */     if ("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy".equals(nsUri))
/* 360 */       return "http://schemas.xmlsoap.org/ws/2005/02/sc"; 
/* 361 */     if ("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702".equals(nsUri)) {
/* 362 */       return "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
/*     */     }
/* 364 */     return null;
/*     */   }
/*     */   
/*     */   public void setSCPolicyIDtoSctIdMap(Hashtable scPolicyIDtoSctIdMap) {
/* 368 */     this.scPolicyIDtoSctIdMap = scPolicyIDtoSctIdMap;
/*     */   }
/*     */   
/*     */   public Hashtable getSCPolicyIDtoSctIdMap() {
/* 372 */     return this.scPolicyIDtoSctIdMap;
/*     */   }
/*     */   
/*     */   public String getSCPolicyIDtoSctIdMap(String scPolicyID) {
/* 376 */     if (this.scPolicyIDtoSctIdMap == null) {
/* 377 */       return null;
/*     */     }
/* 379 */     return (String)this.scPolicyIDtoSctIdMap.get(scPolicyID);
/*     */   }
/*     */   
/*     */   public void setAction(String action) {
/* 383 */     this.action = action;
/*     */   }
/*     */   public String getAction() {
/* 386 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmSuite getBootstrapAlgoSuite() {
/* 393 */     return this.bootAlgoSuite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBootstrapAlgoSuite(AlgorithmSuite bootAlgoSuite) {
/* 400 */     this.bootAlgoSuite = bootAlgoSuite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSSCVersion getWsscVer() {
/* 407 */     return this.wsscVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWsscVer(WSSCVersion wsscVer) {
/* 414 */     this.wsscVer = wsscVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSTrustVersion getWsTrustVer() {
/* 421 */     return this.wsTrustVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAddressingEnabled() {
/* 428 */     return this.addressingEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWsTrustVer(WSTrustVersion wsTrustVer) {
/* 435 */     this.wsTrustVer = wsTrustVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAddressingEnabled(boolean addressingEnabled) {
/* 442 */     this.addressingEnabled = addressingEnabled;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\ProcessingContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
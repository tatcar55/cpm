/*     */ package com.sun.xml.ws.security.secconv.impl.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.security.secconv.client.SCTokenConfiguration;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust10;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust13;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ public class DefaultSCTokenConfiguration
/*     */   extends SCTokenConfiguration
/*     */ {
/*     */   private static final String SC_CLIENT_CONFIGURATION = "SCClientConfiguration";
/*     */   private static final String RENEW_EXPIRED_SCT = "renewExpiredSCT";
/*     */   private static final String REQUIRE_CANCEL_SCT = "requireCancelSCT";
/*     */   private static final String LIFETIME = "LifeTime";
/*     */   private static final String CONFIG_NAMESPACE = "";
/*  77 */   private Trust10 trust10 = null;
/*  78 */   private Trust13 trust13 = null;
/*  79 */   private SymmetricBinding symBinding = null;
/*  80 */   private int skl = 0;
/*     */   
/*     */   private boolean reqClientEntropy = true;
/*     */   private boolean checkTokenExpiry = true;
/*     */   private boolean clientOutboundMessage = true;
/*  85 */   private WSDLPort wsdlPort = null;
/*  86 */   private WSBinding wsBinding = null;
/*  87 */   private Tube clientSecurityTube = null;
/*  88 */   private Tube nextTube = null;
/*  89 */   private Packet packet = null;
/*  90 */   private AddressingVersion addVer = null;
/*  91 */   private Token scToken = null;
/*  92 */   private String tokenId = null;
/*     */   private boolean addRenewPolicy = true;
/*     */   
/*     */   public DefaultSCTokenConfiguration(String protocol) {
/*  96 */     this.protocol = protocol;
/*     */   }
/*     */   
/*     */   public DefaultSCTokenConfiguration(String protocol, boolean addRenewPolicy) {
/* 100 */     this(protocol);
/* 101 */     this.addRenewPolicy = addRenewPolicy;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSCTokenConfiguration(String protocol, SecureConversationToken scToken, WSDLPort wsdlPort, WSBinding binding, Tube securityTube, Packet packet, AddressingVersion addVer, PolicyAssertion localToken, Tube nextTube) {
/* 106 */     this.protocol = protocol;
/* 107 */     this.scToken = (Token)scToken;
/* 108 */     this.wsdlPort = wsdlPort;
/* 109 */     this.wsBinding = binding;
/* 110 */     this.clientSecurityTube = securityTube;
/* 111 */     this.packet = packet;
/* 112 */     this.addVer = addVer;
/* 113 */     this.tokenId = null;
/* 114 */     this.nextTube = nextTube;
/* 115 */     parseAssertions(scToken, localToken);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSCTokenConfiguration(String protocol, SecureConversationToken scToken, WSDLPort wsdlPort, WSBinding binding, Pipe securityPipe, Packet packet, AddressingVersion addVer, PolicyAssertion localToken) {
/* 120 */     this.protocol = protocol;
/* 121 */     this.scToken = (Token)scToken;
/* 122 */     this.wsdlPort = wsdlPort;
/* 123 */     this.wsBinding = binding;
/* 124 */     this.clientSecurityTube = null;
/* 125 */     this.packet = packet;
/* 126 */     this.addVer = addVer;
/* 127 */     this.tokenId = scToken.getTokenId();
/* 128 */     parseAssertions(scToken, localToken);
/*     */   }
/*     */   
/*     */   public DefaultSCTokenConfiguration(String protocol, String tokenId, boolean checkTokenExpiry) {
/* 132 */     super(protocol);
/* 133 */     this.tokenId = tokenId;
/* 134 */     this.checkTokenExpiry = checkTokenExpiry;
/*     */   }
/*     */   
/*     */   public DefaultSCTokenConfiguration(String protocol, String tokenId, boolean checkTokenExpiry, boolean clientOutboundMessage) {
/* 138 */     this(protocol, tokenId, checkTokenExpiry);
/* 139 */     this.clientOutboundMessage = clientOutboundMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSCTokenConfiguration(String protocol, SecureConversationToken scToken, WSDLPort wsdlPort, WSBinding binding, Packet packet, AddressingVersion addVer, PolicyAssertion localToken) {
/* 144 */     this.protocol = protocol;
/* 145 */     this.scToken = (Token)scToken;
/* 146 */     this.wsdlPort = wsdlPort;
/* 147 */     this.wsBinding = binding;
/* 148 */     this.packet = packet;
/* 149 */     this.addVer = addVer;
/* 150 */     this.tokenId = scToken.getTokenId();
/* 151 */     parseAssertions(scToken, localToken);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSCTokenConfiguration(DefaultSCTokenConfiguration that, String tokenId) {
/* 156 */     this.protocol = that.protocol;
/* 157 */     this.scToken = that.scToken;
/* 158 */     this.wsdlPort = that.wsdlPort;
/* 159 */     this.wsBinding = that.wsBinding;
/* 160 */     this.clientSecurityTube = that.clientSecurityTube;
/* 161 */     this.packet = that.packet;
/* 162 */     this.addVer = that.addVer;
/* 163 */     this.nextTube = that.nextTube;
/* 164 */     this.tokenId = tokenId;
/* 165 */     this.checkTokenExpiry = that.checkTokenExpiry;
/* 166 */     this.clientOutboundMessage = that.clientOutboundMessage;
/* 167 */     this.addRenewPolicy = that.addRenewPolicy;
/* 168 */     this.reqClientEntropy = that.reqClientEntropy;
/* 169 */     this.symBinding = that.symBinding;
/* 170 */     this.skl = that.skl;
/* 171 */     this.scToken = that.scToken;
/* 172 */     this.wsdlPort = that.wsdlPort;
/* 173 */     this.wsBinding = that.wsBinding;
/* 174 */     this.renewExpiredSCT = that.renewExpiredSCT;
/* 175 */     this.requireCancelSCT = that.requireCancelSCT;
/* 176 */     this.scTokenTimeout = that.scTokenTimeout;
/*     */     
/* 178 */     getOtherOptions().putAll(that.getOtherOptions());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseAssertions(SecureConversationToken scToken, PolicyAssertion localToken) {
/* 184 */     AssertionSet assertions = scToken.getBootstrapPolicy().getAssertionSet();
/* 185 */     for (PolicyAssertion policyAssertion : assertions) {
/* 186 */       SecurityPolicyVersion spVersion = PolicyUtil.getSecurityPolicyVersion(policyAssertion.getName().getNamespaceURI());
/*     */       
/* 188 */       if (PolicyUtil.isTrust13(policyAssertion, spVersion)) {
/* 189 */         this.trust13 = (Trust13)policyAssertion; continue;
/* 190 */       }  if (PolicyUtil.isTrust10(policyAssertion, spVersion)) {
/* 191 */         this.trust10 = (Trust10)policyAssertion; continue;
/* 192 */       }  if (PolicyUtil.isSymmetricBinding(policyAssertion, spVersion)) {
/* 193 */         this.symBinding = (SymmetricBinding)policyAssertion;
/*     */       }
/*     */     } 
/* 196 */     if (this.symBinding != null) {
/* 197 */       AlgorithmSuite algoSuite = this.symBinding.getAlgorithmSuite();
/* 198 */       this.skl = algoSuite.getMinSKLAlgorithm();
/*     */     } 
/*     */     
/* 201 */     if (this.trust10 != null) {
/* 202 */       Set trustReqdProps = this.trust10.getRequiredProperties();
/* 203 */       this.reqClientEntropy = trustReqdProps.contains("RequireClientEntropy");
/*     */     } 
/* 205 */     if (this.trust13 != null) {
/* 206 */       Set trustReqdProps = this.trust13.getRequiredProperties();
/* 207 */       this.reqClientEntropy = trustReqdProps.contains("RequireClientEntropy");
/*     */     } 
/*     */     
/* 210 */     if (localToken != null) {
/* 211 */       if ("SCClientConfiguration".equals(localToken.getName().getLocalPart())) {
/* 212 */         Map<QName, String> attrs = localToken.getAttributes();
/* 213 */         this.renewExpiredSCT = Boolean.parseBoolean(attrs.get(new QName("", "renewExpiredSCT")));
/* 214 */         this.requireCancelSCT = Boolean.parseBoolean(attrs.get(new QName("", "requireCancelSCT")));
/* 215 */         String maxClockSkew = attrs.get(new QName("", "maxClockSkew"));
/* 216 */         if (maxClockSkew != null) {
/* 217 */           getOtherOptions().put("maxClockSkew", maxClockSkew);
/*     */         }
/*     */       } 
/*     */       
/* 221 */       Iterator<PolicyAssertion> sctConfig = localToken.getNestedAssertionsIterator();
/* 222 */       while (sctConfig.hasNext()) {
/* 223 */         PolicyAssertion sctConfigPolicy = sctConfig.next();
/* 224 */         if ("LifeTime".equals(sctConfigPolicy.getName().getLocalPart())) {
/* 225 */           this.scTokenTimeout = Integer.parseInt(sctConfigPolicy.getValue());
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getTokenId() {
/* 233 */     return this.tokenId;
/*     */   }
/*     */   
/*     */   public boolean checkTokenExpiry() {
/* 237 */     return this.checkTokenExpiry;
/*     */   }
/*     */   
/*     */   public boolean isClientOutboundMessage() {
/* 241 */     return this.clientOutboundMessage;
/*     */   }
/*     */   
/*     */   public boolean addRenewPolicy() {
/* 245 */     return this.addRenewPolicy;
/*     */   }
/*     */   
/*     */   public boolean getReqClientEntropy() {
/* 249 */     return this.reqClientEntropy;
/*     */   }
/*     */   
/*     */   public boolean isSymmetricBinding() {
/* 253 */     if (this.symBinding == null) {
/* 254 */       return false;
/*     */     }
/* 256 */     return true;
/*     */   }
/*     */   
/*     */   public int getKeySize() {
/* 260 */     return this.skl;
/*     */   }
/*     */   
/*     */   public Token getSCToken() {
/* 264 */     return this.scToken;
/*     */   }
/*     */   
/*     */   public WSDLPort getWSDLPort() {
/* 268 */     return this.wsdlPort;
/*     */   }
/*     */   
/*     */   public WSBinding getWSBinding() {
/* 272 */     return this.wsBinding;
/*     */   }
/*     */   
/*     */   public Tube getClientTube() {
/* 276 */     return this.clientSecurityTube;
/*     */   }
/*     */   
/*     */   public Tube getNextTube() {
/* 280 */     return this.nextTube;
/*     */   }
/*     */   
/*     */   public Packet getPacket() {
/* 284 */     return this.packet;
/*     */   }
/*     */   
/*     */   public AddressingVersion getAddressingVersion() {
/* 288 */     return this.addVer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\impl\client\DefaultSCTokenConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
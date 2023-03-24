/*     */ package com.sun.xml.ws.api.security.secconv.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SCTokenConfiguration
/*     */   implements IssuedTokenConfiguration
/*     */ {
/*     */   public static final String PROTOCOL_10 = "http://schemas.xmlsoap.org/ws/2005/02/sc";
/*     */   public static final String PROTOCOL_13 = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
/*     */   public static final String MAX_CLOCK_SKEW = "maxClockSkew";
/*     */   protected String protocol;
/*     */   protected boolean renewExpiredSCT = false;
/*     */   protected boolean requireCancelSCT = false;
/*  70 */   protected long scTokenTimeout = -1L;
/*     */   
/*  72 */   private Map<String, Object> otherOptions = new HashMap<String, Object>();
/*     */   
/*     */   protected SCTokenConfiguration() {
/*  75 */     this("http://schemas.xmlsoap.org/ws/2005/02/sc");
/*     */   }
/*     */   
/*     */   protected SCTokenConfiguration(String protocol) {
/*  79 */     this.protocol = protocol;
/*     */   }
/*     */   
/*     */   public String getProtocol() {
/*  83 */     return this.protocol;
/*     */   }
/*     */   
/*     */   public boolean isRenewExpiredSCT() {
/*  87 */     return this.renewExpiredSCT;
/*     */   }
/*     */   
/*     */   public boolean isRequireCancelSCT() {
/*  91 */     return this.requireCancelSCT;
/*     */   }
/*     */   
/*     */   public long getSCTokenTimeout() {
/*  95 */     return this.scTokenTimeout;
/*     */   }
/*     */   
/*     */   public abstract String getTokenId();
/*     */   
/*     */   public abstract boolean checkTokenExpiry();
/*     */   
/*     */   public abstract boolean isClientOutboundMessage();
/*     */   
/*     */   public abstract boolean addRenewPolicy();
/*     */   
/*     */   public abstract boolean getReqClientEntropy();
/*     */   
/*     */   public abstract boolean isSymmetricBinding();
/*     */   
/*     */   public abstract int getKeySize();
/*     */   
/*     */   public abstract Token getSCToken();
/*     */   
/*     */   public abstract Packet getPacket();
/*     */   
/*     */   public abstract Tube getClientTube();
/*     */   
/*     */   public abstract Tube getNextTube();
/*     */   
/*     */   public abstract WSDLPort getWSDLPort();
/*     */   
/*     */   public abstract WSBinding getWSBinding();
/*     */   
/*     */   public abstract AddressingVersion getAddressingVersion();
/*     */   
/*     */   public Map<String, Object> getOtherOptions() {
/* 127 */     return this.otherOptions;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\secconv\client\SCTokenConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
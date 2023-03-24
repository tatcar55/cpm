/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import com.sun.xml.wss.impl.MessageLayout;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessingContext
/*     */   implements SecurityProcessingContext
/*     */ {
/*     */   String messageIdentifier;
/*     */   StaticPolicyContext context;
/*     */   SecurityPolicy securityPolicy;
/*     */   boolean inBoundMessage = false;
/* 136 */   CallbackHandler callbackHandler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   SecurityEnvironment environmentHandler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   protected SecurableSoapMessage secureMessage = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   protected Map properties = null;
/*     */ 
/*     */ 
/*     */   
/* 157 */   int configType = 0;
/*     */   
/* 159 */   protected MessageLayout securityHeaderLayout = MessageLayout.Lax;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String OPERATION_RESOLVER = "OperationResolver";
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean retainSecHeader = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean resetMU = false;
/*     */ 
/*     */   
/*     */   private boolean isClient = false;
/*     */ 
/*     */   
/*     */   private boolean isExpired = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingContext() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingContext(StaticPolicyContext context, SecurityPolicy securityPolicy, SOAPMessage message) throws XWSSecurityException {
/* 186 */     generateMessageId();
/*     */     
/* 188 */     setPolicyContext(context);
/*     */     
/* 190 */     setSecurityPolicy(securityPolicy);
/*     */     
/* 192 */     setSOAPMessage(message);
/*     */   }
/*     */   
/*     */   public void resetMustUnderstand(boolean b) {
/* 196 */     this.resetMU = b;
/*     */   }
/*     */   
/*     */   public boolean resetMustUnderstand() {
/* 200 */     return this.resetMU;
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
/*     */   
/*     */   public void setSecurityPolicy(SecurityPolicy securityPolicy) throws XWSSecurityException {
/* 215 */     this.securityPolicy = securityPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityPolicy getSecurityPolicy() {
/* 222 */     return this.securityPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolicyContext(StaticPolicyContext context) {
/* 230 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StaticPolicyContext getPolicyContext() {
/* 237 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSOAPMessage(SOAPMessage message) throws XWSSecurityException {
/* 248 */     this.secureMessage = new SecurableSoapMessage();
/* 249 */     this.secureMessage.setSOAPMessage(message);
/* 250 */     setOptimized();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage getSOAPMessage() {
/* 258 */     return this.secureMessage.getSOAPMessage();
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
/*     */   
/*     */   public void setHandler(CallbackHandler handler) {
/* 273 */     this.callbackHandler = handler;
/* 274 */     this.environmentHandler = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecurityEnvironment(SecurityEnvironment handler) {
/* 282 */     this.environmentHandler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallbackHandler getHandler() {
/* 289 */     return this.callbackHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityEnvironment getSecurityEnvironment() {
/* 296 */     return this.environmentHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getExtraneousProperties() {
/* 306 */     if (this.properties == null) {
/* 307 */       this.properties = new HashMap<Object, Object>();
/*     */     }
/* 309 */     return this.properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isInboundMessage(boolean inBound) {
/* 317 */     this.inBoundMessage = inBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInboundMessage() {
/* 324 */     return this.inBoundMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessageIdentifier(String identifier) {
/* 332 */     this.messageIdentifier = identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessageIdentifier() {
/* 339 */     return this.messageIdentifier;
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
/*     */   public void setExtraneousProperty(String name, Object value) {
/* 352 */     getExtraneousProperties().put(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getExtraneousProperty(String name) {
/* 359 */     return getExtraneousProperties().get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeExtraneousProperty(String name) {
/* 367 */     getExtraneousProperties().remove(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copy(Map p1, Map p2) {
/* 377 */     p1.putAll(p2);
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
/*     */   public void copy(ProcessingContext ctx1, ProcessingContext ctx2) throws XWSSecurityException {
/* 389 */     if (ctx2 == null)
/*     */       return; 
/* 391 */     ctx1.setHandler(ctx2.getHandler());
/* 392 */     ctx1.setSecurityEnvironment(ctx2.getSecurityEnvironment());
/* 393 */     ctx1.setMessageIdentifier(ctx2.getMessageIdentifier());
/* 394 */     if (ctx2.getSecurityPolicy() != null) {
/* 395 */       ctx1.setSecurityPolicy(ctx2.getSecurityPolicy());
/*     */     }
/* 397 */     ctx1.isInboundMessage(ctx2.isInboundMessage());
/* 398 */     ctx1.setSecureMessage(ctx2.getSecureMessage());
/*     */     
/* 400 */     this.properties = ctx2.getExtraneousProperties();
/* 401 */     ctx1.setPolicyContext(ctx2.getPolicyContext());
/* 402 */     ctx1.setConfigType(ctx2.getConfigType());
/* 403 */     ctx1.retainSecurityHeader(ctx2.retainSecurityHeader());
/* 404 */     ctx1.resetMustUnderstand(ctx2.resetMustUnderstand());
/* 405 */     ctx1.isClient(ctx2.isClient());
/* 406 */     ctx1.isExpired(ctx2.isExpired());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateMessageId() {
/* 413 */     Random rnd = new Random();
/* 414 */     long longRandom = rnd.nextLong();
/* 415 */     this.messageIdentifier = String.valueOf(longRandom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConfigType() {
/* 428 */     return this.configType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConfigType(int type) {
/* 435 */     this.configType = type;
/* 436 */     setOptimized();
/*     */   }
/*     */   
/*     */   protected SecurableSoapMessage getSecureMessage() {
/* 440 */     return this.secureMessage;
/*     */   }
/*     */   
/*     */   protected void setSecureMessage(SecurableSoapMessage msg) {
/* 444 */     this.secureMessage = msg;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setOptimized() {
/* 449 */     if (this.secureMessage != null) {
/* 450 */       if (this.configType == 0) {
/* 451 */         this.secureMessage.setOptimized(false);
/*     */       } else {
/* 453 */         this.secureMessage.setOptimized(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void copy(SecurityProcessingContext ctx1, SecurityProcessingContext ctx2) throws XWSSecurityException {
/* 459 */     throw new UnsupportedOperationException("Not yet supported");
/*     */   }
/*     */   
/*     */   public void setSecurityHeaderLayout(MessageLayout layout) {
/* 463 */     this.securityHeaderLayout = layout;
/*     */   }
/*     */   
/*     */   public MessageLayout getSecurityHeaderLayout() {
/* 467 */     return this.securityHeaderLayout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainSecurityHeader() {
/* 474 */     return this.retainSecHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void retainSecurityHeader(boolean arg) {
/* 481 */     this.retainSecHeader = arg;
/*     */   }
/*     */   
/*     */   public void isClient(boolean isClient) {
/* 485 */     this.isClient = isClient;
/*     */   }
/*     */   
/*     */   public boolean isClient() {
/* 489 */     return this.isClient;
/*     */   }
/*     */   
/*     */   public boolean isExpired() {
/* 493 */     return this.isExpired;
/*     */   }
/*     */   
/*     */   public void isExpired(boolean value) {
/* 497 */     this.isExpired = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\ProcessingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
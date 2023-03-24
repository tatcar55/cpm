/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSProcessor;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*     */ import com.sun.xml.wss.impl.SecurityRecipient;
/*     */ import com.sun.xml.wss.impl.config.DeclarativeSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.config.SecurityConfigurationXmlReader;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.io.InputStream;
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
/*     */ public class XWSSProcessor2_0Impl
/*     */   implements XWSSProcessor
/*     */ {
/*  62 */   private DeclarativeSecurityConfiguration declSecConfig = null;
/*  63 */   private CallbackHandler handler = null;
/*  64 */   private SecurityEnvironment secEnv = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected XWSSProcessor2_0Impl(InputStream securityConfig, CallbackHandler handler) throws XWSSecurityException {
/*     */     try {
/*  70 */       this.declSecConfig = SecurityConfigurationXmlReader.createDeclarativeConfiguration(securityConfig);
/*     */       
/*  72 */       this.handler = handler;
/*  73 */       this.secEnv = new DefaultSecurityEnvironmentImpl(this.handler);
/*  74 */     } catch (Exception e) {
/*     */       
/*  76 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected XWSSProcessor2_0Impl(InputStream securityConfig) throws XWSSecurityException {
/*  83 */     throw new UnsupportedOperationException("Operation Not Supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage secureOutboundMessage(ProcessingContext context) throws XWSSecurityException {
/*  91 */     MessagePolicy resolvedPolicy = null;
/*     */     
/*  93 */     if (this.declSecConfig != null) {
/*  94 */       resolvedPolicy = this.declSecConfig.senderSettings();
/*     */     } else {
/*     */       
/*  97 */       throw new XWSSecurityException("Security Policy Unknown");
/*     */     } 
/*     */     
/* 100 */     if (resolvedPolicy == null)
/*     */     {
/* 102 */       return context.getSOAPMessage();
/*     */     }
/*     */     
/* 105 */     if (context.getHandler() == null && context.getSecurityEnvironment() == null) {
/* 106 */       context.setSecurityEnvironment(this.secEnv);
/*     */     }
/*     */     
/* 109 */     context.setSecurityPolicy((SecurityPolicy)resolvedPolicy);
/*     */     
/*     */     try {
/* 112 */       SecurityAnnotator.secureMessage(context);
/* 113 */     } catch (Exception e) {
/* 114 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/*     */     try {
/* 118 */       SOAPMessage msg = context.getSOAPMessage();
/*     */ 
/*     */ 
/*     */       
/* 122 */       return msg;
/* 123 */     } catch (Exception e) {
/* 124 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage verifyInboundMessage(ProcessingContext context) throws XWSSecurityException {
/* 133 */     MessagePolicy resolvedPolicy = null;
/*     */     
/* 135 */     if (this.declSecConfig != null) {
/* 136 */       resolvedPolicy = this.declSecConfig.receiverSettings();
/*     */     } else {
/*     */       
/* 139 */       throw new XWSSecurityException("Security Policy Unknown");
/*     */     } 
/*     */     
/* 142 */     if (context.getHandler() == null && context.getSecurityEnvironment() == null) {
/* 143 */       context.setSecurityEnvironment(this.secEnv);
/*     */     }
/*     */     
/* 146 */     if (this.declSecConfig.retainSecurityHeader()) {
/* 147 */       context.retainSecurityHeader(true);
/*     */     }
/*     */     
/* 150 */     if (this.declSecConfig.resetMustUnderstand()) {
/* 151 */       context.resetMustUnderstand(true);
/*     */     }
/*     */     
/* 154 */     context.setSecurityPolicy((SecurityPolicy)resolvedPolicy);
/*     */     try {
/* 156 */       SecurityRecipient.validateMessage(context);
/* 157 */     } catch (Exception e) {
/* 158 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/*     */     try {
/* 162 */       SOAPMessage msg = context.getSOAPMessage();
/*     */ 
/*     */ 
/*     */       
/* 166 */       return msg;
/* 167 */     } catch (Exception e) {
/* 168 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ProcessingContext createProcessingContext(SOAPMessage msg) throws XWSSecurityException {
/* 174 */     ProcessingContext cntxt = new ProcessingContext();
/* 175 */     cntxt.setSOAPMessage(msg);
/* 176 */     return cntxt;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\XWSSProcessor2_0Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
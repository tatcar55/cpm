/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.handler.MessageHandler;
/*     */ import com.sun.xml.ws.handler.HandlerException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.LogicalHandler;
/*     */ import javax.xml.ws.handler.soap.SOAPHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandlerConfiguration
/*     */ {
/*     */   private final Set<String> roles;
/*     */   private final List<Handler> handlerChain;
/*     */   private final List<LogicalHandler> logicalHandlers;
/*     */   private final List<SOAPHandler> soapHandlers;
/*     */   private final List<MessageHandler> messageHandlers;
/*     */   private final Set<QName> handlerKnownHeaders;
/*     */   
/*     */   public HandlerConfiguration(Set<String> roles, List<Handler> handlerChain) {
/*  79 */     this.roles = roles;
/*  80 */     this.handlerChain = handlerChain;
/*  81 */     this.logicalHandlers = new ArrayList<LogicalHandler>();
/*  82 */     this.soapHandlers = new ArrayList<SOAPHandler>();
/*  83 */     this.messageHandlers = new ArrayList<MessageHandler>();
/*  84 */     Set<QName> modHandlerKnownHeaders = new HashSet<QName>();
/*     */     
/*  86 */     for (Handler handler : handlerChain) {
/*  87 */       if (handler instanceof LogicalHandler) {
/*  88 */         this.logicalHandlers.add((LogicalHandler)handler); continue;
/*  89 */       }  if (handler instanceof SOAPHandler) {
/*  90 */         this.soapHandlers.add((SOAPHandler)handler);
/*  91 */         Set<QName> headers = ((SOAPHandler)handler).getHeaders();
/*  92 */         if (headers != null)
/*  93 */           modHandlerKnownHeaders.addAll(headers);  continue;
/*     */       } 
/*  95 */       if (handler instanceof MessageHandler) {
/*  96 */         this.messageHandlers.add((MessageHandler)handler);
/*  97 */         Set<QName> headers = ((MessageHandler)handler).getHeaders();
/*  98 */         if (headers != null)
/*  99 */           modHandlerKnownHeaders.addAll(headers); 
/*     */         continue;
/*     */       } 
/* 102 */       throw new HandlerException("handler.not.valid.type", new Object[] { handler.getClass() });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.handlerKnownHeaders = Collections.unmodifiableSet(modHandlerKnownHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandlerConfiguration(Set<String> roles, HandlerConfiguration oldConfig) {
/* 116 */     this.roles = roles;
/* 117 */     this.handlerChain = oldConfig.handlerChain;
/* 118 */     this.logicalHandlers = oldConfig.logicalHandlers;
/* 119 */     this.soapHandlers = oldConfig.soapHandlers;
/* 120 */     this.messageHandlers = oldConfig.messageHandlers;
/* 121 */     this.handlerKnownHeaders = oldConfig.handlerKnownHeaders;
/*     */   }
/*     */   
/*     */   public Set<String> getRoles() {
/* 125 */     return this.roles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Handler> getHandlerChain() {
/* 133 */     if (this.handlerChain == null)
/* 134 */       return Collections.emptyList(); 
/* 135 */     return new ArrayList<Handler>(this.handlerChain);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<LogicalHandler> getLogicalHandlers() {
/* 140 */     return this.logicalHandlers;
/*     */   }
/*     */   
/*     */   public List<SOAPHandler> getSoapHandlers() {
/* 144 */     return this.soapHandlers;
/*     */   }
/*     */   
/*     */   public List<MessageHandler> getMessageHandlers() {
/* 148 */     return this.messageHandlers;
/*     */   }
/*     */   
/*     */   public Set<QName> getHandlerKnownHeaders() {
/* 152 */     return this.handlerKnownHeaders;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\HandlerConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
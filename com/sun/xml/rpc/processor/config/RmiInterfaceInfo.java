/*     */ package com.sun.xml.rpc.processor.config;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RmiInterfaceInfo
/*     */ {
/*     */   private RmiModelInfo parent;
/*     */   private String soapAction;
/*     */   private String soapActionBase;
/*     */   private String name;
/*     */   private String servantName;
/*     */   private HandlerChainInfo clientHandlerChainInfo;
/*     */   private HandlerChainInfo serverHandlerChainInfo;
/*     */   
/*     */   public RmiModelInfo getParent() {
/*  40 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(RmiModelInfo rsi) {
/*  44 */     this.parent = rsi;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  48 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/*  52 */     this.name = s;
/*     */   }
/*     */   
/*     */   public String getServantName() {
/*  56 */     return this.servantName;
/*     */   }
/*     */   
/*     */   public void setServantName(String s) {
/*  60 */     this.servantName = s;
/*     */   }
/*     */   
/*     */   public String getSOAPAction() {
/*  64 */     return this.soapAction;
/*     */   }
/*     */   
/*     */   public void setSOAPAction(String s) {
/*  68 */     this.soapAction = s;
/*     */   }
/*     */   
/*     */   public String getSOAPActionBase() {
/*  72 */     return this.soapActionBase;
/*     */   }
/*     */   
/*     */   public void setSOAPActionBase(String s) {
/*  76 */     this.soapActionBase = s;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/*  80 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   public void setSOAPVersion(SOAPVersion version) {
/*  84 */     this.soapVersion = version;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getClientHandlerChainInfo() {
/*  88 */     if (this.clientHandlerChainInfo != null) {
/*  89 */       return this.clientHandlerChainInfo;
/*     */     }
/*  91 */     return this.parent.getClientHandlerChainInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientHandlerChainInfo(HandlerChainInfo i) {
/*  96 */     this.clientHandlerChainInfo = i;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getServerHandlerChainInfo() {
/* 100 */     if (this.serverHandlerChainInfo != null) {
/* 101 */       return this.serverHandlerChainInfo;
/*     */     }
/* 103 */     return this.parent.getServerHandlerChainInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServerHandlerChainInfo(HandlerChainInfo i) {
/* 108 */     this.serverHandlerChainInfo = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\RmiInterfaceInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
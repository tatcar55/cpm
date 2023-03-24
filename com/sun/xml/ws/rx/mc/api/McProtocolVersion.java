/*     */ package com.sun.xml.ws.rx.mc.api;
/*     */ 
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
/*     */ 
/*     */ public enum McProtocolVersion
/*     */ {
/*  52 */   WSMC200702("http://docs.oasis-open.org/ws-rx/wsmc/200702", "http://docs.oasis-open.org/ws-rx/wsmc/200702");
/*     */ 
/*     */   
/*     */   public final QName missingSelectionFaultCode;
/*     */ 
/*     */   
/*     */   public final QName unsupportedSelectionFaultCode;
/*     */ 
/*     */   
/*     */   public final QName messagePendingHeaderName;
/*     */ 
/*     */   
/*     */   public final String wsmcFaultAction;
/*     */ 
/*     */   
/*     */   public final String wsmcAction;
/*     */ 
/*     */   
/*     */   public final String policyNamespaceUri;
/*     */ 
/*     */   
/*     */   public final String protocolNamespaceUri;
/*     */ 
/*     */   
/*     */   McProtocolVersion(String protocolNamespaceUri, String policyNamespaceUri) {
/*  77 */     this.protocolNamespaceUri = protocolNamespaceUri;
/*  78 */     this.policyNamespaceUri = policyNamespaceUri;
/*     */     
/*  80 */     this.wsmcAction = protocolNamespaceUri + "/MakeConnection";
/*  81 */     this.wsmcFaultAction = protocolNamespaceUri + "/fault";
/*     */     
/*  83 */     this.messagePendingHeaderName = new QName(protocolNamespaceUri, "MessagePending");
/*     */     
/*  85 */     this.unsupportedSelectionFaultCode = new QName(protocolNamespaceUri, "UnsupportedSelection");
/*  86 */     this.missingSelectionFaultCode = new QName(protocolNamespaceUri, "MissingSelection");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static McProtocolVersion getDefault() {
/*  97 */     return WSMC200702;
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
/*     */   public boolean isProtocolAction(String wsaAction) {
/* 110 */     return (wsaAction != null && (this.wsmcAction.equals(wsaAction) || isFault(wsaAction)));
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
/*     */   public boolean isFault(String wsaAction) {
/* 125 */     return this.wsmcFaultAction.equals(wsaAction);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\api\McProtocolVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
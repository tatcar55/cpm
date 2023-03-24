/*     */ package com.sun.xml.ws.rx.rm.api;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum RmProtocolVersion
/*     */ {
/*  62 */   WSRM200502("http://schemas.xmlsoap.org/ws/2005/02/rm", RmAssertionNamespace.WSRMP_200502.toString(), "RMAssertion", "/LastMessage"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   WSRM200702("http://docs.oasis-open.org/ws-rx/wsrm/200702", RmAssertionNamespace.WSRMP_200702.toString(), "RMAssertion", "/CloseSequence");
/*     */   
/*     */   public final String protocolNamespaceUri;
/*     */   
/*     */   public final String policyNamespaceUri;
/*     */   
/*     */   public final String ackRequestedAction;
/*     */   
/*     */   public final String createSequenceAction;
/*     */   
/*     */   public final String createSequenceResponseAction;
/*     */   
/*     */   public final String closeSequenceAction;
/*     */   
/*     */   public final String closeSequenceResponseAction;
/*     */   
/*     */   public final String sequenceAcknowledgementAction;
/*     */   
/*     */   public final String wsrmFaultAction;
/*     */   
/*     */   public final String terminateSequenceAction;
/*     */   
/*     */   public final String terminateSequenceResponseAction;
/*     */   
/*     */   public final QName rmAssertionName;
/*     */   
/*     */   public final QName sequenceTerminatedFaultCode;
/*     */   
/*     */   public final QName unknownSequenceFaultCode;
/*     */   
/*     */   public final QName invalidAcknowledgementFaultCode;
/*     */   
/*     */   public final QName messageNumberRolloverFaultCode;
/*     */   
/*     */   public final QName lastMessageNumberExceededFaultCode;
/*     */   
/*     */   public final QName createSequenceRefusedFaultCode;
/*     */   public final QName sequenceClosedFaultCode;
/*     */   public final QName wsrmRequiredFaultCode;
/*     */   
/*     */   RmProtocolVersion(String protocolNamespaceUri, String policyNamespaceUri, String rmAssertionLocalName, String closeSequenceActionSuffix) {
/* 121 */     this.protocolNamespaceUri = protocolNamespaceUri;
/* 122 */     this.policyNamespaceUri = policyNamespaceUri;
/*     */     
/* 124 */     this.rmAssertionName = new QName(policyNamespaceUri, rmAssertionLocalName);
/*     */     
/* 126 */     this.ackRequestedAction = protocolNamespaceUri + "/AckRequested";
/* 127 */     this.createSequenceAction = protocolNamespaceUri + "/CreateSequence";
/* 128 */     this.createSequenceResponseAction = protocolNamespaceUri + "/CreateSequenceResponse";
/* 129 */     this.closeSequenceAction = protocolNamespaceUri + closeSequenceActionSuffix;
/* 130 */     this.closeSequenceResponseAction = protocolNamespaceUri + "/CloseSequenceResponse";
/* 131 */     this.sequenceAcknowledgementAction = protocolNamespaceUri + "/SequenceAcknowledgement";
/* 132 */     this.wsrmFaultAction = protocolNamespaceUri + "/fault";
/* 133 */     this.terminateSequenceAction = protocolNamespaceUri + "/TerminateSequence";
/* 134 */     this.terminateSequenceResponseAction = protocolNamespaceUri + "/TerminateSequenceResponse";
/*     */     
/* 136 */     this.sequenceTerminatedFaultCode = new QName(protocolNamespaceUri, "SequenceTerminated");
/* 137 */     this.unknownSequenceFaultCode = new QName(protocolNamespaceUri, "UnknownSequence");
/* 138 */     this.invalidAcknowledgementFaultCode = new QName(protocolNamespaceUri, "InvalidAcknowledgement");
/* 139 */     this.messageNumberRolloverFaultCode = new QName(protocolNamespaceUri, "MessageNumberRollover");
/* 140 */     this.lastMessageNumberExceededFaultCode = new QName(protocolNamespaceUri, "LastMessageNumberExceeded");
/* 141 */     this.createSequenceRefusedFaultCode = new QName(protocolNamespaceUri, "CreateSequenceRefused");
/* 142 */     this.sequenceClosedFaultCode = new QName(protocolNamespaceUri, "SequenceClosed");
/* 143 */     this.wsrmRequiredFaultCode = new QName(protocolNamespaceUri, "WSRMRequired");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RmProtocolVersion getDefault() {
/* 154 */     return WSRM200702;
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
/* 167 */     return (wsaAction != null && (isProtocolRequest(wsaAction) || isProtocolResponse(wsaAction) || isFault(wsaAction)));
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
/*     */   
/*     */   public boolean isProtocolRequest(String wsaAction) {
/* 183 */     return (wsaAction != null && (this.ackRequestedAction.equals(wsaAction) || this.createSequenceAction.equals(wsaAction) || this.closeSequenceAction.equals(wsaAction) || this.terminateSequenceAction.equals(wsaAction)));
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
/*     */ 
/*     */   
/*     */   public boolean isProtocolResponse(String wsaAction) {
/* 200 */     return (wsaAction != null && (this.createSequenceResponseAction.equals(wsaAction) || this.closeSequenceResponseAction.equals(wsaAction) || this.sequenceAcknowledgementAction.equals(wsaAction) || this.terminateSequenceResponseAction.equals(wsaAction)));
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
/*     */ 
/*     */   
/*     */   public boolean isFault(String wsaAction) {
/* 217 */     return this.wsrmFaultAction.equals(wsaAction);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 222 */     return "RmProtocolVersion{\n\tprotocolNamespaceUri=" + this.protocolNamespaceUri + ",\n\tpolicyNamespaceUri=" + this.policyNamespaceUri + "\n}";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\api\RmProtocolVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
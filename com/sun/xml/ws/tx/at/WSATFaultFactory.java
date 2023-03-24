/*     */ package com.sun.xml.ws.tx.at;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSATFaultFactory
/*     */ {
/*     */   static final String INVALID_STATE = "InvalidState";
/*     */   static final String INVALID_PROTOCOL = "InvalidProtocol";
/*     */   static final String INVALID_PARAMETERS = "InvalidParameters";
/*     */   static final String NO_ACTIVITY = "NoActivity";
/*     */   static final String CONTEXT_REFUSED = "ContextRefused";
/*     */   static final String ALREADY_REGISTERED = "AlreadyRegistered";
/*     */   static final String INCONSISTENT_INTERNAL_STATE = "InconsistentInternalState";
/*     */   static final String HTTP_SCHEMAS_XMLSOAP_ORG_WS_2004_10_WSAT_FAULT = "http://schemas.xmlsoap.org/ws/2004/10/wsat/fault";
/*     */   private static final String CLIENT = "Client";
/* 102 */   static final QName FAULT_CODE_Q_NAME11 = new QName("http://www.w3.org/2003/05/soap-envelope", "Client");
/*     */   private static final String SENDER = "Sender";
/* 104 */   static final QName FAULT_CODE_Q_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Sender");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean m_isSOAP11 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void throwInvalidStateFault() {
/* 119 */     throwSpecifiedWSATFault("The message was invalid for the current state of the activity.", "InvalidState");
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
/*     */   public static void throwInvalidProtocolFault() {
/* 134 */     throwSpecifiedWSATFault("The protocol is invalid or is not supported by the coordinator.", "InvalidProtocol");
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
/*     */   public static void throwInvalidParametersFault() {
/* 149 */     throwSpecifiedWSATFault("The message contained invalid parameters and could not be processed.", "InvalidParameters");
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
/*     */   public static void throwNoActivityFault() {
/* 164 */     throwSpecifiedWSATFault("The participant is not responding and is presumed to have ended.", "NoActivity");
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
/*     */   public static void throwContextRefusedFault() {
/* 178 */     throwSpecifiedWSATFault("The coordination context that was provided could not be accepted.", "ContextRefused");
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
/*     */   public static void throwAlreadyRegisteredFault() {
/* 192 */     throwSpecifiedWSATFault("The participant has already registered for the same protocol.", "AlreadyRegistered");
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
/*     */   public static void throwInconsistentInternalStateFault() {
/* 206 */     throwSpecifiedWSATFault("A global consistency failure has occurred. This is an unrecoverable condition.", "InconsistentInternalState");
/*     */   }
/*     */   
/*     */   static void setSOAPVersion11(boolean isSOAPVersion11) {
/* 210 */     m_isSOAP11 = isSOAPVersion11;
/*     */   }
/*     */   
/*     */   private static void throwSpecifiedWSATFault(String reasonString, String subCode) {
/*     */     try {
/*     */       SOAPFault fault;
/* 216 */       if (m_isSOAP11) {
/* 217 */         fault = SOAPFactory.newInstance().createFault(reasonString, new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat/fault", subCode, "wsat"));
/*     */       }
/*     */       else {
/*     */         
/* 221 */         fault = SOAPFactory.newInstance("SOAP 1.2 Protocol").createFault();
/* 222 */         fault.setFaultCode(FAULT_CODE_Q_NAME);
/* 223 */         fault.appendFaultSubcode(new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat/fault", subCode, "wsat"));
/* 224 */         fault.addFaultReasonText(reasonString, Locale.ENGLISH);
/*     */       } 
/* 226 */       throw new SOAPFaultException(fault);
/* 227 */     } catch (SOAPException e) {
/* 228 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\WSATFaultFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
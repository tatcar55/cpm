/*    */ package com.sun.xml.ws.security.secconv;
/*    */ 
/*    */ import com.sun.xml.ws.security.trust.WSTrustSOAPFaultException;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSSCSOAPFaultException
/*    */   extends WSTrustSOAPFaultException
/*    */ {
/* 53 */   public static final QName WS_SC_BAD_CONTEXT_TOKEN_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "BadContextToken", "wsc");
/* 54 */   public static final QName WS_SC_UNSUPPORTED_CONTEXT_TOKEN_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "UnsupportedContextToken", "wsc");
/* 55 */   public static final QName WS_SC_UNKNOWN_DERIVATION_SOURCE_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "UnknownDerivationSource", "wsc");
/* 56 */   public static final QName WS_SC_RENED_NEEDED_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "RenewNeeded", "wsc");
/* 57 */   public static final QName WS_SC_UNABLE_TO_RENEW_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "UnableToRenew", "wsc");
/*    */   
/*    */   public static final String WS_SC_BAD_CONTEXT_TOKEN_FAULTSTRING = "The requested context elements are insufficient or unsupported";
/*    */   
/*    */   public static final String WS_SC_UNSUPPORTED_CONTEXT_TOKEN_FAULTSTRING = "Not all of the values associated with the SCT are supported";
/*    */   
/*    */   public static final String WS_SC_UNKNOWN_DERIVATION_SOURCE_FAULTSTRING = "The specified source for the derivation is unknown";
/*    */   
/*    */   public static final String WS_SC_RENED_NEEDED_FAULTSTRING = "The provided context token has expired";
/*    */   public static final String WS_SC_UNABLE_TO_RENEW_FAULTSTRING = "The specified context token could not be renewed";
/*    */   
/*    */   public WSSCSOAPFaultException(String message, Throwable cause, QName faultCode, String faultString) {
/* 69 */     super(message, cause, faultCode, faultString);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCSOAPFaultException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
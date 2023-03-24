/*     */ package com.sun.xml.ws.security.trust;
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
/*     */ public class WSTrustSOAPFaultException
/*     */   extends RuntimeException
/*     */ {
/*  55 */   public static final QName WS_TRUST_INVALID_REQUEST_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "InvalidRequest", "wst");
/*  56 */   public static final QName WS_TRUST_FAILED_AUTHENTICATION_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "FailedAuthentication", "wst");
/*  57 */   public static final QName WS_TRUST_REQUEST_FAILED_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestFailed", "wst");
/*  58 */   public static final QName WS_TRUST_INVALID_SECURITYTOKEN_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "InvalidSecurityToken", "wst");
/*  59 */   public static final QName WS_TRUST_AUTHENTICATION_BAD_ELEMENTS_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "AuthenticationBadElements", "wst");
/*  60 */   public static final QName WS_TRUST_EXPIRED_DATA_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ExpiredData", "wst");
/*  61 */   public static final QName WS_TRUST_INVALID_TIMERANGE_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "InvalidTimeRange", "wst");
/*  62 */   public static final QName WS_TRUST_INVALID_SCOPE_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "InvalidScope", "wst");
/*  63 */   public static final QName WS_TRUST_RENEW_NEEDED_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RenewNeeded", "wst");
/*  64 */   public static final QName WS_TRUST_UNABLE_TO_RENEW_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "UnableToRenew", "wst");
/*  65 */   public static final QName WS_TRUST_BAD_REQUEST_FAULT = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "BadRequest", "wst");
/*     */   
/*     */   public static final String WS_TRUST_INVALID_REQUEST_FAULTSTRING = "The request was invalid or malformed";
/*     */   
/*     */   public static final String WS_TRUST_FAILED_AUTHENTICATION_FAULTSTRING = "Authentication Failed";
/*     */   
/*     */   public static final String WS_TRUST_REQUEST_FAILED_FAULTSTRING = "The specified request failed";
/*     */   
/*     */   public static final String WS_TRUST_INVALID_SECURITYTOKEN_FAULTSTRING = "Security Token has been Revoked";
/*     */   
/*     */   public static final String WS_TRUST_AUTHENTICATION_BAD_ELEMENTS_FAULTSTRING = "Insufficient Digest Elements";
/*     */   
/*     */   public static final String WS_TRUST_BAD_REQUEST_FAULTSTRING = "The specified RequestSecurityToken is not understood";
/*     */   
/*     */   public static final String WS_TRUST_EXPIRED_DATA_FAULTSTRING = "The request data is out-of-date";
/*     */   
/*     */   public static final String WS_TRUST_INVALID_TIMERANGE_FAULTSTRING = "The requested time range is invalid or unsupported";
/*     */   public static final String WS_TRUST_INVALID_SCOPE_FAULTSTRING = "The request scope is invalid or unsupported";
/*     */   public static final String WS_TRUST_RENEW_NEEDED_FAULTSTRING = "A renewable security token has expired";
/*     */   public static final String WS_TRUST_UNABLE_TO_RENEW_FAULTSTRING = "The requested renewal failed";
/*     */   private final QName faultCode;
/*     */   private final String faultString;
/*     */   
/*     */   public WSTrustSOAPFaultException(String message, Throwable cause, QName faultCode, String faultString) {
/*  89 */     super(message, cause);
/*  90 */     this.faultCode = faultCode;
/*  91 */     this.faultString = faultString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFaultString() {
/*  98 */     return this.faultString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getFaultCode() {
/* 105 */     return this.faultCode;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\WSTrustSOAPFaultException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
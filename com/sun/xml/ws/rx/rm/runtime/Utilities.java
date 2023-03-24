/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.runtime.dev.Session;
/*     */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*     */ import com.sun.xml.ws.rx.RxException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.str.DirectReference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Utilities
/*     */ {
/*  62 */   private static final Logger LOGGER = Logger.getLogger(Utilities.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void assertSequenceId(String expected, String actual) throws IllegalStateException {
/*  80 */     if (expected != null && !expected.equals(actual)) {
/*  81 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(LocalizationMessages.WSRM_1105_SEQUENCE_ID_NOT_RECOGNIZED(actual, expected)));
/*     */     }
/*     */   }
/*     */   
/*     */   static String extractSecurityContextTokenId(SecurityTokenReferenceType strType) throws RxException {
/*  86 */     Reference strReference = WSTrustElementFactory.newInstance().createSecurityTokenReference((new ObjectFactory()).createSecurityTokenReference(strType)).getReference();
/*     */     
/*  88 */     if (!(strReference instanceof DirectReference)) {
/*  89 */       throw (RxException)LOGGER.logSevereException(new RxException(LocalizationMessages.WSRM_1132_SECURITY_REFERENCE_ERROR(strReference.getClass().getName())));
/*     */     }
/*     */     
/*  92 */     return ((DirectReference)strReference).getURIAttr().toString();
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
/*     */   static Session startSession(WSEndpoint endpoint, String sessionId) {
/* 105 */     SessionManager manager = SessionManager.getSessionManager(endpoint, null);
/* 106 */     Session session = manager.getSession(sessionId);
/* 107 */     if (session == null) {
/* 108 */       session = manager.createSession(sessionId);
/*     */     }
/*     */     
/* 111 */     return session;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void endSessionIfExists(WSEndpoint endpoint, String sessionId) {
/* 122 */     SessionManager manager = SessionManager.getSessionManager(endpoint, null);
/* 123 */     if (manager.getSession(sessionId) != null) {
/* 124 */       manager.terminateSession(sessionId);
/*     */     }
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
/*     */   static boolean isResendPossible(Throwable throwable) {
/* 137 */     if (throwable instanceof java.io.IOException)
/* 138 */       return true; 
/* 139 */     if (throwable instanceof javax.xml.ws.WebServiceException) {
/* 140 */       if (throwable instanceof com.sun.xml.ws.client.ClientTransportException) {
/* 141 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 145 */       if (throwable.getCause() instanceof java.io.IOException) {
/* 146 */         return true;
/*     */       }
/*     */     } 
/* 149 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\Utilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
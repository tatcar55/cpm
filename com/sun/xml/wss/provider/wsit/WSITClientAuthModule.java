/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.security.Principal;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Destroyable;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.AuthException;
/*     */ import javax.security.auth.message.AuthStatus;
/*     */ import javax.security.auth.message.MessageInfo;
/*     */ import javax.security.auth.message.MessagePolicy;
/*     */ import javax.security.auth.message.module.ClientAuthModule;
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
/*     */ public class WSITClientAuthModule
/*     */   implements ClientAuthModule
/*     */ {
/*  78 */   private static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private Class[] supported = new Class[2];
/*     */   
/*     */   protected static final String DEBUG = "debug";
/*     */   private boolean debug = false;
/*     */   
/*     */   public WSITClientAuthModule() {
/*  89 */     this.supported[0] = SOAPMessage.class;
/*  90 */     this.supported[1] = Message.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) throws AuthException {
/*  97 */     String bg = (String)options.get("debug");
/*  98 */     if (bg != null && bg.equals("true")) this.debug = true; 
/*     */   }
/*     */   
/*     */   public Class[] getSupportedMessageTypes() {
/* 102 */     return this.supported;
/*     */   }
/*     */   
/*     */   public AuthStatus secureRequest(MessageInfo messageInfo, Subject clientSubject) throws AuthException {
/* 106 */     return AuthStatus.SUCCESS;
/*     */   }
/*     */   
/*     */   public AuthStatus validateResponse(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
/* 110 */     return AuthStatus.SUCCESS;
/*     */   }
/*     */   
/*     */   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
/* 114 */     if (subject == null) {
/* 115 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0037_NULL_SUBJECT());
/* 116 */       throw new AuthException(LogStringsMessages.WSITPVD_0037_NULL_SUBJECT());
/*     */     } 
/*     */     
/* 119 */     if (!subject.isReadOnly()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 125 */     Set<Principal> principals = subject.getPrincipals();
/* 126 */     Set<Object> privateCredentials = subject.getPrivateCredentials();
/* 127 */     Set<Object> publicCredentials = subject.getPublicCredentials();
/*     */     
/*     */     try {
/* 130 */       principals.clear();
/* 131 */     } catch (UnsupportedOperationException uoe) {
/* 132 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), uoe);
/*     */     } 
/*     */     
/* 135 */     Iterator<Destroyable> pi = privateCredentials.iterator();
/* 136 */     while (pi.hasNext()) {
/*     */       try {
/* 138 */         Destroyable dstroyable = pi.next();
/*     */         
/* 140 */         dstroyable.destroy();
/* 141 */       } catch (DestroyFailedException dfe) {
/* 142 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), dfe);
/* 143 */       } catch (ClassCastException cce) {
/* 144 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), cce);
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     Iterator<Destroyable> qi = publicCredentials.iterator();
/* 149 */     while (qi.hasNext()) {
/*     */       try {
/* 151 */         Destroyable dstroyable = qi.next();
/*     */         
/* 153 */         dstroyable.destroy();
/* 154 */       } catch (DestroyFailedException dfe) {
/* 155 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), dfe);
/* 156 */       } catch (ClassCastException cce) {
/* 157 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), cce);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITClientAuthModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
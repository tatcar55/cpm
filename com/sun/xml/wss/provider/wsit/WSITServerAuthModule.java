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
/*     */ import javax.security.auth.message.module.ServerAuthModule;
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
/*     */ public class WSITServerAuthModule
/*     */   implements ServerAuthModule
/*     */ {
/*  78 */   private static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   Class[] supported = new Class[2];
/*     */   
/*     */   boolean debug = false;
/*     */   protected static final String DEBUG = "debug";
/*     */   
/*     */   public WSITServerAuthModule() {
/*  89 */     this.supported[0] = SOAPMessage.class;
/*  90 */     this.supported[1] = Message.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) throws AuthException {
/*  98 */     String bg = (String)options.get("debug");
/*  99 */     if (bg != null && bg.equals("true")) this.debug = true; 
/*     */   }
/*     */   
/*     */   public Class[] getSupportedMessageTypes() {
/* 103 */     return this.supported;
/*     */   }
/*     */   
/*     */   public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
/* 107 */     return AuthStatus.SUCCESS;
/*     */   }
/*     */   
/*     */   public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
/* 111 */     return AuthStatus.SUCCESS;
/*     */   }
/*     */   
/*     */   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
/* 115 */     if (subject == null) {
/* 116 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0037_NULL_SUBJECT());
/* 117 */       throw new AuthException(LogStringsMessages.WSITPVD_0037_NULL_SUBJECT());
/*     */     } 
/*     */     
/* 120 */     if (!subject.isReadOnly()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 126 */     Set<Principal> principals = subject.getPrincipals();
/* 127 */     Set<Object> privateCredentials = subject.getPrivateCredentials();
/* 128 */     Set<Object> publicCredentials = subject.getPublicCredentials();
/*     */     
/*     */     try {
/* 131 */       principals.clear();
/* 132 */     } catch (UnsupportedOperationException uoe) {
/* 133 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), uoe);
/*     */     } 
/*     */     
/* 136 */     Iterator<Destroyable> pi = privateCredentials.iterator();
/* 137 */     while (pi.hasNext()) {
/*     */       try {
/* 139 */         Destroyable dstroyable = pi.next();
/*     */         
/* 141 */         dstroyable.destroy();
/* 142 */       } catch (DestroyFailedException dfe) {
/* 143 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), dfe);
/* 144 */       } catch (ClassCastException cce) {
/* 145 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), cce);
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     Iterator<Destroyable> qi = publicCredentials.iterator();
/* 150 */     while (qi.hasNext()) {
/*     */       try {
/* 152 */         Destroyable dstroyable = qi.next();
/*     */         
/* 154 */         dstroyable.destroy();
/* 155 */       } catch (DestroyFailedException dfe) {
/* 156 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), dfe);
/* 157 */       } catch (ClassCastException cce) {
/* 158 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0064_ERROR_CLEAN_SUBJECT(), cce);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITServerAuthModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.wss.provider;
/*     */ 
/*     */ import com.sun.enterprise.security.jauth.AuthException;
/*     */ import com.sun.enterprise.security.jauth.AuthParam;
/*     */ import com.sun.enterprise.security.jauth.AuthPolicy;
/*     */ import com.sun.enterprise.security.jauth.ServerAuthModule;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*     */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*     */ import com.sun.xml.wss.impl.SecurityRecipient;
/*     */ import com.sun.xml.wss.impl.config.DeclarativeSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.security.Principal;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Destroyable;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.xml.soap.SOAPException;
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
/*     */ 
/*     */ 
/*     */ public class ServerSecurityAuthModule
/*     */   extends WssProviderAuthModule
/*     */   implements ServerAuthModule
/*     */ {
/*     */   public void initialize(AuthPolicy requestPolicy, AuthPolicy responsePolicy, CallbackHandler handler, Map options) {
/*  87 */     initialize(requestPolicy, responsePolicy, handler, options, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateRequest(AuthParam param, Subject subject, Map sharedState) throws AuthException {
/*     */     try {
/*  96 */       ProcessingContextImpl context = new ProcessingContextImpl();
/*     */       
/*  98 */       this._sEnvironment.setRequesterSubject(subject, context.getExtraneousProperties());
/*     */       
/* 100 */       MessagePolicy receiverCnfg = ((DeclarativeSecurityConfiguration)this._policy).receiverSettings();
/*     */ 
/*     */       
/* 103 */       context.setSecurityPolicy((SecurityPolicy)receiverCnfg);
/* 104 */       context.setSOAPMessage(AuthParamHelper.getRequest(param));
/* 105 */       context.setSecurityEnvironment((SecurityEnvironment)this._sEnvironment);
/*     */       
/* 107 */       SecurityRecipient.validateMessage((ProcessingContext)context);
/*     */       
/* 109 */       populateSharedStateFromContext(sharedState, context);
/*     */       
/* 111 */       context.getSecurableSoapMessage().deleteSecurityHeader();
/*     */     }
/* 113 */     catch (XWSSecurityException xwsse) {
/* 114 */       xwsse.printStackTrace();
/* 115 */       throw new AuthException(xwsse.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void secureResponse(AuthParam param, Subject subject, Map sharedState) throws AuthException {
/*     */     try {
/* 125 */       ProcessingContextImpl context = new ProcessingContextImpl();
/* 126 */       this._sEnvironment.setSubject(subject, context.getExtraneousProperties());
/*     */       
/* 128 */       populateContextFromSharedState(context, sharedState);
/*     */ 
/*     */       
/* 131 */       MessagePolicy senderCnfg = ((DeclarativeSecurityConfiguration)this._policy).senderSettings();
/*     */ 
/*     */       
/* 134 */       SOAPMessage msg = AuthParamHelper.getResponse(param);
/* 135 */       context.setSecurityPolicy((SecurityPolicy)senderCnfg);
/* 136 */       context.setSOAPMessage(msg);
/* 137 */       context.setSecurityEnvironment((SecurityEnvironment)this._sEnvironment);
/*     */       
/* 139 */       if (this.optimize != 0 && isOptimized(msg)) {
/* 140 */         context.setConfigType(this.optimize);
/*     */       } else {
/*     */         try {
/* 143 */           msg.getSOAPBody();
/* 144 */           msg.getSOAPHeader();
/* 145 */           context.setConfigType(0);
/* 146 */         } catch (SOAPException ex) {
/* 147 */           throw new AuthException(ex.getMessage());
/*     */         } 
/*     */       } 
/*     */       
/* 151 */       SecurityAnnotator.secureMessage((ProcessingContext)context);
/*     */     
/*     */     }
/* 154 */     catch (XWSSecurityException xwsse) {
/* 155 */       xwsse.printStackTrace();
/* 156 */       throw new AuthException(xwsse.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disposeSubject(Subject subject, Map sharedState) throws AuthException {
/* 163 */     if (subject == null)
/*     */     {
/* 165 */       throw new AuthException("Subject is null in disposeSubject");
/*     */     }
/*     */     
/* 168 */     if (!subject.isReadOnly()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 174 */     Set<Principal> principals = subject.getPrincipals();
/* 175 */     Set<Object> privateCredentials = subject.getPrivateCredentials();
/* 176 */     Set<Object> publicCredentials = subject.getPublicCredentials();
/*     */     
/*     */     try {
/* 179 */       principals.clear();
/* 180 */     } catch (UnsupportedOperationException uoe) {}
/*     */ 
/*     */ 
/*     */     
/* 184 */     Iterator<Destroyable> pi = privateCredentials.iterator();
/* 185 */     while (pi.hasNext()) {
/*     */       try {
/* 187 */         Destroyable dstroyable = pi.next();
/*     */         
/* 189 */         dstroyable.destroy();
/* 190 */       } catch (DestroyFailedException dfe) {
/*     */       
/* 192 */       } catch (ClassCastException cce) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     Iterator<Destroyable> qi = publicCredentials.iterator();
/* 198 */     while (qi.hasNext()) {
/*     */       try {
/* 200 */         Destroyable dstroyable = qi.next();
/*     */         
/* 202 */         dstroyable.destroy();
/* 203 */       } catch (DestroyFailedException dfe) {
/*     */       
/* 205 */       } catch (ClassCastException cce) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateContextFromSharedState(ProcessingContextImpl context, Map sharedState) {
/* 212 */     context.setExtraneousProperty("javax.security.auth.Subject", sharedState.get("REQUESTER_SUBJECT"));
/*     */     
/* 214 */     context.setExtraneousProperty("requester.keyid", sharedState.get("REQUESTER_KEYID"));
/*     */     
/* 216 */     context.setExtraneousProperty("requester.issuername", sharedState.get("REQUESTER_ISSUERNAME"));
/*     */     
/* 218 */     context.setExtraneousProperty("requester.serial", sharedState.get("REQUESTER_SERIAL"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateSharedStateFromContext(Map<String, Object> sharedState, ProcessingContextImpl context) {
/* 224 */     sharedState.put("REQUESTER_SUBJECT", context.getExtraneousProperty("javax.security.auth.Subject"));
/*     */     
/* 226 */     sharedState.put("REQUESTER_KEYID", context.getExtraneousProperty("requester.keyid"));
/*     */     
/* 228 */     sharedState.put("REQUESTER_ISSUERNAME", context.getExtraneousProperty("requester.issuername"));
/*     */ 
/*     */     
/* 231 */     sharedState.put("REQUESTER_SERIAL", context.getExtraneousProperty("requester.serial"));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\ServerSecurityAuthModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
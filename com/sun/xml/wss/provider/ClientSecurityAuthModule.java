/*     */ package com.sun.xml.wss.provider;
/*     */ 
/*     */ import com.sun.enterprise.security.jauth.AuthException;
/*     */ import com.sun.enterprise.security.jauth.AuthParam;
/*     */ import com.sun.enterprise.security.jauth.AuthPolicy;
/*     */ import com.sun.enterprise.security.jauth.ClientAuthModule;
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
/*     */ public class ClientSecurityAuthModule
/*     */   extends WssProviderAuthModule
/*     */   implements ClientAuthModule
/*     */ {
/*     */   public void initialize(AuthPolicy requestPolicy, AuthPolicy responsePolicy, CallbackHandler handler, Map options) {
/*  86 */     initialize(requestPolicy, responsePolicy, handler, options, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void secureRequest(AuthParam param, Subject subject, Map<String, Subject> sharedState) throws AuthException {
/*     */     try {
/*  95 */       ProcessingContextImpl context = new ProcessingContextImpl();
/*     */ 
/*     */       
/*  98 */       this._sEnvironment.setSubject(subject, context.getExtraneousProperties());
/*  99 */       if (sharedState != null) {
/* 100 */         sharedState.put("SELF_SUBJECT", subject);
/*     */       }
/*     */       
/* 103 */       MessagePolicy senderConfg = ((DeclarativeSecurityConfiguration)this._policy).senderSettings();
/*     */ 
/*     */ 
/*     */       
/* 107 */       SOAPMessage msg = AuthParamHelper.getRequest(param);
/* 108 */       context.setSecurityPolicy((SecurityPolicy)senderConfg);
/* 109 */       context.setSOAPMessage(msg);
/* 110 */       context.setSecurityEnvironment((SecurityEnvironment)this._sEnvironment);
/*     */       
/* 112 */       if (this.optimize != 0 && isOptimized(msg)) {
/* 113 */         context.setConfigType(this.optimize);
/*     */       } else {
/*     */         try {
/* 116 */           msg.getSOAPBody();
/* 117 */           msg.getSOAPHeader();
/* 118 */           context.setConfigType(0);
/* 119 */         } catch (SOAPException ex) {
/* 120 */           throw new AuthException(ex.getMessage());
/*     */         } 
/*     */       } 
/* 123 */       SecurityAnnotator.secureMessage((ProcessingContext)context);
/*     */     }
/* 125 */     catch (XWSSecurityException xwsse) {
/*     */       
/* 127 */       xwsse.printStackTrace();
/* 128 */       throw new AuthException(xwsse.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateResponse(AuthParam param, Subject subject, Map sharedState) throws AuthException {
/*     */     try {
/* 138 */       ProcessingContextImpl context = new ProcessingContextImpl();
/*     */ 
/*     */       
/* 141 */       if (sharedState != null) {
/* 142 */         Subject selfSubject = (Subject)sharedState.get("SELF_SUBJECT");
/* 143 */         this._sEnvironment.setSubject(selfSubject, context.getExtraneousProperties());
/*     */       } 
/*     */       
/* 146 */       this._sEnvironment.setRequesterSubject(subject, context.getExtraneousProperties());
/*     */       
/* 148 */       MessagePolicy receiverConfg = ((DeclarativeSecurityConfiguration)this._policy).receiverSettings();
/*     */ 
/*     */       
/* 151 */       context.setSecurityPolicy((SecurityPolicy)receiverConfg);
/*     */       
/* 153 */       context.setSOAPMessage(AuthParamHelper.getResponse(param));
/* 154 */       context.setSecurityEnvironment((SecurityEnvironment)this._sEnvironment);
/*     */       
/* 156 */       SecurityRecipient.validateMessage((ProcessingContext)context);
/*     */       
/* 158 */       context.getSecurableSoapMessage().deleteSecurityHeader();
/* 159 */     } catch (XWSSecurityException xwsse) {
/* 160 */       xwsse.printStackTrace();
/* 161 */       throw new AuthException(xwsse.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disposeSubject(Subject subject, Map sharedState) throws AuthException {
/* 168 */     if (subject == null)
/*     */     {
/* 170 */       throw new AuthException("Error disposing Subject: null value for Subject");
/*     */     }
/*     */     
/* 173 */     if (!subject.isReadOnly()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 179 */     Set<Principal> principals = subject.getPrincipals();
/* 180 */     Set<Object> privateCredentials = subject.getPrivateCredentials();
/* 181 */     Set<Object> publicCredentials = subject.getPublicCredentials();
/*     */     
/*     */     try {
/* 184 */       principals.clear();
/* 185 */     } catch (UnsupportedOperationException uoe) {}
/*     */ 
/*     */ 
/*     */     
/* 189 */     Iterator<Destroyable> pi = privateCredentials.iterator();
/* 190 */     while (pi.hasNext()) {
/*     */       try {
/* 192 */         Destroyable dstroyable = pi.next();
/*     */         
/* 194 */         dstroyable.destroy();
/* 195 */       } catch (DestroyFailedException dfe) {
/*     */       
/* 197 */       } catch (ClassCastException cce) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     Iterator<Destroyable> qi = publicCredentials.iterator();
/* 203 */     while (qi.hasNext()) {
/*     */       try {
/* 205 */         Destroyable dstroyable = qi.next();
/*     */         
/* 207 */         dstroyable.destroy();
/* 208 */       } catch (DestroyFailedException dfe) {
/*     */       
/* 210 */       } catch (ClassCastException cce) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\ClientSecurityAuthModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
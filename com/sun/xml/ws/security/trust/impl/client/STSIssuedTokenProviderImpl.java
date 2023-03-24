/*     */ package com.sun.xml.ws.security.trust.impl.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenProvider;
/*     */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.trust.TrustPlugin;
/*     */ import com.sun.xml.ws.security.trust.WSTrustFactory;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.SubjectAccessor;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class STSIssuedTokenProviderImpl
/*     */   implements IssuedTokenProvider
/*     */ {
/*  68 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void issue(IssuedTokenContext ctx) throws WSTrustException {
/*  74 */     getIssuedTokenContext(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel(IssuedTokenContext ctx) throws WSTrustException {}
/*     */ 
/*     */   
/*     */   public void renew(IssuedTokenContext ctx) throws WSTrustException {}
/*     */ 
/*     */   
/*     */   public void validate(IssuedTokenContext ctx) throws WSTrustException {
/*  86 */     TrustPlugin tp = WSTrustFactory.newTrustPlugin();
/*  87 */     tp.processValidate(ctx);
/*     */   }
/*     */   
/*     */   private void updateContext(IssuedTokenContext cached, IssuedTokenContext ctx) {
/*  91 */     ctx.setUnAttachedSecurityTokenReference(cached.getUnAttachedSecurityTokenReference());
/*  92 */     ctx.setSecurityToken(cached.getSecurityToken());
/*  93 */     ctx.setRequestorCertificate(cached.getRequestorCertificate());
/*  94 */     ctx.setProofKeyPair(cached.getProofKeyPair());
/*  95 */     ctx.setProofKey(cached.getProofKey());
/*  96 */     ctx.setExpirationTime(cached.getExpirationTime());
/*  97 */     ctx.setCreationTime(cached.getCreationTime());
/*  98 */     ctx.setAttachedSecurityTokenReference(cached.getAttachedSecurityTokenReference());
/*     */   }
/*     */   
/*     */   private void getIssuedTokenContext(IssuedTokenContext ctx) throws WSTrustException {
/* 102 */     STSIssuedTokenConfiguration config = ctx.getSecurityPolicy().get(0);
/* 103 */     ctx.setTokenIssuer(config.getSTSEndpoint());
/* 104 */     boolean shareToken = "true".equals(config.getOtherOptions().get("shareToken"));
/* 105 */     boolean renewExpiredToken = "true".equals(config.getOtherOptions().get("renewExpiredToken"));
/* 106 */     String maxClockSkew = (String)config.getOtherOptions().get("MaxClockSkew");
/* 107 */     Subject subject = SubjectAccessor.getRequesterSubject();
/* 108 */     if (shareToken && subject != null) {
/* 109 */       Set<IssuedTokenContext> pcs = subject.getPrivateCredentials(IssuedTokenContext.class);
/* 110 */       for (IssuedTokenContext obj : pcs) {
/* 111 */         IssuedTokenContext cached = obj;
/*     */ 
/*     */         
/* 114 */         Calendar c = new GregorianCalendar();
/* 115 */         long offset = c.get(15);
/* 116 */         if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 117 */           offset += c.getTimeZone().getDSTSavings();
/*     */         }
/* 119 */         long beforeTime = c.getTimeInMillis();
/* 120 */         long currentTime = beforeTime - offset;
/* 121 */         if (maxClockSkew != null) {
/* 122 */           currentTime -= Long.parseLong(maxClockSkew);
/*     */         }
/* 124 */         c.setTimeInMillis(currentTime);
/* 125 */         Date currentTimeInDateFormat = c.getTime();
/* 126 */         if (cached.getExpirationTime() != null && currentTimeInDateFormat.after(cached.getExpirationTime())) {
/*     */           
/* 128 */           subject.getPrivateCredentials().remove(cached);
/*     */ 
/*     */           
/* 131 */           if (!renewExpiredToken) {
/* 132 */             log.log(Level.SEVERE, LogStringsMessages.WST_0046_TOKEN_EXPIRED(cached.getCreationTime(), cached.getExpirationTime(), currentTimeInDateFormat));
/*     */             
/* 134 */             throw new WSTrustException(LogStringsMessages.WST_0046_TOKEN_EXPIRED(cached.getCreationTime(), cached.getExpirationTime(), currentTimeInDateFormat));
/*     */           }  continue;
/* 136 */         }  if (cached.getTokenIssuer().equals(ctx.getTokenIssuer())) {
/* 137 */           updateContext(cached, ctx);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 143 */     TrustPlugin tp = WSTrustFactory.newTrustPlugin();
/* 144 */     tp.process(ctx);
/* 145 */     if (shareToken) {
/* 146 */       if (subject == null) {
/* 147 */         subject = new Subject();
/*     */       }
/* 149 */       subject.getPrivateCredentials().add(ctx);
/* 150 */       SubjectAccessor.setRequesterSubject(subject);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\client\STSIssuedTokenProviderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
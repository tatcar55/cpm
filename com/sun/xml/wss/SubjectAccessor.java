/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.xml.rpc.handler.MessageContext;
/*     */ import javax.xml.rpc.server.ServletEndpointContext;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubjectAccessor
/*     */ {
/*  64 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static ThreadLocal<Subject> wssThreadCtx = new ThreadLocal<Subject>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Subject getRequesterSubject(Object context) throws XWSSecurityException {
/*  81 */     if (context instanceof ProcessingContext)
/*  82 */       return (Subject)((ProcessingContext)context).getExtraneousProperty("javax.security.auth.Subject"); 
/*  83 */     if (context instanceof MessageContext) {
/*     */       
/*  85 */       MessageContext msgContext = (MessageContext)context;
/*     */       
/*  87 */       Subject subject = (Subject)msgContext.get("javax.security.auth.Subject");
/*  88 */       return subject;
/*     */     } 
/*  90 */     if (context instanceof WebServiceContext)
/*     */       
/*     */       try {
/*  93 */         WebServiceContext wsCtx = (WebServiceContext)context;
/*  94 */         MessageContext msgContext = wsCtx.getMessageContext();
/*  95 */         if (msgContext != null) {
/*  96 */           Subject subject = (Subject)msgContext.get("javax.security.auth.Subject");
/*  97 */           return subject;
/*     */         } 
/*  99 */         return null;
/*     */       
/*     */       }
/* 102 */       catch (NoClassDefFoundError ncde) {
/* 103 */         log.log(Level.SEVERE, "WSS0761.context.not.instanceof.servletendpointcontext", ncde);
/*     */         
/* 105 */         throw new XWSSecurityException(ncde);
/* 106 */       } catch (Exception ex) {
/* 107 */         log.log(Level.SEVERE, "WSS0761.context.not.instanceof.servletendpointcontext", ex);
/*     */         
/* 109 */         throw new XWSSecurityException(ex);
/*     */       }  
/* 111 */     if (context instanceof ServletEndpointContext) {
/*     */       
/* 113 */       MessageContext msgContext = ((ServletEndpointContext)context).getMessageContext();
/* 114 */       if (msgContext != null) {
/* 115 */         Subject subject = (Subject)msgContext.getProperty("javax.security.auth.Subject");
/* 116 */         return subject;
/*     */       } 
/*     */       
/* 119 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Subject getRequesterSubject() {
/* 133 */     return wssThreadCtx.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setRequesterSubject(Subject sub) {
/* 144 */     wssThreadCtx.set(sub);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\SubjectAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
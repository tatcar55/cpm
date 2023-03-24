/*     */ package com.oracle.webservices.api.message;
/*     */ 
/*     */ import com.oracle.webservices.api.EnvelopeStyle;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageContextFactory
/*     */ {
/*  57 */   private static final MessageContextFactory DEFAULT = (MessageContextFactory)new com.sun.xml.ws.api.message.MessageContextFactory(new WebServiceFeature[0]);
/*     */ 
/*     */   
/*     */   protected abstract MessageContextFactory newFactory(WebServiceFeature... paramVarArgs);
/*     */ 
/*     */   
/*     */   public abstract MessageContext createContext();
/*     */ 
/*     */   
/*     */   public abstract MessageContext createContext(SOAPMessage paramSOAPMessage);
/*     */   
/*     */   public abstract MessageContext createContext(Source paramSource);
/*     */   
/*     */   public abstract MessageContext createContext(Source paramSource, EnvelopeStyle.Style paramStyle);
/*     */   
/*     */   public abstract MessageContext createContext(InputStream paramInputStream, String paramString) throws IOException;
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext createContext(InputStream paramInputStream, MimeHeaders paramMimeHeaders) throws IOException;
/*     */   
/*     */   public static MessageContextFactory createFactory(WebServiceFeature... f) {
/*  78 */     return createFactory(null, f);
/*     */   }
/*     */   
/*     */   public static MessageContextFactory createFactory(ClassLoader cl, WebServiceFeature... f) {
/*  82 */     for (MessageContextFactory factory : ServiceFinder.find(MessageContextFactory.class, cl)) {
/*  83 */       MessageContextFactory newfac = factory.newFactory(f);
/*  84 */       if (newfac != null) return newfac; 
/*     */     } 
/*  86 */     return (MessageContextFactory)new com.sun.xml.ws.api.message.MessageContextFactory(f);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext doCreate();
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext doCreate(SOAPMessage paramSOAPMessage);
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext doCreate(Source paramSource, SOAPVersion paramSOAPVersion);
/*     */   
/*     */   @Deprecated
/*     */   public static MessageContext create(ClassLoader... classLoader) {
/* 102 */     return serviceFinder(classLoader, new Creator()
/*     */         {
/*     */           public MessageContext create(MessageContextFactory f) {
/* 105 */             return f.doCreate();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static MessageContext create(final SOAPMessage m, ClassLoader... classLoader) {
/* 112 */     return serviceFinder(classLoader, new Creator()
/*     */         {
/*     */           public MessageContext create(MessageContextFactory f) {
/* 115 */             return f.doCreate(m);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static MessageContext create(final Source m, final SOAPVersion v, ClassLoader... classLoader) {
/* 122 */     return serviceFinder(classLoader, new Creator()
/*     */         {
/*     */           public MessageContext create(MessageContextFactory f) {
/* 125 */             return f.doCreate(m, v);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private static MessageContext serviceFinder(ClassLoader[] classLoader, Creator creator) {
/* 132 */     ClassLoader cl = (classLoader.length == 0) ? null : classLoader[0];
/* 133 */     for (MessageContextFactory factory : ServiceFinder.find(MessageContextFactory.class, cl)) {
/* 134 */       MessageContext messageContext = creator.create(factory);
/* 135 */       if (messageContext != null)
/* 136 */         return messageContext; 
/*     */     } 
/* 138 */     return creator.create(DEFAULT);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private static interface Creator {
/*     */     MessageContext create(MessageContextFactory param1MessageContextFactory);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\message\MessageContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.CookieHandler;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpConfigFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://jax-ws.java.net/features/http-config";
/*     */   private static final Constructor cookieManagerConstructor;
/*     */   private static final Object cookiePolicy;
/*     */   private final CookieHandler cookieJar;
/*     */   
/*     */   static {
/*     */     Constructor<?> constructor;
/*     */     Object object;
/*     */     try {
/*  74 */       Class<?> policyClass = Class.forName("java.net.CookiePolicy");
/*  75 */       Class<?> storeClass = Class.forName("java.net.CookieStore");
/*  76 */       constructor = Class.forName("java.net.CookieManager").getConstructor(new Class[] { storeClass, policyClass });
/*     */ 
/*     */       
/*  79 */       object = policyClass.getField("ACCEPT_ALL").get(null);
/*  80 */     } catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/*  86 */         Class<?> policyClass = Class.forName("com.sun.xml.ws.transport.http.client.CookiePolicy");
/*  87 */         Class<?> storeClass = Class.forName("com.sun.xml.ws.transport.http.client.CookieStore");
/*  88 */         constructor = Class.forName("com.sun.xml.ws.transport.http.client.CookieManager").getConstructor(new Class[] { storeClass, policyClass });
/*     */ 
/*     */         
/*  91 */         object = policyClass.getField("ACCEPT_ALL").get(null);
/*  92 */       } catch (Exception ce) {
/*  93 */         throw new WebServiceException(ce);
/*     */       } 
/*     */     } 
/*  96 */     cookieManagerConstructor = constructor;
/*  97 */     cookiePolicy = object;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpConfigFeature() {
/* 103 */     this(getInternalCookieHandler());
/*     */   }
/*     */   
/*     */   public HttpConfigFeature(CookieHandler cookieJar) {
/* 107 */     this.enabled = true;
/* 108 */     this.cookieJar = cookieJar;
/*     */   }
/*     */   
/*     */   private static CookieHandler getInternalCookieHandler() {
/*     */     try {
/* 113 */       return cookieManagerConstructor.newInstance(new Object[] { null, cookiePolicy });
/* 114 */     } catch (Exception e) {
/* 115 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getID() {
/* 120 */     return "http://jax-ws.java.net/features/http-config";
/*     */   }
/*     */   
/*     */   public CookieHandler getCookieHandler() {
/* 124 */     return this.cookieJar;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\HttpConfigFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.ws.api.tx.at;
/*     */ 
/*     */ import java.lang.annotation.Documented;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.spi.WebServiceFeatureAnnotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
/*     */ @Documented
/*     */ @WebServiceFeatureAnnotation(id = "http://docs.oasis-open.org/ws-tx/", bean = TransactionalFeature.class)
/*     */ public @interface Transactional
/*     */ {
/*     */   boolean enabled() default true;
/*     */   
/*     */   TransactionFlowType value() default TransactionFlowType.SUPPORTS;
/*     */   
/*     */   Version version() default Version.DEFAULT;
/*     */   
/*     */   public enum TransactionFlowType
/*     */   {
/*  58 */     MANDATORY, SUPPORTS, NEVER;
/*     */   }
/*     */   
/*     */   public enum Version
/*     */   {
/*  63 */     WSAT10("wsat10", WsatNamespace.WSAT200410),
/*  64 */     WSAT11("wsat11", WsatNamespace.WSAT200606),
/*  65 */     WSAT12("wsat12", WsatNamespace.WSAT200606),
/*  66 */     DEFAULT("wsat", WsatNamespace.WSAT200606);
/*     */     
/*     */     public final QName qname;
/*     */     public final WsatNamespace namespaceVersion;
/*     */     
/*     */     Version(String prefix, WsatNamespace namespaceVersion) {
/*  72 */       this.namespaceVersion = namespaceVersion;
/*     */       
/*  74 */       this.qname = new QName((namespaceVersion != null) ? namespaceVersion.namespace : "", "ATAssertion", prefix);
/*     */     }
/*     */     
/*     */     public QName getQName() {
/*  78 */       return this.qname;
/*     */     }
/*     */     
/*     */     public static Version forNamespaceVersion(WsatNamespace nsVersion) {
/*  82 */       for (Version version : values()) {
/*  83 */         if (version != WSAT11 && version != DEFAULT)
/*     */         {
/*     */ 
/*     */           
/*  87 */           if (version.namespaceVersion == nsVersion)
/*  88 */             return version; 
/*     */         }
/*     */       } 
/*  91 */       return DEFAULT;
/*     */     }
/*     */     
/*     */     public static Version forNamespaceUri(String ns) {
/*  95 */       for (Version version : values()) {
/*  96 */         if (version != WSAT11 && version != DEFAULT)
/*     */         {
/*     */ 
/*     */           
/* 100 */           if (version.qname.getNamespaceURI().equals(ns))
/* 101 */             return version; 
/*     */         }
/*     */       } 
/* 104 */       return DEFAULT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\tx\at\Transactional.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
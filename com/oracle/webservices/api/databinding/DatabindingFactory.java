/*     */ package com.oracle.webservices.api.databinding;
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DatabindingFactory
/*     */ {
/*     */   static final String ImplClass = "com.sun.xml.ws.db.DatabindingFactoryImpl";
/*     */   
/*     */   public abstract Databinding.Builder createBuilder(Class<?> paramClass1, Class<?> paramClass2);
/*     */   
/*     */   public abstract Map<String, Object> properties();
/*     */   
/*     */   public static DatabindingFactory newInstance() {
/*     */     try {
/* 108 */       Class<?> cls = Class.forName("com.sun.xml.ws.db.DatabindingFactoryImpl");
/* 109 */       return convertIfNecessary(cls);
/* 110 */     } catch (Exception e) {
/* 111 */       e.printStackTrace();
/*     */       
/* 113 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DatabindingFactory convertIfNecessary(Class<?> cls) throws InstantiationException, IllegalAccessException {
/* 118 */     return (DatabindingFactory)cls.newInstance();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\databinding\DatabindingFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
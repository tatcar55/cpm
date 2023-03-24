/*     */ package com.sun.xml.ws.api.databinding;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.Databinding;
/*     */ import com.oracle.webservices.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.ws.db.DatabindingFactoryImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends DatabindingFactory
/*     */ {
/* 117 */   static final String ImplClass = DatabindingFactoryImpl.class.getName();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Databinding createRuntime(DatabindingConfig paramDatabindingConfig);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Map<String, Object> properties();
/*     */ 
/*     */ 
/*     */   
/*     */   public static DatabindingFactory newInstance() {
/*     */     try {
/* 131 */       Class<?> cls = Class.forName(ImplClass);
/* 132 */       return (DatabindingFactory)cls.newInstance();
/* 133 */     } catch (Exception e) {
/* 134 */       e.printStackTrace();
/*     */       
/* 136 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\DatabindingFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
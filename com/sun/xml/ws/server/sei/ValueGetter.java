/*     */ package com.sun.xml.ws.server.sei;
/*     */ 
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.ws.Holder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ValueGetter
/*     */ {
/*  69 */   PLAIN {
/*     */     public Object get(Object parameter) {
/*  71 */       return parameter;
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   HOLDER {
/*     */     public Object get(Object parameter) {
/*  84 */       if (parameter == null)
/*     */       {
/*  86 */         return null; } 
/*  87 */       return ((Holder)parameter).value;
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ValueGetter get(ParameterImpl p) {
/* 101 */     if (p.getMode() == WebParam.Mode.IN || p.getIndex() == -1) {
/* 102 */       return PLAIN;
/*     */     }
/* 104 */     return HOLDER;
/*     */   }
/*     */   
/*     */   public abstract Object get(Object paramObject);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\ValueGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.ws.server.sei;
/*     */ 
/*     */ import com.sun.xml.ws.model.ParameterImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EndpointValueSetter
/*     */ {
/*     */   private EndpointValueSetter() {}
/*     */   
/*  83 */   private static final EndpointValueSetter[] POOL = new EndpointValueSetter[16];
/*     */   
/*     */   static {
/*  86 */     for (int i = 0; i < POOL.length; i++) {
/*  87 */       POOL[i] = new Param(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EndpointValueSetter get(ParameterImpl p) {
/*  94 */     int idx = p.getIndex();
/*  95 */     if (p.isIN()) {
/*  96 */       if (idx < POOL.length) {
/*  97 */         return POOL[idx];
/*     */       }
/*  99 */       return new Param(idx);
/*     */     } 
/*     */     
/* 102 */     return new HolderParam(idx);
/*     */   }
/*     */   
/*     */   abstract void put(Object paramObject, Object[] paramArrayOfObject);
/*     */   
/*     */   static class Param
/*     */     extends EndpointValueSetter
/*     */   {
/*     */     protected final int idx;
/*     */     
/*     */     public Param(int idx) {
/* 113 */       this.idx = idx;
/*     */     }
/*     */     
/*     */     void put(Object obj, Object[] args) {
/* 117 */       if (obj != null)
/* 118 */         args[this.idx] = obj; 
/*     */     }
/*     */   }
/*     */   
/*     */   static final class HolderParam
/*     */     extends Param
/*     */   {
/*     */     public HolderParam(int idx) {
/* 126 */       super(idx);
/*     */     }
/*     */ 
/*     */     
/*     */     void put(Object obj, Object[] args) {
/* 131 */       Holder holder = new Holder();
/* 132 */       if (obj != null) {
/* 133 */         holder.value = (T)obj;
/*     */       }
/* 135 */       args[this.idx] = holder;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\EndpointValueSetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
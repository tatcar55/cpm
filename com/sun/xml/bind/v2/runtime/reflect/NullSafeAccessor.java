/*    */ package com.sun.xml.bind.v2.runtime.reflect;
/*    */ 
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullSafeAccessor<B, V, P>
/*    */   extends Accessor<B, V>
/*    */ {
/*    */   private final Accessor<B, V> core;
/*    */   private final Lister<B, V, ?, P> lister;
/*    */   
/*    */   public NullSafeAccessor(Accessor<B, V> core, Lister<B, V, ?, P> lister) {
/* 59 */     super(core.getValueType());
/* 60 */     this.core = core;
/* 61 */     this.lister = lister;
/*    */   }
/*    */   
/*    */   public V get(B bean) throws AccessorException {
/* 65 */     V v = this.core.get(bean);
/* 66 */     if (v == null) {
/*    */       
/* 68 */       P pack = this.lister.startPacking(bean, this.core);
/* 69 */       this.lister.endPacking(pack, bean, this.core);
/* 70 */       v = this.core.get(bean);
/*    */     } 
/* 72 */     return v;
/*    */   }
/*    */   
/*    */   public void set(B bean, V value) throws AccessorException {
/* 76 */     this.core.set(bean, value);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\NullSafeAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
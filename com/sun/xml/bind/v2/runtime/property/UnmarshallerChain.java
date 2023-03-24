/*    */ package com.sun.xml.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UnmarshallerChain
/*    */ {
/* 63 */   private int offset = 0;
/*    */   
/*    */   public final JAXBContextImpl context;
/*    */   
/*    */   public UnmarshallerChain(JAXBContextImpl context) {
/* 68 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int allocateOffset() {
/* 75 */     return this.offset++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getScopeSize() {
/* 82 */     return this.offset;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\UnmarshallerChain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
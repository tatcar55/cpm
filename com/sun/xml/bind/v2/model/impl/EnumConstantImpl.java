/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.core.EnumConstant;
/*    */ import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
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
/*    */ class EnumConstantImpl<T, C, F, M>
/*    */   implements EnumConstant<T, C>
/*    */ {
/*    */   protected final String lexical;
/*    */   protected final EnumLeafInfoImpl<T, C, F, M> owner;
/*    */   protected final String name;
/*    */   protected final EnumConstantImpl<T, C, F, M> next;
/*    */   
/*    */   public EnumConstantImpl(EnumLeafInfoImpl<T, C, F, M> owner, String name, String lexical, EnumConstantImpl<T, C, F, M> next) {
/* 60 */     this.lexical = lexical;
/* 61 */     this.owner = owner;
/* 62 */     this.name = name;
/* 63 */     this.next = next;
/*    */   }
/*    */   
/*    */   public EnumLeafInfo<T, C> getEnclosingClass() {
/* 67 */     return this.owner;
/*    */   }
/*    */   
/*    */   public final String getLexicalValue() {
/* 71 */     return this.lexical;
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 75 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\EnumConstantImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
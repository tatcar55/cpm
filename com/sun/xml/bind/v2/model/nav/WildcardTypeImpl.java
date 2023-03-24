/*    */ package com.sun.xml.bind.v2.model.nav;
/*    */ 
/*    */ import java.lang.reflect.Type;
/*    */ import java.lang.reflect.WildcardType;
/*    */ import java.util.Arrays;
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
/*    */ final class WildcardTypeImpl
/*    */   implements WildcardType
/*    */ {
/*    */   private final Type[] ub;
/*    */   private final Type[] lb;
/*    */   
/*    */   public WildcardTypeImpl(Type[] ub, Type[] lb) {
/* 56 */     this.ub = ub;
/* 57 */     this.lb = lb;
/*    */   }
/*    */   
/*    */   public Type[] getUpperBounds() {
/* 61 */     return this.ub;
/*    */   }
/*    */   
/*    */   public Type[] getLowerBounds() {
/* 65 */     return this.lb;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 69 */     return Arrays.hashCode((Object[])this.lb) ^ Arrays.hashCode((Object[])this.ub);
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 73 */     if (obj instanceof WildcardType) {
/* 74 */       WildcardType that = (WildcardType)obj;
/* 75 */       return (Arrays.equals((Object[])that.getLowerBounds(), (Object[])this.lb) && Arrays.equals((Object[])that.getUpperBounds(), (Object[])this.ub));
/*    */     } 
/*    */     
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\nav\WildcardTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
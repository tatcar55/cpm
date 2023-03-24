/*    */ package com.sun.xml.rpc.processor.modeler.rmi;
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
/*    */ public final class ArrayType
/*    */   extends RmiType
/*    */ {
/*    */   RmiType elemType;
/*    */   
/*    */   ArrayType(String typeSig, RmiType elemType) {
/* 36 */     super(9, typeSig);
/* 37 */     this.elemType = elemType;
/*    */   }
/*    */   
/*    */   public RmiType getElementType() {
/* 41 */     return this.elemType;
/*    */   }
/*    */   
/*    */   public int getArrayDimension() {
/* 45 */     return this.elemType.getArrayDimension() + 1;
/*    */   }
/*    */   
/*    */   public String typeString(boolean abbrev) {
/* 49 */     String tmp = getElementType().typeString(abbrev) + "[]";
/* 50 */     return tmp;
/*    */   }
/*    */   
/*    */   public boolean isNillable() {
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\ArrayType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
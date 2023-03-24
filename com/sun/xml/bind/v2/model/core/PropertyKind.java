/*    */ package com.sun.xml.bind.v2.model.core;
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
/*    */ public enum PropertyKind
/*    */ {
/* 56 */   VALUE(true, false, 2147483647),
/* 57 */   ATTRIBUTE(false, false, 2147483647),
/* 58 */   ELEMENT(true, true, 0),
/* 59 */   REFERENCE(false, true, 1),
/* 60 */   MAP(false, true, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean canHaveXmlMimeType;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean isOrdered;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final int propertyIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   PropertyKind(boolean canHaveExpectedContentType, boolean isOrdered, int propertyIndex) {
/* 81 */     this.canHaveXmlMimeType = canHaveExpectedContentType;
/* 82 */     this.isOrdered = isOrdered;
/* 83 */     this.propertyIndex = propertyIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\PropertyKind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
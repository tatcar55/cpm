/*    */ package com.sun.xml.rpc.processor.model.literal;
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
/*    */ public class LiteralArrayType
/*    */   extends LiteralType
/*    */ {
/*    */   private LiteralType elementType;
/*    */   
/*    */   public LiteralType getElementType() {
/* 38 */     return this.elementType;
/*    */   }
/*    */   
/*    */   public void setElementType(LiteralType type) {
/* 42 */     this.elementType = type;
/*    */   }
/*    */   
/*    */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/* 46 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralArrayType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
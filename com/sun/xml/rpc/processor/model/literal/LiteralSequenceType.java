/*    */ package com.sun.xml.rpc.processor.model.literal;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class LiteralSequenceType
/*    */   extends LiteralStructuredType
/*    */ {
/*    */   public LiteralSequenceType() {}
/*    */   
/*    */   public LiteralSequenceType(QName name) {
/* 42 */     this(name, null);
/*    */   }
/*    */   
/*    */   public LiteralSequenceType(QName name, JavaStructureType javaType) {
/* 46 */     super(name, javaType);
/*    */   }
/*    */   
/*    */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/* 50 */     visitor.visit(this);
/*    */   }
/*    */   
/*    */   public void setUnwrapped(boolean unwrapped) {
/* 54 */     this.unwrapped = unwrapped;
/*    */   }
/*    */   
/*    */   public boolean isUnwrapped() {
/* 58 */     return this.unwrapped;
/*    */   }
/*    */   
/*    */   private boolean unwrapped = false;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralSequenceType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
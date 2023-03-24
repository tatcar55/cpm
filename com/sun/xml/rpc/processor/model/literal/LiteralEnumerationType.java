/*    */ package com.sun.xml.rpc.processor.model.literal;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaType;
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
/*    */ public class LiteralEnumerationType
/*    */   extends LiteralType
/*    */ {
/*    */   private LiteralType baseType;
/*    */   
/*    */   public LiteralEnumerationType() {}
/*    */   
/*    */   public LiteralEnumerationType(QName name, LiteralType baseType, JavaType javaType) {
/* 44 */     super(name, javaType);
/* 45 */     this.baseType = baseType;
/*    */   }
/*    */   
/*    */   public LiteralType getBaseType() {
/* 49 */     return this.baseType;
/*    */   }
/*    */   
/*    */   public void setBaseType(LiteralType t) {
/* 53 */     this.baseType = t;
/*    */   }
/*    */   
/*    */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/* 57 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralEnumerationType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
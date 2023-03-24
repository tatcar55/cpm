/*    */ package com.sun.xml.rpc.processor.model.literal;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
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
/*    */ public class LiteralSimpleType
/*    */   extends LiteralType
/*    */ {
/*    */   public LiteralSimpleType() {}
/*    */   
/*    */   public LiteralSimpleType(QName name) {
/* 42 */     this(name, null);
/*    */   }
/*    */   
/*    */   public LiteralSimpleType(QName name, JavaSimpleType javaType) {
/* 46 */     super(name, (JavaType)javaType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LiteralSimpleType(QName name, JavaSimpleType javaType, boolean nillable) {
/* 52 */     super(name, (JavaType)javaType);
/* 53 */     setNillable(nillable);
/*    */   }
/*    */   
/*    */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/* 57 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralSimpleType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
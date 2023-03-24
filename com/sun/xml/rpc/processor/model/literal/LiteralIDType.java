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
/*    */ public class LiteralIDType
/*    */   extends LiteralType
/*    */ {
/*    */   private boolean resolveIDREF;
/*    */   
/*    */   public LiteralIDType() {}
/*    */   
/*    */   public LiteralIDType(QName name) {
/* 42 */     this(name, null);
/*    */   }
/*    */   
/*    */   public LiteralIDType(QName name, JavaSimpleType javaType) {
/* 46 */     this(name, javaType, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LiteralIDType(QName name, JavaSimpleType javaType, boolean resolveIDREF) {
/* 52 */     super(name, (JavaType)javaType);
/* 53 */     this.resolveIDREF = resolveIDREF;
/*    */   }
/*    */   
/*    */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/* 57 */     visitor.visit(this);
/*    */   }
/*    */   
/*    */   public boolean getResolveIDREF() {
/* 61 */     return this.resolveIDREF;
/*    */   }
/*    */   
/*    */   public void setResolveIDREF(boolean resolveIDREF) {
/* 65 */     this.resolveIDREF = resolveIDREF;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralIDType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
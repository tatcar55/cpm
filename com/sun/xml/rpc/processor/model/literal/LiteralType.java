/*    */ package com.sun.xml.rpc.processor.model.literal;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.AbstractType;
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
/*    */ public abstract class LiteralType
/*    */   extends AbstractType
/*    */ {
/*    */   private QName _schemaTypeRef;
/*    */   
/*    */   protected LiteralType() {}
/*    */   
/*    */   protected LiteralType(QName name, JavaType javaType) {
/* 43 */     super(name, javaType);
/*    */   }
/*    */   
/*    */   public QName getSchemaTypeRef() {
/* 47 */     return this._schemaTypeRef;
/*    */   }
/*    */   
/*    */   public void setSchemaTypeRef(QName n) {
/* 51 */     this._schemaTypeRef = n;
/*    */   }
/*    */   
/*    */   public boolean isLiteralType() {
/* 55 */     return true;
/*    */   }
/*    */   
/*    */   public void setNillable(boolean nillable) {
/* 59 */     this.isNillable = nillable;
/*    */   }
/*    */   
/*    */   public boolean isNillable() {
/* 63 */     return this.isNillable;
/*    */   }
/*    */   
/*    */   protected boolean isNillable = false;
/*    */   
/*    */   public abstract void accept(LiteralTypeVisitor paramLiteralTypeVisitor) throws Exception;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
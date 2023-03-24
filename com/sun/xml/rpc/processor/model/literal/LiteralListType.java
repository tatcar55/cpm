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
/*    */ public class LiteralListType
/*    */   extends LiteralType
/*    */ {
/*    */   private LiteralType itemType;
/*    */   
/*    */   public LiteralListType() {}
/*    */   
/*    */   public LiteralListType(QName name, LiteralType itemType, JavaType javaType) {
/* 44 */     super(name, javaType);
/* 45 */     this.itemType = itemType;
/*    */   }
/*    */   
/*    */   public LiteralType getItemType() {
/* 49 */     return this.itemType;
/*    */   }
/*    */   
/*    */   public void setItemType(LiteralType t) {
/* 53 */     this.itemType = t;
/*    */   }
/*    */   
/*    */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/* 57 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralListType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
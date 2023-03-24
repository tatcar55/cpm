/*    */ package com.sun.xml.rpc.processor.model.soap;
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
/*    */ public class SOAPListType
/*    */   extends SOAPType
/*    */ {
/*    */   private SOAPType itemType;
/*    */   
/*    */   public SOAPListType() {}
/*    */   
/*    */   public SOAPListType(QName name, SOAPType itemType, JavaType javaType) {
/* 42 */     super(name, javaType);
/* 43 */     this.itemType = itemType;
/*    */   }
/*    */   
/*    */   public SOAPType getItemType() {
/* 47 */     return this.itemType;
/*    */   }
/*    */   
/*    */   public void setItemType(SOAPType t) {
/* 51 */     this.itemType = t;
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 55 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPListType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
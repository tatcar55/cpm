/*    */ package com.sun.xml.rpc.processor.model.java;
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
/*    */ public class JavaArrayType
/*    */   extends JavaType
/*    */ {
/*    */   private String elementName;
/*    */   private JavaType elementType;
/*    */   private String soapArrayHolderName;
/*    */   
/*    */   public JavaArrayType() {}
/*    */   
/*    */   public JavaArrayType(String name) {
/* 39 */     super(name, true, "null");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JavaArrayType(String name, String elementName, JavaType elementType) {
/* 45 */     super(name, true, "null");
/* 46 */     this.elementName = elementName;
/* 47 */     this.elementType = elementType;
/*    */   }
/*    */   
/*    */   public String getElementName() {
/* 51 */     return this.elementName;
/*    */   }
/*    */   
/*    */   public void setElementName(String name) {
/* 55 */     this.elementName = name;
/*    */   }
/*    */   
/*    */   public JavaType getElementType() {
/* 59 */     return this.elementType;
/*    */   }
/*    */   
/*    */   public void setElementType(JavaType type) {
/* 63 */     this.elementType = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSOAPArrayHolderName() {
/* 68 */     return this.soapArrayHolderName;
/*    */   }
/*    */   
/*    */   public void setSOAPArrayHolderName(String holderName) {
/* 72 */     this.soapArrayHolderName = holderName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaArrayType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ 
/*    */ 
/*    */ public class JavaEnumerationEntry
/*    */ {
/*    */   private String name;
/*    */   private Object value;
/*    */   private String literalValue;
/*    */   
/*    */   public JavaEnumerationEntry() {}
/*    */   
/*    */   public JavaEnumerationEntry(String name, Object value, String literalValue) {
/* 40 */     this.name = name;
/* 41 */     this.value = value;
/* 42 */     this.literalValue = literalValue;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 46 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String s) {
/* 50 */     this.name = s;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 54 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(Object obj) {
/* 58 */     this.value = obj;
/*    */   }
/*    */   
/*    */   public String getLiteralValue() {
/* 62 */     return this.literalValue;
/*    */   }
/*    */   
/*    */   public void setLiteralValue(String s) {
/* 66 */     this.literalValue = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaEnumerationEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
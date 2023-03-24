/*    */ package com.sun.xml.rpc.processor.model.java;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.TypeMappingInfo;
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
/*    */ public class JavaCustomType
/*    */   extends JavaType
/*    */ {
/*    */   private TypeMappingInfo typeMappingInfo;
/*    */   
/*    */   public JavaCustomType() {}
/*    */   
/*    */   public JavaCustomType(String name) {
/* 40 */     super(name, true, null);
/*    */   }
/*    */   
/*    */   public JavaCustomType(String name, TypeMappingInfo typeMappingInfo) {
/* 44 */     super(name, true, null);
/* 45 */     this.typeMappingInfo = typeMappingInfo;
/*    */   }
/*    */   
/*    */   public TypeMappingInfo getTypeMappingInfo() {
/* 49 */     return this.typeMappingInfo;
/*    */   }
/*    */   
/*    */   public void setTypeMappingInfo(TypeMappingInfo typeMappingInfo) {
/* 53 */     this.typeMappingInfo = typeMappingInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaCustomType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
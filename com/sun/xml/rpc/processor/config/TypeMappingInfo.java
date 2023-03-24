/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
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
/*    */ public class TypeMappingInfo
/*    */ {
/*    */   private String encodingStyle;
/*    */   private QName xmlType;
/*    */   private String javaTypeName;
/*    */   private String serializerFactoryName;
/*    */   private String deserializerFactoryName;
/*    */   
/*    */   public TypeMappingInfo() {}
/*    */   
/*    */   public TypeMappingInfo(String encodingStyle, QName xmlType, String javaTypeName, String serializerFactoryName, String deserializerFactoryName) {
/* 45 */     this.encodingStyle = encodingStyle;
/* 46 */     this.xmlType = xmlType;
/* 47 */     this.javaTypeName = javaTypeName;
/* 48 */     this.serializerFactoryName = serializerFactoryName;
/* 49 */     this.deserializerFactoryName = deserializerFactoryName;
/*    */   }
/*    */   
/*    */   public String getEncodingStyle() {
/* 53 */     return this.encodingStyle;
/*    */   }
/*    */   
/*    */   public void setEncodingStyle(String s) {
/* 57 */     this.encodingStyle = s;
/*    */   }
/*    */   
/*    */   public QName getXMLType() {
/* 61 */     return this.xmlType;
/*    */   }
/*    */   
/*    */   public void setXMLType(QName n) {
/* 65 */     this.xmlType = n;
/*    */   }
/*    */   
/*    */   public String getJavaTypeName() {
/* 69 */     return this.javaTypeName;
/*    */   }
/*    */   
/*    */   public void setJavaTypeName(String s) {
/* 73 */     this.javaTypeName = s;
/*    */   }
/*    */   
/*    */   public String getSerializerFactoryName() {
/* 77 */     return this.serializerFactoryName;
/*    */   }
/*    */   
/*    */   public void setSerializerFactoryName(String s) {
/* 81 */     this.serializerFactoryName = s;
/*    */   }
/*    */   
/*    */   public String getDeserializerFactoryName() {
/* 85 */     return this.deserializerFactoryName;
/*    */   }
/*    */   
/*    */   public void setDeserializerFactoryName(String s) {
/* 89 */     this.deserializerFactoryName = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\TypeMappingInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
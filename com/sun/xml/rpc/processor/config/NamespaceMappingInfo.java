/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.tools.NamespaceMappingInfo;
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
/*    */ public class NamespaceMappingInfo
/*    */   implements NamespaceMappingInfo
/*    */ {
/*    */   private String namespaceURI;
/*    */   private String javaPackageName;
/*    */   
/*    */   public NamespaceMappingInfo(String namespaceURI, String javaPackageName) {
/* 37 */     this.namespaceURI = namespaceURI;
/* 38 */     this.javaPackageName = javaPackageName;
/*    */   }
/*    */   
/*    */   public String getNamespaceURI() {
/* 42 */     return this.namespaceURI;
/*    */   }
/*    */   
/*    */   public String getJavaPackageName() {
/* 46 */     return this.javaPackageName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\NamespaceMappingInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
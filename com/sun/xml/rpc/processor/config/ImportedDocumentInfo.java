/*    */ package com.sun.xml.rpc.processor.config;
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
/*    */ public class ImportedDocumentInfo
/*    */ {
/*    */   public static final int UNKNOWN_DOCUMENT = 0;
/*    */   public static final int SCHEMA_DOCUMENT = 1;
/*    */   public static final int WSDL_DOCUMENT = 2;
/*    */   private int type;
/*    */   private String namespace;
/*    */   private String location;
/*    */   
/*    */   public ImportedDocumentInfo() {}
/*    */   
/*    */   public ImportedDocumentInfo(int type) {
/* 42 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 46 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(int i) {
/* 50 */     this.type = i;
/*    */   }
/*    */   
/*    */   public String getNamespace() {
/* 54 */     return this.namespace;
/*    */   }
/*    */   
/*    */   public void setNamespace(String s) {
/* 58 */     this.namespace = s;
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 62 */     return this.location;
/*    */   }
/*    */   
/*    */   public void setLocation(String s) {
/* 66 */     this.location = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\ImportedDocumentInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
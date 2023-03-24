/*    */ package com.sun.xml.ws.assembler;
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
/*    */ public class MetroConfigNameImpl
/*    */   implements MetroConfigName
/*    */ {
/*    */   private final String defaultFileName;
/*    */   private final String appFileName;
/*    */   
/*    */   public MetroConfigNameImpl(String defaultFileName, String appFileName) {
/* 54 */     this.defaultFileName = defaultFileName;
/* 55 */     this.appFileName = appFileName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDefaultFileName() {
/* 60 */     return this.defaultFileName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAppFileName() {
/* 65 */     return this.appFileName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\MetroConfigNameImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
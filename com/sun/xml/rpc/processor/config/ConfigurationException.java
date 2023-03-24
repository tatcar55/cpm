/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.ProcessorException;
/*    */ import com.sun.xml.rpc.util.localization.Localizable;
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
/*    */ public class ConfigurationException
/*    */   extends ProcessorException
/*    */ {
/*    */   public ConfigurationException(String key) {
/* 39 */     super(key);
/*    */   }
/*    */   
/*    */   public ConfigurationException(String key, String arg) {
/* 43 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ConfigurationException(String key, Object[] args) {
/* 47 */     super(key, args);
/*    */   }
/*    */   
/*    */   public ConfigurationException(String key, Localizable arg) {
/* 51 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 55 */     return "com.sun.xml.rpc.resources.configuration";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\ConfigurationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
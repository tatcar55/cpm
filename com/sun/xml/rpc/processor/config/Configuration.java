/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*    */ import com.sun.xml.rpc.spi.tools.Configuration;
/*    */ import com.sun.xml.rpc.spi.tools.ModelInfo;
/*    */ import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
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
/*    */ public class Configuration
/*    */   implements Configuration
/*    */ {
/*    */   private ProcessorEnvironment _env;
/*    */   private ModelInfo _modelInfo;
/*    */   
/*    */   public Configuration(ProcessorEnvironment env) {
/* 38 */     this._env = (ProcessorEnvironment)env;
/*    */   }
/*    */   
/*    */   public ModelInfo getModelInfo() {
/* 42 */     return this._modelInfo;
/*    */   }
/*    */   
/*    */   public void setModelInfo(ModelInfo i) {
/* 46 */     this._modelInfo = (ModelInfo)i;
/* 47 */     this._modelInfo.setParent(this);
/*    */   }
/*    */   
/*    */   public ProcessorEnvironment getEnvironment() {
/* 51 */     return (ProcessorEnvironment)this._env;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\Configuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
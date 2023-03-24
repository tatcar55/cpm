/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*    */ import com.sun.xml.rpc.processor.modeler.modelfile.ModelFileModeler;
/*    */ import com.sun.xml.rpc.spi.tools.ModelFileModelInfo;
/*    */ import java.util.Properties;
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
/*    */ public class ModelFileModelInfo
/*    */   extends ModelInfo
/*    */   implements ModelFileModelInfo
/*    */ {
/*    */   private String location;
/*    */   
/*    */   public String getLocation() {
/* 46 */     return this.location;
/*    */   }
/*    */   
/*    */   public void setLocation(String s) {
/* 50 */     this.location = s;
/*    */   }
/*    */   
/*    */   protected Modeler getModeler(Properties options) {
/* 54 */     return (Modeler)new ModelFileModeler(this, options);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\ModelFileModelInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
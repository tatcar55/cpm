/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*    */ import com.sun.xml.rpc.util.JAXRPCClassFactory;
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
/*    */ public class WSDLModelInfo
/*    */   extends ModelInfo
/*    */ {
/*    */   private String _location;
/*    */   
/*    */   protected Modeler getModeler(Properties options) {
/* 43 */     return (Modeler)JAXRPCClassFactory.newInstance().createWSDLModeler(this, options);
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 47 */     return this._location;
/*    */   }
/*    */   
/*    */   public void setLocation(String s) {
/* 51 */     this._location = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\WSDLModelInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
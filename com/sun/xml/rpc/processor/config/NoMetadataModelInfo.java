/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*    */ import com.sun.xml.rpc.processor.modeler.nometadata.NoMetadataModeler;
/*    */ import com.sun.xml.rpc.spi.tools.NoMetadataModelInfo;
/*    */ import java.util.Properties;
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
/*    */ public class NoMetadataModelInfo
/*    */   extends ModelInfo
/*    */   implements NoMetadataModelInfo
/*    */ {
/*    */   private String _location;
/*    */   private String _serviceInterfaceName;
/*    */   private String _interfaceName;
/*    */   private String _servantName;
/*    */   private QName _serviceName;
/*    */   private QName _portName;
/*    */   
/*    */   protected Modeler getModeler(Properties options) {
/* 46 */     return (Modeler)new NoMetadataModeler(this, options);
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 50 */     return this._location;
/*    */   }
/*    */   
/*    */   public void setLocation(String s) {
/* 54 */     this._location = s;
/*    */   }
/*    */   
/*    */   public String getServiceInterfaceName() {
/* 58 */     return this._serviceInterfaceName;
/*    */   }
/*    */   
/*    */   public void setServiceInterfaceName(String s) {
/* 62 */     this._serviceInterfaceName = s;
/*    */   }
/*    */   
/*    */   public String getInterfaceName() {
/* 66 */     return this._interfaceName;
/*    */   }
/*    */   
/*    */   public void setInterfaceName(String s) {
/* 70 */     this._interfaceName = s;
/*    */   }
/*    */   
/*    */   public String getServantName() {
/* 74 */     return this._servantName;
/*    */   }
/*    */   
/*    */   public void setServantName(String s) {
/* 78 */     this._servantName = s;
/*    */   }
/*    */   
/*    */   public QName getServiceName() {
/* 82 */     return this._serviceName;
/*    */   }
/*    */   
/*    */   public void setServiceName(QName n) {
/* 86 */     this._serviceName = n;
/*    */   }
/*    */   
/*    */   public QName getPortName() {
/* 90 */     return this._portName;
/*    */   }
/*    */   
/*    */   public void setPortName(QName n) {
/* 94 */     this._portName = n;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\NoMetadataModelInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
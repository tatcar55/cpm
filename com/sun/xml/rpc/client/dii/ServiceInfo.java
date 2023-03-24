/*    */ package com.sun.xml.rpc.client.dii;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ public class ServiceInfo
/*    */ {
/*    */   protected Map portInfoMap;
/*    */   protected String defaultNamespace;
/*    */   
/*    */   public ServiceInfo() {
/* 45 */     init();
/*    */   }
/*    */   
/*    */   protected void init() {
/* 49 */     this.portInfoMap = new HashMap<Object, Object>();
/* 50 */     this.defaultNamespace = "";
/*    */   }
/*    */   
/*    */   public void setDefaultNamespace(String namespace) {
/* 54 */     this.defaultNamespace = namespace;
/*    */   }
/*    */   
/*    */   public PortInfo getPortInfo(QName portName) {
/* 58 */     PortInfo port = (PortInfo)this.portInfoMap.get(portName);
/* 59 */     if (port == null) {
/* 60 */       port = new PortInfo(portName);
/* 61 */       port.setDefaultNamespace(this.defaultNamespace);
/* 62 */       this.portInfoMap.put(portName, port);
/*    */     } 
/*    */     
/* 65 */     return port;
/*    */   }
/*    */   
/*    */   public Iterator getPortNames() {
/* 69 */     return this.portInfoMap.keySet().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\ServiceInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
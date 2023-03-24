/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*    */ import com.sun.xml.rpc.processor.modeler.rmi.RmiModeler;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public class RmiModelInfo
/*    */   extends ModelInfo
/*    */ {
/*    */   private String targetNamespaceURI;
/*    */   private String typeNamespaceURI;
/* 44 */   private List interfaces = new ArrayList();
/*    */   private String javaPackageName;
/*    */   
/*    */   public String getTargetNamespaceURI() {
/* 48 */     return this.targetNamespaceURI;
/*    */   }
/*    */   private HandlerChainInfo clientHandlerChainInfo; private HandlerChainInfo serverHandlerChainInfo;
/*    */   public void setTargetNamespaceURI(String s) {
/* 52 */     this.targetNamespaceURI = s;
/*    */   }
/*    */   
/*    */   public String getTypeNamespaceURI() {
/* 56 */     return this.typeNamespaceURI;
/*    */   }
/*    */   
/*    */   public void setTypeNamespaceURI(String s) {
/* 60 */     this.typeNamespaceURI = s;
/*    */   }
/*    */   
/*    */   public String getJavaPackageName() {
/* 64 */     return this.javaPackageName;
/*    */   }
/*    */   
/*    */   public void setJavaPackageName(String s) {
/* 68 */     this.javaPackageName = s;
/*    */   }
/*    */   
/*    */   public void add(RmiInterfaceInfo i) {
/* 72 */     this.interfaces.add(i);
/* 73 */     i.setParent(this);
/*    */   }
/*    */   
/*    */   public Iterator getInterfaces() {
/* 77 */     return this.interfaces.iterator();
/*    */   }
/*    */   
/*    */   protected Modeler getModeler(Properties options) {
/* 81 */     return (Modeler)new RmiModeler(this, options);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\RmiModelInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
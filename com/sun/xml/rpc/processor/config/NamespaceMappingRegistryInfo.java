/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.tools.NamespaceMappingInfo;
/*    */ import com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo;
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
/*    */ public class NamespaceMappingRegistryInfo
/*    */   implements NamespaceMappingRegistryInfo
/*    */ {
/* 43 */   private Map namespaceMap = new HashMap<Object, Object>();
/* 44 */   private Map javaPackageNameMap = new HashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   public void addMapping(NamespaceMappingInfo i) {
/* 48 */     addMapping((NamespaceMappingInfo)i);
/*    */   }
/*    */   
/*    */   public void addMapping(NamespaceMappingInfo i) {
/* 52 */     this.namespaceMap.put(i.getNamespaceURI(), i);
/* 53 */     this.javaPackageNameMap.put(i.getJavaPackageName(), i);
/*    */   }
/*    */   
/*    */   public NamespaceMappingInfo getNamespaceMappingInfo(QName xmlType) {
/* 57 */     NamespaceMappingInfo i = (NamespaceMappingInfo)this.namespaceMap.get(xmlType.getNamespaceURI());
/*    */     
/* 59 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NamespaceMappingInfo getNamespaceMappingInfo(String javaPackageName) {
/* 65 */     NamespaceMappingInfo i = (NamespaceMappingInfo)this.javaPackageNameMap.get(javaPackageName);
/*    */     
/* 67 */     return i;
/*    */   }
/*    */   
/*    */   public Iterator getNamespaceMappings() {
/* 71 */     return this.namespaceMap.values().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\NamespaceMappingRegistryInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
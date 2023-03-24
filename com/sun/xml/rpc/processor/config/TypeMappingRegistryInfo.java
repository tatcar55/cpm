/*    */ package com.sun.xml.rpc.processor.config;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ public class TypeMappingRegistryInfo
/*    */ {
/* 44 */   private Map xmlTypeMap = new HashMap<Object, Object>();
/* 45 */   private Map javaTypeMap = new HashMap<Object, Object>();
/* 46 */   private Set extraTypeNames = new HashSet();
/* 47 */   private Map importedDocuments = new HashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   public void addMapping(TypeMappingInfo i) {
/* 51 */     this.xmlTypeMap.put(getKeyFor(i.getEncodingStyle(), i.getXMLType()), i);
/* 52 */     this.javaTypeMap.put(getKeyFor(i.getEncodingStyle(), i.getJavaTypeName()), i);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeMappingInfo getTypeMappingInfo(String encodingStyle, QName xmlType) {
/* 59 */     return (TypeMappingInfo)this.xmlTypeMap.get(getKeyFor(encodingStyle, xmlType));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeMappingInfo getTypeMappingInfo(String encodingStyle, String javaTypeName) {
/* 66 */     return (TypeMappingInfo)this.javaTypeMap.get(getKeyFor(encodingStyle, javaTypeName));
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator getExtraTypeNames() {
/* 71 */     return this.extraTypeNames.iterator();
/*    */   }
/*    */   
/*    */   public void addExtraTypeName(String s) {
/* 75 */     this.extraTypeNames.add(s);
/*    */   }
/*    */   
/*    */   public int getExtraTypeNamesCount() {
/* 79 */     return this.extraTypeNames.size();
/*    */   }
/*    */   
/*    */   public Iterator getImportedDocuments() {
/* 83 */     return this.importedDocuments.values().iterator();
/*    */   }
/*    */   
/*    */   public ImportedDocumentInfo getImportedDocument(String namespace) {
/* 87 */     return (ImportedDocumentInfo)this.importedDocuments.get(namespace);
/*    */   }
/*    */   
/*    */   public void addImportedDocument(ImportedDocumentInfo i) {
/* 91 */     this.importedDocuments.put(i.getNamespace(), i);
/*    */   }
/*    */   
/*    */   private String getKeyFor(String s, QName q) {
/* 95 */     return getKeyFor(s, q.toString());
/*    */   }
/*    */   
/*    */   private String getKeyFor(String s, String t) {
/* 99 */     return s + "***" + t;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\TypeMappingRegistryInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
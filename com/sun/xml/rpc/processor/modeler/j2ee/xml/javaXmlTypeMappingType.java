/*     */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class javaXmlTypeMappingType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setJavaType(javaTypeType javaType) {
/*  43 */     setElementValue("java-type", javaType);
/*     */   }
/*     */   
/*     */   public javaTypeType getJavaType() {
/*  47 */     return (javaTypeType)getElementValue("java-type", "javaTypeType");
/*     */   }
/*     */   
/*     */   public boolean removeJavaType() {
/*  51 */     return removeElement("java-type");
/*     */   }
/*     */   
/*     */   public void setRootTypeQname(xsdQNameType rootTypeQname) {
/*  55 */     setElementValue("root-type-qname", rootTypeQname);
/*     */   }
/*     */   
/*     */   public xsdQNameType getRootTypeQname() {
/*  59 */     return (xsdQNameType)getElementValue("root-type-qname", "xsdQNameType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeRootTypeQname() {
/*  65 */     return removeElement("root-type-qname");
/*     */   }
/*     */   
/*     */   public void setAnonymousTypeQname(string anonymousTypeQname) {
/*  69 */     setElementValue("anonymous-type-qname", anonymousTypeQname);
/*     */   }
/*     */   
/*     */   public string getAnonymousTypeQname() {
/*  73 */     return (string)getElementValue("anonymous-type-qname", "string");
/*     */   }
/*     */   
/*     */   public boolean removeAnonymousTypeQname() {
/*  77 */     return removeElement("anonymous-type-qname");
/*     */   }
/*     */   
/*     */   public void setQnameScope(qnameScopeType qnameScope) {
/*  81 */     setElementValue("qname-scope", qnameScope);
/*     */   }
/*     */   
/*     */   public qnameScopeType getQnameScope() {
/*  85 */     return (qnameScopeType)getElementValue("qname-scope", "qnameScopeType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeQnameScope() {
/*  91 */     return removeElement("qname-scope");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariableMapping(int index, variableMappingType variableMapping) {
/*  97 */     setElementValue(index, "variable-mapping", variableMapping);
/*     */   }
/*     */   
/*     */   public variableMappingType getVariableMapping(int index) {
/* 101 */     return (variableMappingType)getElementValue("variable-mapping", "variableMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVariableMappingCount() {
/* 108 */     return sizeOfElement("variable-mapping");
/*     */   }
/*     */   
/*     */   public boolean removeVariableMapping(int index) {
/* 112 */     return removeElement(index, "variable-mapping");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 116 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 120 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 124 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\javaXmlTypeMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
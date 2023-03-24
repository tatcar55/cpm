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
/*     */ public class variableMappingType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setJavaVariableName(string javaVariableName) {
/*  43 */     setElementValue("java-variable-name", javaVariableName);
/*     */   }
/*     */   
/*     */   public string getJavaVariableName() {
/*  47 */     return (string)getElementValue("java-variable-name", "string");
/*     */   }
/*     */   
/*     */   public boolean removeJavaVariableName() {
/*  51 */     return removeElement("java-variable-name");
/*     */   }
/*     */   
/*     */   public void setDataMember(emptyType dataMember) {
/*  55 */     setElementValue("data-member", dataMember);
/*     */   }
/*     */   
/*     */   public emptyType getDataMember() {
/*  59 */     return (emptyType)getElementValue("data-member", "emptyType");
/*     */   }
/*     */   
/*     */   public boolean removeDataMember() {
/*  63 */     return removeElement("data-member");
/*     */   }
/*     */   
/*     */   public void setXmlWildcard(emptyType xmlWildcard) {
/*  67 */     setElementValue("xml-wildcard", xmlWildcard);
/*     */   }
/*     */   
/*     */   public emptyType getXmlWildcard() {
/*  71 */     return (emptyType)getElementValue("xml-wildcard", "emptyType");
/*     */   }
/*     */   
/*     */   public boolean removeXmlWildcard() {
/*  75 */     return removeElement("xml-wildcard");
/*     */   }
/*     */   
/*     */   public void setXmlElementName(string xmlElementName) {
/*  79 */     setElementValue("xml-element-name", xmlElementName);
/*     */   }
/*     */   
/*     */   public string getXmlElementName() {
/*  83 */     return (string)getElementValue("xml-element-name", "string");
/*     */   }
/*     */   
/*     */   public boolean removeXmlElementName() {
/*  87 */     return removeElement("xml-element-name");
/*     */   }
/*     */   
/*     */   public void setXmlAttributeName(string xmlAttributeName) {
/*  91 */     setElementValue("xml-attribute-name", xmlAttributeName);
/*     */   }
/*     */   
/*     */   public string getXmlAttributeName() {
/*  95 */     return (string)getElementValue("xml-attribute-name", "string");
/*     */   }
/*     */   
/*     */   public boolean removeXmlAttributeName() {
/*  99 */     return removeElement("xml-attribute-name");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 103 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 107 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 111 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\variableMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
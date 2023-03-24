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
/*     */ public class envEntryType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setDescription(int index, descriptionType description) {
/*  43 */     setElementValue(index, "description", description);
/*     */   }
/*     */   
/*     */   public descriptionType getDescription(int index) {
/*  47 */     return (descriptionType)getElementValue("description", "descriptionType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDescriptionCount() {
/*  54 */     return sizeOfElement("description");
/*     */   }
/*     */   
/*     */   public boolean removeDescription(int index) {
/*  58 */     return removeElement(index, "description");
/*     */   }
/*     */   
/*     */   public void setEnvEntryName(jndiNameType envEntryName) {
/*  62 */     setElementValue("env-entry-name", envEntryName);
/*     */   }
/*     */   
/*     */   public jndiNameType getEnvEntryName() {
/*  66 */     return (jndiNameType)getElementValue("env-entry-name", "jndiNameType");
/*     */   }
/*     */   
/*     */   public boolean removeEnvEntryName() {
/*  70 */     return removeElement("env-entry-name");
/*     */   }
/*     */   
/*     */   public void setEnvEntryType(envEntryTypeValuesType envEntryTypeValuesType1) {
/*  74 */     setElementValue("env-entry-type", envEntryTypeValuesType1);
/*     */   }
/*     */   
/*     */   public envEntryTypeValuesType getEnvEntryType() {
/*  78 */     return (envEntryTypeValuesType)getElementValue("env-entry-type", "envEntryTypeValuesType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeEnvEntryType() {
/*  84 */     return removeElement("env-entry-type");
/*     */   }
/*     */   
/*     */   public void setEnvEntryValue(xsdStringType envEntryValue) {
/*  88 */     setElementValue("env-entry-value", envEntryValue);
/*     */   }
/*     */   
/*     */   public xsdStringType getEnvEntryValue() {
/*  92 */     return (xsdStringType)getElementValue("env-entry-value", "xsdStringType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeEnvEntryValue() {
/*  98 */     return removeElement("env-entry-value");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 102 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 106 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 110 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\envEntryType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
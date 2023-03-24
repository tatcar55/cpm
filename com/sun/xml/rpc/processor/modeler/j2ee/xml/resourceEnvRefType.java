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
/*     */ public class resourceEnvRefType
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
/*     */   public void setResourceEnvRefName(jndiNameType resourceEnvRefName) {
/*  62 */     setElementValue("resource-env-ref-name", resourceEnvRefName);
/*     */   }
/*     */   
/*     */   public jndiNameType getResourceEnvRefName() {
/*  66 */     return (jndiNameType)getElementValue("resource-env-ref-name", "jndiNameType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeResourceEnvRefName() {
/*  72 */     return removeElement("resource-env-ref-name");
/*     */   }
/*     */   
/*     */   public void setResourceEnvRefType(fullyQualifiedClassType fullyQualifiedClassType1) {
/*  76 */     setElementValue("resource-env-ref-type", fullyQualifiedClassType1);
/*     */   }
/*     */   
/*     */   public fullyQualifiedClassType getResourceEnvRefType() {
/*  80 */     return (fullyQualifiedClassType)getElementValue("resource-env-ref-type", "fullyQualifiedClassType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeResourceEnvRefType() {
/*  86 */     return removeElement("resource-env-ref-type");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeploymentExtension(int index, deploymentExtensionType deploymentExtension) {
/*  92 */     setElementValue(index, "deployment-extension", deploymentExtension);
/*     */   }
/*     */   
/*     */   public deploymentExtensionType getDeploymentExtension(int index) {
/*  96 */     return (deploymentExtensionType)getElementValue("deployment-extension", "deploymentExtensionType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeploymentExtensionCount() {
/* 103 */     return sizeOfElement("deployment-extension");
/*     */   }
/*     */   
/*     */   public boolean removeDeploymentExtension(int index) {
/* 107 */     return removeElement(index, "deployment-extension");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 111 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 115 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 119 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\resourceEnvRefType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public class resourceRefType
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
/*     */   public void setResRefName(jndiNameType resRefName) {
/*  62 */     setElementValue("res-ref-name", resRefName);
/*     */   }
/*     */   
/*     */   public jndiNameType getResRefName() {
/*  66 */     return (jndiNameType)getElementValue("res-ref-name", "jndiNameType");
/*     */   }
/*     */   
/*     */   public boolean removeResRefName() {
/*  70 */     return removeElement("res-ref-name");
/*     */   }
/*     */   
/*     */   public void setResType(fullyQualifiedClassType resType) {
/*  74 */     setElementValue("res-type", resType);
/*     */   }
/*     */   
/*     */   public fullyQualifiedClassType getResType() {
/*  78 */     return (fullyQualifiedClassType)getElementValue("res-type", "fullyQualifiedClassType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeResType() {
/*  84 */     return removeElement("res-type");
/*     */   }
/*     */   
/*     */   public void setResAuth(resAuthType resAuth) {
/*  88 */     setElementValue("res-auth", resAuth);
/*     */   }
/*     */   
/*     */   public resAuthType getResAuth() {
/*  92 */     return (resAuthType)getElementValue("res-auth", "resAuthType");
/*     */   }
/*     */   
/*     */   public boolean removeResAuth() {
/*  96 */     return removeElement("res-auth");
/*     */   }
/*     */   
/*     */   public void setResSharingScope(resSharingScopeType resSharingScope) {
/* 100 */     setElementValue("res-sharing-scope", resSharingScope);
/*     */   }
/*     */   
/*     */   public resSharingScopeType getResSharingScope() {
/* 104 */     return (resSharingScopeType)getElementValue("res-sharing-scope", "resSharingScopeType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeResSharingScope() {
/* 110 */     return removeElement("res-sharing-scope");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeploymentExtension(int index, deploymentExtensionType deploymentExtension) {
/* 116 */     setElementValue(index, "deployment-extension", deploymentExtension);
/*     */   }
/*     */   
/*     */   public deploymentExtensionType getDeploymentExtension(int index) {
/* 120 */     return (deploymentExtensionType)getElementValue("deployment-extension", "deploymentExtensionType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeploymentExtensionCount() {
/* 127 */     return sizeOfElement("deployment-extension");
/*     */   }
/*     */   
/*     */   public boolean removeDeploymentExtension(int index) {
/* 131 */     return removeElement(index, "deployment-extension");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 135 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 139 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 143 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\resourceRefType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
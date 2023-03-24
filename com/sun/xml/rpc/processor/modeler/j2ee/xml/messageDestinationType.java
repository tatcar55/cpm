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
/*     */ public class messageDestinationType
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
/*     */   public void setDisplayName(int index, displayNameType displayName) {
/*  62 */     setElementValue(index, "display-name", displayName);
/*     */   }
/*     */   
/*     */   public displayNameType getDisplayName(int index) {
/*  66 */     return (displayNameType)getElementValue("display-name", "displayNameType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayNameCount() {
/*  73 */     return sizeOfElement("display-name");
/*     */   }
/*     */   
/*     */   public boolean removeDisplayName(int index) {
/*  77 */     return removeElement(index, "display-name");
/*     */   }
/*     */   
/*     */   public void setIcon(int index, iconType icon) {
/*  81 */     setElementValue(index, "icon", icon);
/*     */   }
/*     */   
/*     */   public iconType getIcon(int index) {
/*  85 */     return (iconType)getElementValue("icon", "iconType", index);
/*     */   }
/*     */   
/*     */   public int getIconCount() {
/*  89 */     return sizeOfElement("icon");
/*     */   }
/*     */   
/*     */   public boolean removeIcon(int index) {
/*  93 */     return removeElement(index, "icon");
/*     */   }
/*     */   
/*     */   public void setMessageDestinationName(string messageDestinationName) {
/*  97 */     setElementValue("message-destination-name", messageDestinationName);
/*     */   }
/*     */   
/*     */   public string getMessageDestinationName() {
/* 101 */     return (string)getElementValue("message-destination-name", "string");
/*     */   }
/*     */   
/*     */   public boolean removeMessageDestinationName() {
/* 105 */     return removeElement("message-destination-name");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeploymentExtension(int index, deploymentExtensionType deploymentExtension) {
/* 111 */     setElementValue(index, "deployment-extension", deploymentExtension);
/*     */   }
/*     */   
/*     */   public deploymentExtensionType getDeploymentExtension(int index) {
/* 115 */     return (deploymentExtensionType)getElementValue("deployment-extension", "deploymentExtensionType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeploymentExtensionCount() {
/* 122 */     return sizeOfElement("deployment-extension");
/*     */   }
/*     */   
/*     */   public boolean removeDeploymentExtension(int index) {
/* 126 */     return removeElement(index, "deployment-extension");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 130 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 134 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 138 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\messageDestinationType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
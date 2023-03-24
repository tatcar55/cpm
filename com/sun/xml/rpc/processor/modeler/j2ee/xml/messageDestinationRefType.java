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
/*     */ public class messageDestinationRefType
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
/*     */   public void setMessageDestinationRefName(jndiNameType messageDestinationRefName) {
/*  62 */     setElementValue("message-destination-ref-name", messageDestinationRefName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public jndiNameType getMessageDestinationRefName() {
/*  68 */     return (jndiNameType)getElementValue("message-destination-ref-name", "jndiNameType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeMessageDestinationRefName() {
/*  74 */     return removeElement("message-destination-ref-name");
/*     */   }
/*     */   
/*     */   public void setMessageDestinationType(messageDestinationTypeType messageDestinationType) {
/*  78 */     setElementValue("message-destination-type", messageDestinationType);
/*     */   }
/*     */   
/*     */   public messageDestinationTypeType getMessageDestinationType() {
/*  82 */     return (messageDestinationTypeType)getElementValue("message-destination-type", "messageDestinationTypeType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeMessageDestinationType() {
/*  88 */     return removeElement("message-destination-type");
/*     */   }
/*     */   
/*     */   public void setMessageDestinationUsage(messageDestinationUsageType messageDestinationUsage) {
/*  92 */     setElementValue("message-destination-usage", messageDestinationUsage);
/*     */   }
/*     */   
/*     */   public messageDestinationUsageType getMessageDestinationUsage() {
/*  96 */     return (messageDestinationUsageType)getElementValue("message-destination-usage", "messageDestinationUsageType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeMessageDestinationUsage() {
/* 102 */     return removeElement("message-destination-usage");
/*     */   }
/*     */   
/*     */   public void setMessageDestinationLink(messageDestinationLinkType messageDestinationLink) {
/* 106 */     setElementValue("message-destination-link", messageDestinationLink);
/*     */   }
/*     */   
/*     */   public messageDestinationLinkType getMessageDestinationLink() {
/* 110 */     return (messageDestinationLinkType)getElementValue("message-destination-link", "messageDestinationLinkType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeMessageDestinationLink() {
/* 116 */     return removeElement("message-destination-link");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeploymentExtension(int index, deploymentExtensionType deploymentExtension) {
/* 122 */     setElementValue(index, "deployment-extension", deploymentExtension);
/*     */   }
/*     */   
/*     */   public deploymentExtensionType getDeploymentExtension(int index) {
/* 126 */     return (deploymentExtensionType)getElementValue("deployment-extension", "deploymentExtensionType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeploymentExtensionCount() {
/* 133 */     return sizeOfElement("deployment-extension");
/*     */   }
/*     */   
/*     */   public boolean removeDeploymentExtension(int index) {
/* 137 */     return removeElement(index, "deployment-extension");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 141 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 145 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 149 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\messageDestinationRefType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
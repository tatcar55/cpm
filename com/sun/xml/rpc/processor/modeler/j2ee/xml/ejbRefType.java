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
/*     */ public class ejbRefType
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
/*     */   public void setEjbRefName(ejbRefNameType ejbRefName) {
/*  62 */     setElementValue("ejb-ref-name", ejbRefName);
/*     */   }
/*     */   
/*     */   public ejbRefNameType getEjbRefName() {
/*  66 */     return (ejbRefNameType)getElementValue("ejb-ref-name", "ejbRefNameType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeEjbRefName() {
/*  72 */     return removeElement("ejb-ref-name");
/*     */   }
/*     */   
/*     */   public void setEjbRefType(ejbRefTypeType ejbRefTypeType1) {
/*  76 */     setElementValue("ejb-ref-type", ejbRefTypeType1);
/*     */   }
/*     */   
/*     */   public ejbRefTypeType getEjbRefType() {
/*  80 */     return (ejbRefTypeType)getElementValue("ejb-ref-type", "ejbRefTypeType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeEjbRefType() {
/*  86 */     return removeElement("ejb-ref-type");
/*     */   }
/*     */   
/*     */   public void setHome(homeType home) {
/*  90 */     setElementValue("home", home);
/*     */   }
/*     */   
/*     */   public homeType getHome() {
/*  94 */     return (homeType)getElementValue("home", "homeType");
/*     */   }
/*     */   
/*     */   public boolean removeHome() {
/*  98 */     return removeElement("home");
/*     */   }
/*     */   
/*     */   public void setRemote(remoteType remote) {
/* 102 */     setElementValue("remote", remote);
/*     */   }
/*     */   
/*     */   public remoteType getRemote() {
/* 106 */     return (remoteType)getElementValue("remote", "remoteType");
/*     */   }
/*     */   
/*     */   public boolean removeRemote() {
/* 110 */     return removeElement("remote");
/*     */   }
/*     */   
/*     */   public void setEjbLink(ejbLinkType ejbLink) {
/* 114 */     setElementValue("ejb-link", ejbLink);
/*     */   }
/*     */   
/*     */   public ejbLinkType getEjbLink() {
/* 118 */     return (ejbLinkType)getElementValue("ejb-link", "ejbLinkType");
/*     */   }
/*     */   
/*     */   public boolean removeEjbLink() {
/* 122 */     return removeElement("ejb-link");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeploymentExtension(int index, deploymentExtensionType deploymentExtension) {
/* 128 */     setElementValue(index, "deployment-extension", deploymentExtension);
/*     */   }
/*     */   
/*     */   public deploymentExtensionType getDeploymentExtension(int index) {
/* 132 */     return (deploymentExtensionType)getElementValue("deployment-extension", "deploymentExtensionType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeploymentExtensionCount() {
/* 139 */     return sizeOfElement("deployment-extension");
/*     */   }
/*     */   
/*     */   public boolean removeDeploymentExtension(int index) {
/* 143 */     return removeElement(index, "deployment-extension");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 147 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 151 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 155 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\ejbRefType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
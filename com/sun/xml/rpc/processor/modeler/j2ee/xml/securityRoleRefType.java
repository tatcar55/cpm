/*    */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class securityRoleRefType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setDescription(int index, descriptionType description) {
/* 43 */     setElementValue(index, "description", description);
/*    */   }
/*    */   
/*    */   public descriptionType getDescription(int index) {
/* 47 */     return (descriptionType)getElementValue("description", "descriptionType", index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDescriptionCount() {
/* 54 */     return sizeOfElement("description");
/*    */   }
/*    */   
/*    */   public boolean removeDescription(int index) {
/* 58 */     return removeElement(index, "description");
/*    */   }
/*    */   
/*    */   public void setRoleName(roleNameType roleName) {
/* 62 */     setElementValue("role-name", roleName);
/*    */   }
/*    */   
/*    */   public roleNameType getRoleName() {
/* 66 */     return (roleNameType)getElementValue("role-name", "roleNameType");
/*    */   }
/*    */   
/*    */   public boolean removeRoleName() {
/* 70 */     return removeElement("role-name");
/*    */   }
/*    */   
/*    */   public void setRoleLink(roleNameType roleLink) {
/* 74 */     setElementValue("role-link", roleLink);
/*    */   }
/*    */   
/*    */   public roleNameType getRoleLink() {
/* 78 */     return (roleNameType)getElementValue("role-link", "roleNameType");
/*    */   }
/*    */   
/*    */   public boolean removeRoleLink() {
/* 82 */     return removeElement("role-link");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 86 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 90 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 94 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\securityRoleRefType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
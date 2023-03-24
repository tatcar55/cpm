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
/*    */ public class iconType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setSmallIcon(pathType smallIcon) {
/* 43 */     setElementValue("small-icon", smallIcon);
/*    */   }
/*    */   
/*    */   public pathType getSmallIcon() {
/* 47 */     return (pathType)getElementValue("small-icon", "pathType");
/*    */   }
/*    */   
/*    */   public boolean removeSmallIcon() {
/* 51 */     return removeElement("small-icon");
/*    */   }
/*    */   
/*    */   public void setLargeIcon(pathType largeIcon) {
/* 55 */     setElementValue("large-icon", largeIcon);
/*    */   }
/*    */   
/*    */   public pathType getLargeIcon() {
/* 59 */     return (pathType)getElementValue("large-icon", "pathType");
/*    */   }
/*    */   
/*    */   public boolean removeLargeIcon() {
/* 63 */     return removeElement("large-icon");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 67 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 71 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 75 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\iconType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
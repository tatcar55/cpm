/*    */ package com.sun.xml.rpc.wsdl.framework;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public abstract class Entity
/*    */   implements Elemental
/*    */ {
/*    */   private Map _properties;
/*    */   
/*    */   public Object getProperty(String key) {
/* 43 */     if (this._properties == null)
/* 44 */       return null; 
/* 45 */     return this._properties.get(key);
/*    */   }
/*    */   
/*    */   public void setProperty(String key, Object value) {
/* 49 */     if (value == null) {
/* 50 */       removeProperty(key);
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     if (this._properties == null) {
/* 55 */       this._properties = new HashMap<Object, Object>();
/*    */     }
/* 57 */     this._properties.put(key, value);
/*    */   }
/*    */   
/*    */   public void removeProperty(String key) {
/* 61 */     if (this._properties != null) {
/* 62 */       this._properties.remove(key);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void withAllSubEntitiesDo(EntityAction action) {}
/*    */ 
/*    */   
/*    */   public void withAllQNamesDo(QNameAction action) {
/* 71 */     action.perform(getElementName());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withAllEntityReferencesDo(EntityReferenceAction action) {}
/*    */ 
/*    */   
/*    */   public abstract void validateThis();
/*    */   
/*    */   protected void failValidation(String key) {
/* 81 */     throw new ValidationException(key, getElementName().getLocalPart());
/*    */   }
/*    */   
/*    */   protected void failValidation(String key, String arg) {
/* 85 */     throw new ValidationException(key, new Object[] { arg, getElementName().getLocalPart() });
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\Entity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
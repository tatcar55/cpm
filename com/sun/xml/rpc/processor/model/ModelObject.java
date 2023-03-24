/*    */ package com.sun.xml.rpc.processor.model;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.model.ModelObject;
/*    */ import com.sun.xml.rpc.util.NullIterator;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
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
/*    */ public abstract class ModelObject
/*    */   implements ModelObject
/*    */ {
/*    */   private Map _properties;
/*    */   
/*    */   public abstract void accept(ModelVisitor paramModelVisitor) throws Exception;
/*    */   
/*    */   public Object getProperty(String key) {
/* 45 */     if (this._properties == null) {
/* 46 */       return null;
/*    */     }
/* 48 */     return this._properties.get(key);
/*    */   }
/*    */   
/*    */   public void setProperty(String key, Object value) {
/* 52 */     if (value == null) {
/* 53 */       removeProperty(key);
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     if (this._properties == null) {
/* 58 */       this._properties = new HashMap<Object, Object>();
/*    */     }
/* 60 */     this._properties.put(key, value);
/*    */   }
/*    */   
/*    */   public void removeProperty(String key) {
/* 64 */     if (this._properties != null) {
/* 65 */       this._properties.remove(key);
/*    */     }
/*    */   }
/*    */   
/*    */   public Iterator getProperties() {
/* 70 */     if (this._properties == null) {
/* 71 */       return (Iterator)NullIterator.getInstance();
/*    */     }
/* 73 */     return this._properties.keySet().iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map getPropertiesMap() {
/* 79 */     return this._properties;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPropertiesMap(Map m) {
/* 84 */     this._properties = m;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\ModelObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
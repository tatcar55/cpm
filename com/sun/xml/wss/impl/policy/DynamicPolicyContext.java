/*    */ package com.sun.xml.wss.impl.policy;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
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
/*    */ public abstract class DynamicPolicyContext
/*    */ {
/* 56 */   protected HashMap properties = new HashMap<Object, Object>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object getProperty(String name) {
/* 64 */     return this.properties.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setProperty(String name, Object value) {
/* 74 */     this.properties.put(name, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void removeProperty(String name) {
/* 82 */     this.properties.remove(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean containsProperty(String name) {
/* 90 */     return this.properties.containsKey(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Iterator getPropertyNames() {
/* 97 */     return this.properties.keySet().iterator();
/*    */   }
/*    */   
/*    */   public abstract StaticPolicyContext getStaticPolicyContext();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\DynamicPolicyContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
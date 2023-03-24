/*    */ package com.sun.xml.rpc.processor.model.exporter;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public class PObject
/*    */ {
/*    */   private Map properties;
/*    */   private String type;
/*    */   
/*    */   public String getType() {
/* 44 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String s) {
/* 48 */     this.type = s;
/*    */   }
/*    */   
/*    */   public Object getProperty(String name) {
/* 52 */     if (this.properties == null) {
/* 53 */       return null;
/*    */     }
/* 55 */     return this.properties.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProperty(String name, Object value) {
/* 60 */     if (this.properties == null) {
/* 61 */       this.properties = new HashMap<Object, Object>();
/*    */     }
/* 63 */     this.properties.put(name, value);
/*    */   }
/*    */   
/*    */   public Iterator getProperties() {
/* 67 */     if (this.properties == null) {
/* 68 */       return (Iterator)NullIterator.getInstance();
/*    */     }
/* 70 */     return this.properties.values().iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator getPropertyNames() {
/* 75 */     if (this.properties == null) {
/* 76 */       return (Iterator)NullIterator.getInstance();
/*    */     }
/* 78 */     return this.properties.keySet().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\PObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
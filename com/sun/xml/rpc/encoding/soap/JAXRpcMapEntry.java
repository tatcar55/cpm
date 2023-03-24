/*    */ package com.sun.xml.rpc.encoding.soap;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class JAXRpcMapEntry
/*    */   implements Serializable
/*    */ {
/* 37 */   private Object key = null;
/* 38 */   private Object value = null;
/*    */ 
/*    */   
/*    */   public JAXRpcMapEntry() {}
/*    */   
/*    */   public JAXRpcMapEntry(Object key, Object value) {
/* 44 */     this.key = key;
/* 45 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Object getKey() {
/* 49 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(Object key) {
/* 53 */     this.key = key;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 57 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 61 */     this.value = value;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 65 */     if (this == obj)
/* 66 */       return true; 
/* 67 */     if (obj != null && getClass() == obj.getClass()) {
/* 68 */       JAXRpcMapEntry map_entry = (JAXRpcMapEntry)obj;
/* 69 */       return (((this.key == null && map_entry.key == null) || (this.key != null && this.key.equals(map_entry.key))) && ((this.value == null && map_entry.value == null) || (this.value != null && this.value.equals(map_entry.value))));
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\JAXRpcMapEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
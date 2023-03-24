/*    */ package com.sun.xml.security.core.dsig.runtime;
/*    */ 
/*    */ import javax.xml.bind.DatatypeConverter;
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*    */ public class ZeroOneBooleanAdapter
/*    */   extends XmlAdapter<String, Boolean>
/*    */ {
/*    */   public Boolean unmarshal(String v) {
/* 54 */     if (v == null) return null; 
/* 55 */     return Boolean.valueOf(DatatypeConverter.parseBoolean(v));
/*    */   }
/*    */   
/*    */   public String marshal(Boolean v) {
/* 59 */     if (v == null) return null; 
/* 60 */     if (v.booleanValue()) {
/* 61 */       return "1";
/*    */     }
/* 63 */     return "0";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\dsig\runtime\ZeroOneBooleanAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
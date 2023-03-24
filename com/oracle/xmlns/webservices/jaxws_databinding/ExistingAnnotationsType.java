/*    */ package com.oracle.xmlns.webservices.jaxws_databinding;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlType(name = "existing-annotations-type")
/*    */ @XmlEnum
/*    */ public enum ExistingAnnotationsType
/*    */ {
/* 69 */   MERGE("merge"),
/*    */   
/* 71 */   IGNORE("ignore");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   ExistingAnnotationsType(String v) {
/* 76 */     this.value = v;
/*    */   }
/*    */   
/*    */   public String value() {
/* 80 */     return this.value;
/*    */   }
/*    */   
/*    */   public static ExistingAnnotationsType fromValue(String v) {
/* 84 */     for (ExistingAnnotationsType c : values()) {
/* 85 */       if (c.value.equals(v)) {
/* 86 */         return c;
/*    */       }
/*    */     } 
/* 89 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\xmlns\webservices\jaxws_databinding\ExistingAnnotationsType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
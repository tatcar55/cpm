/*    */ package com.sun.xml.ws.tx.at.v10.types;
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
/*    */ @XmlType(name = "Outcome")
/*    */ @XmlEnum
/*    */ public enum Outcome
/*    */ {
/* 67 */   COMMIT("Commit"),
/*    */   
/* 69 */   ROLLBACK("Rollback");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   Outcome(String v) {
/* 74 */     this.value = v;
/*    */   }
/*    */   
/*    */   public String value() {
/* 78 */     return this.value;
/*    */   }
/*    */   
/*    */   public static Outcome fromValue(String v) {
/* 82 */     for (Outcome c : values()) {
/* 83 */       if (c.value.equals(v)) {
/* 84 */         return c;
/*    */       }
/*    */     } 
/* 87 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\types\Outcome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
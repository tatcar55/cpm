/*    */ package com.sun.xml.wss.saml.internal.saml20.jaxb20;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlEnum
/*    */ public enum DecisionType
/*    */ {
/* 74 */   PERMIT("Permit"),
/*    */   
/* 76 */   DENY("Deny"),
/*    */   
/* 78 */   INDETERMINATE("Indeterminate");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   DecisionType(String v) {
/* 83 */     this.value = v;
/*    */   }
/*    */   
/*    */   public String value() {
/* 87 */     return this.value;
/*    */   }
/*    */   
/*    */   public static DecisionType fromValue(String v) {
/* 91 */     for (DecisionType c : values()) {
/* 92 */       if (c.value.equals(v)) {
/* 93 */         return c;
/*    */       }
/*    */     } 
/* 96 */     throw new IllegalArgumentException(v.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml20\jaxb20\DecisionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
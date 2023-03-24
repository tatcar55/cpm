/*    */ package com.sun.xml.ws.config.metro.parser.jsr109;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlType(name = "isolation-levelType")
/*    */ @XmlEnum
/*    */ public enum IsolationLevelType
/*    */ {
/* 76 */   TRANSACTION_READ_UNCOMMITTED,
/* 77 */   TRANSACTION_READ_COMMITTED,
/* 78 */   TRANSACTION_REPEATABLE_READ,
/* 79 */   TRANSACTION_SERIALIZABLE;
/*    */   
/*    */   public java.lang.String value() {
/* 82 */     return name();
/*    */   }
/*    */   
/*    */   public static IsolationLevelType fromValue(java.lang.String v) {
/* 86 */     return valueOf(v);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\jsr109\IsolationLevelType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
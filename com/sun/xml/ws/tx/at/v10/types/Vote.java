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
/*    */ 
/*    */ @XmlType(name = "Vote")
/*    */ @XmlEnum
/*    */ public enum Vote
/*    */ {
/* 68 */   VOTE_COMMIT("VoteCommit"),
/*    */   
/* 70 */   VOTE_ROLLBACK("VoteRollback"),
/*    */   
/* 72 */   VOTE_READ_ONLY("VoteReadOnly");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   Vote(String v) {
/* 77 */     this.value = v;
/*    */   }
/*    */   
/*    */   public String value() {
/* 81 */     return this.value;
/*    */   }
/*    */   
/*    */   public static Vote fromValue(String v) {
/* 85 */     for (Vote c : values()) {
/* 86 */       if (c.value.equals(v)) {
/* 87 */         return c;
/*    */       }
/*    */     } 
/* 90 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\types\Vote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
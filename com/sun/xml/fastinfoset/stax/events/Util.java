/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static boolean isEmptyString(String s) {
/* 32 */     if (s != null && !s.equals("")) {
/* 33 */       return false;
/*    */     }
/* 35 */     return true;
/*    */   }
/*    */   
/*    */   public static final String getEventTypeString(int eventType) {
/* 39 */     switch (eventType) {
/*    */       case 1:
/* 41 */         return "START_ELEMENT";
/*    */       case 2:
/* 43 */         return "END_ELEMENT";
/*    */       case 3:
/* 45 */         return "PROCESSING_INSTRUCTION";
/*    */       case 4:
/* 47 */         return "CHARACTERS";
/*    */       case 5:
/* 49 */         return "COMMENT";
/*    */       case 7:
/* 51 */         return "START_DOCUMENT";
/*    */       case 8:
/* 53 */         return "END_DOCUMENT";
/*    */       case 9:
/* 55 */         return "ENTITY_REFERENCE";
/*    */       case 10:
/* 57 */         return "ATTRIBUTE";
/*    */       case 11:
/* 59 */         return "DTD";
/*    */       case 12:
/* 61 */         return "CDATA";
/*    */     } 
/* 63 */     return "UNKNOWN_EVENT_TYPE";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
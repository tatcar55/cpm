/*    */ package com.sun.xml.wss.impl.policy;
/*    */ 
/*    */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolicyUtils
/*    */ {
/*    */   public static boolean isEmpty(SecurityPolicy msgPolicy) {
/* 52 */     if (msgPolicy == null) {
/* 53 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 57 */     if (msgPolicy instanceof MessagePolicy)
/* 58 */       return ((MessagePolicy)msgPolicy).isEmpty(); 
/* 59 */     if (msgPolicy instanceof PolicyAlternatives) {
/* 60 */       PolicyAlternatives pol = (PolicyAlternatives)msgPolicy;
/* 61 */       return pol.isEmpty();
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\PolicyUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
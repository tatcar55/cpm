/*    */ package com.sun.xml.wss.impl.policy.mls;
/*    */ 
/*    */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*    */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SecureConversationTokenKeyBinding
/*    */   extends KeyBindingBase
/*    */ {
/*    */   public SecureConversationTokenKeyBinding() {
/* 61 */     setPolicyIdentifier("SecureConversationTokenKeyBinding");
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 65 */     SecureConversationTokenKeyBinding itb = new SecureConversationTokenKeyBinding();
/*    */     
/* 67 */     itb.setUUID(getUUID());
/* 68 */     itb.setIncludeToken(getIncludeToken());
/* 69 */     itb.setPolicyTokenFlag(policyTokenWasSet());
/* 70 */     return itb;
/*    */   }
/*    */   
/*    */   public boolean equals(WSSPolicy policy) {
/* 74 */     if (!PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)policy)) {
/* 75 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 79 */     return true;
/*    */   }
/*    */   
/*    */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 83 */     return equals(policy);
/*    */   }
/*    */   
/*    */   public String getType() {
/* 87 */     return "SecureConversationTokenKeyBinding";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\SecureConversationTokenKeyBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
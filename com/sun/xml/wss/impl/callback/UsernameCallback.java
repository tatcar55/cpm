/*    */ package com.sun.xml.wss.impl.callback;
/*    */ 
/*    */ import com.sun.xml.wss.impl.MessageConstants;
/*    */ import javax.security.auth.callback.Callback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UsernameCallback
/*    */   extends XWSSCallback
/*    */   implements Callback
/*    */ {
/*    */   private String username;
/*    */   
/*    */   public void setUsername(String username) {
/* 66 */     if (username == null || MessageConstants._EMPTY.equals(username)) {
/* 67 */       throw new RuntimeException("Username can not be empty or NULL");
/*    */     }
/* 69 */     this.username = username;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUsername() {
/* 78 */     return this.username;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\UsernameCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
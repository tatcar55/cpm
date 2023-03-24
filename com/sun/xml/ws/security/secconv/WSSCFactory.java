/*    */ package com.sun.xml.ws.security.secconv;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSSCFactory
/*    */ {
/*    */   public static WSSCPlugin newSCPlugin() {
/* 54 */     return new WSSCPlugin();
/*    */   }
/*    */   
/*    */   public static WSSCContract newWSSCContract(WSSCVersion wsscVer) {
/* 58 */     WSSCContract contract = new WSSCContract();
/* 59 */     contract.init(wsscVer);
/*    */     
/* 61 */     return contract;
/*    */   }
/*    */   
/*    */   public static WSSCClientContract newWSSCClientContract() {
/* 65 */     return new WSSCClientContract();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
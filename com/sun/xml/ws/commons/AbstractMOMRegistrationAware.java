/*    */ package com.sun.xml.ws.commons;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractMOMRegistrationAware
/*    */   implements MOMRegistrationAware
/*    */ {
/*    */   private boolean atMOM = false;
/*    */   
/*    */   public boolean isRegisteredAtMOM() {
/* 11 */     return this.atMOM;
/*    */   }
/*    */   
/*    */   public void setRegisteredAtMOM(boolean atMOM) {
/* 15 */     this.atMOM = atMOM;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\AbstractMOMRegistrationAware.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
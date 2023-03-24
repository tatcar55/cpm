/*    */ package com.sun.xml.ws.tx.at.common.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.common.CoordinatorIF;
/*    */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CoordinatorProxyBuilder<T>
/*    */   extends BaseProxyBuilder<T, CoordinatorProxyBuilder<T>>
/*    */ {
/*    */   protected CoordinatorProxyBuilder(WSATVersion<T> twsatVersion) {
/* 53 */     super(twsatVersion);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract CoordinatorIF<T> build();
/*    */   
/*    */   protected String getDefaultCallbackAddress() {
/* 60 */     return this.version.getWSATHelper().getParticipantAddress();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\client\CoordinatorProxyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
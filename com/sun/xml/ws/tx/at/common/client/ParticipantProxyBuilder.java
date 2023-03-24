/*    */ package com.sun.xml.ws.tx.at.common.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.common.ParticipantIF;
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
/*    */ public abstract class ParticipantProxyBuilder<T>
/*    */   extends BaseProxyBuilder<T, ParticipantProxyBuilder<T>>
/*    */ {
/*    */   protected ParticipantProxyBuilder(WSATVersion<T> version) {
/* 52 */     super(version);
/*    */   }
/*    */   
/*    */   protected String getDefaultCallbackAddress() {
/* 56 */     return this.version.getWSATHelper().getCoordinatorAddress();
/*    */   }
/*    */   
/*    */   public abstract ParticipantIF<T> build();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\client\ParticipantProxyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
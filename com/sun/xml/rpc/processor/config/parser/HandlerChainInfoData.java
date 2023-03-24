/*    */ package com.sun.xml.rpc.processor.config.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HandlerChainInfoData
/*    */ {
/*    */   private HandlerChainInfo client;
/*    */   private HandlerChainInfo server;
/*    */   
/*    */   public HandlerChainInfo getClientHandlerChainInfo() {
/* 41 */     return this.client;
/*    */   }
/*    */   
/*    */   public void setClientHandlerChainInfo(HandlerChainInfo i) {
/* 45 */     this.client = i;
/*    */   }
/*    */   
/*    */   public HandlerChainInfo getServerHandlerChainInfo() {
/* 49 */     return this.server;
/*    */   }
/*    */   
/*    */   public void setServerHandlerChainInfo(HandlerChainInfo i) {
/* 53 */     this.server = i;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\HandlerChainInfoData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
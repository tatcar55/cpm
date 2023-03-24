/*    */ package com.sun.xml.ws.rx.testing.filters;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.rx.testing.PacketFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RmEnabledCheckFilter
/*    */   extends PacketFilter
/*    */ {
/*    */   public Packet filterClientRequest(Packet request) throws Exception {
/* 59 */     checkRmVersion();
/*    */     
/* 61 */     return request;
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet filterServerResponse(Packet response) throws Exception {
/* 66 */     checkRmVersion();
/*    */     
/* 68 */     return response;
/*    */   }
/*    */   
/*    */   private void checkRmVersion() throws IllegalStateException {
/* 72 */     if (getRmVersion() == null)
/* 73 */       throw new IllegalStateException("Reliable Messaging is not enabled on this port!"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\testing\filters\RmEnabledCheckFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
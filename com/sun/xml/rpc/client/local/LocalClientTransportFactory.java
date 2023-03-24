/*    */ package com.sun.xml.rpc.client.local;
/*    */ 
/*    */ import com.sun.xml.rpc.client.ClientTransport;
/*    */ import com.sun.xml.rpc.client.ClientTransportFactory;
/*    */ import com.sun.xml.rpc.soap.message.Handler;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalClientTransportFactory
/*    */   implements ClientTransportFactory
/*    */ {
/*    */   private Handler _handler;
/*    */   private OutputStream _logStream;
/*    */   
/*    */   public LocalClientTransportFactory(Handler handler) {
/* 42 */     this._handler = handler;
/* 43 */     this._logStream = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalClientTransportFactory(Handler handler, OutputStream logStream) {
/* 49 */     this._handler = handler;
/* 50 */     this._logStream = logStream;
/*    */   }
/*    */   
/*    */   public ClientTransport create() {
/* 54 */     return new LocalClientTransport(this._handler, this._logStream);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\local\LocalClientTransportFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
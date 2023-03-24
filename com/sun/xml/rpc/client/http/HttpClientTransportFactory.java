/*    */ package com.sun.xml.rpc.client.http;
/*    */ 
/*    */ import com.sun.xml.rpc.client.ClientTransport;
/*    */ import com.sun.xml.rpc.client.ClientTransportFactory;
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
/*    */ public class HttpClientTransportFactory
/*    */   implements ClientTransportFactory
/*    */ {
/*    */   private OutputStream _logStream;
/*    */   
/*    */   public HttpClientTransportFactory() {
/* 40 */     this(null);
/*    */   }
/*    */   
/*    */   public HttpClientTransportFactory(OutputStream logStream) {
/* 44 */     this._logStream = logStream;
/*    */   }
/*    */   
/*    */   public ClientTransport create() {
/* 48 */     return new HttpClientTransport(this._logStream);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\http\HttpClientTransportFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
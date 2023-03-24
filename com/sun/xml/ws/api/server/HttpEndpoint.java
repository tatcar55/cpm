/*    */ package com.sun.xml.ws.api.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class HttpEndpoint
/*    */ {
/*    */   public static HttpEndpoint create(@NotNull WSEndpoint endpoint) {
/* 63 */     return (HttpEndpoint)new com.sun.xml.ws.transport.http.server.HttpEndpoint(null, HttpAdapter.createAlone(endpoint));
/*    */   }
/*    */   
/*    */   public abstract void publish(@NotNull String paramString);
/*    */   
/*    */   public abstract void stop();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\HttpEndpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.sun.xml.ws.transport.http.server;
/*    */ 
/*    */ import com.sun.xml.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*    */ import com.sun.xml.ws.transport.http.HttpAdapterList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerAdapterList
/*    */   extends HttpAdapterList<ServerAdapter>
/*    */ {
/*    */   protected ServerAdapter createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/* 49 */     return new ServerAdapter(name, urlPattern, endpoint, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\ServerAdapterList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.sun.xml.ws.transport.http.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*    */ import com.sun.xml.ws.api.server.Container;
/*    */ import com.sun.xml.ws.api.server.Module;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ServerContainer
/*    */   extends Container
/*    */ {
/* 57 */   private final Module module = new Module() {
/* 58 */       private final List<BoundEndpoint> endpoints = new ArrayList<BoundEndpoint>();
/*    */       @NotNull
/*    */       public List<BoundEndpoint> getBoundEndpoints() {
/* 61 */         return this.endpoints;
/*    */       }
/*    */     };
/*    */   
/*    */   public <T> T getSPI(Class<T> spiType) {
/* 66 */     T t = (T)super.getSPI(spiType);
/* 67 */     if (t != null)
/* 68 */       return t; 
/* 69 */     if (spiType == Module.class) {
/* 70 */       return spiType.cast(this.module);
/*    */     }
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\ServerContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
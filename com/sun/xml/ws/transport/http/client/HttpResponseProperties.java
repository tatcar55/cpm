/*    */ package com.sun.xml.ws.transport.http.client;
/*    */ 
/*    */ import com.oracle.webservices.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.api.message.PropertySet.Property;
/*    */ import com.sun.istack.NotNull;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class HttpResponseProperties
/*    */   extends BasePropertySet
/*    */ {
/*    */   private final HttpClientTransport deferedCon;
/*    */   
/*    */   public HttpResponseProperties(@NotNull HttpClientTransport con) {
/* 63 */     this.deferedCon = con;
/*    */   }
/*    */   
/*    */   @Property({"javax.xml.ws.http.response.headers"})
/*    */   public Map<String, List<String>> getResponseHeaders() {
/* 68 */     return this.deferedCon.getHeaders();
/*    */   }
/*    */   
/*    */   @Property({"javax.xml.ws.http.response.code"})
/*    */   public int getResponseCode() {
/* 73 */     return this.deferedCon.statusCode;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 78 */     return model;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   private static final BasePropertySet.PropertyMap model = parse(HttpResponseProperties.class);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\HttpResponseProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
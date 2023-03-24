/*    */ package com.sun.xml.ws.transport.tcp.server.glassfish;
/*    */ 
/*    */ import com.sun.enterprise.deployment.WebServiceEndpoint;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSEndpointDescriptor
/*    */ {
/*    */   private final QName wsServiceName;
/*    */   private final String uri;
/*    */   private final String contextRoot;
/*    */   private final String urlPattern;
/*    */   private final boolean isEJB;
/*    */   private final WebServiceEndpoint wsServiceEndpoint;
/*    */   private final String requestURL;
/*    */   
/*    */   public WSEndpointDescriptor(WebServiceEndpoint wsServiceDescriptor, String contextRoot, String urlPattern, String requestURL) {
/* 64 */     this.wsServiceName = wsServiceDescriptor.getServiceName();
/* 65 */     this.uri = wsServiceDescriptor.getEndpointAddressUri();
/* 66 */     this.isEJB = wsServiceDescriptor.implementedByEjbComponent();
/* 67 */     this.wsServiceEndpoint = wsServiceDescriptor;
/* 68 */     this.contextRoot = contextRoot;
/* 69 */     this.urlPattern = urlPattern;
/* 70 */     this.requestURL = requestURL;
/*    */   }
/*    */   
/*    */   public QName getWSServiceName() {
/* 74 */     return this.wsServiceName;
/*    */   }
/*    */   
/*    */   public String getURI() {
/* 78 */     return this.uri;
/*    */   }
/*    */   
/*    */   public String getContextRoot() {
/* 82 */     return this.contextRoot;
/*    */   }
/*    */   
/*    */   public String getRequestURL() {
/* 86 */     return this.requestURL;
/*    */   }
/*    */   
/*    */   public String getUrlPattern() {
/* 90 */     return this.urlPattern;
/*    */   }
/*    */   
/*    */   public WebServiceEndpoint getWSServiceEndpoint() {
/* 94 */     return this.wsServiceEndpoint;
/*    */   }
/*    */   
/*    */   public boolean isEJB() {
/* 98 */     return this.isEJB;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\WSEndpointDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.sun.xml.ws.client;
/*    */ 
/*    */ import com.sun.xml.ws.api.ResourceLoader;
/*    */ import com.sun.xml.ws.api.server.Container;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ClientContainer
/*    */   extends Container
/*    */ {
/* 54 */   private final ResourceLoader loader = new ResourceLoader() {
/*    */       public URL getResource(String resource) throws MalformedURLException {
/* 56 */         ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 57 */         if (cl == null) {
/* 58 */           cl = getClass().getClassLoader();
/*    */         }
/* 60 */         return cl.getResource("META-INF/" + resource);
/*    */       }
/*    */     };
/*    */   
/*    */   public <T> T getSPI(Class<T> spiType) {
/* 65 */     T t = (T)super.getSPI(spiType);
/* 66 */     if (t != null)
/* 67 */       return t; 
/* 68 */     if (spiType == ResourceLoader.class) {
/* 69 */       return spiType.cast(this.loader);
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\ClientContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.sun.xml.ws.transport.http.servlet;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.EventListener;
/*    */ import java.util.Set;
/*    */ import javax.jws.WebService;
/*    */ import javax.servlet.ServletContainerInitializer;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.annotation.HandlesTypes;
/*    */ import javax.xml.ws.WebServiceProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @HandlesTypes({WebService.class, WebServiceProvider.class})
/*    */ public class WSServletContainerInitializer
/*    */   implements ServletContainerInitializer
/*    */ {
/*    */   public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
/*    */     try {
/* 60 */       if (c != null && !c.isEmpty()) {
/* 61 */         URL sunJaxWsXml = ctx.getResource("/WEB-INF/sun-jaxws.xml");
/*    */         
/* 63 */         if (sunJaxWsXml != null) {
/* 64 */           WSServletContextListener listener = new WSServletContextListener();
/* 65 */           listener.parseAdaptersAndCreateDelegate(ctx);
/* 66 */           ctx.addListener((EventListener)listener);
/*    */         } 
/*    */       } 
/* 69 */     } catch (Exception e) {
/* 70 */       throw new ServletException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\WSServletContainerInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
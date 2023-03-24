/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletContextAttributeEvent;
/*     */ import javax.servlet.ServletContextAttributeListener;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSSPIContextListener
/*     */   implements ServletContextAttributeListener, ServletContextListener
/*     */ {
/*     */   private WSServletDelegate delegate;
/*     */   private static final String JAXWS_RI_RUNTIME = "/WEB-INF/sun-jaxws.xml";
/*     */   
/*     */   public void attributeAdded(ServletContextAttributeEvent event) {}
/*     */   
/*     */   public void attributeRemoved(ServletContextAttributeEvent event) {}
/*     */   
/*     */   public void attributeReplaced(ServletContextAttributeEvent event) {}
/*     */   
/*     */   public void contextDestroyed(ServletContextEvent event) {
/*  81 */     if (this.delegate != null) {
/*  82 */       this.delegate.destroy();
/*     */     }
/*     */     
/*  85 */     if (logger.isLoggable(Level.INFO)) {
/*  86 */       logger.info("JAX-WS context listener destroyed");
/*     */     }
/*     */   }
/*     */   
/*     */   public void contextInitialized(ServletContextEvent event) {
/*  91 */     if (logger.isLoggable(Level.INFO)) {
/*  92 */       logger.info("JAX-WS context listener initializing");
/*     */     }
/*  94 */     ServletContext context = event.getServletContext();
/*  95 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  96 */     if (classLoader == null) {
/*  97 */       classLoader = getClass().getClassLoader();
/*     */     }
/*     */     
/*     */     try {
/* 101 */       DeploymentDescriptorParser<EndpointAdapter> parser = new DeploymentDescriptorParser<EndpointAdapter>(classLoader, new ServletResourceLoader(context), new EndpointAdapterFactory());
/*     */       
/* 103 */       URL sunJaxWsXml = context.getResource("/WEB-INF/sun-jaxws.xml");
/* 104 */       if (sunJaxWsXml == null)
/* 105 */         throw new WebServiceException("Runtime descriptor /WEB-INF/sun-jaxws.xml is mising"); 
/* 106 */       List<EndpointAdapter> adapters = parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
/* 107 */       for (EndpointAdapter adapter : adapters) {
/* 108 */         adapter.publish();
/*     */       }
/*     */       
/* 111 */       this.delegate = createDelegate(adapters, context);
/*     */       
/* 113 */       context.setAttribute("com.sun.xml.ws.server.http.servletDelegate", this.delegate);
/*     */     }
/* 115 */     catch (Throwable e) {
/* 116 */       logger.log(Level.SEVERE, "failed to parse runtime descriptor", e);
/* 117 */       context.removeAttribute("com.sun.xml.ws.server.http.servletDelegate");
/* 118 */       throw new WebServiceException("failed to parse runtime descriptor", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSServletDelegate createDelegate(List<EndpointAdapter> adapters, ServletContext context) {
/* 126 */     return new WSServletDelegate(adapters, context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 131 */   private static final Logger logger = Logger.getLogger(WSSPIContextListener.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\WSSPIContextListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
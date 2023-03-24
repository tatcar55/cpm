/*     */ package com.sun.xml.ws.transport.tcp.server.servlet;
/*     */ 
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
/*     */ import com.sun.xml.ws.transport.http.ResourceLoader;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPAdapter;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPContext;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPResourceLoader;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPServletContext;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPModule;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletContextAttributeEvent;
/*     */ import javax.servlet.ServletContextAttributeListener;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public final class WSStartupServlet
/*     */   extends HttpServlet
/*     */   implements ServletContextAttributeListener, ServletContextListener
/*     */ {
/*  75 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */   
/*     */   private static final String JAXWS_RI_RUNTIME = "/WEB-INF/sun-jaxws.xml";
/*     */ 
/*     */   
/*     */   private WSTCPModule registry;
/*     */   
/*     */   private List<TCPAdapter> adapters;
/*     */ 
/*     */   
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}
/*     */ 
/*     */   
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}
/*     */ 
/*     */   
/*     */   public void contextInitialized(ServletContextEvent contextEvent) {
/*  93 */     logger.log(Level.FINE, "WSStartupServlet.contextInitialized");
/*  94 */     ServletContext servletContext = contextEvent.getServletContext();
/*  95 */     TCPServletContext tCPServletContext = new TCPServletContext(servletContext);
/*  96 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  97 */     if (classLoader == null) {
/*  98 */       classLoader = getClass().getClassLoader();
/*     */     }
/* 100 */     ServletContainer container = new ServletContainer(servletContext);
/*     */     
/*     */     try {
/* 103 */       this.registry = WSTCPModule.getInstance();
/* 104 */       DeploymentDescriptorParser<TCPAdapter> parser = new DeploymentDescriptorParser(classLoader, (ResourceLoader)new TCPResourceLoader((TCPContext)tCPServletContext), container, TCPAdapter.FACTORY);
/*     */       
/* 106 */       URL sunJaxWsXml = tCPServletContext.getResource("/WEB-INF/sun-jaxws.xml");
/* 107 */       if (sunJaxWsXml == null)
/* 108 */         throw new WebServiceException(MessagesMessages.WSTCP_0014_NO_JAXWS_DESCRIPTOR()); 
/* 109 */       this.adapters = parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
/*     */       
/* 111 */       this.registry.register(servletContext.getContextPath(), this.adapters);
/* 112 */     } catch (Exception e) {
/* 113 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 114 */       throw new IllegalStateException("listener.parsingFailed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void contextDestroyed(ServletContextEvent contextEvent) {
/* 119 */     logger.log(Level.FINE, "WSStartupServlet.contextDestroyed");
/* 120 */     if (this.registry != null && this.adapters != null) {
/* 121 */       this.registry.free(contextEvent.getServletContext().getContextPath(), this.adapters);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void attributeAdded(ServletContextAttributeEvent scab) {}
/*     */ 
/*     */   
/*     */   public void attributeRemoved(ServletContextAttributeEvent scab) {}
/*     */ 
/*     */   
/*     */   public void attributeReplaced(ServletContextAttributeEvent scab) {}
/*     */ 
/*     */   
/*     */   private static final class ServletContainer
/*     */     extends Container
/*     */   {
/*     */     private final ServletContext servletContext;
/*     */ 
/*     */     
/*     */     ServletContainer(ServletContext servletContext) {
/* 143 */       this.servletContext = servletContext;
/*     */     }
/*     */     
/*     */     public <T> T getSPI(Class<T> spiType) {
/* 147 */       if (spiType == ServletContext.class) {
/* 148 */         return (T)this.servletContext;
/*     */       }
/* 150 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\servlet\WSStartupServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
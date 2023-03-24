/*     */ package com.sun.xml.ws.transport.tcp.server.glassfish;
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
/*     */ 
/*     */ @Deprecated
/*     */ public final class WSStartupServlet
/*     */   extends HttpServlet
/*     */   implements ServletContextAttributeListener, ServletContextListener
/*     */ {
/*  77 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */   
/*     */   private static final String JAXWS_RI_RUNTIME = "/WEB-INF/sun-jaxws.xml";
/*     */ 
/*     */   
/*     */   private transient WSTCPModule transportModule;
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
/*  95 */     logger.log(Level.FINE, "WSStartupServlet.contextInitialized");
/*  96 */     ServletContext servletContext = contextEvent.getServletContext();
/*  97 */     TCPServletContext tCPServletContext = new TCPServletContext(servletContext);
/*  98 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  99 */     if (classLoader == null) {
/* 100 */       classLoader = getClass().getClassLoader();
/*     */     }
/* 102 */     ServletContainer container = new ServletContainer(servletContext);
/*     */     
/*     */     try {
/* 105 */       this.transportModule = WSTCPModule.getInstance();
/* 106 */       DeploymentDescriptorParser<TCPAdapter> parser = new DeploymentDescriptorParser(classLoader, (ResourceLoader)new TCPResourceLoader((TCPContext)tCPServletContext), container, TCPAdapter.FACTORY);
/*     */       
/* 108 */       URL sunJaxWsXml = tCPServletContext.getResource("/WEB-INF/sun-jaxws.xml");
/* 109 */       if (sunJaxWsXml == null)
/* 110 */         throw new WebServiceException(MessagesMessages.WSTCP_0014_NO_JAXWS_DESCRIPTOR()); 
/* 111 */       this.adapters = parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
/*     */       
/* 113 */       this.transportModule.register(servletContext.getContextPath(), this.adapters);
/* 114 */     } catch (Exception e) {
/* 115 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 116 */       throw new IllegalStateException("listener.parsingFailed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void contextDestroyed(ServletContextEvent contextEvent) {
/* 121 */     logger.log(Level.FINE, "WSStartupServlet.contextDestroyed");
/* 122 */     if (this.transportModule != null && this.adapters != null) {
/* 123 */       this.transportModule.free(contextEvent.getServletContext().getContextPath(), this.adapters);
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
/* 145 */       this.servletContext = servletContext;
/*     */     }
/*     */     
/*     */     public <T> T getSPI(Class<T> spiType) {
/* 149 */       if (spiType == ServletContext.class) {
/* 150 */         return (T)this.servletContext;
/*     */       }
/* 152 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\WSStartupServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
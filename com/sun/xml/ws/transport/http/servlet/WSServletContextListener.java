/*     */ package com.sun.xml.ws.transport.http.servlet;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletContextAttributeEvent;
/*     */ import javax.servlet.ServletContextAttributeListener;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import javax.servlet.ServletRegistration;
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
/*     */ public final class WSServletContextListener
/*     */   implements ServletContextAttributeListener, ServletContextListener
/*     */ {
/*     */   private WSServletDelegate delegate;
/*     */   private List<ServletAdapter> adapters;
/*  73 */   private final JAXWSRIDeploymentProbeProvider probe = new JAXWSRIDeploymentProbeProvider();
/*     */   
/*     */   private static final String WSSERVLET_CONTEXT_LISTENER_INVOKED = "com.sun.xml.ws.transport.http.servlet.WSServletContextListener.Invoked";
/*     */   
/*     */   static final String JAXWS_RI_RUNTIME = "/WEB-INF/sun-jaxws.xml";
/*     */ 
/*     */   
/*     */   public void attributeAdded(ServletContextAttributeEvent event) {}
/*     */   
/*     */   public void attributeRemoved(ServletContextAttributeEvent event) {}
/*     */   
/*     */   public void attributeReplaced(ServletContextAttributeEvent event) {}
/*     */   
/*     */   public void contextDestroyed(ServletContextEvent event) {
/*  87 */     if (this.delegate != null) {
/*  88 */       this.delegate.destroy();
/*     */     }
/*     */     
/*  91 */     if (this.adapters != null)
/*     */     {
/*  93 */       for (ServletAdapter a : this.adapters) {
/*     */         try {
/*  95 */           a.getEndpoint().dispose();
/*  96 */         } catch (Throwable e) {
/*  97 */           logger.log(Level.SEVERE, e.getMessage(), e);
/*     */         } 
/*     */ 
/*     */         
/* 101 */         this.probe.undeploy(a);
/*     */       } 
/*     */     }
/*     */     
/* 105 */     if (logger.isLoggable(Level.INFO)) {
/* 106 */       logger.info(WsservletMessages.LISTENER_INFO_DESTROY());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void parseAdaptersAndCreateDelegate(ServletContext context) {
/* 113 */     String alreadyInvoked = (String)context.getAttribute("com.sun.xml.ws.transport.http.servlet.WSServletContextListener.Invoked");
/* 114 */     if (Boolean.valueOf(alreadyInvoked).booleanValue()) {
/*     */       return;
/*     */     }
/* 117 */     context.setAttribute("com.sun.xml.ws.transport.http.servlet.WSServletContextListener.Invoked", "true");
/* 118 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 119 */     if (classLoader == null) {
/* 120 */       classLoader = getClass().getClassLoader();
/*     */     }
/*     */     try {
/* 123 */       URL sunJaxWsXml = context.getResource("/WEB-INF/sun-jaxws.xml");
/* 124 */       if (sunJaxWsXml == null) {
/* 125 */         throw new WebServiceException(WsservletMessages.NO_SUNJAXWS_XML("/WEB-INF/sun-jaxws.xml"));
/*     */       }
/*     */ 
/*     */       
/* 129 */       DeploymentDescriptorParser<ServletAdapter> parser = new DeploymentDescriptorParser(classLoader, new ServletResourceLoader(context), createContainer(context), (DeploymentDescriptorParser.AdapterFactory)new ServletAdapterList(context));
/*     */       
/* 131 */       this.adapters = parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
/* 132 */       registerWSServlet(this.adapters, context);
/* 133 */       this.delegate = createDelegate(this.adapters, context);
/*     */       
/* 135 */       context.setAttribute("com.sun.xml.ws.server.http.servletDelegate", this.delegate);
/*     */     }
/* 137 */     catch (Throwable e) {
/* 138 */       logger.log(Level.SEVERE, WsservletMessages.LISTENER_PARSING_FAILED(e), e);
/*     */       
/* 140 */       context.removeAttribute("com.sun.xml.ws.server.http.servletDelegate");
/* 141 */       throw new WSServletException("listener.parsingFailed", new Object[] { e });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void contextInitialized(ServletContextEvent event) {
/* 147 */     if (logger.isLoggable(Level.INFO)) {
/* 148 */       logger.info(WsservletMessages.LISTENER_INFO_INITIALIZE());
/*     */     }
/* 150 */     ServletContext context = event.getServletContext();
/*     */     
/* 152 */     parseAdaptersAndCreateDelegate(context);
/* 153 */     if (this.adapters != null)
/*     */     {
/* 155 */       for (ServletAdapter adapter : this.adapters) {
/* 156 */         this.probe.deploy(adapter);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerWSServlet(List<ServletAdapter> adapters, ServletContext context) {
/* 162 */     if (!ServletUtil.isServlet30Based())
/*     */       return; 
/* 164 */     Set<String> unregisteredUrlPatterns = new HashSet<String>();
/*     */     try {
/* 166 */       Collection<? extends ServletRegistration> registrations = context.getServletRegistrations().values();
/* 167 */       for (ServletAdapter adapter : adapters) {
/* 168 */         if (!existsServletForUrlPattern(adapter.urlPattern, registrations)) {
/* 169 */           unregisteredUrlPatterns.add(adapter.urlPattern);
/*     */         }
/*     */       } 
/* 172 */       if (!unregisteredUrlPatterns.isEmpty())
/*     */       {
/* 174 */         ServletRegistration.Dynamic registration = context.addServlet("Dynamic JAXWS Servlet", WSServlet.class);
/* 175 */         registration.addMapping(unregisteredUrlPatterns.<String>toArray(new String[unregisteredUrlPatterns.size()]));
/* 176 */         registration.setAsyncSupported(true);
/*     */       }
/*     */     
/* 179 */     } catch (Exception e) {
/* 180 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean existsServletForUrlPattern(String urlpattern, Collection<? extends ServletRegistration> registrations) {
/* 185 */     for (ServletRegistration r : registrations) {
/* 186 */       if (r.getMappings().contains(urlpattern)) {
/* 187 */         return true;
/*     */       }
/*     */     } 
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected Container createContainer(ServletContext context) {
/* 197 */     return new ServletContainer(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected WSServletDelegate createDelegate(List<ServletAdapter> adapters, ServletContext context) {
/* 204 */     return new WSServletDelegate(adapters, context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 209 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server.http");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\WSServletContextListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.ws.transport.http.servlet;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.ws.api.server.Module;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebModule;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.AsyncContext;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ public class ServletAdapter
/*     */   extends HttpAdapter
/*     */   implements BoundEndpoint
/*     */ {
/*     */   final String name;
/*     */   
/*     */   protected ServletAdapter(String name, String urlPattern, WSEndpoint endpoint, ServletAdapterList owner) {
/*  84 */     super(endpoint, owner, urlPattern);
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
/* 269 */     this.isServlet30Based = ServletUtil.isServlet30Based();
/*     */     this.name = name;
/*     */     Module module = (Module)endpoint.getContainer().getSPI(Module.class);
/*     */     if (module == null) {
/*     */       LOGGER.log(Level.WARNING, "Container {0} doesn''t support {1}", new Object[] { endpoint.getContainer(), Module.class });
/*     */     } else {
/*     */       module.getBoundEndpoints().add(this);
/*     */     } 
/*     */     boolean sticky = false;
/*     */     if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) {
/*     */       WebServiceFeature[] features = endpoint.getBinding().getFeatures().toArray();
/*     */       for (WebServiceFeature f : features) {
/*     */         if (f instanceof com.sun.xml.ws.api.ha.StickyFeature) {
/*     */           sticky = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       this.disableJreplicaCookie = HighAvailabilityProvider.INSTANCE.isDisabledJreplica();
/*     */     } 
/*     */     this.stickyCookie = sticky;
/*     */   }
/*     */   
/*     */   public ServletContext getServletContext() {
/*     */     return ((ServletAdapterList)this.owner).getServletContext();
/*     */   }
/*     */   
/*     */   public String getName() {
/*     */     return this.name;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public URI getAddress() {
/*     */     WebModule webModule = (WebModule)this.endpoint.getContainer().getSPI(WebModule.class);
/*     */     if (webModule == null)
/*     */       throw new WebServiceException("Container " + this.endpoint.getContainer() + " doesn't support " + WebModule.class); 
/*     */     return getAddress(webModule.getContextPath());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public URI getAddress(String baseAddress) {
/*     */     String adrs = baseAddress + getValidPath();
/*     */     try {
/*     */       return new URI(adrs);
/*     */     } catch (URISyntaxException e) {
/*     */       throw new WebServiceException("Unable to compute address for " + this.endpoint, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public QName getPortName() {
/*     */     WSDLPort port = getEndpoint().getPort();
/*     */     if (port == null)
/*     */       return null; 
/*     */     return port.getName();
/*     */   }
/*     */   
/*     */   public void handle(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
/*     */     handle(createConnection(context, request, response));
/*     */   }
/*     */   
/*     */   protected WSHTTPConnection createConnection(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
/*     */     return new ServletConnectionImpl(this, context, request, response);
/*     */   }
/*     */   
/*     */   public void invokeAsync(ServletContext context, HttpServletRequest request, HttpServletResponse response, HttpAdapter.CompletionCallback callback) throws IOException {
/*     */     boolean asyncStarted = false;
/*     */     try {
/*     */       WSHTTPConnection connection = new ServletConnectionImpl(this, context, request, response);
/*     */       if (handleGet(connection))
/*     */         return; 
/*     */       boolean asyncRequest = false;
/*     */       try {
/*     */         asyncRequest = (this.isServlet30Based && request.isAsyncSupported() && !request.isAsyncStarted());
/*     */       } catch (Throwable t) {
/*     */         LOGGER.log(Level.INFO, request.getClass().getName() + " does not support Async API, Continuing with synchronous processing", t);
/*     */         this.isServlet30Based = false;
/*     */       } 
/*     */       if (asyncRequest) {
/*     */         final AsyncContext asyncContext = request.startAsync((ServletRequest)request, (ServletResponse)response);
/*     */         final AsyncCompletionCheck completionCheck = new AsyncCompletionCheck();
/*     */         (new WSAsyncListener(connection, callback)).addListenerTo(asyncContext, completionCheck);
/*     */         invokeAsync(connection, new HttpAdapter.CompletionCallback() {
/*     */               public void onCompletion() {
/*     */                 synchronized (completionCheck) {
/*     */                   if (!completionCheck.isCompleted()) {
/*     */                     asyncContext.complete();
/*     */                     completionCheck.markComplete();
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             });
/*     */         asyncStarted = true;
/*     */       } else {
/*     */         handle(connection);
/*     */       } 
/*     */     } finally {
/*     */       if (!asyncStarted)
/*     */         callback.onCompletion(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AsyncCompletionCheck {
/*     */     boolean completed = false;
/*     */     
/*     */     synchronized void markComplete() {
/*     */       this.completed = true;
/*     */     }
/*     */     
/*     */     synchronized boolean isCompleted() {
/*     */       return this.completed;
/*     */     }
/*     */   }
/*     */   
/*     */   public void publishWSDL(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
/*     */     WSHTTPConnection connection = new ServletConnectionImpl(this, context, request, response);
/*     */     handle(connection);
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return super.toString() + "[name=" + this.name + ']';
/*     */   }
/*     */   
/*     */   private static final Logger LOGGER = Logger.getLogger(ServletAdapter.class.getName());
/*     */   private boolean isServlet30Based;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\ServletAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
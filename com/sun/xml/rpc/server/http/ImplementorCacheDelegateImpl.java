/*     */ package com.sun.xml.rpc.server.http;
/*     */ 
/*     */ import com.sun.xml.rpc.server.Tie;
/*     */ import com.sun.xml.rpc.spi.runtime.Implementor;
/*     */ import com.sun.xml.rpc.spi.runtime.ImplementorCacheDelegate;
/*     */ import com.sun.xml.rpc.spi.runtime.RuntimeEndpointInfo;
/*     */ import com.sun.xml.rpc.spi.runtime.Tie;
/*     */ import java.rmi.Remote;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.xml.rpc.JAXRPCException;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImplementorCacheDelegateImpl
/*     */   extends ImplementorCacheDelegate
/*     */ {
/*     */   private ServletConfig servletConfig;
/*     */   private ServletContext servletContext;
/*     */   private Map cachedImplementors;
/*     */   
/*     */   public ImplementorCacheDelegateImpl(ServletConfig servletConfig) {
/* 203 */     this.cachedImplementors = new HashMap<Object, Object>(); this.servletConfig = servletConfig; this.servletContext = servletConfig.getServletContext();
/* 204 */     this.cachedImplementors = new HashMap<Object, Object>(); } private static final Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.server.http");
/*     */   
/*     */   public Implementor getImplementorFor(RuntimeEndpointInfo targetEndpoint) {
/*     */     synchronized (this) {
/*     */       Implementor implementor = (Implementor)this.cachedImplementors.get(targetEndpoint);
/*     */       if (implementor != null)
/*     */         return implementor; 
/*     */     } 
/*     */     try {
/*     */       if (this.servletConfig != null) {
/*     */         Tie tie1 = targetEndpoint.getTieClass().newInstance();
/*     */         Remote remote = targetEndpoint.getImplementationClass().newInstance();
/*     */         tie1.setTarget(remote);
/*     */         Implementor implementor1 = new Implementor(this.servletContext, (Tie)tie1);
/*     */         implementor1.init();
/*     */         postImplementorInit(implementor1, (RuntimeEndpointInfo)targetEndpoint);
/*     */         Implementor existingImplementor = null;
/*     */         synchronized (this) {
/*     */           existingImplementor = (Implementor)this.cachedImplementors.get(targetEndpoint);
/*     */           if (existingImplementor == null)
/*     */             this.cachedImplementors.put(targetEndpoint, implementor1); 
/*     */         } 
/*     */         if (existingImplementor == null)
/*     */           return implementor1; 
/*     */         preImplementorDestroy(implementor1);
/*     */         implementor1.destroy();
/*     */         return existingImplementor;
/*     */       } 
/*     */       Tie tie = targetEndpoint.getTieClass().newInstance();
/*     */       Remote servant = targetEndpoint.getImplementationClass().newInstance();
/*     */       tie.setTarget(servant);
/*     */       Implementor implementor = new Implementor(null, (Tie)tie);
/*     */       this.cachedImplementors.put(targetEndpoint, implementor);
/*     */       return implementor;
/*     */     } catch (IllegalAccessException e) {
/*     */       logger.log(Level.SEVERE, e.getMessage(), e);
/*     */       throw new JAXRPCServletException("error.implementorFactory.newInstanceFailed", ((RuntimeEndpointInfo)targetEndpoint).getName());
/*     */     } catch (InstantiationException e) {
/*     */       logger.log(Level.SEVERE, e.getMessage(), e);
/*     */       throw new JAXRPCServletException("error.implementorFactory.newInstanceFailed", ((RuntimeEndpointInfo)targetEndpoint).getName());
/*     */     } catch (ServiceException e) {
/*     */       logger.log(Level.SEVERE, e.getMessage(), (Throwable)e);
/*     */       throw new JAXRPCServletException("error.implementorFactory.newInstanceFailed", ((RuntimeEndpointInfo)targetEndpoint).getName());
/*     */     } catch (JAXRPCServletException e) {
/*     */       throw e;
/*     */     } catch (JAXRPCException e) {
/*     */       logger.log(Level.SEVERE, e.getMessage(), (Throwable)e);
/*     */       throw new JAXRPCServletException("error.implementorFactory.servantInitFailed", ((RuntimeEndpointInfo)targetEndpoint).getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseImplementor(RuntimeEndpointInfo targetEndpoint, Implementor implementor) {
/*     */     boolean mustDestroy = false;
/*     */     synchronized (this) {
/*     */       Implementor cachedImplementor = (Implementor)this.cachedImplementors.get(targetEndpoint);
/*     */       if (cachedImplementor != implementor)
/*     */         mustDestroy = true; 
/*     */     } 
/*     */     if (mustDestroy) {
/*     */       preImplementorDestroy(implementor);
/*     */       implementor.destroy();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void destroy() {
/*     */     if (this.servletConfig != null) {
/*     */       Iterator<Implementor> iter = this.cachedImplementors.values().iterator();
/*     */       while (iter.hasNext()) {
/*     */         Implementor implementor = iter.next();
/*     */         preImplementorDestroy(implementor);
/*     */         implementor.destroy();
/*     */       } 
/*     */     } 
/*     */     try {
/*     */       this.cachedImplementors.clear();
/*     */     } catch (UnsupportedOperationException e) {}
/*     */   }
/*     */   
/*     */   protected void postImplementorInit(Implementor implementor, RuntimeEndpointInfo targetEndpoint) {}
/*     */   
/*     */   protected void preImplementorDestroy(Implementor implementor) {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ImplementorCacheDelegateImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.rpc.server.http.ea;
/*     */ 
/*     */ import com.sun.xml.rpc.server.http.Implementor;
/*     */ import com.sun.xml.rpc.server.http.JAXRPCServletException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
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
/*     */ public class ImplementorFactory
/*     */ {
/*     */   protected ServletConfig _servletConfig;
/*     */   protected ImplementorRegistry _registry;
/*     */   protected Map _cachedImplementors;
/*     */   
/*     */   public ImplementorFactory(ServletConfig servletConfig) {
/* 184 */     this._registry = new ImplementorRegistry();
/* 185 */     this._cachedImplementors = new HashMap<Object, Object>(); this._servletConfig = servletConfig; } public ImplementorFactory(ServletConfig servletConfig, String configFilePath) { this._registry = new ImplementorRegistry(); this._cachedImplementors = new HashMap<Object, Object>(); if (configFilePath == null) throw new JAXRPCServletException("error.implementorFactory.noConfiguration");  this._registry.readFrom(configFilePath); this._servletConfig = servletConfig; } public ImplementorFactory(ServletConfig servletConfig, InputStream configInputStream) { this._registry = new ImplementorRegistry(); this._cachedImplementors = new HashMap<Object, Object>();
/*     */     if (configInputStream == null)
/*     */       throw new IllegalArgumentException("error.implementorFactory.noInputStream"); 
/*     */     this._registry.readFrom(configInputStream);
/*     */     this._servletConfig = servletConfig; }
/*     */ 
/*     */   
/*     */   public Implementor getImplementorFor(String name) {
/*     */     synchronized (this) {
/*     */       Implementor implementor = (Implementor)this._cachedImplementors.get(name);
/*     */       if (implementor != null)
/*     */         return implementor; 
/*     */     } 
/*     */     try {
/*     */       ImplementorInfo info = this._registry.getImplementorInfo(name);
/*     */       if (this._servletConfig != null) {
/*     */         Implementor implementor1 = info.createImplementor(this._servletConfig.getServletContext());
/*     */         implementor1.init();
/*     */         Implementor existingImplementor = null;
/*     */         synchronized (this) {
/*     */           existingImplementor = (Implementor)this._cachedImplementors.get(name);
/*     */           if (existingImplementor == null)
/*     */             this._cachedImplementors.put(name, implementor1); 
/*     */         } 
/*     */         if (existingImplementor == null)
/*     */           return implementor1; 
/*     */         implementor1.destroy();
/*     */         return existingImplementor;
/*     */       } 
/*     */       Implementor implementor = info.createImplementor(null);
/*     */       this._cachedImplementors.put(name, implementor);
/*     */       return implementor;
/*     */     } catch (IllegalAccessException e) {
/*     */       throw new JAXRPCServletException("error.implementorFactory.newInstanceFailed", name);
/*     */     } catch (InstantiationException e) {
/*     */       throw new JAXRPCServletException("error.implementorFactory.newInstanceFailed", name);
/*     */     } catch (ServiceException e) {
/*     */       throw new JAXRPCServletException("error.implementorFactory.newInstanceFailed", name);
/*     */     } catch (JAXRPCServletException e) {
/*     */       throw e;
/*     */     } catch (JAXRPCException e) {
/*     */       throw new JAXRPCServletException("error.implementorFactory.servantInitFailed", name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseImplementor(String name, Implementor implementor) {
/*     */     boolean mustDestroy = false;
/*     */     synchronized (this) {
/*     */       Implementor cachedImplementor = (Implementor)this._cachedImplementors.get(name);
/*     */       if (cachedImplementor != implementor)
/*     */         mustDestroy = true; 
/*     */     } 
/*     */     if (mustDestroy)
/*     */       implementor.destroy(); 
/*     */   }
/*     */   
/*     */   public Iterator names() {
/*     */     return this._registry.names();
/*     */   }
/*     */   
/*     */   public void destroy() {
/*     */     if (this._servletConfig != null) {
/*     */       Iterator<Implementor> iter = this._cachedImplementors.values().iterator();
/*     */       while (iter.hasNext()) {
/*     */         Implementor implementor = iter.next();
/*     */         implementor.destroy();
/*     */       } 
/*     */     } 
/*     */     try {
/*     */       this._cachedImplementors.clear();
/*     */     } catch (UnsupportedOperationException e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ea\ImplementorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.sun.xml.rpc.server.http;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.runtime.Implementor;
/*    */ import com.sun.xml.rpc.spi.runtime.ImplementorCache;
/*    */ import com.sun.xml.rpc.spi.runtime.ImplementorCacheDelegate;
/*    */ import javax.servlet.ServletConfig;
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
/*    */ public class ImplementorCache
/*    */   implements ImplementorCache
/*    */ {
/*    */   ImplementorCacheDelegate delegate;
/*    */   
/*    */   public ImplementorCache(ServletConfig servletConfig) {
/* 39 */     this.delegate = new ImplementorCacheDelegateImpl(servletConfig);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Implementor getImplementorFor(RuntimeEndpointInfo targetEndpoint) {
/* 45 */     return this.delegate.getImplementorFor(targetEndpoint);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void releaseImplementor(RuntimeEndpointInfo targetEndpoint, Implementor implementor) {
/* 51 */     this.delegate.releaseImplementor(targetEndpoint, implementor);
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 55 */     this.delegate.destroy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDelegate(ImplementorCacheDelegate implementorCacheDelegate) {
/* 66 */     this.delegate = implementorCacheDelegate;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ImplementorCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
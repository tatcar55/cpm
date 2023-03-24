/*    */ package com.sun.xml.rpc.spi.runtime;
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
/*    */ public abstract class ImplementorCacheDelegate
/*    */ {
/*    */   public Implementor getImplementorFor(RuntimeEndpointInfo targetEndpoint) {
/* 35 */     return null;
/*    */   }
/*    */   
/*    */   public void releaseImplementor(RuntimeEndpointInfo targetEndpoint, Implementor implementor) {}
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\runtime\ImplementorCacheDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
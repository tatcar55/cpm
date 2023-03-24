/*    */ package com.sun.xml.rpc.server.http;
/*    */ 
/*    */ import com.sun.xml.rpc.server.Tie;
/*    */ import com.sun.xml.rpc.spi.runtime.Implementor;
/*    */ import com.sun.xml.rpc.spi.runtime.Tie;
/*    */ import java.rmi.Remote;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.xml.rpc.ServiceException;
/*    */ import javax.xml.rpc.server.ServiceLifecycle;
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
/*    */ public class Implementor
/*    */   implements Implementor
/*    */ {
/*    */   private Tie tie;
/*    */   private Remote endpoint;
/*    */   private ServletEndpointContextImpl context;
/*    */   
/*    */   public Implementor(ServletContext servletContext, Tie tie) {
/* 46 */     this.tie = (Tie)tie;
/* 47 */     this.context = new ServletEndpointContextImpl(servletContext, this);
/*    */   }
/*    */   
/*    */   public Tie getTie() {
/* 51 */     return (Tie)this.tie;
/*    */   }
/*    */   
/*    */   public Remote getTarget() {
/* 55 */     return this.tie.getTarget();
/*    */   }
/*    */   
/*    */   public ServletEndpointContextImpl getContext() {
/* 59 */     return this.context;
/*    */   }
/*    */   
/*    */   public void init() throws ServiceException {
/* 63 */     Remote servant = this.tie.getTarget();
/* 64 */     if (servant != null && servant instanceof ServiceLifecycle) {
/* 65 */       ((ServiceLifecycle)servant).init(this.context);
/*    */     }
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 70 */     Remote servant = this.tie.getTarget();
/* 71 */     if (servant != null && servant instanceof ServiceLifecycle) {
/* 72 */       ((ServiceLifecycle)servant).destroy();
/*    */     }
/* 74 */     this.tie.destroy();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\Implementor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.rpc.server;
/*     */ 
/*     */ import com.sun.xml.rpc.client.HandlerChainImpl;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistryImpl;
/*     */ import java.io.IOException;
/*     */ import java.rmi.Remote;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.rpc.encoding.TypeMappingRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TieBase
/*     */   extends StreamingHandler
/*     */   implements Tie
/*     */ {
/*     */   protected TypeMappingRegistry typeMappingRegistry;
/*     */   protected InternalTypeMappingRegistry internalTypeMappingRegistry;
/*     */   protected HandlerChainImpl handlerChain;
/*     */   private Remote _servant;
/*     */   
/*     */   public HandlerChainImpl getHandlerChain() {
/*  51 */     if (this.handlerChain == null)
/*     */     {
/*     */       
/*  54 */       this.handlerChain = new HandlerChainImpl(new ArrayList());
/*     */     }
/*  56 */     return this.handlerChain;
/*     */   }
/*     */   
/*     */   protected TieBase(TypeMappingRegistry registry) throws Exception {
/*  60 */     this.typeMappingRegistry = registry;
/*  61 */     this.internalTypeMappingRegistry = (InternalTypeMappingRegistry)new InternalTypeMappingRegistryImpl(registry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flushHttpResponse(StreamingHandlerState state) throws IOException {
/*  73 */     state.getMessageContext().setProperty("com.sun.xml.rpc.server.OneWayOperation", "true");
/*     */ 
/*     */     
/*  76 */     HttpServletResponse httpResp = (HttpServletResponse)state.getMessageContext().getProperty("com.sun.xml.rpc.server.http.HttpServletResponse");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     if (httpResp != null) {
/*  83 */       httpResp.setStatus(202);
/*     */       
/*  85 */       httpResp.setContentType("text/xml");
/*  86 */       httpResp.flushBuffer();
/*  87 */       httpResp.getWriter().close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTarget(Remote servant) {
/*  92 */     this._servant = servant;
/*     */   }
/*     */   
/*     */   public Remote getTarget() {
/*  96 */     return this._servant;
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 100 */     if (this.handlerChain != null)
/* 101 */       this.handlerChain.destroy(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\TieBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
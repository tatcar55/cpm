/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.transport.http.client.HttpTransportPipe;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TransportTubeFactory
/*     */ {
/*     */   public abstract Tube doCreate(@NotNull ClientTubeAssemblerContext paramClientTubeAssemblerContext);
/*     */   
/* 113 */   private static final TransportTubeFactory DEFAULT = new DefaultTransportTubeFactory();
/*     */   
/*     */   private static class DefaultTransportTubeFactory
/*     */     extends TransportTubeFactory {
/*     */     public Tube doCreate(ClientTubeAssemblerContext context) {
/* 118 */       return createDefault(context);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private DefaultTransportTubeFactory() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tube create(@Nullable ClassLoader classLoader, @NotNull ClientTubeAssemblerContext context) {
/* 132 */     for (TransportTubeFactory factory : ServiceFinder.find(TransportTubeFactory.class, classLoader, (Component)context.getContainer())) {
/* 133 */       Tube tube = factory.doCreate(context);
/* 134 */       if (tube != null) {
/* 135 */         if (logger.isLoggable(Level.FINE)) {
/* 136 */           logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), tube });
/*     */         }
/* 138 */         return tube;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 143 */     ClientPipeAssemblerContext ctxt = new ClientPipeAssemblerContext(context.getAddress(), context.getWsdlModel(), context.getService(), context.getBinding(), context.getContainer());
/*     */ 
/*     */     
/* 146 */     ctxt.setCodec(context.getCodec());
/* 147 */     for (TransportPipeFactory factory : ServiceFinder.find(TransportPipeFactory.class, classLoader)) {
/* 148 */       Pipe pipe = factory.doCreate(ctxt);
/* 149 */       if (pipe != null) {
/* 150 */         if (logger.isLoggable(Level.FINE)) {
/* 151 */           logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), pipe });
/*     */         }
/* 153 */         return PipeAdapter.adapt(pipe);
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     return DEFAULT.createDefault(ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Tube createDefault(ClientTubeAssemblerContext context) {
/* 162 */     String scheme = context.getAddress().getURI().getScheme();
/* 163 */     if (scheme != null && (
/* 164 */       scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
/* 165 */       return createHttpTransport(context);
/*     */     }
/* 167 */     throw new WebServiceException("Unsupported endpoint address: " + context.getAddress());
/*     */   }
/*     */   
/*     */   protected Tube createHttpTransport(ClientTubeAssemblerContext context) {
/* 171 */     return (Tube)new HttpTransportPipe(context.getCodec(), context.getBinding());
/*     */   }
/*     */   
/* 174 */   private static final Logger logger = Logger.getLogger(TransportTubeFactory.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\TransportTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
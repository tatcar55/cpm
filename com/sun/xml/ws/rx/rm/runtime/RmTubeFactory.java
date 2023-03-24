/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.TubeFactory;
/*     */ import java.util.logging.Level;
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
/*     */ public final class RmTubeFactory
/*     */   implements TubeFactory
/*     */ {
/*  61 */   private static final Logger LOGGER = Logger.getLogger(RmTubeFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/*  70 */     RmConfiguration configuration = RmConfigurationFactory.INSTANCE.createInstance(context);
/*     */     
/*  72 */     if (configuration.isReliableMessagingEnabled()) {
/*  73 */       if (LOGGER.isLoggable(Level.CONFIG)) {
/*  74 */         LOGGER.config("Creating client-side RM tube with configuration: " + configuration.toString());
/*     */       }
/*     */       
/*  77 */       return (Tube)new ClientTube(configuration, context);
/*     */     } 
/*     */     
/*  80 */     return context.getTubelineHead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/*  90 */     RmConfiguration configuration = RmConfigurationFactory.INSTANCE.createInstance(context);
/*     */     
/*  92 */     if (configuration.isReliableMessagingEnabled()) {
/*  93 */       if (LOGGER.isLoggable(Level.CONFIG)) {
/*  94 */         LOGGER.config("Creating endpoint-side RM tube with configuration: " + configuration.toString());
/*     */       }
/*     */       
/*  97 */       return (Tube)new ServerTube(configuration, context);
/*     */     } 
/*     */     
/* 100 */     return context.getTubelineHead();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\RmTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
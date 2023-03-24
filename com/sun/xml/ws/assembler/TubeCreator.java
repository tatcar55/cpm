/*     */ package com.sun.xml.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.TubeFactory;
/*     */ import com.sun.xml.ws.assembler.dev.TubelineAssemblyContextUpdater;
/*     */ import com.sun.xml.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.ws.runtime.config.TubeFactoryConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TubeCreator
/*     */ {
/*  58 */   private static final Logger LOGGER = Logger.getLogger(TubeCreator.class);
/*     */   private final TubeFactory factory;
/*     */   private final String msgDumpPropertyBase;
/*     */   
/*     */   TubeCreator(TubeFactoryConfig config, ClassLoader tubeFactoryClassLoader) {
/*     */     try {
/*  64 */       Class<?> factoryClass = Class.forName(config.getClassName(), true, tubeFactoryClassLoader);
/*  65 */       if (TubeFactory.class.isAssignableFrom(factoryClass)) {
/*     */ 
/*     */         
/*  68 */         Class<TubeFactory> typedClass = (Class)factoryClass;
/*  69 */         this.factory = typedClass.newInstance();
/*  70 */         this.msgDumpPropertyBase = this.factory.getClass().getName() + ".dump";
/*     */       } else {
/*  72 */         throw new RuntimeException(TubelineassemblyMessages.MASM_0015_CLASS_DOES_NOT_IMPLEMENT_INTERFACE(factoryClass.getName(), TubeFactory.class.getName()));
/*     */       } 
/*  74 */     } catch (InstantiationException ex) {
/*  75 */       throw (RuntimeException)LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0016_UNABLE_TO_INSTANTIATE_TUBE_FACTORY(config.getClassName()), ex), true);
/*  76 */     } catch (IllegalAccessException ex) {
/*  77 */       throw (RuntimeException)LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0016_UNABLE_TO_INSTANTIATE_TUBE_FACTORY(config.getClassName()), ex), true);
/*  78 */     } catch (ClassNotFoundException ex) {
/*  79 */       throw (RuntimeException)LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0017_UNABLE_TO_LOAD_TUBE_FACTORY_CLASS(config.getClassName()), ex), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   Tube createTube(DefaultClientTubelineAssemblyContext context) {
/*  85 */     return this.factory.createTube(context);
/*     */   }
/*     */ 
/*     */   
/*     */   Tube createTube(DefaultServerTubelineAssemblyContext context) {
/*  90 */     return this.factory.createTube(context);
/*     */   }
/*     */   
/*     */   void updateContext(ClientTubelineAssemblyContext context) {
/*  94 */     if (this.factory instanceof TubelineAssemblyContextUpdater) {
/*  95 */       ((TubelineAssemblyContextUpdater)this.factory).prepareContext(context);
/*     */     }
/*     */   }
/*     */   
/*     */   void updateContext(DefaultServerTubelineAssemblyContext context) {
/* 100 */     if (this.factory instanceof TubelineAssemblyContextUpdater) {
/* 101 */       ((TubelineAssemblyContextUpdater)this.factory).prepareContext(context);
/*     */     }
/*     */   }
/*     */   
/*     */   String getMessageDumpPropertyBase() {
/* 106 */     return this.msgDumpPropertyBase;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\TubeCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
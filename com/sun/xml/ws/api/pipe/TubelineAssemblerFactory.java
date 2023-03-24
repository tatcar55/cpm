/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.assembler.MetroConfigName;
/*     */ import com.sun.xml.ws.assembler.MetroTubelineAssembler;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TubelineAssemblerFactory
/*     */ {
/*     */   public abstract TubelineAssembler doCreate(BindingID paramBindingID);
/*     */   
/*     */   public static TubelineAssembler create(ClassLoader classLoader, BindingID bindingId) {
/*  83 */     return create(classLoader, bindingId, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TubelineAssembler create(ClassLoader classLoader, BindingID bindingId, @Nullable Container container) {
/*  99 */     if (container != null) {
/*     */       
/* 101 */       TubelineAssemblerFactory taf = (TubelineAssemblerFactory)container.getSPI(TubelineAssemblerFactory.class);
/* 102 */       if (taf != null) {
/* 103 */         TubelineAssembler a = taf.doCreate(bindingId);
/* 104 */         if (a != null) {
/* 105 */           return a;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     for (TubelineAssemblerFactory factory : ServiceFinder.find(TubelineAssemblerFactory.class, classLoader)) {
/* 111 */       TubelineAssembler assembler = factory.doCreate(bindingId);
/* 112 */       if (assembler != null) {
/* 113 */         logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), assembler });
/* 114 */         return assembler;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 119 */     for (PipelineAssemblerFactory factory : ServiceFinder.find(PipelineAssemblerFactory.class, classLoader)) {
/* 120 */       PipelineAssembler assembler = factory.doCreate(bindingId);
/* 121 */       if (assembler != null) {
/* 122 */         logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), assembler });
/* 123 */         return new TubelineAssemblerAdapter(assembler);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 128 */     return (TubelineAssembler)new MetroTubelineAssembler(bindingId, (MetroConfigName)MetroTubelineAssembler.JAXWS_TUBES_CONFIG_NAMES);
/*     */   }
/*     */   
/*     */   private static class TubelineAssemblerAdapter implements TubelineAssembler {
/*     */     private PipelineAssembler assembler;
/*     */     
/*     */     TubelineAssemblerAdapter(PipelineAssembler assembler) {
/* 135 */       this.assembler = assembler;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Tube createClient(@NotNull ClientTubeAssemblerContext context) {
/* 140 */       ClientPipeAssemblerContext ctxt = new ClientPipeAssemblerContext(context.getAddress(), context.getWsdlModel(), context.getService(), context.getBinding(), context.getContainer());
/*     */ 
/*     */       
/* 143 */       return PipeAdapter.adapt(this.assembler.createClient(ctxt));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Tube createServer(@NotNull ServerTubeAssemblerContext context) {
/* 148 */       if (!(context instanceof ServerPipeAssemblerContext)) {
/* 149 */         throw new IllegalArgumentException("{0} is not instance of ServerPipeAssemblerContext");
/*     */       }
/* 151 */       return PipeAdapter.adapt(this.assembler.createServer((ServerPipeAssemblerContext)context));
/*     */     }
/*     */   }
/*     */   
/* 155 */   private static final Logger logger = Logger.getLogger(TubelineAssemblerFactory.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\TubelineAssemblerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
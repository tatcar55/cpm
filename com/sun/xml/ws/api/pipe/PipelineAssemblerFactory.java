/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import com.sun.xml.ws.util.pipe.StandalonePipeAssembler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PipelineAssemblerFactory
/*     */ {
/*     */   public abstract PipelineAssembler doCreate(BindingID paramBindingID);
/*     */   
/*     */   public static PipelineAssembler create(ClassLoader classLoader, BindingID bindingId) {
/*  97 */     for (PipelineAssemblerFactory factory : ServiceFinder.find(PipelineAssemblerFactory.class, classLoader)) {
/*  98 */       PipelineAssembler assembler = factory.doCreate(bindingId);
/*  99 */       if (assembler != null) {
/* 100 */         logger.fine(factory.getClass() + " successfully created " + assembler);
/* 101 */         return assembler;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     return (PipelineAssembler)new StandalonePipeAssembler();
/*     */   }
/*     */   
/* 110 */   private static final Logger logger = Logger.getLogger(PipelineAssemblerFactory.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\PipelineAssemblerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
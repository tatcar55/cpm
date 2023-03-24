/*     */ package com.sun.xml.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.assembler.dev.TubelineAssemblyContext;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TubelineAssemblyContextImpl
/*     */   implements TubelineAssemblyContext
/*     */ {
/*  61 */   private static final Logger LOGGER = Logger.getLogger(TubelineAssemblyContextImpl.class);
/*     */   
/*     */   private Tube head;
/*     */   private Pipe adaptedHead;
/*  65 */   private List<Tube> tubes = new LinkedList<Tube>();
/*     */ 
/*     */   
/*     */   public Tube getTubelineHead() {
/*  69 */     return this.head;
/*     */   }
/*     */ 
/*     */   
/*     */   public Pipe getAdaptedTubelineHead() {
/*  74 */     if (this.adaptedHead == null) {
/*  75 */       this.adaptedHead = PipeAdapter.adapt(this.head);
/*     */     }
/*  77 */     return this.adaptedHead;
/*     */   }
/*     */   
/*     */   boolean setTubelineHead(Tube newHead) {
/*  81 */     if (newHead == this.head || newHead == this.adaptedHead) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     this.head = newHead;
/*  86 */     this.tubes.add(this.head);
/*  87 */     this.adaptedHead = null;
/*     */     
/*  89 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  90 */       LOGGER.finer(MessageFormat.format("Added '{0}' tube instance to the tubeline.", new Object[] { (newHead == null) ? null : newHead.getClass().getName() }));
/*     */     }
/*     */     
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getImplementation(Class<T> type) {
/*  98 */     for (Tube tube : this.tubes) {
/*  99 */       if (type.isInstance(tube)) {
/* 100 */         return type.cast(tube);
/*     */       }
/*     */     } 
/* 103 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\TubelineAssemblyContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
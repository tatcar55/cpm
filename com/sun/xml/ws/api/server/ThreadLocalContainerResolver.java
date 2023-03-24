/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import java.util.concurrent.Executor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadLocalContainerResolver
/*     */   extends ContainerResolver
/*     */ {
/*  64 */   private ThreadLocal<Container> containers = new ThreadLocal<Container>()
/*     */     {
/*     */       protected Container initialValue() {
/*  67 */         return Container.NONE;
/*     */       }
/*     */     };
/*     */   
/*     */   public Container getContainer() {
/*  72 */     return this.containers.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container enterContainer(Container container) {
/*  81 */     Container old = this.containers.get();
/*  82 */     this.containers.set(container);
/*  83 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exitContainer(Container old) {
/*  91 */     this.containers.set(old);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Executor wrapExecutor(final Container container, final Executor ex) {
/* 101 */     if (ex == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     return new Executor()
/*     */       {
/*     */         public void execute(final Runnable command) {
/* 107 */           ex.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 110 */                   Container old = ThreadLocalContainerResolver.this.enterContainer(container);
/*     */                   try {
/* 112 */                     command.run();
/*     */                   } finally {
/* 114 */                     ThreadLocalContainerResolver.this.exitContainer(old);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\ThreadLocalContainerResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
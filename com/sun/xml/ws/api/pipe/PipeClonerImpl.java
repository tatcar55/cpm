/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class PipeClonerImpl
/*     */   extends PipeCloner
/*     */ {
/*  63 */   private static final Logger LOGGER = Logger.getLogger(PipeClonerImpl.class.getName());
/*     */ 
/*     */   
/*     */   public PipeClonerImpl() {
/*  67 */     super(new HashMap<Object, Object>());
/*     */   }
/*     */   
/*     */   protected PipeClonerImpl(Map<Object, Object> master2copy) {
/*  71 */     super(master2copy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Pipe> T copy(T p) {
/*  79 */     Pipe r = (Pipe)this.master2copy.get(p);
/*  80 */     if (r == null) {
/*  81 */       r = p.copy(this);
/*     */       
/*  83 */       assert this.master2copy.get(p) == r : "the pipe must call the add(...) method to register itself before start copying other pipes, but " + p + " hasn't done so";
/*     */     } 
/*  85 */     return (T)r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Pipe original, Pipe copy) {
/*  93 */     assert !this.master2copy.containsKey(original);
/*  94 */     assert original != null && copy != null;
/*  95 */     this.master2copy.put(original, copy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(AbstractTubeImpl original, AbstractTubeImpl copy) {
/* 102 */     add((Tube)original, (Tube)copy);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Tube original, Tube copy) {
/* 107 */     assert !this.master2copy.containsKey(original);
/* 108 */     assert original != null && copy != null;
/* 109 */     this.master2copy.put(original, copy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Tube> T copy(T t) {
/* 115 */     Tube r = (Tube)this.master2copy.get(t);
/* 116 */     if (r == null) {
/* 117 */       if (t != null) {
/* 118 */         r = t.copy(this);
/*     */       }
/* 120 */       else if (LOGGER.isLoggable(Level.FINER)) {
/* 121 */         LOGGER.fine("WARNING, tube passed to 'copy' in " + this + " was null, so no copy was made");
/*     */       } 
/*     */     }
/*     */     
/* 125 */     return (T)r;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\PipeClonerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.ws.assembler.dev;
/*     */ 
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TubelineAssemblyDecorator
/*     */ {
/*     */   public static TubelineAssemblyDecorator composite(Iterable<TubelineAssemblyDecorator> decorators) {
/*  60 */     return new CompositeTubelineAssemblyDecorator(decorators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateClient(Tube tube, ClientTubelineAssemblyContext context) {
/*  70 */     return tube;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateClientHead(Tube tube, ClientTubelineAssemblyContext context) {
/*  81 */     return tube;
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
/*     */   public Tube decorateClientTail(Tube tube, ClientTubelineAssemblyContext context) {
/*  93 */     return tube;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateServer(Tube tube, ServerTubelineAssemblyContext context) {
/* 103 */     return tube;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateServerTail(Tube tube, ServerTubelineAssemblyContext context) {
/* 114 */     return tube;
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
/*     */   public Tube decorateServerHead(Tube tube, ServerTubelineAssemblyContext context) {
/* 126 */     return tube;
/*     */   }
/*     */   
/*     */   private static class CompositeTubelineAssemblyDecorator extends TubelineAssemblyDecorator {
/* 130 */     private Collection<TubelineAssemblyDecorator> decorators = new ArrayList<TubelineAssemblyDecorator>();
/*     */     
/*     */     public CompositeTubelineAssemblyDecorator(Iterable<TubelineAssemblyDecorator> decorators) {
/* 133 */       for (TubelineAssemblyDecorator decorator : decorators) {
/* 134 */         this.decorators.add(decorator);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Tube decorateClient(Tube tube, ClientTubelineAssemblyContext context) {
/* 140 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 141 */         tube = decorator.decorateClient(tube, context);
/*     */       }
/* 143 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateClientHead(Tube tube, ClientTubelineAssemblyContext context) {
/* 149 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 150 */         tube = decorator.decorateClientHead(tube, context);
/*     */       }
/* 152 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateClientTail(Tube tube, ClientTubelineAssemblyContext context) {
/* 159 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 160 */         tube = decorator.decorateClientTail(tube, context);
/*     */       }
/* 162 */       return tube;
/*     */     }
/*     */     
/*     */     public Tube decorateServer(Tube tube, ServerTubelineAssemblyContext context) {
/* 166 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 167 */         tube = decorator.decorateServer(tube, context);
/*     */       }
/* 169 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateServerTail(Tube tube, ServerTubelineAssemblyContext context) {
/* 175 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 176 */         tube = decorator.decorateServerTail(tube, context);
/*     */       }
/* 178 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateServerHead(Tube tube, ServerTubelineAssemblyContext context) {
/* 185 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 186 */         tube = decorator.decorateServerHead(tube, context);
/*     */       }
/* 188 */       return tube;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\dev\TubelineAssemblyDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
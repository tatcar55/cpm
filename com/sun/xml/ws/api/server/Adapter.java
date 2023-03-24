/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.config.management.Reconfigurable;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.util.Pool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Adapter<TK extends Adapter.Toolkit>
/*     */   implements Reconfigurable, Component
/*     */ {
/*     */   protected final WSEndpoint<?> endpoint;
/*     */   
/*     */   public class Toolkit
/*     */   {
/* 108 */     public final Codec codec = Adapter.this.endpoint.createCodec();
/* 109 */     public final WSEndpoint.PipeHead head = Adapter.this.endpoint.createPipeHead();
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
/* 120 */   protected volatile Pool<TK> pool = new Pool<TK>() {
/*     */       protected TK create() {
/* 122 */         return Adapter.this.createToolkit();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Adapter(WSEndpoint<?> endpoint) {
/* 131 */     assert endpoint != null;
/* 132 */     this.endpoint = endpoint;
/*     */     
/* 134 */     endpoint.getComponents().add(getEndpointComponent());
/*     */   }
/*     */   
/*     */   protected Component getEndpointComponent() {
/* 138 */     return new Component() {
/*     */         public <S> S getSPI(Class<S> spiType) {
/* 140 */           if (spiType.isAssignableFrom(Reconfigurable.class)) {
/* 141 */             return spiType.cast(Adapter.this);
/*     */           }
/* 143 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reconfigure() {
/* 152 */     this.pool = new Pool<TK>() {
/*     */         protected TK create() {
/* 154 */           return Adapter.this.createToolkit();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public <S> S getSPI(Class<S> spiType) {
/* 160 */     if (spiType.isAssignableFrom(Reconfigurable.class)) {
/* 161 */       return spiType.cast(this);
/*     */     }
/* 163 */     if (this.endpoint != null) {
/* 164 */       return this.endpoint.getSPI(spiType);
/*     */     }
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSEndpoint<?> getEndpoint() {
/* 176 */     return this.endpoint;
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
/*     */   protected Pool<TK> getPool() {
/* 190 */     return this.pool;
/*     */   }
/*     */   
/*     */   protected abstract TK createToolkit();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\Adapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
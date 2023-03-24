/*     */ package com.sun.xml.ws.api.pipe.helper;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.PipeCloner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFilterPipeImpl
/*     */   extends AbstractPipeImpl
/*     */ {
/*     */   protected final Pipe next;
/*     */   
/*     */   protected AbstractFilterPipeImpl(Pipe next) {
/* 118 */     this.next = next;
/* 119 */     assert next != null;
/*     */   }
/*     */   
/*     */   protected AbstractFilterPipeImpl(AbstractFilterPipeImpl that, PipeCloner cloner) {
/* 123 */     super(that, cloner);
/* 124 */     this.next = cloner.copy(that.next);
/* 125 */     assert this.next != null;
/*     */   }
/*     */   
/*     */   public Packet process(Packet packet) {
/* 129 */     return this.next.process(packet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 134 */     this.next.preDestroy();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\helper\AbstractFilterPipeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
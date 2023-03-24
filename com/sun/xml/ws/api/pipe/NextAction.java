/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NextAction
/*     */ {
/*     */   int kind;
/*     */   Tube next;
/*     */   Packet packet;
/*     */   Throwable throwable;
/*     */   Runnable onExitRunnable;
/*     */   static final int INVOKE = 0;
/*     */   static final int INVOKE_AND_FORGET = 1;
/*     */   static final int RETURN = 2;
/*     */   static final int THROW = 3;
/*     */   static final int SUSPEND = 4;
/*     */   static final int THROW_ABORT_RESPONSE = 5;
/*     */   static final int ABORT_RESPONSE = 6;
/*     */   static final int INVOKE_ASYNC = 7;
/*     */   
/*     */   private void set(int k, Tube v, Packet p, Throwable t) {
/*  81 */     this.kind = k;
/*  82 */     this.next = v;
/*  83 */     this.packet = p;
/*  84 */     this.throwable = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invoke(Tube next, Packet p) {
/*  94 */     set(0, next, p, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeAndForget(Tube next, Packet p) {
/* 104 */     set(1, next, p, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnWith(Packet response) {
/* 112 */     set(2, null, response, null);
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
/*     */   public void throwException(Packet response, Throwable t) {
/* 126 */     set(2, null, response, t);
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
/*     */   public void throwException(Throwable t) {
/* 139 */     assert t instanceof RuntimeException || t instanceof Error;
/* 140 */     set(3, null, null, t);
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
/*     */   public void throwExceptionAbortResponse(Throwable t) {
/* 152 */     set(5, null, null, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abortResponse(Packet response) {
/* 162 */     set(6, null, response, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeAsync(Tube next, Packet p) {
/* 173 */     set(7, next, p, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspend() {
/* 182 */     suspend(null, null);
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
/*     */   public void suspend(Runnable onExitRunnable) {
/* 196 */     suspend(null, onExitRunnable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspend(Tube next) {
/* 206 */     suspend(next, null);
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
/*     */   
/*     */   public void suspend(Tube next, Runnable onExitRunnable) {
/* 223 */     set(4, next, null, null);
/* 224 */     this.onExitRunnable = onExitRunnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube getNext() {
/* 231 */     return this.next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNext(Tube next) {
/* 238 */     this.next = next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getPacket() {
/* 246 */     return this.packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/* 254 */     return this.throwable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 262 */     StringBuilder buf = new StringBuilder();
/* 263 */     buf.append(super.toString()).append(" [");
/* 264 */     buf.append("kind=").append(getKindString()).append(',');
/* 265 */     buf.append("next=").append(this.next).append(',');
/* 266 */     buf.append("packet=").append((this.packet != null) ? this.packet.toShortString() : null).append(',');
/* 267 */     buf.append("throwable=").append(this.throwable).append(']');
/* 268 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKindString() {
/* 275 */     switch (this.kind) { case 0:
/* 276 */         return "INVOKE";
/* 277 */       case 1: return "INVOKE_AND_FORGET";
/* 278 */       case 2: return "RETURN";
/* 279 */       case 3: return "THROW";
/* 280 */       case 4: return "SUSPEND";
/* 281 */       case 5: return "THROW_ABORT_RESPONSE";
/* 282 */       case 6: return "ABORT_RESPONSE";
/* 283 */       case 7: return "INVOKE_ASYNC"; }
/* 284 */      throw new AssertionError(this.kind);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\NextAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
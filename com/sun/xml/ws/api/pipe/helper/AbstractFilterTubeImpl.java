/*    */ package com.sun.xml.ws.api.pipe.helper;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractFilterTubeImpl
/*    */   extends AbstractTubeImpl
/*    */ {
/*    */   protected final Tube next;
/*    */   
/*    */   protected AbstractFilterTubeImpl(Tube next) {
/* 63 */     this.next = next;
/*    */   }
/*    */   
/*    */   protected AbstractFilterTubeImpl(AbstractFilterTubeImpl that, TubeCloner cloner) {
/* 67 */     super(that, cloner);
/* 68 */     if (that.next != null) {
/* 69 */       this.next = cloner.copy(that.next);
/*    */     } else {
/* 71 */       this.next = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processRequest(Packet request) {
/* 79 */     return doInvoke(this.next, request);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processResponse(Packet response) {
/* 86 */     return doReturnWith(response);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processException(Throwable t) {
/* 93 */     return doThrow(t);
/*    */   }
/*    */   
/*    */   public void preDestroy() {
/* 97 */     if (this.next != null)
/* 98 */       this.next.preDestroy(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\helper\AbstractFilterTubeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
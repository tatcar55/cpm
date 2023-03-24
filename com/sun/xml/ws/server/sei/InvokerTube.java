/*    */ package com.sun.xml.ws.server.sei;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
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
/*    */ public abstract class InvokerTube<T extends Invoker>
/*    */   extends AbstractTubeImpl
/*    */   implements InvokerSource<T>
/*    */ {
/*    */   protected final T invoker;
/*    */   
/*    */   protected InvokerTube(T invoker) {
/* 59 */     this.invoker = invoker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected InvokerTube(InvokerTube<T> that, TubeCloner cloner) {
/* 66 */     cloner.add((Tube)that, (Tube)this);
/* 67 */     this.invoker = that.invoker;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public T getInvoker(Packet request) {
/* 74 */     return this.invoker;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\InvokerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
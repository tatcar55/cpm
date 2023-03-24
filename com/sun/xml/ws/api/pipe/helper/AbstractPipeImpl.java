/*    */ package com.sun.xml.ws.api.pipe.helper;
/*    */ 
/*    */ import com.sun.xml.ws.api.pipe.Pipe;
/*    */ import com.sun.xml.ws.api.pipe.PipeCloner;
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
/*    */ public abstract class AbstractPipeImpl
/*    */   implements Pipe
/*    */ {
/*    */   protected AbstractPipeImpl() {}
/*    */   
/*    */   protected AbstractPipeImpl(Pipe that, PipeCloner cloner) {
/* 72 */     cloner.add(that, this);
/*    */   }
/*    */   
/*    */   public void preDestroy() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\helper\AbstractPipeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
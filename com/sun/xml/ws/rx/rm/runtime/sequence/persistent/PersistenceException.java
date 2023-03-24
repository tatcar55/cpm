/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence.persistent;
/*    */ 
/*    */ import com.sun.xml.ws.rx.RxRuntimeException;
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
/*    */ public final class PersistenceException
/*    */   extends RxRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -5886181992944526905L;
/*    */   
/*    */   public PersistenceException(String message) {
/* 53 */     super(message);
/*    */   }
/*    */   
/*    */   public PersistenceException(String message, Throwable cause) {
/* 57 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\persistent\PersistenceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.ctc.wstx.exc;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WstxIOException
/*    */   extends WstxException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public WstxIOException(IOException ie) {
/* 15 */     super(ie);
/*    */   }
/*    */   
/*    */   public WstxIOException(String msg) {
/* 19 */     super(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\exc\WstxIOException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
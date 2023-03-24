/*    */ package com.ctc.wstx.sax;
/*    */ 
/*    */ import org.xml.sax.SAXException;
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
/*    */ public final class WrappedSaxException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final SAXException mCause;
/*    */   
/*    */   public WrappedSaxException(SAXException cause) {
/* 33 */     this.mCause = cause;
/*    */   }
/*    */   public SAXException getSaxException() {
/* 36 */     return this.mCause;
/*    */   } public String toString() {
/* 38 */     return this.mCause.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\sax\WrappedSaxException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
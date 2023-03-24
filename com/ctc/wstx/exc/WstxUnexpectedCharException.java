/*    */ package com.ctc.wstx.exc;
/*    */ 
/*    */ import javax.xml.stream.Location;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WstxUnexpectedCharException
/*    */   extends WstxParsingException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   final char mChar;
/*    */   
/*    */   public WstxUnexpectedCharException(String msg, Location loc, char c) {
/* 19 */     super(msg, loc);
/* 20 */     this.mChar = c;
/*    */   }
/*    */   
/*    */   public char getChar() {
/* 24 */     return this.mChar;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\exc\WstxUnexpectedCharException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
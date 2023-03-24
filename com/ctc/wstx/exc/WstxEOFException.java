/*    */ package com.ctc.wstx.exc;
/*    */ 
/*    */ import javax.xml.stream.Location;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WstxEOFException
/*    */   extends WstxParsingException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public WstxEOFException(String msg, Location loc) {
/* 15 */     super(msg, loc);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\exc\WstxEOFException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.sun.xml.rpc.sp;
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
/*    */ public class ParseException
/*    */   extends Exception
/*    */ {
/*    */   private String publicId;
/*    */   private String systemId;
/*    */   private int line;
/*    */   private int col;
/*    */   
/*    */   public ParseException(String message, String publicId, String systemId, int line, int col) {
/* 46 */     super(message);
/* 47 */     this.publicId = publicId;
/* 48 */     this.systemId = systemId;
/* 49 */     this.line = line;
/* 50 */     this.col = col;
/*    */   }
/*    */   
/*    */   public ParseException(String message, StreamingParser parser) {
/* 54 */     this(message, parser.publicId(), parser.systemId(), parser.line(), parser.column());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParseException(String message, String publicId, String systemId) {
/* 63 */     this(message, publicId, systemId, -1, -1);
/*    */   }
/*    */   
/*    */   public ParseException(String message) {
/* 67 */     this(message, null, null, -1, -1);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 71 */     StringBuffer sb = new StringBuffer(getClass().getName());
/* 72 */     if (this.publicId != null)
/* 73 */       sb.append(": " + this.publicId); 
/* 74 */     if (this.systemId != null)
/* 75 */       sb.append(": " + this.systemId); 
/* 76 */     if (this.line != -1) {
/* 77 */       sb.append(":" + this.line);
/* 78 */       if (this.col != -1)
/* 79 */         sb.append("," + this.col); 
/*    */     } 
/* 81 */     if (getMessage() != null)
/* 82 */       sb.append(": " + getMessage()); 
/* 83 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\ParseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
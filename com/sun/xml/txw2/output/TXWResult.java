/*    */ package com.sun.xml.txw2.output;
/*    */ 
/*    */ import com.sun.xml.txw2.TypedXmlWriter;
/*    */ import javax.xml.transform.Result;
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
/*    */ public class TXWResult
/*    */   implements Result
/*    */ {
/*    */   private String systemId;
/*    */   private TypedXmlWriter writer;
/*    */   
/*    */   public TXWResult(TypedXmlWriter writer) {
/* 63 */     this.writer = writer;
/*    */   }
/*    */   
/*    */   public TypedXmlWriter getWriter() {
/* 67 */     return this.writer;
/*    */   }
/*    */   
/*    */   public void setWriter(TypedXmlWriter writer) {
/* 71 */     this.writer = writer;
/*    */   }
/*    */   
/*    */   public String getSystemId() {
/* 75 */     return this.systemId;
/*    */   }
/*    */   
/*    */   public void setSystemId(String systemId) {
/* 79 */     this.systemId = systemId;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\TXWResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
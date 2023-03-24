/*    */ package com.sun.xml.ws.addressing.model;
/*    */ 
/*    */ import com.sun.xml.ws.resources.AddressingMessages;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class InvalidAddressingHeaderException
/*    */   extends WebServiceException
/*    */ {
/*    */   private QName problemHeader;
/*    */   private QName subsubcode;
/*    */   
/*    */   public InvalidAddressingHeaderException(QName problemHeader, QName subsubcode) {
/* 67 */     super(AddressingMessages.INVALID_ADDRESSING_HEADER_EXCEPTION(problemHeader, subsubcode));
/* 68 */     this.problemHeader = problemHeader;
/* 69 */     this.subsubcode = subsubcode;
/*    */   }
/*    */   
/*    */   public QName getProblemHeader() {
/* 73 */     return this.problemHeader;
/*    */   }
/*    */   
/*    */   public QName getSubsubcode() {
/* 77 */     return this.subsubcode;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\model\InvalidAddressingHeaderException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
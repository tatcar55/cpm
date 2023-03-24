/*    */ package com.sun.istack;
/*    */ 
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.XMLStreamException;
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
/*    */ public class XMLStreamException2
/*    */   extends XMLStreamException
/*    */ {
/*    */   public XMLStreamException2(String msg) {
/* 53 */     super(msg);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(Throwable th) {
/* 57 */     super(th);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(String msg, Throwable th) {
/* 61 */     super(msg, th);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(String msg, Location location) {
/* 65 */     super(msg, location);
/*    */   }
/*    */   
/*    */   public XMLStreamException2(String msg, Location location, Throwable th) {
/* 69 */     super(msg, location, th);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 76 */     return getNestedException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\XMLStreamException2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
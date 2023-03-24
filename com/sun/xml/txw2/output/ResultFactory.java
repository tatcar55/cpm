/*    */ package com.sun.xml.txw2.output;
/*    */ 
/*    */ import javax.xml.transform.Result;
/*    */ import javax.xml.transform.dom.DOMResult;
/*    */ import javax.xml.transform.sax.SAXResult;
/*    */ import javax.xml.transform.stream.StreamResult;
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
/*    */ public abstract class ResultFactory
/*    */ {
/*    */   public static XmlSerializer createSerializer(Result result) {
/* 70 */     if (result instanceof SAXResult)
/* 71 */       return new SaxSerializer((SAXResult)result); 
/* 72 */     if (result instanceof DOMResult)
/* 73 */       return new DomSerializer((DOMResult)result); 
/* 74 */     if (result instanceof StreamResult)
/* 75 */       return new StreamSerializer((StreamResult)result); 
/* 76 */     if (result instanceof TXWResult) {
/* 77 */       return new TXWSerializer(((TXWResult)result).getWriter());
/*    */     }
/* 79 */     throw new UnsupportedOperationException("Unsupported Result type: " + result.getClass().getName());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\ResultFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
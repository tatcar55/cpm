/*    */ package com.sun.xml.wss.impl.transform;
/*    */ 
/*    */ import javax.xml.stream.StreamFilter;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ public class EnvelopedSignatureFilter
/*    */   implements StreamFilter
/*    */ {
/*    */   private static final String _SIGNATURE = "Signature";
/*    */   private static final String _NAMESPACE_URI = "http://www.w3.org/2000/09/xmldsig#";
/*    */   private boolean _skipSignatureElement = false;
/*    */   private boolean _skipDone = false;
/*    */   
/*    */   public boolean accept(XMLStreamReader reader) {
/* 64 */     if (this._skipDone)
/* 65 */       return false; 
/* 66 */     if (!this._skipSignatureElement) {
/* 67 */       if (reader.getEventType() == 1 && 
/* 68 */         "Signature".equals(reader.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(reader.getNamespaceURI())) {
/* 69 */         this._skipSignatureElement = true;
/* 70 */         return true;
/*    */       } 
/*    */     } else {
/*    */       
/* 74 */       if (reader.getEventType() == 2 && 
/* 75 */         "Signature".equals(reader.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(reader.getNamespaceURI())) {
/* 76 */         this._skipSignatureElement = false;
/* 77 */         this._skipDone = true;
/* 78 */         return true;
/*    */       } 
/*    */       
/* 81 */       return true;
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\EnvelopedSignatureFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class XMLWriterUtil
/*    */ {
/*    */   public static String encodeQName(XMLWriter writer, QName qname) {
/* 53 */     String namespaceURI = qname.getNamespaceURI();
/* 54 */     String localPart = qname.getLocalPart();
/*    */     
/* 56 */     if (namespaceURI == null || namespaceURI.equals("")) {
/* 57 */       return localPart;
/*    */     }
/* 59 */     String prefix = writer.getPrefix(namespaceURI);
/* 60 */     if (prefix == null) {
/* 61 */       writer.writeNamespaceDeclaration(namespaceURI);
/* 62 */       prefix = writer.getPrefix(namespaceURI);
/*    */     } 
/* 64 */     return prefix + ":" + localPart;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLWriterUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
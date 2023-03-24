/*    */ package com.sun.xml.bind.v2.model.util;
/*    */ 
/*    */ import com.sun.xml.bind.v2.TODO;
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
/*    */ public class ArrayInfoUtil
/*    */ {
/*    */   public static QName calcArrayTypeName(QName n) {
/*    */     String uri;
/* 22 */     if (n.getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema")) {
/* 23 */       TODO.checkSpec("this URI");
/* 24 */       uri = "http://jaxb.dev.java.net/array";
/*    */     } else {
/* 26 */       uri = n.getNamespaceURI();
/* 27 */     }  return new QName(uri, n.getLocalPart() + "Array");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\mode\\util\ArrayInfoUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
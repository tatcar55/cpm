/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.w3c.dom.Attr;
/*    */ import org.w3c.dom.NamedNodeMap;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class DomPostInitAction
/*    */   implements Runnable
/*    */ {
/*    */   private final Node node;
/*    */   private final XMLSerializer serializer;
/*    */   
/*    */   DomPostInitAction(Node node, XMLSerializer serializer) {
/* 66 */     this.node = node;
/* 67 */     this.serializer = serializer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 72 */     Set<String> declaredPrefixes = new HashSet<String>();
/* 73 */     for (Node n = this.node; n != null && n.getNodeType() == 1; n = n.getParentNode()) {
/* 74 */       NamedNodeMap atts = n.getAttributes();
/* 75 */       if (atts != null)
/* 76 */         for (int i = 0; i < atts.getLength(); i++) {
/* 77 */           Attr a = (Attr)atts.item(i);
/* 78 */           String nsUri = a.getNamespaceURI();
/* 79 */           if (nsUri != null && nsUri.equals("http://www.w3.org/2000/xmlns/")) {
/*    */             
/* 81 */             String prefix = a.getLocalName();
/* 82 */             if (prefix != null) {
/*    */               
/* 84 */               if (prefix.equals("xmlns")) {
/* 85 */                 prefix = "";
/*    */               }
/* 87 */               String value = a.getValue();
/* 88 */               if (value != null)
/*    */               {
/* 90 */                 if (declaredPrefixes.add(prefix))
/* 91 */                   this.serializer.addInscopeBinding(value, prefix); 
/*    */               }
/*    */             } 
/*    */           } 
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\DomPostInitAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
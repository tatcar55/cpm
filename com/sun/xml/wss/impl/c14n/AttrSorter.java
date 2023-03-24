/*    */ package com.sun.xml.wss.impl.c14n;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttrSorter
/*    */   implements Comparator
/*    */ {
/*    */   boolean namespaceSort = false;
/*    */   
/*    */   public AttrSorter(boolean namespaceSort) {
/* 65 */     this.namespaceSort = namespaceSort;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 70 */     if (this.namespaceSort) {
/* 71 */       return sortNamespaces(o1, o2);
/*    */     }
/* 73 */     return sortAttributes(o1, o2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int sortAttributes(Object object, Object object0) {
/* 79 */     Attribute attr = (Attribute)object;
/* 80 */     Attribute attr0 = (Attribute)object0;
/* 81 */     String uri = attr.getNamespaceURI();
/* 82 */     String uri0 = attr0.getNamespaceURI();
/* 83 */     int result = uri.compareTo(uri0);
/* 84 */     if (result == 0) {
/* 85 */       String lN = attr.getLocalName();
/* 86 */       String lN0 = attr0.getLocalName();
/* 87 */       result = lN.compareTo(lN0);
/*    */     } 
/* 89 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int sortNamespaces(Object object, Object object0) {
/* 94 */     AttributeNS attr = (AttributeNS)object;
/* 95 */     AttributeNS attr0 = (AttributeNS)object0;
/*    */     
/* 97 */     String lN = attr.getPrefix();
/* 98 */     String lN0 = attr0.getPrefix();
/* 99 */     return lN.compareTo(lN0);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\AttrSorter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
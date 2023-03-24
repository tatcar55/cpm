/*    */ package com.sun.xml.wss.impl.c14n;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StAXAttrSorter
/*    */   extends AttrSorter
/*    */ {
/*    */   public StAXAttrSorter(boolean value) {
/* 61 */     super(value);
/*    */   }
/*    */   
/*    */   protected int sortAttributes(Object object, Object object0) {
/* 65 */     StAXAttr attr = (StAXAttr)object;
/* 66 */     StAXAttr attr0 = (StAXAttr)object0;
/* 67 */     String uri = attr.getUri();
/* 68 */     String uri0 = attr0.getUri();
/* 69 */     int result = uri.compareTo(uri0);
/* 70 */     if (result == 0) {
/* 71 */       String lN = attr.getLocalName();
/* 72 */       String lN0 = attr0.getLocalName();
/* 73 */       result = lN.compareTo(lN0);
/*    */     } 
/* 75 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\StAXAttrSorter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
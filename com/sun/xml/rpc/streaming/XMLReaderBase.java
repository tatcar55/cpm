/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XMLReaderBase
/*    */   implements XMLReader
/*    */ {
/*    */   public int nextContent() {
/*    */     while (true) {
/* 40 */       int state = next();
/* 41 */       switch (state) {
/*    */         case 1:
/*    */         case 2:
/*    */         case 5:
/* 45 */           return state;
/*    */         case 3:
/* 47 */           if (getValue().trim().length() != 0) {
/* 48 */             return 3;
/*    */           }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int nextElementContent() {
/* 58 */     int state = nextContent();
/* 59 */     if (state == 3) {
/* 60 */       throw new XMLReaderException("xmlreader.unexpectedCharacterContent", getValue());
/*    */     }
/*    */ 
/*    */     
/* 64 */     return state;
/*    */   }
/*    */   
/*    */   public void skipElement() {
/* 68 */     skipElement(getElementId());
/*    */   }
/*    */   
/*    */   public abstract void skipElement(int paramInt);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLReaderBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
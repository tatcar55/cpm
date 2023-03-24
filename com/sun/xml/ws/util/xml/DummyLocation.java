/*    */ package com.sun.xml.ws.util.xml;
/*    */ 
/*    */ import javax.xml.stream.Location;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DummyLocation
/*    */   implements Location
/*    */ {
/* 53 */   public static final Location INSTANCE = new DummyLocation();
/*    */   
/*    */   public int getCharacterOffset() {
/* 56 */     return -1;
/*    */   }
/*    */   public int getColumnNumber() {
/* 59 */     return -1;
/*    */   }
/*    */   public int getLineNumber() {
/* 62 */     return -1;
/*    */   }
/*    */   public String getPublicId() {
/* 65 */     return null;
/*    */   }
/*    */   public String getSystemId() {
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\DummyLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
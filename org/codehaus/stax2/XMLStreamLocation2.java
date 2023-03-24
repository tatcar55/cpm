/*    */ package org.codehaus.stax2;
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
/*    */ public interface XMLStreamLocation2
/*    */   extends Location
/*    */ {
/* 16 */   public static final XMLStreamLocation2 NOT_AVAILABLE = new XMLStreamLocation2() {
/*    */       public XMLStreamLocation2 getContext() {
/* 18 */         return null;
/*    */       }
/*    */       
/*    */       public int getCharacterOffset() {
/* 22 */         return -1;
/*    */       }
/*    */       
/*    */       public int getColumnNumber() {
/* 26 */         return -1;
/*    */       }
/*    */       
/*    */       public int getLineNumber() {
/* 30 */         return -1;
/*    */       }
/*    */       
/*    */       public String getPublicId() {
/* 34 */         return null;
/*    */       }
/*    */       
/*    */       public String getSystemId() {
/* 38 */         return null;
/*    */       }
/*    */     };
/*    */   
/*    */   XMLStreamLocation2 getContext();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\XMLStreamLocation2.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
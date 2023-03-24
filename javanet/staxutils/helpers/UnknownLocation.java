/*    */ package javanet.staxutils.helpers;
/*    */ 
/*    */ import javanet.staxutils.StaticLocation;
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
/*    */ public final class UnknownLocation
/*    */   implements Location, StaticLocation
/*    */ {
/* 18 */   public static final UnknownLocation INSTANCE = new UnknownLocation();
/*    */ 
/*    */   
/*    */   public int getLineNumber() {
/* 22 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getColumnNumber() {
/* 28 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCharacterOffset() {
/* 34 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPublicId() {
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSystemId() {
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\UnknownLocation.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
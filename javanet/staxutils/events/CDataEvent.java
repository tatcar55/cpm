/*    */ package javanet.staxutils.events;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.events.Characters;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CDataEvent
/*    */   extends AbstractCharactersEvent
/*    */ {
/*    */   public CDataEvent(String data) {
/* 50 */     super(data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CDataEvent(String data, Location location) {
/* 56 */     super(data, location);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CDataEvent(String data, Location location, QName schemaType) {
/* 62 */     super(data, location, schemaType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CDataEvent(Characters that) {
/* 68 */     super(that);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCData() {
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 81 */     return 12;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isIgnorableWhiteSpace() {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\CDataEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
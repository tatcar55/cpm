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
/*    */ public class IgnorableSpaceEvent
/*    */   extends AbstractCharactersEvent
/*    */ {
/*    */   public IgnorableSpaceEvent(String data) {
/* 50 */     super(data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IgnorableSpaceEvent(String data, Location location) {
/* 56 */     super(data, location);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IgnorableSpaceEvent(String data, Location location, QName schemaType) {
/* 62 */     super(data, location, schemaType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IgnorableSpaceEvent(Characters that) {
/* 68 */     super(that);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 75 */     return 6;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCData() {
/* 81 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isIgnorableWhiteSpace() {
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\IgnorableSpaceEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
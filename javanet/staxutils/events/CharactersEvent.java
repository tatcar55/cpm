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
/*    */ public class CharactersEvent
/*    */   extends AbstractCharactersEvent
/*    */ {
/*    */   public CharactersEvent(String data) {
/* 50 */     super(data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CharactersEvent(String data, Location location) {
/* 56 */     super(data, location);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CharactersEvent(String data, Location location, QName schemaType) {
/* 62 */     super(data, location, schemaType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CharactersEvent(Characters that) {
/* 68 */     super(that);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCData() {
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isIgnorableWhiteSpace() {
/* 80 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 87 */     return 4;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\CharactersEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
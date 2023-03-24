/*    */ package javanet.staxutils.events;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.events.EndDocument;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EndDocumentEvent
/*    */   extends AbstractXMLEvent
/*    */   implements EndDocument
/*    */ {
/*    */   public EndDocumentEvent() {}
/*    */   
/*    */   public EndDocumentEvent(Location location) {
/* 54 */     super(location);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EndDocumentEvent(Location location, QName schemaType) {
/* 60 */     super(location, schemaType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EndDocumentEvent(EndDocument that) {
/* 66 */     super(that);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 73 */     return 8;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\EndDocumentEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
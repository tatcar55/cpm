/*    */ package javanet.staxutils.events;
/*    */ 
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.events.Comment;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommentEvent
/*    */   extends AbstractXMLEvent
/*    */   implements Comment
/*    */ {
/*    */   protected String text;
/*    */   
/*    */   public CommentEvent(String text) {
/* 52 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CommentEvent(String text, Location location) {
/* 58 */     super(location);
/* 59 */     this.text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommentEvent(Comment that) {
/* 70 */     super(that);
/* 71 */     this.text = that.getText();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 77 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 84 */     return 5;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\CommentEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
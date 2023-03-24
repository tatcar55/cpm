/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
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
/*    */ public class CommentEvent
/*    */   extends EventBase
/*    */   implements Comment
/*    */ {
/*    */   private String _text;
/*    */   
/*    */   public CommentEvent() {
/* 29 */     super(5);
/*    */   }
/*    */   
/*    */   public CommentEvent(String text) {
/* 33 */     this();
/* 34 */     this._text = text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "<!--" + this._text + "-->";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 50 */     return this._text;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 54 */     this._text = text;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\CommentEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
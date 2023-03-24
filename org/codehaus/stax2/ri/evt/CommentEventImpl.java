/*    */ package org.codehaus.stax2.ri.evt;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.events.Comment;
/*    */ import org.codehaus.stax2.XMLStreamWriter2;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommentEventImpl
/*    */   extends BaseEventImpl
/*    */   implements Comment
/*    */ {
/*    */   final String mContent;
/*    */   
/*    */   public CommentEventImpl(Location loc, String content) {
/* 19 */     super(loc);
/* 20 */     this.mContent = content;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 25 */     return this.mContent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 35 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeAsEncodedUnicode(Writer w) throws XMLStreamException {
/*    */     try {
/* 42 */       w.write("<!--");
/* 43 */       w.write(this.mContent);
/* 44 */       w.write("-->");
/* 45 */     } catch (IOException ie) {
/* 46 */       throwFromIOE(ie);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeUsing(XMLStreamWriter2 w) throws XMLStreamException {
/* 52 */     w.writeComment(this.mContent);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 63 */     if (o == this) return true; 
/* 64 */     if (o == null) return false; 
/* 65 */     if (!(o instanceof Comment)) return false;
/*    */     
/* 67 */     Comment other = (Comment)o;
/* 68 */     return this.mContent.equals(other.getText());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 73 */     return this.mContent.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\ri\evt\CommentEventImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.codehaus.stax2.ri.evt;
/*    */ 
/*    */ import java.io.Writer;
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.events.EndDocument;
/*    */ import org.codehaus.stax2.XMLStreamWriter2;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EndDocumentEventImpl
/*    */   extends BaseEventImpl
/*    */   implements EndDocument
/*    */ {
/*    */   public EndDocumentEventImpl(Location loc) {
/* 16 */     super(loc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 26 */     return 8;
/*    */   }
/*    */   
/*    */   public boolean isEndDocument() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeAsEncodedUnicode(Writer w) throws XMLStreamException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeUsing(XMLStreamWriter2 w) throws XMLStreamException {
/* 41 */     w.writeEndDocument();
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
/* 52 */     if (o == this) return true; 
/* 53 */     if (o == null) return false; 
/* 54 */     return o instanceof EndDocument;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     return 8;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\ri\evt\EndDocumentEventImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
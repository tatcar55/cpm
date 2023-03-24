/*    */ package javanet.staxutils.helpers;
/*    */ 
/*    */ import javax.xml.namespace.NamespaceContext;
/*    */ import javax.xml.stream.XMLEventReader;
/*    */ import javax.xml.stream.XMLEventWriter;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.events.XMLEvent;
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
/*    */ public abstract class EventWriterDelegate
/*    */   implements XMLEventWriter
/*    */ {
/*    */   protected final XMLEventWriter out;
/*    */   
/*    */   protected EventWriterDelegate(XMLEventWriter out) {
/* 50 */     this.out = out;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 57 */     this.out.setNamespaceContext(context);
/*    */   }
/*    */   
/*    */   public NamespaceContext getNamespaceContext() {
/* 61 */     return this.out.getNamespaceContext();
/*    */   }
/*    */   
/*    */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 65 */     this.out.setDefaultNamespace(uri);
/*    */   }
/*    */   
/*    */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 69 */     this.out.setPrefix(prefix, uri);
/*    */   }
/*    */   
/*    */   public String getPrefix(String uri) throws XMLStreamException {
/* 73 */     return this.out.getPrefix(uri);
/*    */   }
/*    */   
/*    */   public void add(XMLEvent event) throws XMLStreamException {
/* 77 */     this.out.add(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(XMLEventReader reader) throws XMLStreamException {
/* 82 */     while (reader.hasNext()) {
/* 83 */       add(reader.nextEvent());
/*    */     }
/*    */   }
/*    */   
/*    */   public void flush() throws XMLStreamException {
/* 88 */     this.out.flush();
/*    */   }
/*    */   
/*    */   public void close() throws XMLStreamException {
/* 92 */     this.out.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\EventWriterDelegate.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
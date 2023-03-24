/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import java.io.IOException;
/*    */ import javax.activation.MimeType;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import org.xml.sax.SAXException;
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
/*    */ public final class MimeTypedTransducer<V>
/*    */   extends FilterTransducer<V>
/*    */ {
/*    */   private final MimeType expectedMimeType;
/*    */   
/*    */   public MimeTypedTransducer(Transducer<V> core, MimeType expectedMimeType) {
/* 67 */     super(core);
/* 68 */     this.expectedMimeType = expectedMimeType;
/*    */   }
/*    */ 
/*    */   
/*    */   public CharSequence print(V o) throws AccessorException {
/* 73 */     XMLSerializer w = XMLSerializer.getInstance();
/* 74 */     MimeType old = w.setExpectedMimeType(this.expectedMimeType);
/*    */     try {
/* 76 */       return this.core.print(o);
/*    */     } finally {
/* 78 */       w.setExpectedMimeType(old);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeText(XMLSerializer w, V o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 84 */     MimeType old = w.setExpectedMimeType(this.expectedMimeType);
/*    */     try {
/* 86 */       this.core.writeText(w, o, fieldName);
/*    */     } finally {
/* 88 */       w.setExpectedMimeType(old);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeLeafElement(XMLSerializer w, Name tagName, V o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 94 */     MimeType old = w.setExpectedMimeType(this.expectedMimeType);
/*    */     try {
/* 96 */       this.core.writeLeafElement(w, tagName, o, fieldName);
/*    */     } finally {
/* 98 */       w.setExpectedMimeType(old);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\MimeTypedTransducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
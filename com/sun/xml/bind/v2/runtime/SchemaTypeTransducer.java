/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import java.io.IOException;
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ public class SchemaTypeTransducer<V>
/*    */   extends FilterTransducer<V>
/*    */ {
/*    */   private final QName schemaType;
/*    */   
/*    */   public SchemaTypeTransducer(Transducer<V> core, QName schemaType) {
/* 68 */     super(core);
/* 69 */     this.schemaType = schemaType;
/*    */   }
/*    */ 
/*    */   
/*    */   public CharSequence print(V o) throws AccessorException {
/* 74 */     XMLSerializer w = XMLSerializer.getInstance();
/* 75 */     QName old = w.setSchemaType(this.schemaType);
/*    */     try {
/* 77 */       return this.core.print(o);
/*    */     } finally {
/* 79 */       w.setSchemaType(old);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeText(XMLSerializer w, V o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 85 */     QName old = w.setSchemaType(this.schemaType);
/*    */     try {
/* 87 */       this.core.writeText(w, o, fieldName);
/*    */     } finally {
/* 89 */       w.setSchemaType(old);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeLeafElement(XMLSerializer w, Name tagName, V o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 95 */     QName old = w.setSchemaType(this.schemaType);
/*    */     try {
/* 97 */       this.core.writeLeafElement(w, tagName, o, fieldName);
/*    */     } finally {
/* 99 */       w.setSchemaType(old);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\SchemaTypeTransducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
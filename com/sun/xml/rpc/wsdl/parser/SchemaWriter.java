/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.document.schema.Schema;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*     */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaWriter
/*     */ {
/*     */   public void write(SchemaDocument document, OutputStream os) throws IOException {
/*  53 */     WriterContext context = new WriterContext(os);
/*  54 */     writeSchema(context, document.getSchema());
/*  55 */     context.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSchema(WriterContext context, Schema schema) throws IOException {
/*  60 */     context.push();
/*     */     
/*  62 */     try { writeTopSchemaElement(context, schema); }
/*  63 */     catch (Exception e) {  }
/*     */     finally
/*  65 */     { context.pop(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeTopSchemaElement(WriterContext context, Schema schema) throws IOException {
/*  71 */     SchemaElement schemaElement = schema.getContent();
/*  72 */     QName name = schemaElement.getQName();
/*     */ 
/*     */     
/*  75 */     for (Iterator<String> iterator2 = schema.prefixes(); iterator2.hasNext(); ) {
/*  76 */       String prefix = iterator2.next();
/*  77 */       String expectedURI = schema.getURIForPrefix(prefix);
/*  78 */       if (!expectedURI.equals(context.getNamespaceURI(prefix))) {
/*  79 */         context.declarePrefix(prefix, expectedURI);
/*     */       }
/*     */     } 
/*     */     
/*  83 */     for (Iterator<String> iterator1 = schemaElement.prefixes(); iterator1.hasNext(); ) {
/*  84 */       String prefix = iterator1.next();
/*  85 */       String uri = schemaElement.getURIForPrefix(prefix);
/*  86 */       context.declarePrefix(prefix, uri);
/*     */     } 
/*     */     
/*  89 */     context.writeStartTag(name);
/*     */     
/*  91 */     for (Iterator<SchemaAttribute> iterator = schemaElement.attributes(); iterator.hasNext(); ) {
/*  92 */       SchemaAttribute attribute = iterator.next();
/*  93 */       if (attribute.getNamespaceURI() == null) {
/*  94 */         context.writeAttribute(attribute.getLocalName(), attribute.getValue(context));
/*     */         
/*     */         continue;
/*     */       } 
/*  98 */       context.writeAttribute(context.getQNameString(attribute.getQName()), attribute.getValue(context));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     context.writeAllPendingNamespaceDeclarations();
/*     */     
/* 106 */     for (Iterator<SchemaElement> iter = schemaElement.children(); iter.hasNext(); ) {
/* 107 */       SchemaElement child = iter.next();
/* 108 */       writeSchemaElement(context, child);
/*     */     } 
/*     */     
/* 111 */     context.writeEndTag(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeSchemaElement(WriterContext context, SchemaElement schemaElement) throws IOException {
/* 118 */     QName name = schemaElement.getQName();
/*     */     
/* 120 */     if (schemaElement.declaresPrefixes()) {
/* 121 */       context.push();
/*     */     }
/*     */     
/* 124 */     context.writeStartTag(name);
/*     */     
/* 126 */     if (schemaElement.declaresPrefixes()) {
/* 127 */       for (Iterator<String> iterator1 = schemaElement.prefixes(); iterator1.hasNext(); ) {
/* 128 */         String prefix = iterator1.next();
/* 129 */         String uri = schemaElement.getURIForPrefix(prefix);
/* 130 */         context.writeNamespaceDeclaration(prefix, uri);
/* 131 */         context.declarePrefix(prefix, uri);
/*     */       } 
/*     */     }
/*     */     
/* 135 */     for (Iterator<SchemaAttribute> iterator = schemaElement.attributes(); iterator.hasNext(); ) {
/* 136 */       SchemaAttribute attribute = iterator.next();
/* 137 */       if (attribute.getNamespaceURI() == null) {
/* 138 */         context.writeAttribute(attribute.getLocalName(), attribute.getValue(context));
/*     */         
/*     */         continue;
/*     */       } 
/* 142 */       context.writeAttribute(context.getQNameString(attribute.getQName()), attribute.getValue(context));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     for (Iterator<SchemaElement> iter = schemaElement.children(); iter.hasNext(); ) {
/* 149 */       SchemaElement child = iter.next();
/* 150 */       writeSchemaElement(context, child);
/*     */     } 
/*     */     
/* 153 */     context.writeEndTag(name);
/*     */     
/* 155 */     if (schemaElement.declaresPrefixes())
/* 156 */       context.pop(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\SchemaWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
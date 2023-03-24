/*     */ package com.sun.xml.rpc.processor.model.exporter;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
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
/*     */ public class PGraphExporter
/*     */   extends ExporterBase
/*     */ {
/*     */   public PGraphExporter(OutputStream s) {
/*  43 */     super(s);
/*     */   }
/*     */   
/*     */   public void doExport(PGraph g) {
/*  47 */     internalDoExport(g);
/*     */   }
/*     */   
/*     */   protected void internalDoExport(Object root) {
/*  51 */     initialize();
/*  52 */     PGraph graph = (PGraph)root;
/*  53 */     this.writer.startElement(graph.getName());
/*  54 */     if (graph.getVersion() != null) {
/*  55 */       this.writer.writeAttribute("version", graph.getVersion());
/*     */     }
/*  57 */     int id = getId(graph.getRoot());
/*  58 */     while (!this.obj2serializeStack.empty()) {
/*  59 */       Object obj = this.obj2serializeStack.pop();
/*  60 */       this.obj2serialize.remove(obj);
/*  61 */       visit(obj);
/*     */     } 
/*  63 */     this.writer.endElement();
/*  64 */     this.writer.close();
/*     */   }
/*     */   
/*     */   protected void define(Object obj, Integer id) {
/*  68 */     if (obj instanceof PObject) {
/*  69 */       PObject anObject = (PObject)obj;
/*  70 */       this.writer.startElement(getDefineObjectName());
/*  71 */       this.writer.writeAttribute("id", id.toString());
/*  72 */       this.writer.writeAttribute("type", anObject.getType());
/*  73 */       this.writer.endElement();
/*  74 */       this.obj2serialize.add(obj);
/*  75 */       this.obj2serializeStack.push(obj);
/*     */     } else {
/*  77 */       super.define(obj, id);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void failUnsupportedClass(Class klass) {
/*  82 */     throw new ModelException("model.exporter.unsupportedClass", klass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName getContainerName() {
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   protected void visit(Object obj) {
/*  93 */     if (obj == null) {
/*     */       return;
/*     */     }
/*  96 */     if (obj instanceof PObject) {
/*  97 */       PObject anObject = (PObject)obj;
/*  98 */       for (Iterator<String> iter = anObject.getPropertyNames(); iter.hasNext(); ) {
/*  99 */         String name = iter.next();
/* 100 */         Object value = anObject.getProperty(name);
/* 101 */         property(name, obj, value);
/*     */       } 
/*     */     } else {
/* 104 */       super.visit(obj);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\PGraphExporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
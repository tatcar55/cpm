/*     */ package com.sun.xml.rpc.processor.model.exporter;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import java.io.InputStream;
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
/*     */ public class PGraphImporter
/*     */   extends ImporterBase
/*     */ {
/*     */   public PGraphImporter(InputStream s) {
/*  44 */     super(s);
/*     */   }
/*     */   
/*     */   public PGraph doImport() {
/*  48 */     return (PGraph)internalDoImport();
/*     */   }
/*     */   
/*     */   protected Object internalDoImport() {
/*  52 */     initialize();
/*  53 */     PGraph graph = new PGraph();
/*     */     
/*  55 */     this.reader.nextElementContent();
/*  56 */     if (this.reader.getState() != 1) {
/*  57 */       failInvalidSyntax(this.reader);
/*     */     }
/*  59 */     graph.setName(this.reader.getName());
/*  60 */     String versionAttr = getRequiredAttribute(this.reader, "version");
/*  61 */     graph.setVersion(versionAttr);
/*  62 */     while (this.reader.nextElementContent() != 2) {
/*  63 */       if (this.reader.getName().equals(getDefineImmediateObjectName())) {
/*  64 */         parseDefineImmediateObject(this.reader); continue;
/*  65 */       }  if (this.reader.getName().equals(getDefineObjectName())) {
/*  66 */         parseDefineObject(this.reader); continue;
/*  67 */       }  if (this.reader.getName().equals(getPropertyName())) {
/*  68 */         parseProperty(this.reader); continue;
/*     */       } 
/*  70 */       failInvalidSyntax(this.reader);
/*     */     } 
/*     */     
/*  73 */     XMLReaderUtil.verifyReaderState(this.reader, 2);
/*     */ 
/*     */     
/*  76 */     graph.setRoot((PObject)this.id2obj.get(new Integer(1)));
/*  77 */     return graph;
/*     */   }
/*     */   
/*     */   protected void parseDefineObject(XMLReader reader) {
/*  81 */     String idAttr = getRequiredAttribute(reader, "id");
/*  82 */     String typeAttr = getRequiredAttribute(reader, "type");
/*  83 */     Integer id = parseId(reader, idAttr);
/*  84 */     if (getObjectForId(id) != null) {
/*  85 */       failInvalidId(reader, id);
/*     */     }
/*  87 */     PObject obj = createPObject();
/*  88 */     obj.setType(typeAttr);
/*  89 */     this.id2obj.put(id, obj);
/*  90 */     verifyNoContent(reader);
/*     */   }
/*     */   
/*     */   protected PObject createPObject() {
/*  94 */     return new PObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName getContainerName() {
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void property(XMLReader reader, Object subject, String name, Object value) {
/* 106 */     if (subject instanceof PObject) {
/* 107 */       PObject obj = (PObject)subject;
/* 108 */       obj.setProperty(name, value);
/*     */     } else {
/* 110 */       super.property(reader, subject, name, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void failInvalidSyntax(XMLReader reader) {
/* 115 */     throw new ModelException("model.importer.syntaxError", Integer.toString(reader.getLineNumber()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void failInvalidVersion(XMLReader reader, String version) {
/* 120 */     throw new ModelException("model.importer.invalidVersion", new Object[] { Integer.toString(reader.getLineNumber()), version });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void failInvalidMinorMinorOrPatchVersion(XMLReader reader, String targetVersion, String currentVersion) {
/* 128 */     throw new ModelException("model.importer.invalidMinorMinorOrPatchVersion", new Object[] { Integer.toString(reader.getLineNumber()), targetVersion, currentVersion });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void failInvalidClass(XMLReader reader, String className) {
/* 137 */     throw new ModelException("model.importer.invalidClass", new Object[] { Integer.toString(reader.getLineNumber()), className });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void failInvalidId(XMLReader reader, Integer id) {
/* 143 */     throw new ModelException("model.importer.invalidId", new Object[] { Integer.toString(reader.getLineNumber()), id.toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void failInvalidLiteral(XMLReader reader, String type, String value) {
/* 151 */     throw new ModelException("model.importer.invalidLiteral", Integer.toString(reader.getLineNumber()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void failInvalidProperty(XMLReader reader, Object subject, String name, Object value) {
/* 158 */     throw new ModelException("model.importer.invalidProperty", Integer.toString(reader.getLineNumber()));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\PGraphImporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
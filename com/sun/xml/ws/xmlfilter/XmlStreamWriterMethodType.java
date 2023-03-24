/*     */ package com.sun.xml.ws.xmlfilter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum XmlStreamWriterMethodType
/*     */ {
/*  50 */   WRITE_START_DOCUMENT("writeStartDocument", true),
/*  51 */   WRITE_END_DOCUMENT("writeEndDocument", true),
/*  52 */   WRITE_START_ELEMENT("writeStartElement", true),
/*  53 */   WRITE_END_ELEMENT("writeEndElement", true),
/*  54 */   WRITE_EMPTY_ELEMENT("writeEmptyElement", true),
/*  55 */   WRITE_ATTRIBUTE("writeAttribute", true),
/*  56 */   WRITE_CHARACTERS("writeCharacters", true),
/*  57 */   WRITE_PROCESSING_INSTRUCTION("writeProcessingInstruction", true),
/*  58 */   WRITE_ENTITY_REFERENCE("writeEntityRef", true),
/*  59 */   WRITE_CDATA("writeCData", true),
/*  60 */   WRITE_COMMENT("writeComment", true),
/*  61 */   WRITE_DTD("writeDTD", true),
/*  62 */   WRITE_DEFAULT_NAMESPACE("writeDefaultNamespace", true),
/*  63 */   WRITE_NAMESPACE("writeNamespace", true),
/*     */   
/*  65 */   GET_NAMESPACE_CONTEXT("getNamespaceContext", false),
/*  66 */   GET_PREFIX("getPrefix", false),
/*  67 */   GET_PROPERTY("getProperty", false),
/*     */   
/*  69 */   SET_DEFAULT_NAMESPACE("setDefaultNamespace", true),
/*  70 */   SET_NAMESPACE_CONTEXT("setNamespaceContext", true),
/*  71 */   SET_PREFIX("setPrefix", true),
/*     */   
/*  73 */   CLOSE("close", false),
/*  74 */   FLUSH("flush", true),
/*     */   
/*  76 */   UNKNOWN("", true);
/*     */   
/*     */   static XmlStreamWriterMethodType getMethodType(String methodName) {
/*  79 */     if (methodName != null && methodName.length() > 0) {
/*  80 */       for (XmlStreamWriterMethodType type : values()) {
/*  81 */         if (type.methodName.equals(methodName)) {
/*  82 */           return type;
/*     */         }
/*     */       } 
/*     */     }
/*  86 */     return UNKNOWN;
/*     */   }
/*     */   private String methodName;
/*     */   private boolean filterable;
/*     */   
/*     */   XmlStreamWriterMethodType(String methodName, boolean isFilterable) {
/*  92 */     this.methodName = methodName;
/*  93 */     this.filterable = isFilterable;
/*     */   }
/*     */   
/*     */   public String getMethodName() {
/*  97 */     return this.methodName;
/*     */   }
/*     */   
/*     */   public boolean isFilterable() {
/* 101 */     return this.filterable;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\XmlStreamWriterMethodType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
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
/*     */ public class LoggingXMLReader
/*     */   implements XMLReader
/*     */ {
/*     */   PrintWriter log;
/*     */   XMLReader reader;
/*     */   
/*     */   public LoggingXMLReader(OutputStream log, XMLReader reader) {
/*  44 */     this(new PrintWriter(log), reader);
/*     */   }
/*     */   
/*     */   public LoggingXMLReader(PrintWriter log, XMLReader reader) {
/*  48 */     this.log = log;
/*  49 */     this.reader = reader;
/*     */   }
/*     */   
/*     */   public int next() {
/*  53 */     int nextState = this.reader.next();
/*  54 */     this.log.println("Next state: " + XMLReaderUtil.getStateName(this.reader));
/*  55 */     return nextState;
/*     */   }
/*     */   public int nextContent() {
/*  58 */     int nextState = this.reader.nextContent();
/*  59 */     this.log.println("Next content state: " + XMLReaderUtil.getStateName(this.reader));
/*     */     
/*  61 */     return nextState;
/*     */   }
/*     */   public int nextElementContent() {
/*  64 */     int nextState = this.reader.nextElementContent();
/*  65 */     this.log.println("Next element content state: " + XMLReaderUtil.getStateName(this.reader));
/*     */ 
/*     */     
/*  68 */     return nextState;
/*     */   }
/*     */   public int getState() {
/*  71 */     int currentState = this.reader.getState();
/*  72 */     this.log.println("Current state: " + XMLReaderUtil.getStateName(this.reader));
/*  73 */     return currentState;
/*     */   }
/*     */   public QName getName() {
/*  76 */     QName name = this.reader.getName();
/*  77 */     this.log.println("name: " + name);
/*  78 */     return name;
/*     */   }
/*     */   public String getURI() {
/*  81 */     String uri = this.reader.getURI();
/*  82 */     this.log.println("uri: " + uri);
/*  83 */     return uri;
/*     */   }
/*     */   public String getLocalName() {
/*  86 */     String localName = this.reader.getLocalName();
/*  87 */     this.log.println("localName: " + localName);
/*  88 */     return localName;
/*     */   }
/*     */   public Attributes getAttributes() {
/*  91 */     Attributes attributes = this.reader.getAttributes();
/*  92 */     this.log.println("attributes: " + attributes);
/*  93 */     return attributes;
/*     */   }
/*     */   public String getValue() {
/*  96 */     String value = this.reader.getValue();
/*  97 */     this.log.println("value: " + value);
/*  98 */     return value;
/*     */   }
/*     */   public int getElementId() {
/* 101 */     int id = this.reader.getElementId();
/* 102 */     this.log.println("id: " + id);
/* 103 */     return id;
/*     */   }
/*     */   public int getLineNumber() {
/* 106 */     int lineNumber = this.reader.getLineNumber();
/* 107 */     this.log.println("lineNumber: " + lineNumber);
/* 108 */     return lineNumber;
/*     */   }
/*     */   public String getURI(String prefix) {
/* 111 */     String uri = this.reader.getURI(prefix);
/* 112 */     this.log.println("uri for: " + prefix + ": " + uri);
/* 113 */     return uri;
/*     */   }
/*     */   public Iterator getPrefixes() {
/* 116 */     return this.reader.getPrefixes();
/*     */   }
/*     */   public XMLReader recordElement() {
/* 119 */     return this.reader.recordElement();
/*     */   }
/*     */   public void skipElement() {
/* 122 */     this.reader.skipElement();
/* 123 */     this.log.println("Skipped to: " + XMLReaderUtil.getStateName(this.reader));
/*     */   }
/*     */   public void skipElement(int elementId) {
/* 126 */     this.reader.skipElement(elementId);
/* 127 */     this.log.println("Skipped to: " + XMLReaderUtil.getStateName(this.reader));
/*     */   }
/*     */   public void close() {
/* 130 */     this.reader.close();
/* 131 */     this.log.println("reader closed");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\LoggingXMLReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
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
/*     */ public class ParserPool
/*     */ {
/*     */   private final BlockingQueue queue;
/*     */   private SAXParserFactory factory;
/*     */   private int capacity;
/*     */   
/*     */   public ParserPool(int capacity) {
/*  63 */     this.capacity = capacity;
/*  64 */     this.queue = new ArrayBlockingQueue(capacity);
/*     */     
/*  66 */     this.factory = new SAXParserFactoryImpl();
/*  67 */     this.factory.setNamespaceAware(true);
/*  68 */     for (int i = 0; i < capacity; i++) {
/*     */       try {
/*  70 */         this.queue.put(this.factory.newSAXParser());
/*  71 */       } catch (InterruptedException ex) {
/*  72 */         Thread.currentThread().interrupt();
/*  73 */         throw new RuntimeException(ex);
/*  74 */       } catch (ParserConfigurationException ex) {
/*  75 */         throw new RuntimeException(ex);
/*  76 */       } catch (SAXException ex) {
/*  77 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXParser get() throws ParserConfigurationException, SAXException {
/*     */     try {
/*  86 */       return this.queue.take();
/*  87 */     } catch (InterruptedException ex) {
/*  88 */       throw new SAXException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(SAXParser parser) {
/*  94 */     this.queue.offer(parser);
/*     */   }
/*     */   
/*     */   public void returnParser(SAXParser saxParser) {
/*  98 */     saxParser.reset();
/*  99 */     resetSaxParser(saxParser);
/* 100 */     put(saxParser);
/*     */   }
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
/*     */   private void resetSaxParser(SAXParser parser) {
/*     */     try {
/* 115 */       SymbolTable table = new SymbolTable();
/* 116 */       parser.setProperty("http://apache.org/xml/properties/internal/symbol-table", table);
/*     */     }
/* 118 */     catch (SAXNotRecognizedException ex) {
/*     */     
/* 120 */     } catch (SAXNotSupportedException ex) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\ParserPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
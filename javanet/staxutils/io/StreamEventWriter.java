/*     */ package javanet.staxutils.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import javanet.staxutils.BaseXMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamEventWriter
/*     */   extends BaseXMLEventWriter
/*     */ {
/*     */   private Writer writer;
/*     */   private StartElement savedStart;
/*     */   
/*     */   public StreamEventWriter(File file) throws IOException {
/*  69 */     this(new FileWriter(file));
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
/*     */   public StreamEventWriter(OutputStream os) {
/*  81 */     this(new OutputStreamWriter(os));
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
/*     */   public StreamEventWriter(Writer writer) {
/*  93 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() throws XMLStreamException {
/*  99 */     super.flush();
/*     */     
/*     */     try {
/* 102 */       this.writer.flush();
/*     */     }
/* 104 */     catch (IOException e) {
/*     */       
/* 106 */       throw new XMLStreamException(e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendEvent(XMLEvent event) throws XMLStreamException {
/*     */     try {
/* 127 */       if (this.savedStart != null) {
/*     */         
/* 129 */         StartElement start = this.savedStart;
/* 130 */         this.savedStart = null;
/*     */         
/* 132 */         if (event.getEventType() == 2) {
/*     */ 
/*     */ 
/*     */           
/* 136 */           XMLWriterUtils.writeStartElement(start, true, this.writer);
/* 137 */           this.writer.flush();
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 143 */         XMLWriterUtils.writeStartElement(start, false, this.writer);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 149 */       if (event.isStartElement())
/*     */       {
/* 151 */         this.savedStart = event.asStartElement();
/*     */       }
/*     */       else
/*     */       {
/* 155 */         event.writeAsEncodedUnicode(this.writer);
/*     */       }
/*     */     
/*     */     }
/* 159 */     catch (IOException e) {
/*     */       
/* 161 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\io\StreamEventWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
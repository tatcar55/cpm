/*     */ package com.sun.xml.ws.commons.xmlutil;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public final class Converter
/*     */ {
/*     */   public static final String UTF_8 = "UTF-8";
/*  68 */   private static final Logger LOGGER = Logger.getLogger(Converter.class);
/*  69 */   private static final XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
/*  70 */   private static final AtomicBoolean logMissingStaxUtilsWarning = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Throwable throwable) {
/*  79 */     if (throwable == null) {
/*  80 */       return "[ No exception ]";
/*     */     }
/*     */     
/*  83 */     StringWriter stringOut = new StringWriter();
/*  84 */     throwable.printStackTrace(new PrintWriter(stringOut));
/*     */     
/*  86 */     return stringOut.toString();
/*     */   }
/*     */   
/*     */   public static String toString(Packet packet) {
/*  90 */     if (packet == null)
/*  91 */       return "[ Null packet ]"; 
/*  92 */     if (packet.getMessage() == null) {
/*  93 */       return "[ Empty packet ]";
/*     */     }
/*     */     
/*  96 */     return toString(packet.getMessage());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toString(Message message) {
/* 101 */     if (message == null) {
/* 102 */       return "[ Null message ]";
/*     */     }
/* 104 */     StringWriter stringOut = null;
/*     */     try {
/* 106 */       stringOut = new StringWriter();
/* 107 */       XMLStreamWriter writer = null;
/*     */       try {
/* 109 */         writer = xmlOutputFactory.createXMLStreamWriter(stringOut);
/* 110 */         writer = createIndenter(writer);
/* 111 */         message.copy().writeTo(writer);
/* 112 */       } catch (Exception e) {
/* 113 */         LOGGER.log(Level.WARNING, "Unexpected exception occured while dumping message", e);
/*     */       } finally {
/* 115 */         if (writer != null) {
/*     */           try {
/* 117 */             writer.close();
/* 118 */           } catch (XMLStreamException ignored) {
/* 119 */             LOGGER.fine("Unexpected exception occured while closing XMLStreamWriter", ignored);
/*     */           } 
/*     */         }
/*     */       } 
/* 123 */       return stringOut.toString();
/*     */     } finally {
/* 125 */       if (stringOut != null) {
/*     */         try {
/* 127 */           stringOut.close();
/* 128 */         } catch (IOException ex) {
/* 129 */           LOGGER.finest("An exception occured when trying to close StringWriter", ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static byte[] toBytes(Message message, String encoding) throws XMLStreamException {
/* 136 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/* 139 */       if (message != null) {
/* 140 */         XMLStreamWriter xsw = xmlOutputFactory.createXMLStreamWriter(baos, encoding);
/*     */         try {
/* 142 */           message.writeTo(xsw);
/*     */         } finally {
/*     */           try {
/* 145 */             xsw.close();
/* 146 */           } catch (XMLStreamException ex) {
/* 147 */             LOGGER.warning("Unexpected exception occured while closing XMLStreamWriter", ex);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 152 */       return baos.toByteArray();
/*     */     } finally {
/*     */       try {
/* 155 */         baos.close();
/* 156 */       } catch (IOException ex) {
/* 157 */         LOGGER.warning("Unexpected exception occured while closing ByteArrayOutputStream", ex);
/*     */       } 
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
/*     */   public static Message toMessage(@NotNull InputStream dataStream, String encoding) throws XMLStreamException {
/* 172 */     XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(dataStream, encoding);
/* 173 */     return Messages.create(xsr);
/*     */   }
/*     */   
/*     */   public static String messageDataToString(byte[] data, String encoding) {
/*     */     try {
/* 178 */       return toString(toMessage(new ByteArrayInputStream(data), encoding));
/*     */     }
/* 180 */     catch (XMLStreamException ex) {
/* 181 */       LOGGER.warning("Unexpected exception occured while converting message data to string", ex);
/* 182 */       return "[ Message Data Conversion Failed ]";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XMLStreamWriter createIndenter(XMLStreamWriter writer) {
/*     */     try {
/* 194 */       Class<?> clazz = Converter.class.getClassLoader().loadClass("javanet.staxutils.IndentingXMLStreamWriter");
/* 195 */       Constructor<?> c = clazz.getConstructor(new Class[] { XMLStreamWriter.class });
/* 196 */       writer = XMLStreamWriter.class.cast(c.newInstance(new Object[] { writer }));
/* 197 */     } catch (Exception ex) {
/*     */ 
/*     */       
/* 200 */       if (logMissingStaxUtilsWarning.compareAndSet(false, true)) {
/* 201 */         LOGGER.log(Level.WARNING, "Put stax-utils.jar to the classpath to indent the dump output", ex);
/*     */       }
/*     */     } 
/* 204 */     return writer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\xmlutil\Converter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
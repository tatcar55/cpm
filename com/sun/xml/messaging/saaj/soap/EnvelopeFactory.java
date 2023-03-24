/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.util.JAXMStreamSource;
/*     */ import com.sun.xml.messaging.saaj.util.ParserPool;
/*     */ import com.sun.xml.messaging.saaj.util.RejectDoctypeSaxFilter;
/*     */ import com.sun.xml.messaging.saaj.util.transform.EfficientStreamingTransformer;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.XMLReader;
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
/*     */ public class EnvelopeFactory
/*     */ {
/*  68 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*     */ 
/*     */   
/*  71 */   private static ParserPool parserPool = new ParserPool(5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Envelope createEnvelope(Source src, SOAPPartImpl soapPart) throws SOAPException {
/*  78 */     SAXParser saxParser = null;
/*  79 */     if (src instanceof javax.xml.transform.stream.StreamSource) {
/*  80 */       RejectDoctypeSaxFilter rejectDoctypeSaxFilter; if (src instanceof JAXMStreamSource) {
/*     */         try {
/*  82 */           if (!SOAPPartImpl.lazyContentLength) {
/*  83 */             ((JAXMStreamSource)src).reset();
/*     */           }
/*  85 */         } catch (IOException ioe) {
/*  86 */           log.severe("SAAJ0515.source.reset.exception");
/*  87 */           throw new SOAPExceptionImpl(ioe);
/*     */         } 
/*     */       }
/*     */       try {
/*  91 */         saxParser = parserPool.get();
/*  92 */       } catch (Exception e) {
/*  93 */         log.severe("SAAJ0601.util.newSAXParser.exception");
/*  94 */         throw new SOAPExceptionImpl("Couldn't get a SAX parser while constructing a envelope", e);
/*     */       } 
/*     */ 
/*     */       
/*  98 */       InputSource is = SAXSource.sourceToInputSource(src);
/*  99 */       if (is.getEncoding() == null && soapPart.getSourceCharsetEncoding() != null) {
/* 100 */         is.setEncoding(soapPart.getSourceCharsetEncoding());
/*     */       }
/*     */       
/*     */       try {
/* 104 */         rejectDoctypeSaxFilter = new RejectDoctypeSaxFilter(saxParser);
/* 105 */       } catch (Exception ex) {
/* 106 */         log.severe("SAAJ0510.soap.cannot.create.envelope");
/* 107 */         throw new SOAPExceptionImpl("Unable to create envelope from given source: ", ex);
/*     */       } 
/*     */ 
/*     */       
/* 111 */       src = new SAXSource((XMLReader)rejectDoctypeSaxFilter, is);
/*     */     } 
/*     */     
/*     */     try {
/* 115 */       Transformer transformer = EfficientStreamingTransformer.newTransformer();
/*     */       
/* 117 */       DOMResult result = new DOMResult(soapPart);
/* 118 */       transformer.transform(src, result);
/*     */       
/* 120 */       Envelope env = (Envelope)soapPart.getEnvelope();
/* 121 */       return env;
/* 122 */     } catch (Exception ex) {
/* 123 */       if (ex instanceof SOAPVersionMismatchException) {
/* 124 */         throw (SOAPVersionMismatchException)ex;
/*     */       }
/* 126 */       log.severe("SAAJ0511.soap.cannot.create.envelope");
/* 127 */       throw new SOAPExceptionImpl("Unable to create envelope from given source: ", ex);
/*     */     }
/*     */     finally {
/*     */       
/* 131 */       if (saxParser != null)
/* 132 */         parserPool.returnParser(saxParser); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\EnvelopeFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
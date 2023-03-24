/*     */ package com.sun.xml.ws.streaming;
/*     */ 
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.util.FastInfosetUtil;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SourceReaderFactory
/*     */ {
/*     */   static Class fastInfosetSourceClass;
/*     */   static Method fastInfosetSource_getInputStream;
/*     */   
/*     */   static {
/*     */     try {
/*  78 */       fastInfosetSourceClass = Class.forName("org.jvnet.fastinfoset.FastInfosetSource");
/*     */       
/*  80 */       fastInfosetSource_getInputStream = fastInfosetSourceClass.getMethod("getInputStream", new Class[0]);
/*     */     
/*     */     }
/*  83 */     catch (Exception e) {
/*  84 */       fastInfosetSourceClass = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static XMLStreamReader createSourceReader(Source source, boolean rejectDTDs) {
/*  89 */     return createSourceReader(source, rejectDTDs, null);
/*     */   }
/*     */   
/*     */   public static XMLStreamReader createSourceReader(Source source, boolean rejectDTDs, String charsetName) {
/*     */     try {
/*  94 */       if (source instanceof StreamSource) {
/*  95 */         StreamSource streamSource = (StreamSource)source;
/*  96 */         InputStream is = streamSource.getInputStream();
/*     */         
/*  98 */         if (is != null) {
/*     */           
/* 100 */           if (charsetName != null) {
/* 101 */             return XMLStreamReaderFactory.create(source.getSystemId(), new InputStreamReader(is, charsetName), rejectDTDs);
/*     */           }
/*     */ 
/*     */           
/* 105 */           return XMLStreamReaderFactory.create(source.getSystemId(), is, rejectDTDs);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 110 */         Reader reader = streamSource.getReader();
/* 111 */         if (reader != null) {
/* 112 */           return XMLStreamReaderFactory.create(source.getSystemId(), reader, rejectDTDs);
/*     */         }
/*     */ 
/*     */         
/* 116 */         return XMLStreamReaderFactory.create(source.getSystemId(), (new URL(source.getSystemId())).openStream(), rejectDTDs);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (source.getClass() == fastInfosetSourceClass) {
/* 122 */         return FastInfosetUtil.createFIStreamReader((InputStream)fastInfosetSource_getInputStream.invoke(source, new Object[0]));
/*     */       }
/*     */       
/* 125 */       if (source instanceof DOMSource) {
/* 126 */         DOMStreamReader dsr = new DOMStreamReader();
/* 127 */         dsr.setCurrentNode(((DOMSource)source).getNode());
/* 128 */         return dsr;
/*     */       } 
/* 130 */       if (source instanceof javax.xml.transform.sax.SAXSource) {
/*     */         
/* 132 */         Transformer tx = XmlUtil.newTransformer();
/* 133 */         DOMResult domResult = new DOMResult();
/* 134 */         tx.transform(source, domResult);
/* 135 */         return createSourceReader(new DOMSource(domResult.getNode()), rejectDTDs);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 140 */       throw new XMLReaderException("sourceReader.invalidSource", new Object[] { source.getClass().getName() });
/*     */ 
/*     */     
/*     */     }
/* 144 */     catch (Exception e) {
/* 145 */       throw new XMLReaderException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\streaming\SourceReaderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
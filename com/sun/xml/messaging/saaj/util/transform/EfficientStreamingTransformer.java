/*     */ package com.sun.xml.messaging.saaj.util.transform;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.FastInfosetReflection;
/*     */ import com.sun.xml.messaging.saaj.util.XMLDeclarationParser;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PushbackReader;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Properties;
/*     */ import javax.xml.transform.ErrorListener;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.URIResolver;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EfficientStreamingTransformer
/*     */   extends Transformer
/*     */ {
/*  83 */   private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private Transformer m_realTransformer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private Object m_fiDOMDocumentParser = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private Object m_fiDOMDocumentSerializer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void materialize() throws TransformerException {
/* 120 */     if (this.m_realTransformer == null) {
/* 121 */       this.m_realTransformer = this.transformerFactory.newTransformer();
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearParameters() {
/* 126 */     if (this.m_realTransformer != null)
/* 127 */       this.m_realTransformer.clearParameters(); 
/*     */   }
/*     */   
/*     */   public ErrorListener getErrorListener() {
/*     */     try {
/* 132 */       materialize();
/* 133 */       return this.m_realTransformer.getErrorListener();
/* 134 */     } catch (TransformerException e) {
/*     */ 
/*     */       
/* 137 */       return null;
/*     */     } 
/*     */   }
/*     */   public Properties getOutputProperties() {
/*     */     try {
/* 142 */       materialize();
/* 143 */       return this.m_realTransformer.getOutputProperties();
/* 144 */     } catch (TransformerException e) {
/*     */ 
/*     */       
/* 147 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getOutputProperty(String str) throws IllegalArgumentException {
/*     */     try {
/* 153 */       materialize();
/* 154 */       return this.m_realTransformer.getOutputProperty(str);
/* 155 */     } catch (TransformerException e) {
/*     */ 
/*     */       
/* 158 */       return null;
/*     */     } 
/*     */   }
/*     */   public Object getParameter(String str) {
/*     */     try {
/* 163 */       materialize();
/* 164 */       return this.m_realTransformer.getParameter(str);
/* 165 */     } catch (TransformerException e) {
/*     */ 
/*     */       
/* 168 */       return null;
/*     */     } 
/*     */   }
/*     */   public URIResolver getURIResolver() {
/*     */     try {
/* 173 */       materialize();
/* 174 */       return this.m_realTransformer.getURIResolver();
/* 175 */     } catch (TransformerException e) {
/*     */ 
/*     */       
/* 178 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setErrorListener(ErrorListener errorListener) throws IllegalArgumentException {
/*     */     try {
/* 185 */       materialize();
/* 186 */       this.m_realTransformer.setErrorListener(errorListener);
/* 187 */     } catch (TransformerException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputProperties(Properties properties) throws IllegalArgumentException {
/*     */     try {
/* 195 */       materialize();
/* 196 */       this.m_realTransformer.setOutputProperties(properties);
/* 197 */     } catch (TransformerException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputProperty(String str, String str1) throws IllegalArgumentException {
/*     */     try {
/* 205 */       materialize();
/* 206 */       this.m_realTransformer.setOutputProperty(str, str1);
/* 207 */     } catch (TransformerException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(String str, Object obj) {
/*     */     try {
/* 214 */       materialize();
/* 215 */       this.m_realTransformer.setParameter(str, obj);
/* 216 */     } catch (TransformerException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURIResolver(URIResolver uRIResolver) {
/*     */     try {
/* 223 */       materialize();
/* 224 */       this.m_realTransformer.setURIResolver(uRIResolver);
/* 225 */     } catch (TransformerException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getInputStreamFromSource(StreamSource s) throws TransformerException {
/* 233 */     InputStream stream = s.getInputStream();
/* 234 */     if (stream != null) {
/* 235 */       return stream;
/*     */     }
/* 237 */     if (s.getReader() != null) {
/* 238 */       return null;
/*     */     }
/* 240 */     String systemId = s.getSystemId();
/* 241 */     if (systemId != null) {
/*     */       try {
/* 243 */         String fileURL = systemId;
/*     */         
/* 245 */         if (systemId.startsWith("file:///")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 254 */           String absolutePath = systemId.substring(7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 261 */           boolean hasDriveDesignator = (absolutePath.indexOf(":") > 0);
/* 262 */           if (hasDriveDesignator) {
/* 263 */             String driveDesignatedPath = absolutePath.substring(1);
/*     */ 
/*     */             
/* 266 */             fileURL = driveDesignatedPath;
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 271 */             fileURL = absolutePath;
/*     */           } 
/*     */         } 
/*     */         
/*     */         try {
/* 276 */           return new FileInputStream(new File(new URI(fileURL)));
/* 277 */         } catch (URISyntaxException ex) {
/* 278 */           throw new TransformerException(ex);
/*     */         } 
/* 280 */       } catch (IOException e) {
/* 281 */         throw new TransformerException(e.toString());
/*     */       } 
/*     */     }
/*     */     
/* 285 */     throw new TransformerException("Unexpected StreamSource object");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transform(Source source, Result result) throws TransformerException {
/* 296 */     if (source instanceof StreamSource && result instanceof StreamResult) {
/*     */       
/*     */       try {
/* 299 */         StreamSource streamSource = (StreamSource)source;
/* 300 */         InputStream is = getInputStreamFromSource(streamSource);
/*     */         
/* 302 */         OutputStream os = ((StreamResult)result).getOutputStream();
/* 303 */         if (os == null)
/*     */         {
/*     */           
/* 306 */           throw new TransformerException("Unexpected StreamResult object contains null OutputStream");
/*     */         }
/* 308 */         if (is != null) {
/* 309 */           if (is.markSupported()) {
/* 310 */             is.mark(2147483647);
/*     */           }
/* 312 */           byte[] b = new byte[8192]; int num;
/* 313 */           while ((num = is.read(b)) != -1) {
/* 314 */             os.write(b, 0, num);
/*     */           }
/* 316 */           if (is.markSupported()) {
/* 317 */             is.reset();
/*     */           }
/*     */           return;
/*     */         } 
/* 321 */         Reader reader = streamSource.getReader();
/* 322 */         if (reader != null) {
/*     */           
/* 324 */           if (reader.markSupported()) {
/* 325 */             reader.mark(2147483647);
/*     */           }
/* 327 */           PushbackReader pushbackReader = new PushbackReader(reader, 4096);
/*     */           
/* 329 */           XMLDeclarationParser ev = new XMLDeclarationParser(pushbackReader);
/*     */           
/*     */           try {
/* 332 */             ev.parse();
/* 333 */           } catch (Exception ex) {
/* 334 */             throw new TransformerException("Unable to run the JAXP transformer on a stream " + ex.getMessage());
/*     */           } 
/*     */ 
/*     */           
/* 338 */           Writer writer = new OutputStreamWriter(os);
/*     */           
/* 340 */           ev.writeTo(writer);
/*     */ 
/*     */           
/* 343 */           char[] ac = new char[8192]; int num;
/* 344 */           while ((num = pushbackReader.read(ac)) != -1) {
/* 345 */             writer.write(ac, 0, num);
/*     */           }
/* 347 */           writer.flush();
/*     */           
/* 349 */           if (reader.markSupported())
/* 350 */             reader.reset(); 
/*     */           return;
/*     */         } 
/* 353 */       } catch (IOException e) {
/* 354 */         e.printStackTrace();
/* 355 */         throw new TransformerException(e.toString());
/*     */       } 
/*     */       
/* 358 */       throw new TransformerException("Unexpected StreamSource object");
/*     */     } 
/*     */     
/* 361 */     if (FastInfosetReflection.isFastInfosetSource(source) && result instanceof DOMResult) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 366 */         if (this.m_fiDOMDocumentParser == null) {
/* 367 */           this.m_fiDOMDocumentParser = FastInfosetReflection.DOMDocumentParser_new();
/*     */         }
/*     */ 
/*     */         
/* 371 */         FastInfosetReflection.DOMDocumentParser_parse(this.m_fiDOMDocumentParser, (Document)((DOMResult)result).getNode(), FastInfosetReflection.FastInfosetSource_getInputStream(source));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/* 379 */       } catch (Exception e) {
/* 380 */         throw new TransformerException(e);
/*     */       } 
/*     */     }
/*     */     
/* 384 */     if (source instanceof DOMSource && FastInfosetReflection.isFastInfosetResult(result)) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 389 */         if (this.m_fiDOMDocumentSerializer == null) {
/* 390 */           this.m_fiDOMDocumentSerializer = FastInfosetReflection.DOMDocumentSerializer_new();
/*     */         }
/*     */ 
/*     */         
/* 394 */         FastInfosetReflection.DOMDocumentSerializer_setOutputStream(this.m_fiDOMDocumentSerializer, FastInfosetReflection.FastInfosetResult_getOutputStream(result));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 399 */         FastInfosetReflection.DOMDocumentSerializer_serialize(this.m_fiDOMDocumentSerializer, ((DOMSource)source).getNode());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/* 406 */       } catch (Exception e) {
/* 407 */         throw new TransformerException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 413 */     materialize();
/* 414 */     this.m_realTransformer.transform(source, result);
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
/*     */ 
/*     */   
/*     */   public static Transformer newTransformer() {
/* 435 */     return new EfficientStreamingTransformer();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\transform\EfficientStreamingTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
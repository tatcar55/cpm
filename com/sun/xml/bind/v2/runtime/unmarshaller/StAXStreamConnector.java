/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StAXStreamConnector
/*     */   extends StAXConnector
/*     */ {
/*     */   private final XMLStreamReader staxStreamReader;
/*     */   
/*     */   public static StAXConnector create(XMLStreamReader reader, XmlVisitor visitor) {
/*  77 */     Class<?> readerClass = reader.getClass();
/*  78 */     if (FI_STAX_READER_CLASS != null && FI_STAX_READER_CLASS.isAssignableFrom(readerClass) && FI_CONNECTOR_CTOR != null) {
/*     */       try {
/*  80 */         return FI_CONNECTOR_CTOR.newInstance(new Object[] { reader, visitor });
/*  81 */       } catch (Exception t) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  86 */     boolean isZephyr = readerClass.getName().equals("com.sun.xml.stream.XMLReaderImpl");
/*  87 */     if (!getBoolProp(reader, "org.codehaus.stax2.internNames") || !getBoolProp(reader, "org.codehaus.stax2.internNsUris"))
/*     */     {
/*     */ 
/*     */       
/*  91 */       if (!isZephyr)
/*     */       {
/*     */         
/*  94 */         if (!checkImplementaionNameOfSjsxp(reader))
/*     */         {
/*     */           
/*  97 */           visitor = new InterningXmlVisitor(visitor); }  } 
/*     */     }
/*  99 */     if (STAX_EX_READER_CLASS != null && STAX_EX_READER_CLASS.isAssignableFrom(readerClass)) {
/*     */       try {
/* 101 */         return STAX_EX_CONNECTOR_CTOR.newInstance(new Object[] { reader, visitor });
/* 102 */       } catch (Exception t) {}
/*     */     }
/*     */ 
/*     */     
/* 106 */     return new StAXStreamConnector(reader, visitor);
/*     */   }
/*     */   
/*     */   private static boolean checkImplementaionNameOfSjsxp(XMLStreamReader reader) {
/*     */     try {
/* 111 */       Object name = reader.getProperty("http://java.sun.com/xml/stream/properties/implementation-name");
/* 112 */       return (name != null && name.equals("sjsxp"));
/* 113 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 116 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean getBoolProp(XMLStreamReader r, String n) {
/*     */     try {
/* 122 */       Object o = r.getProperty(n);
/* 123 */       if (o instanceof Boolean) return ((Boolean)o).booleanValue(); 
/* 124 */       return false;
/* 125 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 128 */       return false;
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
/* 140 */   protected final StringBuilder buffer = new StringBuilder();
/*     */ 
/*     */   
/*     */   protected boolean textReported = false;
/*     */   
/*     */   private final Attributes attributes;
/*     */ 
/*     */   
/*     */   protected StAXStreamConnector(XMLStreamReader staxStreamReader, XmlVisitor visitor) {
/* 149 */     super(visitor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     this.attributes = new Attributes() {
/*     */         public int getLength() {
/* 254 */           return StAXStreamConnector.this.staxStreamReader.getAttributeCount();
/*     */         }
/*     */         
/*     */         public String getURI(int index) {
/* 258 */           String uri = StAXStreamConnector.this.staxStreamReader.getAttributeNamespace(index);
/* 259 */           if (uri == null) return ""; 
/* 260 */           return uri;
/*     */         }
/*     */         
/*     */         public String getLocalName(int index) {
/* 264 */           return StAXStreamConnector.this.staxStreamReader.getAttributeLocalName(index);
/*     */         }
/*     */         
/*     */         public String getQName(int index) {
/* 268 */           String prefix = StAXStreamConnector.this.staxStreamReader.getAttributePrefix(index);
/* 269 */           if (prefix == null || prefix.length() == 0) {
/* 270 */             return getLocalName(index);
/*     */           }
/* 272 */           return prefix + ':' + getLocalName(index);
/*     */         }
/*     */         
/*     */         public String getType(int index) {
/* 276 */           return StAXStreamConnector.this.staxStreamReader.getAttributeType(index);
/*     */         }
/*     */         
/*     */         public String getValue(int index) {
/* 280 */           return StAXStreamConnector.this.staxStreamReader.getAttributeValue(index);
/*     */         }
/*     */         
/*     */         public int getIndex(String uri, String localName) {
/* 284 */           for (int i = getLength() - 1; i >= 0; i--) {
/* 285 */             if (localName.equals(getLocalName(i)) && uri.equals(getURI(i)))
/* 286 */               return i; 
/* 287 */           }  return -1;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int getIndex(String qName) {
/* 293 */           for (int i = getLength() - 1; i >= 0; i--) {
/* 294 */             if (qName.equals(getQName(i)))
/* 295 */               return i; 
/*     */           } 
/* 297 */           return -1;
/*     */         }
/*     */         
/*     */         public String getType(String uri, String localName) {
/* 301 */           int index = getIndex(uri, localName);
/* 302 */           if (index < 0) return null; 
/* 303 */           return getType(index);
/*     */         }
/*     */         
/*     */         public String getType(String qName) {
/* 307 */           int index = getIndex(qName);
/* 308 */           if (index < 0) return null; 
/* 309 */           return getType(index);
/*     */         }
/*     */         
/*     */         public String getValue(String uri, String localName) {
/* 313 */           int index = getIndex(uri, localName);
/* 314 */           if (index < 0) return null; 
/* 315 */           return getValue(index);
/*     */         }
/*     */         
/*     */         public String getValue(String qName) {
/* 319 */           int index = getIndex(qName);
/* 320 */           if (index < 0) return null; 
/* 321 */           return getValue(index); }
/*     */       }; this.staxStreamReader = staxStreamReader; } public void bridge() throws XMLStreamException { try { int depth = 0; int event = this.staxStreamReader.getEventType(); if (event == 7)
/*     */         while (!this.staxStreamReader.isStartElement())
/*     */           event = this.staxStreamReader.next();   if (event != 1)
/*     */         throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);  handleStartDocument(this.staxStreamReader.getNamespaceContext()); while (true) { switch (event) { case 1: handleStartElement(); depth++; break;case 2: depth--; handleEndElement(); if (depth == 0)
/* 326 */               break;  break;case 4: case 6: case 12: handleCharacters(); break; }  event = this.staxStreamReader.next(); }  this.staxStreamReader.next(); handleEndDocument(); } catch (SAXException e) { throw new XMLStreamException(e); }  } protected void handleCharacters() throws XMLStreamException, SAXException { if (this.predictor.expectText())
/* 327 */       this.buffer.append(this.staxStreamReader.getTextCharacters(), this.staxStreamReader.getTextStart(), this.staxStreamReader.getTextLength());  }
/*     */   protected Location getCurrentLocation() { return this.staxStreamReader.getLocation(); }
/*     */   protected String getCurrentQName() { return getQName(this.staxStreamReader.getPrefix(), this.staxStreamReader.getLocalName()); }
/*     */   private void handleEndElement() throws SAXException { processText(false); this.tagName.uri = fixNull(this.staxStreamReader.getNamespaceURI()); this.tagName.local = this.staxStreamReader.getLocalName(); this.visitor.endElement(this.tagName); int nsCount = this.staxStreamReader.getNamespaceCount(); for (int i = nsCount - 1; i >= 0; i--)
/*     */       this.visitor.endPrefixMapping(fixNull(this.staxStreamReader.getNamespacePrefix(i)));  }
/*     */   private void handleStartElement() throws SAXException { processText(true); int nsCount = this.staxStreamReader.getNamespaceCount(); for (int i = 0; i < nsCount; i++)
/*     */       this.visitor.startPrefixMapping(fixNull(this.staxStreamReader.getNamespacePrefix(i)), fixNull(this.staxStreamReader.getNamespaceURI(i)));  this.tagName.uri = fixNull(this.staxStreamReader.getNamespaceURI()); this.tagName.local = this.staxStreamReader.getLocalName(); this.tagName.atts = this.attributes;
/* 334 */     this.visitor.startElement(this.tagName); } private void processText(boolean ignorable) throws SAXException { if (this.predictor.expectText() && (!ignorable || !WhiteSpaceProcessor.isWhiteSpace(this.buffer))) {
/* 335 */       if (this.textReported) {
/* 336 */         this.textReported = false;
/*     */       } else {
/* 338 */         this.visitor.text(this.buffer);
/*     */       } 
/*     */     }
/* 341 */     this.buffer.setLength(0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 349 */   private static final Class FI_STAX_READER_CLASS = initFIStAXReaderClass();
/* 350 */   private static final Constructor<? extends StAXConnector> FI_CONNECTOR_CTOR = initFastInfosetConnectorClass();
/*     */   
/*     */   private static Class initFIStAXReaderClass() {
/*     */     try {
/* 354 */       ClassLoader cl = getClassLoader();
/* 355 */       Class<?> fisr = cl.loadClass("org.jvnet.fastinfoset.stax.FastInfosetStreamReader");
/* 356 */       Class<?> sdp = cl.loadClass("com.sun.xml.fastinfoset.stax.StAXDocumentParser");
/*     */       
/* 358 */       if (fisr.isAssignableFrom(sdp)) {
/* 359 */         return sdp;
/*     */       }
/* 361 */       return null;
/* 362 */     } catch (Throwable e) {
/* 363 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends StAXConnector> initFastInfosetConnectorClass() {
/*     */     try {
/* 369 */       if (FI_STAX_READER_CLASS == null) {
/* 370 */         return null;
/*     */       }
/* 372 */       Class<?> c = getClassLoader().loadClass("com.sun.xml.bind.v2.runtime.unmarshaller.FastInfosetConnector");
/*     */       
/* 374 */       return (Constructor)c.getConstructor(new Class[] { FI_STAX_READER_CLASS, XmlVisitor.class });
/* 375 */     } catch (Throwable e) {
/* 376 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 383 */   private static final Class STAX_EX_READER_CLASS = initStAXExReader();
/* 384 */   private static final Constructor<? extends StAXConnector> STAX_EX_CONNECTOR_CTOR = initStAXExConnector();
/*     */   
/*     */   private static Class initStAXExReader() {
/*     */     try {
/* 388 */       return getClassLoader().loadClass("org.jvnet.staxex.XMLStreamReaderEx");
/* 389 */     } catch (Throwable e) {
/* 390 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends StAXConnector> initStAXExConnector() {
/*     */     try {
/* 396 */       Class<?> c = getClassLoader().loadClass("com.sun.xml.bind.v2.runtime.unmarshaller.StAXExConnector");
/* 397 */       return (Constructor)c.getConstructor(new Class[] { STAX_EX_READER_CLASS, XmlVisitor.class });
/* 398 */     } catch (Throwable e) {
/* 399 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ClassLoader getClassLoader() {
/* 404 */     ClassLoader cl = SecureLoader.getClassClassLoader(UnmarshallerImpl.class);
/* 405 */     if (cl == null) {
/* 406 */       cl = SecureLoader.getContextClassLoader();
/*     */     }
/* 408 */     return cl;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\StAXStreamConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
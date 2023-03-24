/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.IDResolver;
/*     */ import com.sun.xml.bind.api.ClassResolver;
/*     */ import com.sun.xml.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.bind.unmarshaller.InfosetScanner;
/*     */ import com.sun.xml.bind.unmarshaller.Messages;
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.runtime.AssociationMap;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.util.XmlFactory;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.UnmarshalException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.UnmarshallerHandler;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnmarshallerImpl
/*     */   extends AbstractUnmarshallerImpl
/*     */   implements ValidationEventHandler, Closeable
/*     */ {
/*     */   protected final JAXBContextImpl context;
/*     */   private Schema schema;
/*     */   public final UnmarshallingContext coordinator;
/*     */   private Unmarshaller.Listener externalListener;
/*     */   private AttachmentUnmarshaller attachmentUnmarshaller;
/* 120 */   private IDResolver idResolver = new DefaultIDResolver();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLReader reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmarshallerHandler getUnmarshallerHandler() {
/* 134 */     return getUnmarshallerHandler(true, null);
/*     */   }
/*     */   public UnmarshallerImpl(JAXBContextImpl context, AssociationMap assoc) {
/* 137 */     this.reader = null;
/*     */     this.context = context;
/*     */     this.coordinator = new UnmarshallingContext(this, assoc);
/*     */     try {
/*     */       setEventHandler(this);
/*     */     } catch (JAXBException e) {
/*     */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLReader getXMLReader() throws JAXBException {
/* 152 */     if (this.reader == null) {
/*     */       try {
/* 154 */         SAXParserFactory parserFactory = XmlFactory.createParserFactory(this.context.disableSecurityProcessing);
/*     */ 
/*     */ 
/*     */         
/* 158 */         parserFactory.setValidating(false);
/* 159 */         this.reader = parserFactory.newSAXParser().getXMLReader();
/* 160 */       } catch (ParserConfigurationException e) {
/* 161 */         throw new JAXBException(e);
/* 162 */       } catch (SAXException e) {
/* 163 */         throw new JAXBException(e);
/*     */       } 
/*     */     }
/* 166 */     return this.reader;
/*     */   }
/*     */   
/*     */   private SAXConnector getUnmarshallerHandler(boolean intern, JaxBeanInfo expectedType) {
/* 170 */     XmlVisitor h = createUnmarshallerHandler((InfosetScanner)null, false, expectedType);
/* 171 */     if (intern) {
/* 172 */       h = new InterningXmlVisitor(h);
/*     */     }
/* 174 */     return new SAXConnector(h, null);
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
/*     */   public final XmlVisitor createUnmarshallerHandler(InfosetScanner scanner, boolean inplace, JaxBeanInfo expectedType) {
/* 192 */     this.coordinator.reset(scanner, inplace, expectedType, this.idResolver);
/* 193 */     XmlVisitor unmarshaller = this.coordinator;
/*     */ 
/*     */     
/* 196 */     if (this.schema != null) {
/* 197 */       unmarshaller = new ValidatingUnmarshaller(this.schema, unmarshaller);
/*     */     }
/*     */     
/* 200 */     if (this.attachmentUnmarshaller != null && this.attachmentUnmarshaller.isXOPPackage()) {
/* 201 */       unmarshaller = new MTOMDecorator(this, unmarshaller, this.attachmentUnmarshaller);
/*     */     }
/*     */     
/* 204 */     return unmarshaller;
/*     */   }
/*     */   
/* 207 */   private static final DefaultHandler dummyHandler = new DefaultHandler();
/*     */   public static final String FACTORY = "com.sun.xml.bind.ObjectFactory";
/*     */   
/*     */   public static boolean needsInterning(XMLReader reader) {
/*     */     try {
/* 212 */       reader.setFeature("http://xml.org/sax/features/string-interning", true);
/* 213 */     } catch (SAXException e) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 218 */       if (reader.getFeature("http://xml.org/sax/features/string-interning")) {
/* 219 */         return false;
/*     */       }
/* 221 */     } catch (SAXException e) {}
/*     */ 
/*     */ 
/*     */     
/* 225 */     return true;
/*     */   }
/*     */   
/*     */   protected Object unmarshal(XMLReader reader, InputSource source) throws JAXBException {
/* 229 */     return unmarshal0(reader, source, (JaxBeanInfo)null);
/*     */   }
/*     */   
/*     */   protected <T> JAXBElement<T> unmarshal(XMLReader reader, InputSource source, Class<T> expectedType) throws JAXBException {
/* 233 */     if (expectedType == null) {
/* 234 */       throw new IllegalArgumentException();
/*     */     }
/* 236 */     return (JAXBElement<T>)unmarshal0(reader, source, getBeanInfo(expectedType));
/*     */   }
/*     */ 
/*     */   
/*     */   private Object unmarshal0(XMLReader reader, InputSource source, JaxBeanInfo expectedType) throws JAXBException {
/* 241 */     SAXConnector connector = getUnmarshallerHandler(needsInterning(reader), expectedType);
/*     */     
/* 243 */     reader.setContentHandler(connector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     reader.setErrorHandler(this.coordinator);
/*     */     
/*     */     try {
/* 258 */       reader.parse(source);
/* 259 */     } catch (IOException e) {
/* 260 */       this.coordinator.clearStates();
/* 261 */       throw new UnmarshalException(e);
/* 262 */     } catch (SAXException e) {
/* 263 */       this.coordinator.clearStates();
/* 264 */       throw createUnmarshalException(e);
/*     */     } 
/*     */     
/* 267 */     Object result = connector.getResult();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     reader.setContentHandler(dummyHandler);
/* 273 */     reader.setErrorHandler(dummyHandler);
/*     */     
/* 275 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(Source source, Class<T> expectedType) throws JAXBException {
/* 280 */     if (source instanceof SAXSource) {
/* 281 */       SAXSource ss = (SAXSource)source;
/*     */       
/* 283 */       XMLReader locReader = ss.getXMLReader();
/* 284 */       if (locReader == null) {
/* 285 */         locReader = getXMLReader();
/*     */       }
/*     */       
/* 288 */       return unmarshal(locReader, ss.getInputSource(), expectedType);
/*     */     } 
/* 290 */     if (source instanceof StreamSource) {
/* 291 */       return unmarshal(getXMLReader(), streamSourceToInputSource((StreamSource)source), expectedType);
/*     */     }
/* 293 */     if (source instanceof DOMSource) {
/* 294 */       return unmarshal(((DOMSource)source).getNode(), expectedType);
/*     */     }
/*     */ 
/*     */     
/* 298 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public Object unmarshal0(Source source, JaxBeanInfo expectedType) throws JAXBException {
/* 302 */     if (source instanceof SAXSource) {
/* 303 */       SAXSource ss = (SAXSource)source;
/*     */       
/* 305 */       XMLReader locReader = ss.getXMLReader();
/* 306 */       if (locReader == null) {
/* 307 */         locReader = getXMLReader();
/*     */       }
/*     */       
/* 310 */       return unmarshal0(locReader, ss.getInputSource(), expectedType);
/*     */     } 
/* 312 */     if (source instanceof StreamSource) {
/* 313 */       return unmarshal0(getXMLReader(), streamSourceToInputSource((StreamSource)source), expectedType);
/*     */     }
/* 315 */     if (source instanceof DOMSource) {
/* 316 */       return unmarshal0(((DOMSource)source).getNode(), expectedType);
/*     */     }
/*     */ 
/*     */     
/* 320 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ValidationEventHandler getEventHandler() {
/*     */     try {
/* 327 */       return super.getEventHandler();
/* 328 */     } catch (JAXBException e) {
/*     */       
/* 330 */       throw new AssertionError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasEventHandler() {
/* 340 */     return (getEventHandler() != this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(Node node, Class<T> expectedType) throws JAXBException {
/* 345 */     if (expectedType == null) {
/* 346 */       throw new IllegalArgumentException();
/*     */     }
/* 348 */     return (JAXBElement<T>)unmarshal0(node, getBeanInfo(expectedType));
/*     */   }
/*     */   
/*     */   public final Object unmarshal(Node node) throws JAXBException {
/* 352 */     return unmarshal0(node, (JaxBeanInfo)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final Object unmarshal(SAXSource source) throws JAXBException {
/* 358 */     return unmarshal(source);
/*     */   }
/*     */   
/*     */   public final Object unmarshal0(Node node, JaxBeanInfo expectedType) throws JAXBException {
/*     */     try {
/* 363 */       DOMScanner scanner = new DOMScanner();
/*     */       
/* 365 */       InterningXmlVisitor handler = new InterningXmlVisitor(createUnmarshallerHandler((InfosetScanner)null, false, expectedType));
/* 366 */       scanner.setContentHandler(new SAXConnector(handler, (LocatorEx)scanner));
/*     */       
/* 368 */       if (node.getNodeType() == 1) {
/* 369 */         scanner.scan((Element)node);
/* 370 */       } else if (node.getNodeType() == 9) {
/* 371 */         scanner.scan((Document)node);
/*     */       } else {
/* 373 */         throw new IllegalArgumentException("Unexpected node type: " + node);
/*     */       } 
/*     */       
/* 376 */       Object retVal = handler.getContext().getResult();
/* 377 */       handler.getContext().clearResult();
/* 378 */       return retVal;
/* 379 */     } catch (SAXException e) {
/* 380 */       throw createUnmarshalException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object unmarshal(XMLStreamReader reader) throws JAXBException {
/* 386 */     return unmarshal0(reader, (JaxBeanInfo)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(XMLStreamReader reader, Class<T> expectedType) throws JAXBException {
/* 391 */     if (expectedType == null) {
/* 392 */       throw new IllegalArgumentException();
/*     */     }
/* 394 */     return (JAXBElement<T>)unmarshal0(reader, getBeanInfo(expectedType));
/*     */   }
/*     */   
/*     */   public Object unmarshal0(XMLStreamReader reader, JaxBeanInfo expectedType) throws JAXBException {
/* 398 */     if (reader == null) {
/* 399 */       throw new IllegalArgumentException(Messages.format("Unmarshaller.NullReader"));
/*     */     }
/*     */ 
/*     */     
/* 403 */     int eventType = reader.getEventType();
/* 404 */     if (eventType != 1 && eventType != 7)
/*     */     {
/*     */       
/* 407 */       throw new IllegalStateException(Messages.format("Unmarshaller.IllegalReaderState", Integer.valueOf(eventType)));
/*     */     }
/*     */ 
/*     */     
/* 411 */     XmlVisitor h = createUnmarshallerHandler((InfosetScanner)null, false, expectedType);
/* 412 */     StAXConnector connector = StAXStreamConnector.create(reader, h);
/*     */     
/*     */     try {
/* 415 */       connector.bridge();
/* 416 */     } catch (XMLStreamException e) {
/* 417 */       throw handleStreamException(e);
/*     */     } 
/*     */     
/* 420 */     Object retVal = h.getContext().getResult();
/* 421 */     h.getContext().clearResult();
/* 422 */     return retVal;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(XMLEventReader reader, Class<T> expectedType) throws JAXBException {
/* 427 */     if (expectedType == null) {
/* 428 */       throw new IllegalArgumentException();
/*     */     }
/* 430 */     return (JAXBElement<T>)unmarshal0(reader, getBeanInfo(expectedType));
/*     */   }
/*     */ 
/*     */   
/*     */   public Object unmarshal(XMLEventReader reader) throws JAXBException {
/* 435 */     return unmarshal0(reader, (JaxBeanInfo)null);
/*     */   }
/*     */   
/*     */   private Object unmarshal0(XMLEventReader reader, JaxBeanInfo expectedType) throws JAXBException {
/* 439 */     if (reader == null) {
/* 440 */       throw new IllegalArgumentException(Messages.format("Unmarshaller.NullReader"));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 445 */       XMLEvent event = reader.peek();
/*     */       
/* 447 */       if (!event.isStartElement() && !event.isStartDocument())
/*     */       {
/* 449 */         throw new IllegalStateException(Messages.format("Unmarshaller.IllegalReaderState", Integer.valueOf(event.getEventType())));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 455 */       boolean isZephyr = reader.getClass().getName().equals("com.sun.xml.stream.XMLReaderImpl");
/* 456 */       XmlVisitor h = createUnmarshallerHandler((InfosetScanner)null, false, expectedType);
/* 457 */       if (!isZephyr) {
/* 458 */         h = new InterningXmlVisitor(h);
/*     */       }
/* 460 */       (new StAXEventConnector(reader, h)).bridge();
/* 461 */       return h.getContext().getResult();
/* 462 */     } catch (XMLStreamException e) {
/* 463 */       throw handleStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object unmarshal0(InputStream input, JaxBeanInfo expectedType) throws JAXBException {
/* 468 */     return unmarshal0(getXMLReader(), new InputSource(input), expectedType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JAXBException handleStreamException(XMLStreamException e) {
/* 478 */     Throwable ne = e.getNestedException();
/* 479 */     if (ne instanceof JAXBException) {
/* 480 */       return (JAXBException)ne;
/*     */     }
/* 482 */     if (ne instanceof SAXException) {
/* 483 */       return new UnmarshalException(ne);
/*     */     }
/* 485 */     return new UnmarshalException(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 490 */     if (name.equals(IDResolver.class.getName())) {
/* 491 */       return this.idResolver;
/*     */     }
/* 493 */     return super.getProperty(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 498 */     if (name.equals("com.sun.xml.bind.ObjectFactory")) {
/* 499 */       this.coordinator.setFactories(value);
/*     */       return;
/*     */     } 
/* 502 */     if (name.equals(IDResolver.class.getName())) {
/* 503 */       this.idResolver = (IDResolver)value;
/*     */       return;
/*     */     } 
/* 506 */     if (name.equals(ClassResolver.class.getName())) {
/* 507 */       this.coordinator.classResolver = (ClassResolver)value;
/*     */       return;
/*     */     } 
/* 510 */     if (name.equals(ClassLoader.class.getName())) {
/* 511 */       this.coordinator.classLoader = (ClassLoader)value;
/*     */       return;
/*     */     } 
/* 514 */     super.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchema(Schema schema) {
/* 521 */     this.schema = schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema getSchema() {
/* 526 */     return this.schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentUnmarshaller getAttachmentUnmarshaller() {
/* 531 */     return this.attachmentUnmarshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttachmentUnmarshaller(AttachmentUnmarshaller au) {
/* 536 */     this.attachmentUnmarshaller = au;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidating() {
/* 544 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidating(boolean validating) {
/* 552 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
/* 557 */     if (type == null) {
/* 558 */       throw new IllegalArgumentException();
/*     */     }
/* 560 */     this.coordinator.putAdapter(type, (XmlAdapter)adapter);
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends XmlAdapter> A getAdapter(Class<A> type) {
/* 565 */     if (type == null) {
/* 566 */       throw new IllegalArgumentException();
/*     */     }
/* 568 */     if (this.coordinator.containsAdapter(type)) {
/* 569 */       return (A)this.coordinator.getAdapter(type);
/*     */     }
/* 571 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmarshalException createUnmarshalException(SAXException e) {
/* 578 */     return super.createUnmarshalException(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleEvent(ValidationEvent event) {
/* 586 */     return (event.getSeverity() != 2);
/*     */   }
/*     */   
/*     */   private static InputSource streamSourceToInputSource(StreamSource ss) {
/* 590 */     InputSource is = new InputSource();
/* 591 */     is.setSystemId(ss.getSystemId());
/* 592 */     is.setByteStream(ss.getInputStream());
/* 593 */     is.setCharacterStream(ss.getReader());
/*     */     
/* 595 */     return is;
/*     */   }
/*     */   
/*     */   public <T> JaxBeanInfo<T> getBeanInfo(Class<T> clazz) throws JAXBException {
/* 599 */     return this.context.getBeanInfo(clazz, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Unmarshaller.Listener getListener() {
/* 604 */     return this.externalListener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(Unmarshaller.Listener listener) {
/* 609 */     this.externalListener = listener;
/*     */   }
/*     */   
/*     */   public UnmarshallingContext getContext() {
/* 613 */     return this.coordinator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 620 */       ClassFactory.cleanCache();
/*     */     } finally {
/* 622 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 631 */     ClassFactory.cleanCache();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\UnmarshallerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
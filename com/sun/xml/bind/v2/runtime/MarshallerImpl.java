/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.DataWriter;
/*     */ import com.sun.xml.bind.marshaller.DumbEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.MinimumEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.bind.marshaller.NioEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.SAX2DOMEx;
/*     */ import com.sun.xml.bind.marshaller.XMLWriter;
/*     */ import com.sun.xml.bind.v2.runtime.output.C14nXmlOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.Encoded;
/*     */ import com.sun.xml.bind.v2.runtime.output.ForkXmlOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.IndentingUTF8XmlOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.NamespaceContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.output.SAXOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.UTF8XmlOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.XMLEventWriterOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.XMLStreamWriterOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.XmlOutput;
/*     */ import com.sun.xml.bind.v2.util.FatalAdapter;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.Closeable;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.MarshalException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.helpers.AbstractMarshallerImpl;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.ValidatorHandler;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MarshallerImpl
/*     */   extends AbstractMarshallerImpl
/*     */   implements ValidationEventHandler
/*     */ {
/* 114 */   private String indent = "    ";
/*     */ 
/*     */   
/* 117 */   private NamespacePrefixMapper prefixMapper = null;
/*     */ 
/*     */   
/* 120 */   private CharacterEscapeHandler escapeHandler = null;
/*     */ 
/*     */   
/* 123 */   private String header = null;
/*     */ 
/*     */ 
/*     */   
/*     */   final JAXBContextImpl context;
/*     */ 
/*     */   
/*     */   protected final XMLSerializer serializer;
/*     */ 
/*     */   
/*     */   private Schema schema;
/*     */ 
/*     */   
/* 136 */   private Marshaller.Listener externalListener = null;
/*     */   
/*     */   private boolean c14nSupport;
/*     */   private Flushable toBeFlushed;
/*     */   private Closeable toBeClosed;
/*     */   protected static final String INDENT_STRING = "com.sun.xml.bind.indentString";
/*     */   protected static final String PREFIX_MAPPER = "com.sun.xml.bind.namespacePrefixMapper";
/*     */   protected static final String ENCODING_HANDLER = "com.sun.xml.bind.characterEscapeHandler";
/*     */   protected static final String ENCODING_HANDLER2 = "com.sun.xml.bind.marshaller.CharacterEscapeHandler";
/*     */   protected static final String XMLDECLARATION = "com.sun.xml.bind.xmlDeclaration";
/*     */   protected static final String XML_HEADERS = "com.sun.xml.bind.xmlHeaders";
/*     */   protected static final String C14N = "com.sun.xml.bind.c14n";
/*     */   protected static final String OBJECT_IDENTITY_CYCLE_DETECTION = "com.sun.xml.bind.objectIdentitityCycleDetection";
/*     */   
/*     */   public MarshallerImpl(JAXBContextImpl c, AssociationMap assoc) {
/* 151 */     this.context = c;
/* 152 */     this.serializer = new XMLSerializer(this);
/* 153 */     this.c14nSupport = this.context.c14nSupport;
/*     */     
/*     */     try {
/* 156 */       setEventHandler(this);
/* 157 */     } catch (JAXBException e) {
/* 158 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public JAXBContextImpl getContext() {
/* 163 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, OutputStream out, NamespaceContext inscopeNamespace) throws JAXBException {
/* 173 */     write(obj, createWriter(out), new StAXPostInitAction(inscopeNamespace, this.serializer));
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, XMLStreamWriter writer) throws JAXBException {
/* 178 */     write(obj, XMLStreamWriterOutput.create(writer, this.context), new StAXPostInitAction(writer, this.serializer));
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, XMLEventWriter writer) throws JAXBException {
/* 183 */     write(obj, (XmlOutput)new XMLEventWriterOutput(writer), new StAXPostInitAction(writer, this.serializer));
/*     */   }
/*     */   
/*     */   public void marshal(Object obj, XmlOutput output) throws JAXBException {
/* 187 */     write(obj, output, (Runnable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final XmlOutput createXmlOutput(Result result) throws JAXBException {
/* 194 */     if (result instanceof SAXResult) {
/* 195 */       return (XmlOutput)new SAXOutput(((SAXResult)result).getHandler());
/*     */     }
/* 197 */     if (result instanceof DOMResult) {
/* 198 */       Node node = ((DOMResult)result).getNode();
/*     */       
/* 200 */       if (node == null) {
/* 201 */         Document doc = JAXBContextImpl.createDom((getContext()).disableSecurityProcessing);
/* 202 */         ((DOMResult)result).setNode(doc);
/* 203 */         return (XmlOutput)new SAXOutput((ContentHandler)new SAX2DOMEx(doc));
/*     */       } 
/* 205 */       return (XmlOutput)new SAXOutput((ContentHandler)new SAX2DOMEx(node));
/*     */     } 
/*     */     
/* 208 */     if (result instanceof StreamResult) {
/* 209 */       StreamResult sr = (StreamResult)result;
/*     */       
/* 211 */       if (sr.getWriter() != null)
/* 212 */         return createWriter(sr.getWriter()); 
/* 213 */       if (sr.getOutputStream() != null)
/* 214 */         return createWriter(sr.getOutputStream()); 
/* 215 */       if (sr.getSystemId() != null) {
/* 216 */         String fileURL = sr.getSystemId();
/*     */         
/*     */         try {
/* 219 */           fileURL = (new URI(fileURL)).getPath();
/* 220 */         } catch (URISyntaxException use) {}
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 225 */           FileOutputStream fos = new FileOutputStream(fileURL);
/* 226 */           assert this.toBeClosed == null;
/* 227 */           this.toBeClosed = fos;
/* 228 */           return createWriter(fos);
/* 229 */         } catch (IOException e) {
/* 230 */           throw new MarshalException(e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 236 */     throw new MarshalException(Messages.UNSUPPORTED_RESULT.format(new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Runnable createPostInitAction(Result result) {
/* 243 */     if (result instanceof DOMResult) {
/* 244 */       Node node = ((DOMResult)result).getNode();
/* 245 */       return new DomPostInitAction(node, this.serializer);
/*     */     } 
/* 247 */     return null;
/*     */   }
/*     */   
/*     */   public void marshal(Object target, Result result) throws JAXBException {
/* 251 */     write(target, createXmlOutput(result), createPostInitAction(result));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final <T> void write(Name rootTagName, JaxBeanInfo<T> bi, T obj, XmlOutput out, Runnable postInitAction) throws JAXBException {
/*     */     try {
/*     */       try {
/* 261 */         prewrite(out, true, postInitAction);
/* 262 */         this.serializer.startElement(rootTagName, (Object)null);
/* 263 */         if (bi.jaxbType == Void.class || bi.jaxbType == void.class) {
/*     */           
/* 265 */           this.serializer.endNamespaceDecls((Object)null);
/* 266 */           this.serializer.endAttributes();
/*     */         }
/* 268 */         else if (obj == null) {
/* 269 */           this.serializer.writeXsiNilTrue();
/*     */         } else {
/* 271 */           this.serializer.childAsXsiType(obj, "root", bi, false);
/*     */         } 
/* 273 */         this.serializer.endElement();
/* 274 */         postwrite();
/* 275 */       } catch (SAXException e) {
/* 276 */         throw new MarshalException(e);
/* 277 */       } catch (IOException e) {
/* 278 */         throw new MarshalException(e);
/* 279 */       } catch (XMLStreamException e) {
/* 280 */         throw new MarshalException(e);
/*     */       } finally {
/* 282 */         this.serializer.close();
/*     */       } 
/*     */     } finally {
/* 285 */       cleanUp();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(Object obj, XmlOutput out, Runnable postInitAction) throws JAXBException {
/*     */     try {
/*     */       ForkXmlOutput forkXmlOutput;
/* 294 */       if (obj == null) {
/* 295 */         throw new IllegalArgumentException(Messages.NOT_MARSHALLABLE.format(new Object[0]));
/*     */       }
/* 297 */       if (this.schema != null) {
/*     */         
/* 299 */         ValidatorHandler validator = this.schema.newValidatorHandler();
/* 300 */         validator.setErrorHandler((ErrorHandler)new FatalAdapter(this.serializer));
/*     */         
/* 302 */         XMLFilterImpl f = new XMLFilterImpl()
/*     */           {
/*     */             public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 305 */               super.startPrefixMapping(prefix.intern(), uri.intern());
/*     */             }
/*     */           };
/* 308 */         f.setContentHandler(validator);
/* 309 */         forkXmlOutput = new ForkXmlOutput((XmlOutput)new SAXOutput(f)
/*     */             {
/*     */               public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws SAXException, IOException, XMLStreamException {
/* 312 */                 super.startDocument(serializer, false, nsUriIndex2prefixIndex, nsContext);
/*     */               }
/*     */               
/*     */               public void endDocument(boolean fragment) throws SAXException, IOException, XMLStreamException {
/* 316 */                 super.endDocument(false);
/*     */               }
/*     */             }out);
/*     */       } 
/*     */       
/*     */       try {
/* 322 */         prewrite((XmlOutput)forkXmlOutput, isFragment(), postInitAction);
/* 323 */         this.serializer.childAsRoot(obj);
/* 324 */         postwrite();
/* 325 */       } catch (SAXException e) {
/* 326 */         throw new MarshalException(e);
/* 327 */       } catch (IOException e) {
/* 328 */         throw new MarshalException(e);
/* 329 */       } catch (XMLStreamException e) {
/* 330 */         throw new MarshalException(e);
/*     */       } finally {
/* 332 */         this.serializer.close();
/*     */       } 
/*     */     } finally {
/* 335 */       cleanUp();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanUp() {
/* 340 */     if (this.toBeFlushed != null) {
/*     */       try {
/* 342 */         this.toBeFlushed.flush();
/* 343 */       } catch (IOException e) {}
/*     */     }
/*     */     
/* 346 */     if (this.toBeClosed != null) {
/*     */       try {
/* 348 */         this.toBeClosed.close();
/* 349 */       } catch (IOException e) {}
/*     */     }
/*     */     
/* 352 */     this.toBeFlushed = null;
/* 353 */     this.toBeClosed = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void prewrite(XmlOutput out, boolean fragment, Runnable postInitAction) throws IOException, SAXException, XMLStreamException {
/* 359 */     this.serializer.startDocument(out, fragment, getSchemaLocation(), getNoNSSchemaLocation());
/* 360 */     if (postInitAction != null) postInitAction.run(); 
/* 361 */     if (this.prefixMapper != null) {
/*     */       
/* 363 */       String[] decls = this.prefixMapper.getContextualNamespaceDecls();
/* 364 */       if (decls != null)
/* 365 */         for (int i = 0; i < decls.length; i += 2) {
/* 366 */           String prefix = decls[i];
/* 367 */           String nsUri = decls[i + 1];
/* 368 */           if (nsUri != null && prefix != null) {
/* 369 */             this.serializer.addInscopeBinding(nsUri, prefix);
/*     */           }
/*     */         }  
/*     */     } 
/* 373 */     this.serializer.setPrefixMapper(this.prefixMapper);
/*     */   }
/*     */   
/*     */   private void postwrite() throws IOException, SAXException, XMLStreamException {
/* 377 */     this.serializer.endDocument();
/* 378 */     this.serializer.reconcileID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharacterEscapeHandler createEscapeHandler(String encoding) {
/* 389 */     if (this.escapeHandler != null)
/*     */     {
/* 391 */       return this.escapeHandler;
/*     */     }
/* 393 */     if (encoding.startsWith("UTF"))
/*     */     {
/*     */       
/* 396 */       return MinimumEscapeHandler.theInstance;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 401 */       return (CharacterEscapeHandler)new NioEscapeHandler(getJavaEncoding(encoding));
/* 402 */     } catch (Throwable e) {
/*     */       
/* 404 */       return DumbEscapeHandler.theInstance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public XmlOutput createWriter(Writer w, String encoding) {
/*     */     XMLWriter xw;
/* 410 */     if (!(w instanceof BufferedWriter)) {
/* 411 */       w = new BufferedWriter(w);
/*     */     }
/* 413 */     assert this.toBeFlushed == null;
/* 414 */     this.toBeFlushed = w;
/*     */     
/* 416 */     CharacterEscapeHandler ceh = createEscapeHandler(encoding);
/*     */ 
/*     */     
/* 419 */     if (isFormattedOutput()) {
/* 420 */       DataWriter d = new DataWriter(w, encoding, ceh);
/* 421 */       d.setIndentStep(this.indent);
/* 422 */       DataWriter dataWriter1 = d;
/*     */     } else {
/* 424 */       xw = new XMLWriter(w, encoding, ceh);
/*     */     } 
/* 426 */     xw.setXmlDecl(!isFragment());
/* 427 */     xw.setHeader(this.header);
/* 428 */     return (XmlOutput)new SAXOutput((ContentHandler)xw);
/*     */   }
/*     */   
/*     */   public XmlOutput createWriter(Writer w) {
/* 432 */     return createWriter(w, getEncoding());
/*     */   }
/*     */   
/*     */   public XmlOutput createWriter(OutputStream os) throws JAXBException {
/* 436 */     return createWriter(os, getEncoding());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XmlOutput createWriter(OutputStream os, String encoding) throws JAXBException {
/* 444 */     if (encoding.equals("UTF-8")) {
/* 445 */       UTF8XmlOutput out; Encoded[] table = this.context.getUTF8NameTable();
/*     */       
/* 447 */       if (isFormattedOutput()) {
/* 448 */         IndentingUTF8XmlOutput indentingUTF8XmlOutput = new IndentingUTF8XmlOutput(os, this.indent, table, this.escapeHandler);
/*     */       }
/* 450 */       else if (this.c14nSupport) {
/* 451 */         C14nXmlOutput c14nXmlOutput = new C14nXmlOutput(os, table, this.context.c14nSupport, this.escapeHandler);
/*     */       } else {
/* 453 */         out = new UTF8XmlOutput(os, table, this.escapeHandler);
/*     */       } 
/* 455 */       if (this.header != null)
/* 456 */         out.setHeader(this.header); 
/* 457 */       return (XmlOutput)out;
/*     */     } 
/*     */     
/*     */     try {
/* 461 */       return createWriter(new OutputStreamWriter(os, getJavaEncoding(encoding)), encoding);
/*     */     
/*     */     }
/* 464 */     catch (UnsupportedEncodingException e) {
/* 465 */       throw new MarshalException(Messages.UNSUPPORTED_ENCODING.format(new Object[] { encoding }, ), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 474 */     if ("com.sun.xml.bind.indentString".equals(name))
/* 475 */       return this.indent; 
/* 476 */     if ("com.sun.xml.bind.characterEscapeHandler".equals(name) || "com.sun.xml.bind.marshaller.CharacterEscapeHandler".equals(name))
/* 477 */       return this.escapeHandler; 
/* 478 */     if ("com.sun.xml.bind.namespacePrefixMapper".equals(name))
/* 479 */       return this.prefixMapper; 
/* 480 */     if ("com.sun.xml.bind.xmlDeclaration".equals(name))
/* 481 */       return Boolean.valueOf(!isFragment()); 
/* 482 */     if ("com.sun.xml.bind.xmlHeaders".equals(name))
/* 483 */       return this.header; 
/* 484 */     if ("com.sun.xml.bind.c14n".equals(name))
/* 485 */       return Boolean.valueOf(this.c14nSupport); 
/* 486 */     if ("com.sun.xml.bind.objectIdentitityCycleDetection".equals(name)) {
/* 487 */       return Boolean.valueOf(this.serializer.getObjectIdentityCycleDetection());
/*     */     }
/* 489 */     return super.getProperty(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 494 */     if ("com.sun.xml.bind.indentString".equals(name)) {
/* 495 */       checkString(name, value);
/* 496 */       this.indent = (String)value;
/*     */       return;
/*     */     } 
/* 499 */     if ("com.sun.xml.bind.characterEscapeHandler".equals(name) || "com.sun.xml.bind.marshaller.CharacterEscapeHandler".equals(name)) {
/* 500 */       if (!(value instanceof CharacterEscapeHandler)) {
/* 501 */         throw new PropertyException(Messages.MUST_BE_X.format(new Object[] { name, CharacterEscapeHandler.class.getName(), value.getClass().getName() }));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 506 */       this.escapeHandler = (CharacterEscapeHandler)value;
/*     */       return;
/*     */     } 
/* 509 */     if ("com.sun.xml.bind.namespacePrefixMapper".equals(name)) {
/* 510 */       if (!(value instanceof NamespacePrefixMapper)) {
/* 511 */         throw new PropertyException(Messages.MUST_BE_X.format(new Object[] { name, NamespacePrefixMapper.class.getName(), value.getClass().getName() }));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 516 */       this.prefixMapper = (NamespacePrefixMapper)value;
/*     */       return;
/*     */     } 
/* 519 */     if ("com.sun.xml.bind.xmlDeclaration".equals(name)) {
/* 520 */       checkBoolean(name, value);
/*     */ 
/*     */       
/* 523 */       super.setProperty("jaxb.fragment", Boolean.valueOf(!((Boolean)value).booleanValue()));
/*     */       return;
/*     */     } 
/* 526 */     if ("com.sun.xml.bind.xmlHeaders".equals(name)) {
/* 527 */       checkString(name, value);
/* 528 */       this.header = (String)value;
/*     */       return;
/*     */     } 
/* 531 */     if ("com.sun.xml.bind.c14n".equals(name)) {
/* 532 */       checkBoolean(name, value);
/* 533 */       this.c14nSupport = ((Boolean)value).booleanValue();
/*     */       return;
/*     */     } 
/* 536 */     if ("com.sun.xml.bind.objectIdentitityCycleDetection".equals(name)) {
/* 537 */       checkBoolean(name, value);
/* 538 */       this.serializer.setObjectIdentityCycleDetection(((Boolean)value).booleanValue());
/*     */       
/*     */       return;
/*     */     } 
/* 542 */     super.setProperty(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkBoolean(String name, Object value) throws PropertyException {
/* 549 */     if (!(value instanceof Boolean)) {
/* 550 */       throw new PropertyException(Messages.MUST_BE_X.format(new Object[] { name, Boolean.class.getName(), value.getClass().getName() }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkString(String name, Object value) throws PropertyException {
/* 561 */     if (!(value instanceof String)) {
/* 562 */       throw new PropertyException(Messages.MUST_BE_X.format(new Object[] { name, String.class.getName(), value.getClass().getName() }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
/* 571 */     if (type == null)
/* 572 */       throw new IllegalArgumentException(); 
/* 573 */     this.serializer.putAdapter(type, (XmlAdapter)adapter);
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends XmlAdapter> A getAdapter(Class<A> type) {
/* 578 */     if (type == null)
/* 579 */       throw new IllegalArgumentException(); 
/* 580 */     if (this.serializer.containsAdapter(type))
/*     */     {
/* 582 */       return this.serializer.getAdapter(type);
/*     */     }
/* 584 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttachmentMarshaller(AttachmentMarshaller am) {
/* 589 */     this.serializer.attachmentMarshaller = am;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentMarshaller getAttachmentMarshaller() {
/* 594 */     return this.serializer.attachmentMarshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema getSchema() {
/* 599 */     return this.schema;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSchema(Schema s) {
/* 604 */     this.schema = s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleEvent(ValidationEvent event) {
/* 612 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Marshaller.Listener getListener() {
/* 617 */     return this.externalListener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(Marshaller.Listener listener) {
/* 622 */     this.externalListener = listener;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\MarshallerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
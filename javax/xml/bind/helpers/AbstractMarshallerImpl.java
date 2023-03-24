/*     */ package javax.xml.bind.helpers;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMarshallerImpl
/*     */   implements Marshaller
/*     */ {
/*  85 */   private ValidationEventHandler eventHandler = new DefaultValidationEventHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private String encoding = "UTF-8";
/*     */ 
/*     */   
/*  95 */   private String schemaLocation = null;
/*     */ 
/*     */   
/*  98 */   private String noNSSchemaLocation = null;
/*     */ 
/*     */   
/*     */   private boolean formattedOutput = false;
/*     */ 
/*     */   
/*     */   private boolean fragment = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(Object obj, OutputStream os) throws JAXBException {
/* 109 */     checkNotNull(obj, "obj", os, "os");
/* 110 */     marshal(obj, new StreamResult(os));
/*     */   }
/*     */   
/*     */   public void marshal(Object jaxbElement, File output) throws JAXBException {
/* 114 */     checkNotNull(jaxbElement, "jaxbElement", output, "output");
/*     */     try {
/* 116 */       OutputStream os = new BufferedOutputStream(new FileOutputStream(output));
/*     */       try {
/* 118 */         marshal(jaxbElement, new StreamResult(os));
/*     */       } finally {
/* 120 */         os.close();
/*     */       } 
/* 122 */     } catch (IOException e) {
/* 123 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(Object obj, Writer w) throws JAXBException {
/* 130 */     checkNotNull(obj, "obj", w, "writer");
/* 131 */     marshal(obj, new StreamResult(w));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(Object obj, ContentHandler handler) throws JAXBException {
/* 137 */     checkNotNull(obj, "obj", handler, "handler");
/* 138 */     marshal(obj, new SAXResult(handler));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(Object obj, Node node) throws JAXBException {
/* 144 */     checkNotNull(obj, "obj", node, "node");
/* 145 */     marshal(obj, new DOMResult(node));
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
/*     */   public Node getNode(Object obj) throws JAXBException {
/* 157 */     checkNotNull(obj, "obj", Boolean.TRUE, "foo");
/*     */     
/* 159 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getEncoding() {
/* 168 */     return this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEncoding(String encoding) {
/* 178 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSchemaLocation() {
/* 187 */     return this.schemaLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setSchemaLocation(String location) {
/* 196 */     this.schemaLocation = location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getNoNSSchemaLocation() {
/* 206 */     return this.noNSSchemaLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setNoNSSchemaLocation(String location) {
/* 215 */     this.noNSSchemaLocation = location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isFormattedOutput() {
/* 225 */     return this.formattedOutput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFormattedOutput(boolean v) {
/* 234 */     this.formattedOutput = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isFragment() {
/* 245 */     return this.fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFragment(boolean v) {
/* 254 */     this.fragment = v;
/*     */   }
/*     */ 
/*     */   
/* 258 */   static String[] aliases = new String[] { "UTF-8", "UTF8", "UTF-16", "Unicode", "UTF-16BE", "UnicodeBigUnmarked", "UTF-16LE", "UnicodeLittleUnmarked", "US-ASCII", "ASCII", "TIS-620", "TIS620", "ISO-10646-UCS-2", "Unicode", "EBCDIC-CP-US", "cp037", "EBCDIC-CP-CA", "cp037", "EBCDIC-CP-NL", "cp037", "EBCDIC-CP-WT", "cp037", "EBCDIC-CP-DK", "cp277", "EBCDIC-CP-NO", "cp277", "EBCDIC-CP-FI", "cp278", "EBCDIC-CP-SE", "cp278", "EBCDIC-CP-IT", "cp280", "EBCDIC-CP-ES", "cp284", "EBCDIC-CP-GB", "cp285", "EBCDIC-CP-FR", "cp297", "EBCDIC-CP-AR1", "cp420", "EBCDIC-CP-HE", "cp424", "EBCDIC-CP-BE", "cp500", "EBCDIC-CP-CH", "cp500", "EBCDIC-CP-ROECE", "cp870", "EBCDIC-CP-YU", "cp870", "EBCDIC-CP-IS", "cp871", "EBCDIC-CP-AR2", "cp918" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJavaEncoding(String encoding) throws UnsupportedEncodingException {
/*     */     try {
/* 310 */       "1".getBytes(encoding);
/* 311 */       return encoding;
/* 312 */     } catch (UnsupportedEncodingException e) {
/*     */       
/* 314 */       for (int i = 0; i < aliases.length; i += 2) {
/* 315 */         if (encoding.equals(aliases[i])) {
/* 316 */           "1".getBytes(aliases[i + 1]);
/* 317 */           return aliases[i + 1];
/*     */         } 
/*     */       } 
/*     */       
/* 321 */       throw new UnsupportedEncodingException(encoding);
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
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 341 */     if (name == null) {
/* 342 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "name"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 347 */     if ("jaxb.encoding".equals(name)) {
/* 348 */       checkString(name, value);
/* 349 */       setEncoding((String)value);
/*     */       return;
/*     */     } 
/* 352 */     if ("jaxb.formatted.output".equals(name)) {
/* 353 */       checkBoolean(name, value);
/* 354 */       setFormattedOutput(((Boolean)value).booleanValue());
/*     */       return;
/*     */     } 
/* 357 */     if ("jaxb.noNamespaceSchemaLocation".equals(name)) {
/* 358 */       checkString(name, value);
/* 359 */       setNoNSSchemaLocation((String)value);
/*     */       return;
/*     */     } 
/* 362 */     if ("jaxb.schemaLocation".equals(name)) {
/* 363 */       checkString(name, value);
/* 364 */       setSchemaLocation((String)value);
/*     */       return;
/*     */     } 
/* 367 */     if ("jaxb.fragment".equals(name)) {
/* 368 */       checkBoolean(name, value);
/* 369 */       setFragment(((Boolean)value).booleanValue());
/*     */       
/*     */       return;
/*     */     } 
/* 373 */     throw new PropertyException(name, value);
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
/*     */   public Object getProperty(String name) throws PropertyException {
/* 385 */     if (name == null) {
/* 386 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "name"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 391 */     if ("jaxb.encoding".equals(name))
/* 392 */       return getEncoding(); 
/* 393 */     if ("jaxb.formatted.output".equals(name))
/* 394 */       return isFormattedOutput() ? Boolean.TRUE : Boolean.FALSE; 
/* 395 */     if ("jaxb.noNamespaceSchemaLocation".equals(name))
/* 396 */       return getNoNSSchemaLocation(); 
/* 397 */     if ("jaxb.schemaLocation".equals(name))
/* 398 */       return getSchemaLocation(); 
/* 399 */     if ("jaxb.fragment".equals(name)) {
/* 400 */       return isFragment() ? Boolean.TRUE : Boolean.FALSE;
/*     */     }
/* 402 */     throw new PropertyException(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidationEventHandler getEventHandler() throws JAXBException {
/* 408 */     return this.eventHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEventHandler(ValidationEventHandler handler) throws JAXBException {
/* 417 */     if (handler == null) {
/* 418 */       this.eventHandler = new DefaultValidationEventHandler();
/*     */     } else {
/* 420 */       this.eventHandler = handler;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkBoolean(String name, Object value) throws PropertyException {
/* 431 */     if (!(value instanceof Boolean)) {
/* 432 */       throw new PropertyException(Messages.format("AbstractMarshallerImpl.MustBeBoolean", name));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkString(String name, Object value) throws PropertyException {
/* 440 */     if (!(value instanceof String)) {
/* 441 */       throw new PropertyException(Messages.format("AbstractMarshallerImpl.MustBeString", name));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkNotNull(Object o1, String o1Name, Object o2, String o2Name) {
/* 451 */     if (o1 == null) {
/* 452 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", o1Name));
/*     */     }
/*     */     
/* 455 */     if (o2 == null) {
/* 456 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", o2Name));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, XMLEventWriter writer) throws JAXBException {
/* 464 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Object obj, XMLStreamWriter writer) throws JAXBException {
/* 470 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setSchema(Schema schema) {
/* 474 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/* 478 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setAdapter(XmlAdapter adapter) {
/* 482 */     if (adapter == null)
/* 483 */       throw new IllegalArgumentException(); 
/* 484 */     setAdapter(adapter.getClass(), adapter);
/*     */   }
/*     */   
/*     */   public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
/* 488 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <A extends XmlAdapter> A getAdapter(Class<A> type) {
/* 492 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setAttachmentMarshaller(AttachmentMarshaller am) {
/* 496 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public AttachmentMarshaller getAttachmentMarshaller() {
/* 500 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setListener(Marshaller.Listener listener) {
/* 504 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Marshaller.Listener getListener() {
/* 508 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\helpers\AbstractMarshallerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
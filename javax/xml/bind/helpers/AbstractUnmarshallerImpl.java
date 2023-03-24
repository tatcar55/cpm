/*     */ package javax.xml.bind.helpers;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.UnmarshalException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractUnmarshallerImpl
/*     */   implements Unmarshaller
/*     */ {
/*  91 */   private ValidationEventHandler eventHandler = new DefaultValidationEventHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean validating = false;
/*     */ 
/*     */ 
/*     */   
/* 100 */   private XMLReader reader = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLReader getXMLReader() throws JAXBException {
/* 112 */     if (this.reader == null) {
/*     */       
/*     */       try {
/* 115 */         SAXParserFactory parserFactory = SAXParserFactory.newInstance();
/* 116 */         parserFactory.setNamespaceAware(true);
/*     */ 
/*     */ 
/*     */         
/* 120 */         parserFactory.setValidating(false);
/* 121 */         this.reader = parserFactory.newSAXParser().getXMLReader();
/* 122 */       } catch (ParserConfigurationException e) {
/* 123 */         throw new JAXBException(e);
/* 124 */       } catch (SAXException e) {
/* 125 */         throw new JAXBException(e);
/*     */       } 
/*     */     }
/* 128 */     return this.reader;
/*     */   }
/*     */   
/*     */   public Object unmarshal(Source source) throws JAXBException {
/* 132 */     if (source == null) {
/* 133 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "source"));
/*     */     }
/*     */ 
/*     */     
/* 137 */     if (source instanceof SAXSource)
/* 138 */       return unmarshal((SAXSource)source); 
/* 139 */     if (source instanceof StreamSource)
/* 140 */       return unmarshal(streamSourceToInputSource((StreamSource)source)); 
/* 141 */     if (source instanceof DOMSource) {
/* 142 */       return unmarshal(((DOMSource)source).getNode());
/*     */     }
/*     */     
/* 145 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object unmarshal(SAXSource source) throws JAXBException {
/* 151 */     XMLReader r = source.getXMLReader();
/* 152 */     if (r == null) {
/* 153 */       r = getXMLReader();
/*     */     }
/* 155 */     return unmarshal(r, source.getInputSource());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object unmarshal(XMLReader paramXMLReader, InputSource paramInputSource) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object unmarshal(InputSource source) throws JAXBException {
/* 167 */     if (source == null) {
/* 168 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "source"));
/*     */     }
/*     */ 
/*     */     
/* 172 */     return unmarshal(getXMLReader(), source);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object unmarshal(String url) throws JAXBException {
/* 177 */     return unmarshal(new InputSource(url));
/*     */   }
/*     */   
/*     */   public final Object unmarshal(URL url) throws JAXBException {
/* 181 */     if (url == null) {
/* 182 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "url"));
/*     */     }
/*     */ 
/*     */     
/* 186 */     return unmarshal(url.toExternalForm());
/*     */   }
/*     */   
/*     */   public final Object unmarshal(File f) throws JAXBException {
/* 190 */     if (f == null) {
/* 191 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "file"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 197 */       String path = f.getAbsolutePath();
/* 198 */       if (File.separatorChar != '/')
/* 199 */         path = path.replace(File.separatorChar, '/'); 
/* 200 */       if (!path.startsWith("/"))
/* 201 */         path = "/" + path; 
/* 202 */       if (!path.endsWith("/") && f.isDirectory())
/* 203 */         path = path + "/"; 
/* 204 */       return unmarshal(new URL("file", "", path));
/* 205 */     } catch (MalformedURLException e) {
/* 206 */       throw new IllegalArgumentException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object unmarshal(InputStream is) throws JAXBException {
/* 213 */     if (is == null) {
/* 214 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "is"));
/*     */     }
/*     */ 
/*     */     
/* 218 */     InputSource isrc = new InputSource(is);
/* 219 */     return unmarshal(isrc);
/*     */   }
/*     */   
/*     */   public final Object unmarshal(Reader reader) throws JAXBException {
/* 223 */     if (reader == null) {
/* 224 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "reader"));
/*     */     }
/*     */ 
/*     */     
/* 228 */     InputSource isrc = new InputSource(reader);
/* 229 */     return unmarshal(isrc);
/*     */   }
/*     */ 
/*     */   
/*     */   private static InputSource streamSourceToInputSource(StreamSource ss) {
/* 234 */     InputSource is = new InputSource();
/* 235 */     is.setSystemId(ss.getSystemId());
/* 236 */     is.setByteStream(ss.getInputStream());
/* 237 */     is.setCharacterStream(ss.getReader());
/*     */     
/* 239 */     return is;
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
/*     */   public boolean isValidating() throws JAXBException {
/* 256 */     return this.validating;
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
/*     */   public void setEventHandler(ValidationEventHandler handler) throws JAXBException {
/* 276 */     if (handler == null) {
/* 277 */       this.eventHandler = new DefaultValidationEventHandler();
/*     */     } else {
/* 279 */       this.eventHandler = handler;
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
/*     */   public void setValidating(boolean validating) throws JAXBException {
/* 297 */     this.validating = validating;
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
/*     */   public ValidationEventHandler getEventHandler() throws JAXBException {
/* 310 */     return this.eventHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UnmarshalException createUnmarshalException(SAXException e) {
/* 335 */     Exception nested = e.getException();
/* 336 */     if (nested instanceof UnmarshalException) {
/* 337 */       return (UnmarshalException)nested;
/*     */     }
/* 339 */     if (nested instanceof RuntimeException)
/*     */     {
/*     */ 
/*     */       
/* 343 */       throw (RuntimeException)nested;
/*     */     }
/*     */ 
/*     */     
/* 347 */     if (nested != null) {
/* 348 */       return new UnmarshalException(nested);
/*     */     }
/* 350 */     return new UnmarshalException(e);
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
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 362 */     if (name == null) {
/* 363 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "name"));
/*     */     }
/*     */ 
/*     */     
/* 367 */     throw new PropertyException(name, value);
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
/* 379 */     if (name == null) {
/* 380 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "name"));
/*     */     }
/*     */ 
/*     */     
/* 384 */     throw new PropertyException(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object unmarshal(XMLEventReader reader) throws JAXBException {
/* 389 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object unmarshal(XMLStreamReader reader) throws JAXBException {
/* 394 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(Node node, Class<T> expectedType) throws JAXBException {
/* 398 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(Source source, Class<T> expectedType) throws JAXBException {
/* 402 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(XMLStreamReader reader, Class<T> expectedType) throws JAXBException {
/* 406 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <T> JAXBElement<T> unmarshal(XMLEventReader reader, Class<T> expectedType) throws JAXBException {
/* 410 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setSchema(Schema schema) {
/* 414 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/* 418 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setAdapter(XmlAdapter adapter) {
/* 422 */     if (adapter == null)
/* 423 */       throw new IllegalArgumentException(); 
/* 424 */     setAdapter(adapter.getClass(), adapter);
/*     */   }
/*     */   
/*     */   public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
/* 428 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <A extends XmlAdapter> A getAdapter(Class<A> type) {
/* 432 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setAttachmentUnmarshaller(AttachmentUnmarshaller au) {
/* 436 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public AttachmentUnmarshaller getAttachmentUnmarshaller() {
/* 440 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setListener(Unmarshaller.Listener listener) {
/* 444 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Unmarshaller.Listener getListener() {
/* 448 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\helpers\AbstractUnmarshallerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
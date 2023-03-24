/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.JAXBAssertionError;
/*     */ import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.DataWriter;
/*     */ import com.sun.xml.bind.marshaller.DumbEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.Messages;
/*     */ import com.sun.xml.bind.marshaller.MinimumEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.bind.marshaller.NioEscapeHandler;
/*     */ import com.sun.xml.bind.marshaller.SAX2DOMEx;
/*     */ import com.sun.xml.bind.marshaller.SchemaLocationFilter;
/*     */ import com.sun.xml.bind.marshaller.XMLWriter;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.MarshalException;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.helpers.AbstractMarshallerImpl;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MarshallerImpl
/*     */   extends AbstractMarshallerImpl
/*     */ {
/*  59 */   private String indent = "    ";
/*     */ 
/*     */   
/*  62 */   private NamespacePrefixMapper prefixMapper = null;
/*     */ 
/*     */   
/*  65 */   private CharacterEscapeHandler escapeHandler = null;
/*     */ 
/*     */   
/*     */   private boolean printXmlDeclaration = true;
/*     */ 
/*     */   
/*  71 */   private String header = null;
/*     */   
/*     */   final DefaultJAXBContextImpl context;
/*     */   private static final String INDENT_STRING = "com.sun.xml.bind.indentString";
/*     */   private static final String PREFIX_MAPPER = "com.sun.xml.bind.namespacePrefixMapper";
/*     */   
/*     */   public MarshallerImpl(DefaultJAXBContextImpl c) {
/*  78 */     DatatypeConverter.setDatatypeConverter(DatatypeConverterImpl.theInstance);
/*     */     
/*  80 */     this.context = c;
/*     */   }
/*     */   private static final String ENCODING_HANDLER = "com.sun.xml.bind.characterEscapeHandler"; private static final String XMLDECLARATION = "com.sun.xml.bind.xmlDeclaration"; private static final String XML_HEADERS = "com.sun.xml.bind.xmlHeaders";
/*     */   
/*     */   public void marshal(Object obj, Result result) throws JAXBException {
/*  85 */     XMLSerializable so = this.context.getGrammarInfo().castToXMLSerializable(obj);
/*     */     
/*  87 */     if (so == null) {
/*  88 */       throw new MarshalException(Messages.format("MarshallerImpl.NotMarshallable"));
/*     */     }
/*     */ 
/*     */     
/*  92 */     if (result instanceof SAXResult) {
/*  93 */       write(so, ((SAXResult)result).getHandler());
/*     */       return;
/*     */     } 
/*  96 */     if (result instanceof DOMResult) {
/*  97 */       Node node = ((DOMResult)result).getNode();
/*     */       
/*  99 */       if (node == null) {
/*     */         try {
/* 101 */           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 102 */           dbf.setNamespaceAware(true);
/* 103 */           DocumentBuilder db = dbf.newDocumentBuilder();
/* 104 */           Document doc = db.newDocument();
/* 105 */           ((DOMResult)result).setNode(doc);
/* 106 */           write(so, (ContentHandler)new SAX2DOMEx(doc));
/* 107 */         } catch (ParserConfigurationException pce) {
/* 108 */           throw new JAXBAssertionError(pce);
/*     */         } 
/*     */       } else {
/* 111 */         write(so, (ContentHandler)new SAX2DOMEx(node));
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     if (result instanceof StreamResult) {
/* 117 */       StreamResult sr = (StreamResult)result;
/* 118 */       XMLWriter w = null;
/*     */       
/* 120 */       if (sr.getWriter() != null) {
/* 121 */         w = createWriter(sr.getWriter());
/* 122 */       } else if (sr.getOutputStream() != null) {
/* 123 */         w = createWriter(sr.getOutputStream());
/* 124 */       } else if (sr.getSystemId() != null) {
/* 125 */         String fileURL = sr.getSystemId();
/*     */         
/* 127 */         if (fileURL.startsWith("file:///")) {
/* 128 */           if (fileURL.substring(8).indexOf(":") > 0) {
/* 129 */             fileURL = fileURL.substring(8);
/*     */           } else {
/* 131 */             fileURL = fileURL.substring(7);
/*     */           } 
/*     */         }
/*     */         try {
/* 135 */           w = createWriter(new FileOutputStream(fileURL));
/* 136 */         } catch (IOException e) {
/* 137 */           throw new MarshalException(e);
/*     */         } 
/*     */       } 
/*     */       
/* 141 */       if (w == null) {
/* 142 */         throw new IllegalArgumentException();
/*     */       }
/* 144 */       write(so, (ContentHandler)w);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 149 */     throw new MarshalException(Messages.format("MarshallerImpl.UnsupportedResult"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(XMLSerializable obj, ContentHandler writer) throws JAXBException {
/*     */     try {
/*     */       SchemaLocationFilter schemaLocationFilter;
/* 157 */       if (getSchemaLocation() != null || getNoNSSchemaLocation() != null)
/*     */       {
/*     */         
/* 160 */         schemaLocationFilter = new SchemaLocationFilter(getSchemaLocation(), getNoNSSchemaLocation(), writer);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 166 */       SAXMarshaller serializer = new SAXMarshaller((ContentHandler)schemaLocationFilter, this.prefixMapper, this);
/*     */ 
/*     */       
/* 169 */       schemaLocationFilter.setDocumentLocator(new LocatorImpl());
/* 170 */       schemaLocationFilter.startDocument();
/* 171 */       serializer.childAsBody(obj, null);
/* 172 */       schemaLocationFilter.endDocument();
/*     */       
/* 174 */       serializer.reconcileID();
/* 175 */     } catch (SAXException e) {
/* 176 */       throw new MarshalException(e);
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
/*     */   protected CharacterEscapeHandler createEscapeHandler(String encoding) {
/* 188 */     if (this.escapeHandler != null)
/*     */     {
/* 190 */       return this.escapeHandler;
/*     */     }
/* 192 */     if (encoding.startsWith("UTF"))
/*     */     {
/*     */       
/* 195 */       return MinimumEscapeHandler.theInstance;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 200 */       return (CharacterEscapeHandler)new NioEscapeHandler(getJavaEncoding(encoding));
/* 201 */     } catch (Throwable e) {
/*     */       
/* 203 */       return DumbEscapeHandler.theInstance;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLWriter createWriter(Writer w, String encoding) throws JAXBException {
/*     */     XMLWriter xw;
/* 210 */     w = new BufferedWriter(w);
/*     */     
/* 212 */     CharacterEscapeHandler ceh = createEscapeHandler(encoding);
/*     */ 
/*     */     
/* 215 */     if (isFormattedOutput()) {
/* 216 */       DataWriter d = new DataWriter(w, encoding, ceh);
/* 217 */       d.setIndentStep(this.indent);
/* 218 */       DataWriter dataWriter1 = d;
/*     */     } else {
/*     */       
/* 221 */       xw = new XMLWriter(w, encoding, ceh);
/*     */     } 
/* 223 */     xw.setXmlDecl(this.printXmlDeclaration);
/* 224 */     xw.setHeader(this.header);
/* 225 */     return xw;
/*     */   }
/*     */   
/*     */   public XMLWriter createWriter(Writer w) throws JAXBException {
/* 229 */     return createWriter(w, getEncoding());
/*     */   }
/*     */   
/*     */   public XMLWriter createWriter(OutputStream os) throws JAXBException {
/* 233 */     return createWriter(os, getEncoding());
/*     */   }
/*     */   
/*     */   public XMLWriter createWriter(OutputStream os, String encoding) throws JAXBException {
/*     */     try {
/* 238 */       return createWriter(new OutputStreamWriter(os, getJavaEncoding(encoding)), encoding);
/*     */     
/*     */     }
/* 241 */     catch (UnsupportedEncodingException e) {
/* 242 */       throw new MarshalException(Messages.format("MarshallerImpl.UnsupportedEncoding", encoding), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 250 */     if ("com.sun.xml.bind.indentString".equals(name))
/* 251 */       return this.indent; 
/* 252 */     if ("com.sun.xml.bind.characterEscapeHandler".equals(name))
/* 253 */       return this.escapeHandler; 
/* 254 */     if ("com.sun.xml.bind.namespacePrefixMapper".equals(name))
/* 255 */       return this.prefixMapper; 
/* 256 */     if ("com.sun.xml.bind.xmlDeclaration".equals(name))
/* 257 */       return this.printXmlDeclaration ? Boolean.TRUE : Boolean.FALSE; 
/* 258 */     if ("com.sun.xml.bind.xmlHeaders".equals(name)) {
/* 259 */       return this.header;
/*     */     }
/* 261 */     return super.getProperty(name);
/*     */   }
/*     */   
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 265 */     if ("com.sun.xml.bind.indentString".equals(name) && value instanceof String) {
/* 266 */       this.indent = (String)value;
/*     */       return;
/*     */     } 
/* 269 */     if ("com.sun.xml.bind.characterEscapeHandler".equals(name)) {
/* 270 */       this.escapeHandler = (CharacterEscapeHandler)value;
/*     */       return;
/*     */     } 
/* 273 */     if ("com.sun.xml.bind.namespacePrefixMapper".equals(name)) {
/* 274 */       this.prefixMapper = (NamespacePrefixMapper)value;
/*     */       return;
/*     */     } 
/* 277 */     if ("com.sun.xml.bind.xmlDeclaration".equals(name)) {
/* 278 */       this.printXmlDeclaration = ((Boolean)value).booleanValue();
/*     */       return;
/*     */     } 
/* 281 */     if ("com.sun.xml.bind.xmlHeaders".equals(name)) {
/* 282 */       this.header = (String)value;
/*     */       
/*     */       return;
/*     */     } 
/* 286 */     super.setProperty(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\MarshallerImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
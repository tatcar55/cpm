/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.xml.NamedNodeMapIterator;
/*     */ import com.sun.xml.rpc.util.xml.NullEntityResolver;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.Schema;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParseException;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*     */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.FactoryConfigurationError;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaParser
/*     */ {
/*     */   private boolean _followImports;
/*     */   private static final String PREFIX_XMLNS = "xmlns";
/*     */   private static final String PREFIX_XMLNS_COLON = "xmlns:";
/*     */   
/*     */   public boolean getFollowImports() {
/*  69 */     return this._followImports;
/*     */   }
/*     */   
/*     */   public void setFollowImports(boolean b) {
/*  73 */     this._followImports = b;
/*     */   }
/*     */   
/*     */   public SchemaDocument parse(InputSource source) {
/*  77 */     SchemaDocument schemaDocument = new SchemaDocument();
/*  78 */     schemaDocument.setSystemId(source.getSystemId());
/*  79 */     ParserContext context = new ParserContext((AbstractDocument)schemaDocument, null);
/*  80 */     context.setFollowImports(this._followImports);
/*  81 */     schemaDocument.setSchema(parseSchema(context, source, (String)null));
/*  82 */     return schemaDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Schema parseSchema(ParserContext context, InputSource source, String expectedTargetNamespaceURI) {
/*  90 */     Schema schema = parseSchemaNoImport(context, source, expectedTargetNamespaceURI);
/*     */     
/*  92 */     schema.defineAllEntities();
/*  93 */     processImports(context, source, schema);
/*  94 */     return schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Schema parseSchema(ParserContext context, Element e, String expectedTargetNamespaceURI) {
/* 101 */     Schema schema = parseSchemaNoImport(context, e, expectedTargetNamespaceURI);
/*     */     
/* 103 */     schema.defineAllEntities();
/* 104 */     processImports(context, null, schema);
/* 105 */     return schema;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processImports(ParserContext context, InputSource source, Schema schema) {
/* 112 */     for (Iterator<SchemaElement> iter = schema.getContent().children(); iter.hasNext(); ) {
/* 113 */       SchemaElement child = iter.next();
/* 114 */       if (child.getQName().equals(SchemaConstants.QNAME_IMPORT)) {
/* 115 */         String location = child.getValueOfAttributeOrNull("schemaLocation");
/*     */ 
/*     */         
/* 118 */         String namespace = child.getValueOfAttributeOrNull("namespace");
/*     */ 
/*     */ 
/*     */         
/* 122 */         if (location != null) {
/* 123 */           String adjustedLocation = null;
/* 124 */           if (source != null && source.getSystemId() != null) {
/* 125 */             adjustedLocation = Util.processSystemIdWithBase(source.getSystemId(), location);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 131 */           if (adjustedLocation == null) {
/* 132 */             adjustedLocation = (context.getWSDLLocation() == null) ? location : Util.processSystemIdWithBase(context.getWSDLLocation(), location);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 139 */           if (!context.getDocument().isImportedDocument(adjustedLocation)) {
/*     */ 
/*     */ 
/*     */             
/* 143 */             context.getDocument().addImportedDocument(adjustedLocation);
/*     */ 
/*     */             
/* 146 */             context.getDocument().addImportedEntity((Entity)parseSchema(context, new InputSource(adjustedLocation), namespace));
/*     */           } 
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 153 */       if (child.getQName().equals(SchemaConstants.QNAME_INCLUDE) && schema.getTargetNamespaceURI() != null) {
/*     */ 
/*     */         
/* 156 */         String location = child.getValueOfAttributeOrNull("schemaLocation");
/*     */ 
/*     */         
/* 159 */         if (location != null && !context.getDocument().isIncludedDocument(location)) {
/*     */           
/* 161 */           context.getDocument().addIncludedDocument(location);
/* 162 */           String adjustedLocation = null;
/* 163 */           if (source != null && source.getSystemId() != null) {
/* 164 */             adjustedLocation = Util.processSystemIdWithBase(source.getSystemId(), location);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 169 */           if (adjustedLocation == null) {
/* 170 */             adjustedLocation = (context.getDocument().getSystemId() == null) ? location : Util.processSystemIdWithBase(context.getDocument().getSystemId(), location);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 177 */           context.getDocument().addIncludedEntity((Entity)parseSchema(context, new InputSource(adjustedLocation), schema.getTargetNamespaceURI()));
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 183 */       if (child.getQName().equals(SchemaConstants.QNAME_REDEFINE))
/*     */       {
/*     */         
/* 186 */         Util.fail("validation.unsupportedSchemaFeature", "redefine");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Schema parseSchemaNoImport(ParserContext context, InputSource source, String expectedTargetNamespaceURI) {
/*     */     try {
/* 196 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/*     */       
/* 198 */       builderFactory.setNamespaceAware(true);
/* 199 */       builderFactory.setValidating(false);
/* 200 */       DocumentBuilder builder = builderFactory.newDocumentBuilder();
/* 201 */       builder.setErrorHandler(new ErrorHandler()
/*     */           {
/*     */             public void error(SAXParseException e) throws SAXParseException {
/* 204 */               throw e;
/*     */             }
/*     */             
/*     */             public void fatalError(SAXParseException e) throws SAXParseException {
/* 208 */               throw e;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void warning(SAXParseException err) throws SAXParseException {}
/*     */           });
/* 215 */       builder.setEntityResolver((EntityResolver)new NullEntityResolver());
/*     */       
/*     */       try {
/* 218 */         Document document = builder.parse(source);
/* 219 */         return parseSchemaNoImport(context, document, expectedTargetNamespaceURI);
/*     */ 
/*     */       
/*     */       }
/* 223 */       catch (IOException e) {
/* 224 */         throw new ParseException("parsing.ioException", new LocalizableExceptionAdapter(e));
/*     */       
/*     */       }
/* 227 */       catch (SAXException e) {
/* 228 */         throw new ParseException("parsing.saxException", new LocalizableExceptionAdapter(e));
/*     */       }
/*     */     
/*     */     }
/* 232 */     catch (ParserConfigurationException e) {
/* 233 */       throw new ParseException("parsing.parserConfigException", new LocalizableExceptionAdapter(e));
/*     */     
/*     */     }
/* 236 */     catch (FactoryConfigurationError e) {
/* 237 */       throw new ParseException("parsing.factoryConfigException", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Schema parseSchemaNoImport(ParserContext context, Document doc, String expectedTargetNamespaceURI) {
/* 247 */     Element root = doc.getDocumentElement();
/* 248 */     Util.verifyTagNSRootElement(root, SchemaConstants.QNAME_SCHEMA);
/* 249 */     return parseSchemaNoImport(context, root, expectedTargetNamespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Schema parseSchemaNoImport(ParserContext context, Element e, String expectedTargetNamespaceURI) {
/* 256 */     Schema schema = new Schema(context.getDocument());
/* 257 */     String targetNamespaceURI = XmlUtil.getAttributeOrNull(e, "targetNamespace");
/*     */ 
/*     */     
/* 260 */     if (targetNamespaceURI != null && expectedTargetNamespaceURI != null && !expectedTargetNamespaceURI.equals(targetNamespaceURI))
/*     */     {
/*     */       
/* 263 */       throw new ValidationException("validation.incorrectTargetNamespace", new Object[] { targetNamespaceURI, expectedTargetNamespaceURI });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     if (targetNamespaceURI == null) {
/* 270 */       schema.setTargetNamespaceURI(expectedTargetNamespaceURI);
/*     */     } else {
/* 272 */       schema.setTargetNamespaceURI(targetNamespaceURI);
/*     */     } 
/*     */     
/* 275 */     for (Iterator<String> iter = context.getPrefixes(); iter.hasNext(); ) {
/* 276 */       String prefix = iter.next();
/* 277 */       String nsURI = context.getNamespaceURI(prefix);
/* 278 */       if (nsURI == null)
/*     */       {
/* 280 */         throw new ParseException("parsing.shouldNotHappen");
/*     */       }
/* 282 */       schema.addPrefix(prefix, nsURI);
/*     */     } 
/*     */     
/* 285 */     context.push();
/* 286 */     context.registerNamespaces(e);
/*     */ 
/*     */     
/* 289 */     SchemaElement schemaElement = new SchemaElement(SchemaConstants.QNAME_SCHEMA);
/*     */ 
/*     */     
/* 292 */     copyNamespaceDeclarations(schemaElement, e);
/* 293 */     copyAttributesNoNs(schemaElement, e);
/* 294 */     copyElementContent(schemaElement, e);
/*     */     
/* 296 */     schema.setContent(schemaElement);
/* 297 */     schemaElement.setSchema(schema);
/*     */     
/* 299 */     context.pop();
/* 300 */     context.fireDoneParsingEntity(SchemaConstants.QNAME_SCHEMA, (Entity)schema);
/* 301 */     return schema;
/*     */   }
/*     */   
/*     */   protected void copyAttributesNoNs(SchemaElement target, Element source) {
/* 305 */     NamedNodeMapIterator<Attr> namedNodeMapIterator = new NamedNodeMapIterator(source.getAttributes());
/* 306 */     while (namedNodeMapIterator.hasNext()) {
/*     */       
/* 308 */       Attr attr = namedNodeMapIterator.next();
/* 309 */       if (attr.getName().equals("xmlns") || attr.getName().startsWith("xmlns:")) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 314 */       SchemaAttribute attribute = new SchemaAttribute(attr.getLocalName());
/*     */       
/* 316 */       attribute.setNamespaceURI(attr.getNamespaceURI());
/* 317 */       attribute.setValue(attr.getValue());
/* 318 */       target.addAttribute(attribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyNamespaceDeclarations(SchemaElement target, Element source) {
/* 325 */     NamedNodeMapIterator<Attr> namedNodeMapIterator = new NamedNodeMapIterator(source.getAttributes());
/* 326 */     while (namedNodeMapIterator.hasNext()) {
/*     */       
/* 328 */       Attr attr = namedNodeMapIterator.next();
/* 329 */       if (attr.getName().equals("xmlns")) {
/*     */         
/* 331 */         target.addPrefix("", attr.getValue()); continue;
/*     */       } 
/* 333 */       String prefix = XmlUtil.getPrefix(attr.getName());
/* 334 */       if (prefix != null && prefix.equals("xmlns")) {
/* 335 */         String nsPrefix = XmlUtil.getLocalPart(attr.getName());
/* 336 */         String uri = attr.getValue();
/* 337 */         target.addPrefix(nsPrefix, uri);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void copyElementContent(SchemaElement target, Element source) {
/* 344 */     for (Iterator iter = XmlUtil.getAllChildren(source); iter.hasNext(); ) {
/* 345 */       Element e2 = Util.nextElementIgnoringCharacterContent(iter);
/* 346 */       if (e2 == null)
/*     */         break; 
/* 348 */       SchemaElement newElement = new SchemaElement(e2.getLocalName());
/* 349 */       newElement.setNamespaceURI(e2.getNamespaceURI());
/* 350 */       copyNamespaceDeclarations(newElement, e2);
/* 351 */       copyAttributesNoNs(newElement, e2);
/* 352 */       copyElementContent(newElement, e2);
/* 353 */       target.addChild(newElement);
/* 354 */       newElement.setParent(target);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\SchemaParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
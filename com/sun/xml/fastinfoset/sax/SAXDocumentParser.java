/*      */ package com.sun.xml.fastinfoset.sax;
/*      */ 
/*      */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*      */ import com.sun.xml.fastinfoset.Decoder;
/*      */ import com.sun.xml.fastinfoset.DecoderStateTables;
/*      */ import com.sun.xml.fastinfoset.EncodingConstants;
/*      */ import com.sun.xml.fastinfoset.QualifiedName;
/*      */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*      */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithmState;
/*      */ import com.sun.xml.fastinfoset.util.CharArray;
/*      */ import com.sun.xml.fastinfoset.util.CharArrayString;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithm;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*      */ import org.jvnet.fastinfoset.FastInfosetException;
/*      */ import org.jvnet.fastinfoset.sax.EncodingAlgorithmContentHandler;
/*      */ import org.jvnet.fastinfoset.sax.FastInfosetReader;
/*      */ import org.jvnet.fastinfoset.sax.PrimitiveTypeContentHandler;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.ext.DeclHandler;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SAXDocumentParser
/*      */   extends Decoder
/*      */   implements FastInfosetReader
/*      */ {
/*   66 */   private static final Logger logger = Logger.getLogger(SAXDocumentParser.class.getName());
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class LexicalHandlerImpl
/*      */     implements LexicalHandler
/*      */   {
/*      */     private LexicalHandlerImpl() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void comment(char[] ch, int start, int end) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void startDTD(String name, String publicId, String systemId) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void endDTD() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void startEntity(String name) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void endEntity(String name) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void startCDATA() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void endCDATA() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DeclHandlerImpl
/*      */     implements DeclHandler
/*      */   {
/*      */     private DeclHandlerImpl() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void elementDecl(String name, String model) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException {}
/*      */ 
/*      */     
/*      */     public void internalEntityDecl(String name, String value) throws SAXException {}
/*      */ 
/*      */     
/*      */     public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {}
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean _namespacePrefixesFeature = false;
/*      */   
/*      */   protected EntityResolver _entityResolver;
/*      */   
/*      */   protected DTDHandler _dtdHandler;
/*      */   
/*      */   protected ContentHandler _contentHandler;
/*      */   
/*      */   protected ErrorHandler _errorHandler;
/*      */   
/*      */   protected LexicalHandler _lexicalHandler;
/*      */   
/*      */   protected DeclHandler _declHandler;
/*      */   
/*      */   protected EncodingAlgorithmContentHandler _algorithmHandler;
/*      */   
/*      */   protected PrimitiveTypeContentHandler _primitiveHandler;
/*      */   
/*  145 */   protected BuiltInEncodingAlgorithmState builtInAlgorithmState = new BuiltInEncodingAlgorithmState();
/*      */ 
/*      */   
/*      */   protected AttributesHolder _attributes;
/*      */   
/*  150 */   protected int[] _namespacePrefixes = new int[16];
/*      */   
/*      */   protected int _namespacePrefixesIndex;
/*      */   
/*      */   protected boolean _clearAttributes = false;
/*      */ 
/*      */   
/*      */   public SAXDocumentParser() {
/*  158 */     DefaultHandler handler = new DefaultHandler();
/*  159 */     this._attributes = new AttributesHolder(this._registeredEncodingAlgorithms);
/*      */     
/*  161 */     this._entityResolver = handler;
/*  162 */     this._dtdHandler = handler;
/*  163 */     this._contentHandler = handler;
/*  164 */     this._errorHandler = handler;
/*  165 */     this._lexicalHandler = new LexicalHandlerImpl();
/*  166 */     this._declHandler = new DeclHandlerImpl();
/*      */   }
/*      */   
/*      */   protected void resetOnError() {
/*  170 */     this._clearAttributes = false;
/*  171 */     this._attributes.clear();
/*  172 */     this._namespacePrefixesIndex = 0;
/*      */     
/*  174 */     if (this._v != null) {
/*  175 */       this._v.prefix.clearCompletely();
/*      */     }
/*  177 */     this._duplicateAttributeVerifier.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  184 */     if (name.equals("http://xml.org/sax/features/namespaces"))
/*  185 */       return true; 
/*  186 */     if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/*  187 */       return this._namespacePrefixesFeature; 
/*  188 */     if (name.equals("http://xml.org/sax/features/string-interning") || name.equals("http://jvnet.org/fastinfoset/parser/properties/string-interning"))
/*      */     {
/*  190 */       return getStringInterning();
/*      */     }
/*  192 */     throw new SAXNotRecognizedException(CommonResourceBundle.getInstance().getString("message.featureNotSupported") + name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  199 */     if (name.equals("http://xml.org/sax/features/namespaces")) {
/*  200 */       if (!value) {
/*  201 */         throw new SAXNotSupportedException(name + ":" + value);
/*      */       }
/*  203 */     } else if (name.equals("http://xml.org/sax/features/namespace-prefixes")) {
/*  204 */       this._namespacePrefixesFeature = value;
/*  205 */     } else if (name.equals("http://xml.org/sax/features/string-interning") || name.equals("http://jvnet.org/fastinfoset/parser/properties/string-interning")) {
/*      */       
/*  207 */       setStringInterning(value);
/*      */     } else {
/*  209 */       throw new SAXNotRecognizedException(CommonResourceBundle.getInstance().getString("message.featureNotSupported") + name);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  216 */     if (name.equals("http://xml.org/sax/properties/lexical-handler"))
/*  217 */       return getLexicalHandler(); 
/*  218 */     if (name.equals("http://xml.org/sax/properties/declaration-handler"))
/*  219 */       return getDeclHandler(); 
/*  220 */     if (name.equals("http://jvnet.org/fastinfoset/parser/properties/external-vocabularies"))
/*  221 */       return getExternalVocabularies(); 
/*  222 */     if (name.equals("http://jvnet.org/fastinfoset/parser/properties/registered-encoding-algorithms"))
/*  223 */       return getRegisteredEncodingAlgorithms(); 
/*  224 */     if (name.equals("http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler"))
/*  225 */       return getEncodingAlgorithmContentHandler(); 
/*  226 */     if (name.equals("http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler")) {
/*  227 */       return getPrimitiveTypeContentHandler();
/*      */     }
/*  229 */     throw new SAXNotRecognizedException(CommonResourceBundle.getInstance().getString("message.propertyNotRecognized", new Object[] { name }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  236 */     if (name.equals("http://xml.org/sax/properties/lexical-handler")) {
/*  237 */       if (value instanceof LexicalHandler) {
/*  238 */         setLexicalHandler((LexicalHandler)value);
/*      */       } else {
/*  240 */         throw new SAXNotSupportedException("http://xml.org/sax/properties/lexical-handler");
/*      */       } 
/*  242 */     } else if (name.equals("http://xml.org/sax/properties/declaration-handler")) {
/*  243 */       if (value instanceof DeclHandler) {
/*  244 */         setDeclHandler((DeclHandler)value);
/*      */       } else {
/*  246 */         throw new SAXNotSupportedException("http://xml.org/sax/properties/lexical-handler");
/*      */       } 
/*  248 */     } else if (name.equals("http://jvnet.org/fastinfoset/parser/properties/external-vocabularies")) {
/*  249 */       if (value instanceof Map) {
/*  250 */         setExternalVocabularies((Map)value);
/*      */       } else {
/*  252 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/parser/properties/external-vocabularies");
/*      */       } 
/*  254 */     } else if (name.equals("http://jvnet.org/fastinfoset/parser/properties/registered-encoding-algorithms")) {
/*  255 */       if (value instanceof Map) {
/*  256 */         setRegisteredEncodingAlgorithms((Map)value);
/*      */       } else {
/*  258 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/parser/properties/registered-encoding-algorithms");
/*      */       } 
/*  260 */     } else if (name.equals("http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler")) {
/*  261 */       if (value instanceof EncodingAlgorithmContentHandler) {
/*  262 */         setEncodingAlgorithmContentHandler((EncodingAlgorithmContentHandler)value);
/*      */       } else {
/*  264 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler");
/*      */       } 
/*  266 */     } else if (name.equals("http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler")) {
/*  267 */       if (value instanceof PrimitiveTypeContentHandler) {
/*  268 */         setPrimitiveTypeContentHandler((PrimitiveTypeContentHandler)value);
/*      */       } else {
/*  270 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler");
/*      */       } 
/*  272 */     } else if (name.equals("http://jvnet.org/fastinfoset/parser/properties/buffer-size")) {
/*  273 */       if (value instanceof Integer) {
/*  274 */         setBufferSize(((Integer)value).intValue());
/*      */       } else {
/*  276 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/parser/properties/buffer-size");
/*      */       } 
/*      */     } else {
/*  279 */       throw new SAXNotRecognizedException(CommonResourceBundle.getInstance().getString("message.propertyNotRecognized", new Object[] { name }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityResolver(EntityResolver resolver) {
/*  285 */     this._entityResolver = resolver;
/*      */   }
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  289 */     return this._entityResolver;
/*      */   }
/*      */   
/*      */   public void setDTDHandler(DTDHandler handler) {
/*  293 */     this._dtdHandler = handler;
/*      */   }
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/*  297 */     return this._dtdHandler;
/*      */   }
/*      */   public void setContentHandler(ContentHandler handler) {
/*  300 */     this._contentHandler = handler;
/*      */   }
/*      */   
/*      */   public ContentHandler getContentHandler() {
/*  304 */     return this._contentHandler;
/*      */   }
/*      */   
/*      */   public void setErrorHandler(ErrorHandler handler) {
/*  308 */     this._errorHandler = handler;
/*      */   }
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*  312 */     return this._errorHandler;
/*      */   }
/*      */   
/*      */   public void parse(InputSource input) throws IOException, SAXException {
/*      */     try {
/*  317 */       InputStream s = input.getByteStream();
/*  318 */       if (s == null) {
/*  319 */         String systemId = input.getSystemId();
/*  320 */         if (systemId == null) {
/*  321 */           throw new SAXException(CommonResourceBundle.getInstance().getString("message.inputSource"));
/*      */         }
/*  323 */         parse(systemId);
/*      */       } else {
/*  325 */         parse(s);
/*      */       } 
/*  327 */     } catch (FastInfosetException e) {
/*  328 */       logger.log(Level.FINE, "parsing error", (Throwable)e);
/*  329 */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void parse(String systemId) throws IOException, SAXException {
/*      */     try {
/*  335 */       systemId = SystemIdResolver.getAbsoluteURI(systemId);
/*  336 */       parse((new URL(systemId)).openStream());
/*  337 */     } catch (FastInfosetException e) {
/*  338 */       logger.log(Level.FINE, "parsing error", (Throwable)e);
/*  339 */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void parse(InputStream s) throws IOException, FastInfosetException, SAXException {
/*  349 */     setInputStream(s);
/*  350 */     parse();
/*      */   }
/*      */   
/*      */   public void setLexicalHandler(LexicalHandler handler) {
/*  354 */     this._lexicalHandler = handler;
/*      */   }
/*      */   
/*      */   public LexicalHandler getLexicalHandler() {
/*  358 */     return this._lexicalHandler;
/*      */   }
/*      */   
/*      */   public void setDeclHandler(DeclHandler handler) {
/*  362 */     this._declHandler = handler;
/*      */   }
/*      */   
/*      */   public DeclHandler getDeclHandler() {
/*  366 */     return this._declHandler;
/*      */   }
/*      */   
/*      */   public void setEncodingAlgorithmContentHandler(EncodingAlgorithmContentHandler handler) {
/*  370 */     this._algorithmHandler = handler;
/*      */   }
/*      */   
/*      */   public EncodingAlgorithmContentHandler getEncodingAlgorithmContentHandler() {
/*  374 */     return this._algorithmHandler;
/*      */   }
/*      */   
/*      */   public void setPrimitiveTypeContentHandler(PrimitiveTypeContentHandler handler) {
/*  378 */     this._primitiveHandler = handler;
/*      */   }
/*      */   
/*      */   public PrimitiveTypeContentHandler getPrimitiveTypeContentHandler() {
/*  382 */     return this._primitiveHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void parse() throws FastInfosetException, IOException {
/*  389 */     if (this._octetBuffer.length < this._bufferSize) {
/*  390 */       this._octetBuffer = new byte[this._bufferSize];
/*      */     }
/*      */     
/*      */     try {
/*  394 */       reset();
/*  395 */       decodeHeader();
/*  396 */       if (this._parseFragments)
/*  397 */       { processDIIFragment(); }
/*      */       else
/*  399 */       { processDII(); } 
/*  400 */     } catch (RuntimeException e) {
/*      */       try {
/*  402 */         this._errorHandler.fatalError(new SAXParseException(e.getClass().getName(), null, e));
/*  403 */       } catch (Exception ee) {}
/*      */       
/*  405 */       resetOnError();
/*      */       
/*  407 */       throw new FastInfosetException(e);
/*  408 */     } catch (FastInfosetException e) {
/*      */       try {
/*  410 */         this._errorHandler.fatalError(new SAXParseException(e.getClass().getName(), null, (Exception)e));
/*  411 */       } catch (Exception ee) {}
/*      */       
/*  413 */       resetOnError();
/*  414 */       throw e;
/*  415 */     } catch (IOException e) {
/*      */       try {
/*  417 */         this._errorHandler.fatalError(new SAXParseException(e.getClass().getName(), null, e));
/*  418 */       } catch (Exception ee) {}
/*      */       
/*  420 */       resetOnError();
/*  421 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processDII() throws FastInfosetException, IOException {
/*      */     try {
/*  427 */       this._contentHandler.startDocument();
/*  428 */     } catch (SAXException e) {
/*  429 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */     
/*  432 */     this._b = read();
/*  433 */     if (this._b > 0) {
/*  434 */       processDIIOptionalProperties();
/*      */     }
/*      */ 
/*      */     
/*  438 */     boolean firstElementHasOccured = false;
/*  439 */     boolean documentTypeDeclarationOccured = false;
/*  440 */     while (!this._terminate || !firstElementHasOccured) {
/*  441 */       QualifiedName qn; String system_identifier, public_identifier; this._b = read();
/*  442 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 0:
/*  444 */           processEII(this._elementNameTable._array[this._b], false);
/*  445 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 1:
/*  448 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*  449 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 2:
/*  452 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*  453 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 3:
/*  456 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*  457 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 5:
/*  461 */           qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */           
/*  464 */           this._elementNameTable.add(qn);
/*  465 */           processEII(qn, ((this._b & 0x40) > 0));
/*  466 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 4:
/*  470 */           processEIIWithNamespaces();
/*  471 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 20:
/*  475 */           if (documentTypeDeclarationOccured) {
/*  476 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.secondOccurenceOfDTDII"));
/*      */           }
/*  478 */           documentTypeDeclarationOccured = true;
/*      */           
/*  480 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */           
/*  482 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */ 
/*      */           
/*  485 */           this._b = read();
/*  486 */           while (this._b == 225) {
/*  487 */             switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */               case 0:
/*  489 */                 if (this._addToTable) {
/*  490 */                   this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */                 }
/*      */                 break;
/*      */               case 2:
/*  494 */                 throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  500 */             this._b = read();
/*      */           } 
/*  502 */           if ((this._b & 0xF0) != 240) {
/*  503 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingInstructionIIsNotTerminatedCorrectly"));
/*      */           }
/*  505 */           if (this._b == 255) {
/*  506 */             this._terminate = true;
/*      */           }
/*      */           
/*  509 */           if (this._notations != null) this._notations.clear(); 
/*  510 */           if (this._unparsedEntities != null) this._unparsedEntities.clear();
/*      */           
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 18:
/*  518 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  521 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  524 */           this._doubleTerminate = true;
/*      */         case 22:
/*  526 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  529 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  534 */     while (!this._terminate) {
/*  535 */       this._b = read();
/*  536 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 18:
/*  538 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  541 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  544 */           this._doubleTerminate = true;
/*      */         case 22:
/*  546 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  549 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  554 */       this._contentHandler.endDocument();
/*  555 */     } catch (SAXException e) {
/*  556 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processDIIFragment() throws FastInfosetException, IOException {
/*      */     try {
/*  562 */       this._contentHandler.startDocument();
/*  563 */     } catch (SAXException e) {
/*  564 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */     
/*  567 */     this._b = read();
/*  568 */     if (this._b > 0) {
/*  569 */       processDIIOptionalProperties();
/*      */     }
/*      */     
/*  572 */     while (!this._terminate) {
/*  573 */       QualifiedName qn; boolean addToTable; int index; String entity_reference_name, system_identifier, public_identifier; this._b = read();
/*  574 */       switch (DecoderStateTables.EII(this._b)) {
/*      */         case 0:
/*  576 */           processEII(this._elementNameTable._array[this._b], false);
/*      */           continue;
/*      */         case 1:
/*  579 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*      */           continue;
/*      */         case 2:
/*  582 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         case 3:
/*  585 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 5:
/*  589 */           qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */           
/*  592 */           this._elementNameTable.add(qn);
/*  593 */           processEII(qn, ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 4:
/*  597 */           processEIIWithNamespaces();
/*      */           continue;
/*      */         case 6:
/*  600 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  602 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 7:
/*  605 */           this._octetBufferLength = read() + 3;
/*  606 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 8:
/*  609 */           this._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  614 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 9:
/*  617 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  619 */           decodeUtf16StringAsCharBuffer();
/*  620 */           if ((this._b & 0x10) > 0) {
/*  621 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  625 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  626 */           } catch (SAXException e) {
/*  627 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 10:
/*  631 */           this._octetBufferLength = read() + 3;
/*  632 */           decodeUtf16StringAsCharBuffer();
/*  633 */           if ((this._b & 0x10) > 0) {
/*  634 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  638 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  639 */           } catch (SAXException e) {
/*  640 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 11:
/*  644 */           this._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  649 */           decodeUtf16StringAsCharBuffer();
/*  650 */           if ((this._b & 0x10) > 0) {
/*  651 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  655 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  656 */           } catch (SAXException e) {
/*  657 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 12:
/*  662 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  665 */           this._identifier = (this._b & 0x2) << 6;
/*  666 */           this._b = read();
/*  667 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  669 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  671 */           decodeRestrictedAlphabetAsCharBuffer();
/*      */           
/*  673 */           if (addToTable) {
/*  674 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  678 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  679 */           } catch (SAXException e) {
/*  680 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 13:
/*  686 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  689 */           this._identifier = (this._b & 0x2) << 6;
/*  690 */           this._b = read();
/*  691 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  693 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  695 */           processCIIEncodingAlgorithm(addToTable);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/*  700 */           index = this._b & 0xF;
/*      */           try {
/*  702 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  705 */           catch (SAXException e) {
/*  706 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 15:
/*  712 */           index = ((this._b & 0x3) << 8 | read()) + 16;
/*      */           
/*      */           try {
/*  715 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  718 */           catch (SAXException e) {
/*  719 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 16:
/*  725 */           index = ((this._b & 0x3) << 16 | read() << 8 | read()) + 1040;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  731 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  734 */           catch (SAXException e) {
/*  735 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 17:
/*  741 */           index = (read() << 16 | read() << 8 | read()) + 263184;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  747 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  750 */           catch (SAXException e) {
/*  751 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 18:
/*  756 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  759 */           processProcessingII();
/*      */           continue;
/*      */         
/*      */         case 21:
/*  763 */           entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */           
/*  765 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */           
/*  767 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  778 */             this._contentHandler.skippedEntity(entity_reference_name);
/*  779 */           } catch (SAXException e) {
/*  780 */             throw new FastInfosetException("processUnexpandedEntityReferenceII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 23:
/*  785 */           this._doubleTerminate = true;
/*      */         case 22:
/*  787 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  790 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  795 */       this._contentHandler.endDocument();
/*  796 */     } catch (SAXException e) {
/*  797 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processDIIOptionalProperties() throws FastInfosetException, IOException {
/*  803 */     if (this._b == 32) {
/*  804 */       decodeInitialVocabulary();
/*      */       
/*      */       return;
/*      */     } 
/*  808 */     if ((this._b & 0x40) > 0) {
/*  809 */       decodeAdditionalData();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  816 */     if ((this._b & 0x20) > 0) {
/*  817 */       decodeInitialVocabulary();
/*      */     }
/*      */     
/*  820 */     if ((this._b & 0x10) > 0) {
/*  821 */       decodeNotations();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  831 */     if ((this._b & 0x8) > 0) {
/*  832 */       decodeUnparsedEntities();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  842 */     if ((this._b & 0x4) > 0) {
/*  843 */       String characterEncodingScheme = decodeCharacterEncodingScheme();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  850 */     if ((this._b & 0x2) > 0) {
/*  851 */       boolean standalone = (read() > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  858 */     if ((this._b & 0x1) > 0) {
/*  859 */       decodeVersion();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processEII(QualifiedName name, boolean hasAttributes) throws FastInfosetException, IOException {
/*  868 */     if (this._prefixTable._currentInScope[name.prefixIndex] != name.namespaceNameIndex) {
/*  869 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameOfEIINotInScope"));
/*      */     }
/*      */     
/*  872 */     if (hasAttributes) {
/*  873 */       processAIIs();
/*      */     }
/*      */     
/*      */     try {
/*  877 */       this._contentHandler.startElement(name.namespaceName, name.localName, name.qName, (Attributes)this._attributes);
/*  878 */     } catch (SAXException e) {
/*  879 */       logger.log(Level.FINE, "processEII error", e);
/*  880 */       throw new FastInfosetException("processEII", e);
/*      */     } 
/*      */     
/*  883 */     if (this._clearAttributes) {
/*  884 */       this._attributes.clear();
/*  885 */       this._clearAttributes = false;
/*      */     } 
/*      */     
/*  888 */     while (!this._terminate) {
/*  889 */       QualifiedName qn; boolean addToTable; int index; String entity_reference_name, system_identifier, public_identifier; this._b = read();
/*  890 */       switch (DecoderStateTables.EII(this._b)) {
/*      */         case 0:
/*  892 */           processEII(this._elementNameTable._array[this._b], false);
/*      */           continue;
/*      */         case 1:
/*  895 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*      */           continue;
/*      */         case 2:
/*  898 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         case 3:
/*  901 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 5:
/*  905 */           qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */           
/*  908 */           this._elementNameTable.add(qn);
/*  909 */           processEII(qn, ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 4:
/*  913 */           processEIIWithNamespaces();
/*      */           continue;
/*      */         case 6:
/*  916 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  918 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 7:
/*  921 */           this._octetBufferLength = read() + 3;
/*  922 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 8:
/*  925 */           this._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  930 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 9:
/*  933 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  935 */           decodeUtf16StringAsCharBuffer();
/*  936 */           if ((this._b & 0x10) > 0) {
/*  937 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  941 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  942 */           } catch (SAXException e) {
/*  943 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 10:
/*  947 */           this._octetBufferLength = read() + 3;
/*  948 */           decodeUtf16StringAsCharBuffer();
/*  949 */           if ((this._b & 0x10) > 0) {
/*  950 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  954 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  955 */           } catch (SAXException e) {
/*  956 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 11:
/*  960 */           this._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  965 */           decodeUtf16StringAsCharBuffer();
/*  966 */           if ((this._b & 0x10) > 0) {
/*  967 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  971 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  972 */           } catch (SAXException e) {
/*  973 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 12:
/*  978 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  981 */           this._identifier = (this._b & 0x2) << 6;
/*  982 */           this._b = read();
/*  983 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  985 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  987 */           decodeRestrictedAlphabetAsCharBuffer();
/*      */           
/*  989 */           if (addToTable) {
/*  990 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  994 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  995 */           } catch (SAXException e) {
/*  996 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 13:
/* 1002 */           addToTable = ((this._b & 0x10) > 0);
/*      */           
/* 1004 */           this._identifier = (this._b & 0x2) << 6;
/* 1005 */           this._b = read();
/* 1006 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/* 1008 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/* 1010 */           processCIIEncodingAlgorithm(addToTable);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/* 1015 */           index = this._b & 0xF;
/*      */           try {
/* 1017 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1020 */           catch (SAXException e) {
/* 1021 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 15:
/* 1027 */           index = ((this._b & 0x3) << 8 | read()) + 16;
/*      */           
/*      */           try {
/* 1030 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1033 */           catch (SAXException e) {
/* 1034 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 16:
/* 1040 */           index = ((this._b & 0x3) << 16 | read() << 8 | read()) + 1040;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1046 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1049 */           catch (SAXException e) {
/* 1050 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 17:
/* 1056 */           index = (read() << 16 | read() << 8 | read()) + 263184;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1062 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1065 */           catch (SAXException e) {
/* 1066 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 18:
/* 1071 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/* 1074 */           processProcessingII();
/*      */           continue;
/*      */         
/*      */         case 21:
/* 1078 */           entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */           
/* 1080 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */           
/* 1082 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1093 */             this._contentHandler.skippedEntity(entity_reference_name);
/* 1094 */           } catch (SAXException e) {
/* 1095 */             throw new FastInfosetException("processUnexpandedEntityReferenceII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 23:
/* 1100 */           this._doubleTerminate = true;
/*      */         case 22:
/* 1102 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/* 1105 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     } 
/*      */ 
/*      */     
/* 1109 */     this._terminate = this._doubleTerminate;
/* 1110 */     this._doubleTerminate = false;
/*      */     
/*      */     try {
/* 1113 */       this._contentHandler.endElement(name.namespaceName, name.localName, name.qName);
/* 1114 */     } catch (SAXException e) {
/* 1115 */       throw new FastInfosetException("processEII", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private final void processUtf8CharacterString() throws FastInfosetException, IOException {
/* 1120 */     if ((this._b & 0x10) > 0) {
/* 1121 */       this._characterContentChunkTable.ensureSize(this._octetBufferLength);
/* 1122 */       int charactersOffset = this._characterContentChunkTable._arrayIndex;
/* 1123 */       decodeUtf8StringAsCharBuffer(this._characterContentChunkTable._array, charactersOffset);
/* 1124 */       this._characterContentChunkTable.add(this._charBufferLength);
/*      */       try {
/* 1126 */         this._contentHandler.characters(this._characterContentChunkTable._array, charactersOffset, this._charBufferLength);
/* 1127 */       } catch (SAXException e) {
/* 1128 */         throw new FastInfosetException("processCII", e);
/*      */       } 
/*      */     } else {
/* 1131 */       decodeUtf8StringAsCharBuffer();
/*      */       try {
/* 1133 */         this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/* 1134 */       } catch (SAXException e) {
/* 1135 */         throw new FastInfosetException("processCII", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected final void processEIIWithNamespaces() throws FastInfosetException, IOException {
/*      */     QualifiedName qn;
/* 1141 */     boolean hasAttributes = ((this._b & 0x40) > 0);
/*      */     
/* 1143 */     this._clearAttributes = this._namespacePrefixesFeature;
/*      */     
/* 1145 */     if (++this._prefixTable._declarationId == Integer.MAX_VALUE) {
/* 1146 */       this._prefixTable.clearDeclarationIds();
/*      */     }
/*      */     
/* 1149 */     String prefix = "", namespaceName = "";
/* 1150 */     int start = this._namespacePrefixesIndex;
/* 1151 */     int b = read();
/* 1152 */     while ((b & 0xFC) == 204) {
/* 1153 */       if (this._namespacePrefixesIndex == this._namespacePrefixes.length) {
/* 1154 */         int[] namespaceAIIs = new int[this._namespacePrefixesIndex * 3 / 2 + 1];
/* 1155 */         System.arraycopy(this._namespacePrefixes, 0, namespaceAIIs, 0, this._namespacePrefixesIndex);
/* 1156 */         this._namespacePrefixes = namespaceAIIs;
/*      */       } 
/*      */       
/* 1159 */       switch (b & 0x3) {
/*      */ 
/*      */         
/*      */         case 0:
/* 1163 */           prefix = namespaceName = "";
/* 1164 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = -1; this._namespaceNameIndex = this._prefixIndex = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 1:
/* 1169 */           prefix = "";
/* 1170 */           namespaceName = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(false);
/*      */           
/* 1172 */           this._prefixIndex = this._namespacePrefixes[this._namespacePrefixesIndex++] = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1177 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(false);
/* 1178 */           namespaceName = "";
/*      */           
/* 1180 */           this._namespaceNameIndex = -1;
/* 1181 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/* 1186 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(true);
/* 1187 */           namespaceName = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(true);
/*      */           
/* 1189 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */       } 
/*      */       
/* 1193 */       this._prefixTable.pushScope(this._prefixIndex, this._namespaceNameIndex);
/*      */       
/* 1195 */       if (this._namespacePrefixesFeature)
/*      */       {
/* 1197 */         if (prefix != "") {
/* 1198 */           this._attributes.addAttribute(new QualifiedName("xmlns", "http://www.w3.org/2000/xmlns/", prefix), namespaceName);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1204 */           this._attributes.addAttribute(EncodingConstants.DEFAULT_NAMESPACE_DECLARATION, namespaceName);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 1210 */         this._contentHandler.startPrefixMapping(prefix, namespaceName);
/* 1211 */       } catch (SAXException e) {
/* 1212 */         throw new IOException("processStartNamespaceAII");
/*      */       } 
/*      */       
/* 1215 */       b = read();
/*      */     } 
/* 1217 */     if (b != 240) {
/* 1218 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.EIInamespaceNameNotTerminatedCorrectly"));
/*      */     }
/* 1220 */     int end = this._namespacePrefixesIndex;
/*      */     
/* 1222 */     this._b = read();
/* 1223 */     switch (DecoderStateTables.EII(this._b)) {
/*      */       case 0:
/* 1225 */         processEII(this._elementNameTable._array[this._b], hasAttributes);
/*      */         break;
/*      */       case 2:
/* 1228 */         processEII(decodeEIIIndexMedium(), hasAttributes);
/*      */         break;
/*      */       case 3:
/* 1231 */         processEII(decodeEIIIndexLarge(), hasAttributes);
/*      */         break;
/*      */       
/*      */       case 5:
/* 1235 */         qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */         
/* 1238 */         this._elementNameTable.add(qn);
/* 1239 */         processEII(qn, hasAttributes);
/*      */         break;
/*      */       
/*      */       default:
/* 1243 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEIIAfterAIIs"));
/*      */     } 
/*      */     
/*      */     try {
/* 1247 */       for (int i = end - 1; i >= start; i--) {
/* 1248 */         int prefixIndex = this._namespacePrefixes[i];
/* 1249 */         this._prefixTable.popScope(prefixIndex);
/* 1250 */         prefix = (prefixIndex > 0) ? this._prefixTable.get(prefixIndex - 1) : ((prefixIndex == -1) ? "" : "xml");
/*      */         
/* 1252 */         this._contentHandler.endPrefixMapping(prefix);
/*      */       } 
/* 1254 */       this._namespacePrefixesIndex = start;
/* 1255 */     } catch (SAXException e) {
/* 1256 */       throw new IOException("processStartNamespaceAII");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processAIIs() throws FastInfosetException, IOException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: iconst_1
/*      */     //   2: putfield _clearAttributes : Z
/*      */     //   5: aload_0
/*      */     //   6: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   9: dup
/*      */     //   10: getfield _currentIteration : I
/*      */     //   13: iconst_1
/*      */     //   14: iadd
/*      */     //   15: dup_x1
/*      */     //   16: putfield _currentIteration : I
/*      */     //   19: ldc 2147483647
/*      */     //   21: if_icmpne -> 31
/*      */     //   24: aload_0
/*      */     //   25: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   28: invokevirtual clear : ()V
/*      */     //   31: aload_0
/*      */     //   32: invokevirtual read : ()I
/*      */     //   35: istore_2
/*      */     //   36: iload_2
/*      */     //   37: invokestatic AII : (I)I
/*      */     //   40: tableswitch default -> 215, 0 -> 80, 1 -> 93, 2 -> 124, 3 -> 164, 4 -> 207, 5 -> 202
/*      */     //   80: aload_0
/*      */     //   81: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   84: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   87: iload_2
/*      */     //   88: aaload
/*      */     //   89: astore_1
/*      */     //   90: goto -> 231
/*      */     //   93: iload_2
/*      */     //   94: bipush #31
/*      */     //   96: iand
/*      */     //   97: bipush #8
/*      */     //   99: ishl
/*      */     //   100: aload_0
/*      */     //   101: invokevirtual read : ()I
/*      */     //   104: ior
/*      */     //   105: bipush #64
/*      */     //   107: iadd
/*      */     //   108: istore #4
/*      */     //   110: aload_0
/*      */     //   111: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   114: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   117: iload #4
/*      */     //   119: aaload
/*      */     //   120: astore_1
/*      */     //   121: goto -> 231
/*      */     //   124: iload_2
/*      */     //   125: bipush #15
/*      */     //   127: iand
/*      */     //   128: bipush #16
/*      */     //   130: ishl
/*      */     //   131: aload_0
/*      */     //   132: invokevirtual read : ()I
/*      */     //   135: bipush #8
/*      */     //   137: ishl
/*      */     //   138: ior
/*      */     //   139: aload_0
/*      */     //   140: invokevirtual read : ()I
/*      */     //   143: ior
/*      */     //   144: sipush #8256
/*      */     //   147: iadd
/*      */     //   148: istore #4
/*      */     //   150: aload_0
/*      */     //   151: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   154: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   157: iload #4
/*      */     //   159: aaload
/*      */     //   160: astore_1
/*      */     //   161: goto -> 231
/*      */     //   164: aload_0
/*      */     //   165: iload_2
/*      */     //   166: iconst_3
/*      */     //   167: iand
/*      */     //   168: aload_0
/*      */     //   169: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   172: invokevirtual getNext : ()Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   175: invokevirtual decodeLiteralQualifiedName : (ILcom/sun/xml/fastinfoset/QualifiedName;)Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   178: astore_1
/*      */     //   179: aload_1
/*      */     //   180: aload_0
/*      */     //   181: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   184: pop
/*      */     //   185: sipush #256
/*      */     //   188: invokevirtual createAttributeValues : (I)V
/*      */     //   191: aload_0
/*      */     //   192: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   195: aload_1
/*      */     //   196: invokevirtual add : (Lcom/sun/xml/fastinfoset/QualifiedName;)V
/*      */     //   199: goto -> 231
/*      */     //   202: aload_0
/*      */     //   203: iconst_1
/*      */     //   204: putfield _doubleTerminate : Z
/*      */     //   207: aload_0
/*      */     //   208: iconst_1
/*      */     //   209: putfield _terminate : Z
/*      */     //   212: goto -> 950
/*      */     //   215: new java/io/IOException
/*      */     //   218: dup
/*      */     //   219: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   222: ldc 'message.decodingAIIs'
/*      */     //   224: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   227: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   230: athrow
/*      */     //   231: aload_1
/*      */     //   232: getfield prefixIndex : I
/*      */     //   235: ifle -> 273
/*      */     //   238: aload_0
/*      */     //   239: getfield _prefixTable : Lcom/sun/xml/fastinfoset/util/PrefixArray;
/*      */     //   242: getfield _currentInScope : [I
/*      */     //   245: aload_1
/*      */     //   246: getfield prefixIndex : I
/*      */     //   249: iaload
/*      */     //   250: aload_1
/*      */     //   251: getfield namespaceNameIndex : I
/*      */     //   254: if_icmpeq -> 273
/*      */     //   257: new org/jvnet/fastinfoset/FastInfosetException
/*      */     //   260: dup
/*      */     //   261: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   264: ldc 'message.AIIqNameNotInScope'
/*      */     //   266: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   269: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   272: athrow
/*      */     //   273: aload_0
/*      */     //   274: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   277: aload_1
/*      */     //   278: getfield attributeHash : I
/*      */     //   281: aload_1
/*      */     //   282: getfield attributeId : I
/*      */     //   285: invokevirtual checkForDuplicateAttribute : (II)V
/*      */     //   288: aload_0
/*      */     //   289: invokevirtual read : ()I
/*      */     //   292: istore_2
/*      */     //   293: iload_2
/*      */     //   294: invokestatic NISTRING : (I)I
/*      */     //   297: tableswitch default -> 934, 0 -> 360, 1 -> 403, 2 -> 447, 3 -> 516, 4 -> 559, 5 -> 603, 6 -> 672, 7 -> 753, 8 -> 813, 9 -> 836, 10 -> 874, 11 -> 921
/*      */     //   360: aload_0
/*      */     //   361: iload_2
/*      */     //   362: bipush #7
/*      */     //   364: iand
/*      */     //   365: iconst_1
/*      */     //   366: iadd
/*      */     //   367: putfield _octetBufferLength : I
/*      */     //   370: aload_0
/*      */     //   371: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   374: astore_3
/*      */     //   375: iload_2
/*      */     //   376: bipush #64
/*      */     //   378: iand
/*      */     //   379: ifle -> 391
/*      */     //   382: aload_0
/*      */     //   383: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   386: aload_3
/*      */     //   387: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   390: pop
/*      */     //   391: aload_0
/*      */     //   392: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   395: aload_1
/*      */     //   396: aload_3
/*      */     //   397: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   400: goto -> 950
/*      */     //   403: aload_0
/*      */     //   404: aload_0
/*      */     //   405: invokevirtual read : ()I
/*      */     //   408: bipush #9
/*      */     //   410: iadd
/*      */     //   411: putfield _octetBufferLength : I
/*      */     //   414: aload_0
/*      */     //   415: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   418: astore_3
/*      */     //   419: iload_2
/*      */     //   420: bipush #64
/*      */     //   422: iand
/*      */     //   423: ifle -> 435
/*      */     //   426: aload_0
/*      */     //   427: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   430: aload_3
/*      */     //   431: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   434: pop
/*      */     //   435: aload_0
/*      */     //   436: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   439: aload_1
/*      */     //   440: aload_3
/*      */     //   441: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   444: goto -> 950
/*      */     //   447: aload_0
/*      */     //   448: aload_0
/*      */     //   449: invokevirtual read : ()I
/*      */     //   452: bipush #24
/*      */     //   454: ishl
/*      */     //   455: aload_0
/*      */     //   456: invokevirtual read : ()I
/*      */     //   459: bipush #16
/*      */     //   461: ishl
/*      */     //   462: ior
/*      */     //   463: aload_0
/*      */     //   464: invokevirtual read : ()I
/*      */     //   467: bipush #8
/*      */     //   469: ishl
/*      */     //   470: ior
/*      */     //   471: aload_0
/*      */     //   472: invokevirtual read : ()I
/*      */     //   475: ior
/*      */     //   476: sipush #265
/*      */     //   479: iadd
/*      */     //   480: putfield _octetBufferLength : I
/*      */     //   483: aload_0
/*      */     //   484: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   487: astore_3
/*      */     //   488: iload_2
/*      */     //   489: bipush #64
/*      */     //   491: iand
/*      */     //   492: ifle -> 504
/*      */     //   495: aload_0
/*      */     //   496: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   499: aload_3
/*      */     //   500: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   503: pop
/*      */     //   504: aload_0
/*      */     //   505: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   508: aload_1
/*      */     //   509: aload_3
/*      */     //   510: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   513: goto -> 950
/*      */     //   516: aload_0
/*      */     //   517: iload_2
/*      */     //   518: bipush #7
/*      */     //   520: iand
/*      */     //   521: iconst_1
/*      */     //   522: iadd
/*      */     //   523: putfield _octetBufferLength : I
/*      */     //   526: aload_0
/*      */     //   527: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   530: astore_3
/*      */     //   531: iload_2
/*      */     //   532: bipush #64
/*      */     //   534: iand
/*      */     //   535: ifle -> 547
/*      */     //   538: aload_0
/*      */     //   539: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   542: aload_3
/*      */     //   543: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   546: pop
/*      */     //   547: aload_0
/*      */     //   548: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   551: aload_1
/*      */     //   552: aload_3
/*      */     //   553: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   556: goto -> 950
/*      */     //   559: aload_0
/*      */     //   560: aload_0
/*      */     //   561: invokevirtual read : ()I
/*      */     //   564: bipush #9
/*      */     //   566: iadd
/*      */     //   567: putfield _octetBufferLength : I
/*      */     //   570: aload_0
/*      */     //   571: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   574: astore_3
/*      */     //   575: iload_2
/*      */     //   576: bipush #64
/*      */     //   578: iand
/*      */     //   579: ifle -> 591
/*      */     //   582: aload_0
/*      */     //   583: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   586: aload_3
/*      */     //   587: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   590: pop
/*      */     //   591: aload_0
/*      */     //   592: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   595: aload_1
/*      */     //   596: aload_3
/*      */     //   597: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   600: goto -> 950
/*      */     //   603: aload_0
/*      */     //   604: aload_0
/*      */     //   605: invokevirtual read : ()I
/*      */     //   608: bipush #24
/*      */     //   610: ishl
/*      */     //   611: aload_0
/*      */     //   612: invokevirtual read : ()I
/*      */     //   615: bipush #16
/*      */     //   617: ishl
/*      */     //   618: ior
/*      */     //   619: aload_0
/*      */     //   620: invokevirtual read : ()I
/*      */     //   623: bipush #8
/*      */     //   625: ishl
/*      */     //   626: ior
/*      */     //   627: aload_0
/*      */     //   628: invokevirtual read : ()I
/*      */     //   631: ior
/*      */     //   632: sipush #265
/*      */     //   635: iadd
/*      */     //   636: putfield _octetBufferLength : I
/*      */     //   639: aload_0
/*      */     //   640: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   643: astore_3
/*      */     //   644: iload_2
/*      */     //   645: bipush #64
/*      */     //   647: iand
/*      */     //   648: ifle -> 660
/*      */     //   651: aload_0
/*      */     //   652: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   655: aload_3
/*      */     //   656: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   659: pop
/*      */     //   660: aload_0
/*      */     //   661: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   664: aload_1
/*      */     //   665: aload_3
/*      */     //   666: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   669: goto -> 950
/*      */     //   672: iload_2
/*      */     //   673: bipush #64
/*      */     //   675: iand
/*      */     //   676: ifle -> 683
/*      */     //   679: iconst_1
/*      */     //   680: goto -> 684
/*      */     //   683: iconst_0
/*      */     //   684: istore #4
/*      */     //   686: aload_0
/*      */     //   687: iload_2
/*      */     //   688: bipush #15
/*      */     //   690: iand
/*      */     //   691: iconst_4
/*      */     //   692: ishl
/*      */     //   693: putfield _identifier : I
/*      */     //   696: aload_0
/*      */     //   697: invokevirtual read : ()I
/*      */     //   700: istore_2
/*      */     //   701: aload_0
/*      */     //   702: dup
/*      */     //   703: getfield _identifier : I
/*      */     //   706: iload_2
/*      */     //   707: sipush #240
/*      */     //   710: iand
/*      */     //   711: iconst_4
/*      */     //   712: ishr
/*      */     //   713: ior
/*      */     //   714: putfield _identifier : I
/*      */     //   717: aload_0
/*      */     //   718: iload_2
/*      */     //   719: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   722: aload_0
/*      */     //   723: invokevirtual decodeRestrictedAlphabetAsString : ()Ljava/lang/String;
/*      */     //   726: astore_3
/*      */     //   727: iload #4
/*      */     //   729: ifeq -> 741
/*      */     //   732: aload_0
/*      */     //   733: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   736: aload_3
/*      */     //   737: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   740: pop
/*      */     //   741: aload_0
/*      */     //   742: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   745: aload_1
/*      */     //   746: aload_3
/*      */     //   747: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   750: goto -> 950
/*      */     //   753: iload_2
/*      */     //   754: bipush #64
/*      */     //   756: iand
/*      */     //   757: ifle -> 764
/*      */     //   760: iconst_1
/*      */     //   761: goto -> 765
/*      */     //   764: iconst_0
/*      */     //   765: istore #4
/*      */     //   767: aload_0
/*      */     //   768: iload_2
/*      */     //   769: bipush #15
/*      */     //   771: iand
/*      */     //   772: iconst_4
/*      */     //   773: ishl
/*      */     //   774: putfield _identifier : I
/*      */     //   777: aload_0
/*      */     //   778: invokevirtual read : ()I
/*      */     //   781: istore_2
/*      */     //   782: aload_0
/*      */     //   783: dup
/*      */     //   784: getfield _identifier : I
/*      */     //   787: iload_2
/*      */     //   788: sipush #240
/*      */     //   791: iand
/*      */     //   792: iconst_4
/*      */     //   793: ishr
/*      */     //   794: ior
/*      */     //   795: putfield _identifier : I
/*      */     //   798: aload_0
/*      */     //   799: iload_2
/*      */     //   800: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   803: aload_0
/*      */     //   804: aload_1
/*      */     //   805: iload #4
/*      */     //   807: invokevirtual processAIIEncodingAlgorithm : (Lcom/sun/xml/fastinfoset/QualifiedName;Z)V
/*      */     //   810: goto -> 950
/*      */     //   813: aload_0
/*      */     //   814: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   817: aload_1
/*      */     //   818: aload_0
/*      */     //   819: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   822: getfield _array : [Ljava/lang/String;
/*      */     //   825: iload_2
/*      */     //   826: bipush #63
/*      */     //   828: iand
/*      */     //   829: aaload
/*      */     //   830: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   833: goto -> 950
/*      */     //   836: iload_2
/*      */     //   837: bipush #31
/*      */     //   839: iand
/*      */     //   840: bipush #8
/*      */     //   842: ishl
/*      */     //   843: aload_0
/*      */     //   844: invokevirtual read : ()I
/*      */     //   847: ior
/*      */     //   848: bipush #64
/*      */     //   850: iadd
/*      */     //   851: istore #4
/*      */     //   853: aload_0
/*      */     //   854: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   857: aload_1
/*      */     //   858: aload_0
/*      */     //   859: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   862: getfield _array : [Ljava/lang/String;
/*      */     //   865: iload #4
/*      */     //   867: aaload
/*      */     //   868: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   871: goto -> 950
/*      */     //   874: iload_2
/*      */     //   875: bipush #15
/*      */     //   877: iand
/*      */     //   878: bipush #16
/*      */     //   880: ishl
/*      */     //   881: aload_0
/*      */     //   882: invokevirtual read : ()I
/*      */     //   885: bipush #8
/*      */     //   887: ishl
/*      */     //   888: ior
/*      */     //   889: aload_0
/*      */     //   890: invokevirtual read : ()I
/*      */     //   893: ior
/*      */     //   894: sipush #8256
/*      */     //   897: iadd
/*      */     //   898: istore #4
/*      */     //   900: aload_0
/*      */     //   901: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   904: aload_1
/*      */     //   905: aload_0
/*      */     //   906: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   909: getfield _array : [Ljava/lang/String;
/*      */     //   912: iload #4
/*      */     //   914: aaload
/*      */     //   915: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   918: goto -> 950
/*      */     //   921: aload_0
/*      */     //   922: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   925: aload_1
/*      */     //   926: ldc ''
/*      */     //   928: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   931: goto -> 950
/*      */     //   934: new java/io/IOException
/*      */     //   937: dup
/*      */     //   938: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   941: ldc 'message.decodingAIIValue'
/*      */     //   943: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   946: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   949: athrow
/*      */     //   950: aload_0
/*      */     //   951: getfield _terminate : Z
/*      */     //   954: ifeq -> 31
/*      */     //   957: aload_0
/*      */     //   958: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   961: aload_0
/*      */     //   962: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   965: getfield _poolHead : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   968: putfield _poolCurrent : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   971: aload_0
/*      */     //   972: aload_0
/*      */     //   973: getfield _doubleTerminate : Z
/*      */     //   976: putfield _terminate : Z
/*      */     //   979: aload_0
/*      */     //   980: iconst_0
/*      */     //   981: putfield _doubleTerminate : Z
/*      */     //   984: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1265	-> 0
/*      */     //   #1267	-> 5
/*      */     //   #1268	-> 24
/*      */     //   #1273	-> 31
/*      */     //   #1274	-> 36
/*      */     //   #1276	-> 80
/*      */     //   #1277	-> 90
/*      */     //   #1280	-> 93
/*      */     //   #1282	-> 110
/*      */     //   #1283	-> 121
/*      */     //   #1287	-> 124
/*      */     //   #1289	-> 150
/*      */     //   #1290	-> 161
/*      */     //   #1293	-> 164
/*      */     //   #1296	-> 179
/*      */     //   #1297	-> 191
/*      */     //   #1298	-> 199
/*      */     //   #1300	-> 202
/*      */     //   #1302	-> 207
/*      */     //   #1304	-> 212
/*      */     //   #1306	-> 215
/*      */     //   #1309	-> 231
/*      */     //   #1310	-> 257
/*      */     //   #1313	-> 273
/*      */     //   #1317	-> 288
/*      */     //   #1318	-> 293
/*      */     //   #1320	-> 360
/*      */     //   #1321	-> 370
/*      */     //   #1322	-> 375
/*      */     //   #1323	-> 382
/*      */     //   #1326	-> 391
/*      */     //   #1327	-> 400
/*      */     //   #1329	-> 403
/*      */     //   #1330	-> 414
/*      */     //   #1331	-> 419
/*      */     //   #1332	-> 426
/*      */     //   #1335	-> 435
/*      */     //   #1336	-> 444
/*      */     //   #1338	-> 447
/*      */     //   #1343	-> 483
/*      */     //   #1344	-> 488
/*      */     //   #1345	-> 495
/*      */     //   #1348	-> 504
/*      */     //   #1349	-> 513
/*      */     //   #1351	-> 516
/*      */     //   #1352	-> 526
/*      */     //   #1353	-> 531
/*      */     //   #1354	-> 538
/*      */     //   #1357	-> 547
/*      */     //   #1358	-> 556
/*      */     //   #1360	-> 559
/*      */     //   #1361	-> 570
/*      */     //   #1362	-> 575
/*      */     //   #1363	-> 582
/*      */     //   #1366	-> 591
/*      */     //   #1367	-> 600
/*      */     //   #1369	-> 603
/*      */     //   #1374	-> 639
/*      */     //   #1375	-> 644
/*      */     //   #1376	-> 651
/*      */     //   #1379	-> 660
/*      */     //   #1380	-> 669
/*      */     //   #1383	-> 672
/*      */     //   #1385	-> 686
/*      */     //   #1386	-> 696
/*      */     //   #1387	-> 701
/*      */     //   #1389	-> 717
/*      */     //   #1391	-> 722
/*      */     //   #1392	-> 727
/*      */     //   #1393	-> 732
/*      */     //   #1396	-> 741
/*      */     //   #1397	-> 750
/*      */     //   #1401	-> 753
/*      */     //   #1403	-> 767
/*      */     //   #1404	-> 777
/*      */     //   #1405	-> 782
/*      */     //   #1407	-> 798
/*      */     //   #1409	-> 803
/*      */     //   #1410	-> 810
/*      */     //   #1413	-> 813
/*      */     //   #1415	-> 833
/*      */     //   #1418	-> 836
/*      */     //   #1421	-> 853
/*      */     //   #1423	-> 871
/*      */     //   #1427	-> 874
/*      */     //   #1430	-> 900
/*      */     //   #1432	-> 918
/*      */     //   #1435	-> 921
/*      */     //   #1436	-> 931
/*      */     //   #1438	-> 934
/*      */     //   #1441	-> 950
/*      */     //   #1444	-> 957
/*      */     //   #1446	-> 971
/*      */     //   #1447	-> 979
/*      */     //   #1448	-> 984
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   110	14	4	i	I
/*      */     //   150	14	4	i	I
/*      */     //   686	67	4	addToTable	Z
/*      */     //   767	46	4	addToTable	Z
/*      */     //   853	21	4	index	I
/*      */     //   900	21	4	index	I
/*      */     //   90	860	1	name	Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   0	985	0	this	Lcom/sun/xml/fastinfoset/sax/SAXDocumentParser;
/*      */     //   36	949	2	b	I
/*      */     //   375	610	3	value	Ljava/lang/String;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processCommentII() throws FastInfosetException, IOException {
/*      */     CharArray ca;
/* 1451 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1453 */         if (this._addToTable) {
/* 1454 */           this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */         }
/*      */         
/*      */         try {
/* 1458 */           this._lexicalHandler.comment(this._charBuffer, 0, this._charBufferLength);
/* 1459 */         } catch (SAXException e) {
/* 1460 */           throw new FastInfosetException("processCommentII", e);
/*      */         } 
/*      */         break;
/*      */       case 2:
/* 1464 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.commentIIAlgorithmNotSupported"));
/*      */       case 1:
/* 1466 */         ca = this._v.otherString.get(this._integer);
/*      */         
/*      */         try {
/* 1469 */           this._lexicalHandler.comment(ca.ch, ca.start, ca.length);
/* 1470 */         } catch (SAXException e) {
/* 1471 */           throw new FastInfosetException("processCommentII", e);
/*      */         } 
/*      */         break;
/*      */       case 3:
/*      */         try {
/* 1476 */           this._lexicalHandler.comment(this._charBuffer, 0, 0);
/* 1477 */         } catch (SAXException e) {
/* 1478 */           throw new FastInfosetException("processCommentII", e);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processProcessingII() throws FastInfosetException, IOException {
/* 1485 */     String data, target = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */     
/* 1487 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1489 */         data = new String(this._charBuffer, 0, this._charBufferLength);
/* 1490 */         if (this._addToTable) {
/* 1491 */           this._v.otherString.add((CharArray)new CharArrayString(data));
/*      */         }
/*      */         try {
/* 1494 */           this._contentHandler.processingInstruction(target, data);
/* 1495 */         } catch (SAXException e) {
/* 1496 */           throw new FastInfosetException("processProcessingII", e);
/*      */         } 
/*      */         break;
/*      */       case 2:
/* 1500 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */       case 1:
/*      */         try {
/* 1503 */           this._contentHandler.processingInstruction(target, this._v.otherString.get(this._integer).toString());
/* 1504 */         } catch (SAXException e) {
/* 1505 */           throw new FastInfosetException("processProcessingII", e);
/*      */         } 
/*      */         break;
/*      */       case 3:
/*      */         try {
/* 1510 */           this._contentHandler.processingInstruction(target, "");
/* 1511 */         } catch (SAXException e) {
/* 1512 */           throw new FastInfosetException("processProcessingII", e);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processCIIEncodingAlgorithm(boolean addToTable) throws FastInfosetException, IOException {
/* 1519 */     if (this._identifier < 9) {
/* 1520 */       if (this._primitiveHandler != null) {
/* 1521 */         processCIIBuiltInEncodingAlgorithmAsPrimitive();
/* 1522 */       } else if (this._algorithmHandler != null) {
/* 1523 */         Object array = processBuiltInEncodingAlgorithmAsObject();
/*      */         
/*      */         try {
/* 1526 */           this._algorithmHandler.object(null, this._identifier, array);
/* 1527 */         } catch (SAXException e) {
/* 1528 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } else {
/* 1531 */         StringBuffer buffer = new StringBuffer();
/* 1532 */         processBuiltInEncodingAlgorithmAsCharacters(buffer);
/*      */         
/*      */         try {
/* 1535 */           this._contentHandler.characters(buffer.toString().toCharArray(), 0, buffer.length());
/* 1536 */         } catch (SAXException e) {
/* 1537 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } 
/*      */       
/* 1541 */       if (addToTable) {
/* 1542 */         StringBuffer buffer = new StringBuffer();
/* 1543 */         processBuiltInEncodingAlgorithmAsCharacters(buffer);
/* 1544 */         this._characterContentChunkTable.add(buffer.toString().toCharArray(), buffer.length());
/*      */       } 
/* 1546 */     } else if (this._identifier == 9) {
/*      */       
/* 1548 */       this._octetBufferOffset -= this._octetBufferLength;
/* 1549 */       decodeUtf8StringIntoCharBuffer();
/*      */       
/*      */       try {
/* 1552 */         this._lexicalHandler.startCDATA();
/* 1553 */         this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/* 1554 */         this._lexicalHandler.endCDATA();
/* 1555 */       } catch (SAXException e) {
/* 1556 */         throw new FastInfosetException(e);
/*      */       } 
/*      */       
/* 1559 */       if (addToTable) {
/* 1560 */         this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */       }
/* 1562 */     } else if (this._identifier >= 32 && this._algorithmHandler != null) {
/* 1563 */       String URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1564 */       if (URI == null) {
/* 1565 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) }));
/*      */       }
/*      */ 
/*      */       
/* 1569 */       EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1570 */       if (ea != null) {
/* 1571 */         Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */         try {
/* 1573 */           this._algorithmHandler.object(URI, this._identifier, data);
/* 1574 */         } catch (SAXException e) {
/* 1575 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } else {
/*      */         try {
/* 1579 */           this._algorithmHandler.octets(URI, this._identifier, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1580 */         } catch (SAXException e) {
/* 1581 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } 
/* 1584 */       if (addToTable)
/* 1585 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.addToTableNotSupported")); 
/*      */     } else {
/* 1587 */       if (this._identifier >= 32)
/*      */       {
/* 1589 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported"));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1595 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processCIIBuiltInEncodingAlgorithmAsPrimitive() throws FastInfosetException, IOException {
/*      */     try {
/*      */       int length;
/* 1602 */       switch (this._identifier) {
/*      */         case 0:
/*      */         case 1:
/* 1605 */           this._primitiveHandler.bytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           return;
/*      */         case 2:
/* 1608 */           length = BuiltInEncodingAlgorithmFactory.shortEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/*      */           
/* 1610 */           if (length > this.builtInAlgorithmState.shortArray.length) {
/* 1611 */             short[] array = new short[length * 3 / 2 + 1];
/* 1612 */             System.arraycopy(this.builtInAlgorithmState.shortArray, 0, array, 0, this.builtInAlgorithmState.shortArray.length);
/*      */             
/* 1614 */             this.builtInAlgorithmState.shortArray = array;
/*      */           } 
/*      */           
/* 1617 */           BuiltInEncodingAlgorithmFactory.shortEncodingAlgorithm.decodeFromBytesToShortArray(this.builtInAlgorithmState.shortArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */           
/* 1620 */           this._primitiveHandler.shorts(this.builtInAlgorithmState.shortArray, 0, length);
/*      */           return;
/*      */         case 3:
/* 1623 */           length = BuiltInEncodingAlgorithmFactory.intEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/*      */           
/* 1625 */           if (length > this.builtInAlgorithmState.intArray.length) {
/* 1626 */             int[] array = new int[length * 3 / 2 + 1];
/* 1627 */             System.arraycopy(this.builtInAlgorithmState.intArray, 0, array, 0, this.builtInAlgorithmState.intArray.length);
/*      */             
/* 1629 */             this.builtInAlgorithmState.intArray = array;
/*      */           } 
/*      */           
/* 1632 */           BuiltInEncodingAlgorithmFactory.intEncodingAlgorithm.decodeFromBytesToIntArray(this.builtInAlgorithmState.intArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */           
/* 1635 */           this._primitiveHandler.ints(this.builtInAlgorithmState.intArray, 0, length);
/*      */           return;
/*      */         case 4:
/* 1638 */           length = BuiltInEncodingAlgorithmFactory.longEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/*      */           
/* 1640 */           if (length > this.builtInAlgorithmState.longArray.length) {
/* 1641 */             long[] array = new long[length * 3 / 2 + 1];
/* 1642 */             System.arraycopy(this.builtInAlgorithmState.longArray, 0, array, 0, this.builtInAlgorithmState.longArray.length);
/*      */             
/* 1644 */             this.builtInAlgorithmState.longArray = array;
/*      */           } 
/*      */           
/* 1647 */           BuiltInEncodingAlgorithmFactory.longEncodingAlgorithm.decodeFromBytesToLongArray(this.builtInAlgorithmState.longArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */           
/* 1650 */           this._primitiveHandler.longs(this.builtInAlgorithmState.longArray, 0, length);
/*      */           return;
/*      */         case 5:
/* 1653 */           length = BuiltInEncodingAlgorithmFactory.booleanEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength, this._octetBuffer[this._octetBufferStart] & 0xFF);
/*      */           
/* 1655 */           if (length > this.builtInAlgorithmState.booleanArray.length) {
/* 1656 */             boolean[] array = new boolean[length * 3 / 2 + 1];
/* 1657 */             System.arraycopy(this.builtInAlgorithmState.booleanArray, 0, array, 0, this.builtInAlgorithmState.booleanArray.length);
/*      */             
/* 1659 */             this.builtInAlgorithmState.booleanArray = array;
/*      */           } 
/*      */           
/* 1662 */           BuiltInEncodingAlgorithmFactory.booleanEncodingAlgorithm.decodeFromBytesToBooleanArray(this.builtInAlgorithmState.booleanArray, 0, length, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */ 
/*      */           
/* 1666 */           this._primitiveHandler.booleans(this.builtInAlgorithmState.booleanArray, 0, length);
/*      */           return;
/*      */         case 6:
/* 1669 */           length = BuiltInEncodingAlgorithmFactory.floatEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/*      */           
/* 1671 */           if (length > this.builtInAlgorithmState.floatArray.length) {
/* 1672 */             float[] array = new float[length * 3 / 2 + 1];
/* 1673 */             System.arraycopy(this.builtInAlgorithmState.floatArray, 0, array, 0, this.builtInAlgorithmState.floatArray.length);
/*      */             
/* 1675 */             this.builtInAlgorithmState.floatArray = array;
/*      */           } 
/*      */           
/* 1678 */           BuiltInEncodingAlgorithmFactory.floatEncodingAlgorithm.decodeFromBytesToFloatArray(this.builtInAlgorithmState.floatArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */           
/* 1681 */           this._primitiveHandler.floats(this.builtInAlgorithmState.floatArray, 0, length);
/*      */           return;
/*      */         case 7:
/* 1684 */           length = BuiltInEncodingAlgorithmFactory.doubleEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/*      */           
/* 1686 */           if (length > this.builtInAlgorithmState.doubleArray.length) {
/* 1687 */             double[] array = new double[length * 3 / 2 + 1];
/* 1688 */             System.arraycopy(this.builtInAlgorithmState.doubleArray, 0, array, 0, this.builtInAlgorithmState.doubleArray.length);
/*      */             
/* 1690 */             this.builtInAlgorithmState.doubleArray = array;
/*      */           } 
/*      */           
/* 1693 */           BuiltInEncodingAlgorithmFactory.doubleEncodingAlgorithm.decodeFromBytesToDoubleArray(this.builtInAlgorithmState.doubleArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */           
/* 1696 */           this._primitiveHandler.doubles(this.builtInAlgorithmState.doubleArray, 0, length);
/*      */           return;
/*      */         case 8:
/* 1699 */           length = BuiltInEncodingAlgorithmFactory.uuidEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/*      */           
/* 1701 */           if (length > this.builtInAlgorithmState.longArray.length) {
/* 1702 */             long[] array = new long[length * 3 / 2 + 1];
/* 1703 */             System.arraycopy(this.builtInAlgorithmState.longArray, 0, array, 0, this.builtInAlgorithmState.longArray.length);
/*      */             
/* 1705 */             this.builtInAlgorithmState.longArray = array;
/*      */           } 
/*      */           
/* 1708 */           BuiltInEncodingAlgorithmFactory.uuidEncodingAlgorithm.decodeFromBytesToLongArray(this.builtInAlgorithmState.longArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */           
/* 1711 */           this._primitiveHandler.uuids(this.builtInAlgorithmState.longArray, 0, length);
/*      */           return;
/*      */         case 9:
/* 1714 */           throw new UnsupportedOperationException("CDATA");
/*      */       } 
/* 1716 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.unsupportedAlgorithm", new Object[] { Integer.valueOf(this._identifier) }));
/*      */     
/*      */     }
/* 1719 */     catch (SAXException e) {
/* 1720 */       throw new FastInfosetException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processAIIEncodingAlgorithm(QualifiedName name, boolean addToTable) throws FastInfosetException, IOException {
/* 1726 */     if (this._identifier < 9)
/* 1727 */     { if (this._primitiveHandler != null || this._algorithmHandler != null) {
/* 1728 */         Object data = processBuiltInEncodingAlgorithmAsObject();
/* 1729 */         this._attributes.addAttributeWithAlgorithmData(name, null, this._identifier, data);
/*      */       } else {
/* 1731 */         StringBuffer buffer = new StringBuffer();
/* 1732 */         processBuiltInEncodingAlgorithmAsCharacters(buffer);
/* 1733 */         this._attributes.addAttribute(name, buffer.toString());
/*      */       }  }
/* 1735 */     else if (this._identifier >= 32 && this._algorithmHandler != null)
/* 1736 */     { String URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1737 */       if (URI == null) {
/* 1738 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) }));
/*      */       }
/*      */ 
/*      */       
/* 1742 */       EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1743 */       if (ea != null) {
/* 1744 */         Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1745 */         this._attributes.addAttributeWithAlgorithmData(name, URI, this._identifier, data);
/*      */       } else {
/* 1747 */         byte[] data = new byte[this._octetBufferLength];
/* 1748 */         System.arraycopy(this._octetBuffer, this._octetBufferStart, data, 0, this._octetBufferLength);
/* 1749 */         this._attributes.addAttributeWithAlgorithmData(name, URI, this._identifier, data);
/*      */       }  }
/* 1751 */     else { if (this._identifier >= 32)
/*      */       {
/* 1753 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported"));
/*      */       }
/* 1755 */       if (this._identifier == 9) {
/* 1756 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported"));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1761 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved")); }
/*      */ 
/*      */     
/* 1764 */     if (addToTable) {
/* 1765 */       this._attributeValueTable.add(this._attributes.getValue(this._attributes.getIndex(name.qName)));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processBuiltInEncodingAlgorithmAsCharacters(StringBuffer buffer) throws FastInfosetException, IOException {
/* 1771 */     Object array = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */     
/* 1774 */     BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).convertToCharacters(array, buffer);
/*      */   }
/*      */   
/*      */   protected final Object processBuiltInEncodingAlgorithmAsObject() throws FastInfosetException, IOException {
/* 1778 */     return BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\sax\SAXDocumentParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
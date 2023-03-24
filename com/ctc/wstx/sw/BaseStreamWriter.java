/*      */ package com.ctc.wstx.sw;
/*      */ 
/*      */ import com.ctc.wstx.api.WriterConfig;
/*      */ import com.ctc.wstx.cfg.ErrorConsts;
/*      */ import com.ctc.wstx.cfg.OutputConfigFlags;
/*      */ import com.ctc.wstx.exc.WstxIOException;
/*      */ import com.ctc.wstx.exc.WstxValidationException;
/*      */ import com.ctc.wstx.io.WstxInputLocation;
/*      */ import com.ctc.wstx.sr.AttributeCollector;
/*      */ import com.ctc.wstx.sr.InputElementStack;
/*      */ import com.ctc.wstx.sr.StreamReaderImpl;
/*      */ import com.ctc.wstx.util.DataUtil;
/*      */ import com.ctc.wstx.util.StringUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.text.MessageFormat;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.Location;
/*      */ import javax.xml.stream.XMLReporter;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.stream.events.Characters;
/*      */ import javax.xml.stream.events.StartElement;
/*      */ import org.codehaus.stax2.DTDInfo;
/*      */ import org.codehaus.stax2.XMLStreamLocation2;
/*      */ import org.codehaus.stax2.XMLStreamReader2;
/*      */ import org.codehaus.stax2.ri.Stax2WriterImpl;
/*      */ import org.codehaus.stax2.validation.ValidationContext;
/*      */ import org.codehaus.stax2.validation.ValidationProblemHandler;
/*      */ import org.codehaus.stax2.validation.ValidatorPair;
/*      */ import org.codehaus.stax2.validation.XMLValidationProblem;
/*      */ import org.codehaus.stax2.validation.XMLValidationSchema;
/*      */ import org.codehaus.stax2.validation.XMLValidator;
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
/*      */ public abstract class BaseStreamWriter
/*      */   extends Stax2WriterImpl
/*      */   implements ValidationContext, OutputConfigFlags
/*      */ {
/*      */   protected static final int STATE_PROLOG = 1;
/*      */   protected static final int STATE_TREE = 2;
/*      */   protected static final int STATE_EPILOG = 3;
/*      */   protected static final char CHAR_SPACE = ' ';
/*      */   protected static final int MIN_ARRAYCOPY = 12;
/*      */   protected static final int ATTR_MIN_ARRAYCOPY = 12;
/*      */   protected static final int DEFAULT_COPYBUFFER_LEN = 512;
/*      */   protected final XmlWriter mWriter;
/*  103 */   protected char[] mCopyBuffer = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final WriterConfig mConfig;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mCfgCDataAsText;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mCfgCopyDefaultAttrs;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mCfgAutomaticEmptyElems;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mCheckStructure;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mCheckAttrs;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String mEncoding;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   protected XMLValidator mValidator = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mXml11 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   protected ValidationProblemHandler mVldProbHandler = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   protected int mState = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mAnyOutput = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mStartElementOpen = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mEmptyElement = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  197 */   protected int mVldContent = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  206 */   protected String mDtdRootElem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mReturnNullForDefaultNamespace;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BaseStreamWriter(XmlWriter xw, String enc, WriterConfig cfg) {
/*  218 */     this.mWriter = xw;
/*  219 */     this.mEncoding = enc;
/*  220 */     this.mConfig = cfg;
/*      */     
/*  222 */     int flags = cfg.getConfigFlags();
/*      */     
/*  224 */     this.mCheckStructure = ((flags & 0x100) != 0);
/*  225 */     this.mCheckAttrs = ((flags & 0x800) != 0);
/*      */     
/*  227 */     this.mCfgAutomaticEmptyElems = ((flags & 0x4) != 0);
/*  228 */     this.mCfgCDataAsText = ((flags & 0x8) != 0);
/*  229 */     this.mCfgCopyDefaultAttrs = ((flags & 0x10) != 0);
/*      */     
/*  231 */     Object value = getProperty("com.ctc.wstx.returnNullForDefaultNamespace");
/*  232 */     this.mReturnNullForDefaultNamespace = (value instanceof Boolean && ((Boolean)value).booleanValue());
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
/*      */   public void close() throws XMLStreamException {
/*  249 */     _finishDocument(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() throws XMLStreamException {
/*      */     try {
/*  261 */       this.mWriter.flush();
/*  262 */     } catch (IOException ie) {
/*  263 */       throw new WstxIOException(ie);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract NamespaceContext getNamespaceContext();
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getPrefix(String paramString);
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) {
/*  277 */     if (name.equals("com.ctc.wstx.outputUnderlyingStream")) {
/*  278 */       return this.mWriter.getOutputStream();
/*      */     }
/*  280 */     if (name.equals("com.ctc.wstx.outputUnderlyingWriter")) {
/*  281 */       return this.mWriter.getWriter();
/*      */     }
/*  283 */     return this.mConfig.getProperty(name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setDefaultNamespace(String paramString) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setNamespaceContext(NamespaceContext paramNamespaceContext) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setPrefix(String paramString1, String paramString2) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeAttribute(String paramString1, String paramString2) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeAttribute(String paramString1, String paramString2, String paramString3) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeAttribute(String paramString1, String paramString2, String paramString3, String paramString4) throws XMLStreamException;
/*      */ 
/*      */   
/*      */   public void writeCData(String data) throws XMLStreamException {
/*      */     int ix;
/*  313 */     if (this.mCfgCDataAsText) {
/*  314 */       writeCharacters(data);
/*      */       
/*      */       return;
/*      */     } 
/*  318 */     this.mAnyOutput = true;
/*      */     
/*  320 */     if (this.mStartElementOpen) {
/*  321 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*  323 */     verifyWriteCData();
/*  324 */     if (this.mVldContent == 3 && this.mValidator != null)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  329 */       this.mValidator.validateText(data, false);
/*      */     }
/*      */     
/*      */     try {
/*  333 */       ix = this.mWriter.writeCData(data);
/*  334 */     } catch (IOException ioe) {
/*  335 */       throw new WstxIOException(ioe);
/*      */     } 
/*  337 */     if (ix >= 0) {
/*  338 */       reportNwfContent(ErrorConsts.WERR_CDATA_CONTENT, DataUtil.Integer(ix));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/*  345 */     this.mAnyOutput = true;
/*      */     
/*  347 */     if (this.mStartElementOpen) {
/*  348 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  354 */     if (this.mCheckStructure && 
/*  355 */       inPrologOrEpilog() && 
/*  356 */       !StringUtil.isAllWhitespace(text, start, len)) {
/*  357 */       reportNwfStructure(ErrorConsts.WERR_PROLOG_NONWS_TEXT);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  362 */     if (this.mVldContent <= 1) {
/*  363 */       if (this.mVldContent == 0) {
/*  364 */         reportInvalidContent(4);
/*      */       }
/*  366 */       else if (!StringUtil.isAllWhitespace(text, start, len)) {
/*  367 */         reportInvalidContent(4);
/*      */       }
/*      */     
/*  370 */     } else if (this.mVldContent == 3 && 
/*  371 */       this.mValidator != null) {
/*      */ 
/*      */ 
/*      */       
/*  375 */       this.mValidator.validateText(text, start, len, false);
/*      */     } 
/*      */ 
/*      */     
/*  379 */     if (len > 0) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/*  384 */         if (inPrologOrEpilog()) {
/*  385 */           this.mWriter.writeRaw(text, start, len);
/*      */         } else {
/*  387 */           this.mWriter.writeCharacters(text, start, len);
/*      */         } 
/*  389 */       } catch (IOException ioe) {
/*  390 */         throw new WstxIOException(ioe);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeCharacters(String text) throws XMLStreamException {
/*  398 */     this.mAnyOutput = true;
/*      */     
/*  400 */     if (this.mStartElementOpen) {
/*  401 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */ 
/*      */     
/*  405 */     if (this.mCheckStructure)
/*      */     {
/*  407 */       if (inPrologOrEpilog() && 
/*  408 */         !StringUtil.isAllWhitespace(text)) {
/*  409 */         reportNwfStructure(ErrorConsts.WERR_PROLOG_NONWS_TEXT);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  419 */     if (this.mVldContent <= 1) {
/*  420 */       if (this.mVldContent == 0) {
/*  421 */         reportInvalidContent(4);
/*      */       }
/*  423 */       else if (!StringUtil.isAllWhitespace(text)) {
/*  424 */         reportInvalidContent(4);
/*      */       }
/*      */     
/*  427 */     } else if (this.mVldContent == 3 && 
/*  428 */       this.mValidator != null) {
/*      */ 
/*      */ 
/*      */       
/*  432 */       this.mValidator.validateText(text, false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  440 */     if (inPrologOrEpilog()) {
/*      */       try {
/*  442 */         this.mWriter.writeRaw(text);
/*  443 */       } catch (IOException ioe) {
/*  444 */         throw new WstxIOException(ioe);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  453 */     int len = text.length();
/*  454 */     if (len >= 12) {
/*  455 */       char[] buf = getCopyBuffer();
/*      */       
/*  457 */       int offset = 0;
/*  458 */       while (len > 0) {
/*  459 */         int thisLen = (len > buf.length) ? buf.length : len;
/*  460 */         text.getChars(offset, offset + thisLen, buf, 0);
/*      */         try {
/*  462 */           this.mWriter.writeCharacters(buf, 0, thisLen);
/*  463 */         } catch (IOException ioe) {
/*  464 */           throw new WstxIOException(ioe);
/*      */         } 
/*  466 */         offset += thisLen;
/*  467 */         len -= thisLen;
/*      */       } 
/*      */     } else {
/*      */       try {
/*  471 */         this.mWriter.writeCharacters(text);
/*  472 */       } catch (IOException ioe) {
/*  473 */         throw new WstxIOException(ioe);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeComment(String data) throws XMLStreamException {
/*      */     int ix;
/*  481 */     this.mAnyOutput = true;
/*      */     
/*  483 */     if (this.mStartElementOpen) {
/*  484 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */ 
/*      */     
/*  488 */     if (this.mVldContent == 0) {
/*  489 */       reportInvalidContent(5);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  498 */       ix = this.mWriter.writeComment(data);
/*  499 */     } catch (IOException ioe) {
/*  500 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */     
/*  503 */     if (ix >= 0) {
/*  504 */       reportNwfContent(ErrorConsts.WERR_COMMENT_CONTENT, DataUtil.Integer(ix));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeDefaultNamespace(String paramString) throws XMLStreamException;
/*      */ 
/*      */   
/*      */   public void writeDTD(String dtd) throws XMLStreamException {
/*  514 */     verifyWriteDTD();
/*  515 */     this.mDtdRootElem = "";
/*      */     try {
/*  517 */       this.mWriter.writeDTD(dtd);
/*  518 */     } catch (IOException ioe) {
/*  519 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeEmptyElement(String paramString) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeEmptyElement(String paramString1, String paramString2) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeEmptyElement(String paramString1, String paramString2, String paramString3) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEndDocument() throws XMLStreamException {
/*  538 */     _finishDocument(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract void writeEndElement() throws XMLStreamException;
/*      */ 
/*      */   
/*      */   public void writeEntityRef(String name) throws XMLStreamException {
/*  546 */     this.mAnyOutput = true;
/*      */     
/*  548 */     if (this.mStartElementOpen) {
/*  549 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */ 
/*      */     
/*  553 */     if (this.mCheckStructure && 
/*  554 */       inPrologOrEpilog()) {
/*  555 */       reportNwfStructure("Trying to output an entity reference outside main element tree (in prolog or epilog)");
/*      */     }
/*      */ 
/*      */     
/*  559 */     if (this.mVldContent == 0)
/*      */     {
/*      */ 
/*      */       
/*  563 */       reportInvalidContent(9);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  573 */       this.mWriter.writeEntityReference(name);
/*  574 */     } catch (IOException ioe) {
/*  575 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeNamespace(String paramString1, String paramString2) throws XMLStreamException;
/*      */ 
/*      */   
/*      */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/*  585 */     writeProcessingInstruction(target, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/*      */     int ix;
/*  591 */     this.mAnyOutput = true;
/*      */     
/*  593 */     if (this.mStartElementOpen) {
/*  594 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  599 */     if (this.mVldContent == 0) {
/*  600 */       reportInvalidContent(3);
/*      */     }
/*      */     
/*      */     try {
/*  604 */       ix = this.mWriter.writePI(target, data);
/*  605 */     } catch (IOException ioe) {
/*  606 */       throw new WstxIOException(ioe);
/*      */     } 
/*  608 */     if (ix >= 0) {
/*  609 */       throw new XMLStreamException("Illegal input: processing instruction content has embedded '?>' in it (index " + ix + ")");
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
/*      */   public void writeStartDocument() throws XMLStreamException {
/*  625 */     if (this.mEncoding == null) {
/*  626 */       this.mEncoding = "UTF-8";
/*      */     }
/*  628 */     writeStartDocument(this.mEncoding, "1.0");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartDocument(String version) throws XMLStreamException {
/*  634 */     writeStartDocument(this.mEncoding, version);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/*  640 */     doWriteStartDocument(version, encoding, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doWriteStartDocument(String version, String encoding, String standAlone) throws XMLStreamException {
/*  650 */     if (this.mCheckStructure && 
/*  651 */       this.mAnyOutput) {
/*  652 */       reportNwfStructure("Can not output XML declaration, after other output has already been done.");
/*      */     }
/*      */ 
/*      */     
/*  656 */     this.mAnyOutput = true;
/*      */     
/*  658 */     if (this.mConfig.willValidateContent())
/*      */     {
/*      */ 
/*      */       
/*  662 */       if (version != null && version.length() > 0 && 
/*  663 */         !version.equals("1.0") && !version.equals("1.1"))
/*      */       {
/*  665 */         reportNwfContent("Illegal version argument ('" + version + "'); should only use '" + "1.0" + "' or '" + "1.1" + "'");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  672 */     if (version == null || version.length() == 0) {
/*  673 */       version = "1.0";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     this.mXml11 = "1.1".equals(version);
/*  680 */     if (this.mXml11) {
/*  681 */       this.mWriter.enableXml11();
/*      */     }
/*      */     
/*  684 */     if (encoding != null && encoding.length() > 0)
/*      */     {
/*      */ 
/*      */       
/*  688 */       if (this.mEncoding == null || this.mEncoding.length() == 0) {
/*  689 */         this.mEncoding = encoding;
/*      */       }
/*      */     }
/*      */     try {
/*  693 */       this.mWriter.writeXmlDeclaration(version, encoding, standAlone);
/*  694 */     } catch (IOException ioe) {
/*  695 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeStartElement(String paramString) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeStartElement(String paramString1, String paramString2) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeStartElement(String paramString1, String paramString2, String paramString3) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyEventFromReader(XMLStreamReader2 sr, boolean preserveEventData) throws XMLStreamException {
/*      */     try {
/*      */       String version;
/*      */       DTDInfo info;
/*  731 */       switch (sr.getEventType()) {
/*      */         
/*      */         case 7:
/*  734 */           version = sr.getVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  739 */           if (version != null && version.length() != 0)
/*      */           {
/*      */             
/*  742 */             if (sr.standaloneSet()) {
/*  743 */               writeStartDocument(sr.getVersion(), sr.getCharacterEncodingScheme(), sr.isStandalone());
/*      */             }
/*      */             else {
/*      */               
/*  747 */               writeStartDocument(sr.getCharacterEncodingScheme(), sr.getVersion());
/*      */             } 
/*      */           }
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 8:
/*  755 */           writeEndDocument();
/*      */           return;
/*      */ 
/*      */         
/*      */         case 1:
/*  760 */           if (sr instanceof StreamReaderImpl) {
/*  761 */             StreamReaderImpl impl = (StreamReaderImpl)sr;
/*  762 */             copyStartElement(impl.getInputElementStack(), impl.getAttributeCollector());
/*      */           } else {
/*  764 */             copyStartElement((XMLStreamReader)sr);
/*      */           } 
/*      */           return;
/*      */         
/*      */         case 2:
/*  769 */           writeEndElement();
/*      */           return;
/*      */ 
/*      */         
/*      */         case 6:
/*  774 */           this.mAnyOutput = true;
/*      */           
/*  776 */           if (this.mStartElementOpen) {
/*  777 */             closeStartElement(this.mEmptyElement);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  783 */           sr.getText(wrapAsRawWriter(), preserveEventData);
/*      */           return;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 12:
/*  790 */           if (!this.mCfgCDataAsText) {
/*  791 */             this.mAnyOutput = true;
/*      */             
/*  793 */             if (this.mStartElementOpen) {
/*  794 */               closeStartElement(this.mEmptyElement);
/*      */             }
/*      */ 
/*      */             
/*  798 */             if (this.mCheckStructure && 
/*  799 */               inPrologOrEpilog()) {
/*  800 */               reportNwfStructure(ErrorConsts.WERR_PROLOG_CDATA);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  806 */             this.mWriter.writeCDataStart();
/*  807 */             sr.getText(wrapAsRawWriter(), preserveEventData);
/*  808 */             this.mWriter.writeCDataEnd();
/*      */             return;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/*  818 */           this.mAnyOutput = true;
/*      */           
/*  820 */           if (this.mStartElementOpen) {
/*  821 */             closeStartElement(this.mEmptyElement);
/*      */           }
/*  823 */           sr.getText(wrapAsTextWriter(), preserveEventData);
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 5:
/*  829 */           this.mAnyOutput = true;
/*  830 */           if (this.mStartElementOpen) {
/*  831 */             closeStartElement(this.mEmptyElement);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  836 */           this.mWriter.writeCommentStart();
/*  837 */           sr.getText(wrapAsRawWriter(), preserveEventData);
/*  838 */           this.mWriter.writeCommentEnd();
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 3:
/*  844 */           this.mWriter.writePIStart(sr.getPITarget(), true);
/*  845 */           sr.getText(wrapAsRawWriter(), preserveEventData);
/*  846 */           this.mWriter.writePIEnd();
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 11:
/*  852 */           info = sr.getDTDInfo();
/*  853 */           if (info == null)
/*      */           {
/*      */ 
/*      */             
/*  857 */             throwOutputError("Current state DOCTYPE, but not DTDInfo Object returned -- reader doesn't support DTDs?");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  863 */           writeDTD(info);
/*      */           return;
/*      */ 
/*      */         
/*      */         case 9:
/*  868 */           writeEntityRef(sr.getLocalName());
/*      */           return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     } catch (IOException ioe) {
/*  878 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */     
/*  881 */     throw new XMLStreamException("Unrecognized event type (" + sr.getEventType() + "); not sure how to copy");
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
/*      */   public void closeCompletely() throws XMLStreamException {
/*  894 */     _finishDocument(true);
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
/*      */   public boolean isPropertySupported(String name) {
/*  907 */     return this.mConfig.isPropertySupported(name);
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
/*      */   public boolean setProperty(String name, Object value) {
/*  922 */     return this.mConfig.setProperty(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLValidator validateAgainst(XMLValidationSchema schema) throws XMLStreamException {
/*  928 */     XMLValidator vld = schema.createValidator(this);
/*      */     
/*  930 */     if (this.mValidator == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  935 */       this.mCheckStructure = true;
/*  936 */       this.mCheckAttrs = true;
/*  937 */       this.mValidator = vld;
/*      */     } else {
/*  939 */       this.mValidator = (XMLValidator)new ValidatorPair(this.mValidator, vld);
/*      */     } 
/*  941 */     return vld;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLValidator stopValidatingAgainst(XMLValidationSchema schema) throws XMLStreamException {
/*  947 */     XMLValidator[] results = new XMLValidator[2];
/*  948 */     XMLValidator found = null;
/*  949 */     if (ValidatorPair.removeValidator(this.mValidator, schema, results)) {
/*  950 */       found = results[0];
/*  951 */       this.mValidator = results[1];
/*  952 */       found.validationCompleted(false);
/*  953 */       if (this.mValidator == null) {
/*  954 */         resetValidationFlags();
/*      */       }
/*      */     } 
/*  957 */     return found;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLValidator stopValidatingAgainst(XMLValidator validator) throws XMLStreamException {
/*  963 */     XMLValidator[] results = new XMLValidator[2];
/*  964 */     XMLValidator found = null;
/*  965 */     if (ValidatorPair.removeValidator(this.mValidator, validator, results)) {
/*  966 */       found = results[0];
/*  967 */       this.mValidator = results[1];
/*  968 */       found.validationCompleted(false);
/*  969 */       if (this.mValidator == null) {
/*  970 */         resetValidationFlags();
/*      */       }
/*      */     } 
/*  973 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   public ValidationProblemHandler setValidationProblemHandler(ValidationProblemHandler h) {
/*  978 */     ValidationProblemHandler oldH = this.mVldProbHandler;
/*  979 */     this.mVldProbHandler = h;
/*  980 */     return oldH;
/*      */   }
/*      */ 
/*      */   
/*      */   private void resetValidationFlags() {
/*  985 */     int flags = this.mConfig.getConfigFlags();
/*  986 */     this.mCheckStructure = ((flags & 0x100) != 0);
/*  987 */     this.mCheckAttrs = ((flags & 0x800) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamLocation2 getLocation() {
/*  998 */     return (XMLStreamLocation2)new WstxInputLocation(null, null, null, this.mWriter.getAbsOffset(), this.mWriter.getRow(), this.mWriter.getColumn());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() {
/* 1005 */     return this.mEncoding;
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
/*      */   public void writeCData(char[] cbuf, int start, int len) throws XMLStreamException {
/*      */     int ix;
/* 1021 */     if (this.mCfgCDataAsText) {
/* 1022 */       writeCharacters(cbuf, start, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1026 */     this.mAnyOutput = true;
/*      */     
/* 1028 */     if (this.mStartElementOpen) {
/* 1029 */       closeStartElement(this.mEmptyElement);
/*      */     }
/* 1031 */     verifyWriteCData();
/* 1032 */     if (this.mVldContent == 3 && this.mValidator != null)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1037 */       this.mValidator.validateText(cbuf, start, len, false);
/*      */     }
/*      */     
/*      */     try {
/* 1041 */       ix = this.mWriter.writeCData(cbuf, start, len);
/* 1042 */     } catch (IOException ioe) {
/* 1043 */       throw new WstxIOException(ioe);
/*      */     } 
/* 1045 */     if (ix >= 0) {
/* 1046 */       throwOutputError(ErrorConsts.WERR_CDATA_CONTENT, DataUtil.Integer(ix));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeDTD(DTDInfo info) throws XMLStreamException {
/* 1053 */     writeDTD(info.getDTDRootName(), info.getDTDSystemId(), info.getDTDPublicId(), info.getDTDInternalSubset());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeDTD(String rootName, String systemId, String publicId, String internalSubset) throws XMLStreamException {
/* 1061 */     verifyWriteDTD();
/* 1062 */     this.mDtdRootElem = rootName;
/*      */     try {
/* 1064 */       this.mWriter.writeDTD(rootName, systemId, publicId, internalSubset);
/* 1065 */     } catch (IOException ioe) {
/* 1066 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void writeFullEndElement() throws XMLStreamException;
/*      */ 
/*      */   
/*      */   public void writeStartDocument(String version, String encoding, boolean standAlone) throws XMLStreamException {
/* 1076 */     doWriteStartDocument(version, encoding, standAlone ? "yes" : "no");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(String text) throws XMLStreamException {
/* 1082 */     this.mAnyOutput = true;
/* 1083 */     if (this.mStartElementOpen) {
/* 1084 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */     try {
/* 1087 */       this.mWriter.writeRaw(text, 0, text.length());
/* 1088 */     } catch (IOException ioe) {
/* 1089 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(String text, int start, int offset) throws XMLStreamException {
/* 1096 */     this.mAnyOutput = true;
/* 1097 */     if (this.mStartElementOpen) {
/* 1098 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */     try {
/* 1101 */       this.mWriter.writeRaw(text, start, offset);
/* 1102 */     } catch (IOException ioe) {
/* 1103 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRaw(char[] text, int start, int offset) throws XMLStreamException {
/* 1110 */     this.mAnyOutput = true;
/* 1111 */     if (this.mStartElementOpen) {
/* 1112 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */     try {
/* 1115 */       this.mWriter.writeRaw(text, start, offset);
/* 1116 */     } catch (IOException ioe) {
/* 1117 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeSpace(String text) throws XMLStreamException {
/* 1127 */     writeRaw(text);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeSpace(char[] text, int offset, int length) throws XMLStreamException {
/* 1134 */     writeRaw(text, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getXmlVersion() {
/* 1144 */     return this.mXml11 ? "1.1" : "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract QName getCurrentElementName();
/*      */ 
/*      */   
/*      */   public abstract String getNamespaceURI(String paramString);
/*      */ 
/*      */   
/*      */   public String getBaseUri() {
/* 1156 */     return null;
/*      */   }
/*      */   
/*      */   public Location getValidationLocation() {
/* 1160 */     return (Location)getLocation();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportProblem(XMLValidationProblem prob) throws XMLStreamException {
/* 1167 */     if (this.mVldProbHandler != null) {
/* 1168 */       this.mVldProbHandler.reportProblem(prob);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1182 */     if (prob.getSeverity() > 2) {
/* 1183 */       throw WstxValidationException.create(prob);
/*      */     }
/* 1185 */     XMLReporter rep = this.mConfig.getProblemReporter();
/* 1186 */     if (rep != null) {
/* 1187 */       doReportProblem(rep, prob);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1192 */     else if (prob.getSeverity() >= 2) {
/* 1193 */       throw WstxValidationException.create(prob);
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
/*      */   public int addDefaultAttribute(String localName, String uri, String prefix, String value) {
/* 1206 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNotationDeclared(String name) {
/* 1211 */     return false;
/*      */   } public boolean isUnparsedEntityDeclared(String name) {
/* 1213 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAttributeCount() {
/* 1222 */     return 0;
/*      */   } public String getAttributeLocalName(int index) {
/* 1224 */     return null;
/*      */   } public String getAttributeNamespace(int index) {
/* 1226 */     return null;
/*      */   } public String getAttributePrefix(int index) {
/* 1228 */     return null;
/*      */   } public String getAttributeValue(int index) {
/* 1230 */     return null;
/*      */   }
/*      */   public String getAttributeValue(String nsURI, String localName) {
/* 1233 */     return null;
/*      */   }
/*      */   
/*      */   public String getAttributeType(int index) {
/* 1237 */     return "";
/*      */   }
/*      */   
/*      */   public int findAttributeIndex(String nsURI, String localName) {
/* 1241 */     return -1;
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
/*      */   public final Writer wrapAsRawWriter() {
/* 1257 */     return this.mWriter.wrapAsRawWriter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Writer wrapAsTextWriter() {
/* 1267 */     return this.mWriter.wrapAsTextWriter();
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
/*      */   protected boolean isValidating() {
/* 1279 */     return (this.mValidator != null);
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
/*      */   public abstract void writeStartElement(StartElement paramStartElement) throws XMLStreamException;
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
/*      */   public abstract void writeEndElement(QName paramQName) throws XMLStreamException;
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
/*      */   public void writeCharacters(Characters ch) throws XMLStreamException {
/* 1315 */     if (this.mStartElementOpen) {
/* 1316 */       closeStartElement(this.mEmptyElement);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1322 */     if (this.mCheckStructure && 
/* 1323 */       inPrologOrEpilog() && 
/* 1324 */       !ch.isIgnorableWhiteSpace() && !ch.isWhiteSpace()) {
/* 1325 */       reportNwfStructure(ErrorConsts.WERR_PROLOG_NONWS_TEXT);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1330 */     if (this.mVldContent <= 1) {
/* 1331 */       if (this.mVldContent == 0) {
/* 1332 */         reportInvalidContent(4);
/*      */       }
/* 1334 */       else if (!ch.isIgnorableWhiteSpace() && !ch.isWhiteSpace()) {
/* 1335 */         reportInvalidContent(4);
/*      */       }
/*      */     
/* 1338 */     } else if (this.mVldContent == 3 && 
/* 1339 */       this.mValidator != null) {
/*      */ 
/*      */ 
/*      */       
/* 1343 */       this.mValidator.validateText(ch.getData(), false);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1349 */       this.mWriter.writeCharacters(ch.getData());
/* 1350 */     } catch (IOException ioe) {
/* 1351 */       throw new WstxIOException(ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void closeStartElement(boolean paramBoolean) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean inPrologOrEpilog() {
/* 1364 */     return (this.mState != 2);
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
/*      */   private final void _finishDocument(boolean forceRealClose) throws XMLStreamException {
/* 1377 */     if (this.mState != 3) {
/* 1378 */       if (this.mCheckStructure && this.mState == 1) {
/* 1379 */         reportNwfStructure("Trying to write END_DOCUMENT when document has no root (ie. trying to output empty document).");
/*      */       }
/*      */ 
/*      */       
/* 1383 */       if (this.mStartElementOpen) {
/* 1384 */         closeStartElement(this.mEmptyElement);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1390 */       if (this.mState != 3 && this.mConfig.automaticEndElementsEnabled()) {
/*      */         do {
/* 1392 */           writeEndElement();
/* 1393 */         } while (this.mState != 3);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1400 */     char[] buf = this.mCopyBuffer;
/* 1401 */     if (buf != null) {
/* 1402 */       this.mCopyBuffer = null;
/* 1403 */       this.mConfig.freeMediumCBuffer(buf);
/*      */     } 
/*      */     try {
/* 1406 */       this.mWriter.close(forceRealClose);
/* 1407 */     } catch (IOException ie) {
/* 1408 */       throw new WstxIOException(ie);
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
/*      */   public abstract void copyStartElement(InputElementStack paramInputElementStack, AttributeCollector paramAttributeCollector) throws IOException, XMLStreamException;
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
/*      */   public abstract String validateQNamePrefix(QName paramQName) throws XMLStreamException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void verifyWriteCData() throws XMLStreamException {
/* 1443 */     if (this.mCheckStructure && 
/* 1444 */       inPrologOrEpilog()) {
/* 1445 */       reportNwfStructure(ErrorConsts.WERR_PROLOG_CDATA);
/*      */     }
/*      */ 
/*      */     
/* 1449 */     if (this.mVldContent <= 1)
/*      */     {
/* 1451 */       reportInvalidContent(12);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void verifyWriteDTD() throws XMLStreamException {
/* 1459 */     if (this.mCheckStructure) {
/* 1460 */       if (this.mState != 1) {
/* 1461 */         throw new XMLStreamException("Can not write DOCTYPE declaration (DTD) when not in prolog any more (state " + this.mState + "; start element(s) written)");
/*      */       }
/*      */       
/* 1464 */       if (this.mDtdRootElem != null) {
/* 1465 */         throw new XMLStreamException("Trying to write multiple DOCTYPE declarations");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void verifyRootElement(String localName, String prefix) throws XMLStreamException {
/* 1476 */     if (isValidating())
/*      */     {
/*      */ 
/*      */       
/* 1480 */       if (this.mDtdRootElem != null && this.mDtdRootElem.length() > 0) {
/* 1481 */         String wrongElem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1487 */         if (!localName.equals(this.mDtdRootElem)) {
/*      */ 
/*      */           
/* 1490 */           int lnLen = localName.length();
/* 1491 */           int oldLen = this.mDtdRootElem.length();
/*      */           
/* 1493 */           if (oldLen <= lnLen || !this.mDtdRootElem.endsWith(localName) || this.mDtdRootElem.charAt(oldLen - lnLen - 1) != ':')
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 1498 */             if (prefix == null) {
/* 1499 */               wrongElem = localName;
/* 1500 */             } else if (prefix.length() == 0) {
/* 1501 */               wrongElem = "[unknown]:" + localName;
/*      */             } else {
/* 1503 */               wrongElem = prefix + ":" + localName;
/*      */             } 
/*      */           }
/*      */         } 
/* 1507 */         if (wrongElem != null) {
/* 1508 */           reportValidationProblem(ErrorConsts.ERR_VLD_WRONG_ROOT, wrongElem, this.mDtdRootElem);
/*      */         }
/*      */       } 
/*      */     }
/* 1512 */     this.mState = 2;
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
/*      */   protected static void throwOutputError(String msg) throws XMLStreamException {
/* 1524 */     throw new XMLStreamException(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void throwOutputError(String format, Object arg) throws XMLStreamException {
/* 1530 */     String msg = MessageFormat.format(format, new Object[] { arg });
/* 1531 */     throwOutputError(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void reportIllegalMethod(String msg) throws XMLStreamException {
/* 1541 */     throwOutputError(msg);
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
/*      */   protected static void reportNwfStructure(String msg) throws XMLStreamException {
/* 1553 */     throwOutputError(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void reportNwfStructure(String msg, Object arg) throws XMLStreamException {
/* 1559 */     throwOutputError(msg, arg);
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
/*      */   protected static void reportNwfContent(String msg) throws XMLStreamException {
/* 1571 */     throwOutputError(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void reportNwfContent(String msg, Object arg) throws XMLStreamException {
/* 1577 */     throwOutputError(msg, arg);
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
/*      */   protected static void reportNwfAttr(String msg) throws XMLStreamException {
/* 1589 */     throwOutputError(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void reportNwfAttr(String msg, Object arg) throws XMLStreamException {
/* 1595 */     throwOutputError(msg, arg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void throwFromIOE(IOException ioe) throws XMLStreamException {
/* 1601 */     throw new WstxIOException(ioe);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void reportIllegalArg(String msg) throws IllegalArgumentException {
/* 1607 */     throw new IllegalArgumentException(msg);
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
/*      */   protected void reportInvalidContent(int evtType) throws XMLStreamException {
/* 1619 */     switch (this.mVldContent) {
/*      */       case 0:
/* 1621 */         reportValidationProblem(ErrorConsts.ERR_VLD_EMPTY, getTopElementDesc(), ErrorConsts.tokenTypeDesc(evtType));
/*      */         return;
/*      */ 
/*      */       
/*      */       case 1:
/* 1626 */         reportValidationProblem(ErrorConsts.ERR_VLD_NON_MIXED, getTopElementDesc());
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 4:
/* 1634 */         reportValidationProblem(ErrorConsts.ERR_VLD_ANY, getTopElementDesc(), ErrorConsts.tokenTypeDesc(evtType));
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1639 */     reportValidationProblem("Internal error: trying to report invalid content for " + evtType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportValidationProblem(String msg, Location loc, int severity) throws XMLStreamException {
/* 1646 */     reportProblem(new XMLValidationProblem(loc, msg, severity));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportValidationProblem(String msg, int severity) throws XMLStreamException {
/* 1652 */     reportProblem(new XMLValidationProblem(getValidationLocation(), msg, severity));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportValidationProblem(String msg) throws XMLStreamException {
/* 1659 */     reportProblem(new XMLValidationProblem(getValidationLocation(), msg, 2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportValidationProblem(Location loc, String msg) throws XMLStreamException {
/* 1667 */     reportProblem(new XMLValidationProblem(getValidationLocation(), msg));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportValidationProblem(String format, Object arg) throws XMLStreamException {
/* 1673 */     String msg = MessageFormat.format(format, new Object[] { arg });
/* 1674 */     reportProblem(new XMLValidationProblem(getValidationLocation(), msg));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportValidationProblem(String format, Object arg, Object arg2) throws XMLStreamException {
/* 1681 */     String msg = MessageFormat.format(format, new Object[] { arg, arg2 });
/* 1682 */     reportProblem(new XMLValidationProblem(getValidationLocation(), msg));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doReportProblem(XMLReporter rep, String probType, String msg, Location loc) throws XMLStreamException {
/*      */     XMLStreamLocation2 xMLStreamLocation2;
/* 1688 */     if (loc == null) {
/* 1689 */       xMLStreamLocation2 = getLocation();
/*      */     }
/* 1691 */     doReportProblem(rep, new XMLValidationProblem((Location)xMLStreamLocation2, msg, 2, probType));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doReportProblem(XMLReporter rep, XMLValidationProblem prob) throws XMLStreamException {
/* 1697 */     if (rep != null) {
/* 1698 */       XMLStreamLocation2 xMLStreamLocation2; Location loc = prob.getLocation();
/* 1699 */       if (loc == null) {
/* 1700 */         xMLStreamLocation2 = getLocation();
/* 1701 */         prob.setLocation((Location)xMLStreamLocation2);
/*      */       } 
/*      */       
/* 1704 */       if (prob.getType() == null) {
/* 1705 */         prob.setType(ErrorConsts.WT_VALIDATION);
/*      */       }
/*      */       
/* 1708 */       rep.report(prob.getMessage(), prob.getType(), prob, (Location)xMLStreamLocation2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract String getTopElementDesc();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final char[] getCopyBuffer() {
/* 1725 */     char[] buf = this.mCopyBuffer;
/* 1726 */     if (buf == null) {
/* 1727 */       this.mCopyBuffer = buf = this.mConfig.allocMediumCBuffer(512);
/*      */     }
/* 1729 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final char[] getCopyBuffer(int minLen) {
/* 1734 */     char[] buf = this.mCopyBuffer;
/* 1735 */     if (buf == null || minLen > buf.length) {
/* 1736 */       this.mCopyBuffer = buf = this.mConfig.allocMediumCBuffer(Math.max(512, minLen));
/*      */     }
/* 1738 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1743 */     return "[StreamWriter: " + getClass() + ", underlying outputter: " + ((this.mWriter == null) ? "NULL" : (this.mWriter.toString() + "]"));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\sw\BaseStreamWriter.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
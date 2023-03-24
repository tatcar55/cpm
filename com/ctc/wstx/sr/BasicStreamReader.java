/*      */ package com.ctc.wstx.sr;
/*      */ 
/*      */ import com.ctc.wstx.api.ReaderConfig;
/*      */ import com.ctc.wstx.cfg.ErrorConsts;
/*      */ import com.ctc.wstx.dtd.MinimalDTDReader;
/*      */ import com.ctc.wstx.ent.EntityDecl;
/*      */ import com.ctc.wstx.exc.WstxException;
/*      */ import com.ctc.wstx.io.BranchingReaderSource;
/*      */ import com.ctc.wstx.io.InputBootstrapper;
/*      */ import com.ctc.wstx.io.WstxInputSource;
/*      */ import com.ctc.wstx.util.DefaultXmlSymbolTable;
/*      */ import com.ctc.wstx.util.TextBuffer;
/*      */ import com.ctc.wstx.util.TextBuilder;
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.Location;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import org.codehaus.stax2.AttributeInfo;
/*      */ import org.codehaus.stax2.DTDInfo;
/*      */ import org.codehaus.stax2.LocationInfo;
/*      */ import org.codehaus.stax2.XMLStreamLocation2;
/*      */ import org.codehaus.stax2.typed.TypedXMLStreamException;
/*      */ import org.codehaus.stax2.validation.DTDValidationSchema;
/*      */ import org.codehaus.stax2.validation.ValidationProblemHandler;
/*      */ import org.codehaus.stax2.validation.XMLValidationSchema;
/*      */ import org.codehaus.stax2.validation.XMLValidator;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class BasicStreamReader
/*      */   extends StreamScanner
/*      */   implements StreamReaderImpl, DTDInfo, LocationInfo
/*      */ {
/*      */   static final int DOC_STANDALONE_UNKNOWN = 0;
/*      */   static final int DOC_STANDALONE_YES = 1;
/*      */   static final int DOC_STANDALONE_NO = 2;
/*      */   static final int STATE_PROLOG = 0;
/*      */   static final int STATE_TREE = 1;
/*      */   static final int STATE_EPILOG = 2;
/*      */   static final int STATE_MULTIDOC_HACK = 3;
/*      */   static final int STATE_CLOSED = 4;
/*      */   static final int TOKEN_NOT_STARTED = 0;
/*      */   static final int TOKEN_STARTED = 1;
/*      */   static final int TOKEN_PARTIAL_SINGLE = 2;
/*      */   static final int TOKEN_FULL_SINGLE = 3;
/*      */   static final int TOKEN_FULL_COALESCED = 4;
/*      */   protected static final int MASK_GET_TEXT = 6768;
/*      */   protected static final int MASK_GET_TEXT_XXX = 4208;
/*      */   protected static final int MASK_GET_TEXT_WITH_WRITER = 6776;
/*      */   protected static final int MASK_GET_ELEMENT_TEXT = 4688;
/*      */   static final int ALL_WS_UNKNOWN = 0;
/*      */   static final int ALL_WS_YES = 1;
/*      */   static final int ALL_WS_NO = 2;
/*      */   private static final int INDENT_CHECK_START = 16;
/*      */   private static final int INDENT_CHECK_MAX = 40;
/*  163 */   protected static final String sPrefixXml = DefaultXmlSymbolTable.getXmlSymbol();
/*      */   
/*  165 */   protected static final String sPrefixXmlns = DefaultXmlSymbolTable.getXmlnsSymbol();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int mConfigFlags;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mCfgCoalesceText;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mCfgReportTextAsChars;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mCfgLazyParsing;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int mShortestTextSegment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ReaderCreator mOwner;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  218 */   protected int mDocStandalone = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String mRootPrefix;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String mRootLName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String mDtdPublicId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String mDtdSystemId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TextBuffer mTextBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final InputElementStack mElementStack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final AttributeCollector mAttrCollector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mStDoctypeFound = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  287 */   protected int mTokenState = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int mStTextThreshold;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mStEmptyElem = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int mParseState;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  310 */   protected int mCurrToken = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  317 */   protected int mSecondaryToken = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int mWsStatus;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mValidateText = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int mCheckIndentation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  346 */   protected XMLStreamException mPendingException = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  360 */   protected Map mGeneralEntities = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  371 */   protected int mVldContent = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BasicStreamReader(InputBootstrapper bs, BranchingReaderSource input, ReaderCreator owner, ReaderConfig cfg, InputElementStack elemStack, boolean forER) throws XMLStreamException {
/*  400 */     super((WstxInputSource)input, cfg, cfg.getEntityResolver());
/*      */     
/*  402 */     this.mOwner = owner;
/*      */     
/*  404 */     this.mTextBuffer = TextBuffer.createRecyclableBuffer(cfg);
/*      */ 
/*      */ 
/*      */     
/*  408 */     this.mConfigFlags = cfg.getConfigFlags();
/*  409 */     this.mCfgCoalesceText = ((this.mConfigFlags & 0x2) != 0);
/*  410 */     this.mCfgReportTextAsChars = ((this.mConfigFlags & 0x200) == 0);
/*  411 */     this.mXml11 = cfg.isXml11();
/*      */ 
/*      */     
/*  414 */     this.mCheckIndentation = this.mNormalizeLFs ? 16 : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  423 */     this.mCfgLazyParsing = (!forER && (this.mConfigFlags & 0x40000) != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  428 */     if (this.mCfgCoalesceText) {
/*  429 */       this.mStTextThreshold = 4;
/*  430 */       this.mShortestTextSegment = Integer.MAX_VALUE;
/*      */     } else {
/*  432 */       this.mStTextThreshold = 2;
/*  433 */       if (forER) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  439 */         this.mShortestTextSegment = Integer.MAX_VALUE;
/*      */       } else {
/*  441 */         this.mShortestTextSegment = cfg.getShortestReportedTextSegment();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  447 */     this.mDocXmlVersion = bs.getDeclaredVersion();
/*  448 */     this.mDocInputEncoding = bs.getInputEncoding();
/*  449 */     this.mDocXmlEncoding = bs.getDeclaredEncoding();
/*      */     
/*  451 */     String sa = bs.getStandalone();
/*  452 */     if (sa == null) {
/*  453 */       this.mDocStandalone = 0;
/*      */     }
/*  455 */     else if ("yes".equals(sa)) {
/*  456 */       this.mDocStandalone = 1;
/*      */     } else {
/*  458 */       this.mDocStandalone = 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  469 */     this.mParseState = this.mConfig.inputParsingModeFragment() ? 1 : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  474 */     this.mElementStack = elemStack;
/*  475 */     this.mAttrCollector = elemStack.getAttrCollector();
/*      */ 
/*      */     
/*  478 */     input.initInputLocation(this, this.mCurrDepth);
/*      */     
/*  480 */     elemStack.connectReporter(this);
/*      */     
/*  482 */     Object value = getProperty("com.ctc.wstx.returnNullForDefaultNamespace");
/*  483 */     this.mReturnNullForDefaultNamespace = (value instanceof Boolean && ((Boolean)value).booleanValue());
/*      */   }
/*      */ 
/*      */   
/*      */   protected static InputElementStack createElementStack(ReaderConfig cfg) {
/*  488 */     return new InputElementStack(cfg, cfg.willSupportNamespaces());
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
/*      */   public String getCharacterEncodingScheme() {
/*  504 */     return this.mDocXmlEncoding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() {
/*  514 */     return this.mDocInputEncoding;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  519 */     if (this.mDocXmlVersion == 256) {
/*  520 */       return "1.0";
/*      */     }
/*  522 */     if (this.mDocXmlVersion == 272) {
/*  523 */       return "1.1";
/*      */     }
/*  525 */     return null;
/*      */   }
/*      */   
/*      */   public boolean isStandalone() {
/*  529 */     return (this.mDocStandalone == 1);
/*      */   }
/*      */   
/*      */   public boolean standaloneSet() {
/*  533 */     return (this.mDocStandalone != 0);
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
/*      */   public Object getProperty(String name) {
/*  549 */     if ("com.ctc.wstx.baseURL".equals(name)) {
/*  550 */       return this.mInput.getSource();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  557 */     return this.mConfig.safeGetProperty(name);
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
/*      */   public int getAttributeCount() {
/*  569 */     if (this.mCurrToken != 1) {
/*  570 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*  572 */     return this.mAttrCollector.getCount();
/*      */   }
/*      */   
/*      */   public String getAttributeLocalName(int index) {
/*  576 */     if (this.mCurrToken != 1) {
/*  577 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*  579 */     return this.mAttrCollector.getLocalName(index);
/*      */   }
/*      */   
/*      */   public QName getAttributeName(int index) {
/*  583 */     if (this.mCurrToken != 1) {
/*  584 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*  586 */     return this.mAttrCollector.getQName(index);
/*      */   }
/*      */   
/*      */   public String getAttributeNamespace(int index) {
/*  590 */     if (this.mCurrToken != 1) {
/*  591 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*      */     
/*  594 */     String uri = this.mAttrCollector.getURI(index);
/*  595 */     return (uri == null) ? "" : uri;
/*      */   }
/*      */   
/*      */   public String getAttributePrefix(int index) {
/*  599 */     if (this.mCurrToken != 1) {
/*  600 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*      */     
/*  603 */     String p = this.mAttrCollector.getPrefix(index);
/*  604 */     return (p == null) ? "" : p;
/*      */   }
/*      */   
/*      */   public String getAttributeType(int index) {
/*  608 */     if (this.mCurrToken != 1) {
/*  609 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*      */     
/*  612 */     return this.mElementStack.getAttributeType(index);
/*      */   }
/*      */   
/*      */   public String getAttributeValue(int index) {
/*  616 */     if (this.mCurrToken != 1) {
/*  617 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*  619 */     return this.mAttrCollector.getValue(index);
/*      */   }
/*      */   
/*      */   public String getAttributeValue(String nsURI, String localName) {
/*  623 */     if (this.mCurrToken != 1) {
/*  624 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*  626 */     return this.mAttrCollector.getValue(nsURI, localName);
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
/*      */   public String getElementText() throws XMLStreamException {
/*      */     int type;
/*  643 */     if (this.mCurrToken != 1) {
/*  644 */       throwParseError(ErrorConsts.ERR_STATE_NOT_STELEM, (Object)null, (Object)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     if (this.mStEmptyElem) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  656 */       this.mStEmptyElem = false;
/*  657 */       this.mCurrToken = 2;
/*  658 */       return "";
/*      */     } 
/*      */     
/*      */     while (true) {
/*  662 */       type = next();
/*  663 */       if (type == 2) {
/*  664 */         return "";
/*      */       }
/*  666 */       if (type == 5 || type == 3)
/*      */         continue;  break;
/*      */     } 
/*  669 */     if ((1 << type & 0x1250) == 0) {
/*  670 */       throw _constructUnexpectedInTyped(type);
/*      */     }
/*      */ 
/*      */     
/*  674 */     if (this.mTokenState < 3) {
/*  675 */       readCoalescedText(this.mCurrToken, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     if (this.mInputPtr + 1 < this.mInputEnd && this.mInputBuffer[this.mInputPtr] == '<' && this.mInputBuffer[this.mInputPtr + 1] == '/') {
/*      */ 
/*      */       
/*  684 */       this.mInputPtr += 2;
/*  685 */       this.mCurrToken = 2;
/*      */       
/*  687 */       String result = this.mTextBuffer.contentsAsString();
/*      */       
/*  689 */       readEndElem();
/*      */       
/*  691 */       return result;
/*      */     } 
/*      */ 
/*      */     
/*  695 */     int extra = 1 + (this.mTextBuffer.size() >> 1);
/*  696 */     StringBuffer sb = this.mTextBuffer.contentsAsStringBuffer(extra);
/*      */     
/*      */     int i;
/*  699 */     while ((i = next()) != 2) {
/*  700 */       if ((1 << i & 0x1250) != 0) {
/*  701 */         if (this.mTokenState < this.mStTextThreshold) {
/*  702 */           finishToken(false);
/*      */         }
/*  704 */         this.mTextBuffer.contentsToStringBuffer(sb);
/*      */         continue;
/*      */       } 
/*  707 */       if (i != 5 && i != 3) {
/*  708 */         throw _constructUnexpectedInTyped(i);
/*      */       }
/*      */     } 
/*      */     
/*  712 */     return sb.toString();
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
/*      */   public int getEventType() {
/*  724 */     if (this.mCurrToken == 12 && (
/*  725 */       this.mCfgCoalesceText || this.mCfgReportTextAsChars)) {
/*  726 */       return 4;
/*      */     }
/*      */     
/*  729 */     return this.mCurrToken;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalName() {
/*  735 */     if (this.mCurrToken == 1 || this.mCurrToken == 2) {
/*  736 */       return this.mElementStack.getLocalName();
/*      */     }
/*  738 */     if (this.mCurrToken == 9)
/*      */     {
/*      */ 
/*      */       
/*  742 */       return (this.mCurrEntity == null) ? this.mCurrName : this.mCurrEntity.getName();
/*      */     }
/*  744 */     throw new IllegalStateException("Current state not START_ELEMENT, END_ELEMENT or ENTITY_REFERENCE");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName getName() {
/*  751 */     if (this.mCurrToken != 1 && this.mCurrToken != 2) {
/*  752 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_ELEM);
/*      */     }
/*  754 */     return this.mElementStack.getCurrentElementName();
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
/*      */   public NamespaceContext getNamespaceContext() {
/*  766 */     return this.mElementStack;
/*      */   }
/*      */   
/*      */   public int getNamespaceCount() {
/*  770 */     if (this.mCurrToken != 1 && this.mCurrToken != 2) {
/*  771 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_ELEM);
/*      */     }
/*  773 */     return this.mElementStack.getCurrentNsCount();
/*      */   }
/*      */   
/*      */   public String getNamespacePrefix(int index) {
/*  777 */     if (this.mCurrToken != 1 && this.mCurrToken != 2) {
/*  778 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_ELEM);
/*      */     }
/*      */     
/*  781 */     String p = this.mElementStack.getLocalNsPrefix(index);
/*  782 */     if (p == null) {
/*  783 */       return this.mReturnNullForDefaultNamespace ? null : "";
/*      */     }
/*  785 */     return p;
/*      */   }
/*      */   
/*      */   public String getNamespaceURI() {
/*  789 */     if (this.mCurrToken != 1 && this.mCurrToken != 2) {
/*  790 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_ELEM);
/*      */     }
/*      */     
/*  793 */     String uri = this.mElementStack.getNsURI();
/*  794 */     return (uri == null) ? "" : uri;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespaceURI(int index) {
/*  799 */     if (this.mCurrToken != 1 && this.mCurrToken != 2) {
/*  800 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_ELEM);
/*      */     }
/*      */     
/*  803 */     String uri = this.mElementStack.getLocalNsURI(index);
/*  804 */     return (uri == null) ? "" : uri;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespaceURI(String prefix) {
/*  809 */     if (this.mCurrToken != 1 && this.mCurrToken != 2) {
/*  810 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_ELEM);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  815 */     return this.mElementStack.getNamespaceURI(prefix);
/*      */   }
/*      */   
/*      */   public String getPIData() {
/*  819 */     if (this.mCurrToken != 3) {
/*  820 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_PI);
/*      */     }
/*  822 */     if (this.mTokenState <= 1) {
/*  823 */       safeFinishToken();
/*      */     }
/*  825 */     return this.mTextBuffer.contentsAsString();
/*      */   }
/*      */   
/*      */   public String getPITarget() {
/*  829 */     if (this.mCurrToken != 3) {
/*  830 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_PI);
/*      */     }
/*      */     
/*  833 */     return this.mCurrName;
/*      */   }
/*      */   
/*      */   public String getPrefix() {
/*  837 */     if (this.mCurrToken != 1 && this.mCurrToken != 2) {
/*  838 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_ELEM);
/*      */     }
/*      */     
/*  841 */     String p = this.mElementStack.getPrefix();
/*  842 */     return (p == null) ? "" : p;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getText() {
/*  847 */     if ((1 << this.mCurrToken & 0x1A70) == 0) {
/*  848 */       throwNotTextual(this.mCurrToken);
/*      */     }
/*  850 */     if (this.mTokenState < this.mStTextThreshold) {
/*  851 */       safeFinishToken();
/*      */     }
/*  853 */     if (this.mCurrToken == 9) {
/*  854 */       return (this.mCurrEntity == null) ? null : this.mCurrEntity.getReplacementText();
/*      */     }
/*  856 */     if (this.mCurrToken == 11)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  861 */       return getDTDInternalSubset();
/*      */     }
/*  863 */     return this.mTextBuffer.contentsAsString();
/*      */   }
/*      */ 
/*      */   
/*      */   public char[] getTextCharacters() {
/*  868 */     if ((1 << this.mCurrToken & 0x1070) == 0) {
/*  869 */       throwNotTextXxx(this.mCurrToken);
/*      */     }
/*  871 */     if (this.mTokenState < this.mStTextThreshold) {
/*  872 */       safeFinishToken();
/*      */     }
/*  874 */     if (this.mCurrToken == 9) {
/*  875 */       return this.mCurrEntity.getReplacementChars();
/*      */     }
/*  877 */     if (this.mCurrToken == 11) {
/*  878 */       return getDTDInternalSubsetArray();
/*      */     }
/*  880 */     return this.mTextBuffer.getTextBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int len) {
/*  885 */     if ((1 << this.mCurrToken & 0x1070) == 0) {
/*  886 */       throwNotTextXxx(this.mCurrToken);
/*      */     }
/*  888 */     if (this.mTokenState < this.mStTextThreshold) {
/*  889 */       safeFinishToken();
/*      */     }
/*  891 */     return this.mTextBuffer.contentsToArray(sourceStart, target, targetStart, len);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTextLength() {
/*  896 */     if ((1 << this.mCurrToken & 0x1070) == 0) {
/*  897 */       throwNotTextXxx(this.mCurrToken);
/*      */     }
/*  899 */     if (this.mTokenState < this.mStTextThreshold) {
/*  900 */       safeFinishToken();
/*      */     }
/*  902 */     return this.mTextBuffer.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTextStart() {
/*  907 */     if ((1 << this.mCurrToken & 0x1070) == 0) {
/*  908 */       throwNotTextXxx(this.mCurrToken);
/*      */     }
/*  910 */     if (this.mTokenState < this.mStTextThreshold) {
/*  911 */       safeFinishToken();
/*      */     }
/*  913 */     return this.mTextBuffer.getTextStart();
/*      */   }
/*      */   
/*      */   public boolean hasName() {
/*  917 */     return (this.mCurrToken == 1 || this.mCurrToken == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasNext() {
/*  924 */     return (this.mCurrToken != 8 || this.mParseState == 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasText() {
/*  929 */     return ((1 << this.mCurrToken & 0x1A70) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeSpecified(int index) {
/*  936 */     if (this.mCurrToken != 1) {
/*  937 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*  939 */     return this.mAttrCollector.isSpecified(index);
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
/*      */   public boolean isCharacters() {
/*  953 */     return (getEventType() == 4);
/*      */   }
/*      */   
/*      */   public boolean isEndElement() {
/*  957 */     return (this.mCurrToken == 2);
/*      */   }
/*      */   
/*      */   public boolean isStartElement() {
/*  961 */     return (this.mCurrToken == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWhiteSpace() {
/*  972 */     if (this.mCurrToken == 4 || this.mCurrToken == 12) {
/*  973 */       if (this.mTokenState < this.mStTextThreshold) {
/*  974 */         safeFinishToken();
/*      */       }
/*  976 */       if (this.mWsStatus == 0) {
/*  977 */         this.mWsStatus = this.mTextBuffer.isAllWhitespace() ? 1 : 2;
/*      */       }
/*      */       
/*  980 */       return (this.mWsStatus == 1);
/*      */     } 
/*  982 */     return (this.mCurrToken == 6);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void require(int type, String nsUri, String localName) throws XMLStreamException {
/*  988 */     int curr = this.mCurrToken;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  994 */     if (curr != type) {
/*  995 */       if (curr == 12) {
/*  996 */         if (this.mCfgCoalesceText || this.mCfgReportTextAsChars) {
/*  997 */           curr = 4;
/*      */         }
/*  999 */       } else if (curr == 6) {
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1006 */     if (type != curr) {
/* 1007 */       throwParseError("Expected type " + tokenTypeDesc(type) + ", current type " + tokenTypeDesc(curr));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1012 */     if (localName != null) {
/* 1013 */       if (curr != 1 && curr != 2 && curr != 9)
/*      */       {
/* 1015 */         throwParseError("Expected non-null local name, but current token not a START_ELEMENT, END_ELEMENT or ENTITY_REFERENCE (was " + tokenTypeDesc(this.mCurrToken) + ")");
/*      */       }
/* 1017 */       String n = getLocalName();
/* 1018 */       if (n != localName && !n.equals(localName)) {
/* 1019 */         throwParseError("Expected local name '" + localName + "'; current local name '" + n + "'.");
/*      */       }
/*      */     } 
/* 1022 */     if (nsUri != null) {
/* 1023 */       if (curr != 1 && curr != 2) {
/* 1024 */         throwParseError("Expected non-null NS URI, but current token not a START_ELEMENT or END_ELEMENT (was " + tokenTypeDesc(curr) + ")");
/*      */       }
/* 1026 */       String uri = this.mElementStack.getNsURI();
/*      */       
/* 1028 */       if (nsUri.length() == 0) {
/* 1029 */         if (uri != null && uri.length() > 0) {
/* 1030 */           throwParseError("Expected empty namespace, instead have '" + uri + "'.");
/*      */         }
/*      */       }
/* 1033 */       else if (nsUri != uri && !nsUri.equals(uri)) {
/* 1034 */         throwParseError("Expected namespace '" + nsUri + "'; have '" + uri + "'.");
/*      */       } 
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
/*      */   public final int next() throws XMLStreamException {
/* 1055 */     if (this.mPendingException != null) {
/* 1056 */       XMLStreamException strEx = this.mPendingException;
/* 1057 */       this.mPendingException = null;
/* 1058 */       throw strEx;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1064 */     if (this.mParseState == 1) {
/* 1065 */       int type = nextFromTree();
/* 1066 */       this.mCurrToken = type;
/* 1067 */       if (this.mTokenState < this.mStTextThreshold)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1072 */         if (!this.mCfgLazyParsing || (this.mValidateText && (type == 4 || type == 12)))
/*      */         {
/* 1074 */           finishToken(false);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1083 */       if (type == 12) {
/* 1084 */         if (this.mValidateText) {
/* 1085 */           this.mElementStack.validateText(this.mTextBuffer, false);
/*      */         }
/* 1087 */         if (this.mCfgCoalesceText || this.mCfgReportTextAsChars) {
/* 1088 */           return 4;
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1094 */       else if (type == 4 && 
/* 1095 */         this.mValidateText) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1103 */         if (this.mInputPtr + 1 < this.mInputEnd && this.mInputBuffer[this.mInputPtr] == '<' && this.mInputBuffer[this.mInputPtr + 1] == '/') {
/*      */ 
/*      */ 
/*      */           
/* 1107 */           this.mElementStack.validateText(this.mTextBuffer, true);
/*      */         } else {
/* 1109 */           this.mElementStack.validateText(this.mTextBuffer, false);
/*      */         } 
/*      */       } 
/*      */       
/* 1113 */       return type;
/*      */     } 
/*      */     
/* 1116 */     if (this.mParseState == 0) {
/* 1117 */       nextFromProlog(true);
/* 1118 */     } else if (this.mParseState == 2) {
/* 1119 */       if (nextFromProlog(false))
/*      */       {
/* 1121 */         this.mSecondaryToken = 0;
/*      */       }
/*      */     }
/* 1124 */     else if (this.mParseState == 3) {
/* 1125 */       this.mCurrToken = nextFromMultiDocState();
/*      */     } else {
/* 1127 */       if (this.mSecondaryToken == 8) {
/* 1128 */         this.mSecondaryToken = 0;
/* 1129 */         return 8;
/*      */       } 
/* 1131 */       throw new NoSuchElementException();
/*      */     } 
/* 1133 */     return this.mCurrToken;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int nextTag() throws XMLStreamException {
/*      */     while (true) {
/* 1140 */       int next = next();
/*      */       
/* 1142 */       switch (next) {
/*      */         case 3:
/*      */         case 5:
/*      */         case 6:
/*      */           continue;
/*      */         case 4:
/*      */         case 12:
/* 1149 */           if (isWhiteSpace()) {
/*      */             continue;
/*      */           }
/* 1152 */           throwParseError("Received non-all-whitespace CHARACTERS or CDATA event in nextTag().");
/*      */           break;
/*      */         case 1:
/*      */         case 2:
/* 1156 */           return next;
/*      */       } 
/* 1158 */       throwParseError("Received event " + ErrorConsts.tokenTypeDesc(next) + ", instead of START_ELEMENT or END_ELEMENT.");
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
/*      */   public void close() throws XMLStreamException {
/* 1173 */     if (this.mParseState != 4) {
/* 1174 */       this.mParseState = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1179 */       if (this.mCurrToken != 8) {
/* 1180 */         this.mCurrToken = this.mSecondaryToken = 8;
/* 1181 */         if (this.mSymbols.isDirty()) {
/* 1182 */           this.mOwner.updateSymbolTable(this.mSymbols);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1192 */       closeAllInput(false);
/*      */       
/* 1194 */       this.mTextBuffer.recycle(true);
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
/*      */   public Object getFeature(String name) {
/* 1209 */     throw new IllegalArgumentException(MessageFormat.format(ErrorConsts.ERR_UNKNOWN_FEATURE, new Object[] { name }));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String name, Object value) {
/* 1215 */     throw new IllegalArgumentException(MessageFormat.format(ErrorConsts.ERR_UNKNOWN_FEATURE, new Object[] { name }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPropertySupported(String name) {
/* 1222 */     return this.mConfig.isPropertySupported(name);
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
/*      */   public boolean setProperty(String name, Object value) {
/* 1234 */     boolean ok = this.mConfig.setProperty(name, value);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     if (ok && "com.ctc.wstx.baseURL".equals(name))
/*      */     {
/* 1241 */       this.mInput.overrideSource(this.mConfig.getBaseURL());
/*      */     }
/* 1243 */     return ok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skipElement() throws XMLStreamException {
/* 1250 */     if (this.mCurrToken != 1) {
/* 1251 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/* 1253 */     int nesting = 1;
/*      */     
/*      */     while (true) {
/* 1256 */       int type = next();
/* 1257 */       if (type == 1) {
/* 1258 */         nesting++; continue;
/* 1259 */       }  if (type == 2 && 
/* 1260 */         --nesting == 0) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeInfo getAttributeInfo() throws XMLStreamException {
/* 1271 */     if (this.mCurrToken != 1) {
/* 1272 */       throw new IllegalStateException(ErrorConsts.ERR_STATE_NOT_STELEM);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1278 */     return this.mElementStack;
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
/*      */   public DTDInfo getDTDInfo() throws XMLStreamException {
/* 1292 */     if (this.mCurrToken != 11) {
/* 1293 */       return null;
/*      */     }
/* 1295 */     if (this.mTokenState < 3) {
/* 1296 */       finishToken(false);
/*      */     }
/* 1298 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final LocationInfo getLocationInfo() {
/* 1307 */     return this;
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
/*      */   public int getText(Writer w, boolean preserveContents) throws IOException, XMLStreamException {
/* 1337 */     if ((1 << this.mCurrToken & 0x1A78) == 0) {
/* 1338 */       throwNotTextual(this.mCurrToken);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1345 */     if (!preserveContents) {
/* 1346 */       if (this.mCurrToken == 4) {
/* 1347 */         int count = this.mTextBuffer.rawContentsTo(w);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1353 */         this.mTextBuffer.resetWithEmpty();
/* 1354 */         if (this.mTokenState < 3) {
/* 1355 */           count += readAndWriteText(w);
/*      */         }
/* 1357 */         if (this.mCfgCoalesceText && this.mTokenState < 4)
/*      */         {
/* 1359 */           if (this.mCfgCoalesceText) {
/* 1360 */             count += readAndWriteCoalesced(w, false);
/*      */           }
/*      */         }
/* 1363 */         return count;
/* 1364 */       }  if (this.mCurrToken == 12) {
/* 1365 */         int count = this.mTextBuffer.rawContentsTo(w);
/* 1366 */         this.mTextBuffer.resetWithEmpty();
/* 1367 */         if (this.mTokenState < 3) {
/* 1368 */           count += readAndWriteCData(w);
/*      */         }
/* 1370 */         if (this.mCfgCoalesceText && this.mTokenState < 4)
/*      */         {
/* 1372 */           if (this.mCfgCoalesceText) {
/* 1373 */             count += readAndWriteCoalesced(w, true);
/*      */           }
/*      */         }
/* 1376 */         return count;
/*      */       } 
/*      */     } 
/* 1379 */     if (this.mTokenState < this.mStTextThreshold)
/*      */     {
/*      */ 
/*      */       
/* 1383 */       finishToken(false);
/*      */     }
/* 1385 */     if (this.mCurrToken == 9) {
/* 1386 */       return this.mCurrEntity.getReplacementText(w);
/*      */     }
/* 1388 */     if (this.mCurrToken == 11) {
/* 1389 */       char[] ch = getDTDInternalSubsetArray();
/* 1390 */       if (ch != null) {
/* 1391 */         w.write(ch);
/* 1392 */         return ch.length;
/*      */       } 
/* 1394 */       return 0;
/*      */     } 
/* 1396 */     return this.mTextBuffer.rawContentsTo(w);
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
/*      */   public int getDepth() {
/* 1411 */     return this.mElementStack.getDepth();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmptyElement() throws XMLStreamException {
/* 1421 */     return (this.mCurrToken == 1) ? this.mStEmptyElem : false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NamespaceContext getNonTransientNamespaceContext() {
/* 1427 */     return (NamespaceContext)this.mElementStack.createNonTransientNsContext(null);
/*      */   } public String getPrefixedName() {
/*      */     String prefix;
/*      */     String ln;
/*      */     StringBuffer sb;
/* 1432 */     switch (this.mCurrToken) {
/*      */       
/*      */       case 1:
/*      */       case 2:
/* 1436 */         prefix = this.mElementStack.getPrefix();
/* 1437 */         ln = this.mElementStack.getLocalName();
/*      */         
/* 1439 */         if (prefix == null) {
/* 1440 */           return ln;
/*      */         }
/* 1442 */         sb = new StringBuffer(ln.length() + 1 + prefix.length());
/* 1443 */         sb.append(prefix);
/* 1444 */         sb.append(':');
/* 1445 */         sb.append(ln);
/* 1446 */         return sb.toString();
/*      */       
/*      */       case 9:
/* 1449 */         return getLocalName();
/*      */       case 3:
/* 1451 */         return getPITarget();
/*      */       case 11:
/* 1453 */         return getDTDRootName();
/*      */     } 
/*      */     
/* 1456 */     throw new IllegalStateException("Current state not START_ELEMENT, END_ELEMENT, ENTITY_REFERENCE, PROCESSING_INSTRUCTION or DTD");
/*      */   }
/*      */ 
/*      */   
/*      */   public void closeCompletely() throws XMLStreamException {
/* 1461 */     closeAllInput(true);
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
/*      */   public Object getProcessedDTD() {
/* 1475 */     return null;
/*      */   }
/*      */   
/*      */   public String getDTDRootName() {
/* 1479 */     if (this.mRootPrefix == null) {
/* 1480 */       return this.mRootLName;
/*      */     }
/* 1482 */     return this.mRootPrefix + ":" + this.mRootLName;
/*      */   }
/*      */   
/*      */   public String getDTDPublicId() {
/* 1486 */     return this.mDtdPublicId;
/*      */   }
/*      */   
/*      */   public String getDTDSystemId() {
/* 1490 */     return this.mDtdSystemId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDTDInternalSubset() {
/* 1498 */     if (this.mCurrToken != 11) {
/* 1499 */       return null;
/*      */     }
/* 1501 */     return this.mTextBuffer.contentsAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char[] getDTDInternalSubsetArray() {
/* 1512 */     return this.mTextBuffer.contentsAsArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTDValidationSchema getProcessedDTDSchema() {
/* 1521 */     return null;
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
/*      */   public long getStartingByteOffset() {
/* 1537 */     return -1L;
/*      */   }
/*      */   
/*      */   public long getStartingCharOffset() {
/* 1541 */     return this.mTokenInputTotal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getEndingByteOffset() throws XMLStreamException {
/* 1550 */     return -1L;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getEndingCharOffset() throws XMLStreamException {
/* 1556 */     if (this.mTokenState < this.mStTextThreshold) {
/* 1557 */       finishToken(false);
/*      */     }
/* 1559 */     return this.mCurrInputProcessed + this.mInputPtr;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final Location getLocation() {
/* 1565 */     return (Location)getStartLocation();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final XMLStreamLocation2 getEndLocation() throws XMLStreamException {
/* 1575 */     if (this.mTokenState < this.mStTextThreshold) {
/* 1576 */       finishToken(false);
/*      */     }
/*      */     
/* 1579 */     return getCurrentLocation();
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
/*      */   public XMLValidator validateAgainst(XMLValidationSchema schema) throws XMLStreamException {
/* 1592 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLValidator stopValidatingAgainst(XMLValidationSchema schema) throws XMLStreamException {
/* 1599 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLValidator stopValidatingAgainst(XMLValidator validator) throws XMLStreamException {
/* 1606 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ValidationProblemHandler setValidationProblemHandler(ValidationProblemHandler h) {
/* 1612 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityDecl getCurrentEntityDecl() {
/* 1622 */     return this.mCurrEntity;
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
/*      */   public Object withStartElement(ElemCallback cb, Location loc) {
/* 1635 */     if (this.mCurrToken != 1) {
/* 1636 */       return null;
/*      */     }
/* 1638 */     return cb.withStartElement(loc, getName(), this.mElementStack.createNonTransientNsContext(loc), this.mAttrCollector.buildAttrOb(), this.mStEmptyElem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNamespaceAware() {
/* 1645 */     return this.mCfgNsEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputElementStack getInputElementStack() {
/* 1654 */     return this.mElementStack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeCollector getAttributeCollector() {
/* 1663 */     return this.mAttrCollector;
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
/*      */   public void fireSaxStartElement(ContentHandler h, Attributes attrs) throws SAXException {
/* 1675 */     if (h != null) {
/*      */       
/* 1677 */       int nsCount = this.mElementStack.getCurrentNsCount();
/* 1678 */       for (int i = 0; i < nsCount; i++) {
/* 1679 */         String prefix = this.mElementStack.getLocalNsPrefix(i);
/* 1680 */         String str1 = this.mElementStack.getLocalNsURI(i);
/* 1681 */         h.startPrefixMapping((prefix == null) ? "" : prefix, str1);
/*      */       } 
/*      */ 
/*      */       
/* 1685 */       String uri = this.mElementStack.getNsURI();
/*      */       
/* 1687 */       h.startElement((uri == null) ? "" : uri, this.mElementStack.getLocalName(), getPrefixedName(), attrs);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fireSaxEndElement(ContentHandler h) throws SAXException {
/* 1695 */     if (h != null) {
/*      */ 
/*      */ 
/*      */       
/* 1699 */       String uri = this.mElementStack.getNsURI();
/*      */       
/* 1701 */       h.endElement((uri == null) ? "" : uri, this.mElementStack.getLocalName(), getPrefixedName());
/*      */ 
/*      */       
/* 1704 */       int nsCount = this.mElementStack.getCurrentNsCount();
/* 1705 */       for (int i = 0; i < nsCount; i++) {
/* 1706 */         String prefix = this.mElementStack.getLocalNsPrefix(i);
/*      */         
/* 1708 */         h.endPrefixMapping((prefix == null) ? "" : prefix);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fireSaxCharacterEvents(ContentHandler h) throws XMLStreamException, SAXException {
/* 1716 */     if (h != null) {
/* 1717 */       if (this.mPendingException != null) {
/* 1718 */         XMLStreamException sex = this.mPendingException;
/* 1719 */         this.mPendingException = null;
/* 1720 */         throw sex;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1725 */       if (this.mTokenState < this.mStTextThreshold) {
/* 1726 */         finishToken(false);
/*      */       }
/* 1728 */       this.mTextBuffer.fireSaxCharacterEvents(h);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fireSaxSpaceEvents(ContentHandler h) throws XMLStreamException, SAXException {
/* 1735 */     if (h != null) {
/* 1736 */       if (this.mTokenState < this.mStTextThreshold) {
/* 1737 */         finishToken(false);
/*      */       }
/* 1739 */       this.mTextBuffer.fireSaxSpaceEvents(h);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fireSaxCommentEvent(LexicalHandler h) throws XMLStreamException, SAXException {
/* 1746 */     if (h != null) {
/* 1747 */       if (this.mTokenState < this.mStTextThreshold) {
/* 1748 */         finishToken(false);
/*      */       }
/* 1750 */       this.mTextBuffer.fireSaxCommentEvent(h);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fireSaxPIEvent(ContentHandler h) throws XMLStreamException, SAXException {
/* 1757 */     if (h != null) {
/* 1758 */       if (this.mTokenState < this.mStTextThreshold) {
/* 1759 */         finishToken(false);
/*      */       }
/* 1761 */       h.processingInstruction(this.mCurrName, this.mTextBuffer.contentsAsString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean hasConfigFlags(int flags) {
/* 1772 */     return ((this.mConfigFlags & flags) == flags);
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
/*      */   protected String checkKeyword(char c, String expected) throws XMLStreamException {
/* 1788 */     int ptr = 0;
/* 1789 */     int len = expected.length();
/*      */     
/* 1791 */     while (expected.charAt(ptr) == c && ++ptr < len) {
/* 1792 */       if (this.mInputPtr < this.mInputEnd) {
/* 1793 */         c = this.mInputBuffer[this.mInputPtr++]; continue;
/*      */       } 
/* 1795 */       int ci = getNext();
/* 1796 */       if (ci < 0) {
/*      */         break;
/*      */       }
/* 1799 */       c = (char)ci;
/*      */     } 
/*      */ 
/*      */     
/* 1803 */     if (ptr == len) {
/*      */       
/* 1805 */       int i = peekNext();
/* 1806 */       if (i < 0 || (!isNameChar((char)i) && i != 58)) {
/* 1807 */         return null;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1812 */     StringBuffer sb = new StringBuffer(expected.length() + 16);
/* 1813 */     sb.append(expected.substring(0, ptr));
/* 1814 */     if (ptr < len) {
/* 1815 */       sb.append(c);
/*      */     }
/*      */     
/*      */     while (true) {
/* 1819 */       if (this.mInputPtr < this.mInputEnd) {
/* 1820 */         c = this.mInputBuffer[this.mInputPtr++];
/*      */       } else {
/* 1822 */         int ci = getNext();
/* 1823 */         if (ci < 0) {
/*      */           break;
/*      */         }
/* 1826 */         c = (char)ci;
/*      */       } 
/* 1828 */       if (!isNameChar(c)) {
/*      */         
/* 1830 */         this.mInputPtr--;
/*      */         break;
/*      */       } 
/* 1833 */       sb.append(c);
/*      */     } 
/*      */     
/* 1836 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkCData() throws XMLStreamException {
/* 1842 */     String wrong = checkKeyword(getNextCharFromCurrent(" in CDATA section"), "CDATA");
/* 1843 */     if (wrong != null) {
/* 1844 */       throwParseError("Unrecognized XML directive '" + wrong + "'; expected 'CDATA'.");
/*      */     }
/*      */     
/* 1847 */     char c = getNextCharFromCurrent(" in CDATA section");
/* 1848 */     if (c != '[') {
/* 1849 */       throwUnexpectedChar(c, "excepted '[' after '<![CDATA'");
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
/*      */   private final void parseAttrValue(char openingQuote, TextBuilder tb) throws XMLStreamException {
/* 1867 */     char[] outBuf = tb.getCharBuffer();
/* 1868 */     int outPtr = tb.getCharSize();
/* 1869 */     int outLen = outBuf.length;
/* 1870 */     WstxInputSource currScope = this.mInput;
/*      */     
/*      */     while (true) {
/* 1873 */       char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextChar(" in attribute value");
/*      */ 
/*      */       
/* 1876 */       if (c <= '\'') {
/* 1877 */         if (c < ' ') {
/* 1878 */           if (c == '\n') {
/* 1879 */             markLF();
/* 1880 */           } else if (c == '\r') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1889 */             if (this.mNormalizeLFs) {
/* 1890 */               c = getNextChar(" in attribute value");
/* 1891 */               if (c != '\n') {
/* 1892 */                 this.mInputPtr--;
/*      */               }
/*      */             } 
/* 1895 */             markLF();
/* 1896 */           } else if (c != '\t') {
/* 1897 */             throwInvalidSpace(c);
/*      */           } 
/*      */           
/* 1900 */           c = ' ';
/* 1901 */         } else if (c == openingQuote) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1907 */           if (this.mInput == currScope) {
/*      */             break;
/*      */           }
/* 1910 */         } else if (c == '&') {
/*      */           int ch;
/* 1912 */           if (inputInBuffer() < 3 || (ch = resolveSimpleEntity(true)) == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1917 */             ch = fullyResolveEntity(false);
/* 1918 */             if (ch == 0) {
/*      */               continue;
/*      */             }
/*      */           } 
/*      */           
/* 1923 */           if (ch <= 65535) {
/* 1924 */             c = (char)ch;
/*      */           } else {
/* 1926 */             ch -= 65536;
/* 1927 */             if (outPtr >= outLen) {
/* 1928 */               outBuf = tb.bufferFull(1);
/* 1929 */               outLen = outBuf.length;
/*      */             } 
/* 1931 */             outBuf[outPtr++] = (char)((ch >> 10) + 55296);
/* 1932 */             c = (char)((ch & 0x3FF) + 56320);
/*      */           } 
/*      */         } 
/* 1935 */       } else if (c == '<') {
/* 1936 */         throwParseError("Unexpected '<'  in attribute value");
/*      */       } 
/*      */ 
/*      */       
/* 1940 */       if (outPtr >= outLen) {
/* 1941 */         outBuf = tb.bufferFull(1);
/* 1942 */         outLen = outBuf.length;
/*      */       } 
/* 1944 */       outBuf[outPtr++] = c;
/*      */     } 
/*      */ 
/*      */     
/* 1948 */     tb.setBufferSize(outPtr);
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
/*      */   private boolean nextFromProlog(boolean isProlog) throws XMLStreamException {
/*      */     int i;
/* 1971 */     if (this.mTokenState < this.mStTextThreshold) {
/* 1972 */       this.mTokenState = 4;
/* 1973 */       i = skipToken();
/*      */     }
/*      */     else {
/*      */       
/* 1977 */       this.mTokenInputTotal = this.mCurrInputProcessed + this.mInputPtr;
/* 1978 */       this.mTokenInputRow = this.mCurrInputRow;
/* 1979 */       this.mTokenInputCol = this.mInputPtr - this.mCurrInputRowStart;
/* 1980 */       i = getNext();
/*      */     } 
/*      */ 
/*      */     
/* 1984 */     if (i <= 32 && i >= 0) {
/*      */       
/* 1986 */       if (hasConfigFlags(256)) {
/* 1987 */         this.mCurrToken = 6;
/* 1988 */         if (readSpacePrimary((char)i, true)) {
/*      */ 
/*      */ 
/*      */           
/* 1992 */           this.mTokenState = 4;
/*      */         }
/* 1994 */         else if (this.mCfgLazyParsing) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1999 */           this.mTokenState = 1;
/*      */         } else {
/* 2001 */           readSpaceSecondary(true);
/* 2002 */           this.mTokenState = 4;
/*      */         } 
/*      */         
/* 2005 */         return false;
/*      */       } 
/*      */       
/* 2008 */       this.mInputPtr--;
/* 2009 */       i = getNextAfterWS();
/* 2010 */       if (i >= 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2017 */         this.mTokenInputTotal = this.mCurrInputProcessed + this.mInputPtr - 1L;
/* 2018 */         this.mTokenInputRow = this.mCurrInputRow;
/* 2019 */         this.mTokenInputCol = this.mInputPtr - this.mCurrInputRowStart - 1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2024 */     if (i < 0) {
/* 2025 */       handleEOF(isProlog);
/* 2026 */       this.mParseState = 4;
/* 2027 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2031 */     if (i != 60) {
/* 2032 */       throwUnexpectedChar(i, (isProlog ? " in prolog" : " in epilog") + "; expected '<'");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2037 */     char c = getNextChar(isProlog ? " in prolog" : " in epilog");
/*      */     
/* 2039 */     if (c == '?') {
/* 2040 */       this.mCurrToken = readPIPrimary();
/* 2041 */     } else if (c == '!') {
/*      */       
/* 2043 */       nextFromPrologBang(isProlog);
/* 2044 */     } else if (c == '/') {
/* 2045 */       if (isProlog) {
/* 2046 */         throwParseError("Unexpected character combination '</' in prolog.");
/*      */       }
/* 2048 */       throwParseError("Unexpected character combination '</' in epilog (extra close tag?).");
/* 2049 */     } else if (c == ':' || isNameStartChar(c)) {
/*      */       
/* 2051 */       if (!isProlog) {
/*      */ 
/*      */ 
/*      */         
/* 2055 */         this.mCurrToken = handleExtraRoot(c);
/* 2056 */         return false;
/*      */       } 
/* 2058 */       handleRootElem(c);
/* 2059 */       this.mCurrToken = 1;
/*      */     } else {
/* 2061 */       throwUnexpectedChar(c, (isProlog ? " in prolog" : " in epilog") + ", after '<'.");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2066 */     if (!this.mCfgLazyParsing && this.mTokenState < this.mStTextThreshold) {
/* 2067 */       finishToken(false);
/*      */     }
/*      */     
/* 2070 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleRootElem(char c) throws XMLStreamException {
/* 2076 */     this.mParseState = 1;
/* 2077 */     initValidation();
/* 2078 */     handleStartElem(c);
/*      */ 
/*      */     
/* 2081 */     if (this.mRootLName != null && 
/* 2082 */       hasConfigFlags(32) && 
/* 2083 */       !this.mElementStack.matches(this.mRootPrefix, this.mRootLName)) {
/* 2084 */       String actual = (this.mRootPrefix == null) ? this.mRootLName : (this.mRootPrefix + ":" + this.mRootLName);
/*      */       
/* 2086 */       reportValidationProblem(ErrorConsts.ERR_VLD_WRONG_ROOT, actual, this.mRootLName);
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
/*      */   protected void initValidation() throws XMLStreamException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int handleEOF(boolean isProlog) throws XMLStreamException {
/* 2110 */     this.mCurrToken = this.mSecondaryToken = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2116 */     this.mTextBuffer.recycle(true);
/*      */     
/* 2118 */     if (isProlog) {
/* 2119 */       throwUnexpectedEOF(" in prolog");
/*      */     }
/* 2121 */     return this.mCurrToken;
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
/*      */   private int handleExtraRoot(char c) throws XMLStreamException {
/* 2135 */     if (!this.mConfig.inputParsingModeDocuments())
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 2140 */       throwParseError("Illegal to have multiple roots (start tag in epilog?).");
/*      */     }
/*      */     
/* 2143 */     this.mInputPtr--;
/* 2144 */     return handleMultiDocStart(1);
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
/*      */   protected int handleMultiDocStart(int nextEvent) {
/* 2157 */     this.mParseState = 3;
/* 2158 */     this.mTokenState = 4;
/* 2159 */     this.mSecondaryToken = nextEvent;
/* 2160 */     return 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nextFromMultiDocState() throws XMLStreamException {
/* 2171 */     if (this.mCurrToken == 8) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2176 */       if (this.mSecondaryToken == 7) {
/* 2177 */         handleMultiDocXmlDecl();
/*      */       } else {
/* 2179 */         this.mDocXmlEncoding = null;
/* 2180 */         this.mDocXmlVersion = 0;
/* 2181 */         this.mDocStandalone = 0;
/*      */       } 
/* 2183 */       return 7;
/*      */     } 
/* 2185 */     if (this.mCurrToken == 7) {
/* 2186 */       this.mParseState = 0;
/*      */ 
/*      */       
/* 2189 */       if (this.mSecondaryToken == 7) {
/* 2190 */         nextFromProlog(true);
/* 2191 */         return this.mCurrToken;
/*      */       } 
/*      */       
/* 2194 */       if (this.mSecondaryToken == 1) {
/* 2195 */         handleRootElem(getNextChar(" in start tag"));
/* 2196 */         return 1;
/*      */       } 
/* 2198 */       if (this.mSecondaryToken == 11) {
/* 2199 */         this.mStDoctypeFound = true;
/* 2200 */         startDTD();
/* 2201 */         return 11;
/*      */       } 
/*      */     } 
/* 2204 */     throw new IllegalStateException("Internal error: unexpected state; current event " + tokenTypeDesc(this.mCurrToken) + ", sec. state: " + tokenTypeDesc(this.mSecondaryToken));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleMultiDocXmlDecl() throws XMLStreamException {
/* 2212 */     this.mDocStandalone = 0;
/* 2213 */     this.mDocXmlEncoding = null;
/*      */     
/* 2215 */     char c = getNextInCurrAfterWS(" in xml declaration");
/* 2216 */     String wrong = checkKeyword(c, "version");
/* 2217 */     if (wrong != null) {
/* 2218 */       throwParseError(ErrorConsts.ERR_UNEXP_KEYWORD, wrong, "version");
/*      */     }
/* 2220 */     c = skipEquals("version", " in xml declaration");
/* 2221 */     TextBuffer tb = this.mTextBuffer;
/* 2222 */     tb.resetInitialized();
/* 2223 */     parseQuoted("version", c, tb);
/*      */     
/* 2225 */     if (tb.equalsString("1.0")) {
/* 2226 */       this.mDocXmlVersion = 256;
/* 2227 */       this.mXml11 = false;
/* 2228 */     } else if (tb.equalsString("1.1")) {
/* 2229 */       this.mDocXmlVersion = 272;
/* 2230 */       this.mXml11 = true;
/*      */     } else {
/* 2232 */       this.mDocXmlVersion = 0;
/* 2233 */       this.mXml11 = false;
/* 2234 */       throwParseError("Unexpected xml version '" + tb.toString() + "'; expected '" + "1.0" + "' or '" + "1.1" + "'");
/*      */     } 
/*      */     
/* 2237 */     c = getNextInCurrAfterWS(" in xml declaration");
/*      */     
/* 2239 */     if (c != '?') {
/* 2240 */       if (c == 'e') {
/* 2241 */         wrong = checkKeyword(c, "encoding");
/* 2242 */         if (wrong != null) {
/* 2243 */           throwParseError(ErrorConsts.ERR_UNEXP_KEYWORD, wrong, "encoding");
/*      */         }
/* 2245 */         c = skipEquals("encoding", " in xml declaration");
/* 2246 */         tb.resetWithEmpty();
/* 2247 */         parseQuoted("encoding", c, tb);
/* 2248 */         this.mDocXmlEncoding = tb.toString();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2253 */         c = getNextInCurrAfterWS(" in xml declaration");
/* 2254 */       } else if (c != 's') {
/* 2255 */         throwUnexpectedChar(c, " in xml declaration; expected either 'encoding' or 'standalone' pseudo-attribute");
/*      */       } 
/*      */ 
/*      */       
/* 2259 */       if (c == 's') {
/* 2260 */         wrong = checkKeyword(c, "standalone");
/* 2261 */         if (wrong != null) {
/* 2262 */           throwParseError(ErrorConsts.ERR_UNEXP_KEYWORD, wrong, "standalone");
/*      */         }
/* 2264 */         c = skipEquals("standalone", " in xml declaration");
/* 2265 */         tb.resetWithEmpty();
/* 2266 */         parseQuoted("standalone", c, tb);
/* 2267 */         if (tb.equalsString("yes")) {
/* 2268 */           this.mDocStandalone = 1;
/* 2269 */         } else if (tb.equalsString("no")) {
/* 2270 */           this.mDocStandalone = 2;
/*      */         } else {
/* 2272 */           throwParseError("Unexpected xml 'standalone' pseudo-attribute value '" + tb.toString() + "'; expected '" + "yes" + "' or '" + "no" + "'");
/*      */         } 
/*      */ 
/*      */         
/* 2276 */         c = getNextInCurrAfterWS(" in xml declaration");
/*      */       } 
/*      */     } 
/*      */     
/* 2280 */     if (c != '?') {
/* 2281 */       throwUnexpectedChar(c, " in xml declaration; expected '?>' as the end marker");
/*      */     }
/* 2283 */     c = getNextCharFromCurrent(" in xml declaration");
/* 2284 */     if (c != '>') {
/* 2285 */       throwUnexpectedChar(c, " in xml declaration; expected '>' to close the declaration");
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
/*      */   protected final char skipEquals(String name, String eofMsg) throws XMLStreamException {
/* 2298 */     char c = getNextInCurrAfterWS(eofMsg);
/* 2299 */     if (c != '=') {
/* 2300 */       throwUnexpectedChar(c, " in xml declaration; expected '=' to follow pseudo-attribute '" + name + "'");
/*      */     }
/*      */     
/* 2303 */     return getNextInCurrAfterWS(eofMsg);
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
/*      */   protected final void parseQuoted(String name, char quoteChar, TextBuffer tbuf) throws XMLStreamException {
/* 2320 */     if (quoteChar != '"' && quoteChar != '\'') {
/* 2321 */       throwUnexpectedChar(quoteChar, " in xml declaration; waited ' or \" to start a value for pseudo-attribute '" + name + "'");
/*      */     }
/* 2323 */     char[] outBuf = tbuf.getCurrentSegment();
/* 2324 */     int outPtr = 0;
/*      */     
/*      */     while (true) {
/* 2327 */       char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextChar(" in xml declaration");
/*      */ 
/*      */       
/* 2330 */       if (c == quoteChar) {
/*      */         break;
/*      */       }
/* 2333 */       if (c < ' ' || c == '<') {
/* 2334 */         throwUnexpectedChar(c, " in xml declaration");
/* 2335 */       } else if (c == '\000') {
/* 2336 */         throwNullChar();
/*      */       } 
/* 2338 */       if (outPtr >= outBuf.length) {
/* 2339 */         outBuf = tbuf.finishCurrentSegment();
/* 2340 */         outPtr = 0;
/*      */       } 
/* 2342 */       outBuf[outPtr++] = c;
/*      */     } 
/* 2344 */     tbuf.setCurrentLength(outPtr);
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
/*      */   private void nextFromPrologBang(boolean isProlog) throws XMLStreamException {
/* 2356 */     int i = getNext();
/* 2357 */     if (i < 0) {
/* 2358 */       throwUnexpectedEOF(" in prolog");
/*      */     }
/* 2360 */     if (i == 68) {
/* 2361 */       String keyw = checkKeyword('D', "DOCTYPE");
/* 2362 */       if (keyw != null) {
/* 2363 */         throwParseError("Unrecognized XML directive '<!" + keyw + "' (misspelled DOCTYPE?).");
/*      */       }
/*      */       
/* 2366 */       if (!isProlog)
/*      */       {
/* 2368 */         if (this.mConfig.inputParsingModeDocuments()) {
/* 2369 */           if (!this.mStDoctypeFound) {
/* 2370 */             this.mCurrToken = handleMultiDocStart(11);
/*      */             return;
/*      */           } 
/*      */         } else {
/* 2374 */           throwParseError(ErrorConsts.ERR_DTD_IN_EPILOG);
/*      */         } 
/*      */       }
/* 2377 */       if (this.mStDoctypeFound) {
/* 2378 */         throwParseError(ErrorConsts.ERR_DTD_DUP);
/*      */       }
/* 2380 */       this.mStDoctypeFound = true;
/*      */       
/* 2382 */       this.mCurrToken = 11;
/* 2383 */       startDTD(); return;
/*      */     } 
/* 2385 */     if (i == 45) {
/* 2386 */       char c = getNextChar(isProlog ? " in prolog" : " in epilog");
/* 2387 */       if (c != '-') {
/* 2388 */         throwUnexpectedChar(i, " (malformed comment?)");
/*      */       }
/*      */       
/* 2391 */       this.mTokenState = 1;
/* 2392 */       this.mCurrToken = 5; return;
/*      */     } 
/* 2394 */     if (i == 91) {
/* 2395 */       i = peekNext();
/*      */       
/* 2397 */       if (i == 67) {
/* 2398 */         throwUnexpectedChar(i, ErrorConsts.ERR_CDATA_IN_EPILOG);
/*      */       }
/*      */     } 
/*      */     
/* 2402 */     throwUnexpectedChar(i, " after '<!' (malformed comment?)");
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
/*      */   private void startDTD() throws XMLStreamException {
/* 2416 */     this.mTextBuffer.resetInitialized();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2423 */     char c = getNextInCurrAfterWS(" in DOCTYPE declaration");
/* 2424 */     if (this.mCfgNsEnabled) {
/* 2425 */       String str = parseLocalName(c);
/* 2426 */       c = getNextChar(" in DOCTYPE declaration");
/* 2427 */       if (c == ':') {
/* 2428 */         this.mRootPrefix = str;
/* 2429 */         this.mRootLName = parseLocalName(getNextChar("; expected an identifier"));
/* 2430 */       } else if (c <= ' ' || c == '[' || c == '>') {
/*      */         
/* 2432 */         this.mInputPtr--;
/* 2433 */         this.mRootPrefix = null;
/* 2434 */         this.mRootLName = str;
/*      */       } else {
/* 2436 */         throwUnexpectedChar(c, " in DOCTYPE declaration; expected '[' or white space.");
/*      */       } 
/*      */     } else {
/* 2439 */       this.mRootLName = parseFullName(c);
/* 2440 */       this.mRootPrefix = null;
/*      */     } 
/*      */ 
/*      */     
/* 2444 */     c = getNextInCurrAfterWS(" in DOCTYPE declaration");
/* 2445 */     if (c != '[' && c != '>') {
/* 2446 */       String keyw = null;
/*      */       
/* 2448 */       if (c == 'P') {
/* 2449 */         keyw = checkKeyword(getNextChar(" in DOCTYPE declaration"), "UBLIC");
/* 2450 */         if (keyw != null) {
/* 2451 */           keyw = "P" + keyw;
/*      */         } else {
/* 2453 */           if (!skipWS(getNextChar(" in DOCTYPE declaration"))) {
/* 2454 */             throwUnexpectedChar(c, " in DOCTYPE declaration; expected a space between PUBLIC keyword and public id");
/*      */           }
/* 2456 */           c = getNextCharFromCurrent(" in DOCTYPE declaration");
/* 2457 */           if (c != '"' && c != '\'') {
/* 2458 */             throwUnexpectedChar(c, " in DOCTYPE declaration; expected a public identifier.");
/*      */           }
/* 2460 */           this.mDtdPublicId = parsePublicId(c, " in DOCTYPE declaration");
/* 2461 */           if (this.mDtdPublicId.length() == 0);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2466 */           if (!skipWS(getNextChar(" in DOCTYPE declaration"))) {
/* 2467 */             throwUnexpectedChar(c, " in DOCTYPE declaration; expected a space between public and system identifiers");
/*      */           }
/* 2469 */           c = getNextCharFromCurrent(" in DOCTYPE declaration");
/* 2470 */           if (c != '"' && c != '\'') {
/* 2471 */             throwParseError(" in DOCTYPE declaration; expected a system identifier.");
/*      */           }
/* 2473 */           this.mDtdSystemId = parseSystemId(c, this.mNormalizeLFs, " in DOCTYPE declaration");
/* 2474 */           if (this.mDtdSystemId.length() == 0);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 2480 */       else if (c == 'S') {
/* 2481 */         this.mDtdPublicId = null;
/* 2482 */         keyw = checkKeyword(getNextChar(" in DOCTYPE declaration"), "YSTEM");
/* 2483 */         if (keyw != null) {
/* 2484 */           keyw = "S" + keyw;
/*      */         } else {
/* 2486 */           c = getNextInCurrAfterWS(" in DOCTYPE declaration");
/* 2487 */           if (c != '"' && c != '\'') {
/* 2488 */             throwUnexpectedChar(c, " in DOCTYPE declaration; expected a system identifier.");
/*      */           }
/* 2490 */           this.mDtdSystemId = parseSystemId(c, this.mNormalizeLFs, " in DOCTYPE declaration");
/* 2491 */           if (this.mDtdSystemId.length() == 0)
/*      */           {
/* 2493 */             this.mDtdSystemId = null;
/*      */           }
/*      */         }
/*      */       
/* 2497 */       } else if (!isNameStartChar(c)) {
/* 2498 */         throwUnexpectedChar(c, " in DOCTYPE declaration; expected keywords 'PUBLIC' or 'SYSTEM'.");
/*      */       } else {
/* 2500 */         this.mInputPtr--;
/* 2501 */         keyw = checkKeyword(c, "SYSTEM");
/*      */       } 
/*      */ 
/*      */       
/* 2505 */       if (keyw != null) {
/* 2506 */         throwParseError("Unexpected keyword '" + keyw + "'; expected 'PUBLIC' or 'SYSTEM'");
/*      */       }
/*      */ 
/*      */       
/* 2510 */       c = getNextInCurrAfterWS(" in DOCTYPE declaration");
/*      */     } 
/*      */     
/* 2513 */     if (c != '[')
/*      */     {
/*      */       
/* 2516 */       if (c != '>') {
/* 2517 */         throwUnexpectedChar(c, " in DOCTYPE declaration; expected closing '>'.");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2525 */     this.mInputPtr--;
/* 2526 */     this.mTokenState = 1;
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
/*      */   protected void finishDTD(boolean copyContents) throws XMLStreamException {
/* 2549 */     char c = getNextChar(" in DOCTYPE declaration");
/* 2550 */     if (c == '[') {
/*      */       
/* 2552 */       if (copyContents) {
/* 2553 */         ((BranchingReaderSource)this.mInput).startBranch(this.mTextBuffer, this.mInputPtr, this.mNormalizeLFs);
/*      */       }
/*      */       
/*      */       try {
/* 2557 */         MinimalDTDReader.skipInternalSubset(this, this.mInput, this.mConfig);
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 2562 */         if (copyContents)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2567 */           ((BranchingReaderSource)this.mInput).endBranch(this.mInputPtr - 1);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2572 */       c = getNextCharAfterWS(" in internal DTD subset");
/*      */     } 
/*      */     
/* 2575 */     if (c != '>') {
/* 2576 */       throwUnexpectedChar(c, "; expected '>' to finish DOCTYPE declaration.");
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
/*      */   private final int nextFromTree() throws XMLStreamException {
/*      */     int i;
/* 2596 */     if (this.mTokenState < this.mStTextThreshold) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2602 */       if (this.mVldContent == 3 && (
/* 2603 */         this.mCurrToken == 4 || this.mCurrToken == 12)) {
/* 2604 */         throwParseError("Internal error: skipping validatable text");
/*      */       }
/*      */       
/* 2607 */       i = skipToken();
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2613 */       if (this.mCurrToken == 1) {
/*      */         
/* 2615 */         if (this.mStEmptyElem) {
/*      */           
/* 2617 */           this.mStEmptyElem = false;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2622 */           int vld = this.mElementStack.validateEndElement();
/* 2623 */           this.mVldContent = vld;
/* 2624 */           this.mValidateText = (vld == 3);
/* 2625 */           return 2;
/*      */         } 
/* 2627 */       } else if (this.mCurrToken == 2) {
/*      */         
/* 2629 */         if (!this.mElementStack.pop())
/*      */         {
/* 2631 */           if (!this.mConfig.inputParsingModeFragment()) {
/* 2632 */             return closeContentTree();
/*      */           }
/*      */         }
/*      */       }
/* 2636 */       else if (this.mCurrToken == 12 && this.mTokenState <= 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2647 */         this.mTokenInputTotal = this.mCurrInputProcessed + this.mInputPtr;
/* 2648 */         this.mTokenInputRow = this.mCurrInputRow;
/* 2649 */         this.mTokenInputCol = this.mInputPtr - this.mCurrInputRowStart;
/* 2650 */         char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextChar(" in CDATA section");
/*      */         
/* 2652 */         if (readCDataPrimary(c)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2658 */           if (this.mTextBuffer.size() > 0) {
/* 2659 */             return 12;
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/* 2667 */         else if (this.mTextBuffer.size() == 0 && readCDataSecondary(this.mCfgLazyParsing ? 1 : this.mShortestTextSegment)) {
/*      */ 
/*      */ 
/*      */           
/* 2671 */           if (this.mTextBuffer.size() > 0) {
/*      */             
/* 2673 */             this.mTokenState = 3;
/* 2674 */             return 12;
/*      */           } 
/*      */         } else {
/*      */           
/* 2678 */           this.mTokenState = 2;
/* 2679 */           return 12;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2688 */       this.mTokenInputTotal = this.mCurrInputProcessed + this.mInputPtr;
/* 2689 */       this.mTokenInputRow = this.mCurrInputRow;
/* 2690 */       this.mTokenInputCol = this.mInputPtr - this.mCurrInputRowStart;
/* 2691 */       i = getNext();
/*      */     } 
/*      */     
/* 2694 */     if (i < 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2699 */       if (!this.mElementStack.isEmpty()) {
/* 2700 */         throwUnexpectedEOF();
/*      */       }
/* 2702 */       return handleEOF(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2710 */     while (i == 38) {
/* 2711 */       this.mWsStatus = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2716 */       if (this.mVldContent == 0)
/*      */       {
/*      */ 
/*      */         
/* 2720 */         reportInvalidContent(9);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2726 */       int ch = this.mCfgReplaceEntities ? fullyResolveEntity(true) : resolveCharOnlyEntity(true);
/*      */ 
/*      */       
/* 2729 */       if (ch != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2736 */         if (this.mVldContent <= 1)
/*      */         {
/* 2738 */           if (ch > 32)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2747 */             reportInvalidContent(4);
/*      */           }
/*      */         }
/* 2750 */         TextBuffer tb = this.mTextBuffer;
/* 2751 */         tb.resetInitialized();
/* 2752 */         if (ch <= 65535) {
/* 2753 */           tb.append((char)ch);
/*      */         } else {
/* 2755 */           ch -= 65536;
/* 2756 */           tb.append((char)((ch >> 10) + 55296));
/* 2757 */           tb.append((char)((ch & 0x3FF) + 56320));
/*      */         } 
/* 2759 */         this.mTokenState = 1;
/* 2760 */         return 4;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2766 */       if (!this.mCfgReplaceEntities || this.mCfgTreatCharRefsAsEntities) {
/* 2767 */         if (!this.mCfgTreatCharRefsAsEntities) {
/* 2768 */           EntityDecl ed = resolveNonCharEntity();
/*      */           
/* 2770 */           this.mCurrEntity = ed;
/*      */         } 
/*      */         
/* 2773 */         this.mTokenState = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2781 */         return 9;
/*      */       } 
/*      */ 
/*      */       
/* 2785 */       i = getNextChar(" in main document content");
/*      */     } 
/*      */     
/* 2788 */     if (i == 60) {
/*      */       
/* 2790 */       char c = getNextChar(" in start tag");
/* 2791 */       if (c == '?') {
/*      */         
/* 2793 */         if (this.mVldContent == 0) {
/* 2794 */           reportInvalidContent(3);
/*      */         }
/* 2796 */         return readPIPrimary();
/*      */       } 
/*      */       
/* 2799 */       if (c == '!') {
/*      */         
/* 2801 */         int type = nextFromTreeCommentOrCData();
/*      */         
/* 2803 */         if (this.mVldContent == 0) {
/* 2804 */           reportInvalidContent(type);
/*      */         }
/* 2806 */         return type;
/*      */       } 
/* 2808 */       if (c == '/') {
/* 2809 */         readEndElem();
/* 2810 */         return 2;
/*      */       } 
/*      */       
/* 2813 */       if (c == ':' || isNameStartChar(c)) {
/*      */ 
/*      */ 
/*      */         
/* 2817 */         handleStartElem(c);
/* 2818 */         return 1;
/*      */       } 
/* 2820 */       if (c == '[') {
/* 2821 */         throwUnexpectedChar(c, " in content after '<' (malformed <![CDATA[]] directive?)");
/*      */       }
/* 2823 */       throwUnexpectedChar(c, " in content after '<' (malformed start element?).");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2833 */     if (this.mVldContent <= 2) {
/* 2834 */       if (this.mVldContent == 0 && 
/* 2835 */         this.mElementStack.reallyValidating()) {
/* 2836 */         reportInvalidContent(4);
/*      */       }
/*      */       
/* 2839 */       if (i <= 32) {
/*      */ 
/*      */ 
/*      */         
/* 2843 */         this.mTokenState = readSpacePrimary((char)i, false) ? 4 : 1;
/*      */         
/* 2845 */         return 6;
/*      */       } 
/*      */       
/* 2848 */       if (this.mElementStack.reallyValidating()) {
/* 2849 */         reportInvalidContent(4);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2859 */     if (readTextPrimary((char)i)) {
/* 2860 */       this.mTokenState = 3;
/*      */     
/*      */     }
/* 2863 */     else if (!this.mCfgCoalesceText && this.mTextBuffer.size() >= this.mShortestTextSegment) {
/*      */       
/* 2865 */       this.mTokenState = 2;
/*      */     } else {
/* 2867 */       this.mTokenState = 1;
/*      */     } 
/*      */     
/* 2870 */     return 4;
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
/*      */   private int closeContentTree() throws XMLStreamException {
/* 2887 */     this.mParseState = 2;
/*      */     
/* 2889 */     if (nextFromProlog(false)) {
/* 2890 */       this.mSecondaryToken = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2896 */     if (this.mSymbols.isDirty()) {
/* 2897 */       this.mOwner.updateSymbolTable(this.mSymbols);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2903 */     this.mTextBuffer.recycle(false);
/* 2904 */     return this.mCurrToken;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void handleStartElem(char c) throws XMLStreamException {
/*      */     boolean empty;
/* 2915 */     this.mTokenState = 4;
/*      */ 
/*      */     
/* 2918 */     if (this.mCfgNsEnabled) {
/* 2919 */       String str = parseLocalName(c);
/* 2920 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent("; expected an identifier");
/*      */       
/* 2922 */       if (c == ':') {
/* 2923 */         c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent("; expected an identifier");
/*      */         
/* 2925 */         this.mElementStack.push(str, parseLocalName(c));
/* 2926 */         c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
/*      */       } else {
/*      */         
/* 2929 */         this.mElementStack.push(null, str);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2941 */       empty = (c == '>') ? false : handleNsAttrs(c);
/*      */     } else {
/* 2943 */       this.mElementStack.push(null, parseFullName(c));
/* 2944 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
/*      */       
/* 2946 */       empty = (c == '>') ? false : handleNonNsAttrs(c);
/*      */     } 
/* 2948 */     if (!empty) {
/* 2949 */       this.mCurrDepth++;
/*      */     }
/* 2951 */     this.mStEmptyElem = empty;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2957 */     int vld = this.mElementStack.resolveAndValidateElement();
/* 2958 */     this.mVldContent = vld;
/* 2959 */     this.mValidateText = (vld == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean handleNsAttrs(char c) throws XMLStreamException {
/* 2968 */     AttributeCollector ac = this.mAttrCollector; while (true) {
/*      */       String prefix, localName;
/*      */       TextBuilder tb;
/* 2971 */       if (c <= ' ') {
/* 2972 */         c = getNextInCurrAfterWS(" in start tag", c);
/* 2973 */       } else if (c != '/' && c != '>') {
/* 2974 */         throwUnexpectedChar(c, " excepted space, or '>' or \"/>\"");
/*      */       } 
/*      */       
/* 2977 */       if (c == '/') {
/* 2978 */         c = getNextCharFromCurrent(" in start tag");
/* 2979 */         if (c != '>') {
/* 2980 */           throwUnexpectedChar(c, " expected '>'");
/*      */         }
/* 2982 */         return true;
/* 2983 */       }  if (c == '>')
/* 2984 */         return false; 
/* 2985 */       if (c == '<') {
/* 2986 */         throwParseError("Unexpected '<' character in element (missing closing '>'?)");
/*      */       }
/*      */ 
/*      */       
/* 2990 */       String str = parseLocalName(c);
/* 2991 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent("; expected an identifier");
/*      */       
/* 2993 */       if (c == ':') {
/* 2994 */         prefix = str;
/* 2995 */         c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent("; expected an identifier");
/*      */         
/* 2997 */         localName = parseLocalName(c);
/*      */       } else {
/* 2999 */         this.mInputPtr--;
/* 3000 */         prefix = null;
/* 3001 */         localName = str;
/*      */       } 
/*      */       
/* 3004 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
/*      */       
/* 3006 */       if (c <= ' ') {
/* 3007 */         c = getNextInCurrAfterWS(" in start tag", c);
/*      */       }
/* 3009 */       if (c != '=') {
/* 3010 */         throwUnexpectedChar(c, " expected '='");
/*      */       }
/* 3012 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
/*      */       
/* 3014 */       if (c <= ' ') {
/* 3015 */         c = getNextInCurrAfterWS(" in start tag", c);
/*      */       }
/*      */ 
/*      */       
/* 3019 */       if (c != '"' && c != '\'') {
/* 3020 */         throwUnexpectedChar(c, " in start tag Expected a quote");
/*      */       }
/*      */ 
/*      */       
/* 3024 */       int startLen = -1;
/*      */ 
/*      */       
/* 3027 */       if (prefix == sPrefixXmlns) {
/* 3028 */         tb = ac.getNsBuilder(localName);
/*      */         
/* 3030 */         if (null == tb) {
/* 3031 */           throwParseError("Duplicate declaration for namespace prefix '" + localName + "'.");
/*      */         }
/* 3033 */         startLen = tb.getCharSize();
/* 3034 */       } else if (localName == sPrefixXmlns && prefix == null) {
/* 3035 */         tb = ac.getDefaultNsBuilder();
/*      */         
/* 3037 */         if (null == tb) {
/* 3038 */           throwParseError("Duplicate default namespace declaration.");
/*      */         }
/*      */       } else {
/* 3041 */         tb = ac.getAttrBuilder(prefix, localName);
/*      */       } 
/* 3043 */       parseAttrValue(c, tb);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3056 */       if (!this.mXml11 && 
/* 3057 */         startLen >= 0 && tb.getCharSize() == startLen) {
/* 3058 */         throwParseError(ErrorConsts.ERR_NS_EMPTY);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3063 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
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
/*      */   private final boolean handleNonNsAttrs(char c) throws XMLStreamException {
/* 3075 */     AttributeCollector ac = this.mAttrCollector;
/*      */     
/*      */     while (true) {
/* 3078 */       if (c <= ' ') {
/* 3079 */         c = getNextInCurrAfterWS(" in start tag", c);
/* 3080 */       } else if (c != '/' && c != '>') {
/* 3081 */         throwUnexpectedChar(c, " excepted space, or '>' or \"/>\"");
/*      */       } 
/* 3083 */       if (c == '/') {
/* 3084 */         c = getNextCharFromCurrent(" in start tag");
/* 3085 */         if (c != '>') {
/* 3086 */           throwUnexpectedChar(c, " expected '>'");
/*      */         }
/* 3088 */         return true;
/* 3089 */       }  if (c == '>')
/* 3090 */         return false; 
/* 3091 */       if (c == '<') {
/* 3092 */         throwParseError("Unexpected '<' character in element (missing closing '>'?)");
/*      */       }
/*      */       
/* 3095 */       String name = parseFullName(c);
/* 3096 */       TextBuilder tb = ac.getAttrBuilder(null, name);
/* 3097 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
/*      */       
/* 3099 */       if (c <= ' ') {
/* 3100 */         c = getNextInCurrAfterWS(" in start tag", c);
/*      */       }
/* 3102 */       if (c != '=') {
/* 3103 */         throwUnexpectedChar(c, " expected '='");
/*      */       }
/* 3105 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
/*      */       
/* 3107 */       if (c <= ' ') {
/* 3108 */         c = getNextInCurrAfterWS(" in start tag", c);
/*      */       }
/*      */ 
/*      */       
/* 3112 */       if (c != '"' && c != '\'') {
/* 3113 */         throwUnexpectedChar(c, " in start tag Expected a quote");
/*      */       }
/*      */ 
/*      */       
/* 3117 */       parseAttrValue(c, tb);
/*      */       
/* 3119 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in start tag");
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
/*      */   protected final void readEndElem() throws XMLStreamException {
/* 3132 */     this.mTokenState = 4;
/*      */     
/* 3134 */     if (this.mElementStack.isEmpty()) {
/*      */       
/* 3136 */       reportExtraEndElem();
/*      */       
/*      */       return;
/*      */     } 
/* 3140 */     char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in end tag");
/*      */ 
/*      */     
/* 3143 */     if (!isNameStartChar(c) && c != ':') {
/* 3144 */       if (c <= ' ') {
/* 3145 */         throwUnexpectedChar(c, "; missing element name?");
/*      */       }
/* 3147 */       throwUnexpectedChar(c, "; expected an element name.");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3153 */     String expPrefix = this.mElementStack.getPrefix();
/* 3154 */     String expLocalName = this.mElementStack.getLocalName();
/*      */ 
/*      */     
/* 3157 */     if (expPrefix != null && expPrefix.length() > 0) {
/* 3158 */       int j = expPrefix.length();
/* 3159 */       int k = 0;
/*      */       
/*      */       while (true) {
/* 3162 */         if (c != expPrefix.charAt(k)) {
/* 3163 */           reportWrongEndPrefix(expPrefix, expLocalName, k);
/*      */           return;
/*      */         } 
/* 3166 */         if (++k >= j) {
/*      */           break;
/*      */         }
/* 3169 */         c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in end tag");
/*      */       } 
/*      */ 
/*      */       
/* 3173 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in end tag");
/*      */       
/* 3175 */       if (c != ':') {
/* 3176 */         reportWrongEndPrefix(expPrefix, expLocalName, k);
/*      */         return;
/*      */       } 
/* 3179 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in end tag");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3184 */     int len = expLocalName.length();
/* 3185 */     int i = 0;
/*      */     
/*      */     while (true) {
/* 3188 */       if (c != expLocalName.charAt(i)) {
/*      */         
/* 3190 */         reportWrongEndElem(expPrefix, expLocalName, i);
/*      */         return;
/*      */       } 
/* 3193 */       if (++i >= len) {
/*      */         break;
/*      */       }
/* 3196 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in end tag");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3201 */     c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in end tag");
/*      */     
/* 3203 */     if (c <= ' ') {
/* 3204 */       c = getNextInCurrAfterWS(" in end tag", c);
/* 3205 */     } else if (c != '>') {
/*      */       
/* 3207 */       if (c == ':' || isNameChar(c)) {
/* 3208 */         reportWrongEndElem(expPrefix, expLocalName, len);
/*      */       }
/*      */     } 
/*      */     
/* 3212 */     if (c != '>') {
/* 3213 */       throwUnexpectedChar(c, " in end tag Expected '>'.");
/*      */     }
/*      */ 
/*      */     
/* 3217 */     int vld = this.mElementStack.validateEndElement();
/* 3218 */     this.mVldContent = vld;
/* 3219 */     this.mValidateText = (vld == 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3226 */     if (this.mCurrDepth == this.mInputTopDepth) {
/* 3227 */       handleGreedyEntityProblem(this.mInput);
/*      */     }
/* 3229 */     this.mCurrDepth--;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportExtraEndElem() throws XMLStreamException {
/* 3235 */     String name = parseFNameForError();
/* 3236 */     throwParseError("Unbalanced close tag </" + name + ">; no open start tag.");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportWrongEndPrefix(String prefix, String localName, int done) throws XMLStreamException {
/* 3242 */     this.mInputPtr--;
/* 3243 */     String fullName = prefix + ":" + localName;
/* 3244 */     String rest = parseFNameForError();
/* 3245 */     String actName = fullName.substring(0, done) + rest;
/* 3246 */     throwParseError("Unexpected close tag </" + actName + ">; expected </" + fullName + ">.");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportWrongEndElem(String prefix, String localName, int done) throws XMLStreamException {
/*      */     String fullName;
/* 3253 */     this.mInputPtr--;
/*      */     
/* 3255 */     if (prefix != null && prefix.length() > 0) {
/* 3256 */       fullName = prefix + ":" + localName;
/* 3257 */       done += 1 + prefix.length();
/*      */     } else {
/* 3259 */       fullName = localName;
/*      */     } 
/* 3261 */     String rest = parseFNameForError();
/* 3262 */     String actName = fullName.substring(0, done) + rest;
/* 3263 */     throwParseError("Unexpected close tag </" + actName + ">; expected </" + fullName + ">.");
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
/*      */   private int nextFromTreeCommentOrCData() throws XMLStreamException {
/* 3277 */     char c = getNextCharFromCurrent(" in main document content");
/* 3278 */     if (c == '[') {
/* 3279 */       checkCData();
/*      */ 
/*      */ 
/*      */       
/* 3283 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in CDATA section");
/*      */       
/* 3285 */       readCDataPrimary(c);
/* 3286 */       return 12;
/*      */     } 
/* 3288 */     if (c == '-' && getNextCharFromCurrent(" in main document content") == '-') {
/* 3289 */       this.mTokenState = 1;
/* 3290 */       return 5;
/*      */     } 
/* 3292 */     throwParseError("Unrecognized XML directive; expected CDATA or comment ('<![CDATA[' or '<!--').");
/* 3293 */     return 0;
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
/*      */   private int skipToken() throws XMLStreamException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield mCurrToken : I
/*      */     //   4: tableswitch default -> 435, 1 -> 435, 2 -> 435, 3 -> 168, 4 -> 133, 5 -> 118, 6 -> 308, 7 -> 400, 8 -> 400, 9 -> 400, 10 -> 435, 11 -> 158, 12 -> 80, 13 -> 435, 14 -> 400, 15 -> 400
/*      */     //   80: aload_0
/*      */     //   81: getfield mTokenState : I
/*      */     //   84: iconst_2
/*      */     //   85: if_icmpgt -> 97
/*      */     //   88: aload_0
/*      */     //   89: ldc ' in CDATA section'
/*      */     //   91: bipush #93
/*      */     //   93: iconst_0
/*      */     //   94: invokespecial skipCommentOrCData : (Ljava/lang/String;CZ)V
/*      */     //   97: aload_0
/*      */     //   98: invokevirtual getNext : ()I
/*      */     //   101: istore_1
/*      */     //   102: aload_0
/*      */     //   103: getfield mCfgCoalesceText : Z
/*      */     //   106: ifeq -> 470
/*      */     //   109: aload_0
/*      */     //   110: iload_1
/*      */     //   111: invokespecial skipCoalescedText : (I)I
/*      */     //   114: istore_1
/*      */     //   115: goto -> 470
/*      */     //   118: aload_0
/*      */     //   119: ldc_w ' in comment'
/*      */     //   122: bipush #45
/*      */     //   124: iconst_1
/*      */     //   125: invokespecial skipCommentOrCData : (Ljava/lang/String;CZ)V
/*      */     //   128: iconst_0
/*      */     //   129: istore_1
/*      */     //   130: goto -> 470
/*      */     //   133: aload_0
/*      */     //   134: aload_0
/*      */     //   135: invokevirtual getNext : ()I
/*      */     //   138: invokespecial skipTokenText : (I)I
/*      */     //   141: istore_1
/*      */     //   142: aload_0
/*      */     //   143: getfield mCfgCoalesceText : Z
/*      */     //   146: ifeq -> 470
/*      */     //   149: aload_0
/*      */     //   150: iload_1
/*      */     //   151: invokespecial skipCoalescedText : (I)I
/*      */     //   154: istore_1
/*      */     //   155: goto -> 470
/*      */     //   158: aload_0
/*      */     //   159: iconst_0
/*      */     //   160: invokevirtual finishDTD : (Z)V
/*      */     //   163: iconst_0
/*      */     //   164: istore_1
/*      */     //   165: goto -> 470
/*      */     //   168: aload_0
/*      */     //   169: getfield mInputPtr : I
/*      */     //   172: aload_0
/*      */     //   173: getfield mInputEnd : I
/*      */     //   176: if_icmpge -> 198
/*      */     //   179: aload_0
/*      */     //   180: getfield mInputBuffer : [C
/*      */     //   183: aload_0
/*      */     //   184: dup
/*      */     //   185: getfield mInputPtr : I
/*      */     //   188: dup_x1
/*      */     //   189: iconst_1
/*      */     //   190: iadd
/*      */     //   191: putfield mInputPtr : I
/*      */     //   194: caload
/*      */     //   195: goto -> 205
/*      */     //   198: aload_0
/*      */     //   199: ldc_w ' in processing instruction'
/*      */     //   202: invokevirtual getNextCharFromCurrent : (Ljava/lang/String;)C
/*      */     //   205: istore_2
/*      */     //   206: iload_2
/*      */     //   207: bipush #63
/*      */     //   209: if_icmpne -> 267
/*      */     //   212: aload_0
/*      */     //   213: getfield mInputPtr : I
/*      */     //   216: aload_0
/*      */     //   217: getfield mInputEnd : I
/*      */     //   220: if_icmpge -> 242
/*      */     //   223: aload_0
/*      */     //   224: getfield mInputBuffer : [C
/*      */     //   227: aload_0
/*      */     //   228: dup
/*      */     //   229: getfield mInputPtr : I
/*      */     //   232: dup_x1
/*      */     //   233: iconst_1
/*      */     //   234: iadd
/*      */     //   235: putfield mInputPtr : I
/*      */     //   238: caload
/*      */     //   239: goto -> 249
/*      */     //   242: aload_0
/*      */     //   243: ldc_w ' in processing instruction'
/*      */     //   246: invokevirtual getNextCharFromCurrent : (Ljava/lang/String;)C
/*      */     //   249: istore_2
/*      */     //   250: iload_2
/*      */     //   251: bipush #63
/*      */     //   253: if_icmpeq -> 212
/*      */     //   256: iload_2
/*      */     //   257: bipush #62
/*      */     //   259: if_icmpne -> 267
/*      */     //   262: iconst_0
/*      */     //   263: istore_1
/*      */     //   264: goto -> 470
/*      */     //   267: iload_2
/*      */     //   268: bipush #32
/*      */     //   270: if_icmpge -> 305
/*      */     //   273: iload_2
/*      */     //   274: bipush #10
/*      */     //   276: if_icmpeq -> 285
/*      */     //   279: iload_2
/*      */     //   280: bipush #13
/*      */     //   282: if_icmpne -> 294
/*      */     //   285: aload_0
/*      */     //   286: iload_2
/*      */     //   287: invokevirtual skipCRLF : (C)Z
/*      */     //   290: pop
/*      */     //   291: goto -> 305
/*      */     //   294: iload_2
/*      */     //   295: bipush #9
/*      */     //   297: if_icmpeq -> 305
/*      */     //   300: aload_0
/*      */     //   301: iload_2
/*      */     //   302: invokevirtual throwInvalidSpace : (I)V
/*      */     //   305: goto -> 168
/*      */     //   308: aload_0
/*      */     //   309: getfield mInputPtr : I
/*      */     //   312: aload_0
/*      */     //   313: getfield mInputEnd : I
/*      */     //   316: if_icmpge -> 388
/*      */     //   319: aload_0
/*      */     //   320: getfield mInputBuffer : [C
/*      */     //   323: aload_0
/*      */     //   324: dup
/*      */     //   325: getfield mInputPtr : I
/*      */     //   328: dup_x1
/*      */     //   329: iconst_1
/*      */     //   330: iadd
/*      */     //   331: putfield mInputPtr : I
/*      */     //   334: caload
/*      */     //   335: istore_2
/*      */     //   336: iload_2
/*      */     //   337: bipush #32
/*      */     //   339: if_icmple -> 347
/*      */     //   342: iload_2
/*      */     //   343: istore_1
/*      */     //   344: goto -> 470
/*      */     //   347: iload_2
/*      */     //   348: bipush #10
/*      */     //   350: if_icmpeq -> 359
/*      */     //   353: iload_2
/*      */     //   354: bipush #13
/*      */     //   356: if_icmpne -> 368
/*      */     //   359: aload_0
/*      */     //   360: iload_2
/*      */     //   361: invokevirtual skipCRLF : (C)Z
/*      */     //   364: pop
/*      */     //   365: goto -> 385
/*      */     //   368: iload_2
/*      */     //   369: bipush #32
/*      */     //   371: if_icmpeq -> 385
/*      */     //   374: iload_2
/*      */     //   375: bipush #9
/*      */     //   377: if_icmpeq -> 385
/*      */     //   380: aload_0
/*      */     //   381: iload_2
/*      */     //   382: invokevirtual throwInvalidSpace : (I)V
/*      */     //   385: goto -> 308
/*      */     //   388: aload_0
/*      */     //   389: invokevirtual loadMore : ()Z
/*      */     //   392: ifne -> 308
/*      */     //   395: iconst_m1
/*      */     //   396: istore_1
/*      */     //   397: goto -> 470
/*      */     //   400: new java/lang/IllegalStateException
/*      */     //   403: dup
/*      */     //   404: new java/lang/StringBuffer
/*      */     //   407: dup
/*      */     //   408: invokespecial <init> : ()V
/*      */     //   411: ldc_w 'skipToken() called when current token is '
/*      */     //   414: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   417: aload_0
/*      */     //   418: aload_0
/*      */     //   419: getfield mCurrToken : I
/*      */     //   422: invokevirtual tokenTypeDesc : (I)Ljava/lang/String;
/*      */     //   425: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   428: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   431: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   434: athrow
/*      */     //   435: new java/lang/IllegalStateException
/*      */     //   438: dup
/*      */     //   439: new java/lang/StringBuffer
/*      */     //   442: dup
/*      */     //   443: invokespecial <init> : ()V
/*      */     //   446: ldc_w 'Internal error: unexpected token '
/*      */     //   449: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   452: aload_0
/*      */     //   453: aload_0
/*      */     //   454: getfield mCurrToken : I
/*      */     //   457: invokevirtual tokenTypeDesc : (I)Ljava/lang/String;
/*      */     //   460: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
/*      */     //   463: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   466: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   469: athrow
/*      */     //   470: iload_1
/*      */     //   471: iconst_1
/*      */     //   472: if_icmpge -> 523
/*      */     //   475: aload_0
/*      */     //   476: aload_0
/*      */     //   477: getfield mCurrInputRow : I
/*      */     //   480: putfield mTokenInputRow : I
/*      */     //   483: aload_0
/*      */     //   484: aload_0
/*      */     //   485: getfield mCurrInputProcessed : J
/*      */     //   488: aload_0
/*      */     //   489: getfield mInputPtr : I
/*      */     //   492: i2l
/*      */     //   493: ladd
/*      */     //   494: putfield mTokenInputTotal : J
/*      */     //   497: aload_0
/*      */     //   498: aload_0
/*      */     //   499: getfield mInputPtr : I
/*      */     //   502: aload_0
/*      */     //   503: getfield mCurrInputRowStart : I
/*      */     //   506: isub
/*      */     //   507: putfield mTokenInputCol : I
/*      */     //   510: iload_1
/*      */     //   511: ifge -> 518
/*      */     //   514: iload_1
/*      */     //   515: goto -> 522
/*      */     //   518: aload_0
/*      */     //   519: invokevirtual getNext : ()I
/*      */     //   522: ireturn
/*      */     //   523: aload_0
/*      */     //   524: aload_0
/*      */     //   525: getfield mCurrInputRow : I
/*      */     //   528: putfield mTokenInputRow : I
/*      */     //   531: aload_0
/*      */     //   532: aload_0
/*      */     //   533: getfield mCurrInputProcessed : J
/*      */     //   536: aload_0
/*      */     //   537: getfield mInputPtr : I
/*      */     //   540: i2l
/*      */     //   541: ladd
/*      */     //   542: lconst_1
/*      */     //   543: lsub
/*      */     //   544: putfield mTokenInputTotal : J
/*      */     //   547: aload_0
/*      */     //   548: aload_0
/*      */     //   549: getfield mInputPtr : I
/*      */     //   552: aload_0
/*      */     //   553: getfield mCurrInputRowStart : I
/*      */     //   556: isub
/*      */     //   557: iconst_1
/*      */     //   558: isub
/*      */     //   559: putfield mTokenInputCol : I
/*      */     //   562: iload_1
/*      */     //   563: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #3320	-> 0
/*      */     //   #3328	-> 80
/*      */     //   #3330	-> 88
/*      */     //   #3332	-> 97
/*      */     //   #3334	-> 102
/*      */     //   #3335	-> 109
/*      */     //   #3341	-> 118
/*      */     //   #3342	-> 128
/*      */     //   #3343	-> 130
/*      */     //   #3347	-> 133
/*      */     //   #3349	-> 142
/*      */     //   #3350	-> 149
/*      */     //   #3356	-> 158
/*      */     //   #3357	-> 163
/*      */     //   #3358	-> 165
/*      */     //   #3362	-> 168
/*      */     //   #3364	-> 206
/*      */     //   #3366	-> 212
/*      */     //   #3368	-> 250
/*      */     //   #3369	-> 256
/*      */     //   #3370	-> 262
/*      */     //   #3371	-> 264
/*      */     //   #3374	-> 267
/*      */     //   #3375	-> 273
/*      */     //   #3376	-> 285
/*      */     //   #3377	-> 294
/*      */     //   #3378	-> 300
/*      */     //   #3381	-> 305
/*      */     //   #3388	-> 308
/*      */     //   #3389	-> 319
/*      */     //   #3390	-> 336
/*      */     //   #3391	-> 342
/*      */     //   #3392	-> 344
/*      */     //   #3394	-> 347
/*      */     //   #3395	-> 359
/*      */     //   #3396	-> 368
/*      */     //   #3397	-> 380
/*      */     //   #3399	-> 385
/*      */     //   #3400	-> 388
/*      */     //   #3401	-> 395
/*      */     //   #3402	-> 397
/*      */     //   #3413	-> 400
/*      */     //   #3425	-> 435
/*      */     //   #3439	-> 470
/*      */     //   #3440	-> 475
/*      */     //   #3441	-> 483
/*      */     //   #3442	-> 497
/*      */     //   #3443	-> 510
/*      */     //   #3447	-> 523
/*      */     //   #3448	-> 531
/*      */     //   #3449	-> 547
/*      */     //   #3450	-> 562
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   102	165	1	result	I
/*      */     //   206	99	2	c	C
/*      */     //   344	3	1	result	I
/*      */     //   336	49	2	c	C
/*      */     //   0	564	0	this	Lcom/ctc/wstx/sr/BasicStreamReader;
/*      */     //   397	167	1	result	I
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
/*      */   private void skipCommentOrCData(String errorMsg, char endChar, boolean preventDoubles) throws XMLStreamException {
/*      */     while (true) {
/* 3462 */       char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(errorMsg);
/*      */       
/* 3464 */       if (c < ' ') {
/* 3465 */         if (c == '\n' || c == '\r') {
/* 3466 */           skipCRLF(c);
/* 3467 */         } else if (c != '\t') {
/* 3468 */           throwInvalidSpace(c);
/*      */         } 
/*      */       }
/* 3471 */       if (c == endChar) {
/*      */ 
/*      */         
/* 3474 */         c = getNextChar(errorMsg);
/* 3475 */         if (c == endChar) {
/*      */           
/* 3477 */           c = getNextChar(errorMsg);
/* 3478 */           if (c == '>') {
/*      */             break;
/*      */           }
/* 3481 */           if (preventDoubles) {
/* 3482 */             throwParseError("String '--' not allowed in comment (missing '>'?)");
/*      */           }
/*      */           
/* 3485 */           while (c == endChar) {
/* 3486 */             c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(errorMsg);
/*      */           }
/*      */           
/* 3489 */           if (c == '>') {
/*      */             break;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 3495 */         if (c < ' ') {
/* 3496 */           if (c == '\n' || c == '\r') {
/* 3497 */             skipCRLF(c); continue;
/* 3498 */           }  if (c != '\t') {
/* 3499 */             throwInvalidSpace(c);
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   private int skipCoalescedText(int i) throws XMLStreamException {
/*      */     do {
/* 3520 */       while (i == 60) {
/*      */         
/* 3522 */         if (!ensureInput(3))
/*      */         {
/*      */ 
/*      */           
/* 3526 */           return i;
/*      */         }
/* 3528 */         if (this.mInputBuffer[this.mInputPtr] != '!' || this.mInputBuffer[this.mInputPtr + 1] != '[')
/*      */         {
/*      */           
/* 3531 */           return i;
/*      */         }
/*      */         
/* 3534 */         this.mInputPtr += 2;
/*      */         
/* 3536 */         checkCData();
/* 3537 */         skipCommentOrCData(" in CDATA section", ']', false);
/* 3538 */         i = getNext();
/* 3539 */       }  if (i < 0) {
/* 3540 */         return i;
/*      */       }
/* 3542 */       i = skipTokenText(i);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 3547 */     while (i != 38 && i >= 0);
/* 3548 */     return i;
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
/*      */   private int skipTokenText(int i) throws XMLStreamException {
/*      */     while (true) {
/* 3562 */       if (i == 60) {
/* 3563 */         return i;
/*      */       }
/* 3565 */       if (i == 38) {
/*      */         
/* 3567 */         if (this.mCfgReplaceEntities)
/*      */         {
/* 3569 */           if (this.mInputEnd - this.mInputPtr < 3 || resolveSimpleEntity(true) == 0)
/*      */           {
/*      */ 
/*      */             
/* 3573 */             i = fullyResolveEntity(true);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 3582 */         else if (resolveCharOnlyEntity(true) == 0)
/*      */         {
/*      */ 
/*      */           
/* 3586 */           return i;
/*      */         }
/*      */       
/* 3589 */       } else if (i < 32) {
/* 3590 */         if (i == 13 || i == 10)
/* 3591 */         { skipCRLF((char)i); }
/* 3592 */         else { if (i < 0)
/* 3593 */             return i; 
/* 3594 */           if (i != 9) {
/* 3595 */             throwInvalidSpace(i);
/*      */           } }
/*      */       
/*      */       } 
/*      */ 
/*      */       
/* 3601 */       while (this.mInputPtr < this.mInputEnd) {
/* 3602 */         char c = this.mInputBuffer[this.mInputPtr++];
/* 3603 */         if (c < '?') {
/* 3604 */           i = c;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 3609 */       i = getNext();
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
/*      */   protected void ensureFinishToken() throws XMLStreamException {
/* 3623 */     if (this.mTokenState < this.mStTextThreshold) {
/* 3624 */       finishToken(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void safeEnsureFinishToken() {
/* 3630 */     if (this.mTokenState < this.mStTextThreshold) {
/* 3631 */       safeFinishToken();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void safeFinishToken() {
/*      */     try {
/* 3643 */       boolean deferErrors = (this.mCurrToken == 4);
/* 3644 */       finishToken(deferErrors);
/* 3645 */     } catch (XMLStreamException strex) {
/* 3646 */       throwLazyError(strex);
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
/*      */   protected void finishToken(boolean deferErrors) throws XMLStreamException {
/*      */     boolean prolog;
/* 3663 */     switch (this.mCurrToken) {
/*      */       case 12:
/* 3665 */         if (this.mCfgCoalesceText) {
/* 3666 */           readCoalescedText(this.mCurrToken, deferErrors);
/*      */         }
/* 3668 */         else if (readCDataSecondary(this.mShortestTextSegment)) {
/* 3669 */           this.mTokenState = 3;
/*      */         } else {
/* 3671 */           this.mTokenState = 2;
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 4:
/* 3677 */         if (this.mCfgCoalesceText) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3682 */           if (this.mTokenState == 3 && this.mInputPtr + 1 < this.mInputEnd && this.mInputBuffer[this.mInputPtr + 1] != '!') {
/*      */ 
/*      */             
/* 3685 */             this.mTokenState = 4;
/*      */             return;
/*      */           } 
/* 3688 */           readCoalescedText(this.mCurrToken, deferErrors);
/*      */         }
/* 3690 */         else if (readTextSecondary(this.mShortestTextSegment, deferErrors)) {
/* 3691 */           this.mTokenState = 3;
/*      */         } else {
/* 3693 */           this.mTokenState = 2;
/*      */         } 
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 3704 */         prolog = (this.mParseState != 1);
/* 3705 */         readSpaceSecondary(prolog);
/* 3706 */         this.mTokenState = 4;
/*      */         return;
/*      */ 
/*      */       
/*      */       case 5:
/* 3711 */         readComment();
/* 3712 */         this.mTokenState = 4;
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/*      */         try {
/* 3724 */           finishDTD(true);
/*      */         } finally {
/* 3726 */           this.mTokenState = 4;
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 3:
/* 3731 */         readPI();
/* 3732 */         this.mTokenState = 4;
/*      */         return;
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 14:
/*      */       case 15:
/* 3742 */         throw new IllegalStateException("finishToken() called when current token is " + tokenTypeDesc(this.mCurrToken));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3750 */     throw new IllegalStateException("Internal error: unexpected token " + tokenTypeDesc(this.mCurrToken));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readComment() throws XMLStreamException {
/* 3756 */     char[] inputBuf = this.mInputBuffer;
/* 3757 */     int inputLen = this.mInputEnd;
/* 3758 */     int ptr = this.mInputPtr;
/* 3759 */     int start = ptr;
/*      */ 
/*      */     
/* 3762 */     while (ptr < inputLen) {
/* 3763 */       char c = inputBuf[ptr++];
/* 3764 */       if (c > '-') {
/*      */         continue;
/*      */       }
/*      */       
/* 3768 */       if (c < ' ') {
/* 3769 */         if (c == '\n') {
/* 3770 */           markLF(ptr); continue;
/* 3771 */         }  if (c == '\r') {
/* 3772 */           if (!this.mNormalizeLFs && ptr < inputLen) {
/* 3773 */             if (inputBuf[ptr] == '\n') {
/* 3774 */               ptr++;
/*      */             }
/* 3776 */             markLF(ptr); continue;
/*      */           } 
/* 3778 */           ptr--;
/*      */           break;
/*      */         } 
/* 3781 */         if (c != '\t')
/* 3782 */           throwInvalidSpace(c);  continue;
/*      */       } 
/* 3784 */       if (c == '-') {
/*      */ 
/*      */         
/* 3787 */         if (ptr + 1 >= inputLen) {
/*      */ 
/*      */ 
/*      */           
/* 3791 */           ptr--;
/*      */           
/*      */           break;
/*      */         } 
/* 3795 */         if (inputBuf[ptr] != '-') {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 3800 */         c = inputBuf[ptr + 1];
/* 3801 */         if (c != '>') {
/* 3802 */           throwParseError("String '--' not allowed in comment (missing '>'?)");
/*      */         }
/* 3804 */         this.mTextBuffer.resetWithShared(inputBuf, start, ptr - start - 1);
/* 3805 */         this.mInputPtr = ptr + 2;
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 3810 */     this.mInputPtr = ptr;
/* 3811 */     this.mTextBuffer.resetWithCopy(inputBuf, start, ptr - start);
/* 3812 */     readComment2(this.mTextBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readComment2(TextBuffer tb) throws XMLStreamException {
/* 3821 */     char[] outBuf = this.mTextBuffer.getCurrentSegment();
/* 3822 */     int outPtr = this.mTextBuffer.getCurrentSegmentSize();
/* 3823 */     int outLen = outBuf.length;
/*      */     
/*      */     while (true) {
/* 3826 */       char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in comment");
/*      */ 
/*      */       
/* 3829 */       if (c < ' ') {
/* 3830 */         if (c == '\n') {
/* 3831 */           markLF();
/* 3832 */         } else if (c == '\r') {
/* 3833 */           if (skipCRLF(c)) {
/* 3834 */             if (!this.mNormalizeLFs) {
/* 3835 */               if (outPtr >= outLen) {
/* 3836 */                 outBuf = this.mTextBuffer.finishCurrentSegment();
/* 3837 */                 outLen = outBuf.length;
/* 3838 */                 outPtr = 0;
/*      */               } 
/* 3840 */               outBuf[outPtr++] = c;
/*      */             } 
/*      */             
/* 3843 */             c = '\n';
/* 3844 */           } else if (this.mNormalizeLFs) {
/* 3845 */             c = '\n';
/*      */           } 
/* 3847 */         } else if (c != '\t') {
/* 3848 */           throwInvalidSpace(c);
/*      */         } 
/* 3850 */       } else if (c == '-') {
/* 3851 */         c = getNextCharFromCurrent(" in comment");
/* 3852 */         if (c == '-') {
/*      */           
/* 3854 */           c = getNextCharFromCurrent(" in comment");
/* 3855 */           if (c != '>') {
/* 3856 */             throwParseError(ErrorConsts.ERR_HYPHENS_IN_COMMENT);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 3865 */         c = '-';
/* 3866 */         this.mInputPtr--;
/*      */       } 
/*      */ 
/*      */       
/* 3870 */       if (outPtr >= outLen) {
/* 3871 */         outBuf = this.mTextBuffer.finishCurrentSegment();
/* 3872 */         outLen = outBuf.length;
/* 3873 */         outPtr = 0;
/*      */       } 
/*      */       
/* 3876 */       outBuf[outPtr++] = c;
/*      */     } 
/*      */ 
/*      */     
/* 3880 */     this.mTextBuffer.setCurrentLength(outPtr);
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
/*      */   private final int readPIPrimary() throws XMLStreamException {
/* 3895 */     String target = parseFullName();
/* 3896 */     this.mCurrName = target;
/*      */     
/* 3898 */     if (target.length() == 0) {
/* 3899 */       throwParseError(ErrorConsts.ERR_WF_PI_MISSING_TARGET);
/*      */     }
/*      */ 
/*      */     
/* 3903 */     if (target.equalsIgnoreCase("xml")) {
/*      */       
/* 3905 */       if (!this.mConfig.inputParsingModeDocuments()) {
/* 3906 */         throwParseError(ErrorConsts.ERR_WF_PI_XML_TARGET, target, (Object)null);
/*      */       }
/*      */       
/* 3909 */       char c1 = getNextCharFromCurrent(" in xml declaration");
/* 3910 */       if (!isSpaceChar(c1)) {
/* 3911 */         throwUnexpectedChar(c1, "excepted a space in xml declaration after 'xml'");
/*      */       }
/* 3913 */       return handleMultiDocStart(7);
/*      */     } 
/*      */ 
/*      */     
/* 3917 */     char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in processing instruction");
/*      */     
/* 3919 */     if (isSpaceChar(c)) {
/* 3920 */       this.mTokenState = 1;
/*      */       
/* 3922 */       skipWS(c);
/*      */     } else {
/* 3924 */       this.mTokenState = 4;
/* 3925 */       this.mTextBuffer.resetWithEmpty();
/*      */       
/* 3927 */       if (c != '?' || getNextCharFromCurrent(" in processing instruction") != '>') {
/* 3928 */         throwUnexpectedChar(c, ErrorConsts.ERR_WF_PI_XML_MISSING_SPACE);
/*      */       }
/*      */     } 
/*      */     
/* 3932 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readPI() throws XMLStreamException {
/* 3942 */     int ptr = this.mInputPtr;
/* 3943 */     int start = ptr;
/* 3944 */     char[] inputBuf = this.mInputBuffer;
/* 3945 */     int inputLen = this.mInputEnd;
/*      */ 
/*      */     
/* 3948 */     while (ptr < inputLen) {
/* 3949 */       char c = inputBuf[ptr++];
/* 3950 */       if (c < ' ') {
/* 3951 */         if (c == '\n') {
/* 3952 */           markLF(ptr); continue;
/* 3953 */         }  if (c == '\r') {
/* 3954 */           if (ptr < inputLen && !this.mNormalizeLFs) {
/* 3955 */             if (inputBuf[ptr] == '\n') {
/* 3956 */               ptr++;
/*      */             }
/* 3958 */             markLF(ptr); continue;
/*      */           } 
/* 3960 */           ptr--;
/*      */           break;
/*      */         } 
/* 3963 */         if (c != '\t')
/* 3964 */           throwInvalidSpace(c);  continue;
/*      */       } 
/* 3966 */       if (c == '?') {
/*      */         
/*      */         while (true) {
/* 3969 */           if (ptr >= inputLen) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3974 */             ptr--;
/*      */             break;
/*      */           } 
/* 3977 */           c = inputBuf[ptr++];
/* 3978 */           if (c == '>') {
/* 3979 */             this.mInputPtr = ptr;
/*      */             
/* 3981 */             this.mTextBuffer.resetWithShared(inputBuf, start, ptr - start - 2);
/*      */             return;
/*      */           } 
/* 3984 */           if (c != '?')
/*      */           {
/* 3986 */             ptr--;
/*      */           }
/*      */         } 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 3993 */     this.mInputPtr = ptr;
/*      */     
/* 3995 */     this.mTextBuffer.resetWithCopy(inputBuf, start, ptr - start);
/* 3996 */     readPI2(this.mTextBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readPI2(TextBuffer tb) throws XMLStreamException {
/* 4002 */     char[] inputBuf = this.mInputBuffer;
/* 4003 */     int inputLen = this.mInputEnd;
/* 4004 */     int inputPtr = this.mInputPtr;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4009 */     char[] outBuf = tb.getCurrentSegment();
/* 4010 */     int outPtr = tb.getCurrentSegmentSize();
/*      */ 
/*      */ 
/*      */     
/*      */     label49: while (true) {
/* 4015 */       if (inputPtr >= inputLen) {
/* 4016 */         loadMoreFromCurrent(" in processing instruction");
/* 4017 */         inputBuf = this.mInputBuffer;
/* 4018 */         inputPtr = this.mInputPtr;
/* 4019 */         inputLen = this.mInputEnd;
/*      */       } 
/*      */ 
/*      */       
/* 4023 */       char c = inputBuf[inputPtr++];
/* 4024 */       if (c < ' ') {
/* 4025 */         if (c == '\n') {
/* 4026 */           markLF(inputPtr);
/* 4027 */         } else if (c == '\r') {
/* 4028 */           this.mInputPtr = inputPtr;
/* 4029 */           if (skipCRLF(c)) {
/* 4030 */             if (!this.mNormalizeLFs) {
/*      */               
/* 4032 */               if (outPtr >= outBuf.length) {
/* 4033 */                 outBuf = this.mTextBuffer.finishCurrentSegment();
/* 4034 */                 outPtr = 0;
/*      */               } 
/* 4036 */               outBuf[outPtr++] = c;
/*      */             } 
/*      */             
/* 4039 */             c = '\n';
/* 4040 */           } else if (this.mNormalizeLFs) {
/* 4041 */             c = '\n';
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 4046 */           inputPtr = this.mInputPtr;
/* 4047 */           inputBuf = this.mInputBuffer;
/* 4048 */           inputLen = this.mInputEnd;
/* 4049 */         } else if (c != '\t') {
/* 4050 */           throwInvalidSpace(c);
/*      */         } 
/* 4052 */       } else if (c == '?') {
/* 4053 */         this.mInputPtr = inputPtr;
/*      */ 
/*      */         
/*      */         while (true) {
/* 4057 */           c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in processing instruction");
/*      */           
/* 4059 */           if (c == '>')
/*      */             break; 
/* 4061 */           if (c == '?') {
/* 4062 */             if (outPtr >= outBuf.length) {
/* 4063 */               outBuf = tb.finishCurrentSegment();
/* 4064 */               outPtr = 0;
/*      */             } 
/* 4066 */             outBuf[outPtr++] = c;
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 4074 */           inputPtr = --this.mInputPtr;
/* 4075 */           inputBuf = this.mInputBuffer;
/* 4076 */           inputLen = this.mInputEnd;
/* 4077 */           c = '?';
/*      */           
/*      */           continue label49;
/*      */         } 
/*      */         
/*      */         break;
/*      */       } 
/* 4084 */       if (outPtr >= outBuf.length) {
/* 4085 */         outBuf = tb.finishCurrentSegment();
/* 4086 */         outPtr = 0;
/*      */       } 
/*      */       
/* 4089 */       outBuf[outPtr++] = c;
/*      */     } 
/*      */ 
/*      */     
/* 4093 */     tb.setCurrentLength(outPtr);
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
/*      */   protected void readCoalescedText(int currType, boolean deferErrors) throws XMLStreamException {
/* 4112 */     if (currType == 4 || currType == 6) {
/* 4113 */       readTextSecondary(2147483647, deferErrors);
/* 4114 */       boolean wasCData = false;
/* 4115 */     } else if (currType == 12) {
/*      */ 
/*      */ 
/*      */       
/* 4119 */       if (this.mTokenState <= 2) {
/* 4120 */         readCDataSecondary(2147483647);
/*      */       }
/* 4122 */       boolean wasCData = true;
/*      */     } else {
/* 4124 */       throw new IllegalStateException("Internal error: unexpected token " + tokenTypeDesc(this.mCurrToken) + "; expected CHARACTERS, CDATA or SPACE.");
/*      */     } 
/*      */ 
/*      */     
/* 4128 */     while (!deferErrors || this.mPendingException == null) {
/* 4129 */       if (this.mInputPtr >= this.mInputEnd) {
/* 4130 */         this.mTextBuffer.ensureNotShared();
/* 4131 */         if (!loadMore()) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 4137 */       char c = this.mInputBuffer[this.mInputPtr];
/* 4138 */       if (c == '<') {
/*      */         
/* 4140 */         if (this.mInputEnd - this.mInputPtr < 3) {
/* 4141 */           this.mTextBuffer.ensureNotShared();
/* 4142 */           if (!ensureInput(3)) {
/*      */             break;
/*      */           }
/*      */         } 
/* 4146 */         if (this.mInputBuffer[this.mInputPtr + 1] != '!' || this.mInputBuffer[this.mInputPtr + 2] != '[') {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4152 */         this.mInputPtr += 3;
/*      */         
/* 4154 */         checkCData();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4159 */         readCDataSecondary(2147483647);
/* 4160 */         wasCData = true;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 4165 */       if (c == '&' && !wasCData) {
/*      */         break;
/*      */       }
/*      */       
/* 4169 */       readTextSecondary(2147483647, deferErrors);
/* 4170 */       boolean wasCData = false;
/*      */     } 
/*      */ 
/*      */     
/* 4174 */     this.mTokenState = 4;
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
/*      */   private final boolean readCDataPrimary(char c) throws XMLStreamException {
/* 4196 */     this.mWsStatus = (c <= ' ') ? 0 : 2;
/*      */     
/* 4198 */     int ptr = this.mInputPtr;
/* 4199 */     int inputLen = this.mInputEnd;
/* 4200 */     char[] inputBuf = this.mInputBuffer;
/* 4201 */     int start = ptr - 1;
/*      */     
/*      */     while (true) {
/* 4204 */       if (c < ' ') {
/* 4205 */         if (c == '\n') {
/* 4206 */           markLF(ptr);
/* 4207 */         } else if (c == '\r') {
/* 4208 */           if (ptr >= inputLen) {
/* 4209 */             ptr--;
/*      */             break;
/*      */           } 
/* 4212 */           if (this.mNormalizeLFs) {
/* 4213 */             if (inputBuf[ptr] == '\n') {
/* 4214 */               ptr--;
/*      */               break;
/*      */             } 
/* 4217 */             inputBuf[ptr - 1] = '\n';
/*      */           
/*      */           }
/* 4220 */           else if (inputBuf[ptr] == '\n') {
/* 4221 */             ptr++;
/*      */           } 
/*      */           
/* 4224 */           markLF(ptr);
/* 4225 */         } else if (c != '\t') {
/* 4226 */           throwInvalidSpace(c);
/*      */         } 
/* 4228 */       } else if (c == ']') {
/*      */         
/* 4230 */         if (ptr + 1 >= inputLen) {
/* 4231 */           ptr--;
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 4236 */         if (inputBuf[ptr] == ']') {
/* 4237 */           ptr++;
/*      */           
/*      */           while (true) {
/* 4240 */             if (ptr >= inputLen) {
/*      */ 
/*      */ 
/*      */               
/* 4244 */               ptr -= 2;
/*      */               break;
/*      */             } 
/* 4247 */             c = inputBuf[ptr++];
/* 4248 */             if (c == '>') {
/* 4249 */               this.mInputPtr = ptr;
/* 4250 */               ptr -= start + 3;
/* 4251 */               this.mTextBuffer.resetWithShared(inputBuf, start, ptr);
/* 4252 */               this.mTokenState = 3;
/* 4253 */               return true;
/*      */             } 
/* 4255 */             if (c != ']') {
/*      */               
/* 4257 */               ptr--;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 4265 */       if (ptr >= inputLen) {
/*      */         break;
/*      */       }
/* 4268 */       c = inputBuf[ptr++];
/*      */     } 
/*      */     
/* 4271 */     this.mInputPtr = ptr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4279 */     int len = ptr - start;
/* 4280 */     this.mTextBuffer.resetWithShared(inputBuf, start, len);
/* 4281 */     if (this.mCfgCoalesceText || this.mTextBuffer.size() < this.mShortestTextSegment) {
/*      */       
/* 4283 */       this.mTokenState = 1;
/*      */     } else {
/* 4285 */       this.mTokenState = 2;
/*      */     } 
/* 4287 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean readCDataSecondary(int shortestSegment) throws XMLStreamException {
/* 4298 */     char[] inputBuf = this.mInputBuffer;
/* 4299 */     int inputLen = this.mInputEnd;
/* 4300 */     int inputPtr = this.mInputPtr;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4305 */     char[] outBuf = this.mTextBuffer.getCurrentSegment();
/* 4306 */     int outPtr = this.mTextBuffer.getCurrentSegmentSize();
/*      */     
/*      */     while (true) {
/* 4309 */       if (inputPtr >= inputLen) {
/* 4310 */         loadMore(" in CDATA section");
/* 4311 */         inputBuf = this.mInputBuffer;
/* 4312 */         inputPtr = this.mInputPtr;
/* 4313 */         inputLen = this.mInputEnd;
/*      */       } 
/* 4315 */       char c = inputBuf[inputPtr++];
/*      */       
/* 4317 */       if (c < ' ') {
/* 4318 */         if (c == '\n') {
/* 4319 */           markLF(inputPtr);
/* 4320 */         } else if (c == '\r') {
/* 4321 */           this.mInputPtr = inputPtr;
/* 4322 */           if (skipCRLF(c)) {
/* 4323 */             if (!this.mNormalizeLFs) {
/*      */               
/* 4325 */               outBuf[outPtr++] = c;
/* 4326 */               if (outPtr >= outBuf.length) {
/* 4327 */                 outBuf = this.mTextBuffer.finishCurrentSegment();
/* 4328 */                 outPtr = 0;
/*      */               } 
/*      */             } 
/*      */             
/* 4332 */             c = '\n';
/* 4333 */           } else if (this.mNormalizeLFs) {
/* 4334 */             c = '\n';
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 4339 */           inputPtr = this.mInputPtr;
/* 4340 */           inputBuf = this.mInputBuffer;
/* 4341 */           inputLen = this.mInputEnd;
/* 4342 */         } else if (c != '\t') {
/* 4343 */           throwInvalidSpace(c);
/*      */         } 
/* 4345 */       } else if (c == ']') {
/*      */         
/* 4347 */         this.mInputPtr = inputPtr;
/* 4348 */         if (checkCDataEnd(outBuf, outPtr)) {
/* 4349 */           return true;
/*      */         }
/* 4351 */         inputPtr = this.mInputPtr;
/* 4352 */         inputBuf = this.mInputBuffer;
/* 4353 */         inputLen = this.mInputEnd;
/*      */         
/* 4355 */         outBuf = this.mTextBuffer.getCurrentSegment();
/* 4356 */         outPtr = this.mTextBuffer.getCurrentSegmentSize();
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 4361 */       outBuf[outPtr++] = c;
/*      */ 
/*      */       
/* 4364 */       if (outPtr >= outBuf.length) {
/* 4365 */         TextBuffer tb = this.mTextBuffer;
/*      */         
/* 4367 */         if (!this.mCfgCoalesceText) {
/* 4368 */           tb.setCurrentLength(outBuf.length);
/* 4369 */           if (tb.size() >= shortestSegment) {
/* 4370 */             this.mInputPtr = inputPtr;
/* 4371 */             return false;
/*      */           } 
/*      */         } 
/*      */         
/* 4375 */         outBuf = tb.finishCurrentSegment();
/* 4376 */         outPtr = 0;
/*      */       } 
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
/*      */   private boolean checkCDataEnd(char[] outBuf, int outPtr) throws XMLStreamException {
/*      */     char c;
/* 4393 */     int bracketCount = 0;
/*      */     
/*      */     do {
/* 4396 */       bracketCount++;
/* 4397 */       c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in CDATA section");
/*      */     }
/* 4399 */     while (c == ']');
/*      */     
/* 4401 */     boolean match = (bracketCount >= 2 && c == '>');
/* 4402 */     if (match) {
/* 4403 */       bracketCount -= 2;
/*      */     }
/* 4405 */     while (bracketCount > 0) {
/* 4406 */       bracketCount--;
/* 4407 */       outBuf[outPtr++] = ']';
/* 4408 */       if (outPtr >= outBuf.length) {
/*      */ 
/*      */ 
/*      */         
/* 4412 */         outBuf = this.mTextBuffer.finishCurrentSegment();
/* 4413 */         outPtr = 0;
/*      */       } 
/*      */     } 
/* 4416 */     this.mTextBuffer.setCurrentLength(outPtr);
/*      */     
/* 4418 */     if (match) {
/* 4419 */       return true;
/*      */     }
/*      */     
/* 4422 */     this.mInputPtr--;
/* 4423 */     return false;
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
/*      */   private final boolean readTextPrimary(char c) throws XMLStreamException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield mInputPtr : I
/*      */     //   4: istore_2
/*      */     //   5: iload_2
/*      */     //   6: iconst_1
/*      */     //   7: isub
/*      */     //   8: istore_3
/*      */     //   9: iload_1
/*      */     //   10: bipush #32
/*      */     //   12: if_icmpgt -> 130
/*      */     //   15: aload_0
/*      */     //   16: getfield mInputEnd : I
/*      */     //   19: istore #4
/*      */     //   21: iload_2
/*      */     //   22: iload #4
/*      */     //   24: if_icmpge -> 122
/*      */     //   27: aload_0
/*      */     //   28: getfield mNormalizeLFs : Z
/*      */     //   31: ifeq -> 122
/*      */     //   34: iload_1
/*      */     //   35: bipush #13
/*      */     //   37: if_icmpne -> 78
/*      */     //   40: bipush #10
/*      */     //   42: istore_1
/*      */     //   43: aload_0
/*      */     //   44: getfield mInputBuffer : [C
/*      */     //   47: iload_2
/*      */     //   48: caload
/*      */     //   49: iload_1
/*      */     //   50: if_icmpne -> 68
/*      */     //   53: iinc #3, 1
/*      */     //   56: iinc #2, 1
/*      */     //   59: iload_2
/*      */     //   60: iload #4
/*      */     //   62: if_icmplt -> 87
/*      */     //   65: goto -> 122
/*      */     //   68: aload_0
/*      */     //   69: getfield mInputBuffer : [C
/*      */     //   72: iload_3
/*      */     //   73: iload_1
/*      */     //   74: castore
/*      */     //   75: goto -> 87
/*      */     //   78: iload_1
/*      */     //   79: bipush #10
/*      */     //   81: if_icmpeq -> 87
/*      */     //   84: goto -> 122
/*      */     //   87: aload_0
/*      */     //   88: iload_2
/*      */     //   89: invokevirtual markLF : (I)V
/*      */     //   92: aload_0
/*      */     //   93: getfield mCheckIndentation : I
/*      */     //   96: ifle -> 112
/*      */     //   99: aload_0
/*      */     //   100: iload_1
/*      */     //   101: iload_2
/*      */     //   102: invokespecial readIndentation : (CI)I
/*      */     //   105: istore_2
/*      */     //   106: iload_2
/*      */     //   107: ifge -> 112
/*      */     //   110: iconst_1
/*      */     //   111: ireturn
/*      */     //   112: aload_0
/*      */     //   113: getfield mInputBuffer : [C
/*      */     //   116: iload_2
/*      */     //   117: iinc #2, 1
/*      */     //   120: caload
/*      */     //   121: istore_1
/*      */     //   122: aload_0
/*      */     //   123: iconst_0
/*      */     //   124: putfield mWsStatus : I
/*      */     //   127: goto -> 135
/*      */     //   130: aload_0
/*      */     //   131: iconst_2
/*      */     //   132: putfield mWsStatus : I
/*      */     //   135: aload_0
/*      */     //   136: getfield mInputBuffer : [C
/*      */     //   139: astore #4
/*      */     //   141: aload_0
/*      */     //   142: getfield mInputEnd : I
/*      */     //   145: istore #5
/*      */     //   147: iload_1
/*      */     //   148: bipush #63
/*      */     //   150: if_icmpge -> 407
/*      */     //   153: iload_1
/*      */     //   154: bipush #60
/*      */     //   156: if_icmpne -> 182
/*      */     //   159: aload_0
/*      */     //   160: iinc #2, -1
/*      */     //   163: iload_2
/*      */     //   164: putfield mInputPtr : I
/*      */     //   167: aload_0
/*      */     //   168: getfield mTextBuffer : Lcom/ctc/wstx/util/TextBuffer;
/*      */     //   171: aload #4
/*      */     //   173: iload_3
/*      */     //   174: iload_2
/*      */     //   175: iload_3
/*      */     //   176: isub
/*      */     //   177: invokevirtual resetWithShared : ([CII)V
/*      */     //   180: iconst_1
/*      */     //   181: ireturn
/*      */     //   182: iload_1
/*      */     //   183: bipush #32
/*      */     //   185: if_icmpge -> 326
/*      */     //   188: iload_1
/*      */     //   189: bipush #10
/*      */     //   191: if_icmpne -> 202
/*      */     //   194: aload_0
/*      */     //   195: iload_2
/*      */     //   196: invokevirtual markLF : (I)V
/*      */     //   199: goto -> 407
/*      */     //   202: iload_1
/*      */     //   203: bipush #13
/*      */     //   205: if_icmpne -> 273
/*      */     //   208: iload_2
/*      */     //   209: iload #5
/*      */     //   211: if_icmplt -> 220
/*      */     //   214: iinc #2, -1
/*      */     //   217: goto -> 427
/*      */     //   220: aload_0
/*      */     //   221: getfield mNormalizeLFs : Z
/*      */     //   224: ifeq -> 253
/*      */     //   227: aload #4
/*      */     //   229: iload_2
/*      */     //   230: caload
/*      */     //   231: bipush #10
/*      */     //   233: if_icmpne -> 242
/*      */     //   236: iinc #2, -1
/*      */     //   239: goto -> 427
/*      */     //   242: aload #4
/*      */     //   244: iload_2
/*      */     //   245: iconst_1
/*      */     //   246: isub
/*      */     //   247: bipush #10
/*      */     //   249: castore
/*      */     //   250: goto -> 265
/*      */     //   253: aload #4
/*      */     //   255: iload_2
/*      */     //   256: caload
/*      */     //   257: bipush #10
/*      */     //   259: if_icmpne -> 265
/*      */     //   262: iinc #2, 1
/*      */     //   265: aload_0
/*      */     //   266: iload_2
/*      */     //   267: invokevirtual markLF : (I)V
/*      */     //   270: goto -> 407
/*      */     //   273: iload_1
/*      */     //   274: bipush #9
/*      */     //   276: if_icmpeq -> 407
/*      */     //   279: aload_0
/*      */     //   280: iload_2
/*      */     //   281: putfield mInputPtr : I
/*      */     //   284: aload_0
/*      */     //   285: getfield mTextBuffer : Lcom/ctc/wstx/util/TextBuffer;
/*      */     //   288: aload #4
/*      */     //   290: iload_3
/*      */     //   291: iload_2
/*      */     //   292: iload_3
/*      */     //   293: isub
/*      */     //   294: iconst_1
/*      */     //   295: isub
/*      */     //   296: invokevirtual resetWithShared : ([CII)V
/*      */     //   299: iload_2
/*      */     //   300: iload_3
/*      */     //   301: isub
/*      */     //   302: iconst_1
/*      */     //   303: if_icmple -> 310
/*      */     //   306: iconst_1
/*      */     //   307: goto -> 311
/*      */     //   310: iconst_0
/*      */     //   311: istore #6
/*      */     //   313: aload_0
/*      */     //   314: aload_0
/*      */     //   315: iload_1
/*      */     //   316: iload #6
/*      */     //   318: invokevirtual throwInvalidSpace : (IZ)Lcom/ctc/wstx/exc/WstxException;
/*      */     //   321: putfield mPendingException : Ljavax/xml/stream/XMLStreamException;
/*      */     //   324: iconst_1
/*      */     //   325: ireturn
/*      */     //   326: iload_1
/*      */     //   327: bipush #38
/*      */     //   329: if_icmpne -> 338
/*      */     //   332: iinc #2, -1
/*      */     //   335: goto -> 427
/*      */     //   338: iload_1
/*      */     //   339: bipush #62
/*      */     //   341: if_icmpne -> 407
/*      */     //   344: iload_2
/*      */     //   345: iload_3
/*      */     //   346: isub
/*      */     //   347: iconst_3
/*      */     //   348: if_icmplt -> 407
/*      */     //   351: aload #4
/*      */     //   353: iload_2
/*      */     //   354: iconst_3
/*      */     //   355: isub
/*      */     //   356: caload
/*      */     //   357: bipush #93
/*      */     //   359: if_icmpne -> 407
/*      */     //   362: aload #4
/*      */     //   364: iload_2
/*      */     //   365: iconst_2
/*      */     //   366: isub
/*      */     //   367: caload
/*      */     //   368: bipush #93
/*      */     //   370: if_icmpne -> 407
/*      */     //   373: aload_0
/*      */     //   374: iload_2
/*      */     //   375: putfield mInputPtr : I
/*      */     //   378: aload_0
/*      */     //   379: getfield mTextBuffer : Lcom/ctc/wstx/util/TextBuffer;
/*      */     //   382: aload #4
/*      */     //   384: iload_3
/*      */     //   385: iload_2
/*      */     //   386: iload_3
/*      */     //   387: isub
/*      */     //   388: iconst_1
/*      */     //   389: isub
/*      */     //   390: invokevirtual resetWithShared : ([CII)V
/*      */     //   393: aload_0
/*      */     //   394: aload_0
/*      */     //   395: getstatic com/ctc/wstx/cfg/ErrorConsts.ERR_BRACKET_IN_TEXT : Ljava/lang/String;
/*      */     //   398: iconst_1
/*      */     //   399: invokevirtual throwWfcException : (Ljava/lang/String;Z)Lcom/ctc/wstx/exc/WstxException;
/*      */     //   402: putfield mPendingException : Ljavax/xml/stream/XMLStreamException;
/*      */     //   405: iconst_1
/*      */     //   406: ireturn
/*      */     //   407: iload_2
/*      */     //   408: iload #5
/*      */     //   410: if_icmplt -> 416
/*      */     //   413: goto -> 427
/*      */     //   416: aload #4
/*      */     //   418: iload_2
/*      */     //   419: iinc #2, 1
/*      */     //   422: caload
/*      */     //   423: istore_1
/*      */     //   424: goto -> 147
/*      */     //   427: aload_0
/*      */     //   428: iload_2
/*      */     //   429: putfield mInputPtr : I
/*      */     //   432: aload_0
/*      */     //   433: getfield mTextBuffer : Lcom/ctc/wstx/util/TextBuffer;
/*      */     //   436: aload #4
/*      */     //   438: iload_3
/*      */     //   439: iload_2
/*      */     //   440: iload_3
/*      */     //   441: isub
/*      */     //   442: invokevirtual resetWithShared : ([CII)V
/*      */     //   445: iconst_0
/*      */     //   446: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #4444	-> 0
/*      */     //   #4445	-> 5
/*      */     //   #4448	-> 9
/*      */     //   #4449	-> 15
/*      */     //   #4459	-> 21
/*      */     //   #4460	-> 34
/*      */     //   #4461	-> 40
/*      */     //   #4462	-> 43
/*      */     //   #4466	-> 53
/*      */     //   #4468	-> 56
/*      */     //   #4469	-> 65
/*      */     //   #4472	-> 68
/*      */     //   #4474	-> 78
/*      */     //   #4475	-> 84
/*      */     //   #4477	-> 87
/*      */     //   #4478	-> 92
/*      */     //   #4479	-> 99
/*      */     //   #4480	-> 106
/*      */     //   #4481	-> 110
/*      */     //   #4485	-> 112
/*      */     //   #4490	-> 122
/*      */     //   #4491	-> 127
/*      */     //   #4492	-> 130
/*      */     //   #4495	-> 135
/*      */     //   #4496	-> 141
/*      */     //   #4500	-> 147
/*      */     //   #4501	-> 153
/*      */     //   #4502	-> 159
/*      */     //   #4503	-> 167
/*      */     //   #4504	-> 180
/*      */     //   #4506	-> 182
/*      */     //   #4507	-> 188
/*      */     //   #4508	-> 194
/*      */     //   #4509	-> 202
/*      */     //   #4510	-> 208
/*      */     //   #4511	-> 214
/*      */     //   #4512	-> 217
/*      */     //   #4514	-> 220
/*      */     //   #4515	-> 227
/*      */     //   #4516	-> 236
/*      */     //   #4517	-> 239
/*      */     //   #4525	-> 242
/*      */     //   #4528	-> 253
/*      */     //   #4529	-> 262
/*      */     //   #4532	-> 265
/*      */     //   #4533	-> 273
/*      */     //   #4535	-> 279
/*      */     //   #4536	-> 284
/*      */     //   #4541	-> 299
/*      */     //   #4542	-> 313
/*      */     //   #4543	-> 324
/*      */     //   #4545	-> 326
/*      */     //   #4547	-> 332
/*      */     //   #4548	-> 335
/*      */     //   #4549	-> 338
/*      */     //   #4551	-> 344
/*      */     //   #4552	-> 351
/*      */     //   #4557	-> 373
/*      */     //   #4558	-> 378
/*      */     //   #4559	-> 393
/*      */     //   #4560	-> 405
/*      */     //   #4566	-> 407
/*      */     //   #4567	-> 413
/*      */     //   #4569	-> 416
/*      */     //   #4571	-> 427
/*      */     //   #4579	-> 432
/*      */     //   #4580	-> 445
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   21	106	4	len	I
/*      */     //   313	13	6	deferErrors	Z
/*      */     //   0	447	0	this	Lcom/ctc/wstx/sr/BasicStreamReader;
/*      */     //   0	447	1	c	C
/*      */     //   5	442	2	ptr	I
/*      */     //   9	438	3	start	I
/*      */     //   141	306	4	inputBuf	[C
/*      */     //   147	300	5	inputLen	I
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
/*      */   protected final boolean readTextSecondary(int shortestSegment, boolean deferErrors) throws XMLStreamException {
/* 4599 */     char[] outBuf = this.mTextBuffer.getCurrentSegment();
/* 4600 */     int outPtr = this.mTextBuffer.getCurrentSegmentSize();
/* 4601 */     int inputPtr = this.mInputPtr;
/* 4602 */     char[] inputBuffer = this.mInputBuffer;
/* 4603 */     int inputLen = this.mInputEnd;
/*      */     
/*      */     while (true) {
/* 4606 */       if (inputPtr >= inputLen) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4612 */         this.mInputPtr = inputPtr;
/* 4613 */         if (!loadMore()) {
/*      */           break;
/*      */         }
/* 4616 */         inputPtr = this.mInputPtr;
/* 4617 */         inputBuffer = this.mInputBuffer;
/* 4618 */         inputLen = this.mInputEnd;
/*      */       } 
/* 4620 */       char c = inputBuffer[inputPtr++];
/*      */ 
/*      */       
/* 4623 */       if (c < '?') {
/* 4624 */         if (c < ' ') {
/* 4625 */           if (c == '\n') {
/* 4626 */             markLF(inputPtr);
/* 4627 */           } else if (c == '\r') {
/* 4628 */             this.mInputPtr = inputPtr;
/* 4629 */             if (skipCRLF(c)) {
/* 4630 */               if (!this.mNormalizeLFs) {
/*      */                 
/* 4632 */                 outBuf[outPtr++] = c;
/* 4633 */                 if (outPtr >= outBuf.length) {
/* 4634 */                   outBuf = this.mTextBuffer.finishCurrentSegment();
/* 4635 */                   outPtr = 0;
/*      */                 } 
/*      */               } 
/*      */               
/* 4639 */               c = '\n';
/* 4640 */             } else if (this.mNormalizeLFs) {
/* 4641 */               c = '\n';
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4648 */             inputLen = this.mInputEnd;
/* 4649 */             inputPtr = this.mInputPtr;
/* 4650 */           } else if (c != '\t') {
/* 4651 */             this.mTextBuffer.setCurrentLength(outPtr);
/* 4652 */             this.mInputPtr = inputPtr;
/* 4653 */             this.mPendingException = (XMLStreamException)throwInvalidSpace(c, deferErrors); break;
/*      */           } 
/*      */         } else {
/* 4656 */           if (c == '<') {
/* 4657 */             this.mInputPtr = inputPtr - 1; break;
/*      */           } 
/* 4659 */           if (c == '&') {
/* 4660 */             int ch; this.mInputPtr = inputPtr;
/*      */             
/* 4662 */             if (this.mCfgReplaceEntities) {
/* 4663 */               if (inputLen - inputPtr < 3 || (ch = resolveSimpleEntity(true)) == 0) {
/*      */ 
/*      */ 
/*      */                 
/* 4667 */                 ch = fullyResolveEntity(true);
/* 4668 */                 if (ch == 0) {
/*      */                   
/* 4670 */                   inputBuffer = this.mInputBuffer;
/* 4671 */                   inputLen = this.mInputEnd;
/* 4672 */                   inputPtr = this.mInputPtr;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */               } 
/*      */             } else {
/* 4681 */               ch = resolveCharOnlyEntity(true);
/* 4682 */               if (ch == 0) {
/*      */ 
/*      */ 
/*      */                 
/* 4686 */                 this.mInputPtr--;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 4691 */             if (ch <= 65535) {
/* 4692 */               c = (char)ch;
/*      */             } else {
/* 4694 */               ch -= 65536;
/*      */               
/* 4696 */               if (outPtr >= outBuf.length) {
/* 4697 */                 outBuf = this.mTextBuffer.finishCurrentSegment();
/* 4698 */                 outPtr = 0;
/*      */               } 
/* 4700 */               outBuf[outPtr++] = (char)((ch >> 10) + 55296);
/* 4701 */               c = (char)((ch & 0x3FF) + 56320);
/*      */             } 
/* 4703 */             inputPtr = this.mInputPtr;
/*      */             
/* 4705 */             inputLen = this.mInputEnd;
/* 4706 */           } else if (c == '>') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4714 */             if (inputPtr > 2)
/*      */             {
/* 4716 */               if (inputBuffer[inputPtr - 3] == ']' && inputBuffer[inputPtr - 2] == ']') {
/*      */                 
/* 4718 */                 this.mInputPtr = inputPtr;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 4723 */                 this.mTextBuffer.setCurrentLength(outPtr);
/* 4724 */                 this.mPendingException = (XMLStreamException)throwWfcException(ErrorConsts.ERR_BRACKET_IN_TEXT, deferErrors);
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 4737 */       outBuf[outPtr++] = c;
/*      */ 
/*      */       
/* 4740 */       if (outPtr >= outBuf.length) {
/* 4741 */         TextBuffer tb = this.mTextBuffer;
/*      */         
/* 4743 */         tb.setCurrentLength(outBuf.length);
/* 4744 */         if (tb.size() >= shortestSegment) {
/* 4745 */           this.mInputPtr = inputPtr;
/* 4746 */           return false;
/*      */         } 
/*      */         
/* 4749 */         outBuf = tb.finishCurrentSegment();
/* 4750 */         outPtr = 0;
/*      */       } 
/*      */     } 
/*      */     
/* 4754 */     this.mTextBuffer.setCurrentLength(outPtr);
/* 4755 */     return true;
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
/*      */   private final int readIndentation(char c, int ptr) throws XMLStreamException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield mInputEnd : I
/*      */     //   4: istore_3
/*      */     //   5: aload_0
/*      */     //   6: getfield mInputBuffer : [C
/*      */     //   9: astore #4
/*      */     //   11: iload_2
/*      */     //   12: iconst_1
/*      */     //   13: isub
/*      */     //   14: istore #5
/*      */     //   16: iload_1
/*      */     //   17: istore #6
/*      */     //   19: aload #4
/*      */     //   21: iload_2
/*      */     //   22: iinc #2, 1
/*      */     //   25: caload
/*      */     //   26: istore_1
/*      */     //   27: iload_1
/*      */     //   28: bipush #32
/*      */     //   30: if_icmpeq -> 39
/*      */     //   33: iload_1
/*      */     //   34: bipush #9
/*      */     //   36: if_icmpne -> 118
/*      */     //   39: iload_1
/*      */     //   40: bipush #32
/*      */     //   42: if_icmpne -> 50
/*      */     //   45: bipush #32
/*      */     //   47: goto -> 52
/*      */     //   50: bipush #8
/*      */     //   52: istore #7
/*      */     //   54: iload #7
/*      */     //   56: iload_2
/*      */     //   57: iadd
/*      */     //   58: istore #7
/*      */     //   60: iload #7
/*      */     //   62: iload_3
/*      */     //   63: if_icmple -> 69
/*      */     //   66: iload_3
/*      */     //   67: istore #7
/*      */     //   69: iload_2
/*      */     //   70: iload #7
/*      */     //   72: if_icmplt -> 81
/*      */     //   75: iinc #2, -1
/*      */     //   78: goto -> 196
/*      */     //   81: aload #4
/*      */     //   83: iload_2
/*      */     //   84: iinc #2, 1
/*      */     //   87: caload
/*      */     //   88: istore #8
/*      */     //   90: iload #8
/*      */     //   92: iload_1
/*      */     //   93: if_icmpeq -> 112
/*      */     //   96: iload #8
/*      */     //   98: bipush #60
/*      */     //   100: if_icmpne -> 106
/*      */     //   103: goto -> 115
/*      */     //   106: iinc #2, -1
/*      */     //   109: goto -> 196
/*      */     //   112: goto -> 69
/*      */     //   115: goto -> 130
/*      */     //   118: iload_1
/*      */     //   119: bipush #60
/*      */     //   121: if_icmpeq -> 130
/*      */     //   124: iinc #2, -1
/*      */     //   127: goto -> 196
/*      */     //   130: iload_2
/*      */     //   131: iload_3
/*      */     //   132: if_icmpge -> 193
/*      */     //   135: aload #4
/*      */     //   137: iload_2
/*      */     //   138: caload
/*      */     //   139: bipush #33
/*      */     //   141: if_icmpeq -> 193
/*      */     //   144: aload_0
/*      */     //   145: iinc #2, -1
/*      */     //   148: iload_2
/*      */     //   149: putfield mInputPtr : I
/*      */     //   152: aload_0
/*      */     //   153: getfield mTextBuffer : Lcom/ctc/wstx/util/TextBuffer;
/*      */     //   156: iload_2
/*      */     //   157: iload #5
/*      */     //   159: isub
/*      */     //   160: iconst_1
/*      */     //   161: isub
/*      */     //   162: iload_1
/*      */     //   163: invokevirtual resetWithIndentation : (IC)V
/*      */     //   166: aload_0
/*      */     //   167: getfield mCheckIndentation : I
/*      */     //   170: bipush #40
/*      */     //   172: if_icmpge -> 186
/*      */     //   175: aload_0
/*      */     //   176: dup
/*      */     //   177: getfield mCheckIndentation : I
/*      */     //   180: bipush #16
/*      */     //   182: iadd
/*      */     //   183: putfield mCheckIndentation : I
/*      */     //   186: aload_0
/*      */     //   187: iconst_1
/*      */     //   188: putfield mWsStatus : I
/*      */     //   191: iconst_m1
/*      */     //   192: ireturn
/*      */     //   193: iinc #2, -1
/*      */     //   196: aload_0
/*      */     //   197: dup
/*      */     //   198: getfield mCheckIndentation : I
/*      */     //   201: iconst_1
/*      */     //   202: isub
/*      */     //   203: putfield mCheckIndentation : I
/*      */     //   206: iload #6
/*      */     //   208: bipush #13
/*      */     //   210: if_icmpne -> 220
/*      */     //   213: aload #4
/*      */     //   215: iload #5
/*      */     //   217: bipush #10
/*      */     //   219: castore
/*      */     //   220: iload_2
/*      */     //   221: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #4785	-> 0
/*      */     //   #4786	-> 5
/*      */     //   #4787	-> 11
/*      */     //   #4788	-> 16
/*      */     //   #4793	-> 19
/*      */     //   #4794	-> 27
/*      */     //   #4796	-> 39
/*      */     //   #4797	-> 54
/*      */     //   #4798	-> 60
/*      */     //   #4799	-> 66
/*      */     //   #4804	-> 69
/*      */     //   #4805	-> 75
/*      */     //   #4806	-> 78
/*      */     //   #4808	-> 81
/*      */     //   #4809	-> 90
/*      */     //   #4810	-> 96
/*      */     //   #4811	-> 103
/*      */     //   #4813	-> 106
/*      */     //   #4814	-> 109
/*      */     //   #4816	-> 112
/*      */     //   #4818	-> 115
/*      */     //   #4819	-> 124
/*      */     //   #4820	-> 127
/*      */     //   #4824	-> 130
/*      */     //   #4826	-> 144
/*      */     //   #4827	-> 152
/*      */     //   #4829	-> 166
/*      */     //   #4830	-> 175
/*      */     //   #4832	-> 186
/*      */     //   #4833	-> 191
/*      */     //   #4836	-> 193
/*      */     //   #4843	-> 196
/*      */     //   #4849	-> 206
/*      */     //   #4850	-> 213
/*      */     //   #4852	-> 220
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   90	22	8	d	C
/*      */     //   54	61	7	lastIndCharPos	I
/*      */     //   0	222	0	this	Lcom/ctc/wstx/sr/BasicStreamReader;
/*      */     //   0	222	1	c	C
/*      */     //   0	222	2	ptr	I
/*      */     //   5	217	3	inputLen	I
/*      */     //   11	211	4	inputBuf	[C
/*      */     //   16	206	5	start	I
/*      */     //   19	203	6	lf	C
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
/*      */   private final boolean readSpacePrimary(char c, boolean prologWS) throws XMLStreamException {
/* 4873 */     int ptr = this.mInputPtr;
/* 4874 */     char[] inputBuf = this.mInputBuffer;
/* 4875 */     int inputLen = this.mInputEnd;
/* 4876 */     int start = ptr - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 4887 */       if (c > ' ') {
/* 4888 */         this.mInputPtr = --ptr;
/* 4889 */         this.mTextBuffer.resetWithShared(this.mInputBuffer, start, ptr - start);
/* 4890 */         return true;
/*      */       } 
/*      */       
/* 4893 */       if (c == '\n') {
/* 4894 */         markLF(ptr);
/* 4895 */       } else if (c == '\r') {
/* 4896 */         if (ptr >= this.mInputEnd) {
/* 4897 */           ptr--;
/*      */           break;
/*      */         } 
/* 4900 */         if (this.mNormalizeLFs) {
/* 4901 */           if (inputBuf[ptr] == '\n') {
/* 4902 */             ptr--;
/*      */             break;
/*      */           } 
/* 4905 */           inputBuf[ptr - 1] = '\n';
/*      */         
/*      */         }
/* 4908 */         else if (inputBuf[ptr] == '\n') {
/* 4909 */           ptr++;
/*      */         } 
/*      */         
/* 4912 */         markLF(ptr);
/* 4913 */       } else if (c != ' ' && c != '\t') {
/* 4914 */         throwInvalidSpace(c);
/*      */       } 
/* 4916 */       if (ptr >= inputLen) {
/*      */         break;
/*      */       }
/* 4919 */       c = inputBuf[ptr++];
/*      */     } 
/*      */     
/* 4922 */     this.mInputPtr = ptr;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4927 */     this.mTextBuffer.resetWithShared(inputBuf, start, ptr - start);
/* 4928 */     return false;
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
/*      */   private void readSpaceSecondary(boolean prologWS) throws XMLStreamException {
/* 4945 */     char[] outBuf = this.mTextBuffer.getCurrentSegment();
/* 4946 */     int outPtr = this.mTextBuffer.getCurrentSegmentSize();
/*      */     
/*      */     while (true) {
/* 4949 */       if (this.mInputPtr >= this.mInputEnd)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 4954 */         if (!loadMore()) {
/*      */           break;
/*      */         }
/*      */       }
/* 4958 */       char c = this.mInputBuffer[this.mInputPtr];
/* 4959 */       if (c > ' ') {
/*      */         break;
/*      */       }
/* 4962 */       this.mInputPtr++;
/* 4963 */       if (c == '\n') {
/* 4964 */         markLF();
/* 4965 */       } else if (c == '\r') {
/* 4966 */         if (skipCRLF(c)) {
/* 4967 */           if (!this.mNormalizeLFs) {
/*      */             
/* 4969 */             outBuf[outPtr++] = c;
/* 4970 */             if (outPtr >= outBuf.length) {
/* 4971 */               outBuf = this.mTextBuffer.finishCurrentSegment();
/* 4972 */               outPtr = 0;
/*      */             } 
/*      */           } 
/* 4975 */           c = '\n';
/* 4976 */         } else if (this.mNormalizeLFs) {
/* 4977 */           c = '\n';
/*      */         } 
/* 4979 */       } else if (c != ' ' && c != '\t') {
/* 4980 */         throwInvalidSpace(c);
/*      */       } 
/*      */ 
/*      */       
/* 4984 */       outBuf[outPtr++] = c;
/*      */ 
/*      */       
/* 4987 */       if (outPtr >= outBuf.length) {
/* 4988 */         outBuf = this.mTextBuffer.finishCurrentSegment();
/* 4989 */         outPtr = 0;
/*      */       } 
/*      */     } 
/* 4992 */     this.mTextBuffer.setCurrentLength(outPtr);
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
/*      */   private int readAndWriteText(Writer w) throws IOException, XMLStreamException {
/* 5006 */     this.mTokenState = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5014 */     int start = this.mInputPtr;
/* 5015 */     int count = 0;
/*      */ 
/*      */     
/*      */     while (true) {
/*      */       char c;
/*      */       
/* 5021 */       if (this.mInputPtr >= this.mInputEnd) {
/* 5022 */         int i = this.mInputPtr - start;
/* 5023 */         if (i > 0) {
/* 5024 */           w.write(this.mInputBuffer, start, i);
/* 5025 */           count += i;
/*      */         } 
/* 5027 */         c = getNextChar(" in document text content");
/* 5028 */         start = this.mInputPtr - 1;
/*      */       } else {
/* 5030 */         c = this.mInputBuffer[this.mInputPtr++];
/*      */       } 
/*      */       
/* 5033 */       if (c < '?') {
/* 5034 */         if (c < ' ') {
/* 5035 */           if (c == '\n') {
/* 5036 */             markLF(); continue;
/* 5037 */           }  if (c == '\r') {
/*      */             char d;
/* 5039 */             if (this.mInputPtr >= this.mInputEnd) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5044 */               int i = this.mInputPtr - start;
/* 5045 */               if (i > 0) {
/* 5046 */                 w.write(this.mInputBuffer, start, i);
/* 5047 */                 count += i;
/*      */               } 
/* 5049 */               d = getNextChar(" in document text content");
/* 5050 */               start = this.mInputPtr;
/*      */             } else {
/* 5052 */               d = this.mInputBuffer[this.mInputPtr++];
/*      */             } 
/* 5054 */             if (d == '\n') {
/* 5055 */               if (this.mNormalizeLFs)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 5061 */                 int i = this.mInputPtr - 2 - start;
/* 5062 */                 if (i > 0) {
/* 5063 */                   w.write(this.mInputBuffer, start, i);
/* 5064 */                   count += i;
/*      */                 } 
/* 5066 */                 start = this.mInputPtr - 1;
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 5071 */               this.mInputPtr--;
/* 5072 */               if (this.mNormalizeLFs) {
/* 5073 */                 this.mInputBuffer[this.mInputPtr - 1] = '\n';
/*      */               }
/*      */             } 
/* 5076 */             markLF(); continue;
/* 5077 */           }  if (c != '\t')
/* 5078 */             throwInvalidSpace(c);  continue;
/*      */         } 
/* 5080 */         if (c == '<')
/*      */           break; 
/* 5082 */         if (c == '&') {
/*      */ 
/*      */ 
/*      */           
/* 5086 */           int ch, i = this.mInputPtr - 1 - start;
/* 5087 */           if (i > 0) {
/* 5088 */             w.write(this.mInputBuffer, start, i);
/* 5089 */             count += i;
/*      */           } 
/*      */           
/* 5092 */           if (this.mCfgReplaceEntities) {
/* 5093 */             if (this.mInputEnd - this.mInputPtr < 3 || (ch = resolveSimpleEntity(true)) == 0)
/*      */             {
/* 5095 */               ch = fullyResolveEntity(true);
/*      */             }
/*      */           } else {
/* 5098 */             ch = resolveCharOnlyEntity(true);
/* 5099 */             if (ch == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5105 */               start = this.mInputPtr;
/*      */               break;
/*      */             } 
/*      */           } 
/* 5109 */           if (ch != 0) {
/* 5110 */             if (ch <= 65535) {
/* 5111 */               c = (char)ch;
/*      */             } else {
/* 5113 */               ch -= 65536;
/* 5114 */               w.write((char)((ch >> 10) + 55296));
/* 5115 */               c = (char)((ch & 0x3FF) + 56320);
/*      */             } 
/* 5117 */             w.write(c);
/* 5118 */             count++;
/*      */           } 
/* 5120 */           start = this.mInputPtr; continue;
/* 5121 */         }  if (c == '>') {
/*      */ 
/*      */ 
/*      */           
/* 5125 */           if (this.mInputPtr >= 2 && 
/* 5126 */             this.mInputBuffer[this.mInputPtr - 2] == ']' && this.mInputBuffer[this.mInputPtr - 1] == ']') {
/*      */ 
/*      */             
/* 5129 */             int i = this.mInputPtr - start;
/* 5130 */             if (i > 0) {
/* 5131 */               w.write(this.mInputBuffer, start, i);
/*      */             }
/* 5133 */             throwParseError(ErrorConsts.ERR_BRACKET_IN_TEXT);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/* 5138 */         if (c == '\000') {
/* 5139 */           throwNullChar();
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5147 */     this.mInputPtr--;
/*      */ 
/*      */     
/* 5150 */     int len = this.mInputPtr - start;
/* 5151 */     if (len > 0) {
/* 5152 */       w.write(this.mInputBuffer, start, len);
/* 5153 */       count += len;
/*      */     } 
/* 5155 */     return count;
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
/*      */   private int readAndWriteCData(Writer w) throws IOException, XMLStreamException {
/*      */     boolean match;
/* 5171 */     this.mTokenState = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5178 */     char c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextChar(" in CDATA section");
/*      */     
/* 5180 */     int count = 0;
/*      */ 
/*      */     
/*      */     do {
/* 5184 */       int start = this.mInputPtr - 1;
/*      */ 
/*      */       
/*      */       while (true) {
/* 5188 */         if (c > '\r') {
/* 5189 */           if (c == ']') {
/*      */             break;
/*      */           }
/*      */         }
/* 5193 */         else if (c < ' ') {
/* 5194 */           if (c == '\n') {
/* 5195 */             markLF();
/* 5196 */           } else if (c == '\r') {
/*      */             char d;
/* 5198 */             if (this.mInputPtr >= this.mInputEnd) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5203 */               int i = this.mInputPtr - start;
/* 5204 */               if (i > 0) {
/* 5205 */                 w.write(this.mInputBuffer, start, i);
/* 5206 */                 count += i;
/*      */               } 
/* 5208 */               d = getNextChar(" in CDATA section");
/* 5209 */               start = this.mInputPtr;
/*      */             } else {
/* 5211 */               d = this.mInputBuffer[this.mInputPtr++];
/*      */             } 
/* 5213 */             if (d == '\n') {
/* 5214 */               if (this.mNormalizeLFs)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 5220 */                 int i = this.mInputPtr - 2 - start;
/* 5221 */                 if (i > 0) {
/* 5222 */                   w.write(this.mInputBuffer, start, i);
/* 5223 */                   count += i;
/*      */                 } 
/* 5225 */                 start = this.mInputPtr - 1;
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 5230 */               this.mInputPtr--;
/* 5231 */               if (this.mNormalizeLFs) {
/* 5232 */                 this.mInputBuffer[this.mInputPtr - 1] = '\n';
/*      */               }
/*      */             } 
/* 5235 */             markLF();
/* 5236 */           } else if (c != '\t') {
/* 5237 */             throwInvalidSpace(c);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 5242 */         if (this.mInputPtr >= this.mInputEnd) {
/* 5243 */           int i = this.mInputPtr - start;
/* 5244 */           if (i > 0) {
/* 5245 */             w.write(this.mInputBuffer, start, i);
/* 5246 */             count += i;
/*      */           } 
/* 5248 */           start = 0;
/* 5249 */           c = getNextChar(" in CDATA section"); continue;
/*      */         } 
/* 5251 */         c = this.mInputBuffer[this.mInputPtr++];
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5260 */       int len = this.mInputPtr - start - 1;
/* 5261 */       if (len > 0) {
/* 5262 */         w.write(this.mInputBuffer, start, len);
/* 5263 */         count += len;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5271 */       int bracketCount = 0;
/*      */       do {
/* 5273 */         bracketCount++;
/* 5274 */         c = (this.mInputPtr < this.mInputEnd) ? this.mInputBuffer[this.mInputPtr++] : getNextCharFromCurrent(" in CDATA section");
/*      */       }
/* 5276 */       while (c == ']');
/*      */       
/* 5278 */       match = (bracketCount >= 2 && c == '>');
/* 5279 */       if (match) {
/* 5280 */         bracketCount -= 2;
/*      */       }
/* 5282 */       while (bracketCount > 0) {
/* 5283 */         bracketCount--;
/* 5284 */         w.write(93);
/* 5285 */         count++;
/*      */       } 
/* 5287 */     } while (!match);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5295 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readAndWriteCoalesced(Writer w, boolean wasCData) throws IOException, XMLStreamException {
/* 5304 */     this.mTokenState = 4;
/* 5305 */     int count = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5312 */     while (this.mInputPtr < this.mInputEnd || 
/* 5313 */       loadMore()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5321 */       char c = this.mInputBuffer[this.mInputPtr];
/* 5322 */       if (c == '<') {
/*      */         
/* 5324 */         if (this.mInputEnd - this.mInputPtr < 3 && 
/* 5325 */           !ensureInput(3)) {
/*      */           break;
/*      */         }
/*      */         
/* 5329 */         if (this.mInputBuffer[this.mInputPtr + 1] != '!' || this.mInputBuffer[this.mInputPtr + 2] != '[') {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 5335 */         this.mInputPtr += 3;
/*      */         
/* 5337 */         checkCData();
/*      */         
/* 5339 */         count += readAndWriteCData(w);
/* 5340 */         wasCData = true;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 5346 */       if (c == '&' && !wasCData) {
/*      */         break;
/*      */       }
/* 5349 */       count += readAndWriteText(w);
/* 5350 */       wasCData = false;
/*      */     } 
/*      */ 
/*      */     
/* 5354 */     return count;
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
/*      */   protected final boolean skipWS(char c) throws XMLStreamException {
/* 5372 */     if (c > ' ') {
/* 5373 */       return false;
/*      */     }
/*      */     
/*      */     while (true) {
/* 5377 */       if (c == '\n' || c == '\r') {
/* 5378 */         skipCRLF(c);
/* 5379 */       } else if (c != ' ' && c != '\t') {
/* 5380 */         throwInvalidSpace(c);
/*      */       } 
/* 5382 */       if (this.mInputPtr >= this.mInputEnd)
/*      */       {
/* 5384 */         if (!loadMoreFromCurrent()) {
/* 5385 */           return true;
/*      */         }
/*      */       }
/* 5388 */       c = this.mInputBuffer[this.mInputPtr];
/* 5389 */       if (c > ' ') {
/* 5390 */         return true;
/*      */       }
/* 5392 */       this.mInputPtr++;
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
/*      */   protected EntityDecl findEntity(String id, Object arg) throws XMLStreamException {
/* 5406 */     EntityDecl ed = this.mConfig.findCustomInternalEntity(id);
/* 5407 */     if (ed == null && this.mGeneralEntities != null) {
/* 5408 */       ed = (EntityDecl)this.mGeneralEntities.get(id);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 5413 */     if (this.mDocStandalone == 1 && 
/* 5414 */       ed != null && ed.wasDeclaredExternally()) {
/* 5415 */       throwParseError(ErrorConsts.ERR_WF_ENTITY_EXT_DECLARED, ed.getName(), (Object)null);
/*      */     }
/*      */     
/* 5418 */     return ed;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleUndeclaredEntity(String id) throws XMLStreamException {
/* 5424 */     throwParseError((this.mDocStandalone == 1) ? ErrorConsts.ERR_WF_GE_UNDECLARED_SA : ErrorConsts.ERR_WF_GE_UNDECLARED, id, (Object)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleIncompleteEntityProblem(WstxInputSource closing) throws XMLStreamException {
/* 5433 */     String top = this.mElementStack.isEmpty() ? "[ROOT]" : this.mElementStack.getTopElementDesc();
/* 5434 */     throwParseError("Unexpected end of entity expansion for entity &{0}; was expecting a close tag for element <{1}>", closing.getEntityId(), top);
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
/*      */   protected void handleGreedyEntityProblem(WstxInputSource input) throws XMLStreamException {
/* 5452 */     String top = this.mElementStack.isEmpty() ? "[ROOT]" : this.mElementStack.getTopElementDesc();
/* 5453 */     throwParseError("Improper GE/element nesting: entity &" + input.getEntityId() + " contains closing tag for <" + top + ">");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void throwNotTextual(int type) {
/* 5459 */     throw new IllegalStateException("Not a textual event (" + tokenTypeDesc(this.mCurrToken) + ")");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void throwNotTextXxx(int type) {
/* 5465 */     throw new IllegalStateException("getTextXxx() methods can not be called on " + tokenTypeDesc(this.mCurrToken));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void throwNotTextualOrElem(int type) {
/* 5471 */     throw new IllegalStateException(MessageFormat.format(ErrorConsts.ERR_STATE_NOT_ELEM_OR_TEXT, new Object[] { tokenTypeDesc(type) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void throwUnexpectedEOF() throws WstxException {
/* 5481 */     throwUnexpectedEOF("; was expecting a close tag for element <" + this.mElementStack.getTopElementDesc() + ">");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLStreamException _constructUnexpectedInTyped(int nextToken) {
/* 5489 */     if (nextToken == 1) {
/* 5490 */       return (XMLStreamException)_constructTypeException("Element content can not contain child START_ELEMENT when using Typed Access methods", (String)null);
/*      */     }
/* 5492 */     return (XMLStreamException)_constructTypeException("Expected a text token, got " + tokenTypeDesc(nextToken), (String)null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected TypedXMLStreamException _constructTypeException(String msg, String lexicalValue) {
/* 5497 */     return new TypedXMLStreamException(lexicalValue, msg, (Location)getStartLocation());
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
/*      */   protected void reportInvalidContent(int evtType) throws XMLStreamException {
/* 5510 */     throwParseError("Internal error: sub-class should override method");
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\sr\BasicStreamReader.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
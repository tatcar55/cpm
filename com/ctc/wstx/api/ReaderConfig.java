/*      */ package com.ctc.wstx.api;
/*      */ 
/*      */ import com.ctc.wstx.cfg.InputConfigFlags;
/*      */ import com.ctc.wstx.dtd.DTDEventListener;
/*      */ import com.ctc.wstx.ent.EntityDecl;
/*      */ import com.ctc.wstx.ent.IntEntity;
/*      */ import com.ctc.wstx.io.BufferRecycler;
/*      */ import com.ctc.wstx.util.ArgUtil;
/*      */ import com.ctc.wstx.util.DataUtil;
/*      */ import com.ctc.wstx.util.SymbolTable;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.net.URL;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import javax.xml.stream.XMLReporter;
/*      */ import javax.xml.stream.XMLResolver;
/*      */ import org.codehaus.stax2.validation.DTDValidationSchema;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ReaderConfig
/*      */   extends CommonConfig
/*      */   implements InputConfigFlags
/*      */ {
/*      */   static final int PROP_COALESCE_TEXT = 1;
/*      */   static final int PROP_NAMESPACE_AWARE = 2;
/*      */   static final int PROP_REPLACE_ENTITY_REFS = 3;
/*      */   static final int PROP_SUPPORT_EXTERNAL_ENTITIES = 4;
/*      */   static final int PROP_VALIDATE_AGAINST_DTD = 5;
/*      */   static final int PROP_SUPPORT_DTD = 6;
/*      */   public static final int PROP_EVENT_ALLOCATOR = 7;
/*      */   static final int PROP_WARNING_REPORTER = 8;
/*      */   static final int PROP_XML_RESOLVER = 9;
/*      */   static final int PROP_INTERN_NS_URIS = 20;
/*      */   static final int PROP_INTERN_NAMES = 21;
/*      */   static final int PROP_REPORT_CDATA = 22;
/*      */   static final int PROP_REPORT_PROLOG_WS = 23;
/*      */   static final int PROP_PRESERVE_LOCATION = 24;
/*      */   static final int PROP_AUTO_CLOSE_INPUT = 25;
/*      */   static final int PROP_SUPPORT_XMLID = 26;
/*      */   static final int PROP_DTD_OVERRIDE = 27;
/*      */   static final int PROP_NORMALIZE_LFS = 40;
/*      */   static final int PROP_CACHE_DTDS = 42;
/*      */   static final int PROP_CACHE_DTDS_BY_PUBLIC_ID = 43;
/*      */   static final int PROP_LAZY_PARSING = 44;
/*      */   static final int PROP_SUPPORT_DTDPP = 45;
/*      */   static final int PROP_TREAT_CHAR_REFS_AS_ENTS = 46;
/*      */   static final int PROP_INPUT_BUFFER_LENGTH = 50;
/*      */   static final int PROP_MIN_TEXT_SEGMENT = 52;
/*      */   static final int PROP_CUSTOM_INTERNAL_ENTITIES = 53;
/*      */   static final int PROP_DTD_RESOLVER = 54;
/*      */   static final int PROP_ENTITY_RESOLVER = 55;
/*      */   static final int PROP_UNDECLARED_ENTITY_RESOLVER = 56;
/*      */   static final int PROP_BASE_URL = 57;
/*      */   static final int PROP_INPUT_PARSING_MODE = 58;
/*      */   static final int MIN_INPUT_BUFFER_LENGTH = 8;
/*      */   static final int DTD_CACHE_SIZE_J2SE = 12;
/*      */   static final int DTD_CACHE_SIZE_J2ME = 5;
/*      */   static final int DEFAULT_SHORTEST_TEXT_SEGMENT = 64;
/*      */   static final int DEFAULT_FLAGS_FULL = 2965021;
/*      */   static final int DEFAULT_FLAGS_J2ME = 2965021;
/*  230 */   static final HashMap sProperties = new HashMap(64); final boolean mIsJ2MESubset; final SymbolTable mSymbols; int mConfigFlags; int mConfigFlagMods; static final int PROP_INTERN_NAMES_EXPLICIT = 26; static final int PROP_INTERN_NS_URIS_EXPLICIT = 27; int mInputBufferLen; int mMinTextSegmentLen;
/*      */   
/*      */   static {
/*  233 */     sProperties.put("javax.xml.stream.isCoalescing", DataUtil.Integer(1));
/*      */     
/*  235 */     sProperties.put("javax.xml.stream.isNamespaceAware", DataUtil.Integer(2));
/*      */     
/*  237 */     sProperties.put("javax.xml.stream.isReplacingEntityReferences", DataUtil.Integer(3));
/*      */     
/*  239 */     sProperties.put("javax.xml.stream.isSupportingExternalEntities", DataUtil.Integer(4));
/*      */     
/*  241 */     sProperties.put("javax.xml.stream.isValidating", DataUtil.Integer(5));
/*      */     
/*  243 */     sProperties.put("javax.xml.stream.supportDTD", DataUtil.Integer(6));
/*      */ 
/*      */ 
/*      */     
/*  247 */     sProperties.put("javax.xml.stream.allocator", DataUtil.Integer(7));
/*      */     
/*  249 */     sProperties.put("javax.xml.stream.reporter", DataUtil.Integer(8));
/*      */     
/*  251 */     sProperties.put("javax.xml.stream.resolver", DataUtil.Integer(9));
/*      */ 
/*      */ 
/*      */     
/*  255 */     sProperties.put("org.codehaus.stax2.internNames", DataUtil.Integer(21));
/*      */     
/*  257 */     sProperties.put("org.codehaus.stax2.internNsUris", DataUtil.Integer(20));
/*      */     
/*  259 */     sProperties.put("http://java.sun.com/xml/stream/properties/report-cdata-event", DataUtil.Integer(22));
/*      */     
/*  261 */     sProperties.put("org.codehaus.stax2.reportPrologWhitespace", DataUtil.Integer(23));
/*      */     
/*  263 */     sProperties.put("org.codehaus.stax2.preserveLocation", DataUtil.Integer(24));
/*      */     
/*  265 */     sProperties.put("org.codehaus.stax2.closeInputSource", DataUtil.Integer(25));
/*      */     
/*  267 */     sProperties.put("org.codehaus.stax2.supportXmlId", DataUtil.Integer(26));
/*      */     
/*  269 */     sProperties.put("org.codehaus.stax2.propDtdOverride", DataUtil.Integer(27));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  274 */     sProperties.put("com.ctc.wstx.cacheDTDs", DataUtil.Integer(42));
/*      */     
/*  276 */     sProperties.put("com.ctc.wstx.cacheDTDsByPublicId", DataUtil.Integer(43));
/*      */     
/*  278 */     sProperties.put("com.ctc.wstx.lazyParsing", DataUtil.Integer(44));
/*      */     
/*  280 */     sProperties.put("com.ctc.wstx.supportDTDPP", DataUtil.Integer(45));
/*      */     
/*  282 */     sProperties.put("com.ctc.wstx.treatCharRefsAsEnts", DataUtil.Integer(46));
/*      */     
/*  284 */     sProperties.put("com.ctc.wstx.normalizeLFs", DataUtil.Integer(40));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     sProperties.put("com.ctc.wstx.inputBufferLength", DataUtil.Integer(50));
/*      */     
/*  292 */     sProperties.put("com.ctc.wstx.minTextSegment", DataUtil.Integer(52));
/*      */     
/*  294 */     sProperties.put("com.ctc.wstx.customInternalEntities", DataUtil.Integer(53));
/*      */     
/*  296 */     sProperties.put("com.ctc.wstx.dtdResolver", DataUtil.Integer(54));
/*      */     
/*  298 */     sProperties.put("com.ctc.wstx.entityResolver", DataUtil.Integer(55));
/*      */     
/*  300 */     sProperties.put("com.ctc.wstx.undeclaredEntityResolver", DataUtil.Integer(56));
/*      */     
/*  302 */     sProperties.put("com.ctc.wstx.baseURL", DataUtil.Integer(57));
/*      */     
/*  304 */     sProperties.put("com.ctc.wstx.fragmentMode", DataUtil.Integer(58));
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
/*  348 */   URL mBaseURL = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  355 */   WstxInputProperties.ParsingMode mParsingMode = WstxInputProperties.PARSING_MODE_DOCUMENT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean mXml11 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMLReporter mReporter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  375 */   XMLResolver mDtdResolver = null;
/*  376 */   XMLResolver mEntityResolver = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  388 */   Object[] mSpecialProperties = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SPEC_PROC_COUNT = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SP_IX_CUSTOM_ENTITIES = 0;
/*      */ 
/*      */   
/*      */   private static final int SP_IX_UNDECL_ENT_RESOLVER = 1;
/*      */ 
/*      */   
/*      */   private static final int SP_IX_DTD_EVENT_LISTENER = 2;
/*      */ 
/*      */   
/*      */   private static final int SP_IX_DTD_OVERRIDE = 3;
/*      */ 
/*      */   
/*  408 */   static final ThreadLocal mRecyclerRef = new ThreadLocal();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  416 */   BufferRecycler mCurrRecycler = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ReaderConfig(boolean j2meSubset, SymbolTable symbols, int configFlags, int configFlagMods, int inputBufLen, int minTextSegmentLen) {
/*  429 */     this.mIsJ2MESubset = j2meSubset;
/*  430 */     this.mSymbols = symbols;
/*      */     
/*  432 */     this.mConfigFlags = configFlags;
/*  433 */     this.mConfigFlagMods = configFlagMods;
/*      */     
/*  435 */     this.mInputBufferLen = inputBufLen;
/*  436 */     this.mMinTextSegmentLen = minTextSegmentLen;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  444 */     SoftReference ref = mRecyclerRef.get();
/*  445 */     if (ref != null) {
/*  446 */       this.mCurrRecycler = ref.get();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ReaderConfig createJ2MEDefaults() {
/*  455 */     ReaderConfig rc = new ReaderConfig(true, null, 2965021, 0, 2000, 64);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  460 */     return rc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ReaderConfig createFullDefaults() {
/*  468 */     ReaderConfig rc = new ReaderConfig(false, null, 2965021, 0, 4000, 64);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  473 */     return rc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReaderConfig createNonShared(SymbolTable sym) {
/*  480 */     ReaderConfig rc = new ReaderConfig(this.mIsJ2MESubset, sym, this.mConfigFlags, this.mConfigFlagMods, this.mInputBufferLen, this.mMinTextSegmentLen);
/*      */ 
/*      */ 
/*      */     
/*  484 */     rc.mReporter = this.mReporter;
/*  485 */     rc.mDtdResolver = this.mDtdResolver;
/*  486 */     rc.mEntityResolver = this.mEntityResolver;
/*  487 */     rc.mBaseURL = this.mBaseURL;
/*  488 */     rc.mParsingMode = this.mParsingMode;
/*  489 */     if (this.mSpecialProperties != null) {
/*  490 */       int len = this.mSpecialProperties.length;
/*  491 */       Object[] specProps = new Object[len];
/*  492 */       System.arraycopy(this.mSpecialProperties, 0, specProps, 0, len);
/*  493 */       rc.mSpecialProperties = specProps;
/*      */     } 
/*      */     
/*  496 */     return rc;
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
/*      */   public void resetState() {
/*  508 */     this.mXml11 = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int findPropertyId(String propName) {
/*  519 */     Integer I = (Integer)sProperties.get(propName);
/*  520 */     return (I == null) ? -1 : I.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SymbolTable getSymbols() {
/*  531 */     return this.mSymbols;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDtdCacheSize() {
/*  538 */     return this.mIsJ2MESubset ? 5 : 12;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getConfigFlags() {
/*  543 */     return this.mConfigFlags;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean willCoalesceText() {
/*  548 */     return _hasConfigFlag(2);
/*      */   }
/*      */   
/*      */   public boolean willSupportNamespaces() {
/*  552 */     return _hasConfigFlag(1);
/*      */   }
/*      */   
/*      */   public boolean willReplaceEntityRefs() {
/*  556 */     return _hasConfigFlag(4);
/*      */   }
/*      */   
/*      */   public boolean willSupportExternalEntities() {
/*  560 */     return _hasConfigFlag(8);
/*      */   }
/*      */   
/*      */   public boolean willSupportDTDs() {
/*  564 */     return _hasConfigFlag(16);
/*      */   }
/*      */   
/*      */   public boolean willValidateWithDTD() {
/*  568 */     return _hasConfigFlag(32);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean willReportCData() {
/*  574 */     return _hasConfigFlag(512);
/*      */   }
/*      */   
/*      */   public boolean willParseLazily() {
/*  578 */     return _hasConfigFlag(262144);
/*      */   }
/*      */   
/*      */   public boolean willInternNames() {
/*  582 */     return _hasConfigFlag(1024);
/*      */   }
/*      */   
/*      */   public boolean willInternNsURIs() {
/*  586 */     return _hasConfigFlag(2048);
/*      */   }
/*      */   
/*      */   public boolean willPreserveLocation() {
/*  590 */     return _hasConfigFlag(4096);
/*      */   }
/*      */   
/*      */   public boolean willAutoCloseInput() {
/*  594 */     return _hasConfigFlag(8192);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean willReportPrologWhitespace() {
/*  600 */     return _hasConfigFlag(256);
/*      */   }
/*      */   
/*      */   public boolean willCacheDTDs() {
/*  604 */     return _hasConfigFlag(65536);
/*      */   }
/*      */   
/*      */   public boolean willCacheDTDsByPublicId() {
/*  608 */     return _hasConfigFlag(131072);
/*      */   }
/*      */   
/*      */   public boolean willDoXmlIdTyping() {
/*  612 */     return _hasConfigFlag(2097152);
/*      */   }
/*      */   
/*      */   public boolean willDoXmlIdUniqChecks() {
/*  616 */     return _hasConfigFlag(4194304);
/*      */   }
/*      */   
/*      */   public boolean willSupportDTDPP() {
/*  620 */     return _hasConfigFlag(524288);
/*      */   }
/*      */   
/*      */   public boolean willNormalizeLFs() {
/*  624 */     return _hasConfigFlag(8192);
/*      */   }
/*      */   
/*      */   public boolean willTreatCharRefsAsEnts() {
/*  628 */     return _hasConfigFlag(8388608);
/*      */   }
/*      */   public int getInputBufferLength() {
/*  631 */     return this.mInputBufferLen;
/*      */   } public int getShortestReportedTextSegment() {
/*  633 */     return this.mMinTextSegmentLen;
/*      */   }
/*      */   
/*      */   public Map getCustomInternalEntities() {
/*  637 */     Map custEnt = (Map)_getSpecialProperty(0);
/*  638 */     if (custEnt == null) {
/*  639 */       return Collections.EMPTY_MAP;
/*      */     }
/*      */     
/*  642 */     int len = custEnt.size();
/*  643 */     HashMap m = new HashMap(len + (len >> 2), 0.81F);
/*  644 */     Iterator it = custEnt.entrySet().iterator();
/*  645 */     while (it.hasNext()) {
/*  646 */       Map.Entry me = it.next();
/*      */ 
/*      */ 
/*      */       
/*  650 */       m.put(me.getKey(), me.getValue());
/*      */     } 
/*  652 */     return m;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityDecl findCustomInternalEntity(String id) {
/*  657 */     Map custEnt = (Map)_getSpecialProperty(0);
/*  658 */     if (custEnt == null) {
/*  659 */       return null;
/*      */     }
/*  661 */     return (EntityDecl)custEnt.get(id);
/*      */   }
/*      */   public XMLReporter getXMLReporter() {
/*  664 */     return this.mReporter;
/*      */   } public XMLResolver getXMLResolver() {
/*  666 */     return this.mEntityResolver;
/*      */   }
/*  668 */   public XMLResolver getDtdResolver() { return this.mDtdResolver; } public XMLResolver getEntityResolver() {
/*  669 */     return this.mEntityResolver;
/*      */   } public XMLResolver getUndeclaredEntityResolver() {
/*  671 */     return (XMLResolver)_getSpecialProperty(1);
/*      */   }
/*      */   public URL getBaseURL() {
/*  674 */     return this.mBaseURL;
/*      */   }
/*      */   public WstxInputProperties.ParsingMode getInputParsingMode() {
/*  677 */     return this.mParsingMode;
/*      */   }
/*      */   
/*      */   public boolean inputParsingModeDocuments() {
/*  681 */     return (this.mParsingMode == WstxInputProperties.PARSING_MODE_DOCUMENTS);
/*      */   }
/*      */   
/*      */   public boolean inputParsingModeFragment() {
/*  685 */     return (this.mParsingMode == WstxInputProperties.PARSING_MODE_FRAGMENT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isXml11() {
/*  694 */     return this.mXml11;
/*      */   }
/*      */   
/*      */   public DTDEventListener getDTDEventListener() {
/*  698 */     return (DTDEventListener)_getSpecialProperty(2);
/*      */   }
/*      */   
/*      */   public DTDValidationSchema getDTDOverride() {
/*  702 */     return (DTDValidationSchema)_getSpecialProperty(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasInternNamesBeenEnabled() {
/*  711 */     return _hasExplicitConfigFlag(1024);
/*      */   }
/*      */   
/*      */   public boolean hasInternNsURIsBeenEnabled() {
/*  715 */     return _hasExplicitConfigFlag(2048);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConfigFlag(int flag) {
/*  725 */     this.mConfigFlags |= flag;
/*  726 */     this.mConfigFlagMods |= flag;
/*      */   }
/*      */   
/*      */   public void clearConfigFlag(int flag) {
/*  730 */     this.mConfigFlags &= flag ^ 0xFFFFFFFF;
/*  731 */     this.mConfigFlagMods |= flag;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void doCoalesceText(boolean state) {
/*  737 */     setConfigFlag(2, state);
/*      */   }
/*      */   
/*      */   public void doSupportNamespaces(boolean state) {
/*  741 */     setConfigFlag(1, state);
/*      */   }
/*      */   
/*      */   public void doReplaceEntityRefs(boolean state) {
/*  745 */     setConfigFlag(4, state);
/*      */   }
/*      */   
/*      */   public void doSupportExternalEntities(boolean state) {
/*  749 */     setConfigFlag(8, state);
/*      */   }
/*      */   
/*      */   public void doSupportDTDs(boolean state) {
/*  753 */     setConfigFlag(16, state);
/*      */   }
/*      */   
/*      */   public void doValidateWithDTD(boolean state) {
/*  757 */     setConfigFlag(32, state);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void doInternNames(boolean state) {
/*  763 */     setConfigFlag(1024, state);
/*      */   }
/*      */   
/*      */   public void doInternNsURIs(boolean state) {
/*  767 */     setConfigFlag(2048, state);
/*      */   }
/*      */   
/*      */   public void doReportPrologWhitespace(boolean state) {
/*  771 */     setConfigFlag(256, state);
/*      */   }
/*      */   
/*      */   public void doReportCData(boolean state) {
/*  775 */     setConfigFlag(512, state);
/*      */   }
/*      */   
/*      */   public void doCacheDTDs(boolean state) {
/*  779 */     setConfigFlag(65536, state);
/*      */   }
/*      */   
/*      */   public void doCacheDTDsByPublicId(boolean state) {
/*  783 */     setConfigFlag(131072, state);
/*      */   }
/*      */   
/*      */   public void doParseLazily(boolean state) {
/*  787 */     setConfigFlag(262144, state);
/*      */   }
/*      */   
/*      */   public void doXmlIdTyping(boolean state) {
/*  791 */     setConfigFlag(2097152, state);
/*      */   }
/*      */   
/*      */   public void doXmlIdUniqChecks(boolean state) {
/*  795 */     setConfigFlag(4194304, state);
/*      */   }
/*      */   
/*      */   public void doPreserveLocation(boolean state) {
/*  799 */     setConfigFlag(4096, state);
/*      */   }
/*      */   
/*      */   public void doAutoCloseInput(boolean state) {
/*  803 */     setConfigFlag(8192, state);
/*      */   }
/*      */   
/*      */   public void doSupportDTDPP(boolean state) {
/*  807 */     setConfigFlag(524288, state);
/*      */   }
/*      */   
/*      */   public void doTreatCharRefsAsEnts(boolean state) {
/*  811 */     setConfigFlag(8388608, state);
/*      */   }
/*      */   
/*      */   public void doNormalizeLFs(boolean state) {
/*  815 */     setConfigFlag(8192, state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInputBufferLength(int value) {
/*  823 */     if (value < 8) {
/*  824 */       value = 8;
/*      */     }
/*  826 */     this.mInputBufferLen = value;
/*      */   }
/*      */   
/*      */   public void setShortestReportedTextSegment(int value) {
/*  830 */     this.mMinTextSegmentLen = value;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCustomInternalEntities(Map m) {
/*      */     Map entMap;
/*  836 */     if (m == null || m.size() < 1) {
/*  837 */       entMap = Collections.EMPTY_MAP;
/*      */     } else {
/*  839 */       int len = m.size();
/*  840 */       entMap = new HashMap(len + (len >> 1), 0.75F);
/*  841 */       Iterator it = m.entrySet().iterator();
/*  842 */       while (it.hasNext()) {
/*  843 */         char[] ch; Map.Entry me = it.next();
/*  844 */         Object val = me.getValue();
/*      */         
/*  846 */         if (val == null) {
/*  847 */           ch = DataUtil.getEmptyCharArray();
/*  848 */         } else if (val instanceof char[]) {
/*  849 */           ch = (char[])val;
/*      */         } else {
/*      */           
/*  852 */           String str = val.toString();
/*  853 */           ch = str.toCharArray();
/*      */         } 
/*  855 */         String name = (String)me.getKey();
/*  856 */         entMap.put(name, IntEntity.create(name, ch));
/*      */       } 
/*      */     } 
/*  859 */     _setSpecialProperty(0, entMap);
/*      */   }
/*      */   
/*      */   public void setXMLReporter(XMLReporter r) {
/*  863 */     this.mReporter = r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXMLResolver(XMLResolver r) {
/*  871 */     this.mEntityResolver = r;
/*  872 */     this.mDtdResolver = r;
/*      */   }
/*      */   
/*      */   public void setDtdResolver(XMLResolver r) {
/*  876 */     this.mDtdResolver = r;
/*      */   }
/*      */   
/*      */   public void setEntityResolver(XMLResolver r) {
/*  880 */     this.mEntityResolver = r;
/*      */   }
/*      */   
/*      */   public void setUndeclaredEntityResolver(XMLResolver r) {
/*  884 */     _setSpecialProperty(1, r);
/*      */   }
/*      */   public void setBaseURL(URL baseURL) {
/*  887 */     this.mBaseURL = baseURL;
/*      */   }
/*      */   public void setInputParsingMode(WstxInputProperties.ParsingMode mode) {
/*  890 */     this.mParsingMode = mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableXml11(boolean state) {
/*  898 */     this.mXml11 = state;
/*      */   }
/*      */   
/*      */   public void setDTDEventListener(DTDEventListener l) {
/*  902 */     _setSpecialProperty(2, l);
/*      */   }
/*      */   
/*      */   public void setDTDOverride(DTDValidationSchema schema) {
/*  906 */     _setSpecialProperty(3, schema);
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
/*      */   public void configureForXmlConformance() {
/*  934 */     doSupportNamespaces(true);
/*  935 */     doSupportDTDs(true);
/*  936 */     doSupportExternalEntities(true);
/*  937 */     doReplaceEntityRefs(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  942 */     doXmlIdTyping(true);
/*  943 */     doXmlIdUniqChecks(true);
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
/*      */   public void configureForConvenience() {
/*  968 */     doCoalesceText(true);
/*  969 */     doReplaceEntityRefs(true);
/*      */ 
/*      */     
/*  972 */     doReportCData(false);
/*  973 */     doReportPrologWhitespace(false);
/*      */ 
/*      */ 
/*      */     
/*  977 */     doPreserveLocation(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  984 */     doParseLazily(false);
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
/*      */   public void configureForSpeed() {
/* 1018 */     doCoalesceText(false);
/*      */ 
/*      */     
/* 1021 */     doPreserveLocation(false);
/* 1022 */     doReportPrologWhitespace(false);
/*      */     
/* 1024 */     doInternNsURIs(true);
/* 1025 */     doXmlIdUniqChecks(false);
/*      */ 
/*      */     
/* 1028 */     doCacheDTDs(true);
/* 1029 */     doParseLazily(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1036 */     setShortestReportedTextSegment(16);
/* 1037 */     setInputBufferLength(8000);
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
/*      */   public void configureForLowMemUsage() {
/* 1067 */     doCoalesceText(false);
/*      */ 
/*      */ 
/*      */     
/* 1071 */     doPreserveLocation(false);
/*      */ 
/*      */     
/* 1074 */     doCacheDTDs(false);
/* 1075 */     doParseLazily(true);
/* 1076 */     doXmlIdUniqChecks(false);
/* 1077 */     setShortestReportedTextSegment(64);
/* 1078 */     setInputBufferLength(512);
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
/*      */   public void configureForRoundTripping() {
/* 1106 */     doCoalesceText(false);
/* 1107 */     doReplaceEntityRefs(false);
/*      */ 
/*      */     
/* 1110 */     doReportCData(true);
/* 1111 */     doReportPrologWhitespace(true);
/*      */ 
/*      */     
/* 1114 */     doTreatCharRefsAsEnts(true);
/* 1115 */     doNormalizeLFs(false);
/*      */ 
/*      */     
/* 1118 */     setShortestReportedTextSegment(2147483647);
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
/*      */   public char[] allocSmallCBuffer(int minSize) {
/* 1130 */     if (this.mCurrRecycler != null) {
/* 1131 */       char[] result = this.mCurrRecycler.getSmallCBuffer(minSize);
/* 1132 */       if (result != null) {
/* 1133 */         return result;
/*      */       }
/*      */     } 
/*      */     
/* 1137 */     return new char[minSize];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeSmallCBuffer(char[] buffer) {
/* 1144 */     if (this.mCurrRecycler == null) {
/* 1145 */       this.mCurrRecycler = createRecycler();
/*      */     }
/* 1147 */     this.mCurrRecycler.returnSmallCBuffer(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] allocMediumCBuffer(int minSize) {
/* 1153 */     if (this.mCurrRecycler != null) {
/* 1154 */       char[] result = this.mCurrRecycler.getMediumCBuffer(minSize);
/* 1155 */       if (result != null) {
/* 1156 */         return result;
/*      */       }
/*      */     } 
/* 1159 */     return new char[minSize];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeMediumCBuffer(char[] buffer) {
/* 1165 */     if (this.mCurrRecycler == null) {
/* 1166 */       this.mCurrRecycler = createRecycler();
/*      */     }
/* 1168 */     this.mCurrRecycler.returnMediumCBuffer(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] allocFullCBuffer(int minSize) {
/* 1174 */     if (this.mCurrRecycler != null) {
/* 1175 */       char[] result = this.mCurrRecycler.getFullCBuffer(minSize);
/* 1176 */       if (result != null) {
/* 1177 */         return result;
/*      */       }
/*      */     } 
/* 1180 */     return new char[minSize];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeFullCBuffer(char[] buffer) {
/* 1187 */     if (this.mCurrRecycler == null) {
/* 1188 */       this.mCurrRecycler = createRecycler();
/*      */     }
/* 1190 */     this.mCurrRecycler.returnFullCBuffer(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] allocFullBBuffer(int minSize) {
/* 1196 */     if (this.mCurrRecycler != null) {
/* 1197 */       byte[] result = this.mCurrRecycler.getFullBBuffer(minSize);
/* 1198 */       if (result != null) {
/* 1199 */         return result;
/*      */       }
/*      */     } 
/* 1202 */     return new byte[minSize];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeFullBBuffer(byte[] buffer) {
/* 1209 */     if (this.mCurrRecycler == null) {
/* 1210 */       this.mCurrRecycler = createRecycler();
/*      */     }
/* 1212 */     this.mCurrRecycler.returnFullBBuffer(buffer);
/*      */   }
/*      */   
/* 1215 */   static int Counter = 0;
/*      */ 
/*      */   
/*      */   private BufferRecycler createRecycler() {
/* 1219 */     BufferRecycler recycler = new BufferRecycler();
/*      */ 
/*      */     
/* 1222 */     mRecyclerRef.set(new SoftReference(recycler));
/* 1223 */     return recycler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setConfigFlag(int flag, boolean state) {
/* 1234 */     if (state) {
/* 1235 */       this.mConfigFlags |= flag;
/*      */     } else {
/* 1237 */       this.mConfigFlags &= flag ^ 0xFFFFFFFF;
/*      */     } 
/* 1239 */     this.mConfigFlagMods |= flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getProperty(int id) {
/* 1244 */     switch (id) {
/*      */ 
/*      */       
/*      */       case 1:
/* 1248 */         return willCoalesceText() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 2:
/* 1250 */         return willSupportNamespaces() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 3:
/* 1252 */         return willReplaceEntityRefs() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 4:
/* 1254 */         return willSupportExternalEntities() ? Boolean.TRUE : Boolean.FALSE;
/*      */       
/*      */       case 5:
/* 1257 */         return willValidateWithDTD() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 6:
/* 1259 */         return willSupportDTDs() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 8:
/* 1261 */         return getXMLReporter();
/*      */       case 9:
/* 1263 */         return getXMLResolver();
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 1268 */         return null;
/*      */ 
/*      */ 
/*      */       
/*      */       case 23:
/* 1273 */         return willReportPrologWhitespace() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 22:
/* 1275 */         return willReportCData() ? Boolean.TRUE : Boolean.FALSE;
/*      */       
/*      */       case 21:
/* 1278 */         return willInternNames() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 20:
/* 1280 */         return willInternNsURIs() ? Boolean.TRUE : Boolean.FALSE;
/*      */       
/*      */       case 24:
/* 1283 */         return willPreserveLocation() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 25:
/* 1285 */         return willAutoCloseInput() ? Boolean.TRUE : Boolean.FALSE;
/*      */       
/*      */       case 27:
/* 1288 */         return getDTDOverride();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 42:
/* 1294 */         return willCacheDTDs() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 43:
/* 1296 */         return willCacheDTDsByPublicId() ? Boolean.TRUE : Boolean.FALSE;
/*      */       case 44:
/* 1298 */         return willParseLazily() ? Boolean.TRUE : Boolean.FALSE;
/*      */       
/*      */       case 26:
/* 1301 */         if (!_hasConfigFlag(2097152)) {
/* 1302 */           return "disable";
/*      */         }
/* 1304 */         return _hasConfigFlag(4194304) ? "xmlidFull" : "xmlidTyping";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 46:
/* 1310 */         return willTreatCharRefsAsEnts() ? Boolean.TRUE : Boolean.FALSE;
/*      */       
/*      */       case 40:
/* 1313 */         return willNormalizeLFs() ? Boolean.TRUE : Boolean.FALSE;
/*      */ 
/*      */       
/*      */       case 50:
/* 1317 */         return DataUtil.Integer(getInputBufferLength());
/*      */       case 52:
/* 1319 */         return DataUtil.Integer(getShortestReportedTextSegment());
/*      */       case 53:
/* 1321 */         return getCustomInternalEntities();
/*      */       case 54:
/* 1323 */         return getDtdResolver();
/*      */       case 55:
/* 1325 */         return getEntityResolver();
/*      */       case 56:
/* 1327 */         return getUndeclaredEntityResolver();
/*      */       case 57:
/* 1329 */         return getBaseURL();
/*      */       case 58:
/* 1331 */         return getInputParsingMode();
/*      */     } 
/*      */     
/* 1334 */     throw new IllegalStateException("Internal error: no handler for property with internal id " + id + ".");
/*      */   }
/*      */   public boolean setProperty(String propName, int id, Object value) {
/*      */     boolean typing;
/*      */     URL u;
/*      */     boolean uniq;
/* 1340 */     switch (id) {
/*      */ 
/*      */       
/*      */       case 1:
/* 1344 */         doCoalesceText(ArgUtil.convertToBoolean(propName, value));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1516 */         return true;case 2: doSupportNamespaces(ArgUtil.convertToBoolean(propName, value)); return true;case 3: doReplaceEntityRefs(ArgUtil.convertToBoolean(propName, value)); return true;case 4: doSupportExternalEntities(ArgUtil.convertToBoolean(propName, value)); return true;case 6: doSupportDTDs(ArgUtil.convertToBoolean(propName, value)); return true;case 5: doValidateWithDTD(ArgUtil.convertToBoolean(propName, value)); return true;case 8: setXMLReporter((XMLReporter)value); return true;case 9: setXMLResolver((XMLResolver)value); return true;case 7: return false;case 20: doInternNsURIs(ArgUtil.convertToBoolean(propName, value)); return true;case 21: doInternNames(ArgUtil.convertToBoolean(propName, value)); return true;case 22: doReportCData(ArgUtil.convertToBoolean(propName, value)); return true;case 23: doReportPrologWhitespace(ArgUtil.convertToBoolean(propName, value)); return true;case 24: doPreserveLocation(ArgUtil.convertToBoolean(propName, value)); return true;case 25: doAutoCloseInput(ArgUtil.convertToBoolean(propName, value)); return true;case 26: if ("disable".equals(value)) { typing = uniq = false; } else if ("xmlidTyping".equals(value)) { typing = true; uniq = false; } else if ("xmlidFull".equals(value)) { typing = uniq = true; } else { throw new IllegalArgumentException("Illegal argument ('" + value + "') to set property " + "org.codehaus.stax2.supportXmlId" + " to: has to be one of '" + "disable" + "', '" + "xmlidTyping" + "' or '" + "xmlidFull" + "'"); }  setConfigFlag(2097152, typing); setConfigFlag(4194304, uniq); return true;case 27: setDTDOverride((DTDValidationSchema)value); return true;case 42: doCacheDTDs(ArgUtil.convertToBoolean(propName, value)); return true;case 43: doCacheDTDsByPublicId(ArgUtil.convertToBoolean(propName, value)); return true;case 44: doParseLazily(ArgUtil.convertToBoolean(propName, value)); return true;case 46: doTreatCharRefsAsEnts(ArgUtil.convertToBoolean(propName, value)); return true;case 40: doNormalizeLFs(ArgUtil.convertToBoolean(propName, value)); return true;case 50: setInputBufferLength(ArgUtil.convertToInt(propName, value, 1)); return true;case 52: setShortestReportedTextSegment(ArgUtil.convertToInt(propName, value, 1)); return true;case 53: setCustomInternalEntities((Map)value); return true;case 54: setDtdResolver((XMLResolver)value); return true;case 55: setEntityResolver((XMLResolver)value); return true;case 56: setUndeclaredEntityResolver((XMLResolver)value); return true;case 57: if (value == null) { u = null; } else if (value instanceof URL) { u = (URL)value; } else { try { u = new URL(value.toString()); } catch (Exception ioe) { Exception exception1; throw new IllegalArgumentException(exception1.getMessage(), exception1); }  }  setBaseURL(u); return true;case 58: setInputParsingMode((WstxInputProperties.ParsingMode)value); return true;
/*      */     } 
/*      */     throw new IllegalStateException("Internal error: no handler for property with internal id " + id + ".");
/*      */   } protected boolean _hasConfigFlag(int flag) {
/* 1520 */     return ((this.mConfigFlags & flag) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _hasExplicitConfigFlag(int flag) {
/* 1529 */     return (_hasConfigFlag(flag) && (this.mConfigFlagMods & flag) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private final Object _getSpecialProperty(int ix) {
/* 1534 */     if (this.mSpecialProperties == null) {
/* 1535 */       return null;
/*      */     }
/* 1537 */     return this.mSpecialProperties[ix];
/*      */   }
/*      */ 
/*      */   
/*      */   private final void _setSpecialProperty(int ix, Object value) {
/* 1542 */     if (this.mSpecialProperties == null) {
/* 1543 */       this.mSpecialProperties = new Object[4];
/*      */     }
/* 1545 */     this.mSpecialProperties[ix] = value;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\api\ReaderConfig.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
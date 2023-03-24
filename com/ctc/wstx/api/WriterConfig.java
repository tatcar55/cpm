/*     */ package com.ctc.wstx.api;
/*     */ 
/*     */ import com.ctc.wstx.cfg.OutputConfigFlags;
/*     */ import com.ctc.wstx.io.BufferRecycler;
/*     */ import com.ctc.wstx.util.ArgUtil;
/*     */ import com.ctc.wstx.util.DataUtil;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.stream.XMLReporter;
/*     */ import org.codehaus.stax2.io.EscapingWriterFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WriterConfig
/*     */   extends CommonConfig
/*     */   implements OutputConfigFlags
/*     */ {
/*     */   protected static final String DEFAULT_AUTOMATIC_NS_PREFIX = "wstxns";
/*     */   static final int PROP_AUTOMATIC_NS = 1;
/*     */   static final int PROP_AUTOMATIC_EMPTY_ELEMENTS = 2;
/*     */   static final int PROP_AUTO_CLOSE_OUTPUT = 3;
/*     */   static final int PROP_ENABLE_NS = 4;
/*     */   static final int PROP_AUTOMATIC_NS_PREFIX = 5;
/*     */   static final int PROP_TEXT_ESCAPER = 6;
/*     */   static final int PROP_ATTR_VALUE_ESCAPER = 7;
/*     */   static final int PROP_PROBLEM_REPORTER = 8;
/*     */   static final int PROP_OUTPUT_CDATA_AS_TEXT = 11;
/*     */   static final int PROP_COPY_DEFAULT_ATTRS = 12;
/*     */   static final int PROP_ESCAPE_CR = 13;
/*     */   static final int PROP_ADD_SPACE_AFTER_EMPTY_ELEM = 14;
/*     */   static final int PROP_AUTOMATIC_END_ELEMENTS = 15;
/*     */   static final int PROP_VALIDATE_STRUCTURE = 16;
/*     */   static final int PROP_VALIDATE_CONTENT = 17;
/*     */   static final int PROP_VALIDATE_ATTR = 18;
/*     */   static final int PROP_VALIDATE_NAMES = 19;
/*     */   static final int PROP_FIX_CONTENT = 20;
/*     */   static final int PROP_OUTPUT_INVALID_CHAR_HANDLER = 21;
/*     */   static final int PROP_OUTPUT_EMPTY_ELEMENT_HANDLER = 22;
/*     */   static final int PROP_UNDERLYING_STREAM = 30;
/*     */   static final int PROP_UNDERLYING_WRITER = 31;
/*     */   static final boolean DEFAULT_OUTPUT_CDATA_AS_TEXT = false;
/*     */   static final boolean DEFAULT_COPY_DEFAULT_ATTRS = false;
/*     */   static final boolean DEFAULT_ESCAPE_CR = true;
/*     */   static final boolean DEFAULT_ADD_SPACE_AFTER_EMPTY_ELEM = false;
/*     */   static final boolean DEFAULT_VALIDATE_STRUCTURE = true;
/*     */   static final boolean DEFAULT_VALIDATE_CONTENT = true;
/*     */   static final boolean DEFAULT_VALIDATE_ATTR = false;
/*     */   static final boolean DEFAULT_VALIDATE_NAMES = false;
/*     */   static final boolean DEFAULT_FIX_CONTENT = false;
/*     */   static final int DEFAULT_FLAGS_J2ME = 933;
/*     */   static final int DEFAULT_FLAGS_FULL = 933;
/* 164 */   static final HashMap sProperties = new HashMap(8); final boolean mIsJ2MESubset; protected int mConfigFlags;
/*     */   
/*     */   static {
/* 167 */     sProperties.put("javax.xml.stream.isRepairingNamespaces", DataUtil.Integer(1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     sProperties.put("javax.xml.stream.isNamespaceAware", DataUtil.Integer(4));
/*     */ 
/*     */ 
/*     */     
/* 177 */     sProperties.put("org.codehaus.stax2.automaticEmptyElements", DataUtil.Integer(2));
/*     */     
/* 179 */     sProperties.put("org.codehaus.stax2.autoCloseOutput", DataUtil.Integer(3));
/*     */ 
/*     */     
/* 182 */     sProperties.put("org.codehaus.stax2.automaticNsPrefix", DataUtil.Integer(5));
/*     */ 
/*     */     
/* 185 */     sProperties.put("org.codehaus.stax2.textEscaper", DataUtil.Integer(6));
/*     */     
/* 187 */     sProperties.put("org.codehaus.stax2.attrValueEscaper", DataUtil.Integer(7));
/*     */ 
/*     */     
/* 190 */     sProperties.put("javax.xml.stream.reporter", DataUtil.Integer(8));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     sProperties.put("com.ctc.wstx.outputCDataAsText", DataUtil.Integer(11));
/*     */     
/* 198 */     sProperties.put("com.ctc.wstx.copyDefaultAttrs", DataUtil.Integer(12));
/*     */     
/* 200 */     sProperties.put("com.ctc.wstx.outputEscapeCr", DataUtil.Integer(13));
/*     */     
/* 202 */     sProperties.put("com.ctc.wstx.addSpaceAfterEmptyElem", DataUtil.Integer(14));
/*     */ 
/*     */     
/* 205 */     sProperties.put("com.ctc.wstx.automaticEndElements", DataUtil.Integer(15));
/*     */     
/* 207 */     sProperties.put("com.ctc.wstx.outputInvalidCharHandler", DataUtil.Integer(21));
/*     */     
/* 209 */     sProperties.put("com.ctc.wstx.outputEmptyElementHandler", DataUtil.Integer(22));
/*     */ 
/*     */ 
/*     */     
/* 213 */     sProperties.put("com.ctc.wstx.outputValidateStructure", DataUtil.Integer(16));
/*     */     
/* 215 */     sProperties.put("com.ctc.wstx.outputValidateContent", DataUtil.Integer(17));
/*     */     
/* 217 */     sProperties.put("com.ctc.wstx.outputValidateAttr", DataUtil.Integer(18));
/*     */     
/* 219 */     sProperties.put("com.ctc.wstx.outputValidateNames", DataUtil.Integer(19));
/*     */     
/* 221 */     sProperties.put("com.ctc.wstx.outputFixContent", DataUtil.Integer(20));
/*     */ 
/*     */ 
/*     */     
/* 225 */     sProperties.put("com.ctc.wstx.outputUnderlyingStream", DataUtil.Integer(30));
/*     */     
/* 227 */     sProperties.put("com.ctc.wstx.outputUnderlyingStream", DataUtil.Integer(30));
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
/*     */ 
/*     */   
/* 253 */   Object[] mSpecialProperties = null;
/*     */ 
/*     */   
/*     */   private static final int SPEC_PROC_COUNT = 6;
/*     */ 
/*     */   
/*     */   private static final int SP_IX_AUTO_NS_PREFIX = 0;
/*     */ 
/*     */   
/*     */   private static final int SP_IX_TEXT_ESCAPER_FACTORY = 1;
/*     */ 
/*     */   
/*     */   private static final int SP_IX_ATTR_VALUE_ESCAPER_FACTORY = 2;
/*     */ 
/*     */   
/*     */   private static final int SP_IX_PROBLEM_REPORTER = 3;
/*     */ 
/*     */   
/*     */   private static final int SP_IX_INVALID_CHAR_HANDLER = 4;
/*     */   
/*     */   private static final int SP_IX_EMPTY_ELEMENT_HANDLER = 5;
/*     */   
/* 275 */   static final ThreadLocal mRecyclerRef = new ThreadLocal();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   BufferRecycler mCurrRecycler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WriterConfig(boolean j2meSubset, int flags, Object[] specProps) {
/* 293 */     this.mIsJ2MESubset = j2meSubset;
/* 294 */     this.mConfigFlags = flags;
/* 295 */     this.mSpecialProperties = specProps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     SoftReference ref = mRecyclerRef.get();
/* 304 */     if (ref != null) {
/* 305 */       this.mCurrRecycler = ref.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static WriterConfig createJ2MEDefaults() {
/* 311 */     return new WriterConfig(true, 933, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static WriterConfig createFullDefaults() {
/* 316 */     return new WriterConfig(true, 933, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WriterConfig createNonShared() {
/*     */     Object[] specProps;
/* 323 */     if (this.mSpecialProperties != null) {
/* 324 */       int len = this.mSpecialProperties.length;
/* 325 */       specProps = new Object[len];
/* 326 */       System.arraycopy(this.mSpecialProperties, 0, specProps, 0, len);
/*     */     } else {
/* 328 */       specProps = null;
/*     */     } 
/* 330 */     return new WriterConfig(this.mIsJ2MESubset, this.mConfigFlags, specProps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int findPropertyId(String propName) {
/* 341 */     Integer I = (Integer)sProperties.get(propName);
/* 342 */     return (I == null) ? -1 : I.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(int id) {
/* 353 */     switch (id) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 358 */         return automaticNamespacesEnabled() ? Boolean.TRUE : Boolean.FALSE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 365 */         return willSupportNamespaces() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 8:
/* 367 */         return getProblemReporter();
/*     */ 
/*     */       
/*     */       case 2:
/* 371 */         return automaticEmptyElementsEnabled() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 3:
/* 373 */         return willAutoCloseOutput() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 5:
/* 375 */         return getAutomaticNsPrefix();
/*     */       case 6:
/* 377 */         return getTextEscaperFactory();
/*     */       case 7:
/* 379 */         return getAttrValueEscaperFactory();
/*     */ 
/*     */ 
/*     */       
/*     */       case 11:
/* 384 */         return willOutputCDataAsText() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 12:
/* 386 */         return willCopyDefaultAttrs() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 13:
/* 388 */         return willEscapeCr() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 14:
/* 390 */         return willAddSpaceAfterEmptyElem() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 15:
/* 392 */         return automaticEndElementsEnabled() ? Boolean.TRUE : Boolean.FALSE;
/*     */       
/*     */       case 16:
/* 395 */         return willValidateStructure() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 17:
/* 397 */         return willValidateContent() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 18:
/* 399 */         return willValidateAttributes() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 19:
/* 401 */         return willValidateNames() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 20:
/* 403 */         return willFixContent() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 21:
/* 405 */         return getInvalidCharHandler();
/*     */       case 22:
/* 407 */         return getEmptyElementHandler();
/*     */ 
/*     */       
/*     */       case 30:
/*     */       case 31:
/* 412 */         throw new IllegalStateException("Can not access per-stream-writer properties via factory");
/*     */     } 
/*     */     
/* 415 */     throw new IllegalStateException("Internal error: no handler for property with internal id " + id + ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setProperty(String name, int id, Object value) {
/* 424 */     switch (id) {
/*     */ 
/*     */       
/*     */       case 1:
/* 428 */         enableAutomaticNamespaces(ArgUtil.convertToBoolean(name, value));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 508 */         return true;case 4: doSupportNamespaces(ArgUtil.convertToBoolean(name, value)); return true;case 8: setProblemReporter((XMLReporter)value); return true;case 2: enableAutomaticEmptyElements(ArgUtil.convertToBoolean(name, value)); return true;case 3: doAutoCloseOutput(ArgUtil.convertToBoolean(name, value)); return true;case 5: setAutomaticNsPrefix(value.toString()); return true;case 6: setTextEscaperFactory((EscapingWriterFactory)value); return true;case 7: setAttrValueEscaperFactory((EscapingWriterFactory)value); return true;case 11: doOutputCDataAsText(ArgUtil.convertToBoolean(name, value)); return true;case 12: doCopyDefaultAttrs(ArgUtil.convertToBoolean(name, value)); return true;case 13: doEscapeCr(ArgUtil.convertToBoolean(name, value)); return true;case 14: doAddSpaceAfterEmptyElem(ArgUtil.convertToBoolean(name, value)); return true;case 15: enableAutomaticEndElements(ArgUtil.convertToBoolean(name, value)); return true;case 16: doValidateStructure(ArgUtil.convertToBoolean(name, value)); return true;case 17: doValidateContent(ArgUtil.convertToBoolean(name, value)); return true;case 18: doValidateAttributes(ArgUtil.convertToBoolean(name, value)); return true;case 19: doValidateNames(ArgUtil.convertToBoolean(name, value)); return true;case 20: doFixContent(ArgUtil.convertToBoolean(name, value)); return true;case 21: setInvalidCharHandler((InvalidCharHandler)value); return true;case 22: setEmptyElementHandler((EmptyElementHandler)value); return true;
/*     */       case 30:
/*     */       case 31:
/*     */         throw new IllegalStateException("Can not modify per-stream-writer properties via factory");
/*     */     } 
/*     */     throw new IllegalStateException("Internal error: no handler for property with internal id " + id + ".");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConfigFlags() {
/* 519 */     return this.mConfigFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean automaticNamespacesEnabled() {
/* 525 */     return hasConfigFlag(2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean automaticEmptyElementsEnabled() {
/* 531 */     return hasConfigFlag(4);
/*     */   }
/*     */   
/*     */   public boolean willAutoCloseOutput() {
/* 535 */     return hasConfigFlag(8192);
/*     */   }
/*     */   
/*     */   public boolean willSupportNamespaces() {
/* 539 */     return hasConfigFlag(1);
/*     */   }
/*     */   
/*     */   public boolean willOutputCDataAsText() {
/* 543 */     return hasConfigFlag(8);
/*     */   }
/*     */   
/*     */   public boolean willCopyDefaultAttrs() {
/* 547 */     return hasConfigFlag(16);
/*     */   }
/*     */   
/*     */   public boolean willEscapeCr() {
/* 551 */     return hasConfigFlag(32);
/*     */   }
/*     */   
/*     */   public boolean willAddSpaceAfterEmptyElem() {
/* 555 */     return hasConfigFlag(64);
/*     */   }
/*     */   
/*     */   public boolean automaticEndElementsEnabled() {
/* 559 */     return hasConfigFlag(128);
/*     */   }
/*     */   
/*     */   public boolean willValidateStructure() {
/* 563 */     return hasConfigFlag(256);
/*     */   }
/*     */   
/*     */   public boolean willValidateContent() {
/* 567 */     return hasConfigFlag(512);
/*     */   }
/*     */   
/*     */   public boolean willValidateAttributes() {
/* 571 */     return hasConfigFlag(2048);
/*     */   }
/*     */   
/*     */   public boolean willValidateNames() {
/* 575 */     return hasConfigFlag(1024);
/*     */   }
/*     */   
/*     */   public boolean willFixContent() {
/* 579 */     return hasConfigFlag(4096);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAutomaticNsPrefix() {
/* 588 */     String prefix = (String)getSpecialProperty(0);
/* 589 */     if (prefix == null) {
/* 590 */       prefix = "wstxns";
/*     */     }
/* 592 */     return prefix;
/*     */   }
/*     */   
/*     */   public EscapingWriterFactory getTextEscaperFactory() {
/* 596 */     return (EscapingWriterFactory)getSpecialProperty(1);
/*     */   }
/*     */   
/*     */   public EscapingWriterFactory getAttrValueEscaperFactory() {
/* 600 */     return (EscapingWriterFactory)getSpecialProperty(2);
/*     */   }
/*     */   
/*     */   public XMLReporter getProblemReporter() {
/* 604 */     return (XMLReporter)getSpecialProperty(3);
/*     */   }
/*     */   
/*     */   public InvalidCharHandler getInvalidCharHandler() {
/* 608 */     return (InvalidCharHandler)getSpecialProperty(4);
/*     */   }
/*     */   
/*     */   public EmptyElementHandler getEmptyElementHandler() {
/* 612 */     return (EmptyElementHandler)getSpecialProperty(5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableAutomaticNamespaces(boolean state) {
/* 620 */     setConfigFlag(2, state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableAutomaticEmptyElements(boolean state) {
/* 626 */     setConfigFlag(4, state);
/*     */   }
/*     */   
/*     */   public void doAutoCloseOutput(boolean state) {
/* 630 */     setConfigFlag(8192, state);
/*     */   }
/*     */   
/*     */   public void doSupportNamespaces(boolean state) {
/* 634 */     setConfigFlag(1, state);
/*     */   }
/*     */   
/*     */   public void doOutputCDataAsText(boolean state) {
/* 638 */     setConfigFlag(8, state);
/*     */   }
/*     */   
/*     */   public void doCopyDefaultAttrs(boolean state) {
/* 642 */     setConfigFlag(16, state);
/*     */   }
/*     */   
/*     */   public void doEscapeCr(boolean state) {
/* 646 */     setConfigFlag(32, state);
/*     */   }
/*     */   
/*     */   public void doAddSpaceAfterEmptyElem(boolean state) {
/* 650 */     setConfigFlag(64, state);
/*     */   }
/*     */   
/*     */   public void enableAutomaticEndElements(boolean state) {
/* 654 */     setConfigFlag(128, state);
/*     */   }
/*     */   
/*     */   public void doValidateStructure(boolean state) {
/* 658 */     setConfigFlag(256, state);
/*     */   }
/*     */   
/*     */   public void doValidateContent(boolean state) {
/* 662 */     setConfigFlag(512, state);
/*     */   }
/*     */   
/*     */   public void doValidateAttributes(boolean state) {
/* 666 */     setConfigFlag(2048, state);
/*     */   }
/*     */   
/*     */   public void doValidateNames(boolean state) {
/* 670 */     setConfigFlag(1024, state);
/*     */   }
/*     */   
/*     */   public void doFixContent(boolean state) {
/* 674 */     setConfigFlag(4096, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutomaticNsPrefix(String prefix) {
/* 682 */     setSpecialProperty(0, prefix);
/*     */   }
/*     */   
/*     */   public void setTextEscaperFactory(EscapingWriterFactory f) {
/* 686 */     setSpecialProperty(1, f);
/*     */   }
/*     */   
/*     */   public void setAttrValueEscaperFactory(EscapingWriterFactory f) {
/* 690 */     setSpecialProperty(2, f);
/*     */   }
/*     */   
/*     */   public void setProblemReporter(XMLReporter rep) {
/* 694 */     setSpecialProperty(3, rep);
/*     */   }
/*     */   
/*     */   public void setInvalidCharHandler(InvalidCharHandler h) {
/* 698 */     setSpecialProperty(4, h);
/*     */   }
/*     */   
/*     */   public void setEmptyElementHandler(EmptyElementHandler h) {
/* 702 */     setSpecialProperty(5, h);
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
/*     */   public void configureForXmlConformance() {
/* 717 */     doValidateAttributes(true);
/* 718 */     doValidateContent(true);
/* 719 */     doValidateStructure(true);
/* 720 */     doValidateNames(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureForRobustness() {
/* 730 */     doValidateAttributes(true);
/* 731 */     doValidateStructure(true);
/* 732 */     doValidateNames(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 738 */     doValidateContent(true);
/* 739 */     doFixContent(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureForSpeed() {
/* 749 */     doValidateAttributes(false);
/* 750 */     doValidateContent(false);
/* 751 */     doValidateNames(false);
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
/*     */   public char[] allocMediumCBuffer(int minSize) {
/* 768 */     if (this.mCurrRecycler != null) {
/* 769 */       char[] result = this.mCurrRecycler.getMediumCBuffer(minSize);
/* 770 */       if (result != null) {
/* 771 */         return result;
/*     */       }
/*     */     } 
/* 774 */     return new char[minSize];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void freeMediumCBuffer(char[] buffer) {
/* 780 */     if (this.mCurrRecycler == null) {
/* 781 */       this.mCurrRecycler = createRecycler();
/*     */     }
/* 783 */     this.mCurrRecycler.returnMediumCBuffer(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public char[] allocFullCBuffer(int minSize) {
/* 788 */     if (this.mCurrRecycler != null) {
/* 789 */       char[] result = this.mCurrRecycler.getFullCBuffer(minSize);
/* 790 */       if (result != null) {
/* 791 */         return result;
/*     */       }
/*     */     } 
/* 794 */     return new char[minSize];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void freeFullCBuffer(char[] buffer) {
/* 800 */     if (this.mCurrRecycler == null) {
/* 801 */       this.mCurrRecycler = createRecycler();
/*     */     }
/* 803 */     this.mCurrRecycler.returnFullCBuffer(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] allocFullBBuffer(int minSize) {
/* 808 */     if (this.mCurrRecycler != null) {
/* 809 */       byte[] result = this.mCurrRecycler.getFullBBuffer(minSize);
/* 810 */       if (result != null) {
/* 811 */         return result;
/*     */       }
/*     */     } 
/* 814 */     return new byte[minSize];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void freeFullBBuffer(byte[] buffer) {
/* 820 */     if (this.mCurrRecycler == null) {
/* 821 */       this.mCurrRecycler = createRecycler();
/*     */     }
/* 823 */     this.mCurrRecycler.returnFullBBuffer(buffer);
/*     */   }
/*     */   
/* 826 */   static int Counter = 0;
/*     */ 
/*     */   
/*     */   private BufferRecycler createRecycler() {
/* 830 */     BufferRecycler recycler = new BufferRecycler();
/*     */     
/* 832 */     mRecyclerRef.set(new SoftReference(recycler));
/* 833 */     return recycler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setConfigFlag(int flag, boolean state) {
/* 843 */     if (state) {
/* 844 */       this.mConfigFlags |= flag;
/*     */     } else {
/* 846 */       this.mConfigFlags &= flag ^ 0xFFFFFFFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   private final boolean hasConfigFlag(int flag) {
/* 851 */     return ((this.mConfigFlags & flag) == flag);
/*     */   }
/*     */ 
/*     */   
/*     */   private final Object getSpecialProperty(int ix) {
/* 856 */     if (this.mSpecialProperties == null) {
/* 857 */       return null;
/*     */     }
/* 859 */     return this.mSpecialProperties[ix];
/*     */   }
/*     */ 
/*     */   
/*     */   private final void setSpecialProperty(int ix, Object value) {
/* 864 */     if (this.mSpecialProperties == null) {
/* 865 */       this.mSpecialProperties = new Object[6];
/*     */     }
/* 867 */     this.mSpecialProperties[ix] = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\api\WriterConfig.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
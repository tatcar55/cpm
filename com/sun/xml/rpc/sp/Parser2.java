/*      */ package com.sun.xml.rpc.sp;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.Locator;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Parser2
/*      */ {
/*   80 */   private String curName = null;
/*   81 */   private String curValue = null;
/*      */   
/*   83 */   private String curURI = null;
/*      */ 
/*      */   
/*      */   private InputEntity in;
/*      */   
/*      */   private AttributesExImpl attTmp;
/*      */   
/*   90 */   private String[] parts = new String[3];
/*      */   private StringBuffer strTmp;
/*      */   private char[] nameTmp;
/*      */   private NameCache nameCache;
/*   94 */   private char[] charTmp = new char[2];
/*      */   
/*      */   private boolean namespace = false;
/*      */   
/*   98 */   private NamespaceSupport ns = null;
/*      */   
/*      */   private boolean isInAttribute = false;
/*      */   
/*      */   private boolean rejectDTDs = false;
/*      */   
/*      */   private boolean inExternalPE;
/*      */   
/*      */   private boolean doLexicalPE;
/*      */   
/*      */   private boolean donePrologue;
/*      */   
/*      */   private boolean doneEpilogue;
/*      */   private boolean doneContent;
/*  112 */   private AttributesExImpl attr = null;
/*  113 */   private int attrIndex = 0;
/*      */   
/*      */   private boolean startEmptyStack = true;
/*      */   
/*      */   private boolean isStandalone;
/*      */   
/*      */   private String rootElementName;
/*      */   
/*      */   private boolean ignoreDeclarations;
/*  122 */   private SimpleHashtable elements = new SimpleHashtable(47);
/*  123 */   private SimpleHashtable params = new SimpleHashtable(7);
/*      */ 
/*      */   
/*  126 */   Map notations = new HashMap<Object, Object>(7);
/*  127 */   SimpleHashtable entities = new SimpleHashtable(17);
/*      */ 
/*      */ 
/*      */   
/*      */   static final String strANY = "ANY";
/*      */ 
/*      */   
/*      */   static final String strEMPTY = "EMPTY";
/*      */ 
/*      */   
/*      */   private Locale locale;
/*      */ 
/*      */   
/*      */   private EntityResolver resolver;
/*      */ 
/*      */   
/*      */   Locator locator;
/*      */ 
/*      */   
/*      */   private boolean fastStandalone = false;
/*      */ 
/*      */   
/*      */   private static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
/*      */ 
/*      */   
/*      */   private static final String XmlLang = "xml:lang";
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocale(Locale l) throws ParseException {
/*  157 */     if (l != null && !messages.isLocaleSupported(l.toString()))
/*  158 */       fatal(messages.getMessage(this.locale, "P-078", new Object[] { l })); 
/*  159 */     this.locale = l;
/*      */   }
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  164 */     return this.locale;
/*      */   }
/*      */   
/*      */   public String getCurName() {
/*  168 */     return this.curName;
/*      */   }
/*      */   
/*      */   public String getCurURI() {
/*  172 */     return this.curURI;
/*      */   }
/*      */   
/*      */   public String getCurValue() {
/*  176 */     return this.curValue;
/*      */   }
/*      */   
/*      */   public NamespaceSupport getNamespaceSupport() {
/*  180 */     return this.ns;
/*      */   }
/*      */   
/*      */   public AttributesEx getAttributes() {
/*  184 */     return this.attr;
/*      */   }
/*      */   
/*      */   public int getLineNumber() {
/*  188 */     return this.locator.getLineNumber();
/*      */   }
/*      */   
/*      */   public int getColumnNumber() {
/*  192 */     return this.locator.getColumnNumber();
/*      */   }
/*      */   
/*      */   public String getPublicId() {
/*  196 */     return this.locator.getPublicId();
/*      */   }
/*      */   
/*      */   public String getSystemId() {
/*  200 */     return this.locator.getSystemId();
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
/*      */   public Locale chooseLocale(String[] languages) throws ParseException {
/*  219 */     Locale l = messages.chooseLocale(languages);
/*      */     
/*  221 */     if (l != null)
/*  222 */       setLocale(l); 
/*  223 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityResolver(EntityResolver r) {
/*  228 */     this.resolver = r;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  233 */     return this.resolver;
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
/*      */   public void setFastStandalone(boolean value) {
/*  250 */     this.fastStandalone = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFastStandalone() {
/*  258 */     return this.fastStandalone;
/*      */   }
/*      */ 
/*      */   
/*      */   private void init() {
/*  263 */     this.in = null;
/*      */ 
/*      */     
/*  266 */     this.attTmp = new AttributesExImpl();
/*  267 */     this.strTmp = new StringBuffer();
/*  268 */     this.nameTmp = new char[20];
/*  269 */     this.nameCache = new NameCache();
/*      */     
/*  271 */     if (this.namespace) {
/*  272 */       if (this.ns == null) {
/*  273 */         this.ns = new NamespaceSupport();
/*      */       } else {
/*  275 */         this.ns.reset();
/*      */       } 
/*      */     }
/*      */     
/*  279 */     this.isStandalone = false;
/*  280 */     this.rootElementName = null;
/*  281 */     this.isInAttribute = false;
/*      */     
/*  283 */     this.inExternalPE = false;
/*  284 */     this.doLexicalPE = false;
/*  285 */     this.donePrologue = false;
/*  286 */     this.doneEpilogue = false;
/*  287 */     this.doneContent = false;
/*      */     
/*  289 */     this.attr = null;
/*  290 */     this.attrIndex = 0;
/*  291 */     this.startEmptyStack = true;
/*      */     
/*  293 */     this.entities.clear();
/*  294 */     this.notations.clear();
/*  295 */     this.params.clear();
/*  296 */     this.elements.clear();
/*  297 */     this.ignoreDeclarations = false;
/*      */     
/*  299 */     this.stack.clear();
/*  300 */     this.piQueue.clear();
/*      */ 
/*      */     
/*  303 */     builtin("amp", "&#38;");
/*  304 */     builtin("lt", "&#60;");
/*  305 */     builtin("gt", ">");
/*  306 */     builtin("quot", "\"");
/*  307 */     builtin("apos", "'");
/*      */     
/*  309 */     if (this.locale == null)
/*  310 */       this.locale = Locale.getDefault(); 
/*  311 */     if (this.resolver == null) {
/*  312 */       this.resolver = new Resolver();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void builtin(String entityName, String entityValue) {
/*  318 */     InternalEntity entity = new InternalEntity(entityName, entityValue.toCharArray());
/*  319 */     this.entities.put(entityName, entity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void afterRoot() throws ParseException {}
/*      */ 
/*      */ 
/*      */   
/*      */   void afterDocument() {}
/*      */ 
/*      */ 
/*      */   
/*      */   private void whitespace(String roleId) throws IOException, ParseException {
/*  334 */     if (!maybeWhitespace()) {
/*  335 */       fatal("P-004", new Object[] { messages.getMessage(this.locale, roleId) });
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean maybeWhitespace() throws IOException, ParseException {
/*  340 */     if (!this.inExternalPE || !this.doLexicalPE) {
/*  341 */       return this.in.maybeWhitespace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  356 */     char c = getc();
/*  357 */     boolean saw = false;
/*      */     
/*  359 */     while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
/*  360 */       saw = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  365 */       if (this.in.isEOF() && !this.in.isInternal())
/*  366 */         return saw; 
/*  367 */       c = getc();
/*      */     } 
/*  369 */     ungetc();
/*  370 */     return saw;
/*      */   }
/*      */   
/*      */   private String maybeGetName() throws IOException, ParseException {
/*  374 */     NameCacheEntry entry = maybeGetNameCacheEntry();
/*  375 */     return (entry == null) ? null : entry.name;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private NameCacheEntry maybeGetNameCacheEntry() throws IOException, ParseException {
/*  381 */     char c = getc();
/*      */     
/*  383 */     if (!XmlChars.isLetter(c) && c != ':' && c != '_') {
/*  384 */       ungetc();
/*  385 */       return null;
/*      */     } 
/*  387 */     return nameCharString(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNmtoken() throws ParseException, IOException {
/*  393 */     char c = getc();
/*  394 */     if (!XmlChars.isNameChar(c))
/*  395 */       fatal("P-006", new Object[] { new Character(c) }); 
/*  396 */     return (nameCharString(c)).name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NameCacheEntry nameCharString(char c) throws IOException, ParseException {
/*  406 */     int i = 1;
/*      */     
/*  408 */     this.nameTmp[0] = c;
/*      */     
/*  410 */     while ((c = this.in.getNameChar()) != '\000') {
/*      */       
/*  412 */       if (i >= this.nameTmp.length) {
/*  413 */         char[] tmp = new char[this.nameTmp.length + 10];
/*  414 */         System.arraycopy(this.nameTmp, 0, tmp, 0, this.nameTmp.length);
/*  415 */         this.nameTmp = tmp;
/*      */       } 
/*  417 */       this.nameTmp[i++] = c;
/*      */     } 
/*  419 */     return this.nameCache.lookupEntry(this.nameTmp, i);
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
/*      */   private void parseLiteral(boolean isEntityValue) throws IOException, ParseException {
/*  443 */     boolean savedLexicalPE = this.doLexicalPE;
/*  444 */     this.doLexicalPE = isEntityValue;
/*      */     
/*  446 */     char quote = getc();
/*      */     
/*  448 */     InputEntity source = this.in;
/*      */     
/*  450 */     if (quote != '\'' && quote != '"') {
/*  451 */       fatal("P-007");
/*      */     }
/*      */ 
/*      */     
/*  455 */     this.isInAttribute = !isEntityValue;
/*      */ 
/*      */     
/*  458 */     this.strTmp = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  463 */       if (this.in != source && this.in.isEOF()) {
/*      */ 
/*      */         
/*  466 */         this.in = this.in.pop(); continue;
/*      */       } 
/*      */       char c;
/*  469 */       if ((c = getc()) == quote && this.in == source) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  476 */       if (c == '&') {
/*  477 */         String entityName = maybeGetName();
/*      */         
/*  479 */         if (entityName != null) {
/*  480 */           nextChar(';', "F-020", entityName);
/*      */ 
/*      */ 
/*      */           
/*  484 */           if (isEntityValue) {
/*  485 */             this.strTmp.append('&');
/*  486 */             this.strTmp.append(entityName);
/*  487 */             this.strTmp.append(';');
/*      */             continue;
/*      */           } 
/*  490 */           expandEntityInLiteral(entityName, this.entities, isEntityValue);
/*      */           continue;
/*      */         } 
/*  493 */         if ((c = getc()) == '#') {
/*  494 */           int tmp = parseCharNumber();
/*      */           
/*  496 */           if (tmp > 65535) {
/*  497 */             tmp = surrogatesToCharTmp(tmp);
/*  498 */             this.strTmp.append(this.charTmp[0]);
/*  499 */             if (tmp == 2)
/*  500 */               this.strTmp.append(this.charTmp[1]);  continue;
/*      */           } 
/*  502 */           this.strTmp.append((char)tmp); continue;
/*      */         } 
/*  504 */         fatal("P-009");
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  510 */       if (c == '%' && isEntityValue) {
/*  511 */         String entityName = maybeGetName();
/*      */         
/*  513 */         if (entityName != null) {
/*  514 */           nextChar(';', "F-021", entityName);
/*  515 */           if (this.inExternalPE) {
/*  516 */             expandEntityInLiteral(entityName, this.params, isEntityValue);
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  521 */           fatal("P-010", new Object[] { entityName });
/*      */           continue;
/*      */         } 
/*  524 */         fatal("P-011");
/*      */       } 
/*      */ 
/*      */       
/*  528 */       if (!isEntityValue) {
/*      */         
/*  530 */         if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
/*  531 */           this.strTmp.append(' ');
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  536 */         if (c == '<') {
/*  537 */           fatal("P-012");
/*      */         }
/*      */       } 
/*  540 */       this.strTmp.append(c);
/*      */     } 
/*      */     
/*  543 */     this.isInAttribute = false;
/*  544 */     this.doLexicalPE = savedLexicalPE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void expandEntityInLiteral(String name, SimpleHashtable table, boolean isEntityValue) throws ParseException, IOException {
/*  553 */     Object entity = table.get(name);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  560 */     if (entity instanceof InternalEntity) {
/*  561 */       InternalEntity value = (InternalEntity)entity;
/*  562 */       pushReader(value.buf, name, !value.isPE);
/*      */     }
/*  564 */     else if (entity instanceof ExternalEntity) {
/*  565 */       if (!isEntityValue) {
/*  566 */         fatal("P-013", new Object[] { name });
/*      */       }
/*  568 */       pushReader((ExternalEntity)entity);
/*      */     }
/*  570 */     else if (entity == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  576 */       fatal((table == this.params) ? "V-022" : "P-014", new Object[] { name });
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
/*      */   private String getQuotedString(String type, String extra) throws IOException, ParseException {
/*  591 */     char quote = this.in.getc();
/*      */     
/*  593 */     if (quote != '\'' && quote != '"') {
/*  594 */       fatal("P-015", new Object[] { messages.getMessage(this.locale, type, new Object[] { extra }) });
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
/*  605 */     this.strTmp = new StringBuffer(); char c;
/*  606 */     while ((c = this.in.getc()) != quote)
/*  607 */       this.strTmp.append(c); 
/*  608 */     return this.strTmp.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String parsePublicId() throws IOException, ParseException {
/*  614 */     String retval = getQuotedString("F-033", null);
/*  615 */     for (int i = 0; i < retval.length(); i++) {
/*  616 */       char c = retval.charAt(i);
/*  617 */       if (" \r\n-'()+,./:=?;!*#@$_%0123456789".indexOf(c) == -1 && (c < 'A' || c > 'Z') && (c < 'a' || c > 'z'))
/*      */       {
/*      */         
/*  620 */         fatal("P-016", new Object[] { new Character(c) }); } 
/*      */     } 
/*  622 */     this.strTmp = new StringBuffer();
/*  623 */     this.strTmp.append(retval);
/*  624 */     return normalize(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeComment(boolean skipStart) throws IOException, ParseException {
/*  635 */     if (!this.in.peek(skipStart ? "!--" : "<!--", null)) {
/*  636 */       return false;
/*      */     }
/*  638 */     boolean savedLexicalPE = this.doLexicalPE;
/*      */ 
/*      */     
/*  641 */     this.doLexicalPE = false;
/*  642 */     boolean saveCommentText = false;
/*  643 */     if (saveCommentText) {
/*  644 */       this.strTmp = new StringBuffer();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*      */       try {
/*  651 */         int c = getc();
/*  652 */         if (c == 45) {
/*  653 */           c = getc();
/*  654 */           if (c != 45) {
/*  655 */             if (saveCommentText)
/*  656 */               this.strTmp.append('-'); 
/*  657 */             ungetc();
/*      */             continue;
/*      */           } 
/*  660 */           nextChar('>', "F-022", null);
/*      */           break;
/*      */         } 
/*  663 */         if (saveCommentText) {
/*  664 */           this.strTmp.append((char)c);
/*      */         }
/*  666 */       } catch (EndOfInputException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  673 */         if (this.inExternalPE || (!this.donePrologue && this.in.isInternal())) {
/*  674 */           this.in = this.in.pop();
/*      */           continue;
/*      */         } 
/*  677 */         fatal("P-017");
/*      */       } 
/*      */     } 
/*  680 */     this.doLexicalPE = savedLexicalPE;
/*  681 */     return true;
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
/*      */   private void maybeXmlDecl() throws IOException, ParseException {
/*  694 */     if (!peek("<?xml")) {
/*      */       return;
/*      */     }
/*  697 */     readVersion(true, "1.0");
/*  698 */     readEncoding(false);
/*  699 */     readStandalone();
/*  700 */     maybeWhitespace();
/*  701 */     if (!peek("?>")) {
/*  702 */       char c = getc();
/*  703 */       fatal("P-023", new Object[] { Integer.toHexString(c), new Character(c) });
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
/*      */   private String maybeReadAttribute(String name, boolean must) throws IOException, ParseException {
/*  716 */     if (!maybeWhitespace()) {
/*  717 */       if (!must)
/*  718 */         return null; 
/*  719 */       fatal("P-024", new Object[] { name });
/*      */     } 
/*      */ 
/*      */     
/*  723 */     if (!peek(name)) {
/*  724 */       if (must) {
/*  725 */         fatal("P-024", new Object[] { name });
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  730 */         ungetc();
/*  731 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*  735 */     maybeWhitespace();
/*  736 */     nextChar('=', "F-023", null);
/*  737 */     maybeWhitespace();
/*      */     
/*  739 */     return getQuotedString("F-035", name);
/*      */   }
/*      */ 
/*      */   
/*      */   private void readVersion(boolean must, String versionNum) throws IOException, ParseException {
/*  744 */     String value = maybeReadAttribute("version", must);
/*      */ 
/*      */ 
/*      */     
/*  748 */     if (must && value == null)
/*  749 */       fatal("P-025", new Object[] { versionNum }); 
/*  750 */     if (value != null) {
/*  751 */       int length = value.length();
/*  752 */       for (int i = 0; i < length; i++) {
/*  753 */         char c = value.charAt(i);
/*  754 */         if ((c < '0' || c > '9') && c != '_' && c != '.' && (c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != ':' && c != '-')
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  761 */           fatal("P-026", new Object[] { value }); } 
/*      */       } 
/*      */     } 
/*  764 */     if (value != null && !value.equals(versionNum)) {
/*  765 */       error("P-027", new Object[] { versionNum, value });
/*      */     }
/*      */   }
/*      */   
/*      */   private void maybeMisc(boolean eofOK) throws IOException, ParseException {
/*  770 */     while (!eofOK || !this.in.isEOF()) {
/*      */       
/*  772 */       if (maybeComment(false) || maybePI(false) || maybeWhitespace());
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
/*      */   private String getMarkupDeclname(String roleId, boolean qname) throws IOException, ParseException {
/*  785 */     whitespace(roleId);
/*  786 */     String name = maybeGetName();
/*  787 */     if (name == null)
/*  788 */       fatal("P-005", new Object[] { messages.getMessage(this.locale, roleId) }); 
/*  789 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeDoctypeDecl() throws IOException, ParseException {
/*  797 */     if (!peek("<!DOCTYPE")) {
/*  798 */       return false;
/*      */     }
/*  800 */     if (this.rejectDTDs) {
/*  801 */       fatal("P-085");
/*      */     }
/*      */     
/*  804 */     ExternalEntity externalSubset = null;
/*      */     
/*  806 */     this.rootElementName = getMarkupDeclname("F-014", true);
/*  807 */     if (maybeWhitespace() && (externalSubset = maybeExternalID()) != null)
/*      */     {
/*  809 */       maybeWhitespace();
/*      */     }
/*  811 */     if (this.in.peekc('[')) {
/*  812 */       this.in.startRemembering();
/*      */       
/*      */       while (true) {
/*  815 */         if (this.in.isEOF() && !this.in.isDocument()) {
/*  816 */           this.in = this.in.pop();
/*      */           continue;
/*      */         } 
/*  819 */         if (maybeMarkupDecl() || maybePEReference() || maybeWhitespace()) {
/*      */           continue;
/*      */         }
/*      */         
/*  823 */         if (peek("<![")) {
/*  824 */           fatal("P-028"); continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*  828 */       nextChar(']', "F-024", null);
/*  829 */       maybeWhitespace();
/*      */     } 
/*      */     
/*  832 */     nextChar('>', "F-025", null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  839 */     if (externalSubset != null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  846 */     this.params.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  851 */     List<String> v = new ArrayList();
/*      */     
/*  853 */     for (Iterator<String> e = this.notations.keySet().iterator(); e.hasNext(); ) {
/*  854 */       String name = e.next();
/*  855 */       Object value = this.notations.get(name);
/*      */       
/*  857 */       if (value == Boolean.TRUE) {
/*  858 */         v.add(name); continue;
/*  859 */       }  if (value instanceof String) {
/*  860 */         v.add(name);
/*      */       }
/*      */     } 
/*  863 */     while (!v.isEmpty()) {
/*  864 */       Object name = v.get(0);
/*  865 */       v.remove(name);
/*  866 */       this.notations.remove(name);
/*      */     } 
/*      */     
/*  869 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeMarkupDecl() throws IOException, ParseException {
/*  875 */     return (maybeElementDecl() || maybeAttlistDecl() || maybeEntityDecl() || maybeNotationDecl() || maybePI(false) || maybeComment(false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readStandalone() throws IOException, ParseException {
/*  884 */     String value = maybeReadAttribute("standalone", false);
/*      */ 
/*      */     
/*  887 */     if (value == null || "no".equals(value))
/*      */       return; 
/*  889 */     if ("yes".equals(value)) {
/*  890 */       this.isStandalone = true;
/*      */       return;
/*      */     } 
/*  893 */     fatal("P-029", new Object[] { value });
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
/*      */   private boolean isXmlLang(String value) {
/*      */     int nextSuffix;
/*  912 */     if (value.length() < 2)
/*  913 */       return false; 
/*  914 */     char c = value.charAt(1);
/*  915 */     if (c == '-') {
/*  916 */       c = value.charAt(0);
/*  917 */       if (c != 'i' && c != 'I' && c != 'x' && c != 'X')
/*  918 */         return false; 
/*  919 */       nextSuffix = 1;
/*  920 */     } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
/*      */       
/*  922 */       c = value.charAt(0);
/*  923 */       if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
/*  924 */         return false; 
/*  925 */       nextSuffix = 2;
/*      */     } else {
/*  927 */       return false;
/*      */     } 
/*      */     
/*  930 */     while (nextSuffix < value.length()) {
/*  931 */       c = value.charAt(nextSuffix);
/*  932 */       if (c != '-')
/*      */         break; 
/*  934 */       while (++nextSuffix < value.length()) {
/*  935 */         c = value.charAt(nextSuffix);
/*  936 */         if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
/*      */           break; 
/*      */       } 
/*      */     } 
/*  940 */     return (value.length() == nextSuffix && c != '-');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean defaultAttributes(AttributesExImpl attributes, ElementDecl element) throws ParseException {
/*  947 */     boolean didDefault = false;
/*      */ 
/*      */ 
/*      */     
/*  951 */     for (Iterator<String> e = element.attributes.keys(); e.hasNext(); ) {
/*  952 */       String key = e.next();
/*  953 */       String value = attributes.getValue(key);
/*      */ 
/*      */       
/*  956 */       if (value != null) {
/*      */         continue;
/*      */       }
/*  959 */       AttributeDecl info = (AttributeDecl)element.attributes.get(key);
/*  960 */       if (info.defaultValue != null) {
/*  961 */         attributes.addAttribute("", key, key, info.type, info.defaultValue, info.defaultValue, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  969 */         didDefault = true;
/*      */       } 
/*      */     } 
/*  972 */     return didDefault;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeElementDecl() throws IOException, ParseException {
/*  978 */     InputEntity start = peekDeclaration("!ELEMENT");
/*      */     
/*  980 */     if (start == null) {
/*  981 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  985 */     String name = getMarkupDeclname("F-015", true);
/*  986 */     ElementDecl element = (ElementDecl)this.elements.get(name);
/*  987 */     boolean declEffective = false;
/*      */     
/*  989 */     if (element != null) {
/*  990 */       if (element.contentType != null)
/*      */       {
/*  992 */         element = new ElementDecl(name);
/*      */       }
/*      */     } else {
/*  995 */       element = new ElementDecl(name);
/*  996 */       if (!this.ignoreDeclarations) {
/*  997 */         this.elements.put(element.name, element);
/*  998 */         declEffective = true;
/*      */       } 
/*      */     } 
/* 1001 */     element.isFromInternalSubset = !this.inExternalPE;
/*      */     
/* 1003 */     whitespace("F-000");
/* 1004 */     if (peek("EMPTY")) {
/* 1005 */       element.contentType = "EMPTY";
/* 1006 */       element.ignoreWhitespace = true;
/* 1007 */     } else if (peek("ANY")) {
/* 1008 */       element.contentType = "ANY";
/* 1009 */       element.ignoreWhitespace = false;
/*      */     } else {
/* 1011 */       element.contentType = getMixedOrChildren(element);
/*      */     } 
/* 1013 */     maybeWhitespace();
/* 1014 */     char c = getc();
/* 1015 */     if (c != '>') {
/* 1016 */       fatal("P-036", new Object[] { name, new Character(c) });
/*      */     }
/* 1018 */     return true;
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
/*      */   private String getMixedOrChildren(ElementDecl element) throws IOException, ParseException {
/* 1031 */     this.strTmp = new StringBuffer();
/*      */     
/* 1033 */     nextChar('(', "F-028", element.name);
/* 1034 */     InputEntity start = this.in;
/* 1035 */     maybeWhitespace();
/* 1036 */     this.strTmp.append('(');
/*      */     
/* 1038 */     if (peek("#PCDATA")) {
/* 1039 */       this.strTmp.append("#PCDATA");
/* 1040 */       getMixed(element.name, start);
/* 1041 */       element.ignoreWhitespace = false;
/*      */     } else {
/* 1043 */       element.model = getcps(element.name, start);
/* 1044 */       element.ignoreWhitespace = true;
/*      */     } 
/* 1046 */     return this.strTmp.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ContentModel getcps(String element, InputEntity start) throws IOException, ParseException {
/* 1056 */     boolean decided = false;
/* 1057 */     char type = Character.MIN_VALUE;
/*      */ 
/*      */     
/* 1060 */     ContentModel temp = null, current = temp, retval = current;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1065 */       String tag = maybeGetName();
/* 1066 */       if (tag != null) {
/* 1067 */         this.strTmp.append(tag);
/* 1068 */         temp = getFrequency(null);
/* 1069 */       } else if (peek("(")) {
/* 1070 */         InputEntity next = this.in;
/* 1071 */         this.strTmp.append('(');
/* 1072 */         maybeWhitespace();
/* 1073 */         temp = getFrequency(getcps(element, next));
/*      */       } else {
/* 1075 */         fatal((type == '\000') ? "P-039" : ((type == ',') ? "P-037" : "P-038"), new Object[] { new Character(getc()) });
/*      */       } 
/*      */ 
/*      */       
/* 1079 */       maybeWhitespace();
/* 1080 */       if (decided)
/* 1081 */       { char c = getc();
/*      */         
/* 1083 */         if (current != null) {
/* 1084 */           current.next = null;
/* 1085 */           current = current.next;
/*      */         } 
/* 1087 */         if (c == type)
/* 1088 */         { this.strTmp.append(type);
/* 1089 */           maybeWhitespace(); }
/*      */         
/* 1091 */         else if (c == ')')
/* 1092 */         { ungetc(); }
/*      */         else
/*      */         
/* 1095 */         { fatal((type == '\000') ? "P-041" : "P-040", new Object[] { new Character(c), new Character(type) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1111 */           maybeWhitespace(); }  } else { type = getc(); if (type == '|' || type == ',') { decided = true; retval = current = null; }
/* 1112 */         else { retval = current = temp; ungetc(); if (peek(")"))
/* 1113 */           { this.strTmp.append(')');
/* 1114 */             return getFrequency(retval); }  }  this.strTmp.append(type); maybeWhitespace(); }  if (peek(")")) { this.strTmp.append(')'); return getFrequency(retval); }
/*      */     
/*      */     } 
/*      */   }
/*      */   private ContentModel getFrequency(ContentModel original) throws IOException, ParseException {
/* 1119 */     char c = getc();
/*      */     
/* 1121 */     if (c == '?' || c == '+' || c == '*') {
/* 1122 */       this.strTmp.append(c);
/* 1123 */       if (original == null)
/* 1124 */         return null; 
/* 1125 */       if (original.type == '\000') {
/* 1126 */         original.type = c;
/* 1127 */         return original;
/*      */       } 
/* 1129 */       return null;
/*      */     } 
/* 1131 */     ungetc();
/* 1132 */     return original;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getMixed(String element, InputEntity start) throws IOException, ParseException {
/* 1142 */     maybeWhitespace();
/* 1143 */     if (peek(")*") || peek(")")) {
/* 1144 */       this.strTmp.append(')');
/*      */       
/*      */       return;
/*      */     } 
/* 1148 */     while (peek("|")) {
/*      */ 
/*      */       
/* 1151 */       this.strTmp.append('|');
/* 1152 */       maybeWhitespace();
/*      */       
/* 1154 */       String name = maybeGetName();
/* 1155 */       if (name == null) {
/* 1156 */         fatal("P-042", new Object[] { element, Integer.toHexString(getc()) });
/*      */       }
/*      */       
/* 1159 */       this.strTmp.append(name);
/* 1160 */       maybeWhitespace();
/*      */     } 
/*      */     
/* 1163 */     if (!peek(")*"))
/* 1164 */       fatal("P-043", new Object[] { element, new Character(getc()) }); 
/* 1165 */     this.strTmp.append(')');
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
/*      */   private boolean maybeAttlistDecl() throws IOException, ParseException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: ldc '!ATTLIST'
/*      */     //   3: invokespecial peekDeclaration : (Ljava/lang/String;)Lcom/sun/xml/rpc/sp/InputEntity;
/*      */     //   6: astore_1
/*      */     //   7: aload_1
/*      */     //   8: ifnonnull -> 13
/*      */     //   11: iconst_0
/*      */     //   12: ireturn
/*      */     //   13: aload_0
/*      */     //   14: ldc 'F-016'
/*      */     //   16: iconst_1
/*      */     //   17: invokespecial getMarkupDeclname : (Ljava/lang/String;Z)Ljava/lang/String;
/*      */     //   20: astore_2
/*      */     //   21: aload_0
/*      */     //   22: getfield elements : Lcom/sun/xml/rpc/sp/SimpleHashtable;
/*      */     //   25: aload_2
/*      */     //   26: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   29: checkcast com/sun/xml/rpc/sp/ElementDecl
/*      */     //   32: astore_3
/*      */     //   33: aload_3
/*      */     //   34: ifnonnull -> 63
/*      */     //   37: new com/sun/xml/rpc/sp/ElementDecl
/*      */     //   40: dup
/*      */     //   41: aload_2
/*      */     //   42: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   45: astore_3
/*      */     //   46: aload_0
/*      */     //   47: getfield ignoreDeclarations : Z
/*      */     //   50: ifne -> 63
/*      */     //   53: aload_0
/*      */     //   54: getfield elements : Lcom/sun/xml/rpc/sp/SimpleHashtable;
/*      */     //   57: aload_2
/*      */     //   58: aload_3
/*      */     //   59: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   62: pop
/*      */     //   63: aload_0
/*      */     //   64: invokespecial maybeWhitespace : ()Z
/*      */     //   67: pop
/*      */     //   68: aload_0
/*      */     //   69: ldc '>'
/*      */     //   71: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   74: ifne -> 869
/*      */     //   77: aload_0
/*      */     //   78: invokespecial maybeGetName : ()Ljava/lang/String;
/*      */     //   81: astore_2
/*      */     //   82: aload_2
/*      */     //   83: ifnonnull -> 110
/*      */     //   86: aload_0
/*      */     //   87: ldc 'P-044'
/*      */     //   89: iconst_1
/*      */     //   90: anewarray java/lang/Object
/*      */     //   93: dup
/*      */     //   94: iconst_0
/*      */     //   95: new java/lang/Character
/*      */     //   98: dup
/*      */     //   99: aload_0
/*      */     //   100: invokespecial getc : ()C
/*      */     //   103: invokespecial <init> : (C)V
/*      */     //   106: aastore
/*      */     //   107: invokespecial fatal : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   110: aload_0
/*      */     //   111: ldc 'F-001'
/*      */     //   113: invokespecial whitespace : (Ljava/lang/String;)V
/*      */     //   116: new com/sun/xml/rpc/sp/AttributeDecl
/*      */     //   119: dup
/*      */     //   120: aload_2
/*      */     //   121: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   124: astore #4
/*      */     //   126: aload #4
/*      */     //   128: aload_0
/*      */     //   129: getfield inExternalPE : Z
/*      */     //   132: ifne -> 139
/*      */     //   135: iconst_1
/*      */     //   136: goto -> 140
/*      */     //   139: iconst_0
/*      */     //   140: putfield isFromInternalSubset : Z
/*      */     //   143: aload_0
/*      */     //   144: ldc 'CDATA'
/*      */     //   146: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   149: ifeq -> 162
/*      */     //   152: aload #4
/*      */     //   154: ldc 'CDATA'
/*      */     //   156: putfield type : Ljava/lang/String;
/*      */     //   159: goto -> 629
/*      */     //   162: aload_0
/*      */     //   163: ldc 'IDREFS'
/*      */     //   165: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   168: ifeq -> 181
/*      */     //   171: aload #4
/*      */     //   173: ldc 'IDREFS'
/*      */     //   175: putfield type : Ljava/lang/String;
/*      */     //   178: goto -> 629
/*      */     //   181: aload_0
/*      */     //   182: ldc 'IDREF'
/*      */     //   184: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   187: ifeq -> 200
/*      */     //   190: aload #4
/*      */     //   192: ldc 'IDREF'
/*      */     //   194: putfield type : Ljava/lang/String;
/*      */     //   197: goto -> 629
/*      */     //   200: aload_0
/*      */     //   201: ldc 'ID'
/*      */     //   203: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   206: ifeq -> 231
/*      */     //   209: aload #4
/*      */     //   211: ldc 'ID'
/*      */     //   213: putfield type : Ljava/lang/String;
/*      */     //   216: aload_3
/*      */     //   217: getfield id : Ljava/lang/String;
/*      */     //   220: ifnonnull -> 629
/*      */     //   223: aload_3
/*      */     //   224: aload_2
/*      */     //   225: putfield id : Ljava/lang/String;
/*      */     //   228: goto -> 629
/*      */     //   231: aload_0
/*      */     //   232: ldc 'ENTITY'
/*      */     //   234: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   237: ifeq -> 250
/*      */     //   240: aload #4
/*      */     //   242: ldc 'ENTITY'
/*      */     //   244: putfield type : Ljava/lang/String;
/*      */     //   247: goto -> 629
/*      */     //   250: aload_0
/*      */     //   251: ldc 'ENTITIES'
/*      */     //   253: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   256: ifeq -> 269
/*      */     //   259: aload #4
/*      */     //   261: ldc 'ENTITIES'
/*      */     //   263: putfield type : Ljava/lang/String;
/*      */     //   266: goto -> 629
/*      */     //   269: aload_0
/*      */     //   270: ldc 'NMTOKENS'
/*      */     //   272: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   275: ifeq -> 288
/*      */     //   278: aload #4
/*      */     //   280: ldc 'NMTOKENS'
/*      */     //   282: putfield type : Ljava/lang/String;
/*      */     //   285: goto -> 629
/*      */     //   288: aload_0
/*      */     //   289: ldc 'NMTOKEN'
/*      */     //   291: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   294: ifeq -> 307
/*      */     //   297: aload #4
/*      */     //   299: ldc 'NMTOKEN'
/*      */     //   301: putfield type : Ljava/lang/String;
/*      */     //   304: goto -> 629
/*      */     //   307: aload_0
/*      */     //   308: ldc_w 'NOTATION'
/*      */     //   311: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   314: ifeq -> 468
/*      */     //   317: aload #4
/*      */     //   319: ldc_w 'NOTATION'
/*      */     //   322: putfield type : Ljava/lang/String;
/*      */     //   325: aload_0
/*      */     //   326: ldc_w 'F-002'
/*      */     //   329: invokespecial whitespace : (Ljava/lang/String;)V
/*      */     //   332: aload_0
/*      */     //   333: bipush #40
/*      */     //   335: ldc_w 'F-029'
/*      */     //   338: aconst_null
/*      */     //   339: invokespecial nextChar : (CLjava/lang/String;Ljava/lang/String;)V
/*      */     //   342: aload_0
/*      */     //   343: invokespecial maybeWhitespace : ()Z
/*      */     //   346: pop
/*      */     //   347: new java/util/ArrayList
/*      */     //   350: dup
/*      */     //   351: invokespecial <init> : ()V
/*      */     //   354: astore #5
/*      */     //   356: aload_0
/*      */     //   357: invokespecial maybeGetName : ()Ljava/lang/String;
/*      */     //   360: dup
/*      */     //   361: astore_2
/*      */     //   362: ifnonnull -> 372
/*      */     //   365: aload_0
/*      */     //   366: ldc_w 'P-068'
/*      */     //   369: invokespecial fatal : (Ljava/lang/String;)V
/*      */     //   372: aload #5
/*      */     //   374: aload_2
/*      */     //   375: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   380: pop
/*      */     //   381: aload_0
/*      */     //   382: invokespecial maybeWhitespace : ()Z
/*      */     //   385: pop
/*      */     //   386: aload_0
/*      */     //   387: ldc '|'
/*      */     //   389: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   392: ifeq -> 400
/*      */     //   395: aload_0
/*      */     //   396: invokespecial maybeWhitespace : ()Z
/*      */     //   399: pop
/*      */     //   400: aload_0
/*      */     //   401: ldc ')'
/*      */     //   403: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   406: ifeq -> 356
/*      */     //   409: aload #4
/*      */     //   411: aload #5
/*      */     //   413: invokeinterface size : ()I
/*      */     //   418: anewarray java/lang/String
/*      */     //   421: putfield values : [Ljava/lang/String;
/*      */     //   424: iconst_0
/*      */     //   425: istore #6
/*      */     //   427: iload #6
/*      */     //   429: aload #5
/*      */     //   431: invokeinterface size : ()I
/*      */     //   436: if_icmpge -> 465
/*      */     //   439: aload #4
/*      */     //   441: getfield values : [Ljava/lang/String;
/*      */     //   444: iload #6
/*      */     //   446: aload #5
/*      */     //   448: iload #6
/*      */     //   450: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   455: checkcast java/lang/String
/*      */     //   458: aastore
/*      */     //   459: iinc #6, 1
/*      */     //   462: goto -> 427
/*      */     //   465: goto -> 629
/*      */     //   468: aload_0
/*      */     //   469: ldc '('
/*      */     //   471: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   474: ifeq -> 600
/*      */     //   477: aload #4
/*      */     //   479: ldc_w 'ENUMERATION'
/*      */     //   482: putfield type : Ljava/lang/String;
/*      */     //   485: aload_0
/*      */     //   486: invokespecial maybeWhitespace : ()Z
/*      */     //   489: pop
/*      */     //   490: new java/util/ArrayList
/*      */     //   493: dup
/*      */     //   494: invokespecial <init> : ()V
/*      */     //   497: astore #5
/*      */     //   499: aload_0
/*      */     //   500: invokespecial getNmtoken : ()Ljava/lang/String;
/*      */     //   503: astore_2
/*      */     //   504: aload #5
/*      */     //   506: aload_2
/*      */     //   507: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   512: pop
/*      */     //   513: aload_0
/*      */     //   514: invokespecial maybeWhitespace : ()Z
/*      */     //   517: pop
/*      */     //   518: aload_0
/*      */     //   519: ldc '|'
/*      */     //   521: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   524: ifeq -> 532
/*      */     //   527: aload_0
/*      */     //   528: invokespecial maybeWhitespace : ()Z
/*      */     //   531: pop
/*      */     //   532: aload_0
/*      */     //   533: ldc ')'
/*      */     //   535: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   538: ifeq -> 499
/*      */     //   541: aload #4
/*      */     //   543: aload #5
/*      */     //   545: invokeinterface size : ()I
/*      */     //   550: anewarray java/lang/String
/*      */     //   553: putfield values : [Ljava/lang/String;
/*      */     //   556: iconst_0
/*      */     //   557: istore #6
/*      */     //   559: iload #6
/*      */     //   561: aload #5
/*      */     //   563: invokeinterface size : ()I
/*      */     //   568: if_icmpge -> 597
/*      */     //   571: aload #4
/*      */     //   573: getfield values : [Ljava/lang/String;
/*      */     //   576: iload #6
/*      */     //   578: aload #5
/*      */     //   580: iload #6
/*      */     //   582: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   587: checkcast java/lang/String
/*      */     //   590: aastore
/*      */     //   591: iinc #6, 1
/*      */     //   594: goto -> 559
/*      */     //   597: goto -> 629
/*      */     //   600: aload_0
/*      */     //   601: ldc_w 'P-045'
/*      */     //   604: iconst_2
/*      */     //   605: anewarray java/lang/Object
/*      */     //   608: dup
/*      */     //   609: iconst_0
/*      */     //   610: aload_2
/*      */     //   611: aastore
/*      */     //   612: dup
/*      */     //   613: iconst_1
/*      */     //   614: new java/lang/Character
/*      */     //   617: dup
/*      */     //   618: aload_0
/*      */     //   619: invokespecial getc : ()C
/*      */     //   622: invokespecial <init> : (C)V
/*      */     //   625: aastore
/*      */     //   626: invokespecial fatal : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   629: aload_0
/*      */     //   630: ldc_w 'F-003'
/*      */     //   633: invokespecial whitespace : (Ljava/lang/String;)V
/*      */     //   636: aload_0
/*      */     //   637: ldc_w '#REQUIRED'
/*      */     //   640: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   643: ifeq -> 655
/*      */     //   646: aload #4
/*      */     //   648: iconst_1
/*      */     //   649: putfield isRequired : Z
/*      */     //   652: goto -> 771
/*      */     //   655: aload_0
/*      */     //   656: ldc_w '#FIXED'
/*      */     //   659: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   662: ifeq -> 721
/*      */     //   665: aload #4
/*      */     //   667: iconst_1
/*      */     //   668: putfield isFixed : Z
/*      */     //   671: aload_0
/*      */     //   672: ldc_w 'F-004'
/*      */     //   675: invokespecial whitespace : (Ljava/lang/String;)V
/*      */     //   678: aload_0
/*      */     //   679: iconst_0
/*      */     //   680: invokespecial parseLiteral : (Z)V
/*      */     //   683: aload #4
/*      */     //   685: getfield type : Ljava/lang/String;
/*      */     //   688: ldc 'CDATA'
/*      */     //   690: if_acmpeq -> 706
/*      */     //   693: aload #4
/*      */     //   695: aload_0
/*      */     //   696: iconst_0
/*      */     //   697: invokespecial normalize : (Z)Ljava/lang/String;
/*      */     //   700: putfield defaultValue : Ljava/lang/String;
/*      */     //   703: goto -> 771
/*      */     //   706: aload #4
/*      */     //   708: aload_0
/*      */     //   709: getfield strTmp : Ljava/lang/StringBuffer;
/*      */     //   712: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   715: putfield defaultValue : Ljava/lang/String;
/*      */     //   718: goto -> 771
/*      */     //   721: aload_0
/*      */     //   722: ldc_w '#IMPLIED'
/*      */     //   725: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   728: ifne -> 771
/*      */     //   731: aload_0
/*      */     //   732: iconst_0
/*      */     //   733: invokespecial parseLiteral : (Z)V
/*      */     //   736: aload #4
/*      */     //   738: getfield type : Ljava/lang/String;
/*      */     //   741: ldc 'CDATA'
/*      */     //   743: if_acmpeq -> 759
/*      */     //   746: aload #4
/*      */     //   748: aload_0
/*      */     //   749: iconst_0
/*      */     //   750: invokespecial normalize : (Z)Ljava/lang/String;
/*      */     //   753: putfield defaultValue : Ljava/lang/String;
/*      */     //   756: goto -> 771
/*      */     //   759: aload #4
/*      */     //   761: aload_0
/*      */     //   762: getfield strTmp : Ljava/lang/StringBuffer;
/*      */     //   765: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   768: putfield defaultValue : Ljava/lang/String;
/*      */     //   771: ldc_w 'xml:lang'
/*      */     //   774: aload #4
/*      */     //   776: getfield name : Ljava/lang/String;
/*      */     //   779: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   782: ifeq -> 824
/*      */     //   785: aload #4
/*      */     //   787: getfield defaultValue : Ljava/lang/String;
/*      */     //   790: ifnull -> 824
/*      */     //   793: aload_0
/*      */     //   794: aload #4
/*      */     //   796: getfield defaultValue : Ljava/lang/String;
/*      */     //   799: invokespecial isXmlLang : (Ljava/lang/String;)Z
/*      */     //   802: ifne -> 824
/*      */     //   805: aload_0
/*      */     //   806: ldc_w 'P-033'
/*      */     //   809: iconst_1
/*      */     //   810: anewarray java/lang/Object
/*      */     //   813: dup
/*      */     //   814: iconst_0
/*      */     //   815: aload #4
/*      */     //   817: getfield defaultValue : Ljava/lang/String;
/*      */     //   820: aastore
/*      */     //   821: invokevirtual error : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   824: aload_0
/*      */     //   825: getfield ignoreDeclarations : Z
/*      */     //   828: ifne -> 861
/*      */     //   831: aload_3
/*      */     //   832: getfield attributes : Lcom/sun/xml/rpc/sp/SimpleHashtable;
/*      */     //   835: aload #4
/*      */     //   837: getfield name : Ljava/lang/String;
/*      */     //   840: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   843: ifnonnull -> 861
/*      */     //   846: aload_3
/*      */     //   847: getfield attributes : Lcom/sun/xml/rpc/sp/SimpleHashtable;
/*      */     //   850: aload #4
/*      */     //   852: getfield name : Ljava/lang/String;
/*      */     //   855: aload #4
/*      */     //   857: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   860: pop
/*      */     //   861: aload_0
/*      */     //   862: invokespecial maybeWhitespace : ()Z
/*      */     //   865: pop
/*      */     //   866: goto -> 68
/*      */     //   869: iconst_1
/*      */     //   870: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1170	-> 0
/*      */     //   #1172	-> 7
/*      */     //   #1173	-> 11
/*      */     //   #1175	-> 13
/*      */     //   #1176	-> 21
/*      */     //   #1178	-> 33
/*      */     //   #1180	-> 37
/*      */     //   #1181	-> 46
/*      */     //   #1182	-> 53
/*      */     //   #1185	-> 63
/*      */     //   #1186	-> 68
/*      */     //   #1190	-> 77
/*      */     //   #1191	-> 82
/*      */     //   #1192	-> 86
/*      */     //   #1193	-> 110
/*      */     //   #1195	-> 116
/*      */     //   #1196	-> 126
/*      */     //   #1202	-> 143
/*      */     //   #1203	-> 152
/*      */     //   #1210	-> 162
/*      */     //   #1211	-> 171
/*      */     //   #1212	-> 181
/*      */     //   #1213	-> 190
/*      */     //   #1214	-> 200
/*      */     //   #1215	-> 209
/*      */     //   #1216	-> 216
/*      */     //   #1217	-> 223
/*      */     //   #1218	-> 231
/*      */     //   #1219	-> 240
/*      */     //   #1220	-> 250
/*      */     //   #1221	-> 259
/*      */     //   #1222	-> 269
/*      */     //   #1223	-> 278
/*      */     //   #1224	-> 288
/*      */     //   #1225	-> 297
/*      */     //   #1230	-> 307
/*      */     //   #1231	-> 317
/*      */     //   #1232	-> 325
/*      */     //   #1233	-> 332
/*      */     //   #1234	-> 342
/*      */     //   #1236	-> 347
/*      */     //   #1238	-> 356
/*      */     //   #1239	-> 365
/*      */     //   #1241	-> 372
/*      */     //   #1242	-> 381
/*      */     //   #1243	-> 386
/*      */     //   #1244	-> 395
/*      */     //   #1245	-> 400
/*      */     //   #1246	-> 409
/*      */     //   #1247	-> 424
/*      */     //   #1248	-> 439
/*      */     //   #1247	-> 459
/*      */     //   #1251	-> 465
/*      */     //   #1252	-> 477
/*      */     //   #1253	-> 485
/*      */     //   #1255	-> 490
/*      */     //   #1257	-> 499
/*      */     //   #1258	-> 504
/*      */     //   #1259	-> 513
/*      */     //   #1260	-> 518
/*      */     //   #1261	-> 527
/*      */     //   #1262	-> 532
/*      */     //   #1263	-> 541
/*      */     //   #1264	-> 556
/*      */     //   #1265	-> 571
/*      */     //   #1264	-> 591
/*      */     //   #1266	-> 597
/*      */     //   #1267	-> 600
/*      */     //   #1271	-> 629
/*      */     //   #1272	-> 636
/*      */     //   #1273	-> 646
/*      */     //   #1274	-> 655
/*      */     //   #1275	-> 665
/*      */     //   #1276	-> 671
/*      */     //   #1277	-> 678
/*      */     //   #1278	-> 683
/*      */     //   #1279	-> 693
/*      */     //   #1281	-> 706
/*      */     //   #1282	-> 718
/*      */     //   #1283	-> 731
/*      */     //   #1284	-> 736
/*      */     //   #1285	-> 746
/*      */     //   #1287	-> 759
/*      */     //   #1290	-> 771
/*      */     //   #1293	-> 805
/*      */     //   #1295	-> 824
/*      */     //   #1297	-> 846
/*      */     //   #1299	-> 861
/*      */     //   #1300	-> 866
/*      */     //   #1301	-> 869
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   427	38	6	i	I
/*      */     //   356	109	5	v	Ljava/util/List;
/*      */     //   559	38	6	i	I
/*      */     //   499	98	5	v	Ljava/util/List;
/*      */     //   126	740	4	a	Lcom/sun/xml/rpc/sp/AttributeDecl;
/*      */     //   0	871	0	this	Lcom/sun/xml/rpc/sp/Parser2;
/*      */     //   7	864	1	start	Lcom/sun/xml/rpc/sp/InputEntity;
/*      */     //   21	850	2	name	Ljava/lang/String;
/*      */     //   33	838	3	element	Lcom/sun/xml/rpc/sp/ElementDecl;
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
/*      */   private String normalize(boolean invalidIfNeeded) throws ParseException {
/* 1311 */     String s = this.strTmp.toString();
/* 1312 */     String s2 = s.trim();
/* 1313 */     boolean didStrip = false;
/*      */     
/* 1315 */     if (s != s2) {
/* 1316 */       s = s2;
/* 1317 */       s2 = null;
/* 1318 */       didStrip = true;
/*      */     } 
/* 1320 */     this.strTmp = new StringBuffer();
/* 1321 */     for (int i = 0; i < s.length(); i++) {
/* 1322 */       char c = s.charAt(i);
/* 1323 */       if (!XmlChars.isSpace(c)) {
/* 1324 */         this.strTmp.append(c);
/*      */       } else {
/*      */         
/* 1327 */         this.strTmp.append(' ');
/* 1328 */         while (++i < s.length() && XmlChars.isSpace(s.charAt(i)))
/* 1329 */           didStrip = true; 
/* 1330 */         i--;
/*      */       } 
/* 1332 */     }  if (didStrip) {
/* 1333 */       return this.strTmp.toString();
/*      */     }
/* 1335 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeConditionalSect() throws IOException, ParseException {
/* 1341 */     if (!peek("<![")) {
/* 1342 */       return false;
/*      */     }
/*      */     
/* 1345 */     InputEntity start = this.in;
/*      */     
/* 1347 */     maybeWhitespace();
/*      */     String keyword;
/* 1349 */     if ((keyword = maybeGetName()) == null)
/* 1350 */       fatal("P-046"); 
/* 1351 */     maybeWhitespace();
/* 1352 */     nextChar('[', "F-030", null);
/*      */ 
/*      */ 
/*      */     
/* 1356 */     if ("INCLUDE".equals(keyword)) {
/*      */       while (true) {
/* 1358 */         if (this.in.isEOF() && this.in != start) {
/* 1359 */           this.in = this.in.pop(); continue;
/* 1360 */         }  if (this.in.isEOF()) {
/* 1361 */           this.in = this.in.pop();
/*      */         }
/* 1363 */         if (peek("]]>")) {
/*      */           break;
/*      */         }
/* 1366 */         this.doLexicalPE = false;
/* 1367 */         if (maybeWhitespace())
/*      */           continue; 
/* 1369 */         if (maybePEReference())
/*      */           continue; 
/* 1371 */         this.doLexicalPE = true;
/* 1372 */         if (maybeMarkupDecl() || maybeConditionalSect()) {
/*      */           continue;
/*      */         }
/* 1375 */         fatal("P-047");
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1383 */     else if ("IGNORE".equals(keyword)) {
/* 1384 */       int nestlevel = 1;
/*      */       
/* 1386 */       this.doLexicalPE = false;
/* 1387 */       while (nestlevel > 0) {
/* 1388 */         char c = getc();
/* 1389 */         if (c == '<') {
/* 1390 */           if (peek("!["))
/* 1391 */             nestlevel++;  continue;
/* 1392 */         }  if (c == ']' && 
/* 1393 */           peek("]>")) {
/* 1394 */           nestlevel--;
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 1399 */       fatal("P-048", new Object[] { keyword });
/* 1400 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseCharNumber() throws ParseException, IOException {
/* 1406 */     int retval = 0;
/*      */ 
/*      */     
/* 1409 */     if (getc() != 'x') {
/* 1410 */       ungetc();
/*      */       while (true) {
/* 1412 */         char c = getc();
/* 1413 */         if (c >= '0' && c <= '9') {
/* 1414 */           retval *= 10;
/* 1415 */           retval += c - 48;
/*      */           continue;
/*      */         } 
/* 1418 */         if (c == ';')
/* 1419 */           return retval; 
/* 1420 */         fatal("P-049");
/*      */       } 
/*      */     } 
/*      */     while (true) {
/* 1424 */       char c = getc();
/* 1425 */       if (c >= '0' && c <= '9') {
/* 1426 */         retval <<= 4;
/* 1427 */         retval += c - 48;
/*      */         continue;
/*      */       } 
/* 1430 */       if (c >= 'a' && c <= 'f') {
/* 1431 */         retval <<= 4;
/* 1432 */         retval += 10 + c - 97;
/*      */         continue;
/*      */       } 
/* 1435 */       if (c >= 'A' && c <= 'F') {
/* 1436 */         retval <<= 4;
/* 1437 */         retval += 10 + c - 65;
/*      */         continue;
/*      */       } 
/* 1440 */       if (c == ';')
/* 1441 */         return retval; 
/* 1442 */       fatal("P-050");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int surrogatesToCharTmp(int ucs4) throws ParseException {
/* 1449 */     if (ucs4 <= 65535) {
/* 1450 */       if (XmlChars.isChar(ucs4)) {
/* 1451 */         this.charTmp[0] = (char)ucs4;
/* 1452 */         return 1;
/*      */       } 
/* 1454 */     } else if (ucs4 <= 1114111) {
/*      */       
/* 1456 */       ucs4 -= 65536;
/* 1457 */       this.charTmp[0] = (char)(0xD800 | ucs4 >> 10 & 0x3FF);
/* 1458 */       this.charTmp[1] = (char)(0xDC00 | ucs4 & 0x3FF);
/* 1459 */       return 2;
/*      */     } 
/* 1461 */     fatal("P-051", new Object[] { Integer.toHexString(ucs4) });
/*      */     
/* 1463 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybePEReference() throws IOException, ParseException {
/* 1472 */     if (!this.in.peekc('%')) {
/* 1473 */       return false;
/*      */     }
/* 1475 */     String name = maybeGetName();
/*      */ 
/*      */     
/* 1478 */     if (name == null)
/* 1479 */       fatal("P-011"); 
/* 1480 */     nextChar(';', "F-021", name);
/* 1481 */     Object entity = this.params.get(name);
/*      */     
/* 1483 */     if (entity instanceof InternalEntity) {
/* 1484 */       InternalEntity value = (InternalEntity)entity;
/* 1485 */       pushReader(value.buf, name, false);
/*      */     }
/* 1487 */     else if (entity instanceof ExternalEntity) {
/* 1488 */       externalParameterEntity((ExternalEntity)entity);
/*      */     }
/* 1490 */     else if (entity == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1499 */       this.ignoreDeclarations = true;
/* 1500 */       warning("V-022", new Object[] { name });
/*      */     } 
/* 1502 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeEntityDecl() throws IOException, ParseException {
/*      */     SimpleHashtable defns;
/* 1512 */     InputEntity start = peekDeclaration("!ENTITY");
/*      */     
/* 1514 */     if (start == null) {
/* 1515 */       return false;
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
/*      */ 
/*      */ 
/*      */     
/* 1529 */     this.doLexicalPE = false;
/* 1530 */     whitespace("F-005");
/* 1531 */     if (this.in.peekc('%')) {
/* 1532 */       whitespace("F-006");
/* 1533 */       defns = this.params;
/*      */     } else {
/* 1535 */       defns = this.entities;
/*      */     } 
/* 1537 */     ungetc();
/* 1538 */     this.doLexicalPE = true;
/* 1539 */     String entityName = getMarkupDeclname("F-017", false);
/* 1540 */     whitespace("F-007");
/* 1541 */     ExternalEntity externalId = maybeExternalID();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1550 */     boolean doStore = (defns.get(entityName) == null);
/* 1551 */     if (!doStore && defns == this.entities) {
/* 1552 */       warning("P-054", new Object[] { entityName });
/*      */     }
/*      */ 
/*      */     
/* 1556 */     int i = doStore & (!this.ignoreDeclarations ? 1 : 0);
/*      */ 
/*      */     
/* 1559 */     if (externalId == null) {
/*      */ 
/*      */ 
/*      */       
/* 1563 */       this.doLexicalPE = false;
/* 1564 */       parseLiteral(true);
/* 1565 */       this.doLexicalPE = true;
/* 1566 */       if (i != 0) {
/* 1567 */         char[] value = new char[this.strTmp.length()];
/* 1568 */         if (value.length != 0)
/* 1569 */           this.strTmp.getChars(0, value.length, value, 0); 
/* 1570 */         InternalEntity entity = new InternalEntity(entityName, value);
/* 1571 */         entity.isPE = (defns == this.params);
/* 1572 */         entity.isFromInternalSubset = !this.inExternalPE;
/* 1573 */         defns.put(entityName, entity);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1579 */       if (defns == this.entities && maybeWhitespace() && peek("NDATA")) {
/* 1580 */         externalId.notation = getMarkupDeclname("F-018", false);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1585 */       externalId.name = entityName;
/* 1586 */       externalId.isPE = (defns == this.params);
/* 1587 */       externalId.isFromInternalSubset = !this.inExternalPE;
/* 1588 */       if (i != 0) {
/* 1589 */         defns.put(entityName, externalId);
/*      */       }
/*      */     } 
/* 1592 */     maybeWhitespace();
/* 1593 */     nextChar('>', "F-031", entityName);
/* 1594 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ExternalEntity maybeExternalID() throws IOException, ParseException {
/* 1601 */     String temp = null;
/*      */ 
/*      */     
/* 1604 */     if (peek("PUBLIC")) {
/* 1605 */       whitespace("F-009");
/* 1606 */       temp = parsePublicId();
/* 1607 */     } else if (!peek("SYSTEM")) {
/* 1608 */       return null;
/*      */     } 
/* 1610 */     ExternalEntity retval = new ExternalEntity(this.in);
/* 1611 */     retval.publicId = temp;
/* 1612 */     whitespace("F-008");
/* 1613 */     retval.systemId = parseSystemId();
/* 1614 */     return retval;
/*      */   }
/*      */   
/*      */   private String parseSystemId() throws IOException, ParseException {
/* 1618 */     String uri = getQuotedString("F-034", null);
/* 1619 */     int temp = uri.indexOf(':');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1628 */     if (temp == -1 || uri.indexOf('/') < temp) {
/*      */ 
/*      */       
/* 1631 */       String baseURI = this.in.getSystemId();
/* 1632 */       if (baseURI == null) {
/* 1633 */         baseURI = "NODOCTYPE:///tmp/";
/*      */       }
/* 1635 */       if (uri.length() == 0)
/* 1636 */         uri = "."; 
/* 1637 */       baseURI = baseURI.substring(0, baseURI.lastIndexOf('/') + 1);
/* 1638 */       if (uri.charAt(0) != '/') {
/* 1639 */         uri = baseURI + uri;
/*      */       }
/*      */       else {
/*      */         
/* 1643 */         throw new InternalError();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1650 */     if (uri.indexOf('#') != -1)
/* 1651 */       error("P-056", new Object[] { uri }); 
/* 1652 */     return uri;
/*      */   }
/*      */ 
/*      */   
/*      */   private void maybeTextDecl() throws IOException, ParseException {
/* 1657 */     if (peek("<?xml")) {
/* 1658 */       readVersion(false, "1.0");
/* 1659 */       readEncoding(true);
/* 1660 */       maybeWhitespace();
/* 1661 */       if (!peek("?>")) {
/* 1662 */         fatal("P-057");
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
/*      */   private void externalParameterEntity(ExternalEntity next) throws IOException, ParseException {
/* 1677 */     if (this.isStandalone && this.fastStandalone) {
/*      */       return;
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
/*      */     
/* 1690 */     this.inExternalPE = true;
/*      */ 
/*      */     
/* 1693 */     pushReader(next);
/*      */     
/* 1695 */     InputEntity pe = this.in;
/* 1696 */     maybeTextDecl();
/* 1697 */     while (!pe.isEOF()) {
/*      */       
/* 1699 */       if (this.in.isEOF()) {
/* 1700 */         this.in = this.in.pop();
/*      */         continue;
/*      */       } 
/* 1703 */       this.doLexicalPE = false;
/* 1704 */       if (maybeWhitespace())
/*      */         continue; 
/* 1706 */       if (maybePEReference())
/*      */         continue; 
/* 1708 */       this.doLexicalPE = true;
/* 1709 */       if (maybeMarkupDecl() || maybeConditionalSect());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1714 */     if (!pe.isEOF())
/* 1715 */       fatal("P-059", new Object[] { this.in.getName() }); 
/* 1716 */     this.in = this.in.pop();
/* 1717 */     this.inExternalPE = !this.in.isDocument();
/* 1718 */     this.doLexicalPE = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readEncoding(boolean must) throws IOException, ParseException {
/* 1724 */     String name = maybeReadAttribute("encoding", must);
/*      */     
/* 1726 */     if (name == null)
/*      */       return; 
/* 1728 */     for (int i = 0; i < name.length(); i++) {
/* 1729 */       char c = name.charAt(i);
/* 1730 */       if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z'))
/*      */       {
/* 1732 */         if (i == 0 || ((c < '0' || c > '9') && c != '-' && c != '_' && c != '.'))
/*      */         {
/*      */           
/* 1735 */           fatal("P-060", new Object[] { new Character(c) });
/*      */         }
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1747 */     String currentEncoding = this.in.getEncoding();
/*      */     
/* 1749 */     if (currentEncoding != null && !name.equalsIgnoreCase(currentEncoding)) {
/* 1750 */       warning("P-061", new Object[] { name, currentEncoding });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeNotationDecl() throws IOException, ParseException {
/* 1757 */     InputEntity start = peekDeclaration("!NOTATION");
/*      */     
/* 1759 */     if (start == null) {
/* 1760 */       return false;
/*      */     }
/* 1762 */     String name = getMarkupDeclname("F-019", false);
/* 1763 */     ExternalEntity entity = new ExternalEntity(this.in);
/*      */     
/* 1765 */     whitespace("F-011");
/* 1766 */     if (peek("PUBLIC")) {
/* 1767 */       whitespace("F-009");
/* 1768 */       entity.publicId = parsePublicId();
/* 1769 */       if (maybeWhitespace() && 
/* 1770 */         !peek(">")) {
/* 1771 */         entity.systemId = parseSystemId();
/*      */       }
/* 1773 */     } else if (peek("SYSTEM")) {
/* 1774 */       whitespace("F-008");
/* 1775 */       entity.systemId = parseSystemId();
/*      */     } else {
/* 1777 */       fatal("P-062");
/* 1778 */     }  maybeWhitespace();
/* 1779 */     nextChar('>', "F-032", name);
/* 1780 */     if (entity.systemId != null && entity.systemId.indexOf('#') != -1) {
/* 1781 */       error("P-056", new Object[] { entity.systemId });
/*      */     }
/* 1783 */     Object value = this.notations.get(name);
/* 1784 */     if (value != null && value instanceof ExternalEntity) {
/* 1785 */       warning("P-063", new Object[] { name });
/*      */ 
/*      */     
/*      */     }
/* 1789 */     else if (!this.ignoreDeclarations) {
/* 1790 */       this.notations.put(name, entity);
/*      */     } 
/* 1792 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char getc() throws IOException, ParseException {
/* 1802 */     if (!this.inExternalPE || !this.doLexicalPE) {
/* 1803 */       char c1 = this.in.getc();
/* 1804 */       if (c1 == '%' && this.doLexicalPE)
/* 1805 */         fatal("P-080"); 
/* 1806 */       return c1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1827 */     while (this.in.isEOF()) {
/* 1828 */       if (this.in.isInternal() || (this.doLexicalPE && !this.in.isDocument())) {
/* 1829 */         this.in = this.in.pop(); continue;
/*      */       } 
/* 1831 */       fatal("P-064", new Object[] { this.in.getName() });
/*      */     } 
/*      */     char c;
/* 1834 */     if ((c = this.in.getc()) == '%' && this.doLexicalPE) {
/*      */       
/* 1836 */       String name = maybeGetName();
/*      */ 
/*      */       
/* 1839 */       if (name == null)
/* 1840 */         fatal("P-011"); 
/* 1841 */       nextChar(';', "F-021", name);
/* 1842 */       Object entity = this.params.get(name);
/*      */ 
/*      */ 
/*      */       
/* 1846 */       pushReader(" ".toCharArray(), null, false);
/* 1847 */       if (entity instanceof InternalEntity) {
/* 1848 */         pushReader(((InternalEntity)entity).buf, name, false);
/* 1849 */       } else if (entity instanceof ExternalEntity) {
/*      */ 
/*      */         
/* 1852 */         pushReader((ExternalEntity)entity);
/* 1853 */       } else if (entity == null) {
/*      */         
/* 1855 */         fatal("V-022");
/*      */       } else {
/* 1857 */         throw new InternalError();
/* 1858 */       }  pushReader(" ".toCharArray(), null, false);
/* 1859 */       return this.in.getc();
/*      */     } 
/* 1861 */     return c;
/*      */   }
/*      */ 
/*      */   
/*      */   private void ungetc() {
/* 1866 */     this.in.ungetc();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean peek(String s) throws IOException, ParseException {
/* 1872 */     return this.in.peek(s, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InputEntity peekDeclaration(String s) throws IOException, ParseException {
/* 1881 */     if (!this.in.peekc('<'))
/* 1882 */       return null; 
/* 1883 */     InputEntity start = this.in;
/* 1884 */     if (this.in.peek(s, null))
/* 1885 */       return start; 
/* 1886 */     this.in.ungetc();
/* 1887 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void nextChar(char c, String location, String near) throws IOException, ParseException {
/* 1892 */     while (this.in.isEOF() && !this.in.isDocument())
/* 1893 */       this.in = this.in.pop(); 
/* 1894 */     if (!this.in.peekc(c)) {
/* 1895 */       fatal("P-008", new Object[] { new Character(c), messages.getMessage(this.locale, location), (near == null) ? "" : ('"' + near + '"') });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pushReader(char[] buf, String name, boolean isGeneral) throws ParseException {
/* 1905 */     InputEntity r = InputEntity.getInputEntity(this.locale);
/* 1906 */     r.init(buf, name, this.in, !isGeneral);
/* 1907 */     this.in = r;
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
/*      */   private boolean pushReader(ExternalEntity next) throws ParseException, IOException {
/*      */     try {
/* 1920 */       InputEntity r = InputEntity.getInputEntity(this.locale);
/* 1921 */       InputSource s = next.getInputSource(this.resolver);
/*      */       
/* 1923 */       r.init(s, next.name, this.in, next.isPE);
/* 1924 */       this.in = r;
/* 1925 */     } catch (SAXException e) {
/* 1926 */       throw translate(e);
/*      */     } 
/* 1928 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void warning(String messageId, Object[] parameters) throws ParseException {
/* 1935 */     fatal(messages.getMessage(this.locale, messageId, parameters));
/*      */   }
/*      */ 
/*      */   
/*      */   void error(String messageId, Object[] parameters) throws ParseException {
/* 1940 */     fatal(messages.getMessage(this.locale, messageId, parameters));
/*      */   }
/*      */   
/*      */   private void fatal(String message) throws ParseException {
/* 1944 */     fatal(message, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fatal(String message, Object[] parameters) throws ParseException {
/* 1949 */     fatal(message, parameters, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fatal(String messageId, Object[] parameters, Exception e) throws ParseException {
/* 1954 */     String m = messages.getMessage(this.locale, messageId, parameters);
/* 1955 */     String m2 = (e == null) ? null : e.toString();
/* 1956 */     if (m2 != null) {
/* 1957 */       m = m + ": " + m2;
/*      */     }
/* 1959 */     ParseException x = new ParseException(m, getPublicId(), getSystemId(), getLineNumber(), getColumnNumber());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1967 */     throw x;
/*      */   }
/*      */   
/*      */   private ParseException translate(SAXException x) {
/* 1971 */     String m = x.getMessage();
/* 1972 */     if (x.getException() != null) {
/* 1973 */       String n = x.getException().toString();
/* 1974 */       if (m != null) {
/* 1975 */         m = m + ": " + n;
/*      */       } else {
/* 1977 */         m = n;
/*      */       } 
/* 1979 */     }  return new ParseException(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class DocLocator
/*      */     implements Locator
/*      */   {
/*      */     public String getPublicId() {
/* 1989 */       return (Parser2.this.in == null) ? null : Parser2.this.in.getPublicId();
/*      */     }
/*      */     
/*      */     public String getSystemId() {
/* 1993 */       return (Parser2.this.in == null) ? null : Parser2.this.in.getSystemId();
/*      */     }
/*      */     
/*      */     public int getLineNumber() {
/* 1997 */       return (Parser2.this.in == null) ? -1 : Parser2.this.in.getLineNumber();
/*      */     }
/*      */     
/*      */     public int getColumnNumber() {
/* 2001 */       return (Parser2.this.in == null) ? -1 : Parser2.this.in.getColumnNumber();
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
/*      */   static final class NameCache
/*      */   {
/* 2021 */     Parser2.NameCacheEntry[] hashtable = new Parser2.NameCacheEntry[541];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String lookup(char[] value, int len) {
/* 2027 */       return (lookupEntry(value, len)).name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Parser2.NameCacheEntry lookupEntry(char[] value, int len) {
/* 2036 */       int index = 0;
/*      */ 
/*      */ 
/*      */       
/* 2040 */       for (int i = 0; i < len; i++)
/* 2041 */         index = index * 31 + value[i]; 
/* 2042 */       index &= Integer.MAX_VALUE;
/* 2043 */       index %= this.hashtable.length;
/*      */       
/*      */       Parser2.NameCacheEntry entry;
/* 2046 */       for (entry = this.hashtable[index]; entry != null; entry = entry.next) {
/* 2047 */         if (entry.matches(value, len)) {
/* 2048 */           return entry;
/*      */         }
/*      */       } 
/*      */       
/* 2052 */       entry = new Parser2.NameCacheEntry();
/* 2053 */       entry.chars = new char[len];
/* 2054 */       System.arraycopy(value, 0, entry.chars, 0, len);
/* 2055 */       entry.name = new String(entry.chars);
/* 2056 */       entry.name = entry.name.intern();
/* 2057 */       entry.next = this.hashtable[index];
/* 2058 */       this.hashtable[index] = entry;
/* 2059 */       return entry;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class NameCacheEntry {
/*      */     String name;
/*      */     char[] chars;
/*      */     NameCacheEntry next;
/*      */     
/*      */     boolean matches(char[] value, int len) {
/* 2069 */       if (this.chars.length != len)
/* 2070 */         return false; 
/* 2071 */       for (int i = 0; i < len; i++) {
/* 2072 */         if (value[i] != this.chars[i])
/* 2073 */           return false; 
/* 2074 */       }  return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2081 */   static final Catalog messages = new Catalog();
/*      */   
/*      */   static final class Catalog extends MessageCatalog {
/*      */     Catalog() {
/* 2085 */       super(Parser.class);
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
/* 2098 */   private InputSource input = null;
/*      */   
/*      */   private boolean coalescing = false;
/*      */   
/* 2102 */   private StringBuffer charsBuffer = null;
/* 2103 */   private int cacheRet = -1;
/* 2104 */   private String cacheName = null;
/* 2105 */   private String cacheValue = null;
/*      */ 
/*      */ 
/*      */   
/* 2109 */   private String simpleCharsBuffer = null;
/*      */   private boolean lastRetWasEnd = false;
/*      */   private FastStack stack;
/*      */   private PIQueue piQueue;
/*      */   private static final int ELEMENT_IN_CONTENT = 1;
/*      */   private static final int ELEMENT_ROOT = 2;
/*      */   private static final int CONTENT_IN_ELEMENT = 4;
/*      */   private static final int CONTENT_IN_INTREF = 8;
/*      */   private static final int CONTENT_IN_EXTREF = 16;
/*      */   private static final int ELEMENT = 256;
/*      */   private static final int CONTENT = 1024;
/*      */   private static final int START = 1;
/*      */   private static final int END = 2;
/*      */   private static final int CHARS = 3;
/*      */   private static final int PI = 4;
/*      */   private static final int EMPTY = 10;
/*      */   private static final int ATTR = 11;
/*      */   private boolean haveAttributes;
/*      */   private int startLine;
/*      */   private boolean hasContent;
/*      */   
/*      */   public Parser2(InputStream in, boolean coalescing, boolean namespaceAware) {
/* 2131 */     this(new InputSource(in), coalescing, namespaceAware, false);
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
/*      */   public Parser2(InputStream in, boolean coalescing, boolean namespaceAware, boolean rejectDTDs) {
/* 2155 */     this(new InputSource(in), coalescing, namespaceAware, rejectDTDs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parser2(InputStream in) {
/* 2164 */     this(new InputSource(in), false, false, false);
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
/*      */   public Parser2(File file, boolean coalescing, boolean namespaceAware) throws IOException {
/* 2181 */     this(file, coalescing, namespaceAware, false);
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
/*      */   public Parser2(File file, boolean coalescing, boolean namespaceAware, boolean rejectDTDs) throws IOException {
/* 2240 */     this.stack = new FastStack(100);
/*      */     
/* 2242 */     this.piQueue = new PIQueue(10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2269 */     this.haveAttributes = false;
/*      */     
/* 2271 */     this.hasContent = true; InputStream in = new BufferedInputStream(new FileInputStream(file)); InputSource is = new InputSource(in); is.setSystemId(file.toURL().toString()); this.locator = new DocLocator(); this.input = is; this.coalescing = coalescing; this.namespace = namespaceAware; this.rejectDTDs = rejectDTDs; } public Parser2(File file) throws IOException { this.stack = new FastStack(100); this.piQueue = new PIQueue(10); this.haveAttributes = false; this.hasContent = true; InputStream in = new BufferedInputStream(new FileInputStream(file)); InputSource is = new InputSource(in); is.setSystemId(file.toURL().toString()); this.locator = new DocLocator(); this.input = is; } private Parser2(InputSource input, boolean coalescing, boolean namespaceAware, boolean rejectDTDs) { this.stack = new FastStack(100); this.piQueue = new PIQueue(10); this.haveAttributes = false; this.hasContent = true; this.locator = new DocLocator();
/*      */     this.input = input;
/*      */     this.coalescing = coalescing;
/*      */     this.namespace = namespaceAware;
/* 2275 */     this.rejectDTDs = rejectDTDs; } private void prologue() throws IOException, ParseException { init();
/* 2276 */     if (this.input == null)
/* 2277 */       fatal("P-000"); 
/* 2278 */     this.in = InputEntity.getInputEntity(this.locale);
/* 2279 */     this.in.init(this.input, (String)null, (InputEntity)null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2284 */     maybeXmlDecl();
/* 2285 */     maybeMisc(false);
/*      */     
/* 2287 */     maybeDoctypeDecl();
/*      */     
/* 2289 */     maybeMisc(false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int parse() throws ParseException, IOException {
/* 2298 */     int ret = 0;
/*      */     
/* 2300 */     try { if (!this.donePrologue) {
/* 2301 */         prologue();
/* 2302 */         this.donePrologue = true;
/*      */       } 
/* 2304 */       if ((ret = retrievePIs()) != -1) {
/* 2305 */         return ret;
/*      */       }
/*      */       
/* 2308 */       if (!this.doneContent) {
/* 2309 */         if (!this.coalescing) {
/* 2310 */           if ((ret = parseContent()) != 10) {
/* 2311 */             return ret;
/*      */           }
/* 2313 */           this.doneContent = true;
/*      */         } else {
/*      */           
/* 2316 */           if (this.lastRetWasEnd) {
/* 2317 */             this.ns.slideContextUp();
/* 2318 */             this.lastRetWasEnd = false;
/*      */           } 
/*      */ 
/*      */           
/* 2322 */           if (this.cacheRet != -1) {
/* 2323 */             ret = this.cacheRet;
/* 2324 */             this.curName = this.cacheName;
/* 2325 */             this.curValue = this.cacheValue;
/* 2326 */             this.cacheRet = -1;
/* 2327 */             this.cacheName = null;
/* 2328 */             this.cacheValue = null;
/*      */             
/* 2330 */             if (this.namespace) {
/* 2331 */               if (ret == 1) {
/*      */                 
/* 2333 */                 this.ns.slideContextDown();
/* 2334 */               } else if (ret == 2) {
/*      */                 
/* 2336 */                 this.lastRetWasEnd = true;
/*      */               } 
/*      */             }
/*      */             
/* 2340 */             return ret;
/*      */           } 
/* 2342 */           while ((ret = parseContent()) != 10) {
/* 2343 */             if (ret == 3) {
/*      */               
/* 2345 */               if (this.simpleCharsBuffer == null) {
/* 2346 */                 this.simpleCharsBuffer = this.curValue; continue;
/*      */               } 
/* 2348 */               if (this.charsBuffer == null) {
/* 2349 */                 this.charsBuffer = new StringBuffer();
/* 2350 */                 this.charsBuffer.append(this.simpleCharsBuffer);
/*      */               } 
/* 2352 */               this.charsBuffer.append(this.curValue);
/*      */               continue;
/*      */             } 
/* 2355 */             if (ret != 3) {
/* 2356 */               if (this.simpleCharsBuffer != null) {
/*      */                 
/* 2358 */                 this.cacheRet = ret;
/* 2359 */                 this.cacheName = this.curName;
/* 2360 */                 this.cacheValue = this.curValue;
/*      */                 
/* 2362 */                 if (this.charsBuffer == null) {
/* 2363 */                   this.curName = null;
/* 2364 */                   this.curValue = this.simpleCharsBuffer;
/*      */                 }
/*      */                 else {
/*      */                   
/* 2368 */                   this.curName = null;
/* 2369 */                   this.curValue = this.charsBuffer.toString();
/* 2370 */                   this.charsBuffer = null;
/*      */                 } 
/* 2372 */                 this.simpleCharsBuffer = null;
/*      */                 
/* 2374 */                 if (this.namespace) {
/* 2375 */                   if (this.cacheRet == 1) {
/*      */                     
/* 2377 */                     this.ns.slideContextUp();
/* 2378 */                   } else if (this.cacheRet == 2) {
/*      */                     
/* 2380 */                     this.ns.slideContextDown();
/*      */                   } 
/*      */                 }
/*      */                 
/* 2384 */                 return 3;
/*      */               } 
/* 2386 */               if (ret == 2) {
/* 2387 */                 this.lastRetWasEnd = true;
/* 2388 */                 this.ns.slideContextDown();
/*      */               } 
/* 2390 */               return ret;
/*      */             } 
/*      */           } 
/*      */           
/* 2394 */           this.doneContent = true;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2399 */       if (!this.doneEpilogue) {
/* 2400 */         epilogue();
/* 2401 */         this.doneEpilogue = true;
/*      */       } 
/* 2403 */       return retrievePIs(); }
/* 2404 */     catch (EndOfInputException e)
/* 2405 */     { if (!this.in.isDocument())
/* 2406 */       { String name = this.in.getName();
/*      */         
/*      */         try { while (true)
/* 2409 */           { this.in = this.in.pop();
/* 2410 */             if (!this.in.isInternal())
/* 2411 */             { fatal("P-002", new Object[] { name }, e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2430 */               return ret; }  }  } catch (IOException x) { fatal("P-002", new Object[] { name }, e); }  } else { fatal("P-003", null, e); }  } catch (RuntimeException e) { throw new ParseException((e.getMessage() != null) ? e.getMessage() : e.getClass().getName(), getPublicId(), getSystemId(), getLineNumber(), getColumnNumber()); }  return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   private int retrievePIs() {
/* 2435 */     if (!this.piQueue.empty()) {
/* 2436 */       this.curName = this.piQueue.getNextTarget();
/* 2437 */       this.curValue = this.piQueue.getNextContent();
/* 2438 */       return 4;
/*      */     } 
/* 2440 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void epilogue() throws IOException, ParseException {
/*      */     try {
/* 2447 */       afterRoot();
/* 2448 */       maybeMisc(true);
/* 2449 */       if (!this.in.isEOF()) {
/* 2450 */         fatal("P-001", new Object[] { Integer.toHexString(getc()) });
/*      */       }
/*      */     }
/* 2453 */     catch (EndOfInputException e) {
/* 2454 */       if (!this.in.isDocument()) {
/* 2455 */         String name = this.in.getName();
/*      */         while (true) {
/* 2457 */           this.in = this.in.pop();
/* 2458 */           if (!this.in.isInternal())
/* 2459 */           { fatal("P-002", new Object[] { name }, e); return; } 
/*      */         } 
/* 2461 */       }  fatal("P-003", null, e);
/*      */     }
/* 2463 */     catch (RuntimeException e) {
/*      */       
/* 2465 */       throw new ParseException((e.getMessage() != null) ? e.getMessage() : e.getClass().getName(), getPublicId(), getSystemId(), getLineNumber(), getColumnNumber());
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2475 */       this.strTmp = null;
/* 2476 */       this.attTmp = null;
/* 2477 */       this.nameTmp = null;
/* 2478 */       this.nameCache = null;
/*      */ 
/*      */       
/* 2481 */       if (this.in != null) {
/* 2482 */         this.in.close();
/* 2483 */         this.in = null;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2489 */       this.params.clear();
/* 2490 */       this.entities.clear();
/* 2491 */       this.notations.clear();
/* 2492 */       this.elements.clear();
/*      */       
/* 2494 */       afterDocument();
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
/*      */   private ElementDecl getElement() throws IOException, ParseException {
/* 2508 */     NameCacheEntry name = maybeGetNameCacheEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2513 */     if (name == null) {
/* 2514 */       return null;
/*      */     }
/* 2516 */     ElementDecl element = (ElementDecl)this.elements.get(name.name);
/* 2517 */     if (element == null || element.contentType == null) {
/*      */       
/* 2519 */       element = new ElementDecl(name.name);
/* 2520 */       element.contentType = "ANY";
/* 2521 */       this.elements.put(name.name, element);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2526 */     this.startLine = this.in.getLineNumber();
/*      */ 
/*      */ 
/*      */     
/* 2530 */     boolean sawWhite = this.in.maybeWhitespace();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2535 */     while (!this.in.peekc('>')) {
/*      */       String value;
/*      */ 
/*      */       
/* 2539 */       if (this.in.peekc('/')) {
/* 2540 */         this.hasContent = false;
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 2545 */       if (!sawWhite) {
/* 2546 */         fatal("P-030");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2554 */       String attName = maybeGetName();
/*      */ 
/*      */       
/* 2557 */       if (attName == null) {
/* 2558 */         fatal("P-031", new Object[] { new Character(getc()) });
/*      */       }
/* 2560 */       if (this.attTmp.getValue(attName) != null) {
/* 2561 */         fatal("P-032", new Object[] { attName });
/*      */       }
/*      */       
/* 2564 */       this.in.maybeWhitespace();
/* 2565 */       nextChar('=', "F-026", attName);
/* 2566 */       this.in.maybeWhitespace();
/*      */       
/* 2568 */       parseLiteral(false);
/* 2569 */       sawWhite = this.in.maybeWhitespace();
/*      */ 
/*      */ 
/*      */       
/* 2573 */       AttributeDecl info = (element == null) ? null : (AttributeDecl)element.attributes.get(attName);
/*      */ 
/*      */ 
/*      */       
/* 2577 */       if (info == null) {
/* 2578 */         value = this.strTmp.toString();
/*      */       }
/* 2580 */       else if (!"CDATA".equals(info.type)) {
/* 2581 */         value = normalize(!info.isFromInternalSubset);
/*      */       } else {
/* 2583 */         value = this.strTmp.toString();
/*      */       } 
/*      */       
/* 2586 */       if ("xml:lang".equals(attName) && !isXmlLang(value)) {
/* 2587 */         error("P-033", new Object[] { value });
/*      */       }
/* 2589 */       this.attTmp.addAttribute("", attName, attName, (info == null) ? "CDATA" : info.type, value, (info == null) ? null : info.defaultValue, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2597 */       this.haveAttributes = true;
/*      */     } 
/* 2599 */     if (element != null) {
/* 2600 */       this.attTmp.setIdAttributeName(element.id);
/*      */     }
/*      */ 
/*      */     
/* 2604 */     if (element != null && element.attributes.size() != 0) {
/* 2605 */       this.haveAttributes = (defaultAttributes(this.attTmp, element) || this.haveAttributes);
/*      */     }
/*      */     
/* 2608 */     this.attr = this.attTmp;
/* 2609 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeReferenceInContent() throws IOException, ParseException {
/* 2617 */     if (!this.in.peekc('&')) {
/* 2618 */       return false;
/*      */     }
/* 2620 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeEntityReference() throws IOException, ParseException {
/* 2626 */     if (!this.in.peekc('#')) {
/* 2627 */       return true;
/*      */     }
/* 2629 */     return false;
/*      */   }
/*      */   
/*      */   private Object getEntityReference() throws IOException, ParseException {
/* 2633 */     String name = maybeGetName();
/* 2634 */     if (name == null)
/* 2635 */       fatal("P-009"); 
/* 2636 */     nextChar(';', "F-020", name);
/* 2637 */     Object entity = this.entities.get(name);
/* 2638 */     err(" after in = " + this.in);
/*      */     
/* 2640 */     if (entity == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2646 */       fatal("P-014", new Object[] { name });
/*      */     }
/* 2648 */     return entity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void elementEpilogue(ElementDecl element) throws IOException, ParseException {
/* 2659 */     if (!this.in.peek(element.name, null)) {
/* 2660 */       fatal("P-034", new Object[] { element.name, new Integer(this.startLine) });
/*      */     }
/*      */ 
/*      */     
/* 2664 */     this.in.maybeWhitespace();
/* 2665 */     nextChar('>', "F-027", element.name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void intRefEpilogue(StackElement elt) throws IOException, ParseException {
/* 2671 */     InternalEntity entity = (InternalEntity)elt.entity;
/* 2672 */     InputEntity last = elt.in;
/* 2673 */     if (this.in != last && !this.in.isEOF()) {
/* 2674 */       while (this.in.isInternal())
/* 2675 */         this.in = this.in.pop(); 
/* 2676 */       fatal("P-052", new Object[] { entity.name });
/*      */     } 
/* 2678 */     this.in = this.in.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void extRefEpilogue(StackElement elt) throws IOException, ParseException {
/* 2684 */     ExternalEntity entity = (ExternalEntity)elt.entity;
/*      */     
/* 2686 */     if (!this.in.isEOF())
/* 2687 */       fatal("P-058", new Object[] { entity.name }); 
/* 2688 */     this.in = this.in.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybePI(boolean skipStart) throws IOException, ParseException {
/* 2698 */     boolean savedLexicalPE = this.doLexicalPE;
/*      */     
/* 2700 */     if (!this.in.peek(skipStart ? "?" : "<?", null))
/* 2701 */       return false; 
/* 2702 */     this.doLexicalPE = false;
/*      */     
/* 2704 */     String target = maybeGetName();
/* 2705 */     String piContent = null;
/*      */     
/* 2707 */     if (target == null)
/* 2708 */       fatal("P-018"); 
/* 2709 */     if ("xml".equals(target))
/* 2710 */       fatal("P-019"); 
/* 2711 */     if ("xml".equalsIgnoreCase(target)) {
/* 2712 */       fatal("P-020", new Object[] { target });
/*      */     }
/* 2714 */     if (maybeWhitespace()) {
/* 2715 */       this.strTmp = new StringBuffer();
/*      */       
/*      */       try {
/*      */         while (true) {
/* 2719 */           char c = this.in.getc();
/*      */           
/* 2721 */           if (c == '?' && this.in.peekc('>'))
/*      */             break; 
/* 2723 */           this.strTmp.append(c);
/*      */         } 
/* 2725 */       } catch (EndOfInputException e) {
/* 2726 */         fatal("P-021");
/*      */       } 
/* 2728 */       piContent = this.strTmp.toString();
/*      */     } else {
/* 2730 */       if (!this.in.peek("?>", null))
/* 2731 */         fatal("P-022"); 
/* 2732 */       piContent = "";
/*      */     } 
/*      */     
/* 2735 */     this.doLexicalPE = savedLexicalPE;
/* 2736 */     this.piQueue.in(target, piContent);
/* 2737 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processStartElement(ElementDecl elt) throws IOException, ParseException {
/* 2747 */     this.ns.pushContext();
/* 2748 */     boolean seenDecl = false;
/*      */     
/* 2750 */     int length = this.attr.getLength(); int i;
/* 2751 */     for (i = 0; i < length; i++) {
/* 2752 */       String attRawName = this.attr.getQName(i);
/* 2753 */       String value = this.attr.getValue(i);
/*      */ 
/*      */ 
/*      */       
/* 2757 */       boolean isNamespaceDecl = false;
/* 2758 */       String prefix = "";
/*      */       
/* 2760 */       if (attRawName.startsWith("xmlns")) {
/* 2761 */         isNamespaceDecl = true;
/* 2762 */         if (attRawName.length() != 5)
/*      */         {
/* 2764 */           if (attRawName.charAt(5) == ':') {
/* 2765 */             prefix = attRawName.substring(6);
/*      */           } else {
/* 2767 */             isNamespaceDecl = false;
/*      */           } 
/*      */         }
/*      */       } 
/* 2771 */       if (isNamespaceDecl) {
/* 2772 */         if (!this.ns.declarePrefix(prefix, value))
/*      */         {
/* 2774 */           fatal("P-086", new Object[] { prefix });
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2781 */         seenDecl = true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2786 */         this.attr.setURI(i, "http://www.w3.org/2000/xmlns/");
/*      */       }
/*      */       else {
/*      */         
/* 2790 */         String[] attName = this.ns.processName(attRawName, this.parts, true);
/* 2791 */         if (attName != null) {
/*      */ 
/*      */           
/* 2794 */           this.attr.setURI(i, attName[0]);
/* 2795 */           this.attr.setLocalName(i, attName[1]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2805 */     if (seenDecl) {
/* 2806 */       length = this.attr.getLength();
/* 2807 */       for (i = 0; i < length; i++) {
/* 2808 */         String attRawName = this.attr.getQName(i);
/*      */         
/* 2810 */         if (attRawName.startsWith("xmlns"))
/*      */         {
/*      */           
/* 2813 */           if (attRawName.length() == 5 || attRawName.charAt(5) == ':') {
/*      */             continue;
/*      */           }
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2821 */         String[] attName = this.ns.processName(attRawName, this.parts, true);
/* 2822 */         if (attName != null) {
/*      */ 
/*      */           
/* 2825 */           this.attr.setURI(i, attName[0]);
/* 2826 */           this.attr.setLocalName(i, attName[1]);
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */     } 
/* 2831 */     getSetCurName(elt.name, false);
/* 2832 */     this.curValue = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processEndElement(ElementDecl elt) throws IOException, ParseException {
/* 2841 */     getSetCurName(elt.name, false);
/* 2842 */     this.ns.popContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getSetCurName(String rawName, boolean isAttribute) throws ParseException {
/* 2851 */     String[] names = this.ns.processName(rawName, this.parts, isAttribute);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2859 */     if (names == null) {
/* 2860 */       fatal("P-084", new Object[] { rawName });
/*      */     }
/*      */     
/* 2863 */     this.curURI = names[0];
/* 2864 */     this.curName = names[1];
/* 2865 */     this.curValue = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseContent() throws IOException, ParseException {
/* 2874 */     ElementDecl elt = null;
/*      */     
/*      */     while (true) {
/*      */       ElementDecl e2;
/*      */       StackElement se2;
/*      */       String chars;
/* 2880 */       while (this.stack.empty()) {
/*      */ 
/*      */         
/* 2883 */         if (!this.startEmptyStack) {
/* 2884 */           return 10;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2889 */         if (this.startEmptyStack && (!this.in.peekc('<') || (elt = getElement()) == null)) {
/*      */           
/* 2891 */           fatal("P-067");
/*      */           continue;
/*      */         } 
/* 2894 */         this.startEmptyStack = false;
/* 2895 */         this.stack.push(newStackElement(2, 256, elt, null, null));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2902 */         if (!this.haveAttributes && this.hasContent) {
/* 2903 */           this.stack.push(newStackElement(4, 1024, elt, null, null));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2911 */         if (!this.namespace) {
/* 2912 */           this.curName = elt.name;
/* 2913 */           this.curValue = null;
/*      */         } else {
/* 2915 */           processStartElement(elt);
/*      */         } 
/* 2917 */         return 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2926 */       StackElement se = this.stack.pop();
/* 2927 */       elt = se.elt;
/* 2928 */       switch (se.curState) {
/*      */         case 256:
/* 2930 */           if (this.attr == null)
/*      */           {
/* 2932 */             fatal("P-082");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2949 */           if (this.haveAttributes) {
/* 2950 */             this.attr = null;
/* 2951 */             this.attrIndex = 0;
/* 2952 */             this.attTmp.clear();
/* 2953 */             this.haveAttributes = false;
/*      */           } 
/* 2955 */           if (this.hasContent) {
/*      */ 
/*      */             
/* 2958 */             this.stack.push(se);
/* 2959 */             this.stack.push(newStackElement(4, 1024, elt, null, null));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2969 */           this.hasContent = true;
/* 2970 */           nextChar('>', "F-027", elt.name);
/* 2971 */           freeStackElement(se);
/* 2972 */           this.curName = elt.name;
/* 2973 */           if (!this.namespace) {
/* 2974 */             this.curValue = null;
/*      */           } else {
/* 2976 */             processEndElement(elt);
/*      */           } 
/* 2978 */           return 2;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1024:
/* 2983 */           e2 = null;
/* 2984 */           se2 = null;
/* 2985 */           chars = null;
/* 2986 */           if (this.in.peekc('<')) {
/* 2987 */             if ((e2 = getElement()) != null) {
/*      */               
/* 2989 */               this.stack.push(se);
/* 2990 */               this.stack.push(newStackElement(1, 256, e2, null, null));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2997 */               if (!this.haveAttributes && this.hasContent) {
/* 2998 */                 this.stack.push(newStackElement(4, 1024, e2, null, null));
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3005 */               if (!this.namespace) {
/* 3006 */                 this.curName = e2.name;
/* 3007 */                 this.curValue = null;
/*      */               } else {
/* 3009 */                 processStartElement(e2);
/*      */               } 
/* 3011 */               return 1;
/* 3012 */             }  if (!this.in.peekc('/'))
/*      */             {
/* 3014 */               if (maybeComment(true)) {
/*      */                 
/* 3016 */                 this.stack.push(se); continue;
/*      */               } 
/* 3018 */               if (maybePI(true)) {
/*      */                 
/* 3020 */                 this.stack.push(se);
/* 3021 */                 this.curName = this.piQueue.getNextTarget();
/* 3022 */                 this.curValue = this.piQueue.getNextContent();
/* 3023 */                 return 4;
/* 3024 */               }  if ((chars = this.in.getUnparsedContent((elt != null && elt.ignoreWhitespace), null)) != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 3031 */                 this.stack.push(se);
/* 3032 */                 if (chars.length() != 0) {
/* 3033 */                   this.curName = null;
/* 3034 */                   this.curValue = chars;
/* 3035 */                   return 3;
/*      */                 } 
/*      */                 
/*      */                 continue;
/*      */               } 
/* 3040 */               char c = getc();
/* 3041 */               fatal("P-079", new Object[] { Integer.toHexString(c), new Character(c) });
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 3048 */             if (elt != null && elt.ignoreWhitespace && this.in.ignorableWhitespace()) {
/*      */ 
/*      */ 
/*      */               
/* 3052 */               this.stack.push(se); continue;
/*      */             } 
/* 3054 */             if ((chars = this.in.getParsedContent(this.coalescing)) != null) {
/*      */ 
/*      */ 
/*      */               
/* 3058 */               this.stack.push(se);
/* 3059 */               if (chars.length() != 0) {
/* 3060 */                 this.curName = null;
/* 3061 */                 this.curValue = chars;
/* 3062 */                 return 3;
/*      */               } 
/*      */               continue;
/*      */             } 
/* 3066 */             if (this.in.isEOF()) {
/*      */               
/* 3068 */               if (se.origState == 4) {
/* 3069 */                 fatal("P-035");
/*      */               }
/* 3071 */             } else if (maybeReferenceInContent()) {
/*      */               
/* 3073 */               if (maybeEntityReference()) {
/* 3074 */                 this.stack.push(se);
/* 3075 */                 Object entity = getEntityReference();
/* 3076 */                 InputEntity last = this.in;
/* 3077 */                 if (entity instanceof InternalEntity) {
/* 3078 */                   InternalEntity e = (InternalEntity)entity;
/* 3079 */                   this.stack.push(newStackElement(8, 1024, elt, e, last));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 3086 */                   pushReader(e.buf, e.name, true); continue;
/*      */                 } 
/* 3088 */                 if (entity instanceof ExternalEntity) {
/* 3089 */                   ExternalEntity e = (ExternalEntity)entity;
/* 3090 */                   if (e.notation != null)
/* 3091 */                     fatal("P-053", new Object[] { e.name }); 
/* 3092 */                   if (!pushReader(e)) {
/*      */                     continue;
/*      */                   }
/* 3095 */                   maybeTextDecl();
/* 3096 */                   this.stack.push(newStackElement(16, 1024, elt, e, null));
/*      */ 
/*      */ 
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/* 3105 */                 throw new InternalError();
/*      */               } 
/*      */               
/* 3108 */               this.stack.push(se);
/* 3109 */               int ret = surrogatesToCharTmp(parseCharNumber());
/*      */               
/* 3111 */               this.curName = null;
/* 3112 */               this.curValue = new String(this.charTmp, 0, ret);
/* 3113 */               return 3;
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 3119 */           if (se.origState == 4) {
/*      */             
/* 3121 */             se2 = this.stack.pop();
/* 3122 */             if (se2.curState != 256)
/*      */             {
/* 3124 */               fatal("P-083");
/*      */             }
/*      */             
/* 3127 */             elementEpilogue(elt);
/* 3128 */             this.curName = elt.name;
/* 3129 */             if (!this.namespace) {
/* 3130 */               this.curValue = null;
/*      */             } else {
/* 3132 */               processEndElement(elt);
/*      */             } 
/* 3134 */             freeStackElement(se);
/* 3135 */             freeStackElement(se2);
/* 3136 */             return 2;
/* 3137 */           }  if (se.origState == 8) {
/*      */             
/* 3139 */             intRefEpilogue(se);
/* 3140 */             freeStackElement(se); continue;
/* 3141 */           }  if (se.origState == 16) {
/*      */             
/* 3143 */             extRefEpilogue(se);
/* 3144 */             freeStackElement(se);
/*      */           } 
/*      */           continue;
/*      */       } 
/*      */       
/* 3149 */       fatal("P-083");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class FastStack
/*      */   {
/*      */     private Parser2.StackElement first;
/*      */ 
/*      */ 
/*      */     
/*      */     public FastStack(int initialCapacity) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean empty() {
/* 3166 */       return (this.first == null);
/*      */     }
/*      */     
/*      */     public void push(Parser2.StackElement e) {
/* 3170 */       if (this.first == null) {
/* 3171 */         this.first = e;
/*      */       } else {
/* 3173 */         e.next = this.first;
/* 3174 */         this.first = e;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Parser2.StackElement pop() {
/* 3179 */       Parser2.StackElement result = this.first;
/* 3180 */       this.first = this.first.next;
/* 3181 */       result.next = null;
/* 3182 */       return result;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 3186 */       this.first = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final class StackElement
/*      */   {
/*      */     int origState;
/*      */     
/*      */     int curState;
/*      */     
/*      */     ElementDecl elt;
/*      */     
/*      */     EntityDecl entity;
/*      */     
/*      */     InputEntity in;
/*      */     
/*      */     StackElement next;
/*      */     
/*      */     public StackElement(int origState, int curState, ElementDecl elt, EntityDecl entity, InputEntity in) {
/* 3206 */       this.origState = origState;
/* 3207 */       this.curState = curState;
/* 3208 */       this.elt = elt;
/* 3209 */       this.entity = entity;
/* 3210 */       this.in = in;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StackElement newStackElement(int origState, int curState, ElementDecl elt, EntityDecl entity, InputEntity in) {
/* 3220 */     return new StackElement(origState, curState, elt, entity, in);
/*      */   }
/*      */ 
/*      */   
/*      */   private void freeStackElement(StackElement e) {}
/*      */   
/*      */   private final class PIQueue
/*      */   {
/*      */     private String[] pi;
/* 3229 */     private int size = 0;
/* 3230 */     private int index = 0;
/*      */     
/*      */     public PIQueue(int initialCapacity) {
/* 3233 */       this.pi = new String[2 * initialCapacity];
/*      */     }
/*      */     
/*      */     public boolean empty() {
/* 3237 */       return (this.size == this.index);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 3241 */       this.size = 0;
/*      */     }
/*      */     
/*      */     public void in(String target, String content) {
/* 3245 */       ensureCapacity();
/* 3246 */       this.pi[this.size++] = target;
/* 3247 */       this.pi[this.size++] = content;
/*      */     }
/*      */     
/*      */     public String getNextTarget() {
/* 3251 */       String result = null;
/* 3252 */       if (this.index < this.size) {
/* 3253 */         result = this.pi[this.index];
/* 3254 */         this.pi[this.index++] = null;
/*      */       } 
/* 3256 */       return result;
/*      */     }
/*      */     
/*      */     public String getNextContent() {
/* 3260 */       String result = null;
/* 3261 */       if (this.index < this.size) {
/* 3262 */         result = this.pi[this.index];
/* 3263 */         this.pi[this.index++] = null;
/*      */       } 
/* 3265 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void ensureCapacity() {
/* 3273 */       if (this.pi.length == this.size) {
/* 3274 */         String[] oldPi = this.pi;
/* 3275 */         this.pi = new String[2 * this.pi.length + 2];
/* 3276 */         System.arraycopy(oldPi, 0, this.pi, 0, this.size);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void err(String msg) {}
/*      */   
/*      */   private void debug() {}
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\Parser2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
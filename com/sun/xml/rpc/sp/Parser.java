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
/*      */ public final class Parser
/*      */ {
/*   70 */   private String curName = null;
/*   71 */   private String curValue = null;
/*      */   
/*   73 */   private String curURI = null;
/*      */ 
/*      */   
/*      */   private InputEntity in;
/*      */   
/*      */   private AttributesExImpl attTmp;
/*      */   
/*   80 */   private String[] parts = new String[3];
/*      */   private StringBuffer strTmp;
/*      */   private char[] nameTmp;
/*      */   private NameCache nameCache;
/*   84 */   private char[] charTmp = new char[2];
/*      */   
/*      */   private boolean namespace = false;
/*      */   
/*   88 */   private NamespaceSupport ns = null;
/*      */   
/*      */   private boolean isInAttribute = false;
/*      */   
/*      */   private boolean inExternalPE;
/*      */   
/*      */   private boolean doLexicalPE;
/*      */   
/*      */   private boolean donePrologue;
/*      */   
/*      */   private boolean doneEpilogue;
/*      */   private boolean doneContent;
/*  100 */   private AttributesExImpl attr = null;
/*  101 */   private int attrIndex = 0;
/*      */   
/*      */   private boolean startEmptyStack = true;
/*      */   
/*      */   private boolean isStandalone;
/*      */   
/*      */   private String rootElementName;
/*      */   
/*      */   private boolean ignoreDeclarations;
/*  110 */   private SimpleHashtable elements = new SimpleHashtable(47);
/*  111 */   private SimpleHashtable params = new SimpleHashtable(7);
/*      */ 
/*      */   
/*  114 */   Map notations = new HashMap<Object, Object>(7);
/*  115 */   SimpleHashtable entities = new SimpleHashtable(17);
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
/*      */   private static final String XmlLang = "xml:lang";
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocale(Locale l) throws ParseException {
/*  142 */     if (l != null && !messages.isLocaleSupported(l.toString()))
/*  143 */       fatal(messages.getMessage(this.locale, "P-078", new Object[] { l })); 
/*  144 */     this.locale = l;
/*      */   }
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  149 */     return this.locale;
/*      */   }
/*      */   
/*      */   public String getCurName() {
/*  153 */     return this.curName;
/*      */   }
/*      */   
/*      */   public String getCurURI() {
/*  157 */     return this.curURI;
/*      */   }
/*      */   
/*      */   public String getCurValue() {
/*  161 */     return this.curValue;
/*      */   }
/*      */   
/*      */   public int getLineNumber() {
/*  165 */     return this.locator.getLineNumber();
/*      */   }
/*      */   
/*      */   public int getColumnNumber() {
/*  169 */     return this.locator.getColumnNumber();
/*      */   }
/*      */   
/*      */   public String getPublicId() {
/*  173 */     return this.locator.getPublicId();
/*      */   }
/*      */   
/*      */   public String getSystemId() {
/*  177 */     return this.locator.getSystemId();
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
/*  196 */     Locale l = messages.chooseLocale(languages);
/*      */     
/*  198 */     if (l != null)
/*  199 */       setLocale(l); 
/*  200 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityResolver(EntityResolver r) {
/*  205 */     this.resolver = r;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  210 */     return this.resolver;
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
/*  227 */     this.fastStandalone = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFastStandalone() {
/*  235 */     return this.fastStandalone;
/*      */   }
/*      */ 
/*      */   
/*      */   private void init() {
/*  240 */     this.in = null;
/*      */ 
/*      */     
/*  243 */     this.attTmp = new AttributesExImpl();
/*  244 */     this.strTmp = new StringBuffer();
/*  245 */     this.nameTmp = new char[20];
/*  246 */     this.nameCache = new NameCache();
/*      */     
/*  248 */     if (this.namespace) {
/*  249 */       if (this.ns == null) {
/*  250 */         this.ns = new NamespaceSupport();
/*      */       } else {
/*  252 */         this.ns.reset();
/*      */       } 
/*      */     }
/*      */     
/*  256 */     this.isStandalone = false;
/*  257 */     this.rootElementName = null;
/*  258 */     this.isInAttribute = false;
/*      */     
/*  260 */     this.inExternalPE = false;
/*  261 */     this.doLexicalPE = false;
/*  262 */     this.donePrologue = false;
/*  263 */     this.doneEpilogue = false;
/*  264 */     this.doneContent = false;
/*      */     
/*  266 */     this.attr = null;
/*  267 */     this.attrIndex = 0;
/*  268 */     this.startEmptyStack = true;
/*      */     
/*  270 */     this.entities.clear();
/*  271 */     this.notations.clear();
/*  272 */     this.params.clear();
/*  273 */     this.elements.clear();
/*  274 */     this.ignoreDeclarations = false;
/*      */     
/*  276 */     this.stack.clear();
/*  277 */     this.piQueue.clear();
/*      */ 
/*      */     
/*  280 */     builtin("amp", "&#38;");
/*  281 */     builtin("lt", "&#60;");
/*  282 */     builtin("gt", ">");
/*  283 */     builtin("quot", "\"");
/*  284 */     builtin("apos", "'");
/*      */     
/*  286 */     if (this.locale == null)
/*  287 */       this.locale = Locale.getDefault(); 
/*  288 */     if (this.resolver == null) {
/*  289 */       this.resolver = new Resolver();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void builtin(String entityName, String entityValue) {
/*  295 */     InternalEntity entity = new InternalEntity(entityName, entityValue.toCharArray());
/*  296 */     this.entities.put(entityName, entity);
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
/*  311 */     if (!maybeWhitespace()) {
/*  312 */       fatal("P-004", new Object[] { messages.getMessage(this.locale, roleId) });
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean maybeWhitespace() throws IOException, ParseException {
/*  317 */     if (!this.inExternalPE || !this.doLexicalPE) {
/*  318 */       return this.in.maybeWhitespace();
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
/*  333 */     char c = getc();
/*  334 */     boolean saw = false;
/*      */     
/*  336 */     while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
/*  337 */       saw = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  342 */       if (this.in.isEOF() && !this.in.isInternal())
/*  343 */         return saw; 
/*  344 */       c = getc();
/*      */     } 
/*  346 */     ungetc();
/*  347 */     return saw;
/*      */   }
/*      */   
/*      */   private String maybeGetName() throws IOException, ParseException {
/*  351 */     NameCacheEntry entry = maybeGetNameCacheEntry();
/*  352 */     return (entry == null) ? null : entry.name;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private NameCacheEntry maybeGetNameCacheEntry() throws IOException, ParseException {
/*  358 */     char c = getc();
/*      */     
/*  360 */     if (!XmlChars.isLetter(c) && c != ':' && c != '_') {
/*  361 */       ungetc();
/*  362 */       return null;
/*      */     } 
/*  364 */     return nameCharString(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNmtoken() throws ParseException, IOException {
/*  370 */     char c = getc();
/*  371 */     if (!XmlChars.isNameChar(c))
/*  372 */       fatal("P-006", new Object[] { new Character(c) }); 
/*  373 */     return (nameCharString(c)).name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NameCacheEntry nameCharString(char c) throws IOException, ParseException {
/*  383 */     int i = 1;
/*      */     
/*  385 */     this.nameTmp[0] = c;
/*      */     
/*  387 */     while ((c = this.in.getNameChar()) != '\000') {
/*      */       
/*  389 */       if (i >= this.nameTmp.length) {
/*  390 */         char[] tmp = new char[this.nameTmp.length + 10];
/*  391 */         System.arraycopy(this.nameTmp, 0, tmp, 0, this.nameTmp.length);
/*  392 */         this.nameTmp = tmp;
/*      */       } 
/*  394 */       this.nameTmp[i++] = c;
/*      */     } 
/*  396 */     return this.nameCache.lookupEntry(this.nameTmp, i);
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
/*  420 */     boolean savedLexicalPE = this.doLexicalPE;
/*  421 */     this.doLexicalPE = isEntityValue;
/*      */     
/*  423 */     char quote = getc();
/*      */     
/*  425 */     InputEntity source = this.in;
/*      */     
/*  427 */     if (quote != '\'' && quote != '"') {
/*  428 */       fatal("P-007");
/*      */     }
/*      */ 
/*      */     
/*  432 */     this.isInAttribute = !isEntityValue;
/*      */ 
/*      */     
/*  435 */     this.strTmp = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  440 */       if (this.in != source && this.in.isEOF()) {
/*      */ 
/*      */         
/*  443 */         this.in = this.in.pop(); continue;
/*      */       } 
/*      */       char c;
/*  446 */       if ((c = getc()) == quote && this.in == source) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  453 */       if (c == '&') {
/*  454 */         String entityName = maybeGetName();
/*      */         
/*  456 */         if (entityName != null) {
/*  457 */           nextChar(';', "F-020", entityName);
/*      */ 
/*      */ 
/*      */           
/*  461 */           if (isEntityValue) {
/*  462 */             this.strTmp.append('&');
/*  463 */             this.strTmp.append(entityName);
/*  464 */             this.strTmp.append(';');
/*      */             continue;
/*      */           } 
/*  467 */           expandEntityInLiteral(entityName, this.entities, isEntityValue);
/*      */           continue;
/*      */         } 
/*  470 */         if ((c = getc()) == '#') {
/*  471 */           int tmp = parseCharNumber();
/*      */           
/*  473 */           if (tmp > 65535) {
/*  474 */             tmp = surrogatesToCharTmp(tmp);
/*  475 */             this.strTmp.append(this.charTmp[0]);
/*  476 */             if (tmp == 2)
/*  477 */               this.strTmp.append(this.charTmp[1]);  continue;
/*      */           } 
/*  479 */           this.strTmp.append((char)tmp); continue;
/*      */         } 
/*  481 */         fatal("P-009");
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  487 */       if (c == '%' && isEntityValue) {
/*  488 */         String entityName = maybeGetName();
/*      */         
/*  490 */         if (entityName != null) {
/*  491 */           nextChar(';', "F-021", entityName);
/*  492 */           if (this.inExternalPE) {
/*  493 */             expandEntityInLiteral(entityName, this.params, isEntityValue);
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  498 */           fatal("P-010", new Object[] { entityName });
/*      */           continue;
/*      */         } 
/*  501 */         fatal("P-011");
/*      */       } 
/*      */ 
/*      */       
/*  505 */       if (!isEntityValue) {
/*      */         
/*  507 */         if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
/*  508 */           this.strTmp.append(' ');
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  513 */         if (c == '<') {
/*  514 */           fatal("P-012");
/*      */         }
/*      */       } 
/*  517 */       this.strTmp.append(c);
/*      */     } 
/*      */     
/*  520 */     this.isInAttribute = false;
/*  521 */     this.doLexicalPE = savedLexicalPE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void expandEntityInLiteral(String name, SimpleHashtable table, boolean isEntityValue) throws ParseException, IOException {
/*  530 */     Object entity = table.get(name);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  537 */     if (entity instanceof InternalEntity) {
/*  538 */       InternalEntity value = (InternalEntity)entity;
/*  539 */       pushReader(value.buf, name, !value.isPE);
/*      */     }
/*  541 */     else if (entity instanceof ExternalEntity) {
/*  542 */       if (!isEntityValue) {
/*  543 */         fatal("P-013", new Object[] { name });
/*      */       }
/*  545 */       pushReader((ExternalEntity)entity);
/*      */     }
/*  547 */     else if (entity == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  553 */       fatal((table == this.params) ? "V-022" : "P-014", new Object[] { name });
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
/*  568 */     char quote = this.in.getc();
/*      */     
/*  570 */     if (quote != '\'' && quote != '"') {
/*  571 */       fatal("P-015", new Object[] { messages.getMessage(this.locale, type, new Object[] { extra }) });
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
/*  582 */     this.strTmp = new StringBuffer(); char c;
/*  583 */     while ((c = this.in.getc()) != quote)
/*  584 */       this.strTmp.append(c); 
/*  585 */     return this.strTmp.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String parsePublicId() throws IOException, ParseException {
/*  591 */     String retval = getQuotedString("F-033", null);
/*  592 */     for (int i = 0; i < retval.length(); i++) {
/*  593 */       char c = retval.charAt(i);
/*  594 */       if (" \r\n-'()+,./:=?;!*#@$_%0123456789".indexOf(c) == -1 && (c < 'A' || c > 'Z') && (c < 'a' || c > 'z'))
/*      */       {
/*      */         
/*  597 */         fatal("P-016", new Object[] { new Character(c) }); } 
/*      */     } 
/*  599 */     this.strTmp = new StringBuffer();
/*  600 */     this.strTmp.append(retval);
/*  601 */     return normalize(false);
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
/*  612 */     if (!this.in.peek(skipStart ? "!--" : "<!--", null)) {
/*  613 */       return false;
/*      */     }
/*  615 */     boolean savedLexicalPE = this.doLexicalPE;
/*      */ 
/*      */     
/*  618 */     this.doLexicalPE = false;
/*  619 */     boolean saveCommentText = false;
/*  620 */     if (saveCommentText) {
/*  621 */       this.strTmp = new StringBuffer();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*      */       try {
/*  628 */         int c = getc();
/*  629 */         if (c == 45) {
/*  630 */           c = getc();
/*  631 */           if (c != 45) {
/*  632 */             if (saveCommentText)
/*  633 */               this.strTmp.append('-'); 
/*  634 */             ungetc();
/*      */             continue;
/*      */           } 
/*  637 */           nextChar('>', "F-022", null);
/*      */           break;
/*      */         } 
/*  640 */         if (saveCommentText) {
/*  641 */           this.strTmp.append((char)c);
/*      */         }
/*  643 */       } catch (EndOfInputException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  650 */         if (this.inExternalPE || (!this.donePrologue && this.in.isInternal())) {
/*  651 */           this.in = this.in.pop();
/*      */           continue;
/*      */         } 
/*  654 */         fatal("P-017");
/*      */       } 
/*      */     } 
/*  657 */     this.doLexicalPE = savedLexicalPE;
/*  658 */     return true;
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
/*  671 */     if (!peek("<?xml")) {
/*      */       return;
/*      */     }
/*  674 */     readVersion(true, "1.0");
/*  675 */     readEncoding(false);
/*  676 */     readStandalone();
/*  677 */     maybeWhitespace();
/*  678 */     if (!peek("?>")) {
/*  679 */       char c = getc();
/*  680 */       fatal("P-023", new Object[] { Integer.toHexString(c), new Character(c) });
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
/*  693 */     if (!maybeWhitespace()) {
/*  694 */       if (!must)
/*  695 */         return null; 
/*  696 */       fatal("P-024", new Object[] { name });
/*      */     } 
/*      */ 
/*      */     
/*  700 */     if (!peek(name)) {
/*  701 */       if (must) {
/*  702 */         fatal("P-024", new Object[] { name });
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  707 */         ungetc();
/*  708 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*  712 */     maybeWhitespace();
/*  713 */     nextChar('=', "F-023", null);
/*  714 */     maybeWhitespace();
/*      */     
/*  716 */     return getQuotedString("F-035", name);
/*      */   }
/*      */ 
/*      */   
/*      */   private void readVersion(boolean must, String versionNum) throws IOException, ParseException {
/*  721 */     String value = maybeReadAttribute("version", must);
/*      */ 
/*      */ 
/*      */     
/*  725 */     if (must && value == null)
/*  726 */       fatal("P-025", new Object[] { versionNum }); 
/*  727 */     if (value != null) {
/*  728 */       int length = value.length();
/*  729 */       for (int i = 0; i < length; i++) {
/*  730 */         char c = value.charAt(i);
/*  731 */         if ((c < '0' || c > '9') && c != '_' && c != '.' && (c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && c != ':' && c != '-')
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  738 */           fatal("P-026", new Object[] { value }); } 
/*      */       } 
/*      */     } 
/*  741 */     if (value != null && !value.equals(versionNum)) {
/*  742 */       error("P-027", new Object[] { versionNum, value });
/*      */     }
/*      */   }
/*      */   
/*      */   private void maybeMisc(boolean eofOK) throws IOException, ParseException {
/*  747 */     while (!eofOK || !this.in.isEOF()) {
/*      */       
/*  749 */       if (maybeComment(false) || maybePI(false) || maybeWhitespace());
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
/*  762 */     whitespace(roleId);
/*  763 */     String name = maybeGetName();
/*  764 */     if (name == null)
/*  765 */       fatal("P-005", new Object[] { messages.getMessage(this.locale, roleId) }); 
/*  766 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeDoctypeDecl() throws IOException, ParseException {
/*  774 */     if (!peek("<!DOCTYPE")) {
/*  775 */       return false;
/*      */     }
/*  777 */     ExternalEntity externalSubset = null;
/*      */     
/*  779 */     this.rootElementName = getMarkupDeclname("F-014", true);
/*  780 */     if (maybeWhitespace() && (externalSubset = maybeExternalID()) != null)
/*      */     {
/*  782 */       maybeWhitespace();
/*      */     }
/*  784 */     if (this.in.peekc('[')) {
/*  785 */       this.in.startRemembering();
/*      */       
/*      */       while (true) {
/*  788 */         if (this.in.isEOF() && !this.in.isDocument()) {
/*  789 */           this.in = this.in.pop();
/*      */           continue;
/*      */         } 
/*  792 */         if (maybeMarkupDecl() || maybePEReference() || maybeWhitespace()) {
/*      */           continue;
/*      */         }
/*      */         
/*  796 */         if (peek("<![")) {
/*  797 */           fatal("P-028"); continue;
/*      */         } 
/*      */         break;
/*      */       } 
/*  801 */       nextChar(']', "F-024", null);
/*  802 */       maybeWhitespace();
/*      */     } 
/*      */     
/*  805 */     nextChar('>', "F-025", null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  812 */     if (externalSubset != null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  819 */     this.params.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  824 */     List<String> v = new ArrayList();
/*      */     
/*  826 */     for (Iterator<String> e = this.notations.keySet().iterator(); e.hasNext(); ) {
/*  827 */       String name = e.next();
/*  828 */       Object value = this.notations.get(name);
/*      */       
/*  830 */       if (value == Boolean.TRUE) {
/*  831 */         v.add(name); continue;
/*  832 */       }  if (value instanceof String) {
/*  833 */         v.add(name);
/*      */       }
/*      */     } 
/*  836 */     while (!v.isEmpty()) {
/*  837 */       Object name = v.get(0);
/*  838 */       v.remove(name);
/*  839 */       this.notations.remove(name);
/*      */     } 
/*      */     
/*  842 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeMarkupDecl() throws IOException, ParseException {
/*  848 */     return (maybeElementDecl() || maybeAttlistDecl() || maybeEntityDecl() || maybeNotationDecl() || maybePI(false) || maybeComment(false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readStandalone() throws IOException, ParseException {
/*  857 */     String value = maybeReadAttribute("standalone", false);
/*      */ 
/*      */     
/*  860 */     if (value == null || "no".equals(value))
/*      */       return; 
/*  862 */     if ("yes".equals(value)) {
/*  863 */       this.isStandalone = true;
/*      */       return;
/*      */     } 
/*  866 */     fatal("P-029", new Object[] { value });
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
/*  885 */     if (value.length() < 2)
/*  886 */       return false; 
/*  887 */     char c = value.charAt(1);
/*  888 */     if (c == '-') {
/*  889 */       c = value.charAt(0);
/*  890 */       if (c != 'i' && c != 'I' && c != 'x' && c != 'X')
/*  891 */         return false; 
/*  892 */       nextSuffix = 1;
/*  893 */     } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
/*      */       
/*  895 */       c = value.charAt(0);
/*  896 */       if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
/*  897 */         return false; 
/*  898 */       nextSuffix = 2;
/*      */     } else {
/*  900 */       return false;
/*      */     } 
/*      */     
/*  903 */     while (nextSuffix < value.length()) {
/*  904 */       c = value.charAt(nextSuffix);
/*  905 */       if (c != '-')
/*      */         break; 
/*  907 */       while (++nextSuffix < value.length()) {
/*  908 */         c = value.charAt(nextSuffix);
/*  909 */         if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
/*      */           break; 
/*      */       } 
/*      */     } 
/*  913 */     return (value.length() == nextSuffix && c != '-');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean defaultAttributes(AttributesExImpl attributes, ElementDecl element) throws ParseException {
/*  920 */     boolean didDefault = false;
/*      */ 
/*      */ 
/*      */     
/*  924 */     for (Iterator<String> e = element.attributes.keys(); e.hasNext(); ) {
/*  925 */       String key = e.next();
/*  926 */       String value = attributes.getValue(key);
/*      */ 
/*      */       
/*  929 */       if (value != null) {
/*      */         continue;
/*      */       }
/*  932 */       AttributeDecl info = (AttributeDecl)element.attributes.get(key);
/*  933 */       if (info.defaultValue != null) {
/*  934 */         attributes.addAttribute("", key, key, info.type, info.defaultValue, info.defaultValue, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  942 */         didDefault = true;
/*      */       } 
/*      */     } 
/*  945 */     return didDefault;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeElementDecl() throws IOException, ParseException {
/*  951 */     InputEntity start = peekDeclaration("!ELEMENT");
/*      */     
/*  953 */     if (start == null) {
/*  954 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  958 */     String name = getMarkupDeclname("F-015", true);
/*  959 */     ElementDecl element = (ElementDecl)this.elements.get(name);
/*  960 */     boolean declEffective = false;
/*      */     
/*  962 */     if (element != null) {
/*  963 */       if (element.contentType != null)
/*      */       {
/*  965 */         element = new ElementDecl(name);
/*      */       }
/*      */     } else {
/*  968 */       element = new ElementDecl(name);
/*  969 */       if (!this.ignoreDeclarations) {
/*  970 */         this.elements.put(element.name, element);
/*  971 */         declEffective = true;
/*      */       } 
/*      */     } 
/*  974 */     element.isFromInternalSubset = !this.inExternalPE;
/*      */     
/*  976 */     whitespace("F-000");
/*  977 */     if (peek("EMPTY")) {
/*  978 */       element.contentType = "EMPTY";
/*  979 */       element.ignoreWhitespace = true;
/*  980 */     } else if (peek("ANY")) {
/*  981 */       element.contentType = "ANY";
/*  982 */       element.ignoreWhitespace = false;
/*      */     } else {
/*  984 */       element.contentType = getMixedOrChildren(element);
/*      */     } 
/*  986 */     maybeWhitespace();
/*  987 */     char c = getc();
/*  988 */     if (c != '>') {
/*  989 */       fatal("P-036", new Object[] { name, new Character(c) });
/*      */     }
/*  991 */     return true;
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
/* 1004 */     this.strTmp = new StringBuffer();
/*      */     
/* 1006 */     nextChar('(', "F-028", element.name);
/* 1007 */     InputEntity start = this.in;
/* 1008 */     maybeWhitespace();
/* 1009 */     this.strTmp.append('(');
/*      */     
/* 1011 */     if (peek("#PCDATA")) {
/* 1012 */       this.strTmp.append("#PCDATA");
/* 1013 */       getMixed(element.name, start);
/* 1014 */       element.ignoreWhitespace = false;
/*      */     } else {
/* 1016 */       element.model = getcps(element.name, start);
/* 1017 */       element.ignoreWhitespace = true;
/*      */     } 
/* 1019 */     return this.strTmp.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ContentModel getcps(String element, InputEntity start) throws IOException, ParseException {
/* 1029 */     boolean decided = false;
/* 1030 */     char type = Character.MIN_VALUE;
/*      */ 
/*      */     
/* 1033 */     ContentModel temp = null, current = temp, retval = current;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1038 */       String tag = maybeGetName();
/* 1039 */       if (tag != null) {
/* 1040 */         this.strTmp.append(tag);
/* 1041 */         temp = getFrequency(null);
/* 1042 */       } else if (peek("(")) {
/* 1043 */         InputEntity next = this.in;
/* 1044 */         this.strTmp.append('(');
/* 1045 */         maybeWhitespace();
/* 1046 */         temp = getFrequency(getcps(element, next));
/*      */       } else {
/* 1048 */         fatal((type == '\000') ? "P-039" : ((type == ',') ? "P-037" : "P-038"), new Object[] { new Character(getc()) });
/*      */       } 
/*      */ 
/*      */       
/* 1052 */       maybeWhitespace();
/* 1053 */       if (decided)
/* 1054 */       { char c = getc();
/*      */         
/* 1056 */         if (current != null) {
/* 1057 */           current.next = null;
/* 1058 */           current = current.next;
/*      */         } 
/* 1060 */         if (c == type)
/* 1061 */         { this.strTmp.append(type);
/* 1062 */           maybeWhitespace(); }
/*      */         
/* 1064 */         else if (c == ')')
/* 1065 */         { ungetc(); }
/*      */         else
/*      */         
/* 1068 */         { fatal((type == '\000') ? "P-041" : "P-040", new Object[] { new Character(c), new Character(type) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1084 */           maybeWhitespace(); }  } else { type = getc(); if (type == '|' || type == ',') { decided = true; retval = current = null; }
/* 1085 */         else { retval = current = temp; ungetc(); if (peek(")"))
/* 1086 */           { this.strTmp.append(')');
/* 1087 */             return getFrequency(retval); }  }  this.strTmp.append(type); maybeWhitespace(); }  if (peek(")")) { this.strTmp.append(')'); return getFrequency(retval); }
/*      */     
/*      */     } 
/*      */   }
/*      */   private ContentModel getFrequency(ContentModel original) throws IOException, ParseException {
/* 1092 */     char c = getc();
/*      */     
/* 1094 */     if (c == '?' || c == '+' || c == '*') {
/* 1095 */       this.strTmp.append(c);
/* 1096 */       if (original == null)
/* 1097 */         return null; 
/* 1098 */       if (original.type == '\000') {
/* 1099 */         original.type = c;
/* 1100 */         return original;
/*      */       } 
/* 1102 */       return null;
/*      */     } 
/* 1104 */     ungetc();
/* 1105 */     return original;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getMixed(String element, InputEntity start) throws IOException, ParseException {
/* 1115 */     maybeWhitespace();
/* 1116 */     if (peek(")*") || peek(")")) {
/* 1117 */       this.strTmp.append(')');
/*      */       
/*      */       return;
/*      */     } 
/* 1121 */     while (peek("|")) {
/*      */ 
/*      */       
/* 1124 */       this.strTmp.append('|');
/* 1125 */       maybeWhitespace();
/*      */       
/* 1127 */       String name = maybeGetName();
/* 1128 */       if (name == null) {
/* 1129 */         fatal("P-042", new Object[] { element, Integer.toHexString(getc()) });
/*      */       }
/*      */       
/* 1132 */       this.strTmp.append(name);
/* 1133 */       maybeWhitespace();
/*      */     } 
/*      */     
/* 1136 */     if (!peek(")*"))
/* 1137 */       fatal("P-043", new Object[] { element, new Character(getc()) }); 
/* 1138 */     this.strTmp.append(')');
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
/*      */     //   74: ifne -> 866
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
/*      */     //   159: goto -> 626
/*      */     //   162: aload_0
/*      */     //   163: ldc 'IDREFS'
/*      */     //   165: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   168: ifeq -> 181
/*      */     //   171: aload #4
/*      */     //   173: ldc 'IDREFS'
/*      */     //   175: putfield type : Ljava/lang/String;
/*      */     //   178: goto -> 626
/*      */     //   181: aload_0
/*      */     //   182: ldc 'IDREF'
/*      */     //   184: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   187: ifeq -> 200
/*      */     //   190: aload #4
/*      */     //   192: ldc 'IDREF'
/*      */     //   194: putfield type : Ljava/lang/String;
/*      */     //   197: goto -> 626
/*      */     //   200: aload_0
/*      */     //   201: ldc 'ID'
/*      */     //   203: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   206: ifeq -> 231
/*      */     //   209: aload #4
/*      */     //   211: ldc 'ID'
/*      */     //   213: putfield type : Ljava/lang/String;
/*      */     //   216: aload_3
/*      */     //   217: getfield id : Ljava/lang/String;
/*      */     //   220: ifnonnull -> 626
/*      */     //   223: aload_3
/*      */     //   224: aload_2
/*      */     //   225: putfield id : Ljava/lang/String;
/*      */     //   228: goto -> 626
/*      */     //   231: aload_0
/*      */     //   232: ldc 'ENTITY'
/*      */     //   234: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   237: ifeq -> 250
/*      */     //   240: aload #4
/*      */     //   242: ldc 'ENTITY'
/*      */     //   244: putfield type : Ljava/lang/String;
/*      */     //   247: goto -> 626
/*      */     //   250: aload_0
/*      */     //   251: ldc 'ENTITIES'
/*      */     //   253: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   256: ifeq -> 269
/*      */     //   259: aload #4
/*      */     //   261: ldc 'ENTITIES'
/*      */     //   263: putfield type : Ljava/lang/String;
/*      */     //   266: goto -> 626
/*      */     //   269: aload_0
/*      */     //   270: ldc 'NMTOKENS'
/*      */     //   272: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   275: ifeq -> 288
/*      */     //   278: aload #4
/*      */     //   280: ldc 'NMTOKENS'
/*      */     //   282: putfield type : Ljava/lang/String;
/*      */     //   285: goto -> 626
/*      */     //   288: aload_0
/*      */     //   289: ldc 'NMTOKEN'
/*      */     //   291: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   294: ifeq -> 307
/*      */     //   297: aload #4
/*      */     //   299: ldc 'NMTOKEN'
/*      */     //   301: putfield type : Ljava/lang/String;
/*      */     //   304: goto -> 626
/*      */     //   307: aload_0
/*      */     //   308: ldc 'NOTATION'
/*      */     //   310: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   313: ifeq -> 465
/*      */     //   316: aload #4
/*      */     //   318: ldc 'NOTATION'
/*      */     //   320: putfield type : Ljava/lang/String;
/*      */     //   323: aload_0
/*      */     //   324: ldc 'F-002'
/*      */     //   326: invokespecial whitespace : (Ljava/lang/String;)V
/*      */     //   329: aload_0
/*      */     //   330: bipush #40
/*      */     //   332: ldc_w 'F-029'
/*      */     //   335: aconst_null
/*      */     //   336: invokespecial nextChar : (CLjava/lang/String;Ljava/lang/String;)V
/*      */     //   339: aload_0
/*      */     //   340: invokespecial maybeWhitespace : ()Z
/*      */     //   343: pop
/*      */     //   344: new java/util/ArrayList
/*      */     //   347: dup
/*      */     //   348: invokespecial <init> : ()V
/*      */     //   351: astore #5
/*      */     //   353: aload_0
/*      */     //   354: invokespecial maybeGetName : ()Ljava/lang/String;
/*      */     //   357: dup
/*      */     //   358: astore_2
/*      */     //   359: ifnonnull -> 369
/*      */     //   362: aload_0
/*      */     //   363: ldc_w 'P-068'
/*      */     //   366: invokespecial fatal : (Ljava/lang/String;)V
/*      */     //   369: aload #5
/*      */     //   371: aload_2
/*      */     //   372: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   377: pop
/*      */     //   378: aload_0
/*      */     //   379: invokespecial maybeWhitespace : ()Z
/*      */     //   382: pop
/*      */     //   383: aload_0
/*      */     //   384: ldc '|'
/*      */     //   386: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   389: ifeq -> 397
/*      */     //   392: aload_0
/*      */     //   393: invokespecial maybeWhitespace : ()Z
/*      */     //   396: pop
/*      */     //   397: aload_0
/*      */     //   398: ldc ')'
/*      */     //   400: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   403: ifeq -> 353
/*      */     //   406: aload #4
/*      */     //   408: aload #5
/*      */     //   410: invokeinterface size : ()I
/*      */     //   415: anewarray java/lang/String
/*      */     //   418: putfield values : [Ljava/lang/String;
/*      */     //   421: iconst_0
/*      */     //   422: istore #6
/*      */     //   424: iload #6
/*      */     //   426: aload #5
/*      */     //   428: invokeinterface size : ()I
/*      */     //   433: if_icmpge -> 462
/*      */     //   436: aload #4
/*      */     //   438: getfield values : [Ljava/lang/String;
/*      */     //   441: iload #6
/*      */     //   443: aload #5
/*      */     //   445: iload #6
/*      */     //   447: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   452: checkcast java/lang/String
/*      */     //   455: aastore
/*      */     //   456: iinc #6, 1
/*      */     //   459: goto -> 424
/*      */     //   462: goto -> 626
/*      */     //   465: aload_0
/*      */     //   466: ldc '('
/*      */     //   468: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   471: ifeq -> 597
/*      */     //   474: aload #4
/*      */     //   476: ldc_w 'ENUMERATION'
/*      */     //   479: putfield type : Ljava/lang/String;
/*      */     //   482: aload_0
/*      */     //   483: invokespecial maybeWhitespace : ()Z
/*      */     //   486: pop
/*      */     //   487: new java/util/ArrayList
/*      */     //   490: dup
/*      */     //   491: invokespecial <init> : ()V
/*      */     //   494: astore #5
/*      */     //   496: aload_0
/*      */     //   497: invokespecial getNmtoken : ()Ljava/lang/String;
/*      */     //   500: astore_2
/*      */     //   501: aload #5
/*      */     //   503: aload_2
/*      */     //   504: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   509: pop
/*      */     //   510: aload_0
/*      */     //   511: invokespecial maybeWhitespace : ()Z
/*      */     //   514: pop
/*      */     //   515: aload_0
/*      */     //   516: ldc '|'
/*      */     //   518: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   521: ifeq -> 529
/*      */     //   524: aload_0
/*      */     //   525: invokespecial maybeWhitespace : ()Z
/*      */     //   528: pop
/*      */     //   529: aload_0
/*      */     //   530: ldc ')'
/*      */     //   532: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   535: ifeq -> 496
/*      */     //   538: aload #4
/*      */     //   540: aload #5
/*      */     //   542: invokeinterface size : ()I
/*      */     //   547: anewarray java/lang/String
/*      */     //   550: putfield values : [Ljava/lang/String;
/*      */     //   553: iconst_0
/*      */     //   554: istore #6
/*      */     //   556: iload #6
/*      */     //   558: aload #5
/*      */     //   560: invokeinterface size : ()I
/*      */     //   565: if_icmpge -> 594
/*      */     //   568: aload #4
/*      */     //   570: getfield values : [Ljava/lang/String;
/*      */     //   573: iload #6
/*      */     //   575: aload #5
/*      */     //   577: iload #6
/*      */     //   579: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   584: checkcast java/lang/String
/*      */     //   587: aastore
/*      */     //   588: iinc #6, 1
/*      */     //   591: goto -> 556
/*      */     //   594: goto -> 626
/*      */     //   597: aload_0
/*      */     //   598: ldc_w 'P-045'
/*      */     //   601: iconst_2
/*      */     //   602: anewarray java/lang/Object
/*      */     //   605: dup
/*      */     //   606: iconst_0
/*      */     //   607: aload_2
/*      */     //   608: aastore
/*      */     //   609: dup
/*      */     //   610: iconst_1
/*      */     //   611: new java/lang/Character
/*      */     //   614: dup
/*      */     //   615: aload_0
/*      */     //   616: invokespecial getc : ()C
/*      */     //   619: invokespecial <init> : (C)V
/*      */     //   622: aastore
/*      */     //   623: invokespecial fatal : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   626: aload_0
/*      */     //   627: ldc_w 'F-003'
/*      */     //   630: invokespecial whitespace : (Ljava/lang/String;)V
/*      */     //   633: aload_0
/*      */     //   634: ldc_w '#REQUIRED'
/*      */     //   637: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   640: ifeq -> 652
/*      */     //   643: aload #4
/*      */     //   645: iconst_1
/*      */     //   646: putfield isRequired : Z
/*      */     //   649: goto -> 768
/*      */     //   652: aload_0
/*      */     //   653: ldc_w '#FIXED'
/*      */     //   656: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   659: ifeq -> 718
/*      */     //   662: aload #4
/*      */     //   664: iconst_1
/*      */     //   665: putfield isFixed : Z
/*      */     //   668: aload_0
/*      */     //   669: ldc_w 'F-004'
/*      */     //   672: invokespecial whitespace : (Ljava/lang/String;)V
/*      */     //   675: aload_0
/*      */     //   676: iconst_0
/*      */     //   677: invokespecial parseLiteral : (Z)V
/*      */     //   680: aload #4
/*      */     //   682: getfield type : Ljava/lang/String;
/*      */     //   685: ldc 'CDATA'
/*      */     //   687: if_acmpeq -> 703
/*      */     //   690: aload #4
/*      */     //   692: aload_0
/*      */     //   693: iconst_0
/*      */     //   694: invokespecial normalize : (Z)Ljava/lang/String;
/*      */     //   697: putfield defaultValue : Ljava/lang/String;
/*      */     //   700: goto -> 768
/*      */     //   703: aload #4
/*      */     //   705: aload_0
/*      */     //   706: getfield strTmp : Ljava/lang/StringBuffer;
/*      */     //   709: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   712: putfield defaultValue : Ljava/lang/String;
/*      */     //   715: goto -> 768
/*      */     //   718: aload_0
/*      */     //   719: ldc_w '#IMPLIED'
/*      */     //   722: invokespecial peek : (Ljava/lang/String;)Z
/*      */     //   725: ifne -> 768
/*      */     //   728: aload_0
/*      */     //   729: iconst_0
/*      */     //   730: invokespecial parseLiteral : (Z)V
/*      */     //   733: aload #4
/*      */     //   735: getfield type : Ljava/lang/String;
/*      */     //   738: ldc 'CDATA'
/*      */     //   740: if_acmpeq -> 756
/*      */     //   743: aload #4
/*      */     //   745: aload_0
/*      */     //   746: iconst_0
/*      */     //   747: invokespecial normalize : (Z)Ljava/lang/String;
/*      */     //   750: putfield defaultValue : Ljava/lang/String;
/*      */     //   753: goto -> 768
/*      */     //   756: aload #4
/*      */     //   758: aload_0
/*      */     //   759: getfield strTmp : Ljava/lang/StringBuffer;
/*      */     //   762: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   765: putfield defaultValue : Ljava/lang/String;
/*      */     //   768: ldc_w 'xml:lang'
/*      */     //   771: aload #4
/*      */     //   773: getfield name : Ljava/lang/String;
/*      */     //   776: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   779: ifeq -> 821
/*      */     //   782: aload #4
/*      */     //   784: getfield defaultValue : Ljava/lang/String;
/*      */     //   787: ifnull -> 821
/*      */     //   790: aload_0
/*      */     //   791: aload #4
/*      */     //   793: getfield defaultValue : Ljava/lang/String;
/*      */     //   796: invokespecial isXmlLang : (Ljava/lang/String;)Z
/*      */     //   799: ifne -> 821
/*      */     //   802: aload_0
/*      */     //   803: ldc_w 'P-033'
/*      */     //   806: iconst_1
/*      */     //   807: anewarray java/lang/Object
/*      */     //   810: dup
/*      */     //   811: iconst_0
/*      */     //   812: aload #4
/*      */     //   814: getfield defaultValue : Ljava/lang/String;
/*      */     //   817: aastore
/*      */     //   818: invokevirtual error : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   821: aload_0
/*      */     //   822: getfield ignoreDeclarations : Z
/*      */     //   825: ifne -> 858
/*      */     //   828: aload_3
/*      */     //   829: getfield attributes : Lcom/sun/xml/rpc/sp/SimpleHashtable;
/*      */     //   832: aload #4
/*      */     //   834: getfield name : Ljava/lang/String;
/*      */     //   837: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   840: ifnonnull -> 858
/*      */     //   843: aload_3
/*      */     //   844: getfield attributes : Lcom/sun/xml/rpc/sp/SimpleHashtable;
/*      */     //   847: aload #4
/*      */     //   849: getfield name : Ljava/lang/String;
/*      */     //   852: aload #4
/*      */     //   854: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   857: pop
/*      */     //   858: aload_0
/*      */     //   859: invokespecial maybeWhitespace : ()Z
/*      */     //   862: pop
/*      */     //   863: goto -> 68
/*      */     //   866: iconst_1
/*      */     //   867: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1143	-> 0
/*      */     //   #1145	-> 7
/*      */     //   #1146	-> 11
/*      */     //   #1148	-> 13
/*      */     //   #1149	-> 21
/*      */     //   #1151	-> 33
/*      */     //   #1153	-> 37
/*      */     //   #1154	-> 46
/*      */     //   #1155	-> 53
/*      */     //   #1158	-> 63
/*      */     //   #1159	-> 68
/*      */     //   #1163	-> 77
/*      */     //   #1164	-> 82
/*      */     //   #1165	-> 86
/*      */     //   #1166	-> 110
/*      */     //   #1168	-> 116
/*      */     //   #1169	-> 126
/*      */     //   #1175	-> 143
/*      */     //   #1176	-> 152
/*      */     //   #1183	-> 162
/*      */     //   #1184	-> 171
/*      */     //   #1185	-> 181
/*      */     //   #1186	-> 190
/*      */     //   #1187	-> 200
/*      */     //   #1188	-> 209
/*      */     //   #1189	-> 216
/*      */     //   #1190	-> 223
/*      */     //   #1191	-> 231
/*      */     //   #1192	-> 240
/*      */     //   #1193	-> 250
/*      */     //   #1194	-> 259
/*      */     //   #1195	-> 269
/*      */     //   #1196	-> 278
/*      */     //   #1197	-> 288
/*      */     //   #1198	-> 297
/*      */     //   #1203	-> 307
/*      */     //   #1204	-> 316
/*      */     //   #1205	-> 323
/*      */     //   #1206	-> 329
/*      */     //   #1207	-> 339
/*      */     //   #1209	-> 344
/*      */     //   #1211	-> 353
/*      */     //   #1212	-> 362
/*      */     //   #1214	-> 369
/*      */     //   #1215	-> 378
/*      */     //   #1216	-> 383
/*      */     //   #1217	-> 392
/*      */     //   #1218	-> 397
/*      */     //   #1219	-> 406
/*      */     //   #1220	-> 421
/*      */     //   #1221	-> 436
/*      */     //   #1220	-> 456
/*      */     //   #1224	-> 462
/*      */     //   #1225	-> 474
/*      */     //   #1226	-> 482
/*      */     //   #1228	-> 487
/*      */     //   #1230	-> 496
/*      */     //   #1231	-> 501
/*      */     //   #1232	-> 510
/*      */     //   #1233	-> 515
/*      */     //   #1234	-> 524
/*      */     //   #1235	-> 529
/*      */     //   #1236	-> 538
/*      */     //   #1237	-> 553
/*      */     //   #1238	-> 568
/*      */     //   #1237	-> 588
/*      */     //   #1239	-> 594
/*      */     //   #1240	-> 597
/*      */     //   #1244	-> 626
/*      */     //   #1245	-> 633
/*      */     //   #1246	-> 643
/*      */     //   #1247	-> 652
/*      */     //   #1248	-> 662
/*      */     //   #1249	-> 668
/*      */     //   #1250	-> 675
/*      */     //   #1251	-> 680
/*      */     //   #1252	-> 690
/*      */     //   #1254	-> 703
/*      */     //   #1255	-> 715
/*      */     //   #1256	-> 728
/*      */     //   #1257	-> 733
/*      */     //   #1258	-> 743
/*      */     //   #1260	-> 756
/*      */     //   #1263	-> 768
/*      */     //   #1266	-> 802
/*      */     //   #1268	-> 821
/*      */     //   #1270	-> 843
/*      */     //   #1272	-> 858
/*      */     //   #1273	-> 863
/*      */     //   #1274	-> 866
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   424	38	6	i	I
/*      */     //   353	109	5	v	Ljava/util/List;
/*      */     //   556	38	6	i	I
/*      */     //   496	98	5	v	Ljava/util/List;
/*      */     //   126	737	4	a	Lcom/sun/xml/rpc/sp/AttributeDecl;
/*      */     //   0	868	0	this	Lcom/sun/xml/rpc/sp/Parser;
/*      */     //   7	861	1	start	Lcom/sun/xml/rpc/sp/InputEntity;
/*      */     //   21	847	2	name	Ljava/lang/String;
/*      */     //   33	835	3	element	Lcom/sun/xml/rpc/sp/ElementDecl;
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
/* 1284 */     String s = this.strTmp.toString();
/* 1285 */     String s2 = s.trim();
/* 1286 */     boolean didStrip = false;
/*      */     
/* 1288 */     if (s != s2) {
/* 1289 */       s = s2;
/* 1290 */       s2 = null;
/* 1291 */       didStrip = true;
/*      */     } 
/* 1293 */     this.strTmp = new StringBuffer();
/* 1294 */     for (int i = 0; i < s.length(); i++) {
/* 1295 */       char c = s.charAt(i);
/* 1296 */       if (!XmlChars.isSpace(c)) {
/* 1297 */         this.strTmp.append(c);
/*      */       } else {
/*      */         
/* 1300 */         this.strTmp.append(' ');
/* 1301 */         while (++i < s.length() && XmlChars.isSpace(s.charAt(i)))
/* 1302 */           didStrip = true; 
/* 1303 */         i--;
/*      */       } 
/* 1305 */     }  if (didStrip) {
/* 1306 */       return this.strTmp.toString();
/*      */     }
/* 1308 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeConditionalSect() throws IOException, ParseException {
/* 1314 */     if (!peek("<![")) {
/* 1315 */       return false;
/*      */     }
/*      */     
/* 1318 */     InputEntity start = this.in;
/*      */     
/* 1320 */     maybeWhitespace();
/*      */     String keyword;
/* 1322 */     if ((keyword = maybeGetName()) == null)
/* 1323 */       fatal("P-046"); 
/* 1324 */     maybeWhitespace();
/* 1325 */     nextChar('[', "F-030", null);
/*      */ 
/*      */ 
/*      */     
/* 1329 */     if ("INCLUDE".equals(keyword)) {
/*      */       while (true) {
/* 1331 */         if (this.in.isEOF() && this.in != start) {
/* 1332 */           this.in = this.in.pop(); continue;
/* 1333 */         }  if (this.in.isEOF()) {
/* 1334 */           this.in = this.in.pop();
/*      */         }
/* 1336 */         if (peek("]]>")) {
/*      */           break;
/*      */         }
/* 1339 */         this.doLexicalPE = false;
/* 1340 */         if (maybeWhitespace())
/*      */           continue; 
/* 1342 */         if (maybePEReference())
/*      */           continue; 
/* 1344 */         this.doLexicalPE = true;
/* 1345 */         if (maybeMarkupDecl() || maybeConditionalSect()) {
/*      */           continue;
/*      */         }
/* 1348 */         fatal("P-047");
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1356 */     else if ("IGNORE".equals(keyword)) {
/* 1357 */       int nestlevel = 1;
/*      */       
/* 1359 */       this.doLexicalPE = false;
/* 1360 */       while (nestlevel > 0) {
/* 1361 */         char c = getc();
/* 1362 */         if (c == '<') {
/* 1363 */           if (peek("!["))
/* 1364 */             nestlevel++;  continue;
/* 1365 */         }  if (c == ']' && 
/* 1366 */           peek("]>")) {
/* 1367 */           nestlevel--;
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 1372 */       fatal("P-048", new Object[] { keyword });
/* 1373 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseCharNumber() throws ParseException, IOException {
/* 1379 */     int retval = 0;
/*      */ 
/*      */     
/* 1382 */     if (getc() != 'x') {
/* 1383 */       ungetc();
/*      */       while (true) {
/* 1385 */         char c = getc();
/* 1386 */         if (c >= '0' && c <= '9') {
/* 1387 */           retval *= 10;
/* 1388 */           retval += c - 48;
/*      */           continue;
/*      */         } 
/* 1391 */         if (c == ';')
/* 1392 */           return retval; 
/* 1393 */         fatal("P-049");
/*      */       } 
/*      */     } 
/*      */     while (true) {
/* 1397 */       char c = getc();
/* 1398 */       if (c >= '0' && c <= '9') {
/* 1399 */         retval <<= 4;
/* 1400 */         retval += c - 48;
/*      */         continue;
/*      */       } 
/* 1403 */       if (c >= 'a' && c <= 'f') {
/* 1404 */         retval <<= 4;
/* 1405 */         retval += 10 + c - 97;
/*      */         continue;
/*      */       } 
/* 1408 */       if (c >= 'A' && c <= 'F') {
/* 1409 */         retval <<= 4;
/* 1410 */         retval += 10 + c - 65;
/*      */         continue;
/*      */       } 
/* 1413 */       if (c == ';')
/* 1414 */         return retval; 
/* 1415 */       fatal("P-050");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int surrogatesToCharTmp(int ucs4) throws ParseException {
/* 1422 */     if (ucs4 <= 65535) {
/* 1423 */       if (XmlChars.isChar(ucs4)) {
/* 1424 */         this.charTmp[0] = (char)ucs4;
/* 1425 */         return 1;
/*      */       } 
/* 1427 */     } else if (ucs4 <= 1114111) {
/*      */       
/* 1429 */       ucs4 -= 65536;
/* 1430 */       this.charTmp[0] = (char)(0xD800 | ucs4 >> 10 & 0x3FF);
/* 1431 */       this.charTmp[1] = (char)(0xDC00 | ucs4 & 0x3FF);
/* 1432 */       return 2;
/*      */     } 
/* 1434 */     fatal("P-051", new Object[] { Integer.toHexString(ucs4) });
/*      */     
/* 1436 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybePEReference() throws IOException, ParseException {
/* 1445 */     if (!this.in.peekc('%')) {
/* 1446 */       return false;
/*      */     }
/* 1448 */     String name = maybeGetName();
/*      */ 
/*      */     
/* 1451 */     if (name == null)
/* 1452 */       fatal("P-011"); 
/* 1453 */     nextChar(';', "F-021", name);
/* 1454 */     Object entity = this.params.get(name);
/*      */     
/* 1456 */     if (entity instanceof InternalEntity) {
/* 1457 */       InternalEntity value = (InternalEntity)entity;
/* 1458 */       pushReader(value.buf, name, false);
/*      */     }
/* 1460 */     else if (entity instanceof ExternalEntity) {
/* 1461 */       externalParameterEntity((ExternalEntity)entity);
/*      */     }
/* 1463 */     else if (entity == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1472 */       this.ignoreDeclarations = true;
/* 1473 */       warning("V-022", new Object[] { name });
/*      */     } 
/* 1475 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeEntityDecl() throws IOException, ParseException {
/*      */     SimpleHashtable defns;
/* 1485 */     InputEntity start = peekDeclaration("!ENTITY");
/*      */     
/* 1487 */     if (start == null) {
/* 1488 */       return false;
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
/* 1502 */     this.doLexicalPE = false;
/* 1503 */     whitespace("F-005");
/* 1504 */     if (this.in.peekc('%')) {
/* 1505 */       whitespace("F-006");
/* 1506 */       defns = this.params;
/*      */     } else {
/* 1508 */       defns = this.entities;
/*      */     } 
/* 1510 */     ungetc();
/* 1511 */     this.doLexicalPE = true;
/* 1512 */     String entityName = getMarkupDeclname("F-017", false);
/* 1513 */     whitespace("F-007");
/* 1514 */     ExternalEntity externalId = maybeExternalID();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1523 */     boolean doStore = (defns.get(entityName) == null);
/* 1524 */     if (!doStore && defns == this.entities) {
/* 1525 */       warning("P-054", new Object[] { entityName });
/*      */     }
/*      */ 
/*      */     
/* 1529 */     int i = doStore & (!this.ignoreDeclarations ? 1 : 0);
/*      */ 
/*      */     
/* 1532 */     if (externalId == null) {
/*      */ 
/*      */ 
/*      */       
/* 1536 */       this.doLexicalPE = false;
/* 1537 */       parseLiteral(true);
/* 1538 */       this.doLexicalPE = true;
/* 1539 */       if (i != 0) {
/* 1540 */         char[] value = new char[this.strTmp.length()];
/* 1541 */         if (value.length != 0)
/* 1542 */           this.strTmp.getChars(0, value.length, value, 0); 
/* 1543 */         InternalEntity entity = new InternalEntity(entityName, value);
/* 1544 */         entity.isPE = (defns == this.params);
/* 1545 */         entity.isFromInternalSubset = !this.inExternalPE;
/* 1546 */         defns.put(entityName, entity);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1552 */       if (defns == this.entities && maybeWhitespace() && peek("NDATA")) {
/* 1553 */         externalId.notation = getMarkupDeclname("F-018", false);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1558 */       externalId.name = entityName;
/* 1559 */       externalId.isPE = (defns == this.params);
/* 1560 */       externalId.isFromInternalSubset = !this.inExternalPE;
/* 1561 */       if (i != 0) {
/* 1562 */         defns.put(entityName, externalId);
/*      */       }
/*      */     } 
/* 1565 */     maybeWhitespace();
/* 1566 */     nextChar('>', "F-031", entityName);
/* 1567 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ExternalEntity maybeExternalID() throws IOException, ParseException {
/* 1574 */     String temp = null;
/*      */ 
/*      */     
/* 1577 */     if (peek("PUBLIC")) {
/* 1578 */       whitespace("F-009");
/* 1579 */       temp = parsePublicId();
/* 1580 */     } else if (!peek("SYSTEM")) {
/* 1581 */       return null;
/*      */     } 
/* 1583 */     ExternalEntity retval = new ExternalEntity(this.in);
/* 1584 */     retval.publicId = temp;
/* 1585 */     whitespace("F-008");
/* 1586 */     retval.systemId = parseSystemId();
/* 1587 */     return retval;
/*      */   }
/*      */   
/*      */   private String parseSystemId() throws IOException, ParseException {
/* 1591 */     String uri = getQuotedString("F-034", null);
/* 1592 */     int temp = uri.indexOf(':');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1601 */     if (temp == -1 || uri.indexOf('/') < temp) {
/*      */ 
/*      */       
/* 1604 */       String baseURI = this.in.getSystemId();
/* 1605 */       if (baseURI == null) {
/* 1606 */         baseURI = "NODOCTYPE:///tmp/";
/*      */       }
/* 1608 */       if (uri.length() == 0)
/* 1609 */         uri = "."; 
/* 1610 */       baseURI = baseURI.substring(0, baseURI.lastIndexOf('/') + 1);
/* 1611 */       if (uri.charAt(0) != '/') {
/* 1612 */         uri = baseURI + uri;
/*      */       }
/*      */       else {
/*      */         
/* 1616 */         throw new InternalError();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1623 */     if (uri.indexOf('#') != -1)
/* 1624 */       error("P-056", new Object[] { uri }); 
/* 1625 */     return uri;
/*      */   }
/*      */ 
/*      */   
/*      */   private void maybeTextDecl() throws IOException, ParseException {
/* 1630 */     if (peek("<?xml")) {
/* 1631 */       readVersion(false, "1.0");
/* 1632 */       readEncoding(true);
/* 1633 */       maybeWhitespace();
/* 1634 */       if (!peek("?>")) {
/* 1635 */         fatal("P-057");
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
/* 1650 */     if (this.isStandalone && this.fastStandalone) {
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
/* 1663 */     this.inExternalPE = true;
/*      */ 
/*      */     
/* 1666 */     pushReader(next);
/*      */     
/* 1668 */     InputEntity pe = this.in;
/* 1669 */     maybeTextDecl();
/* 1670 */     while (!pe.isEOF()) {
/*      */       
/* 1672 */       if (this.in.isEOF()) {
/* 1673 */         this.in = this.in.pop();
/*      */         continue;
/*      */       } 
/* 1676 */       this.doLexicalPE = false;
/* 1677 */       if (maybeWhitespace())
/*      */         continue; 
/* 1679 */       if (maybePEReference())
/*      */         continue; 
/* 1681 */       this.doLexicalPE = true;
/* 1682 */       if (maybeMarkupDecl() || maybeConditionalSect());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1687 */     if (!pe.isEOF())
/* 1688 */       fatal("P-059", new Object[] { this.in.getName() }); 
/* 1689 */     this.in = this.in.pop();
/* 1690 */     this.inExternalPE = !this.in.isDocument();
/* 1691 */     this.doLexicalPE = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readEncoding(boolean must) throws IOException, ParseException {
/* 1697 */     String name = maybeReadAttribute("encoding", must);
/*      */     
/* 1699 */     if (name == null)
/*      */       return; 
/* 1701 */     for (int i = 0; i < name.length(); i++) {
/* 1702 */       char c = name.charAt(i);
/* 1703 */       if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z'))
/*      */       {
/* 1705 */         if (i == 0 || ((c < '0' || c > '9') && c != '-' && c != '_' && c != '.'))
/*      */         {
/*      */           
/* 1708 */           fatal("P-060", new Object[] { new Character(c) });
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
/* 1720 */     String currentEncoding = this.in.getEncoding();
/*      */     
/* 1722 */     if (currentEncoding != null && !name.equalsIgnoreCase(currentEncoding)) {
/* 1723 */       warning("P-061", new Object[] { name, currentEncoding });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeNotationDecl() throws IOException, ParseException {
/* 1730 */     InputEntity start = peekDeclaration("!NOTATION");
/*      */     
/* 1732 */     if (start == null) {
/* 1733 */       return false;
/*      */     }
/* 1735 */     String name = getMarkupDeclname("F-019", false);
/* 1736 */     ExternalEntity entity = new ExternalEntity(this.in);
/*      */     
/* 1738 */     whitespace("F-011");
/* 1739 */     if (peek("PUBLIC")) {
/* 1740 */       whitespace("F-009");
/* 1741 */       entity.publicId = parsePublicId();
/* 1742 */       if (maybeWhitespace() && 
/* 1743 */         !peek(">")) {
/* 1744 */         entity.systemId = parseSystemId();
/*      */       }
/* 1746 */     } else if (peek("SYSTEM")) {
/* 1747 */       whitespace("F-008");
/* 1748 */       entity.systemId = parseSystemId();
/*      */     } else {
/* 1750 */       fatal("P-062");
/* 1751 */     }  maybeWhitespace();
/* 1752 */     nextChar('>', "F-032", name);
/* 1753 */     if (entity.systemId != null && entity.systemId.indexOf('#') != -1) {
/* 1754 */       error("P-056", new Object[] { entity.systemId });
/*      */     }
/* 1756 */     Object value = this.notations.get(name);
/* 1757 */     if (value != null && value instanceof ExternalEntity) {
/* 1758 */       warning("P-063", new Object[] { name });
/*      */ 
/*      */     
/*      */     }
/* 1762 */     else if (!this.ignoreDeclarations) {
/* 1763 */       this.notations.put(name, entity);
/*      */     } 
/* 1765 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char getc() throws IOException, ParseException {
/* 1775 */     if (!this.inExternalPE || !this.doLexicalPE) {
/* 1776 */       char c1 = this.in.getc();
/* 1777 */       if (c1 == '%' && this.doLexicalPE)
/* 1778 */         fatal("P-080"); 
/* 1779 */       return c1;
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
/* 1800 */     while (this.in.isEOF()) {
/* 1801 */       if (this.in.isInternal() || (this.doLexicalPE && !this.in.isDocument())) {
/* 1802 */         this.in = this.in.pop(); continue;
/*      */       } 
/* 1804 */       fatal("P-064", new Object[] { this.in.getName() });
/*      */     } 
/*      */     char c;
/* 1807 */     if ((c = this.in.getc()) == '%' && this.doLexicalPE) {
/*      */       
/* 1809 */       String name = maybeGetName();
/*      */ 
/*      */       
/* 1812 */       if (name == null)
/* 1813 */         fatal("P-011"); 
/* 1814 */       nextChar(';', "F-021", name);
/* 1815 */       Object entity = this.params.get(name);
/*      */ 
/*      */ 
/*      */       
/* 1819 */       pushReader(" ".toCharArray(), null, false);
/* 1820 */       if (entity instanceof InternalEntity) {
/* 1821 */         pushReader(((InternalEntity)entity).buf, name, false);
/* 1822 */       } else if (entity instanceof ExternalEntity) {
/*      */ 
/*      */         
/* 1825 */         pushReader((ExternalEntity)entity);
/* 1826 */       } else if (entity == null) {
/*      */         
/* 1828 */         fatal("V-022");
/*      */       } else {
/* 1830 */         throw new InternalError();
/* 1831 */       }  pushReader(" ".toCharArray(), null, false);
/* 1832 */       return this.in.getc();
/*      */     } 
/* 1834 */     return c;
/*      */   }
/*      */ 
/*      */   
/*      */   private void ungetc() {
/* 1839 */     this.in.ungetc();
/*      */   }
/*      */   
/*      */   private boolean peek(String s) throws IOException, ParseException {
/* 1843 */     return this.in.peek(s, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InputEntity peekDeclaration(String s) throws IOException, ParseException {
/* 1853 */     if (!this.in.peekc('<'))
/* 1854 */       return null; 
/* 1855 */     InputEntity start = this.in;
/* 1856 */     if (this.in.peek(s, null))
/* 1857 */       return start; 
/* 1858 */     this.in.ungetc();
/* 1859 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void nextChar(char c, String location, String near) throws IOException, ParseException {
/* 1864 */     while (this.in.isEOF() && !this.in.isDocument())
/* 1865 */       this.in = this.in.pop(); 
/* 1866 */     if (!this.in.peekc(c)) {
/* 1867 */       fatal("P-008", new Object[] { new Character(c), messages.getMessage(this.locale, location), (near == null) ? "" : ('"' + near + '"') });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pushReader(char[] buf, String name, boolean isGeneral) throws ParseException {
/* 1877 */     InputEntity r = InputEntity.getInputEntity(this.locale);
/* 1878 */     r.init(buf, name, this.in, !isGeneral);
/* 1879 */     this.in = r;
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
/* 1892 */       InputEntity r = InputEntity.getInputEntity(this.locale);
/* 1893 */       InputSource s = next.getInputSource(this.resolver);
/*      */       
/* 1895 */       r.init(s, next.name, this.in, next.isPE);
/* 1896 */       this.in = r;
/* 1897 */     } catch (SAXException e) {
/* 1898 */       throw translate(e);
/*      */     } 
/* 1900 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void warning(String messageId, Object[] parameters) throws ParseException {
/* 1907 */     fatal(messages.getMessage(this.locale, messageId, parameters));
/*      */   }
/*      */ 
/*      */   
/*      */   void error(String messageId, Object[] parameters) throws ParseException {
/* 1912 */     fatal(messages.getMessage(this.locale, messageId, parameters));
/*      */   }
/*      */   
/*      */   private void fatal(String message) throws ParseException {
/* 1916 */     fatal(message, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fatal(String message, Object[] parameters) throws ParseException {
/* 1921 */     fatal(message, parameters, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void fatal(String messageId, Object[] parameters, Exception e) throws ParseException {
/* 1926 */     String m = messages.getMessage(this.locale, messageId, parameters);
/* 1927 */     String m2 = (e == null) ? null : e.toString();
/* 1928 */     if (m2 != null) {
/* 1929 */       m = m + ": " + m2;
/*      */     }
/* 1931 */     ParseException x = new ParseException(m, getPublicId(), getSystemId(), getLineNumber(), getColumnNumber());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1939 */     throw x;
/*      */   }
/*      */   
/*      */   private ParseException translate(SAXException x) {
/* 1943 */     String m = x.getMessage();
/* 1944 */     if (x.getException() != null) {
/* 1945 */       String n = x.getException().toString();
/* 1946 */       if (m != null) {
/* 1947 */         m = m + ": " + n;
/*      */       } else {
/* 1949 */         m = n;
/*      */       } 
/* 1951 */     }  return new ParseException(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class DocLocator
/*      */     implements Locator
/*      */   {
/*      */     public String getPublicId() {
/* 1961 */       return (Parser.this.in == null) ? null : Parser.this.in.getPublicId();
/*      */     }
/*      */     
/*      */     public String getSystemId() {
/* 1965 */       return (Parser.this.in == null) ? null : Parser.this.in.getSystemId();
/*      */     }
/*      */     
/*      */     public int getLineNumber() {
/* 1969 */       return (Parser.this.in == null) ? -1 : Parser.this.in.getLineNumber();
/*      */     }
/*      */     
/*      */     public int getColumnNumber() {
/* 1973 */       return (Parser.this.in == null) ? -1 : Parser.this.in.getColumnNumber();
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
/* 1993 */     Parser.NameCacheEntry[] hashtable = new Parser.NameCacheEntry[541];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String lookup(char[] value, int len) {
/* 1999 */       return (lookupEntry(value, len)).name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Parser.NameCacheEntry lookupEntry(char[] value, int len) {
/* 2008 */       int index = 0;
/*      */ 
/*      */ 
/*      */       
/* 2012 */       for (int i = 0; i < len; i++)
/* 2013 */         index = index * 31 + value[i]; 
/* 2014 */       index &= Integer.MAX_VALUE;
/* 2015 */       index %= this.hashtable.length;
/*      */       
/*      */       Parser.NameCacheEntry entry;
/* 2018 */       for (entry = this.hashtable[index]; entry != null; entry = entry.next) {
/* 2019 */         if (entry.matches(value, len)) {
/* 2020 */           return entry;
/*      */         }
/*      */       } 
/*      */       
/* 2024 */       entry = new Parser.NameCacheEntry();
/* 2025 */       entry.chars = new char[len];
/* 2026 */       System.arraycopy(value, 0, entry.chars, 0, len);
/* 2027 */       entry.name = new String(entry.chars);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2033 */       entry.name = entry.name.intern();
/* 2034 */       entry.next = this.hashtable[index];
/* 2035 */       this.hashtable[index] = entry;
/* 2036 */       return entry;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class NameCacheEntry {
/*      */     String name;
/*      */     char[] chars;
/*      */     NameCacheEntry next;
/*      */     
/*      */     boolean matches(char[] value, int len) {
/* 2046 */       if (this.chars.length != len)
/* 2047 */         return false; 
/* 2048 */       for (int i = 0; i < len; i++) {
/* 2049 */         if (value[i] != this.chars[i])
/* 2050 */           return false; 
/* 2051 */       }  return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2058 */   static final Catalog messages = new Catalog();
/*      */   
/*      */   static final class Catalog extends MessageCatalog {
/*      */     Catalog() {
/* 2062 */       super(Parser.class);
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
/* 2075 */   private InputSource input = null;
/*      */   
/*      */   private boolean coalescing = false;
/*      */   
/* 2079 */   private StringBuffer charsBuffer = null;
/* 2080 */   private int cacheRet = -1;
/* 2081 */   private String cacheName = null;
/* 2082 */   private String cacheValue = null;
/*      */ 
/*      */ 
/*      */   
/* 2086 */   private String simpleCharsBuffer = null; private FastStack stack; private PIQueue piQueue; private static final int ELEMENT_IN_CONTENT = 1; private static final int ELEMENT_ROOT = 2; private static final int CONTENT_IN_ELEMENT = 4; private static final int CONTENT_IN_INTREF = 8;
/*      */   private static final int CONTENT_IN_EXTREF = 16;
/*      */   private static final int ELEMENT = 256;
/*      */   private static final int CONTENT = 1024;
/*      */   private static final int START = 0;
/*      */   private static final int END = 1;
/*      */   private static final int ATTR = 2;
/*      */   private static final int CHARS = 3;
/*      */   private static final int PI = 5;
/*      */   private static final int EMPTY = 10;
/*      */   private boolean haveAttributes;
/*      */   private int startLine;
/*      */   private boolean hasContent;
/*      */   
/*      */   public Parser(InputStream in, boolean coalescing, boolean namespaceAware) {
/* 2101 */     this(new InputSource(in), coalescing, namespaceAware);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parser(InputStream in) {
/* 2110 */     this(new InputSource(in), false, false);
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
/*      */   public Parser(File file, boolean coalescing, boolean namespaceAware) throws IOException {
/* 2160 */     this.stack = new FastStack(100);
/*      */     
/* 2162 */     this.piQueue = new PIQueue(10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2189 */     this.haveAttributes = false;
/*      */     
/* 2191 */     this.hasContent = true; InputStream in = new BufferedInputStream(new FileInputStream(file)); InputSource is = new InputSource(in); is.setSystemId(file.toURL().toString()); this.locator = new DocLocator(); this.input = is; this.coalescing = coalescing; this.namespace = namespaceAware; } public Parser(File file) throws IOException { this.stack = new FastStack(100); this.piQueue = new PIQueue(10); this.haveAttributes = false; this.hasContent = true; InputStream in = new BufferedInputStream(new FileInputStream(file)); InputSource is = new InputSource(in); is.setSystemId(file.toURL().toString()); this.locator = new DocLocator(); this.input = is; } private Parser(InputSource input, boolean coalescing, boolean namespaceAware) { this.stack = new FastStack(100); this.piQueue = new PIQueue(10); this.haveAttributes = false; this.hasContent = true;
/*      */     this.locator = new DocLocator();
/*      */     this.input = input;
/*      */     this.coalescing = coalescing;
/* 2195 */     this.namespace = namespaceAware; } private void prologue() throws IOException, ParseException { init();
/* 2196 */     if (this.input == null)
/* 2197 */       fatal("P-000"); 
/* 2198 */     this.in = InputEntity.getInputEntity(this.locale);
/* 2199 */     this.in.init(this.input, (String)null, (InputEntity)null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2204 */     maybeXmlDecl();
/* 2205 */     maybeMisc(false);
/*      */     
/* 2207 */     maybeDoctypeDecl();
/*      */     
/* 2209 */     maybeMisc(false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int parse() throws ParseException, IOException {
/* 2218 */     int ret = 0;
/*      */     
/* 2220 */     try { if (!this.donePrologue) {
/* 2221 */         prologue();
/* 2222 */         this.donePrologue = true;
/*      */       } 
/* 2224 */       if ((ret = retrievePIs()) != -1) {
/* 2225 */         return ret;
/*      */       }
/*      */       
/* 2228 */       if (!this.doneContent) {
/* 2229 */         if (!this.coalescing) {
/* 2230 */           if ((ret = parseContent()) != 10) {
/* 2231 */             return ret;
/*      */           }
/* 2233 */           this.doneContent = true;
/*      */         }
/*      */         else {
/*      */           
/* 2237 */           if (this.cacheRet != -1) {
/* 2238 */             ret = this.cacheRet;
/* 2239 */             this.curName = this.cacheName;
/* 2240 */             this.curValue = this.cacheValue;
/* 2241 */             this.cacheRet = -1;
/* 2242 */             this.cacheName = null;
/* 2243 */             this.cacheValue = null;
/* 2244 */             return ret;
/*      */           } 
/* 2246 */           while ((ret = parseContent()) != 10) {
/* 2247 */             if (ret == 3) {
/*      */               
/* 2249 */               if (this.simpleCharsBuffer == null) {
/* 2250 */                 this.simpleCharsBuffer = this.curValue; continue;
/*      */               } 
/* 2252 */               if (this.charsBuffer == null) {
/* 2253 */                 this.charsBuffer = new StringBuffer();
/* 2254 */                 this.charsBuffer.append(this.simpleCharsBuffer);
/*      */               } 
/* 2256 */               this.charsBuffer.append(this.curValue);
/*      */               continue;
/*      */             } 
/* 2259 */             if (ret != 3) {
/* 2260 */               if (this.simpleCharsBuffer != null) {
/*      */                 
/* 2262 */                 this.cacheRet = ret;
/* 2263 */                 this.cacheName = this.curName;
/* 2264 */                 this.cacheValue = this.curValue;
/*      */                 
/* 2266 */                 if (this.charsBuffer == null) {
/* 2267 */                   this.curName = null;
/* 2268 */                   this.curValue = this.simpleCharsBuffer;
/*      */                 }
/*      */                 else {
/*      */                   
/* 2272 */                   this.curName = null;
/* 2273 */                   this.curValue = this.charsBuffer.toString();
/* 2274 */                   this.charsBuffer = null;
/*      */                 } 
/* 2276 */                 this.simpleCharsBuffer = null;
/* 2277 */                 return 3;
/*      */               } 
/* 2279 */               return ret;
/*      */             } 
/*      */           } 
/*      */           
/* 2283 */           this.doneContent = true;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2288 */       if (!this.doneEpilogue) {
/* 2289 */         epilogue();
/* 2290 */         this.doneEpilogue = true;
/*      */       } 
/* 2292 */       return retrievePIs(); }
/* 2293 */     catch (EndOfInputException e)
/* 2294 */     { if (!this.in.isDocument())
/* 2295 */       { String name = this.in.getName();
/*      */         
/*      */         try { while (true)
/* 2298 */           { this.in = this.in.pop();
/* 2299 */             if (!this.in.isInternal())
/* 2300 */             { fatal("P-002", new Object[] { name }, e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2319 */               return ret; }  }  } catch (IOException x) { fatal("P-002", new Object[] { name }, e); }  } else { fatal("P-003", null, e); }  } catch (RuntimeException e) { throw new ParseException((e.getMessage() != null) ? e.getMessage() : e.getClass().getName(), getPublicId(), getSystemId(), getLineNumber(), getColumnNumber()); }  return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   private int retrievePIs() {
/* 2324 */     if (!this.piQueue.empty()) {
/* 2325 */       this.curName = this.piQueue.getNextTarget();
/* 2326 */       this.curValue = this.piQueue.getNextContent();
/* 2327 */       return 5;
/*      */     } 
/* 2329 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void epilogue() throws IOException, ParseException {
/*      */     try {
/* 2336 */       afterRoot();
/* 2337 */       maybeMisc(true);
/* 2338 */       if (!this.in.isEOF()) {
/* 2339 */         fatal("P-001", new Object[] { Integer.toHexString(getc()) });
/*      */       }
/*      */     }
/* 2342 */     catch (EndOfInputException e) {
/* 2343 */       if (!this.in.isDocument()) {
/* 2344 */         String name = this.in.getName();
/*      */         while (true) {
/* 2346 */           this.in = this.in.pop();
/* 2347 */           if (!this.in.isInternal())
/* 2348 */           { fatal("P-002", new Object[] { name }, e); return; } 
/*      */         } 
/* 2350 */       }  fatal("P-003", null, e);
/*      */     }
/* 2352 */     catch (RuntimeException e) {
/*      */       
/* 2354 */       throw new ParseException((e.getMessage() != null) ? e.getMessage() : e.getClass().getName(), getPublicId(), getSystemId(), getLineNumber(), getColumnNumber());
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2364 */       this.strTmp = null;
/* 2365 */       this.attTmp = null;
/* 2366 */       this.nameTmp = null;
/* 2367 */       this.nameCache = null;
/*      */ 
/*      */       
/* 2370 */       if (this.in != null) {
/* 2371 */         this.in.close();
/* 2372 */         this.in = null;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2378 */       this.params.clear();
/* 2379 */       this.entities.clear();
/* 2380 */       this.notations.clear();
/* 2381 */       this.elements.clear();
/*      */       
/* 2383 */       afterDocument();
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
/* 2397 */     NameCacheEntry name = maybeGetNameCacheEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2402 */     if (name == null) {
/* 2403 */       return null;
/*      */     }
/* 2405 */     ElementDecl element = (ElementDecl)this.elements.get(name.name);
/* 2406 */     if (element == null || element.contentType == null) {
/*      */       
/* 2408 */       element = new ElementDecl(name.name);
/* 2409 */       element.contentType = "ANY";
/* 2410 */       this.elements.put(name.name, element);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2415 */     this.startLine = this.in.getLineNumber();
/*      */ 
/*      */ 
/*      */     
/* 2419 */     boolean sawWhite = this.in.maybeWhitespace();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2424 */     while (!this.in.peekc('>')) {
/*      */       String value;
/*      */ 
/*      */       
/* 2428 */       if (this.in.peekc('/')) {
/* 2429 */         this.hasContent = false;
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 2434 */       if (!sawWhite) {
/* 2435 */         fatal("P-030");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2443 */       String attName = maybeGetName();
/*      */ 
/*      */       
/* 2446 */       if (attName == null) {
/* 2447 */         fatal("P-031", new Object[] { new Character(getc()) });
/*      */       }
/* 2449 */       if (this.attTmp.getValue(attName) != null) {
/* 2450 */         fatal("P-032", new Object[] { attName });
/*      */       }
/*      */       
/* 2453 */       this.in.maybeWhitespace();
/* 2454 */       nextChar('=', "F-026", attName);
/* 2455 */       this.in.maybeWhitespace();
/*      */       
/* 2457 */       parseLiteral(false);
/* 2458 */       sawWhite = this.in.maybeWhitespace();
/*      */ 
/*      */ 
/*      */       
/* 2462 */       AttributeDecl info = (element == null) ? null : (AttributeDecl)element.attributes.get(attName);
/*      */ 
/*      */ 
/*      */       
/* 2466 */       if (info == null) {
/* 2467 */         value = this.strTmp.toString();
/*      */       }
/* 2469 */       else if (!"CDATA".equals(info.type)) {
/* 2470 */         value = normalize(!info.isFromInternalSubset);
/*      */       } else {
/* 2472 */         value = this.strTmp.toString();
/*      */       } 
/*      */       
/* 2475 */       if ("xml:lang".equals(attName) && !isXmlLang(value)) {
/* 2476 */         error("P-033", new Object[] { value });
/*      */       }
/* 2478 */       this.attTmp.addAttribute("", attName, attName, (info == null) ? "CDATA" : info.type, value, (info == null) ? null : info.defaultValue, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2486 */       this.haveAttributes = true;
/*      */     } 
/* 2488 */     if (element != null) {
/* 2489 */       this.attTmp.setIdAttributeName(element.id);
/*      */     }
/*      */ 
/*      */     
/* 2493 */     if (element != null && element.attributes.size() != 0) {
/* 2494 */       this.haveAttributes = (defaultAttributes(this.attTmp, element) || this.haveAttributes);
/*      */     }
/*      */     
/* 2497 */     this.attr = this.attTmp;
/* 2498 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeReferenceInContent() throws IOException, ParseException {
/* 2506 */     if (!this.in.peekc('&')) {
/* 2507 */       return false;
/*      */     }
/* 2509 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybeEntityReference() throws IOException, ParseException {
/* 2515 */     if (!this.in.peekc('#')) {
/* 2516 */       return true;
/*      */     }
/* 2518 */     return false;
/*      */   }
/*      */   
/*      */   private Object getEntityReference() throws IOException, ParseException {
/* 2522 */     String name = maybeGetName();
/* 2523 */     if (name == null)
/* 2524 */       fatal("P-009"); 
/* 2525 */     nextChar(';', "F-020", name);
/* 2526 */     Object entity = this.entities.get(name);
/* 2527 */     err(" after in = " + this.in);
/*      */     
/* 2529 */     if (entity == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2535 */       fatal("P-014", new Object[] { name });
/*      */     }
/* 2537 */     return entity;
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
/* 2548 */     if (!this.in.peek(element.name, null)) {
/* 2549 */       fatal("P-034", new Object[] { element.name, new Integer(this.startLine) });
/*      */     }
/*      */ 
/*      */     
/* 2553 */     this.in.maybeWhitespace();
/* 2554 */     nextChar('>', "F-027", element.name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void intRefEpilogue(StackElement elt) throws IOException, ParseException {
/* 2560 */     InternalEntity entity = (InternalEntity)elt.entity;
/* 2561 */     InputEntity last = elt.in;
/* 2562 */     if (this.in != last && !this.in.isEOF()) {
/* 2563 */       while (this.in.isInternal())
/* 2564 */         this.in = this.in.pop(); 
/* 2565 */       fatal("P-052", new Object[] { entity.name });
/*      */     } 
/* 2567 */     this.in = this.in.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void extRefEpilogue(StackElement elt) throws IOException, ParseException {
/* 2573 */     ExternalEntity entity = (ExternalEntity)elt.entity;
/*      */     
/* 2575 */     if (!this.in.isEOF())
/* 2576 */       fatal("P-058", new Object[] { entity.name }); 
/* 2577 */     this.in = this.in.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maybePI(boolean skipStart) throws IOException, ParseException {
/* 2587 */     boolean savedLexicalPE = this.doLexicalPE;
/*      */     
/* 2589 */     if (!this.in.peek(skipStart ? "?" : "<?", null))
/* 2590 */       return false; 
/* 2591 */     this.doLexicalPE = false;
/*      */     
/* 2593 */     String target = maybeGetName();
/* 2594 */     String piContent = null;
/*      */     
/* 2596 */     if (target == null)
/* 2597 */       fatal("P-018"); 
/* 2598 */     if ("xml".equals(target))
/* 2599 */       fatal("P-019"); 
/* 2600 */     if ("xml".equalsIgnoreCase(target)) {
/* 2601 */       fatal("P-020", new Object[] { target });
/*      */     }
/* 2603 */     if (maybeWhitespace()) {
/* 2604 */       this.strTmp = new StringBuffer();
/*      */       
/*      */       try {
/*      */         while (true) {
/* 2608 */           char c = this.in.getc();
/*      */           
/* 2610 */           if (c == '?' && this.in.peekc('>'))
/*      */             break; 
/* 2612 */           this.strTmp.append(c);
/*      */         } 
/* 2614 */       } catch (EndOfInputException e) {
/* 2615 */         fatal("P-021");
/*      */       } 
/* 2617 */       piContent = this.strTmp.toString();
/*      */     } else {
/* 2619 */       if (!this.in.peek("?>", null))
/* 2620 */         fatal("P-022"); 
/* 2621 */       piContent = "";
/*      */     } 
/*      */     
/* 2624 */     this.doLexicalPE = savedLexicalPE;
/* 2625 */     this.piQueue.in(target, piContent);
/* 2626 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processStartElement(ElementDecl elt) throws IOException, ParseException {
/* 2636 */     this.ns.pushContext();
/* 2637 */     boolean seenDecl = false;
/*      */     
/* 2639 */     int length = this.attr.getLength(); int i;
/* 2640 */     for (i = 0; i < length; i++) {
/* 2641 */       String attRawName = this.attr.getQName(i);
/* 2642 */       String value = this.attr.getValue(i);
/*      */ 
/*      */ 
/*      */       
/* 2646 */       boolean isNamespaceDecl = false;
/* 2647 */       String prefix = "";
/*      */       
/* 2649 */       if (attRawName.startsWith("xmlns")) {
/* 2650 */         isNamespaceDecl = true;
/* 2651 */         if (attRawName.length() != 5)
/*      */         {
/* 2653 */           if (attRawName.charAt(5) == ':') {
/* 2654 */             prefix = attRawName.substring(6);
/*      */           } else {
/* 2656 */             isNamespaceDecl = false;
/*      */           } 
/*      */         }
/*      */       } 
/* 2660 */       if (isNamespaceDecl) {
/* 2661 */         if (!this.ns.declarePrefix(prefix, value))
/*      */         {
/* 2663 */           fatal("Illegal Namespace prefix: " + prefix);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2668 */         seenDecl = true;
/*      */       } else {
/* 2670 */         String[] attName = this.ns.processName(attRawName, this.parts, true);
/* 2671 */         if (attName != null) {
/*      */ 
/*      */           
/* 2674 */           this.attr.setURI(i, attName[0]);
/* 2675 */           this.attr.setLocalName(i, attName[1]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2685 */     if (seenDecl) {
/* 2686 */       length = this.attr.getLength();
/* 2687 */       for (i = 0; i < length; i++) {
/* 2688 */         String attRawName = this.attr.getQName(i);
/* 2689 */         if (attRawName.startsWith("xmlns"))
/*      */         {
/*      */           
/* 2692 */           if (attRawName.length() == 5 || attRawName.charAt(5) == ':') {
/*      */             continue;
/*      */           }
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2700 */         String[] attName = this.ns.processName(attRawName, this.parts, true);
/* 2701 */         if (attName != null) {
/*      */ 
/*      */           
/* 2704 */           this.attr.setURI(i, attName[0]);
/* 2705 */           this.attr.setLocalName(i, attName[1]);
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */     } 
/* 2710 */     getSetCurName(elt.name, false);
/* 2711 */     this.curValue = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processEndElement(ElementDecl elt) throws IOException, ParseException {
/* 2720 */     getSetCurName(elt.name, false);
/* 2721 */     this.ns.popContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getSetCurName(String rawName, boolean isAttribute) throws ParseException {
/* 2730 */     String[] names = this.ns.processName(rawName, this.parts, isAttribute);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2738 */     if (names == null) {
/* 2739 */       fatal("P-084", new Object[] { rawName });
/*      */     }
/*      */     
/* 2742 */     this.curURI = names[0];
/* 2743 */     this.curName = names[1];
/* 2744 */     this.curValue = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseContent() throws IOException, ParseException {
/* 2753 */     ElementDecl elt = null;
/*      */     
/*      */     while (true) {
/*      */       ElementDecl e2;
/*      */       StackElement se2;
/*      */       String chars;
/* 2759 */       while (this.stack.empty()) {
/*      */ 
/*      */         
/* 2762 */         if (!this.startEmptyStack) {
/* 2763 */           return 10;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2768 */         if (this.startEmptyStack && (!this.in.peekc('<') || (elt = getElement()) == null)) {
/*      */           
/* 2770 */           fatal("P-067");
/*      */           continue;
/*      */         } 
/* 2773 */         this.startEmptyStack = false;
/* 2774 */         this.stack.push(newStackElement(2, 256, elt, null, null));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2781 */         if (!this.haveAttributes && this.hasContent) {
/* 2782 */           this.stack.push(newStackElement(4, 1024, elt, null, null));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2790 */         if (!this.namespace) {
/* 2791 */           this.curName = elt.name;
/* 2792 */           this.curValue = null;
/*      */         } else {
/* 2794 */           processStartElement(elt);
/*      */         } 
/* 2796 */         return 0;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2805 */       StackElement se = this.stack.pop();
/* 2806 */       elt = se.elt;
/* 2807 */       switch (se.curState) {
/*      */         case 256:
/* 2809 */           if (this.attr == null)
/*      */           {
/* 2811 */             fatal("P-082");
/*      */           }
/*      */           
/* 2814 */           if (this.attrIndex < this.attr.getLength()) {
/* 2815 */             this.curName = this.attr.getLocalName(this.attrIndex);
/* 2816 */             this.curValue = this.attr.getValue(this.attrIndex);
/* 2817 */             this.curURI = this.attr.getURI(this.attrIndex);
/* 2818 */             this.attrIndex++;
/* 2819 */             this.stack.push(se);
/* 2820 */             return 2;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2825 */           if (this.haveAttributes) {
/* 2826 */             this.attr = null;
/* 2827 */             this.attrIndex = 0;
/* 2828 */             this.attTmp.clear();
/* 2829 */             this.haveAttributes = false;
/*      */           } 
/* 2831 */           if (this.hasContent) {
/*      */ 
/*      */             
/* 2834 */             this.stack.push(se);
/* 2835 */             this.stack.push(newStackElement(4, 1024, elt, null, null));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2845 */           this.hasContent = true;
/* 2846 */           nextChar('>', "F-027", elt.name);
/* 2847 */           freeStackElement(se);
/* 2848 */           this.curName = elt.name;
/* 2849 */           if (!this.namespace) {
/* 2850 */             this.curValue = null;
/*      */           } else {
/* 2852 */             processEndElement(elt);
/*      */           } 
/* 2854 */           return 1;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1024:
/* 2859 */           e2 = null;
/* 2860 */           se2 = null;
/* 2861 */           chars = null;
/* 2862 */           if (this.in.peekc('<')) {
/* 2863 */             if ((e2 = getElement()) != null) {
/*      */               
/* 2865 */               this.stack.push(se);
/* 2866 */               this.stack.push(newStackElement(1, 256, e2, null, null));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2873 */               if (!this.haveAttributes && this.hasContent) {
/* 2874 */                 this.stack.push(newStackElement(4, 1024, e2, null, null));
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2881 */               if (!this.namespace) {
/* 2882 */                 this.curName = e2.name;
/* 2883 */                 this.curValue = null;
/*      */               } else {
/* 2885 */                 processStartElement(e2);
/*      */               } 
/* 2887 */               return 0;
/* 2888 */             }  if (!this.in.peekc('/'))
/*      */             {
/* 2890 */               if (maybeComment(true)) {
/*      */                 
/* 2892 */                 this.stack.push(se); continue;
/*      */               } 
/* 2894 */               if (maybePI(true)) {
/*      */                 
/* 2896 */                 this.stack.push(se);
/* 2897 */                 this.curName = this.piQueue.getNextTarget();
/* 2898 */                 this.curValue = this.piQueue.getNextContent();
/* 2899 */                 return 5;
/* 2900 */               }  if ((chars = this.in.getUnparsedContent((elt != null && elt.ignoreWhitespace), null)) != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2907 */                 this.stack.push(se);
/* 2908 */                 if (chars.length() != 0) {
/* 2909 */                   this.curName = null;
/* 2910 */                   this.curValue = chars;
/* 2911 */                   return 3;
/*      */                 } 
/*      */                 
/*      */                 continue;
/*      */               } 
/* 2916 */               char c = getc();
/* 2917 */               fatal("P-079", new Object[] { Integer.toHexString(c), new Character(c) });
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2924 */             if (elt != null && elt.ignoreWhitespace && this.in.ignorableWhitespace()) {
/*      */ 
/*      */ 
/*      */               
/* 2928 */               this.stack.push(se); continue;
/*      */             } 
/* 2930 */             if ((chars = this.in.getParsedContent(this.coalescing)) != null) {
/*      */ 
/*      */ 
/*      */               
/* 2934 */               this.stack.push(se);
/* 2935 */               if (chars.length() != 0) {
/* 2936 */                 this.curName = null;
/* 2937 */                 this.curValue = chars;
/* 2938 */                 return 3;
/*      */               } 
/*      */               continue;
/*      */             } 
/* 2942 */             if (this.in.isEOF()) {
/*      */               
/* 2944 */               if (se.origState == 4) {
/* 2945 */                 fatal("P-035");
/*      */               }
/* 2947 */             } else if (maybeReferenceInContent()) {
/*      */               
/* 2949 */               if (maybeEntityReference()) {
/* 2950 */                 this.stack.push(se);
/* 2951 */                 Object entity = getEntityReference();
/* 2952 */                 InputEntity last = this.in;
/* 2953 */                 if (entity instanceof InternalEntity) {
/* 2954 */                   InternalEntity e = (InternalEntity)entity;
/* 2955 */                   this.stack.push(newStackElement(8, 1024, elt, e, last));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2962 */                   pushReader(e.buf, e.name, true); continue;
/*      */                 } 
/* 2964 */                 if (entity instanceof ExternalEntity) {
/* 2965 */                   ExternalEntity e = (ExternalEntity)entity;
/* 2966 */                   if (e.notation != null)
/* 2967 */                     fatal("P-053", new Object[] { e.name }); 
/* 2968 */                   if (!pushReader(e)) {
/*      */                     continue;
/*      */                   }
/* 2971 */                   maybeTextDecl();
/* 2972 */                   this.stack.push(newStackElement(16, 1024, elt, e, null));
/*      */ 
/*      */ 
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/* 2981 */                 throw new InternalError();
/*      */               } 
/*      */               
/* 2984 */               this.stack.push(se);
/* 2985 */               int ret = surrogatesToCharTmp(parseCharNumber());
/*      */               
/* 2987 */               this.curName = null;
/* 2988 */               this.curValue = new String(this.charTmp, 0, ret);
/* 2989 */               return 3;
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2995 */           if (se.origState == 4) {
/*      */             
/* 2997 */             se2 = this.stack.pop();
/* 2998 */             if (se2.curState != 256)
/*      */             {
/* 3000 */               fatal("P-083");
/*      */             }
/*      */             
/* 3003 */             elementEpilogue(elt);
/* 3004 */             this.curName = elt.name;
/* 3005 */             if (!this.namespace) {
/* 3006 */               this.curValue = null;
/*      */             } else {
/* 3008 */               processEndElement(elt);
/*      */             } 
/* 3010 */             freeStackElement(se);
/* 3011 */             freeStackElement(se2);
/* 3012 */             return 1;
/* 3013 */           }  if (se.origState == 8) {
/*      */             
/* 3015 */             intRefEpilogue(se);
/* 3016 */             freeStackElement(se); continue;
/* 3017 */           }  if (se.origState == 16) {
/*      */             
/* 3019 */             extRefEpilogue(se);
/* 3020 */             freeStackElement(se);
/*      */           } 
/*      */           continue;
/*      */       } 
/*      */       
/* 3025 */       fatal("P-083");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class FastStack
/*      */   {
/*      */     private Parser.StackElement first;
/*      */ 
/*      */ 
/*      */     
/*      */     public FastStack(int initialCapacity) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean empty() {
/* 3042 */       return (this.first == null);
/*      */     }
/*      */     
/*      */     public void push(Parser.StackElement e) {
/* 3046 */       if (this.first == null) {
/* 3047 */         this.first = e;
/*      */       } else {
/* 3049 */         e.next = this.first;
/* 3050 */         this.first = e;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Parser.StackElement pop() {
/* 3055 */       Parser.StackElement result = this.first;
/* 3056 */       this.first = this.first.next;
/* 3057 */       result.next = null;
/* 3058 */       return result;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 3062 */       this.first = null;
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
/* 3082 */       this.origState = origState;
/* 3083 */       this.curState = curState;
/* 3084 */       this.elt = elt;
/* 3085 */       this.entity = entity;
/* 3086 */       this.in = in;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StackElement newStackElement(int origState, int curState, ElementDecl elt, EntityDecl entity, InputEntity in) {
/* 3096 */     return new StackElement(origState, curState, elt, entity, in);
/*      */   }
/*      */ 
/*      */   
/*      */   private void freeStackElement(StackElement e) {}
/*      */   
/*      */   private final class PIQueue
/*      */   {
/*      */     private String[] pi;
/* 3105 */     private int size = 0;
/* 3106 */     private int index = 0;
/*      */     
/*      */     public PIQueue(int initialCapacity) {
/* 3109 */       this.pi = new String[2 * initialCapacity];
/*      */     }
/*      */     
/*      */     public boolean empty() {
/* 3113 */       return (this.size == this.index);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 3117 */       this.size = 0;
/*      */     }
/*      */     
/*      */     public void in(String target, String content) {
/* 3121 */       ensureCapacity();
/* 3122 */       this.pi[this.size++] = target;
/* 3123 */       this.pi[this.size++] = content;
/*      */     }
/*      */     
/*      */     public String getNextTarget() {
/* 3127 */       String result = null;
/* 3128 */       if (this.index < this.size) {
/* 3129 */         result = this.pi[this.index];
/* 3130 */         this.pi[this.index++] = null;
/*      */       } 
/* 3132 */       return result;
/*      */     }
/*      */     
/*      */     public String getNextContent() {
/* 3136 */       String result = null;
/* 3137 */       if (this.index < this.size) {
/* 3138 */         result = this.pi[this.index];
/* 3139 */         this.pi[this.index++] = null;
/*      */       } 
/* 3141 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void ensureCapacity() {
/* 3149 */       if (this.pi.length == this.size) {
/* 3150 */         String[] oldPi = this.pi;
/* 3151 */         this.pi = new String[2 * this.pi.length + 2];
/* 3152 */         System.arraycopy(oldPi, 0, this.pi, 0, this.size);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void err(String msg) {}
/*      */   
/*      */   private void debug() {}
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\Parser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
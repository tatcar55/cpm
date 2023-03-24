/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NamespaceSupport
/*     */ {
/*     */   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
/* 121 */   private static final Iterable<String> EMPTY_ENUMERATION = new ArrayList<String>();
/*     */ 
/*     */   
/*     */   private Context[] contexts;
/*     */   
/*     */   private Context currentContext;
/*     */   
/*     */   private int contextPos;
/*     */ 
/*     */   
/*     */   public NamespaceSupport() {
/* 132 */     reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceSupport(NamespaceSupport that) {
/* 137 */     this.contexts = new Context[that.contexts.length];
/* 138 */     this.currentContext = null;
/* 139 */     this.contextPos = that.contextPos;
/*     */     
/* 141 */     Context currentParent = null;
/*     */     
/* 143 */     for (int i = 0; i < that.contexts.length; i++) {
/* 144 */       Context thatContext = that.contexts[i];
/*     */       
/* 146 */       if (thatContext == null) {
/* 147 */         this.contexts[i] = null;
/*     */       }
/*     */       else {
/*     */         
/* 151 */         Context thisContext = new Context(thatContext, currentParent);
/* 152 */         this.contexts[i] = thisContext;
/* 153 */         if (that.currentContext == thatContext) {
/* 154 */           this.currentContext = thisContext;
/*     */         }
/*     */         
/* 157 */         currentParent = thisContext;
/*     */       } 
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
/*     */   public void reset() {
/* 172 */     this.contexts = new Context[32];
/* 173 */     this.contextPos = 0;
/* 174 */     this.contexts[this.contextPos] = this.currentContext = new Context();
/* 175 */     this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
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
/*     */   public void pushContext() {
/* 193 */     int max = this.contexts.length;
/* 194 */     this.contextPos++;
/*     */ 
/*     */     
/* 197 */     if (this.contextPos >= max) {
/* 198 */       Context[] newContexts = new Context[max * 2];
/* 199 */       System.arraycopy(this.contexts, 0, newContexts, 0, max);
/* 200 */       this.contexts = newContexts;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     this.currentContext = this.contexts[this.contextPos];
/* 205 */     if (this.currentContext == null) {
/* 206 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/*     */     }
/*     */ 
/*     */     
/* 210 */     if (this.contextPos > 0) {
/* 211 */       this.currentContext.setParent(this.contexts[this.contextPos - 1]);
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
/*     */   public void popContext() {
/* 229 */     this.contextPos--;
/* 230 */     if (this.contextPos < 0) {
/* 231 */       throw new EmptyStackException();
/*     */     }
/* 233 */     this.currentContext = this.contexts[this.contextPos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slideContextUp() {
/* 241 */     this.contextPos--;
/* 242 */     this.currentContext = this.contexts[this.contextPos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slideContextDown() {
/* 250 */     this.contextPos++;
/*     */     
/* 252 */     if (this.contexts[this.contextPos] == null)
/*     */     {
/* 254 */       this.contexts[this.contextPos] = this.contexts[this.contextPos - 1];
/*     */     }
/*     */     
/* 257 */     this.currentContext = this.contexts[this.contextPos];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean declarePrefix(String prefix, String uri) {
/* 294 */     if ((prefix.equals("xml") && !uri.equals("http://www.w3.org/XML/1998/namespace")) || prefix.equals("xmlns"))
/*     */     {
/* 296 */       return false;
/*     */     }
/* 298 */     this.currentContext.declarePrefix(prefix, uri);
/* 299 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] processName(String qName, String[] parts, boolean isAttribute) {
/* 345 */     String[] myParts = this.currentContext.processName(qName, isAttribute);
/* 346 */     if (myParts == null) {
/* 347 */       return null;
/*     */     }
/* 349 */     parts[0] = myParts[0];
/* 350 */     parts[1] = myParts[1];
/* 351 */     parts[2] = myParts[2];
/* 352 */     return parts;
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
/*     */   public String getURI(String prefix) {
/* 369 */     return this.currentContext.getURI(prefix);
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
/*     */   public Iterable<String> getPrefixes() {
/* 386 */     return this.currentContext.getPrefixes();
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
/*     */   public String getPrefix(String uri) {
/* 409 */     return this.currentContext.getPrefix(uri);
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
/*     */   public Iterator getPrefixes(String uri) {
/* 434 */     List<String> prefixes = new ArrayList();
/* 435 */     for (String prefix : getPrefixes()) {
/* 436 */       if (uri.equals(getURI(prefix))) {
/* 437 */         prefixes.add(prefix);
/*     */       }
/*     */     } 
/* 440 */     return prefixes.iterator();
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
/*     */   public Iterable<String> getDeclaredPrefixes() {
/* 456 */     return this.currentContext.getDeclaredPrefixes();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Context
/*     */   {
/*     */     HashMap prefixTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HashMap uriTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HashMap elementNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HashMap attributeNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String defaultNS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ArrayList declarations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean tablesDirty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Context parent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Context() {
/* 750 */       this.defaultNS = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 757 */       this.declarations = null;
/* 758 */       this.tablesDirty = false;
/* 759 */       this.parent = null; copyTables(); } Context(Context that, Context newParent) { this.defaultNS = null; this.declarations = null; this.tablesDirty = false; this.parent = null;
/*     */       if (that == null) {
/*     */         copyTables();
/*     */         return;
/*     */       } 
/*     */       if (newParent != null && !that.tablesDirty) {
/*     */         this.prefixTable = (that.prefixTable == that.parent.prefixTable) ? newParent.prefixTable : (HashMap)that.prefixTable.clone();
/*     */         this.uriTable = (that.uriTable == that.parent.uriTable) ? newParent.uriTable : (HashMap)that.uriTable.clone();
/*     */         this.elementNameTable = (that.elementNameTable == that.parent.elementNameTable) ? newParent.elementNameTable : (HashMap)that.elementNameTable.clone();
/*     */         this.attributeNameTable = (that.attributeNameTable == that.parent.attributeNameTable) ? newParent.attributeNameTable : (HashMap)that.attributeNameTable.clone();
/*     */         this.defaultNS = (that.defaultNS == that.parent.defaultNS) ? newParent.defaultNS : that.defaultNS;
/*     */       } else {
/*     */         this.prefixTable = (HashMap)that.prefixTable.clone();
/*     */         this.uriTable = (HashMap)that.uriTable.clone();
/*     */         this.elementNameTable = (HashMap)that.elementNameTable.clone();
/*     */         this.attributeNameTable = (HashMap)that.attributeNameTable.clone();
/*     */         this.defaultNS = that.defaultNS;
/*     */       } 
/*     */       this.tablesDirty = that.tablesDirty;
/*     */       this.parent = newParent;
/*     */       this.declarations = (that.declarations == null) ? null : (ArrayList)that.declarations.clone(); }
/*     */ 
/*     */     
/*     */     void setParent(Context parent) {
/*     */       this.parent = parent;
/*     */       this.declarations = null;
/*     */       this.prefixTable = parent.prefixTable;
/*     */       this.uriTable = parent.uriTable;
/*     */       this.elementNameTable = parent.elementNameTable;
/*     */       this.attributeNameTable = parent.attributeNameTable;
/*     */       this.defaultNS = parent.defaultNS;
/*     */       this.tablesDirty = false;
/*     */     }
/*     */     
/*     */     void declarePrefix(String prefix, String uri) {
/*     */       if (!this.tablesDirty)
/*     */         copyTables(); 
/*     */       if (this.declarations == null)
/*     */         this.declarations = new ArrayList(); 
/*     */       prefix = prefix.intern();
/*     */       uri = uri.intern();
/*     */       if ("".equals(prefix)) {
/*     */         if ("".equals(uri)) {
/*     */           this.defaultNS = null;
/*     */         } else {
/*     */           this.defaultNS = uri;
/*     */         } 
/*     */       } else {
/*     */         this.prefixTable.put(prefix, uri);
/*     */         this.uriTable.put(uri, prefix);
/*     */       } 
/*     */       this.declarations.add(prefix);
/*     */     }
/*     */     
/*     */     String[] processName(String qName, boolean isAttribute) {
/*     */       Map<String, String[]> table;
/*     */       if (isAttribute) {
/*     */         table = this.elementNameTable;
/*     */       } else {
/*     */         table = this.attributeNameTable;
/*     */       } 
/*     */       String[] name = (String[])table.get(qName);
/*     */       if (name != null)
/*     */         return name; 
/*     */       name = new String[3];
/*     */       int index = qName.indexOf(':');
/*     */       if (index == -1) {
/*     */         if (isAttribute || this.defaultNS == null) {
/*     */           name[0] = "";
/*     */         } else {
/*     */           name[0] = this.defaultNS;
/*     */         } 
/*     */         name[1] = qName.intern();
/*     */         name[2] = name[1];
/*     */       } else {
/*     */         String uri, prefix = qName.substring(0, index);
/*     */         String local = qName.substring(index + 1);
/*     */         if ("".equals(prefix)) {
/*     */           uri = this.defaultNS;
/*     */         } else {
/*     */           uri = (String)this.prefixTable.get(prefix);
/*     */         } 
/*     */         if (uri == null)
/*     */           return null; 
/*     */         name[0] = uri;
/*     */         name[1] = local.intern();
/*     */         name[2] = qName.intern();
/*     */       } 
/*     */       table.put(name[2], name);
/*     */       this.tablesDirty = true;
/*     */       return name;
/*     */     }
/*     */     
/*     */     String getURI(String prefix) {
/*     */       if ("".equals(prefix))
/*     */         return this.defaultNS; 
/*     */       if (this.prefixTable == null)
/*     */         return null; 
/*     */       return (String)this.prefixTable.get(prefix);
/*     */     }
/*     */     
/*     */     String getPrefix(String uri) {
/*     */       if (this.uriTable == null)
/*     */         return null; 
/*     */       return (String)this.uriTable.get(uri);
/*     */     }
/*     */     
/*     */     Iterable<String> getDeclaredPrefixes() {
/*     */       if (this.declarations == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.declarations;
/*     */     }
/*     */     
/*     */     Iterable<String> getPrefixes() {
/*     */       if (this.prefixTable == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.prefixTable.keySet();
/*     */     }
/*     */     
/*     */     private void copyTables() {
/*     */       if (this.prefixTable != null) {
/*     */         this.prefixTable = (HashMap)this.prefixTable.clone();
/*     */       } else {
/*     */         this.prefixTable = new HashMap<Object, Object>();
/*     */       } 
/*     */       if (this.uriTable != null) {
/*     */         this.uriTable = (HashMap)this.uriTable.clone();
/*     */       } else {
/*     */         this.uriTable = new HashMap<Object, Object>();
/*     */       } 
/*     */       this.elementNameTable = new HashMap<Object, Object>();
/*     */       this.attributeNameTable = new HashMap<Object, Object>();
/*     */       this.tablesDirty = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\NamespaceSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
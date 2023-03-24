/*     */ package com.sun.xml.rpc.sp;
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
/*     */ public class NamespaceSupport
/*     */ {
/*     */   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
/* 107 */   private static final Iterator EMPTY_ENUMERATION = (new ArrayList()).iterator();
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
/* 118 */     reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceSupport(NamespaceSupport that) {
/* 123 */     this.contexts = new Context[that.contexts.length];
/* 124 */     this.currentContext = null;
/* 125 */     this.contextPos = that.contextPos;
/*     */     
/* 127 */     Context currentParent = null;
/*     */     
/* 129 */     for (int i = 0; i < that.contexts.length; i++) {
/* 130 */       Context thatContext = that.contexts[i];
/*     */       
/* 132 */       if (thatContext == null) {
/* 133 */         this.contexts[i] = null;
/*     */       }
/*     */       else {
/*     */         
/* 137 */         Context thisContext = new Context(thatContext, currentParent);
/* 138 */         this.contexts[i] = thisContext;
/* 139 */         if (that.currentContext == thatContext) {
/* 140 */           this.currentContext = thisContext;
/*     */         }
/*     */         
/* 143 */         currentParent = thisContext;
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
/* 158 */     this.contexts = new Context[32];
/* 159 */     this.contextPos = 0;
/* 160 */     this.contexts[this.contextPos] = this.currentContext = new Context();
/* 161 */     this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
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
/* 179 */     int max = this.contexts.length;
/* 180 */     this.contextPos++;
/*     */ 
/*     */     
/* 183 */     if (this.contextPos >= max) {
/* 184 */       Context[] newContexts = new Context[max * 2];
/* 185 */       System.arraycopy(this.contexts, 0, newContexts, 0, max);
/* 186 */       max *= 2;
/* 187 */       this.contexts = newContexts;
/*     */     } 
/*     */ 
/*     */     
/* 191 */     this.currentContext = this.contexts[this.contextPos];
/* 192 */     if (this.currentContext == null) {
/* 193 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/*     */     }
/*     */ 
/*     */     
/* 197 */     if (this.contextPos > 0) {
/* 198 */       this.currentContext.setParent(this.contexts[this.contextPos - 1]);
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
/* 216 */     this.contextPos--;
/* 217 */     if (this.contextPos < 0) {
/* 218 */       throw new EmptyStackException();
/*     */     }
/* 220 */     this.currentContext = this.contexts[this.contextPos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slideContextUp() {
/* 228 */     this.contextPos--;
/* 229 */     this.currentContext = this.contexts[this.contextPos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slideContextDown() {
/* 237 */     this.contextPos++;
/*     */     
/* 239 */     if (this.contexts[this.contextPos] == null)
/*     */     {
/* 241 */       this.contexts[this.contextPos] = this.contexts[this.contextPos - 1];
/*     */     }
/*     */     
/* 244 */     this.currentContext = this.contexts[this.contextPos];
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
/* 281 */     if ((prefix.equals("xml") && !uri.equals("http://www.w3.org/XML/1998/namespace")) || prefix.equals("xmlns"))
/*     */     {
/* 283 */       return false;
/*     */     }
/* 285 */     this.currentContext.declarePrefix(prefix, uri);
/* 286 */     return true;
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
/* 332 */     String[] myParts = this.currentContext.processName(qName, isAttribute);
/* 333 */     if (myParts == null) {
/* 334 */       return null;
/*     */     }
/* 336 */     parts[0] = myParts[0];
/* 337 */     parts[1] = myParts[1];
/* 338 */     parts[2] = myParts[2];
/* 339 */     return parts;
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
/* 356 */     return this.currentContext.getURI(prefix);
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
/*     */   public Iterator getPrefixes() {
/* 373 */     return this.currentContext.getPrefixes();
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
/*     */   public String getPrefix(String uri) {
/* 398 */     return this.currentContext.getPrefix(uri);
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
/* 423 */     List<String> prefixes = new ArrayList();
/* 424 */     Iterator<String> allPrefixes = getPrefixes();
/* 425 */     while (allPrefixes.hasNext()) {
/* 426 */       String prefix = allPrefixes.next();
/* 427 */       if (uri.equals(getURI(prefix))) {
/* 428 */         prefixes.add(prefix);
/*     */       }
/*     */     } 
/* 431 */     return prefixes.iterator();
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
/*     */   public Iterator getDeclaredPrefixes() {
/* 447 */     return this.currentContext.getDeclaredPrefixes();
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
/* 741 */       this.defaultNS = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 748 */       this.declarations = null;
/* 749 */       this.tablesDirty = false;
/* 750 */       this.parent = null; copyTables(); } Context(Context that, Context newParent) { this.defaultNS = null; this.declarations = null; this.tablesDirty = false; this.parent = null;
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
/*     */     Iterator getDeclaredPrefixes() {
/*     */       if (this.declarations == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.declarations.iterator();
/*     */     }
/*     */     
/*     */     Iterator getPrefixes() {
/*     */       if (this.prefixTable == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.prefixTable.keySet().iterator();
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\NamespaceSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
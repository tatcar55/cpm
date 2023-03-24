/*     */ package com.sun.xml.txw2;
/*     */ 
/*     */ import com.sun.xml.txw2.output.XmlSerializer;
/*     */ import java.util.HashMap;
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
/*     */ public final class Document
/*     */ {
/*     */   private final XmlSerializer out;
/*     */   private boolean started = false;
/*  71 */   private Content current = null;
/*     */   
/*  73 */   private final Map<Class, DatatypeWriter> datatypeWriters = (Map)new HashMap<Class<?>, DatatypeWriter>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private int iota = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private final NamespaceSupport inscopeNamespace = new NamespaceSupport();
/*     */ 
/*     */   
/*     */   private NamespaceDecl activeNamespaces;
/*     */ 
/*     */   
/*     */   private final ContentVisitor visitor;
/*     */   
/*     */   private final StringBuilder prefixSeed;
/*     */   
/*     */   private int prefixIota;
/*     */   
/*     */   static final char MAGIC = '\000';
/*     */ 
/*     */   
/*     */   void flush() {
/*  99 */     this.out.flush();
/*     */   }
/*     */   
/*     */   void setFirstContent(Content c) {
/* 103 */     assert this.current == null;
/* 104 */     this.current = new StartDocument();
/* 105 */     this.current.setNext(this, c);
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
/*     */   public void addDatatypeWriter(DatatypeWriter<?> dw) {
/* 120 */     this.datatypeWriters.put(dw.getType(), dw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void run() {
/*     */     while (true) {
/* 128 */       Content next = this.current.getNext();
/* 129 */       if (next == null || !next.isReadyToCommit())
/*     */         return; 
/* 131 */       next.accept(this.visitor);
/* 132 */       next.written();
/* 133 */       this.current = next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void writeValue(Object obj, NamespaceResolver nsResolver, StringBuilder buf) {
/* 144 */     if (obj == null) {
/* 145 */       throw new IllegalArgumentException("argument contains null");
/*     */     }
/* 147 */     if (obj instanceof Object[]) {
/* 148 */       for (Object o : (Object[])obj)
/* 149 */         writeValue(o, nsResolver, buf); 
/*     */       return;
/*     */     } 
/* 152 */     if (obj instanceof Iterable) {
/* 153 */       for (Object o : obj) {
/* 154 */         writeValue(o, nsResolver, buf);
/*     */       }
/*     */       return;
/*     */     } 
/* 158 */     if (buf.length() > 0) {
/* 159 */       buf.append(' ');
/*     */     }
/* 161 */     Class<?> c = obj.getClass();
/* 162 */     while (c != null) {
/* 163 */       DatatypeWriter<Object> dw = this.datatypeWriters.get(c);
/* 164 */       if (dw != null) {
/* 165 */         dw.print(obj, nsResolver, buf);
/*     */         return;
/*     */       } 
/* 168 */       c = c.getSuperclass();
/*     */     } 
/*     */ 
/*     */     
/* 172 */     buf.append(obj);
/*     */   }
/*     */   
/*     */   Document(XmlSerializer out) {
/* 176 */     this.visitor = new ContentVisitor()
/*     */       {
/*     */         
/*     */         public void onStartDocument()
/*     */         {
/* 181 */           throw new IllegalStateException();
/*     */         }
/*     */         
/*     */         public void onEndDocument() {
/* 185 */           Document.this.out.endDocument();
/*     */         }
/*     */         
/*     */         public void onEndTag() {
/* 189 */           Document.this.out.endTag();
/* 190 */           Document.this.inscopeNamespace.popContext();
/* 191 */           Document.this.activeNamespaces = null;
/*     */         }
/*     */         
/*     */         public void onPcdata(StringBuilder buffer) {
/* 195 */           if (Document.this.activeNamespaces != null)
/* 196 */             buffer = Document.this.fixPrefix(buffer); 
/* 197 */           Document.this.out.text(buffer);
/*     */         }
/*     */         
/*     */         public void onCdata(StringBuilder buffer) {
/* 201 */           if (Document.this.activeNamespaces != null)
/* 202 */             buffer = Document.this.fixPrefix(buffer); 
/* 203 */           Document.this.out.cdata(buffer);
/*     */         }
/*     */         
/*     */         public void onComment(StringBuilder buffer) {
/* 207 */           if (Document.this.activeNamespaces != null)
/* 208 */             buffer = Document.this.fixPrefix(buffer); 
/* 209 */           Document.this.out.comment(buffer);
/*     */         }
/*     */         
/*     */         public void onStartTag(String nsUri, String localName, Attribute attributes, NamespaceDecl namespaces) {
/* 213 */           assert nsUri != null;
/* 214 */           assert localName != null;
/*     */           
/* 216 */           Document.this.activeNamespaces = namespaces;
/*     */           
/* 218 */           if (!Document.this.started) {
/* 219 */             Document.this.started = true;
/* 220 */             Document.this.out.startDocument();
/*     */           } 
/*     */           
/* 223 */           Document.this.inscopeNamespace.pushContext();
/*     */           
/*     */           NamespaceDecl ns;
/* 226 */           for (ns = namespaces; ns != null; ns = ns.next) {
/* 227 */             ns.declared = false;
/*     */             
/* 229 */             if (ns.prefix != null) {
/* 230 */               String uri = Document.this.inscopeNamespace.getURI(ns.prefix);
/* 231 */               if (uri == null || !uri.equals(ns.uri)) {
/*     */ 
/*     */ 
/*     */                 
/* 235 */                 Document.this.inscopeNamespace.declarePrefix(ns.prefix, ns.uri);
/* 236 */                 ns.declared = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 242 */           for (ns = namespaces; ns != null; ns = ns.next) {
/* 243 */             if (ns.prefix == null) {
/* 244 */               if (Document.this.inscopeNamespace.getURI("").equals(ns.uri)) {
/* 245 */                 ns.prefix = "";
/*     */               } else {
/* 247 */                 String p = Document.this.inscopeNamespace.getPrefix(ns.uri);
/* 248 */                 if (p == null) {
/*     */                   
/* 250 */                   while (Document.this.inscopeNamespace.getURI(p = Document.this.newPrefix()) != null);
/*     */                   
/* 252 */                   ns.declared = true;
/* 253 */                   Document.this.inscopeNamespace.declarePrefix(p, ns.uri);
/*     */                 } 
/* 255 */                 ns.prefix = p;
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 261 */           assert namespaces.uri.equals(nsUri);
/* 262 */           assert namespaces.prefix != null : "a prefix must have been all allocated";
/* 263 */           Document.this.out.beginStartTag(nsUri, localName, namespaces.prefix);
/*     */ 
/*     */           
/* 266 */           for (ns = namespaces; ns != null; ns = ns.next) {
/* 267 */             if (ns.declared) {
/* 268 */               Document.this.out.writeXmlns(ns.prefix, ns.uri);
/*     */             }
/*     */           } 
/*     */           
/* 272 */           for (Attribute a = attributes; a != null; a = a.next) {
/*     */             String prefix;
/* 274 */             if (a.nsUri.length() == 0) { prefix = ""; }
/* 275 */             else { prefix = Document.this.inscopeNamespace.getPrefix(a.nsUri); }
/* 276 */              Document.this.out.writeAttribute(a.nsUri, a.localName, prefix, Document.this.fixPrefix(a.value));
/*     */           } 
/*     */           
/* 279 */           Document.this.out.endStartTag(nsUri, localName, namespaces.prefix);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     this.prefixSeed = new StringBuilder("ns");
/*     */     
/* 288 */     this.prefixIota = 0;
/*     */     this.out = out;
/*     */     for (DatatypeWriter<?> dw : DatatypeWriter.BUILTIN)
/*     */       this.datatypeWriters.put(dw.getType(), dw); 
/*     */   }
/*     */   private String newPrefix() {
/* 294 */     this.prefixSeed.setLength(2);
/* 295 */     this.prefixSeed.append(++this.prefixIota);
/* 296 */     return this.prefixSeed.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder fixPrefix(StringBuilder buf) {
/* 307 */     assert this.activeNamespaces != null;
/*     */ 
/*     */     
/* 310 */     int len = buf.length(); int i;
/* 311 */     for (i = 0; i < len && 
/* 312 */       buf.charAt(i) != '\000'; i++);
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (i == len) {
/* 317 */       return buf;
/*     */     }
/* 319 */     while (i < len) {
/* 320 */       char uriIdx = buf.charAt(i + 1);
/* 321 */       NamespaceDecl ns = this.activeNamespaces;
/* 322 */       while (ns != null && ns.uniqueId != uriIdx)
/* 323 */         ns = ns.next; 
/* 324 */       if (ns == null) {
/* 325 */         throw new IllegalStateException("Unexpected use of prefixes " + buf);
/*     */       }
/* 327 */       int length = 2;
/* 328 */       String prefix = ns.prefix;
/* 329 */       if (prefix.length() == 0) {
/* 330 */         if (buf.length() <= i + 2 || buf.charAt(i + 2) != ':')
/* 331 */           throw new IllegalStateException("Unexpected use of prefixes " + buf); 
/* 332 */         length = 3;
/*     */       } 
/*     */       
/* 335 */       buf.replace(i, i + length, prefix);
/* 336 */       len += prefix.length() - length;
/*     */       
/* 338 */       while (i < len && buf.charAt(i) != '\000') {
/* 339 */         i++;
/*     */       }
/*     */     } 
/* 342 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   char assignNewId() {
/* 351 */     return (char)this.iota++;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\Document.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
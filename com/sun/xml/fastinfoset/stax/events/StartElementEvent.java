/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StartElementEvent
/*     */   extends EventBase
/*     */   implements StartElement
/*     */ {
/*     */   private Map _attributes;
/*     */   private List _namespaces;
/*  39 */   private NamespaceContext _context = null;
/*     */   private QName _qname;
/*     */   
/*     */   public void reset() {
/*  43 */     if (this._attributes != null) this._attributes.clear(); 
/*  44 */     if (this._namespaces != null) this._namespaces.clear(); 
/*  45 */     if (this._context != null) this._context = null; 
/*     */   }
/*     */   
/*     */   public StartElementEvent() {
/*  49 */     init();
/*     */   }
/*     */   
/*     */   public StartElementEvent(String prefix, String uri, String localpart) {
/*  53 */     init();
/*  54 */     if (uri == null) uri = ""; 
/*  55 */     if (prefix == null) prefix = ""; 
/*  56 */     this._qname = new QName(uri, localpart, prefix);
/*  57 */     setEventType(1);
/*     */   }
/*     */   
/*     */   public StartElementEvent(QName qname) {
/*  61 */     init();
/*  62 */     this._qname = qname;
/*     */   }
/*     */   
/*     */   public StartElementEvent(StartElement startelement) {
/*  66 */     this(startelement.getName());
/*  67 */     addAttributes(startelement.getAttributes());
/*  68 */     addNamespaces(startelement.getNamespaces());
/*     */   }
/*     */   
/*     */   protected void init() {
/*  72 */     setEventType(1);
/*  73 */     this._attributes = new HashMap<Object, Object>();
/*  74 */     this._namespaces = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/*  83 */     return this._qname;
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
/*     */   public Iterator getAttributes() {
/*  96 */     if (this._attributes != null) {
/*  97 */       Collection coll = this._attributes.values();
/*  98 */       return new ReadIterator(coll.iterator());
/*     */     } 
/* 100 */     return EmptyIterator.getInstance();
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
/*     */   public Iterator getNamespaces() {
/* 125 */     if (this._namespaces != null) {
/* 126 */       return new ReadIterator(this._namespaces.iterator());
/*     */     }
/* 128 */     return EmptyIterator.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute getAttributeByName(QName qname) {
/* 137 */     if (qname == null)
/* 138 */       return null; 
/* 139 */     return (Attribute)this._attributes.get(qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 150 */     return this._context;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(QName qname) {
/* 155 */     this._qname = qname;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespace() {
/* 160 */     return this._qname.getNamespaceURI();
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
/*     */   public String getNamespaceURI(String prefix) {
/* 172 */     if (getNamespace() != null) return getNamespace();
/*     */     
/* 174 */     if (this._context != null)
/* 175 */       return this._context.getNamespaceURI(prefix); 
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 180 */     String s = "<" + nameAsString();
/*     */     
/* 182 */     if (this._attributes != null) {
/* 183 */       Iterator<Attribute> it = getAttributes();
/* 184 */       Attribute attr = null;
/* 185 */       while (it.hasNext()) {
/* 186 */         attr = it.next();
/* 187 */         s = s + " " + attr.toString();
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     if (this._namespaces != null) {
/* 192 */       Iterator<Namespace> it = this._namespaces.iterator();
/* 193 */       Namespace attr = null;
/* 194 */       while (it.hasNext()) {
/* 195 */         attr = it.next();
/* 196 */         s = s + " " + attr.toString();
/*     */       } 
/*     */     } 
/* 199 */     s = s + ">";
/* 200 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nameAsString() {
/* 207 */     if ("".equals(this._qname.getNamespaceURI()))
/* 208 */       return this._qname.getLocalPart(); 
/* 209 */     if (this._qname.getPrefix() != null) {
/* 210 */       return "['" + this._qname.getNamespaceURI() + "']:" + this._qname.getPrefix() + ":" + this._qname.getLocalPart();
/*     */     }
/* 212 */     return "['" + this._qname.getNamespaceURI() + "']:" + this._qname.getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) {
/* 217 */     this._context = context;
/*     */   }
/*     */   
/*     */   public void addAttribute(Attribute attr) {
/* 221 */     this._attributes.put(attr.getName(), attr);
/*     */   }
/*     */   
/*     */   public void addAttributes(Iterator<Attribute> attrs) {
/* 225 */     if (attrs != null) {
/* 226 */       while (attrs.hasNext()) {
/* 227 */         Attribute attr = attrs.next();
/* 228 */         this._attributes.put(attr.getName(), attr);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void addNamespace(Namespace namespace) {
/* 234 */     if (namespace != null) {
/* 235 */       this._namespaces.add(namespace);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addNamespaces(Iterator<Namespace> namespaces) {
/* 240 */     if (namespaces != null)
/* 241 */       while (namespaces.hasNext()) {
/* 242 */         Namespace namespace = namespaces.next();
/* 243 */         this._namespaces.add(namespace);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\StartElementEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
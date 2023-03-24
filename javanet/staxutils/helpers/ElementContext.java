/*     */ package javanet.staxutils.helpers;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javanet.staxutils.SimpleNamespaceContext;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElementContext
/*     */   extends SimpleNamespaceContext
/*     */ {
/*     */   private QName name;
/*     */   private ElementContext parent;
/*     */   private List attributeNames;
/*     */   private Map attributes;
/*     */   private List namespacePrefixes;
/*     */   private boolean isEmpty;
/*     */   private boolean readOnly;
/*     */   
/*     */   public ElementContext(QName name) {
/*  87 */     this.name = name;
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
/*     */   public ElementContext(QName name, boolean isEmpty) {
/* 100 */     this.name = name;
/* 101 */     this.isEmpty = isEmpty;
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
/*     */   public ElementContext(QName name, NamespaceContext context) {
/* 114 */     super(context);
/* 115 */     this.name = name;
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
/*     */   public ElementContext(QName name, ElementContext parent) {
/* 128 */     super((NamespaceContext)parent);
/* 129 */     this.name = name;
/* 130 */     this.parent = parent;
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
/*     */   public ElementContext(QName name, ElementContext parent, boolean isEmpty) {
/* 144 */     super((NamespaceContext)parent);
/* 145 */     this.name = name;
/* 146 */     this.parent = parent;
/* 147 */     this.isEmpty = isEmpty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementContext getParentContext() {
/* 158 */     return this.parent;
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
/*     */   public boolean isRoot() {
/* 170 */     return (this.parent == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 181 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPath() {
/* 192 */     return appendPath(new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 201 */     return getPath();
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
/*     */   public StringBuffer appendPath(StringBuffer buffer) {
/* 213 */     if (this.parent != null)
/*     */     {
/* 215 */       this.parent.appendPath(buffer);
/*     */     }
/*     */     
/* 218 */     return buffer.append('/').append(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/* 229 */     if (this.parent == null)
/*     */     {
/* 231 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 235 */     return this.parent.getDepth() + 1;
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
/*     */   public ElementContext newSubContext(QName name) {
/* 250 */     if (!isEmpty())
/*     */     {
/* 252 */       return new ElementContext(name, this);
/*     */     }
/*     */ 
/*     */     
/* 256 */     throw new IllegalStateException("ElementContext is empty");
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
/*     */   public ElementContext newSubContext(QName name, boolean isEmpty) {
/* 273 */     if (!isEmpty())
/*     */     {
/* 275 */       return new ElementContext(name, this, isEmpty);
/*     */     }
/*     */ 
/*     */     
/* 279 */     throw new IllegalStateException("ElementContext is empty");
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
/*     */   public void putAttribute(QName name, String value) {
/* 294 */     if (isReadOnly())
/*     */     {
/* 296 */       throw new IllegalStateException("ElementContext is readOnly");
/*     */     }
/* 298 */     if (this.attributes == null) {
/*     */       
/* 300 */       this.attributes = new HashMap();
/* 301 */       this.attributeNames = new ArrayList();
/*     */     } 
/*     */ 
/*     */     
/* 305 */     this.attributeNames.add(name);
/* 306 */     this.attributes.put(name, value);
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
/*     */   public void putNamespace(String prefix, String nsURI) {
/* 319 */     if (isReadOnly())
/*     */     {
/* 321 */       throw new IllegalStateException("ElementContext is readOnly");
/*     */     }
/*     */ 
/*     */     
/* 325 */     if (this.namespacePrefixes == null)
/*     */     {
/* 327 */       this.namespacePrefixes = new ArrayList();
/*     */     }
/*     */ 
/*     */     
/* 331 */     if (prefix.length() == 0) {
/*     */ 
/*     */       
/* 334 */       this.namespacePrefixes.add(prefix);
/* 335 */       setDefaultNamespace(nsURI);
/*     */     }
/*     */     else {
/*     */       
/* 339 */       this.namespacePrefixes.add(prefix);
/* 340 */       setPrefix(prefix, nsURI);
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
/*     */   public int attributeCount() {
/* 353 */     if (this.attributes != null)
/*     */     {
/* 355 */       return this.attributes.size();
/*     */     }
/*     */ 
/*     */     
/* 359 */     return 0;
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
/*     */   public String getAttribute(int idx) {
/* 376 */     return getAttribute(getAttributeName(idx));
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
/*     */   public QName getAttributeName(int idx) {
/* 391 */     if (this.attributeNames != null)
/*     */     {
/* 393 */       return this.attributeNames.get(idx);
/*     */     }
/*     */ 
/*     */     
/* 397 */     throw new IndexOutOfBoundsException("Attribute index " + idx + " doesn't exist");
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
/*     */   public String getAttribute(QName name) {
/* 412 */     if (this.attributes != null)
/*     */     {
/* 414 */       return (String)this.attributes.get(name);
/*     */     }
/*     */ 
/*     */     
/* 418 */     return null;
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
/*     */   public boolean attributeExists(QName name) {
/* 433 */     if (this.attributes != null)
/*     */     {
/* 435 */       return this.attributes.containsKey(name);
/*     */     }
/*     */ 
/*     */     
/* 439 */     return false;
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
/*     */   public Iterator attributeNames() {
/* 455 */     if (this.attributeNames != null)
/*     */     {
/* 457 */       return Collections.unmodifiableList(this.attributeNames).iterator();
/*     */     }
/*     */ 
/*     */     
/* 461 */     return Collections.EMPTY_LIST.iterator();
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
/*     */   public int namespaceCount() {
/* 474 */     if (this.namespacePrefixes != null)
/*     */     {
/* 476 */       return this.namespacePrefixes.size();
/*     */     }
/*     */ 
/*     */     
/* 480 */     return 0;
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
/*     */   public String getNamespaceURI(int idx) {
/* 497 */     return getNamespaceURI(getNamespacePrefix(idx));
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
/*     */   public String getNamespacePrefix(int idx) {
/* 512 */     if (this.namespacePrefixes != null)
/*     */     {
/* 514 */       return this.namespacePrefixes.get(idx);
/*     */     }
/*     */ 
/*     */     
/* 518 */     throw new IndexOutOfBoundsException("Namespace index " + idx + " doesn't exist");
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
/*     */   public boolean isReadOnly() {
/* 533 */     return this.readOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadOnly() {
/* 542 */     this.readOnly = true;
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
/*     */   public boolean isEmpty() {
/* 555 */     return this.isEmpty;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\ElementContext.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
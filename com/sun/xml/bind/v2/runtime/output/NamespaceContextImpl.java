/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.NamespaceContext2;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NamespaceContextImpl
/*     */   implements NamespaceContext2
/*     */ {
/*     */   private final XMLSerializer owner;
/*  72 */   private String[] prefixes = new String[4];
/*  73 */   private String[] nsUris = new String[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Element current;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Element top;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private NamespacePrefixMapper prefixMapper = defaultNamespacePrefixMapper;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean collectionMode;
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContextImpl(XMLSerializer owner) {
/* 127 */     this.owner = owner;
/*     */     
/* 129 */     this.current = this.top = new Element(this, null);
/*     */     
/* 131 */     put("http://www.w3.org/XML/1998/namespace", "xml");
/*     */   }
/*     */   
/*     */   public void setPrefixMapper(NamespacePrefixMapper mapper) {
/* 135 */     if (mapper == null)
/* 136 */       mapper = defaultNamespacePrefixMapper; 
/* 137 */     this.prefixMapper = mapper;
/*     */   }
/*     */   
/*     */   public NamespacePrefixMapper getPrefixMapper() {
/* 141 */     return this.prefixMapper;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 145 */     this.current = this.top;
/* 146 */     this.size = 1;
/* 147 */     this.collectionMode = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int declareNsUri(String uri, String preferedPrefix, boolean requirePrefix) {
/* 155 */     preferedPrefix = this.prefixMapper.getPreferredPrefix(uri, preferedPrefix, requirePrefix);
/*     */     
/* 157 */     if (uri.length() == 0) {
/* 158 */       for (int j = this.size - 1; j >= 0; j--) {
/* 159 */         if (this.nsUris[j].length() == 0)
/* 160 */           return j; 
/* 161 */         if (this.prefixes[j].length() == 0) {
/*     */ 
/*     */           
/* 164 */           assert this.current.defaultPrefixIndex == -1 && this.current.oldDefaultNamespaceUriIndex == -1;
/*     */           
/* 166 */           String oldUri = this.nsUris[j];
/* 167 */           String[] knownURIs = this.owner.nameList.namespaceURIs;
/*     */           
/* 169 */           if (this.current.baseIndex <= j) {
/*     */ 
/*     */             
/* 172 */             this.nsUris[j] = "";
/*     */             
/* 174 */             int subst = put(oldUri, null);
/*     */ 
/*     */             
/* 177 */             for (int m = knownURIs.length - 1; m >= 0; m--) {
/* 178 */               if (knownURIs[m].equals(oldUri)) {
/* 179 */                 this.owner.knownUri2prefixIndexMap[m] = subst;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 183 */             if (this.current.elementLocalName != null) {
/* 184 */               this.current.setTagName(subst, this.current.elementLocalName, this.current.getOuterPeer());
/*     */             }
/* 186 */             return j;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 191 */           for (int k = knownURIs.length - 1; k >= 0; k--) {
/* 192 */             if (knownURIs[k].equals(oldUri)) {
/* 193 */               this.current.defaultPrefixIndex = j;
/* 194 */               this.current.oldDefaultNamespaceUriIndex = k;
/*     */ 
/*     */ 
/*     */               
/* 198 */               this.owner.knownUri2prefixIndexMap[k] = this.size;
/*     */               break;
/*     */             } 
/*     */           } 
/* 202 */           if (this.current.elementLocalName != null) {
/* 203 */             this.current.setTagName(this.size, this.current.elementLocalName, this.current.getOuterPeer());
/*     */           }
/*     */           
/* 206 */           put(this.nsUris[j], null);
/* 207 */           return put("", "");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 213 */       return put("", "");
/*     */     } 
/*     */     
/* 216 */     for (int i = this.size - 1; i >= 0; i--) {
/* 217 */       String p = this.prefixes[i];
/* 218 */       if (this.nsUris[i].equals(uri) && (
/* 219 */         !requirePrefix || p.length() > 0)) {
/* 220 */         return i;
/*     */       }
/*     */       
/* 223 */       if (p.equals(preferedPrefix))
/*     */       {
/* 225 */         preferedPrefix = null;
/*     */       }
/*     */     } 
/*     */     
/* 229 */     if (preferedPrefix == null && requirePrefix)
/*     */     {
/*     */       
/* 232 */       preferedPrefix = makeUniquePrefix();
/*     */     }
/*     */ 
/*     */     
/* 236 */     return put(uri, preferedPrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int force(@NotNull String uri, @NotNull String prefix) {
/* 243 */     for (int i = this.size - 1; i >= 0; i--) {
/* 244 */       if (this.prefixes[i].equals(prefix)) {
/* 245 */         if (this.nsUris[i].equals(uri)) {
/* 246 */           return i;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     return put(uri, prefix);
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
/*     */   public int put(@NotNull String uri, @Nullable String prefix) {
/* 269 */     if (this.size == this.nsUris.length) {
/*     */       
/* 271 */       String[] u = new String[this.nsUris.length * 2];
/* 272 */       String[] p = new String[this.prefixes.length * 2];
/* 273 */       System.arraycopy(this.nsUris, 0, u, 0, this.nsUris.length);
/* 274 */       System.arraycopy(this.prefixes, 0, p, 0, this.prefixes.length);
/* 275 */       this.nsUris = u;
/* 276 */       this.prefixes = p;
/*     */     } 
/* 278 */     if (prefix == null) {
/* 279 */       if (this.size == 1) {
/* 280 */         prefix = "";
/*     */       } else {
/*     */         
/* 283 */         prefix = makeUniquePrefix();
/*     */       } 
/*     */     }
/* 286 */     this.nsUris[this.size] = uri;
/* 287 */     this.prefixes[this.size] = prefix;
/*     */     
/* 289 */     return this.size++;
/*     */   }
/*     */ 
/*     */   
/*     */   private String makeUniquePrefix() {
/* 294 */     String prefix = (new StringBuilder(5)).append("ns").append(this.size).toString();
/* 295 */     while (getNamespaceURI(prefix) != null) {
/* 296 */       prefix = prefix + '_';
/*     */     }
/* 298 */     return prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Element getCurrent() {
/* 303 */     return this.current;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrefixIndex(String uri) {
/* 311 */     for (int i = this.size - 1; i >= 0; i--) {
/* 312 */       if (this.nsUris[i].equals(uri))
/* 313 */         return i; 
/*     */     } 
/* 315 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(int prefixIndex) {
/* 324 */     return this.prefixes[prefixIndex];
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(int prefixIndex) {
/* 328 */     return this.nsUris[prefixIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 338 */     for (int i = this.size - 1; i >= 0; i--) {
/* 339 */       if (this.prefixes[i].equals(prefix))
/* 340 */         return this.nsUris[i]; 
/* 341 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) {
/* 349 */     if (this.collectionMode) {
/* 350 */       return declareNamespace(uri, null, false);
/*     */     }
/* 352 */     for (int i = this.size - 1; i >= 0; i--) {
/* 353 */       if (this.nsUris[i].equals(uri))
/* 354 */         return this.prefixes[i]; 
/* 355 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<String> getPrefixes(String uri) {
/* 360 */     String prefix = getPrefix(uri);
/* 361 */     if (prefix == null) {
/* 362 */       return Collections.<String>emptySet().iterator();
/*     */     }
/* 364 */     return Collections.<String>singleton(uri).iterator();
/*     */   }
/*     */   
/*     */   public String declareNamespace(String namespaceUri, String preferedPrefix, boolean requirePrefix) {
/* 368 */     int idx = declareNsUri(namespaceUri, preferedPrefix, requirePrefix);
/* 369 */     return getPrefix(idx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int count() {
/* 376 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final class Element
/*     */   {
/*     */     public final NamespaceContextImpl context;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Element prev;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Element next;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int oldDefaultNamespaceUriIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int defaultPrefixIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int baseIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int depth;
/*     */ 
/*     */ 
/*     */     
/*     */     private int elementNamePrefix;
/*     */ 
/*     */ 
/*     */     
/*     */     private String elementLocalName;
/*     */ 
/*     */ 
/*     */     
/*     */     private Name elementName;
/*     */ 
/*     */ 
/*     */     
/*     */     private Object outerPeer;
/*     */ 
/*     */ 
/*     */     
/*     */     private Object innerPeer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Element(NamespaceContextImpl context, Element prev) {
/* 441 */       this.context = context;
/* 442 */       this.prev = prev;
/* 443 */       this.depth = (prev == null) ? 0 : (prev.depth + 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRootElement() {
/* 451 */       return (this.depth == 1);
/*     */     }
/*     */     
/*     */     public Element push() {
/* 455 */       if (this.next == null)
/* 456 */         this.next = new Element(this.context, this); 
/* 457 */       this.next.onPushed();
/* 458 */       return this.next;
/*     */     }
/*     */     
/*     */     public Element pop() {
/* 462 */       if (this.oldDefaultNamespaceUriIndex >= 0)
/*     */       {
/* 464 */         this.context.owner.knownUri2prefixIndexMap[this.oldDefaultNamespaceUriIndex] = this.defaultPrefixIndex;
/*     */       }
/* 466 */       this.context.size = this.baseIndex;
/* 467 */       this.context.current = this.prev;
/*     */       
/* 469 */       this.outerPeer = this.innerPeer = null;
/* 470 */       return this.prev;
/*     */     }
/*     */     
/*     */     private void onPushed() {
/* 474 */       this.oldDefaultNamespaceUriIndex = this.defaultPrefixIndex = -1;
/* 475 */       this.baseIndex = this.context.size;
/* 476 */       this.context.current = this;
/*     */     }
/*     */     
/*     */     public void setTagName(int prefix, String localName, Object outerPeer) {
/* 480 */       assert localName != null;
/* 481 */       this.elementNamePrefix = prefix;
/* 482 */       this.elementLocalName = localName;
/* 483 */       this.elementName = null;
/* 484 */       this.outerPeer = outerPeer;
/*     */     }
/*     */     
/*     */     public void setTagName(Name tagName, Object outerPeer) {
/* 488 */       assert tagName != null;
/* 489 */       this.elementName = tagName;
/* 490 */       this.outerPeer = outerPeer;
/*     */     }
/*     */     
/*     */     public void startElement(XmlOutput out, Object innerPeer) throws IOException, XMLStreamException {
/* 494 */       this.innerPeer = innerPeer;
/* 495 */       if (this.elementName != null) {
/* 496 */         out.beginStartTag(this.elementName);
/*     */       } else {
/* 498 */         out.beginStartTag(this.elementNamePrefix, this.elementLocalName);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void endElement(XmlOutput out) throws IOException, SAXException, XMLStreamException {
/* 503 */       if (this.elementName != null) {
/* 504 */         out.endTag(this.elementName);
/* 505 */         this.elementName = null;
/*     */       } else {
/* 507 */         out.endTag(this.elementNamePrefix, this.elementLocalName);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int count() {
/* 515 */       return this.context.size - this.baseIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String getPrefix(int idx) {
/* 525 */       return this.context.prefixes[this.baseIndex + idx];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String getNsUri(int idx) {
/* 535 */       return this.context.nsUris[this.baseIndex + idx];
/*     */     }
/*     */     
/*     */     public int getBase() {
/* 539 */       return this.baseIndex;
/*     */     }
/*     */     
/*     */     public Object getOuterPeer() {
/* 543 */       return this.outerPeer;
/*     */     }
/*     */     
/*     */     public Object getInnerPeer() {
/* 547 */       return this.innerPeer;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Element getParent() {
/* 554 */       return this.prev;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 563 */   private static final NamespacePrefixMapper defaultNamespacePrefixMapper = new NamespacePrefixMapper() {
/*     */       public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/* 565 */         if (namespaceUri.equals("http://www.w3.org/2001/XMLSchema-instance"))
/* 566 */           return "xsi"; 
/* 567 */         if (namespaceUri.equals("http://www.w3.org/2001/XMLSchema"))
/* 568 */           return "xs"; 
/* 569 */         if (namespaceUri.equals("http://www.w3.org/2005/05/xmlmime"))
/* 570 */           return "xmime"; 
/* 571 */         return suggestion;
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\NamespaceContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
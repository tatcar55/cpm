/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javanet.staxutils.NamespaceContextAdapter;
/*     */ import javanet.staxutils.StaticNamespaceContext;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends AbstractXMLEvent
/*     */   implements StartElement
/*     */ {
/*     */   protected QName name;
/*     */   protected Map attributes;
/*     */   protected Map namespaces;
/*     */   protected NamespaceContext namespaceCtx;
/*     */   
/*     */   public StartElementEvent(QName name, NamespaceContext namespaceCtx, Location location) {
/*  79 */     super(location);
/*  80 */     this.name = name;
/*  81 */     this.namespaceCtx = (NamespaceContext)new StartElementContext(this, namespaceCtx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElementEvent(QName name, Iterator attributes, Iterator namespaces, NamespaceContext namespaceCtx, Location location, QName schemaType) {
/*  89 */     super(location, schemaType);
/*  90 */     this.namespaceCtx = (NamespaceContext)new StartElementContext(this, namespaceCtx);
/*     */     
/*  92 */     mergeNamespaces(namespaces);
/*  93 */     mergeAttributes(attributes);
/*     */     
/*  95 */     QName newName = processQName(name);
/*  96 */     this.name = (newName == null) ? name : newName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElementEvent(StartElement that) {
/* 102 */     this(that.getName(), that.getAttributes(), that.getNamespaces(), that.getNamespaceContext(), that.getLocation(), that.getSchemaType());
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
/*     */   public int getEventType() {
/* 114 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 120 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute getAttributeByName(QName name) {
/* 126 */     if (this.attributes != null)
/*     */     {
/* 128 */       return (Attribute)this.attributes.get(name);
/*     */     }
/*     */ 
/*     */     
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getAttributes() {
/* 140 */     if (this.attributes != null)
/*     */     {
/* 142 */       return this.attributes.values().iterator();
/*     */     }
/*     */ 
/*     */     
/* 146 */     return Collections.EMPTY_LIST.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 154 */     return this.namespaceCtx;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getNamespaces() {
/* 160 */     if (this.namespaces != null)
/*     */     {
/* 162 */       return this.namespaces.values().iterator();
/*     */     }
/*     */ 
/*     */     
/* 166 */     return Collections.EMPTY_LIST.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 174 */     return getNamespaceContext().getNamespaceURI(prefix);
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
/*     */   private void mergeAttributes(Iterator iter) {
/* 187 */     if (iter == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 193 */     while (iter.hasNext()) {
/*     */       
/* 195 */       Attribute attr = iter.next();
/*     */       
/* 197 */       if (this.attributes == null)
/*     */       {
/* 199 */         this.attributes = new HashMap();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 204 */       QName attrName = attr.getName();
/* 205 */       QName newName = processQName(attrName);
/* 206 */       if (newName != null) {
/*     */ 
/*     */         
/* 209 */         Attribute newAttr = new AttributeEvent(newName, null, attr);
/* 210 */         this.attributes.put(newName, newAttr);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 215 */       this.attributes.put(attrName, attr);
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
/*     */   private void mergeNamespaces(Iterator iter) {
/* 231 */     if (iter == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     while (iter.hasNext()) {
/*     */       
/* 240 */       Namespace ns = iter.next();
/* 241 */       String prefix = ns.getPrefix();
/*     */       
/* 243 */       if (this.namespaces == null)
/*     */       {
/* 245 */         this.namespaces = new HashMap();
/*     */       }
/*     */ 
/*     */       
/* 249 */       if (!this.namespaces.containsKey(prefix))
/*     */       {
/* 251 */         this.namespaces.put(prefix, ns);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private QName processQName(QName name) {
/* 269 */     String nsURI = name.getNamespaceURI();
/* 270 */     String prefix = name.getPrefix();
/*     */     
/* 272 */     if (nsURI == null || nsURI.length() == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 280 */       if (prefix != null && prefix.length() > 0)
/*     */       {
/* 282 */         return new QName(name.getLocalPart());
/*     */       }
/*     */ 
/*     */       
/* 286 */       return name;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     String resolvedNS = this.namespaceCtx.getNamespaceURI(prefix);
/* 295 */     if (resolvedNS == null) {
/*     */ 
/*     */       
/* 298 */       if (prefix != null && prefix.length() > 0) {
/*     */         
/* 300 */         if (this.namespaces == null)
/*     */         {
/* 302 */           this.namespaces = new HashMap();
/*     */         }
/*     */         
/* 305 */         this.namespaces.put(prefix, new NamespaceEvent(prefix, nsURI));
/*     */       } 
/*     */ 
/*     */       
/* 309 */       return null;
/*     */     } 
/* 311 */     if (!resolvedNS.equals(nsURI)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 316 */       String newPrefix = this.namespaceCtx.getPrefix(nsURI);
/* 317 */       if (newPrefix == null)
/*     */       {
/*     */         
/* 320 */         newPrefix = generatePrefix(nsURI);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 325 */       return new QName(nsURI, name.getLocalPart(), newPrefix);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 330 */     return null;
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
/*     */   private String generatePrefix(String nsURI) {
/*     */     String newPrefix;
/* 346 */     int nsCount = 0;
/*     */     
/*     */     do {
/* 349 */       newPrefix = "ns" + nsCount;
/* 350 */       nsCount++;
/*     */     }
/* 352 */     while (this.namespaceCtx.getNamespaceURI(newPrefix) != null);
/*     */     
/* 354 */     if (this.namespaces == null)
/*     */     {
/* 356 */       this.namespaces = new HashMap();
/*     */     }
/*     */     
/* 359 */     this.namespaces.put(newPrefix, new NamespaceEvent(newPrefix, nsURI));
/*     */     
/* 361 */     return newPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class StartElementContext
/*     */     extends NamespaceContextAdapter
/*     */     implements StaticNamespaceContext
/*     */   {
/*     */     private final StartElementEvent this$0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StartElementContext(StartElementEvent this$0, NamespaceContext namespaceCtx) {
/* 378 */       super(namespaceCtx);
/*     */       this.this$0 = this$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getNamespaceURI(String prefix) {
/* 384 */       if (this.this$0.namespaces != null && this.this$0.namespaces.containsKey(prefix)) {
/*     */         
/* 386 */         Namespace namespace = (Namespace)this.this$0.namespaces.get(prefix);
/* 387 */         return namespace.getNamespaceURI();
/*     */       } 
/*     */ 
/*     */       
/* 391 */       return super.getNamespaceURI(prefix);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPrefix(String nsURI) {
/* 399 */       for (Iterator i = this.this$0.getNamespaces(); i.hasNext(); ) {
/*     */         
/* 401 */         Namespace ns = i.next();
/* 402 */         if (ns.getNamespaceURI().equals(nsURI))
/*     */         {
/* 404 */           return ns.getPrefix();
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 410 */       return super.getPrefix(nsURI);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator getPrefixes(String nsURI) {
/* 417 */       List prefixes = null;
/*     */ 
/*     */       
/* 420 */       if (this.this$0.namespaces != null)
/*     */       {
/* 422 */         for (Iterator i = this.this$0.namespaces.values().iterator(); i.hasNext(); ) {
/*     */           
/* 424 */           Namespace ns = i.next();
/* 425 */           if (ns.getNamespaceURI().equals(nsURI)) {
/*     */             
/* 427 */             if (prefixes == null)
/*     */             {
/* 429 */               prefixes = new ArrayList();
/*     */             }
/*     */ 
/*     */             
/* 433 */             String prefix = ns.getPrefix();
/* 434 */             prefixes.add(prefix);
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 443 */       Iterator parentPrefixes = super.getPrefixes(nsURI);
/* 444 */       while (parentPrefixes.hasNext()) {
/*     */         
/* 446 */         String prefix = parentPrefixes.next();
/*     */ 
/*     */         
/* 449 */         if (this.this$0.namespaces != null && !this.this$0.namespaces.containsKey(prefix)) {
/*     */           
/* 451 */           if (prefixes == null)
/*     */           {
/* 453 */             prefixes = new ArrayList();
/*     */           }
/*     */           
/* 456 */           prefixes.add(prefix);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 462 */       return (prefixes == null) ? Collections.EMPTY_LIST.iterator() : prefixes.iterator();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\StartElementEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
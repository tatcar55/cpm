/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleNamespaceContext
/*     */   implements ExtendedNamespaceContext, StaticNamespaceContext
/*     */ {
/*     */   protected NamespaceContext parent;
/*  67 */   protected Map namespaces = new LinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleNamespaceContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleNamespaceContext(Map namespaces) {
/*  85 */     if (namespaces != null)
/*     */     {
/*  87 */       this.namespaces.putAll(namespaces);
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
/*     */   public SimpleNamespaceContext(NamespaceContext parent) {
/* 102 */     this.parent = parent;
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
/*     */   public SimpleNamespaceContext(NamespaceContext parent, Map namespaces) {
/* 116 */     this.parent = parent;
/*     */     
/* 118 */     if (namespaces != null)
/*     */     {
/* 120 */       this.namespaces.putAll(namespaces);
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
/*     */   public NamespaceContext getParent() {
/* 134 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(NamespaceContext parent) {
/* 145 */     this.parent = parent;
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
/*     */   public boolean isRootContext() {
/* 157 */     return (this.parent == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 163 */     if (prefix == null)
/*     */     {
/* 165 */       throw new IllegalArgumentException("prefix argument was null");
/*     */     }
/* 167 */     if (prefix.equals("xml"))
/*     */     {
/* 169 */       return "http://www.w3.org/XML/1998/namespace";
/*     */     }
/* 171 */     if (prefix.equals("xmlns"))
/*     */     {
/* 173 */       return "http://www.w3.org/2000/xmlns/";
/*     */     }
/* 175 */     if (this.namespaces.containsKey(prefix)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       String uri = (String)this.namespaces.get(prefix);
/* 182 */       if (uri.length() == 0)
/*     */       {
/* 184 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 188 */       return uri;
/*     */     } 
/*     */ 
/*     */     
/* 192 */     if (this.parent != null)
/*     */     {
/* 194 */       return this.parent.getNamespaceURI(prefix);
/*     */     }
/*     */ 
/*     */     
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String nsURI) {
/* 206 */     if (nsURI == null)
/*     */     {
/* 208 */       throw new IllegalArgumentException("nsURI was null");
/*     */     }
/* 210 */     if (nsURI.length() == 0)
/*     */     {
/* 212 */       throw new IllegalArgumentException("nsURI was empty");
/*     */     }
/* 214 */     if (nsURI.equals("http://www.w3.org/XML/1998/namespace"))
/*     */     {
/* 216 */       return "xml";
/*     */     }
/* 218 */     if (nsURI.equals("http://www.w3.org/2000/xmlns/"))
/*     */     {
/* 220 */       return "xmlns";
/*     */     }
/*     */ 
/*     */     
/* 224 */     Iterator iter = this.namespaces.entrySet().iterator();
/* 225 */     while (iter.hasNext()) {
/*     */       
/* 227 */       Map.Entry entry = iter.next();
/* 228 */       String uri = (String)entry.getValue();
/*     */       
/* 230 */       if (uri.equals(nsURI))
/*     */       {
/* 232 */         return (String)entry.getKey();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 238 */     if (this.parent != null)
/*     */     {
/* 240 */       return this.parent.getPrefix(nsURI);
/*     */     }
/* 242 */     if (nsURI.length() == 0)
/*     */     {
/* 244 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 248 */     return null;
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
/*     */   public boolean isPrefixDeclared(String prefix) {
/* 264 */     return this.namespaces.containsKey(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getDeclaredPrefixes() {
/* 270 */     return Collections.unmodifiableCollection(this.namespaces.keySet()).iterator();
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
/*     */   public int getDeclaredPrefixCount() {
/* 282 */     return this.namespaces.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getPrefixes() {
/* 288 */     if (this.parent == null || !(this.parent instanceof ExtendedNamespaceContext))
/*     */     {
/* 290 */       return getDeclaredPrefixes();
/*     */     }
/*     */ 
/*     */     
/* 294 */     Set prefixes = new HashSet(this.namespaces.keySet());
/*     */     
/* 296 */     ExtendedNamespaceContext superCtx = (ExtendedNamespaceContext)this.parent;
/* 297 */     for (Iterator i = superCtx.getPrefixes(); i.hasNext(); ) {
/*     */       
/* 299 */       String prefix = i.next();
/* 300 */       prefixes.add(prefix);
/*     */     } 
/*     */ 
/*     */     
/* 304 */     return prefixes.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getPrefixes(String nsURI) {
/* 312 */     if (nsURI == null)
/*     */     {
/* 314 */       throw new IllegalArgumentException("nsURI was null");
/*     */     }
/* 316 */     if (nsURI.equals("http://www.w3.org/XML/1998/namespace"))
/*     */     {
/* 318 */       return Collections.<String>singleton("xml").iterator();
/*     */     }
/* 320 */     if (nsURI.equals("http://www.w3.org/2000/xmlns/"))
/*     */     {
/* 322 */       return Collections.<String>singleton("xmlns").iterator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 327 */     Set prefixes = null;
/* 328 */     Iterator iter = this.namespaces.entrySet().iterator();
/* 329 */     while (iter.hasNext()) {
/*     */       
/* 331 */       Map.Entry entry = iter.next();
/* 332 */       String uri = (String)entry.getValue();
/* 333 */       if (uri.equals(nsURI)) {
/*     */         
/* 335 */         if (prefixes == null)
/*     */         {
/* 337 */           prefixes = new HashSet();
/*     */         }
/*     */         
/* 340 */         prefixes.add(entry.getKey());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 346 */     if (this.parent != null)
/*     */     {
/* 348 */       for (Iterator i = this.parent.getPrefixes(nsURI); i.hasNext(); ) {
/*     */         
/* 350 */         String prefix = i.next();
/*     */         
/* 352 */         if (prefixes == null)
/*     */         {
/* 354 */           prefixes = new HashSet();
/*     */         }
/*     */         
/* 357 */         prefixes.add(prefix);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 363 */     if (prefixes != null)
/*     */     {
/* 365 */       return Collections.<String>unmodifiableSet(prefixes).iterator();
/*     */     }
/* 367 */     if (nsURI.length() == 0)
/*     */     {
/* 369 */       return Collections.<String>singleton("").iterator();
/*     */     }
/*     */ 
/*     */     
/* 373 */     return Collections.EMPTY_LIST.iterator();
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
/*     */   public String setDefaultNamespace(String nsURI) {
/* 388 */     if (nsURI != null) {
/*     */       
/* 390 */       if (nsURI.equals("http://www.w3.org/XML/1998/namespace"))
/*     */       {
/* 392 */         throw new IllegalArgumentException("Attempt to map 'xml' uri");
/*     */       }
/* 394 */       if (nsURI.equals("http://www.w3.org/2000/xmlns/"))
/*     */       {
/* 396 */         throw new IllegalArgumentException("Attempt to map 'xmlns' uri");
/*     */       }
/*     */ 
/*     */       
/* 400 */       return this.namespaces.put("", nsURI);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     return this.namespaces.put("", "");
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
/*     */   public String setPrefix(String prefix, String nsURI) {
/* 422 */     if (prefix == null)
/*     */     {
/* 424 */       throw new NullPointerException("Namespace Prefix was null");
/*     */     }
/* 426 */     if (prefix.equals(""))
/*     */     {
/* 428 */       return setDefaultNamespace(nsURI);
/*     */     }
/* 430 */     if (prefix.equals("xml"))
/*     */     {
/* 432 */       throw new IllegalArgumentException("Attempt to map 'xml' prefix");
/*     */     }
/* 434 */     if (prefix.equals("xmlns"))
/*     */     {
/* 436 */       throw new IllegalArgumentException("Attempt to map 'xmlns' prefix");
/*     */     }
/* 438 */     if (nsURI != null) {
/*     */       
/* 440 */       if (nsURI.equals("http://www.w3.org/XML/1998/namespace"))
/*     */       {
/* 442 */         throw new IllegalArgumentException("Attempt to map 'xml' uri");
/*     */       }
/* 444 */       if (nsURI.equals("http://www.w3.org/2000/xmlns/"))
/*     */       {
/* 446 */         throw new IllegalArgumentException("Attempt to map 'xmlns' uri");
/*     */       }
/*     */ 
/*     */       
/* 450 */       return this.namespaces.put(prefix, nsURI);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     return this.namespaces.put(prefix, "");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\SimpleNamespaceContext.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.wss.impl.transform;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameSpaceSymbTable
/*     */ {
/*  83 */   HashMap<String, NameSpaceSymbEntry> symb = new HashMap<String, NameSpaceSymbEntry>();
/*     */   
/*  85 */   int nameSpaces = 0;
/*     */   
/*  87 */   List level = new ArrayList();
/*     */   
/*     */   boolean cloned = true;
/*     */   
/*     */   static final String XMLNS = "xmlns";
/*     */ 
/*     */   
/*     */   public NameSpaceSymbTable() {
/*  95 */     NameSpaceSymbEntry ne = new NameSpaceSymbEntry("", null, true);
/*  96 */     ne.lastrendered = "";
/*  97 */     this.symb.put("xmlns", ne);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getUnrenderedNodes(Collection<Attr> result) {
/* 108 */     Iterator<Map.Entry> it = this.symb.entrySet().iterator();
/* 109 */     while (it.hasNext()) {
/* 110 */       NameSpaceSymbEntry n = (NameSpaceSymbEntry)((Map.Entry)it.next()).getValue();
/*     */       
/* 112 */       if (!n.rendered && n.n != null) {
/* 113 */         result.add(n.n);
/* 114 */         n.rendered = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void outputNodePush() {
/* 124 */     this.nameSpaces++;
/* 125 */     push();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void outputNodePop() {
/* 132 */     this.nameSpaces--;
/* 133 */     pop();
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
/*     */   public void push() {
/* 147 */     this.level.add(null);
/* 148 */     this.cloned = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pop() {
/* 157 */     int size = this.level.size() - 1;
/* 158 */     Object ob = this.level.remove(size);
/* 159 */     if (ob != null)
/* 160 */     { this.symb = (HashMap<String, NameSpaceSymbEntry>)ob;
/* 161 */       if (size == 0) {
/* 162 */         this.cloned = false;
/*     */       } else {
/* 164 */         this.cloned = (this.level.get(size - 1) != this.symb);
/*     */       }  }
/* 166 */     else { this.cloned = false; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final void needsClone() {
/* 173 */     if (!this.cloned) {
/* 174 */       this.level.remove(this.level.size() - 1);
/* 175 */       this.level.add(this.symb);
/* 176 */       this.symb = (HashMap<String, NameSpaceSymbEntry>)this.symb.clone();
/* 177 */       this.cloned = true;
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
/*     */   public Attr getMapping(String prefix) {
/* 189 */     NameSpaceSymbEntry entry = this.symb.get(prefix);
/* 190 */     if (entry == null)
/*     */     {
/* 192 */       return null;
/*     */     }
/* 194 */     if (entry.rendered)
/*     */     {
/* 196 */       return null;
/*     */     }
/*     */     
/* 199 */     entry = (NameSpaceSymbEntry)entry.clone();
/* 200 */     needsClone();
/* 201 */     this.symb.put(prefix, entry);
/* 202 */     entry.rendered = true;
/* 203 */     entry.level = this.nameSpaces;
/* 204 */     entry.lastrendered = entry.uri;
/*     */     
/* 206 */     return entry.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr getMappingWithoutRendered(String prefix) {
/* 216 */     NameSpaceSymbEntry entry = this.symb.get(prefix);
/* 217 */     if (entry == null) {
/* 218 */       return null;
/*     */     }
/* 220 */     if (entry.rendered) {
/* 221 */       return null;
/*     */     }
/* 223 */     return entry.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addMapping(String prefix, String uri, Attr n) {
/* 234 */     NameSpaceSymbEntry ob = this.symb.get(prefix);
/* 235 */     if (ob != null && uri.equals(ob.uri))
/*     */     {
/* 237 */       return false;
/*     */     }
/*     */     
/* 240 */     NameSpaceSymbEntry ne = new NameSpaceSymbEntry(uri, n, false);
/* 241 */     needsClone();
/* 242 */     this.symb.put(prefix, ne);
/* 243 */     if (ob != null) {
/*     */ 
/*     */       
/* 246 */       ne.lastrendered = ob.lastrendered;
/* 247 */       if (ob.lastrendered != null && ob.lastrendered.equals(uri))
/*     */       {
/* 249 */         ne.rendered = true;
/*     */       }
/*     */     } 
/* 252 */     return true;
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
/*     */   public Node addMappingAndRender(String prefix, String uri, Attr n) {
/* 264 */     NameSpaceSymbEntry ob = this.symb.get(prefix);
/*     */     
/* 266 */     if (ob != null && uri.equals(ob.uri)) {
/* 267 */       if (!ob.rendered) {
/* 268 */         ob = (NameSpaceSymbEntry)ob.clone();
/* 269 */         needsClone();
/* 270 */         this.symb.put(prefix, ob);
/* 271 */         ob.lastrendered = uri;
/* 272 */         ob.rendered = true;
/* 273 */         return ob.n;
/*     */       } 
/* 275 */       return null;
/*     */     } 
/*     */     
/* 278 */     NameSpaceSymbEntry ne = new NameSpaceSymbEntry(uri, n, true);
/* 279 */     ne.lastrendered = uri;
/* 280 */     needsClone();
/* 281 */     this.symb.put(prefix, ne);
/* 282 */     if (ob != null)
/*     */     {
/* 284 */       if (ob.lastrendered != null && ob.lastrendered.equals(uri)) {
/* 285 */         ne.rendered = true;
/* 286 */         return null;
/*     */       } 
/*     */     }
/* 289 */     return ne.n;
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
/*     */   public Node addMappingAndRenderXNodeSet(String prefix, String uri, Attr n, boolean outputNode) {
/* 303 */     NameSpaceSymbEntry ob = this.symb.get(prefix);
/* 304 */     int visibleNameSpaces = this.nameSpaces;
/* 305 */     if (ob != null && uri.equals(ob.uri)) {
/* 306 */       if (!ob.rendered) {
/* 307 */         ob = (NameSpaceSymbEntry)ob.clone();
/* 308 */         needsClone();
/* 309 */         this.symb.put(prefix, ob);
/* 310 */         ob.rendered = true;
/* 311 */         ob.level = visibleNameSpaces;
/* 312 */         return ob.n;
/*     */       } 
/* 314 */       ob = (NameSpaceSymbEntry)ob.clone();
/* 315 */       needsClone();
/* 316 */       this.symb.put(prefix, ob);
/* 317 */       if (outputNode && (visibleNameSpaces - ob.level < 2 || "xmlns".equals(prefix))) {
/* 318 */         ob.level = visibleNameSpaces;
/* 319 */         return null;
/*     */       } 
/* 321 */       ob.level = visibleNameSpaces;
/* 322 */       return ob.n;
/*     */     } 
/*     */     
/* 325 */     NameSpaceSymbEntry ne = new NameSpaceSymbEntry(uri, n, true);
/* 326 */     ne.level = this.nameSpaces;
/* 327 */     ne.rendered = true;
/* 328 */     needsClone();
/* 329 */     this.symb.put(prefix, ne);
/* 330 */     if (ob != null) {
/* 331 */       ne.lastrendered = ob.lastrendered;
/*     */       
/* 333 */       if (ob.lastrendered != null && ob.lastrendered.equals(uri)) {
/* 334 */         ne.rendered = true;
/*     */       }
/*     */     } 
/* 337 */     return ne.n;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\NameSpaceSymbTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.txw2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StartTag
/*     */   extends Content
/*     */   implements NamespaceResolver
/*     */ {
/*     */   private String uri;
/*     */   private final String localName;
/*     */   private Attribute firstAtt;
/*     */   private Attribute lastAtt;
/*     */   private ContainerElement owner;
/*     */   private NamespaceDecl firstNs;
/*     */   private NamespaceDecl lastNs;
/*     */   final Document document;
/*     */   
/*     */   public StartTag(ContainerElement owner, String uri, String localName) {
/*  91 */     this(owner.document, uri, localName);
/*  92 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public StartTag(Document document, String uri, String localName) {
/*  96 */     assert uri != null;
/*  97 */     assert localName != null;
/*     */     
/*  99 */     this.uri = uri;
/* 100 */     this.localName = localName;
/* 101 */     this.document = document;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     addNamespaceDecl(uri, null, false);
/*     */   }
/*     */   
/*     */   public void addAttribute(String nsUri, String localName, Object arg) {
/* 110 */     checkWritable();
/*     */     
/*     */     Attribute a;
/*     */     
/* 114 */     for (a = this.firstAtt; a != null && 
/* 115 */       !a.hasName(nsUri, localName); a = a.next);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (a == null) {
/* 122 */       a = new Attribute(nsUri, localName);
/* 123 */       if (this.lastAtt == null) {
/* 124 */         assert this.firstAtt == null;
/* 125 */         this.firstAtt = this.lastAtt = a;
/*     */       } else {
/* 127 */         assert this.firstAtt != null;
/* 128 */         this.lastAtt.next = a;
/* 129 */         this.lastAtt = a;
/*     */       } 
/* 131 */       if (nsUri.length() > 0) {
/* 132 */         addNamespaceDecl(nsUri, null, true);
/*     */       }
/*     */     } 
/* 135 */     this.document.writeValue(arg, this, a.value);
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
/*     */   public NamespaceDecl addNamespaceDecl(String uri, String prefix, boolean requirePrefix) {
/* 158 */     checkWritable();
/*     */     
/* 160 */     if (uri == null)
/* 161 */       throw new IllegalArgumentException(); 
/* 162 */     if (uri.length() == 0) {
/* 163 */       if (requirePrefix)
/* 164 */         throw new IllegalArgumentException("The empty namespace cannot have a non-empty prefix"); 
/* 165 */       if (prefix != null && prefix.length() > 0)
/* 166 */         throw new IllegalArgumentException("The empty namespace can be only bound to the empty prefix"); 
/* 167 */       prefix = "";
/*     */     } 
/*     */ 
/*     */     
/* 171 */     for (NamespaceDecl n = this.firstNs; n != null; n = n.next) {
/* 172 */       if (uri.equals(n.uri)) {
/* 173 */         if (prefix == null) {
/*     */           
/* 175 */           n.requirePrefix |= requirePrefix;
/* 176 */           return n;
/*     */         } 
/* 178 */         if (n.prefix == null) {
/*     */           
/* 180 */           n.prefix = prefix;
/* 181 */           n.requirePrefix |= requirePrefix;
/* 182 */           return n;
/*     */         } 
/* 184 */         if (prefix.equals(n.prefix)) {
/*     */           
/* 186 */           n.requirePrefix |= requirePrefix;
/* 187 */           return n;
/*     */         } 
/*     */       } 
/* 190 */       if (prefix != null && n.prefix != null && n.prefix.equals(prefix)) {
/* 191 */         throw new IllegalArgumentException("Prefix '" + prefix + "' is already bound to '" + n.uri + '\'');
/*     */       }
/*     */     } 
/*     */     
/* 195 */     NamespaceDecl ns = new NamespaceDecl(this.document.assignNewId(), uri, prefix, requirePrefix);
/* 196 */     if (this.lastNs == null) {
/* 197 */       assert this.firstNs == null;
/* 198 */       this.firstNs = this.lastNs = ns;
/*     */     } else {
/* 200 */       assert this.firstNs != null;
/* 201 */       this.lastNs.next = ns;
/* 202 */       this.lastNs = ns;
/*     */     } 
/* 204 */     return ns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkWritable() {
/* 211 */     if (isWritten()) {
/* 212 */       throw new IllegalStateException("The start tag of " + this.localName + " has already been written. " + "If you need out of order writing, see the TypedXmlWriter.block method");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isWritten() {
/* 221 */     return (this.uri == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isReadyToCommit() {
/* 229 */     if (this.owner != null && this.owner.isBlocked()) {
/* 230 */       return false;
/*     */     }
/* 232 */     for (Content c = getNext(); c != null; c = c.getNext()) {
/* 233 */       if (c.concludesPendingStartTag())
/* 234 */         return true; 
/*     */     } 
/* 236 */     return false;
/*     */   }
/*     */   
/*     */   public void written() {
/* 240 */     this.firstAtt = this.lastAtt = null;
/* 241 */     this.uri = null;
/* 242 */     if (this.owner != null) {
/* 243 */       assert this.owner.startTag == this;
/* 244 */       this.owner.startTag = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean concludesPendingStartTag() {
/* 249 */     return true;
/*     */   }
/*     */   
/*     */   void accept(ContentVisitor visitor) {
/* 253 */     visitor.onStartTag(this.uri, this.localName, this.firstAtt, this.firstNs);
/*     */   }
/*     */   
/*     */   public String getPrefix(String nsUri) {
/* 257 */     NamespaceDecl ns = addNamespaceDecl(nsUri, null, false);
/* 258 */     if (ns.prefix != null)
/*     */     {
/* 260 */       return ns.prefix; } 
/* 261 */     return ns.dummyPrefix;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\StartTag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
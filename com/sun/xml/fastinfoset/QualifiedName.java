/*     */ package com.sun.xml.fastinfoset;
/*     */ 
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
/*     */ public class QualifiedName
/*     */ {
/*     */   public String prefix;
/*     */   public String namespaceName;
/*     */   public String localName;
/*     */   public String qName;
/*     */   public int index;
/*     */   public int prefixIndex;
/*     */   public int namespaceNameIndex;
/*     */   public int localNameIndex;
/*     */   public int attributeId;
/*     */   public int attributeHash;
/*     */   private QName qNameObject;
/*     */   
/*     */   public QualifiedName() {}
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName, String localName, String qName) {
/*  38 */     this.prefix = prefix;
/*  39 */     this.namespaceName = namespaceName;
/*  40 */     this.localName = localName;
/*  41 */     this.qName = qName;
/*  42 */     this.index = -1;
/*  43 */     this.prefixIndex = 0;
/*  44 */     this.namespaceNameIndex = 0;
/*  45 */     this.localNameIndex = -1;
/*     */   }
/*     */   
/*     */   public void set(String prefix, String namespaceName, String localName, String qName) {
/*  49 */     this.prefix = prefix;
/*  50 */     this.namespaceName = namespaceName;
/*  51 */     this.localName = localName;
/*  52 */     this.qName = qName;
/*  53 */     this.index = -1;
/*  54 */     this.prefixIndex = 0;
/*  55 */     this.namespaceNameIndex = 0;
/*  56 */     this.localNameIndex = -1;
/*  57 */     this.qNameObject = null;
/*     */   }
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName, String localName, String qName, int index) {
/*  61 */     this.prefix = prefix;
/*  62 */     this.namespaceName = namespaceName;
/*  63 */     this.localName = localName;
/*  64 */     this.qName = qName;
/*  65 */     this.index = index;
/*  66 */     this.prefixIndex = 0;
/*  67 */     this.namespaceNameIndex = 0;
/*  68 */     this.localNameIndex = -1;
/*     */   }
/*     */   
/*     */   public final QualifiedName set(String prefix, String namespaceName, String localName, String qName, int index) {
/*  72 */     this.prefix = prefix;
/*  73 */     this.namespaceName = namespaceName;
/*  74 */     this.localName = localName;
/*  75 */     this.qName = qName;
/*  76 */     this.index = index;
/*  77 */     this.prefixIndex = 0;
/*  78 */     this.namespaceNameIndex = 0;
/*  79 */     this.localNameIndex = -1;
/*  80 */     this.qNameObject = null;
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName, String localName, String qName, int index, int prefixIndex, int namespaceNameIndex, int localNameIndex) {
/*  86 */     this.prefix = prefix;
/*  87 */     this.namespaceName = namespaceName;
/*  88 */     this.localName = localName;
/*  89 */     this.qName = qName;
/*  90 */     this.index = index;
/*  91 */     this.prefixIndex = prefixIndex + 1;
/*  92 */     this.namespaceNameIndex = namespaceNameIndex + 1;
/*  93 */     this.localNameIndex = localNameIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public final QualifiedName set(String prefix, String namespaceName, String localName, String qName, int index, int prefixIndex, int namespaceNameIndex, int localNameIndex) {
/*  98 */     this.prefix = prefix;
/*  99 */     this.namespaceName = namespaceName;
/* 100 */     this.localName = localName;
/* 101 */     this.qName = qName;
/* 102 */     this.index = index;
/* 103 */     this.prefixIndex = prefixIndex + 1;
/* 104 */     this.namespaceNameIndex = namespaceNameIndex + 1;
/* 105 */     this.localNameIndex = localNameIndex;
/* 106 */     this.qNameObject = null;
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName, String localName) {
/* 111 */     this.prefix = prefix;
/* 112 */     this.namespaceName = namespaceName;
/* 113 */     this.localName = localName;
/* 114 */     this.qName = createQNameString(prefix, localName);
/* 115 */     this.index = -1;
/* 116 */     this.prefixIndex = 0;
/* 117 */     this.namespaceNameIndex = 0;
/* 118 */     this.localNameIndex = -1;
/*     */   }
/*     */   
/*     */   public final QualifiedName set(String prefix, String namespaceName, String localName) {
/* 122 */     this.prefix = prefix;
/* 123 */     this.namespaceName = namespaceName;
/* 124 */     this.localName = localName;
/* 125 */     this.qName = createQNameString(prefix, localName);
/* 126 */     this.index = -1;
/* 127 */     this.prefixIndex = 0;
/* 128 */     this.namespaceNameIndex = 0;
/* 129 */     this.localNameIndex = -1;
/* 130 */     this.qNameObject = null;
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName, String localName, int prefixIndex, int namespaceNameIndex, int localNameIndex, char[] charBuffer) {
/* 137 */     this.prefix = prefix;
/* 138 */     this.namespaceName = namespaceName;
/* 139 */     this.localName = localName;
/*     */     
/* 141 */     if (charBuffer != null) {
/* 142 */       int l1 = prefix.length();
/* 143 */       int l2 = localName.length();
/* 144 */       int total = l1 + l2 + 1;
/* 145 */       if (total < charBuffer.length) {
/* 146 */         prefix.getChars(0, l1, charBuffer, 0);
/* 147 */         charBuffer[l1] = ':';
/* 148 */         localName.getChars(0, l2, charBuffer, l1 + 1);
/* 149 */         this.qName = new String(charBuffer, 0, total);
/*     */       } else {
/* 151 */         this.qName = createQNameString(prefix, localName);
/*     */       } 
/*     */     } else {
/* 154 */       this.qName = this.localName;
/*     */     } 
/*     */     
/* 157 */     this.prefixIndex = prefixIndex + 1;
/* 158 */     this.namespaceNameIndex = namespaceNameIndex + 1;
/* 159 */     this.localNameIndex = localNameIndex;
/* 160 */     this.index = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final QualifiedName set(String prefix, String namespaceName, String localName, int prefixIndex, int namespaceNameIndex, int localNameIndex, char[] charBuffer) {
/* 166 */     this.prefix = prefix;
/* 167 */     this.namespaceName = namespaceName;
/* 168 */     this.localName = localName;
/*     */     
/* 170 */     if (charBuffer != null) {
/* 171 */       int l1 = prefix.length();
/* 172 */       int l2 = localName.length();
/* 173 */       int total = l1 + l2 + 1;
/* 174 */       if (total < charBuffer.length) {
/* 175 */         prefix.getChars(0, l1, charBuffer, 0);
/* 176 */         charBuffer[l1] = ':';
/* 177 */         localName.getChars(0, l2, charBuffer, l1 + 1);
/* 178 */         this.qName = new String(charBuffer, 0, total);
/*     */       } else {
/* 180 */         this.qName = createQNameString(prefix, localName);
/*     */       } 
/*     */     } else {
/* 183 */       this.qName = this.localName;
/*     */     } 
/*     */     
/* 186 */     this.prefixIndex = prefixIndex + 1;
/* 187 */     this.namespaceNameIndex = namespaceNameIndex + 1;
/* 188 */     this.localNameIndex = localNameIndex;
/* 189 */     this.index = -1;
/* 190 */     this.qNameObject = null;
/* 191 */     return this;
/*     */   }
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName, String localName, int index) {
/* 195 */     this.prefix = prefix;
/* 196 */     this.namespaceName = namespaceName;
/* 197 */     this.localName = localName;
/* 198 */     this.qName = createQNameString(prefix, localName);
/* 199 */     this.index = index;
/* 200 */     this.prefixIndex = 0;
/* 201 */     this.namespaceNameIndex = 0;
/* 202 */     this.localNameIndex = -1;
/*     */   }
/*     */   
/*     */   public final QualifiedName set(String prefix, String namespaceName, String localName, int index) {
/* 206 */     this.prefix = prefix;
/* 207 */     this.namespaceName = namespaceName;
/* 208 */     this.localName = localName;
/* 209 */     this.qName = createQNameString(prefix, localName);
/* 210 */     this.index = index;
/* 211 */     this.prefixIndex = 0;
/* 212 */     this.namespaceNameIndex = 0;
/* 213 */     this.localNameIndex = -1;
/* 214 */     this.qNameObject = null;
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName, String localName, int index, int prefixIndex, int namespaceNameIndex, int localNameIndex) {
/* 220 */     this.prefix = prefix;
/* 221 */     this.namespaceName = namespaceName;
/* 222 */     this.localName = localName;
/* 223 */     this.qName = createQNameString(prefix, localName);
/* 224 */     this.index = index;
/* 225 */     this.prefixIndex = prefixIndex + 1;
/* 226 */     this.namespaceNameIndex = namespaceNameIndex + 1;
/* 227 */     this.localNameIndex = localNameIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public final QualifiedName set(String prefix, String namespaceName, String localName, int index, int prefixIndex, int namespaceNameIndex, int localNameIndex) {
/* 232 */     this.prefix = prefix;
/* 233 */     this.namespaceName = namespaceName;
/* 234 */     this.localName = localName;
/* 235 */     this.qName = createQNameString(prefix, localName);
/* 236 */     this.index = index;
/* 237 */     this.prefixIndex = prefixIndex + 1;
/* 238 */     this.namespaceNameIndex = namespaceNameIndex + 1;
/* 239 */     this.localNameIndex = localNameIndex;
/* 240 */     this.qNameObject = null;
/* 241 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QualifiedName(String prefix, String namespaceName) {
/* 246 */     this.prefix = prefix;
/* 247 */     this.namespaceName = namespaceName;
/* 248 */     this.localName = "";
/* 249 */     this.qName = "";
/* 250 */     this.index = -1;
/* 251 */     this.prefixIndex = 0;
/* 252 */     this.namespaceNameIndex = 0;
/* 253 */     this.localNameIndex = -1;
/*     */   }
/*     */   
/*     */   public final QualifiedName set(String prefix, String namespaceName) {
/* 257 */     this.prefix = prefix;
/* 258 */     this.namespaceName = namespaceName;
/* 259 */     this.localName = "";
/* 260 */     this.qName = "";
/* 261 */     this.index = -1;
/* 262 */     this.prefixIndex = 0;
/* 263 */     this.namespaceNameIndex = 0;
/* 264 */     this.localNameIndex = -1;
/* 265 */     this.qNameObject = null;
/* 266 */     return this;
/*     */   }
/*     */   
/*     */   public final QName getQName() {
/* 270 */     if (this.qNameObject == null) {
/* 271 */       this.qNameObject = new QName(this.namespaceName, this.localName, this.prefix);
/*     */     }
/*     */     
/* 274 */     return this.qNameObject;
/*     */   }
/*     */   
/*     */   public final String getQNameString() {
/* 278 */     if (this.qName != "") {
/* 279 */       return this.qName;
/*     */     }
/*     */     
/* 282 */     return this.qName = createQNameString(this.prefix, this.localName);
/*     */   }
/*     */   
/*     */   public final void createAttributeValues(int size) {
/* 286 */     this.attributeId = this.localNameIndex | this.namespaceNameIndex << 20;
/* 287 */     this.attributeHash = this.localNameIndex % size;
/*     */   }
/*     */   
/*     */   private final String createQNameString(String p, String l) {
/* 291 */     if (p != null && p.length() > 0) {
/* 292 */       StringBuffer b = new StringBuffer(p);
/* 293 */       b.append(':');
/* 294 */       b.append(l);
/* 295 */       return b.toString();
/*     */     } 
/* 297 */     return l;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\QualifiedName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
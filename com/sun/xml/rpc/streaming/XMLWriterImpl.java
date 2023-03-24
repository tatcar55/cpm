/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.NamespaceSupport;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.xml.CDATA;
/*     */ import com.sun.xml.rpc.util.xml.XmlWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLWriterImpl
/*     */   extends XMLWriterBase
/*     */ {
/*     */   private XmlWriter _writer;
/*     */   
/*     */   public XMLWriterImpl(OutputStream out, String enc, boolean declare) {
/*     */     try {
/*  47 */       this._writer = new XmlWriter(out, enc, declare);
/*  48 */     } catch (IOException e) {
/*  49 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(String localName, String uri) {
/*     */     try {
/*  55 */       this._nsSupport.pushContext();
/*     */       
/*  57 */       if (!uri.equals("")) {
/*  58 */         String aPrefix = null;
/*  59 */         boolean mustDeclarePrefix = false;
/*     */         
/*  61 */         String defaultNamespaceURI = this._nsSupport.getPrefix("");
/*  62 */         if (defaultNamespaceURI != null && 
/*  63 */           uri.equals(defaultNamespaceURI)) {
/*  64 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/*  68 */         aPrefix = this._nsSupport.getPrefix(uri);
/*     */         
/*  70 */         if (aPrefix == null) {
/*  71 */           mustDeclarePrefix = true;
/*     */           
/*  73 */           if (this._prefixFactory != null) {
/*  74 */             aPrefix = this._prefixFactory.getPrefix(uri);
/*     */           }
/*     */           
/*  77 */           if (aPrefix == null) {
/*  78 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  84 */         String rawName = aPrefix.equals("") ? localName : (aPrefix + ":" + localName);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  89 */         this._writer.start(rawName);
/*  90 */         this._elemStack.push(rawName);
/*     */         
/*  92 */         if (mustDeclarePrefix) {
/*  93 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*  96 */         this._writer.start(localName);
/*  97 */         this._elemStack.push(localName);
/*     */       } 
/*  99 */     } catch (IOException e) {
/* 100 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(String localName, String uri, String prefix) {
/*     */     try {
/* 106 */       this._nsSupport.pushContext();
/*     */       
/* 108 */       if (!uri.equals("")) {
/* 109 */         String aPrefix = null;
/* 110 */         boolean mustDeclarePrefix = false;
/*     */         
/* 112 */         String defaultNamespaceURI = this._nsSupport.getPrefix("");
/* 113 */         if (defaultNamespaceURI != null && 
/* 114 */           uri.equals(defaultNamespaceURI)) {
/* 115 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/* 119 */         aPrefix = this._nsSupport.getPrefix(uri);
/*     */         
/* 121 */         if (aPrefix == null) {
/* 122 */           mustDeclarePrefix = true;
/*     */           
/* 124 */           aPrefix = prefix;
/*     */           
/* 126 */           if (aPrefix == null) {
/* 127 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 133 */         String rawName = aPrefix.equals("") ? localName : (aPrefix + ":" + localName);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 138 */         this._writer.start(rawName);
/* 139 */         this._elemStack.push(rawName);
/*     */         
/* 141 */         if (mustDeclarePrefix) {
/* 142 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/* 145 */         this._writer.start(localName);
/* 146 */         this._elemStack.push(localName);
/*     */       } 
/* 148 */     } catch (IOException e) {
/* 149 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeNamespaceDeclaration(String prefix, String uri) {
/*     */     try {
/* 155 */       this._nsSupport.declarePrefix(prefix, uri);
/*     */       
/* 157 */       if (prefix != null && !prefix.equals("")) {
/*     */         
/* 159 */         this._writer.attribute("xmlns", prefix, uri);
/*     */       } else {
/* 161 */         this._writer.attribute("xmlns", uri);
/*     */       }
/*     */     
/* 164 */     } catch (IOException e) {
/* 165 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String uri) {
/* 171 */     if (this._prefixFactory == null) {
/* 172 */       throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */     }
/*     */     
/* 175 */     String aPrefix = this._prefixFactory.getPrefix(uri);
/* 176 */     writeNamespaceDeclaration(aPrefix, uri);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localName, String uri, String value) {
/*     */     try {
/* 181 */       if (!uri.equals("")) {
/*     */         
/* 183 */         String aPrefix = null;
/* 184 */         boolean mustDeclarePrefix = false;
/*     */         
/* 186 */         String defaultNamespaceURI = this._nsSupport.getPrefix("");
/* 187 */         if (defaultNamespaceURI != null && 
/* 188 */           uri.equals(defaultNamespaceURI)) {
/* 189 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/* 193 */         aPrefix = this._nsSupport.getPrefix(uri);
/*     */         
/* 195 */         if (aPrefix == null) {
/* 196 */           mustDeclarePrefix = true;
/*     */           
/* 198 */           if (this._prefixFactory != null) {
/* 199 */             aPrefix = this._prefixFactory.getPrefix(uri);
/*     */           }
/*     */           
/* 202 */           if (aPrefix == null) {
/* 203 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 209 */         this._writer.attribute(aPrefix, localName, value);
/*     */         
/* 211 */         if (mustDeclarePrefix) {
/* 212 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*     */         
/* 216 */         this._writer.attribute(localName, value);
/*     */       } 
/* 218 */     } catch (IOException e) {
/* 219 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttributeUnquoted(String localName, String uri, String value) {
/*     */     try {
/* 228 */       if (!uri.equals("")) {
/*     */         
/* 230 */         String aPrefix = null;
/* 231 */         boolean mustDeclarePrefix = false;
/*     */         
/* 233 */         String defaultNamespaceURI = this._nsSupport.getPrefix("");
/* 234 */         if (defaultNamespaceURI != null && 
/* 235 */           uri.equals(defaultNamespaceURI)) {
/* 236 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/* 240 */         aPrefix = this._nsSupport.getPrefix(uri);
/*     */         
/* 242 */         if (aPrefix == null) {
/* 243 */           mustDeclarePrefix = true;
/*     */           
/* 245 */           if (this._prefixFactory != null) {
/* 246 */             aPrefix = this._prefixFactory.getPrefix(uri);
/*     */           }
/*     */           
/* 249 */           if (aPrefix == null) {
/* 250 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 256 */         this._writer.attributeUnquoted(aPrefix, localName, value);
/*     */         
/* 258 */         if (mustDeclarePrefix) {
/* 259 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*     */         
/* 263 */         this._writer.attributeUnquoted(localName, value);
/*     */       } 
/* 265 */     } catch (IOException e) {
/* 266 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeChars(CDATA chars) {
/*     */     try {
/* 272 */       this._writer.chars(chars);
/* 273 */     } catch (IOException e) {
/* 274 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeChars(String chars) {
/*     */     try {
/* 280 */       this._writer.chars(chars);
/* 281 */     } catch (IOException e) {
/* 282 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeCharsUnquoted(String chars) {
/*     */     try {
/* 288 */       this._writer.charsUnquoted(chars);
/* 289 */     } catch (IOException e) {
/* 290 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeCharsUnquoted(char[] buf, int offset, int len) {
/*     */     try {
/* 296 */       this._writer.charsUnquoted(buf, offset, len);
/* 297 */     } catch (IOException e) {
/* 298 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endElement() {
/*     */     try {
/* 305 */       String rawName = this._elemStack.pop();
/* 306 */       this._writer.end(rawName);
/*     */       
/* 308 */       this._nsSupport.popContext();
/* 309 */     } catch (IOException e) {
/* 310 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PrefixFactory getPrefixFactory() {
/* 315 */     return this._prefixFactory;
/*     */   }
/*     */   
/*     */   public void setPrefixFactory(PrefixFactory factory) {
/* 319 */     this._prefixFactory = factory;
/*     */   }
/*     */   
/*     */   public String getURI(String prefix) {
/* 323 */     return this._nsSupport.getURI(prefix);
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) {
/* 327 */     return this._nsSupport.getPrefix(uri);
/*     */   }
/*     */   
/*     */   public void flush() {
/*     */     try {
/* 332 */       this._writer.flush();
/* 333 */     } catch (IOException e) {
/* 334 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 340 */       this._writer.close();
/* 341 */     } catch (IOException e) {
/* 342 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private XMLWriterException wrapException(IOException e) {
/* 347 */     return new XMLWriterException("xmlwriter.ioException", (Localizable)new LocalizableExceptionAdapter(e));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   private NamespaceSupport _nsSupport = new NamespaceSupport();
/* 354 */   private Stack _elemStack = new Stack();
/*     */   private PrefixFactory _prefixFactory;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLWriterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
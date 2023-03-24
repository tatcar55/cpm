/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.NamespaceSupport;
/*     */ import com.sun.xml.rpc.streaming.PrefixFactory;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterBase;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterException;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.xml.CDATA;
/*     */ import com.sun.xml.rpc.util.xml.PrettyPrintingXmlWriter;
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
/*     */ 
/*     */ 
/*     */ public class PrettyPrintingXMLWriterImpl
/*     */   extends XMLWriterBase
/*     */ {
/*     */   private PrettyPrintingXmlWriter _writer;
/*     */   
/*     */   public PrettyPrintingXMLWriterImpl(OutputStream out, String enc, boolean declare) {
/*     */     try {
/*  52 */       this._writer = new PrettyPrintingXmlWriter(out, enc, declare);
/*  53 */     } catch (IOException e) {
/*  54 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(String localName, String uri) {
/*     */     try {
/*  60 */       this._nsSupport.pushContext();
/*     */       
/*  62 */       if (!uri.equals("")) {
/*  63 */         String aPrefix = null;
/*  64 */         boolean mustDeclarePrefix = false;
/*     */         
/*  66 */         String defaultNamespaceURI = this._nsSupport.getPrefix("");
/*  67 */         if (defaultNamespaceURI != null && 
/*  68 */           uri.equals(defaultNamespaceURI)) {
/*  69 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/*  73 */         aPrefix = this._nsSupport.getPrefix(uri);
/*     */         
/*  75 */         if (aPrefix == null) {
/*  76 */           mustDeclarePrefix = true;
/*     */           
/*  78 */           if (this._prefixFactory != null) {
/*  79 */             aPrefix = this._prefixFactory.getPrefix(uri);
/*     */           }
/*     */           
/*  82 */           if (aPrefix == null) {
/*  83 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  88 */         String rawName = aPrefix.equals("") ? localName : (aPrefix + ":" + localName);
/*     */ 
/*     */         
/*  91 */         this._writer.start(rawName);
/*  92 */         this._elemStack.push(rawName);
/*     */         
/*  94 */         if (mustDeclarePrefix) {
/*  95 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*  98 */         this._writer.start(localName);
/*  99 */         this._elemStack.push(localName);
/*     */       } 
/* 101 */     } catch (IOException e) {
/* 102 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(String localName, String uri, String prefix) {
/*     */     try {
/* 108 */       this._nsSupport.pushContext();
/*     */       
/* 110 */       if (!uri.equals("")) {
/* 111 */         String aPrefix = null;
/* 112 */         boolean mustDeclarePrefix = false;
/*     */         
/* 114 */         String defaultNamespaceURI = this._nsSupport.getPrefix("");
/* 115 */         if (defaultNamespaceURI != null && 
/* 116 */           uri.equals(defaultNamespaceURI)) {
/* 117 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/* 121 */         aPrefix = this._nsSupport.getPrefix(uri);
/*     */         
/* 123 */         if (aPrefix == null) {
/* 124 */           mustDeclarePrefix = true;
/*     */           
/* 126 */           aPrefix = prefix;
/*     */           
/* 128 */           if (aPrefix == null) {
/* 129 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 134 */         String rawName = aPrefix.equals("") ? localName : (aPrefix + ":" + localName);
/*     */ 
/*     */         
/* 137 */         this._writer.start(rawName);
/* 138 */         this._elemStack.push(rawName);
/*     */         
/* 140 */         if (mustDeclarePrefix) {
/* 141 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/* 144 */         this._writer.start(localName);
/* 145 */         this._elemStack.push(localName);
/*     */       } 
/* 147 */     } catch (IOException e) {
/* 148 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeNamespaceDeclaration(String prefix, String uri) {
/*     */     try {
/* 154 */       this._nsSupport.declarePrefix(prefix, uri);
/*     */       
/* 156 */       String rawName = "xmlns";
/* 157 */       if (prefix != null && !prefix.equals(""))
/*     */       {
/*     */         
/* 160 */         rawName = rawName + ":" + prefix;
/*     */       }
/*     */       
/* 163 */       this._writer.attribute(rawName, uri);
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
/* 207 */         String rawName = aPrefix + ":" + localName;
/* 208 */         this._writer.attribute(rawName, value);
/*     */         
/* 210 */         if (mustDeclarePrefix) {
/* 211 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*     */         
/* 215 */         this._writer.attribute(localName, value);
/*     */       } 
/* 217 */     } catch (IOException e) {
/* 218 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttributeUnquoted(String localName, String uri, String value) {
/*     */     try {
/* 226 */       if (!uri.equals("")) {
/*     */         
/* 228 */         String aPrefix = null;
/* 229 */         boolean mustDeclarePrefix = false;
/*     */         
/* 231 */         String defaultNamespaceURI = this._nsSupport.getPrefix("");
/* 232 */         if (defaultNamespaceURI != null && 
/* 233 */           uri.equals(defaultNamespaceURI)) {
/* 234 */           aPrefix = "";
/*     */         }
/*     */ 
/*     */         
/* 238 */         aPrefix = this._nsSupport.getPrefix(uri);
/*     */         
/* 240 */         if (aPrefix == null) {
/* 241 */           mustDeclarePrefix = true;
/*     */           
/* 243 */           if (this._prefixFactory != null) {
/* 244 */             aPrefix = this._prefixFactory.getPrefix(uri);
/*     */           }
/*     */           
/* 247 */           if (aPrefix == null) {
/* 248 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 253 */         this._writer.attributeUnquoted(aPrefix, localName, value);
/*     */         
/* 255 */         if (mustDeclarePrefix) {
/* 256 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } else {
/*     */         
/* 260 */         this._writer.attributeUnquoted(localName, value);
/*     */       } 
/* 262 */     } catch (IOException e) {
/* 263 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeChars(CDATA chars) {
/*     */     try {
/* 269 */       this._writer.chars(chars);
/* 270 */     } catch (IOException e) {
/* 271 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeChars(String chars) {
/*     */     try {
/* 277 */       this._writer.chars(chars);
/* 278 */     } catch (IOException e) {
/* 279 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeCharsUnquoted(String chars) {
/*     */     try {
/* 285 */       this._writer.charsUnquoted(chars);
/* 286 */     } catch (IOException e) {
/* 287 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeCharsUnquoted(char[] buf, int offset, int len) {
/*     */     try {
/* 293 */       this._writer.charsUnquoted(buf, offset, len);
/* 294 */     } catch (IOException e) {
/* 295 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement() {
/*     */     try {
/* 303 */       String rawName = this._elemStack.pop();
/* 304 */       this._writer.end(rawName);
/*     */       
/* 306 */       this._nsSupport.popContext();
/* 307 */     } catch (IOException e) {
/* 308 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PrefixFactory getPrefixFactory() {
/* 313 */     return this._prefixFactory;
/*     */   }
/*     */   
/*     */   public void setPrefixFactory(PrefixFactory factory) {
/* 317 */     this._prefixFactory = factory;
/*     */   }
/*     */   
/*     */   public String getURI(String prefix) {
/* 321 */     return this._nsSupport.getURI(prefix);
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) {
/* 325 */     return this._nsSupport.getPrefix(uri);
/*     */   }
/*     */   
/*     */   public void flush() {
/*     */     try {
/* 330 */       this._writer.flush();
/* 331 */     } catch (IOException e) {
/* 332 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 338 */       this._writer.close();
/* 339 */     } catch (IOException e) {
/* 340 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private XMLWriterException wrapException(IOException e) {
/* 345 */     return new XMLWriterException("xmlwriter.ioException", (Localizable)new LocalizableExceptionAdapter(e));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 350 */   private NamespaceSupport _nsSupport = new NamespaceSupport();
/* 351 */   private Stack _elemStack = new Stack();
/*     */   private PrefixFactory _prefixFactory;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\PrettyPrintingXMLWriterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
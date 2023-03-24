/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PushbackReader;
/*     */ import java.io.Writer;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLDeclarationParser
/*     */ {
/*     */   private String m_encoding;
/*     */   private PushbackReader m_pushbackReader;
/*     */   private boolean m_hasHeader;
/*  59 */   private String xmlDecl = null;
/*  60 */   static String gt16 = null;
/*  61 */   static String utf16Decl = null;
/*     */   static {
/*     */     try {
/*  64 */       gt16 = new String(">".getBytes("utf-16"));
/*  65 */       utf16Decl = new String("<?xml".getBytes("utf-16"));
/*  66 */     } catch (Exception e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLDeclarationParser(PushbackReader pr) {
/*  73 */     this.m_pushbackReader = pr;
/*  74 */     this.m_encoding = "utf-8";
/*  75 */     this.m_hasHeader = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/*  81 */     return this.m_encoding;
/*     */   }
/*     */   
/*     */   public String getXmlDeclaration() {
/*  85 */     return this.xmlDecl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() throws TransformerException, IOException {
/*  92 */     int c = 0;
/*  93 */     int index = 0;
/*  94 */     char[] aChar = new char[65535];
/*  95 */     StringBuffer xmlDeclStr = new StringBuffer();
/*  96 */     while ((c = this.m_pushbackReader.read()) != -1) {
/*  97 */       aChar[index] = (char)c;
/*  98 */       xmlDeclStr.append((char)c);
/*  99 */       index++;
/* 100 */       if (c == 62) {
/*     */         break;
/*     */       }
/*     */     } 
/* 104 */     int len = index;
/*     */     
/* 106 */     String decl = xmlDeclStr.toString();
/* 107 */     boolean utf16 = false;
/* 108 */     boolean utf8 = false;
/*     */     
/* 110 */     int xmlIndex = decl.indexOf(utf16Decl);
/* 111 */     if (xmlIndex > -1) {
/* 112 */       utf16 = true;
/*     */     } else {
/* 114 */       xmlIndex = decl.indexOf("<?xml");
/* 115 */       if (xmlIndex > -1) {
/* 116 */         utf8 = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 121 */     if (!utf16 && !utf8) {
/* 122 */       this.m_pushbackReader.unread(aChar, 0, len);
/*     */       return;
/*     */     } 
/* 125 */     this.m_hasHeader = true;
/*     */     
/* 127 */     if (utf16) {
/* 128 */       this.xmlDecl = new String(decl.getBytes(), "utf-16");
/* 129 */       this.xmlDecl = this.xmlDecl.substring(this.xmlDecl.indexOf("<"));
/*     */     } else {
/* 131 */       this.xmlDecl = decl;
/*     */     } 
/*     */     
/* 134 */     if (xmlIndex != 0) {
/* 135 */       throw new IOException("Unexpected characters before XML declaration");
/*     */     }
/*     */     
/* 138 */     int versionIndex = this.xmlDecl.indexOf("version");
/* 139 */     if (versionIndex == -1) {
/* 140 */       throw new IOException("Mandatory 'version' attribute Missing in XML declaration");
/*     */     }
/*     */ 
/*     */     
/* 144 */     int encodingIndex = this.xmlDecl.indexOf("encoding");
/* 145 */     if (encodingIndex == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     if (versionIndex > encodingIndex) {
/* 150 */       throw new IOException("The 'version' attribute should preceed the 'encoding' attribute in an XML Declaration");
/*     */     }
/*     */     
/* 153 */     int stdAloneIndex = this.xmlDecl.indexOf("standalone");
/* 154 */     if (stdAloneIndex > -1 && (stdAloneIndex < versionIndex || stdAloneIndex < encodingIndex)) {
/* 155 */       throw new IOException("The 'standalone' attribute should be the last attribute in an XML Declaration");
/*     */     }
/*     */     
/* 158 */     int eqIndex = this.xmlDecl.indexOf("=", encodingIndex);
/* 159 */     if (eqIndex == -1) {
/* 160 */       throw new IOException("Missing '=' character after 'encoding' in XML declaration");
/*     */     }
/*     */     
/* 163 */     this.m_encoding = parseEncoding(this.xmlDecl, eqIndex);
/* 164 */     if (this.m_encoding.startsWith("\"")) {
/* 165 */       this.m_encoding = this.m_encoding.substring(this.m_encoding.indexOf("\"") + 1, this.m_encoding.lastIndexOf("\""));
/* 166 */     } else if (this.m_encoding.startsWith("'")) {
/* 167 */       this.m_encoding = this.m_encoding.substring(this.m_encoding.indexOf("'") + 1, this.m_encoding.lastIndexOf("'"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Writer wr) throws IOException {
/* 174 */     if (!this.m_hasHeader)
/* 175 */       return;  wr.write(this.xmlDecl.toString());
/*     */   }
/*     */   
/*     */   private String parseEncoding(String xmlDeclFinal, int eqIndex) throws IOException {
/* 179 */     StringTokenizer strTok = new StringTokenizer(xmlDeclFinal.substring(eqIndex + 1));
/*     */     
/* 181 */     if (strTok.hasMoreTokens()) {
/* 182 */       String encodingTok = strTok.nextToken();
/* 183 */       int indexofQ = encodingTok.indexOf("?");
/* 184 */       if (indexofQ > -1) {
/* 185 */         return encodingTok.substring(0, indexofQ);
/*     */       }
/* 187 */       return encodingTok;
/*     */     } 
/*     */     
/* 190 */     throw new IOException("Error parsing 'encoding' attribute in XML declaration");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\XMLDeclarationParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
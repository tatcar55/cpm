/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class InternetHeaders
/*     */ {
/*  77 */   private final FinalArrayList<Hdr> headers = new FinalArrayList<Hdr>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InternetHeaders(MIMEParser.LineInputStream lis) {
/*  94 */     String prevline = null;
/*     */     
/*  96 */     StringBuilder lineBuffer = new StringBuilder();
/*     */     
/*     */     try {
/*     */       String line;
/*     */       do {
/* 101 */         line = lis.readLine();
/* 102 */         if (line != null && (line.startsWith(" ") || line.startsWith("\t"))) {
/*     */ 
/*     */           
/* 105 */           if (prevline != null) {
/* 106 */             lineBuffer.append(prevline);
/* 107 */             prevline = null;
/*     */           } 
/* 109 */           lineBuffer.append("\r\n");
/* 110 */           lineBuffer.append(line);
/*     */         } else {
/*     */           
/* 113 */           if (prevline != null) {
/* 114 */             addHeaderLine(prevline);
/* 115 */           } else if (lineBuffer.length() > 0) {
/*     */             
/* 117 */             addHeaderLine(lineBuffer.toString());
/* 118 */             lineBuffer.setLength(0);
/*     */           } 
/* 120 */           prevline = line;
/*     */         } 
/* 122 */       } while (line != null && line.length() > 0);
/* 123 */     } catch (IOException ioex) {
/* 124 */       throw new MIMEParsingException("Error in input stream", ioex);
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
/*     */   List<String> getHeader(String name) {
/* 138 */     FinalArrayList<String> v = new FinalArrayList<String>();
/*     */     
/* 140 */     int len = this.headers.size();
/* 141 */     for (int i = 0; i < len; i++) {
/* 142 */       Hdr h = this.headers.get(i);
/* 143 */       if (name.equalsIgnoreCase(h.name)) {
/* 144 */         v.add(h.getValue());
/*     */       }
/*     */     } 
/* 147 */     return (v.size() == 0) ? null : v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FinalArrayList<? extends Header> getAllHeaders() {
/* 157 */     return (FinalArrayList)this.headers;
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
/*     */   void addHeaderLine(String line) {
/*     */     try {
/* 171 */       char c = line.charAt(0);
/* 172 */       if (c == ' ' || c == '\t') {
/* 173 */         Hdr h = this.headers.get(this.headers.size() - 1);
/* 174 */         h.line += "\r\n" + line;
/*     */       } else {
/* 176 */         this.headers.add(new Hdr(line));
/*     */       } 
/* 178 */     } catch (StringIndexOutOfBoundsException e) {
/*     */     
/* 180 */     } catch (NoSuchElementException e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\InternetHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
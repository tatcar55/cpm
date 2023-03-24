/*     */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.util.LineInputStream;
/*     */ import com.sun.xml.messaging.saaj.util.FinalArrayList;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.AbstractList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InternetHeaders
/*     */ {
/*  91 */   private final FinalArrayList headers = new FinalArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List headerValueView;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InternetHeaders(InputStream is) throws MessagingException {
/* 116 */     load(is);
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
/*     */   public void load(InputStream is) throws MessagingException {
/* 134 */     LineInputStream lis = new LineInputStream(is);
/* 135 */     String prevline = null;
/*     */     
/* 137 */     StringBuffer lineBuffer = new StringBuffer();
/*     */     
/*     */     try {
/*     */       String line;
/*     */       do {
/* 142 */         line = lis.readLine();
/* 143 */         if (line != null && (line.startsWith(" ") || line.startsWith("\t"))) {
/*     */ 
/*     */           
/* 146 */           if (prevline != null) {
/* 147 */             lineBuffer.append(prevline);
/* 148 */             prevline = null;
/*     */           } 
/* 150 */           lineBuffer.append("\r\n");
/* 151 */           lineBuffer.append(line);
/*     */         } else {
/*     */           
/* 154 */           if (prevline != null) {
/* 155 */             addHeaderLine(prevline);
/* 156 */           } else if (lineBuffer.length() > 0) {
/*     */             
/* 158 */             addHeaderLine(lineBuffer.toString());
/* 159 */             lineBuffer.setLength(0);
/*     */           } 
/* 161 */           prevline = line;
/*     */         } 
/* 163 */       } while (line != null && line.length() > 0);
/* 164 */     } catch (IOException ioex) {
/* 165 */       throw new MessagingException("Error in input stream", ioex);
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
/*     */   public String[] getHeader(String name) {
/* 179 */     FinalArrayList v = new FinalArrayList();
/*     */     
/* 181 */     int len = this.headers.size();
/* 182 */     for (int i = 0; i < len; i++) {
/* 183 */       hdr h = (hdr)this.headers.get(i);
/* 184 */       if (name.equalsIgnoreCase(h.name)) {
/* 185 */         v.add(h.getValue());
/*     */       }
/*     */     } 
/* 188 */     if (v.size() == 0) {
/* 189 */       return null;
/*     */     }
/* 191 */     return (String[])v.toArray((Object[])new String[v.size()]);
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
/*     */   public String getHeader(String name, String delimiter) {
/* 207 */     String[] s = getHeader(name);
/*     */     
/* 209 */     if (s == null) {
/* 210 */       return null;
/*     */     }
/* 212 */     if (s.length == 1 || delimiter == null) {
/* 213 */       return s[0];
/*     */     }
/* 215 */     StringBuffer r = new StringBuffer(s[0]);
/* 216 */     for (int i = 1; i < s.length; i++) {
/* 217 */       r.append(delimiter);
/* 218 */       r.append(s[i]);
/*     */     } 
/* 220 */     return r.toString();
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
/*     */   public void setHeader(String name, String value) {
/* 234 */     boolean found = false;
/*     */     
/* 236 */     for (int i = 0; i < this.headers.size(); i++) {
/* 237 */       hdr h = (hdr)this.headers.get(i);
/* 238 */       if (name.equalsIgnoreCase(h.name)) {
/* 239 */         if (!found) {
/*     */           int j;
/* 241 */           if (h.line != null && (j = h.line.indexOf(':')) >= 0) {
/* 242 */             h.line = h.line.substring(0, j + 1) + " " + value;
/*     */           } else {
/* 244 */             h.line = name + ": " + value;
/*     */           } 
/* 246 */           found = true;
/*     */         } else {
/* 248 */           this.headers.remove(i);
/* 249 */           i--;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 254 */     if (!found) {
/* 255 */       addHeader(name, value);
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
/*     */   public void addHeader(String name, String value) {
/* 268 */     int pos = this.headers.size();
/* 269 */     for (int i = this.headers.size() - 1; i >= 0; i--) {
/* 270 */       hdr h = (hdr)this.headers.get(i);
/* 271 */       if (name.equalsIgnoreCase(h.name)) {
/* 272 */         this.headers.add(i + 1, new hdr(name, value));
/*     */         
/*     */         return;
/*     */       } 
/* 276 */       if (h.name.equals(":"))
/* 277 */         pos = i; 
/*     */     } 
/* 279 */     this.headers.add(pos, new hdr(name, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHeader(String name) {
/* 288 */     for (int i = 0; i < this.headers.size(); i++) {
/* 289 */       hdr h = (hdr)this.headers.get(i);
/* 290 */       if (name.equalsIgnoreCase(h.name)) {
/* 291 */         this.headers.remove(i);
/* 292 */         i--;
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
/*     */   public FinalArrayList getAllHeaders() {
/* 304 */     return this.headers;
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
/*     */   public void addHeaderLine(String line) {
/*     */     try {
/* 318 */       char c = line.charAt(0);
/* 319 */       if (c == ' ' || c == '\t')
/* 320 */       { hdr h = (hdr)this.headers.get(this.headers.size() - 1);
/* 321 */         h.line += "\r\n" + line; }
/*     */       else
/* 323 */       { this.headers.add(new hdr(line)); } 
/* 324 */     } catch (StringIndexOutOfBoundsException e) {
/*     */       
/*     */       return;
/* 327 */     } catch (NoSuchElementException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getAllHeaderLines() {
/* 336 */     if (this.headerValueView == null)
/* 337 */       this.headerValueView = new AbstractList() {
/*     */           public Object get(int index) {
/* 339 */             return ((hdr)InternetHeaders.this.headers.get(index)).line;
/*     */           }
/*     */           
/*     */           public int size() {
/* 343 */             return InternetHeaders.this.headers.size();
/*     */           }
/*     */         }; 
/* 346 */     return this.headerValueView;
/*     */   }
/*     */   
/*     */   public InternetHeaders() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\InternetHeaders.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.MultipartDataSource;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.util.LineInputStream;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.util.OutputUtil;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import com.sun.xml.messaging.saaj.util.FinalArrayList;
/*     */ import com.sun.xml.messaging.saaj.util.SAAJUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MimeMultipart
/*     */ {
/*  98 */   protected DataSource ds = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean parsed = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   protected FinalArrayList parts = new FinalArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContentType contentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MimeBodyPart parent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   protected static final boolean ignoreMissingEndBoundary = SAAJUtil.getSystemBoolean("saaj.mime.multipart.ignoremissingendboundary");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeMultipart() {
/* 141 */     this("mixed");
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
/*     */   public MimeMultipart(String subtype) {
/* 157 */     String boundary = UniqueValue.getUniqueBoundaryValue();
/* 158 */     this.contentType = new ContentType("multipart", subtype, null);
/* 159 */     this.contentType.setParameter("boundary", boundary);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeMultipart(DataSource ds, ContentType ct) throws MessagingException {
/* 185 */     this.parsed = false;
/* 186 */     this.ds = ds;
/* 187 */     if (ct == null) {
/* 188 */       this.contentType = new ContentType(ds.getContentType());
/*     */     } else {
/* 190 */       this.contentType = ct;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubType(String subtype) {
/* 201 */     this.contentType.setSubType(subtype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() throws MessagingException {
/* 210 */     parse();
/* 211 */     if (this.parts == null) {
/* 212 */       return 0;
/*     */     }
/* 214 */     return this.parts.size();
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
/*     */   public MimeBodyPart getBodyPart(int index) throws MessagingException {
/* 226 */     parse();
/* 227 */     if (this.parts == null) {
/* 228 */       throw new IndexOutOfBoundsException("No such BodyPart");
/*     */     }
/* 230 */     return (MimeBodyPart)this.parts.get(index);
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
/*     */   public MimeBodyPart getBodyPart(String CID) throws MessagingException {
/* 242 */     parse();
/*     */     
/* 244 */     int count = getCount();
/* 245 */     for (int i = 0; i < count; i++) {
/* 246 */       MimeBodyPart part = getBodyPart(i);
/* 247 */       String s = part.getContentID();
/*     */ 
/*     */       
/* 250 */       String sNoAngle = (s != null) ? s.replaceFirst("^<", "").replaceFirst(">$", "") : null;
/*     */       
/* 252 */       if (s != null && (s.equals(CID) || CID.equals(sNoAngle)))
/* 253 */         return part; 
/*     */     } 
/* 255 */     return null;
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
/*     */   protected void updateHeaders() throws MessagingException {
/* 276 */     for (int i = 0; i < this.parts.size(); i++) {
/* 277 */       ((MimeBodyPart)this.parts.get(i)).updateHeaders();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException, MessagingException {
/* 286 */     parse();
/*     */     
/* 288 */     String boundary = "--" + this.contentType.getParameter("boundary");
/*     */     
/* 290 */     for (int i = 0; i < this.parts.size(); i++) {
/* 291 */       OutputUtil.writeln(boundary, os);
/* 292 */       getBodyPart(i).writeTo(os);
/* 293 */       OutputUtil.writeln(os);
/*     */     } 
/*     */ 
/*     */     
/* 297 */     OutputUtil.writeAsAscii(boundary, os);
/* 298 */     OutputUtil.writeAsAscii("--", os);
/* 299 */     os.flush();
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
/*     */   protected void parse() throws MessagingException {
/*     */     InputStream in;
/* 312 */     if (this.parsed) {
/*     */       return;
/*     */     }
/*     */     
/* 316 */     SharedInputStream sin = null;
/* 317 */     long start = 0L, end = 0L;
/* 318 */     boolean foundClosingBoundary = false;
/*     */     
/*     */     try {
/* 321 */       in = this.ds.getInputStream();
/* 322 */       if (!(in instanceof java.io.ByteArrayInputStream) && !(in instanceof BufferedInputStream) && !(in instanceof SharedInputStream))
/*     */       {
/*     */         
/* 325 */         in = new BufferedInputStream(in); } 
/* 326 */     } catch (Exception ex) {
/* 327 */       throw new MessagingException("No inputstream from datasource");
/*     */     } 
/* 329 */     if (in instanceof SharedInputStream) {
/* 330 */       sin = (SharedInputStream)in;
/*     */     }
/* 332 */     String boundary = "--" + this.contentType.getParameter("boundary");
/* 333 */     byte[] bndbytes = ASCIIUtility.getBytes(boundary);
/* 334 */     int bl = bndbytes.length;
/*     */ 
/*     */     
/*     */     try {
/* 338 */       LineInputStream lin = new LineInputStream(in);
/*     */       String line;
/* 340 */       while ((line = lin.readLine()) != null) {
/*     */         int i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 348 */         for (i = line.length() - 1; i >= 0; i--) {
/* 349 */           char c = line.charAt(i);
/* 350 */           if (c != ' ' && c != '\t')
/*     */             break; 
/*     */         } 
/* 353 */         line = line.substring(0, i + 1);
/* 354 */         if (line.equals(boundary))
/*     */           break; 
/*     */       } 
/* 357 */       if (line == null) {
/* 358 */         throw new MessagingException("Missing start boundary");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 364 */       boolean done = false;
/*     */       
/* 366 */       while (!done) {
/* 367 */         MimeBodyPart part; InternetHeaders headers = null;
/* 368 */         if (sin != null) {
/* 369 */           start = sin.getPosition();
/*     */           
/* 371 */           while ((line = lin.readLine()) != null && line.length() > 0);
/*     */           
/* 373 */           if (line == null) {
/* 374 */             if (!ignoreMissingEndBoundary) {
/* 375 */               throw new MessagingException("Missing End Boundary for Mime Package : EOF while skipping headers");
/*     */             }
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } else {
/* 382 */           headers = createInternetHeaders(in);
/*     */         } 
/*     */         
/* 385 */         if (!in.markSupported()) {
/* 386 */           throw new MessagingException("Stream doesn't support mark");
/*     */         }
/* 388 */         ByteOutputStream buf = null;
/*     */         
/* 390 */         if (sin == null) {
/* 391 */           buf = new ByteOutputStream();
/*     */         }
/* 393 */         boolean bol = true;
/*     */         
/* 395 */         int eol1 = -1, eol2 = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         while (true) {
/* 401 */           if (bol) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 407 */             in.mark(bl + 4 + 1000);
/*     */             int i;
/* 409 */             for (i = 0; i < bl && 
/* 410 */               in.read() == bndbytes[i]; i++);
/*     */             
/* 412 */             if (i == bl) {
/*     */               
/* 414 */               int b2 = in.read();
/* 415 */               if (b2 == 45 && 
/* 416 */                 in.read() == 45) {
/* 417 */                 done = true;
/* 418 */                 foundClosingBoundary = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 423 */               while (b2 == 32 || b2 == 9) {
/* 424 */                 b2 = in.read();
/*     */               }
/* 426 */               if (b2 == 10)
/*     */                 break; 
/* 428 */               if (b2 == 13) {
/* 429 */                 in.mark(1);
/* 430 */                 if (in.read() != 10) {
/* 431 */                   in.reset();
/*     */                 }
/*     */                 break;
/*     */               } 
/*     */             } 
/* 436 */             in.reset();
/*     */ 
/*     */ 
/*     */             
/* 440 */             if (buf != null && eol1 != -1) {
/* 441 */               buf.write(eol1);
/* 442 */               if (eol2 != -1)
/* 443 */                 buf.write(eol2); 
/* 444 */               eol1 = eol2 = -1;
/*     */             } 
/*     */           } 
/*     */           
/*     */           int b;
/* 449 */           if ((b = in.read()) < 0) {
/* 450 */             done = true;
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */ 
/*     */           
/* 458 */           if (b == 13 || b == 10) {
/* 459 */             bol = true;
/* 460 */             if (sin != null)
/* 461 */               end = sin.getPosition() - 1L; 
/* 462 */             eol1 = b;
/* 463 */             if (b == 13) {
/* 464 */               in.mark(1);
/* 465 */               if ((b = in.read()) == 10) {
/* 466 */                 eol2 = b; continue;
/*     */               } 
/* 468 */               in.reset();
/*     */             }  continue;
/*     */           } 
/* 471 */           bol = false;
/* 472 */           if (buf != null) {
/* 473 */             buf.write(b);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 481 */         if (sin != null) {
/* 482 */           part = createMimeBodyPart(sin.newStream(start, end));
/*     */         } else {
/* 484 */           part = createMimeBodyPart(headers, buf.getBytes(), buf.getCount());
/* 485 */         }  addBodyPart(part);
/*     */       } 
/* 487 */     } catch (IOException ioex) {
/* 488 */       throw new MessagingException("IO Error", ioex);
/*     */     } 
/*     */     
/* 491 */     if (!ignoreMissingEndBoundary && !foundClosingBoundary && sin == null) {
/* 492 */       throw new MessagingException("Missing End Boundary for Mime Package : EOF while skipping headers");
/*     */     }
/* 494 */     this.parsed = true;
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
/*     */   protected InternetHeaders createInternetHeaders(InputStream is) throws MessagingException {
/* 510 */     return new InternetHeaders(is);
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
/*     */   protected MimeBodyPart createMimeBodyPart(InternetHeaders headers, byte[] content, int len) {
/* 525 */     return new MimeBodyPart(headers, content, len);
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
/*     */   protected MimeBodyPart createMimeBodyPart(InputStream is) throws MessagingException {
/* 540 */     return new MimeBodyPart(is);
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
/*     */   protected void setMultipartDataSource(MultipartDataSource mp) throws MessagingException {
/* 561 */     this.contentType = new ContentType(mp.getContentType());
/*     */     
/* 563 */     int count = mp.getCount();
/* 564 */     for (int i = 0; i < count; i++) {
/* 565 */       addBodyPart(mp.getBodyPart(i));
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
/*     */   public ContentType getContentType() {
/* 578 */     return this.contentType;
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
/*     */   public boolean removeBodyPart(MimeBodyPart part) throws MessagingException {
/* 590 */     if (this.parts == null) {
/* 591 */       throw new MessagingException("No such body part");
/*     */     }
/* 593 */     boolean ret = this.parts.remove(part);
/* 594 */     part.setParent(null);
/* 595 */     return ret;
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
/*     */   public void removeBodyPart(int index) {
/* 607 */     if (this.parts == null) {
/* 608 */       throw new IndexOutOfBoundsException("No such BodyPart");
/*     */     }
/* 610 */     MimeBodyPart part = (MimeBodyPart)this.parts.get(index);
/* 611 */     this.parts.remove(index);
/* 612 */     part.setParent(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addBodyPart(MimeBodyPart part) {
/* 622 */     if (this.parts == null) {
/* 623 */       this.parts = new FinalArrayList();
/*     */     }
/* 625 */     this.parts.add(part);
/* 626 */     part.setParent(this);
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
/*     */   public synchronized void addBodyPart(MimeBodyPart part, int index) {
/* 640 */     if (this.parts == null) {
/* 641 */       this.parts = new FinalArrayList();
/*     */     }
/* 643 */     this.parts.add(index, part);
/* 644 */     part.setParent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MimeBodyPart getParent() {
/* 653 */     return this.parent;
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
/*     */   void setParent(MimeBodyPart parent) {
/* 666 */     this.parent = parent;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\MimeMultipart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
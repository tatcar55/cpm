/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.Header;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.InternetHeaders;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimePartDataSource;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeUtility;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import com.sun.xml.messaging.saaj.util.FinalArrayList;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.CommandMap;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.MailcapCommandMap;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.jvnet.mimepull.Header;
/*     */ import org.jvnet.mimepull.MIMEPart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttachmentPartImpl
/*     */   extends AttachmentPart
/*     */ {
/*  76 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*     */   
/*     */   private final MimeHeaders headers;
/*     */   
/*     */   static {
/*     */     try {
/*  82 */       CommandMap map = CommandMap.getDefaultCommandMap();
/*  83 */       if (map instanceof MailcapCommandMap) {
/*  84 */         MailcapCommandMap mailMap = (MailcapCommandMap)map;
/*  85 */         String hndlrStr = ";;x-java-content-handler=";
/*  86 */         mailMap.addMailcap("text/xml" + hndlrStr + "com.sun.xml.messaging.saaj.soap.XmlDataContentHandler");
/*     */ 
/*     */ 
/*     */         
/*  90 */         mailMap.addMailcap("application/xml" + hndlrStr + "com.sun.xml.messaging.saaj.soap.XmlDataContentHandler");
/*     */ 
/*     */ 
/*     */         
/*  94 */         mailMap.addMailcap("application/fastinfoset" + hndlrStr + "com.sun.xml.messaging.saaj.soap.FastInfosetDataContentHandler");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 111 */         mailMap.addMailcap("image/*" + hndlrStr + "com.sun.xml.messaging.saaj.soap.ImageDataContentHandler");
/*     */ 
/*     */ 
/*     */         
/* 115 */         mailMap.addMailcap("text/plain" + hndlrStr + "com.sun.xml.messaging.saaj.soap.StringDataContentHandler");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 120 */         throw new SOAPExceptionImpl("Default CommandMap is not a MailcapCommandMap");
/*     */       } 
/* 122 */     } catch (Throwable t) {
/* 123 */       log.log(Level.SEVERE, "SAAJ0508.soap.cannot.register.handlers", t);
/*     */ 
/*     */ 
/*     */       
/* 127 */       if (t instanceof RuntimeException) {
/* 128 */         throw (RuntimeException)t;
/*     */       }
/* 130 */       throw new RuntimeException(t.getLocalizedMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 136 */   private MimeBodyPart rawContent = null;
/* 137 */   private DataHandler dataHandler = null;
/*     */ 
/*     */   
/* 140 */   private MIMEPart mimePart = null;
/*     */   
/*     */   public AttachmentPartImpl() {
/* 143 */     this.headers = new MimeHeaders();
/*     */   }
/*     */   
/*     */   public AttachmentPartImpl(MIMEPart part) {
/* 147 */     this.headers = new MimeHeaders();
/* 148 */     this.mimePart = part;
/* 149 */     List<? extends Header> hdrs = part.getAllHeaders();
/* 150 */     for (Header hd : hdrs) {
/* 151 */       this.headers.addHeader(hd.getName(), hd.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() throws SOAPException {
/* 157 */     if (this.mimePart != null) {
/*     */       try {
/* 159 */         return this.mimePart.read().available();
/* 160 */       } catch (IOException e) {
/* 161 */         return -1;
/*     */       } 
/*     */     }
/* 164 */     if (this.rawContent == null && this.dataHandler == null) {
/* 165 */       return 0;
/*     */     }
/* 167 */     if (this.rawContent != null) {
/*     */       try {
/* 169 */         return this.rawContent.getSize();
/* 170 */       } catch (Exception ex) {
/* 171 */         log.log(Level.SEVERE, "SAAJ0573.soap.attachment.getrawbytes.ioexception", (Object[])new String[] { ex.getLocalizedMessage() });
/*     */ 
/*     */ 
/*     */         
/* 175 */         throw new SOAPExceptionImpl("Raw InputStream Error: " + ex);
/*     */       } 
/*     */     }
/* 178 */     ByteOutputStream bout = new ByteOutputStream();
/*     */     try {
/* 180 */       this.dataHandler.writeTo((OutputStream)bout);
/* 181 */     } catch (IOException ex) {
/* 182 */       log.log(Level.SEVERE, "SAAJ0501.soap.data.handler.err", (Object[])new String[] { ex.getLocalizedMessage() });
/*     */ 
/*     */ 
/*     */       
/* 186 */       throw new SOAPExceptionImpl("Data handler error: " + ex);
/*     */     } 
/* 188 */     return bout.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 193 */     if (this.mimePart != null) {
/* 194 */       this.mimePart.close();
/* 195 */       this.mimePart = null;
/*     */     } 
/* 197 */     this.dataHandler = null;
/* 198 */     this.rawContent = null;
/*     */   }
/*     */   
/*     */   public Object getContent() throws SOAPException {
/*     */     try {
/* 203 */       if (this.mimePart != null)
/*     */       {
/* 205 */         return this.mimePart.read();
/*     */       }
/* 207 */       if (this.dataHandler != null)
/* 208 */         return getDataHandler().getContent(); 
/* 209 */       if (this.rawContent != null) {
/* 210 */         return this.rawContent.getContent();
/*     */       }
/* 212 */       log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 213 */       throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */     }
/* 215 */     catch (Exception ex) {
/* 216 */       log.log(Level.SEVERE, "SAAJ0575.soap.attachment.getcontent.exception", ex);
/* 217 */       throw new SOAPExceptionImpl(ex.getLocalizedMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(Object object, String contentType) throws IllegalArgumentException {
/* 223 */     if (this.mimePart != null) {
/* 224 */       this.mimePart.close();
/* 225 */       this.mimePart = null;
/*     */     } 
/* 227 */     DataHandler dh = new DataHandler(object, contentType);
/*     */     
/* 229 */     setDataHandler(dh);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataHandler getDataHandler() throws SOAPException {
/* 234 */     if (this.mimePart != null)
/*     */     {
/* 236 */       return new DataHandler(new DataSource()
/*     */           {
/*     */             public InputStream getInputStream() throws IOException {
/* 239 */               return AttachmentPartImpl.this.mimePart.read();
/*     */             }
/*     */             
/*     */             public OutputStream getOutputStream() throws IOException {
/* 243 */               throw new UnsupportedOperationException("getOutputStream cannot be supported : You have enabled LazyAttachments Option");
/*     */             }
/*     */             
/*     */             public String getContentType() {
/* 247 */               return AttachmentPartImpl.this.mimePart.getContentType();
/*     */             }
/*     */             
/*     */             public String getName() {
/* 251 */               return "MIMEPart Wrapper DataSource";
/*     */             }
/*     */           });
/*     */     }
/* 255 */     if (this.dataHandler == null) {
/* 256 */       if (this.rawContent != null) {
/* 257 */         return new DataHandler((DataSource)new MimePartDataSource(this.rawContent));
/*     */       }
/* 259 */       log.severe("SAAJ0502.soap.no.handler.for.attachment");
/* 260 */       throw new SOAPExceptionImpl("No data handler associated with this attachment");
/*     */     } 
/* 262 */     return this.dataHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDataHandler(DataHandler dataHandler) throws IllegalArgumentException {
/* 267 */     if (this.mimePart != null) {
/* 268 */       this.mimePart.close();
/* 269 */       this.mimePart = null;
/*     */     } 
/* 271 */     if (dataHandler == null) {
/* 272 */       log.severe("SAAJ0503.soap.no.null.to.dataHandler");
/* 273 */       throw new IllegalArgumentException("Null dataHandler argument to setDataHandler");
/*     */     } 
/* 275 */     this.dataHandler = dataHandler;
/* 276 */     this.rawContent = null;
/*     */     
/* 278 */     if (log.isLoggable(Level.FINE)) {
/* 279 */       log.log(Level.FINE, "SAAJ0580.soap.set.Content-Type", (Object[])new String[] { dataHandler.getContentType() });
/*     */     }
/* 281 */     setMimeHeader("Content-Type", dataHandler.getContentType());
/*     */   }
/*     */   
/*     */   public void removeAllMimeHeaders() {
/* 285 */     this.headers.removeAllHeaders();
/*     */   }
/*     */   
/*     */   public void removeMimeHeader(String header) {
/* 289 */     this.headers.removeHeader(header);
/*     */   }
/*     */   
/*     */   public String[] getMimeHeader(String name) {
/* 293 */     return this.headers.getHeader(name);
/*     */   }
/*     */   
/*     */   public void setMimeHeader(String name, String value) {
/* 297 */     this.headers.setHeader(name, value);
/*     */   }
/*     */   
/*     */   public void addMimeHeader(String name, String value) {
/* 301 */     this.headers.addHeader(name, value);
/*     */   }
/*     */   
/*     */   public Iterator getAllMimeHeaders() {
/* 305 */     return this.headers.getAllHeaders();
/*     */   }
/*     */   
/*     */   public Iterator getMatchingMimeHeaders(String[] names) {
/* 309 */     return this.headers.getMatchingHeaders(names);
/*     */   }
/*     */   
/*     */   public Iterator getNonMatchingMimeHeaders(String[] names) {
/* 313 */     return this.headers.getNonMatchingHeaders(names);
/*     */   }
/*     */   
/*     */   boolean hasAllHeaders(MimeHeaders hdrs) {
/* 317 */     if (hdrs != null) {
/* 318 */       Iterator<MimeHeader> i = hdrs.getAllHeaders();
/* 319 */       while (i.hasNext()) {
/* 320 */         MimeHeader hdr = i.next();
/* 321 */         String[] values = this.headers.getHeader(hdr.getName());
/* 322 */         boolean found = false;
/*     */         
/* 324 */         if (values != null) {
/* 325 */           for (int j = 0; j < values.length; j++) {
/* 326 */             if (hdr.getValue().equalsIgnoreCase(values[j])) {
/* 327 */               found = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 332 */         if (!found) {
/* 333 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 337 */     return true;
/*     */   }
/*     */   
/*     */   MimeBodyPart getMimePart() throws SOAPException {
/*     */     try {
/* 342 */       if (this.mimePart != null) {
/* 343 */         return new MimeBodyPart(this.mimePart);
/*     */       }
/* 345 */       if (this.rawContent != null) {
/* 346 */         copyMimeHeaders(this.headers, this.rawContent);
/* 347 */         return this.rawContent;
/*     */       } 
/*     */       
/* 350 */       MimeBodyPart envelope = new MimeBodyPart();
/*     */       
/* 352 */       envelope.setDataHandler(this.dataHandler);
/* 353 */       copyMimeHeaders(this.headers, envelope);
/*     */       
/* 355 */       return envelope;
/* 356 */     } catch (Exception ex) {
/* 357 */       log.severe("SAAJ0504.soap.cannot.externalize.attachment");
/* 358 */       throw new SOAPExceptionImpl("Unable to externalize attachment", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyMimeHeaders(MimeHeaders headers, MimeBodyPart mbp) throws SOAPException {
/* 365 */     Iterator<MimeHeader> i = headers.getAllHeaders();
/*     */     
/* 367 */     while (i.hasNext()) {
/*     */       try {
/* 369 */         MimeHeader mh = i.next();
/*     */         
/* 371 */         mbp.setHeader(mh.getName(), mh.getValue());
/* 372 */       } catch (Exception ex) {
/* 373 */         log.severe("SAAJ0505.soap.cannot.copy.mime.hdr");
/* 374 */         throw new SOAPExceptionImpl("Unable to copy MIME header", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void copyMimeHeaders(MimeBodyPart mbp, AttachmentPartImpl ap) throws SOAPException {
/*     */     try {
/* 381 */       FinalArrayList<Header> finalArrayList = mbp.getAllHeaders();
/* 382 */       int sz = finalArrayList.size();
/* 383 */       for (int i = 0; i < sz; i++) {
/* 384 */         Header h = finalArrayList.get(i);
/* 385 */         if (!h.getName().equalsIgnoreCase("Content-Type"))
/*     */         {
/* 387 */           ap.addMimeHeader(h.getName(), h.getValue()); } 
/*     */       } 
/* 389 */     } catch (Exception ex) {
/* 390 */       log.severe("SAAJ0506.soap.cannot.copy.mime.hdrs.into.attachment");
/* 391 */       throw new SOAPExceptionImpl("Unable to copy MIME headers into attachment", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBase64Content(InputStream content, String contentType) throws SOAPException {
/* 400 */     if (this.mimePart != null) {
/* 401 */       this.mimePart.close();
/* 402 */       this.mimePart = null;
/*     */     } 
/* 404 */     this.dataHandler = null;
/* 405 */     InputStream decoded = null;
/*     */     try {
/* 407 */       decoded = MimeUtility.decode(content, "base64");
/* 408 */       InternetHeaders hdrs = new InternetHeaders();
/* 409 */       hdrs.setHeader("Content-Type", contentType);
/*     */ 
/*     */ 
/*     */       
/* 413 */       ByteOutputStream bos = new ByteOutputStream();
/* 414 */       bos.write(decoded);
/* 415 */       this.rawContent = new MimeBodyPart(hdrs, bos.getBytes(), bos.getCount());
/* 416 */       setMimeHeader("Content-Type", contentType);
/* 417 */     } catch (Exception e) {
/* 418 */       log.log(Level.SEVERE, "SAAJ0578.soap.attachment.setbase64content.exception", e);
/* 419 */       throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */     } finally {
/*     */       try {
/* 422 */         decoded.close();
/* 423 */       } catch (IOException ex) {
/* 424 */         throw new SOAPException(ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream getBase64Content() throws SOAPException {
/*     */     InputStream inputStream;
/* 431 */     if (this.mimePart != null) {
/* 432 */       inputStream = this.mimePart.read();
/* 433 */     } else if (this.rawContent != null) {
/*     */       try {
/* 435 */         inputStream = this.rawContent.getInputStream();
/* 436 */       } catch (Exception e) {
/* 437 */         log.log(Level.SEVERE, "SAAJ0579.soap.attachment.getbase64content.exception", e);
/* 438 */         throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */       } 
/* 440 */     } else if (this.dataHandler != null) {
/*     */       try {
/* 442 */         inputStream = this.dataHandler.getInputStream();
/* 443 */       } catch (IOException e) {
/* 444 */         log.severe("SAAJ0574.soap.attachment.datahandler.ioexception");
/* 445 */         throw new SOAPExceptionImpl("DataHandler error" + e);
/*     */       } 
/*     */     } else {
/* 448 */       log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 449 */       throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 456 */     int size = 1024;
/*     */     
/* 458 */     if (inputStream != null) {
/*     */       try {
/* 460 */         ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
/*     */ 
/*     */         
/* 463 */         OutputStream ret = MimeUtility.encode(bos, "base64");
/* 464 */         byte[] buf = new byte[size]; int len;
/* 465 */         while ((len = inputStream.read(buf, 0, size)) != -1) {
/* 466 */           ret.write(buf, 0, len);
/*     */         }
/* 468 */         ret.flush();
/* 469 */         buf = bos.toByteArray();
/* 470 */         return new ByteArrayInputStream(buf);
/* 471 */       } catch (Exception e) {
/*     */         
/* 473 */         log.log(Level.SEVERE, "SAAJ0579.soap.attachment.getbase64content.exception", e);
/* 474 */         throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */       } finally {
/*     */         try {
/* 477 */           inputStream.close();
/* 478 */         } catch (IOException ex) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 484 */     log.log(Level.SEVERE, "SAAJ0572.soap.no.content.for.attachment");
/* 485 */     throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRawContent(InputStream content, String contentType) throws SOAPException {
/* 491 */     if (this.mimePart != null) {
/* 492 */       this.mimePart.close();
/* 493 */       this.mimePart = null;
/*     */     } 
/* 495 */     this.dataHandler = null;
/*     */     try {
/* 497 */       InternetHeaders hdrs = new InternetHeaders();
/* 498 */       hdrs.setHeader("Content-Type", contentType);
/*     */ 
/*     */ 
/*     */       
/* 502 */       ByteOutputStream bos = new ByteOutputStream();
/* 503 */       bos.write(content);
/* 504 */       this.rawContent = new MimeBodyPart(hdrs, bos.getBytes(), bos.getCount());
/* 505 */       setMimeHeader("Content-Type", contentType);
/* 506 */     } catch (Exception e) {
/* 507 */       log.log(Level.SEVERE, "SAAJ0576.soap.attachment.setrawcontent.exception", e);
/* 508 */       throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */     } finally {
/*     */       try {
/* 511 */         content.close();
/* 512 */       } catch (IOException ex) {
/* 513 */         throw new SOAPException(ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRawContentBytes(byte[] content, int off, int len, String contentType) throws SOAPException {
/* 539 */     if (this.mimePart != null) {
/* 540 */       this.mimePart.close();
/* 541 */       this.mimePart = null;
/*     */     } 
/* 543 */     if (content == null) {
/* 544 */       throw new SOAPExceptionImpl("Null content passed to setRawContentBytes");
/*     */     }
/* 546 */     this.dataHandler = null;
/*     */     try {
/* 548 */       InternetHeaders hdrs = new InternetHeaders();
/* 549 */       hdrs.setHeader("Content-Type", contentType);
/* 550 */       this.rawContent = new MimeBodyPart(hdrs, content, off, len);
/* 551 */       setMimeHeader("Content-Type", contentType);
/* 552 */     } catch (Exception e) {
/* 553 */       log.log(Level.SEVERE, "SAAJ0576.soap.attachment.setrawcontent.exception", e);
/*     */       
/* 555 */       throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream getRawContent() throws SOAPException {
/* 560 */     if (this.mimePart != null) {
/* 561 */       return this.mimePart.read();
/*     */     }
/* 563 */     if (this.rawContent != null)
/*     */       try {
/* 565 */         return this.rawContent.getInputStream();
/* 566 */       } catch (Exception e) {
/* 567 */         log.log(Level.SEVERE, "SAAJ0577.soap.attachment.getrawcontent.exception", e);
/* 568 */         throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */       }  
/* 570 */     if (this.dataHandler != null) {
/*     */       try {
/* 572 */         return this.dataHandler.getInputStream();
/* 573 */       } catch (IOException e) {
/* 574 */         log.severe("SAAJ0574.soap.attachment.datahandler.ioexception");
/* 575 */         throw new SOAPExceptionImpl("DataHandler error" + e);
/*     */       } 
/*     */     }
/* 578 */     log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 579 */     throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getRawContentBytes() throws SOAPException {
/* 585 */     if (this.mimePart != null) {
/*     */       try {
/* 587 */         InputStream ret = this.mimePart.read();
/* 588 */         return ASCIIUtility.getBytes(ret);
/* 589 */       } catch (IOException ex) {
/* 590 */         log.log(Level.SEVERE, "SAAJ0577.soap.attachment.getrawcontent.exception", ex);
/* 591 */         throw new SOAPExceptionImpl(ex);
/*     */       } 
/*     */     }
/* 594 */     if (this.rawContent != null)
/*     */       try {
/* 596 */         InputStream inputStream = this.rawContent.getInputStream();
/* 597 */         return ASCIIUtility.getBytes(inputStream);
/* 598 */       } catch (Exception e) {
/* 599 */         log.log(Level.SEVERE, "SAAJ0577.soap.attachment.getrawcontent.exception", e);
/* 600 */         throw new SOAPExceptionImpl(e);
/*     */       }  
/* 602 */     if (this.dataHandler != null) {
/*     */       try {
/* 604 */         InputStream inputStream = this.dataHandler.getInputStream();
/* 605 */         return ASCIIUtility.getBytes(inputStream);
/* 606 */       } catch (IOException e) {
/* 607 */         log.severe("SAAJ0574.soap.attachment.datahandler.ioexception");
/* 608 */         throw new SOAPExceptionImpl("DataHandler error" + e);
/*     */       } 
/*     */     }
/* 611 */     log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 612 */     throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 618 */     return (this == o);
/*     */   }
/*     */   
/*     */   public MimeHeaders getMimeHeaders() {
/* 622 */     return this.headers;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\AttachmentPartImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
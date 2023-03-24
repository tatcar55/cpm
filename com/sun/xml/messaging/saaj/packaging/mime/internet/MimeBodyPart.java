/*      */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*      */ 
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.OutputUtil;
/*      */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*      */ import com.sun.xml.messaging.saaj.util.FinalArrayList;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.List;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ import org.jvnet.mimepull.Header;
/*      */ import org.jvnet.mimepull.MIMEPart;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class MimeBodyPart
/*      */ {
/*      */   public static final String ATTACHMENT = "attachment";
/*      */   public static final String INLINE = "inline";
/*      */   private static boolean setDefaultTextCharset = true;
/*      */   private DataHandler dh;
/*      */   private byte[] content;
/*      */   private int contentLength;
/*      */   
/*      */   static {
/*      */     try {
/*  119 */       String s = System.getProperty("mail.mime.setdefaulttextcharset");
/*      */       
/*  121 */       setDefaultTextCharset = (s == null || !s.equalsIgnoreCase("false"));
/*  122 */     } catch (SecurityException sex) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   private int start = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InputStream contentStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final InternetHeaders headers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MimeMultipart parent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MIMEPart mimePart;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MimeBodyPart() {
/*  181 */     this.headers = new InternetHeaders();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MimeBodyPart(InputStream is) throws MessagingException {
/*  200 */     if (!(is instanceof ByteArrayInputStream) && !(is instanceof BufferedInputStream) && !(is instanceof SharedInputStream))
/*      */     {
/*      */       
/*  203 */       is = new BufferedInputStream(is);
/*      */     }
/*  205 */     this.headers = new InternetHeaders(is);
/*      */     
/*  207 */     if (is instanceof SharedInputStream) {
/*  208 */       SharedInputStream sis = (SharedInputStream)is;
/*  209 */       this.contentStream = sis.newStream(sis.getPosition(), -1L);
/*      */     } else {
/*      */       try {
/*  212 */         ByteOutputStream bos = new ByteOutputStream();
/*  213 */         bos.write(is);
/*  214 */         this.content = bos.getBytes();
/*  215 */         this.contentLength = bos.getCount();
/*  216 */       } catch (IOException ioex) {
/*  217 */         throw new MessagingException("Error reading input stream", ioex);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MimeBodyPart(InternetHeaders headers, byte[] content, int len) {
/*  233 */     this.headers = headers;
/*  234 */     this.content = content;
/*  235 */     this.contentLength = len;
/*      */   }
/*      */ 
/*      */   
/*      */   public MimeBodyPart(InternetHeaders headers, byte[] content, int start, int len) {
/*  240 */     this.headers = headers;
/*  241 */     this.content = content;
/*  242 */     this.start = start;
/*  243 */     this.contentLength = len;
/*      */   }
/*      */   
/*      */   public MimeBodyPart(MIMEPart part) {
/*  247 */     this.mimePart = part;
/*  248 */     this.headers = new InternetHeaders();
/*  249 */     List<? extends Header> hdrs = this.mimePart.getAllHeaders();
/*  250 */     for (Header hd : hdrs) {
/*  251 */       this.headers.addHeader(hd.getName(), hd.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MimeMultipart getParent() {
/*  259 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParent(MimeMultipart parent) {
/*  271 */     this.parent = parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSize() {
/*  292 */     if (this.mimePart != null) {
/*      */       try {
/*  294 */         return this.mimePart.read().available();
/*  295 */       } catch (IOException ex) {
/*  296 */         return -1;
/*      */       } 
/*      */     }
/*  299 */     if (this.content != null)
/*  300 */       return this.contentLength; 
/*  301 */     if (this.contentStream != null) {
/*      */       try {
/*  303 */         int size = this.contentStream.available();
/*      */ 
/*      */         
/*  306 */         if (size > 0)
/*  307 */           return size; 
/*  308 */       } catch (IOException ex) {}
/*      */     }
/*      */ 
/*      */     
/*  312 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLineCount() {
/*  328 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getContentType() {
/*  343 */     if (this.mimePart != null) {
/*  344 */       return this.mimePart.getContentType();
/*      */     }
/*  346 */     String s = getHeader("Content-Type", null);
/*  347 */     if (s == null) {
/*  348 */       s = "text/plain";
/*      */     }
/*  350 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMimeType(String mimeType) {
/*      */     boolean bool;
/*      */     try {
/*  371 */       ContentType ct = new ContentType(getContentType());
/*  372 */       bool = ct.match(mimeType);
/*  373 */     } catch (ParseException ex) {
/*  374 */       bool = getContentType().equalsIgnoreCase(mimeType);
/*      */     } 
/*  376 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisposition() throws MessagingException {
/*  393 */     String s = getHeader("Content-Disposition", null);
/*      */     
/*  395 */     if (s == null) {
/*  396 */       return null;
/*      */     }
/*  398 */     ContentDisposition cd = new ContentDisposition(s);
/*  399 */     return cd.getDisposition();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDisposition(String disposition) throws MessagingException {
/*  411 */     if (disposition == null) {
/*  412 */       removeHeader("Content-Disposition");
/*      */     } else {
/*  414 */       String s = getHeader("Content-Disposition", null);
/*  415 */       if (s != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  421 */         ContentDisposition cd = new ContentDisposition(s);
/*  422 */         cd.setDisposition(disposition);
/*  423 */         disposition = cd.toString();
/*      */       } 
/*  425 */       setHeader("Content-Disposition", disposition);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() throws MessagingException {
/*  441 */     String s = getHeader("Content-Transfer-Encoding", null);
/*      */     
/*  443 */     if (s == null) {
/*  444 */       return null;
/*      */     }
/*  446 */     s = s.trim();
/*      */ 
/*      */     
/*  449 */     if (s.equalsIgnoreCase("7bit") || s.equalsIgnoreCase("8bit") || s.equalsIgnoreCase("quoted-printable") || s.equalsIgnoreCase("base64"))
/*      */     {
/*      */       
/*  452 */       return s;
/*      */     }
/*      */     
/*  455 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  461 */       HeaderTokenizer.Token tk = h.next();
/*  462 */       int tkType = tk.getType();
/*  463 */       if (tkType == -4)
/*      */         break; 
/*  465 */       if (tkType == -1) {
/*  466 */         return tk.getValue();
/*      */       }
/*      */     } 
/*      */     
/*  470 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getContentID() {
/*  482 */     return getHeader("Content-ID", null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContentID(String cid) {
/*  495 */     if (cid == null) {
/*  496 */       removeHeader("Content-ID");
/*      */     } else {
/*  498 */       setHeader("Content-ID", cid);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getContentMD5() {
/*  510 */     return getHeader("Content-MD5", null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContentMD5(String md5) {
/*  520 */     setHeader("Content-MD5", md5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getContentLanguage() throws MessagingException {
/*  533 */     String s = getHeader("Content-Language", null);
/*      */     
/*  535 */     if (s == null) {
/*  536 */       return null;
/*      */     }
/*      */     
/*  539 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*  540 */     FinalArrayList v = new FinalArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  546 */       HeaderTokenizer.Token tk = h.next();
/*  547 */       int tkType = tk.getType();
/*  548 */       if (tkType == -4)
/*      */         break; 
/*  550 */       if (tkType == -1) v.add(tk.getValue());
/*      */     
/*      */     } 
/*      */ 
/*      */     
/*  555 */     if (v.size() == 0) {
/*  556 */       return null;
/*      */     }
/*  558 */     return (String[])v.toArray((Object[])new String[v.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContentLanguage(String[] languages) {
/*  568 */     StringBuffer sb = new StringBuffer(languages[0]);
/*  569 */     for (int i = 1; i < languages.length; i++)
/*  570 */       sb.append(',').append(languages[i]); 
/*  571 */     setHeader("Content-Language", sb.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/*  590 */     String rawvalue = getHeader("Content-Description", null);
/*      */     
/*  592 */     if (rawvalue == null) {
/*  593 */       return null;
/*      */     }
/*      */     try {
/*  596 */       return MimeUtility.decodeText(MimeUtility.unfold(rawvalue));
/*  597 */     } catch (UnsupportedEncodingException ex) {
/*  598 */       return rawvalue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDescription(String description) throws MessagingException {
/*  626 */     setDescription(description, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDescription(String description, String charset) throws MessagingException {
/*  655 */     if (description == null) {
/*  656 */       removeHeader("Content-Description");
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/*  661 */       setHeader("Content-Description", MimeUtility.fold(21, MimeUtility.encodeText(description, charset, null)));
/*      */     }
/*  663 */     catch (UnsupportedEncodingException uex) {
/*  664 */       throw new MessagingException("Encoding error", uex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFileName() throws MessagingException {
/*  680 */     String filename = null;
/*  681 */     String s = getHeader("Content-Disposition", null);
/*      */     
/*  683 */     if (s != null) {
/*      */       
/*  685 */       ContentDisposition cd = new ContentDisposition(s);
/*  686 */       filename = cd.getParameter("filename");
/*      */     } 
/*  688 */     if (filename == null) {
/*      */       
/*  690 */       s = getHeader("Content-Type", null);
/*  691 */       if (s != null) {
/*      */         try {
/*  693 */           ContentType ct = new ContentType(s);
/*  694 */           filename = ct.getParameter("name");
/*  695 */         } catch (ParseException pex) {}
/*      */       }
/*      */     } 
/*  698 */     return filename;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileName(String filename) throws MessagingException {
/*  712 */     String s = getHeader("Content-Disposition", null);
/*  713 */     ContentDisposition cd = new ContentDisposition((s == null) ? "attachment" : s);
/*      */     
/*  715 */     cd.setParameter("filename", filename);
/*  716 */     setHeader("Content-Disposition", cd.toString());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  724 */     s = getHeader("Content-Type", null);
/*  725 */     if (s != null) {
/*      */       try {
/*  727 */         ContentType cType = new ContentType(s);
/*  728 */         cType.setParameter("name", filename);
/*  729 */         setHeader("Content-Type", cType.toString());
/*  730 */       } catch (ParseException pex) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getInputStream() throws IOException {
/*  750 */     return getDataHandler().getInputStream();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   InputStream getContentStream() throws MessagingException {
/*  762 */     if (this.mimePart != null) {
/*  763 */       return this.mimePart.read();
/*      */     }
/*  765 */     if (this.contentStream != null)
/*  766 */       return ((SharedInputStream)this.contentStream).newStream(0L, -1L); 
/*  767 */     if (this.content != null) {
/*  768 */       return new ByteArrayInputStream(this.content, this.start, this.contentLength);
/*      */     }
/*  770 */     throw new MessagingException("No content");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getRawInputStream() throws MessagingException {
/*  789 */     return getContentStream();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataHandler getDataHandler() {
/*  799 */     if (this.mimePart != null)
/*      */     {
/*  801 */       return new DataHandler(new DataSource()
/*      */           {
/*      */             public InputStream getInputStream() throws IOException {
/*  804 */               return MimeBodyPart.this.mimePart.read();
/*      */             }
/*      */             
/*      */             public OutputStream getOutputStream() throws IOException {
/*  808 */               throw new UnsupportedOperationException("getOutputStream cannot be supported : You have enabled LazyAttachments Option");
/*      */             }
/*      */             
/*      */             public String getContentType() {
/*  812 */               return MimeBodyPart.this.mimePart.getContentType();
/*      */             }
/*      */             
/*      */             public String getName() {
/*  816 */               return "MIMEPart Wrapped DataSource";
/*      */             }
/*      */           });
/*      */     }
/*  820 */     if (this.dh == null)
/*  821 */       this.dh = new DataHandler(new MimePartDataSource(this)); 
/*  822 */     return this.dh;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getContent() throws IOException {
/*  843 */     return getDataHandler().getContent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataHandler(DataHandler dh) {
/*  855 */     if (this.mimePart != null) {
/*  856 */       this.mimePart = null;
/*      */     }
/*  858 */     this.dh = dh;
/*  859 */     this.content = null;
/*  860 */     this.contentStream = null;
/*  861 */     removeHeader("Content-Type");
/*  862 */     removeHeader("Content-Transfer-Encoding");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContent(Object o, String type) {
/*  881 */     if (this.mimePart != null) {
/*  882 */       this.mimePart = null;
/*      */     }
/*  884 */     if (o instanceof MimeMultipart) {
/*  885 */       setContent((MimeMultipart)o);
/*      */     } else {
/*  887 */       setDataHandler(new DataHandler(o, type));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(String text) {
/*  908 */     setText(text, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setText(String text, String charset) {
/*  919 */     if (charset == null)
/*  920 */       if (MimeUtility.checkAscii(text) != 1) {
/*  921 */         charset = MimeUtility.getDefaultMIMECharset();
/*      */       } else {
/*  923 */         charset = "us-ascii";
/*      */       }  
/*  925 */     setContent(text, "text/plain; charset=" + MimeUtility.quote(charset, "()<>@,;:\\\"\t []/?="));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContent(MimeMultipart mp) {
/*  937 */     if (this.mimePart != null) {
/*  938 */       this.mimePart = null;
/*      */     }
/*  940 */     setDataHandler(new DataHandler(mp, mp.getContentType().toString()));
/*  941 */     mp.setParent(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(OutputStream os) throws IOException, MessagingException {
/*  957 */     List<String> hdrLines = this.headers.getAllHeaderLines();
/*  958 */     int sz = hdrLines.size();
/*  959 */     for (int i = 0; i < sz; i++) {
/*  960 */       OutputUtil.writeln(hdrLines.get(i), os);
/*      */     }
/*      */     
/*  963 */     OutputUtil.writeln(os);
/*      */ 
/*      */ 
/*      */     
/*  967 */     if (this.contentStream != null) {
/*  968 */       ((SharedInputStream)this.contentStream).writeTo(0L, -1L, os);
/*      */     }
/*  970 */     else if (this.content != null) {
/*  971 */       os.write(this.content, this.start, this.contentLength);
/*      */     }
/*  973 */     else if (this.dh != null) {
/*      */       
/*  975 */       OutputStream wos = MimeUtility.encode(os, getEncoding());
/*  976 */       getDataHandler().writeTo(wos);
/*  977 */       if (os != wos)
/*  978 */         wos.flush(); 
/*  979 */     } else if (this.mimePart != null) {
/*  980 */       OutputStream wos = MimeUtility.encode(os, getEncoding());
/*  981 */       getDataHandler().writeTo(wos);
/*  982 */       if (os != wos)
/*  983 */         wos.flush(); 
/*      */     } else {
/*  985 */       throw new MessagingException("no content");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getHeader(String name) {
/*  999 */     return this.headers.getHeader(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHeader(String name, String delimiter) {
/* 1014 */     return this.headers.getHeader(name, delimiter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeader(String name, String value) {
/* 1029 */     this.headers.setHeader(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHeader(String name, String value) {
/* 1043 */     this.headers.addHeader(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeHeader(String name) {
/* 1050 */     this.headers.removeHeader(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FinalArrayList getAllHeaders() {
/* 1058 */     return this.headers.getAllHeaders();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHeaderLine(String line) {
/* 1066 */     this.headers.addHeaderLine(line);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateHeaders() throws MessagingException {
/* 1092 */     DataHandler dh = getDataHandler();
/* 1093 */     if (dh == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1097 */       String type = dh.getContentType();
/* 1098 */       boolean composite = false;
/* 1099 */       boolean needCTHeader = (getHeader("Content-Type") == null);
/*      */       
/* 1101 */       ContentType cType = new ContentType(type);
/* 1102 */       if (cType.match("multipart/*")) {
/*      */         
/* 1104 */         composite = true;
/* 1105 */         Object o = dh.getContent();
/* 1106 */         ((MimeMultipart)o).updateHeaders();
/* 1107 */       } else if (cType.match("message/rfc822")) {
/* 1108 */         composite = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1113 */       if (!composite) {
/* 1114 */         if (getHeader("Content-Transfer-Encoding") == null) {
/* 1115 */           setEncoding(MimeUtility.getEncoding(dh));
/*      */         }
/* 1117 */         if (needCTHeader && setDefaultTextCharset && cType.match("text/*") && cType.getParameter("charset") == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1131 */           String charset, enc = getEncoding();
/* 1132 */           if (enc != null && enc.equalsIgnoreCase("7bit")) {
/* 1133 */             charset = "us-ascii";
/*      */           } else {
/* 1135 */             charset = MimeUtility.getDefaultMIMECharset();
/* 1136 */           }  cType.setParameter("charset", charset);
/* 1137 */           type = cType.toString();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1144 */       if (needCTHeader) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1151 */         String s = getHeader("Content-Disposition", null);
/* 1152 */         if (s != null) {
/*      */           
/* 1154 */           ContentDisposition cd = new ContentDisposition(s);
/* 1155 */           String filename = cd.getParameter("filename");
/* 1156 */           if (filename != null) {
/* 1157 */             cType.setParameter("name", filename);
/* 1158 */             type = cType.toString();
/*      */           } 
/*      */         } 
/*      */         
/* 1162 */         setHeader("Content-Type", type);
/*      */       } 
/* 1164 */     } catch (IOException ex) {
/* 1165 */       throw new MessagingException("IOException updating headers", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setEncoding(String encoding) {
/* 1170 */     setHeader("Content-Transfer-Encoding", encoding);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\MimeBodyPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MIMEMessage
/*     */ {
/*  58 */   private static final Logger LOGGER = Logger.getLogger(MIMEMessage.class.getName());
/*     */   
/*     */   MIMEConfig config;
/*     */   
/*     */   private final InputStream in;
/*     */   
/*     */   private final List<MIMEPart> partsList;
/*     */   
/*     */   private final Map<String, MIMEPart> partsMap;
/*     */   
/*     */   private final Iterator<MIMEEvent> it;
/*     */   private boolean parsed;
/*     */   private MIMEPart currentPart;
/*     */   private int currentIndex;
/*     */   
/*     */   public MIMEMessage(InputStream in, String boundary) {
/*  74 */     this(in, boundary, new MIMEConfig());
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
/*     */   public MIMEMessage(InputStream in, String boundary, MIMEConfig config) {
/*  86 */     this.in = in;
/*  87 */     this.config = config;
/*  88 */     MIMEParser parser = new MIMEParser(in, boundary, config);
/*  89 */     this.it = parser.iterator();
/*     */     
/*  91 */     this.partsList = new ArrayList<MIMEPart>();
/*  92 */     this.partsMap = new HashMap<String, MIMEPart>();
/*  93 */     if (config.isParseEagerly()) {
/*  94 */       parseAll();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MIMEPart> getAttachments() {
/* 105 */     if (!this.parsed) {
/* 106 */       parseAll();
/*     */     }
/* 108 */     return this.partsList;
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
/*     */   public MIMEPart getPart(int index) {
/* 122 */     LOGGER.log(Level.FINE, "index={0}", Integer.valueOf(index));
/* 123 */     MIMEPart part = (index < this.partsList.size()) ? this.partsList.get(index) : null;
/* 124 */     if (this.parsed && part == null) {
/* 125 */       throw new MIMEParsingException("There is no " + index + " attachment part ");
/*     */     }
/* 127 */     if (part == null) {
/*     */       
/* 129 */       part = new MIMEPart(this);
/* 130 */       this.partsList.add(index, part);
/*     */     } 
/* 132 */     LOGGER.log(Level.FINE, "Got attachment at index={0} attachment={1}", new Object[] { Integer.valueOf(index), part });
/* 133 */     return part;
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
/*     */   public MIMEPart getPart(String contentId) {
/* 146 */     LOGGER.log(Level.FINE, "Content-ID={0}", contentId);
/* 147 */     MIMEPart part = getDecodedCidPart(contentId);
/* 148 */     if (this.parsed && part == null) {
/* 149 */       throw new MIMEParsingException("There is no attachment part with Content-ID = " + contentId);
/*     */     }
/* 151 */     if (part == null) {
/*     */       
/* 153 */       part = new MIMEPart(this, contentId);
/* 154 */       this.partsMap.put(contentId, part);
/*     */     } 
/* 156 */     LOGGER.log(Level.FINE, "Got attachment for Content-ID={0} attachment={1}", new Object[] { contentId, part });
/* 157 */     return part;
/*     */   }
/*     */ 
/*     */   
/*     */   private MIMEPart getDecodedCidPart(String cid) {
/* 162 */     MIMEPart part = this.partsMap.get(cid);
/* 163 */     if (part == null && 
/* 164 */       cid.indexOf('%') != -1) {
/*     */       try {
/* 166 */         String tempCid = URLDecoder.decode(cid, "utf-8");
/* 167 */         part = this.partsMap.get(tempCid);
/* 168 */       } catch (UnsupportedEncodingException ue) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 173 */     return part;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void parseAll() {
/* 181 */     while (makeProgress());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean makeProgress() {
/*     */     MIMEEvent.Headers headers;
/*     */     InternetHeaders ih;
/*     */     List<String> cids;
/*     */     String cid;
/*     */     MIMEPart listPart, mapPart;
/*     */     MIMEEvent.Content content;
/*     */     ByteBuffer buf;
/* 194 */     if (!this.it.hasNext()) {
/* 195 */       return false;
/*     */     }
/*     */     
/* 198 */     MIMEEvent event = this.it.next();
/*     */     
/* 200 */     switch (event.getEventType()) {
/*     */       case START_MESSAGE:
/* 202 */         LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.START_MESSAGE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 262 */         return true;case START_PART: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.START_PART); return true;case HEADERS: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.HEADERS); headers = (MIMEEvent.Headers)event; ih = headers.getHeaders(); cids = ih.getHeader("content-id"); cid = (cids != null) ? cids.get(0) : (this.currentIndex + ""); if (cid.length() > 2 && cid.charAt(0) == '<') cid = cid.substring(1, cid.length() - 1);  listPart = (this.currentIndex < this.partsList.size()) ? this.partsList.get(this.currentIndex) : null; mapPart = getDecodedCidPart(cid); if (listPart == null && mapPart == null) { this.currentPart = getPart(cid); this.partsList.add(this.currentIndex, this.currentPart); } else if (listPart == null) { this.currentPart = mapPart; this.partsList.add(this.currentIndex, mapPart); } else if (mapPart == null) { this.currentPart = listPart; this.currentPart.setContentId(cid); this.partsMap.put(cid, this.currentPart); } else if (listPart != mapPart) { throw new MIMEParsingException("Created two different attachments using Content-ID and index"); }  this.currentPart.setHeaders(ih); return true;case CONTENT: LOGGER.log(Level.FINER, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.CONTENT); content = (MIMEEvent.Content)event; buf = content.getData(); this.currentPart.addBody(buf); return true;case END_PART: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.END_PART); this.currentPart.doneParsing(); this.currentIndex++; return true;case END_MESSAGE: LOGGER.log(Level.FINE, "MIMEEvent={0}", MIMEEvent.EVENT_TYPE.END_MESSAGE); this.parsed = true; try { this.in.close(); } catch (IOException ioe) { throw new MIMEParsingException(ioe); }  return true;
/*     */     } 
/*     */     throw new MIMEParsingException("Unknown Parser state = " + event.getEventType());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\MIMEMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
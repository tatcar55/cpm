/*     */ package com.sun.xml.ws.api.message.saaj;
/*     */ 
/*     */ import com.sun.xml.bind.marshaller.SAX2DOMEx;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.message.saaj.SAAJMessage;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAAJFactory
/*     */ {
/*  71 */   private static final SAAJFactory instance = new SAAJFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MessageFactory getMessageFactory(String protocol) throws SOAPException {
/*  97 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/*  98 */       MessageFactory mf = s.createMessageFactory(protocol);
/*  99 */       if (mf != null) {
/* 100 */         return mf;
/*     */       }
/*     */     } 
/* 103 */     return instance.createMessageFactory(protocol);
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
/*     */   public static SOAPFactory getSOAPFactory(String protocol) throws SOAPException {
/* 125 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 126 */       SOAPFactory sf = s.createSOAPFactory(protocol);
/* 127 */       if (sf != null) {
/* 128 */         return sf;
/*     */       }
/*     */     } 
/* 131 */     return instance.createSOAPFactory(protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(SOAPMessage saaj) {
/* 140 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 141 */       Message m = s.createMessage(saaj);
/* 142 */       if (m != null) {
/* 143 */         return m;
/*     */       }
/*     */     } 
/* 146 */     return instance.createMessage(saaj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPMessage read(SOAPVersion soapVersion, Message message) throws SOAPException {
/* 157 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 158 */       SOAPMessage msg = s.readAsSOAPMessage(soapVersion, message);
/* 159 */       if (msg != null) {
/* 160 */         return msg;
/*     */       }
/*     */     } 
/* 163 */     return instance.readAsSOAPMessage(soapVersion, message);
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
/*     */   public static SOAPMessage read(SOAPVersion soapVersion, Message message, Packet packet) throws SOAPException {
/* 175 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 176 */       SOAPMessage msg = s.readAsSOAPMessage(soapVersion, message, packet);
/* 177 */       if (msg != null) {
/* 178 */         return msg;
/*     */       }
/*     */     } 
/* 181 */     return instance.readAsSOAPMessage(soapVersion, message, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SAAJMessage read(Packet packet) throws SOAPException {
/* 191 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 192 */       SAAJMessage msg = s.readAsSAAJ(packet);
/* 193 */       if (msg != null) return msg; 
/*     */     } 
/* 195 */     return instance.readAsSAAJ(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAAJMessage readAsSAAJ(Packet packet) throws SOAPException {
/* 205 */     SOAPVersion v = packet.getMessage().getSOAPVersion();
/* 206 */     SOAPMessage msg = readAsSOAPMessage(v, packet.getMessage());
/* 207 */     return new SAAJMessage(msg);
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
/*     */   
/*     */   public MessageFactory createMessageFactory(String protocol) throws SOAPException {
/* 234 */     return MessageFactory.newInstance(protocol);
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
/*     */   public SOAPFactory createSOAPFactory(String protocol) throws SOAPException {
/* 256 */     return SOAPFactory.newInstance(protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message createMessage(SOAPMessage saaj) {
/* 265 */     return (Message)new SAAJMessage(saaj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(SOAPVersion soapVersion, Message message) throws SOAPException {
/* 276 */     SOAPMessage msg = soapVersion.getMessageFactory().createMessage();
/* 277 */     SaajStaxWriter writer = new SaajStaxWriter(msg);
/*     */     try {
/* 279 */       message.writeTo(writer);
/* 280 */     } catch (XMLStreamException e) {
/* 281 */       throw (e.getCause() instanceof SOAPException) ? (SOAPException)e.getCause() : new SOAPException(e);
/*     */     } 
/* 283 */     msg = writer.getSOAPMessage();
/* 284 */     addAttachmentsToSOAPMessage(msg, message);
/* 285 */     if (msg.saveRequired())
/* 286 */       msg.saveChanges(); 
/* 287 */     return msg;
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessageSax2Dom(SOAPVersion soapVersion, Message message) throws SOAPException {
/* 291 */     SOAPMessage msg = soapVersion.getMessageFactory().createMessage();
/* 292 */     SAX2DOMEx s2d = new SAX2DOMEx(msg.getSOAPPart());
/*     */     try {
/* 294 */       message.writeTo((ContentHandler)s2d, XmlUtil.DRACONIAN_ERROR_HANDLER);
/* 295 */     } catch (SAXException e) {
/* 296 */       throw new SOAPException(e);
/*     */     } 
/* 298 */     addAttachmentsToSOAPMessage(msg, message);
/* 299 */     if (msg.saveRequired())
/* 300 */       msg.saveChanges(); 
/* 301 */     return msg;
/*     */   }
/*     */   
/*     */   protected static void addAttachmentsToSOAPMessage(SOAPMessage msg, Message message) {
/* 305 */     for (Attachment att : message.getAttachments()) {
/* 306 */       AttachmentPart part = msg.createAttachmentPart();
/* 307 */       part.setDataHandler(att.asDataHandler());
/*     */ 
/*     */       
/* 310 */       String cid = att.getContentId();
/* 311 */       if (cid != null) {
/* 312 */         if (cid.startsWith("<") && cid.endsWith(">")) {
/* 313 */           part.setContentId(cid);
/*     */         } else {
/* 315 */           part.setContentId('<' + cid + '>');
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 321 */       if (att instanceof AttachmentEx) {
/* 322 */         AttachmentEx ax = (AttachmentEx)att;
/* 323 */         Iterator<AttachmentEx.MimeHeader> imh = ax.getMimeHeaders();
/* 324 */         while (imh.hasNext()) {
/* 325 */           AttachmentEx.MimeHeader ame = imh.next();
/* 326 */           if (!"Content-ID".equals(ame.getName()) && !"Content-Type".equals(ame.getName()))
/*     */           {
/* 328 */             part.addMimeHeader(ame.getName(), ame.getValue()); } 
/*     */         } 
/*     */       } 
/* 331 */       msg.addAttachmentPart(part);
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
/*     */   public SOAPMessage readAsSOAPMessage(SOAPVersion soapVersion, Message message, Packet packet) throws SOAPException {
/* 346 */     return readAsSOAPMessage(soapVersion, message);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\saaj\SAAJFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
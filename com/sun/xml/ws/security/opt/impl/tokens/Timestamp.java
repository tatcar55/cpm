/*     */ package com.sun.xml.ws.security.opt.impl.tokens;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.tokens.Timestamp;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import com.sun.xml.ws.security.wsu10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.wsu10.TimestampType;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timestamp
/*     */   extends TimestampType
/*     */   implements Timestamp, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  82 */   private static final TimeZone utc = TimeZone.getTimeZone("UTC");
/*  83 */   private static Calendar utcCalendar = new GregorianCalendar(utc);
/*  84 */   public static final SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*     */   
/*  86 */   private static final SimpleDateFormat utcCalendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*     */ 
/*     */   
/*     */   static {
/*  90 */     utcCalendarFormatter1.setTimeZone(utc);
/*     */   }
/*     */   
/*  93 */   private long timeout = 0L;
/*  94 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  95 */   private ObjectFactory objFac = new ObjectFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp(SOAPVersion sv) {
/* 102 */     this.soapVersion = sv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreated(String created) {
/* 110 */     AttributedDateTime timeCreated = this.objFac.createAttributedDateTime();
/* 111 */     timeCreated.setValue(created);
/* 112 */     setCreated(timeCreated);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpires(String expires) {
/* 120 */     AttributedDateTime timeExpires = this.objFac.createAttributedDateTime();
/* 121 */     timeExpires.setValue(expires);
/* 122 */     setExpires(timeExpires);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreatedValue() {
/* 130 */     String createdValue = null;
/* 131 */     AttributedDateTime created = getCreated();
/* 132 */     if (created != null)
/* 133 */       createdValue = created.getValue(); 
/* 134 */     return createdValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpiresValue() {
/* 142 */     String expiresValue = null;
/* 143 */     AttributedDateTime expires = getExpires();
/* 144 */     if (expires != null)
/* 145 */       expiresValue = expires.getValue(); 
/* 146 */     return expiresValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeout(long timeout) {
/* 153 */     this.timeout = timeout;
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 157 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 161 */     return "Timestamp";
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 165 */     QName qname = new QName(nsUri, localName);
/* 166 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 167 */     return otherAttributes.get(qname);
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 171 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 172 */     return otherAttributes.get(name);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 176 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 177 */     JAXBElement<TimestampType> tsElem = (new ObjectFactory()).createTimestamp(this);
/*     */     try {
/* 179 */       getMarshaller().marshal(tsElem, (Result)xbr);
/*     */     }
/* 181 */     catch (JAXBException je) {
/* 182 */       throw new XMLStreamException(je);
/*     */     } 
/* 184 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 202 */     JAXBElement<TimestampType> tsElem = (new ObjectFactory()).createTimestamp(this);
/*     */     
/*     */     try {
/* 205 */       if (streamWriter instanceof Map) {
/* 206 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 207 */         if (os != null) {
/* 208 */           streamWriter.writeCharacters("");
/* 209 */           getMarshaller().marshal(tsElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 214 */       getMarshaller().marshal(tsElem, streamWriter);
/* 215 */     } catch (JAXBException e) {
/* 216 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 221 */     return JAXBUtil.createMarshaller(this.soapVersion);
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
/*     */   public void createDateTime() throws XWSSecurityException {
/* 233 */     if (this.created == null) {
/* 234 */       synchronized (utcCalendar) {
/*     */ 
/*     */         
/* 237 */         long currentTime = System.currentTimeMillis();
/* 238 */         utcCalendar.setTimeInMillis(currentTime);
/*     */         
/* 240 */         setCreated(utcCalendarFormatter1.format(utcCalendar.getTime()));
/*     */         
/* 242 */         utcCalendar.setTimeInMillis(currentTime + this.timeout);
/* 243 */         setExpires(utcCalendarFormatter1.format(utcCalendar.getTime()));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap<Object, Object> props) throws XMLStreamException {
/*     */     try {
/* 266 */       Marshaller marshaller = getMarshaller();
/* 267 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 268 */       while (itr.hasNext()) {
/* 269 */         Map.Entry<Object, Object> entry = itr.next();
/* 270 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 272 */       writeTo(streamWriter);
/* 273 */     } catch (JAXBException jbe) {
/* 274 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\tokens\Timestamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
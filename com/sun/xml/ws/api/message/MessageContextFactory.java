/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.oracle.webservices.api.EnvelopeStyle;
/*     */ import com.oracle.webservices.api.EnvelopeStyleFeature;
/*     */ import com.oracle.webservices.api.message.MessageContext;
/*     */ import com.oracle.webservices.api.message.MessageContextFactory;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageContextFactory
/*     */   extends MessageContextFactory
/*     */ {
/*     */   private WSFeatureList features;
/*     */   private Codec soapCodec;
/*     */   private Codec xmlCodec;
/*     */   private EnvelopeStyleFeature envelopeStyle;
/*     */   private EnvelopeStyle.Style singleSoapStyle;
/*     */   
/*     */   public MessageContextFactory(WebServiceFeature[] wsf) {
/*  82 */     this((WSFeatureList)new WebServiceFeatureList(wsf));
/*     */   }
/*     */   
/*     */   public MessageContextFactory(WSFeatureList wsf) {
/*  86 */     this.features = wsf;
/*  87 */     this.envelopeStyle = (EnvelopeStyleFeature)this.features.get(EnvelopeStyleFeature.class);
/*  88 */     if (this.envelopeStyle == null) {
/*  89 */       this.envelopeStyle = new EnvelopeStyleFeature(new EnvelopeStyle.Style[] { EnvelopeStyle.Style.SOAP11 });
/*  90 */       this.features.mergeFeatures(new WebServiceFeature[] { (WebServiceFeature)this.envelopeStyle }, false);
/*     */     } 
/*  92 */     for (EnvelopeStyle.Style s : this.envelopeStyle.getStyles()) {
/*  93 */       if (s.isXML()) {
/*  94 */         if (this.xmlCodec == null) this.xmlCodec = Codecs.createXMLCodec(this.features); 
/*     */       } else {
/*  96 */         if (this.soapCodec == null) this.soapCodec = (Codec)Codecs.createSOAPBindingCodec(this.features); 
/*  97 */         this.singleSoapStyle = s;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected MessageContextFactory newFactory(WebServiceFeature... f) {
/* 103 */     return new MessageContextFactory(f);
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageContext createContext() {
/* 108 */     return packet(null);
/*     */   }
/*     */   
/*     */   public MessageContext createContext(SOAPMessage soap) {
/* 112 */     throwIfIllegalMessageArgument(soap);
/* 113 */     return packet(Messages.create(soap));
/*     */   }
/*     */   
/*     */   public MessageContext createContext(Source m, EnvelopeStyle.Style envelopeStyle) {
/* 117 */     throwIfIllegalMessageArgument(m);
/* 118 */     return packet(Messages.create(m, SOAPVersion.from(envelopeStyle)));
/*     */   }
/*     */   
/*     */   public MessageContext createContext(Source m) {
/* 122 */     throwIfIllegalMessageArgument(m);
/* 123 */     return packet(Messages.create(m, SOAPVersion.from(this.singleSoapStyle)));
/*     */   }
/*     */   
/*     */   public MessageContext createContext(InputStream in, String contentType) throws IOException {
/* 127 */     throwIfIllegalMessageArgument(in);
/*     */     
/* 129 */     Packet p = packet(null);
/* 130 */     this.soapCodec.decode(in, contentType, p);
/* 131 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MessageContext createContext(InputStream in, MimeHeaders headers) throws IOException {
/* 139 */     String contentType = getHeader(headers, "Content-Type");
/* 140 */     Packet packet = (Packet)createContext(in, contentType);
/* 141 */     packet.acceptableMimeTypes = getHeader(headers, "Accept");
/* 142 */     packet.soapAction = HttpAdapter.fixQuotesAroundSoapAction(getHeader(headers, "SOAPAction"));
/*     */     
/* 144 */     return packet;
/*     */   }
/*     */   
/*     */   static String getHeader(MimeHeaders headers, String name) {
/* 148 */     String[] values = headers.getHeader(name);
/* 149 */     return (values != null && values.length > 0) ? values[0] : null;
/*     */   }
/*     */   
/*     */   static Map<String, List<String>> toMap(MimeHeaders headers) {
/* 153 */     HashMap<String, List<String>> map = new HashMap<String, List<String>>();
/* 154 */     for (Iterator<MimeHeader> i = headers.getAllHeaders(); i.hasNext(); ) {
/* 155 */       MimeHeader mh = i.next();
/* 156 */       List<String> values = map.get(mh.getName());
/* 157 */       if (values == null) {
/* 158 */         values = new ArrayList<String>();
/* 159 */         map.put(mh.getName(), values);
/*     */       } 
/* 161 */       values.add(mh.getValue());
/*     */     } 
/* 163 */     return map;
/*     */   }
/*     */   
/*     */   public MessageContext createContext(Message m) {
/* 167 */     throwIfIllegalMessageArgument(m);
/* 168 */     return packet(m);
/*     */   }
/*     */   
/*     */   private Packet packet(Message m) {
/* 172 */     Packet p = new Packet();
/*     */     
/* 174 */     p.codec = this.soapCodec;
/* 175 */     if (m != null) p.setMessage(m); 
/* 176 */     MTOMFeature mf = (MTOMFeature)this.features.get(MTOMFeature.class);
/* 177 */     if (mf != null) {
/* 178 */       p.setMtomFeature(mf);
/*     */     }
/* 180 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwIfIllegalMessageArgument(Object message) throws IllegalArgumentException {
/* 186 */     if (message == null) {
/* 187 */       throw new IllegalArgumentException("null messages are not allowed.  Consider using MessageContextFactory.createContext()");
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public MessageContext doCreate() {
/* 193 */     return packet(null);
/*     */   }
/*     */   @Deprecated
/*     */   public MessageContext doCreate(SOAPMessage m) {
/* 197 */     return createContext(m);
/*     */   }
/*     */   @Deprecated
/*     */   public MessageContext doCreate(Source x, SOAPVersion soapVersion) {
/* 201 */     return packet(Messages.create(x, soapVersion));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\MessageContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
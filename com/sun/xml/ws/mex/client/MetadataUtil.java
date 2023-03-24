/*     */ package com.sun.xml.ws.mex.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetadataUtil
/*     */ {
/*  67 */   private static final Logger logger = Logger.getLogger(MetadataUtil.class.getName());
/*     */ 
/*     */ 
/*     */   
/*  71 */   private final HttpPoster postClient = new HttpPoster();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getMetadata(String address, MetadataClient.Protocol protocol) throws IOException {
/*  83 */     String request = getMexWsdlRequest(address, protocol);
/*  84 */     if (logger.isLoggable(Level.FINE)) {
/*  85 */       logger.fine("Request message:\n" + request + "\n");
/*     */     }
/*  87 */     String contentType = "application/soap+xml";
/*  88 */     if (protocol == MetadataClient.Protocol.SOAP_1_1) {
/*  89 */       contentType = "text/xml; charset=\"utf-8\"";
/*     */     }
/*  91 */     return this.postClient.post(request, address, contentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMexWsdlRequest(String address, MetadataClient.Protocol protocol) {
/*  98 */     String soapPrefix = "s12";
/*  99 */     String soapNamespace = "http://www.w3.org/2003/05/soap-envelope";
/* 100 */     if (protocol == MetadataClient.Protocol.SOAP_1_1) {
/* 101 */       soapPrefix = "soap-env";
/* 102 */       soapNamespace = "http://schemas.xmlsoap.org/soap/envelope/";
/*     */     } 
/* 104 */     return "<" + soapPrefix + ":Envelope " + "xmlns:" + soapPrefix + "='" + soapNamespace + "' " + "xmlns:" + "wsa" + "='" + AddressingVersion.W3C.nsUri + "'>" + "<" + soapPrefix + ":Header>" + "<" + "wsa" + ":Action>" + "http://schemas.xmlsoap.org/ws/2004/09/transfer/Get" + "</" + "wsa" + ":Action>" + "<" + "wsa" + ":To>" + address + "</" + "wsa" + ":To>" + "<" + "wsa" + ":ReplyTo><" + "wsa" + ":Address>" + "http://www.w3.org/2005/08/addressing/anonymous" + "</" + "wsa" + ":Address></" + "wsa" + ":ReplyTo>" + "<" + "wsa" + ":MessageID>" + "uuid:778b135f-3fdf-44b2-b53e-ebaab7441e40" + "</" + "wsa" + ":MessageID>" + "</" + soapPrefix + ":Header>" + "<" + soapPrefix + ":Body/>" + "</" + soapPrefix + ":Envelope>";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\client\MetadataUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
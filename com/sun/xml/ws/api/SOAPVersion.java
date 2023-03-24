/*     */ package com.sun.xml.ws.api;
/*     */ 
/*     */ import com.oracle.webservices.api.EnvelopeStyle;
/*     */ import com.oracle.webservices.api.EnvelopeStyleFeature;
/*     */ import com.sun.xml.bind.util.Which;
/*     */ import com.sun.xml.ws.api.message.saaj.SAAJFactory;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum SOAPVersion
/*     */ {
/*  91 */   SOAP_11("http://schemas.xmlsoap.org/wsdl/soap/http", "http://schemas.xmlsoap.org/soap/envelope/", "text/xml", "http://schemas.xmlsoap.org/soap/actor/next", "actor", "SOAP 1.1 Protocol", new QName("http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand"), "Client", "Server", Collections.singleton("http://schemas.xmlsoap.org/soap/actor/next")),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   SOAP_12("http://www.w3.org/2003/05/soap/bindings/HTTP/", "http://www.w3.org/2003/05/soap-envelope", "application/soap+xml", "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver", "role", "SOAP 1.2 Protocol", new QName("http://www.w3.org/2003/05/soap-envelope", "MustUnderstand"), "Sender", "Receiver", new HashSet<String>(Arrays.asList(new String[] { "http://www.w3.org/2003/05/soap-envelope/role/next", "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver" })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String httpBindingId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String nsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String contentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultCodeMustUnderstand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MessageFactory saajMessageFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SOAPFactory saajSoapFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String saajFactoryString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String implicitRole;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Set<String> implicitRoleSet;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Set<String> requiredRoles;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String roleAttributeName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultCodeClient;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultCodeServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SOAPVersion(String httpBindingId, String nsUri, String contentType, String implicitRole, String roleAttributeName, String saajFactoryString, QName faultCodeMustUnderstand, String faultCodeClientLocalName, String faultCodeServerLocalName, Set<String> requiredRoles) {
/* 182 */     this.httpBindingId = httpBindingId;
/* 183 */     this.nsUri = nsUri;
/* 184 */     this.contentType = contentType;
/* 185 */     this.implicitRole = implicitRole;
/* 186 */     this.implicitRoleSet = Collections.singleton(implicitRole);
/* 187 */     this.roleAttributeName = roleAttributeName;
/* 188 */     this.saajFactoryString = saajFactoryString;
/*     */     try {
/* 190 */       this.saajMessageFactory = MessageFactory.newInstance(saajFactoryString);
/* 191 */       this.saajSoapFactory = SOAPFactory.newInstance(saajFactoryString);
/* 192 */     } catch (SOAPException e) {
/* 193 */       throw new Error(e);
/* 194 */     } catch (NoSuchMethodError e) {
/*     */       
/* 196 */       LinkageError x = new LinkageError("You are loading old SAAJ from " + Which.which(MessageFactory.class));
/* 197 */       x.initCause(e);
/* 198 */       throw x;
/*     */     } 
/* 200 */     this.faultCodeMustUnderstand = faultCodeMustUnderstand;
/* 201 */     this.requiredRoles = requiredRoles;
/* 202 */     this.faultCodeClient = new QName(nsUri, faultCodeClientLocalName);
/* 203 */     this.faultCodeServer = new QName(nsUri, faultCodeServerLocalName);
/*     */   }
/*     */   
/*     */   public SOAPFactory getSOAPFactory() {
/*     */     try {
/* 208 */       return SAAJFactory.getSOAPFactory(this.saajFactoryString);
/* 209 */     } catch (SOAPException e) {
/* 210 */       throw new Error(e);
/* 211 */     } catch (NoSuchMethodError e) {
/*     */       
/* 213 */       LinkageError x = new LinkageError("You are loading old SAAJ from " + Which.which(MessageFactory.class));
/* 214 */       x.initCause(e);
/* 215 */       throw x;
/*     */     } 
/*     */   }
/*     */   
/*     */   public MessageFactory getMessageFactory() {
/*     */     try {
/* 221 */       return SAAJFactory.getMessageFactory(this.saajFactoryString);
/* 222 */     } catch (SOAPException e) {
/* 223 */       throw new Error(e);
/* 224 */     } catch (NoSuchMethodError e) {
/*     */       
/* 226 */       LinkageError x = new LinkageError("You are loading old SAAJ from " + Which.which(MessageFactory.class));
/* 227 */       x.initCause(e);
/* 228 */       throw x;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 233 */     return this.httpBindingId;
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
/*     */   public static SOAPVersion fromHttpBinding(String binding) {
/* 248 */     if (binding == null) {
/* 249 */       return SOAP_11;
/*     */     }
/* 251 */     if (binding.equals(SOAP_12.httpBindingId)) {
/* 252 */       return SOAP_12;
/*     */     }
/* 254 */     return SOAP_11;
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
/*     */   public static SOAPVersion fromNsUri(String nsUri) {
/* 268 */     if (nsUri.equals(SOAP_12.nsUri)) {
/* 269 */       return SOAP_12;
/*     */     }
/* 271 */     return SOAP_11;
/*     */   }
/*     */   
/*     */   public static SOAPVersion from(EnvelopeStyleFeature f) {
/* 275 */     EnvelopeStyle.Style[] style = f.getStyles();
/* 276 */     if (style.length != 1) throw new IllegalArgumentException("The EnvelopingFeature must has exactly one Enveloping.Style"); 
/* 277 */     return from(style[0]);
/*     */   }
/*     */   
/*     */   public static SOAPVersion from(EnvelopeStyle.Style style) {
/* 281 */     switch (style) { case SOAP11:
/* 282 */         return SOAP_11;
/* 283 */       case SOAP12: return SOAP_12; }
/*     */     
/* 285 */     return SOAP_11;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnvelopeStyleFeature toFeature() {
/* 290 */     return SOAP_11.equals(this) ? new EnvelopeStyleFeature(new EnvelopeStyle.Style[] { EnvelopeStyle.Style.SOAP11 }) : new EnvelopeStyleFeature(new EnvelopeStyle.Style[] { EnvelopeStyle.Style.SOAP12 });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\SOAPVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.Envelope;
/*     */ import com.sun.xml.messaging.saaj.soap.EnvelopeFactory;
/*     */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPPartImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.EnvelopeImpl;
/*     */ import com.sun.xml.messaging.saaj.util.XMLDeclarationParser;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.transform.Source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPPart1_1Impl
/*     */   extends SOAPPartImpl
/*     */   implements SOAPConstants
/*     */ {
/*  61 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.ver1_1", "com.sun.xml.messaging.saaj.soap.ver1_1.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPPart1_1Impl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPPart1_1Impl(MessageImpl message) {
/*  70 */     super(message);
/*     */   }
/*     */   
/*     */   protected String getContentType() {
/*  74 */     return isFastInfoset() ? "application/fastinfoset" : "text/xml";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Envelope createEnvelopeFromSource() throws SOAPException {
/*  80 */     XMLDeclarationParser parser = lookForXmlDecl();
/*  81 */     Source tmp = this.source;
/*  82 */     this.source = null;
/*  83 */     EnvelopeImpl envelope = (EnvelopeImpl)EnvelopeFactory.createEnvelope(tmp, this);
/*     */ 
/*     */     
/*  86 */     if (!envelope.getNamespaceURI().equals("http://schemas.xmlsoap.org/soap/envelope/")) {
/*  87 */       log.severe("SAAJ0304.ver1_1.msg.invalid.SOAP1.1");
/*  88 */       throw new SOAPException("InputStream does not represent a valid SOAP 1.1 Message");
/*     */     } 
/*     */     
/*  91 */     if (parser != null && !this.omitXmlDecl) {
/*  92 */       envelope.setOmitXmlDecl("no");
/*  93 */       envelope.setXmlDecl(parser.getXmlDeclaration());
/*  94 */       envelope.setCharsetEncoding(parser.getEncoding());
/*     */     } 
/*  96 */     return (Envelope)envelope;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Envelope createEmptyEnvelope(String prefix) throws SOAPException {
/* 101 */     return (Envelope)new Envelope1_1Impl(getDocument(), prefix, true, true);
/*     */   }
/*     */   
/*     */   protected SOAPPartImpl duplicateType() {
/* 105 */     return new SOAPPart1_1Impl();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\SOAPPart1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
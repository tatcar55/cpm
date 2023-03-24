/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
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
/*     */ public class SOAPPart1_2Impl
/*     */   extends SOAPPartImpl
/*     */   implements SOAPConstants
/*     */ {
/*  60 */   protected static final Logger log = Logger.getLogger(SOAPPart1_2Impl.class.getName(), "com.sun.xml.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPPart1_2Impl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPPart1_2Impl(MessageImpl message) {
/*  69 */     super(message);
/*     */   }
/*     */   
/*     */   protected String getContentType() {
/*  73 */     return "application/soap+xml";
/*     */   }
/*     */   
/*     */   protected Envelope createEmptyEnvelope(String prefix) throws SOAPException {
/*  77 */     return (Envelope)new Envelope1_2Impl(getDocument(), prefix, true, true);
/*     */   }
/*     */   
/*     */   protected Envelope createEnvelopeFromSource() throws SOAPException {
/*  81 */     XMLDeclarationParser parser = lookForXmlDecl();
/*  82 */     Source tmp = this.source;
/*  83 */     this.source = null;
/*  84 */     EnvelopeImpl envelope = (EnvelopeImpl)EnvelopeFactory.createEnvelope(tmp, this);
/*  85 */     if (!envelope.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope")) {
/*  86 */       log.severe("SAAJ0415.ver1_2.msg.invalid.soap1.2");
/*  87 */       throw new SOAPException("InputStream does not represent a valid SOAP 1.2 Message");
/*     */     } 
/*     */     
/*  90 */     if (parser != null && 
/*  91 */       !this.omitXmlDecl) {
/*  92 */       envelope.setOmitXmlDecl("no");
/*  93 */       envelope.setXmlDecl(parser.getXmlDeclaration());
/*  94 */       envelope.setCharsetEncoding(parser.getEncoding());
/*     */     } 
/*     */     
/*  97 */     return (Envelope)envelope;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPPartImpl duplicateType() {
/* 102 */     return new SOAPPart1_2Impl();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\SOAPPart1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
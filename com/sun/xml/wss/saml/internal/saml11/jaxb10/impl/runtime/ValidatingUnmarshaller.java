/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.msv.grammar.Grammar;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.IVerifier;
/*     */ import com.sun.msv.verifier.Verifier;
/*     */ import com.sun.msv.verifier.VerifierFilter;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.validator.Locator;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import org.iso_relax.verifier.impl.ForkContentHandler;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidatingUnmarshaller
/*     */   extends ForkContentHandler
/*     */   implements SAXUnmarshallerHandler
/*     */ {
/*     */   private final SAXUnmarshallerHandler core;
/*     */   private final AttributesImpl xsiLessAtts;
/*     */   
/*     */   public static ValidatingUnmarshaller create(Grammar grammar, SAXUnmarshallerHandler _core, Locator locator) {
/*  51 */     Verifier v = new Verifier((DocumentDeclaration)new REDocumentDeclaration(grammar), new ErrorHandlerAdaptor(_core, locator));
/*     */ 
/*     */     
/*  54 */     v.setPanicMode(true);
/*     */     
/*  56 */     return new ValidatingUnmarshaller(new VerifierFilter((IVerifier)v), _core);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ValidatingUnmarshaller(VerifierFilter filter, SAXUnmarshallerHandler _core)
/*     */   {
/*  63 */     super((ContentHandler)filter, _core);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     this.xsiLessAtts = new AttributesImpl();
/*     */     this.core = _core;
/*     */   }
/*     */   public void startElement(String nsUri, String local, String qname, Attributes atts) throws SAXException {
/*  85 */     this.xsiLessAtts.clear();
/*  86 */     int len = atts.getLength();
/*  87 */     for (int i = 0; i < len; i++) {
/*  88 */       String aUri = atts.getURI(i);
/*  89 */       String aLocal = atts.getLocalName(i);
/*  90 */       if (!aUri.equals("http://www.w3.org/2001/XMLSchema-instance") || (!aLocal.equals("schemaLocation") && !aLocal.equals("noNamespaceSchemaLocation")))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         this.xsiLessAtts.addAttribute(aUri, aLocal, atts.getQName(i), atts.getType(i), atts.getValue(i));
/*     */       }
/*     */     } 
/*     */     
/* 101 */     super.startElement(nsUri, local, qname, this.xsiLessAtts);
/*     */   }
/*     */   
/*     */   public Object getResult() throws JAXBException, IllegalStateException {
/*     */     return this.core.getResult();
/*     */   }
/*     */   
/*     */   public void handleEvent(ValidationEvent event, boolean canRecover) throws SAXException {
/*     */     this.core.handleEvent(event, canRecover);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\ValidatingUnmarshaller.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */
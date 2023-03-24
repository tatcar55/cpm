/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Templates;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeeFilter
/*     */ {
/*  76 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String prettyPrintStylesheet = "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:wsse='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd'\n  version='1.0'>\n  <xsl:output method='xml' indent='yes'/>\n  <xsl:strip-space elements='*'/>\n  <xsl:template match='/'>\n    <xsl:apply-templates/>\n  </xsl:template>\n  <xsl:template match='node() | @*'>\n    <xsl:choose>\n      <xsl:when test='contains(name(current()), \"wsse:Password\")'>\n        <wsse:Password Type='{@Type}'>****</wsse:Password>\n      </xsl:when>\n      <xsl:otherwise>\n        <xsl:copy>\n          <xsl:apply-templates select='node() | @*'/>\n        </xsl:copy>\n      </xsl:otherwise>\n    </xsl:choose>\n  </xsl:template>\n</xsl:stylesheet>\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OutputStream out;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Templates templates;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeeFilter(OutputStream out, Source stylesheet) throws XWSSecurityException {
/* 117 */     init(out, stylesheet);
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
/*     */   public TeeFilter(OutputStream out, boolean prettyPrint) throws XWSSecurityException {
/* 129 */     if (prettyPrint) {
/* 130 */       init(out, getPrettyPrintStylesheet());
/*     */     } else {
/* 132 */       init(out, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeeFilter(OutputStream out) throws XWSSecurityException {
/* 143 */     init(out, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeeFilter() throws XWSSecurityException {
/* 152 */     init(null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(OutputStream out, Source stylesheet) throws XWSSecurityException {
/* 157 */     this.out = out;
/*     */     
/* 159 */     if (stylesheet == null) {
/* 160 */       this.templates = null;
/*     */     } else {
/* 162 */       TransformerFactory tf = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*     */       
/*     */       try {
/* 165 */         this.templates = tf.newTemplates(stylesheet);
/* 166 */       } catch (TransformerConfigurationException e) {
/* 167 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0147_DIAG_CAUSE_1(), new Object[] { e.getMessage() });
/*     */         
/* 169 */         throw new XWSSecurityException("Unable to use stylesheet", e);
/*     */       } 
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
/*     */   private Source getPrettyPrintStylesheet() {
/* 183 */     byte[] xsltBytes = "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:wsse='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd'\n  version='1.0'>\n  <xsl:output method='xml' indent='yes'/>\n  <xsl:strip-space elements='*'/>\n  <xsl:template match='/'>\n    <xsl:apply-templates/>\n  </xsl:template>\n  <xsl:template match='node() | @*'>\n    <xsl:choose>\n      <xsl:when test='contains(name(current()), \"wsse:Password\")'>\n        <wsse:Password Type='{@Type}'>****</wsse:Password>\n      </xsl:when>\n      <xsl:otherwise>\n        <xsl:copy>\n          <xsl:apply-templates select='node() | @*'/>\n        </xsl:copy>\n      </xsl:otherwise>\n    </xsl:choose>\n  </xsl:template>\n</xsl:stylesheet>\n".getBytes();
/* 184 */     ByteArrayInputStream bais = new ByteArrayInputStream(xsltBytes);
/* 185 */     Source stylesheetSource = new StreamSource(bais);
/* 186 */     return stylesheetSource;
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
/*     */   public void process(SOAPMessage secureMessage) throws XWSSecurityException {
/* 200 */     if (this.out == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 206 */       if (secureMessage.countAttachments() > 0) {
/* 207 */         secureMessage.writeTo(this.out);
/*     */       } else {
/* 209 */         Transformer transformer; if (this.templates == null) {
/*     */           
/* 211 */           transformer = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING).newTransformer();
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 216 */           transformer = this.templates.newTransformer();
/*     */         } 
/* 218 */         Source msgSource = secureMessage.getSOAPPart().getContent();
/* 219 */         transformer.transform(msgSource, new StreamResult(this.out));
/*     */       } 
/* 221 */     } catch (Exception ex) {
/* 222 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0148_UNABLETO_PROCESS_SOAPMESSAGE(new Object[] { ex.getMessage() }));
/* 223 */       throw new XWSSecurityException("Unable to process SOAPMessage", ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\TeeFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceListHeaderBlock
/*     */   extends SecurityHeaderBlockImpl
/*     */ {
/*  83 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Document ownerDoc;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private int size = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceListHeaderBlock() throws XWSSecurityException {
/*     */     try {
/* 104 */       setSOAPElement(getSoapFactory().createElement("ReferenceList", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 109 */       addNamespaceDeclaration("xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */     
/*     */     }
/* 112 */     catch (SOAPException e) {
/* 113 */       log.log(Level.SEVERE, "WSS0360.error.creating.rlhb", e.getMessage());
/* 114 */       throw new XWSSecurityException(e);
/*     */     } 
/* 116 */     this.ownerDoc = getAsSoapElement().getOwnerDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceListHeaderBlock(Document doc) throws XWSSecurityException {
/*     */     try {
/* 127 */       setSOAPElement((SOAPElement)doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:ReferenceList"));
/*     */ 
/*     */ 
/*     */       
/* 131 */       addNamespaceDeclaration("xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */     
/*     */     }
/* 134 */     catch (Exception e) {
/* 135 */       log.log(Level.SEVERE, "WSS0361.error.creating.rlhb", e.getMessage());
/* 136 */       throw new XWSSecurityException(e);
/*     */     } 
/* 138 */     this.ownerDoc = doc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceListHeaderBlock(SOAPElement element) throws XWSSecurityException {
/* 148 */     super(element);
/*     */     
/* 150 */     if (!element.getLocalName().equals("ReferenceList") || !XMLUtil.inEncryptionNS(element)) {
/*     */       
/* 152 */       log.log(Level.SEVERE, "WSS0362.error.creating.rlhb", element.getTagName());
/* 153 */       throw new XWSSecurityException("Invalid ReferenceList passed");
/*     */     } 
/* 155 */     this.ownerDoc = element.getOwnerDocument();
/* 156 */     this.size = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference").getLength();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 161 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addReference(String referenceURI) throws XWSSecurityException {
/*     */     try {
/* 173 */       Element dataRefElement = this.ownerDoc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:DataReference");
/*     */ 
/*     */ 
/*     */       
/* 177 */       dataRefElement.setAttribute("URI", referenceURI);
/* 178 */       XMLUtil.prependChildElement((Element)this, dataRefElement, false, this.ownerDoc);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 183 */       this.size++;
/* 184 */     } catch (Exception e) {
/* 185 */       log.log(Level.SEVERE, "WSS0363.error.adding.datareference", e.getMessage());
/* 186 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getReferences() {
/* 196 */     Vector<String> references = new Vector();
/* 197 */     Iterator<Node> eachChild = getChildElements();
/*     */     
/* 199 */     while (eachChild.hasNext()) {
/* 200 */       Node object = eachChild.next();
/*     */       
/* 202 */       if (object.getNodeType() == 1) {
/* 203 */         SOAPElement element = (SOAPElement)object;
/*     */         
/* 205 */         if (element.getLocalName().equals("DataReference") && XMLUtil.inEncryptionNS(element))
/*     */         {
/* 207 */           references.addElement(element.getAttribute("URI"));
/*     */         }
/*     */       } 
/*     */     } 
/* 211 */     return references.iterator();
/*     */   }
/*     */   
/*     */   public NodeList getDataRefElements() {
/* 215 */     return getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 221 */     return SecurityHeaderBlockImpl.fromSoapElement(element, ReferenceListHeaderBlock.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\ReferenceListHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
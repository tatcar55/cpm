/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureConfirmationHeaderBlock
/*     */   extends SecurityHeaderBlockImpl
/*     */ {
/*  75 */   private String signatureValue = null;
/*  76 */   private String wsuId = null;
/*     */ 
/*     */   
/*     */   public SignatureConfirmationHeaderBlock(String wsuId, String signatureValue) {
/*  80 */     this.wsuId = wsuId;
/*  81 */     this.signatureValue = signatureValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureConfirmationHeaderBlock(SOAPElement element) throws XWSSecurityException {
/*  86 */     if (!"SignatureConfirmation".equals(element.getLocalName()) || !XMLUtil.inWsse11NS(element))
/*     */     {
/*  88 */       throw new XWSSecurityException("Invalid SignatureConfirmation Header Block passed");
/*     */     }
/*     */     
/*  91 */     setSOAPElement(element);
/*     */     
/*  93 */     String wsuId = getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*  94 */     if (!"".equals(wsuId)) {
/*  95 */       setId(wsuId);
/*     */     }
/*  97 */     String signatureValue = getAttribute("Value");
/*     */     try {
/*  99 */       if (!"".equals(signatureValue)) {
/* 100 */         setSignatureValue(signatureValue);
/*     */       }
/* 102 */     } catch (Exception ex) {
/* 103 */       throw new XWSSecurityException(ex);
/*     */     } 
/*     */     
/* 106 */     Iterator<Node> children = getChildElements();
/* 107 */     Node object = null;
/*     */     
/* 109 */     while (children.hasNext()) {
/*     */       
/* 111 */       object = children.next();
/* 112 */       if (object.getNodeType() == 1)
/* 113 */         throw new XWSSecurityException("Child Element Nodes not allowed inside SignatureConfirmation"); 
/* 114 */       if (object.getNodeType() == 2) {
/* 115 */         Attr attr = (Attr)object;
/* 116 */         if ((!"Id".equals(attr.getLocalName()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(attr.getNamespaceURI())) && !"Value".equals(attr.getLocalName()))
/*     */         {
/* 118 */           throw new XWSSecurityException("The attribute " + attr.getLocalName() + "not allowed in SignatureConfirmation");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureConfirmationHeaderBlock(String wsuId) {
/* 126 */     this.wsuId = wsuId;
/*     */   }
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 130 */     return SecurityHeaderBlockImpl.fromSoapElement(element, SignatureConfirmationHeaderBlock.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/*     */     SOAPElement signConfirm;
/*     */     try {
/* 139 */       SOAPFactory sFactory = getSoapFactory();
/* 140 */       signConfirm = sFactory.createElement("SignatureConfirmation", "wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       signConfirm.addNamespaceDeclaration("wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 151 */         if (this.signatureValue != null) {
/* 152 */           Name name = sFactory.createName("Value");
/* 153 */           signConfirm.addAttribute(name, this.signatureValue);
/*     */         } 
/* 155 */       } catch (Exception ex) {
/* 156 */         throw new XWSSecurityException(ex);
/*     */       } 
/* 158 */       if (this.wsuId != null) {
/* 159 */         Name name = sFactory.createName("Id", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/* 160 */         signConfirm.addAttribute(name, this.wsuId);
/*     */       } 
/* 162 */     } catch (SOAPException se) {
/* 163 */       throw new XWSSecurityException("There was an error creating Signature Confirmation " + se.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 168 */     setSOAPElement(signConfirm);
/*     */     
/* 170 */     return signConfirm;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 174 */     return this.wsuId;
/*     */   }
/*     */   
/*     */   public void setId(String wsuId) {
/* 178 */     this.wsuId = wsuId;
/*     */   }
/*     */   
/*     */   public String getSignatureValue() {
/* 182 */     return this.signatureValue;
/*     */   }
/*     */   
/*     */   public void setSignatureValue(String signatureValue) {
/* 186 */     this.signatureValue = signatureValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\SignatureConfirmationHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
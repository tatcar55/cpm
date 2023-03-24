/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.SignatureConfirmation;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*     */ import com.sun.xml.wss.core.SignatureConfirmationHeaderBlock;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
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
/*     */ public class SignatureConfirmationFilter
/*     */ {
/*  86 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void process(FilterProcessingContext context) throws XWSSecurityException {
/* 102 */     if (!context.isInboundMessage()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       List scList = (ArrayList)context.getExtraneousProperty("receivedSignValues");
/*     */ 
/*     */       
/* 110 */       setSignConfirmValues(context, scList);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       SecurityHeader secHeader = context.getSecurableSoapMessage().findSecurityHeader();
/* 121 */       if (secHeader == null) {
/* 122 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1428_SIGNATURE_CONFIRMATION_ERROR());
/* 123 */         throw new XWSSecurityException("Message does not confirm to SignatureConfirmation Policy:wsse11:SignatureConfirmation element not found in Header");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 128 */       Object temp = context.getExtraneousProperty("SignatureConfirmation");
/* 129 */       List scList = null;
/* 130 */       if (temp != null && temp instanceof ArrayList)
/* 131 */         scList = (ArrayList)temp; 
/* 132 */       if (scList != null) {
/*     */         
/* 134 */         SignatureConfirmationHeaderBlock signConfirm = null;
/* 135 */         SOAPElement sc = null;
/*     */         try {
/* 137 */           SOAPFactory factory = SOAPFactory.newInstance();
/* 138 */           Name name = factory.createName("SignatureConfirmation", "wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/*     */ 
/*     */ 
/*     */           
/* 142 */           Iterator<SOAPElement> i = secHeader.getChildElements(name);
/* 143 */           if (!i.hasNext()) {
/* 144 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1428_SIGNATURE_CONFIRMATION_ERROR());
/* 145 */             throw new XWSSecurityException("Message does not confirm to Security Policy:wss11:SignatureConfirmation Element not found");
/*     */           } 
/*     */           
/* 148 */           while (i.hasNext()) {
/* 149 */             sc = i.next();
/*     */             try {
/* 151 */               signConfirm = new SignatureConfirmationHeaderBlock(sc);
/* 152 */             } catch (XWSSecurityException xwsse) {
/* 153 */               log.log(Level.SEVERE, LogStringsMessages.WSS_1435_SIGNATURE_CONFIRMATION_VALIDATION_FAILURE(), (Throwable)xwsse);
/* 154 */               throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Failure in SignatureConfirmation validation\nMessage is: " + xwsse.getMessage(), xwsse);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 160 */             String signValue = signConfirm.getSignatureValue();
/*     */ 
/*     */ 
/*     */             
/* 164 */             if (signValue == null) {
/* 165 */               if (i.hasNext() || !scList.isEmpty()) {
/* 166 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_1435_SIGNATURE_CONFIRMATION_VALIDATION_FAILURE());
/* 167 */                 throw new XWSSecurityException("Failure in SignatureConfirmation Validation");
/*     */               }  continue;
/* 169 */             }  if (scList.contains(signValue)) {
/*     */               
/* 171 */               scList.remove(signValue); continue;
/*     */             } 
/* 173 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1435_SIGNATURE_CONFIRMATION_VALIDATION_FAILURE());
/* 174 */             throw new XWSSecurityException("Mismatch in SignatureConfirmation Element");
/*     */           }
/*     */         
/*     */         }
/* 178 */         catch (SOAPException se) {
/* 179 */           throw new XWSSecurityException(se);
/*     */         } 
/* 181 */         if (!scList.isEmpty()) {
/* 182 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1435_SIGNATURE_CONFIRMATION_VALIDATION_FAILURE());
/* 183 */           throw new XWSSecurityException("Failure in SignatureConfirmation");
/*     */         } 
/* 185 */         context.setExtraneousProperty("SignatureConfirmation", MessageConstants._EMPTY);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setSignConfirmValues(FilterProcessingContext context, List scList) throws XWSSecurityException {
/* 202 */     if (scList != null) {
/* 203 */       Iterator<byte[]> it = scList.iterator();
/* 204 */       if (context instanceof JAXBFilterProcessingContext) {
/* 205 */         JAXBFilterProcessingContext optContext = (JAXBFilterProcessingContext)context;
/* 206 */         SecurityHeader secHeader = optContext.getSecurityHeader();
/*     */         
/* 208 */         ((NamespaceContextEx)optContext.getNamespaceContext()).addWSS11NS();
/* 209 */         if (!it.hasNext()) {
/*     */           
/* 211 */           String id = optContext.generateID();
/* 212 */           SignatureConfirmation scHeader = new SignatureConfirmation(id, optContext.getSOAPVersion());
/* 213 */           secHeader.add((SecurityHeaderElement)scHeader);
/* 214 */           optContext.getSignatureConfirmationIds().add(id);
/*     */         } 
/*     */         
/* 217 */         while (it.hasNext()) {
/* 218 */           byte[] signValue = it.next();
/* 219 */           String id = optContext.generateID();
/* 220 */           SignatureConfirmation scHeader = new SignatureConfirmation(id, optContext.getSOAPVersion());
/* 221 */           scHeader.setValue(signValue);
/* 222 */           secHeader.add((SecurityHeaderElement)scHeader);
/* 223 */           optContext.getSignatureConfirmationIds().add(id);
/*     */         } 
/*     */       } else {
/* 226 */         SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/* 227 */         SecurityHeader secHeader = secureMessage.findOrCreateSecurityHeader();
/*     */         
/* 229 */         if (!it.hasNext()) {
/*     */           
/* 231 */           String id = secureMessage.generateId();
/* 232 */           SignatureConfirmationHeaderBlock signConfirm = new SignatureConfirmationHeaderBlock(id);
/* 233 */           secHeader.insertHeaderBlock((SecurityHeaderBlock)signConfirm);
/* 234 */           context.getSignatureConfirmationIds().add(id);
/*     */         } 
/*     */         
/* 237 */         while (it.hasNext()) {
/*     */           
/* 239 */           String signValue = (String)it.next();
/* 240 */           String id = secureMessage.generateId();
/* 241 */           SignatureConfirmationHeaderBlock signConfirm = new SignatureConfirmationHeaderBlock(id);
/* 242 */           signConfirm.setSignatureValue(signValue);
/* 243 */           secHeader.insertHeaderBlock((SecurityHeaderBlock)signConfirm);
/* 244 */           context.getSignatureConfirmationIds().add(id);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\SignatureConfirmationFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
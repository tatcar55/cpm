/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.message.saaj.SAAJFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.soap.SOAPMessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPMessageContextImpl
/*     */   extends MessageUpdatableContext
/*     */   implements SOAPMessageContext
/*     */ {
/*     */   private Set<String> roles;
/*  73 */   private SOAPMessage soapMsg = null;
/*     */   private WSBinding binding;
/*     */   
/*     */   public SOAPMessageContextImpl(WSBinding binding, Packet packet, Set<String> roles) {
/*  77 */     super(packet);
/*  78 */     this.binding = binding;
/*  79 */     this.roles = roles;
/*     */   }
/*     */   
/*     */   public SOAPMessage getMessage() {
/*  83 */     if (this.soapMsg == null) {
/*     */       try {
/*  85 */         Message m = this.packet.getMessage();
/*  86 */         this.soapMsg = (m != null) ? m.readAsSOAPMessage() : null;
/*  87 */       } catch (SOAPException e) {
/*  88 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*  91 */     return this.soapMsg;
/*     */   }
/*     */   
/*     */   public void setMessage(SOAPMessage soapMsg) {
/*     */     try {
/*  96 */       this.soapMsg = soapMsg;
/*  97 */     } catch (Exception e) {
/*  98 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setPacketMessage(Message newMessage) {
/* 103 */     if (newMessage != null) {
/* 104 */       this.packet.setMessage(newMessage);
/* 105 */       this.soapMsg = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateMessage() {
/* 112 */     if (this.soapMsg != null) {
/* 113 */       this.packet.setMessage(SAAJFactory.create(this.soapMsg));
/* 114 */       this.soapMsg = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object[] getHeaders(QName header, JAXBContext jaxbContext, boolean allRoles) {
/* 119 */     SOAPVersion soapVersion = this.binding.getSOAPVersion();
/*     */     
/* 121 */     List<Object> beanList = new ArrayList();
/*     */     try {
/* 123 */       Iterator<Header> itr = this.packet.getMessage().getMessageHeaders().getHeaders(header, false);
/* 124 */       if (allRoles) {
/* 125 */         while (itr.hasNext()) {
/* 126 */           beanList.add(((Header)itr.next()).readAsJAXB(jaxbContext.createUnmarshaller()));
/*     */         }
/*     */       } else {
/* 129 */         while (itr.hasNext()) {
/* 130 */           Header soapHeader = itr.next();
/*     */           
/* 132 */           String role = soapHeader.getRole(soapVersion);
/* 133 */           if (getRoles().contains(role)) {
/* 134 */             beanList.add(soapHeader.readAsJAXB(jaxbContext.createUnmarshaller()));
/*     */           }
/*     */         } 
/*     */       } 
/* 138 */       return beanList.toArray();
/* 139 */     } catch (Exception e) {
/* 140 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<String> getRoles() {
/* 145 */     return this.roles;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\SOAPMessageContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
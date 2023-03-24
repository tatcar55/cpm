/*     */ package com.sun.xml.ws.transport.tcp.servicechannel.stubs;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelException;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  67 */   private static final QName _InitiateSessionResponse_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "initiateSessionResponse");
/*  68 */   private static final QName _CloseSession_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "closeSession");
/*  69 */   private static final QName _CloseChannelResponse_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "closeChannelResponse");
/*  70 */   private static final QName _CloseChannel_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "closeChannel");
/*  71 */   private static final QName _OpenChannel_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "openChannel");
/*  72 */   private static final QName _InitiateSession_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "initiateSession");
/*  73 */   private static final QName _OpenChannelResponse_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "openChannelResponse");
/*  74 */   private static final QName _ServiceChannelException_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "ServiceChannelException");
/*  75 */   private static final QName _CloseSessionResponse_QNAME = new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "closeSessionResponse");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenChannelResponse createOpenChannelResponse() {
/*  89 */     return new OpenChannelResponse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceChannelException.ServiceChannelExceptionBean createServiceChannelExceptionBean() {
/*  97 */     return new ServiceChannelException.ServiceChannelExceptionBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CloseChannelResponse createCloseChannelResponse() {
/* 105 */     return new CloseChannelResponse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InitiateSessionResponse createInitiateSessionResponse() {
/* 113 */     return new InitiateSessionResponse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenChannel createOpenChannel() {
/* 121 */     return new OpenChannel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InitiateSession createInitiateSession() {
/* 129 */     return new InitiateSession();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CloseChannel createCloseChannel() {
/* 137 */     return new CloseChannel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", name = "initiateSessionResponse")
/*     */   public JAXBElement<InitiateSessionResponse> createInitiateSessionResponse(InitiateSessionResponse value) {
/* 146 */     return new JAXBElement<InitiateSessionResponse>(_InitiateSessionResponse_QNAME, InitiateSessionResponse.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", name = "closeChannelResponse")
/*     */   public JAXBElement<CloseChannelResponse> createCloseChannelResponse(CloseChannelResponse value) {
/* 155 */     return new JAXBElement<CloseChannelResponse>(_CloseChannelResponse_QNAME, CloseChannelResponse.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", name = "closeChannel")
/*     */   public JAXBElement<CloseChannel> createCloseChannel(CloseChannel value) {
/* 164 */     return new JAXBElement<CloseChannel>(_CloseChannel_QNAME, CloseChannel.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", name = "openChannel")
/*     */   public JAXBElement<OpenChannel> createOpenChannel(OpenChannel value) {
/* 173 */     return new JAXBElement<OpenChannel>(_OpenChannel_QNAME, OpenChannel.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", name = "initiateSession")
/*     */   public JAXBElement<InitiateSession> createInitiateSession(InitiateSession value) {
/* 182 */     return new JAXBElement<InitiateSession>(_InitiateSession_QNAME, InitiateSession.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", name = "openChannelResponse")
/*     */   public JAXBElement<OpenChannelResponse> createOpenChannelResponse(OpenChannelResponse value) {
/* 191 */     return new JAXBElement<OpenChannelResponse>(_OpenChannelResponse_QNAME, OpenChannelResponse.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", name = "ServiceChannelException")
/*     */   public JAXBElement<ServiceChannelException.ServiceChannelExceptionBean> createServiceChannelExceptionBean(ServiceChannelException.ServiceChannelExceptionBean value) {
/* 200 */     return new JAXBElement<ServiceChannelException.ServiceChannelExceptionBean>(_ServiceChannelException_QNAME, ServiceChannelException.ServiceChannelExceptionBean.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\stubs\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
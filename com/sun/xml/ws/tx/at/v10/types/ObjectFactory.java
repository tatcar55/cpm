/*     */ package com.sun.xml.ws.tx.at.v10.types;
/*     */ 
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
/*  66 */   private static final QName _Replay_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "Replay");
/*  67 */   private static final QName _Committed_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "Committed");
/*  68 */   private static final QName _ReadOnly_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "ReadOnly");
/*  69 */   private static final QName _Aborted_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "Aborted");
/*  70 */   private static final QName _Commit_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "Commit");
/*  71 */   private static final QName _Prepare_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "Prepare");
/*  72 */   private static final QName _Prepared_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "Prepared");
/*  73 */   private static final QName _Rollback_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "Rollback");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notification createNotification() {
/*  87 */     return new Notification();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ATAlwaysCapability createATAlwaysCapability() {
/*  95 */     return new ATAlwaysCapability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ATAssertion createATAssertion() {
/* 103 */     return new ATAssertion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrepareResponse createPrepareResponse() {
/* 111 */     return new PrepareResponse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReplayResponse createReplayResponse() {
/* 119 */     return new ReplayResponse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "Replay")
/*     */   public JAXBElement<Notification> createReplay(Notification value) {
/* 128 */     return new JAXBElement<Notification>(_Replay_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "Committed")
/*     */   public JAXBElement<Notification> createCommitted(Notification value) {
/* 137 */     return new JAXBElement<Notification>(_Committed_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "ReadOnly")
/*     */   public JAXBElement<Notification> createReadOnly(Notification value) {
/* 146 */     return new JAXBElement<Notification>(_ReadOnly_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "Aborted")
/*     */   public JAXBElement<Notification> createAborted(Notification value) {
/* 155 */     return new JAXBElement<Notification>(_Aborted_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "Commit")
/*     */   public JAXBElement<Notification> createCommit(Notification value) {
/* 164 */     return new JAXBElement<Notification>(_Commit_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "Prepare")
/*     */   public JAXBElement<Notification> createPrepare(Notification value) {
/* 173 */     return new JAXBElement<Notification>(_Prepare_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "Prepared")
/*     */   public JAXBElement<Notification> createPrepared(Notification value) {
/* 182 */     return new JAXBElement<Notification>(_Prepared_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", name = "Rollback")
/*     */   public JAXBElement<Notification> createRollback(Notification value) {
/* 191 */     return new JAXBElement<Notification>(_Rollback_QNAME, Notification.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\types\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
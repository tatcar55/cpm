/*     */ package com.sun.xml.ws.tx.at.v11.types;
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
/*  66 */   private static final QName _Aborted_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "Aborted");
/*  67 */   private static final QName _Commit_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "Commit");
/*  68 */   private static final QName _ReadOnly_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ReadOnly");
/*  69 */   private static final QName _Committed_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "Committed");
/*  70 */   private static final QName _Rollback_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "Rollback");
/*  71 */   private static final QName _Prepare_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "Prepare");
/*  72 */   private static final QName _Prepared_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "Prepared");
/*     */ 
/*     */ 
/*     */ 
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
/*  86 */     return new Notification();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ATAssertion createATAssertion() {
/*  94 */     return new ATAssertion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", name = "Aborted")
/*     */   public JAXBElement<Notification> createAborted(Notification value) {
/* 103 */     return new JAXBElement<Notification>(_Aborted_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", name = "Commit")
/*     */   public JAXBElement<Notification> createCommit(Notification value) {
/* 112 */     return new JAXBElement<Notification>(_Commit_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", name = "ReadOnly")
/*     */   public JAXBElement<Notification> createReadOnly(Notification value) {
/* 121 */     return new JAXBElement<Notification>(_ReadOnly_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", name = "Committed")
/*     */   public JAXBElement<Notification> createCommitted(Notification value) {
/* 130 */     return new JAXBElement<Notification>(_Committed_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", name = "Rollback")
/*     */   public JAXBElement<Notification> createRollback(Notification value) {
/* 139 */     return new JAXBElement<Notification>(_Rollback_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", name = "Prepare")
/*     */   public JAXBElement<Notification> createPrepare(Notification value) {
/* 148 */     return new JAXBElement<Notification>(_Prepare_QNAME, Notification.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", name = "Prepared")
/*     */   public JAXBElement<Notification> createPrepared(Notification value) {
/* 157 */     return new JAXBElement<Notification>(_Prepared_QNAME, Notification.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v11\types\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
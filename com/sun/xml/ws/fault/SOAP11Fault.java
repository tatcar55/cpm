/*     */ package com.sun.xml.ws.fault;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"faultcode", "faultstring", "faultactor", "detail"})
/*     */ @XmlRootElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
/*     */ class SOAP11Fault
/*     */   extends SOAPFaultBuilder
/*     */ {
/*     */   @XmlElement(namespace = "")
/*     */   private QName faultcode;
/*     */   @XmlElement(namespace = "")
/*     */   private String faultstring;
/*     */   @XmlElement(namespace = "")
/*     */   private String faultactor;
/*     */   @XmlElement(namespace = "")
/*     */   private DetailType detail;
/*     */   
/*     */   SOAP11Fault() {}
/*     */   
/*     */   SOAP11Fault(QName code, String reason, String actor, Element detailObject) {
/* 115 */     this.faultcode = code;
/* 116 */     this.faultstring = reason;
/* 117 */     this.faultactor = actor;
/* 118 */     if (detailObject != null) {
/* 119 */       if ((detailObject.getNamespaceURI() == null || "".equals(detailObject.getNamespaceURI())) && "detail".equals(detailObject.getLocalName())) {
/*     */         
/* 121 */         this.detail = new DetailType();
/* 122 */         for (Element detailEntry : DOMUtil.getChildElements(detailObject)) {
/* 123 */           this.detail.getDetails().add(detailEntry);
/*     */         }
/*     */       } else {
/* 126 */         this.detail = new DetailType(detailObject);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   SOAP11Fault(SOAPFault fault) {
/* 132 */     this.faultcode = fault.getFaultCodeAsQName();
/* 133 */     this.faultstring = fault.getFaultString();
/* 134 */     this.faultactor = fault.getFaultActor();
/* 135 */     if (fault.getDetail() != null) {
/* 136 */       this.detail = new DetailType();
/* 137 */       Iterator<Element> iter = fault.getDetail().getDetailEntries();
/* 138 */       while (iter.hasNext()) {
/* 139 */         Element fd = iter.next();
/* 140 */         this.detail.getDetails().add(fd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   QName getFaultcode() {
/* 146 */     return this.faultcode;
/*     */   }
/*     */   
/*     */   void setFaultcode(QName faultcode) {
/* 150 */     this.faultcode = faultcode;
/*     */   }
/*     */ 
/*     */   
/*     */   String getFaultString() {
/* 155 */     return this.faultstring;
/*     */   }
/*     */   
/*     */   void setFaultstring(String faultstring) {
/* 159 */     this.faultstring = faultstring;
/*     */   }
/*     */   
/*     */   String getFaultactor() {
/* 163 */     return this.faultactor;
/*     */   }
/*     */   
/*     */   void setFaultactor(String faultactor) {
/* 167 */     this.faultactor = faultactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DetailType getDetail() {
/* 175 */     return this.detail;
/*     */   }
/*     */   
/*     */   void setDetail(DetailType detail) {
/* 179 */     this.detail = detail;
/*     */   }
/*     */   
/*     */   protected Throwable getProtocolException() {
/*     */     try {
/* 184 */       SOAPFault fault = SOAPVersion.SOAP_11.getSOAPFactory().createFault(this.faultstring, this.faultcode);
/* 185 */       fault.setFaultActor(this.faultactor);
/* 186 */       if (this.detail != null) {
/* 187 */         Detail d = fault.addDetail();
/* 188 */         for (Element det : this.detail.getDetails()) {
/* 189 */           Node n = fault.getOwnerDocument().importNode(det, true);
/* 190 */           d.appendChild(n);
/*     */         } 
/*     */       } 
/* 193 */       return new ServerSOAPFaultException(fault);
/* 194 */     } catch (SOAPException e) {
/* 195 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\fault\SOAP11Fault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
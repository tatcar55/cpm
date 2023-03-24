/*     */ package com.sun.xml.ws.fault;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "Fault", namespace = "http://www.w3.org/2003/05/soap-envelope")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"code", "reason", "node", "role", "detail"})
/*     */ class SOAP12Fault
/*     */   extends SOAPFaultBuilder
/*     */ {
/*     */   @XmlTransient
/*     */   private static final String ns = "http://www.w3.org/2003/05/soap-envelope";
/*     */   @XmlElement(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Code")
/*     */   private CodeType code;
/*     */   @XmlElement(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Reason")
/*     */   private ReasonType reason;
/*     */   @XmlElement(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Node")
/*     */   private String node;
/*     */   @XmlElement(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Role")
/*     */   private String role;
/*     */   @XmlElement(namespace = "http://www.w3.org/2003/05/soap-envelope", name = "Detail")
/*     */   private DetailType detail;
/*     */   
/*     */   SOAP12Fault() {}
/*     */   
/*     */   SOAP12Fault(CodeType code, ReasonType reason, String node, String role, DetailType detail) {
/* 123 */     this.code = code;
/* 124 */     this.reason = reason;
/* 125 */     this.node = node;
/* 126 */     this.role = role;
/* 127 */     this.detail = detail;
/*     */   }
/*     */   
/*     */   SOAP12Fault(CodeType code, ReasonType reason, String node, String role, Element detailObject) {
/* 131 */     this.code = code;
/* 132 */     this.reason = reason;
/* 133 */     this.node = node;
/* 134 */     this.role = role;
/* 135 */     if (detailObject != null) {
/* 136 */       if (detailObject.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope") && detailObject.getLocalName().equals("Detail")) {
/* 137 */         this.detail = new DetailType();
/* 138 */         for (Element detailEntry : DOMUtil.getChildElements(detailObject)) {
/* 139 */           this.detail.getDetails().add(detailEntry);
/*     */         }
/*     */       } else {
/* 142 */         this.detail = new DetailType(detailObject);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   SOAP12Fault(SOAPFault fault) {
/* 148 */     this.code = new CodeType(fault.getFaultCodeAsQName());
/*     */     try {
/* 150 */       fillFaultSubCodes(fault);
/* 151 */     } catch (SOAPException e) {
/* 152 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 155 */     this.reason = new ReasonType(fault.getFaultString());
/* 156 */     this.role = fault.getFaultRole();
/* 157 */     this.node = fault.getFaultNode();
/* 158 */     if (fault.getDetail() != null) {
/* 159 */       this.detail = new DetailType();
/* 160 */       Iterator<Element> iter = fault.getDetail().getDetailEntries();
/* 161 */       while (iter.hasNext()) {
/* 162 */         Element fd = iter.next();
/* 163 */         this.detail.getDetails().add(fd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   SOAP12Fault(QName code, String reason, Element detailObject) {
/* 169 */     this(new CodeType(code), new ReasonType(reason), (String)null, (String)null, detailObject);
/*     */   }
/*     */   
/*     */   CodeType getCode() {
/* 173 */     return this.code;
/*     */   }
/*     */   
/*     */   ReasonType getReason() {
/* 177 */     return this.reason;
/*     */   }
/*     */   
/*     */   String getNode() {
/* 181 */     return this.node;
/*     */   }
/*     */   
/*     */   String getRole() {
/* 185 */     return this.role;
/*     */   }
/*     */ 
/*     */   
/*     */   DetailType getDetail() {
/* 190 */     return this.detail;
/*     */   }
/*     */ 
/*     */   
/*     */   void setDetail(DetailType detail) {
/* 195 */     this.detail = detail;
/*     */   }
/*     */ 
/*     */   
/*     */   String getFaultString() {
/* 200 */     return ((TextType)this.reason.texts().get(0)).getText();
/*     */   }
/*     */   
/*     */   protected Throwable getProtocolException() {
/*     */     try {
/* 205 */       SOAPFault fault = SOAPVersion.SOAP_12.getSOAPFactory().createFault();
/* 206 */       if (this.reason != null) {
/* 207 */         for (TextType tt : this.reason.texts()) {
/* 208 */           fault.setFaultString(tt.getText());
/*     */         }
/*     */       }
/*     */       
/* 212 */       if (this.code != null) {
/* 213 */         fault.setFaultCode(this.code.getValue());
/* 214 */         fillFaultSubCodes(fault, this.code.getSubcode());
/*     */       } 
/*     */       
/* 217 */       if (this.detail != null && this.detail.getDetail(0) != null) {
/* 218 */         Detail detail = fault.addDetail();
/* 219 */         for (Node obj : this.detail.getDetails()) {
/* 220 */           Node n = fault.getOwnerDocument().importNode(obj, true);
/* 221 */           detail.appendChild(n);
/*     */         } 
/*     */       } 
/*     */       
/* 225 */       if (this.node != null) {
/* 226 */         fault.setFaultNode(this.node);
/*     */       }
/*     */       
/* 229 */       return new ServerSOAPFaultException(fault);
/* 230 */     } catch (SOAPException e) {
/* 231 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillFaultSubCodes(SOAPFault fault, SubcodeType subcode) throws SOAPException {
/* 239 */     if (subcode != null) {
/* 240 */       fault.appendFaultSubcode(subcode.getValue());
/* 241 */       fillFaultSubCodes(fault, subcode.getSubcode());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillFaultSubCodes(SOAPFault fault) throws SOAPException {
/* 249 */     Iterator<QName> subcodes = fault.getFaultSubcodes();
/* 250 */     SubcodeType firstSct = null;
/* 251 */     while (subcodes.hasNext()) {
/* 252 */       QName subcode = subcodes.next();
/* 253 */       if (firstSct == null) {
/* 254 */         firstSct = new SubcodeType(subcode);
/* 255 */         this.code.setSubcode(firstSct);
/*     */         continue;
/*     */       } 
/* 258 */       SubcodeType nextSct = new SubcodeType(subcode);
/* 259 */       firstSct.setSubcode(nextSct);
/* 260 */       firstSct = nextSct;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\fault\SOAP12Fault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
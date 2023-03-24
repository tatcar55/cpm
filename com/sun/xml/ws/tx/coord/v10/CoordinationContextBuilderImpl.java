/*     */ package com.sun.xml.ws.tx.coord.v10;
/*     */ 
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.ws.tx.coord.common.CoordinationContextBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.WSCUtil;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.CoordinationContext;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.CoordinationContextType;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.Expires;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoordinationContextBuilderImpl
/*     */   extends CoordinationContextBuilder
/*     */ {
/*     */   protected CoordinationContextIF _fromHeader(Header header) {
/*     */     try {
/*  65 */       Unmarshaller unmarshaller = XmlTypeAdapter.CoordinationContextImpl.jaxbContext.createUnmarshaller();
/*  66 */       CoordinationContext cct = (CoordinationContext)header.readAsJAXB(unmarshaller);
/*  67 */       new XmlTypeAdapter(); return XmlTypeAdapter.adapt(cct);
/*  68 */     } catch (JAXBException e) {
/*  69 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CoordinationContextIF build() {
/*  76 */     CoordinationContext cct = buildContext();
/*     */     
/*  78 */     new XmlTypeAdapter(); return XmlTypeAdapter.adapt(cct);
/*     */   }
/*     */   
/*     */   public JAXBRIContext getJAXBRIContext() {
/*  82 */     return XmlTypeAdapter.CoordinationContextImpl.jaxbContext;
/*     */   }
/*     */   
/*     */   private CoordinationContext buildContext() {
/*  86 */     CoordinationContext cct = new CoordinationContext();
/*  87 */     if (this.mustUnderstand) {
/*  88 */       if (this.soapVersion == null) {
/*  89 */         throw new WebServiceException("SOAP version is not specified!");
/*     */       }
/*  91 */       cct.getOtherAttributes().put(new QName(this.soapVersion.nsUri, "mustUnderstand"), "1");
/*     */     } 
/*  93 */     cct.setCoordinationType(this.coordinationType);
/*     */     
/*  95 */     CoordinationContextType.Identifier IdentifierObj = new CoordinationContextType.Identifier();
/*  96 */     IdentifierObj.setValue(this.identifier);
/*  97 */     cct.setIdentifier(IdentifierObj);
/*     */     
/*  99 */     Expires expiresObj = new Expires();
/* 100 */     expiresObj.setValue(this.expires);
/* 101 */     cct.setExpires(expiresObj);
/*     */     
/* 103 */     cct.setRegistrationService(getEPR());
/* 104 */     return cct;
/*     */   }
/*     */   
/*     */   private MemberSubmissionEndpointReference getEPR() {
/* 108 */     Element ele = WSCUtil.referenceElementTxId(this.txId);
/* 109 */     Element ele2 = WSCUtil.referenceElementRoutingInfo();
/* 110 */     return (MemberSubmissionEndpointReference)EndpointReferenceBuilder.MemberSubmission().address(this.address).referenceParameter(new Element[] { ele }).referenceParameter(new Element[] { ele2 }).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\CoordinationContextBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
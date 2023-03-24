/*     */ package com.sun.xml.ws.tx.coord.v11;
/*     */ 
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.tx.coord.common.CoordinationContextBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.WSCUtil;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.CoordinationContext;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.CoordinationContextType;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.Expires;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
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
/*     */ 
/*     */ public class CoordinationContextBuilderImpl
/*     */   extends CoordinationContextBuilder
/*     */ {
/*     */   public CoordinationContextIF build() {
/*  65 */     CoordinationContext cct = buildContext();
/*     */     
/*  67 */     return XmlTypeAdapter.adapt(cct);
/*     */   }
/*     */   
/*     */   protected CoordinationContextIF _fromHeader(Header header) {
/*     */     try {
/*  72 */       Unmarshaller unmarshaller = XmlTypeAdapter.CoordinationContextImpl.jaxbContext.createUnmarshaller();
/*  73 */       CoordinationContext cct = (CoordinationContext)header.readAsJAXB(unmarshaller);
/*  74 */       return XmlTypeAdapter.adapt(cct);
/*  75 */     } catch (JAXBException e) {
/*  76 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public JAXBRIContext getJAXBRIContext() {
/*  81 */     return XmlTypeAdapter.CoordinationContextImpl.jaxbContext;
/*     */   }
/*     */   
/*     */   private CoordinationContext buildContext() {
/*  85 */     CoordinationContext cct = new CoordinationContext();
/*  86 */     if (this.mustUnderstand) {
/*  87 */       if (this.soapVersion == null) {
/*  88 */         throw new WebServiceException("SOAP version is not specified!");
/*     */       }
/*  90 */       cct.getOtherAttributes().put(new QName(this.soapVersion.nsUri, "mustUnderstand"), "1");
/*     */     } 
/*  92 */     cct.setCoordinationType(this.coordinationType);
/*     */     
/*  94 */     CoordinationContextType.Identifier IdentifierObj = new CoordinationContextType.Identifier();
/*  95 */     IdentifierObj.setValue(this.identifier);
/*  96 */     cct.setIdentifier(IdentifierObj);
/*     */     
/*  98 */     Expires expiresObj = new Expires();
/*  99 */     expiresObj.setValue(this.expires);
/* 100 */     cct.setExpires(expiresObj);
/*     */     
/* 102 */     cct.setRegistrationService(getEPR());
/* 103 */     return cct;
/*     */   }
/*     */ 
/*     */   
/*     */   private W3CEndpointReference getEPR() {
/* 108 */     Element referenceParameter = WSCUtil.referenceElementTxId(this.txId);
/* 109 */     Element referenceParameter2 = WSCUtil.referenceElementRoutingInfo();
/* 110 */     return (new W3CEndpointReferenceBuilder()).address(this.address).referenceParameter(referenceParameter).referenceParameter(referenceParameter2).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\CoordinationContextBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
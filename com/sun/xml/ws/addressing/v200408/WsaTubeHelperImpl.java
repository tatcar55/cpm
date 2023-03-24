/*     */ package com.sun.xml.ws.addressing.v200408;
/*     */ 
/*     */ import com.sun.xml.ws.addressing.WsaTubeHelper;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
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
/*     */ 
/*     */ public class WsaTubeHelperImpl
/*     */   extends WsaTubeHelper
/*     */ {
/*     */   static final JAXBContext jc;
/*     */   
/*     */   static {
/*     */     try {
/*  63 */       jc = JAXBContext.newInstance(new Class[] { ProblemAction.class, ProblemHeaderQName.class });
/*     */     }
/*  65 */     catch (JAXBException e) {
/*  66 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public WsaTubeHelperImpl(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/*  71 */     super(binding, seiModel, wsdlPort);
/*     */   }
/*     */   
/*     */   private Marshaller createMarshaller() throws JAXBException {
/*  75 */     Marshaller marshaller = jc.createMarshaller();
/*  76 */     marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
/*  77 */     return marshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void getProblemActionDetail(String action, Element element) {
/*  82 */     ProblemAction pa = new ProblemAction(action);
/*     */     try {
/*  84 */       createMarshaller().marshal(pa, element);
/*  85 */     } catch (JAXBException e) {
/*  86 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void getInvalidMapDetail(QName name, Element element) {
/*  92 */     ProblemHeaderQName phq = new ProblemHeaderQName(name);
/*     */     try {
/*  94 */       createMarshaller().marshal(phq, element);
/*  95 */     } catch (JAXBException e) {
/*  96 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void getMapRequiredDetail(QName name, Element element) {
/* 102 */     getInvalidMapDetail(name, element);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\v200408\WsaTubeHelperImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
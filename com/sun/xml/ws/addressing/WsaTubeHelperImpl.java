/*     */ package com.sun.xml.ws.addressing;
/*     */ 
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
/*  62 */       jc = JAXBContext.newInstance(new Class[] { ProblemAction.class, ProblemHeaderQName.class });
/*     */     }
/*  64 */     catch (JAXBException e) {
/*  65 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public WsaTubeHelperImpl(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/*  70 */     super(binding, seiModel, wsdlPort);
/*     */   }
/*     */   
/*     */   private Marshaller createMarshaller() throws JAXBException {
/*  74 */     Marshaller marshaller = jc.createMarshaller();
/*  75 */     marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
/*  76 */     return marshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void getProblemActionDetail(String action, Element element) {
/*  81 */     ProblemAction pa = new ProblemAction(action);
/*     */     try {
/*  83 */       createMarshaller().marshal(pa, element);
/*  84 */     } catch (JAXBException e) {
/*  85 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void getInvalidMapDetail(QName name, Element element) {
/*  91 */     ProblemHeaderQName phq = new ProblemHeaderQName(name);
/*     */     try {
/*  93 */       createMarshaller().marshal(phq, element);
/*  94 */     } catch (JAXBException e) {
/*  95 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void getMapRequiredDetail(QName name, Element element) {
/* 101 */     getInvalidMapDetail(name, element);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WsaTubeHelperImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
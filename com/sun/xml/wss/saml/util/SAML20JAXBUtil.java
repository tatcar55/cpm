/*    */ package com.sun.xml.wss.saml.util;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedExceptionAction;
/*    */ import javax.xml.bind.JAXBContext;
/*    */ import javax.xml.bind.JAXBException;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SAML20JAXBUtil
/*    */ {
/*    */   static JAXBContext jaxbContext;
/* 65 */   public static final WSSNamespacePrefixMapper prefixMapper11 = new WSSNamespacePrefixMapper();
/* 66 */   public static final WSSNamespacePrefixMapper prefixMapper12 = new WSSNamespacePrefixMapper(true);
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/*    */     try {
/* 72 */       AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*    */             public Object run() throws Exception {
/* 74 */               SAML20JAXBUtil.jaxbContext = JAXBContext.newInstance("com.sun.xml.wss.saml.internal.saml20.jaxb20");
/* 75 */               return null;
/*    */             }
/*    */           });
/* 78 */     } catch (Exception je) {
/* 79 */       throw new WebServiceException(je);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static JAXBContext getJAXBContext() {
/* 84 */     return jaxbContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static JAXBContext getJAXBContext(String namespaces) throws JAXBException {
/* 93 */     jaxbContext = JAXBContext.newInstance("com.sun.xml.wss.saml.internal.saml20.jaxb20:" + namespaces);
/*    */     
/* 95 */     return jaxbContext;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\sam\\util\SAML20JAXBUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
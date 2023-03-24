/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBUtil
/*     */ {
/*  66 */   public static final WSSNamespacePrefixMapper prefixMapper11 = new WSSNamespacePrefixMapper();
/*  67 */   public static final WSSNamespacePrefixMapper prefixMapper12 = new WSSNamespacePrefixMapper(true);
/*  68 */   private static ThreadLocal<WeakReference<JAXBContext>> jc = new ThreadLocal<WeakReference<JAXBContext>>();
/*     */   
/*     */   private static JAXBContext jaxbContext;
/*     */   private static JAXBContext customjaxbContext;
/*     */   
/*     */   static {
/*  74 */     initJAXBContext();
/*     */   }
/*     */   
/*     */   public static JAXBContext getCustomIdentityJAXBContext() {
/*  78 */     initCustomJAXBContext();
/*  79 */     return customjaxbContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initCustomJAXBContext() {
/*     */     try {
/*  86 */       AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */           {
/*     */             public Object run() throws Exception {
/*  89 */               JAXBUtil.customjaxbContext = JAXBContext.newInstance("com.sun.xml.ws.security.opt.crypto.dsig:com.sun.xml.ws.security.opt.crypto.dsig.keyinfo:com.sun.xml.security.core.dsig:com.sun.xml.security.core.xenc:com.sun.xml.ws.security.opt.impl.keyinfo:com.sun.xml.ws.security.opt.impl.reference:com.sun.xml.ws.security.secext10:com.sun.xml.ws.security.wsu10:com.sun.xml.ws.security.secext11:com.sun.xml.ws.security.secconv.impl.bindings:com.sun.xml.ws.security.secconv.impl.wssx.bindings:com.sun.xml.security.core.ai:");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  95 */               return null;
/*     */             }
/*     */           });
/*  98 */     } catch (Exception je) {
/*  99 */       throw new WebServiceException(je);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initJAXBContext() {
/*     */     try {
/* 108 */       AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */             public Object run() throws Exception {
/* 110 */               JAXBUtil.jaxbContext = JAXBContext.newInstance("com.sun.xml.ws.security.opt.crypto.dsig:com.sun.xml.ws.security.opt.crypto.dsig.keyinfo:com.sun.xml.security.core.dsig:com.sun.xml.security.core.xenc:com.sun.xml.ws.security.opt.impl.keyinfo:com.sun.xml.ws.security.opt.impl.reference:com.sun.xml.ws.security.secext10:com.sun.xml.ws.security.wsu10:com.sun.xml.ws.security.secext11:com.sun.xml.ws.security.secconv.impl.bindings:com.sun.xml.ws.security.secconv.impl.wssx.bindings:");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 116 */               return null;
/*     */             }
/*     */           });
/* 119 */     } catch (Exception je) {
/* 120 */       throw new WebServiceException(je);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static JAXBContext getJAXBContext() {
/* 125 */     return jaxbContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Marshaller createMarshaller(SOAPVersion soapVersion) throws JAXBException {
/*     */     try {
/* 131 */       Marshaller marshaller = jaxbContext.createMarshaller();
/* 132 */       if (SOAPVersion.SOAP_11 == soapVersion) {
/* 133 */         marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", prefixMapper11);
/*     */       } else {
/* 135 */         marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", prefixMapper12);
/*     */       } 
/* 137 */       marshaller.setProperty("jaxb.fragment", Boolean.valueOf(true));
/* 138 */       marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.valueOf(false));
/* 139 */       return marshaller;
/* 140 */     } catch (PropertyException pe) {
/* 141 */       throw new JAXBException("Error occurred while setting security marshaller properties", pe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setSEIJAXBContext(JAXBContext context) {
/* 147 */     jc.set(new WeakReference<JAXBContext>(context));
/*     */   }
/*     */   
/*     */   public static JAXBContext getSEIJAXBContext() {
/* 151 */     return ((WeakReference<JAXBContext>)jc.get()).get();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\JAXBUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
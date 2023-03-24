/*     */ package com.sun.xml.ws.rx.util;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JaxbContextRepository
/*     */ {
/*  68 */   private static final Logger LOGGER = Logger.getLogger(JaxbContextRepository.class);
/*     */   private Map<AddressingVersion, JAXBRIContext> jaxbContexts;
/*     */   
/*  71 */   private ThreadLocal<Map<AddressingVersion, Unmarshaller>> threadLocalUnmarshallers = new ThreadLocal<Map<AddressingVersion, Unmarshaller>>()
/*     */     {
/*     */       protected Map<AddressingVersion, Unmarshaller> initialValue()
/*     */       {
/*  75 */         Map<AddressingVersion, Unmarshaller> result = new HashMap<AddressingVersion, Unmarshaller>();
/*  76 */         for (Map.Entry<AddressingVersion, JAXBRIContext> entry : (Iterable<Map.Entry<AddressingVersion, JAXBRIContext>>)JaxbContextRepository.this.jaxbContexts.entrySet()) {
/*     */           try {
/*  78 */             result.put(entry.getKey(), ((JAXBRIContext)entry.getValue()).createUnmarshaller());
/*  79 */           } catch (JAXBException ex) {
/*  80 */             JaxbContextRepository.LOGGER.severe("Unable to create JAXB unmarshaller", ex);
/*  81 */             throw new RxRuntimeException("Unable to create JAXB unmarshaller", ex);
/*     */           } 
/*     */         } 
/*  84 */         return result;
/*     */       }
/*     */     };
/*     */   
/*     */   public JaxbContextRepository(Class<?>... classes) throws RxRuntimeException {
/*  89 */     this.jaxbContexts = new HashMap<AddressingVersion, JAXBRIContext>();
/*  90 */     for (AddressingVersion av : AddressingVersion.values()) {
/*  91 */       this.jaxbContexts.put(av, createContext(av, classes));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final JAXBRIContext createContext(AddressingVersion av, Class<?>... classes) throws RxRuntimeException {
/* 100 */     LinkedList<Class<?>> jaxbElementClasses = new LinkedList<Class<?>>(Arrays.asList(classes));
/* 101 */     jaxbElementClasses.add(av.eprType.eprClass);
/*     */     
/* 103 */     Map<Class<?>, Class<?>> eprClassReplacementMap = new HashMap<Class<?>, Class<?>>();
/* 104 */     eprClassReplacementMap.put(EndpointReference.class, av.eprType.eprClass);
/*     */     
/*     */     try {
/* 107 */       return JAXBRIContext.newInstance((Class[])jaxbElementClasses.<Class<?>[]>toArray((Class<?>[][])classes), null, eprClassReplacementMap, null, false, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 113 */     catch (JAXBException ex) {
/* 114 */       throw new RxRuntimeException("Unable to create JAXB RI Context", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Unmarshaller getUnmarshaller(AddressingVersion av) throws RxRuntimeException {
/* 133 */     return (Unmarshaller)((Map)this.threadLocalUnmarshallers.get()).get(av);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBRIContext getJaxbContext(AddressingVersion av) {
/* 144 */     return this.jaxbContexts.get(av);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\JaxbContextRepository.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
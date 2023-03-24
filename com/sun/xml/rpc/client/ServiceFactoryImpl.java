/*     */ package com.sun.xml.rpc.client;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.ConfiguredService;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import java.net.URL;
/*     */ import java.util.Properties;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.Service;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import javax.xml.rpc.ServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceFactoryImpl
/*     */   extends ServiceFactory
/*     */ {
/*     */   public Service createService(URL wsdlDocumentLocation, QName name) throws ServiceException {
/*  51 */     if (wsdlDocumentLocation == null) {
/*  52 */       throw new IllegalArgumentException("wsdlDocumentLocation must not be null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  58 */       ConfiguredService service = new ConfiguredService(name, wsdlDocumentLocation);
/*     */       
/*  60 */       if (service.getServiceException() != null)
/*  61 */         throw service.getServiceException(); 
/*  62 */       return (Service)service;
/*  63 */     } catch (ModelerException ex) {
/*  64 */       throw new ServiceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Service createService(QName name) throws ServiceException {
/*  70 */     return new BasicService(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Service createService(Class<?> serviceInterface, QName name) throws ServiceException {
/*  76 */     if (!Service.class.isAssignableFrom(serviceInterface)) {
/*  77 */       throw new ServiceExceptionImpl("service.interface.required", serviceInterface.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     String serviceImplementationName = serviceInterface.getName() + "_Impl";
/*  84 */     Service service = createService(serviceImplementationName);
/*  85 */     if (service.getServiceName().equals(name)) {
/*  86 */       return service;
/*     */     }
/*  88 */     throw new ServiceExceptionImpl("service.implementation.not.found", serviceInterface.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Service createService(String serviceImplementationName) throws ServiceException {
/*  96 */     if (serviceImplementationName == null) {
/*  97 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*     */     try {
/* 101 */       Class<?> serviceImplementationClass = Thread.currentThread().getContextClassLoader().loadClass(serviceImplementationName);
/*     */ 
/*     */ 
/*     */       
/* 105 */       if (!BasicService.class.isAssignableFrom(serviceImplementationClass))
/*     */       {
/*     */         
/* 108 */         throw new ServiceExceptionImpl("service.implementation.not.found", serviceImplementationName);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 115 */         Service service = (Service)serviceImplementationClass.newInstance();
/*     */         
/* 117 */         if (service.getServiceName() != null) {
/* 118 */           return service;
/*     */         }
/* 120 */         throw new ServiceExceptionImpl("service.implementation.not.found", serviceImplementationName);
/*     */ 
/*     */       
/*     */       }
/* 124 */       catch (InstantiationException e) {
/* 125 */         throw new ServiceExceptionImpl("service.implementation.cannot.create", serviceImplementationClass.getName());
/*     */       
/*     */       }
/* 128 */       catch (IllegalAccessException e) {
/* 129 */         throw new ServiceExceptionImpl("service.implementation.cannot.create", serviceImplementationClass.getName());
/*     */       }
/*     */     
/*     */     }
/* 133 */     catch (ClassNotFoundException e) {
/* 134 */       throw new ServiceExceptionImpl("service.implementation.not.found", serviceImplementationName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Service loadService(Class<?> serviceInterface) throws ServiceException {
/* 142 */     if (serviceInterface == null) {
/* 143 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/* 147 */     if (!Service.class.isAssignableFrom(serviceInterface)) {
/* 148 */       throw new ServiceExceptionImpl("service.interface.required", serviceInterface.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 153 */     String serviceImplementationName = serviceInterface.getName() + "_Impl";
/*     */     
/* 155 */     Service service = createService(serviceImplementationName);
/*     */     
/* 157 */     return service;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Service loadService(URL wsdlDocumentLocation, Class<?> serviceInterface, Properties properties) throws ServiceException {
/* 167 */     if (wsdlDocumentLocation == null) {
/* 168 */       throw new IllegalArgumentException("wsdlDocumentLocation must not be null");
/*     */     }
/*     */     
/* 171 */     if (serviceInterface == null) {
/* 172 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/* 176 */     if (!Service.class.isAssignableFrom(serviceInterface)) {
/* 177 */       throw new ServiceExceptionImpl("service.interface.required", serviceInterface.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     String serviceImplementationName = serviceInterface.getName() + "_Impl";
/*     */     
/* 184 */     Service service = createService(serviceImplementationName);
/*     */     
/* 186 */     return service;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Service loadService(URL wsdlDocumentLocation, QName ServiceName, Properties properties) throws ServiceException {
/* 196 */     if (wsdlDocumentLocation == null) {
/* 197 */       throw new IllegalArgumentException("wsdlDocumentLocation must not be null");
/*     */     }
/*     */ 
/*     */     
/* 201 */     String serviceImplementationName = null;
/* 202 */     if (properties != null) {
/* 203 */       serviceImplementationName = properties.getProperty("serviceImplementationName");
/*     */     }
/*     */     
/* 206 */     if (serviceImplementationName == null) {
/* 207 */       throw new IllegalArgumentException("Properties should contain the property:serviceImplementationName");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 212 */     Service service = createService(serviceImplementationName);
/* 213 */     if (service.getServiceName().equals(ServiceName)) {
/* 214 */       return service;
/*     */     }
/* 216 */     throw new ServiceExceptionImpl("service.implementation.not.found", serviceImplementationName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\ServiceFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
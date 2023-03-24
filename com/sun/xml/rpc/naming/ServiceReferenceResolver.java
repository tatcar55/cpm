/*    */ package com.sun.xml.rpc.naming;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Map;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.Name;
/*    */ import javax.naming.Reference;
/*    */ import javax.naming.StringRefAddr;
/*    */ import javax.naming.spi.ObjectFactory;
/*    */ import javax.xml.rpc.Service;
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
/*    */ public class ServiceReferenceResolver
/*    */   implements ObjectFactory
/*    */ {
/* 47 */   protected static final Map registeredServices = Collections.synchronizedMap(new HashMap<Object, Object>());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
/* 53 */     if (obj instanceof StringRefAddr) {
/* 54 */       StringRefAddr ref = (StringRefAddr)obj;
/* 55 */       if (ref.getType() == "ServiceName")
/* 56 */         return registeredServices.get(ref.getContent()); 
/* 57 */       if (ref.getType() == "ServiceClassName") {
/* 58 */         Object serviceKey = ref.getContent();
/* 59 */         Object service = registeredServices.get(serviceKey);
/* 60 */         if (service == null) {
/* 61 */           ClassLoader ctxLoader = Thread.currentThread().getContextClassLoader();
/*    */           
/* 63 */           service = Class.forName((String)ref.getContent(), true, ctxLoader).newInstance();
/*    */           
/* 65 */           registeredServices.put(serviceKey, service);
/*    */         } 
/* 67 */         return service;
/*    */       } 
/*    */     } 
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   public static String registerService(Service service) {
/* 74 */     String serviceName = getQualifiedServiceNameString(service);
/* 75 */     registeredServices.put(serviceName, service);
/* 76 */     return serviceName;
/*    */   }
/*    */   
/*    */   protected static String getQualifiedServiceNameString(Service service) {
/* 80 */     String serviceName = "";
/* 81 */     URL wsdlLocation = service.getWSDLDocumentLocation();
/* 82 */     if (wsdlLocation != null) {
/* 83 */       serviceName = serviceName + wsdlLocation.toExternalForm() + ":";
/*    */     }
/* 85 */     serviceName = serviceName + service.getServiceName().toString();
/* 86 */     return serviceName;
/*    */   }
/*    */   
/*    */   public Reference getServiceClassReference(Class serviceClass) {
/* 90 */     return getServiceClassReference(serviceClass.getName());
/*    */   }
/*    */   
/*    */   public Reference getServiceClassReference(String serviceClassName) {
/* 94 */     Reference reference = new Reference(serviceClassName, "com.sun.xml.rpc.naming.ServiceReferenceResolver", null);
/*    */     
/* 96 */     reference.add(new StringRefAddr("ServiceClassName", serviceClassName));
/* 97 */     return reference;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\naming\ServiceReferenceResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
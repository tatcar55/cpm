/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import com.sun.xml.rpc.client.BasicService;
/*     */ import com.sun.xml.rpc.client.ServiceExceptionImpl;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.rmi.Remote;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.Call;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import javax.xml.rpc.encoding.TypeMapping;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfiguredService
/*     */   extends BasicService
/*     */ {
/*     */   protected URL wsdlDocumentLocation;
/*     */   protected ServiceInfo configuration;
/*     */   protected DynamicProxyBuilder dynamicProxyBuilder;
/*     */   private ServiceException serviceException;
/*  57 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  60 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfiguredService(QName name, URL wsdlLocation) {
/*  65 */     this(name, wsdlLocation, SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   public ConfiguredService(QName name, URL wsdlLocation, SOAPVersion ver) {
/*  69 */     super(name);
/*  70 */     init(ver);
/*     */     
/*  72 */     this.wsdlDocumentLocation = wsdlLocation;
/*     */ 
/*     */ 
/*     */     
/*  76 */     ServiceInfoBuilder configurationBuilder = new ServiceInfoBuilder(wsdlLocation.toExternalForm(), name);
/*     */     
/*     */     try {
/*  79 */       this.configuration = configurationBuilder.buildServiceInfo();
/*  80 */     } catch (ModelerException ex) {
/*  81 */       this.serviceException = new ServiceException((Throwable)ex);
/*  82 */     } catch (ServiceException ex) {
/*  83 */       this.serviceException = ex;
/*     */     } 
/*  85 */     if (this.configuration == null) {
/*     */       return;
/*     */     }
/*     */     
/*  89 */     this.dynamicProxyBuilder = createDynamicProxyBuilder();
/*     */ 
/*     */     
/*  92 */     Iterator<QName> eachPortName = this.configuration.getPortNames();
/*  93 */     while (eachPortName.hasNext()) {
/*  94 */       QName currentPortName = eachPortName.next();
/*  95 */       addPort(currentPortName);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ServiceException getServiceException() {
/* 100 */     return this.serviceException;
/*     */   }
/*     */   
/*     */   protected DynamicProxyBuilder createDynamicProxyBuilder() {
/* 104 */     return new DynamicProxyBuilder(this.internalTypeRegistry, getHandlerRegistry(), this.configuration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getWSDLDocumentLocation() {
/* 110 */     return this.wsdlDocumentLocation;
/*     */   }
/*     */   
/*     */   public Call[] getCalls(QName portName) throws ServiceException {
/* 114 */     ArrayList<Call> calls = new ArrayList();
/*     */ 
/*     */     
/* 117 */     PortInfo portInfo = getPortInfo(portName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     Iterator<OperationInfo> eachOperation = portInfo.getOperations();
/* 123 */     while (eachOperation.hasNext()) {
/* 124 */       OperationInfo currentOperation = eachOperation.next();
/*     */       
/* 126 */       calls.add(createCall(portName, currentOperation.getName()));
/*     */     } 
/*     */     
/* 129 */     return calls.<Call>toArray(new Call[calls.size()]);
/*     */   }
/*     */   
/*     */   public Call createCall(QName portName, String operationName) throws ServiceException {
/* 133 */     return createCall(portName, new QName(operationName));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Call createCall(QName portName, QName operationName) throws ServiceException {
/* 139 */     ConfiguredCall call = new ConfiguredCall(this.internalTypeRegistry, getHandlerRegistry(), this.configuration);
/* 140 */     call.setPortName(portName);
/* 141 */     call.setOperationName(operationName);
/*     */     
/* 143 */     return call;
/*     */   }
/*     */   
/*     */   protected PortInfo getPortInfo(QName portName) throws ServiceException {
/* 147 */     if (!this.ports.contains(portName)) {
/* 148 */       throw portNotFoundException(portName);
/*     */     }
/*     */     
/* 151 */     return this.configuration.getPortInfo(portName);
/*     */   }
/*     */   
/*     */   protected ServiceExceptionImpl portNotFoundException(QName portName) {
/* 155 */     return new ServiceExceptionImpl("dii.service.does.not.contain.port", new Object[] { this.name, portName });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getPorts() {
/* 161 */     return this.ports.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Remote getPort(Class portInterface) throws ServiceException {
/* 166 */     QName portName = getPortNameForInterface(portInterface);
/* 167 */     return getPort(portName, portInterface);
/*     */   }
/*     */   
/*     */   protected QName getPortNameForInterface(Class portInterface) {
/* 171 */     TypeMapping mapping = this.typeRegistry.getTypeMapping(this.soapEncodingConstants.getSOAPEncodingNamespace());
/*     */ 
/*     */     
/* 174 */     Iterator<QName> eachPortInfo = this.configuration.getPortNames();
/* 175 */     while (eachPortInfo.hasNext()) {
/* 176 */       QName currentPortName = eachPortInfo.next();
/* 177 */       PortInfo currentPort = this.configuration.getPortInfo(currentPortName);
/*     */ 
/*     */ 
/*     */       
/* 181 */       Method[] methods = portInterface.getMethods();
/* 182 */       if (currentPort.getOperationCount() != methods.length) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 187 */       boolean allMethodsMatched = true;
/* 188 */       for (int i = 0; i < methods.length; i++) {
/*     */ 
/*     */         
/* 191 */         Iterator<OperationInfo> eachOperation = currentPort.getOperations();
/* 192 */         boolean operationMatchesMethod = false;
/* 193 */         while (eachOperation.hasNext()) {
/* 194 */           OperationInfo currentOperation = eachOperation.next();
/*     */ 
/*     */           
/* 197 */           if (!currentOperation.getName().getLocalPart().equals(methods[i].getName())) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 206 */           Class[] parameters = methods[i].getParameterTypes();
/* 207 */           int paramLength = parameters.length;
/*     */           
/* 209 */           if (currentOperation.getParameterCount() != paramLength) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 214 */           boolean parametersMatched = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 245 */           if (parametersMatched) {
/* 246 */             operationMatchesMethod = true;
/*     */           }
/*     */         } 
/* 249 */         if (!operationMatchesMethod) {
/* 250 */           allMethodsMatched = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 256 */       if (allMethodsMatched) {
/* 257 */         return currentPortName;
/*     */       }
/*     */     } 
/* 260 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote getPort(QName portName, Class portInterface) throws ServiceException {
/* 267 */     return this.dynamicProxyBuilder.buildDynamicProxyFor(getPortInfo(portName), portInterface);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\ConfiguredService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
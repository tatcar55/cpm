/*     */ package com.sun.xml.rpc.client;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.BasicCall;
/*     */ import com.sun.xml.rpc.client.dii.CallEx;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistryImpl;
/*     */ import com.sun.xml.rpc.encoding.SerializerConstants;
/*     */ import com.sun.xml.rpc.encoding.StandardTypeMappings;
/*     */ import com.sun.xml.rpc.encoding.TypeMappingImpl;
/*     */ import com.sun.xml.rpc.encoding.TypeMappingRegistryImpl;
/*     */ import com.sun.xml.rpc.naming.ServiceReferenceResolver;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import java.net.URL;
/*     */ import java.rmi.Remote;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ import javax.naming.StringRefAddr;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.Call;
/*     */ import javax.xml.rpc.Service;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import javax.xml.rpc.encoding.TypeMapping;
/*     */ import javax.xml.rpc.encoding.TypeMappingRegistry;
/*     */ import javax.xml.rpc.handler.HandlerRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicService
/*     */   implements Service, SerializerConstants, Referenceable
/*     */ {
/*     */   protected static final String DEFAULT_OPERATION_STYLE = "rpc";
/*     */   protected QName name;
/*     */   protected List ports;
/*     */   protected TypeMappingRegistry typeRegistry;
/*     */   protected InternalTypeMappingRegistry internalTypeRegistry;
/*     */   private HandlerRegistry handlerRegistry;
/*     */   
/*     */   protected QName[] getPortsAsArray() {
/*  74 */     return (QName[])this.ports.toArray((Object[])new QName[this.ports.size()]);
/*     */   }
/*     */   
/*     */   protected void init(QName name, TypeMappingRegistry registry) {
/*  78 */     init();
/*  79 */     this.name = name;
/*  80 */     this.typeRegistry = registry;
/*  81 */     this.internalTypeRegistry = (InternalTypeMappingRegistry)new InternalTypeMappingRegistryImpl(registry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  86 */     this.ports = new ArrayList();
/*  87 */     this.handlerRegistry = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicService(QName name, TypeMappingRegistry registry) {
/*  92 */     init(name, registry);
/*     */   }
/*     */   
/*     */   public BasicService(QName name) {
/*  96 */     init(name, createStandardTypeMappingRegistry());
/*     */   }
/*     */   
/*     */   public BasicService(QName name, QName[] ports) {
/* 100 */     this(name);
/* 101 */     addPorts(ports);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicService(QName name, QName[] ports, TypeMappingRegistry registry) {
/* 108 */     this(name, registry);
/* 109 */     addPorts(ports);
/*     */   }
/*     */   
/*     */   protected void addPorts(QName[] ports) {
/* 113 */     if (ports != null) {
/* 114 */       for (int i = 0; i < ports.length; i++) {
/* 115 */         addPort(ports[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public BasicService(QName name, Iterator<QName> eachPort) {
/* 121 */     this(name);
/* 122 */     while (eachPort.hasNext()) {
/* 123 */       addPort(eachPort.next());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addPort(QName port) {
/* 128 */     this.ports.add(port);
/*     */   }
/*     */ 
/*     */   
/*     */   public Remote getPort(Class portInterface) throws ServiceException {
/* 133 */     throw noWsdlException();
/*     */   }
/*     */   
/*     */   protected ServiceException noWsdlException() {
/* 137 */     return new ServiceExceptionImpl("dii.service.no.wsdl.available");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Remote getPort(QName portName, Class portInterface) throws ServiceException {
/* 143 */     throw noWsdlException();
/*     */   }
/*     */   
/*     */   public Call[] getCalls(QName portName) throws ServiceException {
/* 147 */     throw noWsdlException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Call createCall(QName portName) throws ServiceException {
/* 152 */     if (!this.ports.contains(portName)) {
/* 153 */       addPort(portName);
/*     */     }
/* 155 */     CallEx newCall = (CallEx)createCall();
/* 156 */     newCall.setPortName(portName);
/*     */     
/* 158 */     return (Call)newCall;
/*     */   }
/*     */ 
/*     */   
/*     */   public Call createCall(QName portName, String operationName) throws ServiceException {
/* 163 */     return createCall(portName, new QName(operationName));
/*     */   }
/*     */ 
/*     */   
/*     */   public Call createCall(QName portName, QName operationName) throws ServiceException {
/* 168 */     CallEx newCall = (CallEx)createCall(portName);
/* 169 */     newCall.setOperationName(operationName);
/*     */     
/* 171 */     return (Call)newCall;
/*     */   }
/*     */   
/*     */   public Call createCall() throws ServiceException {
/* 175 */     BasicCall call = new BasicCall(this.internalTypeRegistry, getHandlerRegistry());
/*     */     
/* 177 */     call.setProperty("javax.xml.rpc.soap.operation.style", "rpc");
/*     */ 
/*     */ 
/*     */     
/* 181 */     return (Call)call;
/*     */   }
/*     */   
/*     */   public QName getServiceName() {
/* 185 */     return this.name;
/*     */   }
/*     */   
/*     */   public Iterator getPorts() throws ServiceException {
/* 189 */     if (this.ports.size() == 0)
/* 190 */       throw noWsdlException(); 
/* 191 */     return this.ports.iterator();
/*     */   }
/*     */   
/*     */   public URL getWSDLDocumentLocation() {
/* 195 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeMappingRegistry getTypeMappingRegistry() {
/* 202 */     return this.typeRegistry;
/*     */   }
/*     */   
/*     */   public HandlerRegistry getHandlerRegistry() {
/* 206 */     if (this.handlerRegistry == null) {
/* 207 */       this.handlerRegistry = new HandlerRegistryImpl();
/*     */     }
/*     */     
/* 210 */     return this.handlerRegistry;
/*     */   }
/*     */   
/*     */   public static TypeMappingRegistry createStandardTypeMappingRegistry() {
/* 214 */     TypeMappingRegistryImpl typeMappingRegistryImpl = new TypeMappingRegistryImpl();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 219 */       TypeMapping soapMappings = createSoapMappings(SOAPVersion.SOAP_11);
/* 220 */       typeMappingRegistryImpl.register("http://schemas.xmlsoap.org/soap/encoding/", soapMappings);
/*     */ 
/*     */ 
/*     */       
/* 224 */       TypeMapping soap12Mappings = createSoapMappings(SOAPVersion.SOAP_12);
/*     */       
/* 226 */       typeMappingRegistryImpl.register("http://www.w3.org/2002/06/soap-encoding", soap12Mappings);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       TypeMapping literalMappings = createLiteralMappings();
/* 232 */       typeMappingRegistryImpl.register("", literalMappings);
/*     */     }
/* 234 */     catch (Exception ex) {
/*     */       
/* 236 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 239 */     return (TypeMappingRegistry)typeMappingRegistryImpl;
/*     */   }
/*     */   
/*     */   protected static TypeMapping createSoapMappings() {
/* 243 */     return createSoapMappings(SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   protected static TypeMapping createSoapMappings(SOAPVersion ver) {
/* 247 */     TypeMappingImpl soapMappings = new TypeMappingImpl(StandardTypeMappings.getSoap(ver));
/*     */ 
/*     */     
/* 250 */     if (ver == SOAPVersion.SOAP_11) {
/* 251 */       soapMappings.setSupportedEncodings(new String[] { "http://schemas.xmlsoap.org/soap/encoding/" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 261 */     else if (ver == SOAPVersion.SOAP_12) {
/* 262 */       soapMappings.setSupportedEncodings(new String[] { "http://www.w3.org/2002/06/soap-encoding" });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     return (TypeMapping)soapMappings;
/*     */   }
/*     */   
/*     */   protected static TypeMapping createLiteralMappings() {
/* 277 */     TypeMappingImpl rpcLiteralMappings = new TypeMappingImpl(StandardTypeMappings.getRPCLiteral());
/*     */ 
/*     */     
/* 280 */     rpcLiteralMappings.setSupportedEncodings(new String[] { "" });
/*     */     
/* 282 */     return (TypeMapping)rpcLiteralMappings;
/*     */   }
/*     */   
/*     */   protected static class HandlerRegistryImpl implements HandlerRegistry {
/*     */     Map handlerChainsForPorts;
/*     */     
/*     */     public HandlerRegistryImpl() {
/* 289 */       init();
/*     */     }
/*     */     
/*     */     protected void init() {
/* 293 */       this.handlerChainsForPorts = new HashMap<Object, Object>();
/*     */     }
/*     */     
/*     */     public List getHandlerChain(QName portName) {
/* 297 */       if (this.handlerChainsForPorts.get(portName) == null) {
/* 298 */         setHandlerChain(portName, new HandlerChainInfoImpl());
/*     */       }
/*     */ 
/*     */       
/* 302 */       return (List)this.handlerChainsForPorts.get(portName);
/*     */     }
/*     */     
/*     */     public void setHandlerChain(QName portName, List chainInfo) {
/* 306 */       this.handlerChainsForPorts.put(portName, chainInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Reference getReference() throws NamingException {
/* 312 */     Reference reference = new Reference(getClass().getName(), "com.sun.xml.rpc.naming.ServiceReferenceResolver", null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 317 */     String serviceName = ServiceReferenceResolver.registerService(this);
/* 318 */     reference.add(new StringRefAddr("ServiceName", serviceName));
/* 319 */     return reference;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\BasicService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
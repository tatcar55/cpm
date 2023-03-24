/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.webservice.WebService;
/*     */ import com.sun.xml.rpc.client.dii.webservice.WebServicesClient;
/*     */ import com.sun.xml.rpc.client.dii.webservice.parser.WebServicesClientParser;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.exporter.ModelImporter;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.ClientProcessorEnvironment;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.spi.tools.ModelInfo;
/*     */ import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ParameterMode;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceInfoBuilder
/*     */ {
/*     */   protected WSDLModelInfo modelInfo;
/*     */   protected QName serviceName;
/*  68 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */   private Package[] packages;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  72 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */   
/*     */   public ServiceInfoBuilder(String wsdlLocation, QName serviceName) {
/*  76 */     this(wsdlLocation, serviceName, SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   public ServiceInfoBuilder(String wsdlLocation, QName serviceName, SOAPVersion ver) {
/*  80 */     init(ver);
/*     */     
/*  82 */     ClientProcessorEnvironment env = new ClientProcessorEnvironment(new ByteArrayOutputStream(), null, null);
/*     */     
/*  84 */     Configuration config = new Configuration((ProcessorEnvironment)env);
/*     */     
/*  86 */     this.modelInfo = new WSDLModelInfo();
/*     */     
/*  88 */     config.setModelInfo((ModelInfo)this.modelInfo);
/*  89 */     this.modelInfo.setParent(config);
/*     */     
/*  91 */     this.modelInfo.setLocation(wsdlLocation);
/*     */     
/*  93 */     this.serviceName = serviceName;
/*  94 */     this.modelInfo.setName(serviceName.getLocalPart());
/*     */   }
/*     */   
/*     */   public void setPackage(String packageName) {
/*  98 */     this.modelInfo.setJavaPackageName(packageName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceInfo buildServiceInfo() throws ServiceException {
/* 103 */     Model wsdlModel = null;
/*     */     
/* 105 */     wsdlModel = getModel(this.modelInfo.getLocation());
/*     */     
/* 107 */     if (wsdlModel == null)
/*     */     {
/* 109 */       wsdlModel = getModel(true, false);
/*     */     }
/*     */     
/* 112 */     Service serviceModel = wsdlModel.getServiceByName(this.serviceName);
/*     */     
/* 114 */     if (serviceModel == null) {
/* 115 */       String knownServiceNames = "";
/* 116 */       Iterator<Service> eachService = wsdlModel.getServices();
/* 117 */       while (eachService.hasNext()) {
/* 118 */         Service service = eachService.next();
/*     */         
/* 120 */         knownServiceNames = knownServiceNames + service.getName().toString();
/* 121 */         if (eachService.hasNext()) {
/* 122 */           knownServiceNames = knownServiceNames + "\n";
/*     */         }
/*     */       } 
/* 125 */       throw new DynamicInvocationException("dii.wsdl.service.unknown", new Object[] { this.serviceName, knownServiceNames });
/*     */     } 
/*     */     
/* 128 */     ServiceInfo serviceInfo = new ServiceInfo();
/* 129 */     serviceInfo.setDefaultNamespace(wsdlModel.getTargetNamespaceURI());
/* 130 */     Iterator<Port> eachPort = serviceModel.getPorts();
/* 131 */     while (eachPort.hasNext()) {
/* 132 */       Port portModel = eachPort.next();
/* 133 */       PortInfo portInfo = serviceInfo.getPortInfo((QName)portModel.getProperty("com.sun.xml.rpc.processor.model.WSDLPortName"));
/* 134 */       portInfo.setPortTypeName((QName)portModel.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName"));
/*     */       
/* 136 */       buildPortInfo(portInfo, portModel);
/*     */     } 
/*     */     
/* 139 */     return serviceInfo;
/*     */   }
/*     */   
/*     */   protected Model getModel() {
/* 143 */     Properties options = new Properties();
/*     */     
/* 145 */     options.put("validationWSDL", new Boolean(false));
/* 146 */     options.put("explicitServiceContext", new Boolean(false));
/* 147 */     options.put("useWSIBasicProfile", "true");
/* 148 */     options.put("unwrapDocLitWrappers", "false");
/* 149 */     return this.modelInfo.buildModel(options);
/*     */   }
/*     */   
/*     */   protected Model getModel(boolean useWSI, boolean done) throws ServiceException, ModelerException {
/* 153 */     Properties options = new Properties();
/*     */     
/* 155 */     options.put("validationWSDL", new Boolean(false));
/* 156 */     options.put("explicitServiceContext", new Boolean(false));
/*     */     
/* 158 */     if (useWSI) {
/* 159 */       options.put("useWSIBasicProfile", "true");
/* 160 */       options.put("unwrapDocLitWrappers", "false");
/*     */     } 
/*     */     
/* 163 */     Model model = null;
/*     */     try {
/* 165 */       model = this.modelInfo.buildModel(options);
/* 166 */     } catch (ModelerException ex) {
/* 167 */       if (!done) {
/* 168 */         model = getModel(false, true);
/*     */       } else {
/* 170 */         throw ex;
/*     */       } 
/* 172 */     }  return model;
/*     */   }
/*     */   
/*     */   protected void buildPortInfo(PortInfo portInfo, Port portModel) {
/* 176 */     portInfo.setTargetEndpoint(portModel.getAddress());
/*     */     
/* 178 */     Iterator<Operation> eachOperation = portModel.getOperations();
/* 179 */     while (eachOperation.hasNext()) {
/* 180 */       Operation operationModel = eachOperation.next();
/* 181 */       OperationInfo operationInfo = portInfo.createOperationForName(operationModel.getName().getLocalPart());
/*     */       
/* 183 */       buildOperationInfo(operationInfo, operationModel);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Package[] getPackages() {
/* 188 */     if (this.packages == null) {
/* 189 */       this.packages = Package.getPackages();
/*     */     }
/* 191 */     return this.packages;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildOperationInfo(OperationInfo operationInfo, Operation operationModel) {
/* 196 */     if (operationModel.getStyle() == SOAPStyle.DOCUMENT && operationModel.getUse() == SOAPUse.LITERAL) {
/* 197 */       buildDocumentOperation(operationInfo, operationModel);
/*     */     }
/* 199 */     else if (operationModel.getStyle() == SOAPStyle.RPC && operationModel.getUse() == SOAPUse.LITERAL) {
/* 200 */       buildRpcLiteralOperation(operationInfo, operationModel);
/*     */     }
/* 202 */     else if (operationModel.getStyle() == SOAPStyle.RPC && operationModel.getUse() == SOAPUse.ENCODED) {
/* 203 */       buildRpcOperation(operationInfo, operationModel);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void buildRpcOperation(OperationInfo operationInfo, Operation operationModel) {
/* 210 */     JavaMethod methodModel = operationModel.getJavaMethod();
/* 211 */     Request requestModel = operationModel.getRequest();
/* 212 */     Response responseModel = operationModel.getResponse();
/*     */     
/* 214 */     Block requestWsdlModel = requestModel.getBodyBlocks().next();
/*     */     
/* 216 */     Block responseWsdlModel = null;
/* 217 */     if (responseModel != null) {
/* 218 */       responseWsdlModel = responseModel.getBodyBlocks().next();
/*     */     } else {
/* 220 */       operationInfo.setIsOneWay(true);
/*     */     } 
/* 222 */     RPCRequestUnorderedStructureType requestType = (RPCRequestUnorderedStructureType)requestWsdlModel.getType();
/*     */     
/* 224 */     RPCResponseStructureType responseType = null;
/* 225 */     if (responseWsdlModel != null) {
/* 226 */       responseType = (RPCResponseStructureType)responseWsdlModel.getType();
/*     */     }
/* 228 */     Iterator<Parameter> eachRequestParameter = requestModel.getParameters();
/* 229 */     Iterator<Parameter> eachResponseParameter = null;
/* 230 */     if (responseModel != null)
/* 231 */       eachResponseParameter = responseModel.getParameters(); 
/* 232 */     Iterator<SOAPStructureMember> eachRequestWsdlParameter = requestType.getMembers();
/*     */     
/* 234 */     Iterator<SOAPStructureMember> eachResponseWsdlParameter = null;
/* 235 */     if (responseType != null) {
/* 236 */       eachResponseWsdlParameter = responseType.getMembers();
/*     */     }
/* 238 */     JavaType returnJavaTypeModel = methodModel.getReturnType();
/* 239 */     if (returnJavaTypeModel != null && 
/* 240 */       !"void".equals(returnJavaTypeModel.getName())) {
/* 241 */       Parameter returnParameter = eachResponseParameter.next();
/*     */       
/* 243 */       SOAPStructureMember returnWsdlParameter = eachResponseWsdlParameter.next();
/*     */       
/* 245 */       QName returnXmlType = returnParameter.getType().getName();
/* 246 */       Class returnJavaType = getJavaClassFor(returnParameter.getType());
/* 247 */       operationInfo.setReturnType(returnXmlType, returnJavaType);
/*     */     } 
/*     */ 
/*     */     
/* 251 */     while (eachRequestParameter.hasNext()) {
/* 252 */       Parameter currentParameter = eachRequestParameter.next();
/* 253 */       SOAPStructureMember currentWsdlParameter = eachRequestWsdlParameter.next();
/*     */       
/* 255 */       ParameterMode mode = ParameterMode.IN;
/* 256 */       if (currentParameter.getLinkedParameter() != null) {
/* 257 */         mode = ParameterMode.INOUT;
/*     */       }
/*     */       
/* 260 */       addParameterTo(operationInfo, currentWsdlParameter, mode);
/*     */     } 
/* 262 */     if (eachResponseParameter != null) {
/* 263 */       while (eachResponseParameter.hasNext()) {
/* 264 */         Parameter currentParameter = eachResponseParameter.next();
/* 265 */         SOAPStructureMember currentWsdlParameter = eachResponseWsdlParameter.next();
/*     */         
/* 267 */         if (currentParameter.getLinkedParameter() != null) {
/*     */           continue;
/*     */         }
/*     */         
/* 271 */         addParameterTo(operationInfo, currentWsdlParameter, ParameterMode.OUT);
/*     */       } 
/*     */     }
/*     */     
/* 275 */     operationInfo.setProperty("javax.xml.rpc.soap.operation.style", "rpc");
/* 276 */     operationInfo.setProperty("javax.xml.rpc.encodingstyle.namespace.uri", this.soapEncodingConstants.getURIEncoding());
/*     */   }
/*     */   
/*     */   protected void buildDocumentOperation(OperationInfo operationInfo, Operation operationModel) {
/* 280 */     JavaMethod methodModel = operationModel.getJavaMethod();
/* 281 */     Request requestModel = operationModel.getRequest();
/* 282 */     Response responseModel = operationModel.getResponse();
/* 283 */     Block requestWsdlModel = null;
/* 284 */     Block responseWsdlModel = null;
/* 285 */     Iterator<Parameter> eachRequestParameter = null;
/* 286 */     Iterator<Parameter> eachResponseParameter = null;
/*     */     
/* 288 */     if (requestModel != null) {
/* 289 */       Iterator<Block> requestIter = requestModel.getBodyBlocks();
/* 290 */       if (requestIter.hasNext())
/* 291 */         requestWsdlModel = requestIter.next(); 
/* 292 */       eachRequestParameter = requestModel.getParameters();
/*     */     } 
/* 294 */     if (responseModel != null) {
/* 295 */       Iterator<Block> responseIter = responseModel.getBodyBlocks();
/* 296 */       if (responseIter.hasNext())
/* 297 */         responseWsdlModel = responseIter.next(); 
/* 298 */       eachResponseParameter = responseModel.getParameters();
/*     */     } else {
/* 300 */       operationInfo.setIsOneWay(true);
/*     */     } 
/* 302 */     LiteralType requestType = null;
/* 303 */     QName requestWsdlName = null;
/* 304 */     QName requestXmlType = null;
/* 305 */     if (requestWsdlModel != null) {
/* 306 */       requestType = (LiteralType)requestWsdlModel.getType();
/* 307 */       requestXmlType = requestType.getName();
/* 308 */       requestWsdlName = requestWsdlModel.getName();
/*     */     } 
/* 310 */     LiteralType responseType = null;
/* 311 */     QName responseWsdlName = null;
/* 312 */     if (responseWsdlModel != null) {
/* 313 */       responseType = (LiteralType)responseWsdlModel.getType();
/* 314 */       responseWsdlName = responseWsdlModel.getName();
/*     */     } 
/*     */ 
/*     */     
/* 318 */     JavaType returnJavaTypeModel = methodModel.getReturnType();
/* 319 */     if (returnJavaTypeModel != null && 
/* 320 */       !"void".equals(returnJavaTypeModel.getName()) && 
/* 321 */       eachResponseParameter.hasNext()) {
/* 322 */       Parameter returnParameter = eachResponseParameter.next();
/* 323 */       LiteralType returnParameterLitType = (LiteralType)returnParameter.getType();
/*     */ 
/*     */ 
/*     */       
/* 327 */       ArrayList<ParameterMemberInfo> memberInfos = new ArrayList();
/* 328 */       if (returnParameterLitType instanceof com.sun.xml.rpc.processor.model.literal.LiteralSequenceType || returnParameterLitType instanceof LiteralStructuredType) {
/*     */         
/* 330 */         Iterator<LiteralElementMember> members = ((LiteralStructuredType)returnParameterLitType).getElementMembers();
/*     */         
/* 332 */         while (members.hasNext()) {
/* 333 */           LiteralElementMember member = members.next();
/* 334 */           String memName = member.getName().getLocalPart();
/* 335 */           LiteralType memberType = member.getType();
/* 336 */           JavaType javaType = memberType.getJavaType();
/* 337 */           Class javaClass = getJavaClassFor((AbstractType)memberType);
/* 338 */           QName xmlTypeName = memberType.getName();
/* 339 */           ParameterMemberInfo info = new ParameterMemberInfo();
/* 340 */           info.addParameterMemberInfo(memName, xmlTypeName, javaClass);
/* 341 */           memberInfos.add(info);
/*     */         } 
/*     */       } 
/*     */       
/* 345 */       operationInfo.setReturnTypeQName(returnParameterLitType.getName());
/* 346 */       operationInfo.setReturnMembers(memberInfos);
/*     */       
/* 348 */       operationInfo.setReturnTypeModel(new LiteralElementMember(responseWsdlName, responseType));
/* 349 */       Class returnJavaType = getJavaClassFor(returnParameter.getType());
/* 350 */       operationInfo.setReturnClassName(returnParameter.getType().getJavaType().getName());
/*     */       
/* 352 */       operationInfo.setReturnType(responseWsdlName, returnJavaType);
/* 353 */       operationInfo.setReturnTypeQName(returnParameterLitType.getName());
/* 354 */       operationInfo.setReturnMembers(memberInfos);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 359 */     while (eachRequestParameter.hasNext()) {
/* 360 */       Parameter currentParameter = eachRequestParameter.next();
/*     */       
/* 362 */       LiteralType currentParameterLitType = (LiteralType)currentParameter.getType();
/*     */ 
/*     */ 
/*     */       
/* 366 */       ArrayList<ParameterMemberInfo> memberInfos = new ArrayList();
/* 367 */       if (currentParameterLitType instanceof com.sun.xml.rpc.processor.model.literal.LiteralSequenceType || currentParameterLitType instanceof LiteralStructuredType) {
/*     */         
/* 369 */         Iterator<LiteralElementMember> members = ((LiteralStructuredType)currentParameterLitType).getElementMembers();
/*     */         
/* 371 */         while (members.hasNext()) {
/* 372 */           LiteralElementMember member = members.next();
/* 373 */           String memName = member.getName().getLocalPart();
/* 374 */           LiteralType memberType = member.getType();
/* 375 */           JavaType javaType = memberType.getJavaType();
/* 376 */           Class javaClass = getJavaClassFor((AbstractType)memberType);
/* 377 */           QName xmlTypeName = memberType.getName();
/* 378 */           ParameterMemberInfo info = new ParameterMemberInfo();
/* 379 */           info.addParameterMemberInfo(memName, xmlTypeName, javaClass);
/* 380 */           memberInfos.add(info);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 385 */       ParameterMode mode = ParameterMode.IN;
/* 386 */       if (currentParameter.getLinkedParameter() != null) {
/* 387 */         mode = ParameterMode.INOUT;
/*     */       }
/* 389 */       operationInfo.addMemberInfos(memberInfos);
/* 390 */       Class parameterJavaType = getJavaClassFor(currentParameter.getType());
/*     */       
/* 392 */       operationInfo.addParameterModel(requestWsdlName.getLocalPart(), new LiteralElementMember(requestWsdlName, requestType));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 398 */       operationInfo.addParameter(requestWsdlName.getLocalPart(), requestWsdlName, parameterJavaType, mode);
/*     */       
/* 400 */       operationInfo.addMemberInfos(memberInfos);
/* 401 */       operationInfo.addParameterXmlTypeQName(currentParameterLitType.getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 406 */     if (eachResponseParameter != null) {
/* 407 */       while (eachResponseParameter.hasNext()) {
/* 408 */         Parameter currentParameter = eachResponseParameter.next();
/* 409 */         LiteralType currentParameterLitType = (LiteralType)currentParameter.getType();
/*     */ 
/*     */ 
/*     */         
/* 413 */         ArrayList<ParameterMemberInfo> memberInfos = new ArrayList();
/* 414 */         if (currentParameterLitType instanceof com.sun.xml.rpc.processor.model.literal.LiteralSequenceType || currentParameterLitType instanceof LiteralStructuredType) {
/*     */           
/* 416 */           Iterator<LiteralElementMember> members = ((LiteralStructuredType)currentParameterLitType).getElementMembers();
/*     */           
/* 418 */           while (members.hasNext()) {
/* 419 */             LiteralElementMember member = members.next();
/* 420 */             String memName = member.getName().getLocalPart();
/* 421 */             LiteralType memberType = member.getType();
/* 422 */             JavaType javaType = memberType.getJavaType();
/* 423 */             Class javaClass = getJavaClassFor((AbstractType)memberType);
/* 424 */             QName xmlTypeName = memberType.getName();
/* 425 */             ParameterMemberInfo info = new ParameterMemberInfo();
/* 426 */             info.addParameterMemberInfo(memName, xmlTypeName, javaClass);
/* 427 */             memberInfos.add(info);
/*     */           } 
/*     */         } 
/* 430 */         operationInfo.addMemberInfos(memberInfos);
/*     */         
/* 432 */         if (currentParameter.getLinkedParameter() != null) {
/*     */           continue;
/*     */         }
/*     */         
/* 436 */         Class parameterJavaType = getJavaClassFor(currentParameter.getType());
/*     */         
/* 438 */         operationInfo.addParameterModel(requestWsdlName.getLocalPart(), new LiteralElementMember(requestWsdlName, requestType));
/*     */         
/* 440 */         operationInfo.addParameterXmlTypeQName(currentParameterLitType.getName());
/* 441 */         operationInfo.addParameter(responseWsdlName.getLocalPart(), responseWsdlName, parameterJavaType, ParameterMode.OUT);
/*     */         
/* 443 */         operationInfo.addParameterXmlTypeQName(currentParameterLitType.getName());
/* 444 */         operationInfo.addMemberInfos(memberInfos);
/*     */       } 
/*     */     }
/*     */     
/* 448 */     if (operationModel.getStyle() == SOAPStyle.DOCUMENT)
/* 449 */       operationInfo.setProperty("javax.xml.rpc.soap.operation.style", "document"); 
/* 450 */     operationInfo.setProperty("javax.xml.rpc.encodingstyle.namespace.uri", "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildRpcLiteralOperation(OperationInfo operationInfo, Operation operationModel) {
/* 455 */     JavaMethod methodModel = operationModel.getJavaMethod();
/* 456 */     Request requestModel = operationModel.getRequest();
/* 457 */     Response responseModel = operationModel.getResponse();
/*     */     
/* 459 */     Block requestWsdlModel = null;
/* 460 */     Block responseWsdlModel = null;
/* 461 */     LiteralStructuredType requestType = null;
/* 462 */     LiteralStructuredType responseType = null;
/* 463 */     QName requestWsdlName = null;
/* 464 */     QName responseWsdlName = null;
/* 465 */     Iterator<Parameter> eachRequestParameter = null;
/* 466 */     Iterator<Parameter> eachResponseParameter = null;
/* 467 */     Iterator<LiteralElementMember> eachRequestWsdlParameter = null;
/* 468 */     Iterator<LiteralElementMember> eachResponseWsdlParameter = null;
/*     */     
/* 470 */     if (requestModel != null) {
/* 471 */       requestWsdlModel = requestModel.getBodyBlocks().next();
/* 472 */       eachRequestParameter = requestModel.getParameters();
/* 473 */       if (requestWsdlModel != null) {
/* 474 */         requestType = (LiteralStructuredType)requestWsdlModel.getType();
/* 475 */         eachRequestWsdlParameter = requestType.getElementMembers();
/* 476 */         requestWsdlName = requestWsdlModel.getName();
/*     */       } 
/*     */     } 
/*     */     
/* 480 */     if (responseModel != null) {
/* 481 */       responseWsdlModel = responseModel.getBodyBlocks().next();
/* 482 */       eachResponseParameter = responseModel.getParameters();
/* 483 */       if (responseWsdlModel != null) {
/* 484 */         responseType = (LiteralStructuredType)responseWsdlModel.getType();
/* 485 */         eachResponseWsdlParameter = responseType.getElementMembers();
/* 486 */         responseWsdlName = responseWsdlModel.getName();
/*     */       } 
/*     */     } else {
/* 489 */       operationInfo.setIsOneWay(true);
/*     */     } 
/* 491 */     operationInfo.setRequestQName(requestWsdlName);
/* 492 */     operationInfo.setResponseQName(responseWsdlName);
/*     */     
/* 494 */     JavaType returnJavaTypeModel = methodModel.getReturnType();
/* 495 */     if (returnJavaTypeModel != null && 
/* 496 */       !"void".equals(returnJavaTypeModel.getName()) && 
/* 497 */       eachResponseParameter != null) {
/* 498 */       Parameter returnParameter = eachResponseParameter.next();
/*     */       
/* 500 */       LiteralElementMember returnWsdlParameter = eachResponseWsdlParameter.next();
/* 501 */       LiteralType parameterParameterLitType = returnWsdlParameter.getType();
/* 502 */       ArrayList<ParameterMemberInfo> memberInfos = new ArrayList();
/* 503 */       if (parameterParameterLitType instanceof com.sun.xml.rpc.processor.model.literal.LiteralSequenceType || parameterParameterLitType instanceof LiteralStructuredType) {
/*     */         
/* 505 */         Iterator<LiteralElementMember> members = ((LiteralStructuredType)parameterParameterLitType).getElementMembers();
/*     */         
/* 507 */         while (members.hasNext()) {
/* 508 */           LiteralElementMember member = members.next();
/* 509 */           String memName = member.getName().getLocalPart();
/* 510 */           LiteralType memberType = member.getType();
/* 511 */           JavaType javaType = memberType.getJavaType();
/* 512 */           Class javaClass = getJavaClassFor((AbstractType)memberType);
/* 513 */           QName xmlTypeName = memberType.getName();
/* 514 */           ParameterMemberInfo info = new ParameterMemberInfo();
/* 515 */           info.addParameterMemberInfo(memName, xmlTypeName, javaClass);
/* 516 */           memberInfos.add(info);
/*     */         } 
/*     */       } 
/* 519 */       operationInfo.setReturnTypeModel(returnWsdlParameter);
/* 520 */       operationInfo.setReturnMembers(memberInfos);
/* 521 */       QName returnXmlType = returnParameter.getType().getName();
/* 522 */       String parameterName = returnParameter.getType().getName().getLocalPart();
/*     */       
/* 524 */       Class returnJavaType = getJavaClassFor(returnParameter.getType());
/*     */       
/* 526 */       operationInfo.setReturnClassName(returnParameter.getType().getJavaType().getName());
/* 527 */       operationInfo.setReturnType(returnXmlType, returnJavaType);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 532 */     if (eachRequestParameter != null) {
/* 533 */       while (eachRequestParameter.hasNext()) {
/* 534 */         Parameter currentParameter = eachRequestParameter.next();
/* 535 */         LiteralElementMember currentWsdlParameter = eachRequestWsdlParameter.next();
/* 536 */         LiteralType parameterParameterLitType = currentWsdlParameter.getType();
/* 537 */         ArrayList<ParameterMemberInfo> memberInfos = new ArrayList();
/* 538 */         if (parameterParameterLitType instanceof com.sun.xml.rpc.processor.model.literal.LiteralSequenceType || parameterParameterLitType instanceof LiteralStructuredType) {
/*     */           
/* 540 */           Iterator<LiteralElementMember> members = ((LiteralStructuredType)parameterParameterLitType).getElementMembers();
/*     */           
/* 542 */           while (members.hasNext()) {
/* 543 */             LiteralElementMember member = members.next();
/* 544 */             String memName = member.getName().getLocalPart();
/* 545 */             LiteralType memberType = member.getType();
/* 546 */             JavaType javaType = memberType.getJavaType();
/* 547 */             Class javaClass = getJavaClassFor((AbstractType)memberType);
/* 548 */             QName xmlTypeName = memberType.getName();
/* 549 */             ParameterMemberInfo info = new ParameterMemberInfo();
/* 550 */             info.addParameterMemberInfo(memName, xmlTypeName, javaClass);
/* 551 */             memberInfos.add(info);
/*     */           } 
/*     */         } 
/*     */         
/* 555 */         ParameterMode mode = ParameterMode.IN;
/* 556 */         if (currentParameter.getLinkedParameter() != null) {
/* 557 */           mode = ParameterMode.INOUT;
/*     */         }
/* 559 */         operationInfo.addMemberInfos(memberInfos);
/* 560 */         addParameterTo(operationInfo, currentWsdlParameter, mode);
/*     */       } 
/*     */     }
/* 563 */     if (eachResponseParameter != null) {
/* 564 */       while (eachResponseParameter.hasNext()) {
/* 565 */         Parameter currentParameter = eachResponseParameter.next();
/* 566 */         LiteralElementMember currentWsdlParameter = eachResponseWsdlParameter.next();
/* 567 */         LiteralType parameterParameterLitType = currentWsdlParameter.getType();
/* 568 */         ArrayList<ParameterMemberInfo> memberInfos = new ArrayList();
/* 569 */         if (parameterParameterLitType instanceof com.sun.xml.rpc.processor.model.literal.LiteralSequenceType || parameterParameterLitType instanceof LiteralStructuredType) {
/*     */           
/* 571 */           Iterator<LiteralElementMember> members = ((LiteralStructuredType)parameterParameterLitType).getElementMembers();
/*     */           
/* 573 */           while (members.hasNext()) {
/* 574 */             LiteralElementMember member = members.next();
/* 575 */             String memName = member.getName().getLocalPart();
/* 576 */             LiteralType memberType = member.getType();
/* 577 */             JavaType javaType = memberType.getJavaType();
/* 578 */             Class javaClass = getJavaClassFor((AbstractType)memberType);
/* 579 */             QName xmlTypeName = memberType.getName();
/* 580 */             ParameterMemberInfo info = new ParameterMemberInfo();
/* 581 */             info.addParameterMemberInfo(memName, xmlTypeName, javaClass);
/* 582 */             memberInfos.add(info);
/*     */           } 
/*     */         } 
/* 585 */         if (currentParameter.getLinkedParameter() != null) {
/*     */           continue;
/*     */         }
/* 588 */         operationInfo.addMemberInfos(memberInfos);
/* 589 */         addParameterTo(operationInfo, currentWsdlParameter, ParameterMode.OUT);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 594 */     operationInfo.setProperty("javax.xml.rpc.soap.operation.style", "rpc");
/* 595 */     operationInfo.setProperty("javax.xml.rpc.encodingstyle.namespace.uri", "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addParameterTo(OperationInfo operationInfo, SOAPStructureMember currentParameter, ParameterMode mode) {
/* 600 */     String parameterName = currentParameter.getName().getLocalPart();
/* 601 */     QName parameterXmlType = currentParameter.getType().getName();
/* 602 */     Class parameterJavaType = getJavaClassFor((AbstractType)currentParameter.getType());
/*     */     
/* 604 */     operationInfo.addParameter(parameterName, parameterXmlType, parameterJavaType, mode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addParameterTo(OperationInfo operationInfo, LiteralElementMember currentParameter, ParameterMode mode) {
/* 610 */     String parameterName = currentParameter.getName().getLocalPart();
/* 611 */     QName parameterXmlType = currentParameter.getType().getName();
/* 612 */     Class parameterJavaType = getJavaClassFor((AbstractType)currentParameter.getType());
/*     */     
/* 614 */     operationInfo.addParameterModel(parameterName, currentParameter);
/* 615 */     operationInfo.addParameter(parameterName, parameterXmlType, parameterJavaType, mode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class getJavaClassFor(AbstractType parameterType) {
/* 621 */     JavaType javaType = parameterType.getJavaType();
/*     */     
/* 623 */     String parameterJavaTypeName = javaType.getName();
/*     */     
/* 625 */     if (parameterJavaTypeName.indexOf("[][]") != -1) {
/* 626 */       String elementTypeName = getArrayElementTypeName(javaType);
/* 627 */       if (elementTypeName != null)
/* 628 */         parameterJavaTypeName = "[[L" + elementTypeName + ";"; 
/* 629 */     } else if (parameterJavaTypeName.indexOf("[]") != -1) {
/* 630 */       String elementTypeName = getArrayElementTypeName(javaType);
/* 631 */       if (elementTypeName != null)
/* 632 */         parameterJavaTypeName = "[L" + elementTypeName + ";"; 
/*     */     } 
/* 634 */     Class<?> parameterJavaType = null;
/*     */     try {
/* 636 */       if (parameterJavaTypeName != null) {
/* 637 */         parameterJavaType = getClassForName(parameterJavaTypeName, Arrays.asList(getPackages()));
/*     */         
/* 639 */         return parameterJavaType;
/*     */       } 
/* 641 */     } catch (NoClassDefFoundError e) {
/* 642 */       String message = e.getMessage();
/* 643 */       if (message.indexOf("wrong name:") != -1) {
/* 644 */         String qualifiedName = findQualifiedName(message);
/* 645 */         if (qualifiedName != null) {
/*     */           try {
/* 647 */             parameterJavaType = Class.forName(qualifiedName);
/* 648 */           } catch (Exception ex) {}
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 653 */     catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/* 657 */     return parameterJavaType;
/*     */   }
/*     */   
/*     */   private String getArrayElementTypeName(JavaType javaType) {
/* 661 */     JavaType elementType = null;
/* 662 */     if (javaType instanceof JavaArrayType)
/* 663 */       elementType = ((JavaArrayType)javaType).getElementType(); 
/* 664 */     if (elementType != null)
/* 665 */       return elementType.getName(); 
/* 666 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String findQualifiedName(String message) {
/* 672 */     StringTokenizer tokens = new StringTokenizer(message);
/* 673 */     while (tokens.hasMoreElements()) {
/* 674 */       String currentToken = tokens.nextToken();
/* 675 */       if (currentToken.equals("name:")) {
/* 676 */         String qualifiedToken = tokens.nextToken();
/* 677 */         if (qualifiedToken.indexOf("\\") != -1) {
/* 678 */           qualifiedToken = qualifiedToken.replace('\\', '.');
/*     */         
/*     */         }
/* 681 */         else if (qualifiedToken.indexOf("/") != -1) {
/* 682 */           qualifiedToken = qualifiedToken.replace('/', '.');
/*     */         } 
/* 684 */         qualifiedToken = qualifiedToken.replace(')', ' ');
/* 685 */         return qualifiedToken.trim();
/*     */       } 
/*     */     } 
/* 688 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class getClassForName(String name, Collection packages) {
/*     */     try {
/* 694 */       return Class.forName(name);
/* 695 */     } catch (ClassNotFoundException cnfe) {
/*     */       
/* 697 */       Iterator<Package> iter = packages.iterator();
/* 698 */       while (iter.hasNext()) {
/* 699 */         String qualifiedName = ((Package)iter.next()).getName() + "." + name;
/*     */         try {
/* 701 */           Class<?> kclass = Class.forName(qualifiedName);
/* 702 */           if (kclass != null)
/* 703 */             return kclass; 
/* 704 */         } catch (ClassNotFoundException cfe) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 709 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Model getModel(String wsdlLocation) throws ServiceException {
/* 714 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 715 */     Model model = null;
/*     */     try {
/* 717 */       Enumeration<URL> urls = null;
/*     */       try {
/* 719 */         urls = loader.getResources("META-INF/client.xml");
/* 720 */         if (urls == null || !urls.hasMoreElements())
/* 721 */           urls = loader.getResources("client.xml"); 
/* 722 */       } catch (IOException e) {
/* 723 */         throw new ServiceException(e);
/*     */       } 
/*     */       
/* 726 */       if (urls.hasMoreElements()) {
/* 727 */         URL url = urls.nextElement();
/*     */         
/* 729 */         InputStream in = null;
/*     */         try {
/* 731 */           in = url.openStream();
/* 732 */         } catch (IOException e) {
/* 733 */           throw new ServiceException(e);
/*     */         } 
/* 735 */         WebServicesClientParser parser = new WebServicesClientParser();
/* 736 */         WebServicesClient client = parser.parse(in);
/* 737 */         Iterator<WebService> iter = client.getWebServices().iterator();
/* 738 */         while (iter.hasNext()) {
/*     */           
/* 740 */           WebService webservice = iter.next();
/* 741 */           String webServiceLocation = webservice.getWsdlLocation();
/* 742 */           String webServiceModel = webservice.getModel();
/*     */           
/* 744 */           if (wsdlLocation.equalsIgnoreCase(webServiceLocation)) {
/*     */             
/* 746 */             URL modelURL = null;
/*     */             try {
/* 748 */               modelURL = new URL(webServiceModel);
/* 749 */             } catch (MalformedURLException e) {
/* 750 */               throw new ServiceException(e);
/*     */             } 
/* 752 */             InputStream is = null;
/*     */             try {
/* 754 */               is = modelURL.openStream();
/* 755 */             } catch (IOException e) {
/* 756 */               throw new ServiceException(e);
/*     */             } 
/*     */             
/* 759 */             ModelImporter importer = new ModelImporter(is);
/* 760 */             model = importer.doImport();
/* 761 */             return model;
/*     */           } 
/*     */         } 
/*     */       } 
/* 765 */     } catch (ModelException ex) {
/* 766 */       throw new ServiceException(ex);
/*     */     } 
/* 768 */     return model;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\ServiceInfoBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.StringUtils;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingFault;
/*     */ import com.sun.xml.rpc.wsdl.document.Message;
/*     */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*     */ import com.sun.xml.rpc.wsdl.document.OperationStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPHeader;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLModeler103
/*     */   extends WSDLModelerBase
/*     */ {
/*     */   public WSDLModeler103(WSDLModelInfo modelInfo, Properties options) {
/*  75 */     super(modelInfo, options);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setUnwrapped(LiteralStructuredType type) {}
/*     */ 
/*     */   
/*     */   protected Operation processSOAPOperationRPCLiteralStyle() {
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List processParameterOrder(Set<String> inputParameterNames, Set<String> outputParameterNames, StringBuffer resultParameterName) {
/*  92 */     if (isOperationDocumentLiteral()) {
/*  93 */       return processDocLitParameterOrder(inputParameterNames, outputParameterNames, resultParameterName);
/*     */     }
/*     */     
/*  96 */     if (resultParameterName == null)
/*  97 */       resultParameterName = new StringBuffer(); 
/*  98 */     SOAPBody soapRequestBody = getSOAPRequestBody();
/*  99 */     Message inputMessage = getInputMessage();
/* 100 */     SOAPBody soapResponseBody = getSOAPResponseBody();
/* 101 */     Message outputMessage = getOutputMessage();
/*     */ 
/*     */     
/* 104 */     String parameterOrder = this.info.portTypeOperation.getParameterOrder();
/* 105 */     List<String> parameterList = null;
/* 106 */     boolean buildParameterList = false;
/*     */     
/* 108 */     if (parameterOrder != null && !parameterOrder.trim().equals("")) {
/* 109 */       parameterList = XmlUtil.parseTokenList(parameterOrder);
/*     */     } else {
/* 111 */       parameterList = new ArrayList();
/* 112 */       buildParameterList = true;
/*     */     } 
/*     */     
/* 115 */     Set<String> partNames = new HashSet();
/*     */     
/* 117 */     Iterator<MessagePart> partsIter = getMessageParts(soapRequestBody, inputMessage, true).iterator();
/* 118 */     while (partsIter.hasNext()) {
/* 119 */       MessagePart part = partsIter.next();
/* 120 */       if (part.getDescriptorKind() != SchemaKinds.XSD_TYPE) {
/* 121 */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { inputMessage.getName(), part.getName() });
/*     */       }
/*     */ 
/*     */       
/* 125 */       partNames.add(part.getName());
/* 126 */       inputParameterNames.add(part.getName());
/* 127 */       if (buildParameterList) {
/* 128 */         parameterList.add(part.getName());
/*     */       }
/*     */     } 
/*     */     
/* 132 */     partsIter = getMessageParts(soapResponseBody, outputMessage, true).iterator();
/* 133 */     while (partsIter.hasNext()) {
/* 134 */       MessagePart part = partsIter.next();
/* 135 */       if (part.getDescriptorKind() != SchemaKinds.XSD_TYPE) {
/* 136 */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { outputMessage.getName(), part.getName() });
/*     */       }
/*     */ 
/*     */       
/* 140 */       partNames.add(part.getName());
/* 141 */       if (buildParameterList && resultParameterName.length() == 0) {
/*     */         
/* 143 */         resultParameterName.append(part.getName()); continue;
/*     */       } 
/* 145 */       outputParameterNames.add(part.getName());
/* 146 */       if (buildParameterList && 
/* 147 */         !inputParameterNames.contains(part.getName())) {
/* 148 */         parameterList.add(part.getName());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 153 */     if (!buildParameterList) {
/*     */       
/* 155 */       for (Iterator<String> iter = parameterList.iterator(); iter.hasNext(); ) {
/* 156 */         String name = iter.next();
/* 157 */         if (!partNames.contains(name)) {
/* 158 */           throw new ModelerException("wsdlmodeler.invalid.parameterorder.parameter", new Object[] { name, this.info.operation.getName().getLocalPart() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         partNames.remove(name);
/*     */       } 
/*     */ 
/*     */       
/* 168 */       if (partNames.size() > 1) {
/* 169 */         throw new ModelerException("wsdlmodeler.invalid.parameterOrder.tooManyUnmentionedParts", new Object[] { this.info.operation.getName().getLocalPart() });
/*     */       }
/*     */ 
/*     */       
/* 173 */       if (partNames.size() == 1) {
/*     */         
/* 175 */         String partName = partNames.iterator().next();
/* 176 */         if (outputParameterNames.contains(partName)) {
/* 177 */           resultParameterName.append(partName);
/*     */         }
/*     */       } 
/*     */     } 
/* 181 */     return parameterList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List processDocLitParameterOrder(Set<String> inputParameterNames, Set<String> outputParameterNames, StringBuffer resultParameterName) {
/* 191 */     Set<String> partNames = new HashSet();
/* 192 */     List<String> parameterList = new ArrayList();
/* 193 */     boolean gotOne = false;
/* 194 */     boolean isRequestResponse = (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     Iterator<MessagePart> partsIter = getMessageParts(getSOAPRequestBody(), getInputMessage(), true).iterator();
/*     */     
/* 201 */     while (partsIter.hasNext()) {
/* 202 */       if (gotOne) {
/* 203 */         warn("wsdlmodeler.warning.ignoringOperation.cannotHandleMoreThanOnePartInInputMessage", this.info.portTypeOperation.getName());
/*     */ 
/*     */         
/* 206 */         return null;
/*     */       } 
/*     */       
/* 209 */       MessagePart part = partsIter.next();
/* 210 */       if (part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT) {
/*     */ 
/*     */         
/* 213 */         warn("wsdlmodeler.warning.ignoringOperation.cannotHandleTypeMessagePart", this.info.portTypeOperation.getName());
/*     */ 
/*     */         
/* 216 */         return null;
/*     */       } 
/*     */       
/* 219 */       partNames.add(part.getName());
/* 220 */       inputParameterNames.add(part.getName());
/* 221 */       parameterList.add(part.getName());
/* 222 */       gotOne = true;
/*     */     } 
/*     */     
/* 225 */     boolean inputIsEmpty = !gotOne;
/*     */     
/* 227 */     if (isRequestResponse) {
/* 228 */       gotOne = false;
/*     */       
/* 230 */       partsIter = getMessageParts(getSOAPResponseBody(), getOutputMessage(), false).iterator();
/*     */       
/* 232 */       while (partsIter.hasNext()) {
/* 233 */         if (gotOne) {
/* 234 */           warn("wsdlmodeler.warning.ignoringOperation.cannotHandleMoreThanOnePartInOutputMessage", this.info.portTypeOperation.getName());
/*     */ 
/*     */           
/* 237 */           return null;
/*     */         } 
/*     */         
/* 240 */         MessagePart part = partsIter.next();
/* 241 */         if (part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT) {
/*     */ 
/*     */           
/* 244 */           warn("wsdlmodeler.warning.ignoringOperation.cannotHandleTypeMessagePart", this.info.portTypeOperation.getName());
/*     */ 
/*     */           
/* 247 */           return null;
/*     */         } 
/* 249 */         partNames.add(part.getName());
/*     */         
/* 251 */         if (!inputParameterNames.contains(part.getName()))
/*     */         {
/* 253 */           outputParameterNames.add(part.getName());
/*     */         }
/* 255 */         resultParameterName.append(part.getName());
/*     */       } 
/*     */     } 
/* 258 */     return parameterList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleLiteralSOAPFault(Response response, Set duplicateNames) {
/* 269 */     for (Iterator<BindingFault> iter = this.info.bindingOperation.faults(); iter.hasNext(); ) {
/* 270 */       BindingFault bindingFault = iter.next();
/*     */       
/* 272 */       warn("wsdlmodeler.warning.ignoringFault.documentOperation", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFaultName(String faultPartName, String soapFaultName, String bindFaultName, String faultMessageName) {
/* 296 */     return faultPartName;
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
/*     */   protected SchemaAnalyzerBase getSchemaAnalyzerInstance(WSDLDocument document, WSDLModelInfo _modelInfo, Properties _options, Set _conflictingClassNames, JavaSimpleTypeCreator _javaTypes) {
/* 308 */     return new SchemaAnalyzer103((AbstractDocument)document, (ModelInfo)_modelInfo, _options, _conflictingClassNames, _javaTypes);
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
/*     */   protected boolean createJavaException(Fault fault, Port port, String operationName) {
/*     */     SOAPOrderedStructureType sOAPOrderedStructureType;
/* 323 */     String exceptionName = null;
/* 324 */     String propertyName = getEnvironment().getNames().validJavaMemberName(fault.getName());
/*     */     
/* 326 */     SOAPType faultType = (SOAPType)fault.getBlock().getType();
/*     */     
/* 328 */     if (faultType instanceof SOAPStructureType) {
/* 329 */       exceptionName = makePackageQualified(getEnvironment().getNames().validJavaClassName(faultType.getName().getLocalPart()), faultType.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       SOAPStructureType soapStruct = (SOAPStructureType)this._faultTypeToStructureMap.get(faultType.getName());
/*     */ 
/*     */       
/* 337 */       if (soapStruct == null) {
/* 338 */         sOAPOrderedStructureType = new SOAPOrderedStructureType(faultType.getName());
/* 339 */         SOAPStructureType temp = (SOAPStructureType)faultType;
/* 340 */         Iterator<SOAPStructureMember> iterator = temp.getMembers();
/* 341 */         while (iterator.hasNext()) {
/* 342 */           sOAPOrderedStructureType.add(iterator.next());
/*     */         }
/* 344 */         this._faultTypeToStructureMap.put(faultType.getName(), sOAPOrderedStructureType);
/*     */       } 
/*     */     } else {
/* 347 */       exceptionName = makePackageQualified(getEnvironment().getNames().validJavaClassName(fault.getName()), port.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 352 */       sOAPOrderedStructureType = new SOAPOrderedStructureType(new QName(faultType.getName().getNamespaceURI(), fault.getName()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       QName memberName = new QName(fault.getBlock().getName().getNamespaceURI(), StringUtils.capitalize(faultType.getName().getLocalPart()));
/*     */ 
/*     */ 
/*     */       
/* 361 */       SOAPStructureMember soapMember = new SOAPStructureMember(memberName, faultType);
/*     */       
/* 363 */       JavaStructureMember javaMember = new JavaStructureMember(fault.getBlock().getName().getLocalPart(), faultType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 368 */       soapMember.setJavaStructureMember(javaMember);
/* 369 */       javaMember.setConstructorPos(0);
/* 370 */       javaMember.setReadMethod("get" + memberName.getLocalPart());
/* 371 */       javaMember.setInherited(soapMember.isInherited());
/* 372 */       soapMember.setJavaStructureMember(javaMember);
/* 373 */       sOAPOrderedStructureType.add(soapMember);
/*     */     } 
/* 375 */     if (isConflictingClassName(exceptionName)) {
/* 376 */       exceptionName = exceptionName + "_Exception";
/*     */     }
/*     */     
/* 379 */     JavaException existingJavaException = (JavaException)this._javaExceptions.get(exceptionName);
/*     */     
/* 381 */     if (existingJavaException != null && 
/* 382 */       existingJavaException.getName().equals(exceptionName) && (
/* 383 */       (SOAPType)existingJavaException.getOwner()).getName().equals(sOAPOrderedStructureType.getName())) {
/*     */ 
/*     */ 
/*     */       
/* 387 */       if (faultType instanceof SOAPStructureType) {
/* 388 */         fault.getBlock().setType((AbstractType)existingJavaException.getOwner());
/*     */       }
/*     */       
/* 391 */       fault.setJavaException(existingJavaException);
/* 392 */       createRelativeJavaExceptions(fault, port, operationName);
/* 393 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 398 */     JavaException javaException = new JavaException(exceptionName, false, sOAPOrderedStructureType);
/*     */     
/* 400 */     sOAPOrderedStructureType.setJavaType((JavaType)javaException);
/*     */     
/* 402 */     this._javaExceptions.put(javaException.getName(), javaException);
/*     */     
/* 404 */     Iterator<SOAPStructureMember> members = sOAPOrderedStructureType.getMembers();
/* 405 */     SOAPStructureMember member = null;
/*     */     
/* 407 */     for (int i = 0; members.hasNext(); i++) {
/* 408 */       member = members.next();
/* 409 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 410 */       javaMember.setConstructorPos(i);
/* 411 */       javaException.add(javaMember);
/*     */     } 
/* 413 */     if (faultType instanceof SOAPStructureType) {
/* 414 */       fault.getBlock().setType((AbstractType)sOAPOrderedStructureType);
/*     */     }
/* 416 */     fault.setJavaException(javaException);
/*     */     
/* 418 */     createRelativeJavaExceptions(fault, port, operationName);
/* 419 */     return true;
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
/*     */   protected void processHeaderFaults(SOAPHeader header, WSDLModelerBase.ProcessSOAPOperationInfo info, Response response, Set duplicateNames) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Extension getAnyExtensionOfType(Extensible extensible, Class type) {
/* 440 */     return getExtensionOfType(extensible, type);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\WSDLModeler103.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
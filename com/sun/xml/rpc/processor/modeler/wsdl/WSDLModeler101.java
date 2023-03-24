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
/*     */ public class WSDLModeler101
/*     */   extends WSDLModelerBase
/*     */ {
/*     */   public WSDLModeler101(WSDLModelInfo modelInfo, Properties options) {
/*  75 */     super(modelInfo, options);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Operation processSOAPOperationRPCLiteralStyle() {
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUnwrapped(LiteralStructuredType type) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List getMessageParts(SOAPBody body, Message message, boolean isInput) {
/*  97 */     if (body.getParts() != null) {
/*     */ 
/*     */       
/* 100 */       warn("wsdlmodeler.warning.ignoringOperation.cannotHandleBodyPartsAttribute", this.info.portTypeOperation.getName());
/*     */ 
/*     */       
/* 103 */       return null;
/*     */     } 
/* 105 */     ArrayList parts = new ArrayList();
/* 106 */     for (Iterator iter = message.parts(); iter.hasNext();) {
/* 107 */       parts.add(iter.next());
/*     */     }
/* 109 */     return parts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List processParameterOrder(Set<String> inputParameterNames, Set<String> outputParameterNames, StringBuffer resultParameterName) {
/* 117 */     if (isOperationDocumentLiteral()) {
/* 118 */       return processDocLitParameterOrder(inputParameterNames, outputParameterNames, resultParameterName);
/*     */     }
/*     */     
/* 121 */     if (resultParameterName == null)
/* 122 */       resultParameterName = new StringBuffer(); 
/* 123 */     Message inputMessage = getInputMessage();
/* 124 */     Message outputMessage = getOutputMessage();
/*     */     
/* 126 */     SOAPBody soapRequestBody = getSOAPRequestBody();
/* 127 */     SOAPBody soapResponseBody = getSOAPResponseBody();
/* 128 */     String parameterOrder = this.info.portTypeOperation.getParameterOrder();
/* 129 */     List<String> parameterList = null;
/*     */     
/* 131 */     boolean buildParameterList = false;
/*     */     
/* 133 */     if (parameterOrder != null) {
/* 134 */       parameterList = XmlUtil.parseTokenList(parameterOrder);
/*     */     } else {
/* 136 */       parameterList = new ArrayList();
/* 137 */       buildParameterList = true;
/*     */     } 
/*     */     
/* 140 */     Set<String> partNames = new HashSet();
/* 141 */     Iterator<MessagePart> partsIter = getMessageParts(soapRequestBody, inputMessage, true).iterator();
/* 142 */     while (partsIter.hasNext()) {
/* 143 */       MessagePart part = partsIter.next();
/* 144 */       if (part.getDescriptorKind() != SchemaKinds.XSD_TYPE) {
/* 145 */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { inputMessage.getName(), part.getName() });
/*     */       }
/*     */ 
/*     */       
/* 149 */       partNames.add(part.getName());
/* 150 */       inputParameterNames.add(part.getName());
/* 151 */       if (buildParameterList) {
/* 152 */         parameterList.add(part.getName());
/*     */       }
/*     */     } 
/* 155 */     for (Iterator<MessagePart> iter = outputMessage.parts(); iter.hasNext(); ) {
/* 156 */       MessagePart part = iter.next();
/* 157 */       if (part.getDescriptorKind() != SchemaKinds.XSD_TYPE) {
/* 158 */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { outputMessage.getName(), part.getName() });
/*     */       }
/*     */ 
/*     */       
/* 162 */       partNames.add(part.getName());
/* 163 */       if (buildParameterList && resultParameterName.length() == 0) {
/*     */         
/* 165 */         resultParameterName.append(part.getName()); continue;
/*     */       } 
/* 167 */       outputParameterNames.add(part.getName());
/* 168 */       if (buildParameterList && 
/* 169 */         !inputParameterNames.contains(part.getName())) {
/* 170 */         parameterList.add(part.getName());
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     if (!buildParameterList) {
/*     */       
/* 178 */       for (Iterator<String> iterator = parameterList.iterator(); iterator.hasNext(); ) {
/* 179 */         String name = iterator.next();
/* 180 */         if (!partNames.contains(name)) {
/* 181 */           throw new ModelerException("wsdlmodeler.invalid.parameterorder.parameter", new Object[] { name, this.info.operation.getName().getLocalPart() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 187 */         partNames.remove(name);
/*     */       } 
/*     */ 
/*     */       
/* 191 */       if (partNames.size() > 1) {
/* 192 */         throw new ModelerException("wsdlmodeler.invalid.parameterOrder.tooManyUnmentionedParts", new Object[] { this.info.operation.getName().getLocalPart() });
/*     */       }
/*     */ 
/*     */       
/* 196 */       if (partNames.size() == 1) {
/* 197 */         resultParameterName.append(partNames.iterator().next());
/*     */       }
/*     */     } 
/*     */     
/* 201 */     return parameterList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List processDocLitParameterOrder(Set<String> inputParameterNames, Set<String> outputParameterNames, StringBuffer resultParameterName) {
/* 211 */     Set<String> partNames = new HashSet();
/* 212 */     List<String> parameterList = new ArrayList();
/* 213 */     boolean gotOne = false;
/* 214 */     boolean isRequestResponse = (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     Iterator<MessagePart> partsIter = getMessageParts(getSOAPRequestBody(), getInputMessage(), true).iterator();
/*     */     
/* 221 */     while (partsIter.hasNext()) {
/* 222 */       if (gotOne) {
/* 223 */         warn("wsdlmodeler.warning.ignoringOperation.cannotHandleMoreThanOnePartInInputMessage", this.info.portTypeOperation.getName());
/*     */ 
/*     */         
/* 226 */         return null;
/*     */       } 
/*     */       
/* 229 */       MessagePart part = partsIter.next();
/* 230 */       if (part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT) {
/*     */ 
/*     */         
/* 233 */         warn("wsdlmodeler.warning.ignoringOperation.cannotHandleTypeMessagePart", this.info.portTypeOperation.getName());
/*     */ 
/*     */         
/* 236 */         return null;
/*     */       } 
/*     */       
/* 239 */       partNames.add(part.getName());
/* 240 */       inputParameterNames.add(part.getName());
/* 241 */       parameterList.add(part.getName());
/* 242 */       gotOne = true;
/*     */     } 
/*     */     
/* 245 */     boolean inputIsEmpty = !gotOne;
/*     */     
/* 247 */     if (isRequestResponse) {
/* 248 */       gotOne = false;
/*     */       
/* 250 */       partsIter = getMessageParts(getSOAPResponseBody(), getOutputMessage(), false).iterator();
/*     */       
/* 252 */       while (partsIter.hasNext()) {
/* 253 */         if (gotOne) {
/* 254 */           warn("wsdlmodeler.warning.ignoringOperation.cannotHandleMoreThanOnePartInOutputMessage", this.info.portTypeOperation.getName());
/*     */ 
/*     */           
/* 257 */           return null;
/*     */         } 
/*     */         
/* 260 */         MessagePart part = partsIter.next();
/* 261 */         if (part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT) {
/*     */ 
/*     */           
/* 264 */           warn("wsdlmodeler.warning.ignoringOperation.cannotHandleTypeMessagePart", this.info.portTypeOperation.getName());
/*     */ 
/*     */           
/* 267 */           return null;
/*     */         } 
/* 269 */         partNames.add(part.getName());
/*     */         
/* 271 */         if (!inputParameterNames.contains(part.getName()))
/*     */         {
/* 273 */           outputParameterNames.add(part.getName());
/*     */         }
/* 275 */         resultParameterName.append(part.getName());
/*     */       } 
/*     */     } 
/* 278 */     return parameterList;
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
/*     */   protected void handleLiteralSOAPFault(Response response, Set duplicateNames) {
/* 290 */     for (Iterator<BindingFault> iter = this.info.bindingOperation.faults(); iter.hasNext(); ) {
/* 291 */       BindingFault bindingFault = iter.next();
/*     */       
/* 293 */       warn("wsdlmodeler.warning.ignoringFault.documentOperation", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() });
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
/*     */   
/*     */   protected String getFaultName(String faultPartName, String soapFaultName, String bindFaultName, String faultMessageName) {
/* 318 */     return faultPartName;
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
/* 330 */     return new SchemaAnalyzer101((AbstractDocument)document, (ModelInfo)_modelInfo, _options, _conflictingClassNames, _javaTypes);
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
/* 345 */     String exceptionName = null;
/* 346 */     String propertyName = getEnvironment().getNames().validJavaMemberName(fault.getName());
/*     */     
/* 348 */     SOAPType faultType = (SOAPType)fault.getBlock().getType();
/*     */     
/* 350 */     if (faultType instanceof SOAPStructureType) {
/* 351 */       exceptionName = makePackageQualified(getEnvironment().getNames().validJavaClassName(faultType.getName().getLocalPart()), faultType.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 356 */       SOAPStructureType soapStruct = (SOAPStructureType)this._faultTypeToStructureMap.get(faultType.getName());
/*     */ 
/*     */       
/* 359 */       if (soapStruct == null) {
/* 360 */         sOAPOrderedStructureType = new SOAPOrderedStructureType(faultType.getName());
/* 361 */         SOAPStructureType temp = (SOAPStructureType)faultType;
/* 362 */         Iterator<SOAPStructureMember> iterator = temp.getMembers();
/* 363 */         while (iterator.hasNext()) {
/* 364 */           sOAPOrderedStructureType.add(iterator.next());
/*     */         }
/* 366 */         this._faultTypeToStructureMap.put(faultType.getName(), sOAPOrderedStructureType);
/*     */       } 
/*     */     } else {
/* 369 */       exceptionName = makePackageQualified(getEnvironment().getNames().validJavaClassName(fault.getName()), port.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 374 */       sOAPOrderedStructureType = new SOAPOrderedStructureType(new QName(faultType.getName().getNamespaceURI(), fault.getName()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 379 */       QName memberName = new QName(fault.getBlock().getName().getNamespaceURI(), StringUtils.capitalize(faultType.getName().getLocalPart()));
/*     */ 
/*     */ 
/*     */       
/* 383 */       SOAPStructureMember soapMember = new SOAPStructureMember(memberName, faultType);
/*     */       
/* 385 */       JavaStructureMember javaMember = new JavaStructureMember(fault.getBlock().getName().getLocalPart(), faultType.getJavaType(), soapMember);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 390 */       soapMember.setJavaStructureMember(javaMember);
/* 391 */       javaMember.setConstructorPos(0);
/* 392 */       javaMember.setReadMethod("get" + memberName.getLocalPart());
/* 393 */       javaMember.setInherited(soapMember.isInherited());
/* 394 */       soapMember.setJavaStructureMember(javaMember);
/* 395 */       sOAPOrderedStructureType.add(soapMember);
/*     */     } 
/* 397 */     if (isConflictingClassName(exceptionName)) {
/* 398 */       exceptionName = exceptionName + "_Exception";
/*     */     }
/*     */     
/* 401 */     JavaException existingJavaException = (JavaException)this._javaExceptions.get(exceptionName);
/*     */     
/* 403 */     if (existingJavaException != null && 
/* 404 */       existingJavaException.getName().equals(exceptionName) && (
/* 405 */       (SOAPType)existingJavaException.getOwner()).getName().equals(sOAPOrderedStructureType.getName())) {
/*     */ 
/*     */ 
/*     */       
/* 409 */       if (faultType instanceof SOAPStructureType) {
/* 410 */         fault.getBlock().setType((AbstractType)existingJavaException.getOwner());
/*     */       }
/*     */       
/* 413 */       fault.setJavaException(existingJavaException);
/* 414 */       createRelativeJavaExceptions(fault, port, operationName);
/* 415 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 420 */     JavaException javaException = new JavaException(exceptionName, false, sOAPOrderedStructureType);
/*     */     
/* 422 */     sOAPOrderedStructureType.setJavaType((JavaType)javaException);
/*     */     
/* 424 */     this._javaExceptions.put(javaException.getName(), javaException);
/*     */     
/* 426 */     Iterator<SOAPStructureMember> members = sOAPOrderedStructureType.getMembers();
/* 427 */     SOAPStructureMember member = null;
/*     */     
/* 429 */     for (int i = 0; members.hasNext(); i++) {
/* 430 */       member = members.next();
/* 431 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 432 */       javaMember.setConstructorPos(i);
/* 433 */       javaException.add(javaMember);
/*     */     } 
/* 435 */     if (faultType instanceof SOAPStructureType) {
/* 436 */       fault.getBlock().setType((AbstractType)sOAPOrderedStructureType);
/*     */     }
/* 438 */     fault.setJavaException(javaException);
/*     */     
/* 440 */     createRelativeJavaExceptions(fault, port, operationName);
/* 441 */     return true;
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
/* 462 */     return getExtensionOfType(extensible, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isBoundToSOAPBody(MessagePart part) {
/* 469 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isBoundToMimeContent(MessagePart part) {
/* 476 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\WSDLModeler101.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */